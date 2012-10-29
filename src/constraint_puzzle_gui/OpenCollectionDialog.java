/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * OpenCollectionDialog.java
 *
 * Created on May 1, 2009, 4:07:44 PM
 */

package constraint_puzzle_gui;

import org.jdesktop.application.Action;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import constraint_puzzle.ConstraintPuzzleCollection;
/**
 *
 * @author rm108s04
 */
public class OpenCollectionDialog extends javax.swing.JDialog {

    /** Creates new form OpenCollectionDialog */
    public OpenCollectionDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        puzzleTypeValueLabel = new javax.swing.JLabel();
        numberOfPuzzlesValueLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(constraint_puzzle_gui.SudokuApplication.class).getContext().getResourceMap(OpenCollectionDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setMinimumSize(new java.awt.Dimension(400, 250));
        setName("Form"); // NOI18N
        setResizable(false);

        jPanel1.setName("jPanel1"); // NOI18N

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        puzzleTypeValueLabel.setText("-"); // NOI18N
        puzzleTypeValueLabel.setName("puzzleTypeValueLabel"); // NOI18N

        numberOfPuzzlesValueLabel.setText(resourceMap.getString("numberOfPuzzlesValueLabel.text")); // NOI18N
        numberOfPuzzlesValueLabel.setName("numberOfPuzzlesValueLabel"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(numberOfPuzzlesValueLabel)
                    .addComponent(puzzleTypeValueLabel))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(puzzleTypeValueLabel)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numberOfPuzzlesValueLabel)
                    .addComponent(jLabel2))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jList1.setModel(
            new javax.swing.AbstractListModel() {
                // This was a mess because of inline class

                public boolean initialized = false;

                private ArrayList<ConstraintPuzzleCollectionDescription> closedDescriptions;

                private void initializeDescriptions() {
                    ConstraintPuzzleCollectionDescription[] allDescs = SudokuApplication.getApplication().getApplicationModel().getCollectionDescriptions();
                    closedDescriptions = new ArrayList<ConstraintPuzzleCollectionDescription>(allDescs.length);

                    SudokuView sv = SudokuApplication.getApplication().getApplicationView();

                    for(int i = 0; i < allDescs.length; i++) {
                        ConstraintPuzzleCollectionDescription desc = allDescs[i];
                        if(!sv.collectionWithNameIsOpen(desc.getName())) {
                            closedDescriptions.add(desc);
                        }
                    }

                    initialized = true;
                }

                public int getSize() {
                    if(!initialized) {
                        initializeDescriptions();
                    }
                    return closedDescriptions.size();
                }

                public Object getElementAt(int i) {
                    if(!initialized) {
                        initializeDescriptions();
                    }
                    return closedDescriptions.get(i);
                }
            }
        );
        jList1.setName("jList1"); // NOI18N
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jList1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jList1MouseExited(evt);
            }
        });
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                puzzleCollectionDescriptionSelected(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(constraint_puzzle_gui.SudokuApplication.class).getContext().getActionMap(OpenCollectionDialog.class, this);
        jButton2.setAction(actionMap.get("close")); // NOI18N
        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N

        jButton1.setAction(actionMap.get("openSelectedCollection")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 108, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(jButton1)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void puzzleCollectionDescriptionSelected(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_puzzleCollectionDescriptionSelected
        // TODO add your handling code here:
        ConstraintPuzzleCollectionDescription desc = (ConstraintPuzzleCollectionDescription)jList1.getSelectedValue();

        numberOfPuzzlesValueLabel.setText(Integer.toString(desc.getNumberOfPuzzlesInCollection()));
        puzzleTypeValueLabel.setText(desc.getTypeName());

    }//GEN-LAST:event_puzzleCollectionDescriptionSelected

    private void jList1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jList1MouseExited

    private void jList1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jList1MouseEntered

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        // TODO add your handling code here:

        if(evt.getClickCount() == 2) {
            // Double click
            openSelectedCollection();
        }

    }//GEN-LAST:event_jList1MouseClicked

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                OpenCollectionDialog dialog = new OpenCollectionDialog(new javax.swing.JFrame(), true);
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
    public void openSelectedCollection() {
        ConstraintPuzzleCollectionDescription desc = (ConstraintPuzzleCollectionDescription)jList1.getSelectedValue();

        if(desc == null) {
            JOptionPane.showMessageDialog(this, "Select a collection.", "Select Collection", JOptionPane.ERROR_MESSAGE);
        }
        else {
            ConstraintPuzzleCollection cpc = SudokuApplication.getApplication().getApplicationModel().getCollection(desc.getName());
            SudokuApplication.getApplication().getApplicationView().openCollection(cpc);
            setVisible(false);
        }
    }

    @Action
    public void close() {
        setVisible(false);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JList jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel numberOfPuzzlesValueLabel;
    private javax.swing.JLabel puzzleTypeValueLabel;
    // End of variables declaration//GEN-END:variables

}