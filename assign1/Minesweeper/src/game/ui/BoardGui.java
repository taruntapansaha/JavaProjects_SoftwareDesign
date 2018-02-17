package game.ui;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

import game.MineSweeper;

public class BoardGui extends JFrame implements MouseListener, ActionListener{
  private final int SIZE = 10;
  private JPanel board;
  private JMenuItem itemNewGame;
  private CellGui grid[][];
  private MineSweeper game;


  public BoardGui(MineSweeper parentGame) {

    Container contPane = getContentPane();
    getContentPane().setLayout(new BorderLayout());
    this.setTitle( "Minesweeper" );

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 450, 300);
    board = new JPanel();
    board.setBackground(Color.LIGHT_GRAY);
    board.setLayout(new GridLayout(10, 10, 1, 1));
    game = parentGame;
    grid = new CellGui[SIZE][SIZE];
    contPane.add(board, BorderLayout.CENTER);

    for(int i = 0 ; i < SIZE ; i++ )
      for(int j = 0 ; j < SIZE ; j++)
      {
        grid[i][j] = new CellGui(i, j);
        board.add(grid[i][j]);
        grid[i][j].addMouseListener(this);

      }
  };
  private JMenuBar myMenu()
  {
    JMenuBar menuBar = new JMenuBar();

    JMenu menuGame = new JMenu( "Menu" );
    itemNewGame = new JMenuItem( "New Game" );
    itemNewGame.addActionListener(this);
    menuGame.add( itemNewGame );

    menuBar.add( menuGame );


    return menuBar;
  }
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        MineSweeper game = new MineSweeper();
        game.setMines();
        BoardGui board = new BoardGui(game);
        board.setVisible(true);
        board.setJMenuBar(board.myMenu());
      }
    });
  }
  private void refreshBoard()
  {
    for(int i = 0 ; i < SIZE ; i++ )
      for(int j = 0 ; j < SIZE ; j++)
      {
        if(game.cellStatus[i][j] == MineSweeper.CellStatus.EXPOSED && !game.isMined(i, j))
        {
          grid[i][j].setBackground(Color.lightGray);
          if(game.isAdjacent(i, j))
            grid[i][j].setText(Integer.toString(game.countAdjacent(i, j)));
        }
        else if (game.cellStatus[i][j] == MineSweeper.CellStatus.EXPOSED && game.isMined(i, j))
        {
          grid[i][j].setBackground(Color.red);
        }
      }
  }
  private void resetBoard()
  {
    for(int i = 0 ; i < SIZE ; i++ )
      for(int j = 0 ; j < SIZE ; j++)
      {
        grid[i][j].setBackground(new JButton().getBackground());
        grid[i][j].setText("");
      }
  }
    public void actionPerformed(ActionEvent e)
    {
      game = new MineSweeper();
      game.setMines();
      resetBoard();
    }
    public void mousePressed(MouseEvent e)
    {
    }
    public void mouseReleased(MouseEvent e)
    {
    }
    public void mouseEntered(MouseEvent e)
    {}
    public void mouseExited(MouseEvent e)
    {}
    @Override
    public void mouseClicked(MouseEvent e)
    {
        int button = e.getButton();
        CellGui cell = (CellGui)e.getSource();

        if( button == 1 && game.getGameStatus() == MineSweeper.GameStatus.INPROGRESS)
        {
          game.exposeCell(cell.getRow(), cell.getColumn());
          refreshBoard();

        }
        else if( button == 3 && game.getGameStatus() == MineSweeper.GameStatus.INPROGRESS)
        {
          game.toggleSeal(cell.getRow(), cell.getColumn());
          if(game.getCellStatus(cell.getRow(), cell.getColumn()) == MineSweeper.CellStatus.SEALED)
            grid[cell.getRow()][cell.getColumn()].setText("F");
          else
            grid[cell.getRow()][cell.getColumn()].setText("");
        }

        if(game.getGameStatus() == MineSweeper.GameStatus.LOST)
          JOptionPane.showMessageDialog(null, "You Lose! \nClick 'Menu' to start a new Game");
        if(game.getGameStatus() == MineSweeper.GameStatus.WON)
          JOptionPane.showMessageDialog(null, "You Win! \nClick 'Menu' to start a new Game");
    }
}
