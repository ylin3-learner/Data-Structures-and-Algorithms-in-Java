public class TwoThreeTree {
    // Node class representing individual nodes in the 2-3 tree
    private class Node {
        int[] keys; // Array to store data values
        Node[] children; // Array to store references to child nodes
        int size; // Current number of keys stored in the node

        // Constructor
        public Node(int key) {
            keys = new int[2]; // Initialize keys array with size 2
            keys[0] = key; // Set the first key
            children = new Node[4]; // Initialize children array with size 4 for potential splits
            size = 1; // Initially only one key is present
        }

        // Method to check if the node is a leaf node
        public boolean isLeaf() {
            return children[0] == null; // A node is a leaf if its first child is null
        }

        // Method to check if the node is full (contains 2 keys)
        public boolean isFull() {
            return size == 2; // A node is full if it contains 2 keys
        }
    }

    private Node root; // Root node of the 2-3 tree

    // Method to search for a key in the 2-3 tree
    public boolean search(int key) {
        return search(root, key); // Start the search from the root
    }

    // Helper method for recursive search
    private boolean search(Node node, int key) {
        if (node == null) // If the tree is empty or the key is not found
            return false;
        else if (node.keys[0] == key || (node.isFull() && node.keys[1] == key))
            return true; // Key found in the current node
        else if (node.isLeaf())
            return false; // Key not found in the leaf node
        else if (key < node.keys[0])
            return search(node.children[0], key); // Explore left subtree
        else if (node.size == 1 || (node.isFull() && key < node.keys[1]))
            return search(node.children[1], key); // Explore middle subtree
        else
            return search(node.children[2], key); // Explore right subtree
    }

    // Method to insert a key into the 2-3 tree
    public void insert(int key) {
        root = insert(root, key); // Start the insertion from the root
    }

    // Helper method for recursive insertion
    private Node insert(Node node, int key) {
        if (node == null) // If the tree is empty, create a new node
            return new Node(key);
        else if (node.isLeaf() && !node.isFull()) { // If the node is a leaf and not full
            insertKey(node, key); // Insert the key into the node
            return node;
        } else if (node.isLeaf() && node.isFull()) { // If the node is a leaf and full
            return splitNode(node, key); // Split the node and return the new parent
        } else { // If the node is not a leaf, recursively insert into appropriate subtree
            if (key < node.keys[0]) {
                node.children[0] = insert(node.children[0], key); // Insert into left subtree
            } else if (node.size == 1 || (node.size == 2 && key < node.keys[1])) {
                node.children[1] = insert(node.children[1], key); // Insert into middle subtree
            } else {
                node.children[2] = insert(node.children[2], key); // Insert into right subtree
            }
            return node;
        }
    }

    // Method to insert a key into a node
    private void insertKey(Node node, int key) {
        if (key < node.keys[0]) { // If the key is smaller than the first key
            node.keys[1] = node.keys[0]; // Shift the first key to the second position
            node.keys[0] = key; // Insert the new key in the first position
        } else { // If the key is larger than the first key
            node.keys[1] = key; // Insert the new key in the second position
        }
        node.size++; // Increment the size of the node
    }

    // Method to split a full node
    private Node splitNode(Node node, int key) {
        int newParentKey;
        Node left, right;
        if (key < node.keys[0]) {
            newParentKey = node.keys[0];
            left = new Node(key);
            right = new Node(node.keys[1]);
        } else if (key < node.keys[1]) {
            newParentKey = key;
            left = new Node(node.keys[0]);
            right = new Node(node.keys[1]);
        } else {
            newParentKey = node.keys[1];
            left = new Node(node.keys[0]);
            right = new Node(key);
        }

        left.children[0] = node.children[0];
        left.children[1] = node.children[1];
        right.children[0] = node.children[2];
        right.children[1] = node.children[3];

        Node parent = new Node(newParentKey); // Create a new parent node
        parent.children[0] = left; // Set left child for parent node
        parent.children[1] = right; // Set right child for parent node
        return parent; // Return the new parent node
    }

    // Method to delete a key from the 2-3 tree
    public void delete(int key) {
        root = delete(root, key); // Start the deletion from the root
    }

    // Helper method for recursive deletion
    private Node delete(Node node, int key) {
        if (node == null) // If the tree is empty or the key is not found
            return null;
        else if (node.isLeaf()) { // If the node is a leaf
            if (node.keys[0] == key) {
                node.keys[0] = node.keys[1]; // Move the second key to the first position
                node.size--; // Decrease the size of the node
                return node.size == 0 ? null : node; // If the node becomes empty, return null
            } else if (node.size == 2 && node.keys[1] == key) {
                node.size--; // Decrease the size of the node
                return node;
            }
            return node; // Key not found in the leaf node
        } else { // If the node is not a leaf, recursively delete from appropriate subtree
            if (key < node.keys[0]) {
                node.children[0] = delete(node.children[0], key); // Delete from left subtree
            } else if (node.size == 1 || (node.size == 2 && key < node.keys[1])) {
                node.children[1] = delete(node.children[1], key); // Delete from middle subtree
            } else {
                node.children[2] = delete(node.children[2], key); // Delete from right subtree
            }
            return node;
        }
    }
}
