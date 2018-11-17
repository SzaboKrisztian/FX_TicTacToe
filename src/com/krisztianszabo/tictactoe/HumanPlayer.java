package com.krisztianszabo.tictactoe;

public class HumanPlayer extends Player {

  HumanPlayer(String name) {
    super(name);
  }

  @Override
  public int getMove() {
    return 0;
  }

  @Override
  public boolean isCPU() {
    return false;
  }
}