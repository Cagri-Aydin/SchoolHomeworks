import java.util.*;
public class HashDictionary<K, V> implements DictionaryInterface<K, V>
{
	private Entry<K, V>[] hashTable;
	private int numberOfEntries;
	private final static int DEFAULT_CAPACITY = 5;
	private static final int MAX_CAPACITY = 10000;
	private int tableSize;
	private String hashMethod; 	//ssf or paf
	private static final int MAX_SIZE = 2 * MAX_CAPACITY;
	private boolean integrityOK = false;
	private static final double MAX_LOAD_FACTOR = 1;
	private String collisionHandling;
	public double collision_count = 0;
	private float loadFactor;

	public HashDictionary(String hashMethod,String collisionHandling ,float loadFactor)
	{
		this.hashMethod = hashMethod;
		this.collisionHandling = collisionHandling;
		this.loadFactor=loadFactor;
		checkCapacity(DEFAULT_CAPACITY);
		numberOfEntries = 0;
		this.tableSize = getNextPrime(DEFAULT_CAPACITY);
		checkSize(tableSize);   // Check that size is not too large
		integrityOK = true;
		@SuppressWarnings("unchecked")
		Entry<K, V>[] temp = (Entry<K, V>[])new Entry[tableSize];
		hashTable = temp;
	}
   public void add(K key, V value) {
		checkIntegrity();
		if ((key == null) || (value == null))
			throw new IllegalArgumentException("Cannot add null to a hashTable.");
		else
		{
			int index = getHashIndex(key, hashMethod) % tableSize;

			if ((hashTable[index] == null)){
				hashTable[index] = new Entry<>(key, value);
				numberOfEntries++;
			}
			else if(!hashTable[index].getKey().equals(key)){
				if (collisionHandling.equalsIgnoreCase("LP")) {
						while (hashTable[index] != null && !hashTable[index].getKey().equals(key)) {
							index++;
							index = index % tableSize;
							collision_count++;
						}
						if(hashTable[index] == null) {
							hashTable[index] = new Entry<>(key, value);
							numberOfEntries++;
						}
					}
				else if (collisionHandling.equalsIgnoreCase("DH")) {
						int second_add = 7 - index % 7;
						int a = 1;
						int start_index =index;
						while(hashTable[index] != null && !hashTable[index].getKey().equals(key)){
							index = (index + a * second_add) % tableSize;
							collision_count++;
							a++;
							if (start_index == index && a > 1) {
								enlargeHashTable();
								index = start_index;
								a=1;
							}
						}
						if(hashTable[index] == null)
							hashTable[index] = new Entry<>(key, value);
						numberOfEntries++;
				}
			}
			if(numberOfEntries / tableSize > loadFactor)
				enlargeHashTable();
		}
	}

   public V getValue(K key)
   {
	    checkIntegrity();

	    V result = null;

		int index = getHashIndex(key, hashMethod) % tableSize;
		if (collisionHandling.equalsIgnoreCase("LP")){
			while(hashTable[index] != null && !hashTable[index].getKey().equals(key))
				index = (index+1) % tableSize;
			try{
				return hashTable[index].getValue();
			}
			catch (NullPointerException e) {}
		}
		else if (collisionHandling.equalsIgnoreCase("DH")) {
			if(hashTable[index] != null && !hashTable[index].getKey().equals(key)){
				int a = 1;
				int second_add = 7 - index % 7;
				try{
					while (!hashTable[index].getKey().equals(key)) {
						index = (index + a * second_add) % tableSize;
						collision_count++;
						a++;
					}
					return hashTable[index].getValue();
				}
				catch (NullPointerException e){}
			}
		}
		return null;
   }

	public V search(K key){
		if(getValue(key) == null) {
			System.out.println("User not found");
			return null;
		}
		else {
			CustomerInfo c = (CustomerInfo) getValue(key);
			System.out.println(c.toString());
			return getValue(key);
		}
	}
	public void remove(K key){
		int index = getHashIndex(key, hashMethod) % tableSize;

		if(!hashTable[index].getKey().equals(key)) {
			if (collisionHandling.equalsIgnoreCase("LP")) {
				while (hashTable[index] == null && hashTable[index].getKey().equals(key)) {
					index++;
					index = index % tableSize;
				}
				if (hashTable[index] != null) {
					hashTable[index] = null;
					numberOfEntries--;
				}
			} else if (collisionHandling.equalsIgnoreCase("DH")) {
				int second_add = 7 - index % 7;
				int a = 1;
				int start_index = index;
				while (hashTable[index] == null && hashTable[index].getKey().equals(key)) {
					index = (index + a * second_add) % tableSize;
					a++;
					if (start_index == index && a > 1) {
						enlargeHashTable();
						index = start_index;
						a = 1;
					}
				}
				if (hashTable[index] != null)
					hashTable[index] = null;
				numberOfEntries--;
			}
		}
	}

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
	private void checkIntegrity()
	{
		if (!integrityOK)
			throw new SecurityException ("HashedDictionary object is corrupt.");
	}
	private int checkCapacity(int capacity){
		if (capacity < DEFAULT_CAPACITY)
			capacity = DEFAULT_CAPACITY;
		else if (capacity > MAX_CAPACITY)
			throw new IllegalStateException("Attempt to create a dictionary " +
					"whose capacity is larger than " +
					MAX_CAPACITY);
		return capacity;
	}
	private void checkSize(int size)
	{
		if (tableSize > MAX_SIZE)
			throw new IllegalStateException("hashTable has become too large.");
	}

	public final void clear()
	{
		checkIntegrity();
		// Clear entries but retain array; no need to create a new array
		for (int index = 0; index < tableSize; index++)
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

	private int getHashIndex(K key, String hashMethod)
	{
		int total_value=0;
		if(hashMethod.equalsIgnoreCase("SSF")) {
			for (int i=0;i<key.toString().length();i++) {
				if (key.toString().charAt(i) - 96 < 0 && key.toString().charAt(i) != '-')
					total_value += key.toString().charAt(i) - 48;
				else if(key.toString().charAt(i) - 96 > 0 && key.toString().charAt(i) != '-')
					total_value += key.toString().charAt(i) - 96;
			}
		}
		else if(hashMethod.equalsIgnoreCase("PAF")) {
			int z = 33;                     // must be a prime number (33, 37, 39, and 41 are particularly good choices).
			int n = key.toString().length();
			for (int i =0;i<n;i++)
				total_value += (key.toString().toLowerCase().charAt(i) - 96) * exponential(z,n-(i+1));
			if (total_value<0)
				total_value = total_value*(-1);
		}
		return total_value;
	}

	private void enlargeHashTable() {
		Entry<K, V>[] oldTable = hashTable;
		int oldSize = hashTable.length;
		int newSize = getNextPrime(2 * oldSize);
		numberOfEntries = 0;

		hashTable = (Entry<K, V>[])new Entry[newSize];

		tableSize = newSize;
		for(int i = 0; i < oldSize; i++){
			if (oldTable[i] != null){
				add(oldTable[i].getKey(), oldTable[i].getValue());
			}
		}
	}
	private int getNextPrime(int integer)
	{
		if (integer % 2 == 0)
			integer++;
		while (!isPrime(integer))
			integer = integer + 2;
		return integer;
	}
	private boolean isPrime(int integer) {
		boolean result;
		boolean done = false;

		if ((integer % 2 == 0) )
			result = false;

		else{
			assert (integer >= DEFAULT_CAPACITY);
			result = true;
			for (int divisor = 3; !done && (divisor * divisor <= integer); divisor = divisor + 2){
				if (integer % divisor == 0){
					result = false;
					done = true;
				}
			}
		}
		return result;
	}
	private static int exponential(int base,int upper){
		int value =1;
		for (int i =0;i<upper;i++)
			value = value*base;
		return value;
	}
	private class KeyIterator implements Iterator<K> {
		private int currentIndex;

		private KeyIterator()
		{
			currentIndex = 0;
		}
		public boolean hasNext(){
			return currentIndex < numberOfEntries;
		}
		public K next()
		{
			K result = null;
			if (hasNext())
			{
				Entry<K, V> currentEntry = hashTable[currentIndex];
				result = currentEntry.getKey();
				currentIndex++;
			}
			else throw new NoSuchElementException();

			return result;
		}

		public void remove(){
			throw new UnsupportedOperationException();
		}
	}

	private class ValueIterator implements Iterator<V>
	{
		private int currentIndex;

		private ValueIterator()
		{
			currentIndex = 0;
		}

		public boolean hasNext(){
			return currentIndex < numberOfEntries;
		}

		public V next()
		{
			V result;

			if (hasNext())
			{
				Entry<K, V> currentEntry = hashTable[currentIndex];
				result = currentEntry.getValue();
				currentIndex++;
			}
			else
			{
				throw new NoSuchElementException();
			}

			return result;
		}

		public void remove()
		{
			throw new UnsupportedOperationException();
		}
	}

	private class Entry<K, V> {
		private K key;
		private V value;

		private Entry(K searchKey, V dataValue)
		{
         key = searchKey;
         value = dataValue;
		}

		private K getKey()
		{
			return key;
		} // end getKey

		private V getValue()
		{
			return value;
		} // end getValue
	}
}
