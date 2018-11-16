package com.krisztianszabo.tictactoe;

import java.lang.Math;
import java.util.HashMap;

public class Board {
  private int[] cells;
  private int currentPlayer;
  private GameState gameState;
  private WinPattern winningPattern;

  public enum GameState {
    ONGOING,
    P1WON,
    P2WON,
    TIE
  }

  public enum WinPattern {
    NONE,
    COL_ONE,
    COL_TWO,
    COL_THREE,
    ROW_ONE,
    ROW_TWO,
    ROW_THREE,
    DIAG_ONE,
    DIAG_TWO
  }

  public Board() {
    this(((int) Math.random() * 2) + 1);
  }

  public Board(int currentPlayer) {
    cells = new int[9]; // This also initializes cells to 0;
    gameState = GameState.ONGOING;
    this.currentPlayer = currentPlayer;
    this.winningPattern = WinPattern.NONE;
  }

  public GameState getGameState() {
    return this.gameState;
  }

  public WinPattern getWinningPattern() { return this.winningPattern; }

  public boolean isValidMove(int move) {
    boolean answer = false;
    if (move >= 1 && move  <= 9) {
      if (cells[move - 1] == 0) {
        answer = true;
      }
    }

    return answer;
  }

  public Board makeMove(int move) {
    Board result = null;
    if (isValidMove(move)) {
      result = this.clone();
      result.cells[move - 1] = currentPlayer;
      result.switchPlayer();
      result.checkWinCondition();
    }
    return result;
  }

  private void switchPlayer() {
    currentPlayer = (currentPlayer == 1) ? 2 : 1;
  }

  public Board clone() {
    Board result = new Board();
    result.currentPlayer = this.currentPlayer;
    result.cells = this.cells.clone();
    result.gameState = this.gameState;
    result.winningPattern = this.winningPattern;
    return result;
  }

  public String getBoardState() {
    StringBuilder result = new StringBuilder();

    for (int cell: cells) {
      result.append(cell);
    }

    return result.toString();
  }

  public int getCurrentPlayer() {
    return this.currentPlayer;
  }

  private void checkWinCondition() {
    HashMap<int[], WinPattern> winPatterns = new HashMap<>();
    //int[][] winPatterns = new int[][]{
    winPatterns.put(new int[]{0, 1, 2}, WinPattern.ROW_ONE);
    winPatterns.put(new int[]{3, 4, 5}, WinPattern.ROW_TWO);
    winPatterns.put(new int[]{6, 7, 8}, WinPattern.ROW_THREE);
    winPatterns.put(new int[]{0, 3, 6}, WinPattern.COL_ONE);
    winPatterns.put(new int[]{1, 4, 7}, WinPattern.COL_TWO);
    winPatterns.put(new int[]{2, 5, 8}, WinPattern.COL_THREE);
    winPatterns.put(new int[]{0, 4, 8}, WinPattern.DIAG_ONE);
    winPatterns.put(new int[]{2, 4, 6}, WinPattern.DIAG_TWO);
    //};
    for (int[] pattern: winPatterns.keySet()) {
      if (cells[pattern[0]] != 0) {
        if (cells[pattern[0]] == cells[pattern[1]] &&
            cells[pattern[1]] == cells[pattern[2]]) {
          this.gameState = cells[pattern[0]] == 1 ? GameState.P1WON : GameState.P2WON;
          this.winningPattern = winPatterns.get(pattern);
          return;
        }
      }
    }
    boolean foundEmptyCell = false;
    for (int cell: cells) {
      if (cell == 0) {
        foundEmptyCell = true;
        break;
      }
    }
    this.gameState = foundEmptyCell ? GameState.ONGOING : GameState.TIE;
    /*System.out.print(Arrays.toString(cells));
    if (cells[0] != 0) {
      if (cells[0] == cells[1] && cells[0] == cells[2]) {
        gameState = (cells[0] == 1) ? GameState.P1WON : GameState.P2WON;
        winningPattern = WinPattern.ROW_ONE;
      } else if (cells[0] == cells[3] && cells[0] == cells[6]) {
        gameState = (cells[0] == 1) ? GameState.P1WON : GameState.P2WON;
        winningPattern = WinPattern.COL_ONE;
      } else if (cells[0] == cells[4] && cells[0] == cells[8]) {
        gameState = (cells[0] == 1) ? GameState.P1WON : GameState.P2WON;
        winningPattern = WinPattern.DIAG_ONE;
      }
    } else if (cells[1] != 0) {
      if (cells[1] == cells[4] && cells[1] == cells[7]) {
        gameState = (cells[1] == 1) ? GameState.P1WON : GameState.P2WON;
        winningPattern = WinPattern.COL_TWO;
      }
    } else if (cells[2] != 0) {
      if (cells[2] == cells[5] && cells[2] == cells[8]) {
        gameState = (cells[2] == 1) ? GameState.P1WON : GameState.P2WON;
        winningPattern = WinPattern.COL_THREE;
      } else if (cells[2] == cells[4] && cells[2] == cells[6]) {
        gameState = (cells[2] == 1) ? GameState.P1WON : GameState.P2WON;
        winningPattern = WinPattern.DIAG_TWO;
      }
    } else if (cells[3] != 0) {
      if (cells[3] == cells[4] && cells[3] == cells[5]) {
        gameState = (cells[3] == 1) ? GameState.P1WON : GameState.P2WON;
        winningPattern = WinPattern.ROW_TWO;
      }
    } else if (cells[6] != 0) {
      if (cells[6] == cells[7] && cells[6] == cells[8]) {
        gameState = (cells[6] == 1) ? GameState.P1WON : GameState.P2WON;
        winningPattern = WinPattern.ROW_THREE;
      }
    } else {
      for (int cell: this.cells) {
        if (cell == 0) {
          gameState = GameState.ONGOING;
        }
      }
      gameState = GameState.TIE;
    }*/
  }
}