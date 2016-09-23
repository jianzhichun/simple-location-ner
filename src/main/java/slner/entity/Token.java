package slner.entity;
/**
 * 
 * @author jianzhichun
 *
 * @param <T>
 */
public class Token<T> {
	private String name;
	private T entity;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public T getEntity() {
		return entity;
	}
	public void setEntity(T entity) {
		this.entity = entity;
	}
	public Token(String name, T entity) {
		this.name = name;
		this.entity = entity;
	}
	@Override
	public String toString() {
		return "Token [name=" + name + ", entity=" + entity + "]";
	}
	
}
