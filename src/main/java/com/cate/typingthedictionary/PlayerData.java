package com.cate.typingthedictionary;

import java.time.Duration;
import java.time.Instant;

/**
 * Player Data contains all statistical data the player generates while using the program and any functions that
 * manipulate that data to generate additional statistics.
 *
 * This class uses the singleton pattern which allows only a single instance to be created.
 *
 * Data collected:
 *  - username
 *  - number of errors
 *  - elapsed time
 *  - number of keystrokes
 *  - number of words typed
 *
 * Collected data is used to calculate:
 *  - number of words typed
 *  - average words per minute (WPM)
 *  - average accuracy
 *
 * There are three layers of data:
 *  - global: data collected across all play sessions
 *  - session: data collected across only the current play session
 *  - entry: data collected for each {@link Dictionary} entry
 *
 * WPM calculation:
 *  Words per minute are calculated using an average word length instead of the actual length of a word and are
 *  corrected for accuracy. Thus, a more quickly typed entry with more errors can have a lower WPM than a more slowly
 *  typed entry with fewer errors.
 *
 * Accuracy calculation:
 *  Accuracy is calculated based on the number of errors and the actual word count, not using the average word length.
 */
public class PlayerData {

    /**
     * The only instance of the PlayerData class.
     */
    private static final PlayerData INSTANCE = new PlayerData();
    /**
     * The length of the average word.
     */
    private final int AVERAGE_WORD_LENGTH = 5;

    /**
     * Global elapsed seconds.
     */
    private long globalElapsedSeconds  = 0;
    /**
     * Global errors.
     */
    private int  globalErrors          = 0;
    /**
     * Global keys pressed.
     */
    private long globalKeystrokes = 0;
    /**
     * Global words typed.
     */
    private int  globalWordsTyped      = 0;

    /**
     * Session elapsed seconds.
     */
    private long sessionElapsedSeconds = 0;
    /**
     * Session errors.
     */
    private int  sessionErrors         = 0;
    /**
     * Session keys pressed.
     */
    private long sessionKeystrokes = 0;
    /**
     * Session words typed.
     */
    private int  sessionWordsTyped     = 0;

    /**
     * {@link Dictionary} entry typing errors.
     */
    private int  entryErrors = 0;

    /**
     * User name.
     */
    private String userName = null;


    /* Constructor */

    /**
     * Instantiates a new PlayerData.
     */
    private PlayerData() {}

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static PlayerData getInstance() {
        return INSTANCE;
    }


    /* Default field getter/setters */

    /**
     * Gets global elapsed seconds.
     *
     * @return the global elapsed seconds
     */
    public long getGlobalElapsedSeconds() {
        return globalElapsedSeconds;
    }

    /**
     * Sets global elapsed seconds.
     *
     * @param globalElapsedSeconds the global elapsed seconds
     */
    public void setGlobalElapsedSeconds(long globalElapsedSeconds) {
        this.globalElapsedSeconds = globalElapsedSeconds;
    }

    /**
     * Gets global errors.
     *
     * @return the global errors
     */
    public int  getGlobalErrors() {
        return globalErrors;
    }

    /**
     * Sets global errors.
     *
     * @param globalErrors the global errors
     */
    public void setGlobalErrors(int globalErrors) {
        this.globalErrors = globalErrors;
    }

    /**
     * Gets global keys pressed.
     *
     * @return the global keys pressed
     */
    public long getGlobalKeystrokes() {
        return globalKeystrokes;
    }

    /**
     * Sets global keys pressed.
     *
     * @param globalKeystrokes the global keys pressed
     */
    public void setGlobalKeystrokes(long globalKeystrokes) {
        this.globalKeystrokes = globalKeystrokes;
    }

    /**
     * Gets global words typed.
     *
     * @return the global words typed
     */
    public int  getGlobalWordsTyped() {
        return globalWordsTyped;
    }

    /**
     * Sets global words typed.
     *
     * @param globalWordsTyped the global words typed
     */
    public void setGlobalWordsTyped(int globalWordsTyped) {
        this.globalWordsTyped = globalWordsTyped;
    }

    /**
     * Gets user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets user name.
     *
     * @param userName the user name
     */
    public void   setUserName(String userName) {
        this.userName = userName;
    }


    /* Public methods */

    /**
     * Increment entry error count.
     */
    public void incrementEntryErrorCount() {
        entryErrors++;
    }

    /**
     * Gets global accuracy via calculation.
     *
     * @return the global accuracy
     */
    public int  getGlobalAccuracy() {
        return calculateAccuracy(globalErrors, globalWordsTyped);
    }

    /**
     * Gets global words per minute via calculation.
     *
     * @return the global words per minute
     */
    public int  getGlobalWPM() {
        return calculateWPM(globalKeystrokes, globalElapsedSeconds, getGlobalAccuracy());
    }

    /**
     * Gets session accuracy that includes the current entry accuracy.
     *
     * @param inputText the input text
     * @return the session accuracy with entry data
     */
    public int  getSessionAccuracyWithEntry(String inputText) {

        int entryWordsTyped        = countTypedWords(inputText);
        int totalSessionWordsTyped = entryWordsTyped + sessionWordsTyped;
        int totalSessionErrors     = entryErrors + sessionErrors;

        return calculateAccuracy(totalSessionErrors, totalSessionWordsTyped);
    }

    /**
     * Gets session words typed that includes the current entry words typed.
     *
     * @param inputText the input text
     * @return the number of session words typed
     */
    public int getSessionWordsTypedWithEntry(String inputText) {

        int wordsTyped = countTypedWords(inputText);

        return wordsTyped + sessionWordsTyped;
    }

    /**
     * Gets session words per minute (WPM).
     *
     * Calculates WPM based on available data. If start/stop times are available, it will return WPM based on session
     * and entry data. If start/stop times are not available, it will return WPM based on session data only.
     *
     * @param inputText the input text
     * @param start     the Instant typing starts
     * @param stop      the Instant the input matches the entire entry text
     * @return the session words per minute, possibly including entry data
     */
    public int  getSessionWPM(String inputText, Instant start, Instant stop) {

        if (start == null || stop == null) {
            return calculateWPM(sessionKeystrokes, sessionElapsedSeconds, getSessionAccuracyWithoutEntry());
        }

        long totalSessionKeysPressed    = inputText.length() + sessionKeystrokes;
        long entryElapsedSeconds        = Duration.between(start, stop).toSeconds();
        long totalSessionElapsedSeconds = entryElapsedSeconds + sessionElapsedSeconds;

        return calculateWPM(totalSessionKeysPressed, totalSessionElapsedSeconds, getSessionAccuracyWithEntry(inputText));
    }

    /**
     * Resets entry error count.
     */
    public void resetEntryData() {
        entryErrors = 0;
    }

    /**
     * Updates all upstream data with current data. Entry data is added to session data and session data is added to
     * global data.
     *
     * @param inputText the input text
     * @param start     the Instant typing starts
     * @param stop      the Instant the input matches the entire entry text
     */
    public void saveAllData(String inputText, Instant start, Instant stop) {
        saveEntryDataToSessionData(inputText, start, stop);
        saveSessionDataToGlobalData();
    }


    /* Private methods */

    /**
     * Calculates accuracy based on the number of errors and words typed.
     *
     * If there are no errors, returns 100 (max) accuracy.
     * If there are no words typed, returns 0 (min) accuracy.
     *
     * @param numErrors  the number of errors made while typing
     * @param totalWords the total words typed
     * @return the calculated accuracy
     */
    private int calculateAccuracy(int numErrors, int totalWords) {

        if (numErrors == 0) return 100;

        if (totalWords == 0) return 0;

        return (int) (100 - (numErrors / (double) totalWords) * 100);
    }

    /**
     * Calculates words per minute (WPM) based on average word length (not actual number of words typed) and accuracy
     * . The less accurate, the lower the resulting WPM will be.
     *
     * @param keystrokes    the number of keys pressed
     * @param elapsedSeconds the elapsed seconds
     * @param accuracy       the accuracy
     * @return the calculated words per minute
     */
    private int calculateWPM(long keystrokes, long elapsedSeconds, int accuracy) {

        long numWords = keystrokes / AVERAGE_WORD_LENGTH;

        if (elapsedSeconds == 0) {
            elapsedSeconds = 1;
        }

        double wpm = numWords / (elapsedSeconds / 60.0); // 60 seconds in one minute.

        return (int)(wpm * (accuracy / 100.0)); // Accuracy out of 100 max accuracy.
    }

    /**
     * Counts typed words. This returns the actual number of words typed, not using the average words typed.
     *
     * @param inputText the input text
     * @return the count of input words
     */
    private int countTypedWords(String inputText) {

        if (inputText.isEmpty()) return 0;

        return inputText.split("\\s+").length;
    }

    /**
     * Gets session accuracy without entry data considered.
     *
     * @return the session accuracy without entry data
     */
    private int getSessionAccuracyWithoutEntry() {
        return calculateAccuracy(sessionErrors, sessionWordsTyped);
    }

    /**
     * Adds entry data to session data.
     *
     * @param inputText the input text
     * @param start     the Instant typing starts
     * @param stop      the Instant the input matches the entire entry text
     */
    private void saveEntryDataToSessionData(String inputText, Instant start, Instant stop) {

        int entryWordsTyped      = countTypedWords(inputText);
        long entryElapsedSeconds = Duration.between(start, stop).toSeconds();

        sessionErrors         += entryErrors;
        sessionElapsedSeconds += entryElapsedSeconds;
        sessionKeystrokes     += inputText.length();
        sessionWordsTyped     += entryWordsTyped;
    }

    /**
     * Adds session data to global data.
     */
    private void saveSessionDataToGlobalData() {

        globalErrors         += sessionErrors;
        globalElapsedSeconds += sessionElapsedSeconds;
        globalKeystrokes     += sessionKeystrokes;
        globalWordsTyped     += sessionWordsTyped;
    }

}
