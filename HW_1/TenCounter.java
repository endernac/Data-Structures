/*
 * TenCounter.java
 */

package hw1;

/** A counter for powers of 10. */
public class TenCounter implements ResetableCounter {

    private int count;

    /** Construct a new TenCounter. */
    public TenCounter() {
        count = 1;
    }

    @Override
    public void reset() {
        count = 1;
    }

    @Override
    public int value() {
        return count;
    }

    /** Increase the value by a factor of 10.
     */
    public void up() {
        count *= 10;
    }

    /** Decrease the value by a factor of 10, rounding up
        to the nearest integer. Do not go below 1.
     */
    public void down() {
        if (count > 1) {
            count /= 10;
        }
    }
}
