package tweetspeak.collections;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class GrammarRules {
	private String path = "tweetspeak/resources/ProductionRules.cfg";
	private File file = new File(path);
	private Scanner scanner = new Scanner(file);
	
	private static ArrayList<ArrayList<String>> rules = new ArrayList<ArrayList<String>>();
	private static ArrayList<String> productions;
	
	//constructors
	public GrammarRules() throws FileNotFoundException {
		while (scanner.hasNextLine()) {
			productions = new ArrayList<String>();
			String productionRule = scanner.nextLine();
			StringTokenizer tokenizer = new StringTokenizer(productionRule);
			
			while (tokenizer.hasMoreTokens()) productions.add(tokenizer.nextToken());
			if (productions.size() != 0) rules.add(productions);
		}
	}
	
	//getters
	public static ArrayList<ArrayList<String>> getRules() { return rules; }
	
	//setters
	public static ArrayList<String> getRule(int rule) { return rules.get(rule); }
	
	//methods
	public static void printRules() {
		for (int counter = 0; counter < rules.size(); counter++) {
			System.out.print(counter + " ");
			for (int counter2 = 0; counter2 < rules.get(counter).size(); counter2++) {
				System.out.print(rules.get(counter).get(counter2) + " ");
			}
			System.out.println("");
		}
	}
}