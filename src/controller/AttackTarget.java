package controller;

import game.GameWorld;

/**
 * A class which is used as a command to execute the corresponding method in
 * GameWorld model to attack the target.
 */
public class AttackTarget implements GameCommand {

  private final String itemName;
  private String output;

  /**
   * A constructor to create instance of the class with the specified item name to
   * attack the target.
   * 
   * @param itemName The name of the item to be used for the attack
   * @throws IllegalArgumentException When itemName is null or empty string
   */
  public AttackTarget(String itemName) throws IllegalArgumentException {
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

    this.output = model.attackTarget(itemName);
  }

  @Override
  public String getOutput() {
    return this.output;
  }
}
