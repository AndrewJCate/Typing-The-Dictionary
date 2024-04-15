package com.cate.typingthedictionary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    private static Stage stg;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {

        stg = stage;

        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("summary.fxml"))));

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("css/styles.css")).toExternalForm());

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Type the Dictionary");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/images/icon.png")).toString()));

        stage.show();

    }

    public void changeScene(String fxml) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));

        stg.getScene().setRoot(root);
    }

    // TODO: DELETE THIS STUFF
    /*
        Dictionary dictionary = DictionaryLoader.loadDictionaryFromJson(Constants.DEFAULT_DICTIONARY_FILENAME);

        Map<String, List<String>> entries = dictionary.getAllEntries();

        for (String entry : entries.keySet()) {
            System.out.println(entry);

            for (String definition : entries.get(entry)) {
                System.out.println(definition);
            }

            System.out.println();
        }

        AbstractMap.SimpleEntry<String, List<String>> entry = dictionary.getRandomEntry();
        System.out.println(entry.getKey());
        System.out.println(entry.getValue());

        int longest = 0;
        String longestWord = "";
        String longestDef = "";

        for (
            Map.Entry<String, List<String>> entry : dictionary.getAllEntries()) {

            int len = entry.getKey().length() + entry.getValue().length();

            if (len > longest) {
                longest = len;
                longestWord = entry.getKey();
                longestDef = entry.getValue();
            }

        }

        System.out.println("Longest = " + longest + ": " + longestWord + " " + longestDef);
    */
}