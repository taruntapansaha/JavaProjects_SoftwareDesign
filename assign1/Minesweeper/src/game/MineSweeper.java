package game;

import java.util.Random;

public class MineSweeper {
  private final int SIZE = 10;
  public enum CellStatus {UNEXPOSED, EXPOSED, SEALED}
  public CellStatus[][] cellStatus = new CellStatus[SIZE][SIZE];
  public enum GameStatus {INPROGRESS, WON, LOST}
  public boolean[][] minedCell = new boolean[SIZE][SIZE];

  public MineSweeper() {
    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
          cellStatus[i][j] = CellStatus.UNEXPOSED;
      }
    }
  }

  public void exposeCell(int row, int column) {
    verifyBounds(row, column);
    if (cellStatus[row][column] == (CellStatus.UNEXPOSED)) {
      cellStatus[row][column] = CellStatus.EXPOSED;
      if (!(!isEmpty(row, column) || isMined(row, column))){
        exposeNeighbors(row, column);
      }
    }
  }

   public void exposeNeighbors(int row, int column) {
    for(int i = row - 1; i <= row + 1; i++) {
      for(int j = column - 1; j <= column + 1; j++) {
        if(i >= 0 && i < SIZE && j >= 0 && j < SIZE)
          exposeCell(i, j);
        }
      }
  }

  public void verifyBounds(int row, int column) throws RuntimeException{
    if (isRowOutOfBound(row, column) || isColumnOutOfBound(row, column))
      throw new RuntimeException("Out of Bound");
  }

  public boolean isRowOutOfBound(int row, int column) {
    return (row < 0 || row > SIZE -1);
  }

  public boolean isColumnOutOfBound(int row, int column) {
    return (column < 0 || column > SIZE -1);
  }

  public void toggleSeal(int row, int column) {
      verifyBounds(row, column);
      switch(cellStatus[row][column]) {
        case UNEXPOSED:
          cellStatus[row][column] = CellStatus.SEALED;
          break;

        case SEALED:
          cellStatus[row][column] = CellStatus.UNEXPOSED;
      }
  }

  public CellStatus getCellStatus(int i, int j) {
  return cellStatus[i][j];
}

  public Boolean isMined(int row, int column) {
    return minedCell[row][column];
  }


  public void setMines()
  {
    Random random = new Random();
    int count = 0;

    while(count < SIZE){
      int randomRow = random.nextInt(99);
      int randomColumn = random.nextInt(99);
      int row = randomRow / 10;
      int column = randomColumn / 10;

      if(!isMined(row, column) && row > 1 && column > 1)
      {
        minedCell[row][column] = true;
        count += 1;
      }
    }
  }

  public boolean isAdjacent(int row, int column) {
    int count = 0;
    if (!minedCell[row][column]) {
      count = countAdjacent(row, column);
      if (count > 0)
        return true;
    }
    return false;
  }

  public int countAdjacent(int row, int column){
    int count = 0;

    for (int i = row -1; i <= row +1; i++){
      for (int j = column -1; j <= column +1; j++) {
        if (i >= 0 && i < SIZE && j >= 0 && j < SIZE) {
          if (isMined(i, j))
            count += 1;
        }
      }
    }
    if (isMined(row, column))
      count -=1;

    return count;
  }

  public Boolean isEmpty(int row, int column){ return (!isAdjacent(row, column) && !isMined(row, column));  }

  public GameStatus getGameStatus() {

    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {

        if (isMined(i, j) && cellStatus[i][j] == CellStatus.EXPOSED) {
          return GameStatus.LOST;
        }
      }
    }

    for (int i = 0; i < 10; i++){
      for (int j = 0; j < 10; j++){
        if((!isMined(i, j) && cellStatus[i][j] == CellStatus.UNEXPOSED) ||
                (!isMined(i, j) && cellStatus[i][j] == CellStatus.SEALED) ||
                (isMined(i, j) && cellStatus[i][j] == CellStatus.UNEXPOSED)){
          return GameStatus.INPROGRESS;
        }
      }
    }return GameStatus.WON;
  }

}

