
//this node class purpose is to store and retrieve data so that it can be used by other classes
public class Node {

//below we have simple variable declarations for any variable we need

    // this string holds our player's name
    public String name;

    // This int holds Hit Count
    public int hitCount;

    // This int holds Out Count
    public int outCount;

    // This int holds strike out Count
    public int strikeoutCount;

    // This int holds Walk  Count
    public int walkCount;

    // This int holds hit By Pitch Count
    public int hitByPitchCount;

    // This int holds Sacrifice Count

    public int sacrificeCount;

    // this is a reference to node object that will allow us to keep track of the next node in the link list
    public Node next;

    // this is the getter for getNextNode
    // this getter's main purpose is to retrieve the next node in a list when called to do so
    // INPUT: None
    // OUTPUT: Next Node
    //
    public Node getNextNode() {
        // returns the value the caller wishes to receive (in this case it will be the next node)
        return this.next;
    }

    // this is the setter for getNextNode
    // this setter's main purpose is to "store" the next node in a list when called to do so
    // INPUT: Node
    // OUTPUT: None
    //
    public void setNextNode(Node nextNode){
        //returns the value the caller wishes to set (in this case it will be the next node)
        this.next = nextNode;
    }

    // this is the setter for setNodeName
    // this setter's main purpose is to "store" the node name in a list when called to do so
    // INPUT: Name
    // OUTPUT: None
    //
    public void setNodeName (String name) {
        //returns the value the caller wishes to set (in this case it will be the node name)
        this.name = name;
    }

    // this is the getter for getNodeName
    // this getter's main purpose is to retrieve the player's/nodes name in a list when called to do so
    // INPUT: None
    // OUTPUT: Name
    //
    public String getNodeName () {
            // returns the value the caller wishes to receive (in this case it will be the node name)
            return (this.name);
    }

    //the next few getters and setters will be used in this program to store and retrieve the stats calculated in main

    // this is the setter for setNodeHitCount
    // this setter's main purpose is to "store" the node hit count in a list when called to do so
    // INPUT: Hit Count
    // OUTPUT: None
    //
    public void setNodeHitCount (int hitCount) {
        //returns the value the caller wishes to set (in this case it will be the node hit count)
        this.hitCount = hitCount;
    }

    // this is the getter for getNodeName
    // this getter's main purpose is to retrieve the nodes hit count in a list when called to do so
    // INPUT: None
    // OUTPUT: Hit Count
    //
    public int getNodeHitCount () {
        // returns the value the caller wishes to receive (in this case it will be the node hit count)
        return (this.hitCount);

    }

    // this is the setter for setNodeOutCount
    // this setter's main purpose is to "store" the node out count in a list when called to do so
    // INPUT: out count
    // OUTPUT: None
    //
    public void setNodeOutCount (int outCount) {

        //returns the value the caller wishes to set (in this case it will be the node out count)
        this.outCount = outCount;
    }

    // this is the getter for getNextNode
    // this getter's main purpose is to retrieve the node out count in a list when called to do so
    // INPUT: None
    // OUTPUT: Out Count
    //
    public int getNodeOutCount () {
        // returns the value the caller wishes to receive (in this case it will be the node out count)
        return (this.outCount);
    }

    // this is the setter for setNodeStrikeoutCount
    // this setter's main purpose is to "store" the node out count in a list when called to do so
    // INPUT: strikeoutCount
    // OUTPUT: None
    //
    public void setNodeStrikeoutCount (int strikeoutCount) {

        // returns the value the caller wishes to set (in this case it will be the node strikeout count)
        this.strikeoutCount = strikeoutCount;
    }

    // this is the getter for getNodeStrikeoutCount
    // this getter's main purpose is to retrieve the node strikeout count in a list when called to do so
    // INPUT: None
    // OUTPUT: strikeoutCount
    //
    public int getNodeStrikeoutCount () {
        // returns the value the caller wishes to receive (in this case it will be the node strikeout count)
        return (this.strikeoutCount);
    }

    // this is the setter for setNodeWalkCount
    // this setter's main purpose is to "store" the node walk count in a list when called to do so
    // INPUT: Walk Count
    // OUTPUT: None
    //
    public void setNodeWalkCount (int walkCount) {
        //returns the value the caller wishes to set (in this case it will be the node walk count)
        this.walkCount = walkCount;
    }

    // this is the getter for getNodeWalkCount
    // this getter's main purpose is to retrieve the node walk count in a list when called to do so
    // INPUT: None
    // OUTPUT: walkCount
    //
    public int getNodeWalkCount () {
            // returns the value the caller wishes to receive (in this case it will be the node walk count)
            return (this.walkCount);
    }

    // this is the setter for setNodeHitByPitchCount
    // this setter's main purpose is to "store" the node hit by pitch count in a list when called to do so
    // INPUT: hitByPitchCount
    // OUTPUT: None
    //
    public void setNodeHitByPitchCount (int hitByPitchCount) {
        //returns the value the caller wishes to set (in this case it will be the node hit by pitch count)
        this.hitByPitchCount = hitByPitchCount;
    }

    // this is the getter for getNodeHitByPitchCount
    // this getter's main purpose is to retrieve the node hit by pitch count in a list when called to do so
    // INPUT: None
    // OUTPUT: hitByPitchCount
    //
    public int getNodeHitByPitchCount () {
        // returns the value the caller wishes to receive (in this case it will be the node hit by pitch count)
        return (this.hitByPitchCount);
    }


    // this is the setter for setNodeSacrificeCount
    // this setter's main purpose is to "store" the node scrafice count in a list when called to do so
    // INPUT: sacrificeCount
    // OUTPUT: None
    //
    public void setNodeSacrificeCount (int sacrificeCount) {
        //returns the value the caller wishes to set (in this case it will be the node sacrifice count)
        this.sacrificeCount = sacrificeCount;
    }

    // this is the getter for sacrificeCount
    // this getter's main purpose is to retrieve the node sacrificeCount in a list when called to do so
    // INPUT: None
    // OUTPUT: sacrificeCount
    public int getNodeSacrificeCount () {
        // returns the value the caller wishes to receive (in this case it will be the node sacrifice count)
        return (this.sacrificeCount);
    }
    
}
