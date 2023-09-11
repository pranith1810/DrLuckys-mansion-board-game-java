package controller;

import game.GameWorld;

/**
 * A class which is used as a command to execute the corresponding method in
 * GameWorld model to display info of a space.
 */
public class DisplayInfoOfSpace implements GameCommand {

  private final String spaceName;
  private String output;

  /**
   * A constructor to create instance of the class with the specified space name.
   * 
   * @param spaceName The name of the space to display info of.
   * @throws IllegalArgumentException When spacename is null or empty
   */
  public DisplayInfoOfSpace(String spaceName) throws IllegalArgumentException {
    if (spaceName == null) {
      throw new IllegalArgumentException("Space name cannot be null");
    }

    if (spaceName.length() == 0) {
      throw new IllegalArgumentException("Space name cannot be empty string");
    }

    this.spaceName = spaceName;
    this.output = "";
  }

  @Override
  public void execute(GameWorld model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("GameWorld cannot be null");
    }

    String result = model.getInfoOfaSpace(spaceName);
    this.output = result;
  }

  @Override
  public String getOutput() {
    return this.output;
  }

}
