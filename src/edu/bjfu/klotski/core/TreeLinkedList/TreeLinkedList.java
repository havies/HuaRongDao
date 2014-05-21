package edu.bjfu.klotski.core.TreeLinkedList;

import java.util.Stack;

import edu.bjfu.klotski.core.BaseComponent.ChessStep;
import edu.bjfu.klotski.core.BaseComponent.MoveMethod;
import edu.bjfu.klotski.core.BaseComponent.Position;
import edu.bjfu.klotski.core.Interfaces.ITreeList;

public class TreeLinkedList implements ITreeList {
	protected TreeNode root = null;
	protected TreeNode current = null;
	protected TreeNode end = null;

	protected int count;
	
	
	public void ClearAll() {
		// TODO Auto-generated method stub
		root = null;
		current = null;
		end = null;
		count = 0;
	}

	
	public void insertNode(ChessStep c) {
		// TODO Auto-generated method stub
		TreeNode newNode;
	    
		newNode = new TreeNode();
		newNode.chessStep = c;
		newNode.link = null;
		newNode.parentNode = null;

		if(root == null)
		{   
			root = newNode;
			current = newNode;
			end = current;
		}
		else
		{
			newNode.parentNode = current;
			end.link = newNode;
			end = newNode;
			end.link = newNode;
			end = newNode;
		}
		count++;
	}

	
	public ChessStep[] TraceResult(ChessStep chessStep) {
		// TODO Auto-generated method stub
		Stack<ChessStep> stack = new Stack<ChessStep>();
		TreeNode nodeTracer = current;
		MoveMethod m1,m2;
		Position p1, p2;

		m1 = chessStep.moveMethod;
		p1 = chessStep.chessmanPosition;

		chessStep.moveMethod = MoveMethod.Nothingness;
		chessStep.chessmanPosition = new Position(-1, -1);
		stack.push(chessStep);

		while(nodeTracer != root)
		{
			m2 = nodeTracer.chessStep.moveMethod;
			p2 = nodeTracer.chessStep.chessmanPosition;

			nodeTracer.chessStep.moveMethod = m1;
			nodeTracer.chessStep.chessmanPosition = p1;
			stack.push(nodeTracer.chessStep);

			m1=m2;
			p1=p2;

			nodeTracer = nodeTracer.parentNode;
		}

		root.chessStep.moveMethod = m1;
		root.chessStep.chessmanPosition = p1;
		stack.push(root.chessStep);

		int n = stack.size();
		ChessStep[] chessSteps = new ChessStep[n];
		for(int i=0; i<n; i++)
			chessSteps[i] = (ChessStep)(stack.pop());

		return chessSteps;
	}

	
	public void MoveCurrentToNext() {
		// TODO Auto-generated method stub
		if(current != null && current.link != null)
			current = current.link;
	}

}
