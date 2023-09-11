package game;

import java.util.List;

/**
 * A space is a closed entity which identifies a particular area. The world of
 * the game is made from multiple spaces. Each space may contain an item.
 */
public interface Space {

  /**
   * Get the name of the current space instance.
   * 
   * @return Name of the space
   */
  public String getName();

  /**
   * Get the top left x coordinate.
   * 
   * @return top left x coordinate
   */
  public int getTopLeftX();

  /**
   * Get the top left y coordinate.
   * 
   * @return top left y coordinate
   */
  public int getTopLeftY();

  /**
   * Get the bottom right x coordinate.
   * 
   * @return bottom right x coordinate
   */
  public int getBottomRightX();

  /**
   * Get the bottom right y coordinate.
   * 
   * @return bottom right y coordinate
   */
  public int getBottomRightY();

  /**
   * Get all the items that are present in this space.
   * 
   * @return List of all items present in this space
   */
  public List<Item> getItemsInSpace();

  /**
   * Get all the items that are present in this space.
   * 
   * @param itemName The name of the item which is to be removed
   * @throws IllegalArgumentException If provided space name is null or empty
   * @throws IllegalStateException If the given item name is not present in the space
   */
  public void removeItemFromSpace(String itemName)
      throws IllegalArgumentException, IllegalStateException;

}
