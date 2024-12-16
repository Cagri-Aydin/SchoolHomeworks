using System;

namespace algorithm_homework_1
{
    class Program
    {
        static void Main(string[] args)
        {
            //==============================================  GETTING AND CHECKING X's VALUE   =============================================================
            
            double x = 0;               //This is our x value it is zero for using it in try-cache
            Boolean errorX = false;     //For do-while loop I assigned a boolean it it is true it will escape from do-while loop
            
            do                          //I used do-while for looping try-catch so that way if user types anything other than intager it will loop try-catch and ask for new value 
            {
                errorX = false;         //After first loop my boolean can be true so making it false to repeat itself 
                try
                {
                    Console.WriteLine("Please enter a X value between 2 and 50: ");         //Getting x value 
                    x = Convert.ToDouble(Console.ReadLine());
                }
                catch (FormatException)     //If x's value is other than a int it will give a FormatExeption error so our catch must be FormatExeption
                {
                    Console.WriteLine("Your X value isn't a intager!");
                    Console.WriteLine(" ");
                    if (x==0)              //If x's value is other than int it will stay 0 because we assigned it 0 and it will loop again
                    {
                        errorX = true;
                    }   
                }
                if (x >= 2 & x <= 50)       //This If statement control the range of X if it is in the range loop will break
                {
                    errorX = false;
                }
                if (x < 2 | x > 50)     //This If statement control the range o X if it is in the states range loop will continue
                {
                    if (x!=0)             //And there is a case of 0 it is in the range of our if statement but I don't want to print the value of 0
                    {
                        Console.WriteLine("Your X value " + x + " isn't in the range of the pragram please enter a valid X value between 2 and 50!");
                        Console.WriteLine(" ");
                        errorX = true;
                    }
                    errorX = true;
                }
            } while (errorX==true);


            
            //==============================================  GETTING AND CHECKING Y's VALUE   =============================================================
            
            double y = 0;               //This is our y value it is zero for using it in try-cache
            Boolean errorY = false;     //For do-while loop I assigned a boolean it it is true it will escape from do-while loop

            do                          //I used do-while for looping try-catch so that way if user types anything other than intager it will loop try-catch and ask for new value 
            {
                errorY = false;         //After first loop my boolean can be true so making it false to repeat itself 
                try
                {
                    Console.WriteLine("Please enter a Y value between 25 and 30: ");      //Getting y value 
                    y = Convert.ToDouble(Console.ReadLine());
                }
                catch (FormatException)     //If y's value is other than a int it will give a FormatExeption error so our catch must be FormatExeption
                {
                    Console.WriteLine("Your Y value isn't a intager!");
                    Console.WriteLine(" ");
                    if (y == 0)            //If y's value is other than int it will stay 0 because we assigned it 0 and it will loop again
                    {
                        errorY = true;
                    }
                }
                if (y >= 25 & y <= 30)    //This If statement control the range of Y if it is in the range loop will break
                {
                    errorY = false;
                }
                if (y < 25 | y > 30)      //This If statement control the range o Y if it is in the states range loop will continue
                {
                    if (y != 0)           //And there is a case of 0 it is in the range of our if statement but I don't want to print the value of 0
                    {
                        Console.WriteLine("Your Y value " + y + " isn't in the range of the pragram please enter a valid Y value between 25 and 30!");
                        Console.WriteLine(" ");
                        errorY = true;
                    }
                    errorY = true;
                }
            } while (errorY == true);



            //===============================================  GETTING AND CHECKING OPERATOR  ==================================================================

            //Getting operant
            Console.WriteLine("Please enter a oparation between multiplication(*) or addition(+) : ");
            string operant = Convert.ToString(Console.ReadLine());
            while (operant != "*" && operant != "+")
            {
                Console.WriteLine("Your operant "+operant+" isn't awalibe for oparating please enter a oparation between multiplication(*) or addition(+) : ");
                operant = Convert.ToString(Console.ReadLine());
            }
            //============================================================================================================================================

            //adding variables for calculating denominator
            int sequence = 2;              //                                             MY FORMULA IS:
            int counter = 0;               //for calculating denominator's terms          
            double multiple = 1;           //                                                min[[(((sequence*3)-1)*x)*((((sequence+1)*3)-1)*x)] , [(y-(sequence-1)!)]]
            double total = 0;              //n th terms total for looping for cycle         ----------------------------------------------------------------------------
            double totalDENOMINATOR = 0;   //n th terms total in denominator                  (((2*(sequence+counter))-1)^sequence) + ...
            double generaltotal =0;        //overall total
            
            //============================================================================================================================================
            Console.Clear();                                                                               //I cleared console because it looks so crowded
            Console.WriteLine("Your x value is : "+x);                                                     //writing x value for user
            Console.WriteLine("Your y value is : " + y);                                                   //writing y value for user
            Console.WriteLine("Your operator is : " + operant);                                            //writing operator for user
            Console.WriteLine("Your answer is :");                                                         //writing this statement for showing the result
            //============================================================================================================================================

            if (operant == "*") //for multiplication
            {
                //first element it is special besause it breaks the possitive and negative order so I made it esspacially
                double firstx = (2 * x) * (5 * x);
                double firsty=1;
                double firsttotal = 0;
                for (double i = 1; i <= (y - (sequence - 1)); i++)  //calculating y! with for loop
                {
                    firsty = i * firsty;
                }
                
                if (firstx<=firsty)   //deciding min value of top if they are equal it will take x value
                {
                    firsttotal = firstx / 4;
                }
                if (firsty<firstx)   //deciding min value of top 
                {
                    firsttotal = firsty / 4;
                }
                //---------------------------------------------------------------------------------

                for (sequence = 2; sequence <= 25; sequence++)     //For calculating other 24 terms
                {

                
                    //calculating denominator
                    for (double i = 1; i <= (sequence+1); i++)
                    {
                        for (double k = 1; k <= sequence ; k++)
                        {
                            multiple = (2 * (sequence + counter) - 1) * multiple;
                        }
                        counter++;
                        total = multiple + total;
                        totalDENOMINATOR = total;
                        multiple = 1;
                    }
                    total = 0;
                    counter = 0;
                    

                    if (sequence%2 ==0) //if sequence equals a even number that means it is possitive 
                    {
                        //calculating x 
                        double x1 = (((sequence * 3)) - 1)*x;
                        double x2 = ((((sequence+1) * 3)) - 1)*x;
                        double xtotal = x1 * x2;
                        //calculating y
                        double ytotal=1;
                        for (double i = 1; i <= (y-(sequence-1)); i++)
                        {
                            ytotal = i * ytotal;
                        }
                        // x<y or they are equal
                        if (xtotal<=ytotal)  //If x smaller than y
                        {
                            generaltotal = (xtotal / totalDENOMINATOR) + generaltotal;
                        }
                        //y<x
                        if (ytotal<xtotal)  //If y smaller than x
                        {
                            generaltotal = (ytotal / totalDENOMINATOR) + generaltotal;
                        }
                        totalDENOMINATOR = 0;
                        
                    }
                    if (sequence%2 ==1) //if sequence equals a odd number that means it is negative
                    {
                        //calculating x 
                        double x1 = (((sequence * 3)) - 1)*x;
                        double x2 = ((((sequence + 1) * 3)) - 1)*x;
                        double xtotal = x1 * x2;
                        //calculating y
                        double ytotal = 1;
                        for (double i = 1; i <= (y - (sequence - 1)); i++)
                        {
                            ytotal = i * ytotal;
                        }
                        // x<y or they are equal
                        if (xtotal <= ytotal)  //If x smaller than y
                        {
                            generaltotal = (-1*(xtotal / totalDENOMINATOR)) + generaltotal;  //making the sequence'th term negative
                            
                        }
                        //y<x
                        if (ytotal < xtotal)  //If y smaller than x
                        {
                            generaltotal = (-1*(ytotal / totalDENOMINATOR)) + generaltotal;  //making the sequence'th term negative

                        }
                        totalDENOMINATOR = 0;
                        
                    }
                }
                Console.WriteLine(generaltotal +firsttotal);  //writing the addition of first term and other 2-25 terms

            }
            
            if (operant == "+") //for addition
            {
                //first element it is special besause it breaks the possitive and negative order
                double firstx = 2 * x + 5 * x;
                double firsty = 1;
                double firsttotal = 0;
                for (double i = 1; i <= (y - (sequence - 1)); i++)
                {
                    firsty = i * firsty;
                }

                if (firstx <= firsty) //deciding min value of top if they are equal it will take x value
                {
                    firsttotal = firstx / 4;
                }
                if (firsty < firstx)
                {
                    firsttotal = firsty / 4;
                }
                //---------------------------------------------------------------------------------

                for (sequence = 2; sequence <= 25; sequence++)
                {


                    //calculating denominator
                    for (double i = 1; i <= (sequence + 1); i++)
                    {
                        for (double k = 1; k <= sequence; k++)
                        {
                            multiple = (2 * (sequence + counter) - 1) * multiple;
                        }
                        counter++;
                        total = multiple + total;
                        totalDENOMINATOR = total;
                        multiple = 1;
                    }
                    total = 0;
                    counter = 0;


                    if (sequence % 2 == 0) //if sequence equals a even number
                    {
                        //calculating x 
                        double x1 = (((sequence * 3)) - 1) * x;
                        double x2 = ((((sequence + 1) * 3)) - 1) * x;
                        double xtotal = x1 + x2;
                        //calculating y
                        double ytotal = 1;
                        for (double i = 1; i <= (y - (sequence - 1)); i++)
                        {
                            ytotal = i * ytotal;
                        }
                        // x<y or they are equal
                        if (xtotal <= ytotal)
                        {
                            generaltotal = (xtotal / totalDENOMINATOR) + generaltotal;
                        }
                        //y<x
                        if (ytotal < xtotal)
                        {
                            generaltotal = (ytotal / totalDENOMINATOR) + generaltotal;
                        }
                        totalDENOMINATOR = 0;

                    }
                    if (sequence % 2 == 1) //if sequence equals a odd number
                    {
                        //calculating x 
                        double x1 = (((sequence * 3)) - 1) * x;
                        double x2 = ((((sequence + 1) * 3)) - 1) * x;
                        double xtotal = x1 + x2;
                        //calculating y
                        double ytotal = 1;
                        for (double i = 1; i <= (y - (sequence - 1)); i++)
                        {
                            ytotal = i * ytotal;
                        }
                        // x<y or they are equal 
                        if (xtotal <= ytotal)
                        {
                            generaltotal = (-1 * (xtotal / totalDENOMINATOR)) + generaltotal;

                        }
                        //y<x
                        if (ytotal < xtotal)
                        {
                            generaltotal = (-1 * (ytotal / totalDENOMINATOR)) + generaltotal;

                        }
                        totalDENOMINATOR = 0;

                    }
                }
                Console.WriteLine(generaltotal + firsttotal);  //writing the addition of first term and other 2-25 terms
            }
        }
    }
}
