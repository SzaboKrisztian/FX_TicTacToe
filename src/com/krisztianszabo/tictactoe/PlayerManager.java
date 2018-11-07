package com.krisztianszabo.tictactoe;

public class PlayerManager {
  private static PlayerManager instance;
  private Player[] players;

  private PlayerManager() {
    players = new Player[2];
    players[0] = new HumanPlayer("Player 1");
    players[1] = new HumanPlayer("Player 2");
  }

  public static PlayerManager getInstance() {
    if (instance == null) {
      instance = new PlayerManager();
    }
    return instance;
  }

  public Player getPlayer(int number) {
    return players[number - 1];
  }

  public void resetScores() {
    players[0].resetScore();
    players[1].resetScore();
  }
}
