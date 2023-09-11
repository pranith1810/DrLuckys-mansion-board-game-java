package game;

import utils.RandomManual;

/**
 * A class representing a player which is controlled by the computer and chooses
 * random actions to perform.
 */
public class ComputerPlayer extends PlayerImpl {

  /**
   * A constructor which is used to construct the instance of a Computer player.
   * 
   * @param name              Name of the computer player
   * @param currentSpaceIndex The index of the space the computer player should
   *                          start from
   */
  public ComputerPlayer(String name, int currentSpaceIndex) {
    super(name, currentSpaceIndex);
  }

  @Override
  public int chooseAction(RandomManual random, int moduloOn) throws IllegalArgumentException {
    if (random == null) {
      throw new IllegalArgumentException("Random instance cannot be null");
    }

    if (moduloOn < 0) {
      throw new IllegalArgumentException("moduloOn cannot be negative");
    }

    if (moduloOn == 0 || random.isPredictable()) {
      return random.getNextNumber();
    }
    int value = random.getNextNumber() % moduloOn;
    return value;
  }

  @Override
  public String getPlayerType() {
    return "Computer";
  }

}
