package tweetspeak.main;

import tweetspeak.divisions.*;
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
	private JButton buttonLoad, buttonSave, buttonRun, buttonClose;
	private JFileChooser browser = new JFileChooser();
	
	public Test() {
		frame = new JFrame(title);
		panel1 = new JPanel();
		panel2 = new JPanel();
		textArea = new JTextArea("");
		buttonLoad = new JButton("Load");
		buttonSave = new JButton("Save");
		buttonRun = new JButton("Run");
		buttonClose = new JButton("Close");
	}
	
	public void launchApp() {
		textArea.setPreferredSize(new Dimension(700,500));
		textArea.setFont(new java.awt.Font("Consolas", 0, 14));
		textArea.setTabSize(2);
		
		scrollPane = new JScrollPane(textArea);
		scrollPane.setWheelScrollingEnabled(true);
		
		panel1.setLayout(new BorderLayout());
		panel1.add(scrollPane, BorderLayout.CENTER);
		
		panel2.setLayout(new GridLayout(1,4));
		panel2.add(buttonLoad);
		panel2.add(buttonSave);
		panel2.add(buttonRun);
		panel2.add(buttonClose);
		
		frame.setLayout(new BorderLayout());
		frame.add(panel1, BorderLayout.CENTER);
		frame.add(panel2, BorderLayout.SOUTH);
		frame.pack();
		frame.setVisible(true);
		
		buttonLoad.addActionListener(this);
		buttonSave.addActionListener(this);
		buttonRun.addActionListener(this);
		buttonClose.addActionListener(this);
		
		frame.addWindowListener(new CloseHandler());
	}
	
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		String sourceCode = "";
		int lineCount = 0;
	
		if (textArea.getText() != null) {
			if (source == buttonLoad) {
				browser.showOpenDialog(frame);
                sourceFile = browser.getSelectedFile();
                filename = browser.getSelectedFile().toString();
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
				try {
					PrintWriter write = new PrintWriter(new FileWriter(sourceFile, true));
					write.print(textArea.getText());
					write.close();
				}
				catch (IOException ie) {}
			}
			
			else if (source == buttonRun) {
				System.out.print(Code.toLines());
			}
			
			else if (source == buttonClose) System.exit(0);
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