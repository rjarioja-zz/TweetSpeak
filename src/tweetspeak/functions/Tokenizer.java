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
		
		while (index < sourceCode.length()) {
			String token = "", tokenType = "";
			int count = 0, whitespace = 0;
			Token t;
			
//			tokenized += "\nindex:" + index + " - ";			
			if (!Character.isWhitespace(sourceCode.charAt(index))) {
				if (sourceCode.charAt(index) == 'a') {
					if (sourceCode.charAt(index + 1) == 'c') {
						if (index + 6 <= sourceCode.length() && (sourceCode.substring(index + 2, index + 6).equals("cept"))) {
							token += "accept"; count += 6; index += 6;
					        //create token
					        t = new Token(
					                token, 
					                TokenName.BOOL_CONST_TRUE.toString(), 
					                TokenType.CONSTANT.toString(), 
					                lineCode.getLineNumber(), 
					                index - count
					            );
					        tokenized += "[" + t.getName() + " = " + t.getLexeme() + "]";
					        // add to lists
					        tokens.add(t);
					        lineCode.addToken(t);
					        continue;
						}
					} else if (sourceCode.charAt(index + 1) == 'r') {
						if (index + 14 <= sourceCode.length() && (sourceCode.substring(index + 2, index + 14).equals("eFriendsWith"))) {
							token += "areFriendsWith"; count += 14; index += 14;
					        //create token
					        t = new Token(
					                token, 
					                TokenName.CONCAT.toString(), 
					                TokenType.RESERVED_WORD.toString(), 
					                lineCode.getLineNumber(), 
					                index - count
					            );
					        tokenized += "[" + t.getName() + "]";
					        // add to lists
					        tokens.add(t);
					        lineCode.addToken(t);
					        continue;
						}
					} 
						
				} else if (sourceCode.charAt(index) == 'd') {
					if (index + 7 <= sourceCode.length() && (sourceCode.substring(index + 1, index + 7).equals("ecline"))) {
						token += "decline"; count += 7; index += 7;
				        //create token
				        t = new Token(
				                token, 
				                TokenName.BOOL_CONST_FALSE.toString(), 
				                TokenType.CONSTANT.toString(), 
				                lineCode.getLineNumber(), 
				                index - count
				            );
				        tokenized += "[" + t.getName() + " = " + t.getLexeme() + "]";
				        // add to lists
				        tokens.add(t);
				        lineCode.addToken(t);
				        continue;
					}
				} else if (sourceCode.charAt(index) == 'n') {
					if (index + 4 <= sourceCode.length() && (sourceCode.substring(index + 1, index + 4).equals("ull"))) {
						token += "null"; count += 4; index += 4;
				        //create token
				        t = new Token(
				                token, 
				                TokenName.NULL.toString(), 
				                TokenType.CONSTANT.toString(), 
				                lineCode.getLineNumber(), 
				                index - count
				            );
				        tokenized += "[" + t.getName() + "]";
				        // add to lists
				        tokens.add(t);
				        lineCode.addToken(t);
				        continue;
					}
				}  else if (sourceCode.charAt(index) == '"') {
					t = stringConst(lineCode, index + 1);
					if (t != null) {
						tokenized += "[" + t.getName() + " = \"" + t.getLexeme() + "\"]";
						tokens.add(t);
						lineCode.addToken(t);
						index += t.getLexeme().length() + 2;
						continue;
					}
				} else if (sourceCode.charAt(index) == '\'') {
					t = charConst(lineCode, index + 1);
					if (t != null) {
						tokenized += "[" + t.getName() + " = \'" + t.getLexeme() + "\']";
						tokens.add(t);
						lineCode.addToken(t);
						index += t.getLexeme().length() + 2;
						continue;
					}
				} else if (Character.isDigit(sourceCode.charAt(index))) {
					t = numConst(lineCode, index);
					if (t != null) {
						tokenized += "[" + t.getName() + " = " + t.getLexeme() + "]";
               			tokens.add(t);
               			lineCode.addToken(t);
               			index += t.getLexeme().length();
               			continue;
               		}
				}
				switch(sourceCode.charAt(index)) {

/************************************************************************************
				jkzurita
				OPERATORS - ARITHMETIC                                                
*************************************************************************************/
				
				case '+':
				    token += sourceCode.charAt(index++); count++;
				    if (index < sourceCode.length() && sourceCode.charAt(index) == '+') {
				        token += sourceCode.charAt(index++); count++;
				        //create token
				        t = new Token(
				                token, 
				                TokenName.INC_OP.toString(), 
				                TokenType.OPERATOR.toString(), 
				                lineCode.getLineNumber(), 
				                index - count
				            );
				        tokenized += "[" + t.getName() + "]";
				        // add to lists
				        tokens.add(t);
				        lineCode.addToken(t);
				        continue;
				    }
				    
				    else {
				        // create token
				        t = new Token(
				                token, 
				                TokenName.ADD_OP.toString(), 
				                TokenType.OPERATOR.toString(), 
				                lineCode.getLineNumber(), 
				                index - count
				            );
				        tokenized += "[" + t.getName() + "]";
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
				        //create token
				        t = new Token(
				                token, 
				                TokenName.DEC_OP.toString(), 
				                TokenType.OPERATOR.toString(), 
				                lineCode.getLineNumber(), 
				                index - count
				            );
				        tokenized += "[" + t.getName() + "]";
				        // add to lists
				        tokens.add(t);
				        lineCode.addToken(t);
				        continue;
				    }
				    
				    else {
				        // create token
				        t = new Token(
				                token, 
				                TokenName.DIF_OP.toString(), 
				                TokenType.OPERATOR.toString(), 
				                lineCode.getLineNumber(), 
				                index - count
				            );
				        tokenized += "[" + t.getName() + "]";
				        // add to lists
				        tokens.add(t);
				        lineCode.addToken(t);
				        continue;
				    }

				case '*':
				    token += sourceCode.charAt(index); count++;
				    //create token
				    t = new Token (
				                    token,
				                    TokenName.MUL_OP.toString(),
				                    TokenType.OPERATOR.toString(),
				                    lineCode.getLineNumber(),
				                    index - count
				            );
				    tokenized += "[" + t.getName() + "]";
				    // add to lists
				    tokens.add(t);
				    lineCode.addToken(t);
				    
				    break;
				    
				case '/':
				    token += sourceCode.charAt(index); count++;
				    //create token
				    t = new Token (
				                    token,
				                    TokenName.DIV_OP.toString(),
				                    TokenType.OPERATOR.toString(),
				                    lineCode.getLineNumber(),
				                    index - count
				            );
				    tokenized += "[" + t.getName() + "]";
				    //add to lists
				    tokens.add(t);
				    lineCode.addToken(t);
				    
				    break;
				    
				case '%':
				    token += sourceCode.charAt(index); count++;
				    //create token
				    t = new Token (
				                    token,
				                    TokenName.MOD_OP.toString(),
				                    TokenType.OPERATOR.toString(),
				                    lineCode.getLineNumber(),
				                    index - count
				            );
				    //add to lists
				    tokenized += "[" + t.getName() + "]";
				    tokens.add(t);
				    lineCode.addToken(t);						
				    break;

				case '^':
				    token += sourceCode.charAt(index); count++;
				    //create token
				    t = new Token (
				                    token,
				                    TokenName.EXP_OP.toString(),
				                    TokenType.OPERATOR.toString(),
				                    lineCode.getLineNumber(),
				                    index - count
				            );
				    //add to lists
				    tokenized += "[" + t.getName() + "]";
				    tokens.add(t);
				    lineCode.addToken(t);
				    break;
				    
				case '(':
				    token += sourceCode.charAt(index); count++;
				    //create token
				    t = new Token (
				                    token,
				                    TokenName.LEFT_PAREN.toString(),
				                    TokenType.OPERATOR.toString(),
				                    lineCode.getLineNumber(),
				                    index - count
				            );
				    tokenized += "[" + t.getName() + "]";
				    //add to lists
				    tokens.add(t);
				    lineCode.addToken(t);
				    break;
				    
				case ')':
				    token += sourceCode.charAt(index); count++;
				    //create token
				    t = new Token (
				                    token,
				                    TokenName.RIGHT_PAREN.toString(),
				                    TokenType.OPERATOR.toString(),
				                    lineCode.getLineNumber(),
				                    index - count
				            );
				    tokenized += "[" + t.getName() + "]";
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
				        //create token
				        t = new Token (
				        		token,
				        		TokenName.OR_OP.toString(),
				        		TokenType.OPERATOR.toString(),
				        		lineCode.getLineNumber(),
				        		index - count
				        		);
				        
				        //add to lists
				        tokenized += "[" + t.getName() + "]";
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
				        //create token
				        t = new Token (
				        		token,
				        		TokenName.AND_OP.toString(),
				        		TokenType.OPERATOR.toString(),
				        		lineCode.getLineNumber(),
				        		index - count
				        		);
				        
				        //add to lists
				        tokenized += "[" + t.getName() + "]";
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
				        //create token
				        t = new Token (
				                    token,
				                    TokenName.LESS_EQ_OP.toString(),
				                    TokenType.OPERATOR.toString(),
				                    lineCode.getLineNumber(),
				                    index - count
				                );
				        //add to lists
				        tokenized += "[" + t.getName() + "]";
				        tokens.add(t);
				        lineCode.addToken(t);
				        continue;
				    } else {
				        //create token
				        t = new Token (
				                    token,
				                    TokenName.LESS_OP.toString(),
				                    TokenType.OPERATOR.toString(),
				                    lineCode.getLineNumber(),
				                    index - count
				                );
				        // add to lists
				        tokenized += "[" + t.getName() + "]";
				        tokens.add(t);
				        lineCode.addToken(t);
				        continue;
				    }
				    
				case '>':
				    token += sourceCode.charAt(index++); count++;
				    if (index < sourceCode.length() && sourceCode.charAt(index) == '=') {
				        token += sourceCode.charAt(index++); count++;
				        //create token
				        t = new Token (
				                    token,
				                    TokenName.GREAT_EQ_OP.toString(),
				                    TokenType.OPERATOR.toString(),
				                    lineCode.getLineNumber(),
				                    index - count
				                );
				        //add to lists
				        tokenized += "[" + t.getName() + "]";
				        tokens.add(t);
				        lineCode.addToken(t);
				        continue;
				    } else {
				        //create token
				        t = new Token (
				                    token,
				                    TokenName.GREAT_OP.toString(),
				                    TokenType.OPERATOR.toString(),
				                    lineCode.getLineNumber(),
				                    index - count
				                );
				        // add to lists
				        tokenized += "[" + t.getName() + "]";
				        tokens.add(t);
				        lineCode.addToken(t);
				        continue;
				    }

				case '=':
				    token += sourceCode.charAt(index++); count++;
				    if (index < sourceCode.length() && sourceCode.charAt(index) == '=') {
				        token += sourceCode.charAt(index++); count++;
				        //create token
				        t = new Token(
				                    token,
				                    TokenName.EQUAL_OP.toString(),
				                    TokenType.OPERATOR.toString(),
				                    lineCode.getLineNumber(),
				                    index - count
				                );
				        //add to lists
				        tokenized += "[" + t.getName() + "]";
				        tokens.add(t);
				        lineCode.addToken(t);
				        continue;
				    } else {
				        //create token
				        t = new Token(
				                    token,
				                    TokenName.ASSIGN_OP.toString(),
				                    TokenType.OPERATOR.toString(),
				                    lineCode.getLineNumber(),
				                    index - count
				                );
				        //add to lists
				        tokenized += "[" + t.getName() + "]";
				        tokens.add(t);
				        lineCode.addToken(t);
				        continue;
				    }
				    
				case '!':
				    token += sourceCode.charAt(index++); count++;
				    if (index < sourceCode.length() && sourceCode.charAt(index) == '=') {
				        token += sourceCode.charAt(index++); count++;
				        //create token
				        t = new Token(
				                    token,
				                    TokenName.NOT_EQUAL_OP.toString(),
				                    TokenType.OPERATOR.toString(),
				                    lineCode.getLineNumber(),
				                    index - count
				                );
				        //add to lists
				        tokenized += "[" + t.getName() + "]";
				        tokens.add(t);
				        lineCode.addToken(t);
				        continue;
				    } else {
				        //create token
				        t = new Token(
				                    token,
				                    TokenName.NOT_OP.toString(),
				                    TokenType.OPERATOR.toString(),
				                    lineCode.getLineNumber(),
				                    index - count
				                );
				        //add to lists
				        tokenized += "[" + t.getName() + "]";
				        tokens.add(t);
				        lineCode.addToken(t);
				        continue;
				    }
				    
				/*************************************************************************************
				OTHER TOKENS                                                
				**************************************************************************************/

				case ',':
				    token += sourceCode.charAt(index); count++;
				    //create token
				    t = new Token (
				                token,
				                TokenName.PARAM_SEP.toString(),
				                TokenType.OPERATOR.toString(),
				                lineCode.getLineNumber(),
				                index - count
				            );
				    tokenized += "[" + t.getName() + "]";
				    tokens.add(t);
			        lineCode.addToken(t);
				    break;
				case ';':
				    token += sourceCode.charAt(index); count++;
				    //create token
				    t = new Token (
				                token,
				                TokenName.STMT_SEP.toString(),
				                TokenType.OPERATOR.toString(),
				                lineCode.getLineNumber(),
				                index - count
				            );
				    tokenized += "[" + t.getName() + "]";
				    tokens.add(t);
			        lineCode.addToken(t);
				    break;
				case '"':
				    token += sourceCode.charAt(index); count++;
				    //create token
				    t = new Token (
				                token,
				                TokenName.DQUOTE.toString(),
				                TokenType.OPERATOR.toString(),
				                lineCode.getLineNumber(),
				                index - count
				            );
				    tokenized += "[" + t.getName() + "]";
				    tokens.add(t);
			        lineCode.addToken(t);
				    break;
				case '\'':
				    token += sourceCode.charAt(index); count++;
				    //create token
				    t = new Token (
				                token,
				                TokenName.SQUOTE.toString(),
				                TokenType.OPERATOR.toString(),
				                lineCode.getLineNumber(),
				                index - count
				            );
				    tokenized += "[" + t.getName() + "]";
				    tokens.add(t);
			        lineCode.addToken(t);
				    break;
					
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
					               tokenized += "[" + t.getName() + "]";
					               continue;
					            } else {
					               index --; 
					               tokenized += "[INVALID_TOKEN = COMMENT @" + index + "]";
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
						            tokenized += "[" + t.getName() + "]";
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
							            tokenized += "[" + t.getName() + "]";
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
						                                tokenized += "[" + t.getName() + "]";
						                                
						                                if (Character.isLetter(sourceCode.charAt(index))) {
						                                	t = identifier(lineCode, index);
							                            	 if (t != null) {
								                            	 t.setName(TokenName.PROG_NAME.toString());
							                                	 tokens.add(t);
							                                	 lineCode.addToken(t);
							                                	 
							                                	 tokenized += "[" + t.getName() + " = " + t.getLexeme() + "]";
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
						                                tokenized += "[" + t.getName() + "]";
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
							                tokenized += "[" + t.getName() + "]";
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
					                tokenized += "[" + t.getName() + "]";
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
				                    tokenized += "[" + t.getName() + "]";
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
					                            tokenized += "[" + t.getName() + "]";
					                            
					                            // if next is identifier
					                            if (Character.isLetter(sourceCode.charAt(index))) {
					                            	 t = identifier(lineCode, index);
					                            	 if (t != null) {
					                            		 tokenized += "[" + t.getLexeme() + "]";
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
					                            tokenized += "[" + t.getName() + "]";
					                            
					                            // if next is identifier
					                            if (Character.isLetter(sourceCode.charAt(index))) {
					                            	 t = identifier(lineCode, index);
					                            	 if (t != null) {
					                            		 tokenized += "[" + t.getLexeme() + "]";
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
					                            tokenized += "[" + t.getName() + "]";
					                            
					                            // if next is identifier
					                            if (Character.isLetter(sourceCode.charAt(index))) {
					                            	 t = identifier(lineCode, index);
					                            	 if (t != null) {
					                            		 tokenized += "[" + t.getLexeme() + "]";
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
					                            tokenized += "[" + t.getName() + "]";
					                            
					                            // if next is identifier
					                            if (Character.isLetter(sourceCode.charAt(index))) {
					                            	 t = identifier(lineCode, index);
					                            	 if (t != null) {
					                            		 tokenized += "[" + t.getLexeme() + "]";
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
					                            tokenized += "[" + t.getName() + "]";
					                            
					                            // if next is identifier
					                            if (Character.isLetter(sourceCode.charAt(index))) {
					                            	 t = identifier(lineCode, index);
					                            	 if (t != null) {
					                            		 tokenized += "[" + t.getLexeme() + "]";
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
					                            tokenized += "[" + t.getName() + "]";
					                            
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
			                            tokenized += "[" + t.getName() + "]";
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
					                            tokenized += "[" + t.getName() + "]";
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
					                            tokenized += "[" + t.getName() + "]";
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
					                            tokenized += "[" + t.getName() + "]";
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
						                            tokenized += "[" + t.getName() + "]";
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
			                            tokenized += "[" + t.getName() + "]";
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
			                            tokenized += "[" + t.getName() + "]";
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
			                            tokenized += "[" + t.getName() + "]";
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
					            tokenized += "[" + t.getName() + "]";
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
				        
			//	LEVEL 1 SWITCH
					default:
						if (Character.isLetter(sourceCode.charAt(index)) || sourceCode.charAt(index) == '_') {
							t = identifier(lineCode, index);
							if (t != null) {
		               			tokens.add(t);
		               			lineCode.addToken(t);
		               			index += t.getLexeme().length();
		               			tokenized += "[" + t.getLexeme() + "]";
	//	               			continue;
		               		}
						}
						
				}
			} else {
				tokenized += " ";
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
		while (index < sourceCode.length()) {
			if (sourceCode.charAt(index) == '"') {
				if (sourceCode.charAt(index - 1) == '\\') {
					constant += sourceCode.charAt(index);
					index += 2;
					continue;
				}
				else break;
			}
			constant += sourceCode.charAt(index++);
		}
		if (sourceCode.length() - index == constant.length() + 1 || constant.isEmpty()) return null;
		else return new Token(constant, TokenName.STRING_CONST.toString(), TokenType.CONSTANT.toString(), lineCode.getLineNumber(), index);
	}
	
	public static Token charConst(CodeLine lineCode, int index) {
		String sourceCode = lineCode.getLineCode();
		String constant = "";
		if (sourceCode.charAt(index + 1) == '\'') {
			constant += sourceCode.charAt(index);
			return new Token(constant, TokenName.CHAR_CONST.toString(), TokenType.CONSTANT.toString(), lineCode.getLineNumber(), index);
		}
		else return null;
	}
	
	public static Token numConst(CodeLine lineCode, int index) {
		String sourceCode = lineCode.getLineCode();
		String constant = "";
		if (Character.isDigit(sourceCode.charAt(index))) {
			constant += sourceCode.charAt(index); index++;
			int dotCount = 0;
			while (index < sourceCode.length() && (Character.isDigit(sourceCode.charAt(index)) || sourceCode.charAt(index) == '.')) {
				if (sourceCode.charAt(index) == '.') dotCount++;
				if (dotCount > 1) break;
				
				constant += sourceCode.charAt(index); index++;
			}
			if (dotCount == 0) return new Token(constant, TokenName.INT_CONST.toString(), TokenType.CONSTANT.toString(), lineCode.getLineNumber(), index);
			else return new Token(constant, TokenName.FLOAT_CONST.toString(), TokenType.CONSTANT.toString(), lineCode.getLineNumber(), index);
		}
		else return null;
	}
}
	
