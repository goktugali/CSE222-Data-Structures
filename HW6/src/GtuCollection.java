/*
* My Collection Interface from (CSE241) course.
* can be found on my github account
* https://github.com/root2a/CSE241/blob/master/HW8/GtuCollection.java
*/
/**
* @author Goktug Akin
* @since 2019
* @version HW8
*/
public interface GtuCollection<T> extends Iterable<T>{

	/**
	* Returns an iterator over the elements in this collection. 
	*/
	GtuIterator<T> iterator();
	/**
	* Ensures that this collection contains the specified element 
	* @param elem Element that will be added to this collection
	* @return true if this collection changed as a result of the call
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
	* @return true if this collection contains no elements.
	*/
	boolean isEmpty();
	/**
	* Removes a single instance of the specified element from this collection.
	* @param elem element to be removed from this collection, if present
	* @return true if an element was removed as a result of this call
	*/
	boolean remove(T elem);
	/**
	* Removes all of the elements from this collection (optional operation).
	*/
	void clear();
	/**
	* Returns the number of elements in this collection.
	* @return the number of elements in this collection
	*/
	int size();

}