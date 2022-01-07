/*
 * BasicCounter.java
 */

package hw1;

import javax.sound.midi.Soundbank;

/** A counter that increments and decrements by 1. */
public class BasicCounter implements ResetableCounter {

    private int count;

    /** Construct a new BasicCounter. */
    public BasicCounter() {
        count = 0;
    }

    @Override
    public void reset() {
        count = 0;
    }

    @Override
    public int value() {
        return count;
    }

    @Override
    public void up() {
        count++;
    }

    @Override
    public void down() {
        count--;
    }
    
}


