package slner.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import slner.entity.Location;
import slner.trietree.Trietree;
import slner.trietree.TrietreeBuilder;
/**
 * LocationTrietreeBuilder
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
				if(strs.length==4){
					int belong2 = Integer.parseInt(strs[3]);
					Location belongTo = code_location.get(belong2);
					temp.setBelong2(belongTo);
				}
				code_location.put(code,temp);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Trietree<List<Location>> build(Trietree<List<Location>> trietree) {
		Map<String,List<Location>> locationByName = code_location.values().stream().collect(Collectors.groupingBy(Location::getName));
		locationByName.entrySet().stream().forEach(entry->{
			trietree.insert(entry.getKey(), entry.getValue());
		});
		return trietree;
	}
	

}
