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
		System.out.println("When you done, enter GO");
		
		// Read first player nickname
		System.out.print("==> ");
		System.out.flush();
		String playerNickname = Globals.reader.nextLine();
		
		// Register players
		while (!playerNickname.trim().toUpperCase().equals("GO") || Game.instance().countPlayers() == 0) {
			try {
				Game.instance().registerPlayer(playerNickname);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			// Read next player name
			System.out.print("==> ");
			System.out.flush();
			playerNickname = Globals.reader.nextLine();			
		}
		
		try {					
			Player winner = null;
			
			// Run as long as we dont have a winner
			while (winner == null) {
				// Clear the console
				Globals.clearConsole();
				
				// Print previous messages
				Printer.instance().print();
				System.out.println();
				
				// Get the next player and do his turn,
				// and check if the player has won the game
				Player playingNow = Game.instance().getNextPlayer();
				
				if (Game.instance().doTurn(playingNow)) {
					winner = playingNow;
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
