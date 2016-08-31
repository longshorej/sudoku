package info.longshore.sudoku.domain;

import java.awt.*;

/**
 * Classes who extend this class must be able to produce a byte representation
 * of their internal state, allowing easy publishing to a file.
 * <p>
 * A mutation to the graphics2D object is done through its reference returned by
 * getGraphics2D. At any given time, getByteRepresentation should restore some
 * byte representation that could be written to a file.
 * <p>
 * Basically, implement getExtensionName to return a string that should be used
 * for the files extension, instantiate a class which extends Graphics2D in the
 * constructor and setup a getter (getGraphics2D) to get the class, and
 * implement getByteRepresentation to return a byte representation of the object
 * in the format desired. After the call to getByteRep, it is assumed that the
 * object will be reset into a state where it can return a new g2d object.
 */
public abstract class Graphics2DFilePublisher {
  public abstract byte[] getByteRepresentation();

  public abstract String getExtensionName();

  public abstract Graphics2D getGraphics2D();

  public abstract String getFormatName();

  public static Graphics2DFilePublisher[] getFilePublishers() {
    Graphics2DFilePublisher[] publishers = {
      new EPSGraphics2DFilePublisher(),
      new PDFGraphics2DFilePublisher()
    };

    return publishers;
  }

  @Override
  public String toString() {
    return getFormatName();
  }
}
