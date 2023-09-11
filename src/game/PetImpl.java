package game;

/**
 * A generic implementation of the Pet interface which represents a target's Pet
 * that moves around the world.
 */
public class PetImpl implements Pet {
  private final String name;
  private int currentSpaceIndex;

  /**
   * A constructor of the PetImpl class to create an instance of the target's pet
   * with the given name and starting space.
   * 
   * @param name The name of the Pet
   */
  public PetImpl(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Pet name cannot be null");
    }

    if (name.length() == 0) {
      throw new IllegalArgumentException("Pet name cannot be an empty string");
    }

    this.name = name;
    this.currentSpaceIndex = 0;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public int getCurrentSpaceIndex() {
    return this.currentSpaceIndex;
  }

  @Override
  public void movePet(int index) {
    if (index < 0) {
      throw new IllegalArgumentException("Space index cannot be negative");
    }

    this.currentSpaceIndex = index;
  }

  /**
   * Returns a string representation of this Pet in the form "Pet(Name: Simba)".
   * 
   * @return String format of this Pet instance
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Pet(Name: ");
    sb.append(this.name);
    sb.append(")");

    return sb.toString();
  }

}
