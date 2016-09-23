package slner.trietree;

import java.util.List;

import slner.entity.Token;

public interface Trietree<T> {

	/**
	 * MaximumMatching
	 * @param text
	 * @return
	 */
	List<Token<?>> MaximumMatching(String text);

	/**
	 * update trietree
	 * @param trietreeBuilder
	 * @return
	 */
	Trietree<T> update(TrietreeBuilder<T> trietreeBuilder);
	/**
	 * insert entity
	 * @param name
	 * @param entity
	 */
	void insert(String name, T entity);

}