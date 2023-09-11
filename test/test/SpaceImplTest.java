package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import game.Item;
import game.ItemImpl;
import game.Space;
import game.SpaceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.junit.Before;
import org.junit.Test;

/**
 * A test class for testing SpaceImpl class.
 */
public class SpaceImplTest {

  private List<Item> itemsInSpaceForTest;
  private List<Space> allSpacesForTest;

  /**
   * This is a method which is used to create new instances of SpaceImpl class.
   * 
   * @param topLeftX     X coordinate of the top left side of the space
   * @param topLeftY     Y coordinate of the top left side of the space
   * @param bottomRightX X coordinate of the bottom right side of the space
   * @param bottomRightY Y coordinate of the bottom right side of the space
   * @param name         Name of the space
   * @param itemsInSpace All the items present in the space
   * @return New instance of SpaceImpl class
   */
  private Space newSpaceInstance(int topLeftX, int topLeftY, int bottomRightX, int bottomRightY,
      String name, List<Item> itemsInSpace) {
    return new SpaceImpl(topLeftX, topLeftY, bottomRightX, bottomRightY, name, itemsInSpace);
  }

  /**
   * Setup for testing the SpaceImpl class.
   */
  @Before
  public void setUp() {
    itemsInSpaceForTest = new ArrayList<>();
    itemsInSpaceForTest.add(new ItemImpl("Pan", 10));
    itemsInSpaceForTest.add(new ItemImpl("Broom", 7));
    itemsInSpaceForTest.add(new ItemImpl("Knife", 14));

    allSpacesForTest = new ArrayList<>();
    allSpacesForTest.add(newSpaceInstance(4, 10, 11, 12, "Dining", itemsInSpaceForTest));
    allSpacesForTest.add(newSpaceInstance(4, 4, 9, 9, "Master Bedroom", itemsInSpaceForTest));
    allSpacesForTest.add(newSpaceInstance(4, 13, 6, 19, "Music Room", itemsInSpaceForTest));
    allSpacesForTest.add(newSpaceInstance(17, 0, 24, 3, "Garage", itemsInSpaceForTest));
    allSpacesForTest.add(newSpaceInstance(17, 13, 20, 19, "Bathroom", itemsInSpaceForTest));
    allSpacesForTest.add(newSpaceInstance(0, 10, 3, 18, "Home Office", itemsInSpaceForTest));
    allSpacesForTest.add(newSpaceInstance(2, 0, 16, 3, "Entrance Hall", itemsInSpaceForTest));
    allSpacesForTest.add(newSpaceInstance(0, 4, 3, 9, "Attic", itemsInSpaceForTest));
  }

  @Test
  public void testValidInputsAndToString() {
    Space testSpace = newSpaceInstance(4, 10, 11, 12, "Dining", itemsInSpaceForTest);
    assertEquals(testSpace.toString(),
        "Space(Name = Dining, Top left = (4, 10), Bottom right = (11, 12))");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTopLeftxNegative() {
    newSpaceInstance(-10, 20, 30, 32, "Dining", itemsInSpaceForTest);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTopLeftyNegative() {
    newSpaceInstance(10, -20, 30, 32, "Dining", itemsInSpaceForTest);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBottomRightxNegative() {
    newSpaceInstance(10, 20, -30, 32, "Dining", itemsInSpaceForTest);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBottomRightyNegative() {
    newSpaceInstance(10, 20, 30, -32, "Dining", itemsInSpaceForTest);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNameIsNull() {
    newSpaceInstance(10, 20, 30, 32, null, itemsInSpaceForTest);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNameIsEmptyString() {
    newSpaceInstance(10, 20, 30, 32, "", itemsInSpaceForTest);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testItemsInSpaceIsNull() {
    newSpaceInstance(10, 20, 30, 32, "Dining", null);
  }

  @Test
  public void testGetItemsInSpace() {
    Space testSpace = newSpaceInstance(4, 10, 11, 12, "Dining", itemsInSpaceForTest);

    List<Item> itemsInSpaceForTesting = new ArrayList<>();
    for (Item item : itemsInSpaceForTest) {
      itemsInSpaceForTesting.add(new ItemImpl(item.getName(), item.getDamage()));
    }

    assertTrue(testSpace.getItemsInSpace().equals(itemsInSpaceForTesting));
  }

  @Test
  public void testGetName() {
    Space testSpace = newSpaceInstance(4, 10, 11, 12, "Dining", itemsInSpaceForTest);
    assertEquals(testSpace.getName(), "Dining");
  }

  @Test
  public void testGetCoordinateMethods() {
    Space testSpace = newSpaceInstance(4, 10, 11, 12, "Dining", itemsInSpaceForTest);
    assertEquals(testSpace.getTopLeftX(), 4);
    assertEquals(testSpace.getTopLeftY(), 10);
    assertEquals(testSpace.getBottomRightX(), 11);
    assertEquals(testSpace.getBottomRightY(), 12);
  }

  @Test
  public void testEquals() {
    assertTrue(newSpaceInstance(4, 10, 11, 12, "Dining", itemsInSpaceForTest)
        .equals(newSpaceInstance(4, 10, 11, 12, "Dining", itemsInSpaceForTest)));
    assertFalse(newSpaceInstance(4, 10, 11, 12, "Dining", itemsInSpaceForTest)
        .equals(newSpaceInstance(3, 10, 11, 12, "Dining", itemsInSpaceForTest)));
    assertFalse(newSpaceInstance(4, 10, 11, 12, "Dining", itemsInSpaceForTest)
        .equals(newSpaceInstance(4, 9, 11, 12, "Dining", itemsInSpaceForTest)));
    assertFalse(newSpaceInstance(4, 10, 11, 12, "Dining", itemsInSpaceForTest)
        .equals(newSpaceInstance(4, 10, 22, 12, "Dining", itemsInSpaceForTest)));
    assertFalse(newSpaceInstance(4, 10, 11, 12, "Dining", itemsInSpaceForTest)
        .equals(newSpaceInstance(4, 10, 11, 24, "Dining", itemsInSpaceForTest)));
    assertFalse(newSpaceInstance(4, 10, 11, 12, "Dining", itemsInSpaceForTest)
        .equals(newSpaceInstance(3, 10, 11, 12, "Master Room", itemsInSpaceForTest)));
    assertFalse(newSpaceInstance(4, 10, 11, 12, "Dining", itemsInSpaceForTest)
        .equals(newSpaceInstance(3, 10, 11, 12, "Dining", new ArrayList<Item>())));
  }

  @Test
  public void testHashCode() {
    Space testSpace = newSpaceInstance(4, 10, 11, 12, "Dining", itemsInSpaceForTest);
    assertEquals(testSpace.hashCode(), Objects.hash(4, 10, 11, 12, "Dining"));
  }

  @Test
  public void testRemoveItemFromSpace() {
    Space testSpace = newSpaceInstance(4, 10, 11, 12, "Dining",
        new ArrayList<Item>(itemsInSpaceForTest));
    testSpace.removeItemFromSpace("Pan");
    List<Item> testItems = new ArrayList<>(itemsInSpaceForTest);
    testItems.remove(0);
    assertTrue(testItems.equals(testSpace.getItemsInSpace()));
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveItemWhenSpaceNameIsNull() {
    Space testSpace = newSpaceInstance(4, 10, 11, 12, "Dining",
        new ArrayList<Item>(itemsInSpaceForTest));
    testSpace.removeItemFromSpace(null);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveItemWhenNameIsEmpty() {
    Space testSpace = newSpaceInstance(4, 10, 11, 12, "Dining",
        new ArrayList<Item>(itemsInSpaceForTest));
    testSpace.removeItemFromSpace("");
  }

  @Test(expected = IllegalStateException.class)
  public void testRemoveItemWhenNameIsInvalid() {
    Space testSpace = newSpaceInstance(4, 10, 11, 12, "Dining",
        new ArrayList<Item>(itemsInSpaceForTest));
    testSpace.removeItemFromSpace("Pan12");
  }

}
