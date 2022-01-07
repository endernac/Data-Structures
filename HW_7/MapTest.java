package hw7.tests;

import hw7.Map;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Testing implementations of PriorityQueues.
 */
public abstract class MapTest {
    protected Map<String, String> m;
    protected abstract Map<String, String> createMap();

    @Before
    public void setupMap() {
        m = this.createMap();
    }

    @Test
    public void newMapEmpty() {
        assertEquals(0, m.size());
        assertFalse(m.has(""));

        int count = 0;
        for (String k : m) {
            count++;
        }
        assertEquals(count, 0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void insertNullKey() {
        m.insert(null, "Hello");
    }

    // TODO: Write lots more tests!
    @Test
    public void insertAndRemove() {
        m.insert("key", "value");
        m.remove("key");
        assertEquals(m.size(), 0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void duplicateInsertExceptionThrown() {
        m.insert("key", "value");
        m.insert("key", "value2");
    }

    @Test(expected=IllegalArgumentException.class)
    public void nullRemoveExceptionThrown() {
        m.insert("key", "value");
        m.remove(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void nonexistentKeyRemoveExceptionThrown() {
        m.insert("key", "value");
        m.remove("key2");
    }

    @Test
    public void insertAndRemoveHas() {
        m.insert("key", "value");
        m.remove("key");
        assertFalse(m.has("key"));
    }

    @Test
    public void testPut() {
        m.insert("key", "value");
        m.put("key", "newValue");
        assertEquals(m.get("key"), "newValue");
    }

    @Test(expected=IllegalArgumentException.class)
    public void nullPutExceptionThrown() {
        m.insert("key", "value");
        m.put(null, "value2");
    }

    @Test(expected=IllegalArgumentException.class)
    public void nonexistentKeyPutExceptionThrown() {
        m.insert("key", "value");
        m.put("key2", "value2");
    }

    @Test
    public void testGet() {
        m.insert("key", "value");
        assertEquals(m.get("key"), "value");
    }

    @Test(expected=IllegalArgumentException.class)
    public void nonexistentKeyGetExceptionThrown() {
        m.insert("key", "value");
        m.get("key2");
    }

    @Test
    public void testHas() {
        m.insert("key", "value");
        assertTrue(m.has("key"));
    }

    @Test
    public void testNonexistentHas() {
        assertFalse(m.has("key"));
    }

    @Test
    public void testLength() {
        m.insert("key", "value");
        assertEquals(m.size(), 1);
    }

}
