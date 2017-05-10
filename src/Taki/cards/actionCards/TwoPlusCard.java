package Taki.cards.actionCards;

import java.util.ArrayList;

import Taki.cards.ActionCard;
import Taki.cards.Card;
import Taki.cards.CardColor;
import Taki.game.Game;
import Taki.game.Globals;
import Taki.game.Player;
import Taki.printer.Message;
import Taki.printer.Printer;

public class TwoPlusCard extends Card implements ActionCard {

	public TwoPlusCard(CardColor color) {
		super(color);
	}

	@Override
	public void execute(Game game, Player currentPlayer) {
		boolean someoneTookCards = false;
		int thrownCardsCounter = 1;
		
		// Add the two plus card to the thrown cards list
		currentPlayer.throwCard(this);
		game.addThrownCard(this);
		
		// Run as long as someone takes the plus two cards
		while (!someoneTookCards) {
			Player nextPlayer = Game.instance().getNextPlayer();
			int plusTwoIndex = currentPlayer.findPlusTwoCard();
			
			// If the player does not has any plus 2 cards
			if (plusTwoIndex == -1) {
				int cardsToDraw = thrownCardsCounter * 2;
				Printer.instance().enqueue(new Message.MessageCreator(nextPlayer.getNickname() + " gets " + cardsToDraw + " cards").build());
				
				// Add the cards to the player's hand
				while (cardsToDraw > 0) {
					game.drawCard(nextPlayer);
					cardsToDraw--;
				}
				
				someoneTookCards = true;
			} else {
				// Ask the user if he wants to throw a +2
				System.out.println("You have a ".concat(this.name()).concat(", do you want to use it [Y/N]?"));
				String answer = Globals.reader.nextLine();
				
				if (answer.trim().toUpperCase().equals("Y")) {
					// Throw the player's +2 
					game.addThrownCard(nextPlayer.throwCard(plusTwoIndex));
					thrownCardsCounter++;
				} else {
					int cardsToDraw = thrownCardsCounter * 2;
					
					Printer.instance().enqueue(new Message.MessageCreator(nextPlayer.getNickname() + " gets " + cardsToDraw + " cards").build());
					
					// Add the cards to the player's hand
					while (cardsToDraw > 0) {
						game.drawCard(nextPlayer);
						cardsToDraw--;
					}
					
					someoneTookCards = true;
				}
			}
		}
	}
	
	@Override
	public String name() {
		return ("+2");
	}

}
