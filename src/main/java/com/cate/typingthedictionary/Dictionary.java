package com.cate.typingthedictionary;

import com.cate.typingthedictionary.exceptions.WordAlreadyExistsException;

import java.util.*;

/**
 * A dictionary which is a Map of String keys and List<String> values. A key-value pair make up a dictionary entry.
 * Each entry consists of a word (key) and a List of its definition(s) (value).
 */
public class Dictionary {

    private final Map<String, List<String>> DICTIONARY;

    private List<String> wordList;

    /**
     * Instantiates a new Dictionary from an existing dictionary.
     *
     * @param dictionary the dictionary
     */
    public Dictionary(Map<String, List<String>> dictionary) {
        DICTIONARY = dictionary;
    }

    /**
     * Instantiates a new Dictionary.
     */
    public Dictionary() {
        DICTIONARY = new HashMap<>();
    }

    public Map<String, List<String>> getDictionary() {
        return DICTIONARY;
    }

    /**
     * Add an entry (word and definitions) to the dictionary.
     *
     * Throws an exception if a word is already in the dictionary to prevent duplicate entries.
     *
     * @param word        the word
     * @param definitions the definitions
     * @return true if word is added successfully
     */
    public boolean addEntry(String word, List<String> definitions) {

        if (!word.trim().isEmpty() && definitions.size() > 0) {

            if (DICTIONARY.containsKey(word)) {
                throw new WordAlreadyExistsException("Word already exists. If replacing a word, remove the old word " +
                        "first with Dictionary.remove(String word).");
            }

            DICTIONARY.put(word, definitions);

            return true;
        }

        return false;
    }

    /**
     * Gets random entry from the dictionary. An entry is a word and its definitions.
     *
     * @return the random entry
     */
    public AbstractMap.SimpleEntry<String, List<String>> getRandomEntry() {

        if (wordList == null || wordList.isEmpty()) {
            createWordList();
        }

        if (wordList.isEmpty()) return null;

        int randomIndex = new Random().nextInt(wordList.size());

        String word = wordList.get(randomIndex);

        return new AbstractMap.SimpleEntry<>(word, DICTIONARY.get(word));
    }

    /**
     * Removes an entry from the dictionary.
     *
     * @return removed entry or null if word not found
     */
    public AbstractMap.SimpleEntry<String, List<String>> removeEntry(String word) {

        if (!word.trim().isEmpty() && !DICTIONARY.isEmpty()) {

            List<String> definitions = DICTIONARY.remove(word);

            return new AbstractMap.SimpleEntry<>(word, definitions);
        }

        return null;
    }


    private void createWordList() {
        wordList = new ArrayList<>(DICTIONARY.keySet());
    }

}
