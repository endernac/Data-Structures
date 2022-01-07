/* 601.226
 * Counter.java
 */

package hw1;

/** The essence of any counter. */
public interface Counter {
    /**
     * Current value of this counter.
     * @return the current value of the counter.
     */
    int value();

    /** Increment this counter. */
    void up();

    /** Decrement this counter. */
    void down();
}
