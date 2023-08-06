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

/*
   This is Link List Class
   It implements functionality of a singly link list
   It maintains the nodes in lexicographical Order
   The key for the sorting is the name in the Node class

 */
public class LinkList {

    // The head of the link list is private to the class
    // It is kept private to stop someone else to delete the head by accident
    private Node head;

    // This is the default constructor
    public LinkList() {
        head = null;
    }

    /*
    This method is used to insert a node into the Link List
    The nodes are inserted in lexicographical Order
    The key for the sorting is the name in the Node class

    Input:
    The function takes Node to be inserted as INPUT

    Output:
    The function does not retun anything

    */
    public void insertNodeInLexicographicalOrder(Node newNode) {
        // Make sure the newNode is valid
        if (newNode == null)
            return;

        // Is list empty?
        if (head == null) {
            // we are creating a new list
            head = newNode;
            head.setNextNode(null);
            // we are done!
            return;
        }

        // Is newNodeName is alphabetically before the head node name
        if (((head.getNodeName()).compareTo((newNode.getNodeName()))) > 0) {
            //inserting at the start of the list
            newNode.setNextNode(head);
            head = newNode;
            // we are done
            return;
        }

        // newNodeName is alphabetically after the head node name
        if (((head.getNodeName()).compareTo((newNode.getNodeName()))) < 0) {
            // Walk the link list to find the place where the new node should be inserted
            Node current = head;
            while (current.getNextNode() != null) {
                // Compare the new Node with the next node
                Node nextNode = current.getNextNode();
                String nextNodeName = nextNode.getNodeName();
                // Compare new node name with the next node name
                if (nextNodeName.compareTo((newNode.getNodeName())) < 0) {
                    // Continue walking
                    current = current.getNextNode();
                } else {
                    break;
                }
            }

            // Insert the new node AFTER the Current node
            // This also takes care of insertion at the end of the list
            newNode.setNextNode(current.getNextNode());
            current.setNextNode(newNode);
        }
    }

    /*
    This function is used to search the node in the link list by name

    INPUT:
    Name of the node being searched

    OUTPUT:
    Node that is being searched
    It returns null is the node is not found
    */
    public Node SearchNodeByName(String name) {
        Node current;
        Node nextNode = null;
        // Check if list is empty
        if (head == null)
            return null;

        // List is not empty
        // Do we have a match with the head node?
        if ((head.getNodeName()).compareTo(name) == 0) {
            // We are deleting the head node
            return head;
        }

        // The node to be deleted is either in the middle or the tail node
        // Walk the link list to locate the node to be deleted
        current = head;
        boolean matchFound = false;
        while (current.getNextNode() != null) {
            nextNode = current.getNextNode();
            if ((nextNode.getNodeName()).compareTo(name) == 0) {
                // A match is found
                matchFound = true;
                break;
            }
            // continue the walk
            current = current.getNextNode();
        }

        if (!matchFound) {
            // No matching node found; nothing to delete
            return null;
        }

        // A matching entry is found; nextNode is the matching entry
        return nextNode;
    }

    /*
    This is the helper function to display/calculate the player data
    It performs the following functions
       - Displays the player name
       - calculates the on base percentage and batting average
       - error test the calculations for dealing with 0 over 0 division
       - Displays player data in desired format

    INPUT:
    Node who's information needs to be printed

    OUTPUT:
    None
    */
    public static void displayPlayerData(Node node) {
        double battingAverage;
        int atBats;
        int plateAppearances;

        // use the following if statement to prevent 0 by 0 division issues
        if(node.getNodeHitCount()+ node.getNodeOutCount()+ node.getNodeStrikeoutCount() !=0) {
            //calculates batting average
            battingAverage = ((node.getNodeHitCount() * 1.0) / (node.getNodeHitCount() + node.getNodeOutCount() +
                    node.getNodeStrikeoutCount()));

        }else{

            // sets batting average to 0 when there is 0 by 0 division
            battingAverage = 0;
        }

        atBats= (node.getNodeHitCount() + node.getNodeOutCount() + node.getNodeStrikeoutCount());
        plateAppearances = (node.getNodeHitCount()+node.getNodeOutCount()+node.getNodeStrikeoutCount()+
                node.getNodeWalkCount()+node.getNodeHitByPitchCount()+node.getNodeSacrificeCount());
        double OB;

        // use the following if statement to prevent 0 by 0 division issues
        if(plateAppearances != 0) {
            //calculates on base percentage
            OB = ((node.getNodeHitCount() + node.getNodeWalkCount() +
                    node.getNodeHitByPitchCount()) * 1.0 / (plateAppearances));
        } else {
            // sets on base percentage to 0 when there is 0 by 0 division
            OB = 0;
        }

        //print out player name
        System.out.print(node.getNodeName()+"\t");

        // displays on node data in the desired format
        System.out.print(atBats+"\t");
        System.out.print(node.getNodeHitCount()+"\t");
        System.out.print(node.getNodeWalkCount()+"\t");
        System.out.print(node.getNodeStrikeoutCount()+"\t");
        System.out.print(node.getNodeHitByPitchCount()+"\t");
        System.out.print(node.getNodeSacrificeCount()+"\t");
        System.out.print((String.format("%.3f", battingAverage)+"\t"));
        System.out.print((String.format("%.3f", OB))+"\n");
    }

    /*
        This is wrapper for the recursive function to display/calculate the
        player data for all nodes in the link list.

        INPUT:
        None

        OUTPUT:
        None
        */
    public void printLinkList()
    {
        print(head);
    }

    /*
        This is a recursive function to display/calculate the
        player data for all nodes in the link list.
        It recursively calls helper function to
           - Displays the player name
           - calculates the on base percentage and batting average
           - error test the calculations for dealing with 0 over 0 division
           - Displays player data in desired format

        INPUT:
        Node who's data needs to be printed

        OUTPUT:
        None
    */
    private void print(Node node) {
        if (node != null) {
            // Call helper function to calculate and display players data in desired format
            displayPlayerData(node);

            print(node.getNextNode());
        }
    }

    /*
    This is a wrapper function to calculate or get desired data for a given Node
    It calls helper function to
       - calculates the on base percentage and batting average
       - Displays player data in desired format

    INPUT:
    node: Node who's data needs to be obtained
    actionType: Type of data being requested

    OUTPUT:
    double: The data being requested
    */
    public double findValueByNode(Node node, ActionType actionType) {
        double value = 0;
        // Call helper function to calculate or get the desired data
        switch (actionType) {
            case BATTING_AVERAGE:
                value = findBattingAverageByNode(node);
                break;

            case ON_BASE_PERCENTAGE:
                value = findOnBasePercentageByNode(node);
                break;

            case HITS:
                value = node.getNodeHitCount();
                break;

            case WALKS:
                value = node.getNodeWalkCount();
                break;

            case STRIKEOUTS:
                value = node.getNodeStrikeoutCount();
                break;

            case HIT_BY_PITCH:
                value = node.getNodeHitByPitchCount();
                break;

            default:
                break;
        }
        // return value obtained from the helper function
        return value;
    }

    /*
    This is a helper function to calculate batting average for the given Node

    INPUT:
    node: Node who's data needs to be obtained

    OUTPUT:
    double: The batting average
    */
    public double findBattingAverageByNode(Node node) {
        // Init Batting Average
        double battingAverage = 0;
        if (node == null)
            return battingAverage;

        // Compute Batting Average for the Node
        // use the following if statement to prevent 0 by 0 division issues
        if (node.getNodeHitCount() + node.getNodeOutCount() + node.getNodeStrikeoutCount() != 0) {
            //calculates batting average
            battingAverage = ((node.getNodeHitCount() * 1.0) / (node.getNodeHitCount() +
                    node.getNodeOutCount() + node.getNodeStrikeoutCount()));

        } else {
            //sets batting average to 0 when there is 0 by 0 division
            battingAverage = 0;
        }
        return battingAverage;
    }

    /*
    This is a helper function to calculate On Base Percentage for the given Node

    INPUT:
    node: Node who's data needs to be obtained

    OUTPUT:
    double: The On Base Percentage
    */
    public double findOnBasePercentageByNode(Node node) {

        double OB = 0;

        if (node == null)
            return OB;

        int plateAppearances = (node.getNodeHitCount() + node.getNodeOutCount() + node.getNodeStrikeoutCount() +
                node.getNodeWalkCount() + node.getNodeHitByPitchCount() + node.getNodeSacrificeCount());

        //use the following if statement to prevent 0 by 0 division issues
        if (plateAppearances != 0) {
            //calculates on base percentage
            OB = ((node.getNodeHitCount() + node.getNodeWalkCount() +
                    node.getNodeHitByPitchCount()) * 1.0 / (plateAppearances));
        } else {
            //sets on base percentage to 0 when there is 0 by 0 division
            OB = 0;
        }

        return OB;
    }

    /*
    This is a helper function to print banner for a given Action Type

    INPUT:
    actionType: Type of banner to be printed

    OUTPUT:
    None
    */
    public void printBanner(ActionType actionType) {

        // Print banner based on the requested type
        switch (actionType) {

            case BATTING_AVERAGE:
                System.out.print("BATTING AVERAGE\n");
                break;

            case ON_BASE_PERCENTAGE:
                System.out.print("ON-BASE PERCENTAGE\n");
                break;

            case HITS:
                System.out.print("HITS\n");
                break;

            case WALKS:
                System.out.print("WALKS\n");
                break;

            case STRIKEOUTS:
                System.out.print("STRIKEOUTS\n");
                break;

            case HIT_BY_PITCH:
                System.out.print("HIT BY PITCH\n");
                break;

            default:
                break;
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
    public void printDataLine(String name, double value,
                              ActionType actionType,
                              int linePosition) {

        switch (actionType) {
            // Print data line based on the data type
            case BATTING_AVERAGE:
            // Fall Through
            case ON_BASE_PERCENTAGE:
                // Print data line based on the line number
                if (linePosition == 0) {
                    System.out.print((String.format("%.3f", value)) + "\t" + name);
                } else {
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
                    System.out.print((String.format("%.0f", value)) + "\t" + name);
                } else {
                    System.out.print(", " + name);
                }
                break;

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
    public void findAndDisplayLeadersByMax(ActionType actionType) {

        // Set the first max, second mac and third max value to invalid Max
        double First = -1;
        double Second = -1;
        double Third = -1;
        double valueForNextNode;
        int numOfTopLeaders = 0;

        // Printer Leader Banner based on the data type
        printBanner(actionType); // displayType is an ennum

        // Walk the link list to find the head
        Node currentNode = head;
        if (head == null)
            return;

        // Walk the link list
        while (currentNode != null) {
            // Find value for the current node
            valueForNextNode = findValueByNode(currentNode, actionType);
            if (valueForNextNode > First) {
                // current node is higher than current First Max
                Third = Second;
                Second = First;
                First = valueForNextNode;
            } else if (valueForNextNode > Second) {
                // current node is higher than current Second Max
                Third = Second;
                Second = valueForNextNode;
            } else if (valueForNextNode > Third) {
                // current node is higher than current Third Max
                Third = valueForNextNode;
            }
            // Go to the next node in the link list
            currentNode = currentNode.getNextNode();
        }

        // First print the First Max Leaders
        // Check who in the list has the value matching the First Max
        currentNode = head;
        // Walk the link list
        while (currentNode != null) {
            valueForNextNode = findValueByNode(currentNode, actionType);
            // Do we have a match?
            if (Math.abs(valueForNextNode - First) <= 0.00000001) {
                // This node is a leader; print the leader info
                printDataLine(currentNode.getNodeName(), First,
                        actionType, numOfTopLeaders);
                numOfTopLeaders++;
            }
            // Get the next node
            currentNode = currentNode.getNextNode();
        }
        System.out.print("\n");

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
            currentNode = head;
            int numOfSecondLeaders = 0;
            // Walk the link list
            while (currentNode != null) {
                valueForNextNode = findValueByNode(currentNode, actionType);
                // Do we have a match?
                if (Math.abs(valueForNextNode - Second) <= 0.00000001) {
                    // This node is a leader; print the leader info
                    printDataLine(currentNode.getNodeName(), Second,
                            actionType, numOfSecondLeaders);
                    // Save counters for printing
                    numOfTopLeaders++;
                    numOfSecondLeaders++;
                }
                // Get the next node
                currentNode = currentNode.getNextNode();
            }
            System.out.println();
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
            currentNode = head;
            int numOfThirdeaders = 0;
            while (currentNode != null) {
                valueForNextNode = findValueByNode(currentNode, actionType);
                // Do we have a match?
                if (Math.abs(valueForNextNode - Third) <= 0.00000001) {
                    // This node is a leader; print the leader info
                    printDataLine(currentNode.getNodeName(), Third,
                            actionType, numOfThirdeaders);
                    // Save line number for printing
                    numOfTopLeaders++;
                    numOfThirdeaders++;
                }
                currentNode = currentNode.getNextNode();
            }
            System.out.println();
        }
        // No more leaders to print
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
    public void findAndDisplayLeadersByMin(ActionType actionType) {
        // Set all mins to invalid values
        double First = 64000;
        double Second = 64000;
        double Third = 64000;
        double valueForNextNode;
        int numOfTopLeaders = 0;

        printBanner(actionType); // displayType is an ennum

        // Walk the link list to find the top Batting Leaders
        Node currentNode = head;
        if (head == null)
            return;

        // Walk the list
        while (currentNode != null) {
            valueForNextNode = findValueByNode(currentNode, actionType);
            if (valueForNextNode < First) {
                Third = Second;
                Second = First;
                First = valueForNextNode;
            } else if (valueForNextNode < Second) {
                Third = Second;
                Second = valueForNextNode;
            } else if (valueForNextNode < Third) {
                Third = valueForNextNode;
            }
            currentNode = currentNode.getNextNode();
        }

        // Check who in the list has the value matching the First
        currentNode = head;
        while (currentNode != null) {
            valueForNextNode = findValueByNode(currentNode, actionType);
            if (Math.abs(valueForNextNode - First) <= 0.00000001) {
                // This node is a leader; save the leader info
                printDataLine(currentNode.getNodeName(), First,
                        actionType, numOfTopLeaders);
                numOfTopLeaders++;
            }
            currentNode = currentNode.getNextNode();
        }
        System.out.print("\n");

        // We are done printing the first leader;
        // Check if we need print additional leaders
        if (numOfTopLeaders >= 3) {
            // we are done!
            System.out.print("\n");
            return;
        }

        // Print the second leader list
        // Only Print the Second Leader if First is not tied with Second; otherwise we will double print
        if ((Second != 64000) && (Second != First)) {
            currentNode = head;
            int numOfSecondLeaders = 0;
            while (currentNode != null) {
                valueForNextNode = findValueByNode(currentNode, actionType);
                // Does value match
                if (Math.abs(valueForNextNode - Second) <= 0.00000001) {
                    // This node is a leader; save the leader info
                    printDataLine(currentNode.getNodeName(), Second,
                            actionType, numOfSecondLeaders);
                    numOfTopLeaders++;
                    numOfSecondLeaders++;
                }
                currentNode = currentNode.getNextNode();
            }
            System.out.println();
        }

        // We are done printing the first and second leaders;
        // Check if we need print additional leaders
        if (numOfTopLeaders >= 3) {
            // we are done!
            return;
        }

        // Print the third leader list
        // Only Print the Third Leader if Second is not tied with Third; otherwise we will double print
        if ((Third != 64000) && (Third != Second)) {
            currentNode = head;
            int numOfThirdeaders = 0;
            while (currentNode != null) {
                valueForNextNode = findValueByNode(currentNode, actionType);
                // Does value match?
                if (Math.abs(valueForNextNode - Third) <= 0.00000001) {
                    // This node is a leader; print the leader info
                    printDataLine(currentNode.getNodeName(), Third,
                            actionType, numOfThirdeaders);
                    numOfTopLeaders++;
                    numOfThirdeaders++;
                }
                currentNode = currentNode.getNextNode();
            }
            System.out.println();
        }
        // No more leaders to print
    }

    /*
     This is a wrapper function to
     - print player's data using helper functions
     - calls helper function for each category

     INPUT:
     None

     OUTPUT:
     None
     */
    public void printLeader(){
        // Find and display leaders for Batting average
        findAndDisplayLeadersByMax(ActionType.BATTING_AVERAGE);
        System.out.print("\n");

        // Find and display leaders for OB %
        findAndDisplayLeadersByMax(ActionType.ON_BASE_PERCENTAGE);
        System.out.print("\n");

        // Find and display leaders for Hits
        findAndDisplayLeadersByMax(ActionType.HITS);
        System.out.print("\n");

        // Find and display leaders for Walks
        findAndDisplayLeadersByMax(ActionType.WALKS);
        System.out.print("\n");

        // Find and display leaders for STRIKEOUTS
        findAndDisplayLeadersByMin(ActionType.STRIKEOUTS);
        System.out.print("\n");

        // Find and display leaders for HIT_BY_PITCH
        findAndDisplayLeadersByMax(ActionType.HIT_BY_PITCH);
        System.out.print("\n");
    }

}
