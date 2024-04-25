package com.cate.typingthedictionary;

/**
 * Statistics is a helper class designed to calculate the following:
 *  - accuracy as a percentage of errors over total typed words
 *  - words typed per minute (WPM)
 *  - word count
 */
public class Statistics {

    /**
     * Calculates accuracy percentage based on the number of errors and words typed.
     *
     * If there are no errors, returns 100 (max) accuracy.
     * If there are no words typed, returns 0 (min) accuracy.
     *
     * @param numErrors  the number of errors made while typing
     * @param totalWords the total words typed
     * @return the calculated accuracy percentage
     */
    public int calculateAccuracy(int numErrors, int totalWords) {

        // Error correction
        if (numErrors <= 0) return 100;
        if (totalWords <= 0) return 0;

        return (int) (100 - (numErrors / (double) totalWords) * 100);
    }

    /**
     * Calculates words per minute (WPM) based on average word length (not actual number of words typed) and accuracy
     * . The less accurate, the lower the resulting WPM will be.
     *
     * @param keystrokes     the number of keys pressed
     * @param elapsedSeconds the elapsed seconds
     * @param accuracy       the accuracy
     * @return the calculated words per minute
     */
    public int calculateWPM(long keystrokes, long elapsedSeconds, int accuracy) {

        final int AVERAGE_WORD_LENGTH = 5;

        // Error correction
        if (keystrokes < 0) keystrokes = 0;
        if (elapsedSeconds < 0) elapsedSeconds = 0;
        if (accuracy > 100) accuracy = 100;
        if (accuracy < 0)   accuracy = 0;

        long numWords = keystrokes / AVERAGE_WORD_LENGTH;

        if (elapsedSeconds == 0) {
            elapsedSeconds = 1;
        }

        double wpm = numWords / (elapsedSeconds / 60.0); // 60 seconds in one minute.

        return (int)(wpm * (accuracy / 100.0)); // Accuracy out of 100 max accuracy.
    }

    /**
     * Counts typed words. This returns the actual number of words typed, not using the average words typed.
     *
     * @param inputText the input text
     * @return the count of input words
     */
    public int countWords(String inputText) {

        if (inputText.isEmpty()) return 0;

        return inputText.split("\\s+").length;
    }

}
