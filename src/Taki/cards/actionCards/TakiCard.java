package Taki.cards.actionCards;

import Taki.cards.ActionCard;
import Taki.cards.Card;
import Taki.cards.CardColor;
import Taki.game.Game;
import Taki.game.Globals;
import Taki.game.Player;

public class TakiCard extends Card implements ActionCard {
	public static void playTaki(Game game, Player currentPlayer, ActionCard takiCard) {
		// Reprint the player's hand
		currentPlayer.printHand();
		
		System.out.println("Choose a card number to drop.");
		System.out.println("When you want to stop dropping cards, enter -1 ==> ");
		
		// Read card input
		int cardInput = Globals.reader.nextInt();
		
		boolean forcedStop = false;
		
		// Run until the player stops or forced to stop
		while (cardInput != -1 && !forcedStop) {
			// Validation check
			if (cardInput < 1 || cardInput > currentPlayer.countCards()) {
				System.out.println("No such card.");
			} else if (!game.validatePlayerAction(currentPlayer.getCard(cardInput - 1))) {
				System.out.println("Illegal move.");
			} else {
				CardColor previousColor = game.getTopColor();
				
				// Throw card
				Card thrownCard = currentPlayer.throwCard(cardInput - 1);			
				
				// Add the thrown card to the used cards list
				game.addThrownCard(thrownCard);
				
				// Reprint the player's hand
				currentPlayer.printHand();
					
				// Check if the thrown card color is different from the deck top color.
				// If so, it means the players has to stop his taki
				if (!previousColor.equals(thrownCard.getColor())) {
					forcedStop = true;
				}					
			}
			
			// If the player changed the top color and has been forced to stop his taki
			if (!forcedStop) {
				// Read next card input
				System.out.println("Drop anotehr card, or enter -1 to stop ==> ");
				cardInput = Globals.reader.nextInt();
			}
		}
		
		// Check if last thrown card is an action card, and if so, activate it
		// In addition, make sure that the card on top is not the current Taki card (to prevent recoursive call)
		if ((takiCard != game.cardOnTop()) && (game.cardOnTop() instanceof ActionCard)) {
			((ActionCard) game.cardOnTop()).execute(game, currentPlayer);
		}
	}
	
	public TakiCard(CardColor color) {
		super(color);
	}

	@Override
	public void execute(Game game, Player currentPlayer) {
		playTaki(game, currentPlayer, this);
	}
	
	@Override
	public String name() {
		return ("T");
	}

}
