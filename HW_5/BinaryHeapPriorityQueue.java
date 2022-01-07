package hw5;

import exceptions.EmptyException;
import java.util.Comparator;
import java.util.ArrayList;

/**
 * Priority queue implemented as a binary heap.
 *
 * Use the ranked array representation of a binary heap!
 * Keep the arithmetic simple by sticking a null into slot 0 of the
 * ArrayList; wasting one reference is an okay price to pay for nicer
 * code.
 *
 *
 * @param <T> Element type.
 */
public class BinaryHeapPriorityQueue<T extends Comparable<? super T>>
    implements PriorityQueue<T> {

    // The default comparator uses the "natural" ordering.
    private static class DefaultComparator<T extends Comparable<? super T>>
        implements Comparator<T> {
        public int compare(T t1, T t2) {
            return t1.compareTo(t2);
        }
    }

    private ArrayList<T> heap;
    private Comparator<T> cmp;

    /**
     * A binary heap using the "natural" ordering of T.
     */
    public BinaryHeapPriorityQueue() {
        this(new DefaultComparator<>());
    }

    /**
     * A binary heap using the given comparator for T.
     * @param cmp Comparator to use.
     */
    public BinaryHeapPriorityQueue(Comparator<T> cmp) {
        this.cmp = cmp;
        this.heap = new ArrayList<>();
        this.heap.add(null);
    }


    @Override
    public void insert(T t) {
        this.heap.add(t);

        // element swims up
        int i = this.heap.size() - 1;
        while (i > 1) {
            T curr = this.heap.get(i);
            T par = this.heap.get(i / 2);

            if (cmp.compare(curr, par) > 0) {
                this.heap.set(i, par);
                this.heap.set(i / 2, curr);
            } else {
                break;
            }

            i = i / 2;
        }
    }

    @Override
    public void remove() throws EmptyException {
        if (this.empty()) {
            throw new EmptyException();
        }


        // put the last node at the top and get rid of previous top
        int size = this.heap.size() - 1;
        T leaf = this.heap.get(size);
        this.heap.set(1, leaf);
        this.heap.remove(size);

        int i = 1;
        while (i < size) {
            // find the maximum value child
            T curr = this.heap.get(i);
            T maxchi;
            int next;

            if (2 * i >= size) {
                // no children, do nothing
                break;
            }
            if (2 * i + 1 >= size
                    || cmp.compare(heap.get(2 * i), heap.get(2 * i + 1)) > 0) {
                // one child or left child is greater
                next = 2 * i;
                maxchi = this.heap.get(2 * i);
            } else {
                // right child is greater
                next = 2 * i + 1;
                maxchi = this.heap.get(2 * i + 1);
            }

            // sink the former last leaf if max child is greater
            if (cmp.compare(curr, maxchi) < 0) {
                this.heap.set(i, maxchi);
                this.heap.set(next, curr);
            } else {
                break;
            }

            i = next;
        }
    }

    @Override
    public T best() throws EmptyException {
        if (this.empty()) {
            throw new EmptyException();
        }

        return this.heap.get(1);
    }

    @Override
    public boolean empty() {
        return this.heap.size() == 1;
    }
}
