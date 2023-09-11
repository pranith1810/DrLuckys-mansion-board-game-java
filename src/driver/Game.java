package driver;

import controller.GameController;
import controller.GameControllerImpl;
import game.GameWorld;
import game.WorldImpl;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.NoSuchElementException;
import utils.RandomManual;
import view.GameView;
import view.GameViewImpl;

/**
 * A driver class for starting the game.
 */
public class Game {

  /**
   * Main method of the driver class which starts the game.
   * 
   * @param args command line arguments which contains filename and number of
   *             turns
   */
  public static void main(String[] args) {
    try {
      if (args.length == 2) {
        String path = args[0];

        System.out.println(String.format("Reading the world specifications from %s...\n", path));

        Readable mansionReadable = new FileReader(path);

        RandomManual rand = new RandomManual();

        int numOfTurns = Integer.parseInt(args[1]);

        System.out.println("Creating the world...\n");

        GameWorld world = new WorldImpl(mansionReadable, rand, numOfTurns);
        GameView gameView = new GameViewImpl("Killing Dr. Lucky - Board Game", world);

        GameController gameController = new GameControllerImpl(world, gameView);
        gameController.startGame();

      } else {
        System.out.println("Please specify the correct command.");
      }
    } catch (FileNotFoundException fne) {
      System.out.println("Unable to find the world specification text file.");
    } catch (NumberFormatException e) {
      System.out.println("Please specify a number for the maximum turns in the game.");
    } catch (IllegalArgumentException | NoSuchElementException ne) {
      System.out.println(ne.getMessage());
    }
  }

}
