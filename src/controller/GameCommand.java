package controller;

import game.GameWorld;

/**
 * Interface for the commands provided by the GameWorld Interface.
 */
public interface GameCommand {

  /**
   * The method is used to execute the corresponding command that is implemented
   * by a class by using the passed model instance.
   * 
   * @param model The instance of the GameWorld model
   * @throws IllegalArgumentException When GameWorld is null
   */
  public void execute(GameWorld model)
      throws IllegalArgumentException;
  
  /**
   * Returns the output for the corresponding model method in string format.
   * 
   * @return Returns string format of the output 
   */
  public String getOutput();
}
