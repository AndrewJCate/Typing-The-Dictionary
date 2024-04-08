package com.cate.typingthedictionary;

import java.io.*;
import java.util.stream.Stream;

public class PlayerDataReader {

    private static final PlayerData PLAYER_DATA = PlayerData.getInstance();

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

                if (statisticsArray.length >= 3) {

                    PLAYER_DATA.setGlobalTotalWordsTyped(statisticsArray[0]);
                    PLAYER_DATA.setGlobalWPM(statisticsArray[1]);
                    PLAYER_DATA.setGlobalAccuracy(statisticsArray[2]);
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
