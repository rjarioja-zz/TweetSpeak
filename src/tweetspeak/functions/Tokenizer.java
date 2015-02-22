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
							    if (index - 1 != 0) {
							    	
							    	token += sourceCode.charAt(index++); count++;
							        if(sourceCode.charAt(index) == 'i' && sourceCode.charAt(index + 1) == 'k' && sourceCode.charAt(index + 2) == 'e') {
							        	token += "ike"; index += 3; count += 3;
							    		t = new Token(
												token, 
												TokenName.DO.toString(), 
												TokenType.RESERVED_WORD.toString(), 
												lineCode.getLineNumber(), 
												index - count
											);
										
										// add to lists
										tokens.add(t);
										lineCode.addToken(t);
							        	System.out.print(token + ".DO");
							        	continue;
							        }
							        else {
							        	index --;
							        	System.out.print(token + ".ERROR");
							        	continue;
							        }
							    }
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
							                    		t = new Token(
																token, 
																TokenName.START.toString(), 
																TokenType.RESERVED_WORD.toString(), 
																lineCode.getLineNumber(), 
																index - count
															);
														
														// add to lists
														tokens.add(t);
														lineCode.addToken(t);
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
								                    	t = new Token(
																token, 
																TokenName.END.toString(), 
																TokenType.RESERVED_WORD.toString(), 
																lineCode.getLineNumber(), 
																index - count
															);
														
														// add to lists
														tokens.add(t);
														lineCode.addToken(t);
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
									   t = new Token(
												token, 
												TokenName.COMMENT.toString(), 
												TokenType.RESERVED_WORD.toString(), 
												lineCode.getLineNumber(), 
												index - count
											);
										
										// add to lists
										tokens.add(t);
										lineCode.addToken(t);
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
										if (index < sourceCode.length() && sourceCode.charAt(index) == ' ') {
											t = new Token(
													token, 
													TokenName.MAIN.toString(), 
													TokenType.RESERVED_WORD.toString(), 
													lineCode.getLineNumber(), 
													index - count
												);
											
											// add to lists
											tokens.add(t);
											lineCode.addToken(t);
											System.out.print(token + ".MAIN");
											continue;
		
										} 
										else {
											index --;
											System.out.print(token + ".ERROR");
										}
									}
									else {
										 index --;
										 System.out.print(token + ".ERROR");
										 continue;
									}
								break;
						    
							//#newsfeed END
							case 's':
									
									token += sourceCode.charAt(index++); count++;
									//System.out.println(index);
									switch(sourceCode.charAt(index)) {
									//#SHARE
										case 'h':
											//System.out.println(index);
											token += sourceCode.charAt(index++); count++;
											if(sourceCode.charAt(index) == 'a' && sourceCode.charAt(index + 1) == 'r' && sourceCode.charAt(index + 2) == 'e'){
												token += "are"; index += 3; count += 3;
												if (index < sourceCode.length() && sourceCode.charAt(index) == ' ') {
													t = new Token(
															token, 
															TokenName.ASSIGN.toString(), 
															TokenType.RESERVED_WORD.toString(), 
															lineCode.getLineNumber(), 
															index - count
														);
													
													// add to lists
													tokens.add(t);
													lineCode.addToken(t);
													System.out.print(token + ".ASSIGN");
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
										
										//#STATUS
										case 't':
											token += sourceCode.charAt(index++); count++;
											if(sourceCode.charAt(index) == 'a' && sourceCode.charAt(index + 1) == 't' && sourceCode.charAt(index + 2) == 'u' &&
													sourceCode.charAt(index + 3) == 's'){
													token += "atus"; index += 4; count += 4;
													if (index < sourceCode.length() && sourceCode.charAt(index) == ' ') {
														t = new Token(
																token, 
																TokenName.WHILE.toString(), 
																TokenType.RESERVED_WORD.toString(), 
																lineCode.getLineNumber(), 
																index - count
															);
														
														// add to lists
														tokens.add(t);
														lineCode.addToken(t);
														System.out.print(token + ".WHILE");
														continue;
													}
													else {
														index --;
														System.out.print(token + ".ERROR");
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
													t = new Token(
															token, 
															TokenName.DATATYPE_INT.toString(), 
															TokenType.RESERVED_WORD.toString(), 
															lineCode.getLineNumber(), 
															index - count
														);
													
													// add to lists
													tokens.add(t);
													lineCode.addToken(t);
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
													t = new Token(
															token, 
															TokenName.DATATYPE_FLOAT.toString(), 
															TokenType.RESERVED_WORD.toString(), 
															lineCode.getLineNumber(), 
															index - count
														);
													
													// add to lists
													tokens.add(t);
													lineCode.addToken(t);
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
													t = new Token(
															token, 
															TokenName.DATATYPE_CHAR.toString(), 
															TokenType.RESERVED_WORD.toString(), 
															lineCode.getLineNumber(), 
															index - count
														);
													
													// add to lists
													tokens.add(t);
													lineCode.addToken(t);
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
													t = new Token(
															token, 
															TokenName.DATATYPE_STRING.toString(), 
															TokenType.RESERVED_WORD.toString(), 
															lineCode.getLineNumber(), 
															index - count
														);
													
													// add to lists
													tokens.add(t);
													lineCode.addToken(t);
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
													t = new Token(
															token, 
															TokenName.DATATYPE_BOOL.toString(), 
															TokenType.RESERVED_WORD.toString(), 
															lineCode.getLineNumber(), 
															index - count
														);
													
													// add to lists
													tokens.add(t);
													lineCode.addToken(t);
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
													t = new Token(
															token, 
															TokenName.DATATYPE_VOID.toString(), 
															TokenType.RESERVED_WORD.toString(), 
															lineCode.getLineNumber(), 
															index - count
														);
													
													// add to lists
													tokens.add(t);
													lineCode.addToken(t);
													System.out.print(token + ".DATATYPE_VOID");
													continue;
											
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
									
									//#OUTBOX
									else if(sourceCode.charAt(index) == 'u' && sourceCode.charAt(index + 1) == 't' && sourceCode.charAt(index + 2) == 'b'
											&& sourceCode.charAt(index + 3) == 'o' && sourceCode.charAt(index + 4) == 'x') {
										
										token += "utbox"; index += 5; count += 5;
										if (index < sourceCode.length() && sourceCode.charAt(index) == ' ') {
										t = new Token(
												token, 
												TokenName.OUTPUT.toString(), 
												TokenType.RESERVED_WORD.toString(), 
												lineCode.getLineNumber(), 
												index - count
											);
										
										// add to lists
										tokens.add(t);
										lineCode.addToken(t);
										System.out.print(token + ".OUTPUT");
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
									break;
							//#T
							case 't':
									token += sourceCode.charAt(index++); count++;
									switch(sourceCode.charAt(index)) {
									//#TRENDING
									case 'r':
										token += sourceCode.charAt(index++); count++;
										if(sourceCode.charAt(index) == 'e' && sourceCode.charAt(index + 1) == 'n' 
												&& sourceCode.charAt(index + 2) == 'd' && sourceCode.charAt(index + 3) == 'i' 
												&& sourceCode.charAt(index + 4) == 'n' && sourceCode.charAt(index + 5) == 'g') {
											token += "ending"; index += 6; count +=6;
											if (index < sourceCode.length() && sourceCode.charAt(index) == ' ') {
											t = new Token(
													token, 
													TokenName.PROC_CALL.toString(), 
													TokenType.RESERVED_WORD.toString(), 
													lineCode.getLineNumber(), 
													index - count
												);
											
											// add to lists
											tokens.add(t);
											lineCode.addToken(t);
											System.out.print(token + ".PROC_CALL");
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
									//#THROWBACK
									case 'h':
										token += sourceCode.charAt(index++); count++;
										if(sourceCode.charAt(index) == 'r' && sourceCode.charAt(index + 1) == 'o' 
												&& sourceCode.charAt(index + 2) == 'w' && sourceCode.charAt(index + 3) == 'b'
												&& sourceCode.charAt(index + 4) == 'a' && sourceCode.charAt(index + 5) == 'c'
												&& sourceCode.charAt(index + 6) == 'k') {
											token += "rowback"; index += 7; count += 7;
											if (index < sourceCode.length() && sourceCode.charAt(index) == ' ') {
											t = new Token(
													token, 
													TokenName.PROC_RET.toString(), 
													TokenType.RESERVED_WORD.toString(), 
													lineCode.getLineNumber(), 
													index - count
												);
											
											// add to lists
											tokens.add(t);
											lineCode.addToken(t);
											System.out.print(token + ".PROC_RET");
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
									//#TWEET
									case 'w':
										token += sourceCode.charAt(index++); count++;
										if(sourceCode.charAt(index) == 'e' && sourceCode.charAt(index + 1) =='e' 
												&& sourceCode.charAt(index + 2) == 't') {
											
											token += "eet"; index += 3; count += 3;
											if (index < sourceCode.length() && sourceCode.charAt(index) == ' ') {
											t = new Token(
													token, 
													TokenName.IF.toString(), 
													TokenType.RESERVED_WORD.toString(), 
													lineCode.getLineNumber(), 
													index - count
												);
											
											// add to lists
											tokens.add(t);
											lineCode.addToken(t);
											System.out.print(token + ".IF");
											continue;
											}
											else {
												index --;
												System.out.print(token + ".ERROR");
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
							//#R
							case 'r':
								token += sourceCode.charAt(index++); count++;
								if(sourceCode.charAt(index) == 'e') {
									token += sourceCode.charAt(index++); count++;
									switch(sourceCode.charAt(index)) {
									//#RETWEET
									case 't':
										token += sourceCode.charAt(index++); count++;
										if(sourceCode.charAt(index) == 'w' && sourceCode.charAt(index + 1) == 'e' 
												&& sourceCode.charAt(index + 2) == 'e' && sourceCode.charAt(index + 3) == 't') {
											token += "weet"; index += 4; count += 4;
											if (index < sourceCode.length() && sourceCode.charAt(index) == ' ') {
											t = new Token(
													token, 
													TokenName.ELSE_IF.toString(), 
													TokenType.RESERVED_WORD.toString(), 
													lineCode.getLineNumber(), 
													index - count
												);
											
											// add to lists
											tokens.add(t);
											lineCode.addToken(t);
											System.out.print(token + ".ELSE_IF");
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
									//#REPLY
									case 'p':
										token += sourceCode.charAt(index++); count++;
										if(sourceCode.charAt(index) == 'l' && sourceCode.charAt(index + 1) == 'y') {
											token += "ly"; index += 2; count += 2;
											if (index < sourceCode.length() && sourceCode.charAt(index) == ' ') {
											t = new Token(
													token, 
													TokenName.ELSE.toString(), 
													TokenType.RESERVED_WORD.toString(), 
													lineCode.getLineNumber(), 
													index - count
												);
											
											// add to lists
											tokens.add(t);
											lineCode.addToken(t);
											System.out.print(token + ".ELSE");
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
										System.out.print("level 3 exit");
										index--;
										break;
									}
								}
								else {
									index --;
									System.out.print(token + ".ERROR");
								}
								
							//#UNFOLLOW
							case 'u':
								token += sourceCode.charAt(index++); count++;
								if(sourceCode.charAt(index) == 'n' && sourceCode.charAt(index + 1) == 'f' && sourceCode.charAt(index + 2) == 'o'
										&& sourceCode.charAt(index + 3) == 'l' && sourceCode.charAt(index + 4) == 'l' && sourceCode.charAt(index + 5) == 'o'
										&& sourceCode.charAt(index + 6) == 'w'){
									
									token += "nfollow"; index += 7; count +=7;
									if (index < sourceCode.length() && sourceCode.charAt(index) == ' ') {
									t = new Token(
											token, 
											TokenName.BREAK.toString(), 
											TokenType.RESERVED_WORD.toString(), 
											lineCode.getLineNumber(), 
											index - count
										);
									
									// add to lists
									tokens.add(t);
									lineCode.addToken(t);
									System.out.print(token + ".BREAK");
									continue;
									}
									else {
										index --;
										System.out.print(token + ".ERROR");
									}
								}
								else {
									index --;
									System.out.print(token + ".ERROR");
									continue;
								}
								
							//#FOLLOW
							case 'f':
								token += sourceCode.charAt(index++); count++;
								if(sourceCode.charAt(index) == 'o' && sourceCode.charAt(index + 1) == 'l' && sourceCode.charAt(index + 2) == 'l'
										&& sourceCode.charAt(index + 3) == 'o' && sourceCode.charAt(index + 4) == 'w'){
									
									token += "ollow"; index += 5; count +=5;
									if (index < sourceCode.length() && sourceCode.charAt(index) == ' ') {
									t = new Token(
											token, 
											TokenName.CONTINUE.toString(), 
											TokenType.RESERVED_WORD.toString(), 
											lineCode.getLineNumber(), 
											index - count
										);
									
									// add to lists
									tokens.add(t);
									lineCode.addToken(t);
									System.out.print(token + ".CONTINUE");
									continue;
									}
									else {
										index --;
										System.out.print(token + ".ERROR");
									}
								}
								else {
									index --;
									System.out.print(token + ".ERROR");
									continue;
								}
							case 'i':
								token += sourceCode.charAt(index++); count++;
								if(sourceCode.charAt(index) == 'n' && sourceCode.charAt(index + 1) == 'b'
										&& sourceCode.charAt(index + 2) == 'o' && sourceCode.charAt(index + 3) == 'x'){
									
									token += "nbox"; index += 4; index += 4;
									if (index < sourceCode.length() && sourceCode.charAt(index) == ' ') {
									t = new Token(
											token, 
											TokenName.INPUT.toString(), 
											TokenType.RESERVED_WORD.toString(), 
											lineCode.getLineNumber(), 
											index - count
										);
									
									// add to lists
									tokens.add(t);
									lineCode.addToken(t);
									System.out.print(token + ".INPUT");
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
							    //reserved words
								System.out.print(" level 2 exit");
							    break;
						}
						
						break;
					//ACCEPT	
					case 'a':
						 token += sourceCode.charAt(index++); count++;
						 switch(sourceCode.charAt(index)) {
						 case 'c':
							 token += sourceCode.charAt(index++); count++;
							 if(sourceCode.charAt(index) == 'c' && sourceCode.charAt(index + 1) == 'e' 
									 && sourceCode.charAt(index + 2) == 'p' && sourceCode.charAt(index + 3) == 't'){
								 token += "cept"; index += 4; count += 4;
								 if (index < sourceCode.length() && sourceCode.charAt(index) == ' ') {
								 t = new Token(
											token, 
											TokenName.BOOL_CONST_TRUE.toString(), 
											TokenType.RESERVED_WORD.toString(), 
											lineCode.getLineNumber(), 
											index - count
										);
									
									// add to lists
									tokens.add(t);
									lineCode.addToken(t);
								 System.out.print(token + ".BOOL_CONST_TRUE");
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
						 case 'r':
							 token += sourceCode.charAt(index++); count++;
							 if(sourceCode.charAt(index) == 'e' && sourceCode.charAt(index + 1) == 'F' && sourceCode.charAt(index + 2) == 'r'
									 && sourceCode.charAt(index + 3) == 'i' && sourceCode.charAt(index + 4) == 'e' 
									 && sourceCode.charAt(index + 5) == 'n' && sourceCode.charAt(index + 6) == 'd'
									 && sourceCode.charAt(index + 7) == 's' && sourceCode.charAt(index + 8) == 'W'
									 && sourceCode.charAt(index + 9) == 'i' && sourceCode.charAt(index + 10) == 't'
									 && sourceCode.charAt(index + 11) == 'h'){
								 
								 token +="eFriendsWith"; index += 12; count += 12;
								 if (index < sourceCode.length() && sourceCode.charAt(index) == ' ') {
								 t = new Token(
											token, 
											TokenName.CONCAT.toString(), 
											TokenType.RESERVED_WORD.toString(), 
											lineCode.getLineNumber(), 
											index - count
										);
									
									// add to lists
									tokens.add(t);
									lineCode.addToken(t);
								 System.out.print(token + ".CONCAT");
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
				
						 }
					 case 'd':
						 token += sourceCode.charAt(index++); count++;
						 if(sourceCode.charAt(index) =='e' && sourceCode.charAt(index + 1) == 'c' && sourceCode.charAt(index + 2) == 'l'
								 && sourceCode.charAt(index + 3) == 'i' && sourceCode.charAt(index + 4) == 'n' 
								 && sourceCode.charAt(index + 5) == 'e') {
							 
							 token +="ecline"; index += 6; count +=6;
							 if (index < sourceCode.length() && sourceCode.charAt(index) == ' ') {
							 t = new Token(
										token, 
										TokenName.BOOL_CONST_FALSE.toString(), 
										TokenType.RESERVED_WORD.toString(), 
										lineCode.getLineNumber(), 
										index - count
									);
								
								// add to lists
								tokens.add(t);
								lineCode.addToken(t);
							 System.out.print(token + ".BOOL_CONST_FALSE");
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
					 case 'n':
						 token += sourceCode.charAt(index++); count++;
						 if(sourceCode.charAt(index) == 'u' && sourceCode.charAt(index + 1) == 'l' 
								 && sourceCode.charAt(index + 2) == 'l') {
							 token += "ull"; index += 3; count += 3;
							 if (index < sourceCode.length() && sourceCode.charAt(index) == ' ') {
							 t = new Token(
										token, 
										TokenName.NULL.toString(), 
										TokenType.RESERVED_WORD.toString(), 
										lineCode.getLineNumber(), 
										index - count
									);
								
								// add to lists
								tokens.add(t);
								lineCode.addToken(t);
							 System.out.print(token + ".NULL");
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
		Tokenizer.tokenize(new CodeLine("#newsfeed #shares nulls", 1));
	}
		
}	
