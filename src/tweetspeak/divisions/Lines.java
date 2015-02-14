package tweetspeak.divisions;

import java.util.*;

import tweetspeak.objects.Token;

public class Lines {
	private LinkedList<Token> tokens = new LinkedList<Token>();
	private LinkedList<Character> characters = new LinkedList<Character>();
	private int blockLevel;
}
