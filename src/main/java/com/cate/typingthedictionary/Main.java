package com.cate.typingthedictionary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage stg;

    public static final String DEFAULT_DICTIONARY_FILENAME = "dictionaries/default_dictionary.json";

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {

        stg = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view.fxml"));
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("view.fxml")));

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Type the Dictionary");
        stage.getIcons().add(new Image(getClass().getResource("/images/icon.png").toString()));

        stage.show();

//        DictionaryLoader loader = new DictionaryLoader(DEFAULT_DICTIONARY_FILENAME);
//
//        Map<String, String> dictionary = loader.getMapDictionary();
//
//        for (Map.Entry<String, String> entry : dictionary.entrySet()) {
//            System.out.println(entry.getKey() + " " + entry.getValue());
//        }
    }

    public void changeScene(String fxml) throws IOException {

        Parent pane = FXMLLoader.load(getClass().getResource(fxml));

        stg.getScene().setRoot(pane);
    }

    /*
     TODO NOTES

     INCLUDE
        Disclaimer about content could be inappropriate

     SPLITTING

        Exception: months ["More than one month; the plural form of \"month\"."]

        String s = "[\"A period of three months.\",\"One of the three academic terms, " +
                "each comprising one-third of an academic year.\",\"One of the three divisions of pregnancy, each " +
                "lasting three months.\"]";

        String[] definitions = s.split("\",\"");

        for (String str : definitions) {
            str = StringCleaner.removeBrackets(str);
            str = StringCleaner.removeQuotations(str);
            System.out.println(str);
        }

     PARSING CHALLENGES

        Source at end of definition:
            oafs ["An elf's child; a changeling left by fairies or goblins, hence, a deformed or foolish child.
            Source:Wiktionary"]
            ointment ["A semisolid preparation intended for external application to the skin or mucous membranes. (source: UMLS)"]

        Source at end of definition after newline:
            regeneration ["The renewing or reuse of materials such as activated carbon, single ion exchange resins,
            and filter beds by appropriate means to remove organics, metals, solids, etc.\\n(Source: LEE)"]

        No period at end of definition:
            electric locomotive ["Locomotive that is propelled by electric motors"]

        Entry with unusual characters:
            Bororo ["A Macro-Gê language spoken by the Bororo people in the Central Mato Grosso region of Brazil."]

        Weird entry spelling issues:
            bedpan ["A small quite flat bowl used f ex in health care to collect urine from those confined to bed."]

        Contain unicode chars:
            it's…'s job to ["Having, as for me\/you\/him\/us\u2026, the task of"]
            N-methylamphetamine ["An addictive psychoactive drug of formula C\u2081\u2080H\u2081\u2085N."]

     CONSIDER REMOVING ENTRIES

        contains("ISO")
        contains("offensive"), contains("insult"), contains("Pejorative")
        contains("fictional character"), contains("fictional")
        contains("language"), contains("A language of"), contains("dialect")

        Zigzag ["A small town in Oregon, USA."]

        Having unique characters: ā, Ö, ó, ô, č, é, í, š

        Where word is all caps: COCOMO

     */

}