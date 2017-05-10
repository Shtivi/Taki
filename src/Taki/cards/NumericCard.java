package Taki.cards;

import java.util.InputMismatchException;

public class NumericCard extends Card {
	// Data members
	private int value;
	
	// Access methods
	
	public int getValue() {
		return value;
	}

	private void setValue(int value) {
		// Validation check
		if (value < 1 || value > 9) {
			throw new InputMismatchException("Numeric card value must be between 3 and 10");
		} else if (value == 2) {
			throw new InputMismatchException("Numeric card cannot have value of 2, it's an action card");
		}
		
		this.value = value;
	}

	// Ctor
	public NumericCard(CardColor color, int value) {
		super(color);
		this.setValue(value);
	}
	
	// Methods
	@Override
	public String name() {
		return (String.valueOf(this.getValue()));
	}
}
