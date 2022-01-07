/* MeasuredArray.java */

package hw3;

import exceptions.IndexException;
import hw2.SimpleArray;

/**
 * An Array that is able to report the number of accesses and mutations,
 * as well as reset those statistics.
 * @param <T> The type of the array.
 */
public class MeasuredArray<T> extends SimpleArray<T> implements Measured<T> {

    private int accesses;
    private int mutations;

    /**
     * Constructor for a MeasuredArray that calls the SimpleArray constructor.
     * @param n The size of the array.
     * @param t The initial value to set every object to in the array..
     */
    public MeasuredArray(int n, T t) {
        super(n, t);
        accesses = 0;
        mutations = 0;
    }

    @Override
    public int length() {
        return super.length();
    }

    @Override
    public T get(int i)  throws IndexException {
        T t = super.get(i);
        accesses++;
        return t;
    }

    @Override
    public void put(int i, T t) throws IndexException {
        super.put(i, t);
        mutations++;
    }

    @Override
    public void reset() {
        accesses = 0;
        mutations = 0;
    }

    @Override
    public int accesses() {
        return accesses;
    }

    @Override
    public int mutations() {
        return mutations;
    }

    @Override
    public int count(T t) {
        int count = 0;
        for (int i = 0; i < this.length(); i++) {
            if (this.get(i) == t) {
                count++;
            }
        }
        // TODO
        return count;
    }
}
