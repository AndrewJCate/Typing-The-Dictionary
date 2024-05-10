package com.cate.typingthedictionary.io;

import com.cate.typingthedictionary.Dictionary;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Dictionary loader handles reading dictionary data from a JSON file.
 */
public class DictionaryLoader {

    /**
     * Reads from a JSON file and adds entries from the data into a new {@link Dictionary}.
     *
     * @param fileName the file name relative to the resources folder
     * @return a Map with String key as the word and List<String> value as the word's definitions or an empty Map if
     * no JSON data is found.
     */
    public static Dictionary loadDictionaryFromFile(String fileName) {

        Dictionary dictionary = new Dictionary();

        try (InputStreamReader reader = new InputStreamReader((Objects.requireNonNull(
                DictionaryLoader.class.getResourceAsStream(fileName))), StandardCharsets.UTF_8
                )) {

            Type mapType = new TypeToken<Map<String, String>>() {}.getType();

            Map<String, String> rawDictionary = new Gson().fromJson(reader, mapType);

            for (String word : rawDictionary.keySet()) {

                Type listType = new TypeToken<List<String>>() {}.getType();

                List<String> definitions = new Gson().fromJson(rawDictionary.get(word), listType);

                dictionary.addEntry(word, definitions);
            }
        }
        catch (FileNotFoundException e) {

            System.out.println("File not found.");

            e.printStackTrace();
        }
        catch (IOException e) {

            System.out.println("Something went wrong when reading the file.");

            e.printStackTrace();
        }

        return dictionary;
    }

}
