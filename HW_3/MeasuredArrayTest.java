/* MeasuredArrayTest.java */


package hw3;

import exceptions.IndexException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MeasuredArrayTest {

    private static final int SIZE = 20;
    private static final String VAL = "test";

    private MeasuredArray<String> array;

    @Before
    public void createArray() {
        this.array = new MeasuredArray<>(SIZE, VAL);
    }

    @Test
    public void testLength() {
        assertEquals(SIZE, array.length());
    }

    @Test
    public void newArrayZeroMutations() {
        assertEquals(0, array.mutations());
    }

    @Test
    public void newArrayZeroAccesses() {
        assertEquals(0, array.accesses());
    }

    @Test
    public void accessesMethodTest() {
        String s;
        for (int i = 0; i < array.length(); i++) {
            s = array.get(i);
        }
        //assert that entire array has been accessed
        assertEquals(array.length(), array.accesses());
    }

    @Test
    public void mutationsMethodTest() {
        String s  = "new";
        for (int i = 0; i < array.length(); i++) {
            array.put(i, s);
        }
        //assert that entire Array has been mutated
        assertEquals(array.length(), array.mutations());
    }

    @Test(expected = IndexException.class)
    public void testInvalidIndexPut() {
        String s = "new";
        array.put(SIZE + 1, s);
    }

    @Test
    public void testWrongIndexMutationValue() {
        String s = "new";
        try {
            array.put(SIZE + 1, s);
            assert false;
        } catch (IndexException e) {
            assertEquals(0, array.mutations());
        }
    }

    @Test(expected = IndexException.class)
    public void testInvalidIndexGet() {
        String s = "";
        s = array.get(SIZE + 1);
    }
}
