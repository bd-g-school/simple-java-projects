package spell;

public class Trie implements ITrie {
    public Trie(){
        root = new Node();
        numWords = 0;
        numNodes = 1;
    }

    private Node root;
    private int numWords;
    private int numNodes;

    @Override
    public void add(String word) {
       Node next = root;
        for(int i = 0; i < word.length(); i++) {
            next = next.node(word.charAt(i));
            if (next.isNewNode()){
                numNodes++;
            }
        }
        if (next.incrementCount()){
            numWords++;
            next.setString(word.toLowerCase());
        };
    }

    @Override
    public INode find(String word) {
        Node next = root;
        for(int i = 0; i < word.length(); i++) {
            next = next.exists(word.charAt(i));
            if (next == null) {
                return null;
            }
        }
        return (next.getValue() > 0) ? next : null;
    }

    @Override
    public String toString(){
        StringBuilder myTrie = new StringBuilder();
        StringBuilder currentString = new StringBuilder(50);
        root.toString(myTrie, currentString);
        return myTrie.toString();
    }

    @Override
    public boolean equals(Object o){
        if (o == this){
            return true;
        }
        if (!(o instanceof Trie)){
            return false;
        }

        Trie other = (Trie) o;
        return root.NodeEquals(other.root);
    }

    @Override
    public int hashCode(){
        int hash = 7;
        hash *=  23 * numWords;
        hash *=  23 * numNodes;
        hash *= root.makeHash();
        return hash;
    }

    @Override
    public int getWordCount() {
        return numWords;
    }

    @Override
    public int getNodeCount() {
        return numNodes;
    }
}
