# DAA Assignment 4 — Dynamic Programming and String Algorithms

**Student:** Sayazhan Kanat  
**Group:** SE-2517  


## Project Description

This project implements and compares algorithms for Edit Distance and String Search.

The goal is to understand their performance using different input sizes and input types. The project includes automated tests, operation counters, benchmark results and graphs.

## Implemented Algorithms

### Edit Distance

- **RecursiveEditDistance** — direct recursion without memoization.
- **TableEditDistance** — dynamic programming using a full table.
- **TwoRowsEditDistance** — dynamic programming using only two rows.

All three implementations calculate the minimum number of insertions, deletions and replacements needed to transform one string into another.

### String Search

- **NaiveSearch** — checks every possible starting position.
- **KMPSearch** — uses the LPS array to avoid unnecessary comparisons.

Both algorithms return all matching positions, including overlapping matches.

## Technologies

- Java 17+
- Maven
- JUnit 5
- Python 3
- Matplotlib

## Project Structure

```text
DAA_Assignment4_Sayazhan/
├── src/
│   ├── main/java/com/sayazhan/
│   │   ├── Main.java
│   │   ├── RecursiveEditDistance.java
│   │   ├── TableEditDistance.java
│   │   ├── TwoRowsEditDistance.java
│   │   ├── NaiveSearch.java
│   │   ├── KMPSearch.java
│   │   ├── InputGenerator.java
│   │   └── ExperimentRunner.java
│   └── test/java/com/sayazhan/
│       ├── EditDistanceTest.java
│       ├── KMPSearchTest.java
│       └── StringSearchTest.java
├── results/
│   ├── results.csv
│   └── plots/
├── plot_results.py
├── EXPERIMENT_DESIGN.md
├── REPORT.md
├── README.md
└── pom.xml
```

## How to Run

### 1. Build and test

Open the project in IntelliJ IDEA and load the Maven project.

Run **Maven → Lifecycle → test**.

Alternatively, if Maven is installed on your computer, run:

```bash
mvn clean test
```

The tests check reference values, edge cases, overlapping matches and 100 random inputs for each algorithm family.

### 2. Run experiments

Open `ExperimentRunner.java` in IntelliJ IDEA and run its `main()` method.

The program generates benchmark data and saves it to:

`results/results.csv`

Each measurement uses JVM warm-up and 100 executions. Five measurements are recorded for each configuration.

### 3. Generate plots

Install Matplotlib:

```bash
python3 -m pip install matplotlib
```

Generate the plots:

```bash
python3 plot_results.py
```

The generated PNG files are saved in `results/plots/`.

## Experiment Results

The experiment uses random and repetitive input strings with a fixed seed of 42.

The results show that:

- Recursive Edit Distance makes many repeated calls.
- Table and TwoRows have quadratic time complexity.
- TwoRows needs less additional memory than Table.
- KMP makes fewer character comparisons than Naive Search on repetitive text.

The complete results and explanations are available in [REPORT.md](REPORT.md).

## AI Use Acknowledgement

AI assistance was used to help plan the project, draft and review parts of the Java and Python code, prepare tests, and improve the documentation. The algorithms were run and tested in IntelliJ IDEA, and the benchmark measurements and graphs were generated from actual executions.