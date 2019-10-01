/*
* My List Interface from (CSE241) course.
* can be found on my github account
* https://github.com/root2a/CSE241/blob/master/HW8/GtuList.java
*/
/**
* @author Goktug Akin
* @since 2019
* @version HW8
*/
public interface GtuList<T> extends GtuCollection<T>
{	
	/**
	* Returns an iterator over the ArrayList.
	* @return Iterator for iterating over the collection List.
	*/
	GtuIterator<T> iterator();
	/**
	* Ensures that this collection contains the specified element
	* @param elem Element that will be added to this collectionList.
	* @return true after adding.
	*/
	boolean add(T elem);
	/**
	* Inserts the specified element at the specified position in this list.
	* @param index index at which the specified element is to be inserted
	* @param elem element to be inserted.
	* @return true after adding.
	* @throws IndexOutOfBoundsException if the index is out of range
	*/
	boolean add(int index, T elem)throws IndexOutOfBoundsException;
	/**
	* Returns true if this list contains the specified element.
	* @param elem element whose presence in this list is to be tested.
	* @return true if this list contains the specified element
	*/
	boolean contains(T elem);
	/**
	* If the list is empty, returns 1 , els return 0.
	* @return If the list is empty, returns 1 , els return 0.
	*/
	boolean isEmpty();
	/**
	* Removes the first occurrence of the specified element from this list, if it is present.
	* @param elem element to be removed from this list, if present
	* @return true if this list contained the specified element 
	*/
	boolean remove(T elem);

	T remove(int index);

	/**
	* Removes all of the elements from this list. The list will be empty after this call returns.
	*/
	void clear();
	/**
	* Gets the size of (used) data location in ArrayList.
	* @return Size of the collection ArrayList.
	*/
	int size();
	/**
	* Replaces the element at the specified position in this list with the specified element.
	* @param index index of the element to replace
	* @param elem element to be stored at the specified position
	* @return the element previously at the specified position
	* @throws IndexOutOfBoundsException if the index is out of range
	*/
	T set(int index,T elem)throws IndexOutOfBoundsException; //additonal
	/**
	* Returns the element at the specified position in this list.
	* @param index index of the element to return
	* @return the element at the specified position in this list
	* @throws IndexOutOfBoundsException if the index is out of range
	*/
	T get(int index)throws IndexOutOfBoundsException; //additonal
	/**
	* Returns the index of the first occurrence of the specified element in this list, or -1 if this list does not contain the element.
	* @param elem element to search for
	* @return the index of the first occurrence of the specified element in this list, or -1 if this list does not contain the element
	*/
	int indexOf(T elem); //additonal
	/**
	* Couns the number of occurences by giving element.
	* @param elem Element whose occurence number in this list is to be tested.
	* @return Number of occurences of element gived by parameter elem.
	*/
	int count(T elem); //additonal
}