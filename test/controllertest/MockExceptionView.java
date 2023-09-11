package controllertest;

import controller.Features;
import view.GameView;

/**
 * Creates a Mock View that only throws exceptions.
 */
public class MockExceptionView implements GameView {

  private final StringBuffer out;
  private final String uniqueCode;

  /**
   * A constructor which constructs the instance of MockExceptionView which
   * represents a mock view used for testing purposes.
   * 
   * @param out        The log to append the input received by the mock methods of
   *                   the mock view class
   * @param uniqueCode A code which is used to verify if the mock class instance
   *                   is the same one as expected
   */
  public MockExceptionView(StringBuffer out, String uniqueCode) {
    this.out = out;
    this.uniqueCode = uniqueCode;
  }

  @Override
  public void displayWelcomeScreen(Features featuresController) throws IllegalArgumentException {
    if (Integer.parseInt(this.uniqueCode) % 2 == 0) {
      throw new IllegalArgumentException("IllegalArgumentException for displayWelcomeScreen");
    } else {
      throw new IllegalStateException("IllegalStateException for displayWelcomeScreen");
    }
  }

  @Override
  public void displayAddPlayerScreen(Features featuresController) throws IllegalArgumentException {
    if (Integer.parseInt(this.uniqueCode) % 2 == 0) {
      throw new IllegalArgumentException("IllegalArgumentException for displayAddPlayerScreen");
    } else {
      throw new IllegalStateException("IllegalStateException for displayAddPlayerScreen");
    }
  }

  @Override
  public void displayPopupMessage(String message, String type) throws IllegalArgumentException {
    this.out.append(String.format("message: %s, type: %s", message, type));
  }

  @Override
  public void displayGameScreen(Features featuresController) throws IllegalArgumentException {
    if (Integer.parseInt(this.uniqueCode) % 2 == 0) {
      throw new IllegalArgumentException("IllegalArgumentException for displayGameScreen");
    } else {
      throw new IllegalStateException("IllegalStateException for displayGameScreen");
    }
  }

  @Override
  public void updateGameScreen(String message) throws IllegalArgumentException {
    if (Integer.parseInt(this.uniqueCode) % 2 == 0) {
      throw new IllegalArgumentException("IllegalArgumentException for updateGameScreen");
    } else {
      throw new IllegalStateException("IllegalStateException for updateGameScreen");
    }
  }

  @Override
  public void setFeatures(Features featuresController) throws IllegalArgumentException {
    this.out.append(String.format("Mock called from setFeatures. uniqueCode: %s", this.uniqueCode));
  }

  @Override
  public void displayAddPlayerPopup(Features featuresController) throws IllegalArgumentException {
    if (Integer.parseInt(this.uniqueCode) % 2 == 0) {
      throw new IllegalArgumentException("IllegalArgumentException for displayAddPlayerPopup");
    } else {
      throw new IllegalStateException("IllegalStateException for displayAddPlayerPopup");
    }
  }

  @Override
  public void displayPickItemPopup(Features featuresController) throws IllegalArgumentException {
    if (Integer.parseInt(this.uniqueCode) % 2 == 0) {
      throw new IllegalArgumentException("IllegalArgumentException for displayPickItemPopup");
    } else {
      throw new IllegalStateException("IllegalStateException for displayPickItemPopup");
    }
  }

  @Override
  public void displayAttackTargetPopup(Features featuresController)
      throws IllegalArgumentException {
    if (Integer.parseInt(this.uniqueCode) % 2 == 0) {
      throw new IllegalArgumentException("IllegalArgumentException for displayAttackTargetPopup");
    } else {
      throw new IllegalStateException("IllegalStateException for displayAttackTargetPopup");
    }
  }

  @Override
  public void displayMovePetPopup(Features featuresController) throws IllegalArgumentException {
    if (Integer.parseInt(this.uniqueCode) % 2 == 0) {
      throw new IllegalArgumentException("IllegalArgumentException for displayMovePetPopup");
    } else {
      throw new IllegalStateException("IllegalStateException for displayMovePetPopup");
    }
  }

  @Override
  public void displayFileChooser(Features featuresController) throws IllegalArgumentException {
    if (Integer.parseInt(this.uniqueCode) % 2 == 0) {
      throw new IllegalArgumentException("IllegalArgumentException for displayFileChooser");
    } else {
      throw new IllegalStateException("IllegalStateException for displayFileChooser");
    }
  }

  @Override
  public void exitGame() {
    if (Integer.parseInt(this.uniqueCode) % 2 == 0) {
      throw new IllegalArgumentException("IllegalArgumentException for exitGame");
    } else {
      throw new IllegalStateException("IllegalStateException for exitGame");
    }
  }

}
