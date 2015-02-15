package tweetspeak.divisions;

import java.util.*;

import tweetspeak.objects.Token;

public class CodeLine {
	private LinkedList<Token> tokens = new LinkedList<Token>();
	private String lineCode;
	private int lineNumber;
	
	//constructors
	public CodeLine() {
		setLineCode("");
		setLineNumber(0);
	}
	
	public CodeLine(String lineCode, int lineNumber) {
		setLineCode(lineCode);
		setLineNumber(lineNumber);
	}
	
	//getters
	public LinkedList<Token> getTokens() { return tokens; }
	public String getLineCode() { return lineCode; }
	public int getLineNumber() { return lineNumber; }
	
	//setters
	public void setTokens(LinkedList<Token> tokens) { this.tokens = tokens; }
	public void setLineCode(String lineCode) { this.lineCode = lineCode; }
	public void setLineNumber(int lineNumber) { this.lineNumber = lineNumber; }
	
	//methods
	public void addToken(Token token) { tokens.add(token); }
	public String toString() {
		return lineNumber + "\t" + lineCode;
	}
}