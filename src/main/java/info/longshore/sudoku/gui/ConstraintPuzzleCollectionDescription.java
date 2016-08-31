package info.longshore.sudoku.gui;

public class ConstraintPuzzleCollectionDescription {
  public ConstraintPuzzleCollectionDescription(String name, String type, int number) {
    collectionName = name;
    collectionType = type;
    numberOfPuzzlesInCollection = number;
  }

  public String getName() {
    return collectionName;
  }

  public String getTypeName() {
    return collectionType;
  }

  public int getNumberOfPuzzlesInCollection() {
    return numberOfPuzzlesInCollection;
  }

  @Override
  public String toString() {
    return collectionName;
  }

  private String collectionName;
  private String collectionType;
  private int numberOfPuzzlesInCollection;
}
