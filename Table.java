import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import java.awt.Image;
import javax.swing.*;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.BorderLayout;

/**
 *  The Table class represents the game table where cards are displayed.
 * It extends JPanel and includes methods for drawing the game components.
 *
 * @author (Mohammadjavad Aghaeipour, Ricky Tan)
 * @version (2024-01-24)
 */
public class Table extends JPanel
{
    final int WIDTH = 1600; // screen size
    final int HEIGHT = 900; 
    Driver ourDriver;
    Color yellow;
    Color white;
    Color black;
    Color red;
    Color blue;
    private int numberOfPlayers;
    private Game game;  // Reference to the Game instance

    JFrame frame;
    JButton button;
    JCheckBox RedcheckBox;
    JCheckBox GreencheckBox;
    JCheckBox BluecheckBox;
    JCheckBox YellowcheckBox;

    public Table(Driver driver)
    {
        setPreferredSize( new Dimension(WIDTH,HEIGHT) );
        setBackground( new Color(255,255,255) ); // soft Green
        yellow = new Color(255, 255, 51);
        white = new Color(255, 255, 255);
        black = new Color(0,0,0);
        red = new Color(255,0,0);
        blue = new Color(0,0,255);

        this.ourDriver = driver;

        addMouseListener(ourDriver);
    }

    public void paintComponent(Graphics brush)
    {
        super.paintComponent(brush);
        // cardMark(WIDTH/4,HEIGHT/2,brush);
        // cardMark(WIDTH/4*3,HEIGHT/2,brush);

        // Graphics2D g2 = (Graphics2D) brush;
        // Font font = new Font(null, Font.PLAIN, 20);
        // g2.setFont(font);
        // g2.drawString("text = ", 120, 460);

        // Draw the game components based on the current game state

        drawDeck(brush);
        drawDiscardPile(brush);
        //game.start();
        drawCurrentPlayerHands(brush);
        brush.setColor(black);
        Graphics2D g2 = (Graphics2D) brush;
        Font font = new Font(null, Font.PLAIN, 20);
        g2.setFont(font);
        g2.drawString(game.getCurrentPlayer().getName(), 20, HEIGHT/2+250);
        if (game.getFlagInvalid() == 1){
            float newSize = 60.0f;
            font = font.deriveFont(newSize);
            g2.setFont(font);
            g2.drawString("Invalid card", WIDTH/2, HEIGHT/2);

            // Player winner = game.getWinner();
            // if (winner != null){
            // ourDriver.frame.getContentPane().removeAll();
            // ourDriver.frame.getContentPane().add(new JLabel(new ImageIcon("win.jpg")));
            // newSize = 20.0f;
            // g2.drawString(winner.getName(), 200, 260);
            // }

            repaint();
            //game.setFlagInvalid(2);
            // Simulate a delay or wait for user input between turns

        }
        if (game.getFlagInvalid() == 2){
            repaint();
            //Move to the next player
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            game.setCurrentPlayerIndex (game.giveNextPlayerIndex());
            game.setFlagInvalid(0);

        }

        if (game.getSpecailWildColorFlag() == true){
            float newSize = 20.0f;
            font = font.deriveFont(newSize);
            g2.setFont(font);
            g2.drawString("Color: "+game.getSpecailWildColor().toString() , WIDTH/2, HEIGHT/4);

        }

        if (game.getplayedFlag() == true){
            ourDriver.next.setEnabled(false);
            game.setplayedFlag(false);
            ourDriver.getCard.setEnabled(true);
        }
        if (game.getdraw2wild4Flag() == true){
            float newSize = 60.0f;
            g2.drawString("You get skipped", WIDTH/2, HEIGHT/2);
            ourDriver.next.setEnabled(true);
            ourDriver.getCard.setEnabled(false);

        }

        Player winner = game.getWinner();
        if (winner != null) {

            // Remove all components from the content pane
            ourDriver.frame.getContentPane().removeAll();

            // Create a panel to display the winner
            JPanel winnerPanel = new JPanel();
            winnerPanel.add(new JLabel(new ImageIcon("images/win.jpg")));

            // Adjust the font size based on your design
            float newSize = 20.0f;
            Font font1 = new Font(null, Font.PLAIN, (int)newSize);

            JLabel winnerLabel = new JLabel(winner.getName(), JLabel.CENTER);
            winnerLabel.setFont(font1);
            winnerPanel.add(winnerLabel);

            // Add the winner panel to the content pane
            ourDriver.frame.getContentPane().add(winnerPanel);

            // Revalidate and repaint the frame
            ourDriver.frame.getContentPane().revalidate();
            ourDriver.frame.getContentPane().repaint();

        }

        repaint();
    }

    public void cardMark(int x, int y, Graphics pen){
        pen.setColor(black);
        pen.fillRect(x-70,y-96,140,192);
        pen.setColor(white);
        pen.fillRect(x-65,y-91,130,182);
    }

    public int getNumberOfPlayers(){
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int num){
        numberOfPlayers = num;
    }

    private void drawCurrentPlayerHands(Graphics g) {
        int x = getWidth() - WIDTH + 65;
        int y = getHeight() - 91;

        Player player= game.getCurrentPlayer();
        g.drawString(player.getName() + "'s Hand:", x, y);

        for (Card card : player.getHand()) {
            ImageIcon cardImage = new ImageIcon(card.getImagePath());
            cardImage.paintIcon(this, g, x-65, y-91);
            x += 130;
        }

    }

    private void drawDeck(Graphics g) {
        int x = getWidth() - WIDTH/4;
        int y = getHeight() - HEIGHT/4*3;

        ImageIcon deckImage = new ImageIcon("images/card_back_alt.png");
        deckImage.paintIcon(this, g, x-65, y-91);
    }

    private void drawDiscardPile(Graphics g) {
        int x = getWidth() - WIDTH/4*3;
        int y = getHeight() - HEIGHT/4*3;

        if (!game.getDiscardPile().isEmpty()) {
            Card topCard = game.getDiscardPile().get(game.getDiscardPile().size() - 1);
            ImageIcon cardImage = new ImageIcon(topCard.getImagePath());
            cardImage.paintIcon(this, g, x-65, y-91);
        }
    }

    public void setGame(Game game) {
        this.game = game;
    }    

    public void checkBox(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        button = new JButton();
        button.setText("submit");
        button.addActionListener(ourDriver);

        RedcheckBox = new JCheckBox();
        RedcheckBox.setText("RED");
        RedcheckBox.setFocusable(false);
        RedcheckBox.setFont(new Font("Consolas",Font.PLAIN,35));

        BluecheckBox = new JCheckBox();
        BluecheckBox.setText("Blue");
        BluecheckBox.setFocusable(false);
        BluecheckBox.setFont(new Font("Consolas",Font.PLAIN,35));

        GreencheckBox = new JCheckBox();
        GreencheckBox.setText("Green");
        GreencheckBox.setFocusable(false);
        GreencheckBox.setFont(new Font("Consolas",Font.PLAIN,35));

        YellowcheckBox = new JCheckBox();
        YellowcheckBox.setText("Yellow");
        YellowcheckBox.setFocusable(false);
        YellowcheckBox.setFont(new Font("Consolas",Font.PLAIN,35));
        // checkBox.setIcon(xIcon);
        // checkBox.setSelectedIcon(checkIcon);

        frame.add(button);
        frame.add(RedcheckBox);
        frame.add(BluecheckBox);
        frame.add(GreencheckBox);
        frame.add(YellowcheckBox);
        frame.pack();
        frame.setVisible(true);
    }
}
