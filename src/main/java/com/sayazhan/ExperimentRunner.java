
package com.sayazhan;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

public class ExperimentRunner {

    private static final int RUNS = 5;
    private static final int WARMUP = 10;
    private static final InputGenerator generator = new InputGenerator();

    public static void main(String[] args) throws IOException {
        Files.createDirectories(Path.of("results"));

        StringBuilder csv = new StringBuilder();
        csv.append("algorithm,input,n,run,time_ms,operations\n");

        runEditDistance(csv);
        runStringSearch(csv);

        Files.writeString(Path.of("results/results.csv"), csv.toString());

        System.out.println("Results saved to results/results.csv");
        System.out.println("All experiments completed.");
    }

    private static void runEditDistance(StringBuilder csv) {
        int[] recursiveSizes = {2, 4, 6, 8};
        int[] dpSizes = {100, 200, 400, 800, 1600};

        for (int n : recursiveSizes) {
            for (String input : new String[]{"random", "different"}) {
                String a = input.equals("random")
                        ? generator.randomString(n)
                        : generator.repeatedString(n, 'a');

                String b = input.equals("random")
                        ? generator.randomString(n)
                        : generator.repeatedString(n, 'b');

                RecursiveEditDistance recursive = new RecursiveEditDistance();

                for (int i = 0; i < WARMUP; i++) {
                    recursive.distance(a, b);
                }

                for (int run = 1; run <= RUNS; run++) {
                    double ms = measure(() -> recursive.distance(a, b));
                    addRow(csv, "Recursive", input, n, run,
                            ms, recursive.getCalls());
                }
            }
        }

        for (int n : dpSizes) {
            for (String input : new String[]{"random", "different"}) {
                String a = input.equals("random")
                        ? generator.randomString(n)
                        : generator.repeatedString(n, 'a');

                String b = input.equals("random")
                        ? generator.randomString(n)
                        : generator.repeatedString(n, 'b');

                TableEditDistance table = new TableEditDistance();
                TwoRowsEditDistance twoRows = new TwoRowsEditDistance();

                int tableAnswer = table.distance(a, b);
                int twoRowsAnswer = twoRows.distance(a, b);

                if (tableAnswer != twoRowsAnswer) {
                    throw new IllegalStateException(
                            "Edit Distance results do not match"
                    );
                }

                for (int i = 0; i < WARMUP; i++) {
                    table.distance(a, b);
                    twoRows.distance(a, b);
                }

                for (int run = 1; run <= RUNS; run++) {
                    double tableMs = measure(() -> table.distance(a, b));

                    addRow(csv, "Table", input, n, run,
                            tableMs, table.getOperations());

                    double twoRowsMs = measure(() -> twoRows.distance(a, b));

                    addRow(csv, "TwoRows", input, n, run,
                            twoRowsMs, twoRows.getOperations());
                }
            }
        }
    }

    private static void runStringSearch(StringBuilder csv) {
        int[] sizes = {1000, 2000, 4000, 8000, 16000};
        int patternLength = 20;

        for (int n : sizes) {
            for (String input : new String[]{"random", "repetitive"}) {
                String text = input.equals("random")
                        ? generator.randomString(n)
                        : generator.repeatedString(n, 'a');

                String pattern = input.equals("random")
                        ? generator.randomString(patternLength)
                        : generator.worstPattern(patternLength);

                NaiveSearch naive = new NaiveSearch();
                KMPSearch kmp = new KMPSearch();

                List<Integer> naiveResult = naive.search(text, pattern);
                List<Integer> kmpResult = kmp.search(text, pattern);

                if (!naiveResult.equals(kmpResult)) {
                    throw new IllegalStateException(
                            "Naive and KMP results do not match"
                    );
                }

                for (int i = 0; i < WARMUP; i++) {
                    naive.search(text, pattern);
                    kmp.search(text, pattern);
                }

                for (int run = 1; run <= RUNS; run++) {
                    double naiveMs = measure(
                            () -> naive.search(text, pattern)
                    );

                    addRow(csv, "Naive", input, n, run,
                            naiveMs, naive.getComparisons());

                    double kmpMs = measure(
                            () -> kmp.search(text, pattern)
                    );

                    addRow(csv, "KMP", input, n, run,
                            kmpMs, kmp.getComparisons());
                }
            }
        }
    }

    private static double measure(Runnable task) {
        // Measure a batch to reduce timing noise.
        int repetitions = 100;

        long start = System.nanoTime();

        for (int i = 0; i < repetitions; i++) {
            task.run();
        }

        long end = System.nanoTime();

        return (end - start) / 1_000_000.0 / repetitions;
    }

    private static void addRow(StringBuilder csv, String algorithm,
                               String input, int n, int run,
                               double timeMs, long operations) {

        csv.append(String.format(Locale.US,
                "%s,%s,%d,%d,%.6f,%d%n",
                algorithm, input, n, run, timeMs, operations));
    }
}
