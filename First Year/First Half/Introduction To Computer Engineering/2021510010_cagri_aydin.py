fileLenght = open('2021510010_çağrı_aydın_input.txt','r') #opening .txt file to get the lenght of file
range_of_file = len(fileLenght.readlines())               #I did this saparetly otherwise it sees every line in txt file as a empty line
fileLenght.close()                                        #I realized that I used Turkish charecters in input file so sorry about that 


infile = open('input.txt','r')   #opening .txt file

line_list = []                                          #adding .txt file items to a list
for a in range (range_of_file):
    line1 = infile.readline()
    
    if " " not in line1:                                #checking the line if it is seperated or not
        line2 = ''
        line1=line1.rstrip()                            #deleting \n end of the line
        control = ''
        a=0
        c=0
        for z in range(len(line1)+1):                   #
            if a ==len(line1):
                control = line1[c:a]
                line2 += control
                break

            if line1[a].isnumeric() and a <len(line1):
                a=a+1
            else:
                control = line1[c:a]
                line2 += control
                line2 += " "
                control = str(line1[a])
                line2 += control
                line2 += " "
                a=a+1
                c=a  
        line2=line2.split(" ")
        line_list.append(line2)
    else:    
        
        line1=line1.rstrip().split(" ")
        line_list.append(line1)       
    
infile.close()                                          #we no longer need the .txt file




def control_lines(counter):                             #adding a function that controls line
    a=0
    b=0
    while a<len(line_list[counter]):
        
        if line_list[counter] == ['']:                  #if the line is empty it will raise the counter to skip the line
            counter=counter +1 
            break

        elif a%2 ==0 and line_list[counter][a].isnumeric()==False :  #every number is between two operator so it will start with a number and every even element in the line must be numeric
            line_list[counter]="ERROR"
            break

        elif a%2==1 and line_list[counter][a] not in ("*", "/" , "+"  ,"-"  ,"%" , "<", ">", "<=", ">=" ,"!=", "==", "**","//") : #every operator is between two number so it will start with a number and every odd element in the line must be in this operator
            line_list[counter]="ERROR"
            break

        elif a%2 == 1 and line_list[counter][a] in ("<", ">", "<=", ">=" ,"!=", "=="): #checing a line contains more than one logical opeartor
            b=b+1
            if b>1:
                line_list[counter]="ERROR"
                break

        elif a== len(line_list[counter])-1 and line_list[counter][a].isnumeric()==False  : #checking the last digit in line
            line_list[counter]="ERROR"
            break

        elif a== 0 and line_list[counter][a].isnumeric()==False  : #checking the first digit in line
            line_list[counter]="ERROR"
            break
        
        a=a+1



def calculate_line(counter):       #int the down below I write arithmetic opeartor (**,*,/,//,%,+,-)
    a =0
    while len(line_list[counter]) !=1:
        if line_list[counter]!="ERROR":
            while a <=len(line_list[counter])-2:
                
                if line_list[counter][1] == "**":    #controling the second element in line and it is equal "**" it will do the ** opeartion
                    line_list[counter][1] =  float(line_list[counter][0]) ** float(line_list[counter][2]) #making the opeartion and writing to second element
                    line_list[counter][2] = line_list[counter][1]  # after writing the second element it will assign the result to third element 
                    strtemp = line_list[counter][2:] # and deleting the first two element in the line 
                    line_list[counter]= strtemp
                    a=0

                if line_list[counter][a]=="**":
                    line_list[counter][a] =  float(line_list[counter][a-1]) ** float(line_list[counter][a+1])
                    line_list[counter][a+1] = line_list[counter][a]
                    strtemp = line_list[counter][:a-1]+line_list[counter][a+1:]
                    line_list[counter]= strtemp
                    a=0
                a=a+1    

            a=0       
#=================================================================================================================================================        
            while a <=len(line_list[counter])-2:
            
                if line_list[counter][1] == "*":
                    line_list[counter][1] =  float(line_list[counter][0]) * float(line_list[counter][2])
                    line_list[counter][2] = line_list[counter][1]
                    strtemp = line_list[counter][2:]
                    line_list[counter]= strtemp
                    a=0
                                                   
                if line_list[counter][1] == "/" :    
                    if line_list[counter][2]== "0":
                        line_list[counter]="ERROR"
                    else:
                        line_list[counter][1] =  float(line_list[counter][0]) / float(line_list[counter][2])
                        line_list[counter][2] = line_list[counter][1]
                        strtemp = line_list[counter][2:]
                        line_list[counter]= strtemp
                    a=0
                
                if line_list[counter][1] == "//":    
                    if line_list[counter][2] =="0":
                        line_list[counter]="ERROR"
                    else:    
                        line_list[counter][1] =  int(line_list[counter][0]) / int(line_list[counter][2])
                        line_list[counter][2] = int(line_list[counter][1])
                        strtemp = line_list[counter][2:]
                        line_list[counter]= strtemp
                    a=0
                
                if line_list[counter][1] == "%":    
                    if line_list[counter][2] =="0":
                        line_list[counter]="ERROR"
                    else:    
                        line_list[counter][1] =  float(line_list[counter][0]) % float(line_list[counter][2])
                        line_list[counter][2] = float(line_list[counter][1])
                        strtemp = line_list[counter][2:]
                        line_list[counter]= strtemp
                    a=0


                if line_list[counter][a] == "*" and a>1:
                    line_list[counter][a] =  float(line_list[counter][a-1]) * float(line_list[counter][a+1])
                    line_list[counter][a+1] = line_list[counter][a]
                    strtemp = line_list[counter][:a-1]+line_list[counter][a+1:]
                    line_list[counter]= strtemp
                    a=0

                if line_list[counter][a] == "/" and a>1:
                    if line_list[counter][a+1] =="0":
                        line_list[counter] = "ERROR"
                    else:    
                        line_list[counter][a] =  float(line_list[counter][a-1]) / float(line_list[counter][a+1])
                        line_list[counter][a+1] = line_list[counter][a]
                        strtemp = line_list[counter][:a-1]+line_list[counter][a+1:]
                        line_list[counter]= strtemp
                    a=0
                
                if line_list[counter][a] == "//" and a>1:
                    if line_list[counter][a+1] == "0":
                        line_list[counter] = "ERROR"
                    else:    
                        line_list[counter][a] =  int(line_list[counter][a-1]) / int(line_list[counter][a+1])
                        line_list[counter][a+1] = int(line_list[counter][a])
                        strtemp = line_list[counter][:a-1]+line_list[counter][a+1:]
                        line_list[counter]= strtemp
                    a=0
                
                if line_list[counter][a] == "%" and a>1:
                    if line_list[counter][a+1] == "0":
                        line_list[counter] = "ERROR"
                    else:    
                        line_list[counter][a] =  float(line_list[counter][a-1]) % float(line_list[counter][a+1])
                        line_list[counter][a+1] = float(line_list[counter][a])
                        strtemp = line_list[counter][:a-1]+line_list[counter][a+1:]
                        line_list[counter]= strtemp
                    a=0
                a=a+1 
            

            a=0    
 #==============================================================================================================================================           
    
            while a <=len(line_list[counter])-2:    
            
                if line_list[counter][1] == "+":
                    line_list[counter][1] =  float(line_list[counter][0]) + float(line_list[counter][2])
                    line_list[counter][2] = line_list[counter][1]
                    strtemp = line_list[counter][2:]
                    line_list[counter]= strtemp
                    a=0
                    break
                    
                
                if line_list[counter][1] == "-" :
                    line_list[counter][1] =  float(line_list[counter][0]) - float(line_list[counter][2])
                    line_list[counter][2] = line_list[counter][1]
                    strtemp = line_list[counter][2:]
                    line_list[counter]= strtemp
                    a=0
                    
                
           


                if line_list[counter][a] == "+" and a>1:
                    line_list[counter][a] =  float(line_list[counter][a-1]) + float(line_list[counter][a+1])
                    line_list[counter][a+1] = line_list[counter][a]
                    strtemp = line_list[counter][:a-1]+line_list[counter][a+1:]
                    line_list[counter]= strtemp
                    a=0
                    break
                
                if line_list[counter][a] == "-" and a>1:
                    line_list[counter][a] =  float(line_list[counter][a-1]) - float(line_list[counter][a+1])
                    line_list[counter][a+1] = line_list[counter][a]
                    strtemp = line_list[counter][:a-1]+line_list[counter][a+1:]
                    line_list[counter]= strtemp
                    a=0
                a=a+1
            a=0       
            break
        
        break
       

            

def logical_operations(counter):    #controling logical operations I write TRUE or FALSE rather than turning a flag because if I turn a flag it will write like False instead of FALSE 
    
    if line_list[counter]!="ERROR" and len(line_list[counter])>2 :
        a_control = float(line_list[counter][0]) # at the end of the aritmatic operations there last two number if it is containing logical operations
        b_control = float(line_list[counter][2])
        if line_list[counter][1]=="<":  #second element of the line will be a logical operation so checking which operation it is
            c = a_control < b_control
            if c == True:
                line_list[counter] = "TRUE"
            if c == False:
                line_list[counter] = "FALSE"  
        
        if line_list[counter][1]==">":
            c = a_control > b_control
            if c == True:
                line_list[counter] = "TRUE"
            if c == False:
                line_list[counter] = "FALSE" 
        
        if line_list[counter][1]=="<=":
            c = a_control <= b_control
            if c == True:
                line_list[counter] = "TRUE"
            if c == False:
                line_list[counter] = "FALSE"               
        
        if line_list[counter][1]==">=":
            c = a_control >= b_control
            if c == True:
                line_list[counter] = "TRUE"
            if c == False:
                line_list[counter] = "FALSE"      
        
        if line_list[counter][1]=="!=":
            c = a_control != b_control
            if c == True:
                line_list[counter] = "TRUE"
            if c == False:
                line_list[counter] = "FALSE"
        
        if line_list[counter][1]=="==":
            c = a_control == b_control
            if c == True:
                line_list[counter] = "TRUE"
            if c == False:
                line_list[counter] = "FALSE"
        

for a in range(range_of_file):          #calling functions for each element and correct order
    control_lines(a)
    for b in range(len(line_list[a])): #repeating calculation function for each element of the line 
        calculate_line(a)
    logical_operations(a)

output= open('2021510010_cagri_aydin_output.txt', 'a')      #writing to .txt file
for a in range(len(line_list)):
    line = line_list[a]
    n = output.write(str(line)+"\n")
output.close()    