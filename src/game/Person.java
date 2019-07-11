package game;

//EDITED BY HOLLY ON 7/10 
//-- Working on getting bet from GUI rather than console //

/**
 * Person Class
 * Description: Models a single player in the In-Between game, storing the
 * player's info and stats (including their bet and current bankroll, as well
 * as whether or not they are still in the game).
 * @author Allison Rojewski
 * @date
 */
public class Person implements Comparable<Person> {
    
    // Instance Fields //
    
    private final String name;      // The player's name - constant once set
    private double bankroll;        // The amount of money the player has
    private double bet;             // The player's current bet
    private boolean activePlayer;   // Whether the player is still in the game
    
    // Constructors //
    
    /**
     * Person Constructor
     * Description: Builds a new Person object using a given name and
     * bankroll amount. It sets the bet to zero and activePlayer to true
     * by default.
     * @param name The name of the player (a String)
     * @param bankroll The amount of money the player starts with (a double)
     */
    public Person(String name, double bankroll) {
        this.name = name;
        this.bankroll = bankroll;
        bet = 0.0;
        activePlayer = true;
    }
    
    // Methods //
    
    /**
     * setBankroll Mutator
     * Description: Updates the player's bankroll by adding the amount won
     * to the current bankroll. A losing hand is treated as negative winnings.
     * If this causes the bankroll to be zero or negative, activePlayer is set
     * to false.
     * @param money The amount to add to the player's bankroll (a double)
     */
    public void setBankroll(double money) 
    {
        bankroll += money;
        if (bankroll <= 0) {
            activePlayer = false;
        }
    }
    
    /**
     * setActivePlayer Mutator
     * Description: Reverses the logical value of the activePlayer variable.
     */
    public void setActivePlayer() {
        activePlayer = !activePlayer;
    }
    
    /**
     * setBuyIn Method
     * Description: Decreases the bankroll by a specified amount and sets the
     * activePlayer variable to true, thereby allowing a player to rejoin the
     * game. Does nothing if the player does not have enough money in their
     * bankroll to afford the buy in.
     * TODO: Better handling of the case where bankroll is less than buy-in.
     * @param buyingAmount The amount to reduce the bankroll by (a double)
     */
    public void setBuyIn(double buyingAmount) {
        if (bankroll > buyingAmount) {
            bankroll -= buyingAmount;
            activePlayer = true;
        }
    }
    
    /**
     * setBet Mutator
     * Description: Sets the current player's bet to a specified value. 
     * Throws an IllegalBetException if the bet amount is larger than 
     * the player's bankroll.
     * @param money
     * @throws IllegalBetException Thrown if the bankroll is less than the bet
     */
    public void setBet(double money) throws IllegalBetException 
    {
        if (bankroll >= money) 
        {
            bet = money;
        } 
        else 
        {
            throw new IllegalBetException("Not enough money for that bet!");
        }
    }

    /**
     * getName Accessor
     * Description: Accessor for the name field.
     * @return The name of the player (a String)
     */
    public String getName() {
        return name;
    }

    /**
     * getBankroll Accessor
     * Description: Accessor for the bankroll field.
     * @return The amount of the player's bankroll (a double)
     */
    public double getBankroll() {
        return bankroll;
    }

    /**
     * getBet Accessor
     * Description: Accessor for the bet field.
     * @return The amount of the player's current bet (a double)
     */
    public double getBet() {
        return bet;
    }

    /**
     * getActivePlayer Accessor
     * Description: Accessor for the activePlayer field.
     * @return The value of activePlayer (a boolean)
     */
    public boolean getActivePlayer() {
        return activePlayer;
    }
    
    /**
     * equals Method
     * Tests whether two Person objects are equal by comparing names.
     * @param otherPerson The other Person object to compare to
     * @return True if the names are the same, false otherwise.
     */
    public boolean equals(Person otherPerson) {
        return this.name.equals(otherPerson.name);
    }
    
    /**
     * compareTo Method
     * Description: Compares Person objects by comparing their bankrolls.
     * Returns a specified integer value depending on whether the other Person
     * has more money, less money, or the same amount of money as the calling
     * Person object.
     * @param otherPerson The Person object to compare to the calling Person
     * @return -1 if otherPerson has less money, 0 if both People have the
     * same amount of money, and 1 if otherPerson has more money.
     */
    @Override
    public int compareTo(Person otherPerson) {
        if (this.bankroll == otherPerson.getBankroll()) {
            return 0;
        } else if (this.bankroll < otherPerson.getBankroll()) {
            return 1;
        } else {
            return -1;
        }
    }
}
