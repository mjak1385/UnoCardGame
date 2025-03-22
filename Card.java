import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * The Card class represents a playing card with color and number attributes.
 *
 * @author (Mohammadjavad Aghaeipour, Ricky Tan)
 * @version (2024-01-24)
 */

// Card class
public class Card {
    // Enums for color and number
    public enum Color {
        RED, BLUE, GREEN, YELLOW, WILD;
    }

    public enum Num {
        ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, REVERSE, SKIP, DRAW_TWO, WILD_FOUR, WILD_COLOR;
    }

    // Card attributes
    private final Color color;
    private final Num num;

    // Constructor
    public Card(Color color, Num num) {
        this.color = color;
        this.num = num;
    }

    // Getters
    public Color getColor() {
        return color;
    }

    public Num getNum() {
        return num;
    }

    // ToString method for debugging
    @Override
    public String toString() {
        return "Card{" + "color=" + color + ", num=" + num +'}';
    }

    public String getImagePath() {
        // Assuming card images are stored in a folder named "card_images"
        String folderPath = "";
        String fileName;
        // Build the file path based on color and num
        int intNum = convertWordToNumber(num.toString());
        if (intNum != -1) {
            fileName = color.toString().toLowerCase() + "_" + String.valueOf(intNum) + ".png";

            // numeric value
        } else {

            fileName = color.toString().toLowerCase() + "_" + num.toString().toLowerCase()+ ".png";

        }
        return folderPath + fileName;
    }

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static int convertWordToNumber(String word) {
        switch (word.toLowerCase()) {
            case "zero":
                return 0;
            case "one":
                return 1;
            case "two":
                return 2;
            case "three":
                return 3;
            case "four":
                return 4;
            case "five":
                return 5;
            case "six":
                return 6;
            case "seven":
                return 7;
            case "eight":
                return 8;
            case "nine":
                return 9;
            
        }
        return -1;

    }
}
