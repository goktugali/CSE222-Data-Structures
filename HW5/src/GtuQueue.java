import java.util.NoSuchElementException;
/*
 * My queue interface from CSE 241 course.
 *******************
 *   CSE 241 HW8   *
 * Goktug Ali Akin *
 *******************
 ***********************************************************************************/

/**
 * @author Goktug Akin
 * @since 2019
 * @version HW8
 */
public interface GtuQueue<T> extends GtuCollection<T>{

    /**
     * Returns an iterator over the elements in this collection.
     * @return an Iterator over the elements in this collection
     */
    GtuIterator<T> iterator();
    /**
     * Inserts the specified element into this queue
     * @param elem the element to add
     * @return True after adding.
     */
    boolean add(T elem);
    /**
     * Returns true if this collection contains the specified element.
     * @param elem element whose presence in this collection is to be tested
     * @return true if this collection contains the specified element
     */
    boolean contains(T elem);
    /**
     * Returns true if this collection contains no elements.
     * @return true if this collection contains no elements
     */
    boolean isEmpty();
    /**
     * Removes a single instance of the specified element from this collection
     * @param elem element to be removed from this collection, if present
     * @return true if an element was removed as a result of this call
     */
    boolean remove(T elem);
    /**
     * Removes all of the elements from this collection
     */
    void clear();
    /**
     * Retrieves the size of collection.
     * @return size of the collection.
     */
    int size();

    /**
     * Retrieves, but does not remove, the head (first element) of this list.
     * @return the head of this list
     * @throws NoSuchElementException If list is empty.
     */
    T element()throws NoSuchElementException;
    /**
     * Add element to the collection.
     * @param e Element that will be added to this collections.
     */
    boolean offer(T e);
    /**
     * Retrieves and removes the head (first element) of this list.
     * @return the head of this list.
     * @throws NoSuchElementException If list is empty.
     */
    T poll()throws NoSuchElementException;

    T peek();
}
