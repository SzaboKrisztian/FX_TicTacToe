package com.krisztianszabo.tictactoe;

public class HumanPlayer extends Player {

  public HumanPlayer(String name) {
    super(name);
  }

  @Override
  public int getMove() {
    int move = 0;
    /*Scanner scn = new Scanner(System.in);
    int move = 0;
    while (move == 0) {
      System.out.print(super.getName() + ", please enter your move [1-9]: ");
      try {
        move = scn.nextInt();
      } catch (InputMismatchException e) {
        System.out.println("Invalid input. Try again.");
      }
    }*/
    return move;
  }
}