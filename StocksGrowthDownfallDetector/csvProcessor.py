# importing csv module
import csv
import time
import matplotlib.pyplot as plt
# csv file name
import numpy
import numpy as np

high = []
low = []
diff = []


def readData():
    filename = "Z://GIT-REPO/GitHub/StocksGrowthDownfallDetector/tmp/EPZM.csv"
    # initializing the titles and rows list
    rows = []
    # reading csv file
    with open(filename, 'r') as csvfile:
    # creating a csv reader object
     csvreader = csv.reader(csvfile)
    # extracting each data row one by one
     for row in csvreader:
      rows.append(row)
    # get total number of rows
    global total_rows
    total_rows=int(csvreader.line_num)
    print("Total no. of rows: "+str(total_rows))
    # printing the field names
   # print('Field Names Are:Name,Date,Open,High,Low,Close,Volume' )
    # get high / low  data
    for row in rows:
         high.append(row[3])
         low.append(row[4])


    # finding high-low
    for x in range(len(high)):
         diff.append(abs(float(high[x])-float(low[x])))
    userInput()




def plotGraph_Days_vs_High_Low_Difference(days,weeks,months,years):
    # plotting graph

    if(days!=0):
            days=days-1
            print("Showing Data For Last ", days+1, " Days.")
            y = diff[len(diff)-days:len(diff)]
            # corresponding x axis values
            x = []
            for z in range(0, days):
                x.append(z)

            # plotting the points
            plt.plot(x, y, 'o-', color='black', linewidth=1,
                     markerfacecolor='blue', markersize=4)
            # naming the x axis
            plt.ylabel('High-Low -->')
            # naming the y axis
            plt.xlabel('Last '+str(days+1)+' Days -->')
            # giving a title to my graph
            plt.title('High-Low vs Last '+str(days+1)+' Days Graph!')
            # function to show the plot
            plt.show()




    elif(weeks!=0):
            days=weeks*7-1
            print("Showing Data For Last", weeks ," Weeks.")
            y = diff[len(diff)-days:len(diff)]
            # corresponding x axis values
            x = []
            for z in range(0, days):
                x.append(z)

            # plotting the points
            plt.plot(x, y, 'o-', color='black', linewidth=1,
                     markerfacecolor='blue', markersize=4)
            # naming the x axis
            plt.ylabel('High-Low -->')
            # naming the y axis
            plt.xlabel('Last ' + str(days+1) + ' Days -->')
            # giving a title to my graph
            plt.title('High-Low vs Last ' + str(days+1) + ' Days Graph!')
            # function to show the plot
            plt.show()


        
    elif(months!=0):
            # taking number of days in a month =30
            days=months*30-1
            print("Showing Data For Last ", months, " Months.")
            y = diff[len(diff)-days:len(diff)]
            # corresponding x axis values
            x = []
            for z in range(0, days):
                x.append(z)

            # plotting the points
            plt.plot(x, y, 'o-', color='black', linewidth=1,
                     markerfacecolor='blue', markersize=4)
            # naming the x axis
            plt.ylabel('High-Low -->')
            # naming the y axis
            plt.xlabel('Last ' + str(days+1) + ' Days -->')
            # giving a title to my graph
            plt.title('High-Low vs Last ' + str(days+1) + ' Days Graph!')
            # function to show the plot
            plt.show()


    elif(years!=0):
            # taking number of days in an year =365
            days=years*365-1
            print("Showing Data For Last ", years, " Years.")
            y = diff[len(diff)-days:len(diff)]
            # corresponding x axis values
            x = []
            for z in range(0, days):
                x.append(z)

            # plotting the points
            plt.plot(x, y, 'o-', color='black', linewidth=1,
                     markerfacecolor='blue', markersize=4)
            # naming the x axis
            plt.ylabel('High-Low -->')
            # naming the y axis
            plt.xlabel('Last ' + str(days+1) + ' Days -->')
            # giving a title to my graph
            plt.title('High-Low vs Last ' + str(days+1) + ' Days Graph!')
            # function to show the plot
            plt.show()




def userInput():
    print("1. To Enter Last n Days            |")
    print("2. To Enter Last n  Weeks          |")
    print("3. To Enter Last n  Months         |")
    print("4. To Enter Last n  Years          |")
    print("5. To Process All Rows             |")

    print("Enter Your Choice :")
    ch = int(input())
    if(ch==1):
        print("Please Enter Days:")
        days = int(input())
        if(checkContinuous(days)==1):
            plotGraph_Days_vs_High_Low_Difference(days, 0, 0, 0)
    elif(ch==2):
        print("Please Enter Weeks:")
        weeks = int(input())
        if (checkContinuous(weeks*7) == 1):
            plotGraph_Days_vs_High_Low_Difference(0, weeks, 0, 0)
    elif(ch==3):
        print("Please Enter Months:")
        months = int(input())
        if (checkContinuous(months*30) == 1):
            plotGraph_Days_vs_High_Low_Difference(0, 0, months, 0)
    elif(ch==4):
        print("Please Enter Years:")
        years = int(input())
        if (checkContinuous(years*365) == 1):
            plotGraph_Days_vs_High_Low_Difference(0, 0, 0, years)
    elif(ch==5):
        print(total_rows)
        if (checkContinuous(total_rows) == 1):
            plotGraph_Days_vs_High_Low_Difference(total_rows, 0, 0, 0)
    else:
        count=2
        for x in range(0,2):
         print("Invalid Choice,Exiting in "+str(count) +" !")
         count=count-1
         time.sleep(1)
        exit()

# peaks count and continuity check
def checkContinuous(days):
    days=days-1
    print("Checking for continuity:")
    totalinc=0
    totaldec=0
    inc=0
    dec=0
    uppeaks=0
    downpeaks=0
    totalpeaks=0
    peaksforncontinuousdays=0
    uppeaksforncontinuousdays=0
    downpeaksforncontinuousdays=0
    # if continuity is for more than continuousdayslimit , then it will be considered a required peak point.
    continuousdays=4
    if days<len(diff):
        y = diff[len(diff)-days:len(diff)-1]
        for i in range(0, len(y)-1):
            if y[i]<y[i+1]:
                inc=inc+1
                totalinc=totalinc+1
                if (i+2)<len(y):
                    if y[i + 1] > y[i + 2]:
                        totalpeaks = totalpeaks + 1
                        uppeaks = uppeaks + 1
                        temp=inc
                        inc=0
                        if temp>continuousdays:
                            peaksforncontinuousdays=peaksforncontinuousdays+1
                            uppeaksforncontinuousdays=uppeaksforncontinuousdays+1




            elif y[i]>y[i+1]:
                dec=dec+1
                totaldec=totaldec+1
                if (i + 2) < len(y):
                    if y[i + 1] < y[i + 2]:
                        totalpeaks = totalpeaks + 1
                        downpeaks = downpeaks + 1
                        temp=dec
                        dec = 0
                        if temp>continuousdays:
                            peaksforncontinuousdays=peaksforncontinuousdays+1
                            downpeaksforncontinuousdays=downpeaksforncontinuousdays+1




        print("Total No of Peaks:",totalpeaks)
        print("Total No of UpPeaks:",uppeaks)
        print("Total No of DownPeaks:",downpeaks)
        print("Total No of Increments:",totalinc)
        print("Total No of Decrements:",totaldec)
        print("Total No of Peaks Which Have A Continuity Of More Than ",continuousdays," Days:", peaksforncontinuousdays)
        print("Total No of UpPeaks Which Have A Incrementing Continuity Of More Than ",continuousdays," Days:", uppeaksforncontinuousdays)
        print("Total No of DownPeaks Which Have A Decrementing Continuity Of More Than ",continuousdays," Days:", downpeaksforncontinuousdays)


# change below to >n value if u wish to include more than n continuous peaks , default is set to 0 i.e , it will include all peaks with
        # required limit for continuous days for inc/dec i.e,4 days.
        if peaksforncontinuousdays>0:
         return 1
        else:
         print("File doesn't contain peaks for more than "+str(continuousdays) +" continuous days of decrement or increment, so graph plotting is discarded ! Try a different file .")
         return 0
    else:
        print("That Much Of Data Is Not Present In The File, Try Again With Less Input!")



readData()
