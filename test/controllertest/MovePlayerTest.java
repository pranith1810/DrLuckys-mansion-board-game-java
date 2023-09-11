package controllertest;

import static org.junit.Assert.assertEquals;

import controller.GameCommand;
import controller.MovePlayer;
import game.GameWorld;
import org.junit.Test;

/**
 * A test class for testing MovePlayer command class.
 */
public class MovePlayerTest {

  @Test
  public void testInputAndOutput() {
    GameCommand cmd = new MovePlayer(80, 90);
    StringBuffer log = new StringBuffer();
    GameWorld model = new MockGameWorld(log, "1810985");

    cmd.execute(model);

    assertEquals("Input: 80, 90", log.toString());
    assertEquals("Mock result for move player, Code: 1810985", cmd.getOutput());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWhenSpaceIsInvalid() {
    new MovePlayer(-90, -90);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWhenModelIsNull() {
    GameCommand cmd = new MovePlayer(90, 90);
    cmd.execute(null);
  }

}
