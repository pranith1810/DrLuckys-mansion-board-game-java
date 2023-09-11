package controllertest;

import static org.junit.Assert.assertEquals;

import controller.GameCommand;
import controller.IsCurrentComputer;
import game.GameWorld;
import org.junit.Test;

/**
 * A test class for testing IsCurrentComputer command class.
 */
public class IsCurrentComputerTest {

  @Test
  public void testOutput() {
    GameCommand cmd = new IsCurrentComputer();
    StringBuffer log = new StringBuffer();
    GameWorld model = new MockGameWorld(log, "18109891");

    cmd.execute(model);

    assertEquals("Human, Code: 18109891", cmd.getOutput());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWhenModelIsNull() {
    GameCommand cmd = new IsCurrentComputer();
    cmd.execute(null);
  }

}
