import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
   A class that implements the ADT dictionary by using a resizable array.
   The dictionary is sorted and has distinct search keys.
   Search keys and associated values are not null.
 
   @author Frank M. Carrano
   @author Timothy M. Henry
   @version 5.0
*/
public class SortedArrayDictionary<K extends Comparable<? super K>, V>
             implements DictionaryInterface<K, V>
{
	private Entry<K, V>[] dictionary; // Array of entries sorted by search key
	private int numberOfEntries; 
   private boolean integrityOK = false;
	private final static int DEFAULT_CAPACITY = 25;  // 6 is for testing
	private static final int MAX_CAPACITY = 10000;
	
	public SortedArrayDictionary()
	{
		this(DEFAULT_CAPACITY);
	} // end default constructor
	
	public SortedArrayDictionary(int initialCapacity)
	{
      checkCapacity(initialCapacity);
      
		// The cast is safe because the new array contains null entries
      @SuppressWarnings("unchecked")
      Entry<K, V>[] tempDictionary = (Entry<K, V>[])new Entry[initialCapacity];
      dictionary = tempDictionary;
		numberOfEntries = 0;
      integrityOK = true;
	} // end constructor

	/** Precondition: key and value are not null. */
   public V add(K key, V value)
	{
		checkIntegrity();
      if ((key == null) || (value == null))
         throw new IllegalArgumentException("Cannot add null to a dictionary.");
      else
      {
         V result = null;
         int keyIndex = locateIndex(key);

         if ( (keyIndex < numberOfEntries) && 
               key.equals(dictionary[keyIndex].getKey()) )
         {
            // Key found, return and replace entry's value
            result = dictionary[keyIndex].getValue(); // Get old value
            dictionary[keyIndex].setValue(value); 		// Replace value
         }
         else // Key not found; add new entry to dictionary
         {  
            makeRoom(keyIndex);
            dictionary[keyIndex] = new Entry<>(key, value);
            numberOfEntries++;
            ensureCapacity(); // Ensure enough room for next add
         } // end if
         
         return result;
      } // end if
	} // end add

   public V remove(K key)
	{
		checkIntegrity();
   	V result = null;
   	int keyIndex = locateIndex(key);
   	
		if ( (keyIndex < numberOfEntries) && 
		      key.equals(dictionary[keyIndex].getKey()) )
   	{
			// Key found; remove entry and return its value
   		result = dictionary[keyIndex].getValue();
   		removeArrayEntry(keyIndex);
   		numberOfEntries--;
   	} // end if
      // Else result is null
   	
   	return result;
   } // end remove

   public V getValue(K key)
   {
		checkIntegrity();
   	V result = null;
   	int keyIndex = locateIndex(key);
   	
		if ( (keyIndex < numberOfEntries) && 
		      key.equals(dictionary[keyIndex].getKey()) )
   	{
     		result = dictionary[keyIndex].getValue(); // Key found; return value
   	} // end if
   	
		return result;
   } // end getValue

	public boolean contains(K key)
   {
   	return getValue(key) != null; 
   } // end contains

   public boolean isEmpty()
   {
      return numberOfEntries == 0;
   } // end isEmpty
	
   public int getSize()
   {
      return numberOfEntries;
   } // end getSize

	public final void clear()
	{
		checkIntegrity();
      
      // Clear entries but retain array; no need to create a new array
		for (int index = 0; index < numberOfEntries; index++)
			dictionary[index] = null;

      numberOfEntries = 0;
   } // end clear

	public Iterator<K> getKeyIterator()
	{
		return new KeyIterator();
	} // end getKeyIterator
	
	public Iterator<V> getValueIterator()
	{
		return new ValueIterator();
	} // end getValueIterator

	// Returns the index of either the entry that contains key or
	   // the location that should contain key, if no such entry exists.
	   private int locateIndex(K key)
	   {
	      // Search until either you find an entry containing key or
	      // discover that it doesn not exist.
	      return binarySearch(0, numberOfEntries - 1, key);
	   } // end locateIndex
	   
	   // Searches anArray[first] through anArray[last] for desiredItem.
	   // first >= 0 and < anArray.length.
	   // last >= 0 and < anArray.length.
	   private  <T extends Comparable<? super T>>
	   int binarySearch(int first, int last, K key)
	   {
	      int result;
	      int mid = first + (last - first) / 2;
	      
	      if (first > last)
	         result = first;
	      else if (key.compareTo((dictionary[mid]).getKey()) == 0)
	         result = mid;
	      else if (key.compareTo((dictionary[mid]).getKey()) < 0)
	         result = binarySearch(first, mid - 1, key);
	      else
	         result = binarySearch(mid + 1, last, key);
	      
	      return result;
	   } // end binarySearch

   // Doubles the size of the array dictionary if it is full
	private void ensureCapacity()
	{
		if (numberOfEntries == dictionary.length)
         dictionary = Arrays.copyOf(dictionary, 2 * dictionary.length);
	} // end ensureCapacity
	
   // Makes room for a new entry at a given index by shifting
   // array entries towards the end of the array.
   // Precondition: keyIndex is valid; list length is old length.
   private void makeRoom(int keyIndex)
   {
      // Move each entry to next higher position beginning at end of list
      // and continuing until item at newPosition is moved
		for (int index = numberOfEntries - 1; index >= keyIndex; index--)
		{
			dictionary[index+1] = dictionary[index]; // Shift right
		} // end for
   } // end makeRoom

	// Removes an entry at a given index by shifting array
   // entries toward the entry to be removed.
	private void removeArrayEntry(int keyIndex)
	{
	   for (int fromIndex = keyIndex+1; fromIndex < numberOfEntries; fromIndex++)
	   {
	      dictionary[fromIndex-1] = dictionary[fromIndex]; // Shift left
	   } // end for
      dictionary[numberOfEntries - 1] = null;
	}  // end removeArrayEntry

   // Throws an exception if this object is not initialized.
   private void checkIntegrity()
   {
      if (!integrityOK)
         throw new SecurityException ("ArrayDictionary object is corrupt.");
   } // end checkIntegrity
   
   // Throws an exception if the client requests a capacity that is too large.
   private void checkCapacity(int capacity)
   {
      if (capacity > MAX_CAPACITY)
         throw new IllegalStateException("Attempt to create a dictionary " +
                                         "whose capacity is larger than " +
                                         MAX_CAPACITY);
   } // end checkCapacity

// Since iterators implement Iterator, methods must be public
// Same as ArrayDictionary
	private class KeyIterator implements Iterator<K>
	{
		private int currentIndex;
		
		private KeyIterator()
		{
			currentIndex = 0;
		} // end default constructor
		
		public boolean hasNext() 
		{
			return currentIndex < numberOfEntries;
		} // end hasNext
		
		public K next()
		{
			K result = null;
			
			if (hasNext())
			{
				Entry<K, V> currentEntry = dictionary[currentIndex];
				result = currentEntry.getKey();
				currentIndex++;
			}
			else
			{
				throw new NoSuchElementException();
			} // end if
		
			return result;
		} // end next
		
		public void remove()
		{
			throw new UnsupportedOperationException();
		} // end remove
	} // end KeyIterator 
	
	private class ValueIterator implements Iterator<V>
	{
		private int currentIndex;
		
		private ValueIterator()
		{
			currentIndex = 0;
		} // end default constructor
		
		public boolean hasNext() 
		{
			return currentIndex < numberOfEntries;
		} // end hasNext
		
		public V next()
		{
			V result = null;
			
			if (hasNext())
			{
				Entry<K, V> currentEntry = dictionary[currentIndex];
				result = currentEntry.getValue();
				currentIndex++;
			}
			else
			{
				throw new NoSuchElementException();
			} // end if
		
			return result;
		} // end next
		
		public void remove()
		{
			throw new UnsupportedOperationException();
		} // end remove
	} // end getValueIterator
	
	private class Entry<S, T> 
	{
		private S key;
		private T value;
		
		private Entry(S searchKey, T dataValue)
		{
         key = searchKey;
         value = dataValue;
		} // end constructor
		
		private S getKey()
		{
			return key;
		} // end getKey
		
		private T getValue()
		{
			return value;
		} // end getValue
		
		private void setValue(T dataValue)
		{
         value = dataValue;
		} // end setValue

	// No setKey method
	} // end Entry
} // end SortedArrayDictionary
