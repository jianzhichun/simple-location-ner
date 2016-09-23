package slner.trietree;
/**
 * 
 * @author jianzhichun
 *
 * @param <T>
 */
public interface TrietreeBuilder<T> {
	Trietree<T> build(Trietree<T> trietree);
}
