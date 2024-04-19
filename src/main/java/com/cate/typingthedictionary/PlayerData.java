package com.cate.typingthedictionary;

import java.time.Duration;
import java.time.Instant;

public class PlayerData {

    private static final PlayerData INSTANCE = new PlayerData();
    private static final int AVERAGE_WORD_LENGTH = 5;

    private long globalElapsedSeconds  = 0;
    private int  globalErrors          = 0;
    private long globalKeysPressed     = 0;
    private int  globalWordsTyped      = 0;

    private long sessionElapsedSeconds = 0;
    private int  sessionErrors         = 0;
    private long sessionKeysPressed    = 0;
    private int  sessionWordsTyped     = 0;

    private int  entryErrors = 0;

    private String userName = null;


    /* Constructor */

    private PlayerData() {}

    public static PlayerData getInstance() {
        return INSTANCE;
    }


    /* Default field getter/setters */

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

    public long getGlobalKeysPressed() {
        return globalKeysPressed;
    }

    public void setGlobalKeysPressed(long globalKeysPressed) {
        this.globalKeysPressed = globalKeysPressed;
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

    public void   setUserName(String userName) {
        this.userName = userName;
    }


    /* Public methods */

    public void incrementEntryErrorCount() {
        entryErrors++;
    }

    public int  getGlobalAccuracy() {
        return calculateAccuracy(globalErrors, globalWordsTyped);
    }

    public int  getGlobalWPM() {
        return calculateWPM(globalKeysPressed, globalElapsedSeconds, getGlobalAccuracy());
    }

    public int  getSessionAccuracyWithEntry(String inputText) {

        int entryWordsTyped        = countTypedWords(inputText);
        int totalSessionWordsTyped = entryWordsTyped + sessionWordsTyped;
        int totalSessionErrors     = entryErrors + sessionErrors;

        return calculateAccuracy(totalSessionErrors, totalSessionWordsTyped);
    }

    public int  getSessionWordsTyped(String inputText) {

        int wordsTyped = countTypedWords(inputText);

        return wordsTyped + sessionWordsTyped;
    }

    public int  getSessionWPM(String inputText, Instant start, Instant stop) {

        if (start == null || stop == null) {
            return calculateWPM(sessionKeysPressed, sessionElapsedSeconds, getSessionAccuracyWithoutEntry());
        }

        long totalSessionKeysPressed    = inputText.length() + sessionKeysPressed;
        long entryElapsedSeconds        = Duration.between(start, stop).toSeconds();
        long totalSessionElapsedSeconds = entryElapsedSeconds + sessionElapsedSeconds;

        return calculateWPM(totalSessionKeysPressed, totalSessionElapsedSeconds, getSessionAccuracyWithEntry(inputText));
    }

    public void resetEntryData() {
        entryErrors = 0;
    }

    public void saveAllData(String inputText, Instant start, Instant stop) {
        saveEntryDataToSessionData(inputText, start, stop);
        saveSessionDataToGlobalData();
    }


    /* Private methods */

    private int calculateAccuracy(int numErrors, int totalWords) {

        if (numErrors == 0) return 100;

        if (totalWords == 0) return 0;

        return (int) (100 - (numErrors / (double) totalWords) * 100);

    }

    private int calculateWPM(long keysPressed, long elapsedSeconds, int accuracy) {

        long numWords = keysPressed / AVERAGE_WORD_LENGTH;

        if (elapsedSeconds == 0) {
            elapsedSeconds = 1;
        }

        double wpm = numWords / (elapsedSeconds / 60.0); // 60 seconds in one minute

        return (int)(wpm * (accuracy / 100.0));
    }

    private int countTypedWords(String inputText) {

        if (inputText.isEmpty()) return 0;

        return inputText.split("\\s+").length;
    }

    private int getSessionAccuracyWithoutEntry() {
        return calculateAccuracy(sessionErrors, sessionWordsTyped);
    }

    private void saveEntryDataToSessionData(String inputText, Instant start, Instant stop) {

        int entryWordsTyped      = countTypedWords(inputText);
        long entryElapsedSeconds = Duration.between(start, stop).toSeconds();

        sessionErrors         += entryErrors;
        sessionElapsedSeconds += entryElapsedSeconds;
        sessionKeysPressed    += inputText.length();
        sessionWordsTyped     += entryWordsTyped;
    }

    private void saveSessionDataToGlobalData() {

        globalErrors         += sessionErrors;
        globalElapsedSeconds += sessionElapsedSeconds;
        globalKeysPressed    += sessionKeysPressed;
        globalWordsTyped     += sessionWordsTyped;
    }

}
