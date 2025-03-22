import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;

import javax.swing.JOptionPane;
import javax.swing.JLabel;

/**
 * The Driver class serves as the entry point for the UNO Card Game application.
 * It implements ActionListener and MouseListener to handle user interactions.
 * It also contains the main method to initiate the application.
 *
 * @author (Mohammadjavad Aghaeipour, Ricky Tan)
 * @version (2024-01-24)
 */
// GameManager class to start the game
public class Driver implements ActionListener, MouseListener {
    JFrame frame;
    JFrame gameFrame;
    JPanel buttonPanel, panel, gameBtnPanel;
    JButton play, instruction, playerNum, getCard, next;
    Game game;
    Table ourTable;

    int x, y;
    public Driver()  // Constructor - our object's template
    {
        frame = new JFrame();
        frame.setTitle("UNO Card Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ourTable = new Table(this);
        //game = new Game(ourTable.getNumberOfPlayers(), ourTable);

        buttonPanel = new JPanel();
        play = new JButton("PLAY");
        playerNum = new JButton("choose player");
        instruction = new JButton("Instruction");
        buttonPanel.add(play);
        buttonPanel.add(instruction);
        buttonPanel.add(playerNum);
        instruction.addActionListener(this);
        play.addActionListener(this);
        playerNum.addActionListener(this);
        play.setEnabled(false);

        frame.getContentPane().add(frame.add(new JLabel(new ImageIcon("images/card_back_large.png"))),BorderLayout.CENTER);
        frame.getContentPane().add(buttonPanel,BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae)
    {
        if (ae.getSource() == play){
            frame.getContentPane().removeAll();
            gameBtnPanel = new JPanel();
            getCard = new JButton("draw a card");
            gameBtnPanel.add(getCard);
            getCard.addActionListener(this);
            next = new JButton("next");
            gameBtnPanel.add(next);
            next.addActionListener(this);
            next.setEnabled(false);

            //ourTable = new Table(this);
            game=new Game(ourTable.getNumberOfPlayers(), ourTable);
            ourTable.setGame(game); // Set the Game instance in the GamePanel

            frame.getContentPane().add(ourTable,BorderLayout.CENTER);
            frame.getContentPane().add(gameBtnPanel,BorderLayout.SOUTH);
            frame.pack();
            frame.setVisible(true);
            frame.getContentPane().add(ourTable);

        }
        if (ae.getSource() == instruction){
            String instructions = "<html><body>" +
                "<h1>Uno Game Instructions</h1>" +
                "<ul>" +
                "<li>Players take turns in a clockwise direction.</li>" +
                "<li>On a player's turn, they must play a card that matches the top card on the discard pile " +
                "in either color, number, or symbol.</li>" +
                "<li>If a player cannot play a matching card, they must draw a card from the draw pile. " +
                "If that card can be played, the player may play it immediately.</li>" +
                "<li>Special action cards have specific effects:</li>" +
                "<ul>" +
                "<li>Skip: Skips the next player's turn.</li>" +
                "<li>Reverse: Reverses the direction of play.</li>" +
                "<li>Draw Two: Forces the next player to draw two cards and skip their turn.</li>" +
                "<li>Wild: Allows the player to choose the color to be played. The next player must draw four cards " +
                "and skip their turn.</li>" +
                "<li>Wild Draw Four: Similar to Wild, but the next player must draw four cards.</li>" +
                "</ul>" +
                "</ul>" +
                "</body></html>";

        JOptionPane.showMessageDialog(null, instructions, "Uno Game Instructions", JOptionPane.INFORMATION_MESSAGE);
   

        }
        if (ae.getSource() == playerNum){
            int numPlayer = Integer.parseInt(JOptionPane.showInputDialog(null, "Choose number of players"));
            ourTable.setNumberOfPlayers(numPlayer);
            play.setEnabled(true);
        }
        if (ae.getSource() == getCard){
            Card drawnCard = game.getDeck().deal();
            game.getCurrentPlayer().addCard(drawnCard);
            //ourTable.repaint();

            next.setEnabled(true);
            getCard.setEnabled(false);
        }
        if (ae.getSource() == next){
            game.setFlagInvalid(2);
            next.setEnabled(false);
            getCard.setEnabled(true);
            game.setdraw2wild4Flag(false);
        }
        
        if(ae.getSource() == ourTable.button) {
            //System.out.println(ourTable.checkBox.isSelected());
            if (ourTable.RedcheckBox.isSelected()){
                game.setSpecailWildColor(game.validColor("Red"));
            }
            else if (ourTable.BluecheckBox.isSelected()){
                game.setSpecailWildColor(game.validColor("Blue"));
            }
            else if (ourTable.GreencheckBox.isSelected()){
                game.setSpecailWildColor(game.validColor("Green"));
            }
            if (ourTable.YellowcheckBox.isSelected()){
                game.setSpecailWildColor(game.validColor("Yellow"));
            }
            
            ourTable.frame.setVisible(false);
            
        }
    }
    
    public static void displayInstructions() {
        System.out.println("Uno Game Instructions:\n");

        printBullet("Players take turns in a clockwise direction.");
        printBullet("On a player's turn, they must play a card that matches the top card on the discard pile " +
                "in either color, number, or symbol.");
        printBullet("If a player cannot play a matching card, they must draw a card from the draw pile. " +
                "If that card can be played, the player may play it immediately.");
        printBullet("Special action cards have specific effects:");
        printSubBullet("Skip: Skips the next player's turn.");
        printSubBullet("Reverse: Reverses the direction of play.");
        printSubBullet("Draw Two: Forces the next player to draw two cards and skip their turn.");
        printSubBullet("Wild: Allows the player to choose the color to be played. The next player must draw four cards " +
                "and skip their turn.");
        printSubBullet("Wild Draw Four: Similar to Wild, but the next player must draw four cards.");
    }

    private static void printBullet(String text) {
        System.out.println("• " + text);
    }

    private static void printSubBullet(String text) {
        System.out.println("    - " + text);
    }

    public void mouseClicked(MouseEvent me)
    {
        int x=game.getCurrentPlayer().getHand().size();
        if (me.getY() >= ourTable.HEIGHT-182 && game.getCurrentPlayer().getHand().size() * 130 > me.getX() && game.getdraw2wild4Flag() == false)
            {
                x = (me.getX() / 130);
                
            }
        //y = Math.abs(me.getY() - 720) / 182;

        game.CheckplayedCard(x,game.getCurrentPlayer(),game.getDiscardPile().get(game.getDiscardPile().size() - 1));

        
    }
    public static void main(String[] args) {
        Driver app = new Driver(); // create our application

        // Scanner scanner = new Scanner(System.in);
        // System.out.print("Enter the number of players: ");
        // int numberOfPlayers = scanner.nextInt();
        // // Start the game
        //Game game = new Game(getnumberOfPlayers());
        // game.start();
    }

    public void mousePressed(MouseEvent e){}

    public void mouseEntered(MouseEvent e){}

    public void mouseExited(MouseEvent e){}

    public void mouseReleased(MouseEvent e){}


}
// Game class to handle the game logic
