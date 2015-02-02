package com.ocdsoft.bacta.swg.data.language;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.swg.data.SharedFileLoader;
import com.ocdsoft.bacta.swg.shared.iff.IffReader;
import com.ocdsoft.bacta.swg.shared.iff.chunk.ChunkReader;
import com.ocdsoft.bacta.swg.shared.iff.datatable.DataTable;
import com.ocdsoft.bacta.swg.shared.iff.datatable.DataTableIffReader;
import com.ocdsoft.bacta.swg.shared.iff.datatable.DataTableRow;
import com.ocdsoft.bacta.tre.TreeFile;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by crush on 3/28/14.
 */
@Singleton
public class GameLanguage implements SharedFileLoader {
    private final Map<String, GameLanguageInfo> gameLanguage = new HashMap<>();
    private final TreeFile treeFile;

    @Inject
    public GameLanguage(TreeFile treeFile) {
        this.treeFile = treeFile;
        load();
    }

    /**
     * A {@link GameLanguageInfo} can be retrieved by
     * specifying either the speak skill, comprehension skill, or language name. i.e. language_basic_speak,
     * language_basic_comprehend, and basic will all return the object for the basic language.
     *
     * @param language The key representing the desired GameLanguageInfo.
     * @return {@link GameLanguageInfo} if key exists.
     * Otherwise, null.
     */
    public GameLanguageInfo getGameLanguageInfo(final String language) {
        return gameLanguage.get(language);
    }

    private void load() {
        final IffReader<DataTable> dataTableReader = new DataTableIffReader();
        final DataTable dataTable = dataTableReader.read(
                new ChunkReader("datatables/game_language/game_language.iff", treeFile.open("datatables/game_language/game_language.iff")));

        for (DataTableRow row : dataTable.getRows()) {
            GameLanguageInfo languageInfo = new GameLanguageInfo(row);
            gameLanguage.put(languageInfo.speakSkillModName, languageInfo);
            gameLanguage.put(languageInfo.comprehendSkillModName, languageInfo);
            gameLanguage.put(languageInfo.stringId, languageInfo);
        }
    }

    @Override
    public void reload() {
        synchronized (this) {
            gameLanguage.clear();
            load();
        }
    }

    public static final class GameLanguageInfo {
        @Getter
        private final String speakSkillModName;
        @Getter
        private final String comprehendSkillModName;
        @Getter
        private final String stringId;
        @Getter
        private final int audible;

        private final String[] alphabet = new String[26];

        public GameLanguageInfo(DataTableRow row) {
            speakSkillModName = row.get(0).getString();
            comprehendSkillModName = row.get(1).getString();
            stringId = row.get(2).getString();

            for (int i = 0; i < alphabet.length; i++)
                alphabet[i] = row.get(i + 3).getString();

            audible = row.get(alphabet.length + 3).getInt();
        }
    }
}
