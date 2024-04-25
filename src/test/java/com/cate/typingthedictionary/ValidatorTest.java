package com.cate.typingthedictionary;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ValidatorTest {

    Validator validator;
    AbstractMap.SimpleEntry<String, List<String>> entry;

    @BeforeEach
    void setup() {
        validator = new Validator();
        entry = new AbstractMap.SimpleEntry<>("key", List.of("value1", "value2"));
    }

    @Nested
    class compareInputAndEntryTests {

        @Test
        void input_string_should_match_entry() {

            String testString = "key\n\nvalue1\n\nvalue2";

            assertThat(validator.compareInputAndEntry(testString, entry)).isTrue();
        }

        @Test
        void input_string_should_NOT_match_entry_with_unequal_keys() {

            String testString = "differentKey\n\nvalue1\n\nvalue2";

            assertThat(validator.compareInputAndEntry(testString, entry)).isFalse();
        }

        @Test
        void input_string_should_NOT_match_entry_with_unequal_values() {

            String testString = "key\n\nvalue1\n\ndifferentValue2";

            assertThat(validator.compareInputAndEntry(testString, entry)).isFalse();
        }

        @Test
        void should_return_false_with_empty_input() {

            String testString = "";

            assertThat(validator.compareInputAndEntry(testString, entry)).isFalse();
        }

        @Test
        void should_return_false_with_null_entry() {

            String testString = "key\n\nvalue1\n\nvalue2";

            assertThat(validator.compareInputAndEntry(testString, null)).isFalse();
        }
    }


    @Nested
    class entryStartsWithTextTests {

        @Test
        void should_return_true_when_input_and_entry_match() {

            String testString = "key\n\nvalue1\n\nvalue2";

            assertThat(validator.entryStartsWithText(testString, entry)).isTrue();
        }

        @Test
        void should_return_true_when_input_matches_beginning_of_entry() {

            String testString = "key";

            assertThat(validator.entryStartsWithText(testString, entry)).isTrue();
        }

        @Test
        void should_return_false_when_input_NOT_match_beginning_of_entry() {

            String testString = "key!!";

            assertThat(validator.entryStartsWithText(testString, entry)).isFalse();
        }

        @Test
        void should_return_false_with_empty_input() {

            String testString = "";

            assertThat(validator.compareInputAndEntry(testString, entry)).isFalse();
        }

        @Test
        void should_return_false_with_null_entry() {

            String testString = "key\n\nvalue1\n\nvalue2";

            assertThat(validator.compareInputAndEntry(testString, null)).isFalse();
        }

    }


}