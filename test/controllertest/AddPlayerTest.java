package controllertest;

import static org.junit.Assert.assertEquals;

import controller.AddPlayer;
import controller.GameCommand;
import game.GameWorld;
import org.junit.Test;

/**
 * A class for testing the AddPlayer command class.
 */
public class AddPlayerTest {

  @Test
  public void testInputForHumanPlayer() {
    GameCommand cmd = new AddPlayer("Pranith", "Dining", true);
    StringBuffer log = new StringBuffer();
    GameWorld model = new MockGameWorld(log, "18109800");

    cmd.execute(model);

    assertEquals("Received input: (Name: Pranith, Starting space: Dining, isHuman: true)\n",
        log.toString());
  }

  @Test
  public void testInputForComputerPlayer() {
    GameCommand cmd = new AddPlayer("Robot", "Dining", false);
    StringBuffer log = new StringBuffer();
    GameWorld model = new MockGameWorld(log, "18109801");

    cmd.execute(model);

    assertEquals("Received input: (Name: Robot, Starting space: Dining, isHuman: false)\n",
        log.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWhenModelIsNull() {
    GameCommand cmd = new AddPlayer("Pranith", "Dining", true);
    cmd.execute(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWhenPlayerNameIsNull() {
    new AddPlayer(null, "Dining", true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWhenPlayerNameIsEmpty() {
    new AddPlayer("", "Dining", true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWhenSpaceNameIsNull() {
    new AddPlayer("Pranith", null, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWhenSpaceNameIsEmpty() {
    new AddPlayer("Pranith", "", true);
  }

}
