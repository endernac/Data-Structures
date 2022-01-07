package hw6.tests;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class OrderedMapTest extends MapTest {

    private List<String> inorder;
    private List<String> keys;

    private void resetTestData() {
        inorder = new ArrayList<String>(Arrays.asList(
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"));
        keys = new ArrayList<String>(Arrays.asList(
                "T", "D", "L", "I", "B", "N", "E", "M", "U", "Y", "C", "V", "W", "X", "R", "A", "Q", "O", "F", "P", "G", "J", "S", "Z", "H", "K"));

    }

    // order can only change after you insert or remove elements, so we should
    // only test the insert and remove operations. Since we aren't assuming any
    // implementation details for the data structure other than it being
    // a map, it is hard to narrow down the edge cases (you don't know what's a
    // leaf, what has one child, what has two, *or if it's even a tree at all*)
    // Thus, I used a probabilistic approach

    @Test
    public void testOrder_insertMultipleNodes() {
        resetTestData();

        for (String key : keys) {
            m.insert(key, key);

            String prev = null;
            for (String k : m) {
                if (prev != null) {
                    assertTrue(k.compareTo(prev) > 0);
                }
                prev = k;
            }
        }

        int index = 0;
        for (String key : m) {
            assertEquals(inorder.get(index++), key);
        }
    }

    // "It'll get most of the cases for remove" ™
    @Test
    public void testOrder_removeMultipleNodes() {
        resetTestData();

        for (String key : this.keys) {
            m.insert(key, key);
        }

        for (int i = 0; i < 20; i++) {
            String letter = inorder.get((int) (inorder.size() * Math.random()));
            m.remove(letter);
            inorder.remove(letter);

            String prev = null;
            for (String k : m) {
                if (prev != null) {
                    assertTrue(k.compareTo(prev) > 0);
                }
                prev = k;
            }
        }

        int index = 0;
        for (String key : m) {
            assertEquals(inorder.get(index++), key);
        }
    }

}
