package edu.bjfu.klotski.core.HashTable;

public class Node {
	
	public long info = 0;
	public Node link = null;

	public Node()
	{
	}

	public Node(long layout)
	{
		this.info = layout;
	}
}
