import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Main {
    public static void main_menu(DirectedGraph mainGraph) throws FileNotFoundException {

        System.out.println("please Make a Selection \n1 -> Read From .csv \n2 -> Give line");
        Scanner input = new Scanner(System.in);
        String operation = input.nextLine();
        if (operation.equals("1")){

            try (Scanner scanner = new Scanner(new File("Test100.csv"))) {
                scanner.nextLine();
                while (scanner.hasNext()){
                    String prev = scanner.nextLine();
                    String[] previous_line = new String[3];
                    previous_line = prev.split(",");
                    if (previous_line[2].equals("0")){// 0-> fewer stops
                        mainGraph.fewer_stops(previous_line[0],previous_line[1]);
                    } else if (previous_line[2].equals("1")) {
                        mainGraph.dijsktra_algorithm_search(previous_line[0],previous_line[1]);
                    }
                }
            }
            System.out.println("Any operations?");
            String asd = input.nextLine().toLowerCase();
            if (asd.equals("yes"))
                main_menu(mainGraph);

        } else if (operation.equals("2")) {
            System.out.print("Origin station: ");
            String origin = input.nextLine();
            System.out.print("\nDestination station: ");
            String destination = input.nextLine();
            System.out.println("\nPreferetion: ");
            String pref = input.nextLine().toLowerCase();
            if (pref.equals("minimum time")){
                mainGraph.dijsktra_algorithm_search(origin,destination);
            } else if (pref.equals("fewer stops")) {
                mainGraph.fewer_stops(origin,destination);
            }
            System.out.println("Any operations?");
            String asd = input.nextLine().toLowerCase();
            if (asd.equals("yes"))
                main_menu(mainGraph);
        }else {
            System.out.println("Wrong input");
        }
    }
    public static void main(String[] args) throws FileNotFoundException {
        
        DirectedGraph mainGraph = new DirectedGraph();

        try (Scanner scanner = new Scanner(new File("Paris_RER_Metro_v2.csv"))) {
            scanner.nextLine();
            String prev = scanner.nextLine();
            String[] previous_line = new String[8];
            previous_line = prev.split(",");

            while (scanner.hasNext()){
                String now = scanner.nextLine();
                String[] current_line = new String[8];
                current_line = now.split(",");
                if (previous_line[5].equals(current_line[5]) && !previous_line[1].equals(current_line[1]) && !previous_line[0].equals(current_line[0])){
                    int weight = Math.abs(Integer.parseInt(previous_line[2])-Integer.parseInt(current_line[2]));
                    mainGraph.addEdge(Integer.parseInt(previous_line[2]), previous_line[1], Integer.parseInt(current_line[2]), current_line[1],weight,current_line[5]);
                }
                previous_line = null;
                previous_line = current_line;
                current_line = null;
            }
        }
        //adding walk edges
        try (Scanner scanner = new Scanner(new File("walk_edges.txt"))){
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] line_array = new String[2];
                line_array = line.split(",");
                mainGraph.addEdge(line_array[0],line_array[1],1000,"-1"); //if it is a walk edge route short name is -1
            }
        }
        main_menu(mainGraph);

        //mainGraph.dijsktra_algorithm_search("Bérault","Ternes");
        //mainGraph.fewer_stops("Bérault","Ternes");
        //mainGraph.print_elements();
    }
}