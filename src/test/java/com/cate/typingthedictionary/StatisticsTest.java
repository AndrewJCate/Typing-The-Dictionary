package com.cate.typingthedictionary;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StatisticsTest {

    private final String INPUT = "1 2 3 4 5 6 7 8 9 10";
    private final int WORD_COUNT = INPUT.split("\\s+").length;

    Statistics statistics;

    @BeforeEach
    void setup() {
        statistics = new Statistics();
    }

    @Nested
    class CalculateWPMTests {

        @Test
        void wpm_should_be_1200_when_seconds_are_0() {
            assertThat(statistics.calculateWPM(100, 0, 100)).isEqualTo(1200);
        }

        @Test
        void wpm_should_be_1200_when_seconds_are_negative() {
            assertThat(statistics.calculateWPM(100, -1, 100)).isEqualTo(1200);
        }

        @Test
        void wpm_should_be_100() {
            assertThat(statistics.calculateWPM(500, 60, 100)).isEqualTo(100);
        }

        @Test
        void wpm_should_be_50() {
            assertThat(statistics.calculateWPM(500, 120, 100)).isEqualTo(50);
        }

        @Test
        void wpm_should_be_50_when_accuracy_is_50() {
            assertThat(statistics.calculateWPM(500, 60, 50)).isEqualTo(50);
        }

        @Test
        void wpm_should_be_25() {
            assertThat(statistics.calculateWPM(250, 60, 50)).isEqualTo(25);
        }

        @Test
        void wpm_should_be_0() {
            assertThat(statistics.calculateWPM(500, 60, 0)).isEqualTo(0);
        }

        @Test
        void wpm_should_be_0_when_accuracy_is_negative() {
            assertThat(statistics.calculateWPM(500, 60, -1)).isEqualTo(0);
        }

        @Test
        void wpm_should_be_0_when_keystrokes_are_0() {
            assertThat(statistics.calculateWPM(0, 60, 100)).isEqualTo(0);
        }

        @Test
        void wpm_should_be_0_when_keystrokes_are_negative() {
            assertThat(statistics.calculateWPM(-1, 60, 100)).isEqualTo(0);
        }

        @Test
        void wpm_should_be_100_when_accuracy_is_over_100() {
            assertThat(statistics.calculateWPM(500, 60, 101)).isEqualTo(100);
        }

    }


    @Nested
    class CalculateAccuracyTests {

        @Test
        void accuracy_should_be_100() {
            assertThat(statistics.calculateAccuracy(0, WORD_COUNT)).isEqualTo(100);
        }

        @Test
        void accuracy_should_be_100_with_no_words() {
            assertThat(statistics.calculateAccuracy(0, 0)).isEqualTo(100);
        }

        @Test
        void accuracy_should_be_100_when_word_count_is_negative() {
            assertThat(statistics.calculateAccuracy(0, -1)).isEqualTo(100);
        }

        @Test
        void accuracy_should_be_100_when_error_count_is_negative() {
            assertThat(statistics.calculateAccuracy(-1, WORD_COUNT)).isEqualTo(100);
        }

        @Test
        void accuracy_should_be_90() {
            assertThat(statistics.calculateAccuracy(1, WORD_COUNT)).isEqualTo(90);
        }

        @Test
        void accuracy_should_be_10() {
            assertThat(statistics.calculateAccuracy(9, WORD_COUNT)).isEqualTo(10);
        }

        @Test
        void accuracy_should_be_0() {
            assertThat(statistics.calculateAccuracy(10, WORD_COUNT)).isEqualTo(0);
        }

        @Test
        void accuracy_should_be_0_with_no_words_and_some_errors() {
            assertThat(statistics.calculateAccuracy(1, 0)).isEqualTo(0);
        }

    }

    @Nested
    class WordCountTests {

        @Test
        void count_should_equal_input_word_count() {
            assertThat(statistics.countWords(INPUT)).isEqualTo(WORD_COUNT);
        }

        @Test
        void count_should_be_97() {

            String testString =
                    "The debate over pirates, ninjas, and robots is always an intriguing one. Each has its own unique" +
                            " set of skills and abilities that make them formidable opponents. Pirates are known for " +
                            "their swashbuckling ways on the high seas, while ninjas are stealthy and skilled in " +
                            "martial arts. Robots, on the other hand, possess advanced technology that gives them a " +
                            "mechanical advantage. Choosing a side in this epic battle is no easy task, as each " +
                            "brings something different to the table. Ultimately, the outcome of this clash would " +
                            "depend on various factors and who can outsmart the others.";

            assertThat(statistics.countWords(testString)).isEqualTo(97);
        }

        @Test
        void count_should_be_1() {
            assertThat(statistics.countWords("test")).isEqualTo(1);
        }

        @Test
        void count_should_be_0() {
            assertThat(statistics.countWords("")).isEqualTo(0);
        }
    }

}