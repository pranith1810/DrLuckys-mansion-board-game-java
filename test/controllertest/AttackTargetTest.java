package controllertest;

import static org.junit.Assert.assertEquals;

import controller.AttackTarget;
import controller.GameCommand;
import game.GameWorld;
import org.junit.Test;

/**
 * A test class for testing AttackTarget command class.
 */
public class AttackTargetTest {

  @Test
  public void testInputAndOutput() {
    GameCommand cmd = new AttackTarget("Broom");
    StringBuffer log = new StringBuffer();
    GameWorld model = new MockGameWorld(log, "18109892");

    cmd.execute(model);

    assertEquals("Input: Broom", log.toString());
    assertEquals("Player has attacked the target with the item Broom, code: 18109892\n",
        cmd.getOutput());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWhenItemNameIsNull() {
    new AttackTarget(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWhenPlayerNameIsEmpty() {
    new AttackTarget("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWhenModelIsNull() {
    GameCommand cmd = new AttackTarget("Pan");

    cmd.execute(null);
  }
}
