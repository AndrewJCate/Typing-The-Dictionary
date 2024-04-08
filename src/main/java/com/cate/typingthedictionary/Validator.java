package com.cate.typingthedictionary;

import java.util.AbstractMap;
import java.util.List;

public class Validator {

    public boolean compareInputAndEntry(String input, AbstractMap.SimpleEntry<String, List<String>> entry) {
        return input.equals(entryToString(entry));
    }

    public boolean entryStartsWithText(AbstractMap.SimpleEntry<String, List<String>> entry, String text) {

        String entryString = entryToString(entry);

        return (entryString.startsWith(text));
    }

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
