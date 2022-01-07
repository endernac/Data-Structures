package hw4.tests;

import exceptions.EmptyException;
import exceptions.ExampleException;
import exceptions.LengthException;
import exceptions.IndexException;
import hw4.Deque226;
import hw4.FlawedDeque226;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class FlawedDeque226Test {

    // The unit being tested
    private Deque226<String> stringDequeue;

    @Before
    public void setupDequeue() {
        this.stringDequeue = new FlawedDeque226<String>();
    }

    @Test(expected = ExampleException.class)
    public void exampleTest() {
        throw new ExampleException();
    }

    // Test new Deque is empty
    @Test
    public void emptyNewTest() {
        assertTrue(stringDequeue.empty());
    }

    // Test new Deque has length 0
    @Test
    public void lengthNewTest() {
        assertEquals(0, stringDequeue.length());
    }

    // Test empty Deque throws EmptyException when front is called
    @Test(expected = EmptyException.class)
    public void emptyFrontTest() {
        stringDequeue.front();
    }

    // Test empty Deque throws EmptyException when back is called
    @Test(expected = EmptyException.class)
    public void emptyBackTest() {
        stringDequeue.back();
    }

    // Test empty Deque throws EmptyException when removeFront is called
    @Test(expected = EmptyException.class)
    public void emptyRemoveFrontTest() {
        stringDequeue.removeFront();
    }

    // Test empty Deque throws EmptyException when removeBack is called
    @Test(expected = EmptyException.class)
    public void emptyRemoveBackTest() {
        stringDequeue.removeBack();
    }

    // Test insertFront makes deque non empty
    @Test
    public void emptyInsertFrontTest() {
        stringDequeue.insertFront("Howdy");
        assertTrue(!stringDequeue.empty());
    }

    // Test insertBack makes deque non empty
    @Test
    public void emptyInsertBackTest() {
        stringDequeue.insertBack("Howdy");
        assertTrue(!stringDequeue.empty());
    }

    // Test insertFront adds new string each time it is called
    @Test
    public void lengthInsertFrontTest() {
        for (int i = 1; i < 100; i++) {
            stringDequeue.insertFront("" + i);
            assertEquals(i, stringDequeue.length());
            assertEquals("" + i, stringDequeue.front());
        }
    }

    // Test insertFront adds new string to front each time it is called
    @Test
    public void backInsertFrontTest() {
        for (int i = 1; i < 100; i++) {
            stringDequeue.insertFront("" + i);
            assertEquals("" + 1, stringDequeue.back());
        }
    }

    // Test insertBack adds new string each time it is called
    @Test
    public void lengthInsertBackTest() {
        for (int i = 1; i < 100; i++) {
            stringDequeue.insertBack("" + i);
            assertEquals(i, stringDequeue.length());
            assertEquals("" + i, stringDequeue.back());
        }
    }

    // Test insertFront adds new string to back each time it is called
    @Test
    public void frontInsertBackTest() {
        for (int i = 1; i < 100; i++) {
            stringDequeue.insertBack("" + i);
            assertEquals("" + 1, stringDequeue.front());
        }
    }

    // Test removeFront removes new string from front each time it is called
    // previous tests indicate that insertBack does not work, so we will use
    // insertFront
    @Test
    public void lengthRemoveFrontTest() {
        for (int i = 1; i < 100; i++) {
            stringDequeue.insertFront("" + i);
        }

        for (int i = 1; i < 100; i++) {
            stringDequeue.removeFront();
            assertEquals(99 - i, stringDequeue.length());
            if (stringDequeue.length() > 0) {
                assertEquals("" + 1, stringDequeue.back());
            }
        }
        assertTrue(stringDequeue.empty());
    }

    // Test removeBack removes new string from front each time it is called
    // previous tests indicate that insertBack does not work, so we will use
    // insertFront
    @Test
    public void lengthRemoveBackTest() {
        for (int i = 1; i < 100; i++) {
            stringDequeue.insertFront("" + i);
        }

        for (int i = 1; i < 100; i++) {
            stringDequeue.removeBack();
            assertEquals(99 - i, stringDequeue.length());
            if (stringDequeue.length() > 0) {
                assertEquals("" + 99, stringDequeue.front());
            }
        }
        assertTrue(stringDequeue.empty());
    }




}
