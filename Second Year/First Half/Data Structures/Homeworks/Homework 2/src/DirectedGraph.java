import java.util.*;
public class DirectedGraph {
	private HashMap<String, Vertex> vertices;
	public DirectedGraph() {
		this.vertices = new HashMap<>();
	}
	public void addEdge(int source_arrival, String source, int destination_arrival, String destination, int weight,String route_short_name) {
		Vertex source_v = vertices.get(source);
		Vertex destination_v = vertices.get(destination);

		if (source_v != null && destination_v != null && source_v.hasEdge(destination)) {

		}
		else
		{
			if (vertices.get(source) == null) {
				source_v = new Vertex(source, source_arrival);
				vertices.put(source, source_v);
			}
			if (vertices.get(destination) == null) {
				destination_v = new Vertex(destination, destination_arrival);
				vertices.put(destination, destination_v);
			}
			Edge edge = new Edge(source_v, destination_v, weight,route_short_name,false);
			source_v.addEdge(edge);
		} 
	}
	public void addEdge(String source, String destination, int weight,String route_short_name) {
		Vertex source_v = vertices.get(source);
		Vertex destination_v = vertices.get(destination);
		Edge edge = new Edge(source_v, destination_v, weight,route_short_name,false);
		source_v.addEdge(edge);
	}
	public void fewer_stops(String start,String end){
		resetVertices();
		Vertex start_vertex = vertices.get(start);
		Vertex end_vertex = vertices.get(end);
		Vertex current = start_vertex;

		Stack<Vertex> parent_stack = new Stack<>();
		Stack<Vertex> childs1 = new Stack<>();
		Stack<Vertex> childs2 = new Stack<>();

		childs1.push(current);

		while (!current.getName().equals(end_vertex.getName())){
			while(!childs1.isEmpty()){
				current = childs1.pop();
				current.visit();
				if (current.getName().equals(end_vertex.getName())){
					parent_stack.push(end_vertex);
					write_parents(end_vertex,parent_stack);
					break;
				}

				Iterator<Vertex> currents_neighbours = current.getNeighborIterator();
				while(currents_neighbours.hasNext()){
					Vertex neighbor = currents_neighbours.next();
					if (!neighbor.isVisited()){
						neighbor.setParent(current);
						childs2.push(neighbor);
					}
				}
			}
			while(!childs2.isEmpty()){
				childs1.push(childs2.pop());
			}
		}
		//printing stops
		int stops =0;
		while(!parent_stack.isEmpty()){
			stops++;
			System.out.print(parent_stack.pop().getName()+" -> ");
		}
		System.out.println("\nStop count : "+stops);
	}
	public static void write_parents(Vertex v,Stack<Vertex> parent_stack){
		if (v.getParent()!=null){
			parent_stack.push(v.getParent());
			write_parents(v.getParent(),parent_stack);
		}
	}
	public void print(Vertex source_v, Vertex destination_v) {
		Stack<Vertex> vertexStack = new Stack<>();
		String path = "";
		int total = (int)destination_v.getCost();
		while (destination_v.getParent() != null) {
			vertexStack.push(destination_v);
			destination_v = destination_v.getParent();
		}
		vertexStack.push(source_v);
		while (vertexStack.size() != 1)
			path += vertexStack.pop().getName() + " --> ";
		path += vertexStack.pop().getName();
		System.out.println(path);
		System.out.println((total / 60) + " min " + (total % 60) + " sec");
	}
	public void dijsktra_algorithm_search(String source, String destination){
		resetVertices();
		Vertex source_v = vertices.get(source);
		source_v.setCost(0);
		Vertex destination_v = vertices.get(destination);
		int visitedVerticesCount = 0;
		while(visitedVerticesCount != -1){
			int index = 0;
			Iterator<Vertex> neighbors = source_v.getNeighborIterator();
			while (neighbors.hasNext()){
				Vertex neighbor = neighbors.next();
				if( (source_v.getCost() + source_v.getEdges().get(index).getWeight()) < neighbor.getCost()){
					neighbor.setCost(source_v.getCost() + source_v.getEdges().get(index).getWeight());
					neighbor.setParent(source_v);
				}
				index++;
			}
			source_v.visit();
			Iterator<Vertex> iter_v = vertices().iterator();
			visitedVerticesCount = -1;
			source_v = iter_v.next();
			while (iter_v.hasNext()){
				Vertex new_source_v = iter_v.next();
				if(!new_source_v.isVisited() && new_source_v.getCost() < source_v.getCost()) {
					visitedVerticesCount++;
					source_v = new_source_v;
				}
			}
		}
		print(vertices.get(source),destination_v);
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
			v.setCost(Double.POSITIVE_INFINITY);
			v.setParent(null);
		}
	}
}
