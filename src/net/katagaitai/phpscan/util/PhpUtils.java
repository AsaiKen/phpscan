package net.katagaitai.phpscan.util;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Lists;

public class PhpUtils {
	public static String getAbsolute(String name, String namespace, Map<String, String> useMap) {
		if (name == null) {
			return null;
		}
		String[] elements = name.split("\\\\");
		List<String> list = Lists.newArrayList();
		if (elements.length > 0) {
			// 先頭がエイリアスと一致すれば絶対名前空間に置換
			for (Entry<String, String> entry : useMap.entrySet()) {
				if (elements[0].equals(entry.getValue())) {
					list.add(entry.getKey());
					break;
				}
			}
			// 一致しなければそのまま
			if (list.size() == 0) {
				list.add(elements[0]);
			}
			// 残り
			for (int i = 1; i < elements.length; i++) {
				list.add(elements[i]);
			}
		}
		String absolute = String.join("\\", list);
		if (absolute.startsWith("\\")) {
			// OK
		} else if (namespace.equals("\\")) {
			absolute = "\\" + absolute;
		} else {
			// まだ相対のままである場合は現在の名前空間名を使う
			absolute = namespace + "\\" + absolute;
		}
		return absolute;
	}
}
