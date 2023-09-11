package controllertest;

import static org.junit.Assert.assertEquals;

import controller.GameCommand;
import controller.PickItem;
import game.GameWorld;
import org.junit.Test;

/**
 * A test class for testing PickItem command class.
 */
public class PickItemTest {

  @Test
  public void testInputAndOutput() {
    GameCommand cmd = new PickItem("Pan");
    StringBuffer log = new StringBuffer();
    GameWorld model = new MockGameWorld(log, "1810986");

    cmd.execute(model);

    assertEquals("Input: Pan", log.toString());
    assertEquals("Player has picked up the Item Pan successfully, code: 1810986\n",
        cmd.getOutput());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWhenItemNameIsNull() {
    new PickItem(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWhenPlayerNameIsEmpty() {
    new PickItem("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWhenModelIsNull() {
    GameCommand cmd = new PickItem("Pan");

    cmd.execute(null);
  }

}
