package spell;

import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector {

    public SpellCorrector(){
        dictionary = null;
    }

    private Trie dictionary;

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        Scanner scanner = new Scanner(new File(dictionaryFileName));
        dictionary = new Trie();
        while (scanner.hasNext()) {
            dictionary.add(scanner.next());
        }
    }

    @Override
    public String suggestSimilarWord(String inputWord) {

        Node bestCurrent = null;
        Node alternative = null;
        int length = inputWord.length();
        List<String> valuesToCheck = new ArrayList<String>((54 * length) + 25);

        alternative = (Node) dictionary.find(inputWord);
        if (alternative != null){
            return alternative.toString();
        }

        alternative = findDeletionInstance(inputWord, valuesToCheck, true);
        bestCurrent = compareSuggestions(bestCurrent, alternative);

        alternative = findTranspositionInstance(inputWord, valuesToCheck, true);
        bestCurrent = compareSuggestions(bestCurrent, alternative);

        alternative = findAlterationInstance(inputWord, valuesToCheck, true);
        bestCurrent = compareSuggestions(bestCurrent, alternative);

        alternative = findInsertionInstance(inputWord, valuesToCheck, true);
        bestCurrent = compareSuggestions(bestCurrent, alternative);

        if (bestCurrent != null){
            return bestCurrent.toString();
        }

        for(String oneEditFromInput: valuesToCheck){
            alternative = findDeletionInstance(oneEditFromInput, null, false);
            bestCurrent = compareSuggestions(bestCurrent, alternative);

            alternative = findTranspositionInstance(oneEditFromInput, null, false);
            bestCurrent = compareSuggestions(bestCurrent, alternative);

            alternative = findAlterationInstance(oneEditFromInput, null, false);
            bestCurrent = compareSuggestions(bestCurrent, alternative);

            alternative = findInsertionInstance(oneEditFromInput, null, false);
            bestCurrent = compareSuggestions(bestCurrent, alternative);
        }

        if (bestCurrent != null){
            return bestCurrent.toString();
        }

        return null;
    }

    private Node findDeletionInstance(String inputWord, List<String> valuesToCheck, boolean addToList){
        Node returnNode = null;
        Node replace = null;
        for (int i = 0; i < inputWord.length(); i++){
            StringBuilder tmp = new StringBuilder(inputWord);
            tmp.deleteCharAt(i);
            if (addToList) {
                valuesToCheck.add(tmp.toString());
            }
            replace = (Node) dictionary.find(tmp.toString());
            returnNode = compareSuggestions(returnNode, replace);
        }
        return returnNode;
    }

    private Node findTranspositionInstance(String inputWord, List<String> valuesToCheck, boolean addToList){
        Node returnNode = null;
        Node replace = null;
        for (int i = 0; i < inputWord.length() - 1; i++){
            StringBuilder tmp = new StringBuilder(inputWord);
            tmp.setCharAt(i, inputWord.charAt(i + 1));
            tmp.setCharAt(i + 1, inputWord.charAt(i));
            if (addToList) {
                valuesToCheck.add(tmp.toString());
            }
            replace = (Node) dictionary.find(tmp.toString());
            returnNode = compareSuggestions(returnNode, replace);
        }
        return returnNode;
    }

    private Node findAlterationInstance(String inputWord, List<String> valuesToCheck, boolean addToList){
        Node returnNode = null;
        Node replace = null;
        StringBuilder tmp = new StringBuilder();
        for (int i = 0; i < inputWord.length(); i++){
            for(int j = 0; j < 26; j++) {
                tmp.append(inputWord);
                tmp.setCharAt(i, (char)(j + 97));
                if (addToList) {
                    valuesToCheck.add(tmp.toString());
                }
                replace = (Node) dictionary.find(tmp.toString());
                returnNode = compareSuggestions(returnNode, replace);
                tmp.setLength(0);
            }
        }
        return returnNode;
    }

    private Node findInsertionInstance(String inputWord, List<String> valuesToCheck, boolean addToList){
        Node returnNode = null;
        Node replace = null;
        StringBuilder tmp = new StringBuilder();
        for (int i = 0; i < inputWord.length() + 1; i++){
            for(int j = 0; j < 26; j++) {
                tmp.append(inputWord);
                tmp.insert(i, (char)(j + 97));
                if (addToList) {
                    valuesToCheck.add(tmp.toString());
                }
                replace = (Node) dictionary.find(tmp.toString());
                returnNode = compareSuggestions(returnNode, replace);
                tmp.setLength(0);
            }
        }
        return returnNode;
    }

    private Node compareSuggestions(Node original, Node alternative){
        try {
            if (original == null) {
                return alternative;
            }
            else if (alternative == null) {
                return original;
            }
            else if (alternative.getValue() > original.getValue()) {
                return alternative;
            }
            else if (alternative.getValue() == original.getValue()) {
                if (alternative.toString().compareTo(original.toString()) < 0) {
                    return alternative;
                }
            }
            return original;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
