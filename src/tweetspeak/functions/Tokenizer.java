package tweetspeak.functions;

import java.util.*;

import tweetspeak.collections.TokenName;
import tweetspeak.collections.TokenType;
import tweetspeak.divisions.Code;
import tweetspeak.divisions.CodeLine;
import tweetspeak.objects.*;
import tweetspeak.objects.Error;

public class Tokenizer {
	private static String sourceCode = Code.getCode();
	private static String tokenizedCode = "";
	private static LinkedList<Token> tokens = new LinkedList<Token>();
	private HashMap<String, Token> SymbolTable;
	
	public Tokenizer () {
		SymbolTable = new HashMap<>(100);
		
		SymbolTable.put("areFriendsWith", new Token("areFriendsWith", TokenName.ASSIGN_OP.toString(),TokenType.RESERVED_WORD.toString()));
		SymbolTable.put("#comment", new Token("#comment", TokenName.COMMENT.toString(),TokenType.RESERVED_WORD.toString()));
		SymbolTable.put("#follow", new Token("#follow",TokenName.CONTINUE.toString(),TokenType.RESERVED_WORD.toString()));
		SymbolTable.put("#inbox", new Token("#inbox", TokenName.INPUT.toString(), TokenType.RESERVED_WORD.toString()));
		SymbolTable.put("#login", new Token("#login",TokenName.START.toString(), TokenType.RESERVED_WORD.toString()));
		SymbolTable.put("#logout", new Token("#logout", TokenName.END.toString(), TokenType.RESERVED_WORD.toString()));
		SymbolTable.put("#like", new Token("#like", TokenName.DO.toString(), TokenType.RESERVED_WORD.toString()));
		SymbolTable.put("#newsfeed", new Token("#newsfeed", TokenName.MAIN.toString(), TokenType.RESERVED_WORD.toString()));
		SymbolTable.put("#ooti", new Token("#ooti", TokenName.DATATYPE_INT.toString(), TokenType.RESERVED_WORD.toString()));
		SymbolTable.put("#ootf", new Token("#ootf", TokenName.DATATYPE_FLOAT.toString(), TokenType.RESERVED_WORD.toString()));
		SymbolTable.put("#ootc", new Token("#ootc", TokenName.DATATYPE_CHAR.toString(), TokenType.RESERVED_WORD.toString()));
		SymbolTable.put("#oots", new Token("#oots", TokenName.DATATYPE_STRING.toString(), TokenType.RESERVED_WORD.toString()));
		SymbolTable.put("#ootb", new Token("#ootb", TokenName.DATATYPE_BOOL.toString(), TokenType.RESERVED_WORD.toString()));
		SymbolTable.put("#ootv", new Token("#ootv", TokenName.DATATYPE_VOID.toString(), TokenType.RESERVED_WORD.toString()));
		SymbolTable.put("#outbox", new Token("#outbox", TokenName.OUTPUT.toString(), TokenType.RESERVED_WORD.toString()));
		SymbolTable.put("#retweet", new Token("#retweet", TokenName.ELSE_IF.toString(), TokenType.RESERVED_WORD.toString()));
		SymbolTable.put("#reply", new Token("#reply", TokenName.ELSE.toString(), TokenType.RESERVED_WORD.toString()));
		SymbolTable.put("#share", new Token("#share", TokenName.ASSIGN.toString(), TokenType.RESERVED_WORD.toString()));
		SymbolTable.put("#status", new Token("#status", TokenName.WHILE.toString(), TokenType.RESERVED_WORD.toString()));
		SymbolTable.put("#trending", new Token("#trending", TokenName.PROC_CALL.toString(),TokenType.RESERVED_WORD.toString()));
		SymbolTable.put("#throwback", new Token("#throwback", TokenName.PROC_RET.toString(), TokenType.RESERVED_WORD.toString()));
		SymbolTable.put("#tweet", new Token("#tweet", TokenName.IF.toString(), TokenType.RESERVED_WORD.toString()));
		SymbolTable.put("#unfollow", new Token("#unfollow", TokenName.BREAK.toString(), TokenType.RESERVED_WORD.toString()));
	}
	
	//setters
	public static void setSourceCode(String sourceCode) { Tokenizer.sourceCode = sourceCode; }
	public static void setTokenizedCode(String tokenizedCode) { Tokenizer.tokenizedCode = tokenizedCode; }
	
	//getters
	public static String getSourceCode() { return sourceCode; }
	public static String getTokenizedCode() { return tokenizedCode; }
	
	//methods
	public static void clearTokenizedCode() { Tokenizer.tokenizedCode = ""; }
	
	public static void tokenize() {
		clearTokenizedCode();
		for (CodeLine line : Code.getLineList()) {
			Token token;
			String code = line.getLineCode(), buffer = "";
			int index = 0;
			
			while (index < code.length()) {
				switch (code.charAt(index)) {
					case ' ': 
						if (index == 0) {
							while (index < code.length() && code.charAt(index) == ' ') buffer += code.charAt(index++);
							if (buffer.length() % 2 == 0 && !buffer.isEmpty()) {
								for (int i = 0; i < buffer.length(); i += 2) {
									token = new Token(buffer.substring(i, i + 2), 
											TokenName.NEST.toString(),
											TokenType.SPEC_SYMBOL.toString(),
											line.getLineNumber(),
											index);
									Tokenizer.tokens.add(token);
									line.addToken(token);
									tokenizedCode += token.printToken();
									index = token.getNextIndex();
								}
								continue;
							} else {
								token = new Error(buffer, 
										"INVALID NESTING",
										line.getLineNumber(),
										index);
								Tokenizer.tokens.add(token);
								line.addToken(token);
								tokenizedCode += token.printToken();
								index = token.getNextIndex();
								continue;
							}
						} else {
							tokenizedCode += code.charAt(index++);
							continue;
						}
					
					case '+': case '-':
					case '*': case '/':
					case '%': case '^':
					case '(': case ')':
						token = getArithmeticOperator(line, index);
						Tokenizer.tokens.add(token);
						line.addToken(token);
						tokenizedCode += token.printToken();
						index = token.getNextIndex();
						continue;
						
					case '|': case '&':
					case '>': case '<':
					case '=': case '!':
						token = getRelationalOperator(line, index);
						Tokenizer.tokens.add(token);
						line.addToken(token);
						tokenizedCode += token.printToken();
						//index = ((token instanceof Token) ? token.getNextIndex() - 1 : token.getNextIndex());
						index = token.getNextIndex();
						continue;
						
					default:
						tokenizedCode += code.charAt(index++);
						continue;
				}
			}
			tokenizedCode += "\n";
		}
	}
	
	public static Token getArithmeticOperator(CodeLine lineCode, int index) {
		String sourceCode = lineCode.getLineCode();
		String token = "";
		switch(sourceCode.charAt(index)) {
			case '+':
			    token += sourceCode.charAt(index++);
			    if (index < sourceCode.length() && sourceCode.charAt(index) == '+') {
			    	token += sourceCode.charAt(index++);
					return new Token(token, TokenName.INC_OP.toString(), TokenType.OPERATOR.toString(), lineCode.getLineNumber(), index);
			    } else return new Token(token, TokenName.ADD_OP.toString(), TokenType.OPERATOR.toString(), lineCode.getLineNumber(), index);
			case '-':
			    token += sourceCode.charAt(index++);
			    if (index < sourceCode.length() && sourceCode.charAt(index) == '-') {
			        token += sourceCode.charAt(index++);
			        return new Token(token, TokenName.DEC_OP.toString(), TokenType.OPERATOR.toString(), lineCode.getLineNumber(), index);
			    } else return new Token(token, TokenName.ADD_OP.toString(), TokenType.OPERATOR.toString(), lineCode.getLineNumber(), index);
			case '*':
			    token += sourceCode.charAt(index++);
				return new Token(token, TokenName.MUL_OP.toString(), TokenType.OPERATOR.toString(), lineCode.getLineNumber(), index);
			case '/':
			    token += sourceCode.charAt(index++);
			    return new Token(token, TokenName.DIV_OP.toString(), TokenType.OPERATOR.toString(), lineCode.getLineNumber(), index);
			case '%':
			    token += sourceCode.charAt(index++);
				return new Token(token, TokenName.MOD_OP.toString(), TokenType.OPERATOR.toString(), lineCode.getLineNumber(), index);
			case '^':
			    token += sourceCode.charAt(index++);
				return new Token(token, TokenName.EXP_OP.toString(), TokenType.OPERATOR.toString(), lineCode.getLineNumber(), index);
			case '(':
			    token += sourceCode.charAt(index++);
				return new Token(token, TokenName.LEFT_PAREN.toString(), TokenType.OPERATOR.toString(), lineCode.getLineNumber(), index);
			case ')':
			    token += sourceCode.charAt(index++);
				return new Token(token, TokenName.RIGHT_PAREN.toString(), TokenType.OPERATOR.toString(), lineCode.getLineNumber(), index);
		}
		return new Error(token, "INVALID ARITHMETIC OPERATOR", lineCode.getLineNumber(), index);
	}
	
	public static Token getRelationalOperator(CodeLine lineCode, int index) {
		String sourceCode = lineCode.getLineCode();
		String token = "";
		switch(sourceCode.charAt(index)) {
			case '|':
			    token += sourceCode.charAt(index++);
			    if (index < sourceCode.length() && sourceCode.charAt(index) == '|') {
			    	token += sourceCode.charAt(index++);
					return new Token(token, TokenName.OR_OP.toString(), TokenType.OPERATOR.toString(), lineCode.getLineNumber(), index);
			    } else return new Error(token, "INVALID OR OPERATOR", lineCode.getLineNumber(), index);
			case '&':
			    token += sourceCode.charAt(index++);
			    if (index < sourceCode.length() && sourceCode.charAt(index) == '&') {
			        token += sourceCode.charAt(index++);
			        return new Token(token, TokenName.AND_OP.toString(), TokenType.OPERATOR.toString(), lineCode.getLineNumber(), index);
			    } else return new Error(token, "INVALID 'AND' OPERATOR", lineCode.getLineNumber(), index);
			case '<':
			    token += sourceCode.charAt(index++);
			    if (index < sourceCode.length() && sourceCode.charAt(index) == '=') {
			    	token += sourceCode.charAt(index++);
					return new Token(token, TokenName.LESS_EQ_OP.toString(), TokenType.OPERATOR.toString(), lineCode.getLineNumber(), index);
			    } else return new Token(token, TokenName.LESS_OP.toString(), TokenType.OPERATOR.toString(), lineCode.getLineNumber(), index);
			case '>':
			    token += sourceCode.charAt(index++);
			    if (index < sourceCode.length() && sourceCode.charAt(index) == '=') {
			    	token += sourceCode.charAt(index++);
					return new Token(token, TokenName.GREAT_EQ_OP.toString(), TokenType.OPERATOR.toString(), lineCode.getLineNumber(), index);
			    } else return new Token(token, TokenName.GREAT_OP.toString(), TokenType.OPERATOR.toString(), lineCode.getLineNumber(), index);
			case '=':
			    token += sourceCode.charAt(index++);
			    if (index < sourceCode.length() && sourceCode.charAt(index) == '=') {
			    	token += sourceCode.charAt(index++);
					return new Token(token, TokenName.EQUAL_OP.toString(), TokenType.OPERATOR.toString(), lineCode.getLineNumber(), index);
			    } else return new Token(token, TokenName.ASSIGN_OP.toString(), TokenType.OPERATOR.toString(), lineCode.getLineNumber(), index);
			case '!':
			    token += sourceCode.charAt(index++);
			    if (index < sourceCode.length() && sourceCode.charAt(index) == '=') {
			    	token += sourceCode.charAt(index++);
					return new Token(token, TokenName.NOT_EQUAL_OP.toString(), TokenType.OPERATOR.toString(), lineCode.getLineNumber(), index);
			    } else return new Token(token, TokenName.NOT_OP.toString(), TokenType.OPERATOR.toString(), lineCode.getLineNumber(), index);
		}
		return new Error(token, "INVALID RELATIONAL OPERATOR", lineCode.getLineNumber(), index);
	}
	
	/*
	public static Token getReservedWords(CodeLine lineCode, int index) {
		String sourceCode = lineCode.getLineCode();
		String token = "";
		if (sourceCode.charAt(index) == '#') {
			token += sourceCode.charAt(index++);
			switch(sourceCode.charAt(index)) {
				
			}
		}
		
	}
	*/
}
	
