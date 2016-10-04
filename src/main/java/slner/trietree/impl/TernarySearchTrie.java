package slner.trietree.impl;

import java.util.List;

import slner.entity.Token;
import slner.trietree.Trietree;
import slner.trietree.TrietreeBuilder;

public class TernarySearchTrie<T> implements Trietree<T> {
	
	class Node{
		Node left;
		Node right;
		Node center;
		T entity;
		public Node(T entity) {
			this.entity = entity;
		}
	}
	
	@Override
	public List<Token<?>> MaximumMatching(String text) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Trietree<T> update(TrietreeBuilder<T> trietreeBuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(String name, T entity) {
		// TODO Auto-generated method stub
		
	}

}
