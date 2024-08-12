// Java program to implement the
// Compressed Trie

class CompressedTrie {

    class Node {

        // Number of symbols
        private final static int SYMBOLS = 26;

        // 声明了一个Node类型的数组next，存储节点的子节点。
        Node[] next = new Node[SYMBOLS];

        // 声明了一个StringBuilder类型的数组edgeLabel，存储节点到子节点的边的标签。
        StringBuilder[] edgeLabel = new StringBuilder[SYMBOLS];
        boolean isWord;

        // Function to check if the end
        // of the string is reached
        public Node(boolean isWord)
        {
            this.isWord = isWord;
        }
        
        public Node() {
            this(false);
        }
    }

    // Root Node
    private final Node root = new Node();

    // 'a' for lower, 'A' for upper
    private final char CASE;

    // Constructor accepting the
    // starting symbol
    public CompressedTrie(char CASE)
    {
        this.CASE = CASE;
    }

    // Default case
    public CompressedTrie() { this('a'); }

    // Function to insert a word in
    // the compressed trie
    public void insert(String word)
    {
        // Store the root
        Node cur = root;
        int i = 0;

        // Iterate i less than word
        // length
        // 循环直到遇到单词的结尾或者当前节点的子节点为空
        while (i < word.length()
                && cur.edgeLabel[word.charAt(i) - CASE]
                != null) {

            // Find the index
            // 计算当前字符的索引，j初始化为0。
            int index = word.charAt(i) - CASE, j = 0;
            // 获取当前边的标签。
            StringBuilder label = cur.edgeLabel[index];

            // Iterate till j is less
            // than label length
            // 遍历直到标签结束或者单词结束，且当前标签字符与单词字符相同。
            while (j < label.length() && i < word.length()
                    && label.charAt(j) == word.charAt(i)) {
                ++i;
                ++j;
            }

            // If is the same as the
            // label length
            // 如果遍历到标签结束，转到下一个节点。
            if (j == label.length()) {
                cur = cur.next[index];
            }
            else {

                // Inserting a prefix of
                // the existing word
                // 如果遍历到单词结束。
                if (i == word.length()) {

                    // 获取当前节点的子节点。
                    Node existingChild
                            = cur.next[index];
                    // 创建一个新节点表示结束。
                    Node newChild = new Node(true);
                    // 复制剩余的标签。
                    StringBuilder remainingLabel
                            = strCopy(label, j);

                    // Making "facebook"
                    // as "face"
                    // 更新当前节点的标签。
                    label.setLength(j);

                    // New node for "face"
                    cur.next[index] = newChild;
                    // 新节点的子节点指向原来的子节点。
                    newChild.next[remainingLabel.charAt(0)
                            - CASE]
                            = existingChild;
                    // 新节点的边标签设置为剩余的标签。
                    newChild.edgeLabel[remainingLabel.charAt(0)
                            - CASE]
                            = remainingLabel;
                }
                else {

                    // Inserting word which has
                    // a partial match with
                    // existing word
                    StringBuilder remainingLabel
                            = strCopy(label, j);

                    Node newChild = new Node(false);
                    StringBuilder remainingWord
                            = strCopy(word, i);

                    // Store the cur in
                    // temp node
                    Node temp = cur.next[index];

                    label.setLength(j);
                    cur.next[index] = newChild;
                    newChild.edgeLabel[remainingLabel.charAt(0)
                            - CASE]
                            = remainingLabel;
                    newChild.next[remainingLabel.charAt(0)
                            - CASE]
                            = temp;
                    newChild.edgeLabel[remainingWord.charAt(0)
                            - CASE]
                            = remainingWord;
                    newChild.next[remainingWord.charAt(0)
                            - CASE]
                            = new Node(true);
                }

                return;
            }
        }

        // Insert new node for new word
        if (i < word.length()) {
            cur.edgeLabel[word.charAt(i) - CASE]
                    = strCopy(word, i);
            cur.next[word.charAt(i) - CASE]
                    = new Node(true);
        }
        else {

            // Insert "there" when "therein"
            // and "thereafter" are existing
            cur.isWord = true;
        }
    }

    // Function that creates new String
    // from an existing string starting
    // from the given index
    private StringBuilder strCopy(
            CharSequence str, int index)
    {
        StringBuilder result
                = new StringBuilder(100);

        while (index != str.length()) {
            result.append(str.charAt(index++));
        }

        return result;
    }

    // Function to print the Trie
    public void print()
    {
        printUtil(root, new StringBuilder());
    }

    // Function to print the word
    // starting from the given node
    private void printUtil(
            Node node, StringBuilder str)
    {
        if (node.isWord) {
            System.out.println(str);
        }

        for (int i = 0;
             i < node.edgeLabel.length; ++i) {

            // If edgeLabel is not
            // NULL
            if (node.edgeLabel[i] != null) {
                int length = str.length();

                str = str.append(node.edgeLabel[i]);
                printUtil(node.next[i], str);
                str = str.delete(length, str.length());
            }
        }
    }

    // Function to search a word
    public boolean search(String word)
    {
        int i = 0;

        // Stores the root
        Node cur = root;

        while (i < word.length()
                && cur.edgeLabel[word.charAt(i) - CASE]
                != null) {
            int index = word.charAt(i) - CASE;
            StringBuilder label = cur.edgeLabel[index];
            int j = 0;

            while (i < word.length()
                    && j < label.length()) {

                // Character mismatch
                if (word.charAt(i) != label.charAt(j)) {
                    return false;
                }

                ++i;
                ++j;
            }

            if (j == label.length() && i <= word.length()) {

                // curerse further
                cur = cur.next[index];
            }
            else {

                // Edge label is larger
                // than target word
                // searching for "face"
                // when tree has "facebook"
                return false;
            }
        }

        // Target word fully curersed
        // and current node is word
        return i == word.length() && cur.isWord;
    }

    // Function to search the prefix
    public boolean startsWith(String prefix)
    {
        int i = 0;

        // Stores the root
        Node cur = root;

        while (i < prefix.length()
                && cur.edgeLabel[prefix.charAt(i) - CASE]
                != null) {
            int index = prefix.charAt(i) - CASE;
            StringBuilder label = cur.edgeLabel[index];
            int j = 0;

            while (i < prefix.length()
                    && j < label.length()) {

                // Character mismatch
                if (prefix.charAt(i) != label.charAt(j)) {
                    return false;
                }

                ++i;
                ++j;
            }

            if (j == label.length()
                    && i <= prefix.length()) {

                // curerse further
                cur = cur.next[index];
            }
            else {

                // Edge label is larger
                // than target word,
                // which is fine
                return true;
            }
        }

        return i == prefix.length();
    }

    public static void main(String[] args) {
        CompressedTrie trie = new CompressedTrie();

        // Insert words
        trie.insert("facebook");
        trie.insert("face");
        trie.insert("this");
        trie.insert("there");
        trie.insert("then");

        // Print inserted words
        trie.print();

        // Check if these words
        // are present or not
        System.out.println(trie.search("there"));
        System.out.println(trie.search("therein"));
        System.out.println(trie.startsWith("th"));
        System.out.println(trie.startsWith("fab"));
    }
}


