package game;

/**
 * Description: Card object that represents a playing card in a deck of 52 
 * cards. Each card has a suit and a face value. Class includes two getters for 
 * face values: One returns numerical form (for logical comparison) the other 
 * returns String form (for display, i.e. 11 = Jack). There is also a getter for
 * for suit value and a setter to change Ace value from 1 to 14.
 * @author Luke Dantuono
 * Date: 3 July 2019
 */
public class Card 
{
    
    enum Suit {clubs, spades, diamonds, hearts};
    private Suit suit;
    private int face;

   
    //Constructors//
    
    /**
     * Default Constructor
     */
    public Card(){
    }//end of default constructor
    
    
    /**
     * Constructor used to load a deck of 52 card. Random values are fed in 
     * (between 1 and 52) and logic determines face value and suit. There should 
     * be no duplicate values at this point.
     * @param value - new card value as an int
     */
    public Card(int value){
        if(value < 14){
            this.suit = Suit.clubs;
            this.face = value;
        }
        else if(value > 13 && value < 27){
            this.suit = Suit.spades;
            this.face = value - 13;
        }
        else if(value > 26 && value < 40){
            this.suit = Suit.diamonds;
            this.face = value - 26;
        }
        else if(value > 39 && value < 53){
            this.suit = Suit.hearts;
            this.face = value - 39;
        }         
    }//End of constructor
    
    
    //Methods//
    

   /**
    * getSuit() method. Returns suit value for card.
    * @return - Enum value for card suit
    */ 
    public Enum getSuit() {
        return suit;
    }// End of getSuit() method

       
    /**
     * getFace() method. Returns only the numerical face value of the card (1 -
     * 14). Useful for numerical logic in determining if a card has a higher 
     * value than an other.
     * @return - face value as an int.
     */
    public int getFace() {
        return face;
    }//End of getFace() method
    
       
    /**
     * getSFace() method. Returns the face value of the card as a string.
     * Includes logic for determining higher value cards (i.e. 13 = King).
     * Useful for displaying card value to user.
     * @return - Card value as a String
     */
    public String getSFace(){
        String sFace;
        int value = this.getFace();
        
        switch (value) {
            case 1:
            case 14:
                sFace = "Ace";
                break;
            case 11:
                sFace = "Jack";
                break;
            case 12:
                sFace = "Queen";
                break;
            case 13:
                sFace = "King";
                break;
            default:
                sFace = String.valueOf(value);
                break;
        }       
        return sFace;
    }// End of getSFace() method    
    
    
    /**
     * setFace() method, used to set Aces to user determined value of 1 or 14
     * @param newValue 
     */
    public void setFace(int newValue){
        this.face = newValue;
    }// End of setFace() method
    
    
    /**
     * equals() method. Compares the cards face and suit values with second card
     * object.
     * @param card - Card object to be compared to.
     * @return - boolean value
     */
    public boolean equals(Card card){
        return !(this.face != card.face || this.suit != card.suit);
    }// End of equals() method
    
    
    /**
     * compareTo() method. Determines if the card value is higher, lower or
     * equal to a second card object.
     * @param card - Card object to be compared to.
     * @return - returns -1 if this card is higher, 1 if the other card is
     * higher or 0 if the cards are equal.
     */
    public int compareTo(Card card){
        int answer;
        
        if (this.face > card.face)
            answer = -1;
        else if (this.face < card.face)
            answer = 1;
        else
            answer = 0;
        
        return answer;
    }// End of compareTo() method
    
    
    /**
     * toString() method. Displays card information. Ex: 4 (clubs) or
     * Queen (Hearts). Using in main method debugger.
     * @return - String that concatenates SFace and Suit value.
     */
    @Override
    public String toString() {
        return (getSFace() + " ("+ suit +")")  ;
    }// End of toString() method
    
}//end of card class





//Left overs

    //constructor manual (may delete)
    /*
    public Card(Enum suit, int face) {
        this.suit = suit;
        this.face = face;
    }//end of constructor

    */
