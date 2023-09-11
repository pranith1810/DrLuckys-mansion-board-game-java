package controller;

import game.GameWorld;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;
import view.GameView;

/**
 * A class implementation of the GameController interface which is used to take
 * inputs from the view, send commands to the model and send the corresponding
 * output back to the view.
 */
public class GameControllerImpl implements GameController, Features {

  private final GameWorld gameModel;
  private final GameView gameView;
  private String worldSpecification;
  private final Map<Command, Function<List<String>, GameCommand>> commands;

  /**
   * A contructor that is used to create an instance of the GameControllerImpl
   * which is the primary controller of the game.
   * 
   * @param gameModel The model of the game that is to be used
   * @param gameView  The view of the game that is to be used
   * @throws IllegalArgumentException When gameModel or gameView is null
   */
  public GameControllerImpl(GameWorld gameModel, GameView gameView)
      throws IllegalArgumentException {

    if (gameModel == null) {
      throw new IllegalArgumentException("Game model cannot be null");
    }

    if (gameView == null) {
      throw new IllegalArgumentException("Game view cannot be null");
    }

    gameView.setFeatures(this);

    this.gameModel = gameModel;
    this.gameView = gameView;
    this.commands = new HashMap<Command, Function<List<String>, GameCommand>>();
    this.worldSpecification = "";

    commands.put(Command.MOVE, (list) -> {
      return new MovePlayer(Integer.parseInt(list.get(0)), Integer.parseInt(list.get(1)));
    });

    commands.put(Command.PICK_ITEM, (list) -> {
      return new PickItem(list.get(0));
    });

    commands.put(Command.ADD_PLAYER, (list) -> {
      return new AddPlayer(list.get(0), list.get(1), "true".equals(list.get(2)));
    });

    commands.put(Command.ATTACK_TARGET, (list) -> {
      return new AttackTarget(list.get(0));
    });

    commands.put(Command.DISPLAY_SPACE_INFO, (list) -> {
      return new DisplayInfoOfSpace(list.get(0));
    });

    commands.put(Command.DRAW_IMAGE, (list) -> {
      return new DrawImage();
    });

    commands.put(Command.IS_GAME_OVER, (list) -> {
      return new IsGameOver();
    });

    commands.put(Command.LOOK_AROUND, (list) -> {
      return new LookAround();
    });

    commands.put(Command.MOVE_PET, (list) -> {
      return new MovePet(list.get(0));
    });

    commands.put(Command.PERFORM_COMPUTER_ACTION, (list) -> {
      return new PerformComputerAction();
    });

    commands.put(Command.SET_WORLD, (list) -> {
      return new SetWorld(new StringReader(list.get(0)));
    });

    commands.put(Command.IS_CURRENT_COMPUTER, (list) -> {
      return new IsCurrentComputer();
    });

  }

  private String executeCmd(GameWorld model, Command currentCommand, List<String> args) {
    if (model == null) {
      throw new IllegalArgumentException("model cant be null");
    }
    if (currentCommand == null) {
      throw new IllegalArgumentException("Command cant be null");
    }
    if (args == null) {
      throw new IllegalArgumentException("Arguments cant be null");
    }

    Function<List<String>, GameCommand> cmd;
    String result = "";
    cmd = commands.get(currentCommand);
    GameCommand comandOut = cmd.apply(args);
    if (comandOut != null) {
      comandOut.execute(model);
      result = comandOut.getOutput();
    }
    return result;
  }

  @Override
  public void startGame() {
    try {
      gameView.displayWelcomeScreen(this);
    } catch (IllegalArgumentException ise) {
      gameView.displayPopupMessage(ise.getMessage(), "Error");
    } catch (IllegalStateException ie) {
      gameView.displayPopupMessage(ie.getMessage(), "Error");
    }
  }

  @Override
  public void updateWorldSpecification(String worldSpecification) throws IllegalArgumentException {
    if (worldSpecification == null) {
      throw new IllegalArgumentException("World string cannot be null");
    }

    if (worldSpecification.length() == 0) {
      throw new IllegalArgumentException("World string cannot be empty");
    }

    this.worldSpecification = worldSpecification;
  }

  @Override
  public void addPlayer(String name, String startingLocation, boolean isHuman)
      throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("Name Cant be null");
    }
    if (name.length() <= 0) {
      throw new IllegalArgumentException("Invalid name");
    }
    if (startingLocation == null) {
      throw new IllegalArgumentException("Starting Location Cant be null");
    }
    if (startingLocation.length() == 0) {
      throw new IllegalArgumentException("Starting Location Cant be empty");
    }

    try {
      executeCmd(gameModel, Command.ADD_PLAYER,
          new ArrayList<String>(Arrays.asList(name, startingLocation, isHuman ? "true" : "false")));
    } catch (IllegalArgumentException ise) {
      gameView.displayPopupMessage(ise.getMessage(), "Error");
    } catch (IllegalStateException ie) {
      gameView.displayPopupMessage(ie.getMessage(), "Error");
    }

  }

  @Override
  public void spaceIsClicked(int xcoord, int ycoord) throws IllegalArgumentException {
    if (xcoord < 0 || ycoord < 0) {
      throw new IllegalArgumentException("Coordinates cannot be negative");
    }

    try {
      String result = executeCmd(gameModel, Command.MOVE,
          new ArrayList<String>(Arrays.asList(String.valueOf(xcoord), String.valueOf(ycoord))));
      gameView.updateGameScreen(result);
      checkNextTurn();
    } catch (IllegalArgumentException ise) {
      gameView.displayPopupMessage(ise.getMessage(), "Error");
    } catch (IllegalStateException ie) {
      gameView.displayPopupMessage(ie.getMessage(), "Error");
    }
  }

  @Override
  public void startGameIsClicked() {
    try {
      if (worldSpecification.length() != 0) {
        executeCmd(gameModel, Command.SET_WORLD,
            new ArrayList<String>(Arrays.asList(worldSpecification)));
      }
      executeCmd(gameModel, Command.DRAW_IMAGE, new ArrayList<String>());
      gameView.displayAddPlayerScreen(this);
    } catch (IllegalArgumentException | NoSuchElementException e) {
      gameView.displayPopupMessage(e.getMessage(), "Error");
    } catch (IllegalStateException ie) {
      gameView.displayPopupMessage(ie.getMessage(), "Error");
    }
  }

  @Override
  public void showGameScreen() {
    try {
      gameView.displayGameScreen(this);
      checkNextTurn();
    } catch (IllegalArgumentException ie) {
      gameView.displayPopupMessage(ie.getMessage(), "Error");
    } catch (IllegalStateException ie) {
      gameView.displayPopupMessage(ie.getMessage(), "Error");
    }
  }

  private void checkNextTurn() {
    try {
      String gameOverMessage = executeCmd(gameModel, Command.IS_GAME_OVER, new ArrayList<String>());
      if (!"".equals(gameOverMessage)) {
        gameView.displayPopupMessage(gameOverMessage, "");
        gameView.exitGame();
        return;
      }
      String computerOrNot = executeCmd(gameModel, Command.IS_CURRENT_COMPUTER,
          new ArrayList<String>());
      if ("Computer".equals(computerOrNot)) {
        String result = executeCmd(gameModel, Command.PERFORM_COMPUTER_ACTION,
            new ArrayList<String>());
        gameView.updateGameScreen(result);
        checkNextTurn();
      }
    } catch (IllegalArgumentException ie) {
      gameView.displayPopupMessage(ie.getMessage(), "Error");
    } catch (IllegalStateException ie) {
      gameView.displayPopupMessage(ie.getMessage(), "Error");
    }
  }

  @Override
  public void pickItem(String itemName) throws IllegalArgumentException {
    if (itemName == null) {
      throw new IllegalArgumentException("Item name cannot be null");
    }

    if (itemName.length() == 0) {
      throw new IllegalArgumentException("Item name cannot be empty");
    }

    try {
      String result = executeCmd(gameModel, Command.PICK_ITEM,
          new ArrayList<String>(Arrays.asList(itemName)));
      gameView.updateGameScreen(result);
      checkNextTurn();
    } catch (IllegalArgumentException ie) {
      gameView.displayPopupMessage(ie.getMessage(), "Error");
    } catch (IllegalStateException ie) {
      gameView.displayPopupMessage(ie.getMessage(), "Error");
    }
  }

  @Override
  public void lookAround() {
    try {
      String result = executeCmd(gameModel, Command.LOOK_AROUND, new ArrayList<String>());
      gameView.updateGameScreen("");
      gameView.displayPopupMessage(result, "LookAround");
      checkNextTurn();
    } catch (IllegalArgumentException ie) {
      gameView.displayPopupMessage(ie.getMessage(), "Error");
    } catch (IllegalStateException ie) {
      gameView.displayPopupMessage(ie.getMessage(), "Error");
    }
  }

  @Override
  public void attackTarget(String itemName) throws IllegalArgumentException {
    if (itemName == null) {
      throw new IllegalArgumentException("Item name cannot be null");
    }

    if (itemName.length() == 0) {
      throw new IllegalArgumentException("Item name cannot be empty");
    }
    try {
      String result = executeCmd(gameModel, Command.ATTACK_TARGET,
          new ArrayList<String>(Arrays.asList(itemName)));
      gameView.updateGameScreen(result);
      checkNextTurn();
    } catch (IllegalArgumentException ie) {
      gameView.displayPopupMessage(ie.getMessage(), "Error");
    } catch (IllegalStateException ie) {
      gameView.displayPopupMessage(ie.getMessage(), "Error");
    }
  }

  @Override
  public void movePet(String spaceName) throws IllegalArgumentException {
    if (spaceName == null) {
      throw new IllegalArgumentException("Space name cannot be null");
    }
    if (spaceName.length() == 0) {
      throw new IllegalArgumentException("Space name cannot be empty");
    }
    try {
      String result = executeCmd(gameModel, Command.MOVE_PET,
          new ArrayList<String>(Arrays.asList(spaceName)));
      gameView.updateGameScreen(result);
      checkNextTurn();
    } catch (IllegalArgumentException ie) {
      gameView.displayPopupMessage(ie.getMessage(), "Error");
    } catch (IllegalStateException ie) {
      gameView.displayPopupMessage(ie.getMessage(), "Error");
    }
  }

  @Override
  public void addPlayerIsClicked() {
    try {
      gameView.displayAddPlayerPopup(this);
    } catch (IllegalArgumentException ie) {
      gameView.displayPopupMessage(ie.getMessage(), "Error");
    } catch (IllegalStateException ie) {
      gameView.displayPopupMessage(ie.getMessage(), "Error");
    }
  }

  @Override
  public void pickItemIsPressed() {
    try {
      gameView.displayPickItemPopup(this);
    } catch (IllegalArgumentException ie) {
      gameView.displayPopupMessage(ie.getMessage(), "Error");
    } catch (IllegalStateException ie) {
      gameView.displayPopupMessage(ie.getMessage(), "Error");
    }
  }

  @Override
  public void attackTargetIsPressed() {
    try {
      gameView.displayAttackTargetPopup(this);
    } catch (IllegalArgumentException ie) {
      gameView.displayPopupMessage(ie.getMessage(), "Error");
    } catch (IllegalStateException ie) {
      gameView.displayPopupMessage(ie.getMessage(), "Error");
    }
  }

  @Override
  public void movePetIsPressed() {
    try {
      gameView.displayMovePetPopup(this);
    } catch (IllegalArgumentException ie) {
      gameView.displayPopupMessage(ie.getMessage(), "Error");
    } catch (IllegalStateException ie) {
      gameView.displayPopupMessage(ie.getMessage(), "Error");
    }

  }

  @Override
  public void newWorldIsClicked() {
    try {
      gameView.displayFileChooser(this);
    } catch (IllegalArgumentException ie) {
      gameView.displayPopupMessage(ie.getMessage(), "Error");
    } catch (IllegalStateException ie) {
      gameView.displayPopupMessage(ie.getMessage(), "Error");
    }
  }

  @Override
  public void exitIsClicked() {
    try {
      gameView.exitGame();
    } catch (IllegalArgumentException ie) {
      gameView.displayPopupMessage(ie.getMessage(), "Error");
    } catch (IllegalStateException ie) {
      gameView.displayPopupMessage(ie.getMessage(), "Error");
    }
  }

}
