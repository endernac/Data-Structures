/* InsertionSort.java */

package hw3;

import hw2.Array;


/**
 * The Insertion Sort algorithm, with minimizing swaps optimization.
 * @param <T> Element type.
 */
public final class InsertionSort<T extends Comparable<T>>
    implements SortingAlgorithm<T> {

    // Helper to make code more readable.
    private boolean less(T a, T b) {
        return a.compareTo(b) < 0;
    }

    

    @Override
    public void sort(Array<T> a) {
        int i = 1;

        while (i < a.length()) {
            T key = a.get(i);
            int j = i;

            while (j > 0 && !this.less(a.get(j - 1), key)) {
                a.put(j, a.get(j - 1));
                j = j - 1;
            }

            // I could add an if statement to limit unnecessary mutations, but
            // that would be deceptive since the if statement would be executed
            // instead, leading to the same amount, if not more operations.
            a.put(j, key);


            i++;
        }
    }

    @Override
    public String name() {
        return "Insertion Sort";
    }
}
