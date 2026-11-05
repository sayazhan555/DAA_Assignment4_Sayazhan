package com.sayazhan;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class KMPSearchTest {

    @Test
    void testLpsReferenceValues() {
        KMPSearch kmp = new KMPSearch();

        assertArrayEquals(
                new int[]{0, 1, 2, 0, 1, 2, 3, 3},
                kmp.lps("AAACAAAA")
        );

        assertArrayEquals(
                new int[]{0, 0, 1, 2, 0, 1, 2, 3, 4},
                kmp.lps("ABABCABAB")
        );
    }

    @Test
    void testSimplePatterns() {
        KMPSearch kmp = new KMPSearch();

        assertArrayEquals(
                new int[]{0, 0, 0},
                kmp.lps("ABC")
        );

        assertArrayEquals(
                new int[]{0, 1, 2, 3},
                kmp.lps("AAAA")
        );

        assertArrayEquals(
                new int[]{0},
                kmp.lps("A")
        );
    }
}
