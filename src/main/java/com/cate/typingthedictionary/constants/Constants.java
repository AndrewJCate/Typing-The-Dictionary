package com.cate.typingthedictionary.constants;

import java.io.File;

public class Constants {

    public static final String TITLE = "Type the Dictionary";

    public static final String DATA_FILE_PATH = System.getProperty("user.home") + File.separator +
            ".typingthedicitonary";
    public static final String DEFAULT_DICTIONARY_FILE = "/dictionaries" +
            "/default_dictionary" + ".json";
    public static final String FULL_FILE_PATH = DATA_FILE_PATH + File.separator + "user_data.txt";
    public static final String ICON_FILE = "/images/icon.png";
    public static final String STYLES_FILE = "css/styles.css";

    public static final String GAME_VIEW = "game.fxml";
    public static final String INFO_VIEW = "instructions.fxml";
    public static final String NEW_USER_VIEW = "new_user.fxml";
    public static final String SUMMARY_VIEW = "summary.fxml";
}
