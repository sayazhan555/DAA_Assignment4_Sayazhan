package com.sayazhan;

public class RecursiveEditDistance implements EditDistance {

    private long calls;

    public int distance(String a, String b) {
        calls = 0;
        return solve(a, b, a.length(), b.length());
    }

    // State: solve(i, j) is the edit distance between
    // the first i characters of a and first j characters of b.
    // Transition: take the minimum of insert, delete and replace.
    // Base case: if one string is empty, return the length of the other.
    private int solve(String a, String b, int i, int j) {
        calls++;

        if (i == 0) {
            return j;
        }

        if (j == 0) {
            return i;
        }

        if (a.charAt(i - 1) == b.charAt(j - 1)) {
            return solve(a, b, i - 1, j - 1);
        }

        int insert = solve(a, b, i, j - 1);
        int delete = solve(a, b, i - 1, j);
        int replace = solve(a, b, i - 1, j - 1);

        return 1 + Math.min(insert, Math.min(delete, replace));
    }

    public long getCalls() {
        return calls;
    }
}
