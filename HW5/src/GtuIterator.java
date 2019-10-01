/*
* My Iterator Interface from CSE241 course.
* Can be found on my github account:
* https://github.com/root2a/CSE241/blob/master/HW8/GtuIterator.java
***********************************************************************************/  
import java.util.NoSuchElementException;
import java.util.Iterator;
/**
* GtuIterator interface. This interface extends Iterator for using for-each loops.
* @author Goktug Akin
* @since 2019
* @version HW8
*/
public interface GtuIterator<T> extends Iterator<T>{
	/**
	* Returns true if the iteration has more elements.
	* @return true if the iteration has more elements
	*/
	public abstract boolean hasNext();
	/**
	* Returns the next element in the iteration.
	* @return the next element in the iteration
	* @throws NoSuchElementException if the iteration has no more elements
	*/
	public abstract T next()throws NoSuchElementException; 
	/**
     * Removes from the underlying collection the last element returned 
     * by this iterator (optional operation).  This method can be called 
     * only once per call to {@link #next}.
	* @throws UnsupportedOperationException if the remove operation is not supported by this iterator.(Calling remove() before calling next())
	*/
	public abstract void remove();
}
