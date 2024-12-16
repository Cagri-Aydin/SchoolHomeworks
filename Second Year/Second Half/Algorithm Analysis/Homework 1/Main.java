import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void fillArray(int size,String methodToFill,int[] array){
        if (methodToFill.equals("e")){

            Random rnd = new Random();
            int number = rnd.nextInt(size);
            for (int i =0;i<size;i++)
                array[i] = number;


        } else if (methodToFill.equals("r")) {

            Random rnd = new Random();
            for (int i =0;i<size;i++)
                array[i] = rnd.nextInt(size);


        }else if (methodToFill.equals("ı")){

            for (int i =0;i<size;i++)
                array[i] = i;


        } else if (methodToFill.equals("d")) {

            for (int i =0;i<size;i++)
                array[i] = (size-i-1);
        }
    }
    public static void main(String[] args) {
        int a = 0;
        Scanner scner = new Scanner(System.in);
        SortingClass sorting = new SortingClass();

        //-------------------------------------------------------------------------
        System.out.println("Enter the size of the array (1.000 - 10.000 - 100.000)");
        a = Integer.parseInt(scner.next());
        int[] arrayToSort = new int[a];
        int[] backupArray =new int[a];
        //-------------------------------------------------------------------------
        System.out.println("Chose a method to fill the array (EQUAL -E- | RANDOM -R- | INCREASING -I- | DECREASING -D- )");
        String methodToFill = scner.next().toLowerCase();


        //-------------------------------------------------------------------------
        fillArray(a,methodToFill,arrayToSort);

        for (int i=0;i<a;i++)
            backupArray[i]=arrayToSort[i];


        System.out.println("Dual Pivot Quick Sort time :");
        long startTimeDualPivot = System.nanoTime();
        sorting.dualPivotQuickSort(arrayToSort,0,a-1);
        long timeDualPivot = System.nanoTime() - startTimeDualPivot;
        System.out.println(timeDualPivot);



        System.out.println("Shell Sort time :");
        long startTimeShellSort = System.nanoTime();
        sorting.shellSort(backupArray);
        long timeShellSort = System.nanoTime() - startTimeShellSort;
        System.out.println(timeShellSort);



    }
}
class   SortingClass{
    public void dualPivotQuickSort(int[] arrayToSort, int low, int high) {
        if (low < high) {
            int[] piv = partition(arrayToSort, low, high);                       //c1
            dualPivotQuickSort(arrayToSort, low, piv[0] - 1);               //c2
            dualPivotQuickSort(arrayToSort, piv[0] + 1, piv[1] - 1);   //c3
            dualPivotQuickSort(arrayToSort, piv[1] + 1, high);              //c4
        }
    }
    static void exchange(int[] array, int a, int b) {
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    static int[] partition(int[] array, int low, int high) {
        if (array[low] > array[high]) {
            exchange(array, low, high);
        }

        int j = low + 1;
        int g = high - 1;
        int k = low + 1;
        int p = array[low];
        int q = array[high];

        while (k <= g) {                           //k1
            if (array[k] < p) {
                exchange(array, k, j);
                j++;
            } else if (array[k] >= q) {
                while (array[g] > q && k < g) {    //k2
                    g--;
                }
                exchange(array, k, g);
                g--;

                if (array[k] < p) {
                    exchange(array, k, j);
                    j++;
                }
            }
            k++;
        }
        j--;
        g++;
        exchange(array, low, j);
        exchange(array, high, g);
        return new int[]{j, g};
    }

    public void shellSort (int[] arrayToSort) {
        for (int gap = arrayToSort.length/2;gap>0;gap/=2){                //c1
            for (int i = gap;i<arrayToSort.length;i++){                   //c2
                int j =i;                                                 //c3
                while (j>=gap && arrayToSort[j]<(arrayToSort[j-gap])){    //c4
                    int temp = arrayToSort[j];                            //c5
                    arrayToSort[j] = arrayToSort[j-gap];                  //c6
                    arrayToSort[j-gap] = temp;                            //c7
                    j-=gap;                                               //c8
                }
            }
        }
    }
}