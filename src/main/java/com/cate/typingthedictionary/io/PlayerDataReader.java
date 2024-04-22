package com.cate.typingthedictionary.io;

import com.cate.typingthedictionary.PlayerData;

import java.io.*;
import java.util.stream.Stream;

/**
 * Reads player statistics and username data from a file.
 */
public class PlayerDataReader {

    private static final PlayerData PLAYER_DATA = PlayerData.getInstance();

    /**
     * Read player data from a file. The data should be in the following format:
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
     * @return the player data
     */
    public PlayerData readData(String fileName) {

        // Load user data file
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String userName = reader.readLine();

            if (userName != null) {

                PLAYER_DATA.setUserName(userName);

            }

            String statisticsString = reader.readLine();

            if (statisticsString != null) {

                int[] statisticsArray = parseStatistics(statisticsString);

                if (statisticsArray.length >= 4) {

                    PLAYER_DATA.setGlobalElapsedSeconds(statisticsArray[0]);
                    PLAYER_DATA.setGlobalErrors(statisticsArray[1]);
                    PLAYER_DATA.setGlobalKeystrokes(statisticsArray[2]);
                    PLAYER_DATA.setGlobalWordsTyped(statisticsArray[3]);
                }

            }

        } catch (FileNotFoundException e) {

            // Create file if not found
            new File(fileName);

        } catch (IOException e) {

            System.out.println("Unable to access file " + fileName + ".");

            e.printStackTrace();
        }

        return PLAYER_DATA;

    }

    private int[] parseStatistics(String stasticsString) {

        return Stream.of(stasticsString.split("\\s+")).mapToInt(Integer::parseInt).toArray();

    }
}
