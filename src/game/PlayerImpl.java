package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import utils.RandomManual;

/**
 * A class implementation to represent a human controlled player.
 */
public class PlayerImpl implements Player {

  public static final int MAX_ITEMS_OF_PLAYER = 5;

  private final String name;
  private int currentSpaceIndex;
  private List<Item> itemsOfPlayers;
  private final int maxNumOfItems;

  /**
   * Constructs an instance of a human controlled player which depends on the user
   * input.
   * 
   * @param name              Name of the player
   * @param currentSpaceIndex The starting space index of the player
   * @throws IllegalArgumentException When name is null or empty or
   *                                  currentSpaceIndex is negative
   */
  public PlayerImpl(String name, int currentSpaceIndex) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("Player name cannot be null.");
    }

    if (name.length() == 0) {
      throw new IllegalArgumentException("Player name cannot be empty.");
    }

    if (currentSpaceIndex < 0) {
      throw new IllegalArgumentException("The space index cannot be negative");
    }

    this.name = name;
    this.currentSpaceIndex = currentSpaceIndex;
    this.maxNumOfItems = MAX_ITEMS_OF_PLAYER;
    this.itemsOfPlayers = new ArrayList<Item>();
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public int getSpaceIndexOfPlayer() {
    return this.currentSpaceIndex;
  }

  @Override
  public void movePlayer(int index) throws IllegalArgumentException {
    if (index < 0) {
      throw new IllegalArgumentException("Space index cannot be negative");
    }

    this.currentSpaceIndex = index;

  }

  @Override
  public void pickItem(Item item) throws IllegalArgumentException, IllegalStateException {
    if (item == null) {
      throw new IllegalArgumentException("Item cannot be null");
    }
    if (itemsOfPlayers.size() == this.maxNumOfItems) {
      throw new IllegalStateException("The player has reached the maximum limit of items");
    }
    this.itemsOfPlayers.add(item);
  }

  @Override
  public void removeItem(String itemName) throws IllegalArgumentException {
    if (itemName == null) {
      throw new IllegalArgumentException("Item name cannot be null");
    }

    if (itemName.length() == 0) {
      throw new IllegalArgumentException("Item name cannot be empty");
    }

    boolean itemPresent = false;

    for (int i = 0; i < itemsOfPlayers.size(); i++) {
      if (itemsOfPlayers.get(i).getName().equals(itemName)) {
        itemsOfPlayers.remove(i);
        itemPresent = true;
        break;
      }
    }

    if (!itemPresent) {
      throw new IllegalArgumentException("Item is not present with the player");
    }
  }

  @Override
  public List<Item> getPlayerItems() {
    return new ArrayList<Item>(this.itemsOfPlayers);
  }

  @Override
  public int getMaxNumOfItems() {
    return this.maxNumOfItems;
  }

  @Override
  public int chooseAction(RandomManual random, int moduloOn) {
    // A human player gets zero for a random number
    return 0;
  }

  /**
   * Returns a string representation of this Player in the form "Player(Name:
   * Pranith, Items carrying: 0)".
   * 
   * @return String format of this instance
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Player(Name: ");
    sb.append(this.name);
    sb.append(", Items carrying: ");
    sb.append(this.itemsOfPlayers.size());
    sb.append(")");

    return sb.toString();
  }

  /**
   * Compares equality of two player instances based on the name.
   * 
   * @param obj Instance of class Object
   * @return True if equal else false
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Player)) {
      return false;
    }

    Player that = (Player) obj;

    return this.getName().equals(that.getName());
  }

  /**
   * Generates a hash code based on the name.
   * 
   * @return An integer value which is the hash code of the object
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.name);
  }

  @Override
  public String getPlayerType() {
    return "Human";
  }
}
