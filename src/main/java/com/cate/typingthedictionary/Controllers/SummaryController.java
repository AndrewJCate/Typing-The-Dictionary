package com.cate.typingthedictionary.Controllers;

import com.cate.typingthedictionary.Main;
import com.cate.typingthedictionary.PlayerData;
import com.cate.typingthedictionary.PlayerDataReader;
import com.cate.typingthedictionary.PlayerDataWriter;
import com.cate.typingthedictionary.constants.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class SummaryController implements Initializable {

    private final PlayerData PLAYER_DATA = PlayerData.getInstance();

    @FXML
    private Text totalTypedWords;

    @FXML
    private Text avgWPM;

    @FXML
    private Text accuracy;

    @FXML
    private Label username;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        new PlayerDataReader().readData(Constants.USER_DATA_FILE);

        String userName = PLAYER_DATA.getUserName();

        if (userName == null) {
            username.setText("Statistics:");
        }
        else {
            username.setText("Statistics for " + userName + ":");
        }

        setGlobalStatisticsDisplay();
    }


    public void onBeginTypingClicked(ActionEvent event) {

        Main main = new Main();

        File file = new File(Constants.USER_DATA_FILE);

        if (file.length() == 0) {

            try {
                main.changeScene(Constants.NEW_USER_VIEW);
            }
            catch (IOException e) {

                System.out.println("File " + Constants.NEW_USER_VIEW + " not found.");

                e.printStackTrace();
            }
        }
        else {

            try {
                main.changeScene(Constants.GAME_VIEW);
            }
            catch (IOException e) {

                System.out.println("File " + Constants.GAME_VIEW + " not found.");

                e.printStackTrace();
            }
        }
    }

    public void onResetClicked(ActionEvent ae) {

        // Set PLAYER_DATA values to 0
        PLAYER_DATA.setGlobalElapsedSeconds(0);
        PLAYER_DATA.setGlobalErrors(0);
        PLAYER_DATA.setGlobalKeysPressed(0);
        PLAYER_DATA.setGlobalWordsTyped(0);

        // Save new values to file
        new PlayerDataWriter().writeData(Constants.USER_DATA_FILE);

        // Set display values
        setGlobalStatisticsDisplay();
    }


    private void setGlobalStatisticsDisplay() {

        totalTypedWords.setText(Integer.toString(PLAYER_DATA.getGlobalWordsTyped()));
        accuracy.setText(PLAYER_DATA.getGlobalAccuracy() + "%");
        avgWPM.setText(Integer.toString(PLAYER_DATA.getGlobalWPM()));
    }
}