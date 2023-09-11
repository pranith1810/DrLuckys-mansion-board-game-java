package controller;

import game.GameWorld;

/**
 * A class which is used as a command to execute the corresponding method in
 * GameWorld model to perform a computer action.
 */
public class PerformComputerAction implements GameCommand {

  private String output;
  
  /**
   * A constructor to create instance of the command class PerformComputerAction.
   */
  public PerformComputerAction() {
    this.output = "";
  }
  
  @Override
  public void execute(GameWorld model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("GameWorld cannot be null");
    }

    this.output = model.performComputerAction();
    
  }

  @Override
  public String getOutput() {
    return this.output;
  }

}
