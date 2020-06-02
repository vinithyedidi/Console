package console;

import java.io.BufferedReader;
import java.io.Reader;
import javax.swing.JTextArea;

/** 
 * Author: Vinith Yedidi
 * Version: 1.0
 * Date: 6/2/2020
 */

public class CustomBufferedReader extends BufferedReader {

	@SuppressWarnings("unused")
	private JTextArea textArea;
	public CustomBufferedReader(Reader in, JTextArea textArea) {
		super(in);
		this.textArea = textArea;
	
	}
}