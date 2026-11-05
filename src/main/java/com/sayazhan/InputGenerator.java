package com.sayazhan;

import java.util.Random;

public class InputGenerator {

    private final Random random = new Random(42);

    // Generate a random string using a small alphabet.
    public String randomString(int length) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            char c = (char) ('a' + random.nextInt(4));
            sb.append(c);
        }

        return sb.toString();
    }

    // Generate a string consisting of one repeated character.
    public String repeatedString(int length, char c) {
        return String.valueOf(c).repeat(length);
    }

    // Difficult case for Naive Search:
    // text = aaaaa..., pattern = aaaa...b.
    public String worstPattern(int length) {
        if (length < 1) {
            throw new IllegalArgumentException("Length must be positive");
        }

        return "a".repeat(length - 1) + "b";
    }
}
