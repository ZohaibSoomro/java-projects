package com.assignment2;

public class BinarySearchTree {
    private BinarySearchTree left, right;
    private int count;
    private char key=' ';

    public BinarySearchTree() {
    }
    public BinarySearchTree(char key) {
        this.key = key;
        count++;
    }
    //inserts new elements in the tree
    //if character's ascii is less than root's character's ascii then
    //inserts element in left subtree and if vice-versa then in right subtree
    public void insert(char k) {
        //if root has no any character yet
        if(key==' '){
            key=k;
            count++;
            return;
        }
        int keyAscii = getAscii(key);
        int insertingAscii = getAscii(k);
        if (insertingAscii == keyAscii) {
            incrementCounter();
        } else if (insertingAscii < keyAscii) {
            if (left != null) left.insert(k);
            else left = new BinarySearchTree(k);
        } else {
            if (right != null) right.insert(k);
            else right = new BinarySearchTree(k);
        }
    }

    /**
     * @param key : a character
     * @return equivalent ascii value of the input character
     */
    private int getAscii(char key) {
        return (int) Character.toLowerCase(key);
    }

    //method to print tree in all three (pre,in,post)order traversals
    public void print() {
        System.out.println("Preorder: " + preOrderTraversal());
        System.out.println("Inorder: " + inOrderTraversal());
        System.out.println("Postorder: " + postOrderTraversal());
    }

    //returns height for a given tree
    public int height(BinarySearchTree subtree) {
        if (subtree == null) return -1;
        if (subtree.left == null && subtree.right == null) return 0;
        int lefts = 0, rights = 0;
        if (subtree.left != null) lefts = 1 + height(subtree.left);
        if (subtree.right != null) rights = 1 + height(subtree.right);
        return Math.max(lefts, rights);
    }

    //searches for an element and returns its node if found otherwise returns null
    public BinarySearchTree search(char k) {
        if (getAscii(k) == getAscii(key)) return this;
        if (left != null && getAscii(k) < getAscii(key)) return left.search(k);
        if (right != null && getAscii(k) > getAscii(key)) return right.search(k);
        else return null;
    }

    //finds minimum element in given root tree
    private BinarySearchTree minElement(BinarySearchTree root) {
        return root.left == null ? root : minElement(root.left);
    }

    public BinarySearchTree remove(char k) {
        //if element does not exist just print and error
        if (search(k) == null) {
            System.err.println("Element '" + k + "' not found!");
            return this;
        }
        return remove(this, k);

    }

    private BinarySearchTree remove(BinarySearchTree root, char k) {

        if (getAscii(k) < getAscii(root.key)) {
            root.left = remove(root.left, k);
        } else if (getAscii(k) > getAscii(root.key)) {
            root.right = remove(root.right, k);
        }
        //if it is root node which is going to be deleted
        else {
            if (root.count > 1) {
                root.count--;
            }
            // if nodeToBeDeleted have both children
            else if (root.left != null && root.right != null) {
                // Finding minimum element from right
                BinarySearchTree minElement = minElement(root.right);
                // Replacing current node with minimum node from right subtree
                root.key = minElement.key;
                // Deleting minimum node from right now
                root.right = remove(root.right, minElement.key);
            }
            // if nodeToBeDeleted has only left child
            else if (root.left != null) {
                root = root.left;
            }
            // if nodeToBeDeleted has only right child
            else if (root.right != null) {
                root = root.right;
            }
            // if nodeToBeDeleted do not have child (Leaf node)
            else
                root = null;

        }
        return root;
    }

    //as in bst the element on very left position in the tree has the largest key
    public char findMin() {
        if (left == null) return key;
        return left.findMin();
    }

    //as in bst the element on very right position in the tree has the largest key
    public char findMax() {
        if (right == null) return key;
        return right.findMax();
    }

    //strategy
    //root left right
    private String preOrderTraversal() {
        String buf = key + "(" + count + ")";
        if (left != null) buf += ", " + left.preOrderTraversal();
        if (right != null) buf += ", " + right.preOrderTraversal();
        return buf;
    }

    //strategy
    //left root right
    private String inOrderTraversal() {
        String buf = "";
        if (left != null) buf += left.inOrderTraversal() + ", ";
        buf += key + "(" + count + ")";
        if (right != null) buf += ", " + right.inOrderTraversal();
        return buf;
    }

    //strategy
    //left right root
    private String postOrderTraversal() {
        String buf = "";
        if (left != null) buf += left.postOrderTraversal() + ", ";
        if (right != null) buf += right.postOrderTraversal() + ", ";
        buf += key + "(" + count + ")";
        return buf;
    }

    //increments counter for a character
    public void incrementCounter() {
        count++;
    }
}
