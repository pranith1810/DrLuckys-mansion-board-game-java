package controller;

import game.GameWorld;

/**
 * A class which is used as a command to execute the corresponding method in
 * GameWorld model to draw the image of the world.
 */
public class DrawImage implements GameCommand {

  @Override
  public void execute(GameWorld model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("GameWorld or Appendable cannot be null");
    }

    model.drawImage();
  }

  @Override
  public String getOutput() {
    return "";
  }

}
