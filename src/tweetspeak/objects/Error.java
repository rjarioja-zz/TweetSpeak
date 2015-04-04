package tweetspeak.objects;

import tweetspeak.collections.TokenType;

public class Error extends Token{
	
	private String errorMessage;
	
	//constructors
	public Error(String lexeme, String errorMessage) {
		super(lexeme, TokenType.ERROR.toString(), TokenType.ERROR.toString());
		setErrorMessage(errorMessage);
	}
	
	public Error(String lexeme, String errorMessage, int lineNumber, int nextIndex) {
		this(lexeme, errorMessage);
		setLineNumber(lineNumber);
		setStartIndex(nextIndex - lexeme.length());
		setNextIndex(nextIndex);
	}
	
	//setters
	public void setErrorMessage (String errorMessage) { this.errorMessage = errorMessage; }
		
	//getters
	public String getErrorMessage() { return errorMessage; }
	
	//methods
	public String printToken() {
		return "[ERROR at line " + getLineNumber() + ", index " + getNextIndex() + " = " + errorMessage + "]";
	}
	
	public String toString() {
		return "[\"" + getLexeme()  + "\", " + getType() + ", " + getErrorMessage() + ", " + getLineNumber() + ", " + getStartIndex() + "]";
	}
}
