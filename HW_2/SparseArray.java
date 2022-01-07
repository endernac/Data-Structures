/* SparseArray.java
 */

package hw2;

import exceptions.IndexException;
import exceptions.LengthException;

import java.util.Iterator;


/**
 * Array implementation using a linked list. The main difference between
 * SparseArray and the previous implementations is that SparseArray does
 * not store all the elements of the array, only the ones explicitly
 * specified. This can save a lot of memory if there are only a few unique
 * values relative to the size of the full array.
 * @param <T> Element type.
 */
public class SparseArray<T> implements Array<T> {

    // Linked List must store both data and index
    private static class Node<T> {
        T data;
        int index;
        Node<T> next;
    }

    private class SparseArrayIterator implements Iterator<T> {
        int position;

        SparseArrayIterator() {
            position = 0;
        }

        // Since the node positions are out of order and some elements
        // might not be included, it doesn't make sense to look at the
        // next element. You could search for the next element, or you
        // can just compare position to length (which is easier)
        @Override
        public boolean hasNext() {
            return (position < SparseArray.this.length() - 1);
        }

        // Linked list is incomplete and out of order, so iterator
        // doesn't "iterate" so much as find the node with (i+1)th
        // position
        @Override
        public T next() {
            T t = SparseArray.this.get(position);
            position++;
            return t;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


    // SparseArray has a length and linked list similar to ListArray, but
    // also has a default value as well.
    private final int length;
    private Node<T> list;
    private final T defaultValue;

    /**
     * An array that is meant to be filled primarily with a default value
     * that is not going to change - with the benefit of that default
     * value not being stored numerous times as opposed to once.
     * @param length The number of indexes the array should have.
     * @param defaultValue The default value for the array.
     * @throws LengthException if length is 0.
    */
    public SparseArray(int length, T defaultValue) throws LengthException {
        if (length <= 0) {
            throw new LengthException();
        }

        this.length = length;
        this.defaultValue = defaultValue;
    }

    // Insert a node at the beginning of the linked list. It has index i.
    private void prepend(int i, T t) {
        SparseArray.Node<T> n = new SparseArray.Node<>();
        n.data = t;
        n.index = i;
        n.next = this.list;
        this.list = n;
    }

    // private helper to find the node with the correct index, if there is one
    private Node<T> find(int index) {
        Node<T> n = this.list;

        while (n != null && n.index != index) {
            n = n.next;
        }
        return n;
    }

    // returns length of array
    @Override
    public int length() {
        return length;
    }

    // returns the value at the specified index, or throws an error
    // if index not valid.
    @Override
    public T get(int i) throws IndexException {
        if (i < 0 || i >= length) {
            throw new IndexException();
        }

        Node<T> n = this.find(i);

        if (n == null) {
            return defaultValue;
        }

        return n.data;
    }

    @Override
    public void put(int i, T t) throws IndexException {
        if (i < 0 || i >= length) {
            throw new IndexException();
        }

        Node<T> n = this.find(i);

        if (n == null) {
            this.prepend(i, t);
        } else {
            n.data = t;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new SparseArrayIterator();
    }
}
