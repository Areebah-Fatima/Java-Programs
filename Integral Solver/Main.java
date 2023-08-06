//By: Areebah Fatima (AXF190025)

    /*Main Class
    Functions:
    - Main
    */

// Declare packages needed for the program
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/* This is the main function
      It performs the following functions
      * Open an input file
      * It performs error checking for file opening
      * It reads the file until the EOF
      * It parses data stored in the file
      * It calls processInputStringAndComputeIntegral function (payload class)
      * It displays the results in desired format
      * It closes the file
    */

/*
 * This class is used to carry the limit for each polynomial string input by the user
 *
 * Element: isDefinate
 *          Set if the integral string is definate
 *
 * Element: lowerlimit
 *          Set to the lower limit of the integral string if the polynomial is definate
 *          Set to zero otherwise
 *
 * Element: lowerlimit
 *          Set to the upper limit of the integral string if the polynomial is definate
 *          Set to zero otherwise
 */
class Limit {

    //true false variable that we will use to deal with definite integral computation
    boolean isDefinate;
    //int variable to store the lowerLimit
    int lowerLimit;
    //int variable to store the higherLimit
    int higherLimit;
}

public class Main {

    /*
     * This the main function.
     * It read a file line by line
     * It calls helper functions to compute integral of the string in each file
     *
     * INPUT: args (input file name)
     *
     * OUTPUT: None
     *
     */
    public static void main(String[] args) throws FileNotFoundException {
        // Exception handling to ensure input file exists
        try {
            /* Setup the scanner */
            Scanner consoleIo = new Scanner(System.in);
            String fileTitle = consoleIo.nextLine();
            Scanner input = new Scanner(new File(fileTitle));

            /* Read data stored in the file line-by-line (while not EOL)*/
            while (input.hasNext()) {

                // Parse integral equation from file using substring function
                String inputString = input.nextLine();

                //  Call processIntegralString (in the payload class) to interpret equation
                String outputString = processInputStringAndComputeIntegral(inputString);

                // Print out the solved integral in the correct format
                System.out.println(outputString);

            }

            //closes file
            input.close();
        }
        // Exception handling if any error occurs in the try block
        catch (Exception e)
        {
            System.out.println("Input file does not exist.");
        }
    }

    /*
     *  This function performs the following functins:
     *  1. Call parse helper to Seperate out each polynomial term and Store each
     *     polynomial term to Binary Tree
     *  2. Walks the binaryy tree to compute integral string
     *
     * INPUT:
     *    String integralString: Integral String to parse
     *
     * OUTPUT:
     *    Computed Integral String
     *
     */
    public static String processInputStringAndComputeIntegral(String inputString) {

        //creat limit object
        Limit limit = new Limit();

        //this string will be our computed integral
        String retString;
        //create the binary tree (accordingly)
        BinTree<Payload> binTree = new BinTree<Payload>();

        // Parses the Input string
        parseAndStoreInputString(inputString, binTree, limit);

        //will be used later on to format first term outputs
        int walkCount = 0;

        // Walk the tree to formulate the retString
        retString = binTree.inorderWalkForIntegalString(walkCount);

        //this if statement will deal with an empty node/tree
        if (retString.isBlank()) {
            //we will just return(and later print) a 0 in this case
            retString = "0";
        }

        // If the integral is indefinate, add + C and return
        if (!limit.isDefinate) {
            //add the plus c to your computed integral string
            retString = retString + " + C";
            //return the computed definite integral string
            return retString;
        }

        //double variable to keep track of our definite integral answer (after plugging in limits)
        double answer = 0.00;

        //to deal with Definite integrals where a > b
        if(limit.higherLimit < limit.lowerLimit){

            //if we make it here it mean a > b
            int lL = limit.higherLimit;
            int hL = limit.lowerLimit;
            //so we will swap the higher and lower limit values to become b>a
            limit.higherLimit = hL;
            limit.lowerLimit = lL;
        }

        //walk the tree continuously to find the definite integral answer
        answer = binTree.inorderWalkForDefinateIntegalAnswer(limit.higherLimit, limit.lowerLimit, answer);
        // Print limit in desired format
        retString = retString +", " + limit.lowerLimit + '|' + limit.higherLimit
                + " = " + String.format("%.3f", answer);
        return retString;

    }

    /*
     *  This function performs the following functins:
     *  1. parses the integral string
     *  2. Seperate out each polynomial term
     *  3. Store each polynomial term to Binary Tree
     *
     * INPUT:
     *    String integralString: Integral String to parse
     *    inTree binaryTree: Binary tree to store the data
     *    Limit limit: Placeholder to store the limits
     *
     * OUTPUT:
     *    None
     *
     */
    public static void parseAndStoreInputString(String integralString,
                                                BinTree binaryTree,
                                                Limit limit) {

        //variable to hold limits
        int upperLimit = 0;
        int lowerLimit = 0;


        //all necessary variable declarations
        String polynomial = integralString;
        limit.isDefinate = false;
        limit.lowerLimit = lowerLimit;
        limit.higherLimit = upperLimit;

        //if true it means we can parse the function using the "|"
        if (integralString.contains("|")) {

            //if true means we have a indefinte integral
            if (integralString.startsWith("|")) {
                // parse the function using the "|"
                String[] lowerLimitString = integralString.split("\\|", 2);

            }
            //if true means we have a definite integral
            else {
                //set indefinite to true for later (will use to recognize a change in output and methodology)
                limit.isDefinate = true;
                // read the lower limit (by parsing using "|")
                String[] lowerLimitString = integralString.split("\\|", 2);
                lowerLimit = Integer.parseInt(lowerLimitString[0]);

                // read the upper limit (by parsing using "|")
                String[] upperLimitString = lowerLimitString[1].split(" ", 2);
                upperLimit = Integer.parseInt(upperLimitString[0]);

            }
            // Copy the limits into the limitArray
            limit.lowerLimit = lowerLimit;
            limit.higherLimit = upperLimit;

            String[] limits = integralString.split(" ", 2);

            polynomial = limits[1];
        }

        // add or remove all spaces to deal with non-uniform spaces
        polynomial = polynomial.replaceAll("\\+", " +");
        polynomial = polynomial.replaceAll("\\-", " -");
        polynomial = polynomial.replaceAll("\\^ ", "^");
        polynomial = polynomial.replaceAll("\\+ ", "+");
        polynomial = polynomial.replaceAll("\\- ", "-");
        polynomial = polynomial.replaceAll("dx", "");


        //to handle extra spacing
        if (polynomial.contains(" ")) {

            String[] terms = polynomial.split(" ");

            //for loop to insert terms to node
            for (String term : terms) {
                //dont insert if term is null
                if (!term.equals("")) {
                    //call polyTermToNode to insert terms to node
                    Payload payload = polyTermToNode(term);

                    // Create node
                    Node node = new Node(payload);

                    // Insert Node into binary tree
                    binaryTree.Insert(node);
                }
            }
        } else {
            // Add spaces to the + and - signs (will use to parse)
            polynomial = polynomial.replaceAll("\\+", " +");
            polynomial = polynomial.replaceAll("\\-", " -");
            polynomial = polynomial.replaceAll("\\^ ", "^");

            // Split on space character
            String[] terms = polynomial.split(" ");

            //for loop to insert terms to node
            for (String term : terms) {

                //dont insert if term is null
                if (!term.equals("")) {

                    //call polyTermToNode to insert terms to node
                    Payload payload = polyTermToNode(term);

                    // Create node
                    Node node = new Node(payload);

                    // Insert Node into binary tree
                    binaryTree.Insert(node);
                }
            }
        }

    }

    /*
     *  This function convert a polynomial term to payload for Nodes
     *
     * INPUT:
     *    String polyTerm: Polynomial terms like 5x^2, 2, etc.
     *
     * OUTPUT:
     *    Payload that is based on the polynomial term
     *
     */
    public static Payload polyTermToNode(String polyTerm) {

//variables to hold exponent and coefficient
        int cofficient;
        int exponent;

        // Remove any special characters (ex tab, space, newline)
        polyTerm = polyTerm.replaceAll("[ \\t\\n\\x0B\\f\\r]", "");
        polyTerm = polyTerm.replaceAll(" ", "");

        // Spit the terms on "^" to seperate co-efficient and exponent
        // if true poly term has a Exponent term
        if (polyTerm.contains("^")) {
            //we want to split on x.(x and char next to x (^)) to cleanly parse exp and coeff
            String[] subterms = polyTerm.split("x.", 2);

            //if true poly is empty or contains plus
            if (subterms[0].equals("") || subterms[0].equals("+")) {
                //set coeff to 1(will help with parsing errors)
                cofficient = 1;
            } else if (subterms[0].equals("-")) {
                //if - the just make it -1
                cofficient = -1;
            } else {
                //it's safe to parse (wont run into parsing on a sign issue)
                cofficient = Integer.parseInt(subterms[0]);
            }

            //parse the exp
            exponent = Integer.parseInt(subterms[1]);

            //return exp and coeff
            return new Payload(cofficient, exponent);
        }

        // x^1 terms
        if (polyTerm.contains("x")) {
            //split on x
            String[] subterms = polyTerm.split("x", 2);

            //if empty or contains plus
            if (subterms[0].equals("") || subterms[0].equals("+")) {
                //set coeff to 1
                cofficient = 1;
            } else if (subterms[0].equals("-")) {
                //like before set to -1 if "-"
                cofficient = -1;
            } else {
                //safe to parse
                cofficient = Integer.parseInt(subterms[0]);
            }

            exponent = 1;
        } else {
            // constant term parse coeff
            cofficient = Integer.parseInt(polyTerm);
            exponent = 0;
        }
        //return parse coeff and exp
        return new Payload(cofficient, exponent);
    }

    /*
     *  This function compute definate integral of a term stored in the Node
     *  and result is given in a formatted string
     *
     * INPUT:
     *    iNode: Node that stores the payload of the polynomial term
     *    walkcount: Indicates if this is the first term (Highest polynomial) term
     *
     * OUTPUT:
     *    formatted string which is the output
     *
     */
    public static String computeIndefinateIntegralOfATerm(Node iNode, int walkCount) {

        //necessary variable declaration
        String retNode = "";
        String sign = null;

        //if null dont compute anything
        if (iNode == null) {
            return "";
        }

        //necessary variable declaration
        Payload payload = (Payload) iNode.getNodeKey();
        int exp = payload.getExponent();
        int coeff = payload.getCoefficient();
        int ResExp = exp + 1;
        int ResDenominator = exp + 1;
        int gcd = computeGcd(coeff, ResDenominator);
        int simplifiedNum = coeff / gcd;
        int simpliedDen = ResDenominator / gcd;

        // If the resulting coefficient is zero, we do NOT want to print any output
        if (simplifiedNum == 0) {
            //do NOT want to print any output
            retNode = "";
            return retNode;
        }
        // Check if both simplifiedNum and ResDenominator is 1 and of same sign; resulting coeeficient is ignored
        if (((simplifiedNum == 1) && (simpliedDen == 1)) || (((simplifiedNum == -1) && (simpliedDen == -1)))) {
            if (walkCount == 0) {
                //if first term act accordingly (dont print sign)
                retNode = printExponent(iNode, ResExp);
            } else {
                //not first term print sign
                retNode = " + " + printExponent(iNode, ResExp);
            }
            return retNode;
        }

        // Check if both simplifiedNum and ResDenominator is 1 and of different sign; resulting coefficient is - sign
        if (((simplifiedNum == -1) && (simpliedDen == 1)) || ((simplifiedNum == 1) && (simpliedDen == -1))) {
            if (walkCount == 0) {
                //first term and neg print sign
                retNode = "-" + printExponent(iNode, ResExp);
            } else {
                //not first term and neg print sign
                retNode = " -" + printExponent(iNode, ResExp);
            }
            return retNode;
        }

        // We have a/b form of coefficient
        // Find sign of the result

        // Check if simplifiedNum and ResDenominator are of different sign
        if (((simplifiedNum < 0) && (simpliedDen > 0))|| ((simplifiedNum > 0) && (simpliedDen < 0))){
            //set sign (will use later on)
            sign = "-";
        } else {
            sign = "+";
        }

//have whole num
        if (Math.abs(simpliedDen) == 1) {
            // We have a whole number (+ or -)

            //first term
            if (walkCount == 0) {
                if (sign.equals("-")) {
                    //print in correct format
                    retNode = "-" + Math.abs(simplifiedNum) + printExponent(iNode, ResExp);
                } else {
                    //print in correct format
                    retNode = Math.abs(simplifiedNum) + printExponent(iNode, ResExp);
                }
            } else {

                //print in correct format
                retNode = " " + sign + " " + Math.abs(simplifiedNum) + printExponent(iNode, ResExp);
            }

            return retNode;
        }

        // We have a rational number (a/b form)
        if (walkCount == 0) {
            // First term
            if(sign.equals("-")) {
                //print(set the thing that will later be printed) in correct format
                retNode = "(" + sign + Math.abs(simplifiedNum) + "/" + Math.abs(simpliedDen) + ")" + printExponent(iNode, ResExp);
            }
            if(sign.equals("+")) {
                //print(set the thing that will later be printed) in correct format
                retNode = "(" + Math.abs(simplifiedNum) + "/" + Math.abs(simpliedDen) + ")" + printExponent(iNode, ResExp);
            }
        } else {
            //print(set the thing that will later be printed) in correct format
            retNode = " " + sign + " (" + Math.abs(simplifiedNum) + "/" + Math.abs(simpliedDen) + ")" + printExponent(iNode, ResExp);
        }
        return retNode;


    }

    /*
     *  This function compute definate integral of a term stored in the Node
     *  and result is given as double
     *
     * INPUT:
     *    iNode: Node that stores the payload of the polynomial term
     *    Upperlimit: Upper limit of the integral
     *    lowerlimit: Lower limit of the integral
     *
     * OUTPUT:
     *    Double as value of the definate integral
     *
     */
    public static double computeDefinateIntegralOfATerm(Node iNode,
                                                        int upperLimit,
                                                        int lowerLimit) {

        //variable declaration
        double answer;
        Payload payload = (Payload) iNode.getNodeKey();
        int exp = payload.getExponent();
        int coeff = payload.getCoefficient();
        double ResExp = (double) exp + 1;
        int ResDenominator = exp + 1;


        //compute integral answers
        double upperLimitValue = Math.pow(upperLimit, ResExp);
        double lowerLimitValue = Math.pow(lowerLimit, ResExp);


        //dont want to work with 0 denom
        if (ResDenominator != 0) {
            //do math
            answer = coeff * (upperLimitValue - lowerLimitValue) / ResDenominator;
        } else {
            //do math
            answer = coeff * (upperLimitValue - lowerLimitValue);
        }
        //return computed answer
        return answer;
    }

    /*
     *  This function compute GCD of two numders
     *
     * INPUT:
     *    num: Node that stores the payload of the polynomial term
     *    num: Number 1
     *    den: Number 2
     *
     * OUTPUT:
     *    Double as gcd(num, den)
     *
     */
    public static int computeGcd(int num, int den) {

//dont compute gcd with denom = to 0 just return num
        if (den == 0) {
            return num;
        } else {
            //use the remainder after the modulo operation to find the gcd (will use later on)
            int remainder = num % den;
            return (computeGcd(den, remainder));
        }
    }

    /*
     *  This function prints a node as a polynomial term without coefficient
     *
     * INPUT:
     *    None: Node that stores the payload of the polynomial term
     *    exp: Exponent of the node
     *
     * OUTPUT:
     *    String as exponent, e.g., x^5
     *
     */
    public static String printExponent(Node iNode, int exp){

        //if exponent is 1 dont print ^num
        if (exp == 1){
            return "x";
        } else {
            //else we have a exponent to display so act accordingly
            return ("x^" + exp);
        }
    }

}