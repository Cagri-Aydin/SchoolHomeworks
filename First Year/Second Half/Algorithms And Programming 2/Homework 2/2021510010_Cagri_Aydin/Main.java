import java.io.*;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void copyList(SingleLinkedList original, SingleLinkedList copy) {
        Node current = original.head;
        while (current != null) {
            copy.add(current.data);
            current = current.link;
        }
    }
    public static void randomly_fill_SLLs(SingleLinkedList animals, SingleLinkedList outputSll, int n){
        Random rnd = new Random();
        SingleLinkedList temp = new SingleLinkedList();
        copyList(animals,temp);
        for (int i =0;i<n;i++){
            int a = rnd.nextInt(temp.size());
            outputSll.add(temp.delete_Node(a));
        }
    }
    public static int randomly_generated_numbers(SingleLinkedList SLL1){
        Random rnd = new Random();
        return rnd.nextInt(SLL1.size());
    }
    public static void write_score(SingleLinkedList SLL1,int first_length,int gap,int score){
        int size =0;
        for (int i =0;i< SLL1.size();i++){
            size += SLL1.get_Nth_Object(i).toString().length();
        }
        size += SLL1.size();
        for (int i =0;i<gap+(first_length-size);i++)
            System.out.print(" ");
        System.out.print("Score = "+score);
    }
    public static void write_rsn(int x){
        if (x<10) {
            System.out.print((x + 1) + "   ");
        }else if (x>9&&x<100){
            System.out.print((x+1)+"  ");
        }
        else if (x>99 && x<1000) {
            System.out.print((x+1)+" ");
        }
    }
    public static void write_step(int step,int first_length,int gap){
        if ((7+first_length)>=37){
            for(int i =0;i<((first_length+7)-37)+gap;i++)
                System.out.print(" ");
            System.out.print("Step = "+step);
        } else if ((7+first_length)<37) {
            for (int i =0;i<gap-(30-first_length);i++)
                System.out.print(" ");
            System.out.print("Step = "+step);
        }
    }
    public static void order_highscore_table(SingleLinkedList tempSLL4,SingleLinkedList SLL4,SingleLinkedList tempSLL3,SingleLinkedList SLL3){
        int size = tempSLL4.size();
        for (int i =0;i<size;i++){
            int a =0;
            int current = (int)tempSLL4.get_Nth_Object(a);
            for(int k =0;k<tempSLL4.size();k++){
                if (current<(int)tempSLL4.get_Nth_Object(k)){
                    current = (int)tempSLL4.get_Nth_Object(k);
                    a=k;
                }
            }
            SLL4.add(tempSLL4.delete_Node(a));
            SLL3.add(tempSLL3.delete_Node(a));
        }
    }
    public static void write_highscore_table(SingleLinkedList SLL3,SingleLinkedList SLL4){
        System.out.print("\nHigh Score Table");
        int max_size_of_name =0;
        int max_size_of_number =0;
        int current = (int) SLL3.get_Nth_Object(0).toString().length();
        for (int i =0;i<SLL3.size();i++){
            for (int k =0;k< SLL3.size();k++){
                if (current<(int) SLL3.get_Nth_Object(k).toString().length())
                    current = (int) SLL3.get_Nth_Object(k).toString().length();
            }
        }
        max_size_of_name = current;
        int current_number = (int) SLL4.get_Nth_Object(0).toString().length();
        for (int i =0;i<SLL4.size();i++){
            for (int k =0;k< SLL4.size();k++){
                if (current_number<(int) SLL4.get_Nth_Object(k).toString().length())
                    current_number = (int) SLL4.get_Nth_Object(k).toString().length();
            }
        }
        max_size_of_number = current_number;
        for (int i =0;i<SLL3.size();i++){
            System.out.print("\n"+SLL3.get_Nth_Object(i));
            for (int k =0;k<2+(max_size_of_name-SLL3.get_Nth_Object(i).toString().length());k++)
                System.out.print(" ");
            for (int k =0;k<max_size_of_number-SLL4.get_Nth_Object(i).toString().length();k++)
                System.out.print(" ");
            System.out.print(SLL4.get_Nth_Object(i));
        }
    }
    public static void main(String[] args) {
        //==============================================================================================================
        //reading animals.txt file and writing to animals SLL
        BufferedReader reader;
        SingleLinkedList animals = new SingleLinkedList();
        try {
            reader = new BufferedReader(new FileReader("animals.txt")); //I deleted D:// part as you said in forums teacher
            String line = reader.readLine();
            while (line != null) {
                animals.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //==============================================================================================================
        //reading value n from user
        int n =0 ;
        Scanner user_feed = new Scanner(System.in);
        boolean flag_for_n = false;
        while(!flag_for_n){
            try {
                System.out.println("Please enter a n value ");
                n = user_feed.nextInt();
                while (n>animals.size()){
                    System.out.println("n value must be lower than animal count from txt file");
                    n = user_feed.nextInt();
                }
                flag_for_n=true;
            }catch (InputMismatchException not_a_int ){
                System.out.println("n must be an integer to operate!");
                n =0;
                user_feed.next();  //clearing user_feed if I don't it will loop
            }
        }
        user_feed.nextLine();   // if I don't take nextLine() it will skip next inputs, so I am taking this from here I researched Scanner can do this sometimes

        //==============================================================================================================
        //beginning of the game
        SingleLinkedList SLL1 = new SingleLinkedList();
        SingleLinkedList SLL2 = new SingleLinkedList();
        randomly_fill_SLLs(animals,SLL1,n);
        randomly_fill_SLLs(SLL1,SLL2,n);
        //==============================================================================================================
        //calculating first length of the SLL1 for writing score in same line everytime
        int score=0;
        int fisrt_length=0;
        for (int i =0;i<SLL1.size();i++){
            fisrt_length += SLL1.get_Nth_Object(i).toString().length();
        }
        fisrt_length += SLL1.size();
        System.out.print("SLL1 : ");
        SLL1.display();
        write_score(SLL1,fisrt_length,25,score);
        System.out.print("\nSLL2 : ");
        SLL2.display();
        //==============================================================================================================
        //game loop
        int step =0;
        int a = 0;
        int b = 0;
        while(SLL1.size()!=0){
            step++;
            a =randomly_generated_numbers(SLL1);
            b =randomly_generated_numbers(SLL1);
            System.out.print("\nRandomly generated numbers : ");
            write_rsn(a);
            write_rsn(b);
            write_step(step,fisrt_length,25);
            if (SLL1.get_Nth_Object(a).equals(SLL2.get_Nth_Object(b))){
                score+=20;
                SLL1.delete_Node(a);
                SLL2.delete_Node(b);
            }else{score --;}
            System.out.print("\nSLL1 : ");
            SLL1.display();
            write_score(SLL1,fisrt_length,25,score);
            System.out.print("\nSLL2 : ");
            SLL2.display();
        }
        System.out.println("\n\nThe game is over.");
        //==============================================================================================================
        //high score table
        SingleLinkedList tempSLL3 = new SingleLinkedList();
        SingleLinkedList tempSLL4 = new SingleLinkedList();
        SingleLinkedList SLL3 = new SingleLinkedList();
        SingleLinkedList SLL4 = new SingleLinkedList();
        //reading txt file
        int counter_12 =0;
        try {
            Scanner sc = new Scanner(new File("highscoretable.txt"));
            String[] for_reading_from_file = new String[2];
            while (sc.hasNextLine()){
                counter_12++;
                if (counter_12>12){
                    System.out.println("Attention : Your txt file have more than 12 rows program will take highest 12 points and will delete others!\n");
                    break;
                }
                for_reading_from_file = sc.nextLine().split(" ");
                tempSLL3.add(for_reading_from_file[0]);
                tempSLL4.add(Integer.parseInt(for_reading_from_file[1]));
            }
            sc.close();
        }catch (FileNotFoundException ignored){}

        //ordering and getting player name for high score table
        if (tempSLL3.size()<12){
            System.out.print("\nWhat is your name : ");
            tempSLL3.add(user_feed.nextLine().replace(" ","_"));
            tempSLL4.add(score);
            order_highscore_table(tempSLL4,SLL4,tempSLL3,SLL3);
            write_highscore_table(SLL3,SLL4);
        } else if (tempSLL3.size()==12) {
            order_highscore_table(tempSLL4,SLL4,tempSLL3,SLL3);
            if(score>=(int)SLL4.get_Nth_Object(11)){
                SLL3.delete_Node(11);
                SLL4.delete_Node(11);
                System.out.print("\nWhat is your name : ");
                SLL3.add(user_feed.nextLine().replace(" ","_"));
                SLL4.add(score);
                order_highscore_table(SLL4,tempSLL4,SLL3,tempSLL3);
                order_highscore_table(tempSLL4,SLL4,tempSLL3,SLL3); //reversing from tempSLL to SLLs
                write_highscore_table(SLL3,SLL4);
            }else {
                System.out.print("\nYour current point is not enough for high score table!\n");
                order_highscore_table(tempSLL4,SLL4,tempSLL3,SLL3);
                write_highscore_table(SLL3,SLL4);
            }
        }
        //writing new order into txt file
        try{
            PrintWriter pw = new PrintWriter("highscoretable.txt");
            pw.close();
            FileWriter fWriter = new FileWriter("highscoretable.txt");
            for (int i =0;i< SLL3.size();i++)
                fWriter.write(SLL3.get_Nth_Object(i)+" "+SLL4.get_Nth_Object(i)+"\n");
            fWriter.close();
        }catch (IOException ignored){}

    }
}