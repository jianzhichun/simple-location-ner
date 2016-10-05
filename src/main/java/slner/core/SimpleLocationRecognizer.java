package slner.core;


import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import slner.entity.Location;
import slner.entity.Token;
import slner.entity.Location.Type;
import slner.trietree.Trietree;
import slner.trietree.impl.DefaultTrietree;
import slner.trietree.impl.TernarySearchTrie;

/**
 * SimpleLocationRecognizer
 * 
 * @author jianzhichun
 *
 */
public class SimpleLocationRecognizer {

	class Pair {
		String name;
		Location location;

		@Override
		public String toString() {
			return "Pair [name=" + name + ", location=" + location + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((location == null) ? 0 : location.hashCode());
			result = prime * result + ((name == null) ? 0 : name.hashCode());
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
			Pair other = (Pair) obj;
			if (location == null) {
				if (other.location != null)
					return false;
			} else if (!location.equals(other.location))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}

		public Pair(String name, Location location) {
			this.name = name;
			this.location = location;
		}

	}

	private final static SimpleLocationRecognizer INSTANCE = new SimpleLocationRecognizer();

	private SimpleLocationRecognizer() {
//		trietree = new LocationTrietreeBuilder().build(new DefaultTrietree<List<Location>>());
		trietree = new LocationTrietreeBuilder().build(new TernarySearchTrie<List<Location>>());
	}

	public static SimpleLocationRecognizer getInstance() {
		return INSTANCE;
	}

	Trietree<List<Location>> trietree;

	public List<Token<?>> getLocationsTokens(String str) {
		return trietree.MaximumMatching(str).parallelStream().filter(token -> {
			return !(token.getEntity() instanceof String) && null != token.getEntity();
		}).collect(Collectors.toList());
	}

	public Map<String, List<String>> recognizeLocationFormat(String str) {
		return recognizeLocation2TypeLocationSet(
				str).entrySet().stream()
						.collect(
								Collectors
										.groupingBy(
												entry -> entry.getKey()
														.name(),
												Collectors.mapping(
														entry -> StringUtils.join(entry.getValue().stream()
																.map(location -> location.getName())
																.collect(Collectors.toList()), "|"),
												Collectors.toList())));
	}

	public Map<Location.Type, Set<Location>> recognizeLocation2TypeLocationSet(String str) {
		Map<Type, List<Pair>> map = recognizeLocation2TypePairList(str);
		Map<Location.Type, Set<Location>> rs = new HashMap<>();
		List<Pair> areas = map.get(Type.area);
		List<Pair> citys = map.get(Type.city);
		List<Pair> provinces = map.get(Type.province);
		Set<Location> areasSet = new HashSet<>();
		final Set<Location> citysSet = new HashSet<>();
		Set<Location> provincesSet = new HashSet<>();
		// area
		if (null != areas && areas.size() > 0) {
			areasSet.addAll(areas.stream().map(p -> p.location).collect(Collectors.toSet()));
		}
		// city
		if (null != citys && citys.size() > 0) {
			citysSet.addAll(citys.stream().map(p -> p.location).collect(Collectors.toSet()));
		} else {
			if (null != areas && areas.size() > 0) {
				citysSet.addAll(areasSet.stream().map(p -> p.getBelong2()).collect(Collectors.toSet()));
			}
		}
		// provinces
		if (null != provinces && provinces.size() > 0) {
			provincesSet.addAll(provinces.stream().map(p -> p.location).collect(Collectors.toSet()));
		} else {
			if (null != citys && citys.size() > 0) {
				provincesSet.addAll(citysSet.stream().map(p -> p.getBelong2()).collect(Collectors.toSet()));
			}
		}
		rs.put(Location.Type.area, areasSet.stream().filter(
				area -> citysSet.contains(area.getBelong2()) && provincesSet.contains(area.getBelong2().getBelong2()))
				.collect(Collectors.toSet()));
		rs.put(Location.Type.city,
				citysSet.stream().filter(city -> provincesSet.contains(city.getBelong2())).collect(Collectors.toSet()));
		rs.put(Location.Type.province, provincesSet);
		return rs;
	}

	private Map<Type, List<Pair>> recognizeLocation2TypePairList(String str) {
		List<Token<?>> tokens = getLocationsTokens(str);
		Map<Type, List<Pair>> map = tokens.stream().flatMap(token -> {
			@SuppressWarnings("unchecked")
			List<Location> locations = (List<Location>) token.getEntity();
			return locations.stream().map(location -> location.getName().contains(token.getName())
					? new Pair(token.getName(), location) : null).collect(Collectors.toSet()).stream();
		}).collect(Collectors.groupingBy(t -> t.location.getType()));
		return map;
	}

	public static void main(String[] args) {
		
		SimpleLocationRecognizer simpleLocationRecognizer = SimpleLocationRecognizer.getInstance();
//		long a = System.currentTimeMillis();
//		System.out.println(simpleLocationRecognizer.recognizeLocationFormat("河北石家莊桥西区华北商贸城十七区二十二排四号"));
//		System.out.println(System.currentTimeMillis()-a);
//		TernarySearchTrie<List<Location>> tst = (TernarySearchTrie<List<Location>>)simpleLocationRecognizer.trietree;
//		System.out.println(tst.TreeHeight(tst.getRoot()));
		while(true){
			
		}
	}
	
}
