package Taki.printer;

import java.util.LinkedList;
import java.util.Queue;

public class Printer {
	// Singleton
	private static class Holder {
		static final Printer instance = new Printer();
	}
	
	public static Printer instance() {
		return Holder.instance;
	}
	
	// Data members
	private Queue<Message> prints;
	
	// Ctor
	private Printer() {
		this.prints = new LinkedList<Message>();
	}
	
	// Methods
	
	public void enqueue(Message message) {
		this.prints.add(message);
	}
	
	public Message pop() {
		return this.prints.poll();
	}
	
	public void print() {
		while (!this.prints.isEmpty()) {
			this.pop().print();
		}
	}
}
