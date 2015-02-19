package tweetspeak.functions;

import java.util.LinkedList;

import tweetspeak.collections.TokenName;
import tweetspeak.collections.TokenType;
import tweetspeak.divisions.CodeLine;
import tweetspeak.objects.Token;

public class Tokenizer {
	private String sourceCode;
	private LinkedList<Token> tokens = new LinkedList<Token>();
	private int lineIndex;
	
	//getters
	public String getSourceCode() { return sourceCode; }
	
	//setters
	public void setSourceCode(String token) { this.sourceCode = token; }
	
	//methods
	public void tokenize(CodeLine lineCode) {
		String source = lineCode.getLineCode();
		String tokenName = "", tokenType = "";
		String tokens[] = source.split(" ");
		for (String token : tokens) {
			switch (token) {
				case "#login":
					tokenName = TokenName.START.toString();
					tokenType = TokenType.RESERVED_WORD.toString();
					break;
				case "#logout":
					tokenName = TokenName.END.toString();
					tokenType = TokenType.RESERVED_WORD.toString();
					break;
				default:
					for (char c : token.toCharArray()) {
						switch (c) {
							case '+':
								tokenName = TokenName.ADD_OP.toString();
								tokenType = TokenType.OPERATOR.toString();
								break;
						}
					}
					Token t = new Token(token, tokenName, tokenType, lineCode.getLineNumber(), source.indexOf(token));
					this.tokens.add(t);
					lineCode.addToken(t);
			}
		}
	}
}
