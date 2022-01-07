/* 601.226
 * ResetableCounter.java
 */

package hw1;

/**
 * An interface for a counter that can reset. Note that it extends the
 * {@link Counter} interface, meaning a class that implements ResetableCounter
 * must also implement the Counter methods. ResetableCounter is-a Counter.
 */
public interface ResetableCounter extends Counter {
    /** Reset the counter to it's initial value. */
    void reset();
}
