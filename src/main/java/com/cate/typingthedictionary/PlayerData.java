package com.cate.typingthedictionary;

public class PlayerData {

    private static final PlayerData INSTANCE = new PlayerData();

    private int globalTotalWordsTyped = 0;
    private int globalWPM = 0;
    private int globalAccuracy = 0;

    private int sessionTotalWordsTyped = 0;
    private int sessionWPM = 0;
    private int sessionAccuracy = 0;

    private String userName = null;

    private PlayerData() {}

    public static PlayerData getInstance() {
        return INSTANCE;
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

    public int getGlobalAccuracy() {
        return globalAccuracy;
    }

    public void setGlobalAccuracy(int globalAccuracy) {
        this.globalAccuracy = globalAccuracy;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
