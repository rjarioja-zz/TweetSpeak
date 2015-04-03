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
	private static Stack<Integer> indentStack = new Stack<Integer>();
	private static HashMap<String, Token> reserveWordSymbolTable;
	private static HashMap<String, Token> identifierSymbolTable;
	private static int index = 0, lineNumber = 0, currentIndent = 0, previousIndent = 0;
	private static Token tokenBuffer = null;
	
	//constructors
	public Tokenizer () {}
	
	//setters
	public static void setTokens(LinkedList<Token> tokens) { Tokenizer.tokens = tokens; }
	public static void setSourceCode(String sourceCode) { Tokenizer.sourceCode = sourceCode; }
	public static void setTokenizedCode(String tokenizedCode) { Tokenizer.tokenizedCode = tokenizedCode; }
	public static void setLineNumber(int lineNumber) { Tokenizer.lineNumber = lineNumber; }
	public static void setIndex(int index) { Tokenizer.index = index; }
	
	//getters
	public static LinkedList<Token> getTokens() { return tokens; }
	public static String getSourceCode() { return sourceCode; }
	public static String getTokenizedCode() { return tokenizedCode; }
	public static int getLineNumber() { return lineNumber; }
	public static int getIndex() { return index; }
	
	//methods
	public static void initialize() {
		identifierSymbolTable = new HashMap<>(100);
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
		setIndex(0);
		setLineNumber(0);
		currentIndent = 0; 
		previousIndent = 0;
		indentStack.clear(); 
		indentStack.push(0);
	}
	
	public static void clearTokenizedCode() { 
		Tokenizer.tokenizedCode = ""; 
		setIndex(0);
		setLineNumber(0);
		setLineNumber(0);
		currentIndent = 0; 
		previousIndent = 0;
		indentStack.clear(); 
		indentStack.push(0);
	}

	public static Token getToken() {
		CodeLine line = Code.getLineList().get(getLineNumber());
		String code = "";
		Token token = null;		
		
		code = line.getLineCode();
		System.out.println("NEW CALL code:" + code + ","+ getIndex() + "," + getLineNumber() + " size:" + code.length());
		/*System.out.println("before = currentIndent: " + currentIndent + ", previousIndent: " + previousIndent
				 			+ ", indentStackTop: " + indentStack.peek());*/
		System.out.println("before = currentIndent: " + currentIndent + ", previousIndent: " + previousIndent);

		if (getIndex() >= code.length()) {
			System.out.print("first check if EOL: ");
			if (getLineNumber() + 1 == Code.getLineList().size()) return null;
			if (code.isEmpty()) {
				System.out.println("code is empty");
				token = new Token("\\n", "NEWLINE", TokenType.SPEC_SYMBOL.toString(), getLineNumber(), 0);
				setLineNumber(getLineNumber() + 1);
				line = Code.getLineList().get(getLineNumber());
				return token;
			}
			setLineNumber(getLineNumber() + 1);
			setIndex(0);
			tokenizedCode += "\n";
			line = Code.getLineList().get(getLineNumber());
			code = line.getLineCode();
			System.out.println("EOL ");
			System.out.println("NEXT CALL code:" + code + ","+ getIndex() + "," + getLineNumber() + " size:" + code.length());
			previousIndent = currentIndent;
		}
		
		if (code.isEmpty()) {
			System.out.println("check code if empty");
			token = new Token("\\n", "NEWLINE", TokenType.SPEC_SYMBOL.toString(), getLineNumber(), 0);
			setLineNumber(getLineNumber() + 1);
			line = Code.getLineList().get(getLineNumber());
			return token;
		} 
		
		if (getIndex() == 0) {
			System.out.println("check indents code:" + code);
			token = getIndents(line, getIndex());
			if (token.getName().equals("NO_INDENT")) {
				System.out.println("no indents");
				setIndex(token.getNextIndex());
			} else if (token.getName().equals("NEWLINE")) {
				System.out.println("check indents newline line: " + getLineNumber() + ", size: " + Code.getLineList().size());
				setLineNumber(getLineNumber() + 1);
				setIndex(0);
				line = Code.getLineList().get(getLineNumber());
				code = line.getLineCode();
			} else {
				System.out.println(token.toString());
				Tokenizer.getTokens().add(token);
				line.addToken(token);
				tokenizedCode += token.printToken();
				setIndex(token.getNextIndex());
				return token;
			}
		}
		
		while (getIndex() < code.length()) {
			System.out.println("in loop");

			if (getIndex() >= code.length()) {
				System.out.print("second check if EOL: ");
				if (getLineNumber() + 1 == Code.getLineList().size()) return null;
				if (code.isEmpty()) {
					System.out.println("code is empty");
					token = new Token("\\n", "NEWLINE", TokenType.SPEC_SYMBOL.toString(), getLineNumber(), 0);
					setLineNumber(getLineNumber() + 1);
					line = Code.getLineList().get(getLineNumber());
					return token;
				}
				setLineNumber(getLineNumber() + 1);
				setIndex(0);
				tokenizedCode += "\n";
				line = Code.getLineList().get(getLineNumber());
				code = line.getLineCode();
				System.out.println("EOL ");
				System.out.println("NEXT CALL code:" + code + ","+ getIndex() + "," + getLineNumber() + " size:" + code.length());
				previousIndent = currentIndent;
			}
			
			if (code.isEmpty()) {
				System.out.println("check code if empty");
				token = new Token("\\n", "NEWLINE", TokenType.SPEC_SYMBOL.toString(), getLineNumber(), 0);
				setLineNumber(getLineNumber() + 1);
				line = Code.getLineList().get(getLineNumber());
				return token;
			} 
			
			if (getIndex() == 0) {
				System.out.println("check indents code:" + code);
				token = getIndents(line, getIndex());
				if (token.getName().equals("NO_INDENT")) {
					System.out.println("no indents");
					setIndex(token.getNextIndex());
				} else if (token.getName().equals("NEWLINE")) {
					System.out.println("check indents newline line: " + getLineNumber() + ", size: " + Code.getLineList().size());
					setLineNumber(getLineNumber() + 1);
					setIndex(0);
					line = Code.getLineList().get(getLineNumber());
					code = line.getLineCode();
				} else {
					System.out.println(token.toString());
					Tokenizer.getTokens().add(token);
					line.addToken(token);
					tokenizedCode += token.printToken();
					setIndex(token.getNextIndex());
					return token;
				}
			}
			
			System.out.println("before switch");
			switch (code.charAt(getIndex())) {
				//skip spaces
				case ' ':
					System.out.print("space ");
					setIndex(getIndex() + 1);
					continue;
			
				case '+': case '-':
				case '*': case '/':
				case '%': case '^':
				case '(': case ')':
					token = getArithmeticOperator(line, getIndex());
					Tokenizer.getTokens().add(token);
					line.addToken(token);
					tokenizedCode += token.printToken();
					setIndex(token.getNextIndex());
					System.out.println("-= TOKEN CREATED =- " + token.getName());
					return token;
					//continue;
					
				case '|': case '&':
				case '>': case '<':
				case '=': case '!':
					token = getRelationalOperator(line, index);
					Tokenizer.getTokens().add(token);
					line.addToken(token);
					tokenizedCode += token.printToken();
					index = token.getNextIndex();
					System.out.println("-= TOKEN CREATED =- " + token.getName());
					return token;
					//continue;
					
				case '#':
					//System.out.println("reserved words");
					token = getComments(line, getIndex());
					if (token instanceof Error) token = getReservedWords(line, token.getStartIndex()); 
					Tokenizer.getTokens().add(token);
					line.addToken(token);
					tokenizedCode += token.printToken();
					setIndex(token.getNextIndex());
					System.out.println("-= TOKEN CREATED =- " + token.getName());
					return token;
					//continue;
					
				case ';':
				case ',':
					token = getOtherTokens(line, getIndex());
					Tokenizer.getTokens().add(token);
					line.addToken(token);
					tokenizedCode += token.printToken();
					setIndex(token.getNextIndex());
					System.out.println("-= TOKEN CREATED =- " + token.getName());
					return token;
					
				case '\'':
					token = getCharConstant(line, getIndex());
					if (token instanceof Error) token = getOtherTokens(line, token.getStartIndex());
					Tokenizer.getTokens().add(token);
					line.addToken(token);
					tokenizedCode += token.printToken();
					setIndex(token.getNextIndex());
					System.out.println("-= TOKEN CREATED =- " + token.getName());
					return token;
					//continue;
					
				case '"':
					token = getStringConstant(line, getIndex());
					if (token instanceof Error) token = getOtherTokens(line, token.getStartIndex());
					Tokenizer.getTokens().add(token);
					line.addToken(token);
					tokenizedCode += token.printToken();
					setIndex(token.getNextIndex());
					System.out.println("-= TOKEN CREATED =- " + token.getName());
					return token;
					//continue;
					
				case '0': case '1':
				case '2': case '3':
				case '4': case '5':
				case '6': case '7':
				case '8': case '9':
					token = getNumConstant(line, getIndex());
					Tokenizer.getTokens().add(token);
					line.addToken(token);
					tokenizedCode += token.printToken();
					setIndex(token.getNextIndex());
					System.out.println("-= TOKEN CREATED =- " + token.getName());
					return token;
					//continue;
					
				/*case 'a': case 'd':
					token = getBoolConstant(line, index);
					Tokenizer.tokens.add(token);
					line.addToken(token);
					tokenizedCode += token.printToken();
					index = token.getNextIndex();
					return token;
					//continue;
*/					
				default:
					System.out.println("default");
					token = getComments(line, index);
					if (token instanceof Error) token = getBoolConstant(line, index);
					if (token instanceof Error) token = getIdentifier(line, index);
					Tokenizer.getTokens().add(token);
					line.addToken(token);
					System.out.println("-= TOKEN CREATED =- " + token.getName());
					setIndex(token.getNextIndex());
					return token;
			}
		}
		
		previousIndent = currentIndent;
		//System.out.println("-= TOKEN CREATED =- " + token.getLexeme());
		return token;
	}

	private static Token getArithmeticOperator(CodeLine lineCode, int index) {
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
			    } else return new Token(token, TokenName.DIF_OP.toString(), TokenType.OPERATOR.toString(), lineCode.getLineNumber(), index);
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
	
	private static Token getRelationalOperator(CodeLine lineCode, int index) {
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

	private static Token getOtherTokens(CodeLine lineCode, int index) {
		String sourceCode = lineCode.getLineCode();
		String token = "";
		switch(sourceCode.charAt(index)) {
			case ',':
				token += sourceCode.charAt(index++);
			    return new Token(token, TokenName.PARAM_SEP.toString(), TokenType.SPEC_SYMBOL.toString(), lineCode.getLineNumber(), index);
			case '\'':
				token += sourceCode.charAt(index++);
			    return new Token(token, TokenName.SQUOTE.toString(), TokenType.SPEC_SYMBOL.toString(), lineCode.getLineNumber(), index);
			case '"':
				token += sourceCode.charAt(index++);
			    return new Token(token, TokenName.DQUOTE.toString(), TokenType.SPEC_SYMBOL.toString(), lineCode.getLineNumber(), index);
			case ';':
				token += sourceCode.charAt(index++);
			    return new Token(token, TokenName.STMT_SEP.toString(), TokenType.SPEC_SYMBOL.toString(), lineCode.getLineNumber(), index);
		}
		return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
	}	

	private static Token getReservedWords(CodeLine lineCode, int index) {
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
									 || sourceCode.charAt(index) == ';' 
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
										if (sourceCode.charAt(index++) == ' ') return new Token(token, TokenName.DATATYPE_BOOL.toString(), TokenType.RESERVED_WORD.toString(), lineCode.getLineNumber(), index);
										else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
									case 'c':
										token += sourceCode.charAt(index++);
										if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
										if (sourceCode.charAt(index++) == ' ') return new Token(token, TokenName.DATATYPE_CHAR.toString(), TokenType.RESERVED_WORD.toString(), lineCode.getLineNumber(), index);
										else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
									case 'f':
										token += sourceCode.charAt(index++);
										if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
										if (sourceCode.charAt(index++) == ' ') return new Token(token, TokenName.DATATYPE_FLOAT.toString(), TokenType.RESERVED_WORD.toString(), lineCode.getLineNumber(), index);
										else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
									case 'i':
										token += sourceCode.charAt(index++);
										if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
										if (sourceCode.charAt(index++) == ' ') return new Token(token, TokenName.DATATYPE_INT.toString(), TokenType.RESERVED_WORD.toString(), lineCode.getLineNumber(), index);
										else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
									case 's':
										token += sourceCode.charAt(index++);
										if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
										if (sourceCode.charAt(index++) == ' ') return new Token(token, TokenName.DATATYPE_STRING.toString(), TokenType.RESERVED_WORD.toString(), lineCode.getLineNumber(), index);
										else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
									case 'v':
										token += sourceCode.charAt(index++);
										if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
										if (sourceCode.charAt(index++) == ' ') return new Token(token, TokenName.DATATYPE_VOID.toString(), TokenType.RESERVED_WORD.toString(), lineCode.getLineNumber(), index);
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
										if (index == sourceCode.length()) 
											return new Token(token, TokenName.ELSE.toString(), TokenType.RESERVED_WORD.toString(), lineCode.getLineNumber(), index);
										if (sourceCode.charAt(index) == ' ')
											return new Token(token, TokenName.ELSE.toString(), TokenType.RESERVED_WORD.toString(), lineCode.getLineNumber(), index + 1);
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
												if (sourceCode.charAt(index) == ' ') return new Token(token, TokenName.ELSE_IF.toString(), TokenType.RESERVED_WORD.toString(), lineCode.getLineNumber(), index + 1);
												if (sourceCode.charAt(index) == ')') return new Token(token, TokenName.ELSE_IF.toString(), TokenType.RESERVED_WORD.toString(), lineCode.getLineNumber(), index);
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
										 || sourceCode.charAt(index++) == ' ')
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
											if (sourceCode.charAt(index) == '(') return new Token(token, TokenName.WHILE.toString(), TokenType.RESERVED_WORD.toString(), lineCode.getLineNumber(), index);
											if (sourceCode.charAt(index) == ' ') return new Token(token, TokenName.WHILE.toString(), TokenType.RESERVED_WORD.toString(), lineCode.getLineNumber(), index + 1);	
											else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
										} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
									} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
								} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
							} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
						} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
						
					// throwback, trending, tweet
					case 't':
						token += sourceCode.charAt(index++);
						if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
						if (sourceCode.charAt(index) == 'h') {
							token += sourceCode.charAt(index++);
							if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
							if (sourceCode.charAt(index) == 'r') {
								token += sourceCode.charAt(index++);
								if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
								if (sourceCode.charAt(index) == 'o') {
									token += sourceCode.charAt(index++);
									if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
									if (sourceCode.charAt(index) == 'w') {
										token += sourceCode.charAt(index++);
										if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
										if (sourceCode.charAt(index) == 'b') {
											token += sourceCode.charAt(index++);
											if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
											if (sourceCode.charAt(index) == 'a') {
												token += sourceCode.charAt(index++);
												if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
												if (sourceCode.charAt(index) == 'c') {
													token += sourceCode.charAt(index++);
													if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
													if (sourceCode.charAt(index) == 'k') {
														token += sourceCode.charAt(index++);
														if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
														if (sourceCode.charAt(index) == ' ') return new Token(token, TokenName.PROC_RET.toString(), TokenType.RESERVED_WORD.toString(), lineCode.getLineNumber(), index + 1);
														else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
													} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
												} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
											} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
										} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
									} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
								} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
							} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
						} else if (sourceCode.charAt(index) == 'r') {
							token += sourceCode.charAt(index++);
							if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
							if (sourceCode.charAt(index) == 'e') {
								token += sourceCode.charAt(index++);
								if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
								if (sourceCode.charAt(index) == 'n') {
									token += sourceCode.charAt(index++);
									if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
									if (sourceCode.charAt(index) == 'd') {
										token += sourceCode.charAt(index++);
										if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
										if (sourceCode.charAt(index) == 'i') {
											token += sourceCode.charAt(index++);
											if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
											if (sourceCode.charAt(index) == 'n') {
												token += sourceCode.charAt(index++);
												if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
												if (sourceCode.charAt(index) == 'g') {
													token += sourceCode.charAt(index++);
													if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
													if (sourceCode.charAt(index) == ' ')
														return new Token(token, TokenName.PROC_CALL.toString(), TokenType.RESERVED_WORD.toString(), lineCode.getLineNumber(), index + 1);
													else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
												} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
											} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
										} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
									} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
								} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
							} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
						} else if (sourceCode.charAt(index) == 'w') {
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
										if (sourceCode.charAt(index) == ' ') return new Token(token, TokenName.IF.toString(), TokenType.RESERVED_WORD.toString(), lineCode.getLineNumber(), index + 1);
										if (sourceCode.charAt(index) == ')') return new Token(token, TokenName.IF.toString(), TokenType.RESERVED_WORD.toString(), lineCode.getLineNumber(), index);
										else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
									} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
								} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
							} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
						} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
							
					case 'u':
						token += sourceCode.charAt(index++);
						if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
						if (sourceCode.charAt(index) == 'n') {
							token += sourceCode.charAt(index++);
							if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
							if (sourceCode.charAt(index) == 'f') {
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
													 || sourceCode.charAt(index) == ';'
													 || sourceCode.charAt(index) == ' ')
														return new Token(token, TokenName.BREAK.toString(), TokenType.RESERVED_WORD.toString(), lineCode.getLineNumber(), index);
													else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
												} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
											} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
										} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
									} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
								} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
							} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
						} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
				}
		}
		return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
	}
	
	private static Token getComments(CodeLine lineCode, int index) {
		String sourceCode = lineCode.getLineCode();
		String token = "";
		
		if (sourceCode.charAt(index) == '#') {
			token += sourceCode.charAt(index++);
			if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
			if (sourceCode.charAt(index) == 'c') {
				token += sourceCode.charAt(index++);
				if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
				if (sourceCode.charAt(index) == 'o') {
					token += sourceCode.charAt(index++);
					if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
					if (sourceCode.charAt(index) == 'm') {
						token += sourceCode.charAt(index++);
						if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
						if (sourceCode.charAt(index) == 'm') {
							token += sourceCode.charAt(index++);
							if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
							if (sourceCode.charAt(index) == 'e') {
								token += sourceCode.charAt(index++);
								if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
								if (sourceCode.charAt(index) == 'n') {
									token += sourceCode.charAt(index++);
									if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
									if (sourceCode.charAt(index) == 't') {
										token += sourceCode.charAt(index++);
										if (index >= sourceCode.length() || sourceCode.charAt(index++) == ' ') {
											return new Comment(token, sourceCode.substring(index), lineCode.getLineNumber(), sourceCode.length());
										} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
									} else return new Error(token, "INVALID COMMENT" + token, lineCode.getLineNumber(), index);
								} else return new Error(token, "INVALID COMMENT" + token, lineCode.getLineNumber(), index);
							} else return new Error(token, "INVALID COMMENT" + token, lineCode.getLineNumber(), index);
						} else return new Error(token, "INVALID COMMENT" + token, lineCode.getLineNumber(), index);
					} else return new Error(token, "INVALID COMMENT" + token, lineCode.getLineNumber(), index);
				} else return new Error(token, "INVALID COMMENT" + token, lineCode.getLineNumber(), index);
			} else return new Error(token, "INVALID COMMENT" + token, lineCode.getLineNumber(), index);
		} else if (currentIndent >= previousIndent) {
			if (tokens.getLast().getName().equals("INDENT")) {
				if (tokens.get(tokens.size() - 2).getName().equals("COMMENT")) 
					return new Comment(sourceCode.substring(index), sourceCode.substring(index), lineCode.getLineNumber(), sourceCode.length());
				else return new Error(token, "INVALID COMMENT" + token, lineCode.getLineNumber(), index);
			} 
			if (tokens.getLast().getName().equals("COMMENT"))
				return new Comment(sourceCode.substring(index), sourceCode.substring(index), lineCode.getLineNumber(), sourceCode.length());
			else return new Error(token, "INVALID COMMENT" + token, lineCode.getLineNumber(), index);
				
		} else return new Error(token, "INVALID COMMENT" + token, lineCode.getLineNumber(), index);
		//} else return null;
	}
	
	private static Token getCharConstant(CodeLine lineCode, int index) {
		String sourceCode = lineCode.getLineCode();
		String token = "";
		
		if (sourceCode.charAt(index) == '\'') {
			token += sourceCode.charAt(index++);
			if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
			if (sourceCode.charAt(index) == '\\') {
				token += sourceCode.charAt(index++);
				if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
				token += sourceCode.charAt(index++);
			} else token += sourceCode.charAt(index++);
			
			if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
			if (sourceCode.charAt(index) == '\'') {
				token += sourceCode.charAt(index++);
				token = token.replaceAll("^\'|\'$", "");
				return new Token(token, TokenName.CHAR_CONST.toString(), TokenType.CONSTANT.toString(), lineCode.getLineNumber(), index);
			}
		} return new Error(token, "INVALID TOKEN - "  + token, lineCode.getLineNumber(), index);
	}
	
	private static Token getStringConstant(CodeLine lineCode, int index) {
		String sourceCode = lineCode.getLineCode();
		String token = "";
		
		if (sourceCode.charAt(index) == '"') {
			token += sourceCode.charAt(index++);
			while (sourceCode.charAt(index) != '"' && index < sourceCode.length()) {
				if (sourceCode.charAt(index) == '\\') token += sourceCode.charAt(index++);
				token += sourceCode.charAt(index++);
				if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
			}

			if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
			if (sourceCode.charAt(index) == '"') {
				token += sourceCode.charAt(index++);
				token = token.replaceAll("^\"|\"$", "");
				return new Token(token, TokenName.STRING_CONST.toString(), TokenType.CONSTANT.toString(), lineCode.getLineNumber(), index);
			}
		} return new Error(token, "INVALID TOKEN - "  + token, lineCode.getLineNumber(), index);
	}
	
	private static Token getNumConstant(CodeLine lineCode, int index) {
		String sourceCode = lineCode.getLineCode();
		String token = "";
		
		while (index < sourceCode.length()) {	
			if (Character.isDigit(sourceCode.charAt(index))) {
				token += sourceCode.charAt(index++);
				if (index >= sourceCode.length()) return new Token(token, TokenName.INT_CONST.toString(), TokenType.CONSTANT.toString(), lineCode.getLineNumber(), index);
			} else if (sourceCode.charAt(index) == '.') {
				token += sourceCode.charAt(index++);
				while (index < sourceCode.length()) {	
					if (Character.isDigit(sourceCode.charAt(index))) {
						token += sourceCode.charAt(index++);
						if (index >= sourceCode.length()) return new Token(token, TokenName.FLOAT_CONST.toString(), TokenType.CONSTANT.toString(), lineCode.getLineNumber(), index);
					} else if (sourceCode.charAt(index) == ' ' || sourceCode.charAt(index) == ';'
							|| sourceCode.charAt(index) == '+' || sourceCode.charAt(index) == '-'
							|| sourceCode.charAt(index) == '*' || sourceCode.charAt(index) == '/'
							|| sourceCode.charAt(index) == '%' || sourceCode.charAt(index) == '^'
							|| sourceCode.charAt(index) == '(' || sourceCode.charAt(index) == ')') {
						return new Token(token, TokenName.FLOAT_CONST.toString(), TokenType.CONSTANT.toString(), lineCode.getLineNumber(), index);
					} else return new Error(token, "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
				}
			} else if (sourceCode.charAt(index) == ' ' || sourceCode.charAt(index) == ';'
					|| sourceCode.charAt(index) == '+' || sourceCode.charAt(index) == '-'
					|| sourceCode.charAt(index) == '*' || sourceCode.charAt(index) == '/'
					|| sourceCode.charAt(index) == '%' || sourceCode.charAt(index) == '^'
					|| sourceCode.charAt(index) == '(' || sourceCode.charAt(index) == ')') {
				return new Token(token, TokenName.INT_CONST.toString(), TokenType.CONSTANT.toString(), lineCode.getLineNumber(), index);
			} else return new Error(token, "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
		} return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
	}
	
	private static Token getIndents(CodeLine lineCode, int index) {
		String sourceCode = lineCode.getLineCode();
		String token = ""; Token indent = null;
		
		if (sourceCode.isEmpty()) {
			setLineNumber(getLineNumber() + 1);
			lineCode = Code.getLineList().get(getLineNumber());
			return new Token("\\n", "NEWLINE", TokenType.SPEC_SYMBOL.toString(), lineCode.getLineNumber(), 0);
		}
		if (sourceCode.charAt(index) == ' ') {
			while (index < sourceCode.length() && sourceCode.charAt(index) == ' ') token += sourceCode.charAt(index++);
			if (token.length() % 2 == 0 && !token.isEmpty()) {
				currentIndent = token.length() / 2;
				if (currentIndent * 2 > previousIndent * 2 + 2) return new Error(token, "INVALID INDENT", lineCode.getLineNumber(), index);
				if (currentIndent > previousIndent) {
					indentStack.push(currentIndent * 2);
					indent =  new Token("  ", "INDENT", TokenType.SPEC_SYMBOL.toString(), lineCode.getLineNumber(), index);
				} else if (currentIndent == previousIndent)
					indent =  new Token("", "NO_INDENT", TokenType.SPEC_SYMBOL.toString(), lineCode.getLineNumber(), index);
				else {
					if (indentStack.isEmpty()) return new Error(token, "INVALID INDENTATION", lineCode.getLineNumber(), index + 1);
					if (indentStack.peek().intValue() >= currentIndent * 2) {
						indentStack.pop();
						indent = new Token("", "DEDENT", TokenType.SPEC_SYMBOL.toString(), lineCode.getLineNumber(), index);
					} else {
						indent =  new Token("", "NO_INDENT", TokenType.SPEC_SYMBOL.toString(), lineCode.getLineNumber(), index + 1);
					}
				}
			} else return new Error(token, "INVALID INDENT", lineCode.getLineNumber(), index);
			
		} else {
			currentIndent = 0;
			if (currentIndent == previousIndent)
				indent =  new Token("", "NO_INDENT", TokenType.SPEC_SYMBOL.toString(), lineCode.getLineNumber(), index);
			else {
				if (indentStack.isEmpty()) return new Error(token, "INVALID INDENTATION", lineCode.getLineNumber(), index + 1);
				if (indentStack.peek().intValue() != currentIndent * 2) {
					indentStack.pop();
					indent = new Token("", "DEDENT", TokenType.SPEC_SYMBOL.toString(), lineCode.getLineNumber(), index);
				} else {
					indent =  new Token("", "NO_INDENT", TokenType.SPEC_SYMBOL.toString(), lineCode.getLineNumber(), index);
				}
			}
		}
		return indent;
		//return new Error(token, "INVALID INDENT", lineCode.getLineNumber(), index);
	}
	
	private static Token getBoolConstant(CodeLine lineCode, int index) {
		String sourceCode = lineCode.getLineCode();
		String token = "";
		
		switch(sourceCode.charAt(index))
		{
		case 'a':
				token += sourceCode.charAt(index++);
				if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
  				if(sourceCode.charAt(index) == 'c'){
  					token += sourceCode.charAt(index++);
  					if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
  					if(sourceCode.charAt(index) == 'c'){
  						token += sourceCode.charAt(index++);
  						if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
  						if(sourceCode.charAt(index) == 'e'){
  							token += sourceCode.charAt(index++);
  							if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
  							if(sourceCode.charAt(index) == 'p'){
  								token += sourceCode.charAt(index++);
  								if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
  								if(sourceCode.charAt(index) == 't'){
  									token += sourceCode.charAt(index++);
  									if(index < sourceCode.length()){
	  									if(Character.isLetter(sourceCode.charAt(index))){
	  										while(index < sourceCode.length()){
	  											if(Character.isLetter(sourceCode.charAt(index)) 
	  													|| sourceCode.charAt(index) == '_' 
	  													|| Character.isDigit(sourceCode.charAt(index))){
			  												token += sourceCode.charAt(index++);
			  												if(index==sourceCode.length() || index >= sourceCode.length()) {
			  												 return new Token(token, TokenName.VAR.toString(), TokenType.IDENTIFIER.toString(), lineCode.getLineNumber(), index--);
	  												}
	  											} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
	  										}
	  									} else if(Character.isWhitespace(sourceCode.charAt(index)) || sourceCode.charAt(index++) == ';') return new Token(token, TokenName.BOOL_CONST_TRUE.toString(), TokenType.CONSTANT.toString(), lineCode.getLineNumber(), index);
  									} else return new Token(token, TokenName.BOOL_CONST_TRUE.toString(), TokenType.CONSTANT.toString(), lineCode.getLineNumber(), index);
  								} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
  							} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
  						} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
  					} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
  				} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
			
			
			//decline
		case 'd':
				token += sourceCode.charAt(index++);
				if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
  				if (sourceCode.charAt(index) == 'e'){
  	  				token += sourceCode.charAt(index++);
  	  				if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
	  	  			if (sourceCode.charAt(index) == 'c'){
	  	  				token += sourceCode.charAt(index++);
	  	  				if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
	  	  				if (sourceCode.charAt(index) == 'l'){
		  	  				token += sourceCode.charAt(index++);
		  	  				if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
		  	  				if (sourceCode.charAt(index) == 'i'){
			  	  				token += sourceCode.charAt(index++);
			  	  				if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
				  	  			if (sourceCode.charAt(index) == 'n'){
				  	  				token += sourceCode.charAt(index++);
				  	  				if (index >= sourceCode.length()) return new Error(token, "INVALID TOKEN - " + token, lineCode.getLineNumber(), index);
					  	  			if(sourceCode.charAt(index) == 'e'){
						  	  			token += sourceCode.charAt(index++);
	  									if(index < sourceCode.length()){
		  									if(Character.isLetter(sourceCode.charAt(index))){
		  										while(index < sourceCode.length()){
		  											if (Character.isLetter(sourceCode.charAt(index)) 
		  													|| sourceCode.charAt(index) == '_' 
		  													|| Character.isDigit(sourceCode.charAt(index))){
				  												token += sourceCode.charAt(index++);
				  												if(index==sourceCode.length() || index >= sourceCode.length()) {
				  												 return new Token(token, TokenName.VAR.toString(), TokenType.IDENTIFIER.toString(), lineCode.getLineNumber(), index--);
		  												}
		  											} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
		  										}
		  									} else if(Character.isWhitespace(sourceCode.charAt(index)) || sourceCode.charAt(index++) == ';') return new Token(token, TokenName.BOOL_CONST_FALSE.toString(), TokenType.CONSTANT.toString(), lineCode.getLineNumber(), index);
	  									} else return new Token(token, TokenName.BOOL_CONST_FALSE.toString(), TokenType.CONSTANT.toString(), lineCode.getLineNumber(), index);
	  								} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
				  	  			} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
			  	  			} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
		  	  			} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
	  	  			} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
  	  			} else return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
		} 
		return new Error(token + sourceCode.charAt(index), "INVALID TOKEN - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
	}
	
	public static Token getIdentifier(CodeLine lineCode, int index) {
		String sourceCode = lineCode.getLineCode();
  		String token = "";
  		
  		if (Character.isLetter(sourceCode.charAt(index)) || sourceCode.charAt(index) == '_'){	
  				token += sourceCode.charAt(index++);
				while (index < sourceCode.length()) {
					if (Character.isLetter(sourceCode.charAt(index)) 
						|| sourceCode.charAt(index) == '_' 
						|| Character.isDigit(sourceCode.charAt(index))) {
							token += sourceCode.charAt(index++);
							if (index == sourceCode.length() || index >= sourceCode.length()) {
								if (getTokens().getLast().getName().equals("START")) return new Token(token, TokenName.PROG_NAME.toString(), TokenType.IDENTIFIER.toString(), lineCode.getLineNumber(), index--);
								return new Token(token, TokenName.VAR.toString(), TokenType.IDENTIFIER.toString(), lineCode.getLineNumber(), index);
						}
					} else if (sourceCode.charAt(index) == ' ' || sourceCode.charAt(index) == ';'
							|| sourceCode.charAt(index) == '+' || sourceCode.charAt(index) == '-'
							|| sourceCode.charAt(index) == '*' || sourceCode.charAt(index) == '/'
							|| sourceCode.charAt(index) == '%' || sourceCode.charAt(index) == '^'
							|| sourceCode.charAt(index) == ')')
						return new Token(token, TokenName.VAR.toString(), TokenType.IDENTIFIER.toString(), lineCode.getLineNumber(), index);
					else if (sourceCode.charAt(index) == '(') {
						if (currentIndent == 1 || tokens.getLast().getName().equals("PROC_CALL"))
							return new Token(token, TokenName.PROC_NAME.toString(), TokenType.IDENTIFIER.toString(), lineCode.getLineNumber(), index);
						else return new Token(token, TokenName.VAR.toString(), TokenType.IDENTIFIER.toString(), lineCode.getLineNumber(), index);
					} else return new Error(token + sourceCode.charAt(index), "INVALID IDENTIFIER - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
				}
  			} else return new Error(token + sourceCode.charAt(index), "INVALID IDENTIFIER - " + token + sourceCode.charAt(index), lineCode.getLineNumber(), index);
  		return new Error(token, "INVALID IDENTIFIER - " + token, lineCode.getLineNumber(), index);
	}
}