package comp128.biDirectionalTreeMap;

/**
 * @author Yutong Wu, with great help from Yifan Liu
 * Bidirectional treeMap stores keys and values in two subtrees and links them bidirectionally.
 * The bidirectioanal linkage between them makes the searching time complexity for keys and values O(log n),
 * The key value pairs do not tolerate duplicate of each side, unlike the HashMap
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 */
public class BidirectionalTreeMap<K extends Comparable<K>, V extends Comparable<V>> {
    /**
     * The root of the subtree that stores the keys
     */
    private BidirectionalNode kRoot;
    /**
     * The root of the subtree that stores the values
     */
    private BidirectionalNode vRoot;

    /**
     * The number of key-value pairs of the bidirectional tree
     */
    protected int size;

    /**
     * Constructs a new, empty tree map, using the natural ordering of its
     * keys like TreeMap class.
     */
    public BidirectionalTreeMap() {
        kRoot = null;
        vRoot = null;
        size = 0;
    }


    /**
     * Returns {@code true} if this map contains a mapping for the specified
     * key.
     *
     * @param key key whose presence in this map is to be tested
     * @return {@code true} if this map contains a mapping for the
     *         specified key
     */
    public boolean containsKey(K key){
        return getValue(key) != null;
    }

    /**
     * Returns {@code true} if this map contains a mapping for the specified
     * key.
     * @param value the value which we check to see if it exists in the subtree that stores values
     * @return true if the value is found found
     */
    public boolean containsValue(V value) {
        return getKey(value) != null;
    }

    /**
     * @return the number of key-value pairs contained in the map
     */
    public int size() {
        return size;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old
     * value is replaced.
     *
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     *
     * @return the previous value associated with {@code key}, or
     *         {@code null} if there was no mapping for {@code key}.
     *         (A {@code null} return can also indicate that the map
     *         previously associated {@code null} with {@code key}.)

     */
    public boolean put(K key, V value) {
        /**
         * Check if there is duplicate of key-value pair exists in the treeMap
         */
        if (containsKey(key) | containsValue(value)){
            return false;
        }  //Increase the number of key-value pairs by one
            size ++;
            //Create new bidirectional nodes for values and keys and assign corresponding elements
            BidirectionalNode<K, V> keyNode = new BidirectionalNode<>(key);
            BidirectionalNode<V, K> valueNode = new BidirectionalNode<>(value);
            kRoot = add(kRoot, null, keyNode);
            vRoot = add(vRoot, null, valueNode);

            //Create the bidirectional linkage between the key nodes and value nodes
            keyNode.linkage = valueNode;
            valueNode.linkage = keyNode;
            return true;
    }

    /**
     * Recursive add method.
     *
     * @param localRoot The localRoot of the subtree
     * @param parent  The parent node of the inserted nodes
     * @param newNode The node to be inserted
     * @return The new local localRoot containing the
     * inserted node
     */
    private BidirectionalNode add(BidirectionalNode localRoot, BidirectionalNode parent, BidirectionalNode<? extends Comparable, ?> newNode) {
        if (localRoot == null) {
            // Check if the key is contained in the tree
            newNode.parent = parent;
            return newNode;
        }
        if (newNode.key.compareTo(localRoot.key) > 0) {
            // Check if the key of newNode is greater than the key of the localRoot
            localRoot.rightChild = add(localRoot.rightChild, localRoot, newNode);
            return localRoot;
        }
        // Check if the key of newNode is less than the key of the localRoot
        localRoot.leftChild = add(localRoot.leftChild, localRoot, newNode);
        return localRoot;
    }

    /**
     * Retrieve the value associated with the key.
     *
     * @return the value corresponding to the given key
     */
    public V getValue(K key) {
        BidirectionalNode<K, V> localNode = new BidirectionalNode<>(key);
        BidirectionalNode<K, V> result = (BidirectionalNode<K, V>) find(kRoot, localNode);
        if (result != null)
            return result.linkage.key;
        return null;
    }

    /**
     * Retrieve the key.
     *
     * @return the key of the given value
     */
    public K getKey(V value) {
        BidirectionalNode<V, K> localNode = new BidirectionalNode<>(value);
        BidirectionalNode<V, K> result = (BidirectionalNode<V, K>) find(vRoot, localNode);
        if (result != null)
            return result.linkage.key;
        return null;
    }

    /**
     * Recursive find method.
     *
     * @param localRoot   The localRoot of the current subtree
     * @param targetNode
     * @return The targetNode bidirectional node
     * @post Return the targetNode bidirectional node,
     * and return null if not found,
     */
    private BidirectionalNode<? extends Comparable, ?> find(BidirectionalNode<? extends Comparable, ?>  localRoot,
                                                            BidirectionalNode<? extends Comparable, ?> targetNode) {
        if (localRoot == null)
            return null;

        // Compare the targetNode with the data field at the localRoot.
        int compResult = targetNode.key.compareTo(localRoot.key);
        if (compResult == 0){
            return localRoot;
        }
        else if (compResult < 0){
            return find(localRoot.leftChild, targetNode);
        }else{
            return find(localRoot.rightChild, targetNode);
        }
    }

    /**
     * Removes the mapping for this targetKey from this TreeMap if present.
     *
     * @param  targetKey targetKey for which mapping should be removed
     * @return the previous targetKey associated with {@code value}, or
     *         {@code null} if there was no mapping for {@code value}.
     *         (A {@code null} return can also indicate that the map
     *         previously associated {@code null} with {@code value}.)
     */
    public V remove(K targetKey) {
        BidirectionalNode<K, V> localNode = new BidirectionalNode<>(targetKey);
        BidirectionalNode<K, V> keyRemove = new BidirectionalNode<>(null);
        BidirectionalNode<V, K> valueRemove = new BidirectionalNode<>(null);
        if (containsKey(targetKey)) {
            //Decrease the size of targetKey-value pairs
            size --;
            //Recursively find the node that has the targetKey and delete it
            kRoot = delete(kRoot, localNode, keyRemove);
            vRoot = delete(vRoot, keyRemove.linkage, valueRemove);
            return valueRemove.key;
        }
        return null;
    }


    /**
     * Removes all of the mappings from this map.
     * The map will be empty after this call returns.
     */
    public void clear() {
        size = 0;
        kRoot = null;
        vRoot = null;
    }

    /**
     * Acknowledge Great help from Yifan (Albert) Liu
     * Delete BidirectionalNode p, and then re-balance the tree.
     * @param localRoot The localRoot of the current subtree
     * @param p The p to be deleted
     * @param deleteReturn Return value, value is assigned based on
     * different situation based of P
     * @return The modified local localRoot that does not contain
     * the p to be deleted
     * @post The p is not in the tree;
     * deleteReturn is equal to the deleted p
     * as it was stored in the tree or null
     * if the p was not found.
     */
    private BidirectionalNode delete(BidirectionalNode localRoot, BidirectionalNode<? extends Comparable, ?> p, BidirectionalNode deleteReturn){

        deleteReturn.key = localRoot.key;
        deleteReturn.linkage = localRoot.linkage;

        int compResult = p.key.compareTo(localRoot.key);
        if (compResult < 0) {
            // check if the key of p is less than localRoot.key.
            localRoot.leftChild = delete(localRoot.leftChild, p, deleteReturn);
            if (localRoot.leftChild != null)
                localRoot.leftChild.parent = localRoot;
            return localRoot;
        }
        if (compResult > 0) {
            // check if the key of p is larger than localRoot.key..
            localRoot.rightChild = delete(localRoot.rightChild, p, deleteReturn);
            if (localRoot.rightChild != null)
                localRoot.rightChild.parent = localRoot;
            return localRoot;
        }

        if (localRoot.leftChild == null){
            // If the localRoot has no left child, return its right child
            return localRoot.rightChild;
        }
        if (localRoot.rightChild == null){
            // If the localRoot has no right child, return its left child
            return localRoot.leftChild;
        }
        // else replace the data with inorder predecessor.
        if (localRoot.leftChild.rightChild == null) {
            // If the left child has no right child,
            // Replace the key of the localRoot with the key of its
            // left child.
            localRoot.key = localRoot.leftChild.key;

            // Replace the left child of localRoot with its left child.
            localRoot.leftChild = localRoot.leftChild.leftChild;
            localRoot.leftChild.parent = localRoot;
            return localRoot;
        }
        // Replace the deleted node p with the inorder predecessor .
        localRoot.key = findLargestChild(localRoot.leftChild).key;
        return localRoot;
    }

    /**
     * Find the inorder processor and replace it with its left child.
     * @param parent The parent node of inorder predecessor
     * @return The data contained in inorder predecessor
     * @post The inorder predecessor is removed from the tree.
     */
    private BidirectionalNode findLargestChild(BidirectionalNode parent) {
        if (parent.rightChild.rightChild == null) {
            BidirectionalNode returnValue = parent.rightChild;
            parent.rightChild = parent.rightChild.leftChild;
            if (parent.rightChild != null)
                parent.rightChild.parent = parent;
            return returnValue;
        }
        return findLargestChild(parent.rightChild);
    }

    /**
     * An inorder traversal of the map ordered by the keys
     *
     * @return a string representing the inorder traversal of the map ordered by keys in the form:
     * "(apple, 3), (banana, 5), (carrot, 4), (date, 6), (eggplant, 1), (fig, 2)"
     */
    public String inOrderTraverseByKeys() {
        StringBuilder sb = new StringBuilder();
        inOrderTraverse(kRoot, true, sb);
        return sb.toString();
    }

    /**
     * Traverse the TreeMap using inorder traversal ordered by values
     *
     * @return a string representing the inorder traversal of the map ordered by values :
     * "(eggplant, 1), (fig, 2), (apple, 3), (carrot, 4), (banana, 5), (date, 6)"
     */
    public String inOrderTraverseByValues() {
        StringBuilder sb = new StringBuilder();
        inOrderTraverse(vRoot, false, sb);
        return sb.toString();
    }

    /**
     *  Acknowledge Great help from Yifan (Albert) Liu
     * Traverse the TreeMap using inorder traversal.
     *
     * @param node The local root
     * @param sb   The string buffer to save the output
     */
    private void inOrderTraverse(BidirectionalNode node, boolean isKey, StringBuilder sb) {
        if (node != null) {
            inOrderTraverse(node.leftChild, isKey, sb);
            if (sb.length()!= 0){
                sb.append(", (");
            }
            else{
                sb.append("(");
            }

            if (isKey) {
                sb.append(node.toString());
                sb.append(", ");
                sb.append(node.linkage.toString());
            }
            else {
                sb.append(node.linkage.toString());
                sb.append(", ");
                sb.append(node.toString());
            }
            sb.append(")");
            inOrderTraverse(node.rightChild, isKey, sb);
        }
    }
}
