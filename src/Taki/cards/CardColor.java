package Taki.cards;

import org.fusesource.jansi.Ansi.Color;

public enum CardColor {
	NONE(Color.DEFAULT),
	BLUE(Color.BLUE),
	YELLOW(Color.YELLOW),
	GREEN(Color.GREEN),
	RED(Color.RED);
	
	// Data members
	private Color ansi;
	
	// Access methods
	private void setAnsi(Color ansi) {
		this.ansi = ansi;
	}
	
	public Color getAnsi() {
		return this.ansi;
	}
	
	// Ctor
	CardColor(Color ansi) {
		this.setAnsi(ansi);
	}
}
