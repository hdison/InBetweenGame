package game;

import java.util.Scanner;

/**
 * GameDriver Class
 * Description: Driver class to test Person and House classes.
 * @author Allison Rojewski
 * @date
 */
public class GameDriver {
    public static void main(String[] args) throws IllegalBetException {
        // Variables
        Scanner stdin = new Scanner(System.in);
        House house;
        Person player1;
        String player1Name;
        String input;
        boolean done = false;
        
        System.out.print("Enter your name: ");
        player1Name = stdin.nextLine();
        //player1Name = "Holly";
        
        System.out.print("You start with $100. \n"
        		+ "The buyin is $20. \n" +
                           "Do you wish to play? [Y/N] ");
        input = stdin.nextLine();
        if (!input.equalsIgnoreCase("Y")) 
        {
            System.out.println("OK, maybe next time.");
        } 
        else 
        {
            player1 = new Person(player1Name, 100);
            player1.setBuyIn(20);
            house = new House(20);
            do {
                house.playHand(player1);
                if (!player1.getActivePlayer()) {
                    done = true;
                } else if (house.getPot() == 0) {
                    done = true;
                }
                else {
                    System.out.println("The pot is now at " + house.getPot());
                    System.out.println("Press return to continue or any key " +
                                       "to quit");
                    input = stdin.nextLine();
                    if (input.length() > 0) {
                        done = true;
                    }
                }
            } while (!done);
            if (player1.getBankroll() <= 0) {
                System.out.println("You lose! Better luck next time.");
            } else if (house.getPot() <= 0) {
                System.out.println("You win!");
            }
        }
    } 
}
