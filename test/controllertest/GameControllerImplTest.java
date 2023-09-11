package controllertest;

import static org.junit.Assert.assertEquals;

import controller.Features;
import controller.GameControllerImpl;
import game.GameWorld;
import org.junit.Before;
import org.junit.Test;
import view.GameView;

/**
 * A test class for testing GameControllerImpl class.
 */
public class GameControllerImplTest {

  private StringBuffer out;
  private StringBuffer outModelEx;
  private StringBuffer outViewEx;
  private String uniqueCodeOne;
  private String uniqueCodeTwo;
  private String uniqueCodeThree;
  private GameWorld mockModel;
  private GameView gameView;
  private Features controller;
  private Features modelExController;
  private Features viewExController;
  private GameView mockViewForModelEx;
  private GameWorld exceptionModel;
  private GameWorld mockModelForViewEx;
  private GameView exceptionView;

  /**
   * Initializes all the fields of GameControllerImplTest.
   */
  @Before
  public void setup() {
    out = new StringBuffer();
    outModelEx = new StringBuffer();
    outViewEx = new StringBuffer();

    uniqueCodeOne = "12344";
    uniqueCodeTwo = "12345";
    uniqueCodeThree = "12346";

    mockModel = new MockGameWorld(out, uniqueCodeOne);
    gameView = new MockView(out, uniqueCodeOne);
    controller = new GameControllerImpl(mockModel, gameView);

    exceptionModel = new MockGameWorldExceptions();
    mockViewForModelEx = new MockView(outModelEx, uniqueCodeTwo);
    modelExController = new GameControllerImpl(exceptionModel, mockViewForModelEx);

    mockModelForViewEx = new MockGameWorld(outViewEx, uniqueCodeThree);
    exceptionView = new MockExceptionView(outViewEx, uniqueCodeThree);
    viewExController = new GameControllerImpl(mockModelForViewEx, exceptionView);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForNullModel() {
    new GameControllerImpl(null, gameView);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForNullView() {
    new GameControllerImpl(mockModel, null);
  }

  @Test
  public void spaceIsClickedTest() {
    this.controller.spaceIsClicked(90, 90);
    assertEquals("Mock called from setFeatures. uniqueCode: 12344" + "Input: 90, 90"
        + "Mock called from updateGameScreen. message : "
        + "Mock result for move player, Code: 12344, UniqueCode:  12344"
        + "Mock called from displayPopupMessage. message: "
        + "Mock for game over, Code: 12344, type: , uniqueCode: 12344"
        + "Mock called from exitGame. uniqueCode: 12344", this.out.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void spaceIsClickedTestIllegalArg() {
    this.controller.spaceIsClicked(-90, -90);
  }

  @Test
  public void startGameIsClickedTest() {
    this.controller.startGameIsClicked();
    assertEquals("Mock called from setFeatures. uniqueCode: 12344"
        + "Mock called from displayAddPlayerScreen. uniqueCode: 12344", this.out.toString());
  }

  @Test
  public void lookAroundTest() {
    this.controller.lookAround();
    assertEquals("Mock called from setFeatures. uniqueCode: 12344"
        + "Mock called from updateGameScreen. message : , UniqueCode:  12344"
        + "Mock called from displayPopupMessage. message: Output: "
        + "Mock result for look around, code: 12344\n" + ", type: LookAround, uniqueCode: 12344"
        + "Mock called from displayPopupMessage. message: "
        + "Mock for game over, Code: 12344, type: , uniqueCode: 12344"
        + "Mock called from exitGame. uniqueCode: 12344", this.out.toString());
  }

  @Test
  public void showGameScreenTest() {
    this.controller.showGameScreen();
    assertEquals("Mock called from setFeatures. uniqueCode: 12344"
        + "Mock called from displayGameScreen. uniqueCode: 12344"
        + "Mock called from displayPopupMessage. message: "
        + "Mock for game over, Code: 12344, type: , uniqueCode: 12344"
        + "Mock called from exitGame. uniqueCode: 12344", this.out.toString());
  }

  @Test
  public void pickItemTest() {
    this.controller.pickItem("weapon");
    assertEquals("Mock called from setFeatures. uniqueCode: 12344"
        + "Input: weaponMock called from updateGameScreen."
        + " message : Player has picked up the Item weapon " + "successfully, code: 12344\n"
        + ", UniqueCode:  12344Mock called from displayPopupMessage. "
        + "message: Mock for game over, Code: 12344, type: , uniqueCode: 12344"
        + "Mock called from exitGame. uniqueCode: 12344", this.out.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void pickItemTestIllegalArg() {
    this.controller.pickItem("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void pickItemTestNullArg() {
    this.controller.pickItem(null);
  }

  @Test
  public void setFeaturesTest() {
    controller = new GameControllerImpl(mockModel, gameView);
    assertEquals("Mock called from setFeatures. uniqueCode: 12344"
        + "Mock called from setFeatures. uniqueCode: 12344", this.out.toString());
  }

  @Test
  public void addPlayerIsClickedTest() {
    this.controller.addPlayerIsClicked();
    assertEquals("Mock called from setFeatures. uniqueCode: 12344"
        + "Mock called from displayAddPlayerPopup. uniqueCode: 12344", this.out.toString());
  }

  @Test
  public void pickItemIsPressedTest() {
    this.controller.pickItemIsPressed();
    ;
    assertEquals("Mock called from setFeatures. uniqueCode: 12344"
        + "Mock called from displayPickItemPopup. uniqueCode: 12344", this.out.toString());
  }

  @Test
  public void attackTargetIsPressedTest() {
    this.controller.attackTargetIsPressed();
    assertEquals(
        "Mock called from setFeatures. uniqueCode: 12344"
            + "Mock called from displayAttackTargetPopup. " + "uniqueCode: 12344",
        this.out.toString());
  }

  @Test
  public void updateWorldSpecification() {
    String world = "Test world";
    this.controller.updateWorldSpecification(world);
    assertEquals("Mock called from setFeatures. uniqueCode: 12344", this.out.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void updateWorldSpecificationIllegalArg() {
    this.controller.updateWorldSpecification("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void updateWorldSpecificationNullArg() {
    this.controller.updateWorldSpecification(null);
  }

  @Test
  public void addPlayerTest() {
    this.controller.addPlayer("Mock name", "Mock space", true);
    assertEquals(
        "Mock called from setFeatures. uniqueCode: 12344Received input: "
            + "(Name: Mock name, Starting space: Mock space, isHuman: true)\n",
        this.out.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void addPlayerTestEmptyName() {
    this.controller.addPlayer("", "Mock space", true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addPlayerTestNullName() {
    this.controller.addPlayer(null, "Mock space", true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addPlayerTestEmptySpace() {
    this.controller.addPlayer("Mock name", "", true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addPlayerTestNullSpace() {
    this.controller.addPlayer("Mock name", null, true);
  }

  @Test
  public void attackTargetTest() {
    this.controller.attackTarget("Mock item");
    assertEquals("Mock called from setFeatures. uniqueCode: 12344Input: Mock itemMock called "
        + "from updateGameScreen. message : Player has attacked the target with the item Mock "
        + "item, code: 12344\n"
        + ", UniqueCode:  12344Mock called from displayPopupMessage. message: Mock for game over, "
        + "Code: 12344, " + "type: , uniqueCode: 12344Mock called from exitGame. uniqueCode: 12344",
        this.out.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void attacktargetTestEmptyName() {
    this.controller.attackTarget("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void attacktargetTestNullName() {
    this.controller.attackTarget(null);
  }

  @Test
  public void movePetTest() {
    this.controller.attackTarget("Mock space");
    assertEquals("Mock called from setFeatures. uniqueCode: 12344Input: Mock spaceMock called "
        + "from updateGameScreen."
        + " message : Player has attacked the target with the item Mock space, code: 12344\n"
        + ", UniqueCode:  12344Mock called from displayPopupMessage. message: Mock "
        + "for game over, Code: 12344, type: , "
        + "uniqueCode: 12344Mock called from exitGame. uniqueCode: 12344", this.out.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void movePetTestEmptyName() {
    this.controller.movePet("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void movePetTestNullName() {
    this.controller.movePet(null);
  }

  @Test
  public void newWorldIsClickedTest() {
    this.controller.newWorldIsClicked();
    assertEquals("Mock called from setFeatures. uniqueCode: 12344Mock called from "
        + "displayFileChooser. uniqueCode: 12344", this.out.toString());
  }

  @Test
  public void exitIsClicked() {
    this.controller.exitIsClicked();
    assertEquals("Mock called from setFeatures. uniqueCode: 12344Mock called "
        + "from exitGame. uniqueCode: 12344", this.out.toString());
  }

  @Test
  public void movePetIsPressed() {
    this.controller.movePetIsPressed();
    assertEquals("Mock called from setFeatures. uniqueCode: 12344Mock "
        + "called from displayMovePetPopup. uniqueCode: 12344", this.out.toString());
  }

  @Test
  public void updateWorldSpecModelExceptiontest() {
    this.modelExController.updateWorldSpecification("World");
    assertEquals("Mock called from setFeatures. uniqueCode: 12345", this.outModelEx.toString());
  }

  @Test
  public void addPlayerModelExceptiontest() {
    this.modelExController.addPlayer("Mock player", "Mock space", true);
    assertEquals("Mock called from setFeatures. uniqueCode: 12345Mock called from "
        + "displayPopupMessage. message: Test of how controller handles "
        + "AddPlayer exceptions., type: Error, uniqueCode: 12345", this.outModelEx.toString());
  }

  @Test
  public void spaceIsClickedModelExceptiontest() {
    this.modelExController.spaceIsClicked(80, 80);
    assertEquals(
        "Mock called from setFeatures. uniqueCode: 12345Mock called from displayPopupMessage. "
            + "message: Test for how controller handle movePlayerInWorld exception, "
            + "type: Error, uniqueCode: 12345",
        this.outModelEx.toString());
  }

  @Test
  public void spaceIsClickedViewExceptiontest() {
    this.viewExController.spaceIsClicked(80, 80);
    assertEquals(
        "Mock called from setFeatures. uniqueCode: 12346Input: 80, 80message: "
            + "IllegalArgumentException for updateGameScreen, type: Error",
        this.outViewEx.toString());
  }

  @Test
  public void pickItemModelExceptiontest() {
    this.modelExController.pickItem("Mock item");
    assertEquals(
        "Mock called from setFeatures. uniqueCode: 12345Mock called from displayPopupMessage. "
            + "message: Test of how controller handles PickItem illegal argument exceptions., "
            + "type: Error, uniqueCode: 12345",
        this.outModelEx.toString());
  }

  @Test
  public void pickItemViewExceptiontest() {
    this.viewExController.pickItem("Mock item");
    assertEquals(
        "Mock called from setFeatures. uniqueCode: 12346Input: Mock itemmessage: "
            + "IllegalArgumentException for updateGameScreen, type: Error",
        this.outViewEx.toString());
  }

  @Test
  public void attackTargetModelExceptiontest() {
    this.modelExController.attackTarget("Mock item");
    assertEquals(
        "Mock called from setFeatures. uniqueCode: 12345Mock called from "
            + "displayPopupMessage. message: Test of how controller handles AttackTarget's "
            + "illegal argument exceptions., type: Error, uniqueCode: 12345",
        this.outModelEx.toString());
  }

  @Test
  public void attackTargetViewExceptiontest() {
    this.viewExController.pickItem("Mock item");
    assertEquals(
        "Mock called from setFeatures. uniqueCode: 12346Input: Mock itemmessage: "
            + "IllegalArgumentException for updateGameScreen, type: Error",
        this.outViewEx.toString());
  }

  @Test
  public void movePetModelExceptiontest() {
    this.modelExController.movePet("Mock space");
    assertEquals(
        "Mock called from setFeatures. uniqueCode: 12345Mock called from displayPopupMessage. "
            + "message: Test of how controller handles MovePet exceptions., type: Error, "
            + "uniqueCode: 12345",
        this.outModelEx.toString());
  }

  @Test
  public void movePetViewExceptiontest() {
    this.viewExController.movePet("Mock space");
    assertEquals(
        "Mock called from setFeatures. uniqueCode: 12346Input: Mock spacemessage: "
            + "IllegalArgumentException for updateGameScreen, type: Error",
        this.outViewEx.toString());
  }

  @Test
  public void lookAroundModelExceptiontest() {
    this.modelExController.lookAround();
    assertEquals(
        "Mock called from setFeatures. uniqueCode: 12345Mock called from updateGameScreen. "
            + "message : , UniqueCode:  12345Mock called from displayPopupMessage. message: , "
            + "type: LookAround, uniqueCode: 12345",
        this.outModelEx.toString());
  }

  @Test
  public void lookAroundViewExceptiontest() {
    this.viewExController.lookAround();
    assertEquals("Mock called from setFeatures. uniqueCode: 12346message: IllegalArgumentException "
        + "for updateGameScreen, type: Error", this.outViewEx.toString());
  }

  @Test
  public void startGameIsClickedModelExceptiontest() {
    this.modelExController.startGameIsClicked();
    assertEquals(
        "Mock called from setFeatures. uniqueCode: 12345Mock called from displayAddPlayerScreen. "
            + "uniqueCode: 12345",
        this.outModelEx.toString());
  }

  @Test
  public void startGameIsClickedViewExceptiontest() {
    this.viewExController.lookAround();
    assertEquals("Mock called from setFeatures. uniqueCode: 12346message: IllegalArgumentException "
        + "for updateGameScreen, type: Error", this.outViewEx.toString());
  }

  @Test
  public void newWorldIsClickedViewExceptiontest() {
    this.viewExController.newWorldIsClicked();
    assertEquals("Mock called from setFeatures. uniqueCode: 12346message: IllegalArgumentException "
        + "for displayFileChooser, type: Error", this.outViewEx.toString());
  }

  @Test
  public void exitIsClickedViewExceptiontest() {
    this.viewExController.exitIsClicked();
    assertEquals("Mock called from setFeatures. uniqueCode: 12346message: IllegalArgumentException "
        + "for exitGame, type: Error", this.outViewEx.toString());
  }

  @Test
  public void addPlayerIsClickedViewExceptiontest() {
    this.viewExController.addPlayerIsClicked();
    assertEquals(
        "Mock called from setFeatures. uniqueCode: 12346message: IllegalArgumentException for "
            + "displayAddPlayerPopup, type: Error",
        this.outViewEx.toString());
  }

  @Test
  public void pickItemIsClickedViewExceptiontest() {
    this.viewExController.pickItemIsPressed();
    assertEquals(
        "Mock called from setFeatures. uniqueCode: 12346message: IllegalArgumentException for "
            + "displayPickItemPopup, type: Error",
        this.outViewEx.toString());
  }

  @Test
  public void attackTargetIsClickedViewExceptiontest() {
    this.viewExController.attackTargetIsPressed();
    assertEquals(
        "Mock called from setFeatures. uniqueCode: 12346message: IllegalArgumentException for "
            + "displayAttackTargetPopup, type: Error",
        this.outViewEx.toString());
  }

  @Test
  public void movePetIsClickedViewExceptiontest() {
    this.viewExController.movePetIsPressed();
    assertEquals("Mock called from setFeatures. uniqueCode: 12346message: IllegalArgumentException "
        + "for displayMovePetPopup, type: Error", this.outViewEx.toString());
  }

  @Test
  public void showGameScreenViewExceptiontest() {
    this.viewExController.showGameScreen();
    assertEquals(
        "Mock called from setFeatures. uniqueCode: 12346message: IllegalArgumentException for "
            + "displayGameScreen, type: Error",
        this.outViewEx.toString());
  }

}