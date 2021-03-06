package info.longshore.sudoku.gui;

public class UserManualDialog extends javax.swing.JDialog {

  /**
   * Creates new form UserManualDialog
   */
  public UserManualDialog(java.awt.Frame parent, boolean modal) {
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

    jScrollPane1 = new javax.swing.JScrollPane();
    jTextPane1 = new javax.swing.JTextPane();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(info.longshore.sudoku.gui.SudokuApplication.class).getContext().getResourceMap(UserManualDialog.class);
    setTitle(resourceMap.getString("Form.title")); // NOI18N
    setName("Form"); // NOI18N

    jScrollPane1.setName("jScrollPane1"); // NOI18N

    jTextPane1.setContentType(resourceMap.getString("jTextPane1.contentType")); // NOI18N
    jTextPane1.setText(resourceMap.getString("jTextPane1.text")); // NOI18N
    jTextPane1.setName("jTextPane1"); // NOI18N
    jScrollPane1.setViewportView(jTextPane1);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  /**
   * @param args the command line arguments
   */
  public static void main(String args[]) {
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        UserManualDialog dialog = new UserManualDialog(new javax.swing.JFrame(), true);
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
          public void windowClosing(java.awt.event.WindowEvent e) {
            System.exit(0);
          }
        });
        dialog.setVisible(true);
      }
    });
  }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JTextPane jTextPane1;
  // End of variables declaration//GEN-END:variables

}
