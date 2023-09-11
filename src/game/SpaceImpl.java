package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A generic implementaion of the Space interface which is part of the world.
 */
public final class SpaceImpl implements Space {

  private final int topLeftX;
  private final int topLeftY;
  private final int bottomRightX;
  private final int bottomRightY;
  private final String name;
  private final List<Item> itemsInSpace;

  /**
   * Constructs a Space instance with the given coordinates and name.
   * 
   * @param topLeftX     X coordinate of the top left side of the space
   * @param topLeftY     Y coordinate of the top left side of the space
   * @param bottomRightX X coordinate of the bottom right side of the space
   * @param bottomRightY Y coordinate of the bottom right side of the space
   * @param name         Name of the space
   * @param itemsInSpace All the items present in the space
   * @throws IllegalArgumentException If coordinates are negative or name is empty
   *                                  string or itemsInSpace is null
   */
  public SpaceImpl(int topLeftX, int topLeftY, int bottomRightX, int bottomRightY, String name,
      List<Item> itemsInSpace) throws IllegalArgumentException {
    if (topLeftX < 0 || topLeftY < 0 || bottomRightX < 0 || bottomRightY < 0) {
      throw new IllegalArgumentException("Coordinates cannot be negative");
    }

    if (name == null) {
      throw new IllegalArgumentException("Name of the space cannot be null");
    }

    if (name.length() == 0) {
      throw new IllegalArgumentException("Name of the space cannot be empty string");
    }

    if (itemsInSpace == null) {
      throw new IllegalArgumentException("Items list of the space cannot be null");
    }

    this.topLeftX = topLeftX;
    this.topLeftY = topLeftY;
    this.bottomRightX = bottomRightX;
    this.bottomRightY = bottomRightY;
    this.name = name;
    this.itemsInSpace = new ArrayList<>(itemsInSpace);
  }

  @Override
  public String getName() {
    return this.name;
  }

  // Here the item object is immutable
  @Override
  public List<Item> getItemsInSpace() {
    return new ArrayList<Item>(this.itemsInSpace);
  }

  @Override
  public int getTopLeftX() {
    return this.topLeftX;
  }

  @Override
  public int getTopLeftY() {
    return this.topLeftY;
  }

  @Override
  public int getBottomRightX() {
    return this.bottomRightX;
  }

  @Override
  public int getBottomRightY() {
    return this.bottomRightY;
  }

  /**
   * Returns a string representation of this Space in the form "Space(Name =
   * Dining, Top left = (10, 10), Bottom right = (20, 30))".
   * 
   * @return String format of this instance
   */
  @Override
  public String toString() {
    return String.format("Space(Name = %s, Top left = (%d, %d), Bottom right = (%d, %d))",
        this.name, this.topLeftX, this.topLeftY, this.bottomRightX, this.bottomRightY);
  }

  /**
   * Compares equality of two instances based on the coordinates and name.
   * 
   * @param obj Instance of class Object
   * @return True if equal else false
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof SpaceImpl)) {
      return false;
    }

    SpaceImpl that = (SpaceImpl) obj;

    return this.topLeftX == that.topLeftX && this.topLeftY == that.topLeftY
        && this.bottomRightX == that.bottomRightX && this.bottomRightY == that.bottomRightY
        && this.name.equals(that.name);
  }

  /**
   * Generates a hash code based on all the coordinates, name and items in space.
   * 
   * @return An integer value which is the hash code of the object
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.topLeftX, this.topLeftY, this.bottomRightX, this.bottomRightY,
        this.name);
  }

  @Override
  public void removeItemFromSpace(String itemName)
      throws IllegalArgumentException, IllegalStateException {

    boolean itemExists = false;

    if (itemName == null) {
      throw new IllegalArgumentException("Item name is null.");
    }

    if (itemName.length() == 0) {
      throw new IllegalArgumentException("Item name cannot be empty.");
    }

    for (int i = 0; i < itemsInSpace.size(); i++) {
      if (itemsInSpace.get(i).getName().equals(itemName)) {
        itemsInSpace.remove(i);
        itemExists = true;
        break;
      }
    }

    if (!itemExists) {
      throw new IllegalStateException("Provided item name is not present in the space.");
    }
  }

}