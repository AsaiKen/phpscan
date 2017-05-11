package net.katagaitai.phpscan.ast;

import java.util.Map;

import org.junit.Test;

import com.google.common.collect.Maps;

public class MiscTest {
	@Test
	public void test() {
		Map<Object, Boolean> map = Maps.newHashMap();
		map.put(0L, true);
		Object key = ((Integer) 0).longValue();
		System.out.println(map.get(key));
		System.out.println(map.get(0));
	}
}
