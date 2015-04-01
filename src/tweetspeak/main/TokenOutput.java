package tweetspeak.main;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import tweetspeak.divisions.Code;
import tweetspeak.divisions.CodeLine;
import tweetspeak.functions.Tokenizer;
import tweetspeak.objects.Token;

public class TokenOutput implements ActionListener {

	private String title = "TweetSpeak Tokens";
	private JFrame frame;
	private JScrollPane scrollPane;
	private JPanel panel1, panel2;
	private JTextArea textArea;
	private JButton buttonSource, buttonTokenized, buttonTokenList, buttonClose;
	
	public TokenOutput() {
		frame = new JFrame(title);
		panel1 = new JPanel();
		panel2 = new JPanel();
		textArea = new JTextArea("");
		buttonSource = new JButton("Source");
		buttonTokenized = new JButton("Tokens");
		buttonTokenList = new JButton("Token list");
		buttonClose = new JButton("Close");
	}
	
	public TokenOutput(String filename) {
		this();
		frame.setTitle(title + " - " + filename);
	}
	
	public void launch() {
		textArea.setBackground(Color.BLACK);
		textArea.setFont(new java.awt.Font("Consolas", 0, 14));
		textArea.setForeground(Color.white);
		textArea.setTabSize(2);
		/*textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);*/
		textArea.setText(Code.toLines());
		textArea.setEditable(false);
		
		scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(640, 480));
		scrollPane.setWheelScrollingEnabled(true);
		
		panel1.setLayout(new BorderLayout());
		panel1.add(scrollPane, BorderLayout.CENTER);
		
		panel2.setLayout(new GridLayout(1,4));
		panel2.add(buttonSource);
		panel2.add(buttonTokenized);
		panel2.add(buttonTokenList);
		panel2.add(buttonClose);
		
		frame.setLayout(new BorderLayout());
		frame.add(panel1, BorderLayout.CENTER);
		frame.add(panel2, BorderLayout.SOUTH);
		frame.pack();
		frame.setVisible(true);
		
		buttonSource.setEnabled(false);
		buttonSource.addActionListener(this);
		buttonTokenized.addActionListener(this);
		buttonTokenList.addActionListener(this);
		buttonClose.addActionListener(this);
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		
		if (source == buttonSource) {
			String text = "";
			text += Code.getCode();
			text += "==========";
			text += Code.toLines();
			textArea.setText(text);
			buttonSource.setEnabled(false);
			buttonTokenized.setEnabled(true);
			buttonTokenList.setEnabled(true);
		} else if (source == buttonTokenized) {
			buttonSource.setEnabled(true);
			buttonTokenized.setEnabled(false);
			buttonTokenList.setEnabled(true);
			Tokenizer.clearTokenizedCode();
			
			Token t = Tokenizer.getToken();
			String outputBuffer = "";
			while (t != null) {
				outputBuffer += t.toString();
				if (t.getName().equals("NEWLINE") 
						|| Tokenizer.getIndex() == Code.getLine(Tokenizer.getLineNumber()).getLineCode().length()) {
					System.out.println("tokenoutput - newline");
					outputBuffer += "\n";
				}
				t = Tokenizer.getToken();
			}
			textArea.setText(outputBuffer);
			
		} else if (source == buttonTokenList) {
			String text = "";
			Tokenizer.clearTokenizedCode();
			Code.clearTokens();
			
			Token t = Tokenizer.getToken();
			while (t != null) t = Tokenizer.getToken();
			
			for (CodeLine line : Code.getLineList()) {
				String lineNumber = Integer.toString(line.getLineNumber());
				if (lineNumber.length() == 1) lineNumber = 0 + lineNumber + "\t";
				else lineNumber += "\t";
				
				text += lineNumber;
				text += !line.getTokens().isEmpty() ? line.getTokens() + "\n" : "\n";
			}
			textArea.setText(text);
			buttonSource.setEnabled(true);
			buttonTokenized.setEnabled(true);
			buttonTokenList.setEnabled(false);
		} else if (source == buttonClose) frame.dispose();
	}
}