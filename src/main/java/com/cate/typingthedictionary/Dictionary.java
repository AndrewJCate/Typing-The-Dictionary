package com.cate.typingthedictionary;

import com.cate.typingthedictionary.exceptions.WordAlreadyExistsException;

import java.util.*;

public class Dictionary {

    private final Map<String, List<String>> DICTIONARY;

    private List<String> wordList;

    public Dictionary(Map<String, List<String>> dictionary) {

        if (!dictionary.isEmpty()) {

            this.DICTIONARY = dictionary;
        }
        else {

            this.DICTIONARY = new HashMap<>();
        }

    }

    public Dictionary() {
        this.DICTIONARY = new HashMap<>();
    }

    public AbstractMap.SimpleEntry<String, List<String>> getRandomEntry() {

        if (this.wordList == null || this.wordList.isEmpty()) {
            this.createWordList();
        }

        int randomIndex = new Random().nextInt(this.wordList.size());

        String word = this.wordList.get(randomIndex);

        return new AbstractMap.SimpleEntry<>(word, this.DICTIONARY.get(word));

    }

    public Map<String, List<String>> getAllEntries() {
        return this.DICTIONARY;
    }

    // Replaces previous word if present
    // Returns true if operation is successful
    public boolean addEntry(String word, List<String> definitions) {

        if (!word.trim().isEmpty() && definitions.size() > 0) {

            if (this.DICTIONARY.containsKey(word)) {
                throw new WordAlreadyExistsException("Word already exists. If replacing a word, remove the old word " +
                        "first with Dictionary.remove(String word).");
            }

            this.DICTIONARY.put(word, definitions);

            return true;
        }

        return false;
    }

    // Returns null if word not found
    public AbstractMap.SimpleEntry<String, List<String>> removeEntry(String word) {

        if (!word.trim().isEmpty()) {

            List<String> definitions = this.DICTIONARY.remove(word);

            return new AbstractMap.SimpleEntry<>(word, definitions);
        }

        return null;
    }

    public List<String> getWordList() {
        return this.wordList;
    }

    private void createWordList() {
        this.wordList = new ArrayList<>(this.DICTIONARY.keySet());
    }

}
