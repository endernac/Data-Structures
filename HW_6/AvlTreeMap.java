package hw6;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.List;
import java.util.ArrayList;

/**
 * Checkstyle.
 * @param <K>
 * @param <V>
 */
public class AvlTreeMap<K extends Comparable<? super K>, V>
        implements OrderedMap<K, V> {

    /*** Do not change the name of the Node class. ***/
    // Inner node class, each holds a key (which is what we sort the
    // BST by) as well as a value. We don't need a parent pointer as
    // long as we use recursive insert/remove helpers.
    // Do not change the name of this class
    private class Node {
        /***  Do not change variable names in this section. ***/
        Node left;
        Node right;
        K key;
        V value;
        int height;
        // need to add a height field so we don't need to constantly recalculate
        /*********************************************************/

        // Constructor to make node creation easier to read.
        Node(K k, V v) {
            // left and right default to null
            // height is 0 since node will be a leaf
            this.key = k;
            this.value = v;
            this.height = 0;
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
    private int size;

    private int balance(Node n) {
        if (n == null) {
            return 0;
        }
        return -height(n.left) + height(n.right);
    }

    // safe height method, so that you don't access fields of a null
    // pointer. Assumes height in the field is correct (which it will be)
    private int height(Node n) {
        if (n == null) {
            return -1;
        }
        return n.height;
    }

    // updates the height based on what is to the left and right of node
    private void updateHeight(Node n) {
        if (n == null) {
            return;
        }
        n.height = Math.max(height(n.left), height(n.right)) + 1;
    }

    private Node rotateRight(Node z) {
        Node x = z.left;

        z.left = x.right;
        x.right = z;

        updateHeight(z); // update z first since it is lower
        updateHeight(x);

        return x;
    }

    private Node rotateLeft(Node x) {
        Node z = x.right;

        x.right = z.left;
        z.left = x;

        updateHeight(x); // update x first because it is lower
        updateHeight(z);

        return z;
    }

    private Node rotateRightLeft(Node x) {
        Node z = x.right;
        Node t = z.left;

        Node b = t.left;
        Node c = t.right;

        x.right = b;
        z.left = c;

        t.left = x;
        t.right = z;

        updateHeight(x);
        updateHeight(z);
        updateHeight(t);

        return t;
    }

    private Node rotateLeftRight(Node z) {
        Node x = z.left;
        Node t = x.right;

        Node b = t.left;
        Node c = t.right;

        x.right = b;
        z.left = c;

        t.left = x;
        t.right = z;

        updateHeight(x);
        updateHeight(z);
        updateHeight(t);

        return t;
    }

    // determines what rotation is necessary based on the balance factor
    private Node rotate(Node n, int b) {
        Node t = n;

        if (b < -1) { // left heavy, right rotate
            Node l = n.left;
            if (height(l.left) >= height(l.right)) {
                t = rotateRight(n);
            } else {
                t = rotateLeftRight(n);
            }
        } else if (b > 1) { // right heavy, left rotate
            Node r = n.right;
            if (height(r.right) >= height(r.left)) {
                t = rotateLeft(n);
            } else {
                t = rotateRightLeft(n);
            }
        }
        return t;
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

        // rotate if it needs to be rebalanced
        int b = balance(n);
        n = rotate(n, b);

        // the height function hitch hikes on the recursion of insert so
        // it only changes when insert adds a new layer and only for the
        // directly affected ancestors
        updateHeight(n);

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

        // rotate if it needs rebalancing
        int b = balance(n);
        n = rotate(n, b);

        //  update height if it hasn't already been updated
        updateHeight(n);

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
