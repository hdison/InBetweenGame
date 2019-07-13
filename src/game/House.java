package game;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.*;

/**
 * House Class
 * Description: This class handles the playing of a single hand in a game of
 * In-Between. It stores the current value of the pot as well as the card deck
 * used in the game, and provides methods for dealing cards, obtaining bets
 * from players, and playing a hand.
 * @author Allison Rojewski
 * @date
 */
public class House 
{
    
    // Constants // 
    private static final double MIN_BET = 5.0; // Minimum bet
    
    // Instance Fields //
    
    private double pot;     // The amount of money in the pot
    private Deck deck;      // The deck of Cards in play
    private Card card1;
    private Card card2;
    private Card card3;
    private double playerBet;
    private boolean btwn; 	//flag whether card3 is between or not (for GUI)
    
    // Constructors //
    
    /**
     * House Constructor
     * Description: Sets up the initial pot for the game and initializes the
     * Deck object. Assumes one player.
     * @param buyIn The amount of money to add to the pot per player
     */
    public House(double buyIn) {
        pot = buyIn;               // Sets the pot equal to the buy in
        deck = new Deck();         // Generates the deck for play
    }
    
    /**
     * House Constructor (overloaded)
     * Description: Sets up the initial pot for the game and initializes the
     * Deck object. Will take into account more than one player.
     * @param buyIn The amount of money to add to the pot per player
     * @param numPlayers The number of players
     */
    public House(double buyIn, int numPlayers) {
        pot = buyIn * numPlayers; // Sets the pot as number of players * buyIn
        deck = new Deck();         // Generates the deck for play
    }

    // Methods //
    
    /**
     * getPot Accessor
     * Returns the current value of the pot.
     * @return The current value of the pot (a double)
     */
    public double getPot() {
        return pot;
    }

    /**
     * getDeck Accessor
     * Returns the current deck in use.
     * @return The Deck object currently in use
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * getCard1 Accessor
     * Returns a reference to the current card1
     * @return The card1 Card object
     */
    public Card getCard1() {
        return card1;
    }

    /**
     * getCard2 Accessor
     * Returns a reference to the current card2
     * @return The card2 Card object
     */
    public Card getCard2() {
        return card2;
    }

    /**
     * getCard3 Accessor
     * Returns a reference to the current card3
     * @return The card3 Card object
     */
    public Card getCard3() {
        return card3;
    }

    /**
     * setPot Mutator
     * Sets the pot to a specified value.
     * @param pot  The specified amount of the new pot
     */
    public void setPot(double pot) {
        this.pot = pot;
    }

    /**
     * setDeck Mutator
     * Sets the deck to a specific Deck object
     * @param deck The Deck object that the deck instance variable should
     * reference.
     */
    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    /**
     * setCard1 Mutator
     * Sets card1 to a specific Card object
     * @param card1 The Card object that the instance variable should
     * reference.
     */
    public void setCard1(Card card1) {
        this.card1 = card1;
    }

    /**
     * setCard2 Mutator
     * Sets card2 to a specific Card object
     * @param card2 The Card object that the instance variable should
     * reference.
     */
    public void setCard2(Card card2) {
        this.card2 = card2;
    }

    /**
     * setCard3 Mutator
     * Sets card3 to a specific Card object
     * @param card3 The Card object that the instance variable should
     * reference.
     */
    public void setCard3(Card card3) {
        this.card3 = card3;
    }

    public boolean isBtwn()
    {
    	return btwn;
    }
    
    /**
     * dealHandGUI Method
     * Deals a new hand of cards for the player. Used only in the GUI version
     * of In-Between.
     * @param currentPlayer The player who is currently betting
     */
    public void dealHandGUI(Person currentPlayer) {
        if (!currentPlayer.getActivePlayer()) {
            return;     // If the current player isn't active, skip them
        }
        do {
            card1 = dealCard();             // Deal first card
            card2 = dealCard();             // Deal second card
        } while (card1.compareTo(card2) == 0); // Keep dealing until card1 != card2
    }
    
    /**
     * playHandGUI Method
     * Description: Given an active player, asks for a player's bet.
     * It then compares a third card and adds/subtracts from
     * the player's bankroll and the pot depending on the results:
     * 
     * 1. If the third card equals one of the first two cards, the player loses
     * twice their bet, and this amount is added to the pot
     * 2. If the third card is in between the first two cards, the player wins
     * their bet and this amount is subtracted from the pot
     * 3. If the third card is not in between the first two and is also not
     * equal to either of the first two, the player loses their bet and this
     * amount is added to the pot
     * 
     * If the player is inactive, the method is exited early.
     * @param currentPlayer The player whose turn it is.
     * @param input The player's bet (a String, parsed in the method to double)
     * @throws IllegalBetException Checks for players betting more than is
     * in their bankroll.
     */
    public void playHandGUI(Person currentPlayer, String input) 
                                                    throws IllegalBetException {
        playerBet = getBetGUI(currentPlayer, input);  // Get player's bet
        card3 = dealCard();                 // Deal the third card
        btwn = false;		// Added by Holly for her GUI
        
        // If third card matches either first or second card,
        // player loses twice their bet
        if (card3.compareTo(card1) == 0 || card3.compareTo(card2) == 0) {
            currentPlayer.setBankroll(2.0 * -playerBet);
            pot += (playerBet * 2.0);
        // If third card is in between first and second card, player wins
        // their bet
        } else if (((card3.compareTo(card1) == -1) 
                                    && (card3.compareTo(card2) == 1)) ||
                   ((card3.compareTo(card1) == 1) 
                                    && (card3.compareTo(card2) == -1))) {
            currentPlayer.setBankroll(playerBet);
            pot -= playerBet;
            btwn = true;
        // If third card is not in between the first and second card and is
        // not equal to either of them, the player loses their bet
        } else {
            currentPlayer.setBankroll(-playerBet);
            pot += playerBet;
        }
    }
    
    /**
     * playHandGUI Method (Overloaded)
     * Description: Given an active player, asks for a player's bet.
     * It then compares a third card and adds/subtracts from
     * the player's bankroll and the pot depending on the results:
     * 
     * 1. If the third card equals one of the first two cards, the player loses
     * twice their bet, and this amount is added to the pot
     * 2. If the third card is in between the first two cards, the player wins
     * their bet and this amount is subtracted from the pot
     * 3. If the third card is not in between the first two and is also not
     * equal to either of the first two, the player loses their bet and this
     * amount is added to the pot
     * 
     * If the player is inactive, the method is exited early.
     * @param currentPlayer The player whose turn it is.
     * @param input The player's bet (a double)
     * @throws IllegalBetException Checks for players betting more than is
     * in their bankroll.
     */
    public void playHandGUI(Person currentPlayer, double input) 
                                                    throws IllegalBetException {
        playerBet = getBetGUI(currentPlayer, input);  // Get player's bet
        card3 = dealCard();                 // Deal the third card
        // If third card matches either first or second card,
        // player loses twice their bet
        if (card3.compareTo(card1) == 0 || card3.compareTo(card2) == 0) {
            currentPlayer.setBankroll(2.0 * -playerBet);
            pot += (playerBet * 2.0);
        // If third card is in between first and second card, player wins
        // their bet
        } else if (((card3.compareTo(card1) == -1) 
                                    && (card3.compareTo(card2) == 1)) ||
                   ((card3.compareTo(card1) == 1) 
                                    && (card3.compareTo(card2) == -1))) {
            currentPlayer.setBankroll(playerBet);
            pot -= playerBet;
        // If third card is not in between the first and second card and is
        // not equal to either of them, the player loses their bet
        } else {
            currentPlayer.setBankroll(-playerBet);
            pot += playerBet;
        }
    }

    /**
     * dealCard Method
     * Description: Private helper method that determines if the deck is empty
     * (if so, it shuffles the deck) and then returns the top card.
     * @return The card at the top of the deck
     * 
     */
    private Card dealCard() {
        if (!deck.hasNext()) {
            deck.shuffle();      // If no cards remain in the deck, shuffle it
        }
        return deck.popStack(); // Return the top card of the deck
    }
    
    /**
     * getBetGUI Method
     * Description: Private helper method that prompts a player for a bet, then
     * returns that bet. Includes error handling for inappropriate input as
     * well as for when a player attempts to bet more than is in their bankroll.
     * 
     * @param player The player who is currently betting
     * @param input The bet amount (a String, parsed in method to double)
     * @throws IllegalBetException Checks for players betting more than is
     * in their bankroll.
     * @return The player's bet (a double)
     */
    private double getBetGUI(Person player, String input) 
                                                    throws IllegalBetException {
        // Variables
        double bet = 0.0;                   // Stores the player's bet
        bet = Double.parseDouble(input);   // Attempt to parse bet
        // Handle when player's bet is negative - exception is handled upstream
        if (bet <= 0) {
            throw new IllegalBetException("Bet can't be negative or zero!");
        }
        // Handle when player's bet is too low
        if (bet > 0 && bet < MIN_BET) {
            throw new IllegalBetException("Bet must be over minimum ($" + 
                                            MIN_BET +")!");
        }
        // Handle when player's bet is over the pot amount
        if (bet > pot) {
            throw new IllegalBetException("Bet can't be more than the pot!");
        }
        player.setBet(bet); // Sets the player's bet to the input value       
        return bet;
    }
    
    /**
     * getBetGUI Method (Overloaded)
     * Description: Private helper method that prompts a player for a bet, then
     * returns that bet. Includes error handling for inappropriate input as
     * well as for when a player attempts to bet more than is in their bankroll.
     * 
     * @param player The player who is currently betting
     * @param input The player's bet (a double)
     * @throws IllegalBetException Checks for players betting more than is
     * in their bankroll.
     * @return The player's bet (a double)
     */
    private double getBetGUI(Person player, double input) 
                                                    throws IllegalBetException {
        // Handle when player's bet is negative - exception is handled upstream
        if (input <= 0) {
            throw new IllegalBetException("Bet can't be negative or zero!");
        }
        // Handle when player's bet is too low
        if (input > 0 && input < MIN_BET) {
            throw new IllegalBetException("Bet must be over minimum ($" + 
                                            MIN_BET +")!");
        }
        // Handle when player's bet is over the pot amount
        if (input > pot) {
            throw new IllegalBetException("Bet can't be more than the pot!");
        }
        player.setBet(input); // Sets the player's bet to the input value       
        return input;
    }
    
    // OLD CODE - DO NOT USE
    
    /*public void playHand(Person currentPlayer) throws IllegalBetException {
        if (!currentPlayer.getActivePlayer()) {
            return;     // If the current player isn't active, skip them
        }
        do {
            card1 = dealCard();             // Deal first card
            card2 = dealCard();             // Deal second card
        } while (card1.compareTo(card2) == 0); // Keep dealing until card1 != card2
        System.out.println("Card 1: " + card1);
        System.out.println("Card 2: " + card2);
        playerBet = getBet(currentPlayer);  // Get player's bet
        card3 = dealCard();                 // Deal the third card
        System.out.println("Card 3: " + card3);
        // If third card matches either first or second card,
        // player loses twice their bet
        if (card3.compareTo(card1) == 0 || card3.compareTo(card2) == 0) {
            currentPlayer.setBankroll(2.0 * -playerBet);
            pot += (playerBet * 2.0);
        // If third card is in between first and second card, player wins
        // their bet
        } else if (((card3.compareTo(card1) == -1) 
                                    && (card3.compareTo(card2) == 1)) ||
                   ((card3.compareTo(card1) == 1) 
                                    && (card3.compareTo(card2) == -1))) {
            currentPlayer.setBankroll(playerBet);
            pot -= playerBet;
        // If third card is not in between the first and second card and is
        // not equal to either of them, the player loses their bet
        } else {
            currentPlayer.setBankroll(-playerBet);
            pot += playerBet;
        }
    }
    
    private double getBet(Person player) throws IllegalBetException {
        // Variables
        Scanner stdin = new Scanner(System.in);
        double bet = 0.0;                   // Stores the player's bet
        boolean isValid = false;            // Whether the bet is valid or not

        do {
            try {
                System.out.print("Enter your bet: ");
                bet = stdin.nextDouble();   // Get bet from player
                // Handle when player's bet is negative
                if (bet < 0) {
                    throw new IllegalBetException("Bet can't be negative or zero!");
                }
                // Handle when player's bet is too low
                if (bet > 0 && bet < MIN_BET) {
                    throw new IllegalBetException("Bet must be over minimum ($" + 
                                            MIN_BET +")!");
                }
                // Handle when player's bet is over the pot amount
                if (bet > pot) {
                    throw new IllegalBetException("Bet can't be more than the pot!");
                }
                player.setBet(bet); // Sets the player's bet to the input value
                isValid = true; // If bet is valid, exit the input loop
            }   
            catch (IllegalBetException e) {
                System.out.println(e);
            }
            catch (Exception e) {
                System.out.println("Bet must be a valid number!");
                stdin.nextLine();
            }
        } while (!isValid);
                
        return bet; 
    }*/
}
