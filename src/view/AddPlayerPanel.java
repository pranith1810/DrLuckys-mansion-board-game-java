package view;

import controller.Features;
import game.ReadOnlyModel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * Creates a Panel which Displays players in the game and allows users to add
 * players.
 *
 */
public class AddPlayerPanel extends JPanel {

  private static final long serialVersionUID = 7525139079837574057L;

  private final ReadOnlyModel dataModel;
  private final JButton addPlayers;
  private final JButton startGame;
  private JPanel center;

  /**
   * Constructor of AddPlayerPanel to create AddPlayer Screen.
   * 
   * @param dataModel Read Only Model that is passed by the view.
   */
  public AddPlayerPanel(ReadOnlyModel dataModel) {
    if (dataModel == null) {
      throw new IllegalArgumentException("Data Model cant be Null");
    }
    this.dataModel = dataModel;
    this.setLayout(new BorderLayout(20, 15));
    this.setBackground(new Color(76, 17, 49));

    // NORTH LAYOUT

    JLabel heading = new JLabel("<html><font size='6'>List Of Players:</font></html>");
    heading.setForeground(new Color(68, 89, 165));

    JPanel north = new JPanel();
    north.setLayout(new BorderLayout());
    north.setBackground(new Color(76, 17, 49));
    north.add(heading, BorderLayout.CENTER);
    north.setBorder(new EmptyBorder(10, 50, 10, 10));

    center = new JPanel();
    add(north, BorderLayout.NORTH);

    // CENTER LAYOUT
    this.updateAddPlayers();

    // SOUTH LAYOUT
    JPanel south = new JPanel();
    south.setLayout(new BorderLayout());
    south.setBackground(new Color(76, 17, 49));
    south.setBorder(new EmptyBorder(100, 100, 100, 100));
    JPanel buttonPane = new JPanel();
    buttonPane.setLayout(new BorderLayout());
    buttonPane.setBackground(new Color(76, 17, 49));
    this.addPlayers = new JButton("+ Add Players");
    this.addPlayers.setFocusPainted(false);
    buttonPane.add(this.addPlayers, BorderLayout.WEST);
    this.startGame = new JButton("Start Game >");
    this.startGame.setFocusPainted(false);
    buttonPane.add(this.startGame, BorderLayout.EAST);
    south.add(buttonPane, BorderLayout.CENTER);

    add(south, BorderLayout.SOUTH);

    // WEST LAYOUT
    JPanel west = new JPanel();
    west.setLayout(new BorderLayout());
    west.setBackground(new Color(76, 17, 49));
    west.setBorder(new EmptyBorder(0, 20, 0, 20));

    add(west, BorderLayout.WEST);

    // EAST LAYOUT
    JPanel east = new JPanel();
    east.setLayout(new BorderLayout());
    east.setBackground(new Color(76, 17, 49));
    east.setBorder(new EmptyBorder(0, 20, 0, 20));

    add(east, BorderLayout.EAST);

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
    this.startGame.addActionListener(event -> {
      if (this.dataModel.getAllPlayers().length > 1) {
        featuresController.showGameScreen();
      } else {
        this.displayPopup("Cant start the Game with less than 2 Players");
      }
    });

    this.addPlayers.addActionListener(event -> {
      if (this.dataModel.getAllPlayers().length < 10) {
        featuresController.addPlayerIsClicked();
      } else {
        this.displayPopup("Cannot add more than 10 Players");
      }
    });
  }

  /**
   * Creates a Add Player Popup that prompts user to enter the information to add
   * player.
   * 
   * @param featuresController The features of the controller that the view uses
   * @throws IllegalArgumentException When features controller is null
   */
  public void displayAddPlayerPopup(Features featuresController) throws IllegalArgumentException {

    if (featuresController == null) {
      throw new IllegalArgumentException("Features controller cannot be null");
    }

    JPanel myPanel = new JPanel();
    myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));

    JLabel nameLabel = new JLabel("Name of the Player:");
    nameLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
    JTextField name = new JTextField(30);
    myPanel.add(nameLabel);
    myPanel.add(name);

    JLabel landingLocationLabel = new JLabel("Select a Landing Location");
    landingLocationLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
    JComboBox<String> roomList = new JComboBox<String>(this.dataModel.getAllSpaces());
    roomList.setSelectedIndex(0);
    myPanel.add(landingLocationLabel);
    myPanel.add(roomList);

    JLabel typeLabel = new JLabel("Select a Player Type");
    typeLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
    JComboBox<String> typeList = new JComboBox<String>(new String[] { "Computer", "Human" });
    typeList.setSelectedIndex(0);
    myPanel.add(typeLabel);
    myPanel.add(typeList);

    int result = JOptionPane.showConfirmDialog(this, myPanel, "Enter Player Details",
        JOptionPane.OK_CANCEL_OPTION);

    if (result == JOptionPane.OK_OPTION) {
      featuresController.addPlayer(name.getText(), roomList.getSelectedItem().toString(),
          typeList.getSelectedItem().toString().equals("Human"));
      this.updateAddPlayers();
    }
  }

  /**
   * Displays Error Messages with a Pop up.
   * 
   * @param message message that has to be displayed in pop up.
   */
  private void displayPopup(String message) {
    JOptionPane.showMessageDialog(this, message, "ERROR", JOptionPane.ERROR_MESSAGE);
  }

  private void updateAddPlayers() {

    this.remove(center);
    this.center = new JPanel();
    String[] header = { "Name", "Landing Location", "Type" };
    JTable table = new JTable(this.dataModel.getAllPlayers(), header);
    table.setBackground(new Color(255, 242, 205));
    table.setRowHeight(table.getRowHeight() + 28);
    JScrollPane data = new JScrollPane(table);
    center.setLayout(new BorderLayout());
    center.add(data, BorderLayout.CENTER);
    center.setBackground(new Color(255, 242, 205));
    this.add(center, BorderLayout.CENTER);
    center.revalidate();
  }

}
