package com.cate.typingthedictionary.Controllers;

import com.cate.typingthedictionary.Dictionary;
import com.cate.typingthedictionary.DictionaryLoader;
import com.cate.typingthedictionary.Main;
import com.cate.typingthedictionary.Validator;
import com.cate.typingthedictionary.constants.Constants;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.*;

import java.io.IOException;
import java.net.URL;
import java.util.AbstractMap;
import java.util.List;
import java.util.ResourceBundle;

/*
    TODO

    - line space in input text
    - skip word makes both valid/invalid images hidden
    - invalid image display increments errors counter
 */

public class GameController implements Initializable {

    @FXML
    private Button infoButton;

    @FXML
    private Button skipButton;

    @FXML
    private Button backButton;

    @FXML
    private Text sessionWordsTyped;

    @FXML
    private Text sessionWPM;

    @FXML
    private Text sessionAccuracy;

    @FXML
    private TextFlow entryText;

    @FXML
    private TextArea textInput;

    @FXML
    private ImageView correctImage;

    @FXML
    private ImageView wrongImage;

    static final int BACK_SPACE = 8;
    static final int ENTER = 13;

    private final Font WORD_FONT = Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 20);
    private final Font DEFINITION_FONT = Font.font("System", FontWeight.NORMAL, FontPosture.REGULAR, 16);
    private final Font TEXT_INPUT_FONT = Font.font("Tahoma", FontWeight.NORMAL, FontPosture.REGULAR, 16);

    private AbstractMap.SimpleEntry<String, List<String>> currentEntry;
    private Dictionary dictionary;
    private int errorCount = 0;
    private boolean isGameRunning = false;
    private Validator validator;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.loadDictionary();
        this.loadNextEntry();
        this.displayNextEntry();
        this.validator = new Validator();

        this.textInput.setFont(TEXT_INPUT_FONT);

    }

    public void handelKeyTyped(KeyEvent ke) {

        // Skip word if first key pressed is backspace
        if (!isGameRunning) {

            if (ke.getCharacter().charAt(0) == BACK_SPACE) {
                skipWord();
            }
            else {
                startGame(ke);
                isGameRunning = true;
            }

        }
        else {
            // handle typed content

            String enteredText = textInput.getText();

            boolean isValid = validator.entryStartsWithText(currentEntry, enteredText);

            if (isValid && !correctImage.isVisible()) {
                wrongImage.setVisible(false);
                correctImage.setVisible(true);
            }
            else if (!isValid && !wrongImage.isVisible()) {
                correctImage.setVisible(false);
                wrongImage.setVisible(true);
            }

            // TODO
        }

    }

    public void onBackClicked(ActionEvent ae) {

        Main main = new Main();

        try {
            main.changeScene(Constants.SUMMARY_VIEW);
        }
        catch (IOException e) {

            System.out.println("File " + Constants.SUMMARY_VIEW + " not found.");

            e.printStackTrace();
        }

    }

    public void onInfoClicked(ActionEvent ae) {

        Main main = new Main();

        try {
            main.changeScene(Constants.INFO_VIEW);
        }
        catch (IOException e) {

            System.out.println("File " + Constants.INFO_VIEW + " not found.");

            e.printStackTrace();
        }

    }

    public void onSkipClicked(ActionEvent ae) {
        skipWord();
    }

    public void skipWord() {

        // TODO
        isGameRunning = false;

        loadNextEntry();
        displayNextEntry();
        clearInput();
        textInput.requestFocus();

    }

    public void startGame(KeyEvent ke) {

        // TODO
        // Save timestamp

    }

    private void clearInput() {
        Platform.runLater(() -> { textInput.clear(); });
    }

    private void loadDictionary() {

        // TODO swap back
//        this.dictionary = DictionaryLoader.loadDictionaryFromFile(Constants.DEFAULT_DICTIONARY_FILENAME);

        this.dictionary = DictionaryLoader.loadDictionaryFromFile("dictionaries/sorted_pretty_dictionary.json");
    }

    private void loadNextEntry() {
        this.currentEntry = this.dictionary.getRandomEntry();
    }

    private void displayNextEntry() {

        final String BLANK_LINE = "\n\n";

        AbstractMap.SimpleEntry<String, List<String>> entry = this.currentEntry;

        Text word = new Text(entry.getKey());
        word.setFont(WORD_FONT);

        Platform.runLater(() -> {

            this.entryText.getChildren().clear();
            this.entryText.getChildren().add(word);

            for (String s : entry.getValue()) {

                Text blankLine = new Text(BLANK_LINE);

                Text entryText = new Text(s);
                entryText.setFont(DEFINITION_FONT);

                this.entryText.getChildren().addAll(blankLine, entryText);

            }

        });

    }

}
