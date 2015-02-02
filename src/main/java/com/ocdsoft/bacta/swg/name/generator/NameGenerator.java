package com.ocdsoft.bacta.swg.name.generator;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Created by Kyle on 8/17/2014.
 */
public abstract class NameGenerator {

    protected final Map<String, String> letterMapping;
    protected final Map<String, String> letterRules;
    private final Random random;

    public NameGenerator() {

        random = new Random();

        ResourceBundle nameBundle = ResourceBundle.getBundle("name.lettermappings");
        letterMapping = new HashMap<>();
        letterMapping.put(" ", "abcdefghijklmnopqrstuvwxyz");
        letterRules = new HashMap<>();

        for(String letter : nameBundle.keySet()) {
            String letters = nameBundle.getString(letter);
            if(letter.length() == 1) {
                letterMapping.put(letter, letters);
            } else {
                letterRules.put(letter, letters);
            }
        }
    }

    public abstract String validateName(String name, Object... args);

    public abstract String createName(Object... args);

    protected String generateRandomCharacterSet(int minLength, int maxlength) {

        int length = random.nextInt(maxlength - minLength) + minLength;
        String newName = "";
        int consecutiveVowels = 0;
        int consecutiveConsonants = 0;

        String nextSet;
        for(int i = 0; i< length; ++i) {

            if(newName.isEmpty()) {
                nextSet = letterMapping.get(" ");
            } else if(consecutiveVowels > 1) {
                nextSet = letterRules.get("doublevowel");
            } else if(consecutiveConsonants > 1) {
                nextSet = letterRules.get("doubleconsonent");
            } else if(newName.length() >= 2
                    && newName.charAt(newName.length() - 1) == 'u'
                    && newName.charAt(newName.length() - 2) == 'q') {
                nextSet = letterRules.get("qu");
            } else {
                nextSet = letterMapping.get(String.valueOf(newName.charAt(newName.length() - 1)));
            }

            newName += nextSet.charAt(random.nextInt(nextSet.length()));
        }

        return newName;
    }
}
