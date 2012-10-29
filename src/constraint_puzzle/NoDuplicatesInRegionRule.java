package constraint_puzzle;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

/**
 * A rule where all the elements
 * in the region must be unique,
 * and elements 1-n must be used.
 * 
 * @author Jason
 */
public class NoDuplicatesInRegionRule implements Rule
{
	public Set<Integer> getValidElementValues(ConstraintPuzzle cp, int elementNumber)
	{
		int sizeAllowed = (int)Math.sqrt(cp.getSize());
		int doubleSize = sizeAllowed * sizeAllowed;
		int tripleSize = doubleSize * sizeAllowed;

		int boxStartRow = (elementNumber / tripleSize) * sizeAllowed;
		int boxStartColumn = ( ( (elementNumber % doubleSize) / sizeAllowed)  ) * sizeAllowed;


		TreeSet<Integer> validElements = new TreeSet<Integer>();
		for(int i = 1; i <= doubleSize; i++)
		{
			validElements.add( new Integer(i) );
		}


		for(int i = 0; i < sizeAllowed; i++)
		{
			for(int j = 0; j < sizeAllowed; j++)
			{
				int rowPosition = boxStartRow + i;
				int colPosition = boxStartColumn + j;

				if( cp.getElementNumber(rowPosition, colPosition) != elementNumber)
				{
					int elementValue = cp.getElementAtPosition(rowPosition, colPosition);
					validElements.remove(elementValue);
				}
			}
		}

		return validElements;
	}

	public Set<Integer> getRelatedElementNumbers(ConstraintPuzzle cp, int elementNumber)
	{
		// Return a set of all the element numbers in the region

		Set<Integer> relatedElements = new TreeSet<Integer>();

		int sizeAllowed = (int)Math.sqrt(cp.getSize());
		int doubleSize = sizeAllowed * sizeAllowed;
		int tripleSize = doubleSize * sizeAllowed;

		int boxStartRow = (elementNumber / tripleSize) * sizeAllowed;
		int boxStartColumn = ( ( (elementNumber % doubleSize) / sizeAllowed)  ) * sizeAllowed;

		for(int i = 0; i < sizeAllowed; i++)
		{
			for(int j = 0; j < sizeAllowed; j++)
			{
				int rowPosition = boxStartRow + i;
				int colPosition = boxStartColumn + j;

				int elementNum = cp.getElementNumber(rowPosition, colPosition);

				if( elementNum != elementNumber)
				{
					relatedElements.add(elementNum);
				}
			}
		}

		return relatedElements;

	}
}
