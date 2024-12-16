import java.io.*;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {                     // 2021510010_cagri_aydin 
    public static void order_high_score_table(Stack s1,Stack s2){
        Stack s1_store = new Stack(s1.size());
        Stack s2_store = new Stack(s1.size());
        Stack name = new Stack(1);
        while (!s2.isEmpty()){
            int a = (int)s2.pop();
            name.push(s1.pop());
            while (!s2_store.isEmpty() && (int)s2_store.peek()<a){
                s2.push(s2_store.peek());
                s2_store.pop();
                s1.push(s1_store.peek());
                s1_store.pop();
            }
            s2_store.push(a);
            s1_store.push(name.pop());
        }
        //reversing stack and transferring to queue
        reverse_stack(s2_store,s2);
        reverse_stack(s1_store,s1);
    }
    public static void write_high_score_table(CircularQueue Q1,CircularQueue Q2){
        CircularQueue temp1 = new CircularQueue(Q1.size());
        CircularQueue temp2 = new CircularQueue(Q1.size());
        clone_circular_queue(Q1,temp1);
        clone_circular_queue(Q2,temp2);
        int order = 1;
        System.out.println("\n\033[0;32mHigh Score Table\033[0m");
        while(!temp1.isEmpty()){
            System.out.println(order +". "+temp1.dequeue()+" "+temp2.dequeue());
            order++;
        }
    }
    public static void write_for_first_tournament(int n,int score1,int score2,CircularQueue Q3,CircularQueue Q4,Stack S1,Stack S2){
        System.out.print("\033[0;36mPlayer 1 : \033[0m");
        write_stack_sideways(S1);
        write_score(n,S1,score1);
        System.out.print("             \033[0;33mBag 1 : \033[0m");
        write_queue_sideways(Q3);
        System.out.print("\n\033[0;36mPlayer 2 : \033[0m");
        write_stack_sideways(S2);
        write_score(n,S2,score2);
        System.out.print("             \033[0;33mBag 2 : \033[0m");
        write_queue_sideways(Q4);
    }
    public static void queue_to_queue(CircularQueue input,CircularQueue output){
        while (!input.isEmpty() )
            output.enqueue(input.dequeue());
    }
    public static void write_score(int n , Stack s1, int score){
        boolean negative = false;
        int gap = n*3;
        int size = s1.size()*3;
        gap = gap - size;
        for (int i =0;i<11+gap;i++)
            System.out.print(" ");
        if (score<0){
            negative = true;
            score= (score*-1);
        }
        if (negative){
            if (score>99)
                System.out.print("\033[0;32mScore :\033[0m -"+score);
            else if (score>9 && score<100) {
                System.out.print("\033[0;32mScore :\033[0m -"+score+" ");
            } else if (score<10 && score>=0) {
                System.out.print("\033[0;32mScore :\033[0m -"+score+"  ");
            }
        } else if (!negative) {
            if (score>99)
                System.out.print("\033[0;32mScore :\033[0m  "+score);
            else if (score>9 && score<100) {
                System.out.print("\033[0;32mScore :\033[0m  "+score+" ");
            } else if (score<10 && score>=0) {
                System.out.print("\033[0;32mScore :\033[0m  "+score+"  ");
            }
        }
        if (negative)
            score = (score *-1);
    }
    public static void write_queue_sideways(CircularQueue q1){
        if (!q1.isEmpty()){
            CircularQueue temp = new CircularQueue(q1.size());
            clone_circular_queue(q1,temp);
            while(!temp.isEmpty()){
                if ((int)temp.peek() == 0 )
                    System.out.print("A  ");
                else if ((int)temp.peek()==9)
                    System.out.print("10 ");
                else if ((int)temp.peek()==10)
                    System.out.print("J  ");
                else if ((int)temp.peek()==11)
                    System.out.print("Q  ");
                else if ((int)temp.peek()==12)
                    System.out.print("K  ");
                else {
                    System.out.print(((int)temp.peek()+1)+"  ");
                }
                temp.dequeue();
            }
        } else if (q1.isEmpty()) {
            System.out.print(" ");
        }
    }
    public static void write_stack_sideways(Stack s1){
        Stack temp = new Stack(s1.size());
        clone_stack(s1,temp);
        while(!temp.isEmpty()){
            if ((int)temp.peek() == 0 )
                System.out.print("A  ");
            else if ((int)temp.peek()==9)
                System.out.print("10 ");
            else if ((int)temp.peek()==10)
                System.out.print("J  ");
            else if ((int)temp.peek()==11)
                System.out.print("Q  ");
            else if ((int)temp.peek()==12)
                System.out.print("K  ");
            else {
                System.out.print(((int)temp.peek()+1)+"  ");
            }
            temp.pop();
        }
    }
    public static void reverse_stack_itself(Stack s1){
        Stack temp1 = new Stack(s1.size());
        Stack temp2 = new Stack(s1.size());
        while(!s1.isEmpty())
            temp1.push(s1.pop());
        while (!temp1.isEmpty())
            temp2.push(temp1.pop());
        while (!temp2.isEmpty())
            s1.push(temp2.pop());
    }
    public static void reverse_stack(Stack s1 , Stack s2){
        while(!s1.isEmpty()){
            s2.push(s1.pop());
        }
    }
    public static void clone_stack (Stack s1,Stack s2){
        Stack temp = new Stack(s1.size());
        while (!s1.isEmpty())
            temp.push(s1.pop());
        while(!temp.isEmpty()){
            s1.push(temp.peek());
            s2.push(temp.peek());
            temp.pop();
        }
    }
    public static void clone_circular_queue(CircularQueue q1,CircularQueue q2){
        CircularQueue temp = new CircularQueue(q1.size());
        while(!q1.isEmpty())
            temp.enqueue(q1.dequeue());
        while(!temp.isEmpty()){
            q1.enqueue(temp.peek());
            q2.enqueue(temp.peek());
            temp.dequeue();
        }
    }
    public static void stack_to_queue(Stack s1,CircularQueue q1){
        Stack temp = new Stack(s1.size());
        clone_stack(s1,temp);
        while(!q1.isEmpty())
            q1.dequeue();
        while (!temp.isEmpty()){
            q1.enqueue(temp.pop());
        }
    }
    public static void playing_cards_to_queue(CircularQueue q){
        for (int i =0;i<13;i++)
            q.enqueue(i);
    }
    public static void empty_stack(Stack s){
        while (!s.isEmpty())
            s.pop();
    }
    public static void order_stack(Stack s1,Stack s2){
        while(!s1.isEmpty()){
            int a = (int)s1.pop();
            while (!s2.isEmpty() && (int)s2.peek()<a){
                s1.push(s2.peek());
                s2.pop();
            }
            s2.push(a);
        }
    }
    public static void choose_different_int(Stack s1 ,Stack s2){
        Random rnd = new Random();
        //selecting different numbers between 0-12 for choosing random cards from deck
        while(!s1.isFull()){
            if (s1.isEmpty())
                s1.push(rnd.nextInt(13));
            else{
                int a = rnd.nextInt(13);
                boolean flag = false;
                while(!s1.isEmpty()) {
                    if (a != (int) s1.peek()) {
                        s2.push(s1.pop());
                    } else {
                        s2.push(s1.pop());
                        flag = true;
                    }
                }
                if (!flag)
                    s2.push(a);
                reverse_stack(s2,s1);
            }
        }
    }
    public static void main(String[] args) {
        boolean play_again = true;
        int game_counter = 0;
        while(play_again){
            game_counter++;
            Stack s1 = new Stack(12);
            Stack s2 = new Stack(12);
            Stack s1_store = new Stack(12);
            Stack s2_store = new Stack(12);
            CircularQueue Q1 = new CircularQueue(12);
            CircularQueue Q2 = new CircularQueue(12);

            int counter_12 =0;
            try {
            Scanner sc = new Scanner(new File("D:\\highscoretable.txt"));
            String[] for_reading_from_file = new String[2];
            while (sc.hasNextLine()){
                counter_12++;
                if (counter_12>12){
                    System.out.println("\033[1;91mAttention : Please correct your .txt file. Otherwise program will run with first 12 rows and delete other rows!\033[0m\n");
                    break;
                }
                for_reading_from_file = sc.nextLine().split(" ");
                s1_store.push(for_reading_from_file[0]);
                s2_store.push(Integer.parseInt(for_reading_from_file[1]));
            }
            sc.close();
            }catch (FileNotFoundException ignored){}
            reverse_stack(s1_store,s1);
            reverse_stack(s2_store,s2);

            //==============================================================================================================
            //reading value n from user
            int n =0 ;
            Scanner user_feed = new Scanner(System.in);
            boolean flag_for_n = false;
            while(!flag_for_n){
                try {
                    System.out.println("Please enter a n value between 7 and 10");
                    n = user_feed.nextInt();
                    while (n<7 || n>10){
                        System.out.println("n value must be between 7 and 10 please enter a different integer");
                        n = user_feed.nextInt();
                    }
                    flag_for_n=true;
                }catch (InputMismatchException not_a_int ){
                    System.out.println("\033[1;35mn must be an integer to operate!\033[0m");
                    n =0;
                    user_feed.next();  //clearing user_feed if I don't it will loop
                }
            }
            user_feed.nextLine();   // if I don't take nextLine() it will skip next inputs, so I am taking this from here I researched Scanner can do this sometimes

            //==============================================================================================================
            //ordering highscoretable.txt file
            order_high_score_table(s1,s2);
            stack_to_queue(s1,Q1);
            stack_to_queue(s2,Q2);

            //==============================================================================================================
            //game elements
            Stack S1 = new Stack(n);
            Stack S2 = new Stack(n);
            CircularQueue Q3 = new CircularQueue(13);
            CircularQueue Q4 = new CircularQueue(13);
            Stack random_cards_numbers = new Stack(n);
            Stack temp = new Stack(n);
            playing_cards_to_queue(Q3);

            //player1's deck
            choose_different_int(random_cards_numbers,temp); // getting n amount of different number for selecting different cards
            order_stack(random_cards_numbers,temp);          //ordering our stack
            reverse_stack(temp,S1);                          //reversing stack for getting lower to higher value cards
            reverse_stack_itself(S1);                        //changing the order

            empty_stack(random_cards_numbers);               //emptying for reuse
            empty_stack(temp);

            //player2's deck
            choose_different_int(random_cards_numbers,temp);
            order_stack(random_cards_numbers,temp);
            reverse_stack(temp,S2);
            reverse_stack_itself(S2);
            System.out.println("\033[1;92m=================================================== GAME "+game_counter+" ===================================================\033[0m\n");
            //==============================================================================================================
            //writing before start of the game
            System.out.print("\033[0;36mPlayer 1 : \033[0m");
            write_stack_sideways(S1);
            System.out.print("           \033[0;32mScore :\033[0m 0                \033[0;33mBag 1 : \033[0m");
            write_queue_sideways(Q3);
            System.out.print("\n\033[0;36mPlayer 2 : \033[0m");
            write_stack_sideways(S2);
            System.out.print("           \033[0;32mScore :\033[0m 0                \033[0;33mBag 2 : \033[0m");
            System.out.print("\n");

            //==============================================================================================================
            //making rounds
            int score1 =0;
            int score2 =0;
            CircularQueue Q3_temp =new CircularQueue(13);
            Stack cards_numbers = new Stack(13);
            Stack shuffled_cards = new Stack(13);
            Stack temp_write = new Stack(1);
            choose_different_int(cards_numbers,shuffled_cards); //shuffling deck for random cards

            System.out.println("\n\033[1;32mLottery Beggins\033[0m");

            boolean is_in_it = false;
            boolean first_tournament = false;
            int round_counter =1;
            while (!cards_numbers.isEmpty()){
                //writing rounds
                temp_write.push(cards_numbers.peek());
                System.out.print("\n\033[0;35m"+round_counter+". selected value :\033[0m");
                write_stack_sideways(temp_write);
                temp_write.pop();
                System.out.println();
                //--------------------------------------------------------------------------------
                //controlling bag 1 and transforming founding value to bag 2
                while(!Q3.isEmpty()){
                    if (Q3.peek()==cards_numbers.peek()){
                        Q4.enqueue(Q3.dequeue());
                    }else {
                        Q3_temp.enqueue(Q3.dequeue());
                    }
                }
                queue_to_queue(Q3_temp,Q3);
                //--------------------------------------------------------------------------------
                //controlling S1
                empty_stack(temp);
                while (!S1.isEmpty()){
                    if (S1.peek() == cards_numbers.peek()){
                        S1.pop();
                        score1 =score1 +10;
                        is_in_it = true;
                    }
                    else {
                        temp.push(S1.pop());
                    }
                }
                if (!is_in_it){
                    score1 = score1 - 5;
                }
                is_in_it = false;
                reverse_stack(temp,S1);
                System.out.print("\033[0;36mPlayer 1 : \033[0m");
                write_stack_sideways(S1);
                write_score(n,S1,score1);
                System.out.print("             \033[0;33mBag 1 : \033[0m");
                write_queue_sideways(Q3);
                //--------------------------------------------------------------------------------
                //controlling S2
                empty_stack(temp);
                while (!S2.isEmpty()){
                    if (S2.peek() == cards_numbers.peek()){
                        S2.pop();
                        score2 =score2 +10;
                        is_in_it = true;
                    }
                    else {
                        temp.push(S2.pop());
                    }
                }
                if (!is_in_it){
                    score2 = score2 - 5;
                }
                is_in_it = false;
                reverse_stack(temp,S2);
                System.out.print("\n\033[0;36mPlayer 2 : \033[0m");
                write_stack_sideways(S2);
                write_score(n,S2,score2);
                System.out.print("             \033[0;33mBag 2 : \033[0m");
                write_queue_sideways(Q4);

                //==============================================================================================================
                //controlling first tournament
                if (n-S1.size()==4 && n-S2.size()==4 && !first_tournament){
                    System.out.println("\n\n\033[1;32mBoth players copleted the first tournament at the same time!\033[0m");
                    score1+=15;
                    score2+=15;
                    write_for_first_tournament(n,score1,score2,Q3,Q4,S1,S2);
                    first_tournament=true;
                }
                if (n-S1.size()==4 && !first_tournament){
                    System.out.println("\n\n\033[1;32mFirst tournament is completed by \033[0;36mPlayer 1!\033[0m");
                    score1+=30;
                    write_for_first_tournament(n,score1,score2,Q3,Q4,S1,S2);
                    first_tournament=true;
                }
                if (n- S2.size()==4 && !first_tournament){
                    System.out.println("\n\n\033[1;32mFirst tournament is completed by \033[0;36mPlayer 2!\033[0m");
                    score2+=30;
                    write_for_first_tournament(n,score1,score2,Q3,Q4,S1,S2);
                    first_tournament=true;
                }
                if (S1.isEmpty() && S2.isEmpty()){
                    score1+=25;
                    score2+=25;
                    break;
                }
                if (S1.isEmpty()){
                    score1+=50;
                    break;
                }
                if (S2.isEmpty()){
                    score2+=50;
                    break;
                }
                System.out.println();
                round_counter++;
                cards_numbers.pop();
            }
            //==============================================================================================================
            System.out.println("\n\n\033[1;32mGame Over!\033[0m");
            int winner =0; // 0 = draw   1=player1   2=player2
            if (score1>score2){
                System.out.println ("\nWinner \033[0;36mPlayer 1\033[0m with " + score1 +" points");
                winner=1;
            }
            else if (score1<score2){
                System.out.println ("\nWinner \033[0;36mPlayer 2\033[0m with " + score2 +" points");
                winner=2;
            }
            else {
                System.out.println ("\nSo close! Both players have "+score1 +" score points ");
                winner=0;
            }
            //==============================================================================================================
            if (Q1.size()<12){                                              //if high score table have empty space
                int score = 0;
                if(winner==0 || winner ==1)
                    score = score1;
                else {
                    score = score2;
                }
                System.out.print("\nWhat is your name : ");
                s1.push(user_feed.nextLine().replace(" ","_"));
                s2.push(score);
                order_high_score_table(s1,s2);
                reverse_stack_itself(s1);      //reversing them because the order of same points will be reverse
                reverse_stack_itself(s2);      //reversing them because the order of same points will be reverse
                order_high_score_table(s1,s2);
                stack_to_queue(s1,Q1);
                stack_to_queue(s2,Q2);
                write_high_score_table(Q1,Q2);
            } else if (Q1.size() == 12) {                                  //if high score table have 12 records
                int score = 0;
                if(winner==0 || winner ==1)
                    score = score1;
                else {
                    score = score2;
                }
                reverse_stack_itself(s2);
                reverse_stack_itself(s1);
                if (score>=(int)s2.peek()){                 //controlling the lowest score in high score table
                    System.out.print("\nWhat is your name : ");
                    s2.pop();
                    s1.pop();
                    s1.push(user_feed.nextLine().replace(" ","_"));
                    s2.push(score);
                    order_high_score_table(s1,s2);
                    reverse_stack_itself(s1);       //reversing them because the order of same points will be reverse
                    reverse_stack_itself(s2);       //reversing them because the order of same points will be reverse
                    order_high_score_table(s1,s2);
                    stack_to_queue(s1,Q1);
                    stack_to_queue(s2,Q2);
                    write_high_score_table(Q1,Q2);
                } else if (score<(int)s2.peek()) {
                    System.out.print("\n\033[4;35mYour current point is not enough for high score table!\033[0m\n");
                    write_high_score_table(Q1,Q2);
                }
            }
            //==============================================================================================================
            try{
                PrintWriter pw = new PrintWriter("D:\\highscoretable.txt");
                pw.close();
                FileWriter fWriter = new FileWriter("D:\\highscoretable.txt");
                while(!Q1.isEmpty())
                    fWriter.write(Q1.dequeue()+" "+Q2.dequeue()+"\n");
                fWriter.close();

            }catch (IOException ignored){}
            //==============================================================================================================

            System.out.println("\nPlay again ? ( Y / N )");
            boolean repeat = true;
            while(repeat){
                Stack control = new Stack(1);
                control.push(user_feed.nextLine().toLowerCase());
                if (control.peek().equals("n")){
                    play_again=false;
                    System.out.println("\n\033[1;32mHave a nice day. Good bye.\033[0m\n");
                    repeat=false;
                } else if (control.peek().equals("y")) {
                    System.out.println("\n\033[1;32mLet's continue than!\033[0m\n");
                    repeat=false;
                }else{
                        System.out.println("\n\033[1;32mI didn't understand your comment. Can you repeat it?\033[0m\n");
                        control.pop();
                }
            }
        }
    }
}