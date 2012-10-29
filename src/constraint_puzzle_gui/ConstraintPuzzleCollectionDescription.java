/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package constraint_puzzle_gui;

/**
 *
 * @author rm110s05
 */
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

    @Override public String toString() {
        return collectionName;
    }

    private String collectionName;
    private String collectionType;
    private int numberOfPuzzlesInCollection;
}
