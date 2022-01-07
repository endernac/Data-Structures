/* PolyArray.java
 */

package hw2;

import exceptions.IndexException;
import exceptions.LengthException;

import java.util.ArrayList; // see note in main() below
import java.util.Iterator;


/**
 * Simple polymorphic test framework for arrays.
 * See last week's PolyCount. You need to add more test cases (meaning more
 * methods like testNewLength and testNewWrongLength below) to make sure all
 * preconditions and axioms are indeed as expected from the specification.
*/
public final class PolyArray {
    private static final int LENGTH = 113;
    private static final int INITIAL = 7;

    private PolyArray() {}

    private static void testNewLength(Array<Integer> a) {
        assert a.length() == LENGTH;
    }

    private static void testNewGet(Array<Integer> a) {
        for (int i = 0; i < LENGTH; i++) {
            assert a.get(i) == INITIAL;
        }
    }

    // Put in random new integer values to all elements
    private static void testPutLength(Array<Integer> a) {
        for (int i = 0; i < LENGTH; i++) {
            a.put(i, i);
        }
        assert  a.length() == LENGTH;
    }

    // Put in random new integer values to all elements
    private static void testPutGet(Array<Integer> a) {
        for (int i = 0; i < LENGTH; i++) {
            a.put(i, i);
        }

        for (int i = 0; i < LENGTH; i++) {
            assert a.get(i) == i;
        }
    }

    private static void testNewWrongLength() {
        try {
            Array<Integer> a = new SimpleArray<>(0, INITIAL);
            assert false;
        } catch (LengthException e) {
            // passed the test, nothing to do
        }
        try {
            Array<Integer> a = new ListArray<>(0, INITIAL);
            assert false;
        } catch (LengthException e) {
            // passed the test, nothing to do
        }
        try {
            Array<Integer> a = new SparseArray<>(0, INITIAL);
            assert false;
        } catch (LengthException e) {
            // passed the test, nothing to do
        }
    }

    private static void testNewBadGet() {
        // Index too high
        try {
            Array<Integer> a = new SimpleArray<>(LENGTH, INITIAL);
            a.get(LENGTH);
            assert false;
        } catch (IndexException e) {
            // passed the test, nothing to do
        }
        try {
            Array<Integer> a = new ListArray<>(LENGTH, INITIAL);
            a.get(LENGTH);
            assert false;
        } catch (IndexException e) {
            // passed the test, nothing to do
        }
        try {
            Array<Integer> a = new SparseArray<>(LENGTH, INITIAL);
            a.get(LENGTH);
            assert false;
        } catch (IndexException e) {
            // passed the test, nothing to do
        }

        // Index too low
        try {
            Array<Integer> a = new SimpleArray<>(LENGTH, INITIAL);
            a.get(-1);
            assert false;
        } catch (IndexException e) {
            // passed the test, nothing to do
        }
        try {
            Array<Integer> a = new ListArray<>(LENGTH, INITIAL);
            a.get(-1);
            assert false;
        } catch (IndexException e) {
            // passed the test, nothing to do
        }
        try {
            Array<Integer> a = new SparseArray<>(LENGTH, INITIAL);
            a.get(-1);
            assert false;
        } catch (IndexException e) {
            // passed the test, nothing to do
        }
    }

    private static void testNewBadPut() {
        // Index too high
        try {
            Array<Integer> a = new SimpleArray<>(LENGTH, INITIAL);
            a.put(LENGTH, INITIAL);
            assert false;
        } catch (IndexException e) {
            // passed the test, nothing to do
        }
        try {
            Array<Integer> a = new ListArray<>(LENGTH, INITIAL);
            a.put(LENGTH, INITIAL);
            assert false;
        } catch (IndexException e) {
            // passed the test, nothing to do
        }
        try {
            Array<Integer> a = new SparseArray<>(LENGTH, INITIAL);
            a.put(LENGTH, INITIAL);
            assert false;
        } catch (IndexException e) {
            // passed the test, nothing to do
        }

        // Index too low
        try {
            Array<Integer> a = new SimpleArray<>(LENGTH, INITIAL);
            a.put(-1, INITIAL);
            assert false;
        } catch (IndexException e) {
            // passed the test, nothing to do
        }
        try {
            Array<Integer> a = new ListArray<>(LENGTH, INITIAL);
            a.put(-1, INITIAL);
            assert false;
        } catch (IndexException e) {
            // passed the test, nothing to do
        }
        try {
            Array<Integer> a = new SparseArray<>(LENGTH, INITIAL);
            a.put(-1, INITIAL);
            assert false;
        } catch (IndexException e) {
            // passed the test, nothing to do
        }
    }

    private static void testSparseIterator() {
        SparseArray<Integer> a = new SparseArray<>(LENGTH, INITIAL);
        for (int i = 0; i < LENGTH; i += 10) {
            a.put(i, i);
        }

        Iterator it = a.iterator();

        // tests hasNext and next axioms for each element
        int count = 0;
        while (it.hasNext()) {
            if (count % 10 == 0) {
                assert it.next().equals(count);
            } else {
                assert it.next().equals(INITIAL);
            }
            count++;
        }
    }

    /**
     * Run (mostly polymorphic) tests on various array implementations.
     * Make sure you run this with -enableassertions! We'll learn a much
     * better approach to unit testing later.
     *
     * @param args Command line arguments (ignored).
    */
    public static void main(String[] args) {
        // For various technical reasons, we cannot use a plain Java array here
        // like we did in PolyCount. Sorry.
        ArrayList<Array<Integer>> arrays = new ArrayList<>();
        arrays.add(new SimpleArray<>(LENGTH, INITIAL));
        arrays.add(new ListArray<>(LENGTH, INITIAL));
        arrays.add(new SparseArray<>(LENGTH, INITIAL));

        // Test all the axioms. We can do that nicely in a loop. In the test
        // methods, keep in mind that you are handed the same object over and
        // over again! I.e., order matters!
        for (Array<Integer> a: arrays) {
            testNewLength(a);
            testNewGet(a);
            testPutLength(a); // want value != initial
            testPutGet(a);
            testSparseIterator();
        }

        // Exception testing. Sadly we have to code each one of these
        // out manually, not even Java's reflection API would help...
        testNewWrongLength();
        testNewBadGet();
        testNewBadPut();

    }
}
