package constraint_puzzle;

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
