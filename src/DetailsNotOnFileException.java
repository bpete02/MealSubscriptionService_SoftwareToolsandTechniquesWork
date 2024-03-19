/**
 * custom exception class thrown when a user or meal is trying to be found that isn't stored by the program (ie hasn't been read from the input.txt file)
 */
public class DetailsNotOnFileException extends Exception{
    public DetailsNotOnFileException(String message){
        super(message);
    }
}
