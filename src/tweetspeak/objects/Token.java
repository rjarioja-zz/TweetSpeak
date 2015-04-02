package tweetspeak.objects;

public class Token {
	private String lexeme, name, type;
	private int lineNumber, startIndex, nextIndex;
	
	//constructors
	public Token(String lexeme, String name, String type) {
		setLexeme(lexeme);
		setName(name);
		setType(type);
		setLineNumber(-1);
		setStartIndex(-1);
		setNextIndex(-1);
	}
	
	public Token(String lexeme, String name, String type, int lineNumber, int nextIndex) {
		this(lexeme, name, type);
		setLineNumber(lineNumber);
		setStartIndex(nextIndex - lexeme.length());
		setNextIndex(nextIndex);
	}
	
	public Token(String lexeme, String name, String type, int lineNumber, int startIndex, int nextIndex) {
		this(lexeme, name, type, lineNumber, startIndex);
		setNextIndex(nextIndex);
	}
	
	//setters
	public void setLexeme(String lexeme) { this.lexeme = lexeme; }
	public void setName(String name) { this.name = name; }
	public void setType(String type) { this.type = type; }
	public void setLineNumber(int lineNumber) { this.lineNumber = lineNumber; }
	public void setStartIndex(int startIndex) { this.startIndex = startIndex; }
	public void setNextIndex(int nextIndex) { this.nextIndex = nextIndex; }
		
	//getters
	public String getLexeme() { return lexeme; }
	public String getName() { return name; }
	public String getType() { return type; }
	public int getLineNumber() { return lineNumber; }
	public int getStartIndex() { return startIndex; }
	public int getNextIndex() { return nextIndex; }
		
	//methods
	public String printToken() {
		return "[\"" + lexeme  + "\", " + name + ", " + type + "]";
	}
	
	public String toString() {
		return "[\"" + lexeme  + "\", " + name + ", " + type + ", " + lineNumber + ", " + nextIndex + "]";
	}
}
