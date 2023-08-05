

// Declare packages needed for the program
import java.util.*;
import java.io.*;

/*Main Class
Functions:
- Main
- ParseBattingRecord
*/
public class Main {

    /* This is the main function
       It performs the following functions
       * Open an input file
       * It performs error checking for file opening
       * It reads the file until the EOF
       * It parses data stored in the file
       * It calls helper functions to perform desired calculations
       * It calls helper functions to display the results in desired format
       * It closes the file
     */

    public static void main(String[] args) throws FileNotFoundException {

        //variable declaration
        String[] nameArray = new String[30];
        int[] hitArray = new int[30];
        int[] outArray = new int[30];
        int[] strikeArray = new int[30];
        int[] walkArray = new int[30];
        int[] hbpArray = new int[30];
        int[] sacrificeArray = new int[30];
        double[] battingAvg = new double[30];
        double[] obPercent = new double[30];
        int[] tieArray = new int[30];
        int arrayIndex = 0;
        int numbersOfLeaders;

        /* Setup the scanner */
    Scanner consoleIo = new Scanner (System.in);
    String fileTitle = consoleIo.nextLine();
    Scanner input = new Scanner(new File(fileTitle));

    /* Read data stored in the file line-by-line (while not EOL)*/
        while (input.hasNext()) {

            // parse name from file using substring function
            String line = input.nextLine();
            nameArray[arrayIndex] = line.substring(0, line.indexOf(' '));

            // parse record from file using substring function
            line = line.substring(line.indexOf(" "));
            String record = line;

            //  Parse Batting Record function call
            parseBattingRecord(record, nameArray, arrayIndex, hitArray, outArray, strikeArray, walkArray, hbpArray, sacrificeArray, battingAvg, obPercent);

            /* To work with parallel arrays we will keep track of the array index
               and store corresponding data for each play
             */
            arrayIndex++;
        }

        /* Display leader league data */

        System.out.println("LEAGUE LEADERS");
        //System.out.println(Arrays.toString(strikeArray));

        numbersOfLeaders = findLeaderDouble(battingAvg, arrayIndex+1, tieArray, false);
        displayLeaderData_DOUBLE("BA: ", nameArray, battingAvg, numbersOfLeaders, tieArray);

        numbersOfLeaders = findLeaderDouble(obPercent, arrayIndex+1, tieArray, false);
        displayLeaderData_DOUBLE("OB%: ", nameArray, obPercent, numbersOfLeaders, tieArray);

        numbersOfLeaders = findLeaderInteger(hitArray, arrayIndex+1, tieArray, false);
        displayLeaderData("H: ", nameArray, hitArray, numbersOfLeaders, tieArray);

        numbersOfLeaders = findLeaderInteger(walkArray, arrayIndex+1, tieArray, false);
        displayLeaderData("BB: ", nameArray, walkArray, numbersOfLeaders, tieArray);

        numbersOfLeaders = findLeaderInteger(strikeArray, arrayIndex+1, tieArray, true);
        displayLeaderData("K: ", nameArray, strikeArray, numbersOfLeaders, tieArray);

        numbersOfLeaders = findLeaderInteger(hbpArray, arrayIndex+1, tieArray, false);
        displayLeaderData("HBP: ", nameArray, hbpArray, numbersOfLeaders, tieArray);

        // close file
        input.close();

    }

    /* This is the helper function to parse the batting record
       It performs the following functions
       * Steps through the batting record input
       * uses counts to keep track of each valid action
       * stores the result in the provided array
       * It calls helper functions to display the player statistics in desired format
 */
    public static void parseBattingRecord(String battingRecord, String[] nameArray, int arrayIndex, int[] hitArray, int[] outArray, int[] strikeArray, int[] walkArray, int[] hbpArray, int[] sacrificeArray, double[] battingAvg, double[] obPercent)
    {

        //variable declaration
        int hitCount = 0, outCount = 0, strikeoutCount = 0, walkCount = 0, hitByPitchCount = 0, sacrificeCount = 0;
        int recordSize = battingRecord.length();


        //calculate counters for each move
        for (int i = 1; i < battingRecord.length(); ++i) {
            switch (battingRecord.charAt(i)) {
                case 'H':
                    hitCount++;
                    sacrificeCount++;
                    break;
                case 'O':
                    outCount++;
                    sacrificeCount++;
                    break;
                case 'K':
                    strikeoutCount++;
                    sacrificeCount++;
                    break;
                case 'W':
                    walkCount++;
                    sacrificeCount++;
                    break;
                case 'P':
                    hitByPitchCount++;
                    sacrificeCount++;
                    break;
                case 'S':
                    sacrificeCount++;
                    break;
                default:
            }
        }

        //fill all stat arrays with count for each arrayIndex (i.e player)
        hitArray[arrayIndex] = hitCount;
        outArray[arrayIndex] = outCount;
        strikeArray[arrayIndex] = strikeoutCount;
        walkArray[arrayIndex] = walkCount;
        hbpArray[arrayIndex] = hitByPitchCount;
        sacrificeArray[arrayIndex] = sacrificeCount;

        //call helper function to display player data
        displayPlayerData(nameArray, arrayIndex, hitCount, outCount, battingAvg, walkCount, hitByPitchCount, sacrificeCount, strikeoutCount, obPercent, recordSize);

    }

    /* This is the helper function to display/calculate the player data
       It performs the following functions
       * Displays the player name
       * calculates the on base percentage and batting average
       * error test the calculations for dealing with 0 over 0 division
       *Displays player data in desired format
 */

    private static void displayPlayerData(String[] nameArray, int arrayIndex, int hitCount, int outCount, double[] battingAvg, int walkCount, int hitByPitchCount, int sacrificeCount, int strikeoutCount, double[] obPercent, int recordSize) {

        //print out player name
        System.out.println(nameArray[arrayIndex]);
        double battingAverage;

        //use the following if statement to prevent 0 by 0 division issues
        if(hitCount+outCount+strikeoutCount !=0) {
            //calculates batting average
            battingAverage = ((hitCount * 1.0) / (hitCount + outCount + strikeoutCount));

        }else{

            //sets batting average to 0 when there is 0 by 0 division
            battingAverage = 0;
        }

        //displays batting average in desired format
        System.out.println("BA: " + (String.format("%.3f", battingAverage)));
        battingAvg[arrayIndex] = battingAverage;

        double OB;

        //use the following if statement to prevent 0 by 0 division issues
        if((recordSize-1) != 0) {

            //calculates on base percentage
            OB = ((hitCount + walkCount + hitByPitchCount) * 1.0 / (sacrificeCount));
        }else{
            //sets on base percentage to 0 when there is 0 by 0 division
            OB = 0;
        }

        //displays on base percentage in desired format
        System.out.println("OB%: " + (String.format("%.3f", OB)));
        obPercent[arrayIndex] = OB;

        System.out.println("H: " + hitCount);
        System.out.println("BB: " + walkCount);
        System.out.println("K: " + strikeoutCount);
        System.out.println("HBP: " + hitByPitchCount+ "\n");
    }

    /* This is the helper function to find the leader in the provided dataArray
    (NOTE: can only take in int data array)
        It performs the following functions
        * walks through data to find minimum and maximum value
        * double checks to make sure there are no ties
        *when a tie occurs the function stores the data for all
  */
    public static int findLeaderInteger(int[] dataArray, int recordSize, int[] tieArray, boolean min_flag) {
        int MinMax = dataArray[0];
        int numberOfTies = 0;

        // zero out the array
        Arrays.fill(tieArray, 0);

        /* Sets MinMax to index 0 */
        tieArray[0] = 0;

        // walk the data array to find the min or max in the array
        for (int i = 1; i < recordSize-1; ++i) {
            if (min_flag) {
                // find min through comparison
                if (dataArray[i] < MinMax) {
                    //found a new min
                    MinMax = dataArray[i];
                    //store the new min index
                    tieArray[0] = i;
                }
            } else {
                // find max through comparison
                if (dataArray[i] > MinMax) {
                    //found a new max
                    MinMax = dataArray[i];
                    //store the new max index
                    tieArray[0] = i;
                }
            }
        }

        /* Find any other entry that matched the MinMax */
        /* No need to walk from index 0; only from the index of the first MinMax in the list */
        for (int i = tieArray[0] + 1; i < recordSize-1; ++i) {
            if (dataArray[i] == MinMax) {
                //if there is a tie keep track of it's index
                numberOfTies++;
                tieArray[numberOfTies] = i;
            }
        }

        /* Number of leaders are one more than the number of ties */
        return numberOfTies+1;
    }

    /* This is the helper function to find the leader in the provided dataArray
   (NOTE: can only take in double data array)
       It performs the following functions
       * walks through data to find minimum and maximum value
       * double checks to make sure there are no ties
       *when a tie occurs the function stores the data for all
 */

    public static int findLeaderDouble(double[] dataArray, int recordSize, int[] tieArray, boolean min_flag) {
        double MinMax = dataArray[0];
        int numberOfTies = 0;

        // zero out the array
        Arrays.fill(tieArray, 0);

        /* Sets MinMax to index 0 */
        tieArray[0] = 0;

        // walk the data array to find the min or max in the array
        for (int i = 1; i < recordSize-1; ++i) {
            if (min_flag) {
                // find min through comparison
                if (dataArray[i] < MinMax){
                    //found a new min
                    MinMax = dataArray[i];
                    //store the new min index
                    tieArray[0] = i;

                }
            } else {
                // find max through comparison
                if (dataArray[i] > MinMax) {
                    //found a new max
                    MinMax = dataArray[i];
                    //store the new max index
                    tieArray[0] = i;
                }
            }
        }

        /* Find any other entry that matched the Max */
        /* No need to walk from index 0; only from the index of the first Max in the list */
        for (int i = tieArray[0] + 1; i < recordSize-1; ++i) {

            //use the below if statement to prevent issues with comparing doubles and floats
            if (Math.abs(dataArray[i] - MinMax) <= 0.00000001) {
                //if there is a tie keep track of it's index
                numberOfTies++;
                tieArray[numberOfTies] = i;
            }
        }

        /* Number of leaders are one more than the number of ties */
        return numberOfTies+1;
    }

     /* This is the helper function to display the leader data provided in the data array
    (NOTE: can only take in int data array)
        It performs the following functions
        * walks through data tie array
        * displays each leader's info in desired format
  */

    private static void displayLeaderData(String Action, String[] nameArray, int[] dataArray, int numberOfLeaders, int[] tieArray) {
        String output = Action;
        int leaderIndex = 0;

        // Walk through the list of tied leaders
        for (int i = 0; i < numberOfLeaders; ++i) {
            // Display leader's name
            leaderIndex = tieArray[i];
            // The following statement is to print in the desired format
            if ((numberOfLeaders > 1) && (i < numberOfLeaders-1)) {
                output = output.concat(nameArray[leaderIndex] + ", ");
            } else {
                output = output.concat(nameArray[leaderIndex] + " ");
            }
        }
        // Display leader's data
        System.out.println(output + dataArray[leaderIndex]);
    }

    /* This is the helper function to display the leader data provided in the data array
        (NOTE: can only take in double data array)
            It performs the following functions
            * walks through data tie array
            * displays each leader's info in desired format
      */
    private static void displayLeaderData_DOUBLE(String Action, String[] nameArray, double[] dataArray, int numberOfLeaders, int[] tieArray) {
        String output = Action;
        int leaderIndex = 0;

        // Walk through the list of tied leaders
        for (int i = 0; i < numberOfLeaders; ++i) {
            // Display leader's name
            leaderIndex = tieArray[i];
            // The following statement is to print in the desired format
            if ((numberOfLeaders > 1) && (i < numberOfLeaders-1)) {
                output = output.concat(nameArray[leaderIndex] + ", ");
            } else {
                output = output.concat(nameArray[leaderIndex] + " ");
            }
        }

        // Display leader's data
        System.out.println(output + (String.format("%.3f", dataArray[leaderIndex])));
    }
}