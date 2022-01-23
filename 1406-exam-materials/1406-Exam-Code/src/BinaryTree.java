public class BinaryTree {
    private int data;
    private BinaryTree leftChild;
    private BinaryTree rightChild;

    // This constructor now uses sentinels for terminators instead of null
    public BinaryTree(int d) {
        data = d;
        leftChild = null;
        rightChild = null;
    }

    // This constructor is unchanged
    public BinaryTree(int d, BinaryTree left, BinaryTree right) {
        data = d;
        leftChild = left;
        rightChild = right;
    }

    // Get methods
    public int getData() {
        return data;
    }

    public BinaryTree getLeftChild() {
        return leftChild;
    }

    public BinaryTree getRightChild() {
        return rightChild;
    }

    // Set methods
    public void setData(int d) {
        data = d;
    }

    public void setLeftChild(BinaryTree left) {
        leftChild = left;
    }

    public void setRightChild(BinaryTree right) {
        rightChild = right;
    }

    /**
     * Returns true if calling BinaryTree is the same as the argument BinaryTree.
     * Two binary trees should be considered the same if they have the same node
     * structure (i.e., exact same shape) and each matching node has the same value.
     * In other words, the data stored in the two trees is identical.
     */
    public boolean sameAs(BinaryTree otherTreeRoot) {
        //if the data is the same, proceed
        if (data == otherTreeRoot.getData()) {
            //if one tree's child is null and the other isn't, return false
            if ((otherTreeRoot.getLeftChild() == null && leftChild != null)
                    || (otherTreeRoot.getLeftChild() != null && leftChild == null)
                    || (otherTreeRoot.getRightChild() == null && rightChild != null)
                    || (otherTreeRoot.getRightChild() != null && rightChild == null)) {
                return false;
            }
            //if both children are both null, return true
            if ((otherTreeRoot.getLeftChild() == null && leftChild == null)
                    || (otherTreeRoot.getRightChild() == null && rightChild == null)) {
                return true;
            }
            //if nothing was null, recurse
            if (leftChild.sameAs(otherTreeRoot.getLeftChild()) && rightChild.sameAs(otherTreeRoot.getRightChild())) {
                return true;
            }
        }
        return false;
    }

}
