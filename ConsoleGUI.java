package console;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

/** 
 * Author: Vinith Yedidi
 * Version: 1.0
 * Date: 6/2/2020
 */

public class ConsoleGUI implements ActionListener {
	
	JFrame frame;
	JTextArea textArea;
	JButton button;

	CustomBufferedReader reader;
	
	public ConsoleGUI(JFrame frame, JTextArea textArea, JButton button, CustomBufferedReader reader) throws IOException {
		
		this.frame = frame;
		this.textArea = textArea;
		this.button = button;
		this.reader = reader;
		
	}
	
	public void setUpGUI() {
		frame = new JFrame("Console");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(screenSize.width / 4, screenSize.height / 4, screenSize.width / 2, screenSize.height / 2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		textArea = new JTextArea(24, 80);
	    textArea.setBackground(Color.BLACK);
	    textArea.setForeground(Color.LIGHT_GRAY);
	    textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
		textArea.setEditable(true);
		textArea.setLineWrap(true);
		
		button = new JButton("Enter");
		button.setSize(screenSize.width / 4, 300);
		button.addActionListener(this);

		frame.getContentPane().add(textArea);
		frame.getContentPane().add(button, BorderLayout.SOUTH);
		frame.setVisible(true);
	}
	
	// Sets up CustomBufferedReader to read the user's commands and sets System.out to textArea.
	public void setUpIO() {
		@SuppressWarnings("unused")
		CustomBufferedReader reader = new CustomBufferedReader(new InputStreamReader(System.in), textArea);

		System.setOut(new PrintStream(new OutputStream() {
		      @Override
		      public void write(int b) throws IOException {
		    	// redirects data to the text area
		          textArea.append(String.valueOf((char)b));
		          // scrolls the text area to the end of data
		          textArea.setCaretPosition(textArea.getDocument().getLength());
		          // keeps the textArea up to date
		          textArea.update(textArea.getGraphics());
		      }
		    }));

	}

	// When the button is pressed, the infinite loop in the Main method restarts.
	@Override
	public void actionPerformed(ActionEvent e) {
		Console.releaseSemaphore();
		
	}

}
