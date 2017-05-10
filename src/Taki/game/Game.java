package Taki.game;
import static org.fusesource.jansi.Ansi.*;
import java.awt.image.DirectColorModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import Taki.cards.ActionCard;
import Taki.cards.Card;
import Taki.cards.CardColor;
import Taki.cards.NumericCard;
import Taki.cards.SpecialCard;
import Taki.printer.Message;
import Taki.printer.Printer;

public class Game {
	// Singleton
	private static Game instance = null;
	
	public static Game instance() {
		if (instance == null) {
			instance = new Game();
		}
		
		return instance;
	}
	
	// Data members
	private ArrayList<Player> players;
	private Deck deck;
	private ArrayList<Card> usedCards;
	private int gameDirection = 1;
	private int nextPlayersTurnIndex = -1;
	private CardColor topColor;
	
	// Ctor
	private Game() {
		this.players = new ArrayList<Player>();
		this.deck = new Deck();
		this.usedCards = new ArrayList<Card>();
		
		// Pop the first card from the deck, so the game will begin properly
		Card poppedCard = this.deck.draw();
		
		// Only if the openning card is numeric, start the game.3
		while (!(poppedCard instanceof NumericCard)) {
			this.addThrownCard(poppedCard);	
			poppedCard = this.deck.draw();
		}
		
		this.addThrownCard(poppedCard);
	}
	
	// Methods
	
	public int getDirection() {
		return (this.gameDirection);
	}
	
	public void setDirection(int direction) {
		this.gameDirection = direction;
	}
	
	public CardColor getTopColor() {
		return (this.topColor);
	}
	
	public void setTopColor(CardColor color) {
		this.topColor = color;
	}
	
	public void addThrownCard(Card thrownCard) {
		this.usedCards.add(thrownCard);
		this.setTopColor(thrownCard.getColor());
	}
	
	public Player registerPlayer(String nickname) throws Exception {
		// Validation check
		if (this.players.stream().anyMatch(p -> p.getNickname().equals(nickname))) {
			throw new Exception("The wanted nickname already exists");
		} else if (nickname.length() > Globals.MAX_NICKNAME_LENGTH) {
			throw new Exception("Nickname max length is " + Globals.MAX_NICKNAME_LENGTH);
		}
		
		// Create the player object and register it
		Player player = new Player(nickname, this.deck);
		this.players.add(player);
		
		return player;
	}
	
	private void rearrangeCards() {
		// Pop the first card
		Card onTop = this.usedCards.get(this.usedCards.size() - 1);
		this.usedCards.remove(this.usedCards.size() - 1);
		
		// Merge the used cards with the deck
		this.deck.addAll(this.usedCards);
	}
	
	public int countPlayers() {
		return this.players.size();
	}
	
	public boolean validatePlayerAction(Card thrownCard) {
		// If that's a special card
		if (thrownCard instanceof SpecialCard) {
			return true;
		} else {
			Card onTop = this.cardOnTop();
			
			// Check if the card on top of the deck shares the same color with the thrown card
			if (thrownCard.getColor().equals(this.getTopColor())) {
				return true;
			} 
			// If they don't have the same color, check if they are the same type of cards
			else if (thrownCard.getClass().equals(onTop.getClass())) {
				// If that's is an action card it's okay, because it means they are the same type of action cards.
				if (thrownCard instanceof ActionCard) {
					return true;
				} 
				// Make sure that both cards are numeric
				else if(thrownCard.getClass().equals(NumericCard.class)) {
					// Cast to numeric cards and validate their values
					NumericCard thrownCardNumeric = (NumericCard) thrownCard;
					NumericCard onTopNumeric = (NumericCard) onTop;
					
					if (thrownCardNumeric.getValue() == onTopNumeric.getValue()) {
						return true;
					} else {
						return false;
					}
				}
			} 
			
			return false;
		}
	}
	
	public void drawCard(Player player) {
		// If the deck is empty
		if (this.deck.isEmpty()) {
			this.rearrangeCards();
		}
		
		player.addCard(this.deck.draw());
	}
	
	public Card cardOnTop() {
		return this.usedCards.get(this.usedCards.size() - 1);
	}
	
	public Player getNextPlayer() {
		// Find the next player index 
		this.nextPlayersTurnIndex = 
				(this.nextPlayersTurnIndex + this.players.size() + this.gameDirection) % this.players.size();
		
		return (this.players.get(this.nextPlayersTurnIndex));
	}
	
	public void displayStatus() {
		// Run over the players
		for (int playerIndex = 0; playerIndex < this.players.size(); playerIndex++) {
			System.out.printf("%4s %-20s %d\n", 
					String.valueOf(playerIndex).concat("."),
					this.players.get(playerIndex).getNickname(),
					this.players.get(playerIndex).countCards());
		}
	}
	
	public boolean doTurn(Player player) {
		System.out.printf("%-30s     |     The card on top is: ", player.getNickname().concat(" is playing now"));
		this.cardOnTop().print();
		System.out.println();
		player.printHand();
		
		// Ask the player what he wants to do
		System.out.println("=================");
		System.out.println("1. Draw a card");
		System.out.println("2. Throw a card");
		System.out.println("3. Display game status");
		
		boolean turnStillOn = true;
		
		// Run as long as the player made a valid move
		while (turnStillOn) {
			System.out.print("==>  ");
			System.out.flush();
			
			// Read an action code
			int actionCode = Globals.reader.nextInt();

			switch (actionCode) {
			case 1:
				this.drawCard(player);
				player.printHand();
				turnStillOn = false;
				
				break;
			case 2:
				// Read a card to throw
				System.out.println("Enter the number of the card you wanna throw ==> ");
				int cardIndex = Globals.reader.nextInt();
				
				// Validation check
				if (cardIndex < 1 || cardIndex > player.countCards()) {
					System.out.println("No such card.");
				} else if (!this.validatePlayerAction(player.getCard(cardIndex - 1))) {
					System.out.println("Illegal move. Please choose another card, or draw one");
				} else {
					// Throw the player card
					Card thrownCard = player.throwCard(cardIndex - 1);
					
					// Add the thrown card to the used cards list
					this.addThrownCard(thrownCard);
					
					// Check if this is an action card, and if so, activate it
					if (thrownCard instanceof ActionCard) {
						((ActionCard) thrownCard).execute(this, player);
					}
					
					// If the player holds only a single card
					if (player.countCards() == 1) {
						Printer.instance().enqueue(new Message.MessageCreator(player.getNickname() + " has only 1 card left!").build());
					}
					
					turnStillOn = false;
				}
				
				break;
			case 3:
				this.displayStatus();
				
				break;
			default:
				System.out.println("Invalid input. Please enter a valid action code");
				
				break;
			}
		}
		
		System.out.println();
		
		// Check if player has won
		if (player.countCards() == 0) {
			return true;
		}
		
		return false;
	}
}
