package Taki.cards;

import Taki.game.Game;
import Taki.game.Player;

public interface ActionCard {
	void execute(Game game, Player currentPlayer);
}
