package test;

import static org.junit.Assert.assertEquals;

import game.ComputerPlayer;
import game.Player;
import org.junit.Test;
import utils.RandomManual;

/**
 * A test class for testing ComputerPlayer class.
 */
public class ComputerPlayerTest {

  @Test
  public void getPlayerType() {
    Player testPlayer = new ComputerPlayer("Pan", 10);
    assertEquals("Computer", testPlayer.getPlayerType());
  }

  @Test
  public void testChooseAction() {
    Player testPlayer = new ComputerPlayer("Pan", 10);
    RandomManual random = new RandomManual(2, 3, 4, 5, 6);

    assertEquals(2, testPlayer.chooseAction(random, 0));
    assertEquals(3, testPlayer.chooseAction(random, 0));
    assertEquals(4, testPlayer.chooseAction(random, 0));
    assertEquals(5, testPlayer.chooseAction(random, 0));
    assertEquals(6, testPlayer.chooseAction(random, 0));
    assertEquals(2, testPlayer.chooseAction(random, 0));
  }
}
