import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class DriverAVL 
{

	public static double AVLIndexingTime = 0;
	public static double AVLSearchingTime = 0;
	public static double BSTIndexingTime = 0;
	public static double BSTSearchingTime = 0;
	
	public static void main(String[] args) 
	{
		//INDEXING
		
		SearchTreeInterface<Integer> BST = new BinarySearchTree<>();
		SearchTreeInterface<Integer> AVL = new AVLTree<>();
				
		System.out.println("Indexing into the BST");
		long StartTimer = System.nanoTime();	//Start Timer
		BufferedReader reader;
		int index = 0;
		try {
			reader = new BufferedReader(new FileReader("shuffled_numbers_100K.txt"));
			String line = reader.readLine(); 

			while (line != null) {
				BST.add(Integer.parseInt(line));
				line = reader.readLine();	
				index++;
				if(index % 10000 == 0) System.out.println(index); 
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		BSTIndexingTime = (System.nanoTime() - StartTimer)/1000000;	//Stop Timer
		System.out.println("Total BST Indexing Time: " + BSTIndexingTime + " milisecond");
		System.out.println("The Height of BST:" + BST.getHeight());
		System.out.println();
		
		System.out.println("Indexing into the AVL");
		StartTimer = System.nanoTime();	//Start Timer
		index = 0;
		try {
			reader = new BufferedReader(new FileReader("shuffled_numbers_100K.txt"));
			String line = reader.readLine(); 

			while (line != null) {
				AVL.add(Integer.parseInt(line));
				line = reader.readLine();	
				index++;
				if(index % 10000 == 0) System.out.println(index); 
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		AVLIndexingTime = (System.nanoTime() - StartTimer)/1000000;	//Stop Timer
		System.out.println("Total AVL Indexing Time: " + AVLIndexingTime + " milisecond");
		System.out.println("The Height of AVL Tree:" + AVL.getHeight());
		System.out.println();

		
		
		//SEARCHING
		
		List<Integer> searchNumbers = new ArrayList<>();
		try {
			reader = new BufferedReader(new FileReader("search_1K.txt"));
			String line = reader.readLine(); 

			while (line != null) {
				searchNumbers.add(Integer.parseInt(line));
				line = reader.readLine();						
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Searching in the BST");
		StartTimer = System.nanoTime();	//Start Timer
		int successful = 0;
		int unsuccessful = 0;
		for (Integer integer : searchNumbers) {
			Integer result = BST.getEntry(integer);
			if(result == null) unsuccessful ++;
			else successful++;
		}
		BSTSearchingTime = (System.nanoTime() - StartTimer)/1000000;	//Stop Timer
		System.out.println("Total BST Searching Time: " + BSTSearchingTime + " milisecond");
		System.out.println("Successfull search : " + successful + ", Unsuccessfull search : " + unsuccessful);
		System.out.println();
		
		System.out.println("Searching in the AVL Tree");
		StartTimer = System.nanoTime();	//Start Timer
		successful = 0;
		unsuccessful = 0;
		for (Integer integer : searchNumbers) {
			Integer result = AVL.getEntry(integer);
			if(result == null) unsuccessful ++;
			else successful++;
		}
		AVLSearchingTime = (System.nanoTime() - StartTimer)/1000000;	//Stop Timer
		System.out.println("Total AVL Searching Time: " + AVLSearchingTime + " milisecond");
		System.out.println("Successfull search : " + successful + ", Unsuccessfull search : " + unsuccessful);
		System.out.println();
		
		// Tests getInorderIterator
		// System.out.println("\nInorder Traversal");	
		// traverse(AVL);                                  

	}  // end main


	/** Displays the tree's nodes using an inorder traversal. */
	public static void traverse(SearchTreeInterface<String> bst)
	{
		Iterator<String> traverser = bst.getInorderIterator();

		while (traverser.hasNext())
			System.out.println(traverser.next());
		System.out.println();
	} // end traverse

}  // end DriverAVL

