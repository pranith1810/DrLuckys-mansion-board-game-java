package game;

import java.util.List;
import utils.RandomManual;

/**
 * A Player is the one who plays the game and tries to kill the target
 * character. A player will move from one space to other space and carries
 * items. The maximum items a player can carry is 5.
 */
public interface Player {

  /**
   * Get the name of the current player instance.
   * 
   * @return The name of the player.
   */
  public String getName();

  /**
   * Gets the index of the space the player is currently present in.
   * 
   * @return The index of the space the player is in.
   */
  public int getSpaceIndexOfPlayer();

  /**
   * Moves the player to the specified space index.
   * 
   * @param index Index of the space to move the player to.
   * @throws IllegalArgumentException When the index is negative
   */
  public void movePlayer(int index) throws IllegalArgumentException;

  /**
   * Picks the specified item and adds it to the list of items with the player.
   * 
   * @param item The Item which the player wants to pick up.
   * @throws IllegalArgumentException If the item is null
   * @throws IllegalStateException    When the player has reached max number of
   *                                  items he can carry
   */
  public void pickItem(Item item) throws IllegalArgumentException, IllegalStateException;

  /**
   * Removes the specified item from list of items with the player.
   * 
   * @param itemName The Item which the player wants to remove.
   * @throws IllegalArgumentException When item name is an empty string or null or
   *                                  when the item is not present with the player
   */
  public void removeItem(String itemName) throws IllegalArgumentException;

  /**
   * Gets the list of items with the player.
   * 
   * @return The list of items that the player has.
   */
  public List<Item> getPlayerItems();

  /**
   * Gets the maximum number of items the player can carry.
   * 
   * @return The maximum number of items the player can carry
   */
  public int getMaxNumOfItems();

  /**
   * Gets the type of the player.
   * 
   * @return The type of the player Human or Computer.
   */
  public String getPlayerType();

  /**
   * Chooses an action randomly that a player needs to act on. This is used for a
   * computer controlled player.
   * 
   * @param random The object which is used to generate a random number
   * @param moduloOn The number on which the random number generated will be modded on
   * 
   * @return Returns the randomly chosen number for computer player and zero for human player
   * 
   * @throws IllegalArgumentException When random instance is null or moduloOn is negative
   */
  public int chooseAction(RandomManual random, int moduloOn) throws IllegalArgumentException;
}
