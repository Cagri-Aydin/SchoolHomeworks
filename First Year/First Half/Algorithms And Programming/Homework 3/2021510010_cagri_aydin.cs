using System;
using System.IO;
using System.Globalization;

namespace algo_homework_3
{
    class Program
    {
        static void Main(string[] args)
        {
            string[] corpus = File.ReadAllLines("corpus.txt");

            string[] questions = File.ReadAllLines("questions.txt");

            string[] stop_words = new string [] {"a", "after", "again", "all" , "am", "and", "any", "are", "as", "at", "be", "been", "before", "between", "both", "but", "by", "can", "could", "for",
            "from", "had", "has", "he", "her", "here", "him", "in", "into", "I", "is", "it", "me", "my", "of", "on", "our", "she", "so", "such", "than", "that", "the", "then", "they",
            "this", "to", "until", "we", "was", "were", "with", "you", "or","an"};


            //=====================================================================================================================================
            string tempQuestions;                                                         
            string[] TEMPquestions = new string[100];                                     
            string[,] questionsFinal = new string[questions.Length, TEMPquestions.Length];
            int counter_for_questions = 0;
            int counter_order_questions = 0;
            for (int j = 0; j < questions.GetLength(0); j++)           
            {
                tempQuestions = questions[j];                         
                TEMPquestions = tempQuestions.Split(' ');
                for (int i = 0; i < TEMPquestions.Length; i++)
                {
                    for (int k = 0; k < stop_words.Length; k++)
                    {
                        if (TEMPquestions[i] == stop_words[k])
                        {
                            counter_for_questions++;
                        }
                    }
                    if (counter_for_questions == 0)
                    {
                        questionsFinal[j, counter_order_questions] = TEMPquestions[i];
                        
                        int a = questionsFinal[j, counter_order_questions].Length;
                        if (questionsFinal[j, counter_order_questions][a - 1] == '?' || questionsFinal[j, counter_order_questions][a - 1] == ',')
                        {
                            string str = questionsFinal[j, counter_order_questions];
                            string str2 = str.Substring(0, a - 1);
                            questionsFinal[j, counter_order_questions] = str2;
                        }

                        counter_order_questions++;
                    }
                    else
                    {
                        counter_for_questions = 0;
                    }
                    
                }
                counter_order_questions = 0;
            }
            
            //=====================================================================================================================================
            string tempCorpus;
            string[] TEMPcorpus = new string[100];
            string[,] corpusFinal = new string[corpus.Length, TEMPcorpus.Length];
            int counter_for_corpus = 0;
            int counter_order_corpus = 0;
            for (int j = 0; j < corpus.GetLength(0); j++)
            {
                tempCorpus = corpus[j];
                TEMPcorpus = tempCorpus.Split(' ');
                for (int i = 0; i < TEMPcorpus.Length; i++)
                {
                    for (int k = 0; k < stop_words.Length; k++)   
                    {
                        if (TEMPcorpus[i] == stop_words[k])
                        {
                            counter_for_corpus++;
                        }
                    }
                    if (counter_for_corpus == 0)
                    {
                        corpusFinal[j, counter_order_corpus] = TEMPcorpus[i];

                        int a = corpusFinal[j, counter_order_corpus].Length;
                        if (corpusFinal[j, counter_order_corpus][a - 1] == '.' || corpusFinal[j, counter_order_corpus][a - 1] == ',')
                        {
                            string str = corpusFinal[j, counter_order_corpus];
                            string str2 = str.Substring(0, a - 1);
                            corpusFinal[j, counter_order_corpus] = str2;
                        }

                        counter_order_corpus++;
                    }
                    else
                    {
                        counter_for_corpus = 0;
                    }

                }
                counter_order_corpus = 0;
            }
            //=====================================================================================================================================
            string tempCorpus_v2;               //creating a second corpus with stop_words for only finding top10 words 
            string[] TEMPcorpus_v2 = new string[100];
            string[,] corpusFinal_v2 = new string[corpus.Length, TEMPcorpus_v2.Length];
            for (int i = 0; i < corpus.GetLength(0); i++)
            {
                tempCorpus_v2 = corpus[i];
                TEMPcorpus_v2 = tempCorpus_v2.Split(' ');
                for (int j = 0; j < TEMPcorpus_v2.Length; j++)
                {
                    if (TEMPcorpus_v2!=null)
                    {
                        corpusFinal_v2[i, j] = TEMPcorpus_v2[j];
                    }
                    int a = corpusFinal_v2[i, j].Length;
                    if (corpusFinal_v2[i, j][a - 1] == '.' || corpusFinal_v2[i, j][a - 1] == ',')
                    {
                        string str = corpusFinal_v2[i, j];
                        string str2 = str.Substring(0, a - 1);
                        corpusFinal_v2[i, j] = str2;
                    }
                }
            }
            
            //=====================================================================================================================================
            
            //making all word lower case and en-US charachters to make all words in english
            for (int i = 0; i < questionsFinal.GetLength(0); i++)                         
            {
                for (int k = 0; k < questionsFinal.GetLength(1); k++)
                {
                    if (questionsFinal[i,k]!= null)
                    {
                        string str1 = questionsFinal[i, k];
                        str1 = str1.ToLower(new CultureInfo("en-US", false));
                        questionsFinal[i, k] = str1;
                    }
                }
            }
            for (int i = 0; i < corpusFinal.GetLength(0); i++)
            {
                for (int k = 0; k < corpusFinal.GetLength(1); k++)
                {
                    if (corpusFinal[i,k]!= null)
                    {
                        string str1 = corpusFinal[i, k];
                        str1 = str1.ToLower(new CultureInfo("en-US", false));
                        corpusFinal[i, k] = str1;
                    }
                }
            }
            for (int i = 0; i < corpusFinal_v2.GetLength(0); i++)
            {
                for (int k = 0; k < corpusFinal_v2.GetLength(1); k++)
                {
                    if (corpusFinal_v2[i, k] != null)
                    {
                        string str1 = corpusFinal_v2[i, k];
                        str1 = str1.ToLower(new CultureInfo("en-US", false));
                        corpusFinal_v2[i, k] = str1;
                    }
                }
            }
            //=====================================================================================================================================
            
            int order = 0;
            int[,] lines = new int[questions.Length, 100];                         //for finding the most accured answer I created a two dimensional  
            for (int i = 0; i < questionsFinal.GetLength(0); i++)                  //array that keeps the line's number of the word which is in the corpus
            {
                order = 0;
                for (int k = 1; k < questionsFinal.GetLength(1); k++)              //later that I will use that array for finding the most frequend line
                {                                                                  //and write it to the console
                    for (int j = 0; j < corpusFinal.GetLength(0); j++)
                    {
                        for (int l = 0; l < corpusFinal.GetLength(1); l++)
                        {
                            if (questionsFinal[i, k] != null)
                            {
                                if (corpusFinal[j, l] == (questionsFinal[i, k]))
                                {
                                    lines[i, order] = j;
                                    order++;
                                }
                            }
                        }
                    }
                }
            }
            //=====================================================================================================================================
            int current_freq = 0;
            int common = 0;
            int most_common = 0;
            int max_freq = 0;
            for (int i = 0; i < lines.GetLength(0); i++)
            {
                for (int k = 0; k < lines.GetLength(1); k++)
                {
                    for (int j = 0; j < lines.GetLength(1); j++)
                    {
                        if (lines[i,k]==lines[i,j] & lines[i,j]!=0 & lines[i, k] != 0)
                        {
                            current_freq++;
                            common = lines[i, k];
                        }
                    }
                    if (max_freq<=current_freq)
                    {
                        max_freq = current_freq;
                        most_common = common;
                    }
                    current_freq = 0;
                }
                if (max_freq>=2)
                {
                    lines[i, 0] = most_common;
                }
                else
                {
                    lines[i, 0] = -1;
                }
                common = 0;
                most_common = 0;
                max_freq = 0;
            }
            //=====================================================================================================================================
            Console.WriteLine("Answers");
            Console.WriteLine();

            for (int i = 0; i < lines.GetLength(0); i++)
            {
                //-------------------------------------------------------------------------------------------------------------------------
                if (lines[i,0]==-1 & questions[i].Contains("What is the result of expression"))  // calculating mathematichal function
                {
                    try
                    {
                        //--------------------------------------------------------------------------------------------------------------
                        string formula = questionsFinal[i, 3];        //the question starts with all the same sentence so for formula I assigned formula to another string to do calculations
                        string value_of_x = questionsFinal[i, 4];     //the question starts with all the same sentence so for x value I assigned x equality to another string to do calculations

                        value_of_x = value_of_x.Substring(2, value_of_x.Length - 2); //for taking only the value of x (2 instead of x=2) I used substring to get after 'x=' part

                        int index_x;
                        string[] elements_Formula = new string[questionsFinal[i, 3].Length]; //creating a array that keeps all elements after saperating with "+,-,*,/"
                        string opeartor = "+";
                        if (formula.Contains("+"))
                        {
                            elements_Formula = formula.Split('+');
                            opeartor = "+";
                        }
                        else if (formula.Contains("-"))
                        {
                            elements_Formula = formula.Split('-');
                            opeartor = "-";

                        }
                        else if (formula.Contains("*"))
                        {
                            elements_Formula = formula.Split('*');
                            opeartor = "*";
                        }
                        else if (formula.Contains("/"))
                        {
                            elements_Formula = formula.Split('/');
                            opeartor = "/";
                        }
                        //--------------------------------------------------------------------------------------------------------------
                        //after saperating and placing our formula's element we need to do the math 
                        for (int k = 0; k < elements_Formula.Length; k++)
                        {
                            index_x = elements_Formula[k].IndexOf('x');     //first we need the index of our x
                            string str1 = elements_Formula[k].Substring(0, index_x); //than I assinged first element to another string 
                            string str2 = elements_Formula[k].Substring((index_x + 1), (elements_Formula[k].Length) - (index_x + 1)); //and assiging our exponential expression to another string

                            elements_Formula[k] = Convert.ToString((Convert.ToInt32(str1)) * (Math.Pow(Convert.ToInt32(value_of_x), Convert.ToInt32(str2))));
                        }
                        //--------------------------------------------------------------------------------------------------------------
                        //in here we are calculating all the elements in array according to our operator
                        if (opeartor == "+")
                        {
                            for (int k = 1; k < elements_Formula.Length; k++)
                            {
                                elements_Formula[0] = Convert.ToString(Convert.ToInt32(elements_Formula[0]) + Convert.ToInt32(elements_Formula[k]));
                            }
                        }
                        else if (opeartor == "-")
                        {
                            for (int k = 1; k < elements_Formula.Length; k++)
                            {
                                elements_Formula[0] = Convert.ToString(Convert.ToInt32(elements_Formula[0]) - Convert.ToInt32(elements_Formula[k]));
                            }
                        }
                        else if (opeartor == "*")
                        {
                            for (int k = 1; k < elements_Formula.Length; k++)
                            {
                                elements_Formula[0] = Convert.ToString(Convert.ToInt32(elements_Formula[0]) * Convert.ToInt32(elements_Formula[k]));
                            }
                        }
                        else if (opeartor == "/")
                        {
                            for (int k = 1; k < elements_Formula.Length; k++)
                            {
                                elements_Formula[0] = Convert.ToString(Convert.ToDouble(elements_Formula[0]) / Convert.ToDouble(elements_Formula[k]));
                            }
                        }
                        Console.WriteLine("The result is " + elements_Formula[0]);
                    }
                    catch (Exception)
                    {
                        Console.WriteLine("There is a mistake in your equation!");
                    }
                    

                }//-------------------------------------------------------------------------------------------------------------------------------
                else if (lines[i, 0] == -1 & questions[i].Contains("What are the top10 words in the pattern")) //finding top 10 words in corpus
                {
                    int word_counter = 0;  //tracking how many word we found
                    int control = 0;       //if we found a unsuitable word control will increase so it will skip that word
                    string str_control = "";
                    for (int j = 0; j < corpusFinal_v2.GetLength(0); j++)
                    {
                        for (int l = 0; l < corpusFinal_v2.GetLength(1); l++)
                        {
                            if (corpusFinal_v2[j, l]!=null)
                            {
                                if (corpusFinal_v2[j, l].Length == questionsFinal[i, 4].Length)
                                {
                                    for (int g = 0; g < questionsFinal[i, 4].Length; g++)
                                    {
                                        string str1 = questionsFinal[i, 4];
                                        string str2 = corpusFinal_v2[j, l];
                                        if (str1[g] != '-' & str1[g] != str2[g])
                                        {
                                            control++;
                                        }
                                    }
                                    if (control < 1 & word_counter<10 & str_control.Contains(corpusFinal_v2[j,l])==false)
                                    {
                                        Console.Write(corpusFinal_v2[j, l]+" ");
                                        str_control = str_control+ corpusFinal_v2[j, l];
                                        word_counter++;
                                    }
                                    control = 0;
                                }
                            }
                        }
                    }
                    Console.WriteLine();
                }//-------------------------------------------------------------------------------------------------------------------------------
                else if (lines[i, 0] == -1)
                {
                    Console.WriteLine("No answer");
                }//-------------------------------------------------------------------------------------------------------------------------------
                else if (lines[i, 0] != -1)
                {
                    int a = lines[i, 0];
                    Console.WriteLine(corpus[a]);
                }
            }
        }
    }
}
