//By: Areebah Fatima (AXF190025)

/*
 * This class is used to implement binary tree
 *
 * Element: root
 *          root of the tree (Private)
 *
 */
public class BinTree <T extends Comparable<T>> {

    // Root of the binary tree
    private Node<T> root;

    // Default constructor
    BinTree() {
        //creates root node
        root = null;
    }

    /*
     * This the function to insert a node in the binary tree using the key in the node.
     * It is implemented using resursssive algorithm
     *
     * INPUT: nodeToBeInserted = node to be inserted
     *
     * OUTPUT: None
     *
     */
    public void Insert(Node<T> nodeToBeInserted) {

        root = insertNodeRecursive(root, nodeToBeInserted);
    }

    /*
    This method is used to implement a recursive insert of node into a binary tree
    The nodes are inserted by the key stored in the Payload

    Input:
       current Node: Current head of the subtree
       New Node: Node to be inserted

    Output:
       The function returns either:
          head of a subtree to be visited or
          the new node after insertion
    */
    private Node<T> insertNodeRecursive(Node<T> currentNode, Node<T> newNode) {
        if (currentNode == null) {
            return newNode;
        }

        if (currentNode.getNodeKey().compareTo(newNode.getNodeKey()) > 0) {
            // Insert the newNode the the "left" of the "current" node
            currentNode.left = insertNodeRecursive(currentNode.left, newNode);
        } else if (currentNode.getNodeKey().compareTo(newNode.getNodeKey()) < 0) {
            // Insert the newNode the the "right" of the "current" node
            currentNode.right = insertNodeRecursive(currentNode.right, newNode);
        } else {
            /* Node already exists
               This can only happen when we try to insert a new node with the same exponent.
               Combine the terms with same exponents
             */
            Payload payloadNewNode = (Payload) newNode.getNodeKey();
            Payload payloadCurrNode = (Payload) currentNode.getNodeKey();
            int newCofficient = payloadNewNode.getCoefficient() + payloadCurrNode.getCoefficient();
            payloadCurrNode.setCoefficient(newCofficient);
            return currentNode;
        }

        // Return head of the new subtree to be visited
        return currentNode;
    }

    /*
     * This the function for in order walk of the binary tree to compute the
     * integral of the string store in the binary tree.
     *
     * It walks the binary tree, recursively.
     *
     * It performs the following functions:
     * 1. Walk the binary tree in the order of highest key to lowest key value
     * 2. It starts the walk from root
     * 2. Recursively calls a integral string builder function  to build integral
     *    of the polynomial stored in the binary tree.
     *
     * INPUT: walkCount
     *    The function updates the walk count (highest key term will have walkcount of 0)
     *
     * OUTPUT:
     *    Integral of the polynomial stored in the binary tree is returned as a formatted string
     *
     */
    public String inorderWalkForIntegalString(int walkCount) {
        // String to contain integral of the polynomial stored in the binary tree
        StringBuilder IntegralStr = new StringBuilder();
        inorderIntegalStringRecursive(root, IntegralStr, walkCount);
        // Return string now contains integral of the polynomial stored in the binary tree
        return IntegralStr.toString();
    }

    /*
     * This the function recursively traverse the binary tree.
     *
     * It performs the following functions:
     * 1. Walk the binary tree in the order of highest key to lowest key value
     * 2. Calls a helper function to compute indefinate integral of the payload stored in the Node
     * 2. Recursively calls a integral string builder function  to build integral
     *    of the polynomial stored in the binary tree.
     *
     * INPUT:
     *    current Node: The current head of the subtree
     *    String: To hold incremental answer for integral of each node walked in order
     *            highest to lowest
     *    The function updates the walk count (highest key term will have walk count of 0)
     *
     * OUTPUT:
     *    Integral of the polynomial stored in the binary tree is returned as a formatted string
     *
     */
    private int inorderIntegalStringRecursive(Node<T> currentNode, StringBuilder IntegralStr, int walkCount) {
        if (currentNode != null) {
            // First Traversing Right tree to get to the node with highest key and then walk in order
            walkCount = inorderIntegalStringRecursive(currentNode.right, IntegralStr, walkCount);
            /* Reached a node with the next highest key
             * Compute the integral for the node in order, recursively
             * Append the integral result, in order.
             */
            IntegralStr.append(Main.computeIndefinateIntegralOfATerm(currentNode, walkCount));
            walkCount++;
            // Now Traversing left tree, in order
            walkCount = inorderIntegalStringRecursive(currentNode.left, IntegralStr, walkCount);
        }
        return walkCount;
    }

    /*
     * This the function for in order walk of the binary tree to compute the
     * "definite" integral of the string store in the binary tree over the
     * limit passed in by the caller.
     *
     * It walks the binary tree, recursively.
     *
     * It performs the following functions:
     * 1. Walk the binary tree in the order of highest key to lowest key value
     * 2. It starts the walk from root
     * 2. Recursively calls a function to find value of
     *    the definite integral of the polynomial stored in the binary tree
     *    over the passed limits
     *
     * INPUT:
     *    int: The Upper limit of the definite integral
     *    int: The Lower limit of the definite integral
     *    double: Where to store the answer of the definite integral
     *
     * OUTPUT: Double
     *    Value of the "definite" integral of the string store in the binary tree over the
     *    limit passed in by the caller.
     *
     */
    public double inorderWalkForDefinateIntegalAnswer(int upperLimit, int lowerLimit, double answer){
        answer = inorderDefinateIntegalAnswerRecursive(root, upperLimit, lowerLimit, answer);
        return answer;
    }

    /*
     * This the function recursively traverse the binary tree.
     *
     * It performs the following functions:
     * 1. Walk the binary tree in the order of highest key to lowest key value
     * 2. Calls a helper function to compute definite integral of the payload stored in the Node
     *    over the limit passed in
     * 3. Recursively adds the "definite" integral of the each node in the binary tree.
     *
     * INPUT:
     *    current Node: The current head of the subtree
     *    int: The Upper limit of the definite integral
     *    int: The Lower limit of the definite integral
     *    Answer: double to hold incremental answer for integral of each node walked in order
     *            highest to lowest
     *
     * OUTPUT:
     *    Integral of the polynomial stored in the binary tree is returned as a formatted string
     * OUTPUT: Double
     *    Value of the "definite" integral value of the node visited, in order.
     *
     */
    private double inorderDefinateIntegalAnswerRecursive(Node<T> currentNode,
                                                         int upperLimit, int lowerLimit, double answer) {
        //double answer = 0.0;
        if (currentNode != null) {
            // System.out.println("Traversing Right tree: @ Node "+ currentNode.getNodeString() + " with IntStr = " +  answer);
            answer = inorderDefinateIntegalAnswerRecursive(currentNode.right, upperLimit, lowerLimit, answer);
            answer += Main.computeDefinateIntegralOfATerm(currentNode, upperLimit, lowerLimit);
            // System.out.println("Traversing Left tree: @ Node "+ currentNode.getNodeString() + " with IntStr = " +  answer);
            answer = inorderDefinateIntegalAnswerRecursive(currentNode.left, upperLimit, lowerLimit, answer);
            //answer += Payload.computeDefinateIntegralOfATerm(currentNode, upperLimit, lowerLimit);

            // IntegralStr = IntegralStr + Payload.computeIndefinateIntegralOfATerm(currentNode);
            //IntegralStr.append(Payload.computeIndefinateIntegralOfATerm(currentNode));
        }
        return answer;
    }

    /*
     * This the function for in order walk of the binary tree to
     * print the polynomial terms stored in the binary tree
     *
     * It walks the binary tree, recursively.
     *
     * It performs the following functions:
     * 1. Walk the binary tree in the order of highest key to lowest key value
     * 2. It starts the walk from root
     * 2. Recursively calls a function to print the polynomial
     * terms stored in the binary tree
     *
     * INPUT:
     *    None
     *
     * OUTPUT:
     *    None
     *
     */
    public void printTree() {
        printTreeRecursive(root);
    }

    /*
     * This the function recursively traverse the binary tree to
     * print the polynomial terms stored in the binary tree.
     *
     * INPUT:
     *    current Node: The current head of the subtree
     *
     * OUTPUT:
     *    None
     * OUTPUT:
     *    None
     *
     */
    private void printTreeRecursive(Node<T> currentNode) {

        if (currentNode != null) {
            printTreeRecursive(currentNode.right);
            //Payload payload = (Payload) currentNode.getNodeKey();
            //System.out.println("Tree Print: Traversing Node " + payload.printPayload());
            printTreeRecursive(currentNode.left);
        }
    }

    /*
     * This the function for in order walk of the binary tree to
     * search for a node with the matching key
     *
     * It walks the binary tree, recursively.
     *
     * It performs the following functions:
     * 1. Walk the binary tree in the order of highest key to lowest key value
     * 2. It starts the walk from root
     * 2. Recursively calls a function to search the node with matching key
     *    stored in the binary tree
     *
     * INPUT:
     *    Key: Generic
     *
     * OUTPUT:
     *    Node: Generic
     *
     */
    Node<T> Search(T key) {
        //  System.out.println("Search starting from root for payload " + key.printPayload());
        return (SearchRecursive(root, key));
    }

    /*
     * This the function recursively traverse the binary tree.
     *
     * It performs the following functions:
     * 1. Walk the binary tree in the order of highest key to lowest key value
     * 2. looks for node with the desired key value
     * 3. Returns node if the matchig node is found
     *
     * INPUT:
     *    current Node: The current head of the subtree
     *    generic: Key to match
     *
     * OUTPUT:
     *    Node with the matching key
     *
     */
    Node<T> SearchRecursive(Node<T> currentNode, T key) {
        // Did Search failed
        if (currentNode == null) {
            return null;
        }

        // Base Cases: root is null or key is present at root
        if(currentNode.getNodeKey().compareTo(key) == 0) {
            //System.out.println("Found node with payload " + key.printPayload());
            // we found the node; return node
            return currentNode;
        }

        // If the key is less than current key, look towards the left subtree
        if ((currentNode.getNodeKey().compareTo(key) > 0) && (currentNode.left != null)) {
            //Payload payloadLeftNode = (Payload) currentNode.left.getNodeKey();
            // System.out.println("Search left starting from " + payloadLeftNode.printPayload());
            return SearchRecursive(currentNode.left, key);
        } else if ((currentNode.getNodeKey().compareTo(key) < 0) && (currentNode.right != null)) {
            // If the key is greater than current key, look towards the right subtree
            //Payload payloadRightNode = (Payload) currentNode.right.getNodeKey();
            //  System.out.println("Search right starting from " + payloadRightNode.printPayload());
            return SearchRecursive(currentNode.right, key);
        } else {
            // Node is not either left or right of the current node
            return null;
        }
    }

    /*
     * This the function for in order walk of the binary tree to
     * delete for a node with the matching key
     *
     * It walks the binary tree, recursively.
     *
     * It performs the following functions:
     * 1. Walk the binary tree in the order of highest key to lowest key value
     * 2. It starts the walk from root
     * 2. Recursively calls a function to delete the node with matching key
     *    stored in the binary tree
     *
     * INPUT:
     *    Key: Generic
     *
     * OUTPUT:
     *    Node: Generic
     *
     */
    Node<T> Delete (T key) {
        Node <T> nodeBeingDelated;

        // System.out.println("Delete request for Node : " + key.printPayload());
        // T payloadRootNode1 = (Payload) root.getNodeKey();
        nodeBeingDelated = SearchRecursive(root, key);
        // System.out.println("Root before delete completed : " + payloadRootNode1.printPayload());

        root = deleteRecursive(root, key);

        // T payloadRootNode2 = (Payload) root.getNodeKey();
        // System.out.println("Root after delete completed : " + payloadRootNode2.printPayload());
        return(nodeBeingDelated);
    }

    /*
     * This the function recursively deletes the binary tree.
     *
     * It performs the following functions:
     * 1. Walk the binary tree in the order of highest key to lowest key value
     * 2. looks for node with the desired key value
     * 3. removes the node with the matching key
     *
     * INPUT:
     *    current Node: The current head of the subtree
     *    generic: Key to match
     *
     * OUTPUT:
     *    Node that has been removed from the tree
     *
     */
    Node<T> deleteRecursive(Node<T> currentNode, T key)  {
        //
        if (currentNode == null) {
            // We did NOT find find a matching node in the tree
            //System.out.println("Delete failed: Node " + key.printPayload() + " not found");
            return null;
        }

        if (currentNode.getNodeKey().compareTo(key) > 0) {
            // We have not found the matching node
            // Look for the node in the left subtree
            //Payload payloadLeftNode = (Payload) currentNode.left.getNodeKey();
            // System.out.println("Delete: Searching on left side starting of " + currentNode.printPayload());
            currentNode.left = deleteRecursive(currentNode.left, key);
        } else if (currentNode.getNodeKey().compareTo(key) < 0) {
            // If the key is greater than current key, look towards the right subtree
            //Payload payloadRightNode = (Payload) currentNode.right.getNodeKey();
            //System.out.println("Search right starting from " + payloadRightNode.printPayload());
            currentNode.right = deleteRecursive(currentNode.right, key);
        } else {
            // We have found the node with matching key
            // Case 1. The node being delete has only one child
            if (currentNode.left == null) {
                return currentNode.right;
            } else if (currentNode.right == null) {
                return currentNode.left;
            }

            // Node being deleted has both childern
            // Find in order successor, the smallest value
            T smallestValue = findInOrderSuccessor(currentNode.right);
            currentNode.right = deleteRecursive(currentNode.right, smallestValue);
        }
        return currentNode;
    }

    /*
     * This the function recursively finds node with the minimum key.
     *
     * It performs the following functions:
     * 1. Walk the binary subtree for all nodes on left of the sub tree
     * 2. Returns the payload of the key with the lowest key value
     *
     * INPUT:
     *    current Node: The current head of the subtree
     *
     * OUTPUT:
     *    The payload of the key with the lowest key value
     *
     */
    public T findInOrderSuccessor(Node<T> currentNode) {

        //initially minvalKey to the current node

        T minvalPayLoad =  currentNode.getNodeKey();

        //find minvalKey
        while (currentNode.left != null)  {
            minvalPayLoad = currentNode.left.getNodeKey();
            currentNode = currentNode.left;
        }
        return minvalPayLoad;
    }

}