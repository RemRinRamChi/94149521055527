package spelling.quiz;

/**
 * Exception to be thrown and caught when user does not input the custom word list of correct format
 * @author YaoJian
 *
 */
public class InvalidWordListException extends Exception {
	  public InvalidWordListException() { super(); }
	  public InvalidWordListException(String message) { super(message); }
	  public InvalidWordListException(String message, Throwable cause) { super(message, cause); }
	  public InvalidWordListException(Throwable cause) { super(cause); }
}