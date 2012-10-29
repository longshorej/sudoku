package constraint_puzzle;

import java.util.Set;
import java.util.TreeSet;

/**
 * A rule for no duplicates in the row, and the row contains all numbers.
 */
public class RowRule implements Rule
{
    @Override
    public Set<Integer> getValidElementValues(ConstraintPuzzle cp, int elementNumber)
    {
        Set<Integer> validElements = new TreeSet<Integer>();
        for (int i = 1; i <= cp.getSize(); i++) {
            validElements.add(i);
        }

        int startingElementNumber = elementNumber - (elementNumber % cp.getSize());

        for (int i = 0; i < cp.getSize(); i++) {
            int nextElementPosition = i + startingElementNumber;

            if (nextElementPosition != elementNumber) {
                int elementValue = cp.getElementWithNumber(nextElementPosition);
                validElements.remove(elementValue);
            }
        }

        return validElements;
    }

    @Override
    public Set<Integer> getRelatedElementNumbers(ConstraintPuzzle cp, int elementNumber)
    {
        // Return a set of all the element numbers in the row

        Set<Integer> numbersInRow = new TreeSet<Integer>();

        int startingElementNumber = elementNumber - (elementNumber % cp.getSize());

        for (int i = 0; i < cp.getSize(); i++) {
            int nextElementPosition = i + startingElementNumber;

            if (nextElementPosition != elementNumber) {
                numbersInRow.add(nextElementPosition);
            }
        }

        return numbersInRow;
    }
}
