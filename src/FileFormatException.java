/**
 * custom exception thrown when the input.txt file does not match the expected format/pattern:
 * num subscribers (n)
 * subscriber name (1)...
 * ...subscriber name (n)
 * num meals (m)
 * meal name (1)
 * meal number available (1)...
 * ...meal name (m)
 * meal number available (m)
 */
public class FileFormatException extends Exception{
    public FileFormatException(String message){
        super(message);
    }
}
