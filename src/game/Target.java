package game;

/**
 * The Target character which will be part of the world. The target is the one
 * the players attack.
 */
public interface Target {

  /**
   * Get the name of the  current target character instance.
   *
   * @return the name of the target
   */
  public String getName();

  /**
   * Get the current health of the target character.
   *
   * @return the current health
   */
  public int getHealth();

  /**
   * Increase the health of the target character.
   * 
   * @param health Number of health points to increase.
   * @throws IllegalArgumentException when health is zero or negative
   */
  public void increaseHealth(int health);

  /**
   * Decrease the health of the target character.
   *
   * @param health Number of health points to decrease. Lowest possible health is
   *               0.
   * @throws IllegalArgumentException when health is zero or negative
   */
  public void decreaseHealth(int health);

  /**
   * Moves the target to the next space in order.
   * 
   * @param index The index of the space to move the target to.
   */
  public void move(int index);
  
  /**
   * Return the index of the space the target is currently present in.
   * 
   * @return index The index of the space the target is currently present in.
   */
  public int getCurrentSpaceIndex();

}
