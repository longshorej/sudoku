/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * EditProfilesDialog.java
 *
 * Created on May 20, 2009, 12:04:52 PM
 */

package constraint_puzzle_gui;

import constraint_puzzle.*;

import javax.swing.*;

import org.jdesktop.application.Action;

/**
 *
 * @author rm108s04
 */
public class EditProfilesDialog extends javax.swing.JDialog {

    /** Creates new form EditProfilesDialog */
    public EditProfilesDialog(java.awt.Frame parent, boolean modal) {
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

        jButton1 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(constraint_puzzle_gui.SudokuApplication.class).getContext().getActionMap(EditProfilesDialog.class, this);
        jButton1.setAction(actionMap.get("createNewProfile")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(constraint_puzzle_gui.SudokuApplication.class).getContext().getResourceMap(EditProfilesDialog.class);
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N

        jTabbedPane1.setName("jTabbedPane1"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addComponent(jButton1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                EditProfilesDialog dialog = new EditProfilesDialog(new javax.swing.JFrame(), true);
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
    public void createNewProfile() {
        String profileName = null;
        boolean cancelled = false;

        while(profileName == null && !cancelled) {
            String enteredValue = JOptionPane.showInputDialog(this, "Name:");
            if(enteredValue == null) {
                cancelled = true;
            }
            else if(enteredValue.equals("")) {
                JOptionPane.showMessageDialog(this, "Enter A Name", "Enter Name", JOptionPane.ERROR_MESSAGE);
            }
            else {
                profileName = enteredValue;
            }
        }

        if(cancelled) return;

        
/*
        PublishingProfile profile = new PublishingProfile(profileName);
        PublishingProfilePanel panel = new PublishingProfilePanel(profile);
        jTabbedPane1.addTab(profile.getName(), panel);*/
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables

}
