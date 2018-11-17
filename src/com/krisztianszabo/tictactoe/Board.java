package com.krisztianszabo.tictactoe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Board implements Cloneable{
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

  Board() {
    this(new Random().nextInt(2) + 1);
  }

  Board(int currentPlayer) {
    cells = new int[9]; // This also initializes cells to 0;
    this.gameState = GameState.ONGOING;
    this.currentPlayer = currentPlayer;
    this.winningPattern = WinPattern.NONE;
  }

  GameState getGameState() {
    return this.gameState;
  }

  WinPattern getWinningPattern() { return this.winningPattern; }

  private boolean isValidMove(int move) {
    boolean answer = false;
    if (move >= 1 && move  <= 9) {
      if (cells[move - 1] == 0) {
        answer = true;
      }
    }

    return answer;
  }

  Board makeMove(int move) {
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
    try {
      Board result = (Board) super.clone();
      result.currentPlayer = this.currentPlayer;
      result.cells = this.cells.clone();
      result.gameState = this.gameState;
      result.winningPattern = this.winningPattern;
      return result;
    } catch (CloneNotSupportedException e) { e.printStackTrace(); }
    return null;
  }

  String getBoardState() {
    StringBuilder result = new StringBuilder();

    for (int cell: cells) {
      result.append(cell);
    }

    return result.toString();
  }

  int getCurrentPlayer() {
    return this.currentPlayer;
  }

  int[] getLegalMoves() {
    ArrayList<Integer> legalMoves = new ArrayList<>();

    for (int i = 0; i < cells.length; i++) {
      if (cells[i] == 0) {
        legalMoves.add(i + 1);
      }
    }

    if (legalMoves.isEmpty()) {
      return null;
    } else {
      int[] result = new int[legalMoves.size()];
      for (int i = 0; i < result.length; i++) {
        result [i] = legalMoves.get(i);
      }
      return result;
    }
  }

  private void checkWinCondition() {
    HashMap<int[], WinPattern> winPatterns = new HashMap<>();
    winPatterns.put(new int[]{0, 1, 2}, WinPattern.ROW_ONE);
    winPatterns.put(new int[]{3, 4, 5}, WinPattern.ROW_TWO);
    winPatterns.put(new int[]{6, 7, 8}, WinPattern.ROW_THREE);
    winPatterns.put(new int[]{0, 3, 6}, WinPattern.COL_ONE);
    winPatterns.put(new int[]{1, 4, 7}, WinPattern.COL_TWO);
    winPatterns.put(new int[]{2, 5, 8}, WinPattern.COL_THREE);
    winPatterns.put(new int[]{0, 4, 8}, WinPattern.DIAG_ONE);
    winPatterns.put(new int[]{2, 4, 6}, WinPattern.DIAG_TWO);

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
  }
}