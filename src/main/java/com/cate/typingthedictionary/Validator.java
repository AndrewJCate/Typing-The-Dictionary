package com.cate.typingthedictionary;

import java.util.AbstractMap;
import java.util.List;

/**
 * Used to validate the accuracy of entered text compared to the entry text.
 */
public class Validator {

    /**
     * Used to compare {@link Dictionary} entry text against input text.
     *
     * @param input the input
     * @param entry the entry text
     * @return true if input matches entire entry text
     */
    public boolean compareInputAndEntry(String input, AbstractMap.SimpleEntry<String, List<String>> entry) {

        if (entry == null) return false;

        return input.equals(entryToString(entry));
    }

    /**
     * Compares beginning of entry against entered text. This can be used to determine if the current input is
     * accurate before the entire entry text is input.
     *
     * @param entry the entry text
     * @param text  the input text
     * @return true if the entry starts with the current input
     */
    public boolean entryStartsWithText(String text, AbstractMap.SimpleEntry<String, List<String>> entry) {

        String entryString = entryToString(entry);

        return (entryString.startsWith(text));
    }

    /**
     * Converts entry word and definitions to a String. Can be used for calculations or to print the entry.
     *
     * @param entry the entry
     * @return the string
     */
    private String entryToString(AbstractMap.SimpleEntry<String, List<String>> entry) {

        StringBuilder sb = new StringBuilder();

        sb.append(entry.getKey());

        for (String s : entry.getValue()) {
            sb.append("\n\n");
            sb.append(s);
        }

        return sb.toString();
    }

}
