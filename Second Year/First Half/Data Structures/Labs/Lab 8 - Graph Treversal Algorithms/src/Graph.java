import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

//This class implements graph data structure by using adjacency matrix representation

public class Graph {
	private ArrayList<String> vertices; // to keep vertex names
	private int[][] adjacency; // to keep edges
	private int size;

	public Graph(int size) {
		vertices = new ArrayList<String>();
		adjacency = new int[size][size];
		this.size = size;
	}

	public void addEdge(String source, String destination, int weight) {

		//add source and destination into vertices if not exist

		//update the edge weight of corresponding cell in adjacency matrix
	}

	public int size() {
		return this.size;
	}

	public int[][] getAdjacency() {
		return adjacency;
	}

	public ArrayList<String> getVertices() {
		return vertices;
	}

	public void print() {
		for (String v : vertices) {
			System.out.print("\t(" + v + ")");
		}
		System.out.println();
		for (int i = 0; i < vertices.size(); i++) {
			System.out.print("(" + vertices.get(i) + ")\t");
			for (int j = 0; j < adjacency.length; j++) {
				System.out.print(adjacency[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println("Edges");
		int edge_count = 0;
		int total_weight = 0;
		for (int i = 0; i < vertices.size(); i++) {
			for (int j = 0; j < vertices.size(); j++) {
				if (adjacency[i][j] > 0) {
					System.out.println(vertices.get(i) + "-" + vertices.get(j) + ":" + adjacency[i][j]);
					edge_count++;
					total_weight += adjacency[i][j];
				}
			}
		}
		System.out.println("Total " + edge_count + " edges.");
		System.out.println("Total weight is " + total_weight);
		System.out.println();
	}

	public void BFS(String searched) {
		Queue<Integer> queue = new LinkedList<>();
		int root = vertices.indexOf(searched);

		queue.add(root);
		int[] visited = new int[size];
		visited[root] = 1;

		System.out.println("Breadthfirst search for " + searched + " vertex:");
		while (!queue.isEmpty()) {
			int current_vertex = queue.poll(); // the top element is selected and removed from queue
			System.out.print(vertices.get(current_vertex) + " ");

			int v;
			while ((v = unvisitedNeighbor(current_vertex, visited)) != -1) {
				queue.add(v);
				visited[v] = 1;
			}
		}
	}

	public int unvisitedNeighbor(int index, int[] visited) {
		for (int i = 0; i < adjacency.length; i++) {
			if (adjacency[index][i] != 0 && visited[i] == 0)
				return i;
		}
		return -1;
	}

	public void DFS(String searched) {
		int root = vertices.indexOf(searched);
		boolean[] visited = new boolean[size];
		
		System.out.println("Depthfirst search for " + searched + " vertex:");

		solve_DFS(root, visited);
	}
	
	public void solve_DFS(int index, boolean []visited) {
		if (!visited[index]) {
			visited[index] = true; // Mark node as "visited"
			System.out.print(vertices.get(index) + " ");

			for (int j = 0; j < adjacency[index].length; j++) {
				if (adjacency[index][j] != 0 && !visited[j]) {
					solve_DFS(j, visited); // Visit node
				}
			}
		}
	}

}
