package controller;

import game.GameWorld;

/**
 * A class which is used as a command to execute the corresponding method in
 * GameWorld model to move a player in the world.
 */
public class MovePlayer implements GameCommand {

  private final int xcoord;
  private final int ycoord;
  private String output;

  /**
   * A constructor to create instance of the class with the specified space name.
   * 
   * @param xcoord The x coordinate of the mouse click on the world
   * @param ycoord The y coordinate of the mouse click on the world
   * @throws IllegalArgumentException When coordinates are negative
   */
  public MovePlayer(int xcoord, int ycoord) throws IllegalArgumentException {
    if (xcoord < 0 || ycoord < 0) {
      throw new IllegalArgumentException("Coordinates cannot be negative");
    }

    this.xcoord = xcoord;
    this.ycoord = ycoord;
    this.output = "";
  }

  @Override
  public void execute(GameWorld model) throws IllegalArgumentException {

    if (model == null) {
      throw new IllegalArgumentException("GameWorld cannot be null");
    }

    this.output = model.movePlayerInWorld(xcoord, ycoord);

  }

  @Override
  public String getOutput() {
    return this.output;
  }

}
