package controller;

import game.GameWorld;

/**
 * A class which is used as a command to return if the cureent turn player is a
 * computer or a human.
 */
public class IsCurrentComputer implements GameCommand {

  private String output;

  /**
   * A constructor to create instance of the command class IsCurrentComputer.
   */
  public IsCurrentComputer() {
    this.output = "";
  }

  @Override
  public void execute(GameWorld model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("GameWorld or Appendable cannot be null");
    }
    String result = model.getTurnInfo().get(1);
    this.output = result;
  }

  @Override
  public String getOutput() {
    return output;
  }

}
