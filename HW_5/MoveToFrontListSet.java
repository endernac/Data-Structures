package hw5;

import exceptions.PositionException;
import exceptions.EmptyException;


/**
 * Set implemented using our abstract List, with a LinkedList.
 * 
 * accessed. Override the relevant method(s) from ListSet.
 *
 * @param <T> Element type.
 */
public class MoveToFrontListSet<T> extends ListSet<T> {

    /** hush checkstyle.
     * @param t parameter.
     * @return Position.
     */
    @Override
    protected Position<T> find(T t) {
        try {
            Position<T> pos = this.list.front();
            while (! pos.get().equals(t)) {
                pos = this.list.next(pos);
            }
            if (!this.list.first(pos)) {

                T temp = pos.get();
                this.list.insertFront(temp);
                this.list.remove(pos);

                return this.list.front();
            }
            return pos;
        } catch (EmptyException emptyException) {
            return null;
        } catch (PositionException positionException) {
            return null;
        }
    }

}
