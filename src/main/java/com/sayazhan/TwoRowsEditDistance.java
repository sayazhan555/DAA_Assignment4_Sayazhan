package com.sayazhan;

public class TwoRowsEditDistance implements EditDistance {

    private long operations;

    // State: prev[j] and curr[j] store distances for two DP rows.
    // Transition: minimum of insert, delete and replace.
    // Base case: distance from an empty string is its length.
    public int distance(String a, String b) {
        operations = 0;

        // Use the shorter string for columns to save memory.
        if (b.length() > a.length()) {
            String temp = a;
            a = b;
            b = temp;
        }

        int n = a.length();
        int m = b.length();

        int[] prev = new int[m + 1];
        int[] curr = new int[m + 1];

        for (int j = 0; j <= m; j++) {
            prev[j] = j;
        }

        for (int i = 1; i <= n; i++) {
            curr[0] = i;

            for (int j = 1; j <= m; j++) {
                operations++;

                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    curr[j] = prev[j - 1];
                } else {
                    int insert = curr[j - 1];
                    int delete = prev[j];
                    int replace = prev[j - 1];

                    curr[j] = 1 + Math.min(insert,
                            Math.min(delete, replace));
                }
            }

            int[] temp = prev;
            prev = curr;
            curr = temp;
        }

        return prev[m];
    }

    public long getOperations() {
        return operations;
    }
}
