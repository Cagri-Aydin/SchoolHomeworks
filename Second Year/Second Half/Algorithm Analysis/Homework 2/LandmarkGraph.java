//RUNTIME ANALYSIS ARE UNDER THE FUNCTIONS (Student Numbers : 2021510010 - 2021510054)
import java.util.*;
import java.io.*;
class Edge {
    String src, dest;
    double weight,travelTime;
    Edge(String src, String dest, double weight, double travelTime) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
        this.travelTime = travelTime;
    }
}

class Graph {
    Map<String, List<Edge>> adjList = new HashMap<>();
    double maxWeight = Double.MIN_VALUE;
    double Time = Double.MIN_VALUE;
    List<String> bestPath = new ArrayList<>();

    public void addEdge(String src, String dest, double weight, double travelTime) {
        if (!adjList.containsKey(src)) { //c1
            adjList.put(src, new ArrayList<>());    //c2
        }
        adjList.get(src).add(new Edge(src, dest, weight,travelTime)); //c3
    } //Run Time Analysis = c1 + c2 + c3 => O(1)

    public void findMaximumHamiltonianPath(String startVertex,int totalNumbersOfLandmarks) {
        List<String> visited = new ArrayList<>(); //c1
        visited.add(startVertex);                 //c2
        backtrackMaximumPath(visited, startVertex, 0, 0,0, totalNumbersOfLandmarks);  //c3
    } //Run Time Analysis = c1 + c2 + c3.(n-1)! => O((n-1)!)
    private void backtrackMaximumPath(List<String> currentPath, String currentVertex, double currentWeight, int count, double time, int totalNumbersOfLandmarks ) {
        if (count == totalNumbersOfLandmarks-1 && currentVertex.equals("Hotel")) {  //c1
            if (currentWeight > maxWeight) {    //c2
                maxWeight = currentWeight;      //c3
                bestPath = new ArrayList<>(currentPath);        //c4
                Time = time;    //c5
            }
            return;
        }

        List<Edge> edges = adjList.getOrDefault(currentVertex, new ArrayList<>());      //c6
        for (Edge edge : edges) {       //c7
            if (!currentPath.contains(edge.dest) || (edge.dest.equals("Hotel") && count == totalNumbersOfLandmarks-2)) {        //c8
                currentPath.add(edge.dest);     //c9
                backtrackMaximumPath(currentPath, edge.dest, currentWeight + edge.weight, count + 1,time+ edge.travelTime,totalNumbersOfLandmarks); //c10
                currentPath.remove(currentPath.size() - 1);     //c11
            }
        }
    }
    /*RUN TIME ANALYSIS
        -> For this problem we used Travelling Salesman Problem (TSP) approach but instead of finding the min weight we rearranged it to max weight Hamiltonian Cycle
        -> Run Time Consideration
            * The function's branching heavily depends on the number of edges for each vertex and how deep the recursion goes (up to totalNumbersOfLandmarks)
            * If we assume that every vertex connects to potentially every other vertex
            * The first call has up to V−1 possibilities (assuming one vertex is the starting point) and the next call has V−2 possibilities, and so on.
            * So we are creating smaller problems -for DP approach - for solving the problem but our solution is only dividing the problem by -1 itself.
            Worst-Case Complexity: O(V + E).O((V-1)!) which is V is total number of vertices and E is total number of edges.
            => O((n-1)!) ==> O(n!)

     */
    public void printBestPath() {
        if (bestPath.isEmpty()) {   //c1
            System.out.println("No valid path found."); //c2
            return;
        }
        System.out.println("Best path:"); //c3
        int index = 1; //c4
        for (String vertex : bestPath) { //c5
            System.out.println(index + ". " + vertex); //c6
            index++; //c7
        }
        System.out.println("Total attractiveness score: " + String.format("%.2f", maxWeight)); //c8
        System.out.println("Total travel time: " + String.format("%.0f", Time) + " min."); //c9
    }
} //Run Time Analysis = c1 + c2 + c3 + c4 +(n+1)c5 + n(c6 + c7) + c8 + c9 ==> O(n)


public class LandmarkGraph {
    public static void main(String[] args) {

        Graph graph = new Graph();
        Map<String, Double> interestMap = new HashMap<>();
        Map<String, Double> visitorLoadMap = new HashMap<>();
        Scanner scn = new Scanner(System.in);
        System.out.println("Please enter the total number of landmarks (including Hotel): ");
        int totalNumbersOfLandmarks = Integer.parseInt(scn.nextLine());

        // Read Personal interest and visitor load
        try {
            // Reading Personal Interest
            BufferedReader br = new BufferedReader(new FileReader("personal_interest.txt"));
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s+");
                interestMap.put(parts[0], Double.parseDouble(parts[1]));
            }
            br.close();

            // Reading Visitor Load
            br = new BufferedReader(new FileReader("visitor_load.txt"));
            line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s+");
                visitorLoadMap.put(parts[0], Double.parseDouble(parts[1]));
            }
            br.close();

            // Reading edges and creating graph
            br = new BufferedReader(new FileReader("landmark_map_data.txt"));
            line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s+");
                String from = parts[0];
                String to = parts[1];
                double baseScore = Double.parseDouble(parts[2]);
                double travelTime = Double.parseDouble(parts[3]);
                double personalInterest = interestMap.getOrDefault(to, 1.0);
                double visitorLoad = visitorLoadMap.getOrDefault(to, 0.0);
                double weight = baseScore * personalInterest * (1 - visitorLoad);
                graph.addEdge(from, to, weight, travelTime);
            }
            br.close();
            System.out.println("Three input files are read");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error occurred while reading .txt files");
        }

        System.out.println("The tour planning is now processing…");

        long startTime = System.nanoTime();
        graph.findMaximumHamiltonianPath("Hotel",totalNumbersOfLandmarks);
        long time = System.nanoTime() - startTime;

        graph.printBestPath();
        System.out.println("Total time spend while calculating path: " + time/1000000 + " ms.");
    }
}

