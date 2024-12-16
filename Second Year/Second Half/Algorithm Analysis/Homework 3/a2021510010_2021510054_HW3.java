import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class a2021510010_2021510054_HW3 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the number of judges: ");
        int judgeCount = scanner.nextInt();
        System.out.print("Please enter the cost of problem type changing: ");
        int typeChangeCost = scanner.nextInt();

        // Read problem types from a file
        List<String> types = new ArrayList<>();
        Scanner fileScanner = new Scanner(new File("input.txt"));
        fileScanner.next();

        while (fileScanner.hasNext()) {
            types.add(fileScanner.nextLine());
        }
        types.remove(0); //it will add a blank line in the ArrayList So I deleted it
        fileScanner.close();

        // Initialize structures to track judge assignments and last assigned types

        ArrayList<String>[] judges = new ArrayList[judgeCount];
        // Initialize each element in the array
        for (int i = 0; i < judgeCount; i++) {
            judges[i] = new ArrayList<>();
        }
        int totalCost =0;
        while (!types.isEmpty()){
            String currentType = types.get(0);

            if (types.size() == 1) {
                for (int i=0;i<judgeCount ;i++){
                    if (judges[i].getLast().equals(currentType) ){
                        judges[i].add(currentType);
                        types.remove(0);
                        break;
                    }
                }
                if (types.size()==1){
                    judges[0].add(currentType);
                    types.remove(0);
                    break;
                }
            } else{
                if (zeroArray(judges,judgeCount) != -1){
                    if (judges[0].size()==0){
                        judges[0].add(currentType);
                        types.remove(0);
                    }else {
                        int limit = zeroArray(judges,judgeCount);
                        boolean flag = false;
                        for (int i =0;i<limit;i++){
                            if (judges[i].getLast().equals(currentType)){
                                judges[i].add(currentType);
                                types.remove(0);
                                flag = true;
                                break;
                            }
                        }
                        if (!flag){
                            judges[limit].add(currentType);
                            types.remove(0);
                        }
                    }
                }
                else if (zeroArray(judges,judgeCount) == -1 ){
                    boolean flag = false;
                    for (int j = 0;j<judgeCount;j++){
                        if (judges[j].getLast().equals(currentType)){
                            judges[j].add(currentType);
                            types.remove(0);
                            flag = true;
                            break;
                        }
                    }
                    if (!flag){
                        types.remove(0);
                        int leastRemainedCount = 100;
                        int leastRemainedIndex = 100;

                        for (int j =0;j<judgeCount ;j++){
                            int controlCount = countOccurrences(types,judges[j].getLast());
                            if (controlCount < leastRemainedCount){
                                leastRemainedCount = controlCount;
                                leastRemainedIndex = j;
                            } else if (controlCount == leastRemainedCount) {

                                int comperisonA = types.indexOf(judges[j].getLast());
                                int comperisonB = types.indexOf(judges[leastRemainedIndex].getLast());
                                if (comperisonA > comperisonB)
                                    leastRemainedIndex =j;
                                else if (comperisonA == comperisonB){
                                    if (judges[j].size()<judges[leastRemainedIndex].size())
                                        leastRemainedIndex =j;
                                }
                            }
                        }
                        judges[leastRemainedIndex].add(currentType);
                    }
                }
            }

        }
        totalCost = (findCost(judges)+judgeCount)*typeChangeCost;
        System.out.println("Total cost : " + totalCost);
        printArrayListArray(judges);
    }
    //Runtime m.n.n which is n^3
    public static int zeroArray(ArrayList<String>[] lists,int judgeCount){
        int index =-1;
        for (int i =0;i<judgeCount;i++){
            if (lists[i].size()==0){
                index = i;
                break;
            }
        }
        return index;
    }
    public static int findCost (ArrayList<String>[] lists){
        int totalCost =0;
        for (int i = 0; i < lists.length; i++) {
            for (int j =0; j < lists[i].size()-1;j++){
                if (!lists[i].get(j).equals(lists[i].get(j+1)))
                    totalCost++;
            }
        }
        return totalCost;
    }
    public static void printArrayListArray(ArrayList<String>[] lists) {
        if (lists == null || lists.length == 0) {
            System.out.println("The array is empty or null.");
            return;
        }
        for (int i = 0; i < lists.length; i++) {
            System.out.println("Judge " + i + ": " + lists[i]);
        }
    }
    public static int countOccurrences(List<String> list, String target) {
        if (list == null || target == null) {
            return 0;
        }
        int count = 0;
        for (String s : list) {
            if (target.equals(s)) {
                count++;
            }
        }
        return count;
    }
}
