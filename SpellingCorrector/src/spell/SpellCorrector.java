package spell;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector {
    //EDIT THIS OUT FOR PASSOFF

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
        System.out.println(dictionary.toString());
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        Node bestCurrent = null;
        Node alternative = null;

        alternative = (Node) dictionary.find(inputWord);
        bestCurrent = compareSuggestions(bestCurrent, alternative);

        alternative = findDeletionInstance(inputWord);
        bestCurrent = compareSuggestions(bestCurrent, alternative);

        if (bestCurrent != null){
            return bestCurrent.toString();
        }
        return null;
    }

    public Node nextSimilar(String inputWord) {
        Node bestCurrent = null;
        Node alternative = null;

        alternative = (Node)dictionary.find(inputWord);
        bestCurrent = compareSuggestions(bestCurrent, alternative);

        alternative = findDeletionInstance(inputWord);
        bestCurrent = compareSuggestions(bestCurrent, alternative);

        return bestCurrent;
    }

    private Node findDeletionInstance(String inputWord){
        Node returnNode = null;
        Node replace = null;
        for (int i = 0; i < inputWord.length(); i++){
            StringBuilder tmp = new StringBuilder(inputWord);
            tmp.deleteCharAt(i);
            //System.out.println(tmp.toString());
            replace = (Node)dictionary.find(tmp.toString());
            returnNode = compareSuggestions(returnNode, replace);
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
