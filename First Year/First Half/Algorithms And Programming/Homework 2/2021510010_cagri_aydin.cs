using System;
using System.IO;

namespace algorithm_homework_2
{
    class Program
    {
        static void Main(string[] args)
        {
            char[] key = { 'A', 'B', 'D', 'C', 'C', 'C', 'A', 'D', 'B', 'C', 'D', 'B', 'A', 'C', 'B', 'A', 'C', 'D', 'C', 'D', 'A', 'D', 'B', 'C', 'D' };
            string[] keyString = new string[25];                                                            //making a string array for key
            for (int i = 0; i < key.Length; i++)                                                            //translating char array key to string array key
            {
                keyString[i] = Convert.ToString(key[i]);
            }

            string[] candidates = File.ReadAllLines("candidates.txt");                                     //loading candidates information. I used ReadAllLines because very single line would be in a array and then I can translate it to a two dimensional array
            string[] departments = File.ReadAllLines("departments.txt");                                   //loading departments information

            string tempcandidates;                                                                          //created a temp string to convert one dimensional array to two dimensionlar array so I can reach every element of the array
            string[] TEMPcandidates = new string[32];                                                       //for writing the exam scores I created a array that contains only one more element for score
            string[,] candidatesFinal = new string[candidates.Length,TEMPcandidates.Length];                
            
            for (int j = 0; j < candidates.GetLength(0); j++)           //making one dimensional candidate array two dimensional one to accses all elements of string
            {
                tempcandidates = candidates[j];                         //for getting rid of the , translated array into a string 
                TEMPcandidates = tempcandidates.Split(',');             //then I split the string and assign it to a one dimensional array
                try
                {
                    for (int i = 0; i < TEMPcandidates.Length; i++)     
                    {
                        candidatesFinal[j, i] = TEMPcandidates[i];      //assign every element of one dimensional array to two dimensional arry for accessing all the elements of array
                    }
                }
                catch (Exception)                                       //In case of there is a corruption on candidates.txt file with using try-catch I can able to show this error message
                {
                    Console.WriteLine("There could be mistake in candidate.txt file line : "+(j+1));
                    Console.WriteLine("\nThe line =>  "+candidates[j]);
                    Console.WriteLine("\nBefore running the program please check the .txt file!");
                    System.Environment.Exit(1);
                }
            }
            
            if (candidatesFinal.GetLength(0)>40)                        //If there are more than 40 candidates program will give a error message and close
            {
                Console.WriteLine("There are more than 40 candidates please delete some candidates or create a new candidate.txt file that has lower amount of 40 or 40 candidates.");
                System.Environment.Exit(1);
            }

            string tempdepartments;                                     //created a temp string to convert one dimensional array to two dimensionlar array so I can reach every element of the array
            string[] TEMPdepartments = new string[3];
            string[,] departmentsFinal = new string[departments.Length,TEMPdepartments.Length];

            for (int i = 0; i < departments.Length; i++)
            {
                tempdepartments = departments[i];                       //for getting rid of the , translated array into a string 
                TEMPdepartments = tempdepartments.Split(',');           //then I split the string and assign it to a one dimensional array
                try
                {
                    for (int j = 0; j < TEMPdepartments.Length; j++)
                    {
                        departmentsFinal[i, j] = TEMPdepartments[j];    //assign every element of one dimensional array to two dimensional arry for accessing all the elements of array
                    }
                }
                catch (Exception)                                       //In case of there is a corruption on depatments.txt file with using try-catch I can able to show this error message
                {
                    Console.WriteLine("There could be mistake in departments.txt file line : " + (i + 1));
                    Console.WriteLine("\nThe line =>  " + departments[i]);
                    Console.WriteLine("\nBefore running the program please check the .txt file!");
                    System.Environment.Exit(1);
                }
            }
            
            for (int i = 0; i < departmentsFinal.GetLength(0); i++)     //And user can type more quota for a department so this part is chacking for it
            {
                if (Convert.ToInt16(departmentsFinal[i,2])>8 | Convert.ToInt16(departmentsFinal[i, 2]) < 0)
                {
                    Console.WriteLine("Department capacity must be between 1 to 8.");
                    Console.WriteLine("\nIn line "+(i+1)+ " of departments.txt capacity is " + (departmentsFinal[i,2])+" and this capacity is outside of the bounds.");
                    System.Environment.Exit(1);
                }
            }
            
            if (departmentsFinal.GetLength(0)>8)                        //If there are more than 8 departments program will give a error message and close
            {
                Console.WriteLine("There is more than 8 depatments please delete some departments or create a new department.txt file that has lower amount of 8 or 8 departments.");
                System.Environment.Exit(1);
            }
            //CALCULATING GRADES
            int counter = 0;
            for (int i = 0; i < candidatesFinal.GetLength(0); i++)                                      //sorting through lines
            {
                for (int j = 6; j < candidatesFinal.GetLength(1)-1; j++)                                //sorting through elements of lines
                {
                    if (candidatesFinal[i, j] == keyString[j-6])                                        //If it is matching wit keyString it will raise the counter for 4 points
                    {
                        counter = counter + 4;
                    }
                    if (candidatesFinal[i, j] == " ")                                                   //If it is emty space that means the question isn't answered so it will add nothing
                    {
                    }
                    if (candidatesFinal[i, j] != keyString[j - 6] && candidatesFinal[i, j] != " ")      //If it is not equal to keyString's element and it isn't equal to space it will lower the counter for 3 points
                    {
                        counter = counter - 3;
                    }
                    candidatesFinal[i, 31] = Convert.ToString(counter);                                 //Assigning counter to candidates row
                }
                counter = 0;                                                                            //making counter 0 to calculate other candidates scores from 0
            }
            //WRITING CANDİDATES AND THEIR SCORES
            Console.WriteLine("=====================================================================");
            Console.WriteLine("Number     Name & Surname             Grade");
            Console.WriteLine("");
            for (int k = 0; k < candidatesFinal.GetLength(0); k++)
            {
                Console.Write(candidatesFinal[k, 0]+"        "+candidatesFinal[k, 1]);
                Console.SetCursorPosition(38,k+3);
                Console.WriteLine(candidatesFinal[k, 31]); 
            }
            Console.WriteLine("=====================================================================");

            
            for (int i = 0; i < candidatesFinal.GetLength(0); i++)       //If there is a candidate who scored less than 40 point their score will turn into 0 for not including that grade to order of succsess
            {
                if (Convert.ToInt32(candidatesFinal[i,31]) < 40)
                {
                    candidatesFinal[i, 31] = Convert.ToString(0);
                }
            }
            
            //PLACEMENT LIST 
            string[,] placement = new string[departmentsFinal.GetLength(0), 11];        //creating a placement array for writing candidates that won the department
            
            for (int i = 0; i < departmentsFinal.GetLength(0); i++)                
            {
                for (int j = 0; j < 2; j++)
                {
                    placement[i, j] = departmentsFinal[i,j];
                }
            }

            string[,] orderOFsuccess = new string[candidatesFinal.GetLength(0),4];          //created a array that orders students acording to their scores and graduations points and department choises
            
            int counter_1 = 0;
            int counter_2 = 0;
            //ORDERING STUDENTS NUMBER ACORDING TO THEIR SCORES
            for (int i = 0; i < candidatesFinal.GetLength(0); i++)
            {
                try
                {
                    while (Convert.ToInt16(candidatesFinal[counter_2, 31]) == 0)      //If the candidates score is 0 it will skip that candidate
                    {
                        counter_2++;
                    }
                    if (Convert.ToInt16(candidatesFinal[counter_2, 31]) != 0)         //If candidates score isn't equal to 0 it will continiou to finding the hihest point student
                    {
                        for (int j = 0; j < candidatesFinal.GetLength(0); j++)        //for finding the highest scored candidate it will sort between all candidates 
                        {
                            if (Convert.ToInt16(candidatesFinal[counter_2, 31]) < Convert.ToInt16(candidatesFinal[j, 31]))           //If it is found hiher score it will assign this candidates line number to counter_2
                            {
                                counter_2 = j;
                            }
                            if (Convert.ToInt16(candidatesFinal[counter_2, 31]) > Convert.ToInt16(candidatesFinal[j, 31]))           //If It is lower it will do nothing
                            {
                            }
                            if (Convert.ToInt16(candidatesFinal[counter_2, 31]) == Convert.ToInt16(candidatesFinal[j, 31]))          //In case of finding a new candidate that point is equal to other canididate it will look at the graduation scores
                            {
                                if (Convert.ToInt16(candidatesFinal[counter_2, 2]) < Convert.ToInt16(candidatesFinal[j, 2]))         //In here if the graduation score is higher in nwe found candidate it will take the new found candidates line order
                                {
                                    counter_2 = j;
                                }
                                else if (Convert.ToInt16(candidatesFinal[counter_2, 2]) == Convert.ToInt16(candidatesFinal[j, 2]))   //In equality old candidate have preority to new one
                                {
                                }
                                else if (Convert.ToInt16(candidatesFinal[counter_2, 2]) > Convert.ToInt16(candidatesFinal[j, 2]))    //If It is lower it will do nothing
                                {
                                }
                            }
                        }
                    }
                    orderOFsuccess[counter_1, 0] = candidatesFinal[counter_2, 0];           //It will write candidates number to index 0
                    orderOFsuccess[counter_1, 1] = candidatesFinal[counter_2, 3];           //It will write candidetes first choice of department
                    orderOFsuccess[counter_1, 2] = candidatesFinal[counter_2, 4];           //It will write candidetes second choice of department
                    orderOFsuccess[counter_1, 3] = candidatesFinal[counter_2, 5];           //It will write candidetes third choice of department
                    candidatesFinal[counter_2, 31] = Convert.ToString(0);                   //It will asign the candidaes number to 0 because for other sorting it will inculude this candidate again if I don't assign it to 0
                    counter_1++;
                    counter_2 = 0;
                }
                catch (Exception)
                {
                }
            }
            //PLACING CANDIDATES
            int quota = 0;
            for (int i = 0; i < orderOFsuccess.GetLength(0); i++)                                           //this first loop is for student order
            {
                for (int j = 0; j < placement.GetLength(0); j++)                                            //this loop is switching departments
                {
                    quota =Convert.ToInt16(departmentsFinal[j, 2]);                                        //for reducing the complexity of code I converted department quota into a int
                    if (orderOFsuccess[i, 1] == placement[j, 0] & 0 < quota & orderOFsuccess[i, 0] != "0")  //preority for first choise
                    {
                        placement[j, quota+2] = orderOFsuccess[i, 0];                                       //placing students to their departments but this assignment will assign them in reverse order
                        departmentsFinal[j, 2] = Convert.ToString(quota - 1);                               //in case of finding the true department this will lover the quota of department
                        orderOFsuccess[i, 0] = Convert.ToString(0);                                         //student number turns into 0 for skiping it for next loops
                    }
                }
                for (int j = 0; j < placement.GetLength(0); j++)
                {
                    quota = Convert.ToInt16(departmentsFinal[j, 2]);                                        
                    if (orderOFsuccess[i, 2] == placement[j, 0] & 0 < quota & orderOFsuccess[i, 0] != "0")      //preority for second choise
                    {
                        placement[j, quota + 2] = orderOFsuccess[i, 0];                                         //placing students to their departments but this assignment will assign them in reverse order
                        departmentsFinal[j, 2] = Convert.ToString(quota - 1);                                   //in case of finding the true department this will lover the quota of department
                        orderOFsuccess[i, 0] = Convert.ToString(0);                                             //student number turns into 0 for skiping it for next loops
                    }
                }
                for (int j = 0; j < placement.GetLength(0); j++)
                {
                    quota = Convert.ToInt16(departmentsFinal[j, 2]);                                        
                    if (orderOFsuccess[i, 3] == placement[j, 0] & 0 < quota & orderOFsuccess[i, 0] != "0")      //preority for third choise
                    {
                        placement[j, quota + 2] = orderOFsuccess[i, 0];                                         //placing students to their departments but this assignment will assign them in reverse order
                        departmentsFinal[j, 2] = Convert.ToString(quota - 1);                                   //in case of finding the true department this will lover the quota of department
                        orderOFsuccess[i, 0] = Convert.ToString(0);                                             //student number turns into 0 for skiping it for next loops
                    }
                }
            }
            Console.WriteLine("No  Department                        Students");                                //writing departments and their students
            Console.WriteLine("                                      ----------------->>Higher Grade ");
            Console.WriteLine(" ");
            int k_show = 0;
            for (int i = 0; i < placement.GetLength(0); i++)                                                    
            {
                Console.Write(placement[i, 0] + "  " + placement[i, 1]);
                for (int j = 2; j < placement.GetLength(1); j++)
                {
                    Console.SetCursorPosition(34 + (k_show), 7 + candidatesFinal.GetLength(0) + i);
                    if (placement[i,j]==null)
                    {
                        if (k_show==0)
                        {
                            Console.Write("   ");
                        }
                        else
                        {
                            Console.Write("---");
                        }
                    }
                    else
                    {
                        Console.Write(placement[i, j]);
                    }
                    k_show = k_show + 4;
                }
                Console.WriteLine(" ");
                k_show = 0;
            }
            Console.WriteLine("=====================================================================");
        }
    }
}
