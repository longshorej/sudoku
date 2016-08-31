/*
 * SudokuApplication.java
 */

package info.longshore.sudoku.gui;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

import javax.swing.*;
import java.io.File;

/**
 * The main class of the application.
 */
public class SudokuApplication extends SingleFrameApplication {

  @Override
  protected void shutdown() {


    while (applicationView.getSelectedCollectionPanel() != null) {
      applicationView.closeSelectedCollection();
    }
    applicationModel.save();
  }

  /**
   * At startup create and show the main frame of the application.
   */
  @Override
  protected void startup() {
    // Must be created before the view, incase creation of
    // the view requires data from the model.
    applicationModel = new SudokuModel(new File(SudokuApplication.XML_FILENAME));
    applicationView = new SudokuView(this);
    show(applicationView);
    JOptionPane.showMessageDialog(applicationView.getComponent(), "GUI is broken - build is missing NetBeans libs", "Startup", JOptionPane.INFORMATION_MESSAGE);

    if (applicationModel.justCreated()) {
      JOptionPane.showMessageDialog(applicationView.getComponent(), "It appears that this is your first time running the application. To begin, select New Collection from the File menu.", "Startup", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  public SudokuView getApplicationView() {
    return applicationView;
  }

  public SudokuModel getApplicationModel() {
    return applicationModel;
  }

  /**
   * This method is to initialize the specified window by injecting resources.
   * Windows shown in our application come fully initialized from the GUI
   * builder, so this additional configuration is not needed.
   */
  @Override
  protected void configureWindow(java.awt.Window root) {
  }

  /**
   * A convenient static getter for the application instance.
   *
   * @return the instance of SudokuApplication
   */
  public static SudokuApplication getApplication() {
    return Application.getInstance(SudokuApplication.class);
  }

  /**
   * Main method launching the application.
   */
  public static void main(String[] args) {
    launch(SudokuApplication.class, args);
  }

  private SudokuView applicationView;
  private SudokuModel applicationModel;

  private static String XML_FILENAME = "sudoku.xml";
}
