package controller;

/**
 * An interface of the controller which represents all the features provided by
 * the controller to the view.
 */
public interface Features {

  /**
   * Updates the path of the world specification file that must be used to build
   * the world.
   * 
   * @param worldSpecification The string type which contains the new world
   *                           specifications
   * @throws IllegalArgumentException When file readable is null
   */
  public void updateWorldSpecification(String worldSpecification) throws IllegalArgumentException;

  /**
   * Used to add a player into the game.
   * 
   * @param name             The name of the player to be added
   * @param startingLocation The starting space of the player to be added
   * @param isHuman          Whether the player is human or a bot
   * 
   * @throws IllegalArgumentException When name or startingLocation is null or
   *                                  empty
   */
  public void addPlayer(String name, String startingLocation, boolean isHuman)
      throws IllegalArgumentException;

  /**
   * Used to perform an action based on the mouse click.
   * 
   * @param xcoord The x coordinate of the location where mouse was clicked on the
   *               window
   * @param ycoord The y coordinate of the location where mouse was clicked on the
   *               window
   * @throws IllegalArgumentException When coordinates are negative
   *
   */
  public void spaceIsClicked(int xcoord, int ycoord) throws IllegalArgumentException;

  /**
   * Used to perform pick an item action by a player.
   * 
   * @param itemName The name of the item to be picked
   * @throws IllegalArgumentException When itemName is null or empty
   */
  public void pickItem(String itemName) throws IllegalArgumentException;

  /**
   * Used to perform an attack on the target character using the selected item.
   * 
   * @param itemName The name of the item to be used
   * @throws IllegalArgumentException When itemName is null or empty
   */
  public void attackTarget(String itemName) throws IllegalArgumentException;

  /**
   * Used to perform the action of moving a pet to a different space.
   * 
   * @param spaceName The name of the space to move the pet to
   * @throws IllegalArgumentException When spaceName is null or empty
   */
  public void movePet(String spaceName) throws IllegalArgumentException;

  /**
   * Used to perform look around action by a player.
   */
  public void lookAround();

  /**
   * Used to tell the controller that the user has clicked on start game.
   */
  public void startGameIsClicked();

  /**
   * Used to tell the controller that the user has clicked on new world.
   */
  public void newWorldIsClicked();

  /**
   * Used to tell the controller that the user has clicked on exit.
   */
  public void exitIsClicked();

  /**
   * Used to tell the controller that the user has clicked on add player.
   */
  public void addPlayerIsClicked();

  /**
   * Used to tell the controller that the user choosed to pick item.
   */
  public void pickItemIsPressed();

  /**
   * Used to tell the controller that the user choosed to attack target.
   */
  public void attackTargetIsPressed();

  /**
   * Used to tell the controller that the user choosed to move pet.
   */
  public void movePetIsPressed();

  /**
   * Used to move the screen from add player screen to Game screen.
   */
  public void showGameScreen();

}
