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
	public static String tokenize(CodeLine lineCode) {
		sourceCode = lineCode.getLineCode();
		int index = 0;
		String tokenized = "";
//		System.out.println("\n" + sourceCode + " " + sourceCode.length());
		
		while (index < sourceCode.length()) {
			String token = "", tokenType = "";
			int count = 0, whitespace = 0;
			Token t;
			
			tokenized += "\nindex:" + index + " - ";			
			if (!Character.isWhitespace(sourceCode.charAt(index))) {
				switch(sourceCode.charAt(index)) {

/************************************************************************************
				jkzurita
				OPERATORS - ARITHMETIC                                                
*************************************************************************************/

					
					/*
					 * OPERATORS - ARITHMETIC
					 */
/*					case '+':
						token += sourceCode.charAt(index++); count++;
						if (index < sourceCode.length() && sourceCode.charAt(index) == '+') {
							token += sourceCode.charAt(index++); count++;
//							System.out.println(index + " " + count);
							System.out.print(token + ".INC_OP" + index + " " + sourceCode.length());
							System.out.print(" " + index + " ");
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
							System.out.print(token + ".ADD_OP" + index + " " + sourceCode.length());
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
//							continue;
						}
//						break;
						
					case '-':
						token += sourceCode.charAt(index++); count++;
						if (index < sourceCode.length() && sourceCode.charAt(index) == '-') {
							token += sourceCode.charAt(index++); count++;
							//System.out.println(index + " " + count);
							System.out.print(token + ".DEC_OP" + index + " " + sourceCode.length());
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
							System.out.print(token + ".DIF_OP" + index + " " + sourceCode.length());
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
						*/

//				case '+':

					
				case '+':
//>>>>>>> branch 'master' of https://rjarioja@github.com/rjarioja/TweetSpeak.git
				    token += sourceCode.charAt(index++); count++;
				    if (index < sourceCode.length() && sourceCode.charAt(index) == '+') {
				        token += sourceCode.charAt(index++); count++;
				        tokenized += token + ".INC_OP";
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
				        tokenized += token + ".ADD_OP";
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
				    

				    
				case '-':
				    token += sourceCode.charAt(index++); count++;
				    if (index < sourceCode.length() && sourceCode.charAt(index) == '-') {
				        token += sourceCode.charAt(index++); count++;
				        //System.out.println(index + " " + count);
				        tokenized += token + ".DEC_OP";
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
				    	tokenized += token + ".DIF_OP";
				        // create token
				        t = new Token(
				                token, 
				                TokenName.DIF_OP.toString(), 
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
				    tokenized += token + ".MUL_OP";
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
				    tokenized += token + ".DIV_OP";
				    //create token
				    t = new Token (
				                    token,
				                    TokenName.DIV_OP.toString(),
				                    TokenType.OPERATOR.toString(),
				                    lineCode.getLineNumber(),
				                    index - count
				            );
				    //add to lists
				    tokens.add(t);
				    lineCode.addToken(t);
				    
				    break;
				    
				case '%':
				    token += sourceCode.charAt(index); count++;
				    tokenized += token + ".MOD_OP";
				    //create token
				    t = new Token (
				                    token,
				                    TokenName.MOD_OP.toString(),
				                    TokenType.OPERATOR.toString(),
				                    lineCode.getLineNumber(),
				                    index - count
				            );
				    //add to lists
				    tokens.add(t);
				    lineCode.addToken(t);						
				    break;

				case '^':
				    token += sourceCode.charAt(index); count++;
				    tokenized += token + ".EXP_OP";
				    //create token
				    t = new Token (
				                    token,
				                    TokenName.EXP_OP.toString(),
				                    TokenType.OPERATOR.toString(),
				                    lineCode.getLineNumber(),
				                    index - count
				            );
				    //add to lists
				    tokens.add(t);
				    lineCode.addToken(t);
				    break;
				    
				case '(':
				    token += sourceCode.charAt(index); count++;
				    tokenized += token + ".LEFT_PAREN";
				    //create token
				    t = new Token (
				                    token,
				                    TokenName.LEFT_PAREN.toString(),
				                    TokenType.OPERATOR.toString(),
				                    lineCode.getLineNumber(),
				                    index - count
				            );
				    //add to lists
				    tokens.add(t);
				    lineCode.addToken(t);
				    break;
				    
				case ')':
				    token += sourceCode.charAt(index); count++;
				    tokenized += token + ".RIGHT_PAREN";
				    //create token
				    t = new Token (
				                    token,
				                    TokenName.RIGHT_PAREN.toString(),
				                    TokenType.OPERATOR.toString(),
				                    lineCode.getLineNumber(),
				                    index - count
				            );
				    //add to lists
				    tokens.add(t);
				    lineCode.addToken(t);
				    break;

				/************************************************************************************
				RELATIONAL AND LOGICAL OPS
				*************************************************************************************/

				case '|':
				    token += sourceCode.charAt(index++); count++;
				    if (index < sourceCode.length() && sourceCode.charAt(index) == '|') {
				        token += sourceCode.charAt(index++); count++;
				        tokenized += token + ".OR_OP";
				        //create token
				        t = new Token (
				        		token,
				        		TokenName.OR_OP.toString(),
				        		TokenType.OPERATOR.toString(),
				        		lineCode.getLineNumber(),
				        		index - count
				        		);
				        
				        //add to lists
				        tokens.add(t);
				        lineCode.addToken(t);
				        continue;
				    } else {
				        index --;
				        tokenized += token + ".ERROR";
//											continue;
				    }
				break;
				case '&':
				    token += sourceCode.charAt(index++); count++;
				    if (index < sourceCode.length() && sourceCode.charAt(index) == '&') {
				        token += sourceCode.charAt(index++); count++;
				        tokenized += token + ".AND_OP";
				        //create token
				        t = new Token (
				        		token,
				        		TokenName.AND_OP.toString(),
				        		TokenType.OPERATOR.toString(),
				        		lineCode.getLineNumber(),
				        		index - count
				        		);
				        
				        //add to lists
				        tokens.add(t);
				        lineCode.addToken(t);
				        continue;
				    } else {
				        index --;
				        tokenized += token + ".ERROR";
//											continue;
				    }
				break;
				case '<':
				    token += sourceCode.charAt(index++); count++;
				    if (index < sourceCode.length() && sourceCode.charAt(index) == '=') {
				        token += sourceCode.charAt(index++); count++;
				        tokenized += token + ".LESS_EQ_OP";
				        //create token
				        t = new Token (
				                    token,
				                    TokenName.LESS_EQ_OP.toString(),
				                    TokenType.OPERATOR.toString(),
				                    lineCode.getLineNumber(),
				                    index - count
				                );
				        //add to lists
				        tokens.add(t);
				        lineCode.addToken(t);
				        continue;
				    } else {
				    	tokenized += token + ".LESS_OP";
				        //create token
				        t = new Token (
				                    token,
				                    TokenName.LESS_OP.toString(),
				                    TokenType.OPERATOR.toString(),
				                    lineCode.getLineNumber(),
				                    index - count
				                );
				        // add to lists
				        tokens.add(t);
				        lineCode.addToken(t);
				        continue;
				    }
				    
				case '>':
				    token += sourceCode.charAt(index++); count++;
				    if (index < sourceCode.length() && sourceCode.charAt(index) == '=') {
				        token += sourceCode.charAt(index++); count++;
				        tokenized += token + ".GREAT_EQ_OP";
				        //create token
				        t = new Token (
				                    token,
				                    TokenName.GREAT_EQ_OP.toString(),
				                    TokenType.OPERATOR.toString(),
				                    lineCode.getLineNumber(),
				                    index - count
				                );
				        //add to lists
				        tokens.add(t);
				        lineCode.addToken(t);
				        continue;
				    } else {
				    	tokenized += token + ".GREAT_OP";
				        //create token
				        t = new Token (
				                    token,
				                    TokenName.GREAT_OP.toString(),
				                    TokenType.OPERATOR.toString(),
				                    lineCode.getLineNumber(),
				                    index - count
				                );
				        // add to lists
				        tokens.add(t);
				        lineCode.addToken(t);
				        continue;
				    }

				case '=':
				    token += sourceCode.charAt(index++); count++;
				    if (index < sourceCode.length() && sourceCode.charAt(index) == '=') {
				        token += sourceCode.charAt(index++); count++;
				        tokenized += token + ".EQUAL_OP";
				        //create token
				        t = new Token(
				                    token,
				                    TokenName.EQUAL_OP.toString(),
				                    TokenType.OPERATOR.toString(),
				                    lineCode.getLineNumber(),
				                    index - count
				                );
				        //add to lists
				        tokens.add(t);
				        lineCode.addToken(t);
				        continue;
				    } else {
				    	tokenized += token + ".ASSIGN_OP";
				        //create token
				        t = new Token(
				                    token,
				                    TokenName.ASSIGN_OP.toString(),
				                    TokenType.OPERATOR.toString(),
				                    lineCode.getLineNumber(),
				                    index - count
				                );
				        //add to lists
				        tokens.add(t);
				        lineCode.addToken(t);
				        continue;
				    }
				    
				case '!':
				    token += sourceCode.charAt(index++); count++;
				    if (index < sourceCode.length() && sourceCode.charAt(index) == '=') {
				        token += sourceCode.charAt(index++); count++;
				        tokenized += token + ".NOT_EQUAL_OP";
				        //create token
				        t = new Token(
				                    token,
				                    TokenName.NOT_EQUAL_OP.toString(),
				                    TokenType.OPERATOR.toString(),
				                    lineCode.getLineNumber(),
				                    index - count
				                );
				        //add to lists
				        tokens.add(t);
				        lineCode.addToken(t);
				        continue;
				    } else {
				    	tokenized += token + ".NOT_OP";
				        //create token
				        t = new Token(
				                    token,
				                    TokenName.NOT_OP.toString(),
				                    TokenType.OPERATOR.toString(),
				                    lineCode.getLineNumber(),
				                    index - count
				                );
				        //add to lists
				        tokens.add(t);
				        lineCode.addToken(t);
				        continue;
				    }
				    
				/*************************************************************************************
				OTHER TOKENS                                                
				**************************************************************************************/

				case ',':
				    token += sourceCode.charAt(index); count++;
				    tokenized += token + ".PARAM_SEP";
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
				    tokenized += token + ".STMT_SEP";
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
				    tokenized += token + ".DQUOTE";
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
				    tokenized += token + ".SQUOTE";
				    //create token
				    t = new Token (
				                token,
				                TokenName.SQUOTE.toString(),
				                TokenType.OPERATOR.toString(),
				                lineCode.getLineNumber(),
				                index - count
				            );
				    break;

//>>>>>>> branch 'master' of https://rjarioja@github.com/rjarioja/TweetSpeak.git
					
/************************************************************************************
				Fhrey
				RESERVED WORDS                                                
*************************************************************************************/
					case '#':
						token += sourceCode.charAt(index++); count++;
						tokenType = TokenType.RESERVED_WORD.toString();
						
						switch (sourceCode.charAt(index)) {
							// comment
							case 'c':
					            token += sourceCode.charAt(index++); count++;
					            // lookforward "omment"
					            if(sourceCode.charAt(index) == 'o' && sourceCode.charAt(index + 1) == 'm' 
					            		&& sourceCode.charAt(index+2) == 'm' && sourceCode.charAt(index+3) == 'e' 
					            		&& sourceCode.charAt(index+4) == 'n' && sourceCode.charAt(index+5) == 't') {
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
					               tokenized += token + ".COMMENT";
					               continue;
					            } else {
					               index --; 
					               tokenized += token + ".COMMENT_ERROR";
					               continue;
					            }
							
					        //#FOLLOW
						    case 'f':
						    	token += sourceCode.charAt(index++); count++;
						        if (sourceCode.charAt(index) == 'o' && sourceCode.charAt(index + 1) == 'l' 
						        		&& sourceCode.charAt(index + 2) == 'l' && sourceCode.charAt(index + 3) == 'o' 
						        		&& sourceCode.charAt(index + 4) == 'w') {
						            token += "ollow"; index += 5; count +=5;
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
						            tokenized += token + ".CONTINUE";
						            continue;
						        } else {
						            index --;
						            tokenized += token + ".ERROR";
						            continue;
						        }
				            
				            // inbox
						    case 'i':
						        token += sourceCode.charAt(index++); count++;
						        if(sourceCode.charAt(index) == 'n' && sourceCode.charAt(index + 1) == 'b'
						                && sourceCode.charAt(index + 2) == 'o' && sourceCode.charAt(index + 3) == 'x') {
						            token += "nbox"; index += 4;
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
							            tokenized += token + ".INPUT";
							            continue;
						            } else {
						                index--;
						                tokenized += token + ".INPUT_ERROR";
						                continue;
						            }
						        } else {
						            index --;
						            tokenized += token + ".INPUT_ERROR";
						            continue;
						        }
						        
					        // like, login, logout
							case 'l':
						        // check if no other characters at the start
						        if (index - 1 == 0) {
						            // login, logout
						            token += sourceCode.charAt(index++); count++;
						            
						            if (sourceCode.charAt(index) == 'o' && sourceCode.charAt(index + 1) == 'g') {
						                token += "og"; count += 2; index += 2;
						                switch (sourceCode.charAt(index)) {
						                    //login
						                    case 'i': 
						                        token += sourceCode.charAt(index++); count++;
						                        if (sourceCode.charAt(index) == 'n') {  
						                            token += sourceCode.charAt(index++); count++;
						                            if (index < sourceCode.length() && sourceCode.charAt(index++) == ' ') {
						                                t = new Token(
						                                        token, 
						                                        TokenName.START.toString(), 
						                                        TokenType.RESERVED_WORD.toString(), 
						                                        lineCode.getLineNumber(), 
						                                        index - count
						                                    );
						                                
						                                tokens.add(t);
						                                lineCode.addToken(t);
						                                tokenized += token + ".START";
						                                
						                                if (Character.isLetter(sourceCode.charAt(index))) {
						                                	t = identifier(lineCode, index);
							                            	 if (t != null) {
								                            	 t.setName(TokenName.PROG_NAME.toString());
							                                	 tokens.add(t);
							                                	 lineCode.addToken(t);
							                                	 
							                                	 tokenized += " " + t.getLexeme() + ".PROG_NAME";
							                                	 index += t.getLexeme().length();
							                                	 continue;
							                            	 }
						                                }
						                                continue;
						                            } else {
						                                index --; 
						                                tokenized += token + ".START_ERROR";
						                                continue;
						                            }
						                        } else {
						                            index --; 
						                            tokenized += token + ".LOGI_ERROR";
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
						                                tokenized += token + ".END";
						                                continue;
						                            } else {
						                                index --; 
						                                tokenized += token + ".END_ERROR";
						                                continue;
						                            } 
						                        } else {
						                            index --;
						                            tokenized += token + ".END_ERROR";
						                            continue;
						                        }
						                        
				                        //	LEVEL 3 LOG - IN/OUT SWITCH 
						                    default:
						                    	tokenized += token + ".LOG_ERROR";
						                        index -= 2;
						                        continue;
						                }
						            } else if (sourceCode.charAt(index) == 'i') {
						            	// if line starts w/ #like
						            	// must delete in parser
						            	token += sourceCode.charAt(index++); count++;
							            if(sourceCode.charAt(index) == 'k' && sourceCode.charAt(index + 1) == 'e') {
							                token += "ke"; index += 2; count += 2;
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
							                tokenized += token + ".DO";
							                continue;
							            } else {
							                index --;
							                tokenized += token + ".DO_ERROR";
							                continue;
							            }
						            }
						        }
						        
					            //like
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
					                tokenized += token + ".DO";
					                continue;
					            } else {
					                index --;
					                tokenized += token + ".L_ERROR";
					                continue;
					            }
						        
						    // newsfeed
							case 'n':
					            token += sourceCode.charAt(index++); count++;
					            // lookforward "ewsfeed"
					            if(sourceCode.charAt(index) == 'e' && sourceCode.charAt(index + 1) == 'w' 
					            		&& sourceCode.charAt(index + 2) == 's' && sourceCode.charAt(index + 3) == 'f' 
					            		&& sourceCode.charAt(index + 4) == 'e' && sourceCode.charAt(index + 5) == 'e' 
					                    && sourceCode.charAt(index + 6) == 'd') {
					                token += "ewsfeed"; count += 7; index += 7;	
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
				                    tokenized += token + ".MAIN";
				                    continue;
					                } else {
					                    index --;
					                    tokenized += token + ".MAIN_ERROR";
					                    continue;
					                }

					         // ooti, ootf, ootc, oots, ootb, ootv
							case 'o': 
					            token += sourceCode.charAt(index++); count++;
					            if(sourceCode.charAt(index) == 'o' && sourceCode.charAt(index + 1) == 't') {
					                token += "ot"; index +=2; count +=2;
					                /* DATA 
					                 * TYPES
					                 */
					                switch(sourceCode.charAt(index)) {
					                    //int
					                	case 'i': 
					                        token += sourceCode.charAt(index++); count++;
					                        if (index < sourceCode.length() && sourceCode.charAt(index++) == ' ') {
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
					                            tokenized += token + ".DATATYPE_INT";
					                            
					                            // if next is identifier
					                            if (Character.isLetter(sourceCode.charAt(index))) {
					                            	 t = identifier(lineCode, index);
					                            	 if (t != null) {
					                            		 tokenized += " " + t.getLexeme() + ".VAR";
					                            		 tokens.add(t);
					                            		 lineCode.addToken(t);
					                            		 index += t.getLexeme().length();
					                            	 }
					                            }
					                            continue;
					                        } else {
					                            index --;
					                            tokenized += token + ".ERROR";
					                            continue;
					                        }
					                    
					                	// float
					                	case 'f':
					                        token += sourceCode.charAt(index++); count++;
					                        if (index < sourceCode.length() && sourceCode.charAt(index++) == ' ') {
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
					                            tokenized += token + ".DATATYPE_FLOAT";
					                            
					                            // if next is identifier
					                            if (Character.isLetter(sourceCode.charAt(index))) {
					                            	 t = identifier(lineCode, index);
					                            	 if (t != null) {
					                            		 tokenized += " " + t.getLexeme() + ".VAR";
					                            		 tokens.add(t);
					                            		 lineCode.addToken(t);
					                            		 index += t.getLexeme().length();
					                            	 }
					                            }
					                            continue;
					                        } else {
					                            index --;
					                            tokenized += token + ".ERROR";
					                            continue;
					                        }
					                    
					                	// char
					                	case 'c':
					                        token += sourceCode.charAt(index++); count++;
					                        if (index < sourceCode.length() && sourceCode.charAt(index++) == ' ') {
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
					                            tokenized += token + ".DATATYPE_CHAR";
					                            
					                            // if next is identifier
					                            if (Character.isLetter(sourceCode.charAt(index))) {
					                            	 t = identifier(lineCode, index);
					                            	 if (t != null) {
					                            		 tokenized += " " + t.getLexeme() + ".VAR";
					                            		 tokens.add(t);
					                            		 lineCode.addToken(t);
					                            		 index += t.getLexeme().length();
					                            	 }
					                            }
					                            continue;
					                        } else {
					                            index --;
					                            tokenized += token + ".ERROR";
					                            continue;
					                        }
					                    
					                	// string
					                	case 's':
					                        token += sourceCode.charAt(index++); count++;
					                        if (index < sourceCode.length() && sourceCode.charAt(index++) == ' ') {
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
					                            tokenized += token + ".DATATYPE_STRING";
					                            
					                            // if next is identifier
					                            if (Character.isLetter(sourceCode.charAt(index))) {
					                            	 t = identifier(lineCode, index);
					                            	 if (t != null) {
					                            		 tokenized += " " + t.getLexeme() + ".VAR";
					                            		 tokens.add(t);
					                            		 lineCode.addToken(t);
					                            		 index += t.getLexeme().length();
					                            	 }
					                            }
					                            continue;
					                        } else {
					                            index --;
					                            tokenized += token + ".ERROR";
					                            continue;
					                        }
					                    
					                	// boolean
					                	case 'b':
					                        token += sourceCode.charAt(index++); count++;
					                        if (index < sourceCode.length() && sourceCode.charAt(index++) == ' ') {
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
					                            tokenized += token + ".DATATYPE_BOOL";
					                            
					                            // if next is identifier
					                            if (Character.isLetter(sourceCode.charAt(index))) {
					                            	 t = identifier(lineCode, index);
					                            	 if (t != null) {
					                            		 tokenized += " " + t.getLexeme() + ".VAR";
					                            		 tokens.add(t);
					                            		 lineCode.addToken(t);
					                            		 index += t.getLexeme().length();
					                            	 }
					                            }
					                            continue;
					                        } else {
					                            index --;
					                            tokenized += token + ".ERROR";
					                            continue;
					                        }
					                    
					                	// void
					                	case 'v':
					                        token += sourceCode.charAt(index++); count++;
					                        if (index < sourceCode.length() && sourceCode.charAt(index++) == ' ') {
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
					                            tokenized += token + ".DATATYPE_VOID";
					                            
					                            continue;
					                    
					                        } else {
					                            index --;
					                            tokenized += token + ".ERROR";
					                            continue;
					                        }
					                        
					                   // LEVEL 3 DATATYPE SWITCH
					                	default:
					                		tokenized += token + ".DATATYPE_ERROR";
					                		index -= 2;
					                		continue;
					                }
					            }
					            
					            //#OUTBOX
					            else if (sourceCode.charAt(index) == 'u' && sourceCode.charAt(index + 1) == 't' 
					            		&& sourceCode.charAt(index + 2) == 'b' && sourceCode.charAt(index + 3) == 'o' 
					            		&& sourceCode.charAt(index + 4) == 'x') {
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
						                tokenized += token + ".OUTPUT";
						                continue;
					                } else {
					                    index --;
					                    tokenized += token + ".OUTPUT_ERROR";
					                    continue;
					                }
					            } else {
					                index --;
					                tokenized += token + ".ERROR";
					                continue;
					            }

					            
				            // retweet reply
						    case 'r':
						        token += sourceCode.charAt(index++); count++;
						        if (sourceCode.charAt(index) == 'e') {
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
							                    tokenized += token + ".ELSE_IF";
							                    continue;
							                    } else {
							                        index --;
							                        tokenized += token + ".ERROR";
							                        continue;
							                    }
							                } else {
							                    index --;
							                    tokenized += token + ".ERROR";
							                    continue;
							                }
							            //#REPLY
							            case 'p':
							                token += sourceCode.charAt(index++); count++;
							                if (sourceCode.charAt(index) == 'l' && sourceCode.charAt(index + 1) == 'y') {
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
							                    tokenized += token + ".ELSE";
							                    continue;
							                    } else {
							                        index --;
							                        tokenized += token + ".ERROR";
							                        continue;
							                    }
							                } else {
							                    index --;
							                    tokenized += token + ".ERROR";
							                    continue;
							                }
							                
					                // LEVEL 3 RETWEET REPLY SWITCH
							            default: 
							            	tokenized += token + ".ERROR";
							                index--;
							                continue;
							            }
						        } else {
						            index --;
						            tokenized += token + ".ERROR";
						        }
					            
				            // share, status
							case 's':
					            token += sourceCode.charAt(index++); count++;
					            switch(sourceCode.charAt(index)) {
					            //#SHARE
					                case 'h':
					                    token += sourceCode.charAt(index++); count++;
					                    if (sourceCode.charAt(index) == 'a' 
					                    		&& sourceCode.charAt(index + 1) == 'r' 
					                    		&& sourceCode.charAt(index + 2) == 'e') {
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
					                            tokenized += token + ".ASSIGN";
					                            continue;
					                        } else {
					                            index --;
					                            tokenized += token + ".ASSIGN_ERROR";
					                            continue;
					                        }
					                    } else {
					                        index --;
					                        tokenized += token + ".ASSIGN_ERROR";
					                        continue;
					                    }
					                
					                //#STATUS
					                case 't':
					                    token += sourceCode.charAt(index++); count++;
					                    if (sourceCode.charAt(index) == 'a' && sourceCode.charAt(index + 1) == 't' 
					                    		&& sourceCode.charAt(index + 2) == 'u' && sourceCode.charAt(index + 3) == 's') {
					                            token += "atus"; index += 4; count += 4;
					                            if (index < sourceCode.length() && sourceCode.charAt(index) == ' ') {
					                                t = new Token(
					                                        token, 
					                                        TokenName.WHILE.toString(), 
					                                        TokenType.RESERVED_WORD.toString(), 
					                                        lineCode.getLineNumber(), 
					                                        index - count
					                                    );
					                                // TODO: INSERT IDENTIFIER SCAN
					                                // add to lists
					                                tokens.add(t);
					                                lineCode.addToken(t);
					                                tokenized += token + ".WHILE";
					                                continue;
					                            } else {
					                                index --;
					                                tokenized += token + ".WHILE_ERROR";
					                            }
					                    } else {
					                        index --;
					                        tokenized += token + ".WHILE_ERROR";
					                        continue;
					                    }
					                    
					            //	LEVEL 3 SHARE/STATUS SWITCH 
					                default:
					                	tokenized += sourceCode.charAt(index) + " " + index + " " + sourceCode.length();
				                        index --;
				                        continue;
					            }
					            
					        // trending, throwback, tweet
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
					                    tokenized += token + ".PROC_CALL";
					                    continue;
					                    } else {
					                        index --;
					                        tokenized += token + ".ERROR";
					                        continue;
					                    }
					                } else {
					                    index --;
					                    tokenized += token + ".ERROR";
					                    continue;
					                }
					                
					            //#THROWBACK
					            case 'h':
					                token += sourceCode.charAt(index++); count++;
					                if (sourceCode.charAt(index) == 'r' && sourceCode.charAt(index + 1) == 'o' 
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
					                    tokenized += token + ".PROC_RET";
					                    continue;
					                    } else {
					                        index --;
					                        tokenized += token + ".ERROR";
					                        continue;
					                    }
					                } else {
					                    index --;
					                    tokenized += token + ".ERROR";
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
					                    tokenized += token + ".IF";
					                    continue;
					                    } else {
					                        index --;
					                        tokenized += token + ".ERROR";
					                    }
					                } else {
					                    index --;
					                    tokenized += token + ".ERROR";
					                    continue;
					                }
					                
								//	LEVEL 3 TRENDING THROWBACK TWEET SWITCH
					            default:
					                    tokenized += " level 3 exit";
					                    index --;
					                    continue;
				            }
					            
					            
			            // #UNFOLLOW
					    case 'u':
					        token += sourceCode.charAt(index++); count++;
					        if (sourceCode.charAt(index) == 'n' && sourceCode.charAt(index + 1) == 'f' 
					        		&& sourceCode.charAt(index + 2) == 'o' && sourceCode.charAt(index + 3) == 'l' 
					        		&& sourceCode.charAt(index + 4) == 'l' && sourceCode.charAt(index + 5) == 'o'
					                && sourceCode.charAt(index + 6) == 'w'){
					            
					            token += "nfollow"; index += 7; count +=7;
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
					            tokenized += token + ".BREAK";
					            continue;
					        } else {
					            index --;
					            tokenized += token + ".ERROR";
					            continue;
					        }
						        
					//	LEVEL 2 SWITCH
						default:
							tokenized += sourceCode.charAt(index) + " " + index + " " + sourceCode.length();
							continue;
						}
					
					case 'a':
						token += sourceCode.charAt(index++); count++;
						switch(sourceCode.charAt(index)) {
							case 'c':
								token += sourceCode.charAt(index++); count++;
								if (sourceCode.charAt(index) == 'c' && sourceCode.charAt(index + 1) == 'e' 
							             && sourceCode.charAt(index + 2) == 'p' && sourceCode.charAt(index + 3) == 't') {
									token += "cept"; index += 4; count += 4;
									t = new Token(
									           token, 
									           TokenName.BOOL_CONST_TRUE.toString(), 
									           TokenType.RESERVED_WORD.toString(), 
									           lineCode.getLineNumber(), 
									           index - count
										 );
									tokens.add(t);
									lineCode.addToken(t);
									tokenized += token + ".BOOL_CONST_TRUE";
									continue;
								} else {
									index --;
									tokenized += token + ".ERROR";
									continue;
								}
								
							case 'r':
								token += sourceCode.charAt(index++); count++;
								if (sourceCode.charAt(index) == 'e' && sourceCode.charAt(index + 1) == 'F' 
										&& sourceCode.charAt(index + 2) == 'r' && sourceCode.charAt(index + 3) == 'i' 
										&& sourceCode.charAt(index + 4) == 'e' && sourceCode.charAt(index + 5) == 'n' 
										&& sourceCode.charAt(index + 6) == 'd' && sourceCode.charAt(index + 7) == 's' 
										&& sourceCode.charAt(index + 8) == 'W' && sourceCode.charAt(index + 9) == 'i' 
										&& sourceCode.charAt(index + 10) == 't' && sourceCode.charAt(index + 11) == 'h') {
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
							            tokenized += token + ".CONCAT";
							         continue;
							         } else {
							             index --;
							             tokenized += token + ".ERROR";
							             continue;
							         }
								}
								
							default:
								tokenized += sourceCode.charAt(index) + " " + index + " " + sourceCode.length();
								continue;
							}
					
					case 'd':
						token += sourceCode.charAt(index++); count++;
				        tokenized += token + " " + index + " " + sourceCode.length();
				        continue;
				        /*if (sourceCode.charAt(index) =='e' && sourceCode.charAt(index + 1) == 'c' 
				        		&& sourceCode.charAt(index + 2) == 'l' && sourceCode.charAt(index + 3) == 'i' 
				        		&& sourceCode.charAt(index + 4) == 'n' && sourceCode.charAt(index + 5) == 'e') {

				            token +="ecline"; index += 6; count +=6;
				            
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
			                tokenized += token + ".BOOL_CONST_FALSE";
			                continue;
				        } else {
				            index --;
				            tokenized += token + ".ERROR";
				            continue;
				        }
*/					
				        
//					case 'n':
//						token += sourceCode.charAt(index++); count++;
//						tokenized += sourceCode.charAt(index);
//						continue;
						/*if(sourceCode.charAt(index) == 'u' && sourceCode.charAt(index + 1) == 'l' 
						        && sourceCode.charAt(index + 2) == 'l') {
						    token += "ull"; index += 3; count += 3;
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
						    } else {
						        index --; 
						        System.out.print(token + ".ERROR");
						        continue;
						    }
						}
						else {
						    index --;
						    System.out.print(token + ".ERROR");
						    continue;
						}*/ 
				        
			//	LEVEL 1 SWITCH
					default:
						t = null;
						if (Character.isLetter(sourceCode.charAt(index))) {
							t = identifier(lineCode, index);
							if (t != null) {
	                   			tokenized += t.getLexeme() + ".IDENTIFER";
	                   			tokens.add(t);
	                   			lineCode.addToken(t);
	                   			index += t.getLexeme().length();
	                   			continue;
	                   		}
						}
	                   	else if (sourceCode.charAt(index) == '"') {
	                   		t = stringConst(lineCode, index);
	                   		if (t != null) {
	                   			tokenized += t.getLexeme() + ".CONSTANT";
	                   			tokens.add(t);
	                   			lineCode.addToken(t);
	                   			index += t.getLexeme().length() + 2;
	                   			continue;
	                   		}
	                   	}
						else {
							tokenized += sourceCode.charAt(index);
						}
						
				}
		} else {
			tokenized += "space";
			whitespace++;
		}
			
		index++;
		count++;
		}
		return tokenized;
	}
	
	public static Token identifier(CodeLine lineCode, int index) {
		String sourceCode = lineCode.getLineCode();
		String id = "";
		if (Character.isLetter(sourceCode.charAt(index)) || sourceCode.charAt(index) == '_') {
			id += sourceCode.charAt(index); index++;
			while (index < sourceCode.length()) {
				if (Character.isWhitespace(sourceCode.charAt(index))) break;
				else if (Character.isLetter(sourceCode.charAt(index)) 
						|| Character.isDigit(sourceCode.charAt(index)) 
						|| sourceCode.charAt(index) == '_') {
					id += sourceCode.charAt(index++);
				}
				else return null;
			}
		}
		return new Token(id, TokenName.VAR.toString(), TokenType.IDENTIFIER.toString(), lineCode.getLineNumber(), index);
		//return id;
	}
	
	public static Token stringConst(CodeLine lineCode, int index) {
		String sourceCode = lineCode.getLineCode();
		String constant = "";
		if (sourceCode.charAt(index) == '"') {
			for (int i = index + 1; i < sourceCode.length(); i++) {
				if (sourceCode.charAt(i) == '"') break;
				constant += sourceCode.charAt(i);
			}
			return new Token(constant, TokenName.STRING_CONST.toString(), TokenType.CONSTANT.toString(), lineCode.getLineNumber(), index);
		}
		else return null;
	}
	
	// TESTING
/*
	public static void main(String args[]) {
		Tokenizer.tokenize(new CodeLine("#newsfeed #shares nulls", 1));
	}
		*/
	}









