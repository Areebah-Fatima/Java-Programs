//Below we declare the packages and APIs needed for this program
       // java.io will be used to process inputs and produce outputs
       // java.util.Scanner allows us to use the Scanner class to get user inputs */
import java.util.*;
import java.io.*;

 /*
   This is global enum
   It is used to share code to perform the following functionalities:
    - find league leaders
    - display league leaders
 */
enum ActionType {
    BATTING_AVERAGE,
    ON_BASE_PERCENTAGE,
    HITS,
    WALKS,
    STRIKEOUTS,
    HIT_BY_PITCH
}

 /*Main Class
    Functions:
    - Main
    - DisplayScoreBoard
    - PopulatePlayersData
    - PopulatePlayersDataUsingDecodeMap
    - PopulatePlayersDataUsingDecodedAction
    - PlayerKeyValueToBattingActionMapping
    - CreatePlayerNameToPlayerMapping
    - PrintCodeMap
    - PrintPlayer
    - DisplayPlayerData
    - AlphabeticalNameArray
    - FindAndDisplayLeadersByMax
    - FindAndDisplayLeadersByMin
    - PrintDataLine
    - PrintLeader
    - PrintBanner
    */

public class Main {

    /*
     * This the main function.
     * It read a file line by line
     * performs exception handling for file not found exceptions
     * creates both Hashmaps
     * calls functions that populate maps, and help correctly format an output
     * INPUT: args (input file name)
     * OUTPUT: None
     *
     */
    public static void main(String[] args) {
        // Exception handling to ensure input file exists
        try {
            /* Setup the scanner */
            Scanner consoleIo = new Scanner(System.in);
            String fileTitle = consoleIo.nextLine();
            Scanner input = new Scanner(new File(fileTitle));

            //Create a HashMap for the Player name to the player object
            HashMap<String, Player> playerMap;
            playerMap = new HashMap();

            //create array list that will hold names (this will later be alphabetically ordered and used to search map)
            ArrayList<String> arr;
            arr = new ArrayList<>(10);

            /* Read data stored in the file line-by-line (while not EOL)*/
            while (input.hasNext()) {
                // Read Team (Away or Home)
                String team = input.next();
                // Read player name
                String name = input.next();
                if (!arr.contains(name)) {
                    // Add player using name as the key into the array list
                    arr.add(name);
                }
                // Read player's action
                String action = input.next();

                // Call helper function to add player to the hash map
                createPlayerNameToPlayerMapping(playerMap, name, team, action);
            }

            //close first file
            input.close();

            // create the 2nd hashmap
            HashMap<String, String> decodeMap;
            decodeMap = new HashMap();

            //set up file stream
            FileInputStream inputFile = null;

            //set up file while also handling exceptions
            try {
                inputFile = new FileInputStream("./keyfile.txt");
            } catch (FileNotFoundException e) {
                System.out.println("Error reading file");
                e.printStackTrace();
            }
            Scanner myFileScanner;
            assert inputFile != null;
            myFileScanner = new Scanner(inputFile);

            String code;
            String actionCode = "";

            //While loop to read each code in the keyfile one by one
            while (myFileScanner.hasNext()) {

                String line = myFileScanner.nextLine();

                //if true we are dealing with actions (OUTS, WALK, etc)
                if (line.contains("#")) {
                    //parse accordingly to have a clean action variable
                    line = line.replaceAll("#", "");
                    actionCode = line.replaceAll(" ", "");
                } else {
                    //if true we have a code(f6, k, etc)
                    code = line.replaceAll(" ", "");
                    if (!code.isEmpty()) {
                        //handles empty cases
                        PlayerKeyValueToBattingActionMapping(decodeMap, actionCode, code);
                    }
                }
            }


            // close scanner
            myFileScanner.close();

            // Read player's data from the Hash tables and Populate player info
            populatePlayersData(playerMap, decodeMap);

            //call function to alphabetically sort our arraylist of names
            alphabeticalNameArray(arr);

            //call function to display our score board for both teams in the correct format
            displayScoreBoard(playerMap, arr);

            // Leaders Data
            System.out.print("\nLEAGUE LEADERS\n");
            //print leaders
            printLeader(playerMap);
        }
        //if we end up in this catch we never found the file in our directory and need to print an error message
        catch(Exception e){
            System.out.println("Input file does not exist.");
        }

    }

     /* This function is used to print the Scoreboard for each team in the correct format
      *
      *  INPUT:
      *     - Player Hashmap
      *     - Alphabetically sorted name arrayList
      *
      *  OUTPUT:
      *     - NONE
      * It performs the following functions
      * Prints both the AWAY and HOME banners
      * Walks through the alphabetically sorted name array and uses info to determine the location it will be displayed
      * Retrieves the player names and calls displayPlayerData to display each players data
      */
    public static void displayScoreBoard(HashMap playerMap, ArrayList<String> arr) {
        // Display AWAY players
        //Print AWAY Banner
        System.out.println("AWAY");

        //Walk through the alphabetically sorted name arraylist
        for (Object o : arr) {
            //set playerName based on each index of the array list
            String playerName = (String) o;
            // Find the player data for this player
            Player player = (Player) playerMap.get(playerName);

            //if this player has "A" stored as their team then print it first
            if (player.getTeam().equals("A")) {
                //call displayPlayerData to print the data associated with each player
                displayPlayerData(player);
            }
        }

        //Print line for formatting
        System.out.println();

        // Display HOME players
        //Print HOME Banner
        System.out.println("HOME");

        //Walk through the alphabetically sorted name arraylist
        for (Object o : arr) {
            //set playerName based on each index of the array list
            String playerName = (String) o;
            // Find the player data for this player
            Player player = (Player) playerMap.get(playerName);

            //if this player has "H" stored as their team then print it second
            if (player.getTeam().equals("H")) {

                //call displayPlayerData to print the data associated with each player
                displayPlayerData(player);
            }
        }
    }

    /* This function is used to populate/fill the Player hashmap
     *
     *  INPUT:
     *     - Player Hashmap
     *     - Decode Hashmap
     *
     *  OUTPUT:
     *     - NONE
     * It performs the following functions
     * - Steps through all entries in the player hashmap
     * - Uses the player name as a key to find the player entry in the map and calls helper function to start filling
     *   the decoded map values
     */
    public static void populatePlayersData(HashMap playerMap, HashMap decodeMap) {
        // Create a set for the playerMap Hash table
        Set<Map.Entry<String, Player>> playerSet = playerMap.entrySet();

        // Walk each entry in the player set
        for (Map.Entry<String, Player> PlayerEntry : playerSet) {
            // Get the player from the Hash Table
            Player player = PlayerEntry.getValue();
            // For for player, populate players data using the decodeMap
            populatePlayersDataUsingDecodeMap(player, decodeMap);
        }
    }


    /* This function is used to populate/fill the Player hashmap
     *
     *  INPUT:
     *     - Player player
     *     - Decode Hashmap
     *
     *  OUTPUT:
     *     - NONE
     * It performs the following functions
     * - For a given player:
     *      - Walk through all actions it performs
     *      - Look into decode hash map and populated the player info
     */
    public static void populatePlayersDataUsingDecodeMap(Player player, HashMap decodeMap) {
        // Loop through the action list in the player's list
        LinkedList ll = player.actionList;
        for (Object o : ll) {
            String action = (String) o;
            // Decode the action
            String decodedAction = (String) decodeMap.get(action);
            // populate players data
            populatePlayersDataUsingDecodedAction(player, decodedAction);
        }
    }

    /* This function is used to populate/fill the Player data using this new decoded action (ex p3 --> OUT)
     *
     *  INPUT:
     *     - Player player
     *     - String decode
     *
     *  OUTPUT:
     *     - NONE
     * It performs the following functions
     * - uses a switch structure to increment counts based on decoded action found
     *      - updates the player stats count
     */
    public static void populatePlayersDataUsingDecodedAction(Player player, String decodedAction) {

        /* Update players stats based on decoded action */

        //this switch structure will use the decoded action passed in the determine the coutns
        switch (decodedAction) {

            case "OUTS":

                //if true retrieve the outCount and increment it by one
                int outCount = player.getOutCount();
                player.setOutCount(outCount + 1);

                break;
            case "STRIKEOUT":
                //if true retrieve the strikeoutCount and increment it by one
                int strikeoutCount = player.getStrikeoutCount();
                player.setStrikeoutCount(strikeoutCount + 1);

                break;
            case "HITS":
                //if true retrieve the hitCount and increment it by one
                int hitCount = player.getHitCount();
                player.setHitCount(hitCount + 1);

                break;
            case "WALK":
                //if true retrieve the walkCount and increment it by one
                int walkCount = player.getWalkCount();
                player.setWalkCount(walkCount + 1);

                break;
            case "SACRIFICE":
                //if true retrieve the sacrificeCount and increment it by one
                int sacrificeCount = player.getSacrificeCount();
                player.setSacrificeCount(sacrificeCount + 1);

                break;
            case "HITBYPITCH":
                //if true retrieve the hitByPitchCount and increment it by one
                int hitByPitchCount = player.getHitByPitchCount();
                player.setHitByPitchCount(hitByPitchCount + 1);

                break;
            case "ERRORS":
                //if true retrieve the errorCount and increment it by one
                int errorCount = player.getErrorCount();
                player.setErrorCount(errorCount + 1);

                break;

                //if true a invalid entry made for the passed in decoded action
            default:

                //print error message
                System.out.println("Invalid Character");
                break;
        }
    }

    /* This function is used to add a actionCode and code to the decode hashmap
     *
     *  INPUT:
     *     - map decode hashmap
     *     - String actionCode: value for the hash map
     *     - String code: value for the hash key
     *
     *  OUTPUT:
     *     - NONE
     * It performs the following functions
     * - For a actionCode and code :
     *      - Add entry to the hash table
     */
    public static void PlayerKeyValueToBattingActionMapping(HashMap map, String actionCode, String code) {
        // For the actionCode and code, add entry to the hash table
        map.put(code, actionCode);
    }

    /* This function is used to create or add on to players in the Player hashmap
     *
     *  INPUT:
     *     - Player hashmap
     *     - String player name
     *     - Player team and action
     *
     *  OUTPUT:
     *     - NONE
     * It performs the following functions
     * - Checks if players is already in map
     *      - if yes adds to existing action list if not then creates that player in the map
     */
    public static void createPlayerNameToPlayerMapping(HashMap map, String playerName,
                                                       String team, String action) {
        Player player;

        //if map already contains key no need to create the player again
        if (map.containsKey(playerName)) {
            //instead just add the additional action performed to the player's action list
            player = (Player) map.get(playerName);
            player.actionList.add(action);
        } else {
            //if we end up here it means that this player is new
            //so create a new player in the map
            player = new Player(playerName, team);
            player.actionList = new LinkedList();
            //place the new key and value into the map
            map.put(playerName, player);
            player.actionList.add(action);
        }
    }

    /* This function is used to calculate OB% and BA while also displaying the players stats
     *
     *  INPUT:
     *     - Player player
     *
     *  OUTPUT:
     *     - NONE
     * It performs the following functions
     * - Calculates BA and OB% and at bats
     * - Displays all Players stats
     */
    public static void displayPlayerData(Player player) {

        //variables to hold BA, atBats, and plateAppearances (will be used for calculations)
        double battingAverage;
        int atBats;
        int plateAppearances;

        // use the following if statement to prevent 0 by 0 division issues
        if (player.getHitCount() + player.getOutCount() + player.getStrikeoutCount() != 0) {
            //calculates batting average
            battingAverage = ((player.getHitCount() * 1.0) / (player.getHitCount() + player.getOutCount() +
                    player.getStrikeoutCount()));

        } else {

            // sets batting average to 0 when there is 0 by 0 division
            battingAverage = 0;
        }

        //calculate atBats by adding up hits, outs, strikes, and error
        atBats = (player.getHitCount() + player.getOutCount() + player.getStrikeoutCount() + player.getErrorCount());

        //calculate plate Appearances by adding up hits, outs, strikes, walks, hitbypitch, and Sacrifice
        plateAppearances = (player.getHitCount() + player.getOutCount() + player.getStrikeoutCount() +
                player.getWalkCount() + player.getHitByPitchCount() + player.getSacrificeCount());

        //variable for ON%
        double OB;

        // use the following if statement to prevent 0 by 0 division issues
        if (plateAppearances != 0) {
            //calculates on base percentage
            OB = ((player.getHitCount() + player.getWalkCount() +
                    player.getHitByPitchCount()) * 1.0 / (plateAppearances));
        } else {
            // sets on base percentage to 0 when there is 0 by 0 division
            OB = 0;
        }

        //print out player name
        System.out.print(player.getName() + "\t");

        // displays on Player data(atBats, hits count, walkcount, etc) in the desired format
        System.out.print(atBats + "\t");
        System.out.print(player.getHitCount() + "\t");
        System.out.print(player.getWalkCount() + "\t");
        System.out.print(player.getStrikeoutCount() + "\t");
        System.out.print(player.getHitByPitchCount() + "\t");
        System.out.print(player.getSacrificeCount() + "\t");
        System.out.print((String.format("%.3f", battingAverage) + "\t"));
        System.out.print((String.format("%.3f", OB)) + "\n");
    }

    /* This function is used to alphabetically sort the arraylist filled with player names (will be used later on to search map)
     *
     *  INPUT:
     *     - ArrayList arr
     *
     *  OUTPUT:
     *     - NONE
     * It performs the following functions
     * - Uses a collection sort to alphabetically sort the arraylist
     */
    public static void alphabeticalNameArray(ArrayList arr) {

        //a collection sort to alphabetically sort the arraylist
        Collections.sort(arr);
    }

    /*
This is a helper function to
- Find leaders in the link list by maximum value
- print player's data using helper functions
- It display the top 3 leaders for the given category

INPUT:
actionType: Type of data passed
player Hashmap

OUTPUT:
None
*/
    public static void findAndDisplayLeadersByMax(HashMap playerMap, ActionType actionType) {

        // Set the first max, second mac and third max value to invalid Max
        double First = -1;
        double Second = -1;
        double Third = -1;
        double valueForCurrentPlayer;
        int numOfTopLeaders = 0;

        //use array list to store away and home team players for each position (will be used for outputting)
        ArrayList<String> firstListAway
                = new ArrayList<>(5);
        ArrayList<String> firstListHome
                = new ArrayList<>(5);
        ArrayList<String> secondListAway
                = new ArrayList<>(5);
        ArrayList<String> secondListHome
                = new ArrayList<>(5);
        ArrayList<String> thirdListAway
                = new ArrayList<>(5);
        ArrayList<String> thirdListHome
                = new ArrayList<>(5);

        // Printer Leader Banner based on the data type
        printBanner(actionType); // displayType is an enum

        // Walk the table
        Set<Map.Entry<String, Player>> set = playerMap.entrySet();
        for (Map.Entry<String, Player> entry : set) {
            Player currentPlayer = entry.getValue();

            // Find value for the current Player
            valueForCurrentPlayer = Player.findValue(currentPlayer, actionType);

            //update first second and third leaders
            if (valueForCurrentPlayer > First) {
                // current Player is higher than current First Max
                Third = Second;
                Second = First;
                First = valueForCurrentPlayer;
            } else if (valueForCurrentPlayer > Second) {
                // current Player is higher than current Second Max
                Third = Second;
                Second = valueForCurrentPlayer;
            } else if (valueForCurrentPlayer > Third) {
                // current Player is higher than current Third Max
                Third = valueForCurrentPlayer;
            }
        }

        // First print the First Max Leaders
        // Check who in the list has the value matching the First Max
        for (Map.Entry<String, Player> entry : set) {
            Player currentPlayer = entry.getValue();
            valueForCurrentPlayer = Player.findValue(currentPlayer, actionType);
            // Do we have a match?
            if (Math.abs(valueForCurrentPlayer - First) <= 0.00000001) {
                // This Player is a leader; print the leader info

                //if true the player is a part of the away team and will be displayed first
                if (currentPlayer.getTeam().equals("A")) {
                    //store the player in its designated location
                    firstListAway.add(currentPlayer.getName());
                }

                //if true the player is a part of the away home and will be displayed second (after all away team players)
                if (currentPlayer.getTeam().equals("H")) {
                    //store the player in its designated location
                    firstListHome.add(currentPlayer.getName());
                }

                //increment number of leaders
                numOfTopLeaders++;
            }
        }
        // Display First Leaders
        int numLeadersPrinted = 0;

        //alphabetically sort the linklist containing all first place away team leaders
        Collections.sort(firstListAway);

       // walk list and call print data line to print all leaders in a list sperated by commas
        for (String s : firstListAway) {
            printDataLine(s, First,
                    actionType, numLeadersPrinted);

            //increment number of leaders printed (used to format correctly)
            numLeadersPrinted++;
        }

        //alphabetically sort the linklist containing all first place home team leaders
        Collections.sort(firstListHome);
        // walk list and call print data line to print all leaders in a list sperated by commas
        for (String s : firstListHome) {
            printDataLine(s, First,
                    actionType, numLeadersPrinted);

            //increment number of leaders printed (used to format correctly)
            numLeadersPrinted++;
        }

        // We are done printing the first leader;
        // Check if we need print additional leaders
        if (numOfTopLeaders >= 3) {
            // we are done!
            return;
        }

        // Now print the Second Max Leaders
        // Check who in the list has the value matching the Second Max

        // Only Print the Second Leader if First is not tied with Second; otherwise we will double print
        if ((Second != -1) && (Second != First)) {


            //walk the table
            for (Map.Entry<String, Player> entry : set) {
                Player currentPlayer = entry.getValue();
                valueForCurrentPlayer = Player.findValue(currentPlayer, actionType);

                // Do we have a match?
                if (Math.abs(valueForCurrentPlayer - Second) <= 0.00000001) {
                    // This Player is a leader; print the leader info
                    //player from away team act accordingly
                    if (currentPlayer.getTeam().equals("A")) {
                        secondListAway.add(currentPlayer.getName());
                    }

                    //player from away team act accordingly
                    if (currentPlayer.getTeam().equals("H")) {
                        secondListHome.add(currentPlayer.getName());
                    }
                    // Save counters for printing
                    numOfTopLeaders++;
                    //numOfSecondLeaders++;
                }
            }
        }
        // Display Second Leaders
        if ((Second != -1) && (Second != First)) {
            numLeadersPrinted = 0;
            System.out.println();
        }
        //alphabetically sort leaders for away team
        Collections.sort(secondListAway);
        //walk list and print
        for (String s : secondListAway) {
            printDataLine(s, Second,
                    actionType, numLeadersPrinted);
            numLeadersPrinted++;
        }
        //alphabetically sort leaders for home team
        Collections.sort(secondListHome);
        //walk list and print
        for (String s : secondListHome) {
            printDataLine(s, Second,
                    actionType, numLeadersPrinted);
            numLeadersPrinted++;
        }

        // We are done printing the first and second leaders;
        // Check if we need print additional leaders
        if (numOfTopLeaders >= 3) {
            // we are done!
            return;
        }

        // Now print the third leader list
        // Check who in the list has the value matching the Third Max

        // Only Print the Third Leader if Second is not tied with Third; otherwise we will double print
        if ((Third != -1) && (Third != Second)) {
            //int numOfThirdLeaders = 0;
            for (Map.Entry<String, Player> entry : set) {
                Player currentPlayer = entry.getValue();
                valueForCurrentPlayer = Player.findValue(currentPlayer, actionType);
                // Do we have a match?
                if (Math.abs(valueForCurrentPlayer - Third) <= 0.00000001) {
                    // This Player is a leader; print the leader info
                    //if true the player is a part of the away team and will be displayed first
                    if (currentPlayer.getTeam().equals("A")) {
                        //store the player in its designated location
                        thirdListAway.add(currentPlayer.getName());
                    }

                    //if true the player is a part of the away home and will be displayed second (after all away team players)
                    if (currentPlayer.getTeam().equals("H")) {
                        //store the player in its designated location
                        thirdListHome.add(currentPlayer.getName());
                    }
                    //increment number of leaders
                    numOfTopLeaders++;

                }
            }
        }

        // Display Second Leaders
        if ((Third != -1) && (Third != Second)) {
            //if this is true we will reset number of leaders printed to ensure correct outputting format
            numLeadersPrinted = 0;
            System.out.println();
        }

        //alphabetically sort the linklist containing all third place away team leaders
        Collections.sort(thirdListAway);

        // walk list and call print data line to print all leaders in a list separated by commas
        for (String s : thirdListAway) {
            printDataLine(s, Third,
                    actionType, numLeadersPrinted);
            //increment number of leaders printed (used to format correctly)
            numLeadersPrinted++;
        }
        //alphabetically sort the linklist containing all third place home team leaders
        Collections.sort(thirdListHome);
        for (String s : thirdListHome) {
            // walk list and call print data line to print all leaders in a list separated by commas
            printDataLine(s, Third,
                    actionType, numLeadersPrinted);
            //increment number of leaders printed (used to format correctly)
            numLeadersPrinted++;
        }
    }

    /*
    This is a helper function to print player's data based on the given Action Type

    INPUT:
    name: Player's Name
    value: Data value to be printed in the desired format
    actionType: Type of data passed
    linePosition: Line number which influences the output format

    OUTPUT:
    None
    */
    public static void printDataLine(String name, double value,
                                     ActionType actionType,
                                     int linePosition) {

        //this switch structure will use action type to determine the correct formatting for the leader list
        switch (actionType) {
            // Print data line based on the data type
            case BATTING_AVERAGE:
                // Fall Through
            case ON_BASE_PERCENTAGE:
                // Print data line based on the line number

                if (linePosition == 0) {
                    //first entry doesnt start with comma
                    System.out.print((String.format("%.3f", value)) + "\t" + name);
                } else {
                    //not first entry need comma
                    System.out.print(", " + name);
                }
                break;

            case HITS:
                // Fall Through
            case WALKS:
                // Fall Through
            case STRIKEOUTS:
                // Fall Through
            case HIT_BY_PITCH:
                // Print data line based on the line number
                if (linePosition == 0) {
                    //first entry doesnt start with comma
                    System.out.print((String.format("%.0f", value)) + "\t" + name);
                } else {
                    //not first entry need comma
                    System.out.print(", " + name);
                }
                break;

                //if we make it here a invalid action type was passed in
            default:
                break;
        }
    }

    /*
 This is a wrapper function to
 - print player's data using helper functions
 - calls helper function for each category

 INPUT:
 Playermap Hashmap

 OUTPUT:
 None
 */
    public static void printLeader(HashMap playerMap){
        // Find and display leaders for Batting average
        findAndDisplayLeadersByMax(playerMap, ActionType.BATTING_AVERAGE);
        //add new line for formatting purposes
        System.out.print("\n");

        // Find and display leaders for OB %
        findAndDisplayLeadersByMax(playerMap, ActionType.ON_BASE_PERCENTAGE);
        //add new line for formatting purposes
        System.out.print("\n");

        // Find and display leaders for Hits
        findAndDisplayLeadersByMax(playerMap, ActionType.HITS);
        //add new line for formatting purposes
        System.out.print("\n");

        // Find and display leaders for Walks
        findAndDisplayLeadersByMax(playerMap, ActionType.WALKS);
        //add new line for formatting purposes
        System.out.print("\n");

        // Find and display leaders for STRIKEOUTS
        findAndDisplayLeadersByMin(playerMap, ActionType.STRIKEOUTS);
        //add new line for formatting purposes
        System.out.print("\n");

        // Find and display leaders for HIT_BY_PITCH
        findAndDisplayLeadersByMax(playerMap, ActionType.HIT_BY_PITCH);
        //add new line for formatting purposes
        System.out.print("\n");
    }

    /*
    This is a helper function to print banner for a given Action Type

    INPUT:
    actionType: Type of banner to be printed

    OUTPUT:
    None
    */
    public static void printBanner(ActionType actionType) {

        // Print banner based on the requested type
        //this switch structure uses the action type passed in to determine which banner to print
        switch (actionType) {

            //if true we will print the BA banner
            case BATTING_AVERAGE:
                System.out.print("BATTING AVERAGE\n");
                break;

            //if true we will print the OB% banner
            case ON_BASE_PERCENTAGE:
                System.out.print("\nON-BASE PERCENTAGE\n");
                break;

            //if true we will print the hit banner
            case HITS:
                System.out.print("\nHITS\n");
                break;

            //if true we will print the walk banner
            case WALKS:
                System.out.print("\nWALKS\n");
                break;

            //if true we will print the STRIKEOUTS banner
            case STRIKEOUTS:
                System.out.print("\nSTRIKEOUTS\n");
                break;

            //if true we will print the HIT_BY_PITCH banner
            case HIT_BY_PITCH:
                System.out.print("\nHIT BY PITCH\n");
                break;

                //if we end up here it means an invalid action was passed in
            default:
                break;
        }
    }

    /*
This is a helper function to
- Find leaders in the link list by maximum value
- print player's data using helper functions
- It display the top 3 leaders for the given category

INPUT:
actionType: Type of data passed

OUTPUT:
None
*/
     public static void findAndDisplayLeadersByMin(HashMap playerMap, ActionType actionType) {

         // Set all mins to invalid values
         double First = 64000;
         double Second = 64000;
         double Third = 64000;
         double valueForCurrentPlayer;
         int numOfTopLeaders = 0;


         //use array list to store away and home team players for each position (will be used for outputting)
         ArrayList<String> firstListAway
                 = new ArrayList<>(5);
         ArrayList<String> firstListHome
                 = new ArrayList<>(5);
         ArrayList<String> secondListAway
                 = new ArrayList<>(5);
         ArrayList<String> secondListHome
                 = new ArrayList<>(5);
         ArrayList<String> thirdListAway
                 = new ArrayList<>(5);
         ArrayList<String> thirdListHome
                 = new ArrayList<>(5);

         // Printer Leader Banner based on the data type
         printBanner(actionType); // displayType is an ennum

         // Walk the table
         Set<Map.Entry<String, Player>> set = playerMap.entrySet();
         for (Map.Entry<String, Player> entry : set) {
             Player currentPlayer = entry.getValue();

             // Find value for the current Player
             valueForCurrentPlayer = Player.findValue(currentPlayer, actionType);
             //update first second and third leaders
             if (valueForCurrentPlayer < First) {
                 // current Player is higher than current First min
                 Third = Second;
                 Second = First;
                 First = valueForCurrentPlayer;
             } else if (valueForCurrentPlayer < Second) {
                 // current Player is higher than current Second min
                 Third = Second;
                 Second = valueForCurrentPlayer;
             } else if (valueForCurrentPlayer < Third) {
                 // current Player is higher than current Third min
                 Third = valueForCurrentPlayer;
             }
         }

         // First print the First min Leaders
         // Check who in the list has the value matching the First min
         for (Map.Entry<String, Player> entry : set) {
             Player currentPlayer = entry.getValue();
             valueForCurrentPlayer = Player.findValue(currentPlayer, actionType);
             // Do we have a match?
             if (Math.abs(valueForCurrentPlayer - First) <= 0.00000001) {
                 // This Player is a leader; print the leader info
                 //if true the player is a part of the away team and will be displayed first
                 if (currentPlayer.getTeam().equals("A")) {
                     //store the player in its designated location
                     firstListAway.add(currentPlayer.getName());
                 }

                 //if true the player is a part of the away home and will be displayed second (after all away team players)
                 if (currentPlayer.getTeam().equals("H")) {
                     //store the player in its designated location
                     firstListHome.add(currentPlayer.getName());
                 }
                 //increment number of leaders
                 numOfTopLeaders++;
             }
         }
         int numLeadersPrinted = 0;

         // Display First Leaders
         //alphabetically sort the linklist containing all first place away team leaders
         Collections.sort(firstListAway);
         // walk list and call print data line to print all leaders in a list sperated by commas
         for (String s : firstListAway) {
             printDataLine(s, First,
                     actionType, numLeadersPrinted);
             //increment number of leaders printed (used to format correctly)
             numLeadersPrinted++;
         }
         //alphabetically sort the linklist containing all first place home team leaders
         Collections.sort(firstListHome);
         // walk list and call print data line to print all leaders in a list sperated by commas
         for (String s : firstListHome) {
             printDataLine(s, First,
                     actionType, numLeadersPrinted);
             //increment number of leaders printed (used to format correctly)
             numLeadersPrinted++;
         }

         // We are done printing the first leader;
         // Check if we need print additional leaders
         if (numOfTopLeaders >= 3) {
             // we are done!
             return;
         }

         // Now print the Second min Leaders
         // Check who in the list has the value matching the Second min

         // Only Print the Second Leader if First is not tied with Second; otherwise we will double print
         if ((Second != 64000) && (Second != First)) {

             //walk the table
             for (Map.Entry<String, Player> entry : set) {
                 Player currentPlayer = entry.getValue();
                 valueForCurrentPlayer = Player.findValue(currentPlayer, actionType);

                 // Do we have a match?
                 if (Math.abs(valueForCurrentPlayer - Second) <= 0.00000001) {
                     // This Player is a leader; print the leader info
                     // This Player is a leader; print the leader info

                     //if true player is from away team act accordingly
                     if (currentPlayer.getTeam().equals("A")) {
                         secondListAway.add(currentPlayer.getName());
                     }

                     //if true player is from home team act accordingly
                     if (currentPlayer.getTeam().equals("H")) {
                         secondListHome.add(currentPlayer.getName());
                     }
                     // Save counters for printing
                     numOfTopLeaders++;
                     //numOfSecondLeaders++;
                 }
             }
         }

         //placed to prevent formatting issues
         if ((Second != 64000) && (Second != First)) {
             numLeadersPrinted = 0;
         }
         // Display First Leaders
         //alphabetically sort list
         Collections.sort(secondListAway);
         //walk list and print for away team
         for (String s : secondListAway) {
             printDataLine(s, Second,
                     actionType, numLeadersPrinted);
             numLeadersPrinted++;
         }
         //alphabetically sort list for home team
         Collections.sort(secondListHome);
         //walk list and print
         for (String s : secondListHome) {
             printDataLine(s, Second,
                     actionType, numLeadersPrinted);
             numLeadersPrinted++;
         }

         // We are done printing the first and second leaders;
         // Check if we need print additional leaders
         if (numOfTopLeaders >= 3) {
             // we are done!
             return;
         }

         // Now print the third leader list
         // Check who in the list has the value matching the Third min

         // Only Print the Third Leader if Second is not tied with Third; otherwise we will double print
         if ((Third != 64000) && (Third != Second)) {

             //walk map
             for (Map.Entry<String, Player> entry : set) {
                 Player currentPlayer = entry.getValue();
                 valueForCurrentPlayer = Player.findValue(currentPlayer, actionType);

                 // Do we have a match?
                 if (Math.abs(valueForCurrentPlayer - Third) <= 0.00000001) {
                     // This Player is a leader; print the leader info

                     //if true player is from away team act accordingly
                     if (currentPlayer.getTeam().equals("A")) {
                         thirdListAway.add(currentPlayer.getName());
                     }

                     //if true player is from home team act accordingly
                     if (currentPlayer.getTeam().equals("H")) {
                         thirdListHome.add(currentPlayer.getName());
                     }
                     // Save counters for printing
                     numOfTopLeaders++;

                 }
             }
         }

         // No more leaders to print
    // Display First Leaders

         //placed to prevent formatting issues
         if (Third != Second) {
             numLeadersPrinted = 0;
         }

         //alphabetically sort list for away team
         Collections.sort(thirdListAway);
         //walk and print list
         for (String s : thirdListAway) {
             printDataLine(s, Third,
                     actionType, numLeadersPrinted);
             numLeadersPrinted++;
         }
         //alphabetically sort list for home team
         Collections.sort(thirdListHome);
         //walk and print list
         for (String s : thirdListHome) {
             printDataLine(s, Third,
                     actionType, numLeadersPrinted);
             numLeadersPrinted++;
         }
     }
}