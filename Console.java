package console;

import java.io.*;
import java.util.concurrent.Semaphore;
import javax.swing.*;


/** 
 * Author: Vinith Yedidi
 * Version: 1.0
 * Date: 6/2/2020
 */
public class Console {

	//used for waiting for console input
	static Semaphore semaphore = new Semaphore(0);
	
	//determines operating system. This is used for interacting with the operating system.
	public static String getOS() throws OSException {
		if (System.getProperty("os.name").toLowerCase().contains("windows")) {
			return "windows";
		} else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
			return "mac";
		} else {
			throw new OSException("Unsupported operating system");
		}
	}
	
	// takes what the user inputs as a command and returns the total command that will be run.
	public static String[] takeInput(ConsoleGUI console) throws IOException, OSException {
		StringReader sr = new StringReader(console.textArea.getText());
		console.reader = new CustomBufferedReader(sr, console.textArea);
		String line = "";
		String input = "";
		while ((line = console.reader.readLine()) != null) {
			input = line;
		}
		
		String[] command;
		if (getOS().contentEquals("windows")) { 
			command = new String[] {"cmd.exe", "/c", input};
		} else if (getOS().contentEquals("mac")) {
			command = new String[] {input};
		} else {
			throw new OSException("Unsupported operating system");
		}
		return command;
	}
	
	// runs the command
	public static String runCommand(String[] command) {

		// in case user doesn't actually input a command but still presses a button.
		if (command[command.length - 1].contains("operable program or batch file." + 
				"")) {
			return "repeated command";
		}
		
		String output = null;
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command(command);
	    try {
	        Process process = processBuilder.start();
	        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	        String line;
	        while ((line = reader.readLine()) != null) {
	            output += line + "\n";
	        }
	        // in case the process wasn't run (command not valid)
	        if (process.exitValue() != 0) {
	        	return "not a valid command";
	        }
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return output;
	}

	public static void releaseSemaphore() {
		semaphore.release();
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		JFrame frame = null;
		JTextArea textArea = null;
		JButton button = null;
		CustomBufferedReader reader  = null;
	
		ConsoleGUI console = new ConsoleGUI (frame, textArea, button, reader);
		
		console.setUpGUI();
		console.setUpIO();
		
		// continues until user exits.
		boolean infiniteLoop = true;
		while (infiniteLoop) {
			semaphore.acquire();
			String[] command = null;
			try {
				command = takeInput(console);
			} catch (IOException | OSException e) {
				e.printStackTrace();
			}
			
			// user can type "exit" to close the program, or "clear" to clear the screen.
			if (command[command.length - 1].equals("exit")) {
				System.out.println("exiting...");
				System.exit(0);
			} else if (command[command.length - 1].equals("clear")) {
				console.textArea.setText(null);
			} else {
				String output = runCommand(command);
				// if output isn't valid, it issues an error.
				if (output == "not a valid command") {
					System.out.println("\n'" + command[command.length - 1] + "' is not recognized as an internal or external command,\r\n" + 
								   "operable program or batch file.");
				// if the user didn't input a command, it doesn't do anything.
				} else if (output == "repeated command") {
				// if the output isn't a String message but rather an action, it says "executed..." next to it.
				} else if (output == null) {
					System.out.println("\t\t executed...");
				// if the output is a String message, it prints it out.
				} else {
					System.out.println("\n" + output);
				}
			}
		}
    }
}
