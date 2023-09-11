package game;

/**
 * A target's pet roams around the world and makes the spaces invisible to the
 * players. The players can move the pet from one space to another during their
 * turn.
 */
public interface Pet {

  /**
   * Get the name of the current pet instance.
   * 
   * @return The name of the Pet.
   */
  public String getName();

  /**
   * Gets the index of the space the pet is currently present in.
   * 
   * @return The index of the space the pet is in.
   */
  public int getCurrentSpaceIndex();

  /**
   * Moves the pet to the specified space index.
   * 
   * @param index The index of the space the pet should be moved to.
   */
  public void movePet(int index);

}
