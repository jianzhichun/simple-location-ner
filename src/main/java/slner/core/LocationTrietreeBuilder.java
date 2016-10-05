package slner.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import junit.textui.TestRunner;
import slner.entity.Location;
import slner.trietree.Trietree;
import slner.trietree.TrietreeBuilder;
import slner.trietree.impl.DefaultTrietree;
import slner.trietree.impl.TernarySearchTrie;

/**
 * LocationTrietreeBuilder
 * 
 * @author jianzhichun
 *
 */
public class LocationTrietreeBuilder implements TrietreeBuilder<List<Location>> {
	static Map<Integer, Location> code_location;

	static {
		code_location = new HashMap<>();
		try (FileInputStream fis = new FileInputStream(new File("./src/main/resources/location.dat"));
				InputStreamReader isr = new InputStreamReader(fis);
				BufferedReader br = new BufferedReader(isr)) {
			String line = null;
			while ((line = br.readLine()) != null) {
				// TODO
				String[] strs = line.split(",");
				int code = Integer.parseInt(strs[0]);
				String name = strs[1];
				String type = strs[2];
				Location temp = new Location();
				temp.setType(type);
				temp.setName(name);
				if (strs.length == 4) {
					int belong2 = Integer.parseInt(strs[3]);
					Location belongTo = code_location.get(belong2);
					temp.setBelong2(belongTo);
				}
				code_location.put(code, temp);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Trietree<List<Location>> build(Trietree<List<Location>> trietree) {
		if (trietree instanceof DefaultTrietree)
			return forDefaultTrietree(trietree);
		if (trietree instanceof TernarySearchTrie)
			return forTernarySearchTrie(trietree);
		return null;
	}

	private Trietree<List<Location>> forDefaultTrietree(Trietree<List<Location>> trietree) {
		Map<String, List<Location>> locationByName = code_location.values().stream()
				.collect(Collectors.groupingBy(Location::getName));
		locationByName.entrySet().stream().forEach(entry -> {
			trietree.insert(entry.getKey(), entry.getValue());
		});
		return trietree;
	}

	private Trietree<List<Location>> forTernarySearchTrie(Trietree<List<Location>> trietree) {
		Map<String, List<Location>> locationByName = code_location.values().stream()
				.collect(Collectors.groupingBy(Location::getName));
		List<Entry<String, List<Location>>> entryList = locationByName.entrySet().stream()
				.sorted((t, o) -> t.getKey().compareTo(o.getKey())).collect(Collectors.toList());
		perfectInsertTST(trietree, entryList, 0, entryList.size());
//		 locationByName.entrySet().stream()
//		 .sorted((t, o) ->
//		 t.getKey().compareTo(o.getKey())).collect(Collectors.toList())
//		 .forEach(item -> trietree.insert(item.getKey(), item.getValue()));
		return trietree;
	}

	private void perfectInsertTST(Trietree<List<Location>> trietree, List<Entry<String, List<Location>>> entryList,
			int offset, int n) {
		int m;
		if (n < 1) {
			return;
		}
		m = n >> 1;

		Entry<String, List<Location>> item = entryList.get(m + offset);
		trietree.insert(item.getKey(), item.getValue());

		perfectInsertTST(trietree, entryList, offset, m);
		perfectInsertTST(trietree, entryList, offset + m + 1, n - m - 1);

	}

}
