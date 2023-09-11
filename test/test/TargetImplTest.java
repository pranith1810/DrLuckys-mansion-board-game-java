package test;

import static org.junit.Assert.assertEquals;

import game.Target;
import game.TargetImpl;
import org.junit.Test;

/**
 * A test class for testing TargetImpl class.
 */
public class TargetImplTest {
  
  /**
   * This is a method which is used to create new instances of TargetImpl class.
   * 
   * @param name Name of the target
   * @param health Starting health of the target
   * @return New instance of TargetImpl class
   */
  private Target newTargetInstance(String name, int health) {
    return new TargetImpl(name, health);
  }

  @Test
  public void testValidInputsAndToString() {
    Target lucky = newTargetInstance("Dr. Lucky", 50);
    assertEquals("Target(Name = Dr. Lucky, Health = 50)", lucky.toString());
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testNameIsNull() {
    newTargetInstance(null, 50);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testNameIsEmptyString() {
    newTargetInstance("", 50);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testHealthIsZero() {
    newTargetInstance("Dr. Lucky", 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testHealthIsNegative() {
    newTargetInstance("Dr. Lucky", -10);
  }
  
  @Test
  public void testGetName() {
    Target testTargetSingleCharacter = newTargetInstance("a", 50);
    assertEquals(testTargetSingleCharacter.getName(), "a");
    
    Target testTargetMultipleCharacters = newTargetInstance("Dr. Lucky", 20);
    assertEquals(testTargetMultipleCharacters.getName(), "Dr. Lucky");
  }
  
  @Test
  public void testGetHealth() {
    Target testTargetForHealth = newTargetInstance("a", 50);
    assertEquals(testTargetForHealth.getHealth(), 50);
  }
  
  @Test
  public void testIncreaseHealth() {
    Target testTargetForIncreaseHealth = newTargetInstance("a", 50);
    testTargetForIncreaseHealth.increaseHealth(2);
    assertEquals(testTargetForIncreaseHealth.getHealth(), 52);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testIncreaseHealthIfNegative() {
    Target testTargetForIncreaseHealthNegative = newTargetInstance("a", 50);
    testTargetForIncreaseHealthNegative.increaseHealth(-2);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testIncreaseHealthIfZero() {
    Target testTargetForIncreaseHealthZero = newTargetInstance("a", 50);
    testTargetForIncreaseHealthZero.increaseHealth(0);
  }
  
  @Test
  public void testDecreaseHealth() {
    Target testTargetForDecreaseHealth = newTargetInstance("a", 50);
    testTargetForDecreaseHealth.decreaseHealth(2);
    assertEquals(testTargetForDecreaseHealth.getHealth(), 48);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testDecreaseHealthIfNegative() {
    Target testTargetForDecreaseHealthNegative = newTargetInstance("a", 50);
    testTargetForDecreaseHealthNegative.decreaseHealth(-2);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testDecreaseHealthIfZero() {
    Target testTargetForDecreaseHealthZero = newTargetInstance("a", 50);
    testTargetForDecreaseHealthZero.decreaseHealth(0);
  }
  
  @Test
  public void testMoveAndGetCurrentSpaceIndex() {
    Target lucky = newTargetInstance("Dr. Lucky", 50);
    assertEquals(lucky.getCurrentSpaceIndex(), 0);
    
    lucky.move(5);
    assertEquals(lucky.getCurrentSpaceIndex(), 5);
    
  }

}
