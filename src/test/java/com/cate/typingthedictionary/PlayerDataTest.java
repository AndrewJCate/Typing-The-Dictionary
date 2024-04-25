package com.cate.typingthedictionary;

import org.junit.jupiter.api.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PlayerDataTest {

    static final PlayerData PLAYER_DATA = PlayerData.getInstance();

    @Test
    void getInstance() {
        assertThat(PLAYER_DATA != null).isTrue();
    }

    @Test
    void get_global_elapsed_seconds() {

        final int SECONDS = 42;

        PLAYER_DATA.setGlobalElapsedSeconds(SECONDS);

        assertThat(PLAYER_DATA.getGlobalElapsedSeconds() == SECONDS).isTrue();

        PLAYER_DATA.setGlobalElapsedSeconds(0);
    }

    @Test
    void get_and_set_globalErrors() {

        final int ERRORS = 42;

        PLAYER_DATA.setGlobalErrors(ERRORS);

        assertThat(PLAYER_DATA.getGlobalErrors() == ERRORS).isTrue();

        PLAYER_DATA.setGlobalErrors(0);
    }

    @Test
    void get_and_set_GlobalKeystrokes() {

        final long KEYSTROKES = 42;

        PLAYER_DATA.setGlobalKeystrokes(KEYSTROKES);

        assertThat(PLAYER_DATA.getGlobalKeystrokes() == KEYSTROKES).isTrue();

        PLAYER_DATA.setGlobalKeystrokes(0);
    }

    @Test
    void get_and_set_GlobalWordsTyped() {

        final int WORDS = 42;

        PLAYER_DATA.setGlobalKeystrokes(WORDS);

        assertThat(PLAYER_DATA.getGlobalKeystrokes() == WORDS).isTrue();

        PLAYER_DATA.setGlobalWordsTyped(0);
    }

    @Test
    void increment_and_get_EntryErrorCount() {

        int errorCount = PLAYER_DATA.getEntryErrors();

        PLAYER_DATA.incrementEntryErrorCount();

        assertThat(PLAYER_DATA.getEntryErrors()).isEqualTo(errorCount + 1);

        PLAYER_DATA.resetEntryData();
    }

    @Test
    void get_and_set_UserName() {

        final String NAME = "Tester";

        PLAYER_DATA.setUserName(NAME);

        assertThat(PLAYER_DATA.getUserName().equals(NAME)).isTrue();
    }

    @Test
    void resetEntryData() {

        PLAYER_DATA.incrementEntryErrorCount();

        PLAYER_DATA.resetEntryData();

        assertThat(PLAYER_DATA.getEntryErrors()).isEqualTo(0);
    }

    @Nested
    class SaveDataIntegrationTests {

        final static int ADD_SECONDS = 10;
        final static String INPUT    = "1 2 3 4 5 6 7 8 9 10";

        @BeforeAll
        static void setup() {

            Instant start = Instant.now();

            ZonedDateTime zdt = start.atZone(ZoneId.systemDefault());
            ZonedDateTime adjusted = zdt.plusSeconds(ADD_SECONDS);

            Instant stop = adjusted.toInstant();

            PLAYER_DATA.incrementEntryErrorCount();

            PLAYER_DATA.saveAllData(INPUT, start, stop);
        }

        @Test
        void getSessionElapsedSeconds() {
            assertThat(PLAYER_DATA.getSessionElapsedSeconds()).isEqualTo(ADD_SECONDS);
        }

        @Test
        void getSessionErrors() {
            assertThat(PLAYER_DATA.getSessionErrors()).isEqualTo(1);
        }

        @Test
        void getSessionKeystrokes() {
            assertThat(PLAYER_DATA.getSessionKeystrokes()).isEqualTo(INPUT.length());
        }

        @Test
        void getSessionWordsTyped() {

            int inputWords = INPUT.split("\\s+").length;

            assertThat(PLAYER_DATA.getSessionWordsTyped()).isEqualTo(inputWords);
        }

        @Test
        void getGlobalElapsedSeconds() {
            assertThat(PLAYER_DATA.getGlobalElapsedSeconds()).isEqualTo(ADD_SECONDS);
        }

        @Test
        void getGlobalErrors() {
            assertThat(PLAYER_DATA.getGlobalErrors()).isEqualTo(1);
        }

        @Test
        void getGlobalKeystrokes() {
            assertThat(PLAYER_DATA.getGlobalKeystrokes()).isEqualTo(INPUT.length());
        }

        @Test
        void getGlobalWordsTyped() {

            int inputWords = INPUT.split("\\s+").length;

            assertThat(PLAYER_DATA.getGlobalWordsTyped()).isEqualTo(inputWords);
        }

    }

}