package controller;

import game.GameWorld;

/**
 * A class which is used as a command to execute the corresponding method in
 * GameWorld model to pick an item by a player.
 */
public class PickItem implements GameCommand {

  private final String itemName;
  private String output;

  /**
   * A constructor to create instance of the class with the specified item name.
   * 
   * @param itemName The name of the item to be picked
   * @throws IllegalArgumentException When itemName is null or empty string
   */
  public PickItem(String itemName) throws IllegalArgumentException {
    if (itemName == null) {
      throw new IllegalArgumentException("Item name cannot be null");
    }

    if (itemName.length() == 0) {
      throw new IllegalArgumentException("Item name cannot be empty string");
    }

    this.itemName = itemName;
    this.output = "";
  }

  @Override
  public void execute(GameWorld model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("GameWorld or Appendable cannot be null");
    }

    this.output = model.pickItemByPlayer(itemName);
  }

  @Override
  public String getOutput() {
    return this.output;
  }

}
