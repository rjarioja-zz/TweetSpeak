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
						
						if (index + 1 < sourceCode.length() && sourceCode.charAt(index + 1) == '+') {
							token += sourceCode.charAt(index); count++;
							System.out.println(index + " " + count);
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
						}
						
					case '-':
						break;
					case '*':
						break;
					case '/':
						break;
					case '%':
						break;
					
					/*
					 * SPECIAL SYMBOLS
					 */
					case ';':
						break;
					case '\'':
						break;
					case '\"':
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
		Tokenizer.tokenize(new CodeLine("#login +++", 1));
	}
		
}	