package hw6.tests;

import hw6.TreapMap;
import hw6.Map;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TreapMapTest extends OrderedMapTest {

    // First 10 values of randInt() with seed 5:
    // -1157408321
    //  758500184
    //  379066948
    // -1667228448
    //  2099829013
    // -236332086
    //  1983575708
    // -745993913
    //  1926715444
    //  1836354642
    @Override
    protected Map<String, String> createMap() {
        return new TreapMap<>(5);
    }

    @Test
    public void testLeftRotateInsert() {
        m.insert("3", "test");
        m.insert("4", "test");
        m.insert("5", "test");
        assertEquals(m.toString(), "3:test:-1157408321\nnull 5:test:379066948\nnull null 4:test:758500184 null\n");
    }

    @Test
    public void testRightRotateInsert() {
        m.insert("5", "test");
        m.insert("4", "test");
        m.insert("3", "test");
        assertEquals(m.toString(), "5:test:-1157408321\n3:test:379066948 null\nnull 4:test:758500184 null null\n");
    }

    @Test
    public void testLeftRotateDelete() {
        m.insert("4", "test");
        m.insert("3", "test");
        m.insert("5", "test");
        m.remove("4");
        assertEquals(m.toString(), "5:test:379066948\n3:test:758500184 null\n");
    }

    @Test
    public void testRightRotateDelete() {
        m.insert("4", "test");
        m.insert("5", "test");
        m.insert("3", "test");
        m.remove("4");
        assertEquals(m.toString(), "3:test:379066948\nnull 5:test:758500184\n");
    }

}