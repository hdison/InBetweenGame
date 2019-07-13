package game;
import java.util.Scanner;

//EDITED BY HOLLY ON 7/10 
//-- Working on getting bet from GUI rather than console //
// Just made dealCard() public to access from GUI

/**
 * House Class
 * Description: This class handles the playing of a single hand in a game of
 * In-Between. It stores the current value of the pot as well as the card deck
 * used in the game, and provides methods for dealing cards, obtaining bets
 * from players, and playing a hand.
 * @author Allison Rojewski
 * @date
 */
public class HouseOld
{
    
    // Constants //
    
    private static final int NUM_PLAYERS = 2;
    
    // Instance Fields //
    
    private double pot;     // The amount of money in the pot
    private Deck deck;      // The deck of Cards in play
    private Card card1;
    private Card card2;
    private Card card3;
    private double playerBet;
    
    // Constructors //
    
    /**
     * House Constructor
     * Description: Sets up the initial pot for the game and initializes the
     * Deck object
     * @param buyIn The amount of money to add to the pot per player
     */
    public HouseOld(double buyIn) 
    {
        pot = buyIn * NUM_PLAYERS; // Sets the pot as number of players * buyIn
        deck = new Deck();         // Generates the deck for play
    }

        // Methods //
    
    /**
     * getPot Accessor
     * Returns the current value of the pot.
     * @return The current value of the pot (a double)
     */
    public double getPot() 
    {
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

    public Card getCard1() {
        return card1;
    }

    public Card getCard2() {
        return card2;
    }

    public Card getCard3() {
        return card3;
    }

    public double getPlayerBet() {
        return playerBet;
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

    public void setCard1(Card card1) {
        this.card1 = card1;
    }

    public void setCard2(Card card2) {
        this.card2 = card2;
    }

    public void setCard3(Card card3) {
        this.card3 = card3;
    }

    public void setPlayerBet(double playerBet) 
    {
        this.playerBet = playerBet;
    }
    
    /**
     * playHand Method
     * Description: Given an active player, deals two cards and asks for a 
     * player's bet. It then compares a third card and adds/subtracts from
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
     * @throws IllegalBetException Checks for players betting more than is
     * in their bankroll.
     */
    public void playHand(Person currentPlayer) throws IllegalBetException 
    {
        if (!currentPlayer.getActivePlayer()) 
        {
            return;     // If the current player isn't active, skip them
        }
        do {
            card1 = dealCard();             // Deal first card
            card2 = dealCard();             // Deal second card
        } while (card1.equals(card2));      // Keep dealing until card1 != card2
        		
        // NOT USED IN GUI VERSION
        playerBet = getBet(currentPlayer);  // Get player's bet
        
        card3 = dealCard();                 // Deal the third card
        
        // Check if card is in between and adjust pot and bankroll 
        isBtwn( currentPlayer, playerBet, card1, card2, card3);
        
    }
    
    // Holly extracted this chunk out of playHand method to use in GUI
    public boolean isBtwn(Person player, double betAmt, Card card1, Card card2, 
    		Card card3)
    {
    	boolean btwn = false; 
    	
    	// If third card matches either first or second card,
        // player loses twice their bet
        if (card3.compareTo(card1) == 0 || card3.compareTo(card2) == 0) 
        {
            player.setBankroll(2.0 * -betAmt);
            pot += (betAmt * 2.0);
        // If third card is in between first and second card, 
        // player wins their bet
        } else if (((card3.compareTo(card1) == -1) 
                                    && (card3.compareTo(card2) == 1)) ||
                   ((card3.compareTo(card1) == 1) 
                                    && (card3.compareTo(card2) == -1))) {
            player.setBankroll(betAmt);
            pot -= betAmt;
            btwn = true;
        // If third card is not in between the first and second card and is
        // not equal to either of them, the player loses their bet
        } else {
            player.setBankroll(-betAmt);
            pot += betAmt;
        }
    	
    	return btwn;
    }
    
    // Holly Changed to public to use with GUI
    /**
     * dealCard Method
     * Description: Private helper method that determines if the deck is empty
     * (if so, it shuffles the deck) and then returns the top card.
     * @return The card at the top of the deck
     * 
     */
    public Card dealCard() {
        if (!deck.hasNext()) {
            deck.shuffle();      // If no cards remain in the deck, shuffle it
        }
        return deck.popStack(); // Return the top card of the deck
    }
    
    
    
    /**
     * getBet Method
     * Description: Private helper method that prompts a player for a bet, then
     * returns that bet. Includes error handling for inappropriate input as
     * well as for when a player attempts to bet more than is in their bankroll
     * @param player The player who is currently betting
     * @throws IllegalBetException Checks for players betting more than is
     * in their bankroll.
     * @return The player's bet (a double)
     */
    private double getBet(Person player) throws IllegalBetException 
    {
        // Variables
        Scanner stdin = new Scanner(System.in);
        double bet = 0.0;                   // Stores the player's bet
        boolean isValid = false;            // Whether the bet is valid or not

        do 
        {
            try 
            {
                System.out.print("Enter your bet: ");
                bet = stdin.nextDouble();   // Get bet from player
                // Handle when player's bet is negative
                if (bet < 0) 
                {
                    throw new IllegalBetException("Bet can't be negative!");
                }
                player.setBet(bet); // Sets the player's bet to the input value
                isValid = true; // If bet is valid, exit the input loop
            }   
            catch (IllegalBetException e) 
            {
                System.out.println(e);
            }
            catch (Exception e) 
            {
                System.out.println("Bet must be a valid number!");
                stdin.nextLine();
            }
        } while (!isValid);
                
        return bet; 
    }
}
