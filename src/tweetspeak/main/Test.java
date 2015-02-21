package tweetspeak.main;

import tweetspeak.divisions.*;
import tweetspeak.functions.Tokenizer;

import java.io.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Test implements ActionListener {

	private String title = "TweetSpeak Test";
	private String filename = "sample.tsp";
	private File sourceFile = new File(filename);
	
	private JFrame frame;
	private JScrollPane scrollPane;
	private JPanel panel1, panel2;
	private JTextArea textArea;
	private JButton buttonLoad, buttonSave, buttonExit;
	private JButton buttonTokenizer, buttonParser, buttonInterpreter;
	private JFileChooser load = new JFileChooser();
	private JFileChooser save = new JFileChooser();
	
	public Test() {
		frame = new JFrame(title);
		panel1 = new JPanel();
		panel2 = new JPanel();
		textArea = new JTextArea("");
		buttonLoad = new JButton("Load Source Code");
		buttonSave = new JButton("Save Source Code");
		buttonExit = new JButton("Exit Program");
		buttonTokenizer = new JButton("Tokenize");
		buttonParser = new JButton("Parse");
		buttonInterpreter = new JButton("Interprete");
		
	}
	
	public void launchApp() {
		
		textArea.setFont(new java.awt.Font("Consolas", 0, 14));
		textArea.setTabSize(2);
		
		scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(800,600));
		scrollPane.setWheelScrollingEnabled(true);
		
		panel1.setLayout(new BorderLayout());
		panel1.add(scrollPane, BorderLayout.CENTER);
		
		panel2.setLayout(new GridLayout(2,3));
		panel2.add(buttonLoad);
		panel2.add(buttonSave);
		panel2.add(buttonExit);
		panel2.add(buttonTokenizer);
		panel2.add(buttonParser);
		panel2.add(buttonInterpreter);
		
		frame.setLayout(new BorderLayout());
		frame.add(panel1, BorderLayout.CENTER);
		frame.add(panel2, BorderLayout.SOUTH);
		frame.pack();
		frame.setVisible(true);
		
		buttonLoad.addActionListener(this);
		buttonSave.addActionListener(this);
		buttonExit.addActionListener(this);
		buttonTokenizer.addActionListener(this);
		/*buttonRun.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		SeeOutputActionPerformed(arg0);
        	}
        });*/
		buttonParser.setEnabled(false);
		buttonInterpreter.setEnabled(false);
		
		frame.addWindowListener(new CloseHandler());
	}
	
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		String sourceCode = "";
		int lineCount = 0;
	
		if (textArea.getText() != null) {
			if (source == buttonLoad) {
				load.showOpenDialog(frame);
                sourceFile = load.getSelectedFile();
                filename = load.getSelectedFile().getName();
                frame.setTitle(title + " - " + filename);
                try {
					BufferedReader read = new BufferedReader(new FileReader(sourceFile));
					String input;
					textArea.setText("");
					
					while ((input = read.readLine()) != null) {
						Code.addLine(new CodeLine(input, ++lineCount));
						sourceCode += input + "\n";
					}
					
					textArea.setText(sourceCode);
					Code.setCode(sourceCode);
					read.close();
				}
				catch (FileNotFoundException fnfe) {}
				catch (IOException ie) {}
			}
			
			else if (source == buttonSave) {
				save.showSaveDialog(frame);
				sourceFile = save.getSelectedFile();
				filename = save.getSelectedFile().getName();
				try {
					PrintWriter write = new PrintWriter(new FileWriter(sourceFile, false));
					write.print(textArea.getText());
					write.close();
				}
				catch (IOException ie) {}
			}
			
			else if (source == buttonTokenizer) {
//				System.out.print(Code.toLines());
//				Tokenizer.tokenize(Code.getLine(0));
				Code.setCode(textArea.getText());
				TokenOutput tokenOutput = new TokenOutput();
				tokenOutput.launchApp();
			}
			
			else if (source == buttonExit) System.exit(0);
		}
	}
	
	class CloseHandler extends WindowAdapter {
		public void windowClosing(WindowEvent we) {	System.exit(0); }
	}
	
	public static void main(String args[]) {
		Test test = new Test();
		test.launchApp();
	}

}