import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Iterator;
public class Main {
     public static void main(String[] args) throws FileNotFoundException {

         long startTime = System.currentTimeMillis();

         float loadFactor = 0.80f;
         HashDictionary<String,CustomerInfo> mainHash = new HashDictionary("paf","dh", loadFactor);


         String[] values = new String[4];
         int[] date = new int[3];
         try (Scanner scanner = new Scanner(new File("supermarket_dataset_50K.csv"))) {
             scanner.nextLine();
             while (scanner.hasNext()){
                 values = scanner.nextLine().split(","); //values[0] = key, values[1]= name, values[2] = date, values[3] = product
                 for(int i = 2; i >= 0; i--)
                    date[2-i] = Integer.parseInt(values[2].split("-")[i]);
                 CustomerInfo customer = new CustomerInfo(values[0],values[1]);
                 mainHash.add(values[0],customer);
                 if(mainHash.getValue(values[0]) != null)
                    mainHash.getValue(values[0]).addTransaction(new Transaction(new Date(date[2], date[1], date[0]),values[3]));
             }
         }
         long indextime = System.currentTimeMillis();
         try (Scanner scanner = new Scanner(new File("customer_1K.txt"))) {
             while (scanner.hasNext()) {
                 String scanner_key = scanner.nextLine();
                 if(mainHash.getValue(scanner_key) != null)
                    mainHash.search(scanner_key);
             }
         }
         System.out.println("Done");
         System.out.println(mainHash.collision_count);
         long finishTime = System.currentTimeMillis();
         System.out.println("Search time"+((double)(finishTime - indextime) / (double)1000));
         System.out.println("Indexing time"+((double)(indextime - startTime) / (double)1000));

     }
}