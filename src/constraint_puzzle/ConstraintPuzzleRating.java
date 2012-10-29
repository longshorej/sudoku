package constraint_puzzle;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A rating about Sudoku
 */
public class ConstraintPuzzleRating
{
    public ConstraintPuzzleRating()
    {
        movesUsed = new HashMap<String, Integer>();

        difficulty = Difficulty.UNKNOWN;
    }

    public int getDifficulty()
    {
        return difficulty;
    }

    public void setDifficulty(int v)
    {
        difficulty = v;
    }

    public void addMove(String moveName)
    {
        Integer theInteger = movesUsed.get(moveName);
        if (theInteger == null) {
            movesUsed.put(moveName, 1);
        } else {
            movesUsed.put(moveName, theInteger + 1);
        }
    }

    public int getMovesUsed(String moveName)
    {
        Integer theInteger = movesUsed.get(moveName);
        if (theInteger == null) {
            return 0;
        } else {
            return theInteger.intValue();
        }
    }

    public Set<String> getMoveNames()
    {
        return movesUsed.keySet();
    }
    
    private Map<String, Integer> movesUsed;
    private int difficulty;
}
