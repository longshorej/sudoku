package constraint_puzzle;

import java.awt.Color;

/**
 * This is an interface class and is meant to be implemented by publishing
 * format
 */
public class PublishingProfile
{
    /**
     * Data structure for the visual aspect of rendering a puzzle.
     *
     * @param name the name of the profile
     * @param fontsize the Font size.
     * @param bmin the Boarder min value.
     * @param bmax the Boarder max value.
     * @param bgcol the background color.
     * @param txtcol the text color.
     */
    public PublishingProfile(String name, double fontsize, float bmin, float bmax, int cSize, Color brcol, Color smallbrcol, Color txtcol)
    {
        publisherName = name;
        fontSize = fontsize;
        boarderMin = bmin;
        boarderMax = bmax;
        boarderColor = brcol;
        textColor = txtcol;
        cellSize = cSize;
        smallBoarderColor = smallbrcol;
    }

    public PublishingProfile(String name)
    {
        this(name, 0, 0, 0, 0, new Color(0), new Color(0), new Color(0));
    }

    public void setFontSize(double v)
    {
        fontSize = v;
    }

    public double getFontSize()
    {
        return fontSize;
    }

    public void setBoarderMin(float v)
    {
        boarderMin = v;
    }

    public float getBoarderMin()
    {
        return boarderMin;
    }

    public float getBoarderMax()
    {
        return boarderMax;
    }

    public void setBoarderMax(float v)
    {
        boarderMax = v;
    }

    public int getcellSize()
    {
        return cellSize;
    }

    public void setcellSize(int v)
    {
        cellSize = v;
    }

    public Color getBoarderColor()
    {
        return boarderColor;
    }

    public void setBoarderColor(Color c)
    {
        boarderColor = c;
    }

    public Color getSmallBoarderColor()
    {
        return this.smallBoarderColor;
    }

    public void setSmallBoarderColor(Color c)
    {
        smallBoarderColor = c;
    }

    public Color getTextColor()
    {
        return textColor;
    }

    public void setTextColor(Color c)
    {
        textColor = c;
    }

    public String getName()
    {
        return publisherName;
    }

    private double fontSize;
    private float boarderMin;
    private float boarderMax;
    private int cellSize;
    private Color boarderColor;
    private Color smallBoarderColor;
    private Color textColor;
    public String publisherName; //This is the publisher name
}
