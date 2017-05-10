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
			this.getHand().get(cardIndex).print();
		}
		System.out.println();
		
		for (int cardIndex = 0; cardIndex < this.getHand().size(); cardIndex++) {
			String cardOutput = this.getHand().get(cardIndex).toString();
			int realIndex = cardIndex + 1;
			int indexLength = String.valueOf(realIndex).length();
			int leftPadding = cardOutput.length() / 2 - indexLength / 2;
			int rightPadding = leftPadding + indexLength - 1;
			
			if (cardOutput.length() % 2 == 0) {
				leftPadding--;
			}
			
			for (int i = 0; i < leftPadding; i++) {
				System.out.print(" ");
			}
			
			System.out.print(realIndex);
			
			for (int i = 0; i < rightPadding; i++) {
				System.out.print(" ");
			}
			
			System.out.flush();
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
