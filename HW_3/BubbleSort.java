/* BubbleSort.java */

package hw3;

import hw2.Array;

/**
 * The Bubble Sort algorithm with the optimized "quick" break to exit
 * if the array is sorted.
 * @param <T> The type being sorted.
 */
public final class BubbleSort<T extends Comparable<T>>
    implements SortingAlgorithm<T> {

    @Override
    public void sort(Array<T> array) {
        boolean noSwap = false;
        // TODO
        for (int i = 0; i < array.length() - 1; i++) {
            noSwap = true;
            for (int j = 0; j < array.length() - i - 1; j++) {
                if ((array.get(j + 1).compareTo(array.get(j))) < 0) {
                    noSwap = false;

                    //swap values
                    T temp = array.get(j);
                    array.put(j, array.get(j + 1));
                    array.put(j + 1, temp);
                }
            }
            //if no swaps performed in a pass then exit
            if (noSwap) {
                break;
            }
        }
    }

    @Override
    public String name() {
        return "Bubble Sort";
    }
}
