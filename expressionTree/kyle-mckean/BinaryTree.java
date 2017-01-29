/**
 * The <tt> BinaryTree </tt> class is a simple binary tree class where
 * the only method on the tree is a postOrder traversal.
 **/
import java.util.*;
public class BinaryTree<E> {
    private E data;
    private BinaryTree<E> leftChild;
    private BinaryTree<E> rightChild;
    public BinaryTree(E data) {
        this.data = data;
    }
    public BinaryTree(E data, BinaryTree<E> leftChild, BinaryTree<E> rightChild) {
        this.data = data;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }
    public <L extends List<E>> void postOrder(L list) {
        if (this.leftChild != null)
            this.leftChild.postOrder(list);
        if (this.rightChild != null)
            this.rightChild.postOrder(list);
        list.add(this.data);
    }
}
