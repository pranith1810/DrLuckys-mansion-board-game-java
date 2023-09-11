package view;

import controller.Features;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * A class which represents the welcome panel of the game.
 */
public class WelcomePanel extends JPanel {

  private static final long serialVersionUID = 7525139079837574057L;

  private final JLabel gameTitle;
  private final JLabel gameSubTitle;
  private final JLabel emojiLabel;
  private final JLabel aboutLabelOne;
  private final JLabel aboutLabelTwo;
  private final JLabel aboutLabelThree;
  private final JLabel aboutLabelFour;
  private final JButton startButton;
  private final JLabel designedBy;
  private final JLabel firstName;
  private final JLabel secondName;

  /**
   * A constructor which creates the Welcome panel of the game.
   */
  public WelcomePanel() {

    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    setBackground(new Color(76, 17, 49));

    gameTitle = new JLabel("Welcome to the World of Dr. Lucky!");
    gameTitle.setForeground(Color.YELLOW);
    gameTitle.setFont(new Font("Serif", Font.BOLD, 30));
    gameTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
    gameTitle.setBorder(new EmptyBorder(50, 0, 5, 0));

    add(gameTitle);

    gameSubTitle = new JLabel("Murder in the Dark...");
    gameSubTitle.setForeground(Color.RED);
    gameSubTitle.setFont(new Font("Serif", Font.BOLD, 20));
    gameSubTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

    add(gameSubTitle);

    emojiLabel = new JLabel(new ImageIcon(
        new ImageIcon("res/emoji.png").getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT)));
    emojiLabel.setBorder(new EmptyBorder(10, 0, 0, 0));
    emojiLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    add(emojiLabel);

    aboutLabelOne = new JLabel(
        "Your aim is to kill Dr. Lucky and own his world. Unfortunately, the other players "
            + "do as well.");
    aboutLabelOne.setForeground(new Color(86, 187, 241));
    aboutLabelOne.setFont(new Font("Serif", Font.BOLD, 15));
    aboutLabelOne.setAlignmentX(Component.CENTER_ALIGNMENT);
    aboutLabelOne.setBorder(new EmptyBorder(150, 20, 0, 20));

    aboutLabelTwo = new JLabel(
        "You must make your effort in secret if you do not want to go to jail.");
    aboutLabelTwo.setForeground(new Color(86, 187, 241));
    aboutLabelTwo.setFont(new Font("Serif", Font.BOLD, 15));
    aboutLabelTwo.setAlignmentX(Component.CENTER_ALIGNMENT);

    aboutLabelThree = new JLabel("If anyone sees you, act naturally and let the Doctor live... ");
    aboutLabelThree.setForeground(new Color(86, 187, 241));
    aboutLabelThree.setFont(new Font("Serif", Font.BOLD, 15));
    aboutLabelThree.setAlignmentX(Component.CENTER_ALIGNMENT);

    aboutLabelFour = new JLabel("until you get the next chance.");
    aboutLabelFour.setForeground(new Color(86, 187, 241));
    aboutLabelFour.setFont(new Font("Serif", Font.BOLD, 15));
    aboutLabelFour.setAlignmentX(Component.CENTER_ALIGNMENT);
    aboutLabelFour.setBorder(new EmptyBorder(0, 0, 70, 0));

    add(aboutLabelOne);
    add(aboutLabelTwo);
    add(aboutLabelThree);
    add(aboutLabelFour);

    startButton = new JButton("Kill now...");
    startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    startButton.setBorder(new EmptyBorder(10, 8, 10, 8));
    startButton.setFocusPainted(false);

    add(startButton);

    designedBy = new JLabel("Designed By:");
    designedBy.setForeground(new Color(254, 233, 163));
    designedBy.setFont(new Font("Serif", Font.BOLD, 15));
    designedBy.setBorder(new EmptyBorder(80, 120, 0, 0));

    firstName = new JLabel("Kaushik Boora");
    firstName.setForeground(new Color(254, 233, 163));
    firstName.setFont(new Font("Serif", Font.BOLD, 15));
    firstName.setBorder(new EmptyBorder(0, 120, 0, 0));

    secondName = new JLabel("Pranith Rao Nayeneni");
    secondName.setForeground(new Color(254, 233, 163));
    secondName.setFont(new Font("Serif", Font.BOLD, 15));
    secondName.setBorder(new EmptyBorder(0, 120, 20, 0));

    add(designedBy);
    add(firstName);
    add(secondName);

  }

  /**
   * Sets the features of the controller as callbacks to event listeners that this
   * panel can use.
   * 
   * @param featuresController The features controller that the panel uses
   * @throws IllegalArgumentException When features controller is null
   */
  public void setFeatures(Features featuresController) throws IllegalArgumentException {
    if (featuresController == null) {
      throw new IllegalArgumentException("Features controller cannot be null");
    }

    startButton.addActionListener(event -> {
      featuresController.startGameIsClicked();
    });
  }

}
