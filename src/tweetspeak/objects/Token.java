package tweetspeak.objects;

public class Token {
	private String lexeme, name, type;
	private int lineNumber, indexNumber;
	
	public Token(String lexeme, String name, String type, int lineNumber, int indexNumber) {
		setLexeme(lexeme);
		setName(name);
		setType(type);
		setLineNumber(lineNumber);
		setIndexNumber(indexNumber);
	}
	
	//getters
	public String getLexeme() { return lexeme; }
	public String getName() { return name; }
	public String getType() { return type; }
	public int getIndexNumber() { return indexNumber; }
	public int getLineNumber() { return lineNumber; }
		
	//setters
	public void setLexeme(String lexeme) { this.lexeme = lexeme; }
	public void setName(String name) { this.name = name; }
	public void setType(String type) { this.type = type; }
	public void setLineNumber(int lineNumber) { this.lineNumber = lineNumber; }
	public void setIndexNumber(int indexNumber) { this.indexNumber = indexNumber; }
}
