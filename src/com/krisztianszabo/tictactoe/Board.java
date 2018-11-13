package com.krisztianszabo.tictactoe;

import java.lang.Math;

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

  public void checkWinCondition() {

    if (cells[0] != 0) {
      if (cells[0] == cells[1] && cells[0] == cells[2]) {
        gameState = (Character.getNumericValue(cells[0]) == 1) ? GameState.P1WON : GameState.P2WON;
        winningPattern = WinPattern.ROW_ONE;
      } else if (cells[0] == cells[3] && cells[0] == cells[6]) {
        gameState = (Character.getNumericValue(cells[0]) == 1) ? GameState.P1WON : GameState.P2WON;
        winningPattern = WinPattern.COL_ONE;
      } else if (cells[0] == cells[4] && cells[0] == cells[8]) {
        gameState = (Character.getNumericValue(cells[0]) == 1) ? GameState.P1WON : GameState.P2WON;
        winningPattern = WinPattern.DIAG_ONE;
      }
    } else if (cells[1] != 0) {
      if (cells[1] == cells[4] && cells[1] == cells[7]) {
        gameState = (Character.getNumericValue(cells[1]) == 1) ? GameState.P1WON : GameState.P2WON;
        winningPattern = WinPattern.COL_TWO;
      }
    } else if (cells[2] != 0) {
      if (cells[2] == cells[5] && cells[2] == cells[8]) {
        gameState = (Character.getNumericValue(cells[2]) == 1) ? GameState.P1WON : GameState.P2WON;
        winningPattern = WinPattern.COL_THREE;
      }
    } else if (cells[3] != 0) {
      if (cells[3] == cells[4] && cells[3] == cells[5]) {
        gameState = (Character.getNumericValue(cells[3]) == 1) ? GameState.P1WON : GameState.P2WON;
        winningPattern = WinPattern.ROW_TWO;
      }
    } else if (cells[6] != 0) {
      if (cells[6] == cells[7] && cells[6] == cells[8]) {
        gameState = (Character.getNumericValue(cells[6]) == 1) ? GameState.P1WON : GameState.P2WON;
        winningPattern = WinPattern.ROW_THREE;
      }
      if (cells[6] == cells[4] && cells[6] == cells[2]) {
        gameState = (Character.getNumericValue(cells[6]) == 1) ? GameState.P1WON : GameState.P2WON;
        winningPattern = WinPattern.DIAG_TWO;
      }
    } else {
      for (int cell: this.cells) {
        if (cell == 0) {
          gameState = GameState.ONGOING;
        }
      }
      gameState = GameState.TIE;
    }
  }
}