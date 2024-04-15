package com.cate.typingthedictionary;

import com.cate.typingthedictionary.constants.Constants;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PlayerDataWriter {

    private final PlayerData PLAYER_DATA = PlayerData.getInstance();

    public void writeData(String fileName) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {

            bw.write(PLAYER_DATA.getUserName());

            bw.newLine();

            bw.write(playerStatisticsToString());

        }
        catch (IOException e) {

            System.out.println("File " + Constants.USER_DATA_FILE + " could not be created or modified.");

            e.printStackTrace();
        }

    }

    private String playerStatisticsToString() {

        return PLAYER_DATA.getGlobalElapsedSeconds() +
                " " +
                PLAYER_DATA.getGlobalErrors() +
                " " +
                PLAYER_DATA.getGlobalKeysPressed() +
                " " +
                PLAYER_DATA.getGlobalWordsTyped();

    }
}
