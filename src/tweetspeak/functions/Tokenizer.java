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
	private HashMap<String, Token> reserveWordSymbolTable;
	private HashMap<String, Token> identifierSymbolTable;
	
	public Tokenizer () {
		reserveWordSymbolTable = new HashMap<>(100);
		reserveWordSymbolTable.put("areFriendsWith", new Token("areFriendsWith", TokenName.ASSIGN_OP.toString(),TokenType.RESERVED_WORD.toString()));
		reserveWordSymbolTable.put("#comment", new Token("#comment", TokenName.COMMENT.toString(),TokenType.RESERVED_WORD.toString()));
		reserveWordSymbolTable.put("#follow", new Token("#follow",TokenName.CONTINUE.toString(),TokenType.RESERVED_WORD.toString()));
		reserveWordSymbolTable.put("#inbox", new Token("#inbox", TokenName.INPUT.toString(), TokenType.RESERVED_WORD.toString()));
		reserveWordSymbolTable.put("#login", new Token("#login",TokenName.START.toString(), TokenType.RESERVED_WORD.toString()));
		reserveWordSymbolTable.put("#logout", new Token("#logout", TokenName.END.toString(), TokenType.RESERVED_WORD.toString()));
		reserveWordSymbolTable.put("#like", new Token("#like", TokenName.DO.toString(), TokenType.RESERVED_WORD.toString()));
		reserveWordSymbolTable.put("#newsfeed", new Token("#newsfeed", TokenName.MAIN.toString(), TokenType.RESERVED_WORD.toString()));
		reserveWordSymbolTable.put("#ooti", new Token("#ooti", TokenName.DATATYPE_INT.toString(), TokenType.RESERVED_WORD.toString()));
		reserveWordSymbolTable.put("#ootf", new Token("#ootf", TokenName.DATATYPE_FLOAT.toString(), TokenType.RESERVED_WORD.toString()));
		reserveWordSymbolTable.put("#ootc", new Token("#ootc", TokenName.DATATYPE_CHAR.toString(), TokenType.RESERVED_WORD.toString()));
		reserveWordSymbolTable.put("#oots", new Token("#oots", TokenName.DATATYPE_STRING.toString(), TokenType.RESERVED_WORD.toString()));
		reserveWordSymbolTable.put("#ootb", new Token("#ootb", TokenName.DATATYPE_BOOL.toString(), TokenType.RESERVED_WORD.toString()));
		reserveWordSymbolTable.put("#ootv", new Token("#ootv", TokenName.DATATYPE_VOID.toString(), TokenType.RESERVED_WORD.toString()));
		reserveWordSymbolTable.put("#outbox", new Token("#outbox", TokenName.OUTPUT.toString(), TokenType.RESERVED_WORD.toString()));
		reserveWordSymbolTable.put("#retweet", new Token("#retweet", TokenName.ELSE_IF.toString(), TokenType.RESERVED_WORD.toString()));
		reserveWordSymbolTable.put("#reply", new Token("#reply", TokenName.ELSE.toString(), TokenType.RESERVED_WORD.toString()));
		reserveWordSymbolTable.put("#share", new Token("#share", TokenName.ASSIGN.toString(), TokenType.RESERVED_WORD.toString()));
		reserveWordSymbolTable.put("#status", new Token("#status", TokenName.WHILE.toString(), TokenType.RESERVED_WORD.toString()));
		reserveWordSymbolTable.put("#trending", new Token("#trending", TokenName.PROC_CALL.toString(),TokenType.RESERVED_WORD.toString()));
		reserveWordSymbolTable.put("#throwback", new Token("#throwback", TokenName.PROC_RET.toString(), TokenType.RESERVED_WORD.toString()));
		reserveWordSymbolTable.put("#tweet", new Token("#tweet", TokenName.IF.toString(), TokenType.RESERVED_WORD.toString()));
		reserveWordSymbolTable.put("#unfollow", new Token("#unfollow", TokenName.BREAK.toString(), TokenType.RESERVED_WORD.toString()));
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
			int index = 0, prevNestCount = 0, currNestCount = 0;
			
			while (index < code.length()) {
				token = null; currNestCount = 0;
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
									currNestCount++;
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
						index = token.getNextIndex();
						continue;
						
					case '#':
						token = getReservedWords(line, index);
						Tokenizer.tokens.add(token);
						line.addToken(token);
						tokenizedCode += token.printToken();
						index = token.getNextIndex();
						continue;
						
					case '\'':
						token = getCharConstant(line, index);
						Tokenizer.tokens.add(token);
						line.addToken(token);
						tokenizedCode += token.printToken();
						index = token.getNextIndex();
						continue;
						
					case '"':
						token = getStringConstant(line, index);
						Tokenizer.tokens.add(token);
						line.addToken(token);
						tokenizedCode += token.printToken();
						index = token.getNextIndex();
						continue;
						
					case '0': case '1':
					case '2': case '3':
					case '4': case '5':
					case '6': case '7':
					case '8': case '9':
						token = getNumConstant(line, index);
						Tokenizer.tokens.add(token);
						line.addToken(token);
						tokenizedCode += token.printToken();
						index = token.getNextIndex();
						continue;
						
					case 'a': case 'd':
						token = getBoolConstant(line, index);
						Tokenizer.tokens.add(token);
						line.addToken(token);
						tokenizedCode += token.printToken();
						index = token.getNextIndex();
						continue;
						
					default:
						token = getIdentifier(line, index);
						Tokenizer.tokens.add(token);
						line.addToken(token);
						tokenizedCode += code.charAt(index++);
						index = token.getNextIndex();
						continue;
				}
			}
			tokenizedCode += "\n";
			prevNestCount = currNestCount;
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
		return new Error(token, "INVALID RELATIONAL OPERATOR - " + token, lineCode.getLineNumber(), index);
	}
	
	public static Token getReservedWords(CodeLine lineCode, int index) {
		String sourceCode = lineCode.getLineCode();
		String token = "";
		switch(sourceCode.charAt(index)) {
			case '#':
				token += sourceCode.charAt(index++);
				switch (sourceCode.charAt(index)) {
				
					// follow
					case 'f':
						token += sourceCode.charAt(index++);
						if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
						if (sourceCode.charAt(index) == 'o') {
							token += sourceCode.charAt(index++);
							if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
							if (sourceCode.charAt(index) == 'l') {
								token += sourceCode.charAt(index++);
								if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
								if (sourceCode.charAt(index) == 'l') {
									token += sourceCode.charAt(index++);
									if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
									if (sourceCode.charAt(index) == 'o') {
										token += sourceCode.charAt(index++);
										if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
										if (sourceCode.charAt(index) == 'w') {
											token += sourceCode.charAt(index++);
											if (index == sourceCode.length()
											 || sourceCode.charAt(index) == ' ' 
											 || sourceCode.charAt(index) == ';') 
												return new Token(token, TokenName.CONTINUE.toString(), TokenType.RESERVED_WORD.toString(), lineCode.getLineNumber(), index);
											else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
										} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
									} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
								} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
							} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
						} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
						
					// inbox
					case 'i':
						token += sourceCode.charAt(index++);
						if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
						if (sourceCode.charAt(index) == 'n') {
							token += sourceCode.charAt(index++);
							if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
							if (sourceCode.charAt(index) == 'b') {
								token += sourceCode.charAt(index++);
								if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
								if (sourceCode.charAt(index) == 'o') {
									token += sourceCode.charAt(index++);
									if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
									if (sourceCode.charAt(index) == 'x') {
										token += sourceCode.charAt(index++);
										if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
										if (sourceCode.charAt(index) == ' ') {
											return new Token(token, TokenName.INPUT.toString(), TokenType.RESERVED_WORD.toString(), lineCode.getLineNumber(), index + 1);
										} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
									} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
								} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
							} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
						} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
						
					// like, login, logout
					case 'l':
						token += sourceCode.charAt(index++);
						if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
						if (sourceCode.charAt(index) == 'i') {
							token += sourceCode.charAt(index++);
							if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
							if (sourceCode.charAt(index) == 'k') {
								token += sourceCode.charAt(index++);
								if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
								if (sourceCode.charAt(index) == 'e') {
									token += sourceCode.charAt(index++);
									if (index == sourceCode.length()
									 || sourceCode.charAt(index) == '#' 
									 || Character.isWhitespace(sourceCode.charAt(index)))
										return new Token(token, TokenName.DO.toString(), TokenType.RESERVED_WORD.toString(), lineCode.getLineNumber(), index);
									else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
								} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
							} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
						} else if (sourceCode.charAt(index) == 'o') {
							token += sourceCode.charAt(index++);
							if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
							if (sourceCode.charAt(index) == 'g') {
								token += sourceCode.charAt(index++);
								if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
								if (sourceCode.charAt(index) == 'i') {
									token += sourceCode.charAt(index++);
									if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
									if (sourceCode.charAt(index) == 'n') {
										if (index + 1 >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index + 1);
										token += sourceCode.charAt(index++);
										if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
										if (sourceCode.charAt(index) == ' ' && index++ - 6 == 0) {
											return new Token(token, TokenName.START.toString(), TokenType.RESERVED_WORD.toString(), lineCode.getLineNumber(), index);
										} else return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
									} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
								} else if (sourceCode.charAt(index) == 'o') {
									token += sourceCode.charAt(index++);
									if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
									if (sourceCode.charAt(index) == 'u') {
										token += sourceCode.charAt(index++);
										if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
										if (sourceCode.charAt(index) == 't') {
											token += sourceCode.charAt(index++);
											if (index == sourceCode.length()) {
												if (index - 7 == 0) return new Token(token, TokenName.END.toString(), TokenType.RESERVED_WORD.toString(), lineCode.getLineNumber(), index);
												else return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
											} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
										} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
									} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
								} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
							} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
						} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
						
					// newsfeed
					case 'n':
						token += sourceCode.charAt(index++);
						if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
						if (sourceCode.charAt(index) == 'e') {
							token += sourceCode.charAt(index++);
							if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
							if (sourceCode.charAt(index) == 'w') {
								token += sourceCode.charAt(index++);
								if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
								if (sourceCode.charAt(index) == 's') {
									token += sourceCode.charAt(index++);
									if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
									if (sourceCode.charAt(index) == 'f') {
										token += sourceCode.charAt(index++);
										if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
										if (sourceCode.charAt(index) == 'e') {
											token += sourceCode.charAt(index++);
											if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
											if (sourceCode.charAt(index) == 'e') {
												token += sourceCode.charAt(index++);
												if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
												if (sourceCode.charAt(index) == 'd') {
													token += sourceCode.charAt(index++);
													if (index == sourceCode.length()
															 || sourceCode.charAt(index) == ' ' 
															 || sourceCode.charAt(index) == ';') 
														return new Token(token, TokenName.MAIN.toString(), TokenType.RESERVED_WORD.toString(), lineCode.getLineNumber(), index);
													else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
												} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
											} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
										} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
									} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
								} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
							} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
						} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
						
					// ooti, ootf, ootc, oots, ootb, ootv, outbox
					case 'o': 
						token += sourceCode.charAt(index++);
						if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
						if (sourceCode.charAt(index) == 'o') {
							token += sourceCode.charAt(index++);
							if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
							if (sourceCode.charAt(index) == 't') {
								token += sourceCode.charAt(index++);
								if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
								switch (sourceCode.charAt(index)) {
									case 'b': 
										token += sourceCode.charAt(index++);
										if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
										if (sourceCode.charAt(index) == ' ') return new Token(token, TokenName.DATATYPE_BOOL.toString(), TokenType.RESERVED_WORD.toString(), lineCode.getLineNumber(), index);
										else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
									case 'c':
										token += sourceCode.charAt(index++);
										if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
										if (sourceCode.charAt(index) == ' ') return new Token(token, TokenName.DATATYPE_CHAR.toString(), TokenType.RESERVED_WORD.toString(), lineCode.getLineNumber(), index);
										else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
									case 'f':
										token += sourceCode.charAt(index++);
										if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
										if (sourceCode.charAt(index) == ' ') return new Token(token, TokenName.DATATYPE_FLOAT.toString(), TokenType.RESERVED_WORD.toString(), lineCode.getLineNumber(), index);
										else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
									case 'i':
										token += sourceCode.charAt(index++);
										if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
										if (sourceCode.charAt(index) == ' ') return new Token(token, TokenName.DATATYPE_INT.toString(), TokenType.RESERVED_WORD.toString(), lineCode.getLineNumber(), index);
										else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
									case 's':
										token += sourceCode.charAt(index++);
										if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
										if (sourceCode.charAt(index) == ' ') return new Token(token, TokenName.DATATYPE_STRING.toString(), TokenType.RESERVED_WORD.toString(), lineCode.getLineNumber(), index);
										else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
									case 'v':
										token += sourceCode.charAt(index++);
										if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
										if (sourceCode.charAt(index) == ' ') return new Token(token, TokenName.DATATYPE_VOID.toString(), TokenType.RESERVED_WORD.toString(), lineCode.getLineNumber(), index);
										else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
									default: return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
								}
							} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
						} else if (sourceCode.charAt(index) == 'u') {
							token += sourceCode.charAt(index++);
							if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
							if (sourceCode.charAt(index) == 't') {
								token += sourceCode.charAt(index++);
								if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
								if (sourceCode.charAt(index) == 'b') {
									token += sourceCode.charAt(index++);
									if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
									if (sourceCode.charAt(index) == 'o') {
										token += sourceCode.charAt(index++);
										if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
										if (sourceCode.charAt(index) == 'x') {
											token += sourceCode.charAt(index++);
											if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
											if (sourceCode.charAt(index) == ' ') {
												return new Token(token, TokenName.OUTPUT.toString(), TokenType.RESERVED_WORD.toString(), lineCode.getLineNumber(), index + 1);
											} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
										} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
									} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
								} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
							} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
						} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
					
					// reply, retweet
					case 'r':
						token += sourceCode.charAt(index++);
						if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
						if (sourceCode.charAt(index) == 'e') {
							token += sourceCode.charAt(index++);
							if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
							if (sourceCode.charAt(index) == 'p') {
								token += sourceCode.charAt(index++);
								if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
								if (sourceCode.charAt(index) == 'l') {
									token += sourceCode.charAt(index++);
									if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
									if (sourceCode.charAt(index) == 'y') {
										token += sourceCode.charAt(index++);
										if (index == sourceCode.length()
										 || sourceCode.charAt(index) == ' ')
											return new Token(token, TokenName.ELSE.toString(), TokenType.RESERVED_WORD.toString(), lineCode.getLineNumber(), index);
										else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
									} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
								} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
							} else if (sourceCode.charAt(index) == 't') {
								token += sourceCode.charAt(index++);
								if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
								if (sourceCode.charAt(index) == 'w') {
									token += sourceCode.charAt(index++);
									if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
									if (sourceCode.charAt(index) == 'e') {
										token += sourceCode.charAt(index++);
										if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
										if (sourceCode.charAt(index) == 'e') {
											token += sourceCode.charAt(index++);
											if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
											if (sourceCode.charAt(index) == 't') {
												token += sourceCode.charAt(index++);
												if (index == sourceCode.length()
												 || sourceCode.charAt(index) == ' '
												 || sourceCode.charAt(index) == '(')
													return new Token(token, TokenName.ELSE_IF.toString(), TokenType.RESERVED_WORD.toString(), lineCode.getLineNumber(), index);
												else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
											} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
										} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
									} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
								} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
							} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
						} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
				
					// share, status
					case 's':
						token += sourceCode.charAt(index++);
						if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
						if (sourceCode.charAt(index) == 'h') {
							token += sourceCode.charAt(index++);
							if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
							if (sourceCode.charAt(index) == 'a') {
								token += sourceCode.charAt(index++);
								if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
								if (sourceCode.charAt(index) == 'r') {
									token += sourceCode.charAt(index++);
									if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
									if (sourceCode.charAt(index) == 'e') {
										token += sourceCode.charAt(index++);
										if (index == sourceCode.length()
										 || sourceCode.charAt(index) == ' ')
											return new Token(token, TokenName.ASSIGN.toString(), TokenType.RESERVED_WORD.toString(), lineCode.getLineNumber(), index);
										else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
									} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
								} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
							} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
						} else if (sourceCode.charAt(index) == 't') {
							token += sourceCode.charAt(index++);
							if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
							if (sourceCode.charAt(index) == 'a') {
								token += sourceCode.charAt(index++);
								if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
								if (sourceCode.charAt(index) == 't') {
									token += sourceCode.charAt(index++);
									if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
									if (sourceCode.charAt(index) == 'u') {
										token += sourceCode.charAt(index++);
										if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
										if (sourceCode.charAt(index) == 's') {
											token += sourceCode.charAt(index++);
											if (index == sourceCode.length()
											 || sourceCode.charAt(index) == ' ')
												return new Token(token, TokenName.WHILE.toString(), TokenType.RESERVED_WORD.toString(), lineCode.getLineNumber(), index);
											else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
										} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
									} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
								} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
							} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
						} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
				}
		}
		 return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
	}
	
	public static Token getCharConstant(CodeLine lineCode, int index) {
		String sourceCode = lineCode.getLineCode();
		String token = "";
		if(sourceCode.charAt(index) == '\'');
		{
			index++;
			token += sourceCode.charAt(index++);
			if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
			if(sourceCode.charAt(index++) == '\'')
				{
					return new Token(token, TokenName.CHAR_CONST.toString(), TokenType.CONSTANT.toString(), lineCode.getLineNumber(), index);
				} 
		} return new Error(token, "INVALID TOKEN - "  + token, lineCode.getLineNumber(), index);
	}
	
	public static Token getStringConstant(CodeLine lineCode, int index) {
		String sourceCode = lineCode.getLineCode();
		String token = "";
	
		if(sourceCode.charAt(index) == '"');
		{
			index++;
			while(sourceCode.charAt(index) != '"')
			{
				token += sourceCode.charAt(index++);
				if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
			}
		
			if(sourceCode.charAt(index) == '"')
			{
				index++;
				return new Token(token, TokenName.STRING_CONST.toString(), TokenType.CONSTANT.toString(), lineCode.getLineNumber(), index);
			} 
			return new Token(token, TokenName.STRING_CONST.toString(), TokenType.CONSTANT.toString(), lineCode.getLineNumber(), index);
		}  
	}
	public static Token getNumConstant(CodeLine lineCode, int index) {
		String sourceCode = lineCode.getLineCode();
		String token = "";
		
		while(index < sourceCode.length())
		{	
			//integer
			if(Character.isDigit(sourceCode.charAt(index)))
			{
				token += sourceCode.charAt(index++);
			} 
			else if(sourceCode.charAt(index) == ';')
			{
				return new Token(token, TokenName.INT_CONST.toString(), TokenType.CONSTANT.toString(), lineCode.getLineNumber(), index);
			} 
			else if(Character.isWhitespace(sourceCode.charAt(index)))
			{
				return new Token(token, TokenName.INT_CONST.toString(), TokenType.CONSTANT.toString(), lineCode.getLineNumber(), index);
			}
			
			//float
			else if(sourceCode.charAt(index) == '.')
			{
				token += sourceCode.charAt(index++);
				while(index < sourceCode.length())
				{
					if(Character.isDigit(sourceCode.charAt(index)))
					{
						token += sourceCode.charAt(index++);
					}
					else if(sourceCode.charAt(index) == ';')
					{
						return new Token(token, TokenName.FLOAT_CONST.toString(), TokenType.CONSTANT.toString(), lineCode.getLineNumber(), index);
					}
					else if(Character.isWhitespace(sourceCode.charAt(index)))
					{
						return new Token(token, TokenName.FLOAT_CONST.toString(), TokenType.CONSTANT.toString(), lineCode.getLineNumber(), index);
					} else return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
				}
				return new Token(token, TokenName.FLOAT_CONST.toString(), TokenType.CONSTANT.toString(), lineCode.getLineNumber(), index);
			}
		}
		return new Token(token, TokenName.INT_CONST.toString(), TokenType.CONSTANT.toString(), lineCode.getLineNumber(), index);
	}
	
	private static Token getBoolConstant(CodeLine lineCode, int index) {
		int indexi = index;
		String sourceCode = lineCode.getLineCode();
		String token = "";
		
		switch(sourceCode.charAt(index))
		{
		case 'a':
			token += sourceCode.charAt(index++);
			if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
			if (sourceCode.charAt(index) == 'c') {
				token += sourceCode.charAt(index++);
				if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
				if (sourceCode.charAt(index) == 'c') {
					token += sourceCode.charAt(index++);
					if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
						if (sourceCode.charAt(index) == 'e') {
							token += sourceCode.charAt(index++);
							if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
							if (sourceCode.charAt(index) == 'p') {
								token += sourceCode.charAt(index++);
								if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
								if (sourceCode.charAt(index) == 't') {
									token += sourceCode.charAt(index++);
									if (index == sourceCode.length()){ return new Token(token, TokenName.BOOL_CONST_TRUE.toString(), TokenType.CONSTANT.toString(), lineCode.getLineNumber(), index);
									} else if(sourceCode.charAt(index) == ';'){
											return new Token(token, TokenName.BOOL_CONST_TRUE.toString(), TokenType.CONSTANT.toString(), lineCode.getLineNumber(), index);
										} getIdentifier(lineCode, index);
								}else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
							} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
					} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
				} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
			} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
			
			//decline
		case 'd':
		if (sourceCode.charAt(index) == 'd') {
			token += sourceCode.charAt(index++);
			if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
			if (sourceCode.charAt(index) == 'e') {
				token += sourceCode.charAt(index++);
				if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
				if (sourceCode.charAt(index) == 'c') {
					token += sourceCode.charAt(index++);
					if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
						if (sourceCode.charAt(index) == 'l') {
							token += sourceCode.charAt(index++);
							if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
							if (sourceCode.charAt(index) == 'i') {
								token += sourceCode.charAt(index++);
								if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
								if (sourceCode.charAt(index) == 'n') {
									token += sourceCode.charAt(index++);
									if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
									if (sourceCode.charAt(index) == 'e') {
										token += sourceCode.charAt(index++);
										if (index == sourceCode.length()){ return new Token(token, TokenName.BOOL_CONST_FALSE.toString(), TokenType.CONSTANT.toString(), lineCode.getLineNumber(), index);
										} else if(sourceCode.charAt(index) == ';'){
											return new Token(token, TokenName.BOOL_CONST_FALSE.toString(), TokenType.CONSTANT.toString(), lineCode.getLineNumber(), index);
										} //return code balik check kasi bakaaa identifier or whatever
								}else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
							} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
						} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
					} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
				} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
			} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
		} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
	 return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
		default:
			getIdentifier(lineCode, index);
		}
		return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);	
	}
	

	public static Token getIdentifier(CodeLine lineCode, int index) {
		String sourceCode = lineCode.getLineCode();
  		String token = "";
  		
  		if(Character.isLetter(sourceCode.charAt(index)) || sourceCode.charAt(index) == '_')
		{
			token += sourceCode.charAt(index++);
			System.out.println(token);
			while(index < sourceCode.length())
			{
				if(Character.isLetter(sourceCode.charAt(index)) 
						|| sourceCode.charAt(index) == '_' 
						|| Character.isDigit(sourceCode.charAt(index)))
				{
					token += sourceCode.charAt(index++);
					System.out.println(token);
				}else if(Character.isWhitespace(sourceCode.charAt(index)))
					{
						return new Token(token, TokenName.VAR.toString(), TokenType.IDENTIFIER.toString(), lineCode.getLineNumber(), index);
					}else if(sourceCode.charAt(index) == ';')
					{
						return new Token(token, TokenName.VAR.toString(), TokenType.IDENTIFIER.toString(), lineCode.getLineNumber(), index);
					}else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
			}
		} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
  		return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
	}
}

	
