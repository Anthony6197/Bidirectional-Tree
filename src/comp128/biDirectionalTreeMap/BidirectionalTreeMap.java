package comp128.biDirectionalTreeMap;

/**
 * A treemap storing both the keys and values in BSTs with nodes bidirectionally linking keys and values.
 * This makes searching for both a key or a value O(logn).
 * The map does not allow duplicate keys OR values.
 * @param <K>
 * @param <V>
 */
public class BidirectionalTreeMap<K extends Comparable<K>, V extends Comparable<V> > {

    //TODO: add additional instance variables

    protected int size;

    /**
     * constructor initializes the tree with size 0
     */
    public BidirectionalTreeMap() {
        size = 0;
    }

    /**
     * Adds the key and value association to the map. The key is stored in the BST with root keyRoot.
     * The value is stored in a separate BST with root valueRoot. Both node objects contain links to each other.
     * @param key
     * @param value
     * @return true if the key/value pair was inserted. If the key or the value already exist in the map, it is not modified and a value of false is returned.
     */
    public boolean put(K key, V value){
        //TODO: complete me

        return false;
    }

    /**
     * Returns the value associated with a key in O(logn) time.
     * @param key
     * @return value corresponding to key or null if the key does not exist in the map
     */
    public V getValue(K key){
        //TODO: complete me

        return null;
    }

    /**
     * Returns the key associated with a value in O(logn) time. A standard TreeMap would take O(n) time!
     * @param value
     * @return key or null if the value does not exist in the map
     */
    public K getKey(V value){
       //TODO: complete me

        return null;
    }


    /**
     * Remove the key and corresponding value from the map (and both trees)
     * @param key
     * @return value that was removed that corresponds to key or null if the key does not exist in the map.
     */
    public V remove(K key){
        //TODO: complete me

        return null;
    }

    /**
     * An inorder traversal of the map ordered by the keys
     * @return a string representing the inorder traversal of the map ordered by keys in the form:
     *         "(apple, 3), (banana, 5), (carrot, 4), (date, 6), (eggplant, 1), (fig, 2)"
     */
    public String inOrderTraverseByKeys(){
        //TODO: complete me
        return "";
    }

    /**
     * An inorder traversal of the map ordered by values
     * @return a string representing the inorder traversal of the map ordered by values in the form:
     *         "(eggplant, 1), (fig, 2), (apple, 3), (carrot, 4), (banana, 5), (date, 6)"
     */
    public String inOrderTraverseByValues() {
        //TODO: complete me
        return "";
    }

    /**
     * Checks if the key exists in the map
     * @param key
     * @return true if key was found
     */
    public boolean containsKey(K key){
        return getValue(key) != null;
    }

    /**
     * Checks if the value exists in the map
     * @param value
     * @return true if found
     */
    public boolean containsValue(V value) {
        return getKey(value) != null;
    }

    /**
     * @return the number of key/value associations contained in the map
     */
    public int size() {
        return size;
    }
}
