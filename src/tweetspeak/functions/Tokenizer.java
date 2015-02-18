package tweetspeak.functions;

import java.util.LinkedList;
import tweetspeak.objects.Token;

public class Tokenizer {
	private String sourceCode;
	private LinkedList<Token> tokens = new LinkedList<Token>();
	
	//getters
	public String getSourceCode() { return sourceCode; }
	
	//setters
	public void setSourceCode(String token) { this.sourceCode = token; }
}
