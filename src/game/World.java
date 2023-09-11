package game;

/**
 * The world is made up of spaces on a number of rows and columns and contains a
 * target. It basically represents the map of the game.
 */
public interface World extends GameWorld {

  /**
   * Gets the number of rows of spaces the current instance of the world contains.
   * 
   * @return number of rows
   */
  public int getRows();

  /**
   * Gets the number of columns of spaces the current instance of the world
   * contains.
   * 
   * @return number of columns
   */
  public int getColumns();

  /**
   * Gets the name of this current world instance.
   * 
   * @return name of the world
   */
  public String getName();

  /**
   * Get all the neighbouring spaces of this space as a string.
   *
   * @param spaceName Name of the space whose neighbors are to be returned
   * @return String representation of neighbors of the the given space
   * @throws IllegalArgumentException If space name is null or empty
   */
  public String getNeighbours(String spaceName) throws IllegalArgumentException;

  /**
   * Gets the name of Space in which the target is currently present in.
   * 
   * @return Name of the Space in which the target is present
   */
  public String getCurrentSpaceOfTarget();

  /**
   * Determines whether the first player name can see the second player name.
   * 
   * @param firstPlayername  The name of the first player
   * @param secondPlayerName The name of the second player
   * @return True/False based on if the players can see each other
   * @throws IllegalArgumentException If names are null or empty or if the given
   *                                  players do not exist in the world
   */
  public boolean isPlayerVisible(String firstPlayername, String secondPlayerName)
      throws IllegalArgumentException;

}
