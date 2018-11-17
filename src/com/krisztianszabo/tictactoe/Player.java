package com.krisztianszabo.tictactoe;

public abstract class Player {
  private String name;
  private int score;

  public Player(String name) {
    this.name = name;
    this.score = 0;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getScore() {
    return this.score;
  }

  public void resetScore() {
    this.score = 0;
  }

  public void increaseScore() {
    this.score++;
  }

  public abstract int getMove();

  public abstract boolean isCPU();
}
