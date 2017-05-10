package Taki.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import Taki.cards.Card;
import Taki.cards.CardColor;
import Taki.cards.NumericCard;
import Taki.cards.actionCards.ChangeColorCard;
import Taki.cards.actionCards.PlusCard;
import Taki.cards.actionCards.StopCard;
import Taki.cards.actionCards.SuperTakiCard;
import Taki.cards.actionCards.SwitchDirectionCard;
import Taki.cards.actionCards.TakiCard;
import Taki.cards.actionCards.TwoPlusCard;

public class Deck {
	// Data members
	private LinkedList<Card> cards;
	
	// Ctor
	public Deck() {
		this.cards = new LinkedList<Card>();
		
		// Create the game cards and fill the package.
		// Go over the number of cards
		for (int colorIndex = 1; colorIndex < CardColor.values().length; colorIndex++) {
			CardColor currentColor = CardColor.values()[colorIndex];
			
			// Create CARD_COPIES_FOR_COLOR cards of each type
			for (int cardNumber = 1; cardNumber <= Globals.CARD_COPIES_FOR_COLOR; cardNumber++) {
				// Create numeric cards
				for (int numericCard = 1; numericCard <= 9; numericCard++) {
					// Except 2
					if (numericCard == 2) {
						this.cards.add(new TwoPlusCard(currentColor));
					} else {
						this.cards.add(new NumericCard(currentColor, numericCard));
					}
				}
				
				// Create action cards
				this.cards.add(new StopCard(currentColor));
				this.cards.add(new SwitchDirectionCard(currentColor));
				this.cards.add(new TakiCard(currentColor));
				this.cards.add(new PlusCard(currentColor));
			}
		}
		
		// Create color changers
		for (int colorChangerIndex = 1; colorChangerIndex <= Globals.COLOR_CHANGERS_NUM; colorChangerIndex++) {
			this.cards.add(new ChangeColorCard());
		}
		
		// Create super taki cards
		for (int superTakiIndex = 1; superTakiIndex <= Globals.SUPER_TAKIS_NUM; superTakiIndex++) {
			this.cards.add(new SuperTakiCard());
		}
		
		// Shuffle the cards
		this.shuffle();
	}
	
	// Methods
	
	public boolean isEmpty() {
		return this.cards.isEmpty();
	}
	
	public Card draw() {
		// If the deck is empty, return null
		if (this.cards.isEmpty()) {
			return null;
		} else {
			return (this.cards.poll());
		}
	}
	
	public void shuffle() {
		Collections.shuffle(this.cards);
	}
	
	public Card peek() {
		return this.cards.peek();
	}
	
	public void addAll(List<Card> cards) {
		this.cards.addAll(cards);
		this.shuffle();
	}
}
