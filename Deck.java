import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
/**
 * The Deck class manages the Uno deck, handling card creation, shuffling, and dealing.
 * It includes methods for initializing the deck, shuffling, and dealing cards.
 * 
 * @author (Mohammadjavad Aghaeipour, Ricky Tan)
 * @version (2024-01-24)
 */
// Deck class to manage the Uno deck
class Deck {
    private final List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        for (Card.Color color : Card.Color.values()) {
            // Don't create numbered cards for wild colors
            if (color == Card.Color.WILD) continue;

            for (Card.Num num : Card.Num.values()) {
                // Skip wild cards here
                if (num == Card.Num.WILD_FOUR || num == Card.Num.WILD_COLOR) continue;

                // Add card to the deck
                cards.add(new Card(color, num));

                // Add second copy of each card except for zero
                if (num != Card.Num.ZERO) {
                    cards.add(new Card(color, num));
                }
            }
        }

        // Add wild cards
        for (int i = 0; i < 4; i++) {
            cards.add(new Card(Card.Color.WILD, Card.Num.WILD_FOUR));
            cards.add(new Card(Card.Color.WILD, Card.Num.WILD_COLOR));
        }
        
        shuffle();
    }

    // Shuffle the deck
    public void shuffle() {
        Collections.shuffle(cards);
    }

    // Deal a card
    public Card deal() {
        if (!cards.isEmpty()) {
            return cards.remove(cards.size() - 1);
        }else {
            JOptionPane.showMessageDialog(null, "The deck is empty.");
            return null;
        }
    }
    
    
}