package hangman;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class EvilHangmanGame implements IEvilHangmanGame {
    Set<String> fullSetOfWords;
    Set<String> remainingWords;
    SortedSet<Character> guessesMade;
    int wordLength;

    @Override
    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {
        fullSetOfWords = null;  // Reset the game
        remainingWords = null;
        fullSetOfWords = new HashSet<String>();
        remainingWords = new HashSet<String>();
        this.wordLength = wordLength;

        Scanner reader = new Scanner(dictionary);
        if (!reader.hasNext()){
            throw new EmptyDictionaryException();
        }
        while (reader.hasNext()){
            fullSetOfWords.add(reader.next());
        }

        Iterator<String> itr = fullSetOfWords.iterator();

        while (itr.hasNext()) {
            if (itr.next().length() != wordLength) {	// remove even elements
                itr.remove();
            }
        }
        if (fullSetOfWords.size() == 0){
            throw new EmptyDictionaryException();
        }
        remainingWords.addAll(fullSetOfWords);
    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        char Guess = Character.toLowerCase(guess);
        Set<Set<String>> remainingWordsGrouped = new HashSet<>();

        if (guessesMade.contains(Guess)){
            throw new GuessAlreadyMadeException();
        }

        Iterator<String> itr = remainingWords.iterator();

        boolean foundMatch = false;
        boolean addedToSet = false;
        boolean[] letterGuessed = new boolean[wordLength];
        String next = null;
        String potentialMatch = null;


        while (itr.hasNext()) {  // Sort words into groups, like if guess was E, E---, -E--, E-E-, etc
            addedToSet = false;
            next = itr.next();

            for(int i = 0; i < next.length(); i++){  // Check to see if word contains guessed letter and where
                if (next.charAt(i) == Guess){
                    letterGuessed[i] = true;
                }
                else {
                    letterGuessed[i] = false;
                }
            }

            Iterator<Set<String>> itrGrp = remainingWordsGrouped.iterator();

            while(itrGrp.hasNext()){      // Go through each of the groups and check if it also contains strings
                foundMatch = true;        // with the same number and position of the guessed char as the current word
                Set<String> tmp = itrGrp.next();
                potentialMatch = tmp.iterator().next();
                for(int i = 0; i < next.length(); i++){
                    if (!(potentialMatch.charAt(i) == Guess && letterGuessed[i]) &&
                            !(potentialMatch.charAt(i) != Guess && !letterGuessed[i])) {
                        foundMatch = false;
                        break;
                    }
                }
                if (foundMatch){
                    tmp.add(potentialMatch);
                    addedToSet = true;
                    break;
                }
            }

            if (!addedToSet){  // If no set exists with that amount of the guessed char, make one
                HashSet<String> newSet = new HashSet<String>();
                newSet.add(next);
                remainingWordsGrouped.add(newSet);
            }
        }

        Iterator<Set<String>> itrGrp = remainingWordsGrouped.iterator();

      


        return null;
    }

    @Override
    public SortedSet<Character> getGuessedLetters() {
        return guessesMade;
    }
}
