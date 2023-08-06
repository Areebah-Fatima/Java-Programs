//By: Areebah Fatima (AXF190025)

// Generic Header (thats extends comparable (will need later on)) to make node class generic(take all types)
public class Node <T extends Comparable<T>> {
    // Right node for Binary Tree placeholder (right pointer)
    public Node<T> right;

    // Left node for Binary Tree placeholder (left pointer)
    public Node<T> left;

    //Generic reference variable to hold data
    T key;

    // This is a non-default constructor
    public Node(T payload) {

        this.left = null;

        this.right = null;
        //polynomial "carrier"
        this.key = payload;
    }

    // this is the getter for getNodeKey
    // this getter's main purpose is to retrieve the node key stored in the tree when called to do so
    // INPUT: None
    // OUTPUT: key
    // of generic type
    //
    public T getNodeKey() {
        // returns the value the caller wishes to receive (in this case it will be the key)
        return this.key;
    }

    // this is the setter for getNextNode
    // this setter's main purpose is to "store" the key in a node in the tree when called to do so
    // INPUT: generic key (our data)
    // OUTPUT: None
    //
    public void setNodeKey(T key) {
        //returns the value the caller wishes to set (in this case it will be the key)
        this.key = key;
    }

}
