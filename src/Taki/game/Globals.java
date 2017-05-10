package Taki.game;

import java.util.Scanner;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import org.fusesource.jansi.Ansi.Color;

public class Globals {
	// Const settings
	
	public static final int 	INITIAL_HAND_CARDS = 8;
	public static final Scanner reader = new Scanner(System.in);
	public static final String 	ANSI_RESET = "\u001B[0m";
	public static final String 	ANSI_BOLD = "\u001B[1m";
	public static final int 	MAX_NICKNAME_LENGTH = 15;
	public static final int		CARD_COPIES_FOR_COLOR = 2;
	public static final int		COLOR_CHANGERS_NUM = 4;
	public static final int		SUPER_TAKIS_NUM = 2;
	
	// Methods
	
	public static void colorPrint(Color color, String text) {
		AnsiConsole.out.print(Ansi.ansi().fg(color).a(text).reset());
	}
	
	public static void clearConsole() {
		AnsiConsole.out.print("\033[H\033[2J");
		System.out.flush();		
	}
}
