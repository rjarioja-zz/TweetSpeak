package tweetspeak.objects;

import tweetspeak.collections.TokenName;
import tweetspeak.collections.TokenType;

public class Comment extends Token {
	private String value = "";
	
	//constructors
	public Comment(String lexeme, String value) {
		super(lexeme, TokenName.COMMENT.toString(), TokenType.COMMENTS.toString());
		setValue(value);
	}
	
	public Comment(String lexeme, String value, int lineNumber, int nextIndex) {
		this(lexeme, value);
		setLineNumber(lineNumber);
		setNextIndex(nextIndex);
	}

	//setters
	public void setValue(String value) { this.value = value; }
	
	//getters
	public String getValue() { return value; }
	
	//methods
	public String printToken() {
		return "[COMMENTS = " + value + "]";
	}
	
	public String toString() {
		return "[\"" + getLexeme()  + "\", " + getType() + ", \"" + getValue() + "\"]";
	}
}
