package com.krisztianszabo.tictactoe;

import java.lang.Math;

public class Board {
  private int[] cells;
  private int currentPlayer;
  private int gameState;

  public Board() {
    this(((int) Math.random() * 2) + 1);
  }

  public Board(int currentPlayer) {
    cells = new int[9]; // This also initializes cells to 0;
    gameState = 0;
    this.currentPlayer = currentPlayer;
  }

  public int getGameState() {
    return this.gameState;
  }

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
      result.gameState = Board.checkWinCondition(result);
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

  public static int checkWinCondition(Board board) {

    char[] boardArr = board.getBoardState().toCharArray();

    if (boardArr[0] != '0' &&
        ((boardArr[0] == boardArr[1] && boardArr[0] == boardArr[2])
            || (boardArr[0] == boardArr[3] && boardArr[0] == boardArr[6])
            || (boardArr[0] == boardArr[4] && boardArr[0] == boardArr[8]))) {
      return Character.getNumericValue(boardArr[0]);
    } else if (boardArr[1] != '0' &&
        (boardArr[1] == boardArr[4] && boardArr[1] == boardArr[7])) {
      return Character.getNumericValue(boardArr[1]);
    } else if (boardArr[2] != '0' &&
        ((boardArr[2] == boardArr[5] && boardArr[2] == boardArr[8])
            || (boardArr[2] == boardArr[4] && boardArr[2] == boardArr[6]))) {
      return Character.getNumericValue(boardArr[2]);
    } else if (boardArr[3] != '0' &&
        (boardArr[3] == boardArr[4] && boardArr[3] == boardArr[5])) {
      return Character.getNumericValue(boardArr[3]);
    } else if (boardArr[6] != '0' &&
        (boardArr[6] == boardArr[7] && boardArr[6] == boardArr[8])) {
      return Character.getNumericValue(boardArr[6]);
    } else {
      for (char cell: boardArr) {
        if (cell == '0') {
          return 0;
        }
      }
      return 3;
    }
  }
}