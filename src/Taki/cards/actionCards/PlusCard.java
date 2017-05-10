package Taki.cards.actionCards;

import Taki.cards.ActionCard;
import Taki.cards.Card;
import Taki.cards.CardColor;
import Taki.game.Game;
import Taki.game.Player;

public class PlusCard extends Card implements ActionCard {

	public PlusCard(CardColor color) {
		super(color);
	}

	@Override
	public void execute(Game game, Player currentPlayer) {
		game.doTurn(currentPlayer);
	}

	@Override
	public String name() {
		return ("+");
	}

}
