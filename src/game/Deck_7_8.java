package game;

import java.util.Random;
import java.util.Iterator;
/**
 * Description: A deck which is comprised of 52 card objects.
 * @author Luke Dantuono
 * Date: 3 June 2019
 */
public class Deck_7_8 implements Iterator{

    final private int DECK_SIZE = 52; //size of deck 
    private Card[] deck; // an array of Card objects
    private boolean[] used = new boolean[DECK_SIZE];//used to keep track if delt
    private Random rand = new Random(); //random number object
    private int stackCount; // Counts how many cards in deck
    
    /**
     * Constructor method. Builds the deck according to the random generator
     * using isAvailable() method.
     */
    public Deck_7_8() {
        deck = new Card[DECK_SIZE];

        //load deck with randomly controled cards
        for (int i = 0; i < DECK_SIZE; i++) {
            deck[i] = new Card(isAvailable(used));  //<== only card that have 
                                                    //not been used. 
            stackCount++;
        }
    }//End of Constructor METHOD.

    
    //Methods//
    
    
    /**
     * getStackCount() method. Returns the stack count.
     * @return - int value of the stack count.
     */
    public int getStackCount(){ 
      return stackCount;   
    }//End of getStackCount() METHOD.
    
    
    /**
     * peekStack() method. Displays the top card value and suit. Calls card 
     * object toString() method.
     */
    public void peekStack(){
        System.out.println("Card on top is: " +deck[stackCount-1].toString()); 
    } // End of peekStack() METHOD.
    
    
    /**
     * popStack() method. Pops and returns the top card object while reducing
     * the stack count.
     * @return - single top card object 
     */
     public Card popStack(){
         Card topCard = deck[stackCount-1];
        stackCount--;
         
        return topCard;
    }// End of popStack() METHOD
 
     
    /**
     * next() method. Pops and returns the top card object while reducing the 
     * stack count. Required to implement iterator interface.
     * @return 
     */
    @Override
    public Card next() 
    {
        Card topCard = deck[stackCount - 1];
        stackCount--;

        return topCard;
    }//End of next() method
    
    
    /**
     * remove() method. Removes the top card from the stack by reducing the 
     * stack count by one. Nothing is returned.
     */
    @Override
    public void remove()
    {
        stackCount--;
    }//End of remove() method 
   
    
   /**
    * hasNext() method. Returns true if there are more that 0 cards left in deck 
    * @return - boolean value
    */
    @Override
    public boolean hasNext()
    {
       // boolean cardsLeft = true;
       // if (stackCount > 0)
       //     cardsLeft = true;
        
        //return cardsLeft; 
        return (stackCount > 0);
    }// End of hasNext() method
      
    
   /**
    * hasCard() method. Searches the remaining card in deck and returns true
    * if face value and suit are a match
    * @param face - face value of card to be searched 
    * @param suit - suit of card to be searched
    * @return - boolean value if card is found
    */
    public boolean hasCard(int face, Card.Suit suit) {
        boolean isThere = false;
        for (int i = 0; i < stackCount; i++) {
            if (deck[i].getFace() == face && deck[i].getSuit() == suit) {
                isThere = true;
                return isThere;
            }
        }

        return isThere;
    }//End of hasCard() method

   
    /**
     * remainingCards() method. Displays a list of all remaining cards in deck.
     * @return - Concatenated String of remaining cards in deck.
     */
   public String remainingCards()
   {
        String returnString = "";

        for (int i = 0; i < stackCount; i++) {  //used to be DECK_SIZE
         
            returnString += "Card " + (i + 1) + ": " + deck[i].getSFace()+
                    " (" +deck[i].getSuit() + 
                    "s)"  + "\n";           
        }
        return returnString;       
   }//End of remainingCards() method 
   
   
   /**
    * shuffle() method. Re-shuffles a new deck of cards. Resets all values and 
    * reuses code from constructor method.
    */
    public void shuffle() {
        stackCount = 0;
        used = new boolean[DECK_SIZE];
        deck = new Card[DECK_SIZE];

        //load deck with randomly controled cards
        for (int i = 0; i < DECK_SIZE; i++) {
            deck[i] = new Card(isAvailable(used));  //<== only card that have 
                                                    //not been used. 
            stackCount++;
        }
    }// End of shuffle() method
   
   
    /**
     * Overrides toString method, shows suit and face value of all cards in deck
     * calls getSFace() method which returns a string accounting for J,Q,K,A  
     * @return - Concatenated String of all cards in deck 
     */
    @Override
    public String toString() {
        String returnString = "";

        for (int i = 0; i < DECK_SIZE; i++) {  
         
            returnString += "Card " + (i + 1) + ": " + deck[i].getSFace()+
                    " (" + deck[i].getSuit() + 
                    ")"  + "\n";           
        }
        return returnString;
    }//End of toString() method
  
    
    /**
     * isAvailable method. Helper method used with the constructor. Responsible
     * for returning a card value based on the total deck size while keeping
     * track which numbers have already been taken by using a boolean array
     * matching deck size.
     *
     * @param used - boolean array that keeps track of which number have been
     * delt
     * @return - card value (1 though 52) which hasn't already been returned
     */
    private int isAvailable(boolean[] used) {
        int randNum;
        int control = 0;    
        int exit = -1;
        randNum = (rand.nextInt(DECK_SIZE)); //randNum = (rand.nextInt(52) + 1);

        do {
            if (used[randNum] == true) {  //-1
                randNum = (rand.nextInt(DECK_SIZE)); //randNum = (rand.nextInt(52) + 1);
            } else {
                used[randNum] = true;  //-1
                control = exit;
            }
        } while (control != exit);

        return (randNum + 1);
    }//End of method isAvailable

    
}// End of class


