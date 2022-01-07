package hw6.tests;

import hw6.Map;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Testing implementations of PriorityQueues.
 */
public abstract class MapTest {
    protected Map<String, String> m;
    protected abstract Map<String, String> createMap();

    private List<String> inorder;
    private List<String> keys;

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

    // Following tests make sure map throws an error when it needs to

    @Test(expected=IllegalArgumentException.class)
    public void insertNullKey() {
        m.insert(null, "Hello");
    }

    @Test(expected=IllegalArgumentException.class)
    public void insertUsedKey() {
        m.insert("A", "Hello");
        m.insert("A", "yes");
    }

    @Test(expected=IllegalArgumentException.class)
    public void removeNullKey() {
        m.remove(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void removeUnusedKey() {
        m.insert("A", "Hello");
        m.remove("A");
        m.remove("A");
    }

    @Test(expected=IllegalArgumentException.class)
    public void getNullKey() {
        m.get(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void getUnusedKey() {
        m.get("B");
    }

    // testing other methods on an empty map
    @Test()
    public void hasNullKey() {
        assertTrue(!m.has(null));
    }

    @Test()
    public void hasUnusedKey() {
        assertTrue(!m.has("B"));
    }

    @Test()
    public void sizeEmpty() {
        assertEquals(m.size(), 0);
    }

    // test inserting
    @Test()
    public void insertSingleValue() {
        m.insert("A", "1");
        assertEquals(m.get("A"), "1");
        assertEquals(m.size(), 1);
    }

    @Test()
    public void insertManyValues() {
        for (int i = 0; i < 50; i++) {
            m.insert("" + i, "" + (25 - 3 * i));
        }
        for (int i = 0; i < 50; i++) {
            assertEquals(m.get("" + i), "" + (25 - 3 * i));
        }
        assertEquals(m.size(), 50);
    }

    // haphazardly test remove - it'll probably catch everything
    @Test()
    public void removeManyValues() {
        for (int i = 0; i < 1000000; i++) {
            m.insert("" + i, "" + (i));
        }
        for (int i = 0; i < 1000000; i++) {
            System.out.println();
            assertEquals(m.remove("" + i), "" + (i));
        }
        assertEquals(m.size(), 0);
    }

    @Test()
    public void putValue() {
        for (int i = 0; i < 1000; i++) {
            m.insert("" + i, "" + (i));
        }
        m.put("23", "107");
        assertEquals(m.get("23"), "107");
    }

}
