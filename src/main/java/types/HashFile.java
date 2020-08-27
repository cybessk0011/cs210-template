package types;

import java.nio.file.Path;
import java.util.Iterator;

/** 
 * Implements a hash-based map
 * using a random access file structure.
 */
public class HashFile<K, V> implements HashMap<K, V> {
	/*
	 * TODO: For Module 7, implement the stubs.
	 * 
	 * Until then, this class is unused.
	 */
	public HashFile(Path path, Entry<K, V> descriptor) {
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public V put(K key, V value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V get(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V remove(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean contains(K key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double loadFactor() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Iterator<Entry<K, V>> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
}
