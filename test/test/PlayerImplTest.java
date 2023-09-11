package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import game.Item;
import game.ItemImpl;
import game.Player;
import game.PlayerImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.junit.Test;
import utils.RandomManual;

/**
 * A test class to test PlayerImpl class.
 */
public class PlayerImplTest {

  /**
   * This is a method which is used to create new instances of PlayerImpl class.
   * 
   * @param name              Name of the Player
   * @param currentSpaceIndex Starting space index of the player
   * @return New instance of PlayerImpl class
   */
  private Player newPlayerInstance(String name, int currentSpaceIndex) {
    return new PlayerImpl(name, currentSpaceIndex);
  }

  @Test
  public void testConstructorAndToString() {
    Player testPlayer = newPlayerInstance("Pranith", 0);
    assertEquals("Player(Name: Pranith, Items carrying: 0)", testPlayer.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNameIsNull() {
    newPlayerInstance(null, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNameIsEmptyString() {
    newPlayerInstance("", 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeSpaceIndex() {
    newPlayerInstance("Pranith", -2);
  }

  @Test
  public void testGetname() {
    Player player = newPlayerInstance("Pranith", 0);
    assertEquals("Pranith", player.getName());
  }

  @Test
  public void testGetCurrentSpaceIndex() {
    Player player = newPlayerInstance("Pranith", 0);
    assertEquals(0, player.getSpaceIndexOfPlayer());
  }

  @Test
  public void testMovePlayer() {
    Player player = newPlayerInstance("Pranith", 0);
    player.movePlayer(2);
    assertEquals(2, player.getSpaceIndexOfPlayer());
    player.movePlayer(4);
    assertEquals(4, player.getSpaceIndexOfPlayer());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeSpaceIndexForMovePlayer() {
    Player player = newPlayerInstance("Pranith", 2);
    player.movePlayer(-2);
  }

  @Test
  public void testGetPlayerItemsAndPickItemAndRemoveItem() {
    Player player = newPlayerInstance("Pranith", 2);
    List<Item> itemsList = new ArrayList<Item>();

    assertEquals(itemsList, player.getPlayerItems());

    Item panItem = new ItemImpl("Pan", 10);

    player.pickItem(panItem);
    itemsList.add(panItem);
    assertEquals(itemsList, player.getPlayerItems());

    Item knifeItem = new ItemImpl("Knife", 10);
    Item broomItem = new ItemImpl("Broom", 10);
    player.pickItem(knifeItem);
    player.pickItem(broomItem);
    itemsList.add(knifeItem);
    itemsList.add(broomItem);
    assertEquals(itemsList, player.getPlayerItems());

    player.removeItem(panItem.getName());
    itemsList.remove(0);
    assertEquals(itemsList, player.getPlayerItems());

    player.removeItem(knifeItem.getName());
    player.removeItem(broomItem.getName());
    itemsList.remove(0);
    itemsList.remove(0);
    assertEquals(itemsList, player.getPlayerItems());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPickItemWhenItemIsNull() {
    Player player = newPlayerInstance("Pranith", 2);
    player.pickItem(null);
  }

  @Test(expected = IllegalStateException.class)
  public void testPickItemWhenMaximumLimitIsReached() {
    Player player = newPlayerInstance("Pranith", 2);
    for (int i = 0; i < 6; i++) {
      player.pickItem(new ItemImpl("Item" + i, 10));
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveItemWhenNotExisting() {
    Player player = newPlayerInstance("Pranith", 2);
    player.removeItem("Pan");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveItemWhenNull() {
    Player player = newPlayerInstance("Pranith", 2);
    player.removeItem(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveItemWhenEmptyString() {
    Player player = newPlayerInstance("Pranith", 2);
    player.removeItem("");
  }

  @Test
  public void testGetMaxNumOfItems() {
    Player player = newPlayerInstance("Pranith", 2);
    assertEquals(5, player.getMaxNumOfItems());
  }

  @Test
  public void testEquals() {
    Player playerOne = newPlayerInstance("Pranith", 2);
    assertTrue(playerOne.equals(playerOne));

    Player playerTwo = newPlayerInstance("Pranith", 1);
    assertTrue(playerOne.equals(playerTwo));

    Player playerThree = newPlayerInstance("Robot", 2);
    assertFalse(playerOne.equals(playerThree));
  }

  @Test
  public void testHashCode() {
    Player testPlayer = newPlayerInstance("Pranith", 0);
    assertEquals(testPlayer.hashCode(), Objects.hash("Pranith"));
  }

  @Test
  public void chooseAction() {
    Player testPlayer = newPlayerInstance("Pan", 10);
    RandomManual random = new RandomManual();
    testPlayer.chooseAction(random, 0);
  }

  @Test
  public void getPlayerType() {
    Player testPlayer = newPlayerInstance("Pan", 10);
    assertEquals("Human", testPlayer.getPlayerType());
  }
}
