package Taki.cards.actionCards;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import Taki.cards.ActionCard;
import Taki.cards.Card;
import Taki.cards.CardColor;
import Taki.cards.SpecialCard;
import Taki.game.Game;
import Taki.game.Globals;
import Taki.game.Player;
import Taki.printer.Message;
import Taki.printer.Printer;

public class SuperTakiCard extends SpecialCard {

	@Override
	public void execute(Game game, Player currentPlayer) {
		// Play change color
		ChangeColorCard.playChangeColor(game, currentPlayer);
		
		// Play taki
		TakiCard.playTaki(game, currentPlayer);
	}
	
	@Override
	public String name() {
		return ("ST");
	}

}
