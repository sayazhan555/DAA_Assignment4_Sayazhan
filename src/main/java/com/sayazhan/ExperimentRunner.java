package com.sayazhan;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Locale;

public class ExperimentRunner {

    private static final int RUNS = 5;
    private static final InputGenerator generator = new InputGenerator();

    public static void main(String[] args) throws IOException {
        Files.createDirectories(Path.of("results"));

        StringBuilder csv = new StringBuilder();
        csv.append("algorithm,input,n,run,time_ms,operations\n");

        runEditDistance(csv);
        runStringSearch(csv);

        Files.writeString(Path.of("results/results.csv"), csv.toString());

        System.out.println("Results saved to results/results.csv");
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

                RecursiveEditDistance algorithm = new RecursiveEditDistance();

                for (int run = 1; run <= RUNS; run++) {
                    long start = System.nanoTime();
                    algorithm.distance(a, b);
                    double ms = (System.nanoTime() - start) / 1_000_000.0;

                    addRow(csv, "Recursive", input, n, run,
                            ms, algorithm.getCalls());
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

                for (int run = 1; run <= RUNS; run++) {
                    long start = System.nanoTime();
                    table.distance(a, b);
                    double ms = (System.nanoTime() - start) / 1_000_000.0;

                    addRow(csv, "Table", input, n, run,
                            ms, table.getOperations());

                    start = System.nanoTime();
                    twoRows.distance(a, b);
                    ms = (System.nanoTime() - start) / 1_000_000.0;

                    addRow(csv, "TwoRows", input, n, run,
                            ms, twoRows.getOperations());
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

                for (int run = 1; run <= RUNS; run++) {
                    long start = System.nanoTime();
                    naive.search(text, pattern);
                    double ms = (System.nanoTime() - start) / 1_000_000.0;

                    addRow(csv, "Naive", input, n, run,
                            ms, naive.getComparisons());

                    start = System.nanoTime();
                    kmp.search(text, pattern);
                    ms = (System.nanoTime() - start) / 1_000_000.0;

                    addRow(csv, "KMP", input, n, run,
                            ms, kmp.getComparisons());
                }
            }
        }
    }

    private static void addRow(StringBuilder csv, String algorithm,
                               String input, int n, int run,
                               double timeMs, long operations) {

        csv.append(String.format(Locale.US,
                "%s,%s,%d,%d,%.6f,%d%n",
                algorithm, input, n, run, timeMs, operations));
    }
}
