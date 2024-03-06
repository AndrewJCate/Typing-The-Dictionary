package com.cate.typingthedictionary.Controllers;

import com.cate.typingthedictionary.Main;
import com.cate.typingthedictionary.constants.Constants;
import javafx.event.ActionEvent;

import java.io.IOException;

public class InstructionsController {

    public void onBackClicked(ActionEvent ae) {

        Main main = new Main();

        try {
            main.changeScene(Constants.GAME_VIEW);
        }
        catch (IOException e) {

            System.out.println("File " + Constants.GAME_VIEW + " not found.");

            e.printStackTrace();
        }

    }
}
