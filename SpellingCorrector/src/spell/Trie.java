package spell;

public class Trie implements ITrie {
    public class Node implements ITrie.INode {
        public Node() {
            count = 0;
            nodes = new Node[26];
            isNewNode = true;
        }

        private int count;
        private Trie.Node[] nodes;
        private String value;
        private boolean isNewNode;

        public boolean isNewNode(){
            return isNewNode;
        }

        @Override
        public int getValue() {
            return count;
        }

        @Override
        public String toString() { return value; }


        public void setString(String value){ this.value = value; }


        public void toString(StringBuilder myTrie, StringBuilder currentString){
            if (count > 0){
                myTrie.append(currentString.toString());
                myTrie.append("\n");
            }
            for(int i = 0; i < 26; i++){
                if (nodes[i] != null){
                    currentString.append((char)(i + 97));
                    nodes[i].toString(myTrie, currentString);
                    currentString.setLength(currentString.length() - 1);
                }
            }
        }

        public boolean NodeEquals(Trie.Node o){
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

        public Trie.Node exists(char letter){
            int index = (int)Character.toLowerCase(letter) - 97;
            return nodes[index];
        }

        public Trie.Node node(char letter){
            int index = (int)Character.toLowerCase(letter) - 97;
            int nodeAdded = 0;
            if (nodes[index] == null) {
                nodes[index] = new Trie.Node();
                nodeAdded++;
            }
            else{
                isNewNode = false;
            }
            return nodes[index];
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

    public Trie(){
        root = new Trie.Node();
        numWords = 0;
        numNodes = 1;
    }

    private Node root;
    private int numWords;
    private int numNodes;

    @Override
    public void add(String word) {
       Trie.Node next = root;
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
        Trie.Node next = root;
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
