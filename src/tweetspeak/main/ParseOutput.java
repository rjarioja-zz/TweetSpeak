package tweetspeak.main;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;
import static javax.swing.ScrollPaneConstants.*;
import tweetspeak.collections.GrammarRules;
import tweetspeak.divisions.Code;
import tweetspeak.functions.Parser;
import tweetspeak.functions.Tokenizer;

public class ParseOutput implements ActionListener {

	private String title = "TweetSpeak Tokens", text = "";
	private JFrame frame;
	private JScrollPane scrollPane;
	private JPanel panel1, panel2;
	private JTextArea textArea;
	private JButton buttonSource, buttonParsed, buttonProductionRules, buttonClose;
	
	public ParseOutput() {
		frame = new JFrame(title);
		panel1 = new JPanel();
		panel2 = new JPanel();
		textArea = new JTextArea("");
		buttonSource = new JButton("Source");
		buttonParsed = new JButton("Parse Tree");
		buttonProductionRules = new JButton("Production Rules");
		buttonClose = new JButton("Close");

		text += "ORIGINAL SOURCE: \n============================================================================================================================================\n\n" 
				+ Code.toLines();
		text += "\n============================================================================================================================================\n\n";
	}
	
	public ParseOutput(String filename) {
		this();
		frame.setTitle(title + " - " + filename);
	}
	
	public void launch() {
		textArea.setBackground(Color.BLACK);
		textArea.setFont(new java.awt.Font("Consolas", 0, 14));
		textArea.setForeground(Color.white);
		textArea.setTabSize(2);
		textArea.setText(text);
		textArea.setEditable(false);
		
		scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(640, 480));
		scrollPane.setWheelScrollingEnabled(true);
		
		panel1.setLayout(new BorderLayout());
		panel1.add(scrollPane, BorderLayout.CENTER);
		
		panel2.setLayout(new GridLayout(1,4));
		panel2.add(buttonSource);
		panel2.add(buttonParsed);
		panel2.add(buttonProductionRules);
		panel2.add(buttonClose);
		
		frame.setLayout(new BorderLayout());
		frame.add(panel1, BorderLayout.CENTER);
		frame.add(panel2, BorderLayout.SOUTH);
		frame.pack();
		frame.setVisible(true);
		
		buttonSource.setEnabled(false);
		buttonSource.addActionListener(this);
		buttonParsed.addActionListener(this);
		buttonProductionRules.addActionListener(this);
		buttonClose.addActionListener(this);
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		
		if (source == buttonSource) {
			textArea.setText(text);
			textArea.setLineWrap(false);
			textArea.setWrapStyleWord(false);
			scrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_AS_NEEDED);
			
			buttonSource.setEnabled(false);
			buttonParsed.setEnabled(true);
			buttonProductionRules.setEnabled(true);
		} else if (source == buttonParsed) {
			Tokenizer.reset();
			
			String text = "";
			try {
				new Parser();
			} catch (IOException e) {}
			
			text += "PARSE TREE: \n============================================================================================================================================\n\n"; 
			if (Parser.parser()) text += Parser.getRoot().toString()  + "\n";
			else text += "Parser failed";
			text += "\n============================================================================================================================================\n\n";
			
			
			textArea.setText(text);
			textArea.setLineWrap(true);
			textArea.setWrapStyleWord(true);
			scrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
			
			buttonSource.setEnabled(true);
			buttonParsed.setEnabled(false);
			buttonProductionRules.setEnabled(true);
		} else if (source == buttonProductionRules) {
			String text = "";
			text += "GRAMMAR  RULES: \n============================================================================================================================================\n\n"; 
			text += GrammarRules.printRules();
			text += "\n============================================================================================================================================\n\n";
			
			textArea.setText(text);
			textArea.setLineWrap(false);
			textArea.setWrapStyleWord(false);
			scrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_AS_NEEDED);
			
			buttonSource.setEnabled(true);
			buttonParsed.setEnabled(true);
			buttonProductionRules.setEnabled(false);
		} else if (source == buttonClose) frame.dispose();
	}
}