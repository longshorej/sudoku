package info.longshore.sudoku.domain;

import java.util.*;

/**
 * An implementation of a SudokuPuzzle, extending the concept of a Constraint
 * Puzzle.
 */
public class SudokuPuzzle extends ConstraintPuzzle
{

    public static void main(String[] args)
    {
        try {
            if (true) {

                // Easy puzzle just to see how the rater works.
                SudokuPuzzle easyPuzzle = SudokuPuzzle.load9x9Example("48..72.3.1..5..92...53.1...3..28..5....165....6..39..4...4.73...52..3..9.3.92..75");
                System.out.println("Puzzle for easy rating test");
                System.out.println(easyPuzzle);
                System.out.println("difficulty: " + easyPuzzle.getRating());

                // Medium puzzle just to see how the rater works.
                SudokuPuzzle mediumPuzzle = SudokuPuzzle.load9x9Example("...8...9.5..2..1.....69.5.8.5....7.49.3...8.27.2....3.3.4.12.....1..5..6.7...9...");
                System.out.println("Puzzle for medium rating test");
                System.out.println(mediumPuzzle);
                System.out.println("difficulty: " + mediumPuzzle.getRating());

                // Hard puzzle just to see how the rater works.
                SudokuPuzzle hardPuzzle = SudokuPuzzle.load9x9Example("..1....73.....71...7..364.8......23...6...9...83......3.795..2...48.....12....3..");
                System.out.println("Puzzle for hard rating test");
                System.out.println(hardPuzzle);
                System.out.println("difficulty: " + hardPuzzle.getRating());


                // Nick Mic's Technique Test Cannidate Line.
                SudokuPuzzle cannidateLineTest = SudokuPuzzle.load9x9Example("..1957.63...8.6.7.76913.8.5..726135.312495786.56378...1.86.95.7.9.71.6.8674583...");
                System.out.println("\n\nPuzzle for Cannidate Line Technique");
                System.out.println(cannidateLineTest);
                System.out.println("difficulty: " + cannidateLineTest.getRating());

                // Jason Sed's Technique test Double Pairs.
                SudokuPuzzle doublePairTest = SudokuPuzzle.load9x9Example("934.6..5...6..4923..89...468..546..76...1...55..39..6236.4.127.47.6..5...8....634");
                System.out.println("Puzzle for double Pair Technique");
                System.out.println(doublePairTest);
                System.out.println("difficulty: " + doublePairTest.getRating());


                // Mark Jonhson's Technique Naked Pair
                SudokuPuzzle nakedPair = SudokuPuzzle.load9x9Example("4..27.6..798156234.2.84...7237468951849531726561792843.82.15479.7..243....4.87..2");
                System.out.println("\n\nPuzzle for Naked Pair Technique");
                System.out.println(nakedPair);
                System.out.println("difficulty: " + nakedPair.getRating());

                // Mat Folz's Technique X-Wing.
                SudokuPuzzle xWing = SudokuPuzzle.load9x9Example("9...5173.1.73982.55...76.9181.72435.2..165..7.75983.12.21537...75864912339.81257.");
                System.out.println("\n\nPuzzle for X-Wing Technique");
                System.out.println(xWing);
                System.out.println("difficulty: " + xWing.getRating());
            }

            //Mat Folz's Technique Sword.
            SudokuPuzzle swordFish = SudokuPuzzle.load9x9Example("195367248.78.5.3693.6.98157..378.59.7.9..5..65849.671.8325496719.7.13.25.51.729..");
            System.out.println("\n\nPuzzle for Swordfish Technique");
            System.out.println(swordFish);
            System.out.println("difficulty: " + swordFish.getRating());

        } catch (Exception e) {
          e.printStackTrace();
        }

    }

    public static SudokuPuzzle getCopy(SudokuPuzzle p)
    {

        return (SudokuPuzzle) ConstraintPuzzle.getCopy(p);

    }

    /**
     * Returns the ruleset used for all Sudoku puzzles.
     *
     * Things you can count on: rule index 0 : row rule index 1 : column rule
     * index 2 : region
     *
     * @return the ruleset
     */
    public static Ruleset getRuleset()
    {
        Ruleset sudokuRuleset = new Ruleset();
        sudokuRuleset.addRule(new NoDuplicatesInRowRule());
        sudokuRuleset.addRule(new NoDuplicatesInColumnRule());
        sudokuRuleset.addRule(new NoDuplicatesInRegionRule());
        return sudokuRuleset;
    }

    /**
     * Loads a Sudoku 9x9 constraint puzzle in the format used at the bottom of
     * the Wikipedia Sudoku algorithmics page.
     *
     * @param text the representation of the Sudoku. Blanks are periods.
     * @return ConstraintPuzzle the equivalent ConstraintPuzzle
     * @throws Sudoku.ElementValueViolatesRuleException If the Sudoku passed
     * violates rules, this is thrown
     */
    public static SudokuPuzzle load9x9Example(String text) throws ElementValueViolatesRuleException
    {
        SudokuPuzzle samplePuzzle = new SudokuPuzzle(3);

        for (int i = 0; i < 81; i++) {
            String thisCharacter = text.substring(i, i + 1);

            if (!thisCharacter.equals(".")) {
                int integerValue = Integer.parseInt(thisCharacter);
                samplePuzzle.setElementWithNumber(i, integerValue);
            }
        }

        return samplePuzzle;
    }

    /**
     * Creates a Sudoku puzzle with specified region size.
     *
     * @param regionSize the region size (e.g. for 9x9 puzzles, enter 3)
     */
    public SudokuPuzzle(int regionSize)
    {
        super(regionSize * regionSize, SudokuPuzzle.getRuleset());
    }

    public SudokuPuzzle()
    {
        super(0, getRuleset());
    }

    @Override
    public void setSize(int n)
    {
        super.setSize(n);
        eliminatedValues = new ArrayList<Set<Integer>>(this.getNumberOfElements());
        for (int i = 0; i < this.getNumberOfElements(); i++) {
            eliminatedValues.add(i, new HashSet<Integer>());
        }
    }

    public ConstraintPuzzleRating getRating()
    {
        //    System.out.print("GetRatingCalled()");

        //NOTE: the return data was literally changed the day
        // of the presentation.

        ConstraintPuzzleRating rating = new ConstraintPuzzleRating();


        if (isSolved()) {
            //  System.out.println(" 0 techniques used");
            rating.setDifficulty(Difficulty.SOLVED);
            return rating;
        } else {
            // Tally moves used
            int easyMovesUsed = 0;
            int mediumMovesUsed = 0;
            int hardMovesUsed = 0;

            // Work with a copy
            SudokuPuzzle copy = SudokuPuzzle.getCopy(this);
            while (!copy.isSolved()) {
                if (copy.applyTechniqueElimination()) {
                    //   System.out.println("Used Elimination");
                    easyMovesUsed++;
                    rating.addMove("Elimination");
                } else if (copy.applyTechniqueSingleInRow()) {
                    //      System.out.println("Used TechniqueSingleInRow");
                    easyMovesUsed++;
                    rating.addMove("Single (Row)");
                } else if (copy.applyTechniqueSingleInColumn()) {
                    //     System.out.println("Used TechniqueSingleInColumn");
                    easyMovesUsed++;

                    rating.addMove("Single (Col)");
                } else if (copy.applyTechniqueSingleInRegion()) {
                    //    System.out.println("Used TechniqueSingleInRegion");
                    easyMovesUsed++;

                    rating.addMove("Single (Box)");
                } else if (false && copy.applyTechniqueRulesetConstriction()) {
                    easyMovesUsed++;
                    while (copy.applyTechniqueElimination()) {
                    }
                } else if (copy.applyTechniqueCandidateLines()) {
                    //    System.out.println("Used CandidateLine");
                    while (copy.applyTechniqueElimination()) {
                        easyMovesUsed++;
                    }
                    mediumMovesUsed++;

                    rating.addMove("Candidate Line");
                } else if (copy.applyTechniqueDoublePair()) {
                    //    System.out.println("Used Double Pair Col");
                    while (copy.applyTechniqueElimination()) {
                    }
                    mediumMovesUsed += 2;

                    rating.addMove("Double Pair");
                } else if (copy.applyTechnniqueNakedPairCol()) {
                    //   System.out.println("Used Naked Pair Col");
                    while (copy.applyTechniqueElimination()) {
                    }
                    hardMovesUsed++;

                    rating.addMove("Naked Pair");
                } else if (copy.applyTechniqueXWing()) {
                    //      System.out.println("Used X-Wing");
                    hardMovesUsed++;
                    while (copy.applyTechniqueElimination()) {
                    }

                    rating.addMove("X-Wing");
                } else if (copy.applyTechniqueSwordfish()) {
                    //    System.out.println("Used Sword Fish");
                    while (copy.applyTechniqueElimination()) {
                    }
                    hardMovesUsed++;

                    rating.addMove("Swordfish");
                } else {
                    //   System.out.println("Copy: \n" + copy);
                    //System.out.println((easyMovesUsed + mediumMovesUsed + hardMovesUsed) + " techniques used");
                    if (copy.solveWithBacktracking()) {
                        rating.addMove("Backtracking");
                        //    System.out.println("This required guessing...");
                        rating.setDifficulty(Difficulty.REQUIRES_GUESSING);
                        return rating;
                    } else {
                        //    System.out.println("This is unsolvable...");
                        rating.setDifficulty(Difficulty.UNSOLVABLE);
                        return rating;
                    }

                }

                /*
                 * else if (copy.applyTechniqueForcingChains()){
                 * hardMovesUsed++;
                }
                 */

            }
            /*
             *
             * System.out.println("Done Rating"); System.out.println(" Esy: " +
             * easyMovesUsed); System.out.println(" Med: " + mediumMovesUsed);
             * System.out.println(" Hrd: " + hardMovesUsed);
             */

            // Note: If we get here, we are EASY, MEDIUM, or HARD. The above loop
            // returned REQUIRES_GUESSING / UNSOLVABLE.

            // TODO: scaling + range calculation

            // final double EASY_PERCENTAGE = .95;
            // final double MEDIUM_PERCENTAGE = 0.05;
            // final double HARD_PERCENTAGE = 0.02;
            //  double totalMoves = easyMovesUsed + mediumMovesUsed + hardMovesUsed;
            //  System.out.println((easyMovesUsed + mediumMovesUsed + hardMovesUsed) + " techniques used");

            // rofl this is because we dont have techniques


            if (hardMovesUsed > 0) {
                rating.setDifficulty(Difficulty.HARD);
            } else if (mediumMovesUsed > 2) {
                rating.setDifficulty(Difficulty.MEDIUM);
            } else {
                rating.setDifficulty(Difficulty.EASY);
            }
        }

        return rating;
    }

    /**
     * Applys one elimination step. This encompasses many elimination
     * techniques, as many are slight variations of the same concept.
     *
     * @return true if an element was set, false if not.
     */
    public boolean applyTechniqueElimination()
    {
        try {
            int emptyNumber = getNextMostConstrainedEmptyElementNumber();
            if (getValidValuesForElementWithNumber(emptyNumber).size() == 1) {
                int elementValue = this.validElementsCache.get(emptyNumber).iterator().next();
                setElementWithNumber(emptyNumber, elementValue);
                return true;
            } else {
                return false;
            }
        } catch (NoElementsEmptyException e) {
            return false;
        }
    }

    public boolean applyTechniqueSingleInRow()
    {
        Rule rowRule = getRuleset().getRule(0); // TODO: think about constants      
        return applyTechniqueSingleInRule(rowRule);
    }

    public boolean applyTechniqueSingleInColumn()
    {
        Rule columnRule = getRuleset().getRule(1); // TODO: think about constants        
        return applyTechniqueSingleInRule(columnRule);
    }

    public boolean applyTechniqueSingleInRegion()
    {
        Rule regionRule = getRuleset().getRule(2); // TODO: think about constants        
        return applyTechniqueSingleInRule(regionRule);
    }

    protected boolean applyTechniqueSingleInRule(Rule theRule)
    {
        /*
         * Things you can count on: rule index 0 : row rule index 1 : column
         * rule index 2 : region
         */

        boolean appliedTechnique = false;

        Iterator<Integer> emptyElementIterator = emptyElements.iterator();
        while (emptyElementIterator.hasNext() && !appliedTechnique) {
            int nextEmptyElement = emptyElementIterator.next();

            // Remove all valid elements for all related elements
            // Any numbers left over at the end would be the only valid
            // ones.
            Set<Integer> recalculatedValidElements = getValidValuesForElementWithNumber(nextEmptyElement);

            for (int relatedElement : theRule.getRelatedElementNumbers(this, nextEmptyElement)) {
                if (elementWithNumberIsEmpty(relatedElement)) {
                    recalculatedValidElements.removeAll(getValidValuesForElementWithNumber(relatedElement));
                } else {
                    recalculatedValidElements.remove(getElementWithNumber(nextEmptyElement));
                }
            }

            if (recalculatedValidElements.size() == 1) {



                // Work was done...save it and throw flag
                Iterator<Integer> recalculatedElementsIterator = recalculatedValidElements.iterator();
                int oneElement = recalculatedElementsIterator.next();
                setElementWithNumber(nextEmptyElement, oneElement);
                appliedTechnique = true;
            }

        }

        return appliedTechnique;
    }

    public boolean applyTechniqueCandidateLines()
    {
        //System.out.println("Trying CandidateLine");
        // dimension length of puzzle, is 9 for a 9x9 puzzle
        int puzzleLineLength = getSize();
        // dimension length of a single block (subsquare), is 3 for a 9x9 puzzle
        int blockLineLength = (int) Math.sqrt((double) getSize());

        try {
            //for each block (0 thru 8 for a 9x9)
            for (int currentBlock = 0; currentBlock < this.getSize()/*
               * ex:9
               */; currentBlock++) {
                // the upper-left-most element in the block.
                int blockFirstElement = currentBlock * blockLineLength/*
                   * ex:3
                   */ + (int) Math.floor(currentBlock / blockLineLength) * 2 * puzzleLineLength;
                // int[number of possible element values]
                int[] numOfLinesInWhichAGivenValueIsFound = new int[getSize()];
                // int[number of possible element values]
                int[] lineInWhichAGivenValueWasLastEncountered = new int[getSize()];
                // Initialize arrays.
                for (int value = 1; value <= getSize(); value++) {
                    numOfLinesInWhichAGivenValueIsFound[value - 1] = 0;
                    lineInWhichAGivenValueWasLastEncountered[value - 1] = -1;
                }

                //for each row, record candidates
                for (int row = 0; row < blockLineLength; row++) {
                    int rowFirstElement = blockFirstElement + puzzleLineLength * row;
                    int rowLastElement = rowFirstElement + blockLineLength - 1;

                    HashSet<Integer> potentialValuesInThisBlockRow = new HashSet<Integer>();

                    for (int position = rowFirstElement; position <= rowLastElement; position++) {
                        if (getElementWithNumber(position) == EMPTY_ELEMENT_VALUE) {
                            potentialValuesInThisBlockRow.addAll(validElementsCache.get(position));
                        } else {
                            potentialValuesInThisBlockRow.remove(getElementWithNumber(position));
                        }
                    }
                    //make note of which values appear in this row
                    for (int value = 1; value <= getSize(); value++) {
                        if (potentialValuesInThisBlockRow.contains(value)) {
                            numOfLinesInWhichAGivenValueIsFound[value - 1]++;
                            lineInWhichAGivenValueWasLastEncountered[value - 1] = row;
                        }
                    }
                } //end for each row
                //See if any value(s) occurs in only one row.
                for (int value = 1; value <= getSize(); value++) {
                    if (numOfLinesInWhichAGivenValueIsFound[value - 1] == 1) {
                        //If appears in only one row, eliminate it outside this block in this row.
                        int rowToEliminate = lineInWhichAGivenValueWasLastEncountered[value - 1];
                        int rowFirstElement = blockFirstElement;
                        while (rowFirstElement % puzzleLineLength != 0) {
                            rowFirstElement--;
                        }
                        rowFirstElement += puzzleLineLength * rowToEliminate;

                        boolean atLeastOneCandidateRemoved = false;
                        for (int position = rowFirstElement; position < rowFirstElement + puzzleLineLength; position++) {
                            //Eliminate elements outside current block.
                            if (position % puzzleLineLength < blockFirstElement % puzzleLineLength || position % puzzleLineLength > (blockFirstElement % puzzleLineLength) + blockLineLength - 1) {
                                if (getElementWithNumber(position) == EMPTY_ELEMENT_VALUE && validElementsCache.get(position).contains(value)) {
                                    atLeastOneCandidateRemoved = true;
                                    removeValueForElementWithNumber(position, value);
                                }
                            }
                        }
                        //Exit if a line of candidates has successfully been removed.
                        if (atLeastOneCandidateRemoved) {
                            return true;
                        }
                    }
                } //end for each value


                //now we do the same for columns

                //Reinitialize the arrays.
                for (int value = 1; value <= getSize(); value++) {
                    numOfLinesInWhichAGivenValueIsFound[value - 1] = 0;
                    lineInWhichAGivenValueWasLastEncountered[value - 1] = -1;
                }
                //for each column, record candidates
                for (int col = 0; col < blockLineLength; col++) {
                    int colFirstElement = blockFirstElement + col;
                    int colLastElement = colFirstElement + puzzleLineLength * (blockLineLength - 1);

                    HashSet<Integer> potentialValuesInThisBlockCol = new HashSet<Integer>();

                    for (int position = colFirstElement; position <= colLastElement; position += puzzleLineLength) {
                        if (getElementWithNumber(position) == EMPTY_ELEMENT_VALUE) {
                            potentialValuesInThisBlockCol.addAll(validElementsCache.get(position));
                        } else {
                            potentialValuesInThisBlockCol.remove(getElementWithNumber(position));
                        }
                    }
                    //make note of which values appear in this col
                    for (int value = 1; value <= getSize(); value++) {
                        if (potentialValuesInThisBlockCol.contains(value)) {
                            numOfLinesInWhichAGivenValueIsFound[value - 1]++;
                            lineInWhichAGivenValueWasLastEncountered[value - 1] = col;
                        }
                    }
                } //end for each col
                //See if any value(s) occurs in only one col.
                for (int value = 1; value <= getSize(); value++) {
                    if (numOfLinesInWhichAGivenValueIsFound[value - 1] == 1) {
                        //If appears in only one col, eliminate it outside this block in this col.
                        int colToEliminate = lineInWhichAGivenValueWasLastEncountered[value - 1];
                        int colFirstElement = (currentBlock % blockLineLength) * blockLineLength + colToEliminate;

                        boolean atLeastOneCandidateRemoved = false;
                        for (int position = colFirstElement; position < puzzleLineLength * puzzleLineLength; position += puzzleLineLength) {
                            //Eliminate elements outside current block
                            if (position < blockFirstElement || position > blockFirstElement + puzzleLineLength * (blockLineLength - 1) + blockLineLength - 1) {
                                if (getElementWithNumber(position) == EMPTY_ELEMENT_VALUE && validElementsCache.get(position).contains(value)) {
                                    atLeastOneCandidateRemoved = true;
                                    removeValueForElementWithNumber(position, value);
                                }
                            }
                        }
                        //Exit if a line of candidates has successfully been removed.
                        if (atLeastOneCandidateRemoved) {
                            return true;
                        }
                    }
                } //end for each value

            } // end for each block

            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean findOutIfPairEliminatesValues(int bC1, int bC2, int bC3, int bC4, int bC5, int bC6, int sE)
    {
        boolean isValida = this.isValidValueForElementWithNumber(bC1, sE);
        boolean isValidb = this.isValidValueForElementWithNumber(bC2, sE);
        boolean isValidc = this.isValidValueForElementWithNumber(bC3, sE);
        boolean isValidd = this.isValidValueForElementWithNumber(bC4, sE);
        boolean isValide = this.isValidValueForElementWithNumber(bC5, sE);
        boolean isValidf = this.isValidValueForElementWithNumber(bC6, sE);

        if (!this.elementWithNumberIsEmpty(bC1)) {
            isValida = false;
        }
        if (!this.elementWithNumberIsEmpty(bC2)) {
            isValidb = false;
        }
        if (!this.elementWithNumberIsEmpty(bC3)) {
            isValidc = false;
        }
        if (!this.elementWithNumberIsEmpty(bC4)) {
            isValidd = false;
        }
        if (!this.elementWithNumberIsEmpty(bC5)) {
            isValide = false;
        }
        if (!this.elementWithNumberIsEmpty(bC6)) {
            isValidf = false;
        }

        // System.out.println("isValida should be true and = " + isValida + " and isValidb should be false and = " + isValidb + " and isValidc should be true and = " + isValidc + " and isValidd should be false and = " + isValidd + " and isValide should be true and = " + isValide + " and isValidf should be false and = " + isValidf);
        if (isValida || isValidb || isValidc || isValidd || isValide || isValidf) {
            this.removeValueForElementWithNumber(bC1, sE);
            this.removeValueForElementWithNumber(bC2, sE);
            this.removeValueForElementWithNumber(bC3, sE);
            this.removeValueForElementWithNumber(bC4, sE);
            this.removeValueForElementWithNumber(bC5, sE);
            this.removeValueForElementWithNumber(bC6, sE);
            return true;
        }
        return false;
    }

    public boolean applyTechniqueDoublePair()
    {

        ArrayList<ArrayList<TreeSet<Integer>>> positionSet = new ArrayList<ArrayList<TreeSet<Integer>>>(9);
        for (int i = 0; i < 9; i++) {
            ArrayList<TreeSet<Integer>> innerArrayList = new ArrayList<TreeSet<Integer>>(6);
            for (int j = 0; j < 6; j++) {
                TreeSet<Integer> treeSet = new TreeSet<Integer>();
                innerArrayList.add(treeSet);
            }
            positionSet.add(innerArrayList);
        }
        int colIndex = 0;
        int boxIndex = 20;
        int pairIndex = 74;
        int keepCol = -1;
        int keepBox = 0;
        for (int i = 0; i <= 80; i = i + 1) {
            if (keepCol == 2) {
                keepCol = 0;
            } else {
                keepCol++;
            }
            if (this.elementWithNumberIsEmpty(i)) {
                positionSet.get(keepBox).get(keepCol).addAll(getValidValuesForElementWithNumber(i));
            }
            if (i == boxIndex) {
                Set<Integer> aSet = positionSet.get(keepBox).get(0);
                Set<Integer> bSet = positionSet.get(keepBox).get(1);
                Set<Integer> cSet = positionSet.get(keepBox).get(2);
                for (int elementInSetA : aSet) {
                    for (int elementInSetB : bSet) {
                        if (elementInSetA == elementInSetB) {
                            positionSet.get(keepBox).get(3).add(elementInSetA);
                            for (int elementInSetC : cSet) {
                                if (elementInSetC == elementInSetA) {
                                    positionSet.get(keepBox).get(3).remove(elementInSetA);
                                }
                            }
                        }
                    }
                    for (int elementInSetC : cSet) {
                        if (elementInSetA == elementInSetC) {
                            positionSet.get(keepBox).get(4).add(elementInSetA);
                            for (int elementInSetB : bSet) {
                                if (elementInSetB == elementInSetA) {
                                    positionSet.get(keepBox).get(4).remove(elementInSetA);
                                }
                            }
                        }
                    }
                }
                for (int elementInSetB : bSet) {
                    for (int elementInSetC : cSet) {
                        if (elementInSetB == elementInSetC) {
                            positionSet.get(keepBox).get(5).add(elementInSetC);
                            for (int elementInSetA : aSet) {
                                if (elementInSetA == elementInSetC) {
                                    positionSet.get(keepBox).get(5).remove(elementInSetB);
                                }
                            }
                        }
                    }
                }
                if (i == pairIndex) {
                    pairIndex = pairIndex + 3;
                    colIndex = colIndex - 69;
                    boxIndex = boxIndex - 51;
                    keepBox++;
                    if (i != 80) {
                        i = i - 72;
                    }
                } else {
                    colIndex = colIndex + 9;
                    boxIndex = colIndex + 20;
                    i = i + 6;
                    keepBox++;
                }
            } else if (i == colIndex + 2) {
                colIndex = colIndex + 9;
                i = i + 6;
            }
        }

        boolean pairFound = false;
        int set1 = 0;
        int set2 = 1;
        int boxOut = 2;
        boolean setsNeedScanning = true;

        while (setsNeedScanning) {

            for (int i = 3; i < 6; i++) {
                Set<Integer> setToCompare1 = positionSet.get(set1).get(i);
                Set<Integer> setToCompare2 = positionSet.get(set2).get(i);
                for (int elementInSet1 : setToCompare1) {
                    for (int elementInSet2 : setToCompare2) {
                        if (elementInSet1 == elementInSet2 & !pairFound) {
                            int sP;
                            if (boxOut < 3) {
                                sP = 27 * boxOut;
                            } else if (boxOut < 6) {
                                sP = 27 * (boxOut - 3) + 3;
                            } else {
                                sP = 27 * (boxOut - 6) + 6;
                            }
                            if (i == 3) {
                                pairFound = this.findOutIfPairEliminatesValues((sP), (sP + 1), (sP + 9), (sP + 10), (sP + 18), (sP + 19), elementInSet1);
                                if (pairFound == true) {
                                    setsNeedScanning = false;
                                }
                            } else if (i == 4) {
                                pairFound = this.findOutIfPairEliminatesValues((sP), (sP + 2), (sP + 9), (sP + 11), (sP + 18), (sP + 20), elementInSet1);
                                if (pairFound == true) {
                                    setsNeedScanning = false;
                                }
                            } else {
                                pairFound = this.findOutIfPairEliminatesValues((sP + 1), (sP + 2), (sP + 10), (sP + 11), (sP + 19), (sP + 20), elementInSet1);
                                if (pairFound == true) {
                                    setsNeedScanning = false;
                                }
                            }
                        }
                    }
                }
            }
            if (set1 == 0) {
                if (set2 == 1) {
                    set2 = 2;
                    boxOut = 1;
                } else {
                    set1 = 1;
                    boxOut = 0;
                }
            } else if (set1 == 1) {
                set1 = 3;
                set2 = 4;
                boxOut = 5;
            } else if (set1 == 3) {
                if (set2 == 4) {
                    set2 = 5;
                    boxOut = 4;
                } else {
                    set1 = 4;
                    set2 = 5;
                    boxOut = 3;
                }
            } else if (set1 == 4) {
                set1 = 6;
                set2 = 7;
                boxOut = 8;
            } else if (set1 == 6) {
                if (set2 == 7) {
                    set2 = 8;
                    boxOut = 7;
                } else {
                    set1 = 7;
                    set2 = 8;
                    boxOut = 6;
                }
            } else {
                setsNeedScanning = false;
            }
            if (pairFound == true) {
                return pairFound;
            }
        }
        return pairFound;
    }

    public boolean applyTechnniqueNakedPairCol()
    {
        boolean removedSomething = false;
//        System.out.println("Trying Naked Pair");
        try {
            int emptyNumber = getNextMostConstrainedEmptyElementNumber();
            int puzzleSize = this.getSize();
            int tmpNumber; //This helps us iterate through the columns
            int tmpRowNumber; //This will keep track for row iterations
            boolean foundNakedPair = false;

            //Candidate cell arrays.
            Integer[] firstCandidateStorageArray = new Integer[2];
            Integer[] secondCandidateStorageArray = new Integer[2];

            //search until the next constrained value is greater than 2 or we find a naked pair.
            //while (getValidValuesForElementWithNumber(emptyNumber).size() == 2)
            while (emptyNumber < puzzleSize * puzzleSize) {
                if (elementWithNumberIsEmpty(emptyNumber) && getValidValuesForElementWithNumber(emptyNumber).size() == 2) {
                    //store cells candidate values.
                    firstCandidateStorageArray = this.validElementsCache.get(emptyNumber).toArray(firstCandidateStorageArray);


                    //Our tmpNumber starts at the first row of our possible column with pairs.
                    tmpRowNumber = emptyNumber;
                    tmpNumber = emptyNumber - ((emptyNumber / puzzleSize) * puzzleSize);  //gives first row value of the column.

                    //search rest of col for size 2 candidate cells and compare them with this one.
                    for (int i = 0; i < puzzleSize && !foundNakedPair && tmpNumber < puzzleSize * puzzleSize; i++) {
                        if (elementWithNumberIsEmpty(tmpNumber) && getValidValuesForElementWithNumber(tmpNumber).size() == 2 && tmpNumber != emptyNumber) {
                            //store other candidate values in the other cells when the number of candidate values equals 2.
                            secondCandidateStorageArray = this.validElementsCache.get(tmpNumber).toArray(secondCandidateStorageArray);

                            if (firstCandidateStorageArray[0] == secondCandidateStorageArray[0] && firstCandidateStorageArray[1] == secondCandidateStorageArray[1]) {
                                //break out of loop.  No need to check other cells now since we have found a pair.
                                foundNakedPair = true;

                                int checkCellNumber = emptyNumber - ((emptyNumber / puzzleSize) * puzzleSize);

                                //loop back through and eliminate all occurences of first and second candidate values in cells other than our paired cells.
                                for (int j = 0; j < puzzleSize && checkCellNumber < puzzleSize * puzzleSize; j++) {
                                    if (elementWithNumberIsEmpty(checkCellNumber) && checkCellNumber != tmpNumber && checkCellNumber != emptyNumber) {
                                        Set<Integer> beforeRemove = new HashSet<Integer>(this.validElementsCache.get(checkCellNumber));

                                        /*
                                         * System.out.print("emptyNumber: ");
                                         * System.out.println(emptyNumber);
                                         * System.out.print("tmpNumber: ");
                                         * System.out.println(tmpNumber);
                                         *
                                         *
                                         * System.out.print("Removing0: ");
                                         * System.out.print(firstCandidateStorageArray[0]);
                                         * System.out.print(" ");
                                         * System.out.println(firstCandidateStorageArray[1]);
                                         *
                                         * System.out.print("Removing1: ");
                                         * System.out.print(secondCandidateStorageArray[0]);
                                         * System.out.print(" ");
                                         * System.out.println(secondCandidateStorageArray[1]);
                                         *
                                         * System.out.print("From cell: ");
                                         * System.out.println(checkCellNumber);
                                         *
                                         * System.out.println("Before remove:");
                                         * System.out.println(beforeRemove);
                                         */

                                        this.removeValueForElementWithNumber(checkCellNumber, firstCandidateStorageArray[0]);
                                        this.removeValueForElementWithNumber(checkCellNumber, firstCandidateStorageArray[1]);


                                        // System.out.println("After remove:");
                                        //  System.out.println(this.validElementsCache.get(checkCellNumber));


                                        if (!beforeRemove.equals(this.validElementsCache.get(checkCellNumber))) {
                                            removedSomething = true;
                                        } //end inner if
                                    } //end if
                                    checkCellNumber = checkCellNumber + puzzleSize;
                                } //end inner for
                                if (removedSomething) {
//
//                                    System.out.print("Column\n[0]: ");
//                                    System.out.println(firstCandidateStorageArray[0]);
//                                    System.out.print("[1]: ");
//                                    System.out.println(firstCandidateStorageArray[1]);
//
//
//                                    System.out.println("EXITING TRUE! - COL");

                                    return true;
                                } else {
                                    foundNakedPair = false; //didn't change anything so keep looking.
                                }
                            } //end inner if
                        } //end if
                        tmpNumber = tmpNumber + puzzleSize;  //increment to next row.
                    } //end for

                    //Do row work here...
//                    int checkRowCellNumber = tmpRowNumber;
//
                    while (tmpRowNumber % puzzleSize != 0) {
                        tmpRowNumber--; //Navigate to first row entry (left most cell)
                    }

                    for (int i = 0; i < puzzleSize && !foundNakedPair && tmpRowNumber < puzzleSize * puzzleSize; i++) {
                        if (elementWithNumberIsEmpty(tmpRowNumber) && getValidValuesForElementWithNumber(tmpRowNumber).size() == 2 && tmpRowNumber != emptyNumber) {
                            //store other candidate values in the other cells when the number of candidate values equals 2.
                            secondCandidateStorageArray = this.validElementsCache.get(tmpRowNumber).toArray(secondCandidateStorageArray);

                            if (firstCandidateStorageArray[0] == secondCandidateStorageArray[0] && firstCandidateStorageArray[1] == secondCandidateStorageArray[1]) {
                                //break out of loop.  No need to check other cells now since we have found a pair.
                                foundNakedPair = true;

                                int checkRowCellNumber = tmpRowNumber;

                                while (checkRowCellNumber % puzzleSize != 0) {
                                    checkRowCellNumber--; //Navigate to first row entry (left most cell)
                                }

                                //loop back through and eliminate all occurences of first and second candidate values in cells other than our paired cells.
                                for (int j = 0; j < puzzleSize && checkRowCellNumber < puzzleSize * puzzleSize; j++) {
                                    if (elementWithNumberIsEmpty(checkRowCellNumber) && checkRowCellNumber != tmpRowNumber && checkRowCellNumber != emptyNumber) {
                                        Set<Integer> beforeRemove = new HashSet<Integer>(this.validElementsCache.get(checkRowCellNumber));


                                        /*
                                         * System.out.print("RowEmptyNumber: ");
                                         * System.out.println(emptyNumber);
                                         * System.out.print("RowTmpNumber: ");
                                         * System.out.println(tmpRowNumber);
                                         *
                                         *
                                         * System.out.print("Removing0: ");
                                         * System.out.print(firstCandidateStorageArray[0]);
                                         * System.out.print(" ");
                                         * System.out.println(firstCandidateStorageArray[1]);
                                         *
                                         * System.out.print("Removing1: ");
                                         * System.out.print(secondCandidateStorageArray[0]);
                                         * System.out.print(" ");
                                         * System.out.println(secondCandidateStorageArray[1]);
                                         *
                                         * System.out.print("From cell: ");
                                         * System.out.println(checkRowCellNumber);
                                         *
                                         * System.out.println("Before row
                                         * remove:");
                                         * System.out.println(beforeRemove);
                                         */
                                        this.removeValueForElementWithNumber(checkRowCellNumber, firstCandidateStorageArray[0]);
                                        this.removeValueForElementWithNumber(checkRowCellNumber, firstCandidateStorageArray[1]);

                                        //System.out.println("After row remove:");
                                        //     System.out.println(this.validElementsCache.get(checkRowCellNumber));

                                        if (!beforeRemove.equals(this.validElementsCache.get(checkRowCellNumber))) {
                                            removedSomething = true;
                                        } //end inner if
                                    } //end if
                                    checkRowCellNumber++; //= checkCellNumber + puzzleSize;
                                } //end inner for

                                if (removedSomething) {

                                    //  System.out.print("Row\n[0]: ");
                                    //  System.out.println(firstCandidateStorageArray[0]);
                                    //  System.out.print("[1]: ");
                                    //  System.out.println(firstCandidateStorageArray[1]);

//                                    System.out.println("EXITING TRUE! - ROW");

                                    return true;
                                } else {
                                    foundNakedPair = false; //didn't change anything so keep looking.
                                }
                            } //end inner if
                        } //end if
                        tmpRowNumber++; //increment to next row element.
                    }
                } //end emptyCell.size() = 2 if

                //increment to the next most constrained cell here
          /*
                 * Integer[] tmpCandidateStorageArray =
                 * this.validElementsCache.get(emptyNumber).toArray(secondCandidateStorageArray);
                 * System.out.print(emptyNumber); System.out.print(":");
                 * System.out.print(tmpCandidateStorageArray[0]);
                 * System.out.print(" ");
                 * System.out.println(tmpCandidateStorageArray[1]);
                 * //System.out.println(tmpCandidateStorageArray[2]);
                 */
                emptyNumber++;  //= getNextMostConstrainedEmptyElementNumber();

            } //end while
            //found no naked pairs.
            return false;
        } //end try
        catch (NoElementsEmptyException e) {
            return false;
        }
    } //end applyTechniqueNakedPairCol( )

    /*
     * Multi Line
     */
    /**
     * This method is implementing the x-wing technique to eleminate canidates
     * for a given cell.
     *
     * @return true if the technique was used other wise false
     */
    public boolean applyTechniqueXWing()
    {
        //System.out.println("Trying X-Wing");
        /*
         * how it works scan each colums/row for a number that can only be in
         * two spots. then scan the colums/rows of those numbers for another
         * number that meats those condisions then remove that number from all
         * other locations but those 4.
         */

        int currentCell = 0;

        Set<Integer> valuesCheckForRow = new HashSet<Integer>();

        for (int i = 0; i < this.getSize(); i++) {
            valuesCheckForRow.clear();

            for (int j = 0; j < this.getSize(); j++) {
                currentCell = i * this.getSize() + j;
                if (getElementWithNumber(currentCell) == EMPTY_ELEMENT_VALUE) {
                    for (int value : this.validElementsCache.get(currentCell)) {
                        if (!valuesCheckForRow.contains(value) && !rowXwing(currentCell, value)) {
                            if (colXwing(currentCell, value)) {
                                return (true);
                            }
                        } else if (!valuesCheckForRow.contains(value)) {
                            return (true);
                        }
                        valuesCheckForRow.add(value);
                    }
                }
            }
        }
        return (false);
    }

    private boolean rowXwing(int rowElement, int value)
    {
        boolean removedSomething = false;
        //System.out.println("Trying XWing row");

        //this is for the row emplitation.
        Set<Integer> rowElementNumber = findGivenValueInRow(value, rowElement + 1, 1);
        if (rowElementNumber.size() > 0) {
            //loop threw rowElementNumber.
            Set<Integer> colElementNumber1 = findGivenValueInCol(value, rowElement, 0);
            colElementNumber1.remove(rowElement);

            int nextRowElementNumber = rowElementNumber.iterator().next();
            Set<Integer> colElementNumber2 = findGivenValueInCol(value, nextRowElementNumber, 0);
            colElementNumber2.remove(nextRowElementNumber);

            for (int element1 : colElementNumber1) {
                int rowElementNumber1 = element1 / this.getSize();
                for (int element2 : colElementNumber2) {
                    int rowElementNumber2 = element2 / this.getSize();
                    if (rowElementNumber1 == rowElementNumber2) {
                        if (findGivenValueInRow(value, rowElementNumber2 * this.getSize(), 2).size() > 1) {
                            //looping threw the col to remove the value
                            int startOfCol1 = element1 % this.getSize();
                            int startOfCol2 = element2 % this.getSize();

                            for (int i = startOfCol1; i < (this.getSize() * this.getSize()); i += this.getSize()) {
                                if (i != element1 && i != rowElement && i != element2 && i != nextRowElementNumber && getElementWithNumber(i) == EMPTY_ELEMENT_VALUE) {
                                    Set<Integer> beforeRemove = new HashSet<Integer>(this.validElementsCache.get(i));
                                    removeValueForElementWithNumber(i, value);
                                    if (!beforeRemove.equals(this.validElementsCache.get(i))) {
                                        //System.out.println("1 Value: " + value + " i: " + i);
                                        removedSomething = true;
                                    }
                                }
                            }

                            for (int i = startOfCol2; i < (this.getSize() * this.getSize()); i += 9) {
                                if (i != element1 && i != rowElement && i != element2 && i != nextRowElementNumber && getElementWithNumber(i) == EMPTY_ELEMENT_VALUE) {
                                    Set<Integer> beforeRemove = new HashSet<Integer>(this.validElementsCache.get(i));
                                    removeValueForElementWithNumber(i, value);
                                    if (!beforeRemove.equals(this.validElementsCache.get(i))) {
                                        //System.out.println("2 Value: " + value + " i: " + i);
                                        removedSomething = true;
                                    }
                                }
                            }
                        }
                    }
                    if (removedSomething) {
                        break;
                    }
                }
                if (removedSomething) {
                    break;
                }
            }
        }

        return (removedSomething);
    }

    private boolean colXwing(int colElement, int value)
    {
        boolean removedSomething = false;

        //this is for the row emplitation.
        Set<Integer> colElementNumber = findGivenValueInCol(value, colElement + getSize(), 1);
        if (colElementNumber.size() > 0) {
            //loop threw rowElementNumber.
            Set<Integer> rowElementNumber1 = findGivenValueInRow(value, colElement, 0);
            rowElementNumber1.remove(colElement);

            int nextColElementNumber = colElementNumber.iterator().next();
            Set<Integer> rowElementNumber2 = findGivenValueInRow(value, nextColElementNumber, 0);
            rowElementNumber2.remove(nextColElementNumber);

            for (int element1 : rowElementNumber1) {
                int colElementNumber1 = element1 / this.getSize();
                for (int element2 : rowElementNumber2) {
                    int colElementNumber2 = element2 / this.getSize();
                    if (colElementNumber1 == colElementNumber2) {
                        if (findGivenValueInRow(value, colElementNumber2 * this.getSize(), 2).size() > 1) {
                            //looping threw the col to remove the value
                            int startOfRow1 = element1 % this.getSize();
                            int startOfRow2 = element2 % this.getSize();

                            for (int i = startOfRow1; i < (this.getSize() * this.getSize()); i += this.getSize()) {
                                if (i != element1 && i != colElement && i != element2 && i != nextColElementNumber && getElementWithNumber(i) == EMPTY_ELEMENT_VALUE) {
                                    Set<Integer> beforeRemove = new HashSet<Integer>(this.validElementsCache.get(i));
                                    removeValueForElementWithNumber(i, value);
                                    if (!beforeRemove.equals(this.validElementsCache.get(i))) {
                                        removedSomething = true;
                                    }
                                }
                            }

                            for (int i = startOfRow2; i < (this.getSize() * this.getSize()); i += 9) {
                                if (i != element1 && i != colElement && i != element2 && i != nextColElementNumber && getElementWithNumber(i) == EMPTY_ELEMENT_VALUE) {
                                    Set<Integer> beforeRemove = new HashSet<Integer>(this.validElementsCache.get(i));
                                    removeValueForElementWithNumber(i, value);
                                    if (!beforeRemove.equals(this.validElementsCache.get(i))) {
                                        removedSomething = true;
                                    }
                                }
                            }
                        }
                    }
                    if (removedSomething) {
                        break;
                    }
                }
                if (removedSomething) {
                    break;
                }
            }
        }

        return (removedSomething);
    }

    public boolean applyTechniqueSwordfish()
    {
        //System.out.println("Trying Swordfish");
        /*
         * how it works scan each colums/row for a number that can only be in
         * two spots. then scan the colums/rows of those numbers for another
         * number that meats those condisions then remove that number from all
         * other locations but those 6.
         */

        int currentCell = 0;

        Set<Integer> valuesCheckForRow = new HashSet<Integer>();

        for (int i = 0; i < this.getSize(); i++) {
            valuesCheckForRow.clear();
            for (int j = 0; j < this.getSize(); j++) {
                currentCell = i * this.getSize() + j;
                if (getElementWithNumber(currentCell) == EMPTY_ELEMENT_VALUE) {
                    for (int value : this.validElementsCache.get(currentCell)) {
                        if (!valuesCheckForRow.contains(value) && rowSwordfish(currentCell, value)) {
                            return (true);
                        }
//                        else if( !valuesCheckForRow.contains(value) && colSwordfish(currentCell,value)){
//                            return(true);
//                        }
                        valuesCheckForRow.add(value);
                    }
                }
            }
        }
        return (false);
    }

    private boolean rowSwordfish(int rowElement, int value)
    {
        boolean removedSomething = false;
        int rowStart = 0;

        //this is for the row emplitation.
        Set<Integer> rowElementNumber = findGivenValueInRow(value, rowElement + 1, 1);
        if (rowElementNumber.size() > 0) {
            //loop threw rowElementNumber.
            Set<Integer> colElementNumber1 = findGivenValueInCol(value, rowElement, 0);
            colElementNumber1.remove(rowElement);

            int nextRowElementNumber2 = rowElementNumber.iterator().next();
            Set<Integer> colElementNumber2 = findGivenValueInCol(value, nextRowElementNumber2, 0);
            colElementNumber2.remove(nextRowElementNumber2);
            //have 2 of the six numbers needed

            if (colElementNumber1.size() > 0 && colElementNumber2.size() > 0) {
                for (int nextRowElementNumber3 : colElementNumber1) {
                    //int nextRowElementNumber3 = colElementNumber1.iterator().next();
                    rowStart = (nextRowElementNumber3 / this.getSize()) * this.getSize();
                    Set<Integer> rowElementNumber2 = findGivenValueInRow(value, rowStart, 2);
                    rowElementNumber2.remove(nextRowElementNumber3);
                    if (rowElementNumber2.size() > 0) {
                        int nextRowElementNumber4 = rowElementNumber2.iterator().next();
                        int columenEnd = nextRowElementNumber4 % this.getSize();
                        for (int nextRowElementNumber5 : colElementNumber2) {
                            //int nextRowElementNumber5 = colElementNumber2.iterator().next();
                            rowStart = (nextRowElementNumber5 / this.getSize()) * this.getSize();
                            Set<Integer> rowElementNumber3 = findGivenValueInRow(value, rowStart, 2);
                            rowElementNumber3.remove(nextRowElementNumber5);
                            if (rowElementNumber3.size() > 0) {
                                int nextRowElementNumber6 = rowElementNumber3.iterator().next();
                                int checkElementEnd = nextRowElementNumber6 % this.getSize();
                                if (checkElementEnd == columenEnd) {
                                    int startOfCol1 = rowElement % this.getSize();
                                    int startOfCol2 = nextRowElementNumber2 % this.getSize();
                                    int startOfCol3 = nextRowElementNumber6 % this.getSize();

                                    //Remove the values
                                    for (int i = startOfCol1; i < (this.getSize() * this.getSize()); i += this.getSize()) {
                                        if (i != rowElement && i != nextRowElementNumber2 && i != nextRowElementNumber3 && i != nextRowElementNumber4 && i != nextRowElementNumber5 && i != nextRowElementNumber6 && getElementWithNumber(i) == EMPTY_ELEMENT_VALUE) {
                                            Set<Integer> beforeRemove = new HashSet<Integer>(this.validElementsCache.get(i));
                                            removeValueForElementWithNumber(i, value);
                                            if (!beforeRemove.equals(this.validElementsCache.get(i))) {
                                                removedSomething = true;
                                            }
                                        }
                                    }
                                    for (int i = startOfCol2; i < (this.getSize() * this.getSize()); i += 9) {
                                        if (i != rowElement && i != nextRowElementNumber2 && i != nextRowElementNumber3 && i != nextRowElementNumber4 && i != nextRowElementNumber5 && i != nextRowElementNumber6 && getElementWithNumber(i) == EMPTY_ELEMENT_VALUE) {
                                            Set<Integer> beforeRemove = new HashSet<Integer>(this.validElementsCache.get(i));
                                            removeValueForElementWithNumber(i, value);
                                            if (!beforeRemove.equals(this.validElementsCache.get(i))) {
                                                removedSomething = true;
                                            }
                                        }
                                    }
                                    for (int i = startOfCol3; i < (this.getSize() * this.getSize()); i += 9) {
                                        if (i != rowElement && i != nextRowElementNumber2 && i != nextRowElementNumber3 && i != nextRowElementNumber4 && i != nextRowElementNumber5 && i != nextRowElementNumber6 && getElementWithNumber(i) == EMPTY_ELEMENT_VALUE) {
                                            Set<Integer> beforeRemove = new HashSet<Integer>(this.validElementsCache.get(i));
                                            removeValueForElementWithNumber(i, value);
                                            if (!beforeRemove.equals(this.validElementsCache.get(i))) {
                                                removedSomething = true;
                                            }
                                        }
                                    }
                                }
                            }
                            if (removedSomething) {
                                break;
                            }
                        }
                    }
                    if (removedSomething) {
                        break;
                    }
                }
            }
        }
        return (removedSomething);
    }

    private boolean colSwordfish(int colElement, int value)
    {
        boolean removedSomething = false;
        int colStart = 0;

        //this is for the row emplitation.
        Set<Integer> colElementNumber = findGivenValueInCol(value, colElement, 1);
        if (colElementNumber.size() > 0) {
            //loop threw rowElementNumber.
            Set<Integer> rowElementNumber1 = findGivenValueInRow(value, colElement, 0);
            rowElementNumber1.remove(colElement);

            int nextColElementNumber2 = colElementNumber.iterator().next();
            Set<Integer> rowElementNumber2 = findGivenValueInCol(value, nextColElementNumber2, 0);
            rowElementNumber2.remove(nextColElementNumber2);
            //have 2 of the six numbers needed

            if (rowElementNumber1.size() > 0 && rowElementNumber2.size() > 0) {
                for (int nextColElementNumber3 : rowElementNumber1) {
                    colStart = (nextColElementNumber3 / this.getSize()) * this.getSize();
                    Set<Integer> colElementNumber2 = findGivenValueInCol(value, colStart, 2);
                    colElementNumber2.remove(nextColElementNumber3);
                    if (colElementNumber2.size() > 0) {
                        int nextColElementNumber4 = colElementNumber2.iterator().next();
                        int rowumenEnd = nextColElementNumber4 % this.getSize();
                        for (int nextColElementNumber5 : rowElementNumber2) {
                            colStart = (nextColElementNumber5 / this.getSize()) * this.getSize();
                            Set<Integer> colElementNumber3 = findGivenValueInCol(value, colStart, 2);
                            colElementNumber3.remove(nextColElementNumber5);
                            if (colElementNumber3.size() > 0) {
                                int nextColElementNumber6 = colElementNumber3.iterator().next();
                                int checkElementEnd = nextColElementNumber6 % this.getSize();
                                if (checkElementEnd == rowumenEnd) {
                                    int startOfRow1 = colElement % this.getSize();
                                    int startOfRow2 = nextColElementNumber2 % this.getSize();
                                    int startOfRow3 = nextColElementNumber6 % this.getSize();

                                    //Remove the values
                                    for (int i = startOfRow1; i < (this.getSize() * this.getSize()); i += this.getSize()) {
                                        if (i != colElement && i != nextColElementNumber2 && i != nextColElementNumber3 && i != nextColElementNumber4 && i != nextColElementNumber5 && i != nextColElementNumber6 && getElementWithNumber(i) == EMPTY_ELEMENT_VALUE) {
                                            Set<Integer> beforeRemove = new HashSet<Integer>(this.validElementsCache.get(i));
                                            removeValueForElementWithNumber(i, value);
                                            if (!beforeRemove.equals(this.validElementsCache.get(i))) {
                                                removedSomething = true;
                                            }
                                        }
                                    }
                                    for (int i = startOfRow2; i < (this.getSize() * this.getSize()); i += 9) {
                                        if (i != colElement && i != nextColElementNumber2 && i != nextColElementNumber3 && i != nextColElementNumber4 && i != nextColElementNumber5 && i != nextColElementNumber6 && getElementWithNumber(i) == EMPTY_ELEMENT_VALUE) {
                                            Set<Integer> beforeRemove = new HashSet<Integer>(this.validElementsCache.get(i));
                                            removeValueForElementWithNumber(i, value);
                                            if (!beforeRemove.equals(this.validElementsCache.get(i))) {
                                                removedSomething = true;
                                            }
                                        }
                                    }
                                    for (int i = startOfRow3; i < (this.getSize() * this.getSize()); i += 9) {
                                        if (i != colElement && i != nextColElementNumber2 && i != nextColElementNumber3 && i != nextColElementNumber4 && i != nextColElementNumber5 && i != nextColElementNumber6 && getElementWithNumber(i) == EMPTY_ELEMENT_VALUE) {
                                            Set<Integer> beforeRemove = new HashSet<Integer>(this.validElementsCache.get(i));
                                            removeValueForElementWithNumber(i, value);
                                            if (!beforeRemove.equals(this.validElementsCache.get(i))) {
                                                removedSomething = true;
                                            }
                                        }
                                    }
                                }
                            }
                            if (removedSomething) {
                                break;
                            }
                        }
                    }
                    if (removedSomething) {
                        break;
                    }
                }
            }
        }
        return (removedSomething);
    }

    /**
     * Given the value it will try to find that value as many times as it can.
     * If it finds it the specified number of times then it returns the
     * locations of that value. If it finds it more than the specified then it
     * return an empty set.
     *
     * @param value is the value that it is trying to fine.
     * @param startPoint is the starting point for the searching.
     * @param numberOfFinds allows you to turn off the find the number once
     * thing. by setting it 0 it will find all locations of the number.
     * @return
     */
    private Set<Integer> findGivenValueInRow(int value, int startPoint, int numberOfFinds)
    {
        Set<Integer> valueLocations = new HashSet<Integer>();

        int foundNumber = 0;

        int element = (this.getSize() * this.getSize());

        if (startPoint < element) {
            //startPoint--;
        } else {
            startPoint--;
        }

        int stopLocation = (startPoint / this.getSize()) * this.getSize() + (this.getSize() - 1);
        boolean stopSearching = false;

        Set<Integer> currentSet = new HashSet<Integer>();

        for (int i = (startPoint); i <= stopLocation && !stopSearching; i++) {

            if (getElementWithNumber(i) == EMPTY_ELEMENT_VALUE) {
                currentSet.addAll(this.validElementsCache.get(i));
                for (int valueTest : currentSet) {
                    if (value == valueTest && foundNumber < numberOfFinds) {
                        valueLocations.add(i);
                        if (numberOfFinds != 0) {
                            foundNumber++;
                        }
                    } else if (value == valueTest) {
                        //reseting valueLocation to -1 because it found more than one location with the value in the row.
                        valueLocations.clear();
                        stopSearching = true;
                        break;
                    }
                }
                currentSet.clear();
            }
        }
        return (valueLocations);
    }

    /**
     * Given the value it will try to find that value as many times as it can.
     * If it finds it the specified number of times then it returns the
     * locations of that value. If it finds it more than the specified then it
     * return an empty set.
     *
     * @param value is the value that it is trying to fine.
     * @param startPoint is the starting point for the searching.
     * @param numberOfFinds allows you to turn off the find the number once
     * thing. by setting it 0 it will find all locations of the number.
     * @return
     */
    private Set<Integer> findGivenValueInCol(int value, int startPoint, int numberOfFinds)
    {
        Set<Integer> valueLocations = new HashSet<Integer>();
        int stopPoint = (int) Math.pow(this.getSize(), 2);
        int nextColCellNumber = this.getSize();
        int foundNumber = 0;
        if (numberOfFinds == 0) {
            foundNumber--;
        }/*
         * else { startPoint = startPoint + nextColCellNumber;
        }
         */

        boolean stopSearching = false;

        Set<Integer> currentSet = new HashSet<Integer>();

        for (int i = startPoint; i < stopPoint && !stopSearching; i += nextColCellNumber) {
            if (getElementWithNumber(i) == EMPTY_ELEMENT_VALUE) {
                currentSet.addAll(this.validElementsCache.get(i));
                for (int valueTest : currentSet) {
                    if (value == valueTest && foundNumber < numberOfFinds) {
                        valueLocations.add(i);
                        if (numberOfFinds != 0) {
                            foundNumber++;
                        }
                    } else if (value == valueTest) {
                        //reseting valueLocation to -1 because it found more than one location with the value in the row.
                        valueLocations.clear();
                        stopSearching = true;
                        break;
                    }
                }
                currentSet.clear();
            }
        }
        return (valueLocations);
    }

    @Override
    public SudokuPuzzle getNewStartState(int tDiff)
    {
        return getNewStartState(tDiff, SudokuPuzzle.getCopy(this));
    }

    /*
     * Forcing Chains
     */

    // TWEAKED for friday presentation and mostly easy difficulty.....
    public SudokuPuzzle getNewStartState(int tDiff, SudokuPuzzle working)
    {
        //   System.out.println("#Empty: " + working.emptyElements.size());

        ArrayList<Integer> elementPool = new ArrayList<Integer>(working.placedElements);

        boolean removedElement = false;
        Random r = new Random();

        int numberOfTimesTried = 0;
        int maxNumberOfTimesToTry = elementPool.size() / 4;
        int currentRating = Difficulty.UNKNOWN;
        while (elementPool.size() > 0 && !removedElement && (numberOfTimesTried < maxNumberOfTimesToTry || tDiff != Difficulty.EASY)) {
            int indexToRemove = r.nextInt(elementPool.size());
            int elementNumberToRemove = elementPool.get(indexToRemove);
            int currentValue = working.getElementWithNumber(elementNumberToRemove);
            working.emptyElementWithNumber(elementNumberToRemove);

            currentRating = working.getRating().getDifficulty();
            //  System.out.println("Current Rating: " + currentRating);
            if (currentRating <= tDiff) {
                removedElement = true;
            } else {
                elementPool.remove(indexToRemove);
                working.setElementWithNumber(elementNumberToRemove, currentValue);
                numberOfTimesTried++;
            }
        }

        if (removedElement) {
            if (currentRating == Difficulty.UNKNOWN) {
                currentRating = working.getRating().getDifficulty();
            }


            if (currentRating < tDiff) {
                // need to remove another...

                SudokuPuzzle copy = SudokuPuzzle.getCopy(working);
                return getNewStartState(tDiff, copy);
            } else if (currentRating == tDiff) {

                if (currentRating == Difficulty.EASY) {
                    // can we remove one more ?
                    SudokuPuzzle oneMore = getNewStartState(tDiff, working);
                    if (oneMore == null) {
                        return working;
                    } else {
                        return oneMore;
                    }
                } else {
                    return working;
                }
            } else {
                // cant do it from here...

                return null;
            }
        } else {
            if (working.getRating().getDifficulty() == tDiff) {
                return working;
            } else {
                return null;
            }


        }

    }

    /*
    public SudokuPuzzle getJasonSedlackNewStartState(int tDiff)
    {

        SudokuPuzzle mutatedPuzzle = SudokuPuzzle.getCopy(this);




        int puzzleSize = this.elements.length;
        Random rnd = new Random();
        int lastRemovedElementsPos = -1;
        int lastRemovedElementsValue = -1;
        int next = 0;

        ArrayList<Integer> setOfPlacedElements = new ArrayList<Integer>(puzzleSize);
        for (int i = 0; i < puzzleSize; i++) {
            if (!mutatedPuzzle.elementWithNumberIsEmpty(i)) {
                setOfPlacedElements.add(i);
            }
        }
        int puzzleRating;
        while (true) {
            puzzleRating = mutatedPuzzle.getRating();
            if (puzzleRating < tDiff) {
                next = rnd.nextInt(setOfPlacedElements.size());
                lastRemovedElementsPos = (Integer) setOfPlacedElements.get(next);
                lastRemovedElementsValue =
                  mutatedPuzzle.getElementWithNumber(lastRemovedElementsPos);
                mutatedPuzzle.emptyElementWithNumber(lastRemovedElementsPos);
                setOfPlacedElements.remove(new Integer(lastRemovedElementsPos));
            } else if (puzzleRating > tDiff) {
                // Add it back
                mutatedPuzzle.setElementWithNumber(lastRemovedElementsPos, lastRemovedElementsValue);
                setOfPlacedElements.add(lastRemovedElementsPos);

                // Try to find another one
                boolean foundCandidateToRemove = false;

                ArrayList<Integer> temporarySetOfPlacedElements = new ArrayList<Integer>(setOfPlacedElements);
                while (!foundCandidateToRemove) {
                    if (!temporarySetOfPlacedElements.isEmpty()) {
                        next = rnd.nextInt(temporarySetOfPlacedElements.size());
                        lastRemovedElementsPos = temporarySetOfPlacedElements.get(next);

                        lastRemovedElementsValue = mutatedPuzzle.getElementWithNumber(lastRemovedElementsPos);

                        mutatedPuzzle.emptyElementWithNumber(lastRemovedElementsPos);
                        puzzleRating = mutatedPuzzle.getRating();
                        if (puzzleRating <= tDiff) {
                            // Puts us where we want to be
                            setOfPlacedElements.remove(new Integer(lastRemovedElementsPos));
                            foundCandidateToRemove = true;
                        } else {
                            // Reset it
                            mutatedPuzzle.setElementWithNumber(lastRemovedElementsPos, lastRemovedElementsValue);
                            temporarySetOfPlacedElements.remove(new Integer(lastRemovedElementsPos));
                        }
                    } else {
                        return null;
                    }
                }
            } //2 open
            else if (puzzleRating == tDiff) {
                // Remove as many as possible without changing difficulty                
                ArrayList<Integer> temporarySetOfPlacedElements = new ArrayList<Integer>(setOfPlacedElements);
                while (true) {
                    if (!temporarySetOfPlacedElements.isEmpty()) {
                        next = rnd.nextInt(temporarySetOfPlacedElements.size());
                        lastRemovedElementsPos = temporarySetOfPlacedElements.get(next);
                        lastRemovedElementsValue = mutatedPuzzle.getElementWithNumber(lastRemovedElementsPos);
                        mutatedPuzzle.emptyElementWithNumber(lastRemovedElementsPos);
                        puzzleRating = mutatedPuzzle.getRating();
                        if (puzzleRating == tDiff) {
                            setOfPlacedElements.remove(new Integer(lastRemovedElementsPos));
                            temporarySetOfPlacedElements = new ArrayList<Integer>(setOfPlacedElements);
                        } else {
                            mutatedPuzzle.setElementWithNumber(lastRemovedElementsPos, lastRemovedElementsValue);
                            temporarySetOfPlacedElements.remove(new Integer(lastRemovedElementsPos));
                        }
                    } else {
                        return mutatedPuzzle;
                    }
                }
            }
        }
    } */
}
