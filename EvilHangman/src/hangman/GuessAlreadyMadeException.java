package hangman;



public class GuessAlreadyMadeException extends Exception {
    public GuessAlreadyMadeException(String ErrorMessage){
        super(ErrorMessage);
    }
}
