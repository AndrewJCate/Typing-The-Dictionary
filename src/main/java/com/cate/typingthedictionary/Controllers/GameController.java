package com.cate.typingthedictionary.Controllers;

import com.cate.typingthedictionary.Main;
import com.cate.typingthedictionary.constants.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.io.IOException;

public class GameController {

    @FXML
    private Button infoButton;

    @FXML
    private Button skipButton;

    @FXML
    private Button backButton;

    @FXML
    private Text sessionWordsTyped;

    @FXML
    private Text sessionWPM;

    @FXML
    private Text sessionAccuracy;

    @FXML
    private Label entryText;

    @FXML
    private TextArea wordsInput;

    @FXML
    private ImageView correctImage;

    @FXML
    private ImageView wrongImage;

    public void onBackClicked(ActionEvent ae) {

        Main main = new Main();

        try {
            main.changeScene(Constants.SUMMARY_VIEW);
        }
        catch (IOException e) {

            System.out.println("File " + Constants.SUMMARY_VIEW + " not found.");

            e.printStackTrace();
        }

    }

    public void onInfoClicked(ActionEvent ae) {

        Main main = new Main();

        try {
            main.changeScene(Constants.INFO_VIEW);
        }
        catch (IOException e) {

            System.out.println("File " + Constants.INFO_VIEW + " not found.");

            e.printStackTrace();
        }

    }

    public void onSkipClicked(ActionEvent ae) {

        // TODO

        System.out.println("Skip button clicked."); // TODO: DELETE

    }

    public void skipWord(KeyEvent ke) {

        // TODO

        System.out.println("Skip word");    // TODO: DELETE
    }

    public void startGame(KeyEvent ke) {

        // TODO

        System.out.println("Start game");   // TODO: DELETE
    }
}
