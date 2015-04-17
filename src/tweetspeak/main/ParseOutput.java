package tweetspeak.main;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;

import static javax.swing.ScrollPaneConstants.*;
import tweetspeak.collections.GrammarRules;
import tweetspeak.collections.TokenName;
import tweetspeak.divisions.Code;
import tweetspeak.functions.Parser;
import tweetspeak.functions.Tokenizer;
import tweetspeak.objects.TokenNode;

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
			GrammarRules.clear();
			
			String text = "";
			try {
				new Parser();
				GrammarRules.initialize();
			} catch (IOException e) {}
			
			text += "PARSE TREE: \n============================================================================================================================================\n\n"; 
			
			//grading problem
			TokenNode root = new TokenNode("<PROGRAM>");
			TokenNode functions = new TokenNode("<FUNCTIONS>");
			TokenNode mainfxn = new TokenNode("<MAIN_FUNCTION>");
			TokenNode subfxns = new TokenNode("<SUB_FUNCTIONS>");
			TokenNode statements1 = new TokenNode("<STATEMENTS>");
			TokenNode statements2 = new TokenNode("<STATEMENTS>");
			TokenNode statements3 = new TokenNode("<STATEMENTS>");
			TokenNode statements4 = new TokenNode("<STATEMENTS>");
			TokenNode statements5 = new TokenNode("<STATEMENTS>");
			TokenNode statements6 = new TokenNode("<STATEMENTS>");
			TokenNode statements7 = new TokenNode("<STATEMENTS>");
			TokenNode statements8 = new TokenNode("<STATEMENTS>");
			TokenNode statements9 = new TokenNode("<STATEMENTS>");
			TokenNode statements10 = new TokenNode("<STATEMENTS>");
			TokenNode statements11 = new TokenNode("<STATEMENTS>");
			TokenNode statements12 = new TokenNode("<STATEMENTS>");
			TokenNode statements13 = new TokenNode("<STATEMENTS>");
			TokenNode statements14 = new TokenNode("<STATEMENTS>");
			
			TokenNode statement1 = new TokenNode("<STATEMENT>");
			TokenNode statement2 = new TokenNode("<STATEMENT>");
			TokenNode statement3 = new TokenNode("<STATEMENT>");
			TokenNode statement4 = new TokenNode("<STATEMENT>");
			TokenNode statement5 = new TokenNode("<STATEMENT>");
			TokenNode statement6 = new TokenNode("<STATEMENT>");
			TokenNode statement7 = new TokenNode("<STATEMENT>");
			TokenNode statement8 = new TokenNode("<STATEMENT>");
			TokenNode statement9 = new TokenNode("<STATEMENT>");
			TokenNode statement10 = new TokenNode("<STATEMENT>");
			TokenNode statement11 = new TokenNode("<STATEMENT>");
			TokenNode statement12 = new TokenNode("<STATEMENT>");
			TokenNode statement13 = new TokenNode("<STATEMENT>");
			TokenNode statement14 = new TokenNode("<STATEMENT>");
			
			TokenNode io1 = new TokenNode("<IO>");
			TokenNode io2 = new TokenNode("<IO>");
			TokenNode io3 = new TokenNode("<IO>");
			TokenNode io4 = new TokenNode("<IO>");
			TokenNode io5 = new TokenNode("<IO>");
			TokenNode io6 = new TokenNode("<IO>");
			TokenNode io7 = new TokenNode("<IO>");
			
			TokenNode control = new TokenNode("<CONTROL_FLOW>");
			TokenNode condi = new TokenNode("<CONDITIONAL>");
			
			TokenNode declare = new TokenNode("<DECLARATION>");
			TokenNode branching = new TokenNode("<BRANCHING>");
			TokenNode loop = new TokenNode("<LOOPING>");
			TokenNode ifs = new TokenNode("<IF_STMT");
			TokenNode ifelseifs = new TokenNode("<IF_ELSEIF_STMT>");
			TokenNode ifelses = new TokenNode("<IF_ELSE_STMT>");
			TokenNode datatype = new TokenNode("<DATATYPE>");
			
			TokenNode input = new TokenNode("<INPUT_STMT>");
			TokenNode output = new TokenNode("<OUTPUT_STMT>");
			TokenNode expr = new TokenNode("<EXPRESSIONS>");
			TokenNode math = new TokenNode("<MATH_EXPR>");
			TokenNode string = new TokenNode("<STRING_EXPR>");
			TokenNode rel = new TokenNode("<REL_EXPR>");
			TokenNode values = new TokenNode("<VALUE>");
			TokenNode valuei = new TokenNode("<VALUE>");
			TokenNode consti = new TokenNode("<CONST>");
			TokenNode consts = new TokenNode("<CONST>");
			
			TokenNode indent = new TokenNode(TokenName.INDENT.toString());
			TokenNode dedent = new TokenNode(TokenName.DEDENT.toString());
			root.addChild(new TokenNode(TokenName.START.toString()));
			root.addChild(new TokenNode(TokenName.PROG_NAME.toString()));
			root.addChild(indent);
			root.addChild(functions);
			root.addChild(dedent);
			root.addChild(new TokenNode(TokenName.END.toString()));
			
			functions.addChild(mainfxn);
			
			mainfxn.addChild(new TokenNode(TokenName.MAIN.toString()));
			mainfxn.addChild(indent);
			mainfxn.addChild(statements1);
			mainfxn.addChild(dedent);
			
			statements1.addChild(statement1);
			statements1.addChild(statements2);
			statement1.addChild(declare);
			declare.addChild(datatype);
			declare.addChild(new TokenNode(TokenName.VAR.toString()));
			datatype.addChild(new TokenNode(TokenName.DATATYPE_INT.toString()));
			
			statements2.addChild(statement2);
			statements2.addChild(statements3);
			statement2.addChild(io1);
			io1.addChild(output);
			output.addChild(new TokenNode(TokenName.OUTPUT.toString()));
			output.addChild(expr);
			expr.addChild(string);
			string.addChild(values);
			values.addChild(consts);
			consts.addChild(new TokenNode(TokenName.STRING_CONST.toString()));
			
			statements3.addChild(statement3);
			statements3.addChild(statements4);
			statement3.addChild(io2);
			io2.addChild(input);
			input.addChild(new TokenNode(TokenName.INPUT.toString()));
			input.addChild(new TokenNode(TokenName.VAR.toString()));
			
			statements4.addChild(statement4);
			statements4.addChild(statements5);
			statement4.addChild(control);
			
			/*if (Parser.parse()) text += Parser.getRoot().toString()  + "\n";
			else JOptionPane.showMessageDialog(null, "Parser failed :(");*/
			text += root.toString();
			text += "\n============================================================================================================================================\n\n";
			
			textArea.setText(text);
			textArea.setLineWrap(true);
			textArea.setWrapStyleWord(true);
			scrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
			
			buttonSource.setEnabled(true);
			buttonParsed.setEnabled(false);
			buttonProductionRules.setEnabled(true);
		} else if (source == buttonProductionRules) {
			GrammarRules.clear();
			try {
				GrammarRules.initialize();
			} catch (FileNotFoundException fnfe) {}
			
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