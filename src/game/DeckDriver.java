package game;

import javafx.scene.image.Image;

/**
 * Description: Driver program that exercises the functionality of both the card
 * and deck classes.
 * @author Luke Dantuono 
 * Date: 3 July 2019
 */
public class DeckDriver 
{

    public static void main(String[] args) 
    {
        Deck myDeck = new Deck();
        int testCases = 0;
        int inBetween = 0;    //counts if third card is inBetween
        int notInBetween = 0; //counts if third card is NOT inBetween
        int matchesCard = 0;  //counts if third card matches one of the first two

        for (int i = 0; i < 3; i++) 
        {
            testCases++;
            myDeck.shuffle();
            Card nextCard1 = myDeck.popStack();
            Card nextCard2 = myDeck.popStack();
            Card nextCard3 = null;

            //Deal first two cards
            System.out.println("The first card is a " + nextCard1.getSFace()
                    + " of " + nextCard1.getSuit());
            System.out.println("The second card is a " + nextCard2.getSFace()
                    + " of " + nextCard2.getSuit());
            
            System.out.println("The first card is  " + nextCard1.getSuit() 
            + nextCard1.getFace() + ".png");
            

            //Do first two card equal
            if (nextCard1.getFace() == nextCard2.getFace()) {
                System.out.println("The two cards are equal. Re-dealing.");
                matchesCard++;

            } else { //Deal third card
                nextCard3 = myDeck.popStack();
                System.out.println("The third card is a "
                        + nextCard3.getSFace() + " of "
                        + nextCard3.getSuit());
            }

            if (nextCard3 != null) { //Only if third card dealt

                //Is third card in between
                if (nextCard3.compareTo(nextCard1) == 1
                        && nextCard3.compareTo(nextCard2) == -1
                        || nextCard3.compareTo(nextCard1) == -1
                        && nextCard3.compareTo(nextCard2) == 1) { //check
                    System.out.println("The third card IS in between");
                    inBetween++;

                    //Is third card equal to first or second
                } else if ((nextCard3.compareTo(nextCard1) == 0
                        || nextCard3.compareTo(nextCard2) == 0)) {
                    System.out.println("The third card EQUALS one of the "
                            + "other two");
                    matchesCard++;

                } else { //Third card must not be in between
                    System.out.println("The third card is NOT in between");
                    notInBetween++;

                }

            }
            System.out.println("");
        }// end of for loop

        //Display statistics
        System.out.println("Number of test cases = " + testCases);
        System.out.println("inBetween = " + inBetween);
        System.out.println("notInBetween = " + notInBetween);
        System.out.println("Matches = " + matchesCard);

    }//End of main

}//End of class
