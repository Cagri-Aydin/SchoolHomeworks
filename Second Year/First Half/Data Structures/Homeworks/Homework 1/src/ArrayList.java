public class ArrayList<T> implements ListInterface<T>
{
    private T[] list;
    private int numberOfEntries;
    private boolean initialized = false;
    private static final int DEFAULT_CAPACITY = 25;
    private static final int MAX_CAPACITY = 10000;

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }
    public ArrayList(int initialCapacity) {
        if(initialCapacity < DEFAULT_CAPACITY)
            initialCapacity = DEFAULT_CAPACITY;
        T[] templist = (T[])new Object[initialCapacity + 1];
        list = templist;
        numberOfEntries = 0;
        initialized = true;
    }
    public void clear()
    {
        for (int i = 0; i < list.length; i++)
            list[i] = null;
        numberOfEntries = 0;
    } // end clear

    public void add(T newEntry)
    {
        list[numberOfEntries] = newEntry;
        numberOfEntries++;
    }  // end add

    public T remove(int givenPosition)
    {
        T result = null;                           // Return value
        if ((givenPosition >= 1) && (givenPosition <= numberOfEntries))
        {
            result = list[givenPosition];
            list[givenPosition] = null;
            makeRoom(givenPosition);
            numberOfEntries--;
        }
        else
            throw new IndexOutOfBoundsException(
                    "Illegal position given to remove operation.");

        return result;                             // Return removed entry
    } // end remove

    private void makeRoom(int newPosition) {
        assert (newPosition >= 1) && (newPosition <= numberOfEntries + 1);
        for(int i= numberOfEntries; i >= newPosition; i--)
            list[i + 1] = list[i];
    }

    public T getEntry(int givenPosition)
    {
        if ((givenPosition >= 1) && (givenPosition <= numberOfEntries))
        {
            assert !isEmpty();
            return list[givenPosition];
        }
        else
            throw new IndexOutOfBoundsException("Illegal position given to getEntry operation.");
    } // end getEntry

    public boolean contains(T anEntry)
    {
        boolean found = false;
        int count = numberOfEntries;
        while (!found && (count > 0))
        {
            if (anEntry == list[count])
                found = true;
            else
                count--;
        } // end while
        return found;
    } // end contains
    public int getLength()
    {
        return numberOfEntries;
    } // end getLength
    public boolean isEmpty(){
        return numberOfEntries == 0;
    }

}

