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


    private long globalElapsedSeconds  = 0;

    private int  globalErrors          = 0;

    private long globalKeystrokes      = 0;

    private int  globalWordsTyped      = 0;


    private long sessionElapsedSeconds = 0;

    private int  sessionErrors         = 0;

    private long sessionKeystrokes     = 0;

    private int  sessionWordsTyped     = 0;

    /**
     * {@link Dictionary} entry typing errors.
     */
    private int  entryErrors = 0;

    private String userName = null;


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


    public long getGlobalElapsedSeconds() {
        return globalElapsedSeconds;
    }

    public void setGlobalElapsedSeconds(long globalElapsedSeconds) {
        this.globalElapsedSeconds = globalElapsedSeconds;
    }

    public int  getGlobalErrors() {
        return globalErrors;
    }

    public void setGlobalErrors(int globalErrors) {
        this.globalErrors = globalErrors;
    }

    public long getGlobalKeystrokes() {
        return globalKeystrokes;
    }

    public void setGlobalKeystrokes(long globalKeystrokes) {
        this.globalKeystrokes = globalKeystrokes;
    }

    public int  getGlobalWordsTyped() {
        return globalWordsTyped;
    }

    public void setGlobalWordsTyped(int globalWordsTyped) {
        this.globalWordsTyped = globalWordsTyped;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getSessionElapsedSeconds() {
        return sessionElapsedSeconds;
    }

    public int  getSessionErrors() {
        return sessionErrors;
    }

    public long getSessionKeystrokes() {
        return sessionKeystrokes;
    }

    public int  getSessionWordsTyped() {
        return sessionWordsTyped;
    }

    public int  getEntryErrors() {
        return entryErrors;
    }

    public void incrementEntryErrorCount() {
        entryErrors++;
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


    /**
     * Adds entry data to session data.
     *
     * @param inputText the input text
     * @param start     the Instant typing starts
     * @param stop      the Instant the input matches the entire entry text
     */
    private void saveEntryDataToSessionData(String inputText, Instant start, Instant stop) {

        long entryElapsedSeconds = Duration.between(start, stop).toSeconds();
        int  entryWordsTyped     = new Statistics().countWords(inputText);

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
