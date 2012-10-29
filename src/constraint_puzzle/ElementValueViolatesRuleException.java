package constraint_puzzle;

public class ElementValueViolatesRuleException extends RuntimeException
{
	public ElementValueViolatesRuleException(String message)
	{
		super(message);
	}

	public ElementValueViolatesRuleException()
	{
		super();
	}
}