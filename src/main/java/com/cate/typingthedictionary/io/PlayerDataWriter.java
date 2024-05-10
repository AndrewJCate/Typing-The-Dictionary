package com.cate.typingthedictionary.io;

import com.cate.typingthedictionary.PlayerData;
import com.cate.typingthedictionary.constants.Constants;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Writes player statistics data to a file.
 */
public class PlayerDataWriter {

    private final PlayerData PLAYER_DATA = PlayerData.getInstance();

    /**
     * Writes player username and statistics data to a file. The text file format is:
     *
     * username
     * ## ## ## ##
     *
     * Order of the statistics data:
     *  - global elapsed seconds
     *  - global errors
     *  - global keystrokes
     *  - global words typed
     *
     * @param fileName the file name
     */
    public void writeData(String fileName) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {

            bw.write(PLAYER_DATA.getUserName());

            bw.newLine();

            bw.write(playerStatisticsToString());

        }
        catch (IOException e) {

            System.out.println("File " + Constants.FULL_FILE_PATH + " could not be created or modified.");

            e.printStackTrace();
        }

    }

    private String playerStatisticsToString() {

        return PLAYER_DATA.getGlobalElapsedSeconds() +
                " " +
                PLAYER_DATA.getGlobalErrors() +
                " " +
                PLAYER_DATA.getGlobalKeystrokes() +
                " " +
                PLAYER_DATA.getGlobalWordsTyped();
    }
}
