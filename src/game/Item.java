package game;

/**.
 * Item is used to decrese the health of the target. 
 * Items are present in spaces.
 */
public interface Item {
  
  /**
   * Get the name of this item as a string.
   *
   * @return The name of the item
   */
  public String getName();
  
  /**
   * Get the damage value that this item can give to the target.
   *
   * @return The damage of the item
   */
  public int getDamage();
}
