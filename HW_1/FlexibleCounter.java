/*
 * FlexibleCounter.java
 */

package hw1;

/** Class for a counter with flexible starting and incrementing values. */
public class FlexibleCounter implements ResetableCounter {

    private int count;
    private final int initial;
    private int increment;

    /**
     * Construct a new FlexibleCounter.
     * @param initialValue The value to start at.
     * @param incrementValue The value to increment the counter by.
     * @throws IllegalArgumentException If incrementValue is negative.
     */
    public FlexibleCounter(int initialValue, int incrementValue) {
        count = initialValue;
        initial = initialValue;
        if (incrementValue < 0) {
            throw new IllegalArgumentException("Increment value cannot be " +
                                                "negative!");
        } else {
            increment = incrementValue;
        }
    }

    @Override
    public void reset() {
        count = initial;
    }

    @Override
    public int value() {
        return count;
    }

    @Override
    public void up() {
        count += increment;
    }

    @Override
    public void down() {
        count -= increment;
    }
}
