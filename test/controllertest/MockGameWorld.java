package controllertest;

import game.GameWorld;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * A class which represents a mock model of GameWorld model used for testing the
 * controller.
 */
public class MockGameWorld implements GameWorld {
  private StringBuffer log;
  private String code;

  /**
   * A contructor which constructs the instance of MockGameWorld which represents
   * a mock GameWorld model used for testing purposes.
   * 
   * @param log  The log to append the input received by the mock methods of the
   *             mock model class
   * @param code A code which is used to verify if the mock class instance is the
   *             same one as expected
   */
  public MockGameWorld(StringBuffer log, String code) {
    this.log = log;
    this.code = code;
  }

  @Override
  public String getInfoOfaSpace(String spaceName) {
    log.append("Input: " + spaceName);
    return String.format("Output: Result for %s as input, code: %s\n", spaceName, code);
  }

  @Override
  public String drawImage() {
    return "Draw image output\n";
  }

  @Override
  public void addPlayer(String name, String startingSpaceName, boolean isHuman) {
    log.append(String.format("Received input: (Name: %s, Starting space: %s, isHuman: %b)\n", name,
        startingSpaceName, isHuman));
  }

  @Override
  public String getPlayerInfo(String name) throws IllegalArgumentException {
    log.append("Input: " + name);
    return String.format("Output: Result for %s as input, code: %s\n", name, code);
  }

  @Override
  public String pickItemByPlayer(String itemName) {
    log.append("Input: " + itemName);
    return String.format("Player has picked up the Item %s successfully, code: %s\n", itemName,
        code);
  }

  @Override
  public String lookAroundByPlayer() {
    return "Output: Mock result for look around, code: " + code + "\n";
  }

  @Override
  public String performComputerAction() {
    return "Performed random computer action, code: " + code + "\n";
  }

  @Override
  public String movePetByPlayer(String spaceName) throws IllegalArgumentException {
    log.append("Input: " + spaceName);
    return String.format("Player has moved the pet to the space %s, code: %s\n", spaceName, code);
  }

  @Override
  public String attackTarget(String itemName) throws IllegalArgumentException {
    log.append("Input: " + itemName);
    if ("winningItem".equals(itemName)) {
      return String.format("Target died! Player has won the game, code: %s\n", code);
    }
    return String.format("Player has attacked the target with the item %s, code: %s\n", itemName,
        code);
  }

  @Override
  public String isGameOver() {
    return "Mock for game over, Code: " + code;
  }

  @Override
  public String[][] getAllPlayers() {
    String[][] mockResult = { { "Mock result for all players, Code: " + code } };
    return mockResult;
  }

  @Override
  public List<String> getTurnInfo() {
    List<String> mockResult = new ArrayList<String>();
    mockResult.add("Mock result for get turn info, Code: " + code);
    mockResult.add("Human, Code: " + code);
    mockResult.add("Mock player space, Code: " + code);
    mockResult.add("Mock target space, Code: " + code);
    return mockResult;
  }

  @Override
  public String[] getAllSpaces() {
    String[] mockResult = { "Mock result for get all spaces, Code: " + code };
    return mockResult;
  }

  @Override
  public int[] getCoordinates(String spaceName) {
    log.append("Input: " + spaceName);
    int[] mockResult = { 0, 0, 0, 0 };
    return mockResult;
  }

  @Override
  public String[] getCurrentSpaceItems() {
    String[] mockResult = { "Mock result for get current space items, Code: " + code };
    return mockResult;
  }

  @Override
  public String[] getCurrentPlayerItems() {
    String[] mockResult = { "Mock result for get current player items, Code: " + code };
    return mockResult;
  }

  @Override
  public String getCurrentSpaceOfPet() {
    return "Mock result for get current space of pet, Code: " + code;
  }

  @Override
  public void setWorldSpecification(Readable worldData)
      throws IllegalArgumentException, NoSuchElementException, InputMismatchException {
    log.append("Mock input for world, Code:" + code);
  }

  @Override
  public String movePlayerInWorld(int xcoord, int ycoord) throws IllegalArgumentException {
    log.append("Input: " + xcoord + ", " + ycoord);
    return "Mock result for move player, Code: " + code;
  }

  @Override
  public int getNumOfTurns() {
    return Integer.parseInt(code);
  }

}
