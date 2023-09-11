package game;

import java.util.Objects;

/**
 * A generic implementation of the Item interface, which is present in the game
 * and is used to decrease the health of the target.
 */
public final class ItemImpl implements Item {
  private final String name;
  private final int damage;

  /**
   * Constructs an instance of ItemImpl which has a name and damage.
   * 
   * @param name   Name of the item
   * @param damage Damage of health points caused by the item
   * @throws IllegalArgumentException If name is empty string or damage is zero or
   *                                  negative number
   */
  public ItemImpl(String name, int damage) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("Name of the item cannot be null");
    }

    if (name.length() == 0) {
      throw new IllegalArgumentException("Name of the item cannot be empty string");
    }

    if (damage <= 0) {
      throw new IllegalArgumentException("Damage of the item cannot be zero or negative");
    }
    this.name = name;
    this.damage = damage;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public int getDamage() {
    return this.damage;
  }

  /**
   * Returns a string representation of this Item in the form "Item(Name = Pan,
   * Damage = 10)".
   * 
   * @return String format of this instance
   */
  @Override
  public String toString() {
    return String.format("Item(Name = %s, Damage = %d)", this.name, this.damage);
  }

  /**
   * Compares equality of two item instances based on the name and damage.
   * 
   * @param obj Instance of class Object
   * @return True if equal else false
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof ItemImpl)) {
      return false;
    }

    ItemImpl that = (ItemImpl) obj;

    return this.name.equals(that.name) && this.damage == that.damage;
  }

  /**
   * Generates a hash code based on the name and damage.
   * 
   * @return An integer value which is the hash code of the object
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.name, this.damage);
  }

}
