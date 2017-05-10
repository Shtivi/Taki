package Taki.cards.actionCards;

import Taki.cards.ActionCard;
import Taki.cards.Card;
import Taki.cards.CardColor;
import Taki.game.Game;
import Taki.game.Player;
import Taki.printer.Message;
import Taki.printer.Printer;

public class SwitchDirectionCard extends Card implements ActionCard {

	public SwitchDirectionCard(CardColor color) {
		super(color);
	}

	@Override
	public void execute(Game game, Player currentPlayer) {
		Game.instance().setDirection(Game.instance().getDirection() * -1);
		
		Printer.instance().enqueue(new Message.MessageCreator(currentPlayer.getNickname() + " has changed the direction!").build());
	}
	
	@Override
	public String name() {
		return ("<->");
	}

}
