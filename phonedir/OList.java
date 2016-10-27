/**
 * The <tt> OList </tt> class represents an ordered list.
 * The invariant that the internal data is keept sorted is
 * maintained with every operation on this class.
 **/
import java.util.LinkedList;
import java.util.Iterator;
public class OList<E extends Comparable<E>> implements Iterable<E> {
    private E selection;
    private LinkedList<E> sorted;
    public OList() {
        this.selection = null;
        this.sorted = new LinkedList<E>();
    }
    public E getSelection() {
        return this.selection;
    }
    /**
     * Inserts the specified element into the list where the
     * specified element is greater than all previous elements.
     * @pre internal data is sorted
     * @post internal data is sorted
     */
    public void insert(E element) {
        this.selection = element;
        int i = 0;
        for(E other:this.sorted) {
            if (0 < other.compareTo(element)) {
                this.sorted.add(i,element);
                return;
            }
            i++;
        }
        this.sorted.add(i,element);
    }
    /**
     * Deletes the currently selected element
     * @pre internal data is sorted
     * @post internal data is sorted
     **/
    public E delete() {
        E current = this.selection;
        this.selection = null;
        this.sorted.remove(current);
        return current;
    }
    public boolean select(E element) {
        for(E other:this.sorted) {
            if(element.equals(other)) {
                this.selection = other;
                return true;
            }
        }
        return false;
    }
    public Iterator<E> iterator() {
        return this.sorted.iterator();
    }
}
