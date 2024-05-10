package com.cate.typingthedictionary.Controllers;

import com.cate.typingthedictionary.Main;
import com.cate.typingthedictionary.PlayerData;
import com.cate.typingthedictionary.Statistics;
import com.cate.typingthedictionary.io.PlayerDataReader;
import com.cate.typingthedictionary.io.PlayerDataWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.cate.typingthedictionary.constants.Constants.*;

/**
 * The Summary screen is the home screen of the application. Here, global user statistics are displayed and a button
 * to begin the typing game.
 */
public class SummaryController implements Initializable {

    private final PlayerData PLAYER_DATA = PlayerData.getInstance();
    private final Statistics STATISTICS = new Statistics();

    @FXML
    private Text totalTypedWords;

    @FXML
    private Text avgWPM;

    @FXML
    private Text accuracy;

    @FXML
    private Label username;


    /**
     * Reads a data file. If data is present in the file, the userName is displayed.
     *
     * @param url            the url
     * @param resourceBundle the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        createWorkingDirectory();

        new PlayerDataReader().readData(FULL_FILE_PATH);

        String userName = PLAYER_DATA.getUserName();

        if (userName == null) {
            username.setText("Statistics:");
        }
        else {
            username.setText("Statistics for " + userName + ":");
        }

        setGlobalStatisticsDisplay();
    }


    /**
     * If the user data file does not exist, changes to the New User screen to get the username from the user. If the
     * file does exist, changes to the game screen.
     *
     * @param event the event
     */
    public void onBeginTypingClicked(ActionEvent event) {

        Main main = new Main();

        File file = new File(FULL_FILE_PATH);

        if (file.length() == 0) {

            try {
                main.changeScene(NEW_USER_VIEW);
            }
            catch (IOException e) {

                System.out.println("File " + NEW_USER_VIEW + " not found.");

                e.printStackTrace();
            }
        }
        else {

            try {
                main.changeScene(GAME_VIEW);
            }
            catch (IOException e) {

                System.out.println("File " + GAME_VIEW + " not found.");

                e.printStackTrace();
            }
        }
    }

    /**
     * Resets global user data and saves changes to the file.
     *
     * @param ae the ae
     */
    public void onResetClicked(ActionEvent ae) {

        // Set PLAYER_DATA values to 0
        PLAYER_DATA.setGlobalElapsedSeconds(0);
        PLAYER_DATA.setGlobalErrors(0);
        PLAYER_DATA.setGlobalKeystrokes(0);
        PLAYER_DATA.setGlobalWordsTyped(0);

        // Save new values to file
        new PlayerDataWriter().writeData(FULL_FILE_PATH);

        // Set display values
        setGlobalStatisticsDisplay();
    }

    private void createWorkingDirectory() {

        File gameDirectory = new File(DATA_FILE_PATH);

        if (!gameDirectory.exists()) {
            gameDirectory.mkdirs();
        }
    }


    /**
     * Sets global statistics display.
     */
    private void setGlobalStatisticsDisplay() {

        int acc = STATISTICS.calculateAccuracy(PLAYER_DATA.getGlobalErrors(), PLAYER_DATA.getGlobalWordsTyped());
        int wpm = STATISTICS.calculateWPM(PLAYER_DATA.getGlobalKeystrokes(), PLAYER_DATA.getGlobalElapsedSeconds(),
                acc);

        totalTypedWords.setText(Integer.toString(PLAYER_DATA.getGlobalWordsTyped()));
        accuracy.setText(acc + "%");
        avgWPM.setText(Integer.toString(wpm));
    }
}