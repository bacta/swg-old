package com.ocdsoft.bacta.swg.name;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.swg.name.generator.CreatureNameGenerator;
import com.ocdsoft.bacta.swg.name.generator.NameGenerator;
import com.ocdsoft.bacta.swg.name.generator.PlayerNameGenerator;
import com.ocdsoft.bacta.tre.TreeFile;
import org.apache.commons.codec.language.ColognePhonetic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by kburkhardt on 3/28/14.
 */
@Singleton
public final class DefaultNameService implements NameService {

    private final Logger logger = LoggerFactory.getLogger(DefaultNameService.class);

    private final Set<String> fictionalNames;
    private final Set<String> developersNames;
    private final List<ProfaneWord> profaneWords;
    private final ColognePhonetic phonetic;

    private final Map<Integer, NameGenerator> nameGenerators;

    @Inject
    public DefaultNameService(TreeFile treeFile,
                              CreatureNameGenerator creatureNameGenerator,
                              PlayerNameGenerator playerNameGenerator) throws IOException {

        nameGenerators = new HashMap<>();
        nameGenerators.put(NameService.CREATURE, creatureNameGenerator);
        nameGenerators.put(NameService.PLAYER, playerNameGenerator);

        phonetic = new ColognePhonetic();

        fictionalNames =  loadList(this.getClass().getResourceAsStream("/name/fictionalreserved.lst"));
        developersNames =  loadList(this.getClass().getResourceAsStream("/name/developers.lst"));
        profaneWords = new ArrayList<>();

        ResourceBundle bundle = ResourceBundle.getBundle("name.profane");
        for(String word : bundle.keySet()) {
            boolean substringMatch = bundle.getString(word).equalsIgnoreCase("1");
            ProfaneWord newWord = new ProfaneWord(word.toLowerCase(), substringMatch);
            profaneWords.add(newWord);
        }
//
//        final IffReader<DataTable> dataTableReader = new DataTableIffReader();
//        final DataTable dataTable = dataTableReader.read(new ChunkReader("datatables/chat/profanity_filter.iff", treeFile.open("datatables/chat/profanity_filter.iff")));
//
//        for (DataTableRow row : dataTable.getRows()) {
//            String word = row.get(0).getString();
//            boolean substringMatch = row.get(1).getInt() == 1;
//            ProfaneWord newWord = new ProfaneWord(word.toLowerCase(), substringMatch);
//            profaneWords.add(newWord);
//        }
    }

    private Set<String> loadList(InputStream inputStream) {

        Set<String> newSet = new TreeSet<>();

        Scanner scanner = new Scanner(inputStream);
        while(scanner.hasNext()) {
            newSet.add(phonetic.encode(scanner.next()));
        }
        return newSet;
    }

    @Override
    public String generateName(int type, Object... args) {
        NameGenerator generator = nameGenerators.get(type);
        if(generator == null) {
            logger.error("Name Generator for type " + type + " not available");
            return NAME_DECLINED_NO_NAME_GENERATOR;
        }

        String name = generator.createName(args);
        while(validateName(type, name, args) != NameService.NAME_APPROVED) {
            name = generator.createName(args);
        }

        return name;
    }

    @Override
    public String validateName(int type, String name, Object... args) {

        NameGenerator generator = nameGenerators.get(type);
        if(generator == null) {
            logger.error("Name Generator for type " + type + " not available");
            return NAME_DECLINED_NO_NAME_GENERATOR;
        }

        StringTokenizer nameTokens = new StringTokenizer(name);
        while(nameTokens.hasMoreTokens()) {
            String token = nameTokens.nextToken();
            String result = namePreChecks(token);
            if(result != NameService.NAME_APPROVED) {
                return result;
            }
        }

        return generator.validateName(name, args);
    }

    private String namePreChecks(String name) {

        for(ProfaneWord word : profaneWords) {
            if(word.subStringMatch) {
                if(name.toLowerCase().contains(word.word)) {
                    return NAME_DECLINED_RESERVED;
                }
            } else {
                if(name.equalsIgnoreCase(word.word)) {
                    return NAME_DECLINED_RESERVED;
                }
            }
        }

        String encodedName = phonetic.encode(name);

        if(fictionalNames.contains(encodedName)) {
            return NAME_DECLINED_FICTIONALLY_RESERVED;
        }

        if(developersNames.contains(encodedName)) {
            return NAME_DECLINED_DEVELOPER;
        }

        return NAME_APPROVED;
    }

    @Override
    public void addPlayerName(String firstName) {
        PlayerNameGenerator nameGenerator = (PlayerNameGenerator) nameGenerators.get(NameService.PLAYER);
        nameGenerator.addPlayerName(firstName);
    }

    private class ProfaneWord {
        String word;
        boolean subStringMatch;

        public ProfaneWord(String word, boolean subStringMatch) {
            this.word = word;
            this.subStringMatch = subStringMatch;
        }
    }
}
