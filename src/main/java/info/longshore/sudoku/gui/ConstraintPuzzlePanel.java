package info.longshore.sudoku.gui;

import info.longshore.sudoku.domain.ConstraintPuzzle;
import info.longshore.sudoku.domain.ConstraintPuzzleGraphicsRenderer;
import info.longshore.sudoku.domain.PublishingProfile;

import java.awt.*;

public class ConstraintPuzzlePanel extends javax.swing.JPanel {

  /**
   * Creates new form ConstraintPuzzlePanel
   */
  public ConstraintPuzzlePanel(ConstraintPuzzle cpuz) {

    puz = cpuz;
    initComponents();

    setSize(363, 363);
  }

  public ConstraintPuzzle getPuzzle() {
    return puz;
  }

  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    g2.setColor(Color.WHITE);
    g2.fillRect(0, 0, 363, 363);


    if (graphicsRenderer == null) {
      graphicsRenderer = new ConstraintPuzzleGraphicsRenderer();
    }

    PublishingProfile pub = new PublishingProfile("OnScreenRenderer", 2.0f, 1.0f, 3.0f, 20, Color.BLACK, new Color(79, 79, 79), Color.BLACK);
    graphicsRenderer.publishPuzzleToGraphics2DUsingProfile(g2, puz, pub);
  }

  private ConstraintPuzzleGraphicsRenderer graphicsRenderer;
  private ConstraintPuzzle puz;

  /**
   * This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    setName("Form"); // NOI18N

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGap(0, 400, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGap(0, 300, Short.MAX_VALUE)
    );
  }// </editor-fold>//GEN-END:initComponents


  // Variables declaration - do not modify//GEN-BEGIN:variables
  // End of variables declaration//GEN-END:variables

}
