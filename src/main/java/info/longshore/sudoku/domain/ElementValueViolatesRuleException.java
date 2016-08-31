package info.longshore.sudoku.domain;

public class ElementValueViolatesRuleException extends RuntimeException {
  public ElementValueViolatesRuleException(String message) {
    super(message);
  }

  public ElementValueViolatesRuleException() {
    super();
  }
}
