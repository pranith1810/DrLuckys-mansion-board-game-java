package game;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;

/**
 * The interface which is used by the controller to communicate with the model.
 */
public interface GameWorld extends ReadOnlyModel {
  /**
   * Gets the information of a space like Name, items in the space and
   * neighbouring spaces.
   * 
   * @param spaceName Name of the space whose info we want to return
   * @return String with info about the given space
   * @throws IllegalArgumentException If space name is null or empty
   */
  public String getInfoOfaSpace(String spaceName) throws IllegalArgumentException;

  /**
   * Draw the image of the world in PNG format and saves it into the res folder.
   * 
   * @return String Path of the saved PNG image
   */
  public String drawImage() throws IllegalStateException;

  /**
   * Sets the model's world specification using the world data sent.
   * 
   * @param worldData The Readable world data which is used to set the world
   *                  specifications
   * @throws IllegalArgumentException When worldData is null or when rows and
   *                                  columns is less than 1 or list of spaces is
   *                                  empty or world/target/pet name is null or
   *                                  empty string or when the target's health is
   *                                  less than 1 or when there is an overlap
   *                                  among spaces
   * @throws InputMismatchException   When the input world specification is not
   *                                  present in the required format
   * @throws NoSuchElementException   When the number of spaces or items specified
   *                                  does not match the actual number of spaces
   *                                  or items in the text
   */
  public void setWorldSpecification(Readable worldData)
      throws IllegalArgumentException, NoSuchElementException, InputMismatchException;

  /**
   * Add a player to the world.
   * 
   * @param name              Name of the player
   * @param startingSpaceName Name of the space the player will start the game in
   * @param isHuman           The type of the player to add
   * @throws IllegalArgumentException When name or space name is null or an empty
   *                                  string or when the Space Name is not present
   *                                  in the world
   */
  public void addPlayer(String name, String startingSpaceName, boolean isHuman)
      throws IllegalArgumentException;

  /**
   * Move the current turn player to the specified space.
   * 
   * @param xcoord The x coordinate of the mouse click
   * @param ycoord The y coordinate of the mouse click
   * @return return a string which gives the summary of the action performed
   * @throws IllegalArgumentException when the coordinates are negative
   */
  public String movePlayerInWorld(int xcoord, int ycoord) throws IllegalArgumentException;

  /**
   * Adds the specified item to the player's items list.
   * 
   * @param itemName Name of the item the player wants to pickup
   * @return return a string which gives the summary of the action performed
   * @throws IllegalArgumentException when the item name is null or an empty
   *                                  string or when the specified item is not a
   *                                  part of the space
   * @throws IllegalStateException    When the item is not part of the current
   *                                  player's space
   */
  public String pickItemByPlayer(String itemName)
      throws IllegalArgumentException, IllegalStateException;

  /**
   * Return the information of where the current turn player is in the world and
   * the spaces that can be seen from the current space.
   * 
   * @return the information of where the current turn player is in the world and
   *         the spaces that can be seen from the current space as a string
   */
  public String lookAroundByPlayer();

  /**
   * Performs an action as a computer controlled player and returns the summary.
   * 
   * @return Returns a string of the summary of the action performed.
   */
  public String performComputerAction();

  /**
   * Moves the pet to the specified space name by the the current turn player.
   * 
   * @param spaceName The name of the space to move the pet to
   * @return result String.
   * @throws IllegalArgumentException When space name is null or empty or if the
   *                                  pet's current space is the same space the
   *                                  player is trying to move the pet to or when
   *                                  the given space name is not part of the
   *                                  world
   */
  public String movePetByPlayer(String spaceName) throws IllegalArgumentException;

  /**
   * Attack the target by the current turn player using the item specified. Attack
   * target also works with "hand" as a weapon which decrease health by one point
   * 
   * @param itemName The name of the item which is to be used to attack the
   *                 target. If "hand" is specified, then it will do one point of
   *                 damage to the target
   * @return A String, -1 for unsuccessful attempt, 0 for successful attempt and 1
   *         for player won
   * @throws IllegalArgumentException If item name is null or empty or if the item
   *                                  does not exist with the current turn player
   *                                  or when the player is not in the same space
   *                                  as the target.
   */
  public String attackTarget(String itemName) throws IllegalArgumentException;

  /**
   * Returns a message if game is completed or an empty string if game is not
   * completed.
   * 
   * @return A message that should be displayed to the user
   */
  public String isGameOver();

  /**
   * Returns the number of turns that are remaining in the game.
   * 
   * @return The number of turns left in the game
   */
  public int getNumOfTurns();
}
