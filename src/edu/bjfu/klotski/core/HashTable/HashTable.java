package edu.bjfu.klotski.core.HashTable;

import edu.bjfu.klotski.core.Interfaces.IHashTable;

public class HashTable implements  IHashTable {

	private int HashTableCapacity = 66000;
	private Node[] _hashTable = new Node[HashTableCapacity];
	
	public HashTable()
	{
		for(int i=0; i<HashTableCapacity; i++)
			_hashTable[i] = new Node();
	}
	
	
	public void Release() {
		for(int i=0; i<HashTableCapacity; i++)
			_hashTable[i].link = null;
	}

	public boolean Insert(long layout) {
		int hashCode = HashTable.ToHashCode(layout);
		
		Node current = _hashTable[hashCode];

		while(current.link != null)
		{
			if(current.link.info == layout)
				return false;

			current = current.link;
		}

		Node newNode = new Node(layout);
		current.link = newNode;

		return true;
	}
	
	public static int ToHashCode(long layout)
	{
		long mask = 16383; // 二进制 11111111111111
		int hashCode = 0;

		for(int i = 0; i <= 5; i++)
			hashCode += (int)((layout & (mask << i * 14)) >> i * 14);

		return hashCode;
	}

}
