/*
Below we declare the packages and APIs needed for this program
java.io will be used to process inputs and produce outputs
java.util.Scanner allows us to use the Scanner class to get user inputs */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

    /*Main Class
    Functions:
    - Main
    - ParseBattingRecord
    - DisplayPlayerData
    - FindLeader
    */

public class Main {

    /* This is the main function
       It performs the following functions
       * Declares the node and linked list object
       * Open an input file
       * It performs error checking for file opening
       * It reads the file until the EOF
       * It parses data stored (names) in the file
       * Stores names into nodes (while dealing with repeated names)
       * It calls insertNodeInLexicographicalOrder to order the linked list in alphabetical order
       * It parses data stored (the batting record) in the file
       * It calls ParseBattingRecord to perform desired calculations (keep track of scores)
       * It calls PrintLinkList to display the results in desired format
       * It closes the file
     */

    public static void main(String[] args) throws FileNotFoundException {

        /* Declaration a node object (newNode) that holds names which we will later pass to other functions
        (insertNodeInLexicographicalOrder, parseBattingRecord) */

        Node newNode;

        // Creates a LinkList object which will call the constructor in the LinkList class
        LinkList linkList = new LinkList();

        // Setup the scanner, so that we can retrieve data from the text file
        Scanner consoleIo = new Scanner (System.in);
        String fileTitle = consoleIo.nextLine();
        Scanner input = new Scanner(new File(fileTitle));

        // Read data stored in the file line-by-line (the while statement makes sure that we stop reading when we run out of data )
        while (input.hasNext()) {

            /* Parse name from file using substring function (these lines store a full line in a file and stores the
                first space delimited string as the name */
            String line = input.nextLine();
            String name = line.substring(0, line.indexOf(' '));

            // Call SearchNodeByName to see if the node already exists (this will deal with multiple player records)
            newNode = linkList.SearchNodeByName(name);

            // If SearchNodeByName sets newNode to null it means that there is no repeat so we can make a new node
            if (newNode == null) {
                // Create a node object named newNode
                newNode = new Node();

                // "Store" the player name in the newNode
                newNode.setNodeName(name);
                // Call insertNodeInLexicographicalOrder to insert newNode into the link list in alphabetical order
                linkList.insertNodeInLexicographicalOrder(newNode);
            }

            // Parse record from file using substring function (these lines store whatever comes after name until the next ' ' character)
            line = line.substring(line.indexOf(" "));
            // This parsed string will store the record of all moves made by a player
            String record = line;
            //newNode.printNode();

            // Calls parseBattingRecord to see read the string character by character and keeps track of each action made
            parseBattingRecord(newNode, record);
        }

        // Calls printLinkList to print linked list node by node in desired format (both name and stats)
        linkList.printLinkList();
        System.out.print("\nLEAGUE LEADERS\n");
        linkList.printLeader();
        // Close file
        input.close();
    }

    /* This function is used to parse the batting record, count each action, and stores the counts in nodes
            (we will later use this for calculations)

       It performs the following functions
       * Steps through the batting record string that has been passed from the main function character by character
       * Uses a switch structure to keep track of each valid action using counts
       * Stores the stats in the desired node for each player
    */

    public static void parseBattingRecord(Node node, String battingRecord)
    {

        //the following if statment makes sure that we dont start storing data into a list with no nodes
        if (node == null)
            return;

        //use the accessor for each action (declared in the node class) to set each count to its node
        int hitCount = node.getNodeHitCount(), outCount = node.getNodeOutCount(),strikeoutCount = node.getNodeStrikeoutCount(),
                walkCount = node.getNodeWalkCount(), hitByPitchCount = node.getNodeHitByPitchCount(),
                sacrificeCount = node.getNodeSacrificeCount();

        //The below lines calculate counters for each move

        //for loop to ensure that we dont go out of bounds (ie dont read beyond the batting record)
        for (int i = 1; i < battingRecord.length(); ++i) {

            //this switch structure functions using the character at each index in the batting record
            switch (battingRecord.charAt(i)) {

                //if the char at that position in the batting record is 'H' we will fall into this segment of code
                case 'H':

                    //add one to count to keep track of how many hits occur
                    hitCount++;
                    break;

                //if the char at that position in the batting record is 'O' we will fall into this segment of code
                case 'O':

                    //add one to count to keep track of how many outs occur
                    outCount++;
                    break;

                //if the char at that position in the batting record is 'K' we will fall into this segment of code
                case 'K':

                    //add one to count to keep track of how many strikes occur
                    strikeoutCount++;
                    break;

                //if the char at that position in the batting record is 'W' we will fall into this segment of code
                case 'W':

                    //add one to count to keep track of how many walks occur
                    walkCount++;
                    break;

                //if the char at that position in the batting record is 'P' we will fall into this segment of code
                case 'P':

                    //add one to count to keep track of how many hit by pitches occur
                    hitByPitchCount++;
                    break;

                //if the char at that position in the batting record is 'S' we will fall into this segment of code
                case 'S':

                    //add one to count to keep track of how many sacrifices occur
                    sacrificeCount++;
                    break;

                //includes a default scenario to make code less error prone
                //this default scenario will occur whenever a invalid character is found
                default:
                    break;
            }
        }

        //these last few lines perform the same operation but for different stats
        //these lines set the given stats calculated above in our switch structure to the node we are working with
        node.setNodeHitCount(hitCount);
        node.setNodeOutCount(outCount);
        node.setNodeStrikeoutCount(strikeoutCount);
        node.setNodeWalkCount(walkCount);
        node.setNodeHitByPitchCount(hitByPitchCount);
        node.setNodeSacrificeCount(sacrificeCount);


    }

}


