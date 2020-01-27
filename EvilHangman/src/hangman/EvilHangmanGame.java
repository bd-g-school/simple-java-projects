package hangman;

import java.io.File;
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
        guessesMade = null;
        fullSetOfWords = new HashSet<String>();
        remainingWords = new HashSet<String>();
        guessesMade = new TreeSet<Character>();
        this.wordLength = wordLength;

        Scanner reader = new Scanner(dictionary);
        if (!reader.hasNext()){
            throw new EmptyDictionaryException("The dictionary provided is empty");
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
            throw new EmptyDictionaryException("The dictionary provided has no words of the specified length");
        }
        remainingWords.addAll(fullSetOfWords);
    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        char Guess = Character.toLowerCase(guess);
        Set<Set<String>> remainingWordsGrouped = new HashSet<>();

        if (guessesMade.contains(Guess)) {
            throw new GuessAlreadyMadeException("You've already guessed that letter");
        }
        else {
            guessesMade.add(Guess);
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
                    tmp.add(next);
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

        Set<String> bestCurrent = new HashSet<String>();

        while (itrGrp.hasNext()){
            Set<String> currentGroup = itrGrp.next();
            bestCurrent = getBetterSet(currentGroup, bestCurrent, Guess);
        }

        remainingWords = bestCurrent;
        return remainingWords;
    }

    private Set<String> getBetterSet(Set<String> set1, Set<String> set2, char guess){
        Iterator<String> itr1 = set1.iterator();
        Iterator<String> itr2 = set2.iterator();
        String string1, string2;
        Boolean[] guessed1 = new Boolean[wordLength];
        Boolean[] guessed2 = new Boolean[wordLength];
        int numCharsMatch1 = 0;
        int numCharsMatch2 = 0;

        if (set1.size() != set2.size()){
            return (set1.size() > set2.size()) ? set1 : set2;
        }

        string1 = itr1.next();
        string2 = itr2.next();
        for(int i = 0; i < string1.length(); i++){
            if (string1.charAt(i) == guess){
                guessed1[i] = true;
                numCharsMatch1++;
            }
            else {
                guessed1[i] = false;
            }
        }

        for(int i = 0; i < string2.length(); i++){
            if (string2.charAt(i) == guess){
                guessed2[i] = true;
                numCharsMatch2++;
            }
            else {
                guessed2[i] = false;
            }
        }

        // Choose the group in which the letter does not appear at all.
        if (numCharsMatch1 == 0 || numCharsMatch2 == 0){
            return (numCharsMatch1 == 0) ? set1 : set2;
        }
        // If each group has the guessed letter, choose the one with the fewest letters.
        else if (numCharsMatch1 > numCharsMatch2 || numCharsMatch2 > numCharsMatch1){
            return (numCharsMatch1 > numCharsMatch2) ? set2: set1;
        }
        else {
            for(int i = wordLength - 1; i > -1; i--){
                if (guessed1[i] != guessed2[i]){
                    return (guessed1[i]) ? set1: set2;
                }
            }
        }
        return set1;
    }

    @Override
    public SortedSet<Character> getGuessedLetters() {
        return guessesMade;
    }
}
