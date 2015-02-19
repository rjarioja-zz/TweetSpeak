package tweetspeak.functions;

import java.util.*;

import tweetspeak.collections.TokenName;
import tweetspeak.collections.TokenType;
import tweetspeak.divisions.CodeLine;
import tweetspeak.objects.Token;

public class Tokenizer {
	private static String sourceCode;
	private static LinkedList<Token> tokens = new LinkedList<Token>();
	private static String token = "", tokenName = "", tokenType = "";
	
	//getters
	public static String getSourceCode() { return sourceCode; }
	
	//setters
	public static void setSourceCode(String token) { Tokenizer.sourceCode = token; }
	
	//methods
	public static void tokenize(CodeLine lineCode) {
		sourceCode = lineCode.getLineCode();
		ArrayList<Character> characters = new ArrayList<Character>();
		for (char c : sourceCode.toCharArray()) characters.add(new Character(c));
		
		ListIterator<Character> iterator = characters.listIterator();
		System.out.println(characters.toString());
		int count = 0;
		while (iterator.hasNext()) {
			Character currentIndex = iterator.next();
			System.out.print("Current = " + count);
			if (Character.isWhitespace(currentIndex.charValue())) {
				System.out.println("\twhitespace");
				count++;
				continue;
			}
			
			switch(currentIndex.charValue()) {
			//level 1
				case '#':
					currentIndex = iterator.next(); count++;
					switch (currentIndex.charValue()) {
					//level 2
						case 'l':
							currentIndex = iterator.next(); count++; // gets o
							if (currentIndex.charValue() == 'o' && iterator.next().charValue() == 'g') {
								currentIndex = iterator.next(); count+=2;
								switch (currentIndex.charValue()) {
								//level 3
									case 'i':
										currentIndex = iterator.next(); // gets n
										if (currentIndex.charValue() == 'n' && iterator.next().charValue() == ' ') {
											System.out.println(" START");
											count += 2;
										}
									break;
									case 'o':
										currentIndex = iterator.next(); count++; // gets o
										if (currentIndex.charValue() == 'u' && iterator.next().charValue() == 't') System.out.println(" END");
									break;
								}
							}
						break;
						default: System.out.println(" level 2 exit");
					}
				break;
				default: System.out.println(" level 1 exit");
			}
			count++;
		}
	}
	
	
	public static void main(String args[]) {
		Tokenizer.tokenize(new CodeLine("123#login ##123 123", 1));
	}
		
}	