import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
   A class that implements the ADT dictionary by using a resizable array.
   The dictionary is unsorted and has distinct search keys.
   Search keys and associated values are not null.
 
   @author Frank M. Carrano
   @author Timothy M. Henry
   @version 5.0
*/
public class ArrayDictionary<K, V> implements DictionaryInterface<K, V>
{
	private Entry<K, V>[] dictionary; // Array of unsorted entries
	private int numberOfEntries;
   private boolean integrityOK = false;
	private final static int DEFAULT_CAPACITY = 6; // 6 is for testing
	private static final int MAX_CAPACITY = 10000;
	
	public ArrayDictionary()
	{
		this(DEFAULT_CAPACITY);        // Call next constructor
	} // end default constructor
	
	public ArrayDictionary(int initialCapacity)
	{
      checkCapacity(initialCapacity);

		// The cast is safe because the new array contains null entries
      @SuppressWarnings("unchecked")
      Entry<K, V>[] tempDictionary = (Entry<K, V>[])new Entry[initialCapacity];
      dictionary = tempDictionary;
		numberOfEntries = 0;
      integrityOK = true;
	} // end constructor

   public V add(K key, V value)
	{
		checkIntegrity();
      if ((key == null) || (value == null))
         throw new IllegalArgumentException("Cannot add null to a dictionary.");
      else
      {
         V result = null;
         int keyIndex = locateIndex(key); // key cannot be null

         if (keyIndex < numberOfEntries)
         {
            // Key found, return and replace entry's value
            result = dictionary[keyIndex].getValue(); // Get old value
            dictionary[keyIndex].setValue(value); 		// Replace value
         }
         else // Key not found; add new entry to dictionary
         {
            // Add at end of array
            dictionary[numberOfEntries] = new Entry<>(key, value);
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
   	
   	if (keyIndex < numberOfEntries)
   	{
			// Key found; remove entry and return its value
   		result = dictionary[keyIndex].getValue();
   		dictionary[keyIndex] = dictionary[numberOfEntries - 1]; // Replace removed entry with last entry
   		dictionary[numberOfEntries - 1] = null;
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

		if (keyIndex < numberOfEntries)
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

	// Returns the array index of the entry that contains key, or
   // returns numberOfEntries if no such entry exists.
   // Precondition: key is not null.
	private int locateIndex(K key)
	{
		// Sequential search
		int index = 0;
		while ( (index < numberOfEntries) && !key.equals(dictionary[index].getKey()) )
			index++;
		
		return index;
	} // end locateIndex
	
   // Doubles the size of the array dictionary if it is full.
	private void ensureCapacity() 
	{
		if (numberOfEntries >= dictionary.length)
      {
         int newCapacity = 2 * dictionary.length;
         checkCapacity(newCapacity);
         dictionary = Arrays.copyOf(dictionary, newCapacity);
      } // end if
	} // end ensureCapacity

   // Throws an exception if this object is not initialized.
   private void checkIntegrity()
   {
      if (!integrityOK)
         throw new SecurityException ("ArrayDictionary object is corrupt.");
   } // end checkIntegrity
   
   // Ensures that the client requests a capacity
   // that is not too small or too large.
   private void checkCapacity(int capacity)
   {
      if (capacity < DEFAULT_CAPACITY)
         capacity = DEFAULT_CAPACITY;
      else if (capacity > MAX_CAPACITY)
         throw new IllegalStateException("Attempt to create a dictionary " +
                                         "whose capacity is larger than " +
                                         MAX_CAPACITY);
   } // end checkCapacity

// Since iterators implement Iterator, methods must be public.
// Same as SortedArrayDictionary.
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
				Entry<K,V> currentEntry = dictionary[currentIndex];
				result = currentEntry.getKey();
				currentIndex++;
			}
			else throw new NoSuchElementException();
			
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
				Entry<K,V> currentEntry = dictionary[currentIndex];
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

	private class Entry<K, V>
	{
		private K key;
		private V value;
		
		private Entry(K searchKey, V dataValue)
		{
         key = searchKey;
         value = dataValue;
		} // end constructor
		
		private K getKey()
		{
			return key;
		} // end getKey
		
		private V getValue()
		{
			return value;
		} // end getValue
		
		private void setValue(V dataValue)
		{
		 	value = dataValue;
		} // end setValue

      // No setKey method
	} // end Entry
} // end ArrayDictionary
