package Taki.cards.actionCards;

import Taki.cards.ActionCard;
import Taki.cards.Card;
import Taki.cards.CardColor;
import Taki.game.Game;
import Taki.game.Globals;
import Taki.game.Player;

public class TakiCard extends Card implements ActionCard {
	public static void playTaki(Game game, Player currentPlayer) {
		// Reprint the player's hand
		currentPlayer.printHand();
		
		System.out.println("Choose a card number to drop.");
		System.out.println("When you want to stop dropping cards, enter -1 ==> ");
		
		// Read card input
		int cardInput = Globals.reader.nextInt();
		
		// Run until the player stops
		while (cardInput != -1) {
			// Validation check
			if (cardInput < 1 || cardInput > currentPlayer.countCards()) {
				System.out.println("No such card.");
			} else if (!game.validatePlayerAction(currentPlayer.getCard(cardInput - 1))) {
				System.out.println("Illegal move.");
			} else {
				// Throw card
				Card thrownCard = currentPlayer.throwCard(cardInput - 1);
				
				// Add the thrown card to the used cards list
				game.addThrownCard(thrownCard);
				
				// Reprint the player's hand
				currentPlayer.printHand();
			}
			
			// Read next card input
			System.out.println("Drop anotehr card, or enter -1 to stop ==> ");
			cardInput = Globals.reader.nextInt();
		}
		
		// Check if last thrown card is an action card, and if so, activate it
		if (game.cardOnTop() instanceof ActionCard) {
			((ActionCard) game.cardOnTop()).execute(game, currentPlayer);
		}
	}
	
	public TakiCard(CardColor color) {
		super(color);
	}

	@Override
	public void execute(Game game, Player currentPlayer) {
		playTaki(game, currentPlayer);
	}
	
	@Override
	public String name() {
		return ("T");
	}

}
