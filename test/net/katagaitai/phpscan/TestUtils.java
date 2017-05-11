package net.katagaitai.phpscan;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;


public class TestUtils {

	public static String sort(String str) {
		List<String> list = Lists.newArrayList(str.split(""));
		Collections.sort(list);
		return String.join("", list);
	}

}
