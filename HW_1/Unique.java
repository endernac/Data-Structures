/*
 * Unique.java
 */

package hw1;

/** A class with a main method for printing out unique numbers. 
 */
public final class Unique {

    // Make checkstyle happy.
    private Unique() {
        throw new AssertionError("Can not instantiate class Unique\n");
    }

    /**
     * A main method to print the unique numerical command line arguments.
     * @param args The string array of arguments in the command line.
     */
    public static void main(String[] args) {
        int[] uniq = new int[args.length];

        int count = 0;

        for (String str: args) {
            boolean repeat = false;
            int cur;

            try {
                cur = Integer.parseInt(str);
            } catch (NumberFormatException | NullPointerException nfe) {
                throw new IllegalArgumentException("One or more arguments " +
                                                    "is not an integer!");
            }

            for (int u = 0; u < count; u++) {
                if (cur == uniq[u]) {
                    repeat = true;
                    break;
                }
            }

            if (!repeat) {
                uniq[count] = cur;
                count++;
            }
        }


        for (int i = 0; i < count; i++) {
            System.out.println(uniq[i]);
        }
    }
}
