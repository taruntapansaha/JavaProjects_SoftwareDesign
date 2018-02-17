package game;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static game.MineSweeper.CellStatus.UNEXPOSED;
import static org.junit.Assert.*;

public class MineSweeperTest {
  MineSweeper mineSweeper;

  @Before
  public void setUp() {
    mineSweeper = new MineSweeper();
  }

  @Test
  public void Canary() {
    assertTrue(true);
  }

  @Test
  public void exposeACell() {
    mineSweeper.exposeCell(5, 5);

    assertEquals(MineSweeper.CellStatus.EXPOSED, mineSweeper.getCellStatus(5, 5));
  }

  @Test
  public void exposeAnExposedCell() {
    mineSweeper.exposeCell(1, 1);
    mineSweeper.exposeCell(1, 1);

    assertEquals(MineSweeper.CellStatus.EXPOSED, mineSweeper.getCellStatus(1, 1));
  }

  class ExposeCellCallsVerifyBounds extends MineSweeper {
    boolean verifyBoundCalled = false;

    @Override
    public void verifyBounds(int row, int column) {
      verifyBoundCalled = true;
    }
  }

  @Test
  public void exposeACellCallsVerifyBound() {
    ExposeCellCallsVerifyBounds mineSweeper = new ExposeCellCallsVerifyBounds();
    mineSweeper.exposeCell(4, 6);

    assertTrue(mineSweeper.verifyBoundCalled);
  }

  @Test
  public void exposeCellOutOfUpperLeftBound() {
    try {
      mineSweeper.verifyBounds(-1, -1);
      fail("Expected exception stepping out of bound");
    } catch (RuntimeException ex) {
    }
  }

  @Test
  public void exposeCellOutOfUpperRightBound() {
    try {
      mineSweeper.verifyBounds(9, 10);
      fail("Expected exception stepping out of bound");
    } catch (RuntimeException ex) {
    }
  }

  @Test
  public void exposeCellOutOfLowerLeftBound() {
    try {
      mineSweeper.verifyBounds(9, 10);
      fail("Expected exception stepping out of bound");
    } catch (RuntimeException ex) {
    }
  }

  @Test
  public void exposeCellOutOfLowerRightBound() {
    try {
      mineSweeper.verifyBounds(10, 10);
      fail("Expected exception stepping out of bound");
    } catch (RuntimeException ex) {
    }
  }

  class MineSweeperExposeNeighborsStubbed extends MineSweeper {
    public boolean exposeNeighborsCalled = false;

    @Override
    public void exposeNeighbors(int row, int column) {
      exposeNeighborsCalled = true;
    }
  }

  @Test
  public void exposeCellCallsExposeNeighbors() {
    MineSweeperExposeNeighborsStubbed mineSweeper = new MineSweeperExposeNeighborsStubbed();
    mineSweeper.exposeCell(4, 7);

    assertTrue(mineSweeper.exposeNeighborsCalled);
  }

  @Test
  public void exposeCellDoesNotCallExposeNeighborsForAnAlreadyExpsedCell() {
    MineSweeperExposeNeighborsStubbed mineSweeper = new MineSweeperExposeNeighborsStubbed();
    mineSweeper.exposeCell(4, 7);
    mineSweeper.exposeNeighborsCalled = false;
    mineSweeper.exposeCell(4, 7);

    assertFalse(mineSweeper.exposeNeighborsCalled);
  }

  class MineSweeperExposeCellStubbed extends MineSweeper {
    public List<Integer> listOfRows = new ArrayList<>();
    public List<Integer> listOfColumn = new ArrayList<>();

    @Override
    public void exposeCell(int i, int j) {
      listOfRows.add(i);
      listOfColumn.add(j);
    }
  }

  @Test
  public void exposeNeighborCallsExposeCell() {
    MineSweeperExposeCellStubbed mineSweeper = new MineSweeperExposeCellStubbed();
    mineSweeper.exposeNeighbors(3, 4);

    assertEquals(Arrays.asList(2, 2, 2, 3, 3, 3, 4, 4, 4), mineSweeper.listOfRows);
    assertEquals(Arrays.asList(3, 4, 5, 3, 4, 5, 3, 4, 5), mineSweeper.listOfColumn);
  }

  @Test
  public void exposeNeighborsTopLeft() {
    MineSweeperExposeCellStubbed mineSweeper = new MineSweeperExposeCellStubbed();
    mineSweeper.exposeNeighbors(0, 0);

    assertEquals(Arrays.asList(0, 0, 1, 1), mineSweeper.listOfRows);
    assertEquals(Arrays.asList(0, 1, 0, 1), mineSweeper.listOfColumn);
  }

  @Test
  public void exposeNeighborsTopRight() {
    MineSweeperExposeCellStubbed mineSweeper = new MineSweeperExposeCellStubbed();
    mineSweeper.exposeNeighbors(0, 9);

    assertEquals(Arrays.asList(0, 0, 1, 1), mineSweeper.listOfRows);
    assertEquals(Arrays.asList(8, 9, 8, 9), mineSweeper.listOfColumn);
  }

  @Test
  public void exposeNeighborsBottomRight() {
    MineSweeperExposeCellStubbed mineSweeper = new MineSweeperExposeCellStubbed();
    mineSweeper.exposeNeighbors(9, 9);

    assertEquals(Arrays.asList(8, 8, 9, 9), mineSweeper.listOfRows);
    assertEquals(Arrays.asList(8, 9, 8, 9), mineSweeper.listOfColumn);
  }

  @Test
  public void exposeNeighborsBottomLeft() {
    MineSweeperExposeCellStubbed mineSweeper = new MineSweeperExposeCellStubbed();
    mineSweeper.exposeNeighbors(9, 0);

    assertEquals(Arrays.asList(8, 8, 9, 9), mineSweeper.listOfRows);
    assertEquals(Arrays.asList(0, 1, 0, 1), mineSweeper.listOfColumn);
  }

  @Test
  public void sealAnUnexposedCell() {
    mineSweeper.toggleSeal(3, 5);

    assertEquals(MineSweeper.CellStatus.SEALED, mineSweeper.getCellStatus(3, 5));
  }

  @Test
  public void toggleASealedCell() {
    mineSweeper.toggleSeal(3, 5);
    mineSweeper.toggleSeal(3, 5);

    assertEquals(MineSweeper.CellStatus.UNEXPOSED, mineSweeper.getCellStatus(3, 5));
  }

  @Test
  public void sealAnExposedCell() {
    mineSweeper.exposeCell(3, 5);
    mineSweeper.toggleSeal(3, 5);

    assertEquals(MineSweeper.CellStatus.EXPOSED, mineSweeper.getCellStatus(3, 5));
  }

  @Test
  public void tryToExposeASealedCell() {
    mineSweeper.toggleSeal(3, 5);
    mineSweeper.exposeCell(3, 5);

    assertEquals(MineSweeper.CellStatus.SEALED, mineSweeper.getCellStatus(3, 5));
  }

  @Test
  public void toggleSealCallsVerifyBound() {
    ExposeCellCallsVerifyBounds mineSweeper = new ExposeCellCallsVerifyBounds();
    mineSweeper.toggleSeal(4, 6);

    assertTrue(mineSweeper.verifyBoundCalled);
  }

  @Test
  public void exposeASealedCellDoesNotCallExposeNeighbors() {
    MineSweeperExposeNeighborsStubbed mineSweeper = new MineSweeperExposeNeighborsStubbed();
    mineSweeper.toggleSeal(3, 5);
    mineSweeper.exposeCell(3, 5);
    assertFalse(mineSweeper.exposeNeighborsCalled);
  }

  class MineSweeperAdjacentCellStubbed extends MineSweeperExposeNeighborsStubbed {
    public boolean exposeNeighborsOnAdjacentCalled = false;

    @Override
    public boolean isAdjacent(int row, int column) {
      exposeNeighborsOnAdjacentCalled = true;
      return true;
    }
  }

  @Test
  public void exposeCellDoesNotCallExposeNeighborsOnAdjacentCell() {
    MineSweeperAdjacentCellStubbed mineSweeper = new MineSweeperAdjacentCellStubbed();
    mineSweeper.exposeCell(3, 5);

    assertFalse(mineSweeper.exposeNeighborsCalled);
  }

  @Test
  public void exposeCellDoesNotCallExposeNeighborsOnMinedCell() {
    MineSweeperExposeNeighborsStubbed mineSweeper = new MineSweeperExposeNeighborsStubbed();
    mineSweeper.minedCell[5][7] = true;
    mineSweeper.exposeCell(5, 7);

    assertFalse(mineSweeper.exposeNeighborsCalled);
  }

  @Test
  public void testMethod() {
    MineSweeperExposeNeighborsStubbed mineSweeper = new MineSweeperExposeNeighborsStubbed();
    mineSweeper.minedCell[5][7] = true;
    mineSweeper.exposeCell(5, 8);
    mineSweeper.exposeCell(4, 9);

  }

  @Test
  public void isGameInProgress() {
    mineSweeper.setMines();
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        mineSweeper.cellStatus[i][j] = UNEXPOSED;
      }
    }
    mineSweeper.getGameStatus();

    assertEquals(MineSweeper.GameStatus.INPROGRESS, mineSweeper.getGameStatus());
  }

  @Test
  public void isGameWonWhenAllTheMinesAreSealedAndRestCellsExposed() {
    mineSweeper.setMines();
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        if (mineSweeper.isMined(i, j)) {
          mineSweeper.toggleSeal(i, j);
        } else {
          mineSweeper.exposeCell(i, j);
        }
      }
    }
    mineSweeper.getGameStatus();

    assertEquals(MineSweeper.GameStatus.WON, mineSweeper.getGameStatus());
  }

  @Test
  public void isGameLostWhenAMinedCellIsExposed() {
    mineSweeper.minedCell[4][5] = true;
    mineSweeper.exposeCell(4, 5);

    assertEquals(MineSweeper.GameStatus.LOST, mineSweeper.getGameStatus());
  }

  @Test
  public void isGameInProgressWhenAllMinesPlusAnotherCellIsSeal() {
    mineSweeper.setMines();
    int count = 0;
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        if (mineSweeper.isMined(i, j)) {
          mineSweeper.toggleSeal(i, j);
        } else if (count == 0) {
          mineSweeper.toggleSeal(i, j);
          count += 1;
        }
      }
    }

    assertEquals(MineSweeper.GameStatus.INPROGRESS, mineSweeper.getGameStatus());
  }

  @Test
  public void adjacentCountRetrievesCorrectMineCount() {
    mineSweeper.minedCell[0][0] = true;
    mineSweeper.minedCell[1][0] = true;
    mineSweeper.minedCell[2][0] = true;

    assertEquals(3, mineSweeper.countAdjacent(1, 1));
  }

  @Test
  public void countAdjacentMinesForAMinedCell() {
    mineSweeper.minedCell[1][1] = true;

    assertEquals(0, mineSweeper.countAdjacent(1, 1));

  }

  @Test
  public void checkCountAdjacentOnLeftTopCornerCell() {
    int count = mineSweeper.countAdjacent(0, 0);

    assertEquals(0, count);
  }

  @Test
  public void checkIfIsAdjacentReturnsCorrectResult() {
    mineSweeper.minedCell[4][5] = true;

    assertTrue(mineSweeper.isAdjacent(4, 6));
    assertFalse(mineSweeper.isAdjacent(4, 7));
  }

  @Test
  public void checkIfIsMinedReturnsCorrectResult() {
    mineSweeper.minedCell[8][9] = true;

    assertTrue(mineSweeper.isMined(8, 9));
    assertFalse(mineSweeper.isMined(1, 1));
  }

  @Test
  public void checkIfIsEmptyReturnCorrectResult() {
    mineSweeper.minedCell[3][4] = true;

    assertFalse(mineSweeper.isEmpty(3, 4));
    assertTrue(mineSweeper.isEmpty(3, 6));
  }

  @Test
  public void checkGameStatusWithOneCellExposed() {
    mineSweeper.cellStatus[4][7] = MineSweeper.CellStatus.EXPOSED;

    assertEquals(MineSweeper.GameStatus.INPROGRESS, mineSweeper.getGameStatus());
  }

  @Test
  public void testToQuiteCoverageForEnumCellStatus(){
    MineSweeper.CellStatus.values();
    MineSweeper.CellStatus.valueOf("UNEXPOSED");
    MineSweeper.CellStatus.valueOf("EXPOSED");
    MineSweeper.CellStatus.valueOf("SEALED");
  }

  @Test
  public void testToQuiteCoverageForEnumGameStatus(){
    MineSweeper.GameStatus.values();
    MineSweeper.GameStatus.valueOf("INPROGRESS");
    MineSweeper.GameStatus.valueOf("WON");
    MineSweeper.GameStatus.valueOf("LOST");
  }


  @Test
  public void checkIfIsRowOutOfBoundReturnCorrectResult() {
    assertTrue(mineSweeper.isRowOutOfBound(-1, 6));
    assertFalse(mineSweeper.isColumnOutOfBound(3, 5));
  }

  @Test
  public void checkIfIsColumnOutOfBoundReturnCorrectResult() {
    assertTrue(mineSweeper.isColumnOutOfBound(1, -6));
    assertTrue(mineSweeper.isColumnOutOfBound(3, 10));
  }


}