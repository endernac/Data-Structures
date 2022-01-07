package hw7.maps;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import hw7.Map;

// This implementation uses a linear probing approach to
// collision resolution. It uses the has overall structure as
// Linear probing hashmap (LinHashMap in maps), by using an array
// of entries. Each entry is composed of a key and a value. This
// approach comes with some simplicity since I don't need to keep
// two separate arrays of keys and values, but it results in some
// more casting warnings.
//
// The Linear hashing approach is the simplest open addressing hash collision
// resolution strategy. If there are collisions, it just increments the size
// of the index by one until it finds a index that doesn't cause a collision.
// This implementation uses a tombstone approach to deleting elements.
// Every time an element is to be removed, it simply marks an internal variable
// the array to indicate that the entry should be ignored for everything
// besides searching. The element is finally completely removed either when the
// array is resized, or when it gets overwritten by a new element. in insertion.
// However, tombstones and removal aren't important in this application since
// we are only adding, not removing elements from the hashmap.
//
// We also implanted a resizing rule that resized to approximately double the
// current size when the array was over 50% capacity, and half the size when the
// array was under 12 percent. Again the downsizing doesn't matter since no
// elements will be removed in our application.
//
// Another interesting thing I did was add a nextPrime function that
// found the next prime integer. Using prime numbers for the array size
// is important because it increases the distribution of keys along the
// array and potentially reduces hash collisions.
//

/**
 * Checkstyle.
 * @param <K>
 * @param <V>
 */
public class HashMapLin<K, V> implements Map<K, V> {

    // can make nested or inner
    private static class Entry<K, V> {
        K key;
        V value;
        boolean isTombstone;

        Entry(K k, V v) {
            key = k;
            value = v;
            isTombstone = false;
        }
    }

    // what if I had K[] and V[] arrays instead
    private Entry[] table;

    private int size;
    private int m;


    private HashMapLin(int tablesize) {
        m = tablesize;
        table = new Entry[m];
        size = 0;
    }

    /**
     * Default constructor.
     */
    public HashMapLin() {
        this(5); // need a prime
    }

    // Returns int in [0, m-1]
    // precondition k != null
    /**
     * @param k the key
     */
    private int hash(K k) throws IllegalArgumentException {
        if (k == null) {
            throw new IllegalArgumentException();
        }

        // make it positive and compress
        return (k.hashCode() & 0x7fffffff) % m;
    }

    // uses linear probing
    @Override
    public void insert(K k, V v) throws IllegalArgumentException {
        if (has(k)) {
            throw new IllegalArgumentException();
        }

        int i = hash(k);
        while (table[i] != null && !table[i].isTombstone) {
            i = (i + 1) % m;
        }

        table[i] = new Entry<>(k, v);

        size++;
        if (size >= m / 2) {
            resize(2 * m);
        }
    }

    private void resize(int num) {

        HashMapLin<K, V> temp = new HashMapLin<>(nextPrime(num));
        for (int i = 0; i < m; i++) {
            if (table[i] != null && !table[i].isTombstone) {
                temp.insert((K) table[i].key, (V) table[i].value);
            }
        }

        table = temp.table;
        m = temp.m;
    }

    @Override
    public V remove(K k) throws IllegalArgumentException {
        if (!has(k)) {
            throw new IllegalArgumentException();
        }

        Entry e = find(k);
        e.isTombstone = true;

        size--;
        if (size > 0 && size <= m / 8) {
            resize(m / 2);
        }

        return (V) e.value;
    }

    @Override
    public void put(K k, V v) throws IllegalArgumentException {
        if (!has(k)) {
            throw new IllegalArgumentException();
        }

        Entry e = find(k);
        e.value = v;
    }

    @Override
    public V get(K k) throws IllegalArgumentException {
        if (!has(k)) {
            throw new IllegalArgumentException();
        }

        Entry e = find(k);
        return (V) e.value;
    }

    @Override
    public boolean has(K k) {
        return (find(k) != null);
    }

    private Entry find(K k) {
        if (k == null) {
            throw new IllegalArgumentException();
        }

        for (int i = hash(k); table[i] != null; i = (i + 1) % m) {
            Entry<K, V> e = table[i];
            if (k.equals(e.key) && !e.isTombstone) {
                return e;
            }
        }

        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        String toString = "";
        for (K key : this) {
            toString += key.toString() + " : ";
            toString += this.get(key).toString() + "\n";
        }

        return toString;
    }

    @Override
    public Iterator<K> iterator() {
        List<K> keys = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            if (table[i] != null) {
                keys.add((K) table[i].key);
            }
        }

        return keys.iterator();
    }

    // helper methods to ensure table capacity is prime
    private static int nextPrime(int n) {
        if (n % 2 == 0) {
            n++;
        }
        while (!isPrime(n)) {
            n += 2;
        }
        return n;
    }

    private static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }

        if (n <= 3) {
            return false;
        }

        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
        }

        return true;
    }
}