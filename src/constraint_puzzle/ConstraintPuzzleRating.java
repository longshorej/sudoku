/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package constraint_puzzle;

import java.util.*;

/**
 * A rating about Sudoku
 * @author rm108s04
 */
public class ConstraintPuzzleRating {
    public ConstraintPuzzleRating() {
        movesUsed = new HashMap<String, Integer>();

        difficulty = Difficulty.UNKNOWN;
    }


    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int v) {
        difficulty = v;
    }

    public void addMove(String moveName) {
        Integer theInteger = movesUsed.get(moveName);
        if(theInteger == null) {
            movesUsed.put(moveName, 1);
        } else {
            movesUsed.put(moveName, theInteger + 1);
        }
    }

    public int getMovesUsed(String moveName) {
        Integer theInteger = movesUsed.get(moveName);
        if(theInteger == null) {
            return 0;
        } else {
            return theInteger.intValue();
        }
    }

    public Set<String> getMoveNames() {
        return movesUsed.keySet();
    }

    private Map<String, Integer> movesUsed;

    private int difficulty;
}
