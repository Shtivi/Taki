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

public class ChangeColorCard extends SpecialCard {
	
	public static void playChangeColor(Game game, Player currentPlayer) {
		// Print message to user
		System.out.println("Please choose a color:");
		
		// Go over the colors except NONE
		for (int colorIndex = 1; colorIndex < CardColor.values().length; colorIndex++) {
			CardColor currentColor = CardColor.values()[colorIndex];
			System.out.print(colorIndex + ". ");
			AnsiConsole.out.println(Ansi.ansi().fg(currentColor.getAnsi()).a(" ".concat(currentColor.name())).reset());
		}
		
		// Read user's input
		int chosenColor = Globals.reader.nextInt();
		
		// Run as long as the input is not right
		while (chosenColor < 1 && chosenColor > CardColor.values().length) {
			System.out.println("Invalid color code recieved, please enter a valid one ==> ");
			chosenColor = Globals.reader.nextInt();
		}
		
		// Change the color
		CardColor changedColor = CardColor.values()[chosenColor];
		Game.instance().setTopColor(changedColor);
		
		Printer.instance().enqueue(new Message.MessageCreator(currentPlayer.getNickname() + " has changed the color to " + changedColor.name()).setColor(changedColor.getAnsi()).build());
	}
	
	@Override
	public void execute(Game game, Player currentPlayer) {
		playChangeColor(game, currentPlayer);
	}
	
	@Override
	public String name() {
		return ("#");
	}

}
