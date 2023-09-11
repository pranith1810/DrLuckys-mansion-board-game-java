package test;

import static org.junit.Assert.assertEquals;

import game.Pet;
import game.PetImpl;
import org.junit.Test;

/**
 * A test class for testing PetImpl class.
 */
public class PetImplTest {

  /**
   * This is a method which is used to create new instances of PetImpl class.
   * 
   * @param name Name of the Pet
   * @return New instance of PetImpl class
   */
  private Pet newPetInstance(String name) {
    return new PetImpl(name);
  }

  @Test
  public void testConstructorAndToString() {
    Pet testPet = newPetInstance("Simba");
    assertEquals("Pet(Name: Simba)", testPet.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNameIsNull() {
    newPetInstance(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNameIsEmpty() {
    newPetInstance("");
  }

  @Test
  public void testGetName() {
    Pet testPet = newPetInstance("Simba");
    assertEquals("Simba", testPet.getName());
  }

  @Test
  public void testGetCurrentSpaceIndex() {
    Pet testPet = newPetInstance("Simba");
    assertEquals(0, testPet.getCurrentSpaceIndex());
  }

  @Test
  public void testMovePet() {
    Pet testPet = newPetInstance("Simba");
    testPet.movePet(2);
    assertEquals(2, testPet.getCurrentSpaceIndex());
    testPet.movePet(4);
    assertEquals(4, testPet.getCurrentSpaceIndex());
    testPet.movePet(0);
    assertEquals(0, testPet.getCurrentSpaceIndex());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalMovePet() {
    Pet testPet = newPetInstance("Simba");
    testPet.movePet(-2);
  }
}
