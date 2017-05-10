package Taki.printer;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import org.fusesource.jansi.Ansi.Color;

public class Message {
	// Data members
	private String text;
	private Color color;
	private boolean newLine;
	
	// Access methods
	
	public String getText() {
		return text;
	}
	
	private void setText(String text) {
		this.text = text;
	}
	
	public Color getColor() {
		return color;
	}
	
	private void setColor(Color color) {
		this.color = color;
	}
	
	public boolean getNewLine() {
		return this.newLine;
	}
	
	private void setNewLine(boolean newLine) {
		this.newLine = newLine;
	}
	
	// Ctor
	private Message(MessageCreator builder) {
		this.setColor(builder.color);
		this.setNewLine(builder.newLine);
		this.setText(builder.text);
	}
	
	// Methods
	public void print() {
		AnsiConsole.out.print(Ansi.ansi().fg(this.getColor()).a(this.getText()).reset());
	
		if (this.getNewLine()) {
			AnsiConsole.out.println();
		}
	}

	// Builder class
	public static class MessageCreator {
		// Data members
		private String text;
		private Color color;
		private boolean newLine;
		
		// Ctor
		public MessageCreator(String text) {
			this.text = text;
			this.color = Color.DEFAULT;
			this.newLine = true;
		}
		
		// Setters
		
		public MessageCreator setText(String text) {
			this.text = text;
			return this;
		}
		
		public MessageCreator setColor(Color color) {
			this.color = color;
			return this;
		}
		
		public MessageCreator setNewLine(boolean newLine) {
			this.newLine = newLine;
			return this;
		}
		
		// Methods
		
		public Message build() {
			return new Message(this);
		}
	}
}
