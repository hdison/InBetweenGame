package game;

/**
 * IllegalBetException Class
 * Description: An IllegalBetException is thrown when the current player
 * bets more than they currently have in their bankroll.
 * 
 * @author Allison Rojewski
 * @date
 */
public class IllegalBetException extends Exception 
{
    /**
     * Constructs an instance of <code>IllegalBetException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public IllegalBetException(String msg) 
    {
        super(msg);
    }
}
