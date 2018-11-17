package com.krisztianszabo.tictactoe;

import java.util.Deque;
import java.util.LinkedList;

public class XOGame {
  private static XOGame instance;
  private Deque<Board> moveHistory;

  private XOGame() {
    moveHistory = new LinkedList<>();
    moveHistory.add(new Board());
  }

  static XOGame getInstance() {
    if (instance == null) {
      instance = new XOGame();
    }
    return instance;
  }

  String getBoardState() {
    return moveHistory.peek().getBoardState();
  }

  int getCurrentPlayer() {
    return moveHistory.peek().getCurrentPlayer();
  }

  boolean makeMove(int move) {
    Board newPosition = moveHistory.peek().makeMove(move);
    if (newPosition == null) {
      return false;
    } else {
      moveHistory.push(newPosition);
      return true;
    }
  }

  Board.GameState getGameState() {
    return moveHistory.peek().getGameState();
  }

  public Board.WinPattern getWinningPattern() { return moveHistory.peek().getWinningPattern(); }

  void undoLastMove() {
    if (moveHistory.size() > 1) {
      moveHistory.pop();
    }
  }

  void startNewGame() {
    moveHistory.clear();
    moveHistory.add(new Board());
  }

  void startNewGame(int player) {
    moveHistory.clear();
    moveHistory.add(new Board(player));
  }

  int getStartingPlayer() {
    return moveHistory.peekLast().getCurrentPlayer();
  }
}
