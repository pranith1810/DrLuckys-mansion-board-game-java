package controllertest;

import game.GameWorld;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * A class which represents a mock model of GameWorld model used for testing
 * exception handling of the controller..
 */
public class MockGameWorldExceptions implements GameWorld {

  @Override
  public String getInfoOfaSpace(String spaceName) {
    if (!"valid".equals(spaceName)) {
      throw new IllegalArgumentException(
          "Test of how controller handles DisplayInfoOfSpace exceptions.");
    }
    return "";
  }

  @Override
  public String drawImage() {
    return "Draw image output\n";
  }

  @Override
  public void addPlayer(String name, String startingSpaceName, boolean isHuman) {
    if (!name.contains("valid")) {
      throw new IllegalArgumentException("Test of how controller handles AddPlayer exceptions.");
    }
  }

  @Override
  public String getPlayerInfo(String name) throws IllegalArgumentException {
    if (!name.contains("valid")) {
      throw new IllegalArgumentException(
          "Test of how controller handles GetPlayerInfo exceptions.");
    }
    return "";
  }

  @Override
  public String pickItemByPlayer(String itemName) {
    if ("stateException".equals(itemName)) {
      throw new IllegalStateException(
          "Test of how controller handles PickItem illegal state exceptions.");
    } else {
      throw new IllegalArgumentException(
          "Test of how controller handles PickItem illegal argument exceptions.");
    }
  }

  @Override
  public String lookAroundByPlayer() {
    return "";
  }

  @Override
  public String performComputerAction() {
    return "";
  }

  @Override
  public String movePetByPlayer(String spaceName) throws IllegalArgumentException {
    if (!"valid".equals(spaceName)) {
      throw new IllegalArgumentException("Test of how controller handles MovePet exceptions.");
    }
    return "";
  }

  @Override
  public String attackTarget(String itemName) throws IllegalArgumentException {
    throw new IllegalArgumentException(
        "Test of how controller handles AttackTarget's illegal argument exceptions.");
  }

  @Override
  public String[][] getAllPlayers() {
    return new String[1][1];
  }

  @Override
  public List<String> getTurnInfo() {
    List<String> mockResult = new ArrayList<String>();
    mockResult.add("Mock name");
    mockResult.add("Human, Code: ");
    mockResult.add("Mock player space, Code: ");
    mockResult.add("Mock target space, Code: ");
    return mockResult;
  }

  @Override
  public String[] getAllSpaces() {
    return new String[2];
  }

  @Override
  public int[] getCoordinates(String spaceName) {
    throw new IllegalArgumentException("Test of how controller handles getCoordinates exceptions");
  }

  @Override
  public String[] getCurrentSpaceItems() {
    return new String[2];
  }

  @Override
  public String[] getCurrentPlayerItems() {
    return new String[2];
  }

  @Override
  public String getCurrentSpaceOfPet() {
    return "";
  }

  @Override
  public void setWorldSpecification(Readable worldData)
      throws IllegalArgumentException, NoSuchElementException, InputMismatchException {
    if (worldData.toString().contains("illegalArg")) {
      throw new IllegalArgumentException("Test for how controller handles illegal "
          + "argument exceptions from setWorldSpecififcation");
    }
    if (worldData.toString().contains("illegalArg")) {
      throw new InputMismatchException("Test for how controller handles input "
          + "mismatch exceptions from setWorldSpecififcation");
    }
    throw new NoSuchElementException("Test for how controller handles no "
        + "such element exceptions from setWorldSpecififcation");
  }

  @Override
  public String movePlayerInWorld(int xcoord, int ycoord) throws IllegalArgumentException {
    throw new IllegalArgumentException(
        "Test for how controller handle movePlayerInWorld exception");
  }

  @Override
  public String isGameOver() {
    return "";
  }

  @Override
  public int getNumOfTurns() {
    return 0;
  }

}
