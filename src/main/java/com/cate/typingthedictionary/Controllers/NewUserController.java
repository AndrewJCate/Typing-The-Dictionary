package com.cate.typingthedictionary.Controllers;

import com.cate.typingthedictionary.Main;
import com.cate.typingthedictionary.PlayerData;
import com.cate.typingthedictionary.PlayerDataWriter;
import com.cate.typingthedictionary.constants.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class NewUserController {

    private final PlayerData PLAYER_DATA = PlayerData.getInstance();

    @FXML
    private TextField username_input;

    @FXML
    public void onEnter(ActionEvent ae) {
        this.submit(ae);
    }

    public void submit(ActionEvent ae) {

        String username = this.username_input.getText();

        PLAYER_DATA.setUserName(username);

        PlayerDataWriter dataWriter = new PlayerDataWriter();
        dataWriter.writeData(Constants.USER_DATA_FILE);

        Main main = new Main();

        try {
            main.changeScene(Constants.SUMMARY_VIEW);
        }
        catch (IOException e) {

            System.out.println("File " + Constants.SUMMARY_VIEW + " not found.");

            e.printStackTrace();
        }

    }
}
