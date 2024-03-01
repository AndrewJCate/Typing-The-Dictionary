package com.cate.typingthedictionary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private final String USER_DATA_FILE = "src/user_data.txt";

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

            username.setText("Statistics for " + data);

            System.out.println(data);
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
                main.changeScene("popup.fxml");
            }
            catch (IOException e) {

                System.out.println("File \"popup.fxml\" not found.");

                e.printStackTrace();
            }
        }
        else {

            try {
                main.changeScene("game.fxml");
            }
            catch (IOException e) {

                System.out.println("File \"game.fxml\" not found.");

                e.printStackTrace();
            }

        }
    }
}