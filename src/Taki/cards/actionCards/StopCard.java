package Taki.cards.actionCards;

import Taki.cards.ActionCard;
import Taki.cards.Card;
import Taki.cards.CardColor;
import Taki.game.Game;
import Taki.game.Player;
import Taki.printer.Message;
import Taki.printer.Printer;

public class StopCard extends Card implements ActionCard {

	public StopCard(CardColor color) {
		super(color);
	}

	@Override
	public void execute(Game game, Player currentPlayer) {
		Player skipped = Game.instance().getNextPlayer();
		Printer.instance().enqueue(new Message.MessageCreator(currentPlayer.getNickname() + " has skipped " + skipped.getNickname() + "'s turn!").build());
	}

	@Override
	public String name() {
		return ("STOP");
	}
}
