package com.krisztianszabo.tictactoe;

public class ComputerPlayer extends Player {
  ComputerPlayer() {
    super("CPU");
  }

  @Override
  public boolean isCPU() {
    return true;
  }

  @Override
  public int getMove() {
    Board currentBoard = XOGame.getInstance().getBoardCopy();
    int[] moves = currentBoard.getLegalMoves();
    Move[] scoredMoves = new Move[moves.length];
    for (int i = 0; i < scoredMoves.length; i++) {
      scoredMoves[i] = new Move(moves[i], minMax(currentBoard.makeMove(i)));
    }
    Move result = null;
    int bestScore = -10;
    for (Move move: scoredMoves) {
      if (move.score > bestScore) {
        bestScore = move.score;
        result = move;
      }
    }
    return result.move;
  }

  private int minMax(Board board) {
    int self = PlayerManager.getInstance().getCPUPlayerNum();
    if (board.getGameState() == Board.GameState.P1WON) {
      return self == 1 ? 1 : -1;
    } else if (board.getGameState() == Board.GameState.P2WON) {
      return self == 2 ? 1 : -1;
    } else if (board.getGameState() == Board.GameState.TIE) {
      return 0;
    } else {
      int[] moves = board.getLegalMoves();
      Move[] scoredMoves = new Move[moves.length];
      for (int i = 0; i < scoredMoves.length; i++) {
        scoredMoves[i] = new Move(moves[i], minMax(board.makeMove(i)));
      }
      Move bestMove = null;
      if (board.getCurrentPlayer() == self) {
        int bestScore = -10;
        for (Move move: scoredMoves) {
          if (move.score > bestScore) {
            bestScore = move.score;
            bestMove = move;
          }
        }
      } else {
        int bestScore = 10;
        for (Move move: scoredMoves) {
          if (move.score < bestScore) {
            bestScore = move.score;
            bestMove = move;
          }
        }
      }
      return bestMove.score;
    }
  }

  private class Move {
    int move;
    int score;

    private Move(int move, int score) {
      this.move = move;
      this.score = score;
    }
  }
}
