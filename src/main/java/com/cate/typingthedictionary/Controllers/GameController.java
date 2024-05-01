package com.cate.typingthedictionary.Controllers;

import com.cate.typingthedictionary.*;
import com.cate.typingthedictionary.io.DictionaryLoader;
import com.cate.typingthedictionary.io.PlayerDataWriter;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.*;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.AbstractMap;
import java.util.List;
import java.util.ResourceBundle;

import static com.cate.typingthedictionary.constants.Constants.*;

/**
 * Handles the main logic of the game:
 *  - displays a random entry
 *  - analyzes input text
 *  - displays feedback icons
 *  - displays statistical data
 *
 * Buttons and keyboard shortcuts facilitate movement to other screens.
 */
public class GameController implements Initializable {

    @FXML
    private Button skipButton;

    @FXML
    private ScrollPane entryScrollPane;

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

    @FXML
    private Text sessionWordsTyped;

    @FXML
    private Text sessionWPM;

    @FXML
    private Text sessionAccuracy;


    static final int BACK_SPACE = 8;
    static final int ENTER      = 13;
    static final int ESCAPE     = 27;
    static final int SPACE      = 32;

    private final Font WORD_FONT       = Font.font("System", FontWeight.BOLD,   FontPosture.REGULAR, 20);
    private final Font DEFINITION_FONT = Font.font("System", FontWeight.NORMAL, FontPosture.REGULAR, 16);
    private final Font TEXT_INPUT_FONT = Font.font("Tahoma", FontWeight.NORMAL, FontPosture.REGULAR, 16);

    private final int ENTRY_HEIGHT   = 137;
    private final int LINE_HEIGHT    = 19;
    private final int WORDS_PER_LINE = 11;

    private final PlayerData PLAYER_DATA = PlayerData.getInstance();
    private final Statistics STATISTICS = new Statistics();

    private AbstractMap.SimpleEntry<String, List<String>> currentEntry;
    private Dictionary dictionary;

    /**
     * The number of lines of input text, roughly set.
     */
    private int        linesTyped = 1;

    private boolean    isGameRunning = false;

    /**
     * Has input text correctly matched entire entry text.
     */
    private boolean    isEntryValid  = false;

    /**
     * Was the previous character an Enter? Used for regulating autoscroll.
     */
    private boolean    previousEnter = false;

    private Instant    startTime;
    private Validator  validator;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadDictionary();

        validator = new Validator();

        textInput.setFont(TEXT_INPUT_FONT);

        PLAYER_DATA.resetSessionData();
        PLAYER_DATA.resetEntryData();

        nextWord();
    }

    /**
     * Each time a key is pressed, the following happens:
     *
     *  - If the first key pressed is Escape, the game window goes back to the Summary screen.
     *  - If the first key pressed is Backspace, the current entry is skipping and a new entry is displayed (clearing
     *  any existing entry data without saving it to the session data).
     *  - If the first key pressed is any other than the above, it starts the game timer which is used to calculate
     *  words typed per minute.
     *
     *  Validation:
     *    - The input text is compared against the entry text.
     *    - If the entry text starts with the input text, a check mark icon is displayed.
     *    - If not, an "x" icon is displayed.
     *
     *  Statistics:
     *    - Errors are incremented only after input text is accurate (or the first keystroke). Sequential errors only
     *  count as one error until corrected.
     *    - Statistics are calculated and displays updated.
     *    - These include:
     *      - Number of words typed (actual word count, not average).
     *      - Words typed per minute (uses an average word count and factors in accuracy {@link PlayerData}).
     *      - Accuracy as a percentage of total words typed (actual count, not average).
     *
     *  Triggers autoscroll.
     *
     *  Input matches entry text:
     *    - If the input text matches the entry text, the entry data is added to the session data which is saved to
     *    calculate statistics across multiple play sessions.
     *    - A second check mark is displayed to signal the entry has been completed correctly.
     *    - If Enter is pressed, the next random entry will display and the user can continue typing.
     *
     *  Buttons are available:
     *    - Back: return the user to the Summary screen (keyboard shortcut Escape).
     *    - Skip: displays a new random entry (erasing current entry data) (keyboard shortcut Backspace before typing
     *    anything of the current entry).
     *    - When an entry has been typed successfully, the Skip text changes to Next but the function does not change
     *    (keyboard shortcut Enter).
     *    - ?: displays the user instructions. This is the only button without a keyboard shortcut.
     *
     * @param ke the KeyEvent
     */
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

        boolean isInputValid    = validator.entryStartsWithText(enteredText, currentEntry);
        boolean isEntryComplete = validator.compareInputAndEntry(enteredText, currentEntry);

        if (isInputValid && !isEntryValid) {

            if (ke.getCharacter().charAt(0) == SPACE || ke.getCharacter().charAt(0) == ENTER) {

                displaySessionData();

                autoScroll(ke);
            }
        }

        if (isEntryComplete && !isEntryValid) {

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

    /**
     * Displays the instruction screen.
     */
    public void onInfoClicked(ActionEvent ae) {

        Main main = new Main();

        try {
            main.changeScene(INFO_VIEW);
        }
        catch (IOException e) {

            System.out.println("File " + INFO_VIEW + " not found.");

            e.printStackTrace();
        }
    }

    public void onSkipClicked(ActionEvent ae) {
        nextWord();
    }


    /* Private methods */

    /**
     * Some entries require scrolling (entry text > than ENTRY_HEIGHT). This can be done with the mouse scroll wheel,
     * page up/down keys, or arrow keys but is inconvenient when typing. The autoscroll feature automatically scrolls
     * down an appropriate amount (either a calculated amount or SCROLL_AMOUNT) to expose more of the entry text without
     * requiring the user to stop typing.
     *
     * Autoscroll triggers when the user presses Enter.
     * Autoscroll triggers when a certain number of words (WORDS_PER_LINE) have been typed without Enter being pressed.
     *
     * @param ke the KeyEvent
     */
    private void autoScroll(KeyEvent ke) {

        List<String> definitions = currentEntry.getValue();

        int wordCount = 0;

        for (String def : definitions) {
            wordCount += def.split("\\s+").length;
        }

        int definitionNumber = currentEntry.getValue().size();

        if (wordCount / definitionNumber > WORDS_PER_LINE) {

            int inputWordsLength = textInput.getText().split("\\s+").length;

            // Scroll after typing > WORDS_PER_LINE
            if (inputWordsLength >= WORDS_PER_LINE * linesTyped) {

                double extraHeight = entryText.getHeight() - ENTRY_HEIGHT;

                if (extraHeight > 1) {

                    scroll(extraHeight);

                    linesTyped++;
                }
            }
        }

        // Scroll after sequential ENTERs
        if (ke.getCharacter().charAt(0) == ENTER) {

            previousEnter = !previousEnter;

            double extraHeight = entryText.getHeight() - ENTRY_HEIGHT;

            if (extraHeight > 1) {

                scroll(extraHeight);

                if (previousEnter) {
                    linesTyped++;
                }
            }
        }
    }

    /**
     * Returns the user to the Summary screen.
     */
    private void back() {

        Main main = new Main();

        try {
            main.changeScene(SUMMARY_VIEW);
        }
        catch (IOException e) {

            System.out.println("File " + SUMMARY_VIEW + " not found.");

            e.printStackTrace();
        }
    }

    /**
     * Calculates session accuracy that includes the current entry data.
     *
     * @return the accuracy
     */
    private int calculateSessionAccuracy() {

        int entryWordsTyped        = STATISTICS.countWords(textInput.getText());
        int totalSessionWordsTyped = entryWordsTyped + PLAYER_DATA.getSessionWordsTyped();
        int totalSessionErrors     = PLAYER_DATA.getEntryErrors() + PLAYER_DATA.getSessionErrors();

        return STATISTICS.calculateAccuracy(totalSessionErrors, totalSessionWordsTyped);
    }

    /**
     * Calculates session words per minute (WPM).
     *
     * Calculates WPM based on available data. If start/stop times are available, it will return WPM based on session
     * and entry data. If start/stop times are not available, it will return WPM based on session data only.
     *
     * @return the session words per minute, possibly including entry data
     */
    private int calculateSessionWPM() {

        long sessionElapsedSeconds = PLAYER_DATA.getSessionElapsedSeconds();
        int  sessionErrors     = PLAYER_DATA.getSessionErrors();
        long sessionKeystrokes = PLAYER_DATA.getSessionKeystrokes();
        int  sessionWordsTyped = PLAYER_DATA.getSessionWordsTyped();

        if (startTime == null) {

            int sessionAccuracy = STATISTICS.calculateAccuracy(sessionErrors,sessionWordsTyped);

            return STATISTICS.calculateWPM(sessionKeystrokes, sessionElapsedSeconds, sessionAccuracy);
        }

        long totalSessionKeysPressed    = textInput.getText().length() + sessionKeystrokes;
        long entryElapsedSeconds        = Duration.between(startTime, Instant.now()).toSeconds();
        long totalSessionElapsedSeconds = entryElapsedSeconds + sessionElapsedSeconds;

        return STATISTICS.calculateWPM(totalSessionKeysPressed, totalSessionElapsedSeconds,
                calculateSessionAccuracy());
    }

    /**
     * Removes any text from the text input area.
     */
    private void clearInput() {
        Platform.runLater(() -> textInput.clear());
    }

    /**
     * Displays the next random entry.
     */
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

    /**
     * Display session data which may include entry data.
     */
    private void displaySessionData() {

        String inputText = textInput.getText();

        int totalSessionWordsTyped = STATISTICS.countWords(inputText) + PLAYER_DATA.getSessionWordsTyped();
        int totalSessionAccuracy   = calculateSessionAccuracy();
        int totalSessionWPM        = calculateSessionWPM();

        sessionWordsTyped.setText(Integer.toString(totalSessionWordsTyped));
        sessionAccuracy.setText(totalSessionAccuracy + "%");
        sessionWPM.setText(Integer.toString(totalSessionWPM));
    }

    /**
     * Display status icons.
     *
     * A single check mark for correctly input text.
     * A single "x" mark for an error.
     * A double check mark for a successfully completed entry.
     *
     * @param isInputValid the is input valid
     */
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

    /**
     * Loads the dictionary to use for entries.
     */
    private void loadDictionary() {
        dictionary = DictionaryLoader.loadDictionaryFromFile(DEFAULT_DICTIONARY_FILENAME);
    }

    /**
     * Clears the icons, corrects the "Skip" button name and displays current statistics.
     */
    private void resetDisplay() {

        wrongImage.setVisible(false);
        correctImage.setVisible(false);
        doubleCorrectImage.setVisible(false);

        skipButton.setText("Skip");

        displaySessionData();
    }

    /**
     * Resets the game screen to its starting position with updated statistics and displays a new random entry.
     */
    private void nextWord() {

        clearInput();
        resetEntryData();

        Platform.runLater(() -> {
            resetDisplay();

            currentEntry = dictionary.getRandomEntry();
            displayNextEntry();

            textInput.requestFocus();
        });
    }

    /**
     * Resets entry data. This has the intended side effect of deleting any unsaved entry statistic data. Entry data
     * is saved when the entry has been typed successfully.
     */
    private void resetEntryData() {

        isGameRunning = false;
        isEntryValid  = false;

        previousEnter = false;
        linesTyped = 1;
        entryScrollPane.setVvalue(0);

        startTime = null;

        PLAYER_DATA.resetEntryData();
    }

    /**
     * Adds entry data to session data and session data to global data.
     *
     * Writes the resulting data to a file to be used during future play sessions.
     */
    private void saveData() {

        PLAYER_DATA.saveAllData(textInput.getText(), startTime, Instant.now());

        new PlayerDataWriter().writeData(USER_DATA_FILE);
    }

    /**
     * Scrolls a calculated amount.
     *
     * Scroll amount (vValue) is always from 0 (at top) to 100 (at bottom), regardless of whether the distance to be
     * scrolled is 10 pixels or 10_000.
     *
     * The amount to scroll is calculated based on a percentage of the total distance that needs to be scrolled.
     * ENTRY_HEIGHT is the height of the entry display in pixels. extraHeight is calculated based on the amount of
     * pixels the entry uses beyond that height. This is the amount that is left to be displayed, or the amount that
     * needs to be scrolled. This extraHeight is divided by the estimated height of a single line (LINE_HEIGHT) to
     * get the rough amount that needs to be scrolled each time the scroll triggers.
     *
     * @param extraHeight   the extra height that needs to be scrolled
     */
    private void scroll(double extraHeight) {

        double extraLines = extraHeight / LINE_HEIGHT;

        double scrollAmount = (100 / extraLines) / 100;

        entryScrollPane.setVvalue(entryScrollPane.getVvalue() + scrollAmount);
    }

}
