import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Color;
import javax.swing.JOptionPane;

/**
 * The Game class represents the Uno game, managing players, decks, and game rules.
 * It includes methods for initializing the game, checking the validity of moves, and
 * handling special card effects.
 * 
 * @author (Mohammadjavad Aghaeipour, Ricky Tan)
 * @version (2024-01-24)
 */
class Game {
    private List<Player> players;
    private Deck deck;
    private List<Card> discardPile; // stack for the discard pile
    private int currentPlayerIndex;
    private boolean isReverse = false;
    private boolean drawTwoFlag;
    private boolean draw2wild4Flag = false;
    private boolean wildFourFlag;
    private int StackedDrawTwo=0;
    private int StackedWildFour=0;
    String direction = "clockwise";
    private Card.Color SpecailWildColor = Card.Color.WILD;
    private boolean SpecailWildColorFlag = false;
    private Table table;
    private int flagInvalid = 0;
    private boolean playedFlag = false;

    
    public Game(int numberOfPlayers, Table table) {
        this.players = new ArrayList<>();
        this.deck = new Deck();
        // Start with one card on the discard pile
        discardPile = new ArrayList<>();
        this.discardPile.add(deck.deal());

        this.discardPile = new ArrayList<>();
        initializePlayers(numberOfPlayers);
        currentPlayerIndex = 0;
        this.table = table;

        initializeGame();
        // Initialize discard pile with the first card drawn from the deck where it is not onr of the specail cards.

    }

    public void initializeGame() {
        // shuffle draw deck  
        deck.shuffle();

        //Initialize discard pile
        Card selectedCard;
        do {
            selectedCard = deck.deal();
        } while (selectedCard != null && (selectedCard.getNum() == Card.Num.WILD_FOUR || selectedCard.getNum() == Card.Num.WILD_COLOR || selectedCard.getNum() == Card.Num.DRAW_TWO));
        discardPile.add(selectedCard);

        // Deal initial cards to players
        for (Player player : players) {
            drawInitialCards(player);
        }

        // Set the first player as the current player
        currentPlayerIndex = 0;

        // Update the game panel to reflect the initial state
        table.repaint();
    }

    private void drawInitialCards(Player player) {
        for (int i = 0; i < 7; i++) {
            player.addCard(deck.deal());
        }
    }

    private void initializePlayers(int numberOfPlayers) {
        for (int i = 0; i < numberOfPlayers; i++) {
            players.add(new Player("Player " + (i + 1)));
        }
    }

    
    public Card CheckplayedCard(int selectedIndex, Player currentPlayer,Card topCard){
        
        Card selectedCard = currentPlayer.getHand().get(selectedIndex);

        // Check if the selected card is a valid move
        if (isValidMove(selectedCard, topCard)) {
            setFlagInvalid(2);
            //SpecialCard(selectedCard);
            currentPlayer.getHand().remove(selectedIndex); // Remove the played card from the hand
            //return selectedCard;
            if (selectedCard.getNum() != Card.Num.WILD_COLOR){
                SpecailWildColorFlag = false;
            }

            playedFlag = true;
            discardPile.add(selectedCard);
            SpecialCard(selectedCard);
        } 
        else                

            setFlagInvalid (1);

        return null;    
        //SpecialCard(playedCard);
    }

    

    private boolean isValidMove(Card playedCard, Card topCard) {
        // Implement the rules to check if the played card is a valid move
        // For example, check if the color or value matches the top card
        //if (topCard.getColor() != Card.Num.WILD_FOUR || topCard.getColor() != Card.Num.DRAW_TWO)
        return playedCard.getColor().equals(topCard.getColor()) || playedCard.getNum().equals(topCard.getNum()) || playedCard.getColor() == Card.Color.WILD || playedCard.getColor() ==SpecailWildColor;
        //else if (topCard.getColor() == Card.Num.WILD_FOUR) 
    }

    private void SpecialCard(Card card) {
        Card.Num value = card.getNum();

        switch (value) {
            case SKIP:
                // Skip the next player's turn
                skipNextPlayer();
                break;
            case REVERSE:
                // Reverse the direction of play
                reverseDirection();
                break;
            case DRAW_TWO:
                // The next player draws cards and skips their turn if it has not DRAW_TWO or WILD_FOUR card
                drawTwo();
                break;
                // Add more cases for other special cards as needed
            case WILD_FOUR:
                // The next player draws cards and skips their turn if it has not DRAW_TWO or WILD_FOUR card
                wildFour();
                break;
            case WILD_COLOR:
                // The next player draws cards and skips their turn if it has not DRAW_TWO or WILD_FOUR card
                wildColor();
                break;    

                // Add more cases for other special cards as needed    
        }
    }

    private void skipNextPlayer() {
        // Implement logic to skip the next player's turn
        // Increment the current player index (or dere,emt if the reverse is true) to skip the next player's turn
        currentPlayerIndex=giveNextPlayerIndex();
        //System.out.println("\n Next player's turn is skipped,");
        Player nextPlayer = players.get(currentPlayerIndex);
        //System.out.println("which is  "+nextPlayer.getName());
    }

    private void reverseDirection() {
        // Implement logic to reverse the direction of play
        // maintain a flag or variable for the direction
        isReverse = !isReverse;
        //System.out.println("\n Direction of play is reversed.");

        if (isReverse == true){
            if (direction == "clockwise"){
                direction = "counterclockwise";
            }
            else 
                direction = "clockwise";
        }

        //System.out.println("where direction will be "+direction);
    }

    private void drawTwo() {
        // Implement logic to make the next player draw two cards and skip their turn
        // We need to maintain a flag 

        int nextPlayerIndex = giveNextPlayerIndex();
        Player nextPlayer = players.get(nextPlayerIndex);
        draw2wild4Flag = true;
        
        for (int j = 0; j < 2; j++){
            Card drawnCard=deck.deal();
            nextPlayer.addCard(drawnCard); 
            //System.out.println(nextPlayer.getName() + " draws: " + drawnCard);
        }
        

    }

    private void wildFour() {
        wildColor();
        int nextPlayerIndex = giveNextPlayerIndex();
        Player nextPlayer = players.get(nextPlayerIndex);        
        draw2wild4Flag = true;
        
        for (int j = 0; j < 4; j++){
            Card drawnCard=deck.deal();
            nextPlayer.addCard(drawnCard); 
            //System.out.println(nextPlayer.getName() + " draws: " + drawnCard);                
        }
        table.repaint();
        
        StackedWildFour = 0;
        StackedDrawTwo = 0;

    }
    private void wildColor() {
        SpecailWildColorFlag = true;
        table.checkBox();
        
    }

    public void setSpecailWildColor(Card.Color color){
        SpecailWildColor = color;
    }

    public Card.Color getSpecailWildColor(){
        return SpecailWildColor;
    }

    public boolean getSpecailWildColorFlag(){
        return SpecailWildColorFlag;
    }

    public int giveNextPlayerIndex() {
        int playerIndex;
        if (isReverse) {

            // When play is reversed, move to the previous player
            playerIndex = (currentPlayerIndex - 1 + players.size()) % players.size();
            if (playerIndex == -1){
                playerIndex = players.size() -1;
            }
        } else {
            // When play is not reversed, move to the next player
            playerIndex = (currentPlayerIndex + 1) % players.size();
        }
        //return players.get(playerIndex);

        return playerIndex;
    }

    private boolean HasDrawTwoOrWildFour(int nextPlayerIndex){
        Player nextPlayer = players.get(nextPlayerIndex);

        if (nextPlayer.hasDrawTwo() || nextPlayer.hasWildFour()){
            drawTwoFlag = true;
            return true;

        }
        else
            return false;         

    }

    private boolean HasWildFour(int nextPlayerIndex ){
        Player nextPlayer = players.get(nextPlayerIndex);

        if (nextPlayer.hasWildFour()){
            wildFourFlag = true;
            return true;

        }
        else
            return false;         

    }

    public Card.Color validColor(String color) {
        // Convert the entered color to uppercase for case-insensitive comparison
        color = color.toUpperCase();

        // Check if the entered color matches one of the UnoCard.Color enum values (excluding WILD)
        for (Card.Color Color : Card.Color.values()) {
            if (!Color.name().equals("WILD") && Color.name().equals(color)) {
                return Color;
            }
        }
        // If no match is found, return null
        return null;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void setCurrentPlayerIndex(int index) {
        currentPlayerIndex = index;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public Deck getDeck() {
        return deck;
    }

    public List<Card> getDiscardPile() {
        return discardPile;
    }

    public void setFlagInvalid(int flag){
        flagInvalid = flag;
    }

    public int getFlagInvalid(){
        return flagInvalid;
    }

    public boolean getplayedFlag(){
        return playedFlag;
    }

    public void setplayedFlag(boolean flag){
        playedFlag = flag;
    }
    
    public boolean getdraw2wild4Flag(){
        return draw2wild4Flag;
    }

    public void setdraw2wild4Flag(boolean flag){
        draw2wild4Flag = flag;
    }
    
    public Player getWinner() {
        // Determine the player with an empty hand as the winner
        for (Player player : players) {
            if (player.getHand().isEmpty()) {
                return player;
            }
        }
        return null; // No winner found (should not happen in a normal Uno game)
    }
    

}

