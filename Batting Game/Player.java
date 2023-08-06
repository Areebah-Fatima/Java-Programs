
    /*Player Class
    Functions:
    - All need constructors
    - ALl need Getters and Setters
    - findValue
    - findBattingAverage
    - findOnBasePercentageAverage
    */

import java.util.LinkedList;

/*this Player class purpose is to store and retrieve data so that it can be used by other classes
         while also computing important stats related to the players */
public class Player {

    // this string holds our player's name
    String name;
    // this string holds our player's team (home or away)
    String team;
    // this string Linklist stores the actions that Player made
    LinkedList <String> actionList;

    // This int holds Hit Count
    int hitCount;
    // This int holds Out Count
    int outCount;
    // This int holds Strikeout Count
    int strikeoutCount;
    // This int holds Walk Count
    int walkCount;
    // This int holds HitByPitch Count
    int hitByPitchCount;
    // This int holds Sacrifice Count
    int sacrificeCount;
    // This int holds Error Count
    int errorCount;

    //Default Constructor
    public Player(){
        this.actionList = new LinkedList<>();
    }

    //Overloaded Constructor
    public Player(String name, String team) {
        this.name = name;
        this.team = team;
    }

    // this is the getter for getName
    // this getter's main purpose is to retrieve the Player name  when called to do so
    // INPUT: None
    // OUTPUT: name
    //
    public String getName() {
        return name;
    }

    // this is the getter for getTeam
    // this getter's main purpose is to retrieve the player team when called to do so
    // INPUT: None
    // OUTPUT: team
    //
    public String getTeam() {
        return team;
    }

    // this is the getter for getHitCount
    // this getter's main purpose is to retrieve the player's HitCount when called to do so
    // INPUT: None
    // OUTPUT: hitCount
    //
    public int getHitCount() {
        return hitCount;
    }

    // this is the setter for getHitCount
    // this setter's main purpose is to "store" the hitCount when called to do so
    // INPUT: hitCount
    // OUTPUT: None
    //
    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }

    // this is the getter for getOutCount
    // this getter's main purpose is to retrieve the player's outCount when called to do so
    // INPUT: None
    // OUTPUT: outCount
    //
    public int getOutCount() {
        return outCount;
    }

    // this is the setter for setOutCount
    // this setter's main purpose is to "store" the outCount when called to do so
    // INPUT: outCount
    // OUTPUT: None
    //
    public void setOutCount(int outCount) {
        this.outCount = outCount;
    }

    // this is the getter for getWalkCount
    // this getter's main purpose is to retrieve the player's walkCount when called to do so
    // INPUT: None
    // OUTPUT: walkCount
    //
    public int getWalkCount() {
        return walkCount;
    }

    // this is the setter for setWalkCount
    // this setter's main purpose is to "store" the walkCount when called to do so
    // INPUT: walkCount
    // OUTPUT: None
    //
    public void setWalkCount(int walkCount) {
        this.walkCount = walkCount;
    }

    // this is the getter for getStrikeoutCount
    // this getter's main purpose is to retrieve the player's strikeoutCount when called to do so
    // INPUT: None
    // OUTPUT: strikeoutCount
    //
    public int getStrikeoutCount() {
        return strikeoutCount;
    }

    // this is the setter for setStrikeoutCount
    // this setter's main purpose is to "store" the strikeoutCount when called to do so
    // INPUT: strikeoutCount
    // OUTPUT: None
    //
    public void setStrikeoutCount(int strikeoutCount) {
        this.strikeoutCount = strikeoutCount;
    }

    // this is the getter for getHitByPitchCount
    // this getter's main purpose is to retrieve the player's hitByPitchCount when called to do so
    // INPUT: None
    // OUTPUT: hitByPitchCount
    //
    public int getHitByPitchCount() {
        return hitByPitchCount;
    }

    // this is the setter for setHitByPitchCount
    // this setter's main purpose is to "store" the hitByPitchCount when called to do so
    // INPUT: hitByPitchCount
    // OUTPUT: None
    //
    public void setHitByPitchCount(int hitByPitchCount) {
        this.hitByPitchCount = hitByPitchCount;
    }

    // this is the getter for getSacrificeCount
    // this getter's main purpose is to retrieve the player's sacrificeCount when called to do so
    // INPUT: None
    // OUTPUT: sacrificeCount
    //
    public int getSacrificeCount() {
        return sacrificeCount;
    }

    // this is the setter for setSacrificeCount
    // this setter's main purpose is to "store" the sacrificeCount when called to do so
    // INPUT: sacrificeCount
    // OUTPUT: None
    //
    public void setSacrificeCount(int sacrificeCount) {
        this.sacrificeCount = sacrificeCount;
    }

    // this is the getter for getErrorCount
    // this getter's main purpose is to retrieve the player's errorCount when called to do so
    // INPUT: None
    // OUTPUT: errorCount
    //
    public int getErrorCount() {
        return errorCount;
    }

    // this is the setter for setErrorCount
    // this setter's main purpose is to "store" the errorCount when called to do so
    // INPUT: errorCount
    // OUTPUT: None
    //
    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }


    /*
    This is a wrapper function to calculate or get desired data for a given Player
    It calls helper function to
       - set value to a function that calculates the on base percentage and batting average
       - set value to the appropriate count (which will ultimately be used for displaying)

    INPUT:
    player: player who's data needs to be obtained
    actionType: Type of data being requested

    OUTPUT:
    double: The data being requested
    */
    public static double findValue(Player player, ActionType actionType) {
        double value = 0;
        // Call helper function to calculate or get the desired data

        //this switch structure uses the action type passed in to determine the value of "value"
        switch (actionType) {

            case BATTING_AVERAGE:
                //if true actionType we need to call a function that will determine the BattingAverage
                value = findBattingAverage(player);
                break;

            case ON_BASE_PERCENTAGE:
                //if true actionType we need to call a function that will determine the OnBasePercentage
                value = findOnBasePercentage(player);
                break;

            case HITS:
                //if true actionType we need set value to the hit count
                value = player.getHitCount();
                break;

            case WALKS:
                //if true actionType we need set value to the walk count
                value = player.getWalkCount();
                break;

            case STRIKEOUTS:
                //if true actionType we need set value to the strikeout count
                value = player.getStrikeoutCount();
                break;

            case HIT_BY_PITCH:
                //if true actionType we need set value to the hit by pitch count
                value = player.getHitByPitchCount();
                break;

                //if true an invalid character was passed in
            default:
                break;
        }
        // return value obtained from the helper function
        return value;
    }

    /*
    This is a helper function to calculate batting average for the given player

    INPUT:
    player: player who's data needs to be obtained

    OUTPUT:
    double: The batting average
    */
    public static double findBattingAverage(Player player) {
        // Init Batting Average
        double battingAverage = 0;

        //deal with empty data cases
        if (player == null)
            return battingAverage;

        // Compute Batting Average for the player
        // use the following if statement to prevent 0 by 0 division issues
        if (player.getHitCount() + player.getOutCount() + player.getStrikeoutCount() != 0) {
            //calculates batting average using given formula
            battingAverage = ((player.getHitCount() * 1.0) / (player.getHitCount() +
                    player.getOutCount() + player.getStrikeoutCount()));
        } else {
            //sets batting average to 0 when there is 0 by 0 division
            battingAverage = 0;
        }
        //return the calculated BA
        return battingAverage;
    }

    /*
  This is a helper function to calculate On Base Percentage for the given player

  INPUT:
  player: player who's data needs to be obtained

  OUTPUT:
  double: The On Base Percentage
  */

    public static double findOnBasePercentage(Player player) {

        // Init On Base Percentage
        double OB = 0;

        //deal with empty data cases
        if (player == null)
            return OB;

        //calculate plate Appearances by adding up HitCount, OutCount, WalkCount, etc.
        int plateAppearances = (player.getHitCount() + player.getOutCount() + player.getStrikeoutCount() +
                player.getWalkCount() + player.getHitByPitchCount() + player.getSacrificeCount());

        //use the following if statement to prevent 0 by 0 division issues
        if (plateAppearances != 0) {
            //calculates on base percentage using given formula
            OB = ((player.getHitCount() + player.getWalkCount() +
                    player.getHitByPitchCount()) * 1.0 / (plateAppearances));
        } else {
            //sets on base percentage to 0 when there is 0 by 0 division
            OB = 0;
        }

        //return the calculated on base percentage
        return OB;
    }

}
