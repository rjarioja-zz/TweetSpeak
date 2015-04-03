package tweetspeak.main;

import tweetspeak.divisions.*;
import tweetspeak.functions.Tokenizer;

import java.io.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class TweetSpeak implements ActionListener {

	private String title = "TweetSpeak Test";
	private String filename = "new.tsp";
	private File sourceFile = new File(filename);
	
	private JFrame frame;	
	private JScrollPane scrollPane;
	private JPanel panel1, panel2;
	private JTextArea textArea;
	private JButton buttonTokenizer, buttonParser, buttonInterpreter;
	private JFileChooser openBox = new JFileChooser();
	private JFileChooser saveBox = new JFileChooser();
	
	//menu
	private JMenuBar menuBar;
	private JMenu file, compiler, about;
	private JMenuItem open;
	private JMenuItem save;
	private JMenuItem saveAs;
	private JMenuItem exit;
	private JMenuItem tokenize;
	private JMenuItem parser;
	
	//nested class
	class CloseHandler extends WindowAdapter {
		public void windowClosing(WindowEvent we) {	
			System.exit(0); 
		}
	}
	
	//constructors
	public TweetSpeak() {
		frame = new JFrame(title + " - " + filename);
		panel1 = new JPanel();
		panel2 = new JPanel();
		textArea = new JTextArea("");
		buttonTokenizer = new JButton("Tokenize");
		buttonParser = new JButton("Parse");
		buttonInterpreter = new JButton("Interpret");
		
		menuBar = new JMenuBar();
		file = new JMenu("File");
		compiler = new JMenu("Compiler");
		about = new JMenu("About");
	}
	
	//methods
	public void launchApp() {
		textArea.setFont(new java.awt.Font("Consolas", 0, 14));
		textArea.setTabSize(2);
		
		scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(800,600));
		scrollPane.setWheelScrollingEnabled(true);
		
		file.setMnemonic(KeyEvent.VK_F);
		menuBar.add(file);
		compiler.setMnemonic(KeyEvent.VK_C);
		menuBar.add(compiler);
		about.setMnemonic(KeyEvent.VK_A);
		menuBar.add(about);
		
		open = new JMenuItem("Open File             ");
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		open.addActionListener(this);
		file.add(open);
		
		save = new JMenuItem("Save File             ");
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		save.addActionListener(this);
		file.add(save);
		
		saveAs = new JMenuItem("Save As...            ");
		saveAs.addActionListener(this);
		file.add(saveAs);
		
		exit = new JMenuItem("Exit Program...       ");
		exit.setMnemonic(KeyEvent.VK_X);
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
		exit.addActionListener(this);
		file.add(exit);
		
		tokenize = new JMenuItem("Tokenize              ");
		tokenize.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
		tokenize.addActionListener(this);
		compiler.add(tokenize);
		
		parser = new JMenuItem("Parse              ");
		parser.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		parser.addActionListener(this);
		compiler.add(tokenize);
		
		panel1.setLayout(new BorderLayout());
		panel1.add(scrollPane, BorderLayout.CENTER);
		
		panel2.setLayout(new GridLayout(1,3));
		panel2.add(buttonTokenizer);
		panel2.add(buttonParser);
		panel2.add(buttonInterpreter);
		
		frame.setLayout(new BorderLayout());
		frame.add(menuBar, BorderLayout.NORTH);
		frame.add(panel1, BorderLayout.CENTER);
		frame.add(panel2, BorderLayout.SOUTH);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new CloseHandler());		
		
		buttonTokenizer.addActionListener(this);
		buttonTokenizer.setEnabled(false);
		buttonParser.setEnabled(false);
		buttonInterpreter.setEnabled(false);
	}
	
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		String sourceCode = "";
		
		if (textArea.getText() != null) {
			if (source == open) {
				openBox.showOpenDialog(frame);
                sourceFile = openBox.getSelectedFile();
                filename = openBox.getSelectedFile().getName();
                frame.setTitle(title + " - " + filename);
                try {
					BufferedReader read = new BufferedReader(new FileReader(sourceFile));
					String input;
					textArea.setText("");
					
					while ((input = read.readLine()) != null) sourceCode += input + "\n";
					
					textArea.setText(sourceCode);
					Code.setCode(sourceCode);
					read.close();
					
					save.setEnabled(true);
					saveAs.setEnabled(true);
					buttonTokenizer.setEnabled(true);
				}
				catch (FileNotFoundException fnfe) {}
				catch (IOException ie) {}
			} else if (source == save) {
				if (filename.equals("new.tsp")) {
					saveBox.showSaveDialog(frame);
					sourceFile = saveBox.getSelectedFile();
					filename = saveBox.getSelectedFile().getName();
					frame.setTitle(title + " - " + filename);
				}
				try {
					PrintWriter write = new PrintWriter(new FileWriter(sourceFile, false));
					sourceCode = textArea.getText();
					Code.setCode(sourceCode);
					write.print(sourceCode);
					write.close();
					buttonTokenizer.setEnabled(true);
				}
				catch (IOException ie) {}				
			} else if (source == saveAs) {
				saveBox.showSaveDialog(frame);
				sourceFile = saveBox.getSelectedFile();
				filename = saveBox.getSelectedFile().getName();
				frame.setTitle(title + " - " + filename);
				try {
					PrintWriter write = new PrintWriter(new FileWriter(sourceFile, false));
					sourceCode = textArea.getText();
					Code.setCode(sourceCode);
					write.print(textArea.getText());
					write.close();
					buttonTokenizer.setEnabled(true);
				}
				catch (IOException ie) {}
			} else if (source == tokenize || source == buttonTokenizer) {
				Code.setCode(textArea.getText());
				Tokenizer.initialize();
				TokenOutput tokenOutput = new TokenOutput(filename);
				tokenOutput.launch();
			} else if (source == exit) System.exit(0);
		}
	}

	public static void main(String args[]) {
		TweetSpeak main = new TweetSpeak();
		main.launchApp();
	}

}