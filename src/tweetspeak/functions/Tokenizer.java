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
<<<<<<< HEAD
							token += sourceCode.charAt(index); count++;
=======
							token += sourceCode.charAt(index++); count++;
//							System.out.println(index + " " + count);
>>>>>>> branch 'master' of https://github.com/rjarioja/TweetSpeak.git
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
							            	//login
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
							    
							//#comment start ---ZEKE
							case 'c': 
								
									token += sourceCode.charAt(index++); count++;
									//if-level-1
									if(sourceCode.charAt(index) == 'o' && sourceCode.charAt(index + 1) == 'm' && sourceCode.charAt(index+2) == 'm' 
											&& sourceCode.charAt(index+3) == 'e' && sourceCode.charAt(index+4) == 'n' && sourceCode.charAt(index+5) == 't') {
									   token += "omment"; count += 6; index += 6;
									   System.out.print(token + ".COMMENT");
									   continue;
									}
									else {
								       index --; 
								       System.out.print(token + ".ERROR");
								       continue;
								    }
									

								
							//#comment end; ---END
							//#newsfeed start 
							case 'n':
								
									token += sourceCode.charAt(index++); count++;
									if(sourceCode.charAt(index) == 'e' && sourceCode.charAt(index + 1) == 'w' && sourceCode.charAt(index + 2) == 's'
											&& sourceCode.charAt(index + 3) == 'f' && sourceCode.charAt(index + 4) == 'e' && sourceCode.charAt(index + 5) == 'e' 
											&& sourceCode.charAt(index + 6) == 'd') {
										token += "ewsfeed"; count += 7; index += 7;
										
										if (index == sourceCode.length()) {
											System.out.print(token + ".MAIN");
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
						
							//#newsfeed END
							case 's':
									
									token += sourceCode.charAt(index++); count++;
									switch(sourceCode.charAt(index)) {
									//#SHARE
										case 'h':
											token += sourceCode.charAt(index++); count++;
											if(sourceCode.charAt(index) == 'a' && sourceCode.charAt(index + 1) == 'r' && sourceCode.charAt(index + 2) == 'e'){
												token += "are"; index += 3; count += 3;
	
													System.out.print(token + ".ASSIGN");
													continue;
												}	
											else {
												index --;
												System.out.print(token + ".ERROR");
												continue;
											}
										
										//#STATUS
										case 't':
											token += sourceCode.charAt(index++); count++;
											if(sourceCode.charAt(index) == 'a' && sourceCode.charAt(index + 1) == 't' && sourceCode.charAt(index + 2) == 'u' &&
													sourceCode.charAt(index + 3) == 's'){
												token += "atus"; index += 4; count += 4;
													System.out.print(token + ".WHILE");
													continue;
											}
											else {
												index --;
												System.out.print(token + ".ERROR");
												continue;
											}
									}
							
							case 'o': 
									token += sourceCode.charAt(index++); count++;
									if(sourceCode.charAt(index) == 'o' && sourceCode.charAt(index + 1) == 't') {
										token += "ot"; index +=2; count +=2;
										/* DATA 
										 * TYPES
										 */
										switch(sourceCode.charAt(index)) {
											case 'i': 
												token += sourceCode.charAt(index++); count++;
												if (index < sourceCode.length() && sourceCode.charAt(index) == ' ') {
													token += sourceCode.charAt(index++);
													System.out.print(token + ".DATATYPE_INT");
													continue;
												}
												else {
													index --;
													System.out.print(token + ".ERROR");
													continue;
												}
											case 'f':
												token += sourceCode.charAt(index++); count++;
												if (index < sourceCode.length() && sourceCode.charAt(index) == ' ') {
													token += sourceCode.charAt(index++);
													System.out.print(token + ".DATATYPE_FLOAT");
													continue;
												}
												else {
													index --;
													System.out.print(token + ".ERROR");
													continue;
												}
											case 'c':
												token += sourceCode.charAt(index++); count++;
												if (index < sourceCode.length() && sourceCode.charAt(index) == ' ') {
													token += sourceCode.charAt(index++);
													System.out.print(token + ".DATATYPE_CHAR");
													continue;
												}
												else {
													index --;
													System.out.print(token + ".ERROR");
													continue;
												}
											case 's':
												token += sourceCode.charAt(index++); count++;
												if (index < sourceCode.length() && sourceCode.charAt(index) == ' ') {
													token += sourceCode.charAt(index++);
													System.out.print(token + ".DATATYPE_STRING");
													continue;
												}
												else {
													index --;
													System.out.print(token + ".ERROR");
													continue;
												}
											case 'b':
												token += sourceCode.charAt(index++); count++;
												if (index < sourceCode.length() && sourceCode.charAt(index) == ' ') {
													token += sourceCode.charAt(index++);
													System.out.print(token + ".DATATYPE_BOOL");
													continue;
												}
												else {
													index --;
													System.out.print(token + ".ERROR");
													continue;
												}
											case 'v':
												token += sourceCode.charAt(index++); count++;
												if (index < sourceCode.length() && sourceCode.charAt(index) == ' ') {
													token += sourceCode.charAt(index++);
													System.out.print(token + ".DATATYPE_VOID");
													continue;
												}
												else {
													index --;
													System.out.print(token + ".ERROR");
													continue;
												}
										}
									}
									
									//#OUTBOX
									else if(sourceCode.charAt(index) == 'u' && sourceCode.charAt(index + 1) == 't' && sourceCode.charAt(index + 2) == 'b'
											&& sourceCode.charAt(index + 3) == 'o' && sourceCode.charAt(index + 4) == 'x') {
										
										token += "utbox"; index += 5; count += 5;
										System.out.print(token + ".OUTPUT");
									}
									else {
										index --;
										System.out.print(token + ".ERROR");
										continue;
									}
									break;
							
							default:
							    //reserved words
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
		Tokenizer.tokenize(new CodeLine("#ootv #oots #ootc #ootb #comment #share #status #ooti #ootf #outbox ", 1));
	}
		
}	
