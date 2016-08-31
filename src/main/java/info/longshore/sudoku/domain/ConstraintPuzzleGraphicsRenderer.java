package info.longshore.sudoku.domain;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A class which works on a Graphics2D object and draws the graphical
 * representation of the constraint puzzle.
 */
public class ConstraintPuzzleGraphicsRenderer
{
    public ConstraintPuzzleGraphicsRenderer()
    {
    }

    public void publishPuzzleToGraphics2DUsingProfile(Graphics2D graphics, ConstraintPuzzle cp, PublishingProfile pro)
    {
        output = graphics;
        puzzle = cp;
        profile = pro;
        cellSize = profile.getcellSize();
        puzzleSize = puzzle.getSize();
        double scale = profile.getFontSize();
        output.scale(scale, scale);
        createBox();
        getBoarders();
    }

    private void createBox()
    {
        output.setStroke(new BasicStroke(profile.getBoarderMin()));
        for (int i = 0; i < puzzleSize; i++) {
            for (int j = 0; j < puzzleSize; j++) {
                output.setColor(profile.getBoarderColor());
                drawTopLine(j, i);
                drawBottomLine(j, i);
                drawRightLine(j, i);
                drawLeftLine(j, i);

                output.setColor(profile.getTextColor());
                putNumberInsideBox(puzzle.getElementAtPosition(j, i), j, i);
            }
        }

        output.setColor(profile.getSmallBoarderColor());
    }

    private void getBoarders()
    {
        output.setStroke(new BasicStroke(profile.getBoarderMax()));
        getSide();
    }

    private void getSide()
    {
        Set<Integer> tempSet = new HashSet<Integer>();
        Set<Integer> commonElements = new HashSet<Integer>();
        Set<Integer> commonRowElements = new HashSet<Integer>();
        Set<Integer> commonColElements = new HashSet<Integer>();
        Set<Integer> currentSet = puzzle.getRelatedElementNumbersForElementWithNumber(0);
        Iterator<Integer> iter;

        int left;
        int right;
        int top;
        int bottom;

        int currentElement;
        int currentRowMin;
        int currentRowMax;

        int currentCol = 0;
        int currentRow = 0;

        for (int i = 0; i < puzzleSize * puzzleSize; i++) {

            if (i % puzzleSize == 0) {
                commonRowElements.clear();
                currentCol = (i / puzzleSize);
                for (int j = i; j < (puzzleSize * (currentCol + 1)); j++) {
                    commonRowElements.add(j);
                }
            }
            commonColElements.clear();
            currentRow = i % puzzleSize;
            for (int j = currentRow; j < puzzleSize * puzzleSize; j += puzzleSize) {
                commonColElements.add(j);
            }
            commonElements.clear();
            commonElements.addAll(commonRowElements);
            commonElements.addAll(commonColElements);

            currentSet = puzzle.getRelatedElementNumbersForElementWithNumber(i);
            currentSet.removeAll(commonElements);

            if (currentSet.size() <= 0) {
                currentSet = puzzle.getRelatedElementNumbersForElementWithNumber(i);
                currentSet.retainAll(commonElements);
            }

            iter = currentSet.iterator();
            while (iter.hasNext()) {
                currentElement = iter.next();

                currentCol = (currentElement / puzzleSize);
                //This is for loop the numbers around the box.
                //left and right
                left = currentElement - 1;
                if (currentCol != (left / puzzleSize) || left < 0) {
                    left = (puzzleSize * (currentCol + 1)) - 1;
                }

                right = currentElement + 1;
                if (currentCol != (right / puzzleSize)) {
                    right = puzzleSize * currentCol;
                }

                //adding lines to the boxes
                if (!currentSet.contains(right) && !commonElements.contains(right)) {
                    drawRightLineForElmement(currentElement);
                }

                if (!currentSet.contains(left) && !commonElements.contains(left)) {
                    drawLeftLineForElmement(currentElement);
                }

                //top and bottom
                currentRow = currentElement % puzzleSize;
                top = currentElement - puzzleSize;
                currentRowMin = currentRow;
                currentRowMax = (puzzleSize * puzzleSize) - (puzzleSize - currentRowMin);
                if (top < currentRowMin) {
                    top = (((puzzleSize * puzzleSize) - puzzleSize) + currentRow);
                }

                bottom = currentElement + puzzleSize;
                if (bottom > currentRowMax) {
                    bottom = currentRowMin;
                }

                if (!currentSet.contains(bottom) && !commonElements.contains(bottom)) {
                    drawBottomLineForElmement(currentElement);
                }

                if (!currentSet.contains(top) && !commonElements.contains(top)) {
                    drawTopLineForElmement(currentElement);
                }
            }
        }
    }

    private void drawRightLineForElmement(int elm)
    {
        int x = elm % puzzleSize;
        int y = elm / puzzleSize;

        int boxX = (cellSize + x * cellSize);
        int boxY = cellSize + y * cellSize;
        int startY = boxY - cellSize;

        output.drawLine(boxX, startY, boxX, boxY);
    }

    private void drawRightLine(int row, int col)
    {
        int boxX = cellSize + col * cellSize;
        int boxY = cellSize + row * cellSize;
        int startY = boxY - cellSize;

        output.drawLine(boxX, startY, boxX, boxY);
    }

    private void drawLeftLineForElmement(int elm)
    {
        int x = elm % puzzleSize;
        int y = elm / puzzleSize;

        if (x == 0) {
            x = 1;
        } else {
            x += 1;
        }

        int boxX = (x * cellSize - cellSize);
        int boxY = cellSize + y * cellSize;
        int startY = boxY - cellSize;
        if (boxX >= 0) {
            output.drawLine(boxX, startY, boxX, boxY);
        }
    }

    private void drawLeftLine(int row, int col)
    {
        int boxX = col * cellSize - cellSize;
        int boxY = cellSize + row * cellSize;
        int startY = boxY - cellSize;
        if (boxX >= 0) {
            output.drawLine(boxX, startY, boxX, boxY);
        }
    }

    private void drawTopLine(int row, int col)
    {
        int boxX = cellSize + col * cellSize;
        int startX = boxX - cellSize;
        int boxY = row * cellSize - cellSize;
        if (boxY >= 0) {
            output.drawLine(startX, boxY, boxX, boxY);
        }

    }

    private void drawTopLineForElmement(int elm)
    {
        int x = elm % puzzleSize;
        int y = elm / puzzleSize;

        if (y == 0) {
            y = 1;
        } else {
            y += 1;
        }

        int boxX = cellSize + x * cellSize;
        int startX = boxX - cellSize;
        int boxY = y * cellSize - cellSize;
        if (boxY >= 0) {
            output.drawLine(startX, boxY, boxX, boxY);
        }
    }

    private void drawBottomLine(int row, int col)
    {
        int boxX = cellSize + col * cellSize;
        int startX = boxX - cellSize;
        int boxY = cellSize + row * cellSize;

        output.drawLine(startX, boxY, boxX, boxY);

    }

    private void drawBottomLineForElmement(int elm)
    {
        int x = elm % puzzleSize;
        int y = elm / puzzleSize;

        int boxX = cellSize + x * cellSize;
        int startX = boxX - cellSize;
        int boxY = cellSize + y * cellSize;

        output.drawLine(startX, boxY, boxX, boxY);

    }

    private void putNumberInsideBox(Integer cellValue, int row, int col)
    {
        int centerX = 5;
        int centerY = cellSize - 10;

        int boxX = cellSize + col * cellSize;
        int startX = boxX - cellSize;
        int boxY = cellSize + row * cellSize;

        String strNum = cellValue.toString();

        // Maniupulate 0 => empty string
        if (strNum.equals("0")) {
            strNum = " ";
        }

        // Fix spacing
        if (strNum.length() > 1) {
            for (int i = 0; i < strNum.length(); i++) {
                centerX += 5;
            }
        }

        int midX = (boxX + startX - centerX) / 2;
        int midY = (boxY + boxY - centerY) / 2;

        output.drawString(strNum, midX, midY);
    }

    private Graphics2D output;
    private ConstraintPuzzle puzzle;
    private PublishingProfile profile;
    private int puzzleSize;
    private int cellSize;
}
