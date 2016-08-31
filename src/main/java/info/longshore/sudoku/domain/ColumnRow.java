package info.longshore.sudoku.domain;

import java.util.Set;
import java.util.TreeSet;

/**
 * A rule for no duplicates in the column, and the column contains all numbers.
 */
public class ColumnRow implements Rule {
  @Override
  public Set<Integer> getValidElementValues(ConstraintPuzzle cp, int elementNumber) {
    Set<Integer> validElements = new TreeSet<Integer>();

    for (int i = 1; i <= cp.getSize(); i++) {
      validElements.add(i);
    }

    int currentElementNumber = elementNumber % cp.getSize();
    while (currentElementNumber < cp.getNumberOfElements()) {
      if (elementNumber != currentElementNumber) {
        int elementValue = cp.getElementWithNumber(currentElementNumber);
        validElements.remove(elementValue);
      }

      currentElementNumber += cp.getSize();
    }

    return validElements;
  }

  @Override
  public Set<Integer> getRelatedElementNumbers(ConstraintPuzzle cp, int elementNumber) {
    // Return a set of all the element numbers in the column

    Set<Integer> numbersInColumn = new TreeSet<Integer>();

    int currentElementNumber = elementNumber % cp.getSize();
    while (currentElementNumber < cp.getNumberOfElements()) {
      if (elementNumber != currentElementNumber) {
        numbersInColumn.add(currentElementNumber);
      }

      currentElementNumber += cp.getSize();
    }

    return numbersInColumn;

  }
}
