import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashedDictionary<K, V> implements DictionaryInterface<K, V>
{
	// The dictionary:
	private int numberOfEntries;
	private static final int DEFAULT_CAPACITY = 5;     // Must be prime
	private static final int MAX_CAPACITY = 10000;

	// The hash table:
	private HashNode<K, V>[] hashTable;
	private int tableSize;                             // Must be prime
	private static final int MAX_SIZE = 2 * MAX_CAPACITY;
	private boolean integrityOK = false;
	private static final double MAX_LOAD_FACTOR = 1; // Fraction of hash table
	// that can be filled

	public HashedDictionary()
	{
		this(DEFAULT_CAPACITY); // Call next constructor
	} // end default constructor

	public HashedDictionary(int initialCapacity)
	{
		initialCapacity = checkCapacity(initialCapacity);
		numberOfEntries = 0;    // Dictionary is empty

		// Set up hash table:
		// Initial size of hash table is same as initialCapacity if it is prime;
		// otherwise increase it until it is prime size
		int tableSize = getNextPrime(initialCapacity);
		checkSize(tableSize);   // Check that size is not too large

		// The cast is safe because the new array contains null entries
		@SuppressWarnings("unchecked")
		HashNode<K, V>[] temp = (HashNode<K, V>[])new HashNode[tableSize];
		hashTable = temp;
		integrityOK = true;
	} // end constructor

	// -------------------------
	// We've added this method to display the hash table for illustration and testing
	// -------------------------
	public void displayHashTable()
	{
		checkIntegrity();
		for (int index = 0; index < hashTable.length; index++)
		{
			if (hashTable[index] == null)
				System.out.println("null ");
			else
			{
				HashNode<K, V> current = hashTable[index];
				while(current != null)
				{
					System.out.println(current.getKey() + " " + current.getValue());
					current = current.getNext();
				}
			}
		} // end for
		System.out.println();
	} // end displayHashTable
	// -------------------------

	public V add(K key, V value)
	{
		checkIntegrity();
		if ((key == null) || (value == null))
			throw new IllegalArgumentException("Cannot add null to a dictionary.");
		else
		{
			V oldValue; 

			int index = getHashIndex(key);
			
			
			// if chain is empty, insert the first entry
			// else search given key in chain
				// if key found, replace its value
				// if key not found add at the end of the chain
			
			
			if (isHashTableTooFull())
				enlargeHashTable();

			return oldValue;
		} // end if
	} // end add

	public V remove(K key)
	{
		checkIntegrity();
		V removedValue = null;

		int index = getHashIndex(key);

		if (hashTable[index] != null)
		{
			if(hashTable[index].getKey().equals(key))
			{
				//if the key will be removed is head of chain
				removedValue = hashTable[index].getValue();
				hashTable[index] = hashTable[index].getNext();
				numberOfEntries--;
			}
			else 
			{
				HashNode<K, V> current = hashTable[index];
				HashNode<K, V> prev = current;
				while(current != null && !current.getKey().equals(key))
				{
					prev = current;
					current = current.getNext();
				}

				if(current != null)
				{
					// Key found; remove
					removedValue = current.getValue();
					prev.setNext(current.getNext());
					numberOfEntries--;
				}
				// else key not found, do nothing
			}			
		} // end if

		return removedValue;
	} // end remove

	public V getValue(K key)
	{
		checkIntegrity();
		V result = null;

		int index = getHashIndex(key);

		// if chain exist, search the key and return its value
		

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
		for (int index = 0; index < hashTable.length; index++)
			hashTable[index] = null;

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

	private int getHashIndex(K key)
	{
		int hashIndex = key.hashCode() % hashTable.length;

		if (hashIndex < 0)
		{
			hashIndex = hashIndex + hashTable.length;
		} // end if

		return hashIndex;
	} // end getHashIndex


	// Increases the size of the hash table to a prime >= twice its old size.
	// In doing so, this method must rehash the table entries.
	// Precondition: checkIntegrity has been called.
	private void enlargeHashTable()
	{
		HashNode<K, V>[] oldTable = hashTable;
		int oldSize = hashTable.length;
		int newSize = getNextPrime(oldSize + oldSize);
		checkSize(newSize);

		// The cast is safe because the new array contains null entries
		@SuppressWarnings("unchecked")
		HashNode<K, V>[] tempTable = (HashNode<K, V>[])new HashNode[newSize]; // Increase size of array
		hashTable = tempTable;
		numberOfEntries = 0; // Reset number of dictionary entries, since
		// it will be incremented by add during rehash

		// Rehash dictionary entries from old array to the new and bigger array;
		// skip both null locations and removed entries
		for (int index = 0; index < oldSize; index++)
		{
			if (oldTable[index] != null)
			{
				HashNode<K, V> current = oldTable[index];
				while(current != null)
				{
					add(current.getKey(), current.getValue());
					current = current.getNext();
				}
			}

		} // end for
	} // end enlargeHashTable

	// Returns true if lambda > MAX_LOAD_FACTOR for hash table;
	// otherwise returns false. 
	private boolean isHashTableTooFull()
	{
		return numberOfEntries > MAX_LOAD_FACTOR * hashTable.length;
	} // end isHashTableTooFull

	// Returns a prime integer that is >= the given integer.
	private int getNextPrime(int integer)
	{
		// if even, add 1 to make odd
		if (integer % 2 == 0)
		{
			integer++;
		} // end if

		// test odd integers
		while (!isPrime(integer))
		{
			integer = integer + 2;
		} // end while

		return integer;
	} // end getNextPrime

	// Returns true if the given intege is prime.
	private boolean isPrime(int integer)
	{
		boolean result;
		boolean done = false;

		// 1 and even numbers are not prime
		if ( (integer == 1) || (integer % 2 == 0) )
		{
			result = false; 
		}

		// 2 and 3 are prime
		else if ( (integer == 2) || (integer == 3) )
		{
			result = true;
		}

		else // integer is odd and >= 5
		{
			assert (integer % 2 != 0) && (integer >= 5);

			// a prime is odd and not divisible by every odd integer up to its square root
			result = true; // assume prime
			for (int divisor = 3; !done && (divisor * divisor <= integer); divisor = divisor + 2)
			{
				if (integer % divisor == 0)
				{
					result = false; // divisible; not prime
					done = true;
				} // end if
			} // end for
		} // end if

		return result;
	} // end isPrime

	// Throws an exception if this object is not initialized.
	private void checkIntegrity()
	{
		if (!integrityOK)
			throw new SecurityException ("HashedDictionary object is corrupt.");
	} // end checkIntegrity

	// Ensures that the client requests a capacity
	// that is not too small or too large.
	private int checkCapacity(int capacity)
	{
		if (capacity < DEFAULT_CAPACITY)
			capacity = DEFAULT_CAPACITY;
		else if (capacity > MAX_CAPACITY)
			throw new IllegalStateException("Attempt to create a dictionary " +
					"whose capacity is larger than " +
					MAX_CAPACITY);
		return capacity;
	} // end checkCapacity

	// Throws an exception if the hash table becomes too large.
	private void checkSize(int size)
	{
		if (tableSize > MAX_SIZE) 
			throw new IllegalStateException("Dictionary has become too large.");
	} // end checkSize

	private class KeyIterator implements Iterator<K>
	{
		private int currentIndex; // Current position in hash table
		private int numberLeft;   // Number of entries left in iteration
		private HashNode<K, V> currentNode;

		private KeyIterator() 
		{
			currentIndex = 0;
			numberLeft = numberOfEntries;
		} // end default constructor

		public boolean hasNext() 
		{
			return numberLeft > 0;
		} // end hasNext

		public K next()
		{
			K result = null;

			if (hasNext())
			{
				if(currentNode != null)
				{
					result = currentNode.getKey();
					numberLeft--;
					currentNode = currentNode.getNext();
				}
				else
				{
					// Skip table locations that do not contain a hashnode
					while (hashTable[currentIndex] == null)
					{
						currentIndex++;
					} // end while
					
					currentNode = hashTable[currentIndex];
					result = currentNode.getKey();
					numberLeft--;
					currentNode = currentNode.getNext();		
				}				
				if(currentNode == null) currentIndex++;
			}
			else
				throw new NoSuchElementException();

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
		private int numberLeft; 
		private HashNode<K, V> currentNode;

		private ValueIterator() 
		{
			currentIndex = 0;
			numberLeft = numberOfEntries;
		} // end default constructor

		public boolean hasNext() 
		{
			return numberLeft > 0; 
		} // end hasNext

		public V next()
		{
			V result = null;

			if (hasNext())
			{
				if(currentNode != null)
				{
					result = currentNode.getValue();
					numberLeft--;
					currentNode = currentNode.getNext();
				}
				else
				{
					// Skip table locations that do not contain a hashnode
					while (hashTable[currentIndex] == null)
					{
						currentIndex++;
					} // end while
					
					currentNode = hashTable[currentIndex];
					result = currentNode.getValue();
					numberLeft--;
					currentNode = currentNode.getNext();	
				}
				if(currentNode == null) currentIndex++;
			}
			else
				throw new NoSuchElementException();

			return result;
		} // end next

		public void remove()
		{
			throw new UnsupportedOperationException();
		} // end remove
	} // end ValueIterator

	public class HashNode<K,V> {
		private K key;
		private V value;
		private HashNode<K, V> next;

		public HashNode(K key, V value)
		{
			this.key=key;
			this.value=value;
		}

		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}

		public void setValue(V value) {
			this.value = value;
		}	

		public HashNode<K, V> getNext() {
			return this.next;
		}

		public void setNext(HashNode<K, V> next) {
			this.next = next;
		}	
	}
} // end HashedDictionary
