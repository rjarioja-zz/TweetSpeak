package tweetspeak.functions;

import java.util.*;

import tweetspeak.collections.TokenName;
import tweetspeak.collections.TokenType;
import tweetspeak.divisions.CodeLine;
import tweetspeak.objects.Token;

public class Tokenizer {
	private static String sourceCode;
	private static LinkedList<Token> tokens = new LinkedList<Token>();
	
	//getters
	public static String getSourceCode() { return sourceCode; }
	
	//setters
	public static void setSourceCode(String token) { Tokenizer.sourceCode = token; }
	
	//methods
	public static void tokenize(CodeLine lineCode) {
		sourceCode = lineCode.getLineCode();
		int index = 0;
		
		System.out.println(sourceCode.toCharArray());
		
		while (index < sourceCode.length() && sourceCode.indexOf(index + 1) != '\0') {
			String token = "", tokenType = "";
			int count = 0, whitespace = 0;
			Token t;
			
			System.out.print("\nindex:" + index + " - ");
			
			if (!Character.isWhitespace(sourceCode.charAt(index))) {
				switch(sourceCode.charAt(index)) {
					
					/*
					 * OPERATORS - ARITHMETIC
					 */
					case '+':
						token += sourceCode.charAt(index++); count++;
						if (index < sourceCode.length() && sourceCode.charAt(index) == '+') {
							token += sourceCode.charAt(index++); count++;
//							System.out.println(index + " " + count);
							System.out.print(token + ".INC_OP");
							//create token
							t = new Token(
									token, 
									TokenName.INC_OP.toString(), 
									TokenType.OPERATOR.toString(), 
									lineCode.getLineNumber(), 
									index - count
								);
							
							// add to lists
							tokens.add(t);
							lineCode.addToken(t);
							continue;
						}
						
						else {
							System.out.print(token + ".ADD_OP");
							// create token
							t = new Token(
									token, 
									TokenName.ADD_OP.toString(), 
									TokenType.OPERATOR.toString(), 
									lineCode.getLineNumber(), 
									index - count
								);
							
							// add to lists
							tokens.add(t);
							lineCode.addToken(t);
							continue;
						}
						
//						break;
						
					case '-':
						token += sourceCode.charAt(index++); count++;
						if (index < sourceCode.length() && sourceCode.charAt(index) == '-') {
							token += sourceCode.charAt(index++); count++;
							//System.out.println(index + " " + count);
							System.out.print(token + ".DEC_OP");
							//create token
							t = new Token(
									token, 
									TokenName.DEC_OP.toString(), 
									TokenType.OPERATOR.toString(), 
									lineCode.getLineNumber(), 
									index - count
								);
							
							// add to lists
							tokens.add(t);
							lineCode.addToken(t);
							continue;
						}
						
						else {
							System.out.print(token + ".DIF_OP");
							// create token
							t = new Token(
									token, 
									TokenName.DEC_OP.toString(), 
									TokenType.OPERATOR.toString(), 
									lineCode.getLineNumber(), 
									index - count
								);
							
							// add to lists
							tokens.add(t);
							lineCode.addToken(t);
							continue;
						}
					
					case '*':
						token += sourceCode.charAt(index); count++;
						System.out.print(token + ".MUL_OP");
						//create token
						t = new Token (
										token,
										TokenName.MUL_OP.toString(),
										TokenType.OPERATOR.toString(),
										lineCode.getLineNumber(),
										index - count
								);
						// add to lists
						tokens.add(t);
						lineCode.addToken(t);
						
						break;
						
					case '/':
						token += sourceCode.charAt(index); count++;
						System.out.print(token + ".DIV_OP");
						//create token
						t = new Token (
										token,
										TokenName.DIV_OP.toString(),
										TokenType.OPERATOR.toString(),
										lineCode.getLineNumber(),
										index - count
								);
						break;
					case '%':
						token += sourceCode.charAt(index); count++;
						System.out.print(token + ".MOD_OP");
						//create token
						t = new Token (
									token,
									TokenName.MOD_OP.toString(),
									TokenType.OPERATOR.toString(),
									lineCode.getLineNumber(),
									index - count
								);
						break;
						
					/*
					 * RELATIONAL AND LOGICAL OPERATORS	
					 */
					
					case '|':
						token += sourceCode.charAt(index++); count++;
						if (index < sourceCode.length() && sourceCode.charAt(index) == '|') {
							token += sourceCode.charAt(index++); count++;
							System.out.print(token + ".OR_OP");
							continue;
						} else {
							//index --;
							System.out.print(token + ".ERROR");
							continue;
						}
//					break;
					case '&':
						token += sourceCode.charAt(index++); count++;
						if (index < sourceCode.length() && sourceCode.charAt(index) == '&') {
							token += sourceCode.charAt(index++); count++;
							System.out.print(token + ".AND_OP");
							continue;
						} else {
							//index --;
							System.out.print(token + ".ERROR");
							continue;
						}
						
					/*
					 * OTHER TOKENS
					 */
					case ',':
						token += sourceCode.charAt(index); count++;
						System.out.print(token + ".PARAM_SEP");
						//create token
						t = new Token (
									token,
									TokenName.PARAM_SEP.toString(),
									TokenType.OPERATOR.toString(),
									lineCode.getLineNumber(),
									index - count
								);
						break;
					case ';':
						token += sourceCode.charAt(index); count++;
						System.out.print(token + ".STMT_SEP");
						//create token
						t = new Token (
									token,
									TokenName.STMT_SEP.toString(),
									TokenType.OPERATOR.toString(),
									lineCode.getLineNumber(),
									index - count
								);
						break;
					case '"':
						token += sourceCode.charAt(index); count++;
						System.out.print(token + ".DQUOTE");
						//create token
						t = new Token (
									token,
									TokenName.DQUOTE.toString(),
									TokenType.OPERATOR.toString(),
									lineCode.getLineNumber(),
									index - count
								);
						break;
					case '\'':
						token += sourceCode.charAt(index); count++;
						System.out.print(token + ".SQUOTE");
						//create token
						t = new Token (
									token,
									TokenName.SQUOTE.toString(),
									TokenType.OPERATOR.toString(),
									lineCode.getLineNumber(),
									index - count
								);
						break;
					
					
					/*
					 * RESERVED WORDS
					 */
					case '#':
						token += sourceCode.charAt(index++); count++;
						tokenType = TokenType.RESERVED_WORD.toString();
						
						switch (sourceCode.charAt(index)) {
						
						// login, logout
							case 'l':
							    // check if no other characters at the start
							    if (index - 1 != 0) continue; 
							    else {
							    	
							        token += sourceCode.charAt(index++); count++;
							        if (sourceCode.charAt(index) == 'o' && sourceCode.charAt(index + 1) == 'g') {
							        	token += "og"; count += 2; index += 2;
							            switch (sourceCode.charAt(index)) {
							                case 'i': 
							                    token += sourceCode.charAt(index++); count++;
							                    if (sourceCode.charAt(index) == 'n') {  
							                    	token += sourceCode.charAt(index++); count++;
							                    	if (index < sourceCode.length() && sourceCode.charAt(index) == ' ') {
							                    		token += sourceCode.charAt(index++);
							                    		System.out.print(token + ".START");
							                    		
							                    		continue;
							                    	}
							                    	
							                    	else {
											            index --; 
											            System.out.print(token + ".ERROR");
											            continue;
											        }
							                    }
							                    
							                    else {
										            index --; 
										            System.out.print(token + ".ERROR");
										            continue;
										        }
							                    
							                // logout
							                case 'o':
							                	token += sourceCode.charAt(index++); count++;
										        if (sourceCode.charAt(index) == 'u' && sourceCode.charAt(index + 1) == 't') {
										        	token += "ut"; count += 2; index += 2;
								                    if (index == sourceCode.length()) {  
								                    	System.out.print(token + ".END");
							                    		continue;
								                    }
								                    else {
											            index --; 
											            System.out.print(token + ".ERROR");
											            continue;
											        } 

							                    }
							                    
							                    else {
										            index --;
										            System.out.print(token + ".ERROR");
										            continue;
										        }
							                    
							                default:
							                	System.out.print(" level 3 exit");
							                    index --;
							                    break;
							            }
							        }
							        
							        else {
							        	token += sourceCode.charAt(index);
							        	System.out.print(token + ".ERROR");
							            continue;
							        }
							    }
							    
							default:
							    System.out.print(" level 2 exit");
							    break;
						}
						
						break;
						
					default:
						System.out.print(" level 1 exit");
						break;
				}
			}
			
			else {
				System.out.print("space");
				whitespace++;
			}
			index++;
			count++;
		}
	}
	
	// TESTING

	public static void main(String args[]) {
		Tokenizer.tokenize(new CodeLine("&&|", 1));
	}
		
}	
