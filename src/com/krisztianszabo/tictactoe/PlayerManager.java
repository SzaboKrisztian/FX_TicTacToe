package com.krisztianszabo.tictactoe;

class PlayerManager {
  private static PlayerManager instance;
  private Player[] players;

  private PlayerManager() {
    players = new Player[2];
    players[0] = new HumanPlayer("Player 1");
    players[1] = new HumanPlayer("Player 2");
  }

  static PlayerManager getInstance() {
    if (instance == null) {
      instance = new PlayerManager();
    }
    return instance;
  }

  Player getPlayer(int number) {
    return players[number - 1];
  }

  void setPlayer(int number, Player player) {
    if (number == 1 || number == 2) {
      players[number - 1] = player;
    }
  }

  void resetScores() {
    players[0].resetScore();
    players[1].resetScore();
  }

  boolean isCPUPlaying() {
    return players[0] instanceof ComputerPlayer || players[1] instanceof ComputerPlayer;
  }

  int getCPUPlayerNum() {
    if (isCPUPlaying()) {
      return players[0] instanceof ComputerPlayer ? 1 : 2;
    } else {
      return 0;
    }
  }
}
