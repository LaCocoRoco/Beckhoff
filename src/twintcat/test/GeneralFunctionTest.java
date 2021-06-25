package twintcat.test;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class GeneralFunctionTest {

	public static void main(String[] args) {
		new GeneralFunctionTest();
	}

	
	private List<String> dimensionToRange(List<Point> dimension, int index) {
		List<String> data = new ArrayList<>();
		Point range = dimension.get(index);
		String end = index == 0 ? "[" : "";
		for (int i = range.x; i <= range.y; i++) {
			if (dimension.size() > index + 1) {
				for (String r : dimensionToRange(dimension, index + 1))
					data.add(end + Integer.toString(i) + "," + r);
			} else {
				data.add(end + Integer.toString(i) + "]");
			}
		}
		
		return data;
	}

	private List<String> getTypeArraySymbolNameList(String name, String type) {
		String[] list = type.substring(type.indexOf("[") + 1, type.indexOf("]")).split(",");
		List<Point> dimension = new ArrayList<Point>();
		for (String index : list) {
			String[] size = index.replace(" ", "").split("\\..");
			int x = Integer.valueOf(size[0]);
			int y = Integer.valueOf(size[1]);
			dimension.add(new Point(x, y));
		}

		List<String> range = dimensionToRange(dimension, 0);
		for (int i = 0; i < range.size(); i++) {
			String value = range.get(i);
			range.set(i, name + value);
		}

		return range;	
	}
		
	public GeneralFunctionTest() {
		String name = "MAIN.VALUE";
					//:ARRAY[1..4, 0..30, 10..40] OF STRING;
		String type = "ARRAY [0..5] OF INT";

		List<String> test = getTypeArraySymbolNameList(name, type);
			
		for (String str : test) {
			System.out.println(str);
		}

	}
}
