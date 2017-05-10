package Taki.cards;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.Ansi.Color;
import org.fusesource.jansi.AnsiConsole;

import Taki.game.Globals;
import Taki.printer.Printer;

public abstract class Card {
	// Data members
	private CardColor color;
	
	// Access methods
	public CardColor getColor() {
		return color;
	}

	protected void setColor(CardColor color) {
		this.color = color;
	}
	
	// Ctor
	public Card(CardColor color) {
		this.setColor(color);
	}
	
	// Methods
	public abstract String name();
	
	@Override
	public String toString() {
		return (" [" + this.name() + "] ");
	}
	
	public void print() {
		Globals.colorPrint(this.getColor().getAnsi(), this.toString());
	}
	
	public void print(CardColor differentColor) {
		Globals.colorPrint(differentColor.getAnsi(), this.toString());
	}
}
