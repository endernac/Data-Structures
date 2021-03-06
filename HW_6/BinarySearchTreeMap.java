package hw6;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Ordered maps from comparable keys to arbitrary values.
 *
 * These BSTs are not balanced so worst-case all operations are O(n).
 * Each iterator operates on a copy of the keys, so changing the tree
 * will not change iterations in progress.
 *
 * @param <K> Type for keys.
 * @param <V> Type for values.
 */
public class BinarySearchTreeMap<K extends Comparable<? super K>, V>
    implements OrderedMap<K, V> {

    // Inner node class, each holds a key (which is what we sort the
    // BST by) as well as a value. We don't need a parent pointer as
    // long as we use recursive insert/remove helpers.
    private class Node {
        Node left;
        Node right;
        K key;
        V value;

        // Constructor to make node creation easier to read.
        Node(K k, V v) {
            // left and right default to null
            this.key = k;
            this.value = v;
        }

        // Just for debugging purposes.
        public String toString() {
            return "Node<key: " + this.key
                + "; value: " + this.value
                + ">";
        }
    }

    private Node root;
    private int size;


    @Override
    public int size() {
        return this.size;
    }

    // Return node for given key. This one is iterative
    // but the recursive one from lecture would work as
    // well. (For simply finding a node there's no big
    // advantage to using recursion; I did recursion in
    // lecture to get you into the right mindset.)
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

    @Override
    public boolean has(K k) {
        if (k == null) {
            return false;
        }
        return this.find(k) != null;
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

    @Override
    public void put(K k, V v) {
        Node n = this.findForSure(k);
        n.value = v;
    }

    @Override
    public V get(K k) {
        Node n = this.findForSure(k);
        return n.value;
    }

    // Insert given key and value into subtree rooted
    // at given node; return changed subtree with new
    // node added. (Doing this recursively makes it
    // easier to add fancy rebalancing code later.)
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

        return n;
    }

    @Override
    public void insert(K k, V v) {
        if (k == null) {
            throw new IllegalArgumentException("cannot handle null key");
        }
        this.root = this.insert(this.root, k, v);
        this.size += 1;
    }

    // Return node with maximum key in subtree rooted
    // at given node. (Iterative version because once
    // again recursion has no advantage here.)
    private Node max(Node n) {
        while (n.right != null) {
            n = n.right;
        }
        return n;
    }

    // Remove node with given key from subtree rooted at
    // given node; return changed subtree with given key
    // missing. (Once again doing this recursively makes
    // it easier to add fancy rebalancing code later.)
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
        if (n.left == null) {
            return n.right;
        }
        if (n.right == null) {
            return n.left;
        }

        // 2 children
        Node max = this.max(n.left);
        n.key = max.key;
        n.value = max.value;
        n.left = this.remove(n.left, max.key);
        return n;
    }

    @Override
    public V remove(K k) {
        // Need this additional find() for the V return value, because the
        // private remove() method cannot return that in addition to the new
        // root. If we had been smarter and used a void return type, we would
        // not need to do this extra work.
        Node n = this.find(k);
        V val = null;
        if (n != null) {
            val = n.value;
        }
        this.root = this.remove(this.root, k);
        this.size -= 1;
        return val;
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
        List<K> keys = new ArrayList<K>();
        this.iteratorHelper(this.root, keys);
        return keys.iterator();
    }

    // Breadth first traversal that prints binary tree out level by level.
    // Each existing node is printed as follows: 'node.key:node.value'.
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
}
