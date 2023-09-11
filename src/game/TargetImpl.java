package game;

/**
 * A generic implementaion of the Target interface which is part of the world
 * and moves around the spaces. The players attack this target character to win
 * the game.
 */
public final class TargetImpl implements Target {
  private final String name;
  private int health;
  private int currentSpaceIndex;

  /**
   * Constructs an instance of a target with the given name and health.
   * 
   * @param name   Name of the target
   * @param health Starting health of the target
   * @throws IllegalArgumentException when name is empty string or health is less
   *                                  than 1
   */
  public TargetImpl(String name, int health) throws IllegalArgumentException {

    if (name == null) {
      throw new IllegalArgumentException("Name of target cannot be null");
    }

    if (name.length() == 0) {
      throw new IllegalArgumentException("Name of the target cannot be empty string");
    }

    if (health <= 0) {
      throw new IllegalArgumentException("Health of the target cannot be zero or negative");
    }

    this.name = name;
    this.health = health;
    this.currentSpaceIndex = 0;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public int getHealth() {
    return this.health;
  }

  @Override
  public void increaseHealth(int health) throws IllegalArgumentException {
    if (health <= 0) {
      throw new IllegalArgumentException("The health to be increased should be positive");
    }
    this.health = this.health + health;
  }

  @Override
  public void decreaseHealth(int health) throws IllegalArgumentException {
    if (health <= 0) {
      throw new IllegalArgumentException("The health to be decreased should be positive");
    }

    int newHealth = this.health - health;

    if (newHealth < 0) {
      this.health = 0;
    } else {
      this.health = newHealth;
    }
  }

  /**
   * Returns a string representation of this Target in the form "Target(Name =
   * Dr. Lucky, Health = 50)".
   * 
   * @return String format of this instance
   */
  @Override
  public String toString() {
    return String.format("Target(Name = %s, Health = %d)", this.name, this.health);
  }

  @Override
  public void move(int index) {
    this.currentSpaceIndex = index;
  }

  @Override
  public int getCurrentSpaceIndex() {
    return this.currentSpaceIndex;
  }
}
