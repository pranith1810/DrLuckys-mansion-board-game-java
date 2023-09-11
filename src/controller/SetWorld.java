package controller;

import game.GameWorld;

/**
 * A class which is used as a command to execute the corresponding method in
 * GameWorld model to set a new world specification.
 */
public class SetWorld implements GameCommand {
  private final Readable mansionReadable;

  /**
   * A constructor to create instance of the class with the specified world
   * readable file.
   * 
   * @param mansionReadable The readable instance which contains the world
   *                        specification
   * @throws IllegalArgumentException When mansionReadable is null
   */
  public SetWorld(Readable mansionReadable) throws IllegalArgumentException {
    if (mansionReadable == null) {
      throw new IllegalArgumentException("World specification readable cannot be null");
    }

    this.mansionReadable = mansionReadable;
  }

  @Override
  public void execute(GameWorld model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }

    model.setWorldSpecification(mansionReadable);
  }

  @Override
  public String getOutput() {
    return "";
  }

}
