//By: Areebah Fatima (AXF190025)

//this class header for payload will implement comparable (will use to override the Object compareTo)
public class Payload implements Comparable<Payload>  {

    //integer to hold our exponent
    int exponent;
    //integer to hold our coefficient
    int coefficient;

    // Non-default constructor
    public Payload(int coefficient, int exp) {
        this.exponent = exp;
        this.coefficient = coefficient;
    }

    // this is the getter for getCoefficient
    // this getter's main purpose is to retrieve the coefficient
    // INPUT: None
    // OUTPUT: coefficient
    //
    public int getCoefficient() {
        // returns the value the caller wishes to receive (in this case it will be the coefficient)
        return coefficient;
    }

    // this is the setter for setCoefficient
    // this setter's main purpose is to "store" the coefficient
    // INPUT: int coefficient
    // OUTPUT: None
    //
    public void setCoefficient(int coefficient) {
        //returns the value the caller wishes to set (in this case it will be the coefficient)
        this.coefficient = coefficient;
    }

    // this is the getter for getExponent
    // this getter's main purpose is to retrieve the exponent
    // INPUT: None
    // OUTPUT: exponent
    //
    public int getExponent() {
        // returns the value the caller wishes to receive (in this case it will be the exponent)
        return exponent;
    }

    //we will override the compareTo function in the Object class so that we
    //can use it to compare exponents(how we like)
    @Override
    public int compareTo (Payload key)
    {
        // We compare the value of the exponent in the node with
        // the value of the exponent in the key passed in
        if (this.exponent < key.exponent) return -1; // this exponent is less
        if (this.exponent > key.exponent) return 1;  // this exponent is greater

        // if not greater or less, it has to be equal
        return 0;
    }

    // we will override the toString function in the Object class so that we dont print "addresses and etc."
    @Override
    public String toString(){
        //return "Integer.toString(exponent)" so we print the exponent instead of "addresses and etc."
        return Integer.toString(exponent);
    }

}
