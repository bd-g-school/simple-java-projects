package hangman;

import java.io.File;

public class EvilHangman {
    private static EvilHangmanGame _game;

    public static void main(String[] args) {
        String dictionary = args[0];
        int length = Integer.parseInt(args[1]);
        int guesses = Integer.parseInt(args[2]);
        _game = new EvilHangmanGame();

        try {
            _game.startGame(new File(dictionary), length);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

//
//        You have 10 guesses left
//        Used letters:
//        Word: -----
//                Enter guess: a
//        Sorry, there are no a's

    }

}
