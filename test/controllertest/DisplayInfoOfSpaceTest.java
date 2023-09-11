package controllertest;

import static org.junit.Assert.assertEquals;

import controller.DisplayInfoOfSpace;
import controller.GameCommand;
import game.GameWorld;
import org.junit.Test;

/**
 * A class for testing the DisplayInfoOfSpace command class.
 */
public class DisplayInfoOfSpaceTest {

  @Test
  public void testInputAndOutput() {
    GameCommand cmd = new DisplayInfoOfSpace("Dining");
    StringBuffer log = new StringBuffer();
    GameWorld model = new MockGameWorld(log, "1810981");

    cmd.execute(model);

    assertEquals("Input: Dining", log.toString());
    assertEquals("Output: Result for Dining as input, code: 1810981\n", cmd.getOutput());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWhenModelIsNull() {
    GameCommand cmd = new DisplayInfoOfSpace("Dining");

    cmd.execute(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWhenSpaceNameIsNull() {
    new DisplayInfoOfSpace(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWhenSpaceNameIsEmpty() {
    new DisplayInfoOfSpace("");
  }

}
