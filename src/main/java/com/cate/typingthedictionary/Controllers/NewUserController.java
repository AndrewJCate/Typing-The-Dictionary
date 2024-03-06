package com.cate.typingthedictionary.Controllers;

import com.cate.typingthedictionary.Main;
import com.cate.typingthedictionary.constants.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class NewUserController {

    @FXML
    private TextField username_input;

    @FXML
    public void onEnter(ActionEvent ae) {
        this.submit(ae);
    }

    public void submit(ActionEvent ae) {

        String username = this.username_input.getText();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(Constants.USER_DATA_FILE))) {

            bw.write(username);

        }
        catch (IOException e) {

            System.out.println("File " + Constants.USER_DATA_FILE + " could not be created or modified.");

            e.printStackTrace();
        }
        finally {

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
}
