import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class DirectedGraph {
	private HashMap<String, Vertex> vertices;

	public DirectedGraph() {
		this.vertices = new HashMap<>();
	}

	public void addEdge(String source, String destination, int weight) {

		Vertex source_v = vertices.get(source);
		Vertex destination_v = vertices.get(destination);

		if (source_v != null && destination_v != null && source_v.hasEdge(destination)) {
			System.out.println("This edge has already added!");
		}
		else
		{
			if (vertices.get(source) == null) {
				source_v = new Vertex(source);
				vertices.put(source, source_v);
			}

			if (vertices.get(destination) == null) {
				destination_v = new Vertex(destination);
				vertices.put(destination, destination_v);
			}

			Edge edge = new Edge(source_v, destination_v, weight);
			source_v.addEdge(edge);
		} 
	}

	public void print() {

		for (Vertex v : vertices.values()) {
			System.out.print(v.getName() + " -> ");
			
			Iterator<Vertex> neighbors = v.getNeighborIterator();
			while (neighbors.hasNext())
			{
				Vertex n = neighbors.next();
				System.out.print(n.getName() + " ");
			}
			System.out.println();
		}
	}

	public Iterable<Vertex> vertices() {
		return vertices.values();
	}

	public int size() {
		return vertices.size();
	}

	private void resetVertices() {
		for (Vertex v : vertices.values()) {
			v.unvisit();
			v.setCost(0);
			v.setParent(null);
		}
	}

	public Queue<String> getBreadthFirstTraversal(String origin)
	{
		resetVertices();
		Queue<String> traversalOrder = new LinkedList<>(); // Queue of vertex labels
		Queue<Vertex> vertexQueue = new LinkedList<>(); // Queue of Vertex objects

		Vertex originVertex = vertices.get(origin);
		originVertex.visit();

		traversalOrder.add(origin);    // Enqueue vertex label
		vertexQueue.add(originVertex); // Enqueue vertex

		while (!vertexQueue.isEmpty())
		{
			Vertex frontVertex = vertexQueue.remove();
			Iterator<Vertex> neighbors = frontVertex.getNeighborIterator();

			while (neighbors.hasNext())
			{
				Vertex nextNeighbor = neighbors.next();
				if (!nextNeighbor.isVisited())
				{
					nextNeighbor.visit();
					traversalOrder.add(nextNeighbor.getName());
					vertexQueue.add(nextNeighbor);
				} // end if
			} // end while
		} // end while

		return traversalOrder;
	} // end getBreadthFirstTraversal

	public Queue<String> getDepthFirstTraversal(String origin)
	{
		resetVertices();
		Queue<String> traversalOrder = new LinkedList<>();
		Stack<Vertex> vertexStack = new Stack<>();

		//will be implemented
		
		return traversalOrder;
	} // end getDepthFirstTraversal
}
