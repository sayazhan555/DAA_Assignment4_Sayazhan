import csv
import statistics
from collections import defaultdict
from pathlib import Path

import matplotlib.pyplot as plt

RESULTS = Path("results")
PLOTS = RESULTS / "plots"
PLOTS.mkdir(parents=True, exist_ok=True)

with (RESULTS / "results.csv").open(newline="") as file:
    rows = list(csv.DictReader(file))

groups = defaultdict(list)

for row in rows:
    key = (row["algorithm"], row["input"], int(row["n"]))
    groups[key].append(row)

data = {}

for key, values in groups.items():
    data[key] = {
        "time": statistics.median(
            float(v["time_ms"]) for v in values
        ),
        "operations": statistics.median(
            int(v["operations"]) for v in values
        ),
    }


def draw_plot(algorithms, input_type, metric,
              title, ylabel, filename, log_y=False):

    plt.figure(figsize=(9, 5))

    for algorithm in algorithms:
        points = sorted(
            (n, values[metric])
            for (name, kind, n), values in data.items()
            if name == algorithm and kind == input_type
        )

        if not points:
            continue

        x, y = zip(*points)
        plt.plot(x, y, marker="o", label=algorithm)

    plt.title(title)
    plt.xlabel("Input size n (characters)")
    plt.ylabel(ylabel)

    if log_y:
        plt.yscale("log")

    plt.legend()
    plt.grid(True, alpha=0.3)
    plt.tight_layout()
    plt.savefig(PLOTS / filename, dpi=200)
    plt.close()

    print("Saved:", filename)


draw_plot(
    ["Recursive"],
    "different",
    "operations",
    "Recursive Edit Distance - Function Calls",
    "Recursive calls",
    "edit_recursive_calls.png",
    log_y=True
)

draw_plot(
    ["Table", "TwoRows"],
    "different",
    "time",
    "DP Edit Distance - Execution Time",
    "Median time (ms)",
    "edit_dp_time.png"
)

draw_plot(
    ["Naive", "KMP"],
    "repetitive",
    "operations",
    "String Search - Repetitive Input",
    "Character comparisons",
    "search_comparisons.png"
)

draw_plot(
    ["Naive", "KMP"],
    "repetitive",
    "time",
    "String Search - Execution Time",
    "Median time (ms)",
    "search_time.png"
)

print("All plots generated.")
