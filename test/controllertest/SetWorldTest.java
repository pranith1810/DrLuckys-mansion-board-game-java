package controllertest;

import static org.junit.Assert.assertEquals;

import controller.GameCommand;
import controller.SetWorld;
import game.GameWorld;
import java.io.StringReader;
import org.junit.Test;

/**
 * A test class for testing SetWorld command class.
 */
public class SetWorldTest {

  @Test
  public void testInputAndOutput() {
    Readable testReadable = new StringReader("World secififctaions");
    GameCommand cmd = new SetWorld(testReadable);
    StringBuffer log = new StringBuffer();
    GameWorld model = new MockGameWorld(log, "1810943");

    cmd.execute(model);

    assertEquals("Mock input for world, Code:1810943", log.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWhenWorldReadableIsNull() {
    new SetWorld(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWhenModelIsNull() {
    GameCommand cmd = new SetWorld(new StringReader(""));

    cmd.execute(null);
  }

}
