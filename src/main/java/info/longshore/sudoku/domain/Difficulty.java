package info.longshore.sudoku.domain;

/**
 * A difficulty-constants class.
 *
 * Usage: Difficulty.EASY, etc...
 */
public class Difficulty
{
    protected Difficulty()
    {
    } // Block

    public static String getText(int d)
    {

        // Someone could make this a map...
        if (d == SOLVED) {
            return "Solved";
        } else if (d == EASY) {
            return "Easy";
        } else if (d == MEDIUM) {
            return "Medium";
        } else if (d == HARD) {
            return "Hard";
        } else if (d == REQUIRES_GUESSING) {
            return "Solvable By Guessing";
        } else if (d == UNSOLVABLE) {
            return "Unsolvable";
        } else {
            return "Unknown";
        }
    }

    public static final int SOLVED = 0;
    public static final int EASY = 1;
    public static final int MEDIUM = 2;
    public static final int HARD = 3;
    public static final int REQUIRES_GUESSING = 4;
    public static final int UNSOLVABLE = 5;
    public static final int UNKNOWN = -1;
}
