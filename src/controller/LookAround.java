package controller;

import game.GameWorld;

/**
 * A class which is used as a command to execute the corresponding method in
 * GameWorld model to look around by a player.
 */
public class LookAround implements GameCommand {

  private String output;

  /**
   * A constructor to create instance of the command class LookAround.
   */
  public LookAround() {
    this.output = "";
  }

  @Override
  public void execute(GameWorld model) throws IllegalArgumentException {

    if (model == null) {
      throw new IllegalArgumentException("GameWorld or Appendable cannot be null");
    }
    String result = model.lookAroundByPlayer();
    this.output = result;
  }

  @Override
  public String getOutput() {
    return this.output;
  }
}
