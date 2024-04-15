package com.cate.typingthedictionary.Controllers;

import com.cate.typingthedictionary.Main;
import javafx.event.ActionEvent;

import java.io.IOException;

import static com.cate.typingthedictionary.constants.Constants.GAME_VIEW;

public class InstructionsController {

    public void onBackClicked(ActionEvent ae) {

        Main main = new Main();

        try {
            main.changeScene(GAME_VIEW);
        }
        catch (IOException e) {

            System.out.println("File " + GAME_VIEW + " not found.");

            e.printStackTrace();
        }

    }
}
