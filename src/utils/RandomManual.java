package utils;

import java.util.Random;

/**
 * A class to generate either predictable or unpredictable random numbers.
 */
public class RandomManual {

  private final Random random;
  private final int[] values;
  private int currentIndex;

  /**
   * Default constructor which uses Random class to generate real random numbers.
   */
  public RandomManual() {
    this.random = new Random();
    this.values = null;
    this.currentIndex = 0;
  }

  /**
   * Constructor which uses varargs to generate predictable random numbers.
   * 
   * @param values The values varargs paramater which contains the values to be
   *               returned in order
   */
  public RandomManual(int... values) {
    this.values = values;
    this.random = null;
  }

  /**
   * Returns a random number, either predictable or true random number based on
   * field values.
   * 
   * @return The next random number
   */
  public int getNextNumber() {
    int value = 0;
    if (isPredictable() && values != null) {
      value = values[currentIndex];
      currentIndex++;
      if (currentIndex >= values.length) {
        currentIndex = 0;
      }
    } else if (random != null) {
      value = random.nextInt(100);
    }
    return value;
  }

  /**
   * Returns whether the current random instance is predictable or true.
   * 
   * @return True if predictable else false
   */
  public boolean isPredictable() {
    if (random == null) {
      return true;
    }
    return false;
  }
}
