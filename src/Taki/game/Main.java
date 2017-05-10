package Taki.game;

import static org.fusesource.jansi.Ansi.*;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import Taki.printer.Printer;

public class Main {

	public static void main(String[] args) {
		AnsiConsole.systemInstall();
		
		// Print logo
		Globals.clearConsole();
		printLogo();
		System.out.println("----------------------");
		System.out.printf ("       Welcome!       \n");
		System.out.println("----------------------");
		System.out.println("To start the game, enter players' nicknames.");
		System.out.println("There must be at least " + Globals.MIN_PLAYERS_AMOUNT + " players in order to start.");
		System.out.println("When you done, enter GO");
		
		// Read first player nickname
		System.out.print("==> ");
		System.out.flush();
		String playerNickname = Globals.reader.nextLine().trim();
		
		// Register players
		while (Game.instance().countPlayers() < 2 || !playerNickname.equalsIgnoreCase(Globals.FINISH_PLAYERS_REGISTERATION)) {
			// If trying to register the the word FINISH_PLAYERS_REGISTERATION
			if (playerNickname.equalsIgnoreCase(Globals.FINISH_PLAYERS_REGISTERATION)) {
				System.out.println("You can't use the word " + Globals.FINISH_PLAYERS_REGISTERATION + " as a nickname");
			} else {
				try {
					Game.instance().registerPlayer(playerNickname);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			
			// Read next player name
			System.out.print("==> ");
			System.out.flush();
			playerNickname = Globals.reader.nextLine();			
		}
		
		try {					
			Player winner = null;
			
			// Find the first player
			Player playingNow = Game.instance().getNextPlayer();
			
			// Run as long as we dont have a winner
			while (winner == null) {
				// Clear the console
				Globals.clearConsole();
				
				// Print previous messages
				Printer.instance().print();
				System.out.println();
	
				System.out.printf("%-30s     |     The card on top is: ", playingNow.getNickname().concat(" is playing now"));
				Game.instance().cardOnTop().print(Game.instance().getTopColor());
				System.out.println();				
				
				// Perform the current player turn,
				// then check if he won the game
				if (Game.instance().doTurn(playingNow)) {
					winner = playingNow;
				} else {
					// Pass the turn
					playingNow = Game.instance().getNextPlayer();
					
					// Wait for the next player to get his turn
					Globals.clearConsole();
					System.out.println("Pass the turn to " + playingNow.getNickname());
					System.out.println("Hit ENTER when ready.");
					Globals.reader.nextLine();
					Globals.reader.nextLine();
				}
			}
			
			// Print winner!
			System.out.println(winner.getNickname().concat(" has won the game!"));
		} catch (Exception e) {
			System.out.println("An error occured: " + e.getMessage());
			e.printStackTrace();
		}
		
		AnsiConsole.systemUninstall();
	}

	private static void printLogo() {
		Globals.colorPrint(Color.GREEN, " _____ ");
		Globals.colorPrint(Color.RED, "___ ");
		Globals.colorPrint(Color.YELLOW, " _   _");
		Globals.colorPrint(Color.BLUE, "____ ");
		System.out.println();
		
		Globals.colorPrint(Color.GREEN, "(_   _)");
		Globals.colorPrint(Color.RED, " _ \\");
		Globals.colorPrint(Color.YELLOW, "| | / ");
		Globals.colorPrint(Color.BLUE, "(   )");
		System.out.println();
		
		Globals.colorPrint(Color.GREEN, "  | |");
		Globals.colorPrint(Color.RED, "| |_| ");
		Globals.colorPrint(Color.YELLOW, "| |/ /");
		Globals.colorPrint(Color.BLUE, " | | ");
		System.out.println();
		
		Globals.colorPrint(Color.GREEN, "  | |");
		Globals.colorPrint(Color.RED, "|  _  ");
		Globals.colorPrint(Color.YELLOW, "|   <  ");
		Globals.colorPrint(Color.BLUE, "| | ");
		System.out.println();
		
		Globals.colorPrint(Color.GREEN, "  | |");
		Globals.colorPrint(Color.RED, "| | | ");
		Globals.colorPrint(Color.YELLOW, "| |\\ \\ ");
		Globals.colorPrint(Color.BLUE, "| | ");
		System.out.println();
		
		Globals.colorPrint(Color.GREEN, "  |_|");
		Globals.colorPrint(Color.RED, "|_| |_");
		Globals.colorPrint(Color.YELLOW, "|_| \\_");
		Globals.colorPrint(Color.BLUE, "(___)");
		System.out.println();
	}
}
