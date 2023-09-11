package controllertest;

import controller.Features;
import view.GameView;

/**
 * Creates a temporary view to test the inputs and outputs from controller.
 */
public class MockView implements GameView {

  private final StringBuffer out;
  private final String uniqueCode;

  /**
   * A contructor which constructs the instance of MockView which represents a
   * mock view used for testing purposes.
   * 
   * @param out        The log to append the input received by the mock methods of
   *                   the mock view class
   * @param uniqueCode A code which is used to verify if the mock class instance
   *                   is the same one as expected
   */
  public MockView(StringBuffer out, String uniqueCode) {
    this.out = out;
    this.uniqueCode = uniqueCode;
  }

  @Override
  public void displayWelcomeScreen(Features featuresController) throws IllegalArgumentException {
    this.out.append(
        String.format("Mock called from displayWelcomeScreen. uniqueCode: %s", this.uniqueCode));
  }

  @Override
  public void displayAddPlayerScreen(Features featuresController) throws IllegalArgumentException {
    this.out.append(
        String.format("Mock called from displayAddPlayerScreen. uniqueCode: %s", this.uniqueCode));
  }

  @Override
  public void displayPopupMessage(String message, String type) throws IllegalArgumentException {
    this.out.append(
        String.format(
            "Mock called from displayPopupMessage. message: %s, type: %s, uniqueCode: %s",
            message, type, this.uniqueCode));
  }

  @Override
  public void displayGameScreen(Features featuresController) throws IllegalArgumentException {
    this.out.append(
        String.format("Mock called from displayGameScreen. uniqueCode: %s", this.uniqueCode));
  }

  @Override
  public void updateGameScreen(String message) throws IllegalArgumentException {
    this.out
        .append(String.format("Mock called from updateGameScreen. message : %s, UniqueCode:  %s",
            message, this.uniqueCode));
  }

  @Override
  public void setFeatures(Features featuresController) throws IllegalArgumentException {
    this.out.append(String.format("Mock called from setFeatures. uniqueCode: %s", this.uniqueCode));
  }

  @Override
  public void displayAddPlayerPopup(Features featuresController) throws IllegalArgumentException {
    this.out.append(
        String.format("Mock called from displayAddPlayerPopup. uniqueCode: %s", this.uniqueCode));
  }

  @Override
  public void displayPickItemPopup(Features featuresController) throws IllegalArgumentException {
    this.out.append(
        String.format("Mock called from displayPickItemPopup. uniqueCode: %s", this.uniqueCode));
  }

  @Override
  public void displayAttackTargetPopup(Features featuresController)
      throws IllegalArgumentException {
    this.out.append(String.format("Mock called from displayAttackTargetPopup. uniqueCode: %s",
        this.uniqueCode));
  }

  @Override
  public void displayMovePetPopup(Features featuresController) throws IllegalArgumentException {
    this.out.append(
        String.format("Mock called from displayMovePetPopup. uniqueCode: %s", this.uniqueCode));
  }

  @Override
  public void exitGame() {
    this.out.append(String.format("Mock called from exitGame. uniqueCode: %s", this.uniqueCode));
  }

  @Override
  public void displayFileChooser(Features featuresController) throws IllegalArgumentException {
    this.out.append(
        String.format("Mock called from displayFileChooser. uniqueCode: %s", this.uniqueCode));
  }

}
