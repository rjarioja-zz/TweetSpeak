
package tweetspeak.collections;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class GrammarRules {
	/*
	 * Change to this
	 * private static String path = "grammar/ProductionRules.cfg";
	 * 
	 */
	
	// test rules
	private static String path = "grammar/TestProductionRules.cfg";
	private static File file = new File(path);
	private static Scanner scanner;
	
	private static ArrayList<ArrayList<String>> rules = new ArrayList<ArrayList<String>>();
	private static ArrayList<String> productions;
	
	static String text = "";
	//getters
	public static ArrayList<ArrayList<String>> getRules() { return rules; }
	
	//setters
	public static ArrayList<String> getRule(int rule) { return rules.get(rule); }
	
	//methods
	public static void initialize() throws FileNotFoundException {
		scanner = new Scanner(file);
		while (scanner.hasNextLine()) {
			productions = new ArrayList<String>();
			String productionRule = scanner.nextLine();
			StringTokenizer tokenizer = new StringTokenizer(productionRule);
			
			while (tokenizer.hasMoreTokens()) productions.add(tokenizer.nextToken());
			if (productions.size() != 0) rules.add(productions);
		}
	}
	
	public static String printRules() {
		text = "";
		for (int counter = 0; counter < rules.size(); counter++) {
			text += counter + " ";
			for (int counter2 = 0; counter2 < rules.get(counter).size(); counter2++)
				text += rules.get(counter).get(counter2) + " ";
			
			text += "\n";
		}
		return text;
	}
}