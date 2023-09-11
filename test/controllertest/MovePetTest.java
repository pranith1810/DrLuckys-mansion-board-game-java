package controllertest;

import static org.junit.Assert.assertEquals;

import controller.GameCommand;
import controller.MovePet;
import game.GameWorld;
import org.junit.Test;

/**
 * A test class for testing MovePet command class.
 */
public class MovePetTest {

  @Test
  public void testInputAndOutput() {
    GameCommand cmd = new MovePet("Dining");
    StringBuffer log = new StringBuffer();
    GameWorld model = new MockGameWorld(log, "18109893");

    cmd.execute(model);

    assertEquals("Input: Dining", log.toString());
    assertEquals("Player has moved the pet to the space Dining, code: 18109893\n", cmd.getOutput());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWhenSpaceNameIsNull() {
    new MovePet(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWhenSpaceNameIsEmpty() {
    new MovePet("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWhenModelIsNull() {
    GameCommand cmd = new MovePet("Dining");
    cmd.execute(null);
  }

}
