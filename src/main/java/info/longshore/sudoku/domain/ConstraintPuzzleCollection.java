package info.longshore.sudoku.domain;

import java.util.LinkedList;
import java.util.List;

/**
 * A collection of puzzles.
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
