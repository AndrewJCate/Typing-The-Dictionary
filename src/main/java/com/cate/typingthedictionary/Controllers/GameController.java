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
import java.time.Instant;
import java.util.AbstractMap;
import java.util.List;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML
    private Button skipButton;

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
    private boolean    isGameRunning = false;
    private boolean    isEntryValid  = false;
    private Instant    startTime;
    private Validator  validator;


    /* Public methods */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadDictionary();

        validator = new Validator();

        textInput.setFont(TEXT_INPUT_FONT);

        nextWord();
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

                startTime = Instant.now();

                isGameRunning = true;
            }
        }

        // Handle typed content
        String enteredText = textInput.getText();

        boolean isInputValid    = validator.entryStartsWithText(currentEntry, enteredText);
        boolean isEntryComplete = validator.compareInputAndEntry(enteredText, currentEntry);

        if (isInputValid && !isEntryValid) {

            if (ke.getCharacter().charAt(0) == SPACE || ke.getCharacter().charAt(0) == ENTER) {
                displaySessionData();
            }
        }

        if (isEntryComplete) {

            isEntryValid = true;

            displaySessionData();

            saveData();
        }

        displayStatusIcons(isInputValid);

        // Handle ENTER to move to next word
        if (isEntryValid && ke.getCharacter().charAt(0) == ENTER) {
            nextWord();
        }
    }

    public void onBackClicked(ActionEvent ae) { back(); }

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
        nextWord();
    }

    public void nextWord() {

        clearInput();
        resetEntryData();

        Platform.runLater(() -> {
            resetDisplay();

            currentEntry = dictionary.getRandomEntry();
            displayNextEntry();

            textInput.requestFocus();
        });
    }


    /* Private methods */

    private void back() {

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

        String inputText = textInput.getText();

        int totalSessionWordsTyped = PLAYER_DATA.getSessionWordsTyped(inputText);
        int totalSessionAccuracy   = PLAYER_DATA.getSessionAccuracyWithEntry(inputText);
        int totalSessionWPM        = PLAYER_DATA.getSessionWPM(inputText, startTime, Instant.now());

        sessionWordsTyped.setText(Integer.toString(totalSessionWordsTyped));
        sessionAccuracy.setText(totalSessionAccuracy + "%");
        sessionWPM.setText(Integer.toString(totalSessionWPM));
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
        dictionary = DictionaryLoader.loadDictionaryFromFile(Constants.DEFAULT_DICTIONARY_FILENAME);
    }

    private void resetDisplay() {

        wrongImage.setVisible(false);
        correctImage.setVisible(false);
        doubleCorrectImage.setVisible(false);

        skipButton.setText("Skip");

        displaySessionData();
    }

    private void resetEntryData() {

        isGameRunning = false;
        isEntryValid  = false;

        startTime = null;

        PLAYER_DATA.resetEntryData();
    }

    private void saveData() {

        PLAYER_DATA.saveAllData(textInput.getText(), startTime, Instant.now());

        new PlayerDataWriter().writeData(Constants.USER_DATA_FILE);
    }

}
