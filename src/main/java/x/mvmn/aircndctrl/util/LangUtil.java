package x.mvmn.aircndctrl.util;

import java.util.HashMap;
import java.util.Map;

public class LangUtil {

	public static class MapBuilder<K, V> {
		protected HashMap<K, V> map = new HashMap<>();

		public MapBuilder<K, V> set(K k, V v) {
			map.put(k, v);
			return this;
		}

		public Map<K, V> build() {
			return map;
		}
	}

	public static <K, V> MapBuilder<K, V> mapBuilder(K k, V v) {
		return new MapBuilder<K, V>().set(k, v);
	}

	public static String[] arr(String... arg) {
		return arg;
	}

	public static Object[] arr(Object... arg) {
		return arg;
	}
}
