import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.ArrayDeque;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Liubov Nikolenko
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> implements AVLInterface<T> {

    // Do not make any new instance variables.
    private AVLNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty AVL tree.
     */
    public AVL() {
        size = 0;
        root = null;
    }
    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
        this();
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        for (T element: data) {
            if (element == null) {
                throw new IllegalArgumentException("Data is null");
            }
            add(element);
        }
    }
    /**
    * Calculates the balance factor of a given node
    * @param node the balance factor of which is to be calculated
    * @return balance factor of the node
    */
    private int balanceFactor(AVLNode<T> node) {
        if (node == null) {
            return 0;
        }
        AVLNode<T> left = node.getLeft();
        AVLNode<T> right = node.getRight();
        int l = -1;
        int r = -1;
        if (left != null) {
            l = left.getHeight();
        }
        if (right != null) {
            r = right.getHeight();
        }
        int answer = l - r;
        return answer;
    }
    /**
    *Modyfies the given node by adding a new data item in the appropriate place.
    *If data is not in the tree, the duplicate is not added.
    * @throws java.lang.IllegalArgumentException if the data is null
    * @param node node to be modified
    * @param data the data to be added
    * @return modified node
    */
    private AVLNode<T> insert(AVLNode<T> node, T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        if (node == null) {
            size += 1;
            AVLNode<T> newNode = new AVLNode<>(data);
            newNode.setHeight(0);
            newNode.setBalanceFactor(0);
            return newNode;
        } else {
            if (data.compareTo(node.getData()) < 0) {
                node.setLeft(insert(node.getLeft(), data));

            }
            if (data.compareTo(node.getData()) > 0) {
                node.setRight(insert(node.getRight(), data));
            }

        }
        node.setHeight(efficientHeight(node));
        node.setBalanceFactor(balanceFactor(node));
        node = balance(node);
        return node;

    }

    @Override
    public void add(T data) {
        root = insert(root, data);
    }
    /**
    * Removes a node containing certain data from the tree
    * @throws java.lang.IllegalArgumentException if data is null
    * @throws java.util.NoSuchElementException if the data is not in the tree.
    * @param node current node in the recursive step
    * @param data data to be removed
    * @return node after the data is removed
    */
    private AVLNode<T> remove(AVLNode<T> node, T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        if (node == null) {
            String s = "Element " + data + " was not found";
            throw new java.util.NoSuchElementException(s);
        }
        if (data.compareTo(node.getData()) < 0) {
            //System.out.println("going left");
            node.setLeft(remove(node.getLeft(), data));
        }
        if (data.compareTo(node.getData()) > 0) {
            //System.out.println("going right");
            node.setRight(remove(node.getRight(), data));
        }
        if (node.getData().equals(data)) {
            if (isLeaf(node)) {
                return null;
            }
            if (hasOneChild(node)) {
                //System.out.println("one child");
                if (node.getLeft() != null) {
                    return node.getLeft();
                }
                return node.getRight();
            } else {
                node.setLeft(removePredecessor(node.getLeft(), node));
            }
        }
        node.setHeight(efficientHeight(node));
        node.setBalanceFactor(balanceFactor(node));
        node = balance(node);
        return node;
    }
    /**
    * Removes predecessor of the parent node
    * @param node node, starting from which the method searches for the
    * predecessor
    * @param parent node the predecessor of which is to be removed
    * @return left child of the node with the removed predecessor
    */
    private AVLNode<T> removePredecessor(AVLNode<T> node, AVLNode<T> parent) {
        T d = null;
        if (node.getRight() == null) {
            d = node.getData();
            parent.setData(d);
            if (isLeaf(node)) {
                return null;
            }
            return node.getLeft();
        } else {
            node.setRight(removePredecessor(node.getRight(), parent));
        }
        node.setHeight(efficientHeight(node));
        node.setBalanceFactor(balanceFactor(node));
        node = balance(node);
        return node;
    }
    @Override
    public T remove(T data) {
        T ans = get(root, data);
        if (ans == null) {
            String s = "Element " + data + " was not found";
            throw new java.util.NoSuchElementException(s);
        }
        root = remove(root, data);
        size -= 1;
        return ans;
    }
    /**
    * Returns the node that contains the data item in question
    * @throws java.lang.IllegalArgumentException if the data is null
    * @param node node starting from which the item will be searched
    * @param data the data in question
    * @return the node that contains the given data or null if the data was not
    * found
    */
    private T get(AVLNode<T> node, T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        T result = null;
        if (node == null) {
            result = null;
        } else {
            if (node.getData().equals(data)) {
                //System.out.println("found it");
                result = node.getData();
            }
            if (data.compareTo(node.getData()) < 0) {
                //System.out.println("going left");
                node = node.getLeft();
                result = get(node, data);

            }
            if (data.compareTo(node.getData()) > 0) {
                //System.out.println("going right");
                node = node.getRight();
                result = get(node, data);
            }
        }
        return result;

    }

    @Override
    public T get(T data) {
        T result = get(root, data);
        if (result == null) {
            String s = "Element " + data + " was not found";
            throw new java.util.NoSuchElementException(s);
        }
        return result;
    }

    @Override
    public boolean contains(T data) {
        T result = get(root, data);
        return result != null;
    }

    @Override
    public int size() {
        return size;
    }
    /**
    * Stores the elements in the given ArrayList in preorder
    * @param node the node from which the processing of data is started
    * @param list list to put in data
    * @return list with items stored in preorder
    */
    private List<T> preorder(AVLNode<T> node, ArrayList<T> list) {
        if (node != null) {
            list.add(node.getData());
            preorder(node.getLeft(), list);
            preorder(node.getRight(), list);
        }
        return list;
    }
    @Override
    public List<T> preorder() {
        ArrayList<T> list = new ArrayList<>();
        return preorder(root, list);
    }
    /**
    * Stores the elements in the given ArrayList in postorder
    * @param node the node from which the processing of data is started
    * @param list list to put in data
    * @return list with items stored in postorder
    */
    private List<T> postorder(AVLNode<T> node, ArrayList<T> list) {
        if (node != null) {
            postorder(node.getLeft(), list);
            postorder(node.getRight(), list);
            list.add(node.getData());
        }
        return list;
    }
    @Override
    public List<T> postorder() {
        ArrayList<T> list = new ArrayList<>(size);
        return postorder(root, list);
    }
    /**
    * Stores the elements in the given ArrayList in inorder
    * @param node the node from which the processing of data is started
    * @param list list to put in data
    * @return list with items stored in inorder
    */
    private List<T> inorder(AVLNode<T> node, ArrayList<T> list) {
        if (node != null) {
            inorder(node.getLeft(), list);
            list.add(node.getData());
            inorder(node.getRight(), list);
        }
        return list;
    }
    @Override
    public List<T> inorder() {
        ArrayList<T> list = new ArrayList<>(size);
        return inorder(root, list);
    }

    @Override
    public List<T> levelorder() {
        ArrayList<T> list = new ArrayList<>(size);
        ArrayDeque<AVLNode<T>> queue = new ArrayDeque<>(size);
        if (root == null) {
            return list;
        }
        queue.add(root);
        while (!queue.isEmpty()) {
            AVLNode<T> node = queue.poll();
            if (node != null) {
                //System.out.println(queue.poll());
                T data = node.getData();
                list.add(data);
            }
            if (node.getLeft() != null) {
                queue.add(node.getLeft());
            }
            if (node.getRight() != null) {
                queue.add(node.getRight());
            }
        }
        return list;
    }
    /**
    * Calculates the height of the given node
    * @param node that needs its height to be calculated
    * @return height of the node
    */
    private int efficientHeight(AVLNode<T> node) {
        if (node == null) {
            return -1;
        }
        if (isLeaf(node)) {
            return 0;
        }
        int r = -1;
        int l = -1;
        AVLNode<T> right = node.getRight();
        if (right != null) {
            r = right.getHeight();
        }
        AVLNode<T> left = node.getLeft();
        if (left != null) {
            l = left.getHeight();
        }

        int res = Math.max(r, l) + 1;
        return res;

    }
    /**
    * Balances the given node
    * @param node to be balanced
    * @return balanced node
    */
    private AVLNode<T> balance(AVLNode<T> node) {
        if (node.getBalanceFactor() > 1) {
            if (node.getLeft().getBalanceFactor() >= 0) {
                return rotateRight(node);
            } else {
                return leftRightRotation(node);
            }
        }
        if (node.getBalanceFactor() < -1) {
            if (node.getRight().getBalanceFactor() <= 0) {
                return rotateLeft(node);
            } else {
                return rightLeftRotation(node);
            }
        }
        return node;
    }
    /**
    * Peforms right rotation of the node
    * @param node to be rotated
    * @return rotated node
    */
    private AVLNode<T> rotateRight(AVLNode<T> node) {
        AVLNode<T> left1 = node.getLeft();
        AVLNode<T> left2 = left1.getLeft();
        AVLNode<T> child = left1.getRight();
        AVLNode<T> result = null;
        result = left1;
        left1.setLeft(left2);
        left1.setRight(node);
        node.setLeft(child);
        node.setHeight(efficientHeight(node));
        left1.setHeight(efficientHeight(left1));
        node.setBalanceFactor(balanceFactor(node));
        left1.setBalanceFactor(balanceFactor(left1));

        return result;
    }
    /**
    * Peforms left rotation of the node
    * @param node to be rotated
    * @return rotated node
    */
    private AVLNode<T> rotateLeft(AVLNode<T> node) {
        //node.setHeight(height(node));
        AVLNode<T> right1 = node.getRight();
        AVLNode<T> right2 = right1.getRight();
        //right1.setHeight(height(right1));
        AVLNode<T> child = right1.getLeft();
        //right2.setHeight(height(right2));
        AVLNode<T> result = null;
        result = right1;
        right1.setLeft(node);
        node.setRight(child);
        node.setHeight(efficientHeight(node));
        right1.setHeight(efficientHeight(right1));
        node.setBalanceFactor(balanceFactor(node));
        right1.setBalanceFactor(balanceFactor(right1));
        return result;
    }
    /**
    * Peforms left-right rotation of the node
    * @param node to be rotated
    * @return rotated node
    */
    private AVLNode<T> leftRightRotation(AVLNode<T> node) {
        AVLNode<T> left = node.getLeft();
        AVLNode<T> right = left.getRight();
        AVLNode<T> leftChild = right.getLeft();
        node.setLeft(right);
        right.setLeft(left);
        left.setRight(leftChild);
        left.setHeight(efficientHeight(left));
        right.setHeight(efficientHeight(right));
        node.setHeight(efficientHeight(node));
        node.setBalanceFactor(balanceFactor(node));
        left.setBalanceFactor(balanceFactor(left));
        right.setBalanceFactor(balanceFactor(right));
        return rotateRight(node);
    }
    /**
    * Peforms right-left rotation of the node
    * @param node to be rotated
    * @return rotated node
    */
    private AVLNode<T> rightLeftRotation(AVLNode<T> node) {
        AVLNode<T> right = node.getRight();
        AVLNode<T> left = right.getLeft();
        AVLNode<T> rightChild = left.getRight();
        node.setRight(left);
        left.setRight(right);
        right.setLeft(rightChild);
        right.setHeight(efficientHeight(right));
        left.setHeight(efficientHeight(left));
        node.setHeight(efficientHeight(node));
        node.setBalanceFactor(balanceFactor(node));
        left.setBalanceFactor(balanceFactor(left));
        right.setBalanceFactor(balanceFactor(right));
        return rotateLeft(node);
    }

    @Override
    public void clear() {
        size = 0;
        root = null;
    }
    @Override
    public int height() {
        if (size == 0) {
            return -1;
        }

        return root.getHeight();
    }

    /**
    * Checks is the node has two children
    * @param node to be tested
    * @return true if the node has two children, false otherwise
    */
    private boolean hasTwoChildren(AVLNode<T> node) {
        return (node.getLeft() != null && node.getRight() != null);
    }

    /**
    * Checks is the node has one child
    * @param node to be tested
    * @return true if the node has one child, false otherwise
    */
    private boolean hasOneChild(AVLNode<T> node) {
        int c = 0;
        if (node.getLeft() == null) {
            c += 1;
        }
        if (node.getRight() == null) {
            c += 1;
        }
        return c == 1;
    }

    /**
    * Checks is the node has no children (is a leaf)
    * @param node to be tested
    * @return true if a leaf, false otherwise
    */
    private  boolean isLeaf(AVLNode<T> node) {
        return (node.getLeft() == null && node.getRight() == null);

    }

    @Override
    public int depth(T data) {
        return depth(root, data);
    }
    /**
    * Returns the depth of the node, containing the given data
    * @throws java.lang.IllegalArgumentException if data is null
    * @throws java.util.NoSuchElementException if the data is not in the tree.
    * @param data data to calculate the depth of
    * @param node current node in the recursive step
    * @return the depth of the node containing the data if it is in the tree
    */
    private int depth(AVLNode<T> node, T data) {
        if (node == null) {
            String s = "Element " + data + " was not found";
            throw new java.util.NoSuchElementException(s);
        }
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        int d = 1;
        if (data.equals(node.getData())) {
            return d;
        }
        if (data.compareTo(node.getData()) < 0) {
            return depth(node.getLeft(), data) + 1;

        }
        if (data.compareTo(node.getData()) > 0) {
            return depth(node.getRight(), data) + 1;
        }
        return d;

    }

    /**
     * THIS METHOD IS ONLY FOR TESTING PURPOSES.
     * DO NOT USE IT IN YOUR CODE
     * DO NOT CHANGE THIS METHOD
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        return root;
    }

}
