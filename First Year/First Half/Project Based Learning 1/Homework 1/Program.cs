using System;

namespace archerygame_final
{
    class Program
    {
        static void Main()
        {

            Console.WriteLine("Enter a number between 10,-10 for Ax:");
            int Ax = Convert.ToInt32(Console.ReadLine());

            if ((Ax + 11) > 21 || 1 > (Ax + 11))
            {
                Console.WriteLine("Enter a valid location! Ax must be between [-10,10] ");
                Console.WriteLine("Ax : ");
                Ax = Convert.ToInt16(Console.ReadLine());
                if ((Ax+11) > 21 || 1 > (Ax + 11))
                {
                    Console.WriteLine("Please retry...");
                    return;
                }
                
            }

            Console.WriteLine("Enter a number between 10,-10 for Ay:");
            int Ay = Convert.ToInt32(Console.ReadLine());

            if ((Ay + 11) > 21 || 1 > (Ay + 11))
            {
                Console.WriteLine("Enter a valid location! Ay must be between [-10,10] ");
                Console.WriteLine("Ax : ");
                Ax = Convert.ToInt16(Console.ReadLine());
                if ((Ay + 11) > 21 || 1 > (Ay + 11))
                {
                    Console.WriteLine("Please retry...");
                    return;
                }
                
            }

            //  Creates random coordinates for B and C
            Random random = new Random();
            int Bx = random.Next(-10, 11);
            int By = random.Next(-10, 11);
            int Cx = random.Next(-10, 11);
            int Cy = random.Next(-10, 11);

            //  Calculates the distances of characters to each other
            double distanceAB = Math.Sqrt(Math.Pow((Ax - Bx), 2) + Math.Pow((-Ay - By), 2));
            double distanceAC = Math.Sqrt(Math.Pow((Ax - Cx), 2) + Math.Pow((-Ay - Cy), 2));
            double distanceBC = Math.Sqrt(Math.Pow((Bx - Cx), 2) + Math.Pow((By - Cy), 2));



            //  Recreates random coordinates for B and C if 2 characters are in the same coordinates
            //  It checks 4 times
            if (distanceAB - distanceAC == 0 || distanceAB - distanceBC == 0 || distanceAC - distanceBC == 0)
            {
                //  Recreates coordinates of B and C if there are identical distances

                Bx = random.Next(-10, 11);
                By = random.Next(-10, 11);
                Cx = random.Next(-10, 11);
                Cy = random.Next(-10, 11);

                distanceAB = Math.Sqrt(Math.Pow((Ax - Bx), 2) + Math.Pow((-Ay - By), 2));
                distanceAC = Math.Sqrt(Math.Pow((Ax - Cx), 2) + Math.Pow((-Ay - Cy), 2));
                distanceBC = Math.Sqrt(Math.Pow((Bx - Cx), 2) + Math.Pow((By - Cy), 2));
                if (distanceAB - distanceAC == 0 || distanceAB - distanceBC == 0 || distanceAC - distanceBC == 0)
                {

                    Bx = random.Next(-10, 11);
                    By = random.Next(-10, 11);
                    Cx = random.Next(-10, 11);
                    Cy = random.Next(-10, 11);

                    distanceAB = Math.Sqrt(Math.Pow((Ax - Bx), 2) + Math.Pow((-Ay - By), 2));
                    distanceAC = Math.Sqrt(Math.Pow((Ax - Cx), 2) + Math.Pow((-Ay - Cy), 2));
                    distanceBC = Math.Sqrt(Math.Pow((Bx - Cx), 2) + Math.Pow((By - Cy), 2));
                    if (distanceAB - distanceAC == 0 || distanceAB - distanceBC == 0 || distanceAC - distanceBC == 0)
                    {

                        Bx = random.Next(-10, 11);
                        By = random.Next(-10, 11);
                        Cx = random.Next(-10, 11);
                        Cy = random.Next(-10, 11);

                        distanceAB = Math.Sqrt(Math.Pow((Ax - Bx), 2) + Math.Pow((-Ay - By), 2));
                        distanceAC = Math.Sqrt(Math.Pow((Ax - Cx), 2) + Math.Pow((-Ay - Cy), 2));
                        distanceBC = Math.Sqrt(Math.Pow((Bx - Cx), 2) + Math.Pow((By - Cy), 2));
                        if (distanceAB - distanceAC == 0 || distanceAB - distanceBC == 0 || distanceAC - distanceBC == 0)
                        {
                            Console.Clear();
                            Console.ForegroundColor = ConsoleColor.Red;
                            Console.WriteLine("Please retry...");
                            Console.WriteLine("Exiting the application...");
                            Console.ResetColor();
                            return;
                        }
                    }
                }




            }

            //Players sets and healths
            int setA = 0;
            int setB = 0;
            int setC = 0;
            int hA = 0;
            int hB = 0;
            int hC = 0;
            Random random12 = new Random();
            int set = random.Next(1, 7);
            int health = random.Next(1, 7);

            switch (set)
            {
                case 1:
                    setA = 1;
                    setB = 2;
                    setC = 3;
                    break;
                case 2:
                    setA = 1;
                    setB = 3;
                    setC = 2;
                    break;
                case 3:
                    setA = 2;
                    setB = 1;
                    setC = 3;
                    break;
                case 4:
                    setA = 2;
                    setB = 3;
                    setC = 1;
                    break;
                case 5:
                    setA = 3;
                    setB = 2;
                    setC = 1;
                    break;
                case 6:
                    setA = 3;
                    setB = 1;
                    setC = 2;
                    break;

            }

            switch (health)
            {
                case 1:
                    hA = 60;
                    hB = 80;
                    hC = 100;
                    break;
                case 2:
                    hA = 60;
                    hB = 100;
                    hC = 80;
                    break;
                case 3:
                    hA = 80;
                    hB = 60;
                    hC = 100;
                    break;
                case 4:
                    hA = 80;
                    hB = 100;
                    hC = 60;
                    break;
                case 5:
                    hA = 100;
                    hB = 80;
                    hC = 60;
                    break;
                case 6:
                    hA = 100;
                    hB = 60;
                    hC = 80;
                    break;

            }


            //  Writes properties of A, B and C to screen
            Console.Write("\nA: ({0},{1})", Ax, Ay);
            Console.CursorLeft = 13;
            Console.Write("Set " + (setA));
            Console.CursorLeft = 21;
            Console.Write("Health:" + hA);

            Console.Write("\nB: ({0},{1})", Bx, By * -1);
            Console.CursorLeft = 13;
            Console.Write("Set " + (setB));
            Console.CursorLeft = 21;
            Console.Write("Health:" + hB);

            Console.Write("\nC: ({0},{1})", Cx, Cy * -1);
            Console.CursorLeft = 13;
            Console.Write("Set " + (setC));
            Console.CursorLeft = 21;
            Console.Write("Health:" + hC + "\n\n");

            int middleLineY = Console.CursorTop;

            //  Writes coordinate system to console
            Console.WriteLine("   +─────────────────────^─────────────────────+");
            Console.WriteLine(" 10│ · · · · · · · · · · │ · · · · · · · · · · │");
            Console.WriteLine("  9│ · · · · · · · · · · │ · · · · · · · · · · │");
            Console.WriteLine("  8│ · · · · · · · · · · │ · · · · · · · · · · │");
            Console.WriteLine("  7│ · · · · · · · · · · │ · · · · · · · · · · │");
            Console.WriteLine("  6│ · · · · · · · · · · │ · · · · · · · · · · │");
            Console.WriteLine("  5│ · · · · · · · · · · │ · · · · · · · · · · │");
            Console.WriteLine("  4│ · · · · · · · · · · │ · · · · · · · · · · │");
            Console.WriteLine("  3│ · · · · · · · · · · │ · · · · · · · · · · │");
            Console.WriteLine("  2│ · · · · · · · · · · │ · · · · · · · · · · │");
            Console.WriteLine("  1│ · · · · · · · · · · │ · · · · · · · · · · │");
            Console.WriteLine("  0│─────────────────────+─────────────────────>");
            Console.WriteLine(" -1│ · · · · · · · · · · │ · · · · · · · · · · │");
            Console.WriteLine(" -2│ · · · · · · · · · · │ · · · · · · · · · · │");
            Console.WriteLine(" -3│ · · · · · · · · · · │ · · · · · · · · · · │");
            Console.WriteLine(" -4│ · · · · · · · · · · │ · · · · · · · · · · │");
            Console.WriteLine(" -5│ · · · · · · · · · · │ · · · · · · · · · · │");
            Console.WriteLine(" -6│ · · · · · · · · · · │ · · · · · · · · · · │");
            Console.WriteLine(" -7│ · · · · · · · · · · │ · · · · · · · · · · │");
            Console.WriteLine(" -8│ · · · · · · · · · · │ · · · · · · · · · · │");
            Console.WriteLine(" -9│ · · · · · · · · · · │ · · · · · · · · · · │");
            Console.WriteLine("-10│ · · · · · · · · · · │ · · · · · · · · · · │");
            Console.WriteLine("   +───────────────────────────────────────────+");
            Console.WriteLine("    10 9 8 7 6 5 4 3 2 1 0 1 2 3 4 5 6 7 8 9 10");

            //  Sets current cursor position to variables
            var lastLineX = Console.CursorLeft;
            var lastLineY = Console.CursorTop;

            //  Writes A, B and C to coordinate system
            Console.SetCursorPosition(((Ax + 11) * 2) + 3, (Ay * -1) + 11 + middleLineY);
            Console.ForegroundColor = ConsoleColor.Green;
            Console.Write("A");
            Console.ResetColor();

            Console.SetCursorPosition(((Bx + 11) * 2) + 3, By + 11 + middleLineY);
            Console.ForegroundColor = ConsoleColor.Green;
            Console.Write("B");
            Console.ResetColor();

            Console.SetCursorPosition(((Cx + 11) * 2) + 3, Cy + 11 + middleLineY);
            Console.ForegroundColor = ConsoleColor.Green;
            Console.Write("C");
            Console.ResetColor();

            //  Places cursor to old position
            Console.SetCursorPosition(lastLineX, lastLineY);

            //---------------------------------------R O U N D S-------------------------------------------------

            int score = 0;
            int score2 = 0;
            bool stateA = true, stateB = true, stateC = true;

            int manhattanAB = Math.Abs(Ax - Bx) + Math.Abs((Ay) * -1 - By);
            int manhattanAC = Math.Abs(Ax - Cx) + Math.Abs((Ay) * -1 - Cy);
            int manhattanBC = Math.Abs(Bx - Cx) + Math.Abs(By - Cy);

            if (distanceAB > 15 & distanceAC > 15 & distanceBC > 15)
            {
                Console.WriteLine("-------------------------------------------------------------");
                Console.WriteLine("The distance between players is not suitable for battling");
            }

            if (distanceAB < distanceAC && distanceAB < distanceBC && distanceAB < 15)
            {
                Console.WriteLine("");
                Console.WriteLine("----------------------- Round 1 : A-B -----------------------");
                Console.WriteLine("");

                if ((setA - setB) == 1 || (setA - setB) == -2)
                {
                    score = ((manhattanAB * 10) + (100 - (hA - 25)));
                    Console.WriteLine("A: Survivor           Health:" + (hA - 25) + "           Score:" + (score));
                    Console.WriteLine("B: Defeated           Health:0 " + "           Score:0");
                    Console.WriteLine("C: Non-fighter        Health:" + (hC) + "           Score:0");
                    stateB = false;

                    if (distanceAC > 15)
                    {
                        Console.WriteLine("-------------------------------------------------------------");
                        Console.WriteLine("The distance between players is not suitable for round 2");
                        Console.WriteLine("-------------------------------------------------------------");
                        Console.WriteLine("A has the maximum score " + score);
                    }


                    if (distanceAC <= 15)
                    {
                        Console.WriteLine("----------------------- Round 2 : A-C -----------------------");
                        Console.WriteLine("");
                        score2 = ((manhattanAC * 10) + (100 - (hC - 25)));
                        Console.WriteLine("A: Defeated           Health:0 " + "           Score:" + (score));
                        Console.WriteLine("B: Defeated           Health:0 " + "           Score:0");
                        Console.WriteLine("C: Survivor           Health:" + (hC - 25) + "           Score:" + (score2));
                        stateA = false;
                        stateB = false;
                        Console.WriteLine("-------------------------------------------------------------");
                        if (score < score2)
                        {
                            Console.WriteLine("C has the maximum score " + ((score2)));

                        }
                        else if (score == score2)
                        {
                            Console.WriteLine("A and C have maximum score " + (score));
                        }
                        else
                            Console.WriteLine("A has the maximum score " + ((score)));
                    }
                }

                if ((setA - setB) == -1 || (setA - setB) == 2)
                {
                    score = ((manhattanAB * 10) + (100 - (hB - 25)));
                    Console.WriteLine("A: Defeated           Health:0 " + "           Score:0");
                    Console.WriteLine("B: Survivor           Health:" + (hB - 25) + "           Score:" + (score));
                    Console.WriteLine("C: Non-fighter        Health:" + (hC) + "           Score:0");
                    stateA = false;

                    if (distanceBC > 15)
                    {
                        Console.WriteLine("-------------------------------------------------------------");
                        Console.WriteLine("The distance between players is not suitable for round 2");
                        Console.WriteLine("-------------------------------------------------------------");
                        Console.WriteLine("B has the maximum score " + score);
                    }


                    if (distanceBC <= 15)
                    {
                        Console.WriteLine("----------------------- Round 2 : B-C -----------------------");
                        Console.WriteLine("");
                        score2 = ((manhattanBC * 10) + (100 - (hC - 25)));
                        Console.WriteLine("A: Defeated           Health:0            Score:0");
                        Console.WriteLine("B: Defeated           Health:0 " + "           Score:" + (score));
                        Console.WriteLine("C: Survivor           Health:" + (hC - 25) + "           Score:" + (score2));
                        stateA = false;
                        stateB = false;
                        Console.WriteLine("-------------------------------------------------------------");

                        if (score < score2)
                        {
                            Console.WriteLine("C has the maximum score " + ((score2)));

                        }
                        else if (score == score2)
                        {
                            Console.WriteLine("B and C have maximum score " + (score));
                        }
                        else
                            Console.WriteLine("B has the maximum score " + ((score)));

                    }
                }

            }


            if (distanceAC < distanceAB && distanceAC < distanceBC && distanceAC < 15)
            {
                Console.WriteLine("");
                Console.WriteLine("----------------------- Round 1 : A-C -----------------------");
                Console.WriteLine("");

                if ((setA - setC) == 1 || (setA - setC) == -2)
                {
                    score = ((manhattanAC * 10) + (100 - (hA - 25)));
                    Console.WriteLine("A: Survivor           Health:" + (hA - 25) + "           Score:" + (score));
                    Console.WriteLine("B: Non-fighter        Health:" + (hB) + "           Score:0");
                    Console.WriteLine("C: Defeated           Health:0            Score:0");
                    stateC = false;

                    if (distanceAB > 15)
                    {
                        Console.WriteLine("-------------------------------------------------------------");
                        Console.WriteLine("The distance between players is not suitable for round 2");
                        Console.WriteLine("-------------------------------------------------------------");
                        Console.WriteLine("A has the maximum score " + score);
                    }


                    if (distanceAB <= 15)
                    {
                        Console.WriteLine("----------------------- Round 2 : A-B -----------------------");
                        Console.WriteLine("");
                        score2 = ((manhattanAB * 10) + (100 - (hB - 25)));

                        Console.WriteLine("A: Defeated           Health:0 " + "           Score:" + (score));
                        Console.WriteLine("B: Survivor           Health:" + (hB - 25) + "           Score:" + (score2));
                        Console.WriteLine("C: Defeated           Health:0            Score:0");
                        stateA = false;
                        stateC = false;
                        Console.WriteLine("-------------------------------------------------------------");
                        if (score < score2)
                        {
                            Console.WriteLine("B has the maximum score " + ((score2)));

                        }
                        else if (score == score2)
                        {
                            Console.WriteLine("A and C have maximum score " + (score));
                        }
                        else
                            Console.WriteLine("A has the maximum score " + ((score)));

                    }
                }

                if ((setA - setC) == -1 || (setA - setC) == 2)
                {
                    score = ((manhattanAC * 10) + (100 - (hC - 25)));
                    Console.WriteLine("A: Defeated           Health:0            Score:0");
                    Console.WriteLine("B: Non-fighter        Health:" + hB + "           Score:0");
                    Console.WriteLine("C: Survivor           Health:" + (hC - 25) + "           Score:" + (score));
                    stateA = false;

                    if (distanceBC > 15)
                    {
                        Console.WriteLine("-------------------------------------------------------------");
                        Console.WriteLine("The distance between players is not suitable for round 2");
                        Console.WriteLine("-------------------------------------------------------------");
                        Console.WriteLine("C has the maximum score " + score);
                    }


                    if (distanceBC <= 15)
                    {
                        Console.WriteLine("----------------------- Round 2 : B-C -----------------------");
                        Console.WriteLine("");
                        score2 = ((manhattanBC * 10) + (100 - (hB - 25)));
                        Console.WriteLine("A: Defeated           Health:0 " + "           Score:0");
                        Console.WriteLine("B: Survivor           Health:" + (hB - 25) + "           Score:" + (score2));
                        Console.WriteLine("C: Defeated           Health:0            Score:" + (score));
                        stateA = false;
                        stateC = false;

                        Console.WriteLine("-------------------------------------------------------------");
                        if (score < score2)
                        {
                            Console.WriteLine("B has the maximum score " + ((score2)));

                        }
                        else if (score == score2)
                        {
                            Console.WriteLine("A and C have maximum score " + (score));
                        }
                        else
                            Console.WriteLine("A has the maximum score " + ((score)));

                    }
                }

            }


            if (distanceBC < distanceAB && distanceBC < distanceAC && distanceBC < 15)
            {
                Console.WriteLine("");
                Console.WriteLine("----------------------- Round 1 : B-C -----------------------");
                Console.WriteLine("");

                if ((setB - setC) == 1 || (setB - setC) == -2)
                {
                    score = ((manhattanBC * 10) + (100 - (hB - 25)));
                    Console.WriteLine("A: Non-fighter        Health:" + (hA) + "           Score:0");
                    Console.WriteLine("B: Survivor           Health:" + (hB - 25) + "           Score:" + score);
                    Console.WriteLine("C: Defeated           Health:0            Score:0");
                    stateC = false;

                    if (distanceAB > 15)
                    {
                        Console.WriteLine("-------------------------------------------------------------");
                        Console.WriteLine("The distance between players is not suitable for round 2");
                        Console.WriteLine("-------------------------------------------------------------");
                        Console.WriteLine("B has the maximum score " + score);
                    }


                    if (distanceAB <= 15)
                    {
                        Console.WriteLine("----------------------- Round 2 : A-B -----------------------");
                        Console.WriteLine("");
                        score2 = ((manhattanAB * 10) + (100 - (hA - 25)));

                        Console.WriteLine("A: Survivor           Health:" + (hA - 25) + "           Score:" + (score2));
                        Console.WriteLine("B: Defeated           Health:0 " + "           Score:" + (score));
                        Console.WriteLine("C: Defeated           Health:0            Score:0");
                        stateB = false;
                        stateC = false;

                        Console.WriteLine("-------------------------------------------------------------");
                        if (score < score2)
                        {
                            Console.WriteLine("A has the maximum score " + ((score2)));

                        }
                        else if (score == score2)
                        {
                            Console.WriteLine("A and C have maximum score " + (score));
                        }
                        else
                            Console.WriteLine("B has the maximum score " + ((score)));

                    }
                }

                if ((setB - setC) == -1 || (setB - setC) == 2)
                {
                    score = ((manhattanBC * 10) + (100 - (hC - 25)));
                    Console.WriteLine("A: Non-fighter        Health:" + (hA) + "           Score:0");
                    Console.WriteLine("B: Defeated           Health:0            Score:0");
                    Console.WriteLine("C: Survivor           Health:" + (hC - 25) + "           Score:" + score);
                    stateB = false;

                    if (distanceAC > 15)
                    {
                        Console.WriteLine("-------------------------------------------------------------");
                        Console.WriteLine("The distance between players is not suitable for round 2");
                        Console.WriteLine("-------------------------------------------------------------");
                        Console.WriteLine("C has the maximum score " + score);
                    }


                    if (distanceAC <= 15)
                    {
                        Console.WriteLine("----------------------- Round 2 : A-C -----------------------");
                        Console.WriteLine("");
                        score2 = ((manhattanAC * 10) + (100 - (hA - 25)));

                        Console.WriteLine("A: Survivor           Health:" + (hA - 25) + "           Score:" + (score2));
                        Console.WriteLine("B: Defeated           Health:0            Score:0");
                        Console.WriteLine("C: Defeated           Health:0 " + "           Score:" + (score));
                        stateB = false;
                        stateC = false;

                        Console.WriteLine("-------------------------------------------------------------");
                        if (score < score2)
                        {
                            Console.WriteLine("A has the maximum score " + ((score2)));

                        }
                        else if (score == score2)
                        {
                            Console.WriteLine("A and C have maximum score " + (score));
                        }
                        else
                            Console.WriteLine("C has the maximum score " + ((score)));

                    }
                }
            }

            lastLineY = Console.CursorTop;

            //  Updates colors of the players according to the states of them
            if (!stateA)
            {
                Console.SetCursorPosition(((Ax + 11) * 2) + 3, 11 - Ay + middleLineY);
                Console.ForegroundColor = ConsoleColor.Red;
                Console.Write("A");
                Console.ResetColor();
            }
            if (!stateB)
            {
                Console.SetCursorPosition(((Bx + 11) * 2) + 3, By + 11 + middleLineY);
                Console.ForegroundColor = ConsoleColor.Red;
                Console.Write("B");
                Console.ResetColor();
            }
            if (!stateC)
            {
                Console.SetCursorPosition(((Cx + 11) * 2) + 3, Cy + 11 + middleLineY);
                Console.ForegroundColor = ConsoleColor.Red;
                Console.Write("C");
                Console.ResetColor();
            }

            Console.CursorTop = lastLineY;

        }
    }
}   
