package hw5;

/**
 * Set implemented using plain Java arrays.
 * 
 * @param <T> Element type.
 */
public class TransposeArraySet<T> extends ArraySet<T> {

    /**
     * hush checkstyle.
     * @param t parameter.
     * @return integer.
     */
    @Override
    protected int find(T t) {
        for (int i = 0; i < this.used; i++) {
            if (this.data[i].equals(t)) {
                if (i != 0) {
                    T temp = data[i];
                    data[i] = data[i - 1];
                    data[i - 1] = temp;
                    return i - 1;
                }
                return i;
            }
        }
        return -1;
    }
}
