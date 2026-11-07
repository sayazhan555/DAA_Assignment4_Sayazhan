# Experiment Design Card

## Edit Distance

Question: How does the cost of recursive, table and two-row
edit distance change when the input strings become longer?

Sizes:
- Recursive: 2, 4, 6, 8
- Table and Two Rows: 100, 200, 400, 800, 1600

Inputs:
- Random strings: generated with a fixed seed.
- Different strings: repeated 'a' versus repeated 'b'
  to show expensive recursive branching.

Metrics:
- Execution time in milliseconds. Each measurement is the
  average of 100 executions after JVM warm-up.
  The final value is the median of 5 measurements.
- Recursive function calls.
- DP cell updates.

Prediction:
The recursive version will become much slower.
Both DP versions will have approximately quadratic cost.
Two Rows will use less memory than the full table.

Why these sizes and metrics:
I used small sizes for recursion because the number of
function calls grows very quickly. I used larger sizes
for Table and TwoRows to observe quadratic growth.

Recursive calls show repeated calculations, while DP cell
updates show how much work the DP algorithms perform.

## String Search

Question: How do Naive and KMP behave on random
and repetitive input?

Sizes:
- Text lengths: 1000, 2000, 4000, 8000, 16000
- Pattern length: 20

Inputs:
- Random text with a fixed seed.
- Repetitive text: many 'a' characters, pattern ending
  with 'b', to show the difficult case for Naive Search.

Metrics:
- Execution time in milliseconds. Each measurement is the
  average of 100 executions after JVM warm-up.
  The final value is the median of 5 measurements.
- Character comparisons inside the algorithms.

Prediction:
Naive Search will make more comparisons on repetitive
input. KMP will avoid repeated comparisons using LPS.

Why these sizes and metrics:
I increased the text length from 1000 to 16000 while
keeping the pattern length fixed at 20. This helps show
how both algorithms behave as the text becomes longer.

Character comparisons show how much matching work
each algorithm performs. Time measurements help compare
their actual execution speed.


## Planned Plots

1. Edit Distance: operation counts versus input size.
2. Edit Distance: execution time versus input size.
3. String Search: comparisons versus text length.
4. String Search: execution time versus text length.

Plot questions:
1. How quickly do recursive calls increase?
2. How does DP execution time grow with input size?
3. How many comparisons do Naive and KMP perform?
4. How does search execution time change with text length?

Experiment changes:
The first timing results contained noise from JVM startup
and optimization. I added JVM warm-up and measured batches
of 100 executions to get more stable results.

The fixed seed makes input strings and operation counts
reproducible. Exact execution times may vary between runs.
