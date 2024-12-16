import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
public class Vertex {
	private String name;
	private ArrayList<Edge> edges;
	private Vertex parent;
	private boolean visited;  
	private double cost;
	private int arrival_time;

	public Vertex(String name, int arrival_time) {
		this.arrival_time = arrival_time;
		this.name = name;
		edges = new ArrayList<Edge>();
		parent = null;
		visited = false;
		cost = Double.POSITIVE_INFINITY;
	}

	public void addEdge(Edge e) {
		edges.add(e);
	}

	public ArrayList<Edge> getEdges() {
		return this.edges;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vertex getParent() {
		return parent;
	}

	public void setParent(Vertex parent) {
		this.parent = parent;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public void visit() {
		this.visited = true;
	}

	public void unvisit() {
		this.visited = false;
	}

	public boolean isVisited() {
		return this.visited;
	}

	public int getArrival_time() {
		return arrival_time;
	}

	public void setArrival_time(int arrival_time) {
		this.arrival_time = arrival_time;
	}

	// Inside your Vertex class
	public boolean hasEdge(String destination) {
		for (Edge edge : edges) {
			if (edge.getDestination().getName().equals(destination)) {
				return true;
			}
		}
		return false;
	}


	public Iterator<Vertex> getNeighborIterator()
	{
		return new NeighborIterator();
	} // end getNeighborIterator

	public Map<Object, Object> getNeighbors() {
		return null;
	}

	private class NeighborIterator implements Iterator<Vertex>
	{
		int edgeIndex = 0;  
		private NeighborIterator()
		{
			edgeIndex = 0; 
		} // end default constructor

		public boolean hasNext()
		{
			return edgeIndex < edges.size();
		} // end hasNext

		public Vertex next()
		{
			Vertex nextNeighbor = null;

			if (hasNext())
			{
				nextNeighbor = edges.get(edgeIndex).getDestination();
				edgeIndex++;
			}
			else
				throw new NoSuchElementException();

			return nextNeighbor;
		} // end next

		public void remove()
		{
			throw new UnsupportedOperationException();
		} // end remove
	} // end NeighborIterator
}
