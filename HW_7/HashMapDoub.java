package hw7.maps;

import hw7.Map;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// This implementation uses a Double hashing approach to
// collision resolution. It uses the has overall structure as
// Linear probing hashmap (LinHashMap in maps), by using an array
// of entries. Each entry is composed of a key and a value. This
// approach comes with some simplicity since I don't need to keep
// two separate arrays of keys and values, but it results in some
// more casting warnings.
//
// The double hash resolution method is very similar to the linear
// hash resolution method. Every time that there is a collision, I
// increase the index by a random number. This random number is the
// second hash function. The advantage this approach has over quadratic
// probing is that it greatly avoids clustering since now there are two
// elements of randomness rather than just one. However it's harder
// on cache since new hashes need to be constantly re calculated. Similar
// to the other implementations, it grows the array once 50% capacity has
// been reached. One advantage of quadratic probing is that it helps avoid
// clustering due to multiple collisions.
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
public class HashMapDoub<K, V> implements Map<K, V> {

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
    private int prime;

    private HashMapDoub(int tablesize) {
        m = tablesize;
        table = new Entry[m];
        size = 0;
        prime = nextPrime((int) (3.0 / 5 * m));
    }

    /**
     * Default constructor.
     */
    public HashMapDoub() {
        this(5); // need a prime
    }

    // Returns int in [0, m-1]
    // precondition k != null
    /**
     * @param k the key
     */
    private int hash1(K k) throws IllegalArgumentException {
        if (k == null) {
            throw new IllegalArgumentException();
        }

        // make it positive and compress
        return (k.hashCode() & 0x7fffffff) % m;
    }

    // Returns int in [0, m-1]
    // precondition k != null
    /**
     * @param k the key
     */
    private int hash2(K k) throws IllegalArgumentException {
        if (k == null) {
            throw new IllegalArgumentException();
        }

        int h = k.hashCode() & 0x7fffffff;
        return prime - (h % prime);
    }

    // uses linear probing
    @Override
    public void insert(K k, V v) throws IllegalArgumentException {
        if (has(k)) {
            throw new IllegalArgumentException();
        }

        int i = hash1(k);
        while (table[i] != null && !table[i].isTombstone) {
            i = (i + hash2(k)) % m;
        }

        table[i] = new Entry<>(k, v);

        size++;
        if (size >= m / 2) {
            resize(2 * m);
        }
    }

    private void resize(int num) {

        HashMapDoub<K, V> temp = new HashMapDoub<>(nextPrime(num));
        for (int i = 0; i < m; i++) {
            if (table[i] != null && !table[i].isTombstone) {
                temp.insert((K) table[i].key, (V) table[i].value);
            }
        }

        table = temp.table;
        m = temp.m;
        prime = temp.prime;
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

        for (int i = hash1(k); table[i] != null; i = (i + hash2(k)) % m) {
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