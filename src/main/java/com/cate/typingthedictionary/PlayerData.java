package com.cate.typingthedictionary;

public class PlayerData {

    private static final PlayerData INSTANCE = new PlayerData();
    private int globalAccuracy = 100;
    private int globalErrors = 0;
    private int globalTotalWordsTyped = 0;
    private int globalWPM = 0;
    private int sessionAccuracy = 100;
    private int sessionErrors = 0;
    private int sessionTotalWordsTyped = 100;
    private int sessionWPM = 0;
    private String userName = null;

    private PlayerData() {}

    public static PlayerData getInstance() {
        return INSTANCE;
    }

    public int getGlobalAccuracy() {
        return globalAccuracy;
    }

    public void setGlobalAccuracy(int globalAccuracy) {
        this.globalAccuracy = globalAccuracy;
    }

    public int getGlobalErrors() {
        return globalErrors;
    }

    public void setGlobalErrors(int globalErrors) {
        this.globalErrors = globalErrors;
    }

    public int getSessionErrors() {
        return sessionErrors;
    }

    public void setSessionErrors(int sessionErrors) {
        this.sessionErrors = sessionErrors;
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

    public int getSessionAccuracy() {
        return sessionAccuracy;
    }

    public void setSessionAccuracy(int sessionAccuracy) {
        this.sessionAccuracy = sessionAccuracy;
    }

    public int updateSessionAccuracy() {

        return sessionAccuracy = calculateAccuracy(sessionErrors, sessionTotalWordsTyped);

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void addSessionDataToGlobalData() {

        globalTotalWordsTyped += sessionTotalWordsTyped;
        globalWPM += sessionWPM;
        globalErrors += sessionErrors;

        globalAccuracy = calculateAccuracy(globalErrors, globalTotalWordsTyped);

    }

    public int incrementSessionErrorCount() {

        sessionErrors++;

        updateSessionAccuracy();

        return sessionErrors;
    }

    private int calculateAccuracy(int numErrors, int totalWords) {

        if (totalWords == 0) return 0;

        return (int)(100 - (numErrors / (double)totalWords) * 100);

    }

}
