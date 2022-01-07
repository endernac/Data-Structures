/* Unique.java
 */

package hw2;

import java.util.InputMismatchException;
import java.util.Scanner;

/** Unique problem using a SparseArray and processing from standard in. */
public final class Unique {

    // make checkstyle happy
    private Unique() {}

    /**
     * Print only unique integers out of entered numbers.
     * @param args Command line arguments, not used.
     */
    public static void main(String[] args) {
        int len = 10;
        int count = 0;

        Scanner in = new Scanner(System.in);
        ListArray<Integer> uniq = new ListArray<>(len, 0);

        while (in.hasNext()) {

            if (count >= len) {
                ListArray<Integer> temp = new ListArray<>(len * 2, 0);

                for (int i = 0; i < len; i++) {
                    temp.put(i, uniq.get(i));
                }
                uniq = temp;
                len = uniq.length();
            }
            try {
                int val = in.nextInt();
                boolean repeat = false;

                for (int i = 0; i < count; i++) {
                    if (val == uniq.get(i)) {
                        repeat = true;
                        break;
                    }
                }
                if (!repeat) {
                    uniq.put(count, val);
                    count++;
                }

            } catch (InputMismatchException e) {
                throw new IllegalArgumentException("One or more arguments " +
                                                   "is not an integer!");
            }
        }

        for (int i = 0; i < count; i++) {
            System.out.println(uniq.get(i));

        }
    }
}
