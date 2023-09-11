package controller;

import game.GameWorld;

/**
 * A class which is used as a command to execute the corresponding method in
 * GameWorld model to determine if the game is completed when a player won.
 */
public class IsGameOver implements GameCommand {

  private String output;

  /**
   * A constructor to create instance of the command class IsGameOver.
   */
  public IsGameOver() {
    this.output = "";
  }

  @Override
  public void execute(GameWorld model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("GameWorld or Appendable cannot be null");
    }
    String result = model.isGameOver();
    this.output = result;
  }

  @Override
  public String getOutput() {
    return String.valueOf(output);
  }

}
