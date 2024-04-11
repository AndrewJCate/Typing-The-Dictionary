package com.cate.typingthedictionary;

public class PlayerData {

    private static final PlayerData INSTANCE = new PlayerData();


    private int globalErrors = 0;
    private int globalTotalWordsTyped = 0;
    private int globalWPM = 0;

    private int sessionErrors = 0;
    private int sessionTotalWordsTyped = 0;
    private int sessionWPM = 0;

    private int entryErrors = 0;
    private int entryWordsTyped = 0;

    private String userName = null;

    private PlayerData() {
    }

    public static PlayerData getInstance() {
        return INSTANCE;
    }

    public int getGlobalErrors() {
        return globalErrors;
    }

    public void setGlobalErrors(int globalErrors) {
        this.globalErrors = globalErrors;
    }

    public int getGlobalTotalWordsTyped() {
        return globalTotalWordsTyped;
    }

    public void setGlobalTotalWordsTyped(int globalTotalWordsTyped) {
        this.globalTotalWordsTyped = globalTotalWordsTyped;
    }

    public int getGlobalWPM() {
        return globalWPM;
    }

    public void setGlobalWPM(int globalWPM) {
        this.globalWPM = globalWPM;
    }

    public int getSessionErrors() {
        return sessionErrors;
    }

    public void setSessionErrors(int sessionErrors) {
        this.sessionErrors = sessionErrors;
    }

    public int getSessionTotalWordsTyped() {
        return sessionTotalWordsTyped;
    }

    public void setSessionTotalWordsTyped(int sessionTotalWordsTyped) {
        this.sessionTotalWordsTyped = sessionTotalWordsTyped;
    }

    public int getSessionWPM() {
        return sessionWPM;
    }

    public void setSessionWPM(int sessionWPM) {
        this.sessionWPM = sessionWPM;
    }

    public int getEntryErrors() {
        return entryErrors;
    }

    public void setEntryErrors(int entryErrors) {
        this.entryErrors = entryErrors;
    }

    public int getEntryWordsTyped() {
        return entryWordsTyped;
    }

    public void setEntryWordsTyped(int entryWordsTyped) {
        this.entryWordsTyped = entryWordsTyped;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void saveEntryData() {
        sessionTotalWordsTyped += entryWordsTyped;
        sessionErrors += entryErrors;
    }

    public void resetEntryData() {
        entryErrors = 0;
        entryWordsTyped = 0;
    }

    public int calculateSessionAccuracy() {
        return calculateAccuracy(sessionErrors + entryErrors, sessionTotalWordsTyped + entryWordsTyped);
    }

    public void saveSessionData() {
        globalTotalWordsTyped += sessionTotalWordsTyped;
        globalErrors += sessionErrors;
    }

    public void incrementEntryErrorCount() {
        entryErrors++;
    }

    private int calculateAccuracy(int numErrors, int totalWords) {

        if (totalWords == 0) return 0;

        return (int) (100 - (numErrors / (double) totalWords) * 100);

    }

}
