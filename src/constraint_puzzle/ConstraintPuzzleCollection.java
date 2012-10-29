/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package constraint_puzzle;
import java.util.List;
import java.util.LinkedList;

/**
 * A collection of puzzles.
 * 
 * @author rm108s04
 */
public class ConstraintPuzzleCollection {
    public ConstraintPuzzleCollection(String n, Class pc, Ruleset rs) {
        name = n;
        puzzleClass = pc;
        ruleset = rs;
        puzzles = new LinkedList<ConstraintPuzzle>();     
    }

    public String getName() {
        return name;
    }

    public Class getPuzzleClass() {
        return puzzleClass;
    }

    public Ruleset getRuleset() {
        return ruleset;
    }

    public void addPuzzle(ConstraintPuzzle puzzle) {
        puzzles.add(puzzle);
    }

    public List<ConstraintPuzzle> getPuzzles() {
        return puzzles;
    }

    private List<ConstraintPuzzle> puzzles;
    private String name;
    private Class puzzleClass;
    private Ruleset ruleset;
}
