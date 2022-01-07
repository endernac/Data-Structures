package hw6.tests;

import hw6.AvlTreeMap;
import hw6.Map;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AvlTreeMapTest extends OrderedMapTest {

    @Override
    protected Map<String, String> createMap() {
        return new AvlTreeMap<>();
    }

    @Test
    public void testLeftRotationInsert() {
        m.insert("5", "test");
        m.insert("4", "test");
        m.insert("3", "test");
        assertEquals(m.toString(), "4:test\n3:test 5:test\n");
    }

    @Test
    public void testRightRotationInsert() {
        m.insert("3", "test");
        m.insert("4", "test");
        m.insert("5", "test");
        assertEquals(m.toString(), "4:test\n3:test 5:test\n");
    }

    @Test
    public void testRightLeftRotationInsert() {
        m.insert("5", "test");
        m.insert("3", "test");
        m.insert("4", "test");
        assertEquals(m.toString(), "4:test\n3:test 5:test\n");
    }

    @Test
    public void testLeftRightRotationInsert() {
        m.insert("3", "test");
        m.insert("5", "test");
        m.insert("4", "test");
        assertEquals(m.toString(), "4:test\n3:test 5:test\n");
    }

    @Test
    public void testRightRotationRemove() {
        m.insert("5", "test");
        m.insert("6", "test");
        m.insert("4", "test");
        m.insert("3", "test");
        assertEquals(m.toString(), "5:test\n4:test 6:test\n3:test null null null\n");
        m.remove("6");
        assertEquals(m.toString(), "4:test\n3:test 5:test\n");
    }

    @Test
    public void testLeftRotationRemove() {
        m.insert("4", "test");
        m.insert("3", "test");
        m.insert("5", "test");
        m.insert("6", "test");
        assertEquals(m.toString(), "4:test\n3:test 5:test\nnull null null 6:test\n");
        m.remove("3");
        assertEquals(m.toString(), "5:test\n4:test 6:test\n");
    }

    @Test
    public void testRightLeftRotationRemove() {
        m.insert("4", "test");
        m.insert("3", "test");
        m.insert("6", "test");
        m.insert("5", "test");
        assertEquals(m.toString(), "4:test\n3:test 6:test\nnull null 5:test null\n");
        m.remove("3");
        assertEquals(m.toString(), "5:test\n4:test 6:test\n");
    }

    @Test
    public void testLeftRightRotationRemove() {
        m.insert("5", "test");
        m.insert("6", "test");
        m.insert("3", "test");
        m.insert("4", "test");
        assertEquals(m.toString(), "5:test\n3:test 6:test\nnull 4:test null null\n");
        m.remove("6");
        assertEquals(m.toString(), "4:test\n3:test 5:test\n");
    }

}