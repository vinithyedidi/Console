package console;

/** 
 * Author: Vinith Yedidi
 * Version: 1.0
 * Date: 6/2/2020
 */

public class OSException extends Exception {
	String message;
	public OSException(String message) {
		super(message);
	}
}
