package info.longshore.sudoku.domain;

import java.util.Set;

public interface Rule
{
    public Set<Integer> getValidElementValues(ConstraintPuzzle cp, int elementNumber);

    public Set<Integer> getRelatedElementNumbers(ConstraintPuzzle cp, int elementNumber);
}
