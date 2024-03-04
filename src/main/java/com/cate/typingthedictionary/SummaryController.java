package com.cate.typingthedictionary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class SummaryController implements Initializable {

    private final String USER_DATA_FILE = "src/data/user_data.txt";
    private final String NEW_USER_VIEW = "new_user.fxml";
    private final String GAME_VIEW = "game.fxml";

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

        // Load user data file
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {

            String data = reader.readLine();

            if (data == null) {
                username.setText("Statistics for user:");
            }
            else {
                username.setText("Statistics for " + data + ":");
            }

            // TODO: Display statistics data

        }
        catch (FileNotFoundException e) {

            System.out.println("File " + USER_DATA_FILE + " not found.");

            e.printStackTrace();
        }
        catch (IOException e) {

            System.out.println("Unable to access file " + USER_DATA_FILE + ".");

            e.printStackTrace();
        }

    }

    public void beginTyping(ActionEvent event) {

        Main main = new Main();

        File file = new File(USER_DATA_FILE);

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
}