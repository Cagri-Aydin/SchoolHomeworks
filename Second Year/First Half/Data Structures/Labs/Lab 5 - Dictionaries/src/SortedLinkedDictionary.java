import java.util.Iterator;
import java.util.NoSuchElementException;

/**
   A class that implements the ADT dictionary by using a chain of linked nodes.
   The dictionary is sorted and has distinct search keys.
   Search keys and associated values are not null.
 
   @author Frank M. Carrano
   @author Timothy M. Henry
   @version 5.0
*/
public class SortedLinkedDictionary<K extends Comparable<? super K>, V> 
             implements DictionaryInterface<K, V>
{
	private Node firstNode; // Reference to first node of chain
	private int  numberOfEntries; 
	
	public SortedLinkedDictionary()
	{
      initializeDataFields();
	} // end default constructor
	
   public V add(K key, V value)
   {
      V result = null;
      if ((key == null) || (value == null))
         throw new IllegalArgumentException("Cannot add null to a dictionary.");
      else
      {
         // Search chain until you either find a node containing key
         // or locate where it should be
         Node currentNode = firstNode;
         Node nodeBefore = null;
         
         while ( (currentNode != null) && (key.compareTo(currentNode.getKey()) > 0) )
         {
            nodeBefore = currentNode;
            currentNode = currentNode.getNextNode();
         } // end while
         
         if ( (currentNode != null) && key.equals(currentNode.getKey()) )
         {
            // Key in dictionary; replace corresponding value
            result = currentNode.getValue(); // Get old value
            currentNode.setValue(value);     // Replace value
         }
         else // Key not in dictionary; add new node in proper order
         {
            // Assertion: key and value are not null
            Node newNode = new Node(key, value); // Create new node
            
            if (nodeBefore == null)
            {                                    // Add at beginning (includes empty chain)
               newNode.setNextNode(firstNode);
               firstNode = newNode;
            }
            else                                 // Add elsewhere in non-empty chain
            {
               newNode.setNextNode(currentNode); // currentNode is after new node
               nodeBefore.setNextNode(newNode);  // nodeBefore is before new node
            } // end if
            
            numberOfEntries++;                   // Increase length for both cases
         } // end if
      } // end if
      
      return result;
   } // end add
   
   public V remove(K key)
	{
   	V result = null;  // Return value
   	
		if (!isEmpty())
		{	
	      // Find node before the one that contains or could contain key
			Node currentNode = firstNode;
			Node nodeBefore = null;
			
			while ( (currentNode != null) && 
			        (key.compareTo(currentNode.getKey()) > 0) )
			{
				nodeBefore = currentNode;
				currentNode = currentNode.getNextNode();
			} // end while
			
			if ( (currentNode != null) && key.equals(currentNode.getKey()) )
			{
				Node nodeAfter = currentNode.getNextNode(); // Node after the one to be removed

				if (nodeBefore == null)
				{
					firstNode = nodeAfter;
				}
				else
				{
					nodeBefore.setNextNode(nodeAfter);       // Disconnect the node to be removed
				} // end if

				result = currentNode.getValue();            // Get ready to return removed entry
			   numberOfEntries--;                          // Decrease length for both cases
			} // end if
		} // end if
			
      return result;  
   } // end remove

   public V getValue(K key)
   {
   	V result = null;

      // Find node before the one that contains or could contain key
		Node currentNode = firstNode;
		Node nodeBefore = null;
		
		while ( (currentNode != null) && 
		        (key.compareTo(currentNode.getKey()) > 0) )
		{
			nodeBefore = currentNode;
			currentNode = currentNode.getNextNode();
		} // end while

		if ( (currentNode != null) && key.equals(currentNode.getKey()) )
		{
			result = currentNode.getValue();
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
      initializeDataFields();
   } // end clear

	public Iterator<K> getKeyIterator()
	{
		return new KeyIterator();
	} // end getKeyIterator
	
	public Iterator<V> getValueIterator()
	{
		return new ValueIterator();
	} // end getValueIterator

   // Initializes the class's data fields to indicate an empty list.
   private void initializeDataFields()
   {
		firstNode = null;
		numberOfEntries = 0;
   } // end initializeDataFields
	
// Same as in LinkedDictionary.
// Since iterators implement Iterator, methods must be public.
	private class KeyIterator implements Iterator<K>
	{
		private Node nextNode;
		
		private KeyIterator()
		{
			nextNode = firstNode;
		} // end default constructor
		
		public boolean hasNext() 
		{
			return nextNode != null;
		} // end hasNext
		
		public K next()
		{
			K result;
			
			if (hasNext())
			{
				result = nextNode.getKey();
				nextNode = nextNode.getNextNode();
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
		private Node nextNode;
		
		private ValueIterator()
		{
			nextNode = firstNode;
		} // end default constructor
		
		public boolean hasNext() 
		{
			return nextNode != null;
		} // end hasNext
		
		public V next()
		{
			V result;
			
			if (hasNext())
			{
				result = nextNode.getValue();
				nextNode = nextNode.getNextNode();
			}
			else
			{
				throw new NoSuchElementException();
			} // end if
		
			return result;
		} // end next
		
		public void remove()
		{
			throw new java.lang.UnsupportedOperationException();
		} // end remove
	} // end getValueIterator

	private class Node
	{
		private K key;
		private V value;
		private Node next;

		private Node(K searchKey, V dataValue)
		{
			key = searchKey;
			value = dataValue;
			next = null;	
		} // end constructor
		
		private Node(K searchKey, V dataValue, Node nextNode)
		{
			key = searchKey;
			value = dataValue;
			next = nextNode;	
		} // end constructor
		
		private K getKey()
		{
			return key;
		} // end getKey
		
		private V getValue()
		{
			return value;
		} // end getValue

		// no setKey!!

		private void setValue(V newValue)
		{
			value = newValue;
		} // end setValue

		private Node getNextNode()
		{
			return next;
		} // end getNextNode
		
		private void setNextNode(Node nextNode)
		{
			next = nextNode;
		} // end setNextNode
	} // end Node
} // end SortedLinkedDictionary
		
