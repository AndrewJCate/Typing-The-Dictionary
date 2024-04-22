package com.cate.typingthedictionary.io;

import com.cate.typingthedictionary.Dictionary;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Dictionary loader handles reading dictionary data from a JSON file.
 */
public class DictionaryLoader {

    /**
     * Reads from a JSON file and adds entries from the data into a new {@link Dictionary}.
     *
     * @param fileName the file name
     * @return the dictionary
     */
// Attempts to retrieve the JSON dictionary from the provided file.
    // Returns Map with String key as the word and List<String> value as the word's definitions.
    // Returns empty HashMap if no JSON data is found.
    public static Dictionary loadDictionaryFromFile(String fileName) {

        Dictionary dictionary = new Dictionary();

        try (FileReader reader = new FileReader(fileName)) {

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
