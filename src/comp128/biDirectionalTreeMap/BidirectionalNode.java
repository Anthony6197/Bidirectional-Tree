package comp128.biDirectionalTreeMap;

/**
 * @author Yutong Wu, with great help from Yifan Liu
 * Class to use as nodes and entries in bidirectinal map
 */
public class BidirectionalNode<K, V> {

    /**
     * The key (or value) stored in the entry of the node.
     */
    public K key;

    /**
     * Parent node of the node.
     */
    public BidirectionalNode parent;
    /**
     * Left child of the node.
     */
    public BidirectionalNode<K, V> leftChild;
    /**
     * Right child of the node.
     */
    public BidirectionalNode<K, V> rightChild;


    /**
     * Linkage between the key nodes and value nodes, bidirectionally
     */
    public BidirectionalNode<V, K> linkage;

    /**
     * Construct a node with given key and no children, parent or link.
     *
     * @param key The key to store in this node
     */
    public BidirectionalNode(K key) {
        this.key = key;
    }

    /**
     * Retrieve a string representing the node.
     * @return A string representation of its data
     */
    public String toString() {
        return key.toString();
    }
}
