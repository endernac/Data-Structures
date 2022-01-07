package hw7.tests;

import hw7.HashMap;
import hw7.Map;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;

public class HashMapTest extends MapTest {

    @Override
    protected Map<String, String> createMap() {
        return new HashMap<String, String>();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPutException() {
        m.insert("key", "value");
        m.put("key2", "value");
    }

    @Test
    public void testPut() {
        m.insert("key", "value");
        m.put("key", "value2");
        assertEquals(m.get("key"), "value2");
    }

    @Test
    public void testHas() {
        m.insert("key", "value");
        assertEquals(m.has("key"), true);
    }

    @Test
    public void testIterator() {
        m.insert("key", "value");
        m.insert("otherkey", "othervalue");
        Iterator<String> iter = m.iterator();
        String expected = "keyotherkey";
        String result = "";
        while (iter.hasNext()) {
            result += iter.next();
        }
        assertEquals(expected, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDuplicateInsert() {
        m.insert("key", "value");
        m.insert("key", "value2");
    }

    @Test
    public void testSize() {
        m.insert("key", "value2");
        assertEquals(m.size(), 1);
    }

    @Test
    public void testCollisionProperInsert() {
        //"FB" and "Ea" have same hashcode
        m.insert("FB", "value");
        m.insert("Ea", "value2");
        //check that "value2" did not overwrite "value"
        assertEquals(m.get("FB"), "value");
    }

    @Test
    public void testToString() {
        m.insert("key", "value");
        m.insert("newkey", "newvalue");
        m.insert("different", "diffval");
        String expected = "different : diffval\n" +
                "key : value\n" +
                "newkey : newvalue\n";
        assertEquals(expected, m.toString());
    }

}
