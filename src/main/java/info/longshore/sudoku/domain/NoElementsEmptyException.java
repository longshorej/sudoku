package info.longshore.sudoku.domain;

public class NoElementsEmptyException extends RuntimeException
{
    public NoElementsEmptyException(String message)
    {
        super(message);
    }

    public NoElementsEmptyException()
    {
        super();
    }
}
