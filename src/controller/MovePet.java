package controller;

import game.GameWorld;

/**
 * A class which is used as a command to execute the corresponding method in
 * GameWorld model to move a pet in the world.
 */
public class MovePet implements GameCommand {

  private final String spaceName;
  private String output;

  /**
   * A constructor to create instance of the class with the specified space name.
   * 
   * @param spaceName The name of the space to move the pet to
   * @throws IllegalArgumentException When spaceName is null or empty
   */
  public MovePet(String spaceName) throws IllegalArgumentException {
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

    this.output = model.movePetByPlayer(spaceName);

  }

  @Override
  public String getOutput() {
    return this.output;
  }

}
