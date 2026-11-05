package com.sayazhan;

import org.junit.jupiter.api.Test;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class EditDistanceTest {

    private final RecursiveEditDistance recursive = new RecursiveEditDistance();
    private final TableEditDistance table = new TableEditDistance();
    private final TwoRowsEditDistance twoRows = new TwoRowsEditDistance();

    private void checkAll(String a, String b, int expected) {
        assertEquals(expected, recursive.distance(a, b));
        assertEquals(expected, table.distance(a, b));
        assertEquals(expected, twoRows.distance(a, b));
    }

    @Test
    void referenceValues() {
        checkAll("kitten", "sitting", 3);
        checkAll("flaw", "lawn", 2);
        checkAll("intention", "execution", 5);
        checkAll("", "abc", 3);
        checkAll("abc", "", 3);
        checkAll("abc", "abc", 0);
        checkAll("", "", 0);
    }

    @Test
    void randomStrings() {
        Random random = new Random(42);

        for (int t = 0; t < 100; t++) {
            String a = randomString(random, random.nextInt(5));
            String b = randomString(random, random.nextInt(5));

            int expected = table.distance(a, b);

            assertEquals(expected, recursive.distance(a, b));
            assertEquals(expected, twoRows.distance(a, b));
        }
    }

    private String randomString(Random random, int length) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < length; i++) {
            result.append((char) ('a' + random.nextInt(3)));
        }

        return result.toString();
    }
}

