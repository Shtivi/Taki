package Taki.game;

import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;

import java.util.ArrayList;

import org.fusesource.jansi.AnsiConsole;

import Taki.cards.Card;
import Taki.cards.actionCards.TwoPlusCard;

public class Player {
	// Data members
	private String nickname;
	private ArrayList<Card> hand;
	
	// Access methods
	
	public String getNickname() {
		return nickname;
	}
	
	private void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	private ArrayList<Card> getHand() {
		return this.hand;
	}
	
	private void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}
	
	// Ctor
	public Player(String nickname, Deck cards) {
		this.setNickname(nickname);
		this.setHand(new ArrayList<Card>());
		
		// Draw cards
		for (int i = 0; i < Globals.INITIAL_HAND_CARDS; i++) {
			this.addCard(cards.draw());
		}
	}
	
	// Methods
	public void addCard(Card card) {
		this.getHand().add(card);
	}
	
	public Card getCard(int cardIndex) {
		// Make sure that the player a card in this index
		if (cardIndex < this.getHand().size()) {
			return this.getHand().get(cardIndex);
		} else {
			return null;
		}
	}
	
	public Card throwCard(int cardIndex) {
		// Make sure that the player a card in this index
		if (cardIndex < this.getHand().size()) {
			Card thrownCard = this.getHand().get(cardIndex);
			this.getHand().remove(cardIndex);
			
			return thrownCard; 
		} else {
			return null;
		}
	}
	
	public void throwCard(Card thrownCard) {
		this.getHand().remove(thrownCard);
	}
	
	public void printHand() {
		for (int cardIndex = 0; cardIndex < this.getHand().size(); cardIndex++) {
			System.out.print((cardIndex + 1) + ":");	
			this.getHand().get(cardIndex).print();
		}
		
		System.out.print("\n");
	}
	
	public int countCards() {
		return this.getHand().size();
	}
	
	public int findPlusTwoCard() {
		int plusTwoIndex = -1;
		
		for (int i = 0; i < this.getHand().size() && plusTwoIndex == -1; i++) {
			if (this.getHand().get(i) instanceof TwoPlusCard) {
				plusTwoIndex = i;
			}
		}
		
		return plusTwoIndex;
	}
}
