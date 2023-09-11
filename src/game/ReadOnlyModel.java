package game;

import java.util.List;

/**
 * Defines methods used by the View to read details directly from the model.
 *
 */
public interface ReadOnlyModel {

  /**
   * Fetches a list all the Players in the game. The Information of each player is
   * returned in the form of list following the order: Name, Location and Type.
   * 
   * @return the List that consists of all players' information.
   */
  public String[][] getAllPlayers();

  /**
   * Returns the information of a player like name of the player, in which space
   * he is currently present in and the items he is carrying as a string.
   * 
   * @param name Name of the player
   * @return The information of the specified player
   * @throws IllegalArgumentException when name is null or an empty string or when
   *                                  player does not exist in the world
   */
  public String getPlayerInfo(String name) throws IllegalArgumentException;

  /**
   * Returns the name, type and space of the current turn player and the target's
   * current space name as a string in the format:
   * Name,Type,SpaceName,TargetSpaceName.
   * 
   * @return A string in the format: Name,Type,SpaceName,TargetSpaceName
   */
  public List<String> getTurnInfo();

  /**
   * Generates a List that contains names of all the spaces that can be used while
   * prompting user to select a landing location.
   * 
   * @return a list that contains all the space names.
   */
  public String[] getAllSpaces();

  /**
   * Generates a List of all the coordinates of a given space in the following
   * order, top-left-x, top-left-y, bottom-right-x, bottom-right-y.
   * 
   * @param spaceName Name of the space whose coordinates are required.
   * 
   * @return a list that contains 4 coordinates of the space.
   */
  public int[] getCoordinates(String spaceName);

  /**
   * Generates a List of all the items present in the current turn player's space.
   * 
   * @return a list of items in the space.
   */
  public String[] getCurrentSpaceItems();

  /**
   * Generates a List of all the items present with the current turn player.
   * 
   * @return a list of items with the player.
   */
  public String[] getCurrentPlayerItems();

  /**
   * Gets the name of Space in which the pet is currently present in.
   * 
   * @return Name of the Space in which the pet is present
   */
  public String getCurrentSpaceOfPet();

}
