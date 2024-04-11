package com.cate.typingthedictionary.Controllers;

import com.cate.typingthedictionary.*;
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

    @FXML
    private ImageView doubleCorrectImage;

    static final int BACK_SPACE = 8;
    static final int ENTER      = 13;
    static final int ESCAPE     = 27;
    static final int SPACE      = 32;

    private final Font WORD_FONT       = Font.font("System", FontWeight.BOLD,   FontPosture.REGULAR, 20);
    private final Font DEFINITION_FONT = Font.font("System", FontWeight.NORMAL, FontPosture.REGULAR, 16);
    private final Font TEXT_INPUT_FONT = Font.font("Tahoma", FontWeight.NORMAL, FontPosture.REGULAR, 16);

    private final PlayerData PLAYER_DATA = PlayerData.getInstance();

    private AbstractMap.SimpleEntry<String, List<String>> currentEntry;
    private Dictionary dictionary;
    private boolean isGameRunning  = false;
    private boolean isEntryValid = false;
    private Validator validator;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadDictionary();
        loadNextEntry();
        displayNextEntry();
        displaySessionData();

        validator = new Validator();

        textInput.setFont(TEXT_INPUT_FONT);

    }

    public void handelKeyTyped(KeyEvent ke) {

        // Handle ESCAPE
        if (ke.getCharacter().charAt(0) == ESCAPE) { back(); }

        // Handle BACKSPACE
        if (!isGameRunning) {

            if (ke.getCharacter().charAt(0) == BACK_SPACE) {

                nextWord();

                return;
            }
            else {

                startGame();

                isGameRunning = true;
            }

        }

        // Handle typed content
        String enteredText = textInput.getText();

        boolean isInputValid   = validator.entryStartsWithText(currentEntry, enteredText);
        boolean isEntryComplete = validator.compareInputAndEntry(enteredText, currentEntry);

        if (isInputValid) {

            if (ke.getCharacter().charAt(0) == SPACE || ke.getCharacter().charAt(0) == ENTER) {

                PLAYER_DATA.setEntryWordsTyped(countTypedWords(enteredText));

                displaySessionData();

            }

        }

        if (isEntryComplete) {

            // stop time
            // TODO

            isEntryValid = true;

            PLAYER_DATA.setEntryWordsTyped(countTypedWords(enteredText));

            displaySessionData();

            // measure WPM
            // TODO

            PLAYER_DATA.saveEntryData();
            PLAYER_DATA.saveSessionData();

        }

        displayStatusIcons(isInputValid);

        // Handle ENTER to move to next word
        if (isEntryValid && ke.getCharacter().charAt(0) == ENTER) {
            nextWord();
        }


    }

    public void onBackClicked(ActionEvent ae) { back(); }

    public void onInfoClicked(ActionEvent ae) {

        // TODO: Save data

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
        nextWord();
    }

    public void nextWord() {

        // TODO
        isGameRunning = false;

        PLAYER_DATA.resetEntryData();

        clearInput();
        resetDisplay();

        loadNextEntry();
        displayNextEntry();

        textInput.requestFocus();

        isEntryValid = false;

    }

    public void startGame() {

        // TODO
        // Save timestamp

    }

    public void stopGame() {

        // TODO
        // Save timestamp
        // calculate WPM
    }

    private void back() {

        // Stop game
        // TODO: Save data

        Main main = new Main();

        try {
            main.changeScene(Constants.SUMMARY_VIEW);
        }
        catch (IOException e) {

            System.out.println("File " + Constants.SUMMARY_VIEW + " not found.");

            e.printStackTrace();
        }

    }

    private void clearInput() {
        Platform.runLater(() -> textInput.clear());
    }

    private int countTypedWords(String inputText) {
        return inputText.split("\\s+").length;
    }

    private void displayNextEntry() {

        final String BLANK_LINE = "\n\n";

        AbstractMap.SimpleEntry<String, List<String>> entry = currentEntry;

        Text word = new Text(entry.getKey());
        word.setFont(WORD_FONT);

        Platform.runLater(() -> {

            entryText.getChildren().clear();
            entryText.getChildren().add(word);

            for (String definition : entry.getValue()) {

                Text blankLine = new Text(BLANK_LINE);

                Text definitionText = new Text(definition);
                definitionText.setFont(DEFINITION_FONT);

                entryText.getChildren().addAll(blankLine, definitionText);

            }

        });

    }

    private void displaySessionData() {

        sessionWordsTyped.setText(Integer.toString(PLAYER_DATA.getSessionTotalWordsTyped() + PLAYER_DATA.getEntryWordsTyped()));
        sessionWPM.setText(Integer.toString(PLAYER_DATA.getSessionWPM()));

        if (PLAYER_DATA.getSessionErrors() + PLAYER_DATA.getEntryErrors() == 0) {
            sessionAccuracy.setText("100%");
        }
        else {
            sessionAccuracy.setText(PLAYER_DATA.calculateSessionAccuracy() + "%");
        }

    }

    private void displayStatusIcons(boolean isInputValid) {

        // Display correct entry icon
        if (isEntryValid) {

            doubleCorrectImage.setVisible(true);

            skipButton.setText("Next");
        }

        // Display correct icon
        if (isInputValid && !correctImage.isVisible()) {

            wrongImage.setVisible(false);
            correctImage.setVisible(true);

        }
        // Display incorrect icon
        else if (!isInputValid && !wrongImage.isVisible() && !isEntryValid) {

            correctImage.setVisible(false);
            wrongImage.setVisible(true);

            PLAYER_DATA.incrementEntryErrorCount();

            displaySessionData();
        }

    }

    private void loadDictionary() {

        // TODO swap back
//        dictionary = DictionaryLoader.loadDictionaryFromFile(Constants.DEFAULT_DICTIONARY_FILENAME);

        dictionary = DictionaryLoader.loadDictionaryFromFile("dictionaries/sorted_pretty_dictionary.json");
    }

    private void loadNextEntry() {
        currentEntry = dictionary.getRandomEntry();
    }

    private void resetDisplay() {

        wrongImage.setVisible(false);
        correctImage.setVisible(false);
        doubleCorrectImage.setVisible(false);

        skipButton.setText("Skip");

        displaySessionData();

    }

}
