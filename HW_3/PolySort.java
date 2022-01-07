/* 601.226
 * PolySort.java
 */

package hw3;

import hw2.Array;
import hw2.SimpleArray;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * A simple framework for evaluating sorting algorithms.
 *
 * The program reads at most the given number of lines from standard input.
 * (Input is treated as strings, not integers as in Unique.) It then runs a
 * variety of sorting algorithms on the data, collects statistics about each
 * algorithm, and prints those statistics to standard output.
 *
 * Hints: You should run each of your experiments at least three times and
 * ignore outliers to compensate for variations in load. Ideally you run them
 * on your own machine (not a shared lab machine or server) and with as few
 * other programs active as possible. That said, only the wallclock time will
 * be affected by these concerns, and that's a bad critereon to begin with...
 */

public final class PolySort {
    // Default capacity if none on the command line.
    private static final int DEFAULT_CAPACITY = 1000;

    // How many nanoseconds in a second.
    private static final double NANOS = 1e9;

    // We read the strings to be sorted into the following array; only entries
    // 0 to size-1 are actually used.
    private static int capacity = DEFAULT_CAPACITY;
    private static int size;
    private static Array<String> buffer;

    // The list of sorting algorithms we wish to compare. See setup() below
    // for how this list is initialized.
    private static ArrayList<SortingAlgorithm<String>> algorithms =
        new ArrayList<>();

    // Make checkstyle happy.
    private PolySort() {}

    // Print an error message to standard error.
    private static void errorln(String message) {
        System.err.println("error: " + message);
    }

    // Parse arguments.
    private static boolean arguments(String[] args) {

        if (args.length < 1) {
            System.out.println("usage: PolySort <data_file> [<size>]");
            return false;
        } else if (args.length == 1) {
            return true;
        }

        try {
            int value = Integer.parseInt(args[1]);
            if (value <= 0) {
                errorln("Capacity \"" + args[1] + "\" must be > 0.");
                return false;
            } else {
                capacity = value;
            }
        } catch (NumberFormatException e) {
            errorln("\"" + args[1] + "\" is not an integer (too large?).");
            return false;
        }
        return true;
    }

    // Set up buffer and algorithms.
    private static void setup() {
        // Create a buffer of appropriate size.
        buffer = new SimpleArray<>(capacity, null);
        // Add the algorithms we want to compare. This is where you should add
        // your algorithms once you have implemented them.
        algorithms.add(new NullSort<>());
        algorithms.add(new GnomeSort<>());
        algorithms.add(new SelectionSort<>());
        algorithms.add(new BubbleSort<>());
        algorithms.add(new InsertionSort<>());
    }

    // Read input into buffer array.
    private static void input(String fileName) throws FileNotFoundException {
        Scanner in = new Scanner(new FileReader(fileName));
        while (size < capacity && in.hasNextLine()) {
            String str = in.nextLine();
            buffer.put(size, str);
            size += 1;
        }
    }

    // Check whether the given array is sorted in ascending order.
    private static boolean sorted(Array<String> a) {
        for (int i = 0; i < a.length() - 1; i++) {
            if (a.get(i).compareTo(a.get(i + 1)) > 0) {
                return false;
            }
        }
        return true;
    }

    // Run the benchmark for all sorting algorithms.
    private static void benchmark() {
        if (size <= 0) {
            errorln("No input?");
            return;
        }

        System.out.printf(
            "%-20s %-8s %-12s %-12s %-12s %-12s\n\n",
            "Algorithm", "Sorted?", "Size", "Accesses", "Mutations", "Seconds"
        );

        MeasuredArray<String> temp = new MeasuredArray<>(size, null);

        for (SortingAlgorithm<String> algo: algorithms) {
            // copy value into statable array and reset it
            for (int j = 0; j < size; j++) {
                temp.put(j, buffer.get(j));
            }
            temp.reset();

            // run the next sorting algorithm
            long before = System.nanoTime();
            algo.sort(temp);
            long after = System.nanoTime();

            double seconds = (after - before) / NANOS;
            int accesses = temp.accesses();
            int mutations = temp.mutations();
            boolean success = sorted(temp); // last since it changes statistics

            // print the statistics for this algorithm
            System.out.printf(
                "%-20s %-8b %,-12d %,-12d %,-12d %,-12f\n",
                algo.name(), success, size, accesses, mutations, seconds
            );
        }

        // Java Collections sort on ArrayList
        ArrayList<String> list = new ArrayList<>(size);
        for (int j = 0; j < size; j++) {
            list.add(j, buffer.get(j));
        }

        long init = System.nanoTime();
        Collections.sort(list);
        long fin = System.nanoTime();

        double sec = (fin - init) / NANOS;

        // I don't check the ArrayList is sorted because I assume
        // Collections.sort is properly implemented for <String>
        System.out.printf("%-20s %-8b %,-12d %,-12d %,-12d %,-12f\n",
                            "Java Collections", true, size, 0, 0, sec);
    }

    /**
        Compare sorting algorithms.

        $ java PolySort [capacity] [&lt; file]

        @param args Command line arguments.
    */
    public static void main(String[] args) {
        if (!arguments(args)) {
            return;
        }

        setup();

        try {
            input(args[0]);
        } catch (FileNotFoundException e) {
            errorln("File not found: " + args[0]);
            return;
        }

        benchmark();
    }
}
