/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package info.longshore.sudoku.gui;

import javax.swing.*;
import java.awt.Component;
import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Function composition class of JFileChooser into
 * functionality i need out of it.
 *
 * @author jason
 */
public class ExportCollectionFileChooser {
    public ExportCollectionFileChooser() {
        chooser = new JFileChooser();
        zipFilter = new ZIPFileFilter();
        chooser.addChoosableFileFilter(zipFilter);
    }

    /**
     *
     * @return the file, or null if a file was not chosen.
     */
    public File getFileChoice(Component parent) {
        if(chooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {

                File newFile = chooser.getSelectedFile();

                if(chooser.getFileFilter() == zipFilter) {
                    // ZIP was selected...add .zip to the end of the filename
                    // if it doesnt exist already.

                    if(!newFile.getName().toUpperCase().endsWith(".ZIP")) {
                        newFile = new File(newFile.getAbsoluteFile() + ".zip");
                    }
                }

                if(newFile.exists()) {
                    int confirmChoice = JOptionPane.showConfirmDialog(parent, "File already exists. Do you want to replace it?");

                    if(confirmChoice == JOptionPane.YES_OPTION) {
                        newFile.delete();
                    }
                    else if(confirmChoice == JOptionPane.NO_OPTION) {
                        return getFileChoice(parent); // Try again..
                    }
                    else {
                        // Cancelled...
                        return null;
                    }
                }
                
                if(!newFile.exists()) {
                    boolean fileCreated = false;
                    try {
                        fileCreated = newFile.createNewFile();
                    }
                    catch(Exception e) {
                    }
                    finally {
                        if(!fileCreated) {
                            JOptionPane.showMessageDialog(parent, "An Error Occurred");
                            return null;
                        }
                    }
                }

                return newFile;
        } else {
            return null;
        }
    }

    private class ZIPFileFilter extends FileFilter {
        @Override public boolean accept(File f) {
            if(f.isDirectory()) {
                return true;
            }
            else if(f.getName().toUpperCase().endsWith(".ZIP")) {
                return true;
            }
            else {
                return false;
            }
        }

        @Override public String getDescription() {
            return "ZIP File";
        }
    }

    private JFileChooser chooser;
    private ZIPFileFilter zipFilter;
}
