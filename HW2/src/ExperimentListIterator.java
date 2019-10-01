import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * ExperimentListIterator class.
 * @author Goktug Akin , 161044018
 * @version HW2
 * @since 2019
 */
/* This interface is implemented to use iterator
outside of the ExperimentList class, such as Main class.
*/
public interface ExperimentListIterator extends Iterator<Experiment> {

    /**
     * Returns true if the iteration has more elements.
     * @return true if the iteration has more elements
     */
    @Override
    boolean hasNext();

    /**
     * Returns the next element in the iteration.
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    Experiment next();
    /**
     * Removes from the underlying collection the last element returned
     * by this iterator (optional operation).  This method can be called
     * only once per call to {@link #next}.
     * @throws IllegalStateException if the remove operation is not supported by this iterator.(Calling remove() before calling next())
     */
    void remove();
}