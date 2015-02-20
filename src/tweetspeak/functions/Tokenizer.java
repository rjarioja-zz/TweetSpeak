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
			System.out.print("\nindex:" + index + " - ");
			
			if (!Character.isWhitespace(sourceCode.charAt(index))) {
				switch(sourceCode.charAt(index)) {
					
					/*
					 * OPERATORS - ARITHMETIC
					 */
					case '+':
						break;
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
									
									if (sourceCode.charAt(index++) == 'o' && sourceCode.charAt(index++) == 'g') {
										token += "og"; count+=2;
										
										switch (sourceCode.charAt(index)) {
											// login
											case 'i': 
												 
												token += sourceCode.charAt(index++); count++;
												
												if (sourceCode.charAt(index++) == 'n'	&& sourceCode.charAt(index) == ' ') {
													token += "n "; count+=2;
													token += sourceCode.charAt(index);
													System.out.print(token + ".START");
													
													//create token
													tokens.add(
														new Token(
															token, 
															TokenName.START.toString(), 
															tokenType, 
															lineCode.getLineNumber(), 
															index - count
														)
													);
													
													continue;
												}
													
												else {
													System.out.print(token + ".STARTERROR");
													index -= 2; // backtrack 2
													continue;
												}
												
											// logout
											case 'o':
												token += sourceCode.charAt(index++);
												
												if (sourceCode.charAt(index++) == 'u' && sourceCode.charAt(index++) == 't') {
													token += "ut";
													
													// check if no other characters at the end
													if (index == sourceCode.length()) {
														System.out.print(token + " END");
														
														// create token
														tokens.add(
																new Token(
																	token, 
																	TokenName.START.toString(), 
																	tokenType, 
																	lineCode.getLineNumber(), 
																	index - count
																)
															);
														
														continue;
													}
													
													else {
														System.out.print(token + ".ENDERROR");
														index -= 2; // backtrack 2
														continue;
													}
												}
												
												else {
													index -= 2; // backtrack 2
													continue;
												}
												
											default:
												System.out.print("level 3 exit");
												break;
										}
									}
									
									else {
										index -= 2; // backtrack 2
										continue;
									}
								}
								
							default:
								System.out.print("level 2 exit");
								break;
									
						}
						
						break;
						
					default:
						System.out.print("level 1 exit");
						break;
				}
			}
			
			else System.out.print("space");
			index++;
			count++;
		}
	}
	
	// TESTING
	/*public static void main(String args[]) {
		Tokenizer.tokenize(new CodeLine("#login123 ", 1));
	}*/
		
}	