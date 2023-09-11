package controllertest;

import static org.junit.Assert.assertEquals;

import controller.GameCommand;
import controller.IsGameOver;
import game.GameWorld;
import org.junit.Test;

/**
 * A test class for testing LookAround command class.
 */
public class IsGameOverTest {

  @Test
  public void testOutput() {
    GameCommand cmd = new IsGameOver();
    StringBuffer log = new StringBuffer();
    GameWorld model = new MockGameWorld(log, "18109891");

    cmd.execute(model);

    assertEquals("Mock for game over, Code: 18109891", cmd.getOutput());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWhenModelIsNull() {
    GameCommand cmd = new IsGameOver();
    cmd.execute(null);
  }

}
