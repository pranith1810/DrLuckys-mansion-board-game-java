package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import game.Item;
import game.ItemImpl;
import game.Space;
import game.SpaceImpl;
import game.World;
import game.WorldImpl;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;
import utils.RandomManual;

/**
 * A test class for testing WorldImpl class.
 */
public class WorldImplTest {

  private Readable worldData;
  private StringBuilder worldSb;
  private List<Space> allSpacesForTest;
  private int scaleFactor;
  private int buffer;

  /**
   * Setup for testing the SpaceImpl class.
   */
  @Before
  public void setup() {
    List<Item> garageItems = new ArrayList<Item>();
    garageItems.add(new ItemImpl("Pan", 10));

    List<Item> bathroomItems = new ArrayList<Item>();
    bathroomItems.add(new ItemImpl("Broom", 7));

    List<Item> homeOfficeItems = new ArrayList<Item>();
    homeOfficeItems.add(new ItemImpl("Knife", 15));

    allSpacesForTest = new ArrayList<>();
    allSpacesForTest.add(newSpaceInstance(4, 10, 11, 12, "Dining", new ArrayList<Item>()));
    allSpacesForTest.add(newSpaceInstance(4, 4, 9, 9, "Master Bedroom", new ArrayList<Item>()));
    allSpacesForTest.add(newSpaceInstance(4, 13, 6, 19, "Music Room", new ArrayList<Item>()));
    allSpacesForTest.add(newSpaceInstance(17, 0, 24, 3, "Garage", garageItems));
    allSpacesForTest.add(newSpaceInstance(17, 13, 20, 19, "Bathroom", bathroomItems));
    allSpacesForTest.add(newSpaceInstance(0, 10, 3, 18, "Home Office", homeOfficeItems));
    allSpacesForTest.add(newSpaceInstance(2, 0, 16, 3, "Entrance Hall", new ArrayList<Item>()));
    allSpacesForTest.add(newSpaceInstance(0, 4, 3, 9, "Attic", new ArrayList<Item>()));
    this.scaleFactor = 20;
    this.buffer = 30;
  }

  /**
   * This is a method which is used to create new instances of WorldImpl class. *
   * 
   * @param data   World specification string taken as a Readable type
   * @param random RandomManual instance used to generate a random number
   * @return New instance of WorldImpl class
   */
  private World newWorldInstance(Readable data, RandomManual random) {
    return new WorldImpl(data, random, 50);
  }

  /**
   * This is a method which is used to create new instances of WorldImpl class. *
   * 
   * @param data       World specification string taken as a Readable type
   * @param random     RandomManual instance used to generate a random number
   * @param numOfTurns The number of turns in the game
   * @return New instance of WorldImpl class
   */
  private World newWorldInstanceWithTurns(Readable data, RandomManual random, int numOfTurns) {
    return new WorldImpl(data, random, numOfTurns);
  }

  /**
   * Gives the scaled value of the given coordinate.
   * 
   * @param coord The coordinate to be scaled
   * @return The scaled version of the given coordinate
   */
  private int getScaledCoord(int coord) {
    return coord * scaleFactor + buffer;
  }

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

  @Test
  public void testValidInputsAndToString() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);
    assertEquals(testWorld.toString(), "World(rows = 35, columns = 32, name = My World, "
        + "Target name = Dr. Lucky, Number of spaces = 8)");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testZeroRows() {
    worldSb = new StringBuilder("0 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    newWorldInstance(worldData, random);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testZeroColumns() {
    worldSb = new StringBuilder("35 0 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    newWorldInstance(worldData, random);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeRows() {
    worldSb = new StringBuilder("-1 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    newWorldInstance(worldData, random);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeColumns() {
    worldSb = new StringBuilder("35 -2 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    newWorldInstance(worldData, random);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWorldDataIsNull() {
    RandomManual random = new RandomManual();
    newWorldInstance(null, random);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRandomManualIsNull() {
    worldSb = new StringBuilder("-1 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    newWorldInstance(worldData, null);
  }

  @Test(expected = InputMismatchException.class)
  public void testRowsNotNumber() {
    worldSb = new StringBuilder("Hi 32\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    newWorldInstance(worldData, random);
  }

  @Test(expected = InputMismatchException.class)
  public void testColsNotNumber() {
    worldSb = new StringBuilder("35 Hi\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    newWorldInstance(worldData, random);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNoWorldname() {
    worldSb = new StringBuilder("35 32\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    newWorldInstance(worldData, random);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWorldNameIsEmpty() {
    worldSb = new StringBuilder("35 32   \n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    newWorldInstance(worldData, random);
  }

  @Test(expected = InputMismatchException.class)
  public void testTargetHealthNotNumber() {
    worldSb = new StringBuilder("35 32\n HI Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    newWorldInstance(worldData, random);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNoPetName() {
    worldSb = new StringBuilder("35 32\n 59\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    newWorldInstance(worldData, random);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNoTargetName() {
    worldSb = new StringBuilder("35 32\n 59\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    newWorldInstance(worldData, random);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTargetNameNotaString() {
    worldSb = new StringBuilder("35 32\n 59 10\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    newWorldInstance(worldData, random);
  }

  @Test(expected = InputMismatchException.class)
  public void testCoordinateOfSpaceNotaNumber() {
    worldSb = new StringBuilder("35 32\n 59 Dr. Lucky\n Simba\n 8\n Hi 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    newWorldInstance(worldData, random);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNoNameOfSpace() {
    worldSb = new StringBuilder("35 32\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    newWorldInstance(worldData, random);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testZeroNumOfSpaces() {
    worldSb = new StringBuilder("35 32\n 59 Dr. Lucky\n Simba\n 0");
    worldSb.append("\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    newWorldInstance(worldData, random);
  }

  @Test(expected = InputMismatchException.class)
  public void testNumOfSpacesNotMatching() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 12\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    newWorldInstance(worldData, random);
  }

  @Test(expected = InputMismatchException.class)
  public void testNoSpaces() {
    worldSb = new StringBuilder(
        "35 32 My World\n 59 Dr. Lucky\n Simba\n 12\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    newWorldInstance(worldData, random);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNumOfSpacesZeroButSpacesSpecified() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 0\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    newWorldInstance(worldData, random);
  }

  @Test(expected = InputMismatchException.class)
  public void testItemsDamageNotNumber() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 Hi Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    newWorldInstance(worldData, random);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNoItemName() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    newWorldInstance(worldData, random);
  }

  @Test(expected = NoSuchElementException.class)
  public void testNumOfItemsNotMatching() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 10\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    newWorldInstance(worldData, random);
  }

  @Test(expected = NoSuchElementException.class)
  public void testNoItems() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    newWorldInstance(worldData, random);
  }

  @Test
  public void getName() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);
    assertEquals(testWorld.getName(), "My World");
  }

  @Test
  public void testGetRowsAndColumns() {
    worldSb = new StringBuilder("35 35 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorldEqualRowsAndCol = newWorldInstance(worldData, random);
    assertEquals(testWorldEqualRowsAndCol.getRows(), 35);
    assertEquals(testWorldEqualRowsAndCol.getColumns(), 35);

    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    World testWorldRowsGreaterThanCol = newWorldInstance(worldData, random);
    assertEquals(testWorldRowsGreaterThanCol.getRows(), 35);
    assertEquals(testWorldRowsGreaterThanCol.getColumns(), 32);

    worldSb = new StringBuilder("32 35 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    World testWorldColsGreaterThanRows = newWorldInstance(worldData, random);
    assertEquals(testWorldColsGreaterThanRows.getRows(), 32);
    assertEquals(testWorldColsGreaterThanRows.getColumns(), 35);
  }

  @Test
  public void testGetCurrentSpaceOfTarget() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);
    assertTrue(testWorld.getCurrentSpaceOfTarget().equals(allSpacesForTest.get(0).getName()));
  }

  @Test
  public void testGetInfoOfaSpace() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 4\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n 0 17 Pistol\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);

    testWorld.addPlayer("Pranith", "Dining", true);

    StringBuilder expectedString = new StringBuilder().append("Name: ").append("Dining")
        .append("\nAll Items in space:\n");

    expectedString.append("Item(Name = Pistol, Damage = 17)\n");

    expectedString.append("All neighbouring spaces:\n");
    expectedString.append(testWorld.getNeighbours("Dining"));
    expectedString.append("\nAll players in the space:\n");
    expectedString.append("[Player(Name: Pranith, Items carrying: 0)]\n");
    expectedString
        .append("The target Dr. Lucky is currently present in this space with health 59.\n");
    expectedString.append("The pet Simba is currently present in this space.\n");

    String spaceInfo = testWorld.getInfoOfaSpace("Dining");

    assertEquals(expectedString.toString(), spaceInfo);

    StringBuilder expectedStringTwo = new StringBuilder().append("Name: ").append("Master Bedroom")
        .append("\nAll Items in space:\n").append("No items in space\n");
    expectedStringTwo.append("All neighbouring spaces:\n");
    expectedStringTwo.append(testWorld.getNeighbours("Master Bedroom"));
    expectedStringTwo.append("\nAll players in the space:\n");
    expectedStringTwo.append("No players in space\n");

    assertEquals(expectedStringTwo.toString(), testWorld.getInfoOfaSpace("Master Bedroom"));
  }

  // Test to see if a space not part of the world is given as an argument
  @Test(expected = IllegalArgumentException.class)
  public void testGetInfoOfaInvalidSpace() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);
    testWorld.getInfoOfaSpace("Test Room");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetInfoOfaNullSpace() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);
    testWorld.getInfoOfaSpace(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetInfoOfaEmptySpace() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);
    testWorld.getInfoOfaSpace("");
  }

  @Test
  public void testGetNeighboursWithPetInOneNeighbour() {

    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());

    List<Space> neighbouringSpaces = new ArrayList<>();
    neighbouringSpaces.add(newSpaceInstance(4, 4, 9, 9, "Master Bedroom", new ArrayList<Item>()));
    neighbouringSpaces.add(newSpaceInstance(0, 10, 3, 18, "Home Office", new ArrayList<Item>()));
    neighbouringSpaces.add(newSpaceInstance(2, 0, 16, 3, "Entrance Hall", new ArrayList<Item>()));

    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);
    assertTrue(testWorld.getNeighbours("Attic").equals(neighbouringSpaces.toString()));

  }

  @Test
  public void testGetNeighboursWithoutPetInAnyNeighbour() {

    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());

    List<Space> neighbouringSpaces = new ArrayList<>();
    neighbouringSpaces.add(newSpaceInstance(2, 0, 16, 3, "Entrance Hall", new ArrayList<Item>()));
    neighbouringSpaces.add(newSpaceInstance(0, 4, 3, 9, "Attic", new ArrayList<Item>()));

    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);
    assertTrue(testWorld.getNeighbours("Master Bedroom").equals(neighbouringSpaces.toString()));

  }

  // Test to see if a space not part of the world is given as an argument for
  // getNeighbours()
  @Test(expected = IllegalArgumentException.class)
  public void testGetNeighboursForInvalidSpace() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);
    testWorld.getInfoOfaSpace("Test Room");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNeighboursIfSpaceNameIsNull() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);
    testWorld.getNeighbours(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNeighboursIfSpaceIsEmpty() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);
    testWorld.getNeighbours("");
  }

  @Test
  public void testDrawImage() throws IllegalStateException {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);
    try {
      assertEquals(testWorld.drawImage(), "TheWorld.png");
    } catch (IllegalStateException e) {
      throw new IllegalStateException("Error in drawing image.");
    }
  }

  @Test
  public void testAddPlayer() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);
    testWorld.addPlayer("Pranith", "Dining", true);
    assertEquals(
        "Name: Pranith\nCurrent space: Dining\nItems carrying:\nNone\nIs Human player: Yes\n",
        testWorld.getPlayerInfo("Pranith"));
    testWorld.addPlayer("Robot", "Dining", false);
    assertEquals("Name: Robot\nCurrent space: Dining\nItems carrying:\nNone\nIs Human player: No\n",
        testWorld.getPlayerInfo("Robot"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddPlayerWhenNameIsNull() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);
    testWorld.addPlayer(null, "Dining", true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddPlayerWhenNameIsEmpty() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);
    testWorld.addPlayer("", "Dining", true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddPlayerWhenSpaceNameIsNull() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);
    testWorld.addPlayer("Pranith", null, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddPlayerWhenSpaceNameIsEmpty() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);
    testWorld.addPlayer("Pranith", "", true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddPlayerWhenSpaceNameIsInvalid() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);
    testWorld.addPlayer("Pranith", "Test room", true);
  }

  @Test
  public void testGetPlayerInfo() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);
    testWorld.addPlayer("Pranith", "Dining", true);
    assertEquals(
        "Name: Pranith\nCurrent space: Dining\nItems carrying:\nNone\nIs Human player: Yes\n",
        testWorld.getPlayerInfo("Pranith"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetPlayerInfoWhenNameIsNull() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);
    testWorld.addPlayer("Pranith", "Dining", true);
    testWorld.getPlayerInfo(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetPlayerInfoWhenNameIsEmpty() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);
    testWorld.addPlayer("Pranith", "Dining", true);
    testWorld.getPlayerInfo("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetPlayerInfoWhenNameIsInvalid() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);
    testWorld.addPlayer("Pranith", "Dining", true);
    testWorld.getPlayerInfo("Robot");
  }

  @Test
  public void testgetTurnInfo() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);

    testWorld.addPlayer("Pranith", "Dining", true);
    testWorld.addPlayer("Tarun", "Dining", true);
    testWorld.addPlayer("Ajay", "Dining", true);
    testWorld.addPlayer("KK", "Dining", false);
    testWorld.addPlayer("KB", "Dining", true);
    testWorld.addPlayer("Subash", "Dining", true);

    List<String> testArray = new ArrayList<String>();

    testArray.add("Pranith");
    testArray.add("Human");
    testArray.add("Dining");
    testArray.add("Dining");
    assertEquals(testArray, testWorld.getTurnInfo());

    testArray.clear();
    testWorld.lookAroundByPlayer();
    testArray.add("Tarun");
    testArray.add("Human");
    testArray.add("Dining");
    testArray.add("Master Bedroom");
    assertEquals(testArray, testWorld.getTurnInfo());

    testArray.clear();
    testWorld.lookAroundByPlayer();
    testWorld.lookAroundByPlayer();
    testArray.add("KK");
    testArray.add("Computer");
    testArray.add("Dining");
    testArray.add("Garage");
    assertEquals(testArray, testWorld.getTurnInfo());
  }

  @Test
  public void testMovePlayerInWorld() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);

    testWorld.addPlayer("Pranith", "Attic", true);

    testWorld.movePlayerInWorld(getScaledCoord(5), getScaledCoord(5));
    assertEquals("Name: Pranith\nCurrent space: Master Bedroom\n"
        + "Items carrying:\nNone\nIs Human player: Yes\n", testWorld.getPlayerInfo("Pranith"));
  }

  @Test(expected = IllegalStateException.class)
  public void testMovePlayerToaSpaceWithPet() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);

    testWorld.addPlayer("Pranith", "Attic", true);

    testWorld.movePlayerInWorld(getScaledCoord(11), getScaledCoord(5));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMovePlayerInWorldWhenSpaceCoordIsNegative() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);

    testWorld.addPlayer("Pranith", "Attic", true);

    testWorld.movePlayerInWorld(-1, -10);
  }

  @Test(expected = IllegalStateException.class)
  public void testMovePlayerInWorldWhenSpaceIsNotNeighbour() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);

    testWorld.addPlayer("Pranith", "Attic", true);

    testWorld.movePlayerInWorld(getScaledCoord(14), getScaledCoord(5));
  }

  @Test
  public void testPickItemByPlayer() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);

    testWorld.addPlayer("Pranith", "Garage", true);

    testWorld.pickItemByPlayer("Pan");
    assertEquals(
        "Name: Pranith\nCurrent space: Garage\nItems "
            + "carrying:\n[Item(Name = Pan, Damage = 10)]\nIs Human player: Yes\n",
        testWorld.getPlayerInfo("Pranith"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPickItemByPlayerWhenItemIsNull() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);

    testWorld.addPlayer("Pranith", "Garage", true);

    testWorld.pickItemByPlayer(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPickItemByPlayerWhenItemIsEmpty() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);

    testWorld.addPlayer("Pranith", "Garage", true);

    testWorld.pickItemByPlayer("");
  }

  @Test(expected = IllegalStateException.class)
  public void testPickItemByPlayerWhenItemIsNotInSpace() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);

    testWorld.addPlayer("Pranith", "Garage", true);

    testWorld.pickItemByPlayer("Knife");
  }

  @Test
  public void testLookAroundByPlayer() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);
    testWorld.addPlayer("Pranith", "Attic", true);

    assertEquals("Looking around...\n" + "Current space: \n" + "Name: Attic\n"
        + "All Items in space:\n" + "No items in space\n" + "All neighbouring spaces:\n"
        + "[Space(Name = Master Bedroom, Top left = (4, 4), Bottom right = (9, 9)), "
        + "Space(Name = Home Office, Top left = (0, 10), Bottom right = (3, 18)), "
        + "Space(Name = Entrance Hall, Top left = (2, 0), Bottom right = (16, 3))]\n"
        + "All players in the space:\n" + "[Player(Name: Pranith, Items carrying: 0)]\n" + "\n"
        + "Neighbouring spaces:\n" + "Name: Master Bedroom\n" + "All Items in space:\n"
        + "No items in space\n" + "All neighbouring spaces:\n"
        + "[Space(Name = Entrance Hall, Top left = (2, 0), Bottom right = (16, 3)), "
        + "Space(Name = Attic, Top left = (0, 4), Bottom right = (3, 9))]\n"
        + "All players in the space:\n" + "No players in space\n" + "\n" + "Name: Home Office\n"
        + "All Items in space:\n" + "Item(Name = Knife, Damage = 15)\n"
        + "All neighbouring spaces:\n"
        + "[Space(Name = Music Room, Top left = (4, 13), Bottom right = (6, 19)), "
        + "Space(Name = Attic, Top left = (0, 4), Bottom right = (3, 9))]\n"
        + "All players in the space:\n" + "No players in space\n" + "\n" + "Name: Entrance Hall\n"
        + "All Items in space:\n" + "No items in space\n" + "All neighbouring spaces:\n"
        + "[Space(Name = Master Bedroom, Top left = (4, 4), Bottom right = (9, 9)), "
        + "Space(Name = Garage, Top left = (17, 0), Bottom right = (24, 3)), "
        + "Space(Name = Attic, Top left = (0, 4), Bottom right = (3, 9))]\n"
        + "All players in the space:\n" + "No players in space\n" + "\n",
        testWorld.lookAroundByPlayer());

  }

  @Test
  public void testPerformComputerAction() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual(0, 1, 1, 0, 2);
    World testWorld = newWorldInstance(worldData, random);
    testWorld.addPlayer("Pranith", "Attic", true);
    testWorld.addPlayer("Robot", "Dining", false);

    testWorld.lookAroundByPlayer();
    assertEquals("The Player Robot has moved to the space Home Office\n",
        testWorld.performComputerAction());
    testWorld.lookAroundByPlayer();
    assertEquals("The Player Robot has picked up the item Knife from the space Home Office\n",
        testWorld.performComputerAction());
    testWorld.lookAroundByPlayer();
    assertEquals("Looking Around...", testWorld.performComputerAction());

  }

  @Test
  public void testPerformComputerActionForAttack() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual(1, 0, 2, 2, 2, 2, 2, 2, 2);
    World testWorld = newWorldInstance(worldData, random);
    testWorld.addPlayer("Pranith", "Attic", true);
    testWorld.addPlayer("Robot", "Garage", false);

    // Testing if computer player attacks target when in same ]
    // space and with an item with highest damage
    testWorld.lookAroundByPlayer();
    assertEquals("The Player Robot has picked up the item Pan from the space Garage\n",
        testWorld.performComputerAction());
    testWorld.lookAroundByPlayer();
    assertEquals("Attack completed! The target's health has decreased. Removing item...\n",
        testWorld.performComputerAction());
    assertEquals(
        "Name: Bathroom\n" + "All Items in space:\n" + "Item(Name = Broom, Damage = 7)\n"
            + "All neighbouring spaces:\n" + "No neighbouring spaces\n"
            + "All players in the space:\n" + "No players in space\n"
            + "The target Dr. Lucky is currently present in this space with health 49.\n",
        testWorld.getInfoOfaSpace("Bathroom"));

    // Testing if computer player attacks target with hand when no items are present
    // with it
    testWorld.lookAroundByPlayer();
    testWorld.performComputerAction();
    testWorld.lookAroundByPlayer();
    testWorld.performComputerAction();
    testWorld.lookAroundByPlayer();
    testWorld.performComputerAction();
    testWorld.lookAroundByPlayer();
    assertEquals("Attack completed! The target's health has decreased. Removing item...\n",
        testWorld.performComputerAction());
    assertEquals(
        "Name: Bathroom\n" + "All Items in space:\n" + "Item(Name = Broom, Damage = 7)\n"
            + "All neighbouring spaces:\n" + "No neighbouring spaces\n"
            + "All players in the space:\n" + "No players in space\n"
            + "The target Dr. Lucky is currently present in this space with health 48.\n",
        testWorld.getInfoOfaSpace("Bathroom"));

    // Testing if computer player attacks target when it is visible to a player
    testWorld.movePlayerInWorld(getScaledCoord(1), getScaledCoord(3));
    testWorld.performComputerAction();
    testWorld.lookAroundByPlayer();
    testWorld.performComputerAction();
    testWorld.lookAroundByPlayer();
    testWorld.performComputerAction();
    testWorld.lookAroundByPlayer();
    assertEquals("Looking Around...", testWorld.performComputerAction());
    assertEquals(
        "Name: Bathroom\n" + "All Items in space:\n" + "Item(Name = Broom, Damage = 7)\n"
            + "All neighbouring spaces:\n" + "No neighbouring spaces\n"
            + "All players in the space:\n" + "No players in space\n"
            + "The target Dr. Lucky is currently present in this space with health 48.\n",
        testWorld.getInfoOfaSpace("Bathroom"));

  }

  @Test
  public void testGetCurrentSpaceOfPet() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);
    assertTrue(testWorld.getCurrentSpaceOfPet().equals(allSpacesForTest.get(0).getName()));
  }

  @Test
  public void testIsPlayerVisible() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);

    testWorld.addPlayer("Pranith", "Dining", true);
    testWorld.addPlayer("Robot", "Dining", true);
    testWorld.addPlayer("Rao", "Master Bedroom", true);
    testWorld.addPlayer("John", "Entrance Hall", true);

    assertTrue(testWorld.isPlayerVisible("Pranith", "Robot"));
    assertTrue(testWorld.isPlayerVisible("Robot", "Pranith"));
    assertTrue(testWorld.isPlayerVisible("John", "Rao"));
    assertTrue(testWorld.isPlayerVisible("Rao", "John"));
    assertTrue(testWorld.isPlayerVisible("Pranith", "Rao"));

    // When pet is in the second player space
    assertFalse(testWorld.isPlayerVisible("Rao", "Pranith"));

    assertFalse(testWorld.isPlayerVisible("Pranith", "John"));
    assertFalse(testWorld.isPlayerVisible("John", "Pranith"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsPlayerVisibleWhenPlayerOneNameIsNull() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);

    testWorld.addPlayer("Pranith", "Dining", true);
    testWorld.addPlayer("Robot", "Dining", true);

    testWorld.isPlayerVisible(null, "Robot");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsPlayerVisibleWhenPlayerTwoNameIsNull() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);

    testWorld.addPlayer("Pranith", "Dining", true);
    testWorld.addPlayer("Robot", "Dining", true);

    testWorld.isPlayerVisible("Pranith", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsPlayerVisibleWhenPlayerOneNameIsEmpty() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);

    testWorld.addPlayer("Pranith", "Dining", true);
    testWorld.addPlayer("Robot", "Dining", true);

    testWorld.isPlayerVisible("", "Robot");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsPlayerVisibleWhenPlayerTwoNameIsEmpty() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);

    testWorld.addPlayer("Pranith", "Dining", true);
    testWorld.addPlayer("Robot", "Dining", true);

    testWorld.isPlayerVisible("Pranith", "");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsPlayerVisibleWhenPlayerOneNameInvalid() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);

    testWorld.addPlayer("Pranith", "Dining", true);
    testWorld.addPlayer("Robot", "Dining", true);

    testWorld.isPlayerVisible("Invalid", "Robot");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsPlayerVisibleWhenPlayerTwoNameInvalid() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);

    testWorld.addPlayer("Pranith", "Dining", true);
    testWorld.addPlayer("Robot", "Dining", true);

    testWorld.isPlayerVisible("Pranith", "Invalid");
  }

  @Test
  public void testMovePetByPlayer() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);

    testWorld.addPlayer("Pranith", "Dining", true);
    testWorld.addPlayer("Robot", "Dining", true);

    testWorld.movePetByPlayer("Master Bedroom");
    assertEquals("Master Bedroom", testWorld.getCurrentSpaceOfPet());
    assertEquals("Robot", testWorld.getTurnInfo().get(0));

    testWorld.movePetByPlayer("Garage");
    assertEquals("Garage", testWorld.getCurrentSpaceOfPet());
    assertEquals("Pranith", testWorld.getTurnInfo().get(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMovePetByPlayerWhenSpaceNameIsNull() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);

    testWorld.addPlayer("Pranith", "Dining", true);
    testWorld.addPlayer("Robot", "Dining", true);

    testWorld.movePetByPlayer(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMovePetByPlayerWhenSpaceNameIsEmpty() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);

    testWorld.addPlayer("Pranith", "Dining", true);
    testWorld.addPlayer("Robot", "Dining", true);

    testWorld.movePetByPlayer("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMovePetByPlayerWhenSpaceNameIsSameAsCurrent() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);

    testWorld.addPlayer("Pranith", "Dining", true);
    testWorld.addPlayer("Robot", "Dining", true);

    testWorld.movePetByPlayer("Dining");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMovePetByPlayerWhenSpaceNameIsInvalid() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);

    testWorld.addPlayer("Pranith", "Dining", true);
    testWorld.addPlayer("Robot", "Dining", true);

    testWorld.movePetByPlayer("Invalid");
  }

  @Test
  public void testAttackTargetSuccess() {
    worldSb = new StringBuilder("35 32 My World\n 12 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 4 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);

    testWorld.addPlayer("Pranith", "Bathroom", true);
    testWorld.addPlayer("Robot", "Master Bedroom", true);

    testWorld.pickItemByPlayer("Pan");
    testWorld.movePetByPlayer("Garage");
    testWorld.lookAroundByPlayer();
    testWorld.movePlayerInWorld(getScaledCoord(5), getScaledCoord(1));

    // if attack successful
    assertEquals("Attack completed! The target's health has decreased. Removing item...\n",
        testWorld.attackTarget("Pan"));

    // if health decreased
    assertEquals(
        "Name: Home Office\n" + "All Items in space:\n" + "Item(Name = Knife, Damage = 15)\n"
            + "All neighbouring spaces:\n"
            + "[Space(Name = Music Room, Top left = (4, 13), Bottom right = (6, 19)), "
            + "Space(Name = Attic, Top left = (0, 4), Bottom right = (3, 9))]\n"
            + "All players in the space:\n" + "No players in space\n"
            + "The target Dr. Lucky is currently present in this space with health 2.\n",
        testWorld.getInfoOfaSpace("Home Office"));

    List<String> testArray = new ArrayList<String>();
    testArray.add("Robot");
    testArray.add("Human");
    testArray.add("Attic");
    testArray.add("Home Office");

    // if turn changed
    assertEquals(testArray, testWorld.getTurnInfo());

    // if item removed from player
    assertEquals("Name: Pranith\n" + "Current space: Bathroom\n" + "Items carrying:\n" + "None\n"
        + "Is Human player: Yes\n", testWorld.getPlayerInfo("Pranith"));

    // If item is hand
    testWorld.lookAroundByPlayer();
    testWorld.lookAroundByPlayer();

    // if attack successful
    assertEquals("Attack completed! The target's health has decreased. Removing item...\n",
        testWorld.attackTarget("hand"));

    // if health decreased
    assertEquals(
        "Name: Dining\n" + "All Items in space:\n" + "No items in space\n"
            + "All neighbouring spaces:\n"
            + "[Space(Name = Master Bedroom, Top left = (4, 4), Bottom right = (9, 9)), "
            + "Space(Name = Music Room, Top left = (4, 13), Bottom right = (6, 19)), "
            + "Space(Name = Home Office, Top left = (0, 10), Bottom right = (3, 18))]\n"
            + "All players in the space:\n" + "No players in space\n"
            + "The target Dr. Lucky is currently present in this space with health 1.\n",
        testWorld.getInfoOfaSpace("Dining"));

    // if turn changed
    testArray.clear();
    testArray.add("Pranith");
    testArray.add("Human");
    testArray.add("Bathroom");
    testArray.add("Dining");
    assertEquals(testArray, testWorld.getTurnInfo());

    // If player won after success and target health is zero
    testWorld.lookAroundByPlayer();
    testWorld.lookAroundByPlayer();
    testWorld.lookAroundByPlayer();
    testWorld.movePlayerInWorld(getScaledCoord(11), getScaledCoord(1));
    testWorld.lookAroundByPlayer();

    // if attack successful and player won
    assertEquals("Attack completed! The target is dead. Player Robot has won the game!\n",
        testWorld.attackTarget("hand"));
  }

  @Test
  public void testAttackTargetUnsuccessful() {
    worldSb = new StringBuilder("35 32 My World\n 12 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 4 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);

    testWorld.addPlayer("Pranith", "Dining", true);
    testWorld.addPlayer("Robot", "Dining", true);

    // if attack unsuccessful as a player is in same space
    assertEquals("Attack failed! The attack was seen by another player. Removing item...\n",
        testWorld.attackTarget("hand"));

    List<String> testArray = new ArrayList<String>();
    testArray.add("Robot");
    testArray.add("Human");
    testArray.add("Dining");
    testArray.add("Master Bedroom");

    // if turn changed
    assertEquals(testArray, testWorld.getTurnInfo());

    testWorld.movePlayerInWorld(getScaledCoord(11), getScaledCoord(3));
    testWorld.lookAroundByPlayer();
    testWorld.pickItemByPlayer("Knife");
    testWorld.lookAroundByPlayer();

    // if attack unsuccessful as a player is in neighbouring space
    assertEquals("Attack failed! The attack was seen by another player. Removing item...\n",
        testWorld.attackTarget("Knife"));

    // if item removed from player
    assertEquals("Name: Robot\n" + "Current space: Home Office\n" + "Items carrying:\n" + "None\n"
        + "Is Human player: Yes\n", testWorld.getPlayerInfo("Robot"));

  }

  @Test
  public void testAttackTargetWithPet() {
    worldSb = new StringBuilder("35 32 My World\n 12 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 4 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);

    testWorld.addPlayer("Pranith", "Dining", true);
    testWorld.addPlayer("Robot", "Master Bedroom", true);

    // if attack successful as a pet is in same space even when neighbouring space
    // has a player
    assertEquals("Attack completed! The target's health has decreased. Removing item...\n",
        testWorld.attackTarget("hand"));

    testWorld.movePlayerInWorld(getScaledCoord(5), getScaledCoord(1));
    testWorld.movePlayerInWorld(getScaledCoord(5), getScaledCoord(5));
    testWorld.movePetByPlayer("Attic");
    testWorld.movePlayerInWorld(getScaledCoord(1), getScaledCoord(3));
    testWorld.lookAroundByPlayer();

    // if attack unsuccessful when a pet is in neighbouring space and it contains a
    // player
    assertEquals("Attack failed! The attack was seen by another player. Removing item...\n",
        testWorld.attackTarget("hand"));

  }

  @Test(expected = IllegalArgumentException.class)
  public void testAttackTargetWhenItemNameIsNull() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);

    testWorld.addPlayer("Pranith", "Dining", true);
    testWorld.addPlayer("Robot", "Dining", true);

    testWorld.attackTarget(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAttackTargetWhenItemNameIsEmpty() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);

    testWorld.addPlayer("Pranith", "Dining", true);
    testWorld.addPlayer("Robot", "Dining", true);

    testWorld.attackTarget("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAttackTargetWhenItemNotExistWithPlayer() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);

    testWorld.addPlayer("Pranith", "Dining", true);
    testWorld.addPlayer("Robot", "Dining", true);

    testWorld.attackTarget("Pan");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAttackTargetWhenPlayerAndTargetInDiffSpace() {
    worldSb = new StringBuilder("35 32 My World\n 59 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 3 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);

    testWorld.addPlayer("Pranith", "Garage", true);
    testWorld.addPlayer("Robot", "Dining", true);

    testWorld.attackTarget("hand");
  }

  @Test
  public void testIsGameOverForHumanPlayer() {
    worldSb = new StringBuilder("35 32 My World\n 1 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 4 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);

    testWorld.addPlayer("Pranith", "Dining", true);
    testWorld.addPlayer("Robot", "Master Bedroom", true);

    assertEquals("", testWorld.isGameOver());
    testWorld.attackTarget("hand");
    assertEquals("Game is completed. Pranith has won the game!", testWorld.isGameOver());
  }

  @Test
  public void testIsGameOverForComputerPlayer() {
    worldSb = new StringBuilder("35 32 My World\n 1 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 4 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);

    testWorld.addPlayer("Robot", "Dining", false);
    testWorld.addPlayer("Pranith", "Master Bedroom", true);

    assertEquals("", testWorld.isGameOver());
    testWorld.performComputerAction();
    assertEquals("Game is completed. Robot has won the game!", testWorld.isGameOver());
  }

  @Test
  public void testDfsTraversalOfPetWithoutMovingPet() {
    worldSb = new StringBuilder("35 32 My World\n 1 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 4 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);

    testWorld.addPlayer("Pranith", "Master Bedroom", true);
    testWorld.addPlayer("Robot", "Dining", true);

    testWorld.lookAroundByPlayer();
    assertEquals("Master Bedroom", testWorld.getCurrentSpaceOfPet());

    testWorld.lookAroundByPlayer();
    assertEquals("Entrance Hall", testWorld.getCurrentSpaceOfPet());

    testWorld.lookAroundByPlayer();
    assertEquals("Garage", testWorld.getCurrentSpaceOfPet());

    testWorld.lookAroundByPlayer();
    assertEquals("Entrance Hall", testWorld.getCurrentSpaceOfPet());

    testWorld.lookAroundByPlayer();
    assertEquals("Attic", testWorld.getCurrentSpaceOfPet());

    testWorld.lookAroundByPlayer();
    assertEquals("Home Office", testWorld.getCurrentSpaceOfPet());

    testWorld.lookAroundByPlayer();
    assertEquals("Music Room", testWorld.getCurrentSpaceOfPet());

    testWorld.lookAroundByPlayer();
    assertEquals("Home Office", testWorld.getCurrentSpaceOfPet());

    testWorld.lookAroundByPlayer();
    assertEquals("Attic", testWorld.getCurrentSpaceOfPet());

    testWorld.lookAroundByPlayer();
    assertEquals("Entrance Hall", testWorld.getCurrentSpaceOfPet());

    testWorld.lookAroundByPlayer();
    assertEquals("Master Bedroom", testWorld.getCurrentSpaceOfPet());

    testWorld.lookAroundByPlayer();
    assertEquals("Dining", testWorld.getCurrentSpaceOfPet());

    testWorld.lookAroundByPlayer();
    assertEquals("Master Bedroom", testWorld.getCurrentSpaceOfPet());
  }

  @Test
  public void testDfsTraversalOfPetWithMovingPet() {
    worldSb = new StringBuilder("35 32 My World\n 1 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 4 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);

    testWorld.addPlayer("Pranith", "Master Bedroom", true);
    testWorld.addPlayer("Robot", "Dining", true);

    testWorld.lookAroundByPlayer();
    assertEquals("Master Bedroom", testWorld.getCurrentSpaceOfPet());

    testWorld.lookAroundByPlayer();
    assertEquals("Entrance Hall", testWorld.getCurrentSpaceOfPet());

    testWorld.movePetByPlayer("Home Office");
    assertEquals("Home Office", testWorld.getCurrentSpaceOfPet());

    testWorld.lookAroundByPlayer();
    assertEquals("Dining", testWorld.getCurrentSpaceOfPet());

    testWorld.lookAroundByPlayer();
    assertEquals("Master Bedroom", testWorld.getCurrentSpaceOfPet());
  }

  @Test
  public void testGetNumOfTurns() {
    worldSb = new StringBuilder("35 32 My World\n 1 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 4 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstanceWithTurns(worldData, random, 2);

    testWorld.addPlayer("Pranith", "Master Bedroom", true);
    testWorld.addPlayer("Robot", "Dining", true);

    assertEquals(testWorld.getNumOfTurns(), 2);
    testWorld.lookAroundByPlayer();
    testWorld.lookAroundByPlayer();
    assertEquals(testWorld.getNumOfTurns(), 1);

    testWorld.lookAroundByPlayer();
    testWorld.lookAroundByPlayer();
    assertEquals(testWorld.getNumOfTurns(), 0);
  }

  @Test
  public void testGetAllPlayers() {
    worldSb = new StringBuilder("35 32 My World\n 1 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 4 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);

    testWorld.addPlayer("Pranith", "Master Bedroom", true);
    testWorld.addPlayer("Robot", "Dining", true);

    assertEquals("Pranith", testWorld.getAllPlayers()[0][0]);
    assertEquals("Master Bedroom", testWorld.getAllPlayers()[0][1]);
    assertEquals("Human", testWorld.getAllPlayers()[0][2]);

    assertEquals("Robot", testWorld.getAllPlayers()[1][0]);
    assertEquals("Dining", testWorld.getAllPlayers()[1][1]);
    assertEquals("Human", testWorld.getAllPlayers()[1][2]);

    testWorld.movePlayerInWorld(getScaledCoord(1), getScaledCoord(3));
    assertEquals("Pranith", testWorld.getAllPlayers()[0][0]);
    assertEquals("Entrance Hall", testWorld.getAllPlayers()[0][1]);
    assertEquals("Human", testWorld.getAllPlayers()[0][2]);
  }

  @Test
  public void testGetCoordinates() {
    worldSb = new StringBuilder("35 32 My World\n 1 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 4 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);

    testWorld.addPlayer("Pranith", "Master Bedroom", true);
    testWorld.addPlayer("Robot", "Dining", true);

    int[] diningCoordinates = testWorld.getCoordinates("Dining");
    assertEquals(4, diningCoordinates[0]);
    assertEquals(10, diningCoordinates[1]);
    assertEquals(11, diningCoordinates[2]);
    assertEquals(12, diningCoordinates[3]);

    int[] garageCoordinates = testWorld.getCoordinates("Garage");
    assertEquals(17, garageCoordinates[0]);
    assertEquals(0, garageCoordinates[1]);
    assertEquals(24, garageCoordinates[2]);
    assertEquals(3, garageCoordinates[3]);
  }

  @Test
  public void testGetAllSpaces() {
    worldSb = new StringBuilder("35 32 My World\n 1 Dr. Lucky\n Simba\n 8\n 4 10 11 12 Dining\n");
    worldSb.append("4 4 9 9 Master Bedroom\n 4 13 6 19 Music Room\n 17 0 24 3 Garage\n");
    worldSb.append("17 13 20 19 Bathroom\n 0 10 3 18 Home Office\n 2 0 16 3 Entrance Hall\n");
    worldSb.append(" 0 4 3 9 Attic\n 3\n 4 10 Pan\n 4 7 Broom\n 5 15 Knife\n");
    worldData = new StringReader(worldSb.toString());
    RandomManual random = new RandomManual();
    World testWorld = newWorldInstance(worldData, random);

    testWorld.addPlayer("Pranith", "Master Bedroom", true);
    testWorld.addPlayer("Robot", "Dining", true);

    String[] allSpaces = testWorld.getAllSpaces();

    assertEquals("Dining", allSpaces[0]);
    assertEquals("Master Bedroom", allSpaces[1]);
    assertEquals("Music Room", allSpaces[2]);
    assertEquals("Garage", allSpaces[3]);
    assertEquals("Bathroom", allSpaces[4]);
    assertEquals("Home Office", allSpaces[5]);
    assertEquals("Entrance Hall", allSpaces[6]);
    assertEquals("Attic", allSpaces[7]);
  }
}
