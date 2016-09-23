package slner.trietree.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import slner.entity.Token;
import slner.trietree.Trietree;
import slner.trietree.TrietreeBuilder;

/**
 * 
 * @author Administrator
 * 
 * @param <T>
 *            T: type about entity stored
 * @param <E>
 */
public class TrietreeImpl<T> implements Trietree<T> {

	class Node {
		char name;
		T entity;
		Map<Character, Node> children;

		public Node(char name, T type) {
			this.name = name;
			this.entity = type;
		}
	}

	Node root;

	public TrietreeImpl() {
		root = new Node('r', null);
	}
	@Override
	public void insert(String name, T entity) {
		Node node = this.root;
		char[] chars = name.toCharArray();
		for (char c : chars) {
			if (null == node.children)
				node.children = new HashMap<>();
			if (node.children.containsKey(c)) {
				node = node.children.get(c);
			} else {
				Node child = new Node(c, null);
				node.children.put(c, child);
				node = child;
			}
		}
		node.entity = entity;
	}

	Token<?> match(String name) {
		try {
			Node node = this.root;
			char[] chars = name.toCharArray();
			for (char c : chars) {
				if (node.children.containsKey(c))
					node = node.children.get(c);
				else
					return null;
			}
			return new Token<>(name, node.entity);
		} catch (Exception e) {
			return null;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.slner.trietree.impl.Trietree#MaximumMatching(java.lang.String)
	 */
	@Override
	public List<Token<?>> MaximumMatching(String text) {
		List<Token<?>> tokens = new ArrayList<>();
		int first = 0;
		int end = 1;
		char[] chars = text.toCharArray();
		int len = chars.length;
		while (len > first) {
			String temp = String.valueOf(Arrays.copyOfRange(chars, first, end > len ? len : end));
			Token<?> token_A = this.match(temp);
			if (null == token_A) {
				tokens.add(new Token<String>(temp, ""));
				first++;
				end = first + 1;
			}
			Token<?> token_B = len <= end ? null
					: this.match(String.valueOf(Arrays.copyOfRange(chars, first, end + 1 > len ? len : end + 1)));
			if (null != token_A && null == token_B) {
				tokens.add(token_A);
				first = end;
				end++;
			} else
				end++;
		}
		return tokens;
	}
	
	/* (non-Javadoc)
	 * @see com.slner.trietree.impl.Trietree#update(com.slner.trietree.TrietreeBuilder)
	 */
	@Override
	public Trietree<T> update(TrietreeBuilder<T> trietreeBuilder){
		return trietreeBuilder.build(this);
	}

}
