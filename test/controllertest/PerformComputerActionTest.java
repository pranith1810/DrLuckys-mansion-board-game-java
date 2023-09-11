package controllertest;

import static org.junit.Assert.assertEquals;

import controller.GameCommand;
import controller.PerformComputerAction;
import game.GameWorld;
import org.junit.Test;

/**
 * A test class for testing PerformComputerAction command class.
 */
public class PerformComputerActionTest {

  @Test
  public void testOutput() {
    GameCommand cmd = new PerformComputerAction();
    StringBuffer log = new StringBuffer();
    GameWorld model = new MockGameWorld(log, "181098090");

    cmd.execute(model);

    assertEquals("Performed random computer action, code: 181098090\n", cmd.getOutput());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWhenModelIsNull() {
    GameCommand cmd = new PerformComputerAction();
    cmd.execute(null);
  }

}
