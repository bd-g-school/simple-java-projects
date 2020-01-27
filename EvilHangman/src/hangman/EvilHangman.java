package hangman;

import java.io.File;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class EvilHangman {
    private static EvilHangmanGame _game;

    public static void main(String[] args) {
        String dictionary = args[0];
        int length = Integer.parseInt(args[1]);
        int guesses = Integer.parseInt(args[2]);
        int charsGuessed = 0;
        StringBuilder currentGuess = new StringBuilder();
        for(int i = 0; i < length; i++){
            currentGuess.append('-');
        }
        _game = new EvilHangmanGame();


        try {
            _game.startGame(new File(dictionary), length);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return;
        }

        Iterator<Character> guessesMadeItr = null;
        String newMatchTemplate = null;
        char guess = '\0';
        int newCharsGuessed = 0;
        Scanner in = new Scanner(System.in);

        while (guesses > 0 && charsGuessed != length){
            System.out.println("You have " + guesses + " guesses left");

            System.out.print("Used letters: ");
            guessesMadeItr = _game.getGuessedLetters().iterator();
            while(guessesMadeItr.hasNext()) {
                System.out.print(guessesMadeItr.next() + " ");
            }

            System.out.println("\nWord: " + currentGuess.toString());

            System.out.println("Enter guess: ");
            guess = Character.toLowerCase(in.next().charAt(0));

            try {
               newMatchTemplate =  _game.makeGuess(guess).iterator().next();
               newCharsGuessed = 0;
               for(int i = 0; i < length; i++){
                   if(newMatchTemplate.charAt(i) == guess){
                       currentGuess.setCharAt(i, guess);
                       charsGuessed++;
                       newCharsGuessed++;
                   }
               }
               guesses--;
               if (guesses == 0) break;

               if (newCharsGuessed == 0){
                   System.out.println("Sorry, there are no " + guess + "'s");
               }
               else if (newCharsGuessed == 1){
                   System.out.println("Yes, there is 1 " + guess);
               }
               else {
                   System.out.println("Yes, there are " + newCharsGuessed+ " " + guess + "'s");
               }
            }
            catch(GuessAlreadyMadeException e){
                System.out.println("You've already guessed '" + guess + "'");
            }
        }

        if(charsGuessed == length){
            System.out.println("You win!");
        }
        else {
            System.out.println("You lose!");
        }
        System.out.println("The word was: " + newMatchTemplate);
    }
}
