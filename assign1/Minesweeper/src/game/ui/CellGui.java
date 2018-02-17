package game.ui;

import javax.swing.*;

public class CellGui extends JButton{
  private int row, column;

  CellGui (int r, int c) {
    row = r;
    column = c;
  }
  public int getRow() {
    return row;
  }
  public int getColumn() {
    return column;
  }
}
