package types;

/** 
 * Defines the protocols for a hash-based map.
 * <p>
 * Do not modify the protocols.
 */
public interface HashMap<K, V> extends Map<K, V> {
	/**
	 * Returns the load factor (alpha), defined
	 * to be the number of entries in the map
	 * divided by either the length of the array
	 * or page (when open addressing) or the
	 * number of chains (when separate chaining).
	 * 
	 * @return the load factor
	 */
	double loadFactor();
}