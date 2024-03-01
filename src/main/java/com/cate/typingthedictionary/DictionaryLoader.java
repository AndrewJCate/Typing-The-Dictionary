package com.cate.typingthedictionary;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DictionaryLoader {

    private final String fileName;
    private final JSONObject jsonDictionary;

    public DictionaryLoader(String fileName) {
        this.fileName = fileName;
        this.jsonDictionary = this.loadJSONDictionary();
    }

    // Returns raw JSON file
    public JSONObject getJSONDictionary() {
        return this.jsonDictionary;
    }

    // Converts JSONObject to Map<String, String>
    public Map<String, String> getMapDictionary() {

        Map<String, String> mapDictionary = new HashMap<>();

        for (Object key : this.jsonDictionary.keySet()) {
            mapDictionary.put(key.toString(), this.jsonDictionary.get(key).toString());
        }

        return mapDictionary;
    }

    // Attempts to retrieve the JSON dictionary from the provided file. Returns a JSONObject if successful. Returns null if no JSON data is found.
    private JSONObject loadJSONDictionary() {

        JSONObject jsonDictionary = null;
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(this.fileName)) {

            jsonDictionary = (JSONObject) parser.parse(reader);

        } catch (FileNotFoundException e) {

            System.out.println("File not found.");

            e.printStackTrace();

        } catch (IOException e) {

            System.out.println("Something went wrong when reading the file.");

            e.printStackTrace();

        } catch (ParseException e) {

            System.out.println("The dictionary file is not in valid JSON format. The file should contain one object with key (word) / value (definition) pairs.");

            e.printStackTrace();
        }

        return jsonDictionary;
    }
}
