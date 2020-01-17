package spell;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Node implements INode {
    public Node() {
        count = 0;
        nodes = new Node[26];
    }

    private int count;
    private Node[] nodes;
    private String value;

    @Override
    public int getValue() {
        return count;
    }

    @Override
    public String toString() { return value; }


    public void setString(String value){ this.value = value; }


    public void toString(StringBuilder myTrie, List<Character> currentString, int level){
        if (count > 0){
            for(int i = 0; i < level; i++){
                myTrie.append(currentString.get(i));
            }
            myTrie.append("\n");
        }
        for(int i = 0; i < 26; i++){
            if (nodes[i] != null){
                if (currentString.size() < level + 1){
                    currentString.add((char)(i + 97));
                }
                else {
                    currentString.set(level, (char) (i + 97));
                }
                nodes[i].toString(myTrie, currentString, level + 1);
            }
        }
    }

    public boolean NodeEquals(Node o){
        if (this.count != o.count){
            return false;
        }

        for (int i = 0; i < 26; i++){
            if ((nodes[i] == null && o.nodes[i] != null) ||
                (nodes[i] != null && o.nodes[i] == null)){
                return false;
            }
            else if (nodes[i] != null && o.nodes[i] != null) {
                if (!nodes[i].NodeEquals(o.nodes[i])){
                    return false;
                };
            }
        }
        return true;
    }

    /*
    * Increments the count and returns a boolean stating whether or not this is the first instance of this word
    * */
    public boolean incrementCount(){
        count++;
        return (count <= 1);
    }

    public Node exists(char letter){
        int index = (int)Character.toLowerCase(letter) - 97;
        return nodes[index];
    }

    public Pair<Node, Integer> node(char letter){
        int index = (int)Character.toLowerCase(letter) - 97;
        int nodeAdded = 0;
        if (nodes[index] == null) {
            nodes[index] = new Node();
            nodeAdded++;
        }
        return new Pair(nodes[index], nodeAdded);
    }

    public int makeHash(){
        int hash = 1;
        for(int i = 0; i < 26; i++){
            if (nodes[i] != null){
                hash *= (i + 1);
            }
        }
        return hash;
    }
}
