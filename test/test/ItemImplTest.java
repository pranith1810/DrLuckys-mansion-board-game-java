package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import game.Item;
import game.ItemImpl;
import java.util.Objects;
import org.junit.Test;

/**
 * A test class for testing ItemImpl class.
 */
public class ItemImplTest {

  /**
   * This is a method which is used to create new instances of ItemImpl class.
   * 
   * @param name   Name of the item
   * @param damage Damage caused by the item
   * @return New instance of ItemImpl class
   */
  private Item newItemInstance(String name, int damage) {
    return new ItemImpl(name, damage);
  }

  @Test
  public void testValidInputsAndToString() {
    Item pan = newItemInstance("Pan", 10);
    assertEquals(pan.toString(), "Item(Name = Pan, Damage = 10)");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNameIsNull() {
    newItemInstance(null, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNameIsEmptyString() {
    newItemInstance("", 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDamageIsZero() {
    newItemInstance("Pan", 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDamageIsNegative() {
    newItemInstance("Pan", -10);
  }

  @Test
  public void testGetName() {
    Item testItem = newItemInstance("Pan", 10);
    assertEquals(testItem.getName(), "Pan");
  }

  @Test
  public void testGetDamage() {
    Item testItem = newItemInstance("Pan", 10);
    assertEquals(testItem.getDamage(), 10);
  }

  @Test
  public void testEquals() {
    // Same name and damage
    Item testItem = newItemInstance("Pan", 10);
    Item testItemTwo = newItemInstance("Pan", 10);
    assertTrue(testItem.equals(testItemTwo));

    // Different name same damage
    Item testItemThree = newItemInstance("Broom", 10);
    assertFalse(testItem.equals(testItemThree));

    // Different damage same name
    Item testItemFour = newItemInstance("Pan", 20);
    assertFalse(testItem.equals(testItemFour));

  }

  @Test
  public void testHashCode() {
    Item testItem = newItemInstance("Pan", 10);
    assertEquals(testItem.hashCode(), Objects.hash("Pan", 10));
  }

}