package controller;

import game.GameWorld;

/**
 * A class which is used as a command to execute the corresponding method in
 * GameWorld model to add a player to the world.
 */
public class AddPlayer implements GameCommand {

  private final String playerName;
  private final String startingSpaceName;
  private final boolean isHuman;

  /**
   * A constructor to create instance of the class with the specified player name
   * and starting space name.
   * 
   * @param playerName        The name of the player add to the world
   * @param startingSpaceName The name of the space to add the player to
   * @param isHuman           The type of the player to add
   * @throws IllegalArgumentException When playerName or startingSpaceName is null
   *                                  or empty
   */
  public AddPlayer(String playerName, String startingSpaceName, boolean isHuman)
      throws IllegalArgumentException {

    if (playerName == null) {
      throw new IllegalArgumentException("Player name cannot be null");
    }

    if (playerName.length() == 0) {
      throw new IllegalArgumentException("Player name cannot be empty string");
    }

    if (startingSpaceName == null) {
      throw new IllegalArgumentException("Space name cannot be null");
    }

    if (startingSpaceName.length() == 0) {
      throw new IllegalArgumentException("Space name cannot be empty string");
    }

    this.playerName = playerName;
    this.startingSpaceName = startingSpaceName;
    this.isHuman = isHuman;

  }

  @Override
  public void execute(GameWorld model)
      throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("GameWorld cannot be null");
    }

    model.addPlayer(playerName, startingSpaceName, isHuman);
  }

  @Override
  public String getOutput() {
    return "";
  }

}
