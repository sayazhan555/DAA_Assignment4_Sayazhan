package com.sayazhan;

import java.util.ArrayList;
import java.util.List;

public class KMPSearch {

    private long comparisons;

    // LPS stores the length of the longest proper prefix
    // which is also a suffix for each position.
    public int[] lps(String pattern) {
        int[] result = new int[pattern.length()];
        int len = 0;
        int i = 1;

        while (i < pattern.length()) {
            comparisons++;

            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                result[i] = len;
                i++;
            } else if (len != 0) {
                len = result[len - 1];
            } else {
                result[i] = 0;
                i++;
            }
        }

        return result;
    }

    public List<Integer> search(String text, String pattern) {
        if (pattern.isEmpty()) {
            throw new IllegalArgumentException("Pattern cannot be empty");
        }

        comparisons = 0;
        List<Integer> matches = new ArrayList<>();

        int n = text.length();
        int m = pattern.length();

        if (m > n) {
            return matches;
        }

        int[] prefix = lps(pattern);

        int i = 0;
        int j = 0;

        while (i < n) {
            comparisons++;

            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;

                if (j == m) {
                    matches.add(i - j);
                    j = prefix[j - 1];
                }
            } else if (j != 0) {
                j = prefix[j - 1];
            } else {
                i++;
            }
        }

        return matches;
    }

    public long getComparisons() {
        return comparisons;
    }
}
