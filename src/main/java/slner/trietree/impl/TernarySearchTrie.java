package slner.trietree.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;

import slner.entity.Token;
import slner.trietree.Trietree;
import slner.trietree.TrietreeBuilder;

public class TernarySearchTrie<T> implements Trietree<T> {

	class Node {
		Node former;
		Node latter;
		Node next;
		Character c;
		T entity;

		public Node(char c) {
			this.c = c;
		}

		@Override
		public String toString() {
			return "(" + c + "," + former + "," + latter + "," + next + ")";
		}

	}

	private Node root;
	
	public Node getRoot() {
		return root;
	}

	@Override
	public List<Token<?>> MaximumMatching(String text) {
		List<Token<?>> tokens = new ArrayList<>();
		int first = 0;
		int end = 1;
		char[] chars = text.toCharArray();
		int len = chars.length;
		Token<?> token_A = null;
		Token<?> token_B = null;
		while (len > first) {
			String temp = String.valueOf(Arrays.copyOfRange(chars, first, end > len ? len : end));
			token_A = null == token_B ? this.match(temp) : token_B;
			if (null == token_A) {
				tokens.add(new Token<String>(temp, ""));
				end = ++first + 1;
				continue;
			}
			token_B = len <= end ? null
					: this.match(String.valueOf(Arrays.copyOfRange(chars, first, end + 1 > len ? len : end + 1)));
			if (null != token_A && null == token_B) {
				tokens.add(token_A);
				first = end++;
			} else{
				token_A = token_B;
				end++;
			}
		}
		return tokens;
	}

	@Override
	public Trietree<T> update(TrietreeBuilder<T> trietreeBuilder) {
		// TODO Auto-generated method stub
		return trietreeBuilder.build(this);
	}

	@Override
	public void insert(String name, T entity) {
		int i = 0;
		Node node = Optional.ofNullable(this.root).orElseGet(() -> this.root = new Node(name.charAt(0)));
		while (true) {
			int cmp = node.c.compareTo(name.charAt(i));
			final Node currentNode = node;
			final int j = i;
			if (0 == cmp)
				if (++i == name.length())
					break;
				else
					node = Optional.ofNullable(node.next)
							.orElseGet(() -> currentNode.next = new Node(name.charAt(j + 1)));
			else if (0 > cmp)
				node = Optional.ofNullable(node.former).orElseGet(() -> currentNode.former = new Node(name.charAt(j)));
			else
				node = Optional.ofNullable(node.latter).orElseGet(() -> currentNode.latter = new Node(name.charAt(j)));
		}

		node.entity = entity;
	}

	Token<?> match(String name) {
		try {
			Node node = this.root;
			int i = 0;
			while (true) {
				if (node == null)
					break;
				int cmp = node.c.compareTo(name.charAt(i));
				if (0 == cmp)
					if (++i == name.length())
						break;
					else
						node = node.next;
				else if (0 > cmp)
					node = node.former;
				else
					node = node.latter;
			}
			return new Token<>(name, node.entity);
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) {
		TernarySearchTrie<String> tst = new TernarySearchTrie<>();
		List<String> l = Lists.newArrayList("acb", "aa", "ab", "bc", "bcd");
		l.forEach(k -> tst.insert(k, k));
		System.out.println(tst.root);
		long t1 = System.currentTimeMillis();
		System.out.println(tst.MaximumMatching("bcdaaa"));
		System.out.println(System.currentTimeMillis() - t1);

	}
	public int TreeHeight(Node root)
	{
	    int f,l,n;
	    if( null == root )
	        return 0;
	    else
	    {
	        f = TreeHeight(root.former);
	        l = TreeHeight(root.latter);
	        n = TreeHeight(root.next);
	        return Lists.newArrayList(f,l,n).stream().max(Integer::compareTo).get()+1;
	    }
	} 

}
