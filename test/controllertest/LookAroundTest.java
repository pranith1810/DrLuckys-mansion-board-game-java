package controllertest;

import static org.junit.Assert.assertEquals;

import controller.GameCommand;
import controller.LookAround;
import game.GameWorld;
import org.junit.Test;

/**
 * A test class for testing LookAround command class.
 */
public class LookAroundTest {

  @Test
  public void testOutput() {
    GameCommand cmd = new LookAround();
    StringBuffer log = new StringBuffer();
    GameWorld model = new MockGameWorld(log, "1810984");

    cmd.execute(model);

    assertEquals("Output: Mock result for look around, code: 1810984\n", cmd.getOutput());
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testWhenModelIsNull() {
    GameCommand cmd = new LookAround();
    cmd.execute(null);
  }

}
