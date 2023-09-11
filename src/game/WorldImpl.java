package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;
import java.util.function.BiFunction;
import javax.imageio.ImageIO;
import utils.RandomManual;

/**
 * It is a generic implementation of the world interface made up of spaces.
 */
public final class WorldImpl implements World {

  private final int scaleFactor;
  private final int buffer;

  private int rows;
  private int columns;
  private String name;
  private List<Space> allSpaces;
  private Target target;
  private Pet pet;
  private List<Player> allPlayers;
  private int currentTurnIndex;
  private final RandomManual random;
  private boolean gameOver;
  private Stack<Space> currentSpacesTrack;
  private List<Space> visitedSpaces;
  private int numOfTurns;
  private Map<Integer, BiFunction<Space, Player, String>> computerTurn;

  /**
   * Constructs the instance of the world with the given data, with specified
   * rows, columns, items and spaces.
   * 
   * @param worldData  The file which contains the world specification
   * @param random     It is used to generate random numbers
   * @param numOfTurns Total Number of turns for each player.
   * @throws IllegalArgumentException When worldData or random is null or when
   *                                  numOfTurns is less than one
   */
  public WorldImpl(Readable worldData, RandomManual random, int numOfTurns)
      throws IllegalArgumentException, InputMismatchException, NoSuchElementException {
    if (worldData == null) {
      throw new IllegalArgumentException("World data cannot be null.");
    }

    if (random == null) {
      throw new IllegalArgumentException("RandomManual object cannot be null.");
    }

    if (numOfTurns <= 0) {
      throw new IllegalArgumentException("Number of turns cannot be less than one");
    }

    setWorldSpecification(worldData);

    this.random = random;
    this.numOfTurns = numOfTurns;
    this.scaleFactor = 20;
    this.buffer = 30;
  }

  @Override
  public void setWorldSpecification(Readable worldData)
      throws IllegalArgumentException, NoSuchElementException, InputMismatchException {

    if (worldData == null) {
      throw new IllegalArgumentException("World data cannot be empty");
    }

    Scanner mansionScanner = new Scanner(worldData);

    final int rows = mansionScanner.nextInt();
    final int columns = mansionScanner.nextInt();
    final String worldName = mansionScanner.nextLine().trim();
    final int targetHealth = mansionScanner.nextInt();
    final String targetName = mansionScanner.nextLine().trim();
    final String petName = mansionScanner.nextLine().trim();

    int numberOfSpaces = mansionScanner.nextInt();
    mansionScanner.nextLine();
    List<String[]> tempSpaces = new ArrayList<String[]>();
    for (int i = 0; i < numberOfSpaces; i++) {
      String[] spaceDetails = { String.valueOf(mansionScanner.nextInt()),
          String.valueOf(mansionScanner.nextInt()), String.valueOf(mansionScanner.nextInt()),
          String.valueOf(mansionScanner.nextInt()),
          String.valueOf(mansionScanner.nextLine().trim()) };
      tempSpaces.add(spaceDetails);
    }

    int numberOfItems = mansionScanner.nextInt();
    mansionScanner.nextLine();
    Map<Integer, List<Item>> tempItems = new HashMap<>();
    for (int i = 0; i < numberOfItems; i++) {
      int key = mansionScanner.nextInt();
      int damage = mansionScanner.nextInt();
      String name = mansionScanner.nextLine().trim();
      if (tempItems.containsKey(key)) {
        tempItems.get(key).add(new ItemImpl(name, damage));
      } else {
        List<Item> itemsList = new ArrayList<>();
        itemsList.add(new ItemImpl(name, damage));
        tempItems.put(key, itemsList);
      }
    }

    mansionScanner.close();

    List<Space> allSpaces = new ArrayList<>();
    int index = 0;
    for (String[] tempSpace : tempSpaces) {
      allSpaces.add(new SpaceImpl(Integer.parseInt(tempSpace[0]), Integer.parseInt(tempSpace[1]),
          Integer.parseInt(tempSpace[2]), Integer.parseInt(tempSpace[3]), tempSpace[4],
          tempItems.get(index) != null ? tempItems.get(index) : new ArrayList<Item>()));
      index++;
    }

    if (rows <= 0) {
      throw new IllegalArgumentException("Rows cannot be less than zero");
    }

    if (columns <= 0) {
      throw new IllegalArgumentException("Columns cannot be less than zero");
    }

    if (worldName == null) {
      throw new IllegalArgumentException("World Name cannot be null");
    }

    if (worldName.length() == 0) {
      throw new IllegalArgumentException("World Name cannot be empty");
    }

    if (allSpaces.size() == 0) {
      throw new IllegalArgumentException("List of spaces cannot be empty");
    }

    if (targetName == null) {
      throw new IllegalArgumentException("Target Name cannot be null");
    }

    if (targetName.length() == 0) {
      throw new IllegalArgumentException("Target Name cannot be empty");
    }

    if (targetHealth <= 0) {
      throw new IllegalArgumentException("Target health cannot be less than one");
    }

    if (petName == null) {
      throw new IllegalArgumentException("Pet Name cannot be null");
    }

    if (petName.length() == 0) {
      throw new IllegalArgumentException("Pet Name cannot be empty");
    }

    for (int i = 0; i < allSpaces.size(); i++) {
      Space firstSpace = allSpaces.get(i);
      for (int j = 0; j < allSpaces.size(); j++) {
        Space secondSpace = allSpaces.get(j);
        if (secondSpace.equals(firstSpace)) {
          continue;
        }
        // checking if atleast one point of a space is inside or on the other space
        if ((firstSpace.getTopLeftX() >= secondSpace.getTopLeftX()
            && firstSpace.getTopLeftY() >= secondSpace.getTopLeftY()
            && firstSpace.getTopLeftX() <= secondSpace.getBottomRightX()
            && firstSpace.getTopLeftY() <= secondSpace.getBottomRightY())
            || (firstSpace.getBottomRightX() <= secondSpace.getBottomRightX()
                && firstSpace.getBottomRightY() <= secondSpace.getBottomRightY()
                && firstSpace.getBottomRightX() >= secondSpace.getTopLeftX()
                && firstSpace.getBottomRightY() >= secondSpace.getTopLeftY())
            || (firstSpace.getBottomRightX() >= secondSpace.getTopLeftX()
                && firstSpace.getTopLeftY() >= secondSpace.getTopLeftY()
                && firstSpace.getBottomRightX() <= secondSpace.getBottomRightX()
                && firstSpace.getTopLeftY() <= secondSpace.getBottomRightY())
            || (firstSpace.getTopLeftX() >= secondSpace.getTopLeftX()
                && firstSpace.getBottomRightY() >= secondSpace.getTopLeftY()
                && firstSpace.getTopLeftX() <= secondSpace.getBottomRightX()
                && firstSpace.getBottomRightY() <= secondSpace.getBottomRightY())) {
          throw new IllegalArgumentException("Spaces cannot overlap.");
        }
      }
    }

    final Target target = new TargetImpl(targetName, targetHealth);
    final Pet pet = new PetImpl(petName);

    this.rows = rows;
    this.columns = columns;
    this.name = worldName;
    this.allSpaces = allSpaces;
    this.target = target;
    this.pet = pet;
    this.allPlayers = new ArrayList<Player>();
    this.currentTurnIndex = 0;
    this.gameOver = false;
    this.currentSpacesTrack = new Stack<Space>();
    this.visitedSpaces = new ArrayList<Space>();

    this.computerTurn = new HashMap<Integer, BiFunction<Space, Player, String>>();
    
    this.computerTurn.put(Integer.valueOf(1), (currentPlayerSpace, currentPlayer) -> {
      List<Space> neighbours = getNeighboursAsList(currentPlayerSpace.getName());
      Space space = neighbours.get(currentPlayer.chooseAction(random, neighbours.size()));
      return movePlayerInWorld((space.getTopLeftY() * scaleFactor) + buffer,
          (space.getTopLeftX() * scaleFactor) + buffer);
    });

    this.computerTurn.put(Integer.valueOf(2), (currentPlayerSpace, currentPlayer) -> {
      List<Item> currentSpaceItems = currentPlayerSpace.getItemsInSpace();
      String itemName = currentSpaceItems
          .get(currentPlayer.chooseAction(random, currentSpaceItems.size())).getName();
      return pickItemByPlayer(itemName);
    });

    this.computerTurn.put(Integer.valueOf(3), (currentPlayerSpace, currentPlayer) -> {
      lookAroundByPlayer();
      return "Looking Around...";
    });

    this.computerTurn.put(Integer.valueOf(4), (currentPlayerSpace, currentPlayer) -> {
      String spaceNameForPetMovement = "";
      while (true) {
        spaceNameForPetMovement = allSpaces
            .get(currentPlayer.chooseAction(random, allSpaces.size())).getName();
        if (!spaceNameForPetMovement.equals(allSpaces.get(pet.getCurrentSpaceIndex()).getName())) {
          break;
        }
      }
      return movePetByPlayer(spaceNameForPetMovement);
    });

    this.computerTurn.put(Integer.valueOf(5), (currentPlayerSpace, currentPlayer) -> {
      List<Item> currentSpaceItems = currentPlayerSpace.getItemsInSpace();
      String itemName = currentSpaceItems
          .get(currentPlayer.chooseAction(random, currentSpaceItems.size())).getName();
      return pickItemByPlayer(itemName);
    });

  }

  @Override
  public int getRows() {
    return this.rows;
  }

  @Override
  public int getColumns() {
    return this.columns;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public String getCurrentSpaceOfTarget() {
    return this.allSpaces.get(target.getCurrentSpaceIndex()).getName();
  }

  private int getIndexOfSpace(String spaceName) {
    int index = -1;
    int count = 0;

    for (Space spaceInList : allSpaces) {
      if (spaceInList.getName().equals(spaceName)) {
        index = count;
        break;
      }
      count++;
    }

    return index;
  }

  @Override
  public String getInfoOfaSpace(String spaceName) throws IllegalArgumentException {

    if (spaceName == null) {
      throw new IllegalArgumentException("Space cannot be null.");
    }

    if (spaceName.length() == 0) {
      throw new IllegalArgumentException("Space cannot be empty.");
    }

    int spaceIndex = getIndexOfSpace(spaceName);

    if (spaceIndex == -1) {
      throw new IllegalArgumentException("The provided space is not part of the world");
    }

    Space currentSpace = allSpaces.get(spaceIndex);

    StringBuilder result = new StringBuilder();

    result.append("Name: " + spaceName + "\n").append("All Items in space:\n");

    List<Item> allItems = currentSpace.getItemsInSpace();
    if (allItems.size() == 0) {
      result.append("No items in space\n");
    } else {
      for (Item item : allItems) {
        result.append(item.toString());
        result.append("\n");
      }
    }

    result.append("All neighbouring spaces:\n");
    result.append(getNeighbours(spaceName));

    result.append("\nAll players in the space:\n");
    List<Player> playersInSpace = new ArrayList<Player>();

    for (Player player : allPlayers) {
      if (player.getSpaceIndexOfPlayer() == spaceIndex) {
        playersInSpace.add(player);
      }
    }

    if (playersInSpace.size() == 0) {
      result.append("No players in space\n");
    } else {
      result.append(playersInSpace.toString());
      result.append("\n");
    }

    if (spaceIndex == target.getCurrentSpaceIndex()) {
      result.append(
          String.format("The target %s is currently present in this space with health %d.\n",
              target.getName(), target.getHealth()));
    }

    if (spaceIndex == pet.getCurrentSpaceIndex()) {
      result
          .append(String.format("The pet %s is currently present in this space.\n", pet.getName()));
    }

    return result.toString();
  }

  private List<Space> getNeighboursAsList(String spaceName) throws IllegalArgumentException {

    int indexOfSpace = getIndexOfSpace(spaceName);

    if (indexOfSpace == -1) {
      throw new IllegalArgumentException("The provided space is not part of the world");
    }

    Space currentSpace = allSpaces.get(indexOfSpace);

    List<Space> neighbours = new ArrayList<Space>();

    int spaceIndex = -1;

    for (Space space : allSpaces) {
      spaceIndex = spaceIndex + 1;

      // checking if the space has one coordinate which is plus one of the current
      // space's coordinate and then checking that coordinate lies in between the
      // shared wall
      if (((currentSpace.getBottomRightY() + 1 == space.getTopLeftY()
          && space.getTopLeftX() < currentSpace.getBottomRightX()
          && space.getBottomRightX() > currentSpace.getTopLeftX())
          || (currentSpace.getBottomRightX() + 1 == space.getTopLeftX()
              && space.getTopLeftY() < currentSpace.getBottomRightY()
              && space.getBottomRightY() > currentSpace.getTopLeftY())
          || (currentSpace.getTopLeftY() - 1 == space.getBottomRightY()
              && space.getTopLeftX() < currentSpace.getBottomRightX()
              && space.getBottomRightX() > currentSpace.getTopLeftX())
          || (currentSpace.getTopLeftX() - 1 == space.getBottomRightX()
              && space.getTopLeftY() < currentSpace.getBottomRightY()
              && space.getBottomRightY() > currentSpace.getTopLeftY()))
          && pet.getCurrentSpaceIndex() != spaceIndex) {
        neighbours.add(space);
      }
    }

    return neighbours;
  }

  @Override
  public String getNeighbours(String spaceName) throws IllegalArgumentException {
    if (spaceName == null) {
      throw new IllegalArgumentException("Space name cannot be null.");
    }

    if (spaceName.length() == 0) {
      throw new IllegalArgumentException("Space name cannot be empty.");
    }

    List<Space> neighbours = getNeighboursAsList(spaceName);

    if (neighbours.size() > 0) {
      return neighbours.toString();
    }

    return "No neighbouring spaces";
  }

  /**
   * Returns a string representation of this World in the form "World(rows = 20,
   * columns = 30, name = House, Target name = Lucky, Number of spaces = 20)".
   * 
   * @return String format of this instance
   */
  @Override
  public String toString() {
    return String.format(
        "World(rows = %d, columns = %d, name = %s, Target name = %s, Number of spaces = %d)", rows,
        columns, name, target.getName(), allSpaces.size());
  }

  @Override
  public String drawImage() throws IllegalStateException {

    BufferedImage theWorldBuffer = new BufferedImage(800, 800, BufferedImage.TYPE_INT_RGB);

    Graphics2D theWorldGraphics = theWorldBuffer.createGraphics();

    theWorldGraphics.setColor(Color.white);
    theWorldGraphics.fillRect(0, 0, 800, 800);

    theWorldGraphics.setColor(Color.black);

    String path = "TheWorld.png";

    for (Space space : allSpaces) {
      theWorldGraphics.drawRect(space.getTopLeftY() * scaleFactor + buffer,
          space.getTopLeftX() * scaleFactor + buffer,
          (space.getBottomRightY() * scaleFactor - space.getTopLeftY() * scaleFactor) + scaleFactor,
          (space.getBottomRightX() * scaleFactor - space.getTopLeftX() * scaleFactor)
              + scaleFactor);

      String[] nameInParts = space.getName().split("\\s+");

      int spaceInX = 15;
      for (String s : nameInParts) {
        theWorldGraphics.drawString(s.trim(), space.getTopLeftY() * scaleFactor + buffer + 10,
            space.getTopLeftX() * scaleFactor + buffer + spaceInX);

        spaceInX += 15;

      }
    }

    theWorldGraphics.dispose();

    File theWorldFile = new File(path);

    try {
      ImageIO.write(theWorldBuffer, "png", theWorldFile);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }

    return path;
  }

  private void increaseTurnIndex(boolean movePetInDfs) {
    moveTarget();
    if (movePetInDfs) {
      movePet();
    }
    this.currentTurnIndex = this.currentTurnIndex + 1;

    if (this.currentTurnIndex >= this.allPlayers.size()) {
      this.currentTurnIndex = 0;
      numOfTurns = numOfTurns - 1;
    }

    if (numOfTurns <= 0) {
      gameOver = true;
    }
  }

  private void moveTarget() {
    int index = target.getCurrentSpaceIndex() + 1;
    if (index >= allSpaces.size()) {
      index = 0;
    }
    target.move(index);
  }

  private void movePet() {
    Space currentSpace = allSpaces.get(pet.getCurrentSpaceIndex());
    List<Space> allNeighbours = getNeighboursAsList(currentSpace.getName());
    List<Space> neighbours = new ArrayList<>();

    for (Space space : allNeighbours) {
      if (!visitedSpaces.contains(space)) {
        neighbours.add(space);
      }
    }

    if (neighbours.size() > 0) {
      for (int i = 0; i < allSpaces.size(); i++) {
        if (allSpaces.get(i).getName().equals(neighbours.get(0).getName())) {
          pet.movePet(i);
          if (currentSpacesTrack.search(currentSpace) == -1) {
            currentSpacesTrack.push(currentSpace);
          }
          if (!visitedSpaces.contains(currentSpace)) {
            visitedSpaces.add(currentSpace);
          }
          break;
        }
      }
    } else {
      if (!currentSpacesTrack.empty()) {
        for (int i = 0; i < allSpaces.size(); i++) {
          if (allSpaces.get(i).getName().equals(currentSpacesTrack.peek().getName())) {
            currentSpacesTrack.pop();
            pet.movePet(i);
            if (!visitedSpaces.contains(currentSpace)) {
              visitedSpaces.add(currentSpace);
            }
            break;
          }
        }
      } else {
        visitedSpaces.clear();
        movePet();
      }
    }
  }

  @Override
  public void addPlayer(String name, String startingSpaceName, boolean isHuman)
      throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null.");
    }

    if (name.length() == 0) {
      throw new IllegalArgumentException("Name cannot be empty string.");
    }

    if (startingSpaceName == null) {
      throw new IllegalArgumentException("Starting space name cannot be null.");
    }

    if (startingSpaceName.length() == 0) {
      throw new IllegalArgumentException("Starting space name cannot be empty string.");
    }

    for (Player player : allPlayers) {
      if (player.getName().equals(name)) {
        throw new IllegalArgumentException("A player with the same name already exists");
      }
    }

    int spaceIndex = getIndexOfSpace(startingSpaceName);

    if (spaceIndex == -1) {
      throw new IllegalArgumentException("The specified space name is not part of the world");
    }

    if (isHuman) {
      this.allPlayers.add(new PlayerImpl(name, spaceIndex));
    } else {
      this.allPlayers.add(new ComputerPlayer(name, spaceIndex));
    }
  }

  @Override
  public String getPlayerInfo(String name) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null.");
    }

    if (name.length() == 0) {
      throw new IllegalArgumentException("Name cannot be empty string.");
    }

    Player tempPlayer = null;

    for (Player player : allPlayers) {
      if (player.getName().equals(name)) {
        tempPlayer = player;
      }
    }

    if (tempPlayer == null) {
      throw new IllegalArgumentException("The specified player is not part of the world");
    }

    StringBuilder sb = new StringBuilder();
    sb.append("Name: ");
    sb.append(tempPlayer.getName());
    sb.append("\n");
    sb.append("Current space: ");
    sb.append(allSpaces.get(tempPlayer.getSpaceIndexOfPlayer()).getName());
    sb.append("\n");
    sb.append("Items carrying:\n");

    List<Item> playerItems = tempPlayer.getPlayerItems();

    if (playerItems.size() == 0) {
      sb.append("None");
    } else {
      sb.append(playerItems.toString());
    }

    sb.append("\nIs Human player: ");
    if (tempPlayer.getPlayerType().equals("Human")) {
      sb.append("Yes\n");
    } else if (tempPlayer.getPlayerType().equals("Computer")) {
      sb.append("No\n");
    }

    return sb.toString();
  }

  @Override
  public List<String> getTurnInfo() {
    Player currentPlayer = allPlayers.get(currentTurnIndex);
    Space currentSpace = allSpaces.get(currentPlayer.getSpaceIndexOfPlayer());
    Space currentTargetSpace = allSpaces.get(target.getCurrentSpaceIndex());

    return new ArrayList<String>(Arrays.asList(currentPlayer.getName(),
        currentPlayer.getPlayerType(), currentSpace.getName(), currentTargetSpace.getName()));
  }

  private boolean isNeighbour(List<Space> neighbours, String spaceName) {
    boolean neighbour = false;

    for (Space neighbouringSpace : neighbours) {
      if (neighbouringSpace.getName().equals(spaceName)) {
        neighbour = true;
        break;
      }
    }

    return neighbour;
  }

  private String getSpaceFromCoords(int xcoord, int ycoord) {

    String spaceName = "";

    int newy = (xcoord - buffer) / scaleFactor;
    int newx = (ycoord - buffer) / scaleFactor;

    for (Space space : allSpaces) {
      if (space.getTopLeftX() <= newx && space.getTopLeftY() <= newy
          && space.getBottomRightX() >= newx && space.getBottomRightY() >= newy) {
        spaceName = space.getName();
        break;
      }
    }

    return spaceName;
  }

  @Override
  public String movePlayerInWorld(int xcoord, int ycoord) throws IllegalArgumentException {
    if (xcoord < 0 || ycoord < 0) {
      throw new IllegalArgumentException("Coordinates cannot be negative.");
    }

    String spaceName = getSpaceFromCoords(xcoord, ycoord);

    if ("".equals(spaceName)) {
      return "";
    }

    Player currentPlayer = allPlayers.get(currentTurnIndex);
    String currentSpaceName = allSpaces.get(currentPlayer.getSpaceIndexOfPlayer()).getName();

    List<Space> neighbours = getNeighboursAsList(currentSpaceName);

    boolean isNeighbour = isNeighbour(neighbours, spaceName);

    if (!isNeighbour) {
      throw new IllegalStateException("Clicked space is not a neighbour of the current space");
    }

    int spaceIndex = 0;

    for (int i = 0; i < allSpaces.size(); i++) {
      if (allSpaces.get(i).getName().equals(spaceName)) {
        spaceIndex = i;
        break;
      }
    }

    currentPlayer.movePlayer(spaceIndex);
    increaseTurnIndex(true);

    return String.format("The Player %s has moved to the space %s\n", currentPlayer.getName(),
        spaceName);
  }

  @Override
  public String pickItemByPlayer(String itemName)
      throws IllegalArgumentException, IllegalStateException {
    if (itemName == null) {
      throw new IllegalArgumentException("Item name cannot be null");
    }

    if (itemName.length() == 0) {
      throw new IllegalArgumentException("Item name cannot be empty");
    }

    Player currentPlayer = allPlayers.get(currentTurnIndex);
    Space currentPlayerSpace = allSpaces.get(currentPlayer.getSpaceIndexOfPlayer());

    boolean itemPicked = false;

    List<Item> itemsListInSpace = currentPlayerSpace.getItemsInSpace();

    for (Item item : itemsListInSpace) {
      if (item.getName().equals(itemName)) {
        currentPlayer.pickItem(item);
        currentPlayerSpace.removeItemFromSpace(itemName);
        itemPicked = true;
        break;
      }
    }

    if (!itemPicked) {
      throw new IllegalStateException(
          "Item is not present in the space the player is currently in.");
    }

    increaseTurnIndex(true);
    return String.format("The Player %s has picked up the item %s from the space %s\n",
        currentPlayer.getName(), itemName, currentPlayerSpace.getName());
  }

  @Override
  public String lookAroundByPlayer() {
    Player currentPlayer = allPlayers.get(currentTurnIndex);

    StringBuilder sb = new StringBuilder();
    sb.append("Looking around...\n");
    sb.append("Current space: \n");
    sb.append(getInfoOfaSpace(allSpaces.get(currentPlayer.getSpaceIndexOfPlayer()).getName()));
    sb.append("\nNeighbouring spaces:\n");

    List<Space> neighbouringSpaces = getNeighboursAsList(
        allSpaces.get(currentPlayer.getSpaceIndexOfPlayer()).getName());

    for (Space neighbour : neighbouringSpaces) {
      sb.append(getInfoOfaSpace(neighbour.getName()));
      sb.append("\n");
    }

    increaseTurnIndex(true);
    return sb.toString();
  }

  @Override
  public String performComputerAction() {
    Player currentPlayer = allPlayers.get(currentTurnIndex);

    if (currentPlayer.getPlayerType().equals("Human")) {
      throw new IllegalStateException("Cannot call this method as a human player");
    }

    String res = "";

    boolean visible = false;

    for (Player player : allPlayers) {
      if (!player.getName().equals(currentPlayer.getName())
          && isPlayerVisible(player.getName(), currentPlayer.getName())) {
        visible = true;
        break;
      }
    }

    if (target.getCurrentSpaceIndex() == currentPlayer.getSpaceIndexOfPlayer() && !visible) {
      List<Item> currentPlayerItems = currentPlayer.getPlayerItems();

      Item chosenItem = null;

      for (Item item : currentPlayerItems) {
        if (chosenItem == null || chosenItem.getDamage() < item.getDamage()) {
          chosenItem = item;
        }
      }

      String itemName = "hand";

      if (chosenItem != null) {
        itemName = chosenItem.getName();
      }

      res = attackTarget(itemName);

    } else {
      while (true) {
        int input = currentPlayer.chooseAction(this.random, 4) + 1;
        Space currentPlayerSpace = allSpaces.get(currentPlayer.getSpaceIndexOfPlayer());
        List<Item> currentSpaceItems = currentPlayerSpace.getItemsInSpace();
        if (input == 2 && (currentSpaceItems.size() == 0
            || currentPlayer.getPlayerItems().size() == currentPlayer.getMaxNumOfItems())) {
          continue;
        }
        res = this.computerTurn.get(input).apply(currentPlayerSpace, currentPlayer);
        break;
      }
    }

    return res;
  }

  @Override
  public String movePetByPlayer(String spaceName) throws IllegalArgumentException {
    if (spaceName == null) {
      throw new IllegalArgumentException("Space name cannot be null");
    }

    if (spaceName.length() == 0) {
      throw new IllegalArgumentException("Space name cannot be empty");
    }

    Space currentPetSpace = allSpaces.get(pet.getCurrentSpaceIndex());

    if (currentPetSpace.getName().equals(spaceName)) {
      throw new IllegalArgumentException("Pet is already in the specified space");
    }

    int spaceIndex = getIndexOfSpace(spaceName);

    if (spaceIndex == -1) {
      throw new IllegalArgumentException("The given space is not part of the world.");
    }

    pet.movePet(spaceIndex);
    currentSpacesTrack.clear();
    visitedSpaces.clear();
    increaseTurnIndex(false);
    return String.format("Pet %s has been moved to the space %s\n", pet.getName(),
        allSpaces.get(spaceIndex).getName());
  }

  private void removeItemFromPlayer(Player player, String itemName) {
    player.removeItem(itemName);
  }

  @Override
  public String attackTarget(String itemName) throws IllegalArgumentException {
    if (itemName == null) {
      throw new IllegalArgumentException("Item name cannot be null");
    }

    if (itemName.length() == 0) {
      throw new IllegalArgumentException("Item name cannot be empty");
    }

    String msg = "";
    Player currentPlayer = allPlayers.get(currentTurnIndex);

    if (currentPlayer.getSpaceIndexOfPlayer() != target.getCurrentSpaceIndex()) {
      throw new IllegalArgumentException(
          "The current turn player and the target character are not in the same space");
    }

    Item itemChosen = null;

    List<Item> playerItems = currentPlayer.getPlayerItems();

    for (Item item : playerItems) {
      if (item.getName().equals(itemName)) {
        itemChosen = item;
        break;
      }
    }

    if (itemChosen == null && !"hand".equals(itemName)) {
      throw new IllegalArgumentException("Item does not exist with the player.");
    }

    boolean visible = false;

    for (Player playerInList : allPlayers) {
      if (!playerInList.equals(currentPlayer)
          && isPlayerVisible(playerInList.getName(), currentPlayer.getName())) {
        visible = true;
        break;
      }
    }

    if (visible) {
      msg = "Attack failed! The attack was seen by another player. Removing item...\n";
    } else {
      if ("hand".equals(itemName)) {
        target.decreaseHealth(1);
      } else {
        target.decreaseHealth(itemChosen.getDamage());
      }

      if (target.getHealth() <= 0) {
        gameOver = true;
        msg = String.format("Attack completed! The target is dead. Player %s has won the game!\n",
            currentPlayer.getName());
      } else {
        msg = "Attack completed! The target's health has decreased. Removing item...\n";
      }
    }

    if (!"hand".equals(itemName)) {
      removeItemFromPlayer(currentPlayer, itemName);
    }

    if (!gameOver) {
      increaseTurnIndex(true);
    }

    return msg;
  }

  @Override
  public boolean isPlayerVisible(String firstPlayername, String secondPlayerName)
      throws IllegalArgumentException {

    if (firstPlayername == null || secondPlayerName == null) {
      throw new IllegalArgumentException("Player names cannot be null");
    }

    if (firstPlayername.length() == 0 || secondPlayerName.length() == 0) {
      throw new IllegalArgumentException("Player names cannot be empty");
    }

    Player playerOne = null;
    Player playerTwo = null;

    for (Player player : allPlayers) {

      if (playerOne == null && player.getName().equals(firstPlayername)) {
        playerOne = player;
      }

      if (playerTwo == null && player.getName().equals(secondPlayerName)) {
        playerTwo = player;
      }
    }

    if (playerOne == null) {
      throw new IllegalArgumentException("First Player does not exist in the world.");
    }

    if (playerTwo == null) {
      throw new IllegalArgumentException("Second Player does not exist in the world.");
    }

    Space firstPlayerSpace = allSpaces.get(playerOne.getSpaceIndexOfPlayer());
    Space secondPlayerSpace = allSpaces.get(playerTwo.getSpaceIndexOfPlayer());

    if (firstPlayerSpace.equals(secondPlayerSpace)) {
      return true;
    }

    List<Space> neighbours = getNeighboursAsList(firstPlayerSpace.getName());

    boolean isNeighbour = isNeighbour(neighbours, secondPlayerSpace.getName());

    if (isNeighbour) {
      return true;
    }

    return false;
  }

  @Override
  public String getCurrentSpaceOfPet() {
    return allSpaces.get(pet.getCurrentSpaceIndex()).getName();
  }

  @Override
  public String isGameOver() {
    String message = "";
    if (gameOver) {
      if (numOfTurns > 0) {
        message = String.format("Game is completed. %s has won the game!",
            allPlayers.get(currentTurnIndex).getName());
      } else {
        message = "Game ended in a draw!";
      }
    }
    return message;
  }

  @Override
  public String[][] getAllPlayers() {
    List<List<String>> result = new ArrayList<List<String>>();
    for (Player player : this.allPlayers) {
      List<String> data = new ArrayList<String>();
      data.add(player.getName());
      data.add(this.allSpaces.get(player.getSpaceIndexOfPlayer()).getName());
      data.add(player.getPlayerType());
      result.add(data);
    }
    return this.allPlayers.stream()
        .map((element) -> new String[] { element.getName(),
            this.allSpaces.get(element.getSpaceIndexOfPlayer()).getName(),
            element.getPlayerType() })
        .toArray(String[][]::new);
  }

  @Override
  public int getNumOfTurns() {
    return numOfTurns;
  }

  @Override
  public String[] getAllSpaces() {
    return this.allSpaces.stream().map((space) -> space.getName()).toArray(String[]::new);
  }

  @Override
  public int[] getCoordinates(String spaceName) {
    if (spaceName == null) {
      throw new IllegalArgumentException("Space Name cant be null");
    }
    int spaceIndex = this.getIndexOfSpace(spaceName);
    if (spaceIndex == -1) {
      throw new IllegalArgumentException("Space Doesnt Exist");
    }
    return new int[] { this.allSpaces.get(spaceIndex).getTopLeftX(),
        this.allSpaces.get(spaceIndex).getTopLeftY(),
        this.allSpaces.get(spaceIndex).getBottomRightX(),
        this.allSpaces.get(spaceIndex).getBottomRightY() };
  }

  private String[] getItemNamesFromList(List<Item> items) {
    List<String> itemNames = new ArrayList<String>();

    for (Item item : items) {
      itemNames.add(item.getName());
    }

    String[] names = new String[itemNames.size()];

    return itemNames.toArray(names);
  }

  @Override
  public String[] getCurrentSpaceItems() {
    int currentSpaceIndex = allPlayers.get(currentTurnIndex).getSpaceIndexOfPlayer();
    List<Item> currentSpaceItems = allSpaces.get(currentSpaceIndex).getItemsInSpace();

    return getItemNamesFromList(currentSpaceItems);
  }

  @Override
  public String[] getCurrentPlayerItems() {
    List<Item> currentPlayerItems = allPlayers.get(currentTurnIndex).getPlayerItems();
    String[] names = getItemNamesFromList(currentPlayerItems);
    String[] itemsNames = new String[names.length + 1];

    for (int i = 0; i < itemsNames.length - 1; i++) {
      itemsNames[i] = names[i];
    }

    itemsNames[itemsNames.length - 1] = "hand";

    return itemsNames;
  }

}
