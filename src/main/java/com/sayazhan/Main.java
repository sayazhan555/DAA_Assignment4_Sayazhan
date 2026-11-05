package com.sayazhan;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        NaiveSearch naive = new NaiveSearch();
        KMPSearch kmp = new KMPSearch();

        String text = "ABABDABACDABABCABAB";
        String pattern = "ABABCABAB";

        System.out.println("Naive: " + naive.search(text, pattern));
        System.out.println("KMP: " + kmp.search(text, pattern));

        System.out.println("Naive comparisons: " + naive.getComparisons());
        System.out.println("KMP comparisons: " + kmp.getComparisons());

        System.out.println("LPS 1: "
                + Arrays.toString(kmp.lps("AAACAAAA")));

        System.out.println("LPS 2: "
                + Arrays.toString(kmp.lps("ABABCABAB")));

        System.out.println("Overlapping: "
                + kmp.search("AAAAA", "AA"));
    }
}
