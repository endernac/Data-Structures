package hw6;

import java.util.Iterator;
import java.util.Random;
import java.util.LinkedList;
import java.util.Queue;
import java.util.List;
import java.util.ArrayList;

/**
 * Checkstyle.
 * @param <K>
 * @param <V>
 */
public class TreapMap<K extends Comparable<? super K>, V>
        implements OrderedMap<K, V> {

    /*** Do not change the name of the Node class. ***/
    // Inner node class, each holds a key (which is what we sort the
    // BST by) as well as a value. We don't need a parent pointer as
    // long as we use recursive insert/remove helpers. Since this
    // is a node class for a Treap we also include a priority field.
    private class Node {
        /***  Do not change variable names in this section. ***/
        Node left;
        Node right;
        K key;
        V value;
        int priority;
        /*****************************************************/

        // Constructor to make node creation easier to read.
        Node(K k, V v) {
            // left and right default to null
            this.key = k;
            this.value = v;
            this.priority = TreapMap.this.generateRandomInteger();
        }

        // Just for debugging purposes.
        public String toString() {
            return "Node<key: " + this.key
                    + "; value: " + this.value
                    + ">";
        }
    }

    /*** Do not change variable name of 'root'. ***/
    private Node root;

    /*** Do not change variable name of 'rand'. ***/
    private Random rand;

    private int size;

    /**
     * Make a TreapMap.
     */
    public TreapMap() {
        this.rand = new Random();
        root = null;
        size = 0;
    }

    /**
     * Make a TreapMap with seed.
     *
     * @param seed Seed for random number generator.
     */
    public TreapMap(int seed) {
        this.rand = new Random(seed);
        root = null;
        size = 0;
    }

    // Insert given key and value into subtree rooted
    // at given node; return changed subtree with new
    // node added. Update the height as you go
    private Node insert(Node n, K k, V v) {
        if (n == null) {
            return new Node(k, v);
        }

        int cmp = k.compareTo(n.key);
        if (cmp < 0) {
            n.left = this.insert(n.left, k, v);
        } else if (cmp > 0) {
            n.right = this.insert(n.right, k, v);
        } else {
            throw new IllegalArgumentException("duplicate key " + k);
        }

        // rotate if it priorities are out of whack
        n = rotate(n);

        return n;
    }

    @Override
    public void insert(K k, V v) throws IllegalArgumentException {
        if (k == null) {
            throw new IllegalArgumentException("cannot handle null key");
        }
        this.root = this.insert(this.root, k, v);
        this.size += 1;
    }

    // Remove node with given key from subtree rooted at
    // given node; return changed subtree with given key
    // missing.
    private Node remove(Node n, K k) {
        if (n == null) {
            throw new IllegalArgumentException("cannot find key " + k);
        }

        int cmp = k.compareTo(n.key);
        if (cmp < 0) {
            n.left = this.remove(n.left, k);
        } else if (cmp > 0) {
            n.right = this.remove(n.right, k);
        } else {
            n = this.remove(n);
        }

        return n;
    }

    // Remove given node and return the remaining tree.
    // Easy if the node has 0 or 1 child; if it has two
    // children, find the predecessor, copy its data to
    // the given node (thus removing the key we need to
    // get rid off), the remove the predecessor node.
    private Node remove(Node n) {
        // 0 and 1 child
        Node t;

        if (n.left == null && n.right == null) {
            t = null;
        } else if (n.left != null && priority(n.left) < priority(n.right)) {
            t = rotate(n);
            t.right = remove(n);
        }
        else {
            t = rotate(n);
            t.left = remove(n);
        }

        return t;
    }

    @Override
    public V remove(K k) {
        // Need this additional find() for the V return value, because the
        // private remove() method cannot return that in addition to the new
        // root. If we had been smarter and used a void return type, we would
        // not need to do this extra work.
        Node n = this.findForSure(k);
        n.priority = Integer.MAX_VALUE;
        V v = this.findForSure(k).value;

        this.root = this.remove(this.root, k);
        this.size -= 1;
        return v;
    }


    @Override
    public void put(K k, V v) throws IllegalArgumentException {
        Node n = this.findForSure(k);
        n.value = v;
    }

    @Override
    public V get(K k) throws IllegalArgumentException {
        Node n = this.findForSure(k);
        return n.value;
    }

    @Override
    public boolean has(K k) {
        if (k == null) {
            return false;
        }
        return this.find(k) != null;
    }

    @Override
    public int size() {
        return size;
    }

    // Recursively add keys from subtree rooted at given node
    // into the given list.
    private void iteratorHelper(Node n, List<K> keys) {
        if (n == null) {
            return;
        }
        this.iteratorHelper(n.left, keys);
        keys.add(n.key);
        this.iteratorHelper(n.right, keys);
    }

    @Override
    public Iterator<K> iterator() {
        List<K> keys = new ArrayList<>();
        this.iteratorHelper(this.root, keys);
        return keys.iterator();
    }

    /*** Do not change this function's name or modify its code. ***/
    // Breadth first traversal that prints binary tree out level by level.
    // Each existing node is printed as follows:
    // 'node.key:node.value:node.priority'.
    // Non-existing nodes are printed as 'null'.
    // There is a space between all nodes at the same level.
    // The levels of the binary tree are seperated by new lines.
    // Returns empty string if the root is null.
    @Override
    public String toString() {
        if (root == null) {
            return "";
        }

        StringBuilder s = new StringBuilder();
        Queue<Node> q = new LinkedList<>();

        q.add(root);
        while (!q.isEmpty()) {
            boolean onlyNullChildrenAdded = true;

            int levelSize = q.size();
            while (levelSize > 0) {
                Node n = q.remove();
                if (n != null) {
                    if (n.left != null || n.right != null) {
                        onlyNullChildrenAdded = false;
                    }

                    s.append(n.key);
                    s.append(":");
                    s.append(n.value);
                    s.append(":");
                    s.append(n.priority);

                    q.add(n.left);
                    q.add(n.right);
                } else {
                    s.append(n);

                    q.add(null);
                    q.add(null);
                }

                s.append(" ");
                levelSize--;
            }

            s.deleteCharAt(s.length() - 1);
            s.append("\n");
            if (onlyNullChildrenAdded) {
                break;
            }
        }

        return s.toString();
    }

    /*** Do not change this function's name or modify its code. ***/
    // Use this function to generate random values
    // to use as node priorities as you insert new
    // nodes into your TreapMap.
    private int generateRandomInteger() {
        return rand.nextInt();
    }

    private Node rotateRight(Node z) {
        Node x = z.left;

        z.left = x.right;
        x.right = z;

        return x;
    }

    private Node rotateLeft(Node x) {
        Node z = x.right;

        x.right = z.left;
        z.left = x;

        return z;
    }

    private Node find(K k) {
        if (k == null) {
            throw new IllegalArgumentException("cannot handle null key");
        }
        Node n = this.root;
        while (n != null) {
            int cmp = k.compareTo(n.key);
            if (cmp < 0) {
                n = n.left;
            } else if (cmp > 0) {
                n = n.right;
            } else {
                return n;
            }
        }
        return null;
    }

    // Return node for given key, throw an exception
    // if the key is not in the tree.
    private Node findForSure(K k) {
        Node n = this.find(k);
        if (n == null) {
            throw new IllegalArgumentException("cannot find key " + k);
        }
        return n;
    }

    // Safe function that can return valid priorities even for nulls.
    private int priority(Node n) {
        if (n == null) {
            return Integer.MAX_VALUE - 1;
        }
        return n.priority;
    }

    private Node rotate(Node n) {
        if (n == null) {
            return null;
        }

        Node t = n;
        int pl = priority(n.left);
        int pr = priority(n.right);
        int p = priority(n);

        if (pl <= pr && pl < p) {
            t = rotateRight(n);
        } else if (pr <= pl && pr < p) {
            t = rotateLeft(n);
        }

        return t;
    }

}
