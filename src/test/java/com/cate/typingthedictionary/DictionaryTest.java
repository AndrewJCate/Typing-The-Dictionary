package com.cate.typingthedictionary;

import com.cate.typingthedictionary.exceptions.WordAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DictionaryTest {

    private Dictionary dictionary;

    @Nested
    class TestsWithStandardConstructor {

        @BeforeEach
        void setup() {
            dictionary = new Dictionary();
        }

        @Nested
        class AddEntryTests {

            @Nested
            class SingleDefinitionTests {

                boolean result;

                @BeforeEach
                void setup() {
                    result = dictionary.addEntry("test", List.of("testDefinition1"));
                }

                @Test
                void should_return_true_with_a_single_definition() {
                    assertThat(result).isTrue();
                }

                @Test
                void entry_added_to_dictionary_should_contain_correct_key() {
                    assertThat(dictionary.getDictionary().containsKey("test")).isTrue();
                }

                @Test
                void entry_added_to_dictionary_should_contain_correct_definition() {
                    assertThat(dictionary.getDictionary().get("test")).isEqualTo(List.of("testDefinition1"));
                }
            }

            @Nested
            class MultipleDefinitionsTests {

                boolean result;

                @BeforeEach
                void setup() {
                    result = dictionary.addEntry("test", List.of("testDefinition1", "testDefinition2"));
                }

                @Test
                void should_return_true_with_multiple_definitions() {
                    assertThat(result).isTrue();
                }

                @Test
                void entry_added_to_dictionary_should_contain_correct_key() {
                    assertThat(dictionary.getDictionary().containsKey("test")).isTrue();
                }

                @Test
                void entry_added_to_dictionary_should_contain_correct_definition() {
                    assertThat(dictionary.getDictionary().get("test")).isEqualTo(List.of("testDefinition1", "testDefinition2"));
                }
            }

            @Nested
            class WhenWordIsEmpty {

                boolean result;

                @BeforeEach
                void setup() {
                    result = dictionary.addEntry("", List.of("testDefinition1", "testDefinition2"));
                }

                @Test
                void should_return_false_when_word_is_empty() {
                    assertThat(result).isFalse();
                }

                @Test
                void should_not_add_an_entry() {
                    assertThat(dictionary.getDictionary().isEmpty()).isTrue();
                }
            }

            @Nested
            class WhenDefinitionsAreEmpty {

                boolean result;

                @BeforeEach
                void setup() {
                    result = dictionary.addEntry("", List.of());
                }

                @Test
                void should_return_false_when_definitions_are_empty() {
                    assertThat(result).isFalse();
                }

                @Test
                void should_not_add_an_entry() {
                    assertThat(dictionary.getDictionary().isEmpty()).isTrue();
                }
            }

            @Test
            void should_throw_exception_when_duplicate_word_added() {

                dictionary.addEntry("test", List.of("testDefinition1"));

                assertThatThrownBy(() -> dictionary.addEntry("test", List.of("differentTestDefinition"))).isInstanceOf(WordAlreadyExistsException.class);
            }

        }

        @Nested
        class GetRandomEntryTests {

            @Test
            void all_entries_are_returned_given_enough_attempts() {

                // Add 10 entries to the dictionary
                for (int i = 0; i < 10; i++) {
                    dictionary.addEntry(i + " word", List.of(i + " definition1", i + " definition2"));
                }

                Map<String, Integer> tally = new HashMap<>();

                int j = 0;

                // Get random entries and add them to tally map
                while (j < 1000) {

                    AbstractMap.SimpleEntry<String, List<String>> entry = dictionary.getRandomEntry();

                    tally.merge(entry.getKey(), 1, Integer::sum);

                    j++;
                }

                List<Integer> values = new ArrayList<>(tally.values());

                Collections.sort(values);

                assertThat(values.get(0) > 0).isTrue();
            }

            @Test
            void returns_null_if_empty_dictionary() {

                AbstractMap.SimpleEntry<String, List<String>> entry = dictionary.getRandomEntry();

                assertThat(entry).isNull();
            }

        }

        @Nested
        class RemoveEntryTests {

            AbstractMap.SimpleEntry<String, List<String>> testEntry = new AbstractMap.SimpleEntry<>("test", List.of(
                    "test definition"));

            @BeforeEach
            void setup() {
                dictionary.addEntry(testEntry.getKey(), testEntry.getValue());
            }

            @Test
            void returns_removed_entry() {
                assertThat(dictionary.removeEntry(testEntry.getKey())).isEqualTo(testEntry);
            }

            @Test
            void returns_null_if_key_is_empty() {
                assertThat(dictionary.removeEntry("")).isNull();
            }

            @Test
            void returns_null_if_dictionary_is_empty() {

                dictionary = new Dictionary();

                assertThat(dictionary.removeEntry("test")).isNull();
            }
        }
    }

    @Nested
    class TestsWithAlternativeConstructor {

        Map<String, List<String>> mapDictionary = new HashMap<>();

        @BeforeEach
        void setup() {

            // Add 10 entries to the dictionary
            for (int i = 0; i < 10; i++) {
                mapDictionary.put(i + " word", List.of(i + " definition1", i + " definition2"));
            }

            dictionary = new Dictionary(mapDictionary);
        }

        @Test
        void dictionary_contains_correct_entries() {

            boolean missingEntry = false;

            for (String key : mapDictionary.keySet()) {

                if (!dictionary.getDictionary().containsKey(key)) {
                    missingEntry = true;
                    break;
                }
            }

            assertThat(missingEntry).isFalse();
        }
    }

}

