
public class Edge {
	private Vertex source;
	private Vertex destination;
	private int weight;
	private String route_short_name;
	private boolean visited;
	
	public Edge(Vertex source, Vertex destination, int weight,String route_short_name,boolean visited) {
		this.source = source;
		this.destination = destination;
		this.weight = weight;
		this.route_short_name = route_short_name;
		this.visited=visited;
	}

	public Vertex getSource() {
		return source;
	}

	public void setSource(Vertex source) {
		this.source = source;
	}

	public Vertex getDestination() {
		return destination;
	}

	public void setDestination(Vertex destination) {
		this.destination = destination;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getRoute_short_name() {
		return route_short_name;
	}

	public void setRoute_short_name(String route_short_name) {
		this.route_short_name = route_short_name;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}
}
