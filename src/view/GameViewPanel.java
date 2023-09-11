package view;

import controller.Features;
import game.ReadOnlyModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

/**
 * Creates a Panel which Displays Game Layout, Results of a turn and turn
 * information on the screen .
 *
 */
public class GameViewPanel extends JPanel {

  private static final long serialVersionUID = 7525139079837574057L;

  private final ReadOnlyModel dataModel;
  private JLabel worldLabel;
  private final List<ImageIcon> playerIcons;
  private JPanel worldPanel;
  private JLabel targetLabel;
  private JScrollPane scrollableWorld;
  private final List<JLabel> playerLabelIcons;
  private JPanel eastLayout;
  private JPanel eastTurnLayout;
  private JPanel eastResultLayout;
  private JPanel eastPlayerLayout;
  private JLabel turnIconLabel;
  private final Map<String, Consumer<Features>> turns;

  /**
   * Constructor for GameViewPanel to create a new Game Screen.
   * 
   * @param dataModel Read Only Model that is passed by the view.
   */
  public GameViewPanel(ReadOnlyModel dataModel) {

    if (dataModel == null) {
      throw new IllegalArgumentException("Data Model cant be Null");
    }
    this.dataModel = dataModel;
    this.setLayout(new BorderLayout(20, 15));
    this.setBackground(new Color(76, 17, 49));

    this.playerIcons = new ArrayList<ImageIcon>();
    this.playerLabelIcons = new ArrayList<JLabel>();
    this.scrollableWorld = new JScrollPane();
    for (int player = 0; player < 10; player++) {
      playerIcons.add(new ImageIcon(String.format("%d.png", player + 1)));
    }
    this.worldPanel = new JPanel();
    this.targetLabel = new JLabel();

    // EAST LAYOUT
    this.eastLayout = new JPanel();
    this.eastLayout.setLayout(new BoxLayout(this.eastLayout, BoxLayout.Y_AXIS));
    this.eastTurnLayout = new JPanel();
    this.eastResultLayout = new JPanel();
    this.eastPlayerLayout = new JPanel();

    this.worldPanel = new JPanel();
    this.worldLabel = new JLabel();
    this.setFocusable(true);
    this.turns = new HashMap<String, Consumer<Features>>();
  }

  /**
   * Method used for creating the image of the world and setting it in the center
   * of GameView panel.
   * 
   * @throws IllegalStateException When the layout cannot be created
   */
  public void createWorldLayout() throws IllegalStateException {
    worldLabel = new JLabel(new ImageIcon("TheWorld.png"));
    worldLabel.setLayout(null);
    this.worldPanel.setLayout(new GridBagLayout());
    this.worldPanel.add(worldLabel);
    scrollableWorld = new JScrollPane(worldPanel);
    this.add(scrollableWorld, BorderLayout.CENTER);
  }

  /**
   * Update the position of the players in the world.
   */
  public void update() {

    worldLabel.remove(this.targetLabel);

    // adding Target character.
    ImageIcon targetIcon = new ImageIcon("TargetCharacter.png");
    Image targetImage = targetIcon.getImage();
    int dimensions = 20;
    Image newTargetImage = targetImage.getScaledInstance(dimensions, dimensions,
        java.awt.Image.SCALE_SMOOTH);
    targetIcon = new ImageIcon(newTargetImage);
    this.targetLabel = new JLabel(targetIcon);
    this.targetLabel.setToolTipText("Target Character");
    List<String> turnInfo = this.dataModel.getTurnInfo();
    int[] targetCharacterCoordinates = this.dataModel.getCoordinates(turnInfo.get(3));
    int buffer = 30;
    int multiplicationFactor = 20;
    this.targetLabel.setBounds(targetCharacterCoordinates[3] * multiplicationFactor + buffer,
        targetCharacterCoordinates[0] * multiplicationFactor + buffer, dimensions, dimensions);
    worldLabel.add(targetLabel);

    // adding players.
    for (JLabel playerIconLabel : playerLabelIcons) {
      worldLabel.remove(playerIconLabel);
    }

    Map<String, List<Integer>> roomsInfo = new HashMap<>();
    String[][] playerList = dataModel.getAllPlayers();

    for (int playerIndex = 0; playerIndex < playerList.length; playerIndex++) {
      if (roomsInfo.containsKey(playerList[playerIndex][1])) {
        List<Integer> data = roomsInfo.get(playerList[playerIndex][1]);
        data.set(4, data.get(4) + 1);
        data.add(playerIndex);
      } else {
        List<Integer> data = Arrays
            .stream(this.dataModel.getCoordinates(playerList[playerIndex][1])).boxed()
            .collect(Collectors.toCollection(ArrayList::new));
        data.add(1);
        data.add(playerIndex);
        roomsInfo.put(playerList[playerIndex][1], data);
      }
    }

    for (Map.Entry<String, List<Integer>> entry : roomsInfo.entrySet()) {
      Double roomAreay = Math
          .ceil(((entry.getValue().get(2) - entry.getValue().get(0)) * multiplicationFactor)
              / (dimensions));
      Double roomAreax = Math
          .ceil(((entry.getValue().get(3) - entry.getValue().get(1)) * multiplicationFactor)
              / dimensions);
      Double accomodateLength = Math.ceil(roomAreax * roomAreay);
      int accomodates = accomodateLength.intValue();
      int accomodatesx = 0;
      int accomodatesy = 0;
      int dimMultiplication = multiplicationFactor;
      int roomHeight = (entry.getValue().get(2) - entry.getValue().get(0)) * multiplicationFactor;
      int roomWidth = (entry.getValue().get(3) - entry.getValue().get(1)) * multiplicationFactor;

      if (entry.getValue().get(4) > accomodates) {
        dimensions = dimensions / 2;
        dimMultiplication = dimMultiplication / 2;
        roomAreay = Math.ceil((roomHeight) / (dimensions));
        roomAreax = Math.ceil((roomWidth) / dimensions);
      }

      for (int player = 5; player < entry.getValue().size(); player++) {

        ImageIcon playerIcon = this.playerIcons.get(entry.getValue().get(player));
        Image playerImage = playerIcon.getImage();
        Image newPlayerImage = playerImage.getScaledInstance(dimensions, dimensions,
            java.awt.Image.SCALE_SMOOTH);
        playerIcon = new ImageIcon(newPlayerImage);
        JLabel playerIconLabel = new JLabel(playerIcon);
        playerIconLabel.setToolTipText(playerList[entry.getValue().get(player)][0]);
        if (playerList[entry.getValue().get(player)][0].equals(turnInfo.get(0))) {
          this.turnIconLabel = playerIconLabel;
          this.turnIconLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
              eastLayout.remove(eastPlayerLayout);
              String data = dataModel.getPlayerInfo(turnInfo.get(0)).replace("\n", "<br>");
              JLabel playerInfo = new JLabel(
                  String.format("<html>Player Data:<br>%s</html>", data));
              playerInfo.setBackground(Color.GRAY);
              playerInfo.setBounds(0, 0, 100, 100);
              eastPlayerLayout = new JPanel();
              eastPlayerLayout.setBackground(new Color(230, 232, 230));
              eastPlayerLayout.add(playerInfo);
              eastPlayerLayout.setMinimumSize(new Dimension(1500, 1500));
              eastPlayerLayout.setMaximumSize(new Dimension(1500, 1500));
              eastLayout.add(eastPlayerLayout);
              eastLayout.revalidate();
            }
          });
        }
        int refactoredx = entry.getValue().get(3) * multiplicationFactor + buffer
            - (accomodatesx * dimMultiplication);
        int refactoredy = entry.getValue().get(2) * multiplicationFactor + buffer
            - (accomodatesy * dimMultiplication);
        playerIconLabel.setBounds(refactoredx, refactoredy, dimensions, dimensions);

        if (accomodatesx + 1 >= roomAreax) {
          accomodatesy += 1;
          accomodatesx = 0;
        } else {
          accomodatesx += 1;
        }

        playerLabelIcons.add(playerIconLabel);
        worldLabel.add(playerIconLabel);

        // EAST LAYOUT

        this.eastLayout.remove(this.eastTurnLayout);
        this.eastLayout.remove(this.eastPlayerLayout);

        String petString = "";
        if (turnInfo.get(2).equals(this.dataModel.getCurrentSpaceOfPet())) {
          petString = "Pet is in the Current Room";
        }

        JLabel turnInformation = new JLabel(String.format(
            "<html>Turn Info:<br> Player Name : %s<br> Location : %s<br> Type : %s<br>%s </html>",
            turnInfo.get(0), turnInfo.get(2), turnInfo.get(1), petString));
        turnInformation.setBounds(0, 0, 1000, 1000);
        this.eastTurnLayout = new JPanel();
        this.eastTurnLayout.setBackground(new Color(253, 202, 64));
        this.eastTurnLayout.add(turnInformation);
        this.eastTurnLayout.setMinimumSize(new Dimension(1500, 1500));
        this.eastTurnLayout.setMaximumSize(new Dimension(1500, 1500));
        this.eastLayout.add(this.eastTurnLayout);
        this.eastLayout.add(this.eastResultLayout);

        JLabel playerInfo = new JLabel("Player Data:");
        playerInfo.setBackground(Color.GRAY);
        playerInfo.setBounds(0, 0, 100, 100);
        this.eastPlayerLayout = new JPanel();
        this.eastPlayerLayout.setBackground(new Color(230, 232, 230));
        this.eastPlayerLayout.add(playerInfo);
        this.eastPlayerLayout.setMinimumSize(new Dimension(1500, 1500));
        this.eastPlayerLayout.setMaximumSize(new Dimension(1500, 1500));
        this.eastLayout.add(this.eastPlayerLayout);

        this.add(eastLayout, BorderLayout.EAST);
        this.eastLayout.revalidate();
      }
    }
  }

  /**
   * Changes the Result String in result section of East Layout in Game Screen.
   * 
   * @param message Message that should be displayed in the result section.
   */
  public void updateResult(String message) {

    this.eastLayout.remove(this.eastResultLayout);
    message.replace("\n", "<br>");
    StringBuilder strBuilder = new StringBuilder();
    int count = 0;
    while (message.length() > count) {
      if (count % 15 == 0) {
        while (message.length() > count && message.charAt(count) != ' ') {
          strBuilder.append(message.charAt(count));
          count++;
        }
        strBuilder.append(" <br>");
      }
      if (message.length() > count) {
        strBuilder.append(message.charAt(count));
        count++;
      }
    }
    JLabel resultInfo = new JLabel(
        String.format("<html>Results:<br>%s</html>", strBuilder.toString()));
    resultInfo.setBackground(Color.GRAY);
    resultInfo.setBounds(0, 0, 100, 100);
    this.eastResultLayout = new JPanel();
    this.eastResultLayout.setBackground(new Color(133, 218, 215));
    this.eastResultLayout.add(resultInfo);
    this.eastResultLayout.setMinimumSize(new Dimension(1500, 1500));
    this.eastResultLayout.setMaximumSize(new Dimension(1500, 1500));
    this.eastLayout.add(this.eastTurnLayout);
    this.eastLayout.add(this.eastResultLayout);
    this.eastLayout.add(this.eastPlayerLayout);
    this.eastLayout.revalidate();
  }

  /**
   * 
   * Creates a Pop up that lets the user select from one of the oprions.
   * 
   * @param title   String that has to be displayed in the pop up.
   * @param options List of options that has to be given to the user.
   * @return the option selected by the user.
   * 
   * @throws IllegalArgumentException When title is null or empty or options array
   *                                  is null
   */
  private String displayInputPopup(String title, String[] options) {
    if (title == null) {
      throw new IllegalArgumentException("Title cant be null");
    }
    if (options == null) {
      throw new IllegalArgumentException("Options cant be null");
    }
    if (options.length < 1) {
      throw new IllegalArgumentException("Options cant be Empty");
    }

    JPanel myPanel = new JPanel();
    myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
    JLabel landingLocationLabel = new JLabel(String.format("Choose an Option for %s", title));
    landingLocationLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
    JComboBox<String> roomList = new JComboBox<String>(options);
    roomList.setSelectedIndex(0);
    myPanel.add(landingLocationLabel);
    myPanel.add(roomList);

    int result = JOptionPane.showConfirmDialog(this, myPanel, "", JOptionPane.OK_CANCEL_OPTION);

    if (result == JOptionPane.OK_OPTION) {
      return roomList.getSelectedItem().toString();
    }

    return "";
  }

  /**
   * 
   * Used to create a popup to pick an item from a space.
   * 
   * @param featuresController controller instance to call appropriate method.
   * @throws IllegalArgumentException When featuresContoller is null
   */
  public void displayPickItemPopup(Features featuresController) throws IllegalArgumentException {
    if (featuresController == null) {
      throw new IllegalArgumentException("Features controller cannot be null");
    }

    String[] options = dataModel.getCurrentSpaceItems();
    if (options.length < 1) {
      displayErrorPopup("No items in the space");
      return;
    }
    String selectedOption = displayInputPopup("Item", options);
    if (!"".equals(selectedOption)) {
      featuresController.pickItem(selectedOption);
    }
  }

  /**
   * 
   * Used to create a popup to choose an item for attacking the target.
   * 
   * @param featuresController controller instance to call appropriate method.
   * @throws IllegalArgumentException When featuresContoller is null
   */
  public void displayAttackTargetPopup(Features featuresController)
      throws IllegalArgumentException {
    if (featuresController == null) {
      throw new IllegalArgumentException("Features controller cannot be null");
    }

    List<String> turnInfo = dataModel.getTurnInfo();

    if (!turnInfo.get(2).equals(turnInfo.get(3))) {
      displayErrorPopup("Target is not present in the space");
      return;
    }
    String selectedOption = displayInputPopup("Item", dataModel.getCurrentPlayerItems());
    if (!"".equals(selectedOption)) {
      featuresController.attackTarget(selectedOption);
    }
  }

  /**
   * 
   * Used to create a popup to pick a space for moving the pet to.
   * 
   * @param featuresController controller instance to call appropriate method.
   * @throws IllegalArgumentException When featuresContoller is null
   */
  public void displayMovePetPopup(Features featuresController) throws IllegalArgumentException {
    if (featuresController == null) {
      throw new IllegalArgumentException("Features controller cannot be null");
    }

    String selectedOption = displayInputPopup("Space", dataModel.getAllSpaces());
    if (!"".equals(selectedOption)) {
      featuresController.movePet(selectedOption);
    }
  }

  /**
   * Displays Error Messages with a Pop up.
   * 
   * @param message message that has to be displayed in pop up.
   */
  private void displayErrorPopup(String message) {
    JOptionPane.showMessageDialog(this, message, "ERROR", JOptionPane.ERROR_MESSAGE);
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

    // adding turns
    this.turns.put(Commands.ATTACK_TARGET.toString(),
        (features) -> features.attackTargetIsPressed());
    turns.put(Commands.LOOK_AROUND.toString(), (features) -> features.lookAround());
    turns.put(Commands.MOVE_PET.toString(), (features) -> features.movePetIsPressed());
    turns.put(Commands.PICKUP_ITEM.toString(), (features) -> features.pickItemIsPressed());
    this.requestFocus();

    this.worldLabel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        featuresController.spaceIsClicked(e.getX(), e.getY());
      }
    });

    this.addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent e) {
        turns.get(String.valueOf(e.getKeyChar())).accept(featuresController);
      }
    });
  }
}
