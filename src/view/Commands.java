package view;

/**
 * Holds the Names of all the Commands present.
 *
 */
public enum Commands {
  ATTACK_TARGET("a"), MOVE_PET("m"), LOOK_AROUND("l"), PICKUP_ITEM("p");

  private final String name;

  private Commands(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }

}
