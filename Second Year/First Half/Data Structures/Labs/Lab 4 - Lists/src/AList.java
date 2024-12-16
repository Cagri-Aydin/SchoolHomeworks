import java.util.Arrays;

/**
 * A class that implements a list of objects by using an array. Entries in a
 * list have positions that begin with 1. Duplicate entries are allowed.
 * 
 * @author Frank M. Carrano
 * @author Timothy M. Henry
 * @version 5.0
 */
public class AList<T> implements ListInterface<T> {
	private T[] list; // Array of list entries; ignore list[0]
	private int numberOfEntries;
	private static final int DEFAULT_CAPACITY = 25;
	private static final int MAX_CAPACITY = 10000;

	public AList() {
		this(DEFAULT_CAPACITY);
	} // end default constructor

	@SuppressWarnings("unchecked")
	public AList(int initialCapacity) {
		// Is initialCapacity too small?
		if (initialCapacity < DEFAULT_CAPACITY)
			initialCapacity = DEFAULT_CAPACITY;
		else // Is initialCapacity too big?
			checkCapacity(initialCapacity);

		// The cast is safe because the new array contains null entries
		list = (T[]) new Object[initialCapacity + 1];
		numberOfEntries = 0;
	} // end constructor

	public void add(T newEntry) {
		add(numberOfEntries + 1, newEntry);
		/*
		 * // Alternate code list[numberOfEntries + 1] = newEntry; numberOfEntries++;
		 * ensureCapacity();
		 */
	} // end add

	// Throws an exception if the client requests a capacity that is too large.
	private void checkCapacity(int capacity) {
		if (capacity > MAX_CAPACITY)
			throw new IllegalStateException(
					"Attempt to create a list " + "whose capacity exceeds " + "allowed maximum.");
	} // end checkCapacity

	public void add(int givenPosition, T newEntry) {
		if ((givenPosition >= 1) && (givenPosition <= numberOfEntries + 1)) {
			if (givenPosition <= numberOfEntries) 
				makeRoom(givenPosition); // otherwise make room for this entry
			list[givenPosition] = newEntry;
			numberOfEntries++;
			ensureCapacity(); // Ensure enough room for next add
		} else
			throw new IndexOutOfBoundsException("Given position of add's new entry is out of bounds.");
	} // end add

	// Makes room for a new entry at newPosition.
	// Precondition: 1 <= newPosition <= numberOfEntries + 1;
	// numberOfEntries is list's length before addition;
	private void makeRoom(int givenPosition) {
		
		
		/// will be implemented
		
		
	} // end makeRoom

	// Doubles the capacity of the array list if it is full.
	private void ensureCapacity() {
		int capacity = list.length - 1;
		if (numberOfEntries >= capacity) {
			int newCapacity = 2 * capacity;
			checkCapacity(newCapacity); // Is capacity too big?
			list = Arrays.copyOf(list, newCapacity + 1);
		} // end if
	} // end ensureCapacity

	public T remove(int givenPosition) {
		if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
			// Assertion: The list is not empty
			T result = list[givenPosition]; // Get entry to be removed

			// Move subsequent entries towards entry to be removed,
			// unless it is last in list
			if (givenPosition < numberOfEntries) 
				removeGap(givenPosition);
			list[numberOfEntries] = null;
			numberOfEntries--;
			return result; // Return reference to removed entry
		} else
			throw new IndexOutOfBoundsException("Illegal position given to remove operation.");
	} // end remove

	// Shifts entries that are beyond the entry to be removed to the
	// next lower position.
	// Precondition: 1 <= givenPosition < numberOfEntries;
	// numberOfEntries is list's length before removal;
	private void removeGap(int givenPosition) {
		
		
		/// will be implemented
		
		
	} // end removeGap

	public void clear() {
		// Clear entries but retain array; no need to create a new array
		for (int index = 1; index <= numberOfEntries; index++) 
			list[index] = null;

		numberOfEntries = 0;
	} // end clear

	public T replace(int givenPosition, T newEntry) {
		if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
			// Assertion: The list is not empty
			T originalEntry = list[givenPosition];
			list[givenPosition] = newEntry;
			return originalEntry;
		} else
			throw new IndexOutOfBoundsException("Illegal position given to replace operation.");
	} // end replace

	public T getEntry(int givenPosition) {
		if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
			// Assertion: The list is not empty
			return list[givenPosition];
		} else
			throw new IndexOutOfBoundsException("Illegal position given to getEntry operation.");
	} // end getEntry

	public T[] toArray() {
		// The cast is safe because the new array contains null entries
		@SuppressWarnings("unchecked")
		T[] result = (T[]) new Object[numberOfEntries]; // Unchecked cast
		for (int index = 0; index < numberOfEntries; index++) {
			result[index] = list[index + 1];
		} // end for

		return result;
	} // end toArray

	public boolean contains(T anEntry) {
		boolean found = false;
		int index = 1;
		while (!found && (index <= numberOfEntries)) {
			if (anEntry.equals(list[index])) {
				found = true;
				break;
			}				
			index++;
		} // end while
		return found;
	} // end contains

	public int getLength() {
		return numberOfEntries;
	} // end getLength

	public boolean isEmpty() {
		return numberOfEntries == 0; // Or getLength() == 0
	} // end isEmpty
	
	public void mymethod() {
		System.out.println("Here");
	}

} // end AList
