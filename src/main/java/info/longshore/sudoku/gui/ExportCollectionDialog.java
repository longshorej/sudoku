package info.longshore.sudoku.gui;

import info.longshore.sudoku.domain.*;
import org.jdesktop.application.Action;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class ExportCollectionDialog extends javax.swing.JDialog {

  /**
   * Creates new form ExportCollectionDialog
   */
  public ExportCollectionDialog(java.awt.Frame parent, boolean modal) {
    super(parent, modal);
    initComponents();
  }

  /**
   * This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jLabel1 = new javax.swing.JLabel();
    jComboBox1 = new javax.swing.JComboBox();
    jLabel2 = new javax.swing.JLabel();
    jComboBox2 = new javax.swing.JComboBox();
    jButton1 = new javax.swing.JButton();
    jButton2 = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(info.longshore.sudoku.gui.SudokuApplication.class).getContext().getResourceMap(ExportCollectionDialog.class);
    setTitle(resourceMap.getString("Form.title")); // NOI18N
    setName("Form"); // NOI18N
    setResizable(false);

    jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
    jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
    jLabel1.setName("jLabel1"); // NOI18N

    jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Default"}));
    jComboBox1.setName("jComboBox1"); // NOI18N

    jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
    jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
    jLabel2.setName("jLabel2"); // NOI18N

    jComboBox2.setModel(new javax.swing.ComboBoxModel() {
      private Graphics2DFilePublisher[] publishers = Graphics2DFilePublisher.getFilePublishers();
      private Object selected;

      public int getSize() {
        return publishers.length;
      }

      public Object getElementAt(int i) {
        return publishers[i];
      }

      public void setSelectedItem(Object anItem) {
        selected = anItem;
      }

      public Object getSelectedItem() {
        return selected;
      }

      public void addListDataListener(ListDataListener l) {
        // CareFace
      }

      public void removeListDataListener(ListDataListener l) {
        // CareFace
      }
    });
    jComboBox2.setSelectedIndex(0);
    jComboBox2.setName("jComboBox2"); // NOI18N

    javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(info.longshore.sudoku.gui.SudokuApplication.class).getContext().getActionMap(ExportCollectionDialog.class, this);
    jButton1.setAction(actionMap.get("exportSelectedCollection")); // NOI18N
    jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
    jButton1.setName("jButton1"); // NOI18N

    jButton2.setAction(actionMap.get("close")); // NOI18N
    jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
    jButton2.setName("jButton2"); // NOI18N

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
          .addContainerGap()
          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jLabel2)
            .addComponent(jLabel1))
          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jComboBox2, 0, 169, Short.MAX_VALUE)
            .addComponent(jComboBox1, 0, 169, Short.MAX_VALUE))
          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton2)
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE))
          .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
          .addContainerGap()
          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
            .addComponent(jLabel1)
            .addComponent(jButton1)
            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
            .addComponent(jButton2)
            .addComponent(jLabel2)
            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
          .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  /**
   * @param args the command line arguments
   */
  public static void main(String args[]) {
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        ExportCollectionDialog dialog = new ExportCollectionDialog(new javax.swing.JFrame(), true);
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
          public void windowClosing(java.awt.event.WindowEvent e) {
            System.exit(0);
          }
        });
        dialog.setVisible(true);
      }
    });
  }

  @Action
  public void exportSelectedCollection() {
    ExportCollectionFileChooser exportLocationChooser = new ExportCollectionFileChooser();
    File newFile = exportLocationChooser.getFileChoice(this);

    if (newFile != null) {
      try {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(newFile));
        ConstraintPuzzleCollection col = SudokuApplication.getApplication().getApplicationView().getSelectedCollectionPanel().getCollection();


        PublishingProfile pub = new PublishingProfile("ExportProfile", 2.0f, 1.0f, 3.0f, 20, Color.black, new Color(79, 79, 79), Color.BLACK);
        ConstraintPuzzleGraphicsRenderer graphicsRenderer = new ConstraintPuzzleGraphicsRenderer();
        Graphics2DFilePublisher publishingFormatter = (Graphics2DFilePublisher) jComboBox2.getSelectedItem();

        int i = 0;
        for (ConstraintPuzzle c : col.getPuzzles()) {
          String fileExtension = publishingFormatter.getExtensionName();
          if (!fileExtension.equals("")) {
            fileExtension = "." + fileExtension;
          }

          String puzzleFileName = "puzzle";
          graphicsRenderer.publishPuzzleToGraphics2DUsingProfile(publishingFormatter.getGraphics2D(), c, pub);
          out.putNextEntry(new ZipEntry(puzzleFileName + i + fileExtension));
          out.write(publishingFormatter.getByteRepresentation());
          out.closeEntry();

          ConstraintPuzzle solution = c.getSolution();

          graphicsRenderer.publishPuzzleToGraphics2DUsingProfile(publishingFormatter.getGraphics2D(), solution, pub);
          String solutionFileName = "solution";
          out.putNextEntry(new ZipEntry(solutionFileName + i + fileExtension));
          out.write(publishingFormatter.getByteRepresentation());
          out.closeEntry();
          i++;
        }

        out.close();
        setVisible(false);
      } catch (Exception e) {
        System.out.println(e.toString());
        JOptionPane.showMessageDialog(this, "An Error Occurred Creating the ZIP File.");
      }
    } else {

    }
  }

  @Action
  public void close() {
    setVisible(false);
  }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton jButton1;
  private javax.swing.JButton jButton2;
  private javax.swing.JComboBox jComboBox1;
  private javax.swing.JComboBox jComboBox2;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  // End of variables declaration//GEN-END:variables

}
