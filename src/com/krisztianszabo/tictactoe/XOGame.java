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

  public static XOGame getInstance() {
    if (instance == null) {
      instance = new XOGame();
    }
    return instance;
  }

  public String getBoardState() {
    return moveHistory.peek().getBoardState();
  }

  public int getCurrentPlayer() {
    return moveHistory.peek().getCurrentPlayer();
  }

  public boolean makeMove(int move) {
    Board newPosition = moveHistory.peek().makeMove(move);
    if (newPosition == null) {
      return false;
    } else {
      moveHistory.push(newPosition);
      return true;
    }
  }

  public int getGameState() {
    return moveHistory.peek().getGameState();
  }

  public void undoLastMove() {
    if (moveHistory.size() > 1) {
      moveHistory.pop();
    }
  }

  public void startNewGame() {
    moveHistory.clear();
    moveHistory.add(new Board());
  }

  public void startNewGame(int player) {
    moveHistory.clear();
    moveHistory.add(new Board(player));
  }

  public int getStartingPlayer() {
    return moveHistory.peekLast().getCurrentPlayer();
  }
}
