package tweetspeak.main;

import java.io.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import tweetspeak.divisions.Code;

public class TokenOutput implements ActionListener {

	private String title = "TweetSpeak Tokens";
	private String filename = "sample.tsp";
	private String sourceCode = "";
	private File sourceFile = new File(filename);
	
	private JFrame frame;
	private JScrollPane scrollPane;
	private JPanel panel1, panel2;
	private JTextArea textArea;
	private JButton buttonSource, buttonTokenized, buttonClose;
	
	public TokenOutput() {
		frame = new JFrame(title);
		panel1 = new JPanel();
		panel2 = new JPanel();
		textArea = new JTextArea("");
		buttonSource = new JButton("Source");
		buttonTokenized = new JButton("Tokens");
		buttonClose = new JButton("Close");
	}
	
	public TokenOutput(String filename) {
		this();
		this.filename = filename;
		frame.setTitle(title + " - " + filename);
	}
	
	public void launchApp() {
		textArea.setBackground(Color.BLACK);
		textArea.setFont(new java.awt.Font("Consolas", 0, 14));
		textArea.setForeground(Color.GREEN);
		textArea.setTabSize(2);
		textArea.setText(Code.getCode());
		
		scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(640, 480));
		scrollPane.setWheelScrollingEnabled(true);
		
		panel1.setLayout(new BorderLayout());
		panel1.add(scrollPane, BorderLayout.CENTER);
		
		panel2.setLayout(new GridLayout(1,3));
		panel2.add(buttonSource);
		panel2.add(buttonTokenized);
		panel2.add(buttonClose);
		
		frame.setLayout(new BorderLayout());
		frame.add(panel1, BorderLayout.CENTER);
		frame.add(panel2, BorderLayout.SOUTH);
		frame.pack();
		frame.setVisible(true);
		
		buttonSource.addActionListener(this);
		buttonTokenized.addActionListener(this);
		buttonClose.addActionListener(this);
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
	
		if (source == buttonSource) {
			
            
		}
		
		else if (source == buttonTokenized) {
//				System.out.print(Code.toLines());
//				Tokenizer.tokenize(Code.getLine(0));
		}
		
		else if (source == buttonClose) frame.dispose();
	}
}