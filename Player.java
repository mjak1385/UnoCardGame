import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
/**
 * The Player class manages each player's hand in the Uno game.
 * It includes methods for adding cards to the hand, getting player information,
 * displaying the hand, playing a card, and checking if the player has won.
 * 
 * @author (Mohammadjavad Aghaeipour, Ricky Tan)
 * @version (2024-01-24)
 */
// Player class to manage each player's hand
class Player {
    private final List<Card> hand;
    private String name;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    // Add card to the player's hand
    public void addCard(Card card) {
        if (card != null) {
            hand.add(card);
        }
    }

    public List<Card> getHand() {
        return hand;
    }

    public String getHandAsString() {
        StringBuilder handString = new StringBuilder();
        for (Card card : hand) {
            handString.append(card).append("\n");
        }
        return handString.toString();
    }

    public int playCard() {
        displayHand();

        // Let's assume the player selects a card by entering its index in the hand
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the index of the card to play (or -1 to draw): ");
        int selectedIndex = scanner.nextInt();
        
        return selectedIndex;

       
    }

    

    public void displayHand() {
        System.out.println( name + "'s hand:");
        for (Card card : hand) {
            System.out.println(card);
        }
        //System.out.println();
    }

    

    public boolean hasWon() {
        return hand.isEmpty();
    }
    
    public boolean hasDrawTwo() {
        for (Card card : hand) {
            if (card.getNum() == Card.Num.DRAW_TWO) {
                return true;
            }
        }
        return false;
    }
    
    public boolean hasWildFour() {
        for (Card card : hand) {
            if (card.getNum() == Card.Num.WILD_FOUR) {
                return true;
            }
        }
        return false;
    }

}