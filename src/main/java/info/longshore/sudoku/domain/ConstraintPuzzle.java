package info.longshore.sudoku.domain;

import java.io.Serializable;
import java.util.*;

/**
 * An implementation of the concept of a constraint puzzle.
 * <p>
 * An instance of ConstraintPuzzle is an NxN grid which has a ruleset whose
 * purpose is to restrict the valid values for a given element.
 * <p>
 * TODO: ADD INITIAL SET OF VALUES [range of values]
 */
public class ConstraintPuzzle implements Serializable {

  /**
   * Returns a randomly generated puzzle in its final state (all filled in).
   * Puzzles whose solution is the return of this method can then be generated
   * by using getNewStartState.
   */
  public static ConstraintPuzzle getNewSolution(int size, Ruleset rs) {
    ConstraintPuzzle newSolution = new ConstraintPuzzle(size, rs);
    newSolution.solveWithBacktracking(true);
    return newSolution;
  }

  public ConstraintPuzzle() {
    setRandomNumberGeneratorSeed();
    setSize(0);
    setRuleset(new Ruleset());
  }

  public void setSize(int n) {
    size = n;

    // Initialize Elements
    elements = new int[size * size];
    for (int i = 0; i < elements.length; i++) {
      elements[i] = ConstraintPuzzle.EMPTY_ELEMENT_VALUE;
    }


    placedElements = new HashSet<Integer>();
    emptyElements = new HashSet<Integer>();

    // Setup empty
    for (int i = 0; i < elements.length; i++) {
      emptyElements.add(i);
    }

    // If the ruleset exists, initialize it
    if (ruleSet != null) {
      setRuleset(ruleSet);
    }
  }

  public void setRuleset(Ruleset rs) {
    if (rs == null) {
      return;
    }

    validElementsCache = new ArrayList<Set<Integer>>(elements.length);

    ruleSet = rs;
    for (int i = 0; i < elements.length; i++) {
      validElementsCache.add(i, ruleSet.getValidElementValues(this, i));
    }
  }

  public void setRandomNumberGeneratorSeed() {
    setRandomNumberGeneratorSeed(System.currentTimeMillis());
  }

  public void setRandomNumberGeneratorSeed(long seed) {
    randomNumberGeneratorSeed = seed;
    randomNumberGenerator = new Random(randomNumberGeneratorSeed);
  }

  /**
   * Constructs a constraint puzzle with given size and ruleset. The size is
   * equivalent to the number of rows/columns (both of which are equal).
   *
   * @param n  the size
   * @param rs the ruleset
   */
  public ConstraintPuzzle(int n, Ruleset rs) {
    setRandomNumberGeneratorSeed();
    setSize(n);
    setRuleset(rs);
  }

  public static ConstraintPuzzle getCopy(ConstraintPuzzle p) {

    try {
      ConstraintPuzzle c = p.getClass().newInstance();
      c.setRandomNumberGeneratorSeed(p.randomNumberGeneratorSeed);
      c.setSize(p.getSize());
      c.setRuleset(new Ruleset(p.ruleSet));

      // Should work :F
      for (int i = 0; i < c.elements.length; i++) {
        if (p.getElementWithNumber(i) != ConstraintPuzzle.EMPTY_ELEMENT_VALUE) {
          c.setElementWithNumber(i, p.getElementWithNumber(i));
        }
      }

      return c;
    } catch (Exception e) {
      // Nope.

      return null;
    }


  }

  /**
   * Quick 2 minute implementation until jason s's is done
   *
   * @return
   */
  public ConstraintPuzzle getNewStartState(int x) {

    ConstraintPuzzle copy = ConstraintPuzzle.getCopy(this);

    int numberToDivide = randomNumberGenerator.nextInt(3) + 1;

    int numberToRemove = getSize() / numberToDivide;

    while (numberToRemove > 0) {

      int posToRemove = randomNumberGenerator.nextInt(getNumberOfElements());

      copy.emptyElementWithNumber(posToRemove);

      numberToRemove--;
    }

    return copy;
  }

  /**
   * Returns the size of this instance of the constraint puzzle. The size is
   * equivalent to the number of rows / columns.
   *
   * @return the size
   */
  public int getSize() {
    return size;
  }

  /**
   * Maps row/col to element number, where rows and cols start at 0, and
   * elements start at 0.
   *
   * @param rowNumber    the row number
   * @param columnNumber the column number
   * @return the element number
   */
  public int getElementNumber(int rowNumber, int columnNumber) {
    int elementNumber = (size * rowNumber) + columnNumber;
    return elementNumber;
  }

  /**
   * Returns the next most constrained empty element number -- that is the
   * next element whose valid elements is smallest. If there are two or more
   * elements with the same amount of valid elements, make no assumptions as
   * to which element number will be returned.
   *
   * @return the next most constrained element number
   * @throws Sudoku.NoElementsEmptyException if theres no empty elements
   */
  public int getNextMostConstrainedEmptyElementNumber() throws NoElementsEmptyException {
    int smallestIndex = -1;

    for (Integer emptyElementNumber : emptyElements) {
      if (smallestIndex == -1 || validElementsCache.get(emptyElementNumber).size() < validElementsCache.get(smallestIndex).size()) {
        smallestIndex = emptyElementNumber;
      }
    }

    if (smallestIndex != -1) {
      return smallestIndex;
    } else {
      throw new NoElementsEmptyException();
    }
  }

  /**
   * Returns the number of the next empty element, where the element number is
   * the smallest of all empty element's numbers.
   * <p>
   * i.e. smallest empty index
   *
   * @return the number of the next empty element
   * @throws Sudoku.NoElementsEmptyException no other empty elements
   */
  public int getNextSequentialEmptyElementNumber() throws NoElementsEmptyException {
    int smallestIndex = -1;

    for (Integer emptyElementNumber : emptyElements) {
      if (smallestIndex == -1 || emptyElementNumber < smallestIndex) {
        smallestIndex = emptyElementNumber;
      }
    }

    if (smallestIndex != -1) {
      return smallestIndex;
    } else {
      throw new NoElementsEmptyException();
    }
  }

  /**
   * Returns the number of elements in the Sudoku puzzle, i.e. size^2
   *
   * @return the number of elements
   */
  public int getNumberOfElements() {
    return elements.length;
  }

  /**
   * Returns the value of the element with given number, where number 0 is top
   * left corner.
   *
   * @param elementNumber the element number
   * @return the elements value
   */
  public int getElementWithNumber(int elementNumber) {
    return elements[elementNumber];
  }

  /**
   * Returns value of element at row/col
   *
   * @param rowNumber    the row
   * @param columnNumber the col
   * @return element value
   */
  public int getElementAtPosition(int rowNumber, int columnNumber) {
    return getElementWithNumber(getElementNumber(rowNumber, columnNumber));
  }

  public int[] getElements() {
    return elements;
  }

  /**
   * Sets an element to a value.
   *
   * @param elementNumber the element number
   * @param value         the value
   * @throws Sudoku.ElementValueViolatesRuleException if the value violates
   *                                                  the ruleset
   */
  public void setElementWithNumber(int elementNumber, int value) throws ElementValueViolatesRuleException {
    if (!isValidValueForElementWithNumber(elementNumber, value)) {
      throw new ElementValueViolatesRuleException();
    }

    // Fix empty
    if (elements[elementNumber] == ConstraintPuzzle.EMPTY_ELEMENT_VALUE) {
      emptyElements.remove(elementNumber);
    }

    elements[elementNumber] = value;

    // Fix set
    placedElements.add(elementNumber);

    // Update affected valid elements
    Set<Integer> affectedElements = getRelatedElementNumbersForElementWithNumber(elementNumber);
    affectedElements.add(elementNumber);
    for (Integer affectedElementNumber : affectedElements) {
      validElementsCache.set(affectedElementNumber, getValidValuesForElementWithNumber(affectedElementNumber));
    }


  }

  /**
   * Sets an element at row/col to a value.
   *
   * @param rowNumber    the row
   * @param columnNumber the col
   * @param value        value to set
   * @throws Sudoku.ElementValueViolatesRuleException if value violates
   *                                                  ruleset
   */
  public void setElementAtPosition(int rowNumber, int columnNumber, int value) throws ElementValueViolatesRuleException {
    setElementWithNumber(getElementNumber(rowNumber, columnNumber), value);
  }

  /**
   * Emptys an element with given number
   *
   * @param elementNumber the element number
   */
  protected void emptyElementWithNumber(int elementNumber) {
    placedElements.remove(elementNumber);
    emptyElements.add(elementNumber);
    elements[elementNumber] = EMPTY_ELEMENT_VALUE;

    // Update affected valid elements
    Set<Integer> affectedElements = getRelatedElementNumbersForElementWithNumber(elementNumber);
    affectedElements.add(elementNumber);
    for (Integer affectedElementNumber : affectedElements) {
      validElementsCache.set(affectedElementNumber, getValidValuesForElementWithNumber(affectedElementNumber));
    }
  }

  /**
   * Emptys an element at row/col.
   *
   * @param rowNumber    the row
   * @param columnNumber the col
   */
  protected void emptyElementAtPosition(int rowNumber, int columnNumber) {
    emptyElementWithNumber(getElementNumber(rowNumber, columnNumber));
  }

  /**
   * Returns if the value is a valid one for an element with given number. If
   * the value is in the ruleset's valid values for the element, which
   * includes its current value as long as the element isnt empty, its valid.
   * If its empty or not in the valid set of values, its invalid.
   *
   * @param elementNumber the number of the element
   * @param value         the value to check
   * @return true if valid, false if not
   */
  public boolean isValidValueForElementWithNumber(int elementNumber, int value) {
    if (value == EMPTY_ELEMENT_VALUE) {
      return false;
    } else if (elements[elementNumber] == value) {
      return true;
    } else {
      for (int validElement : getValidValuesForElementWithNumber(elementNumber)) {
        if (validElement == value) {
          return true;
        }
      }

      return false;
    }
  }

  /**
   * Returns if the value is a valid one for an element with given row/col. If
   * the value is in the ruleset's valid values for the element, which
   * includes its current value as long as the element isnt empty, its valid.
   * If its empty or not in the valid set of values, its invalid.
   *
   * @param rowNumber    the row
   * @param columnNumber the col
   * @param value        the value to check
   * @return true if valid, false if not
   */
  public boolean isValidValueForElementAtPosition(int rowNumber, int columnNumber, int value) {
    return isValidValueForElementWithNumber(getElementNumber(rowNumber, columnNumber), value);
  }

  /**
   * Returns all the related element numbers for an element with number
   *
   * @param elementNumber the element number
   * @return a set of related element numbers
   */
  public Set<Integer> getRelatedElementNumbersForElementWithNumber(int elementNumber) {
    return ruleSet.getRelatedElementNumbers(this, elementNumber);
  }

  /**
   * Returns all the related element numbers for an element at row/col
   *
   * @param rowNumber    the row
   * @param columnNumber the col
   * @return a set of related element numbers
   */
  public Set<Integer> getRelatedElementNumbersForElementAtPosition(int rowNumber, int columnNumber) {
    return getRelatedElementNumbersForElementWithNumber(getElementNumber(rowNumber, columnNumber));
  }

  /**
   * Returns all the valid values for an element with number.
   *
   * @param elementNumber the element number
   * @return a set of valid values
   */
  public Set<Integer> getValidValuesForElementWithNumber(int elementNumber) {
    Set<Integer> validElementsFromRuleset = ruleSet.getValidElementValues(this, elementNumber);
    Set<Integer> validValues = validElementsFromRuleset;
    validValues.removeAll(eliminatedValues.get(elementNumber));
    return validValues;
  }

  /**
   * Removes an element from the set of valid elements for this number.
   *
   * @param elementNumber the element number the element number to remove for
   * @param elementValue  the element value the value to remove
   */
  protected void removeValueForElementWithNumber(int elementNumber, int elementValue) {
    // we dont care if they are removing a value which is not valid anyways

    // Add to recalculate list
    eliminatedValues.get(elementNumber).add(elementValue);

    // And update the cache
    validElementsCache.get(elementNumber).remove(elementValue);
  }

  /**
   * Returns all the valid values for an element at row/col
   *
   * @param rowNumber    the row
   * @param columnNumber the col
   * @return a set of valid values
   */
  public Set<Integer> getValidValuesForElementAtPosition(int rowNumber, int columnNumber) {
    return getValidValuesForElementWithNumber(getElementNumber(rowNumber, columnNumber));
  }

  public ConstraintPuzzle getSolution() {
    ConstraintPuzzle copy = ConstraintPuzzle.getCopy(this);
    copy.solveWithBacktracking();
    return copy;
  }

  /**
   * Solves puzzle using constraint backtracking and always picking numbers to
   * use sequentially (not randomly seeded)
   *
   * @return true if solved, false if wasnt solvable
   */
  public boolean solveWithBacktracking() {
    return solveWithBacktracking(false);
  }

  /**
   * Solve the puzzle using constraint backtracking
   *
   * @param guessRandomly Whether to pick numbers to try randomly
   * @return true if solved, false if wasnt solvable
   */
  public boolean solveWithBacktracking(boolean useRandomCode) {
    try {
      return solveWithBacktrackingRecursive(getNextMostConstrainedEmptyElementNumber(), useRandomCode);
    } catch (NoElementsEmptyException e) {
      return true;
    }
  }

  /**
   * Solves the puzzle using constraint backtracking, step by step.
   * Backtracking/Recursive algorithm.
   *
   * @param currentElementNumber next element to try
   * @param guessRandomly        whether to pick elements randomly
   * @return true if solvable, false if wasnt solvable
   */
  private boolean solveWithBacktrackingRecursive(int currentElementNumber, boolean guessRandomly) {
    Set<Integer> validElements = getValidValuesForElementWithNumber(currentElementNumber);

    if (validElements.size() < 1) {
      return false;
    } else {
      while (validElements.size() > 0) {
        int nextIndex;

        if (guessRandomly) {
          nextIndex = randomNumberGenerator.nextInt(validElements.size());
        } else {
          nextIndex = 0;
        }

        Integer validElement = null;
        int currentIndex = 0;
        for (Integer validElementC : validElements) {
          if (nextIndex == currentIndex) {
            validElement = validElementC;
            break;
          }

          currentIndex++;
        }
        setElementWithNumber(currentElementNumber, validElement);

        try {
          if (!solveWithBacktrackingRecursive(getNextMostConstrainedEmptyElementNumber(), guessRandomly)) {
            validElements.remove(validElement);
          } else {
            return true;
          }
        } catch (NoElementsEmptyException e) {
          return true;
        }
      }

      emptyElementWithNumber(currentElementNumber);
      return false;
    }
  }

  /**
   * Returns whether or not the puzzle is solved.
   *
   * @return true if solved, false if not.
   */
  public boolean isSolved() {
    return this.emptyElements.size() == 0;
  }

  /**
   * Returns the difficulty rating for this constraint puzzle.
   *
   * @return the difficulty rating of the puzzle, where the domain is in the
   * set of constants defined in class "Difficulty"
   */
  public ConstraintPuzzleRating getRating() {
    ConstraintPuzzle copy = ConstraintPuzzle.getCopy(this);


    ConstraintPuzzleRating r = new ConstraintPuzzleRating();
    if (copy.isSolved()) {
      r.setDifficulty(Difficulty.SOLVED);
    } else if (copy.solveWithBacktracking()) {
      r.setDifficulty(Difficulty.EASY);
    } else {
      r.setDifficulty(Difficulty.UNSOLVABLE);
    }

    return r;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final ConstraintPuzzle cp = (ConstraintPuzzle) obj;

    if (cp.elements.length != elements.length) {
      return false;
    } else {
      for (int i = 0; i < elements.length; i++) {
        if (cp.elements[i] != this.elements[i]) {
          return false;
        }
      }

      return true;
    }
  }

  @Override
  public int hashCode() {
    int runningTotal = 0;

    for (int i = 0; i < elements.length; i++) {
      runningTotal += i * elements[i];
    }

    return runningTotal;
  }

  @Override
  public String toString() {
    String stringRepresentation = "";

    for (int i = 0; i < (size * size); i++) {
      if (i > 0 && i % size == 0) {
        stringRepresentation += "\n";
      }
      stringRepresentation += " " + elements[i];
    }

    return stringRepresentation;
  }

  public boolean elementWithNumberIsEmpty(int elementNumber) {
    return getElementWithNumber(elementNumber) == ConstraintPuzzle.EMPTY_ELEMENT_VALUE;
  }

  public boolean elementAtPositionIsEmpty(int row, int col) {
    return elementWithNumberIsEmpty(getElementNumber(row, col));
  }

  /**
   * Constricts valid elements based on ruleSet stuff.
   *
   * @return true if applied, false if not
   */
  public boolean applyTechniqueRulesetConstriction() {
    boolean appliedTechnique = false;

    Iterator<Integer> emptyElementIterator = emptyElements.iterator();
    while (emptyElementIterator.hasNext() && !appliedTechnique) {
      int nextEmptyElement = emptyElementIterator.next();

      // Remove all valid elements for all related elements
      // Any numbers left over at the end would be the only valid
      // ones.
      Set<Integer> recalculatedValidElements = getValidValuesForElementWithNumber(nextEmptyElement);

      for (int relatedElement : getRelatedElementNumbersForElementWithNumber(nextEmptyElement)) {
        if (elementWithNumberIsEmpty(relatedElement)) {
          recalculatedValidElements.removeAll(getValidValuesForElementWithNumber(relatedElement));
        } else {
          recalculatedValidElements.remove(getElementWithNumber(nextEmptyElement));
        }
      }

      if (recalculatedValidElements.size() != 0) {
        // Work was done...save it and throw flag

        Set<Integer> elementsToRemove = getValidValuesForElementWithNumber(nextEmptyElement);
        elementsToRemove.removeAll(recalculatedValidElements);

        for (int elementToRemove : elementsToRemove) {
          this.removeValueForElementWithNumber(nextEmptyElement, elementToRemove);
        }

        appliedTechnique = true;
      }

    }

    return appliedTechnique;
  }

  protected Set<Integer> emptyElements; // Holds all the empty element numbers
  // Thoughts: if we keep emptyElements arranged by amount of choices for the element, we can gain some performance.
  private int size;
  private Ruleset ruleSet;
  protected Random randomNumberGenerator;
  protected int[] elements;
  protected ArrayList<Set<Integer>> validElementsCache; // Holds valid elements
  protected ArrayList<Set<Integer>> eliminatedValues; // Holds a set of eliminated values, where the arraylist index is the element number
  protected Set<Integer> placedElements; // Holds all the placed element numbers
  private long randomNumberGeneratorSeed;
  public static final int EMPTY_ELEMENT_VALUE = 0;
}
