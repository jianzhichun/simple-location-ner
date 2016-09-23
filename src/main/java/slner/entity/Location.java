package slner.entity;
/**
 * 
 * @author jianzhichun
 *
 */
public class Location {
	public enum Type{
		province,city,area,
	}
	private Type type;
	private String name;
	private Location belong2;
	public Type getType() {
		return type;
	}
	public void setType(String type) {
		this.type = Type.valueOf(type);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Location getBelong2() {
		return belong2;
	}
	public void setBelong2(Location belong2) {
		this.belong2 = belong2;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((belong2 == null) ? 0 : belong2.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (belong2 == null) {
			if (other.belong2 != null)
				return false;
		} else if (!belong2.equals(other.belong2))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Location [type=" + type + ", name=" + name + ", belong2=" + belong2 + "]";
	}
	public static void main(String[] args) {
		Type a = Type.valueOf("province");
		System.out.println(a.equals(Type.province));
	}
}
