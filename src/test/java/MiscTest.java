import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class MiscTest {
	@Test
	public void test() {
		Map<Object, Boolean> map = Maps.newHashMap();
		map.put(0L, true);
		Object key = ((Integer) 0).longValue();
		assertThat(map.get(key), is(true));
		assertThat(map.get(0), is(nullValue()));
	}

	@Test
	public void test2() {
		Map<Integer, Integer> map = Maps.newHashMap();
		map.put(0, 0);
		map.put(1, 1);
		map.put(2, 2);
		assertThat(map.size(), is(3));
		for (Iterator<Entry<Integer, Integer>> iterator = map.entrySet().iterator(); iterator.hasNext();) {
			Entry<Integer, Integer> entry = iterator.next();
			// イテレータをremoveするとオリジナルも消える
			iterator.remove();
		}
		assertThat(map.size(), is(0));
	}

	@Test
	public void test3() {
		Map<Integer, Integer> map = Maps.newLinkedHashMap();
		map.put(0, 0);
		map.put(1, 1);
		map.put(2, 2);
		map.put(1, 3);
		List<Integer> list = Lists.newArrayList(map.keySet());
		StringBuffer b = new StringBuffer();
		for (Integer i : list) {
			b.append(i);
		}
		assertThat(b.toString(), is("012"));
	}

	private static class A {
		public A() {
			System.out.println(this.getClass().getName());
		}
	}

	private static class B extends A {
	}

	@Test
	public void test4() {
		new B();
	}

	private static class C {
		public void test() {
			print();
		}

		public void print() {
			System.out.println("C");
		}
	}

	private static class D extends C {
		@Override
		public void test() {
			super.test();
		}

		@Override
		public void print() {
			System.out.println("D");
		}
	}

	@Test
	public void test6() {
		D d = new D();
		d.test(); // D
	}

	@Test
	public void test7() {
		System.out.println("/".substring(0, 0));// ""
		System.out.println("\\".substring(1));// ""
	}

	@Test
	public void test8() {
		System.out.println(Arrays.asList("a=b=c".split("=", 1)));
		System.out.println(Arrays.asList("a=b=c".split("=", 2)));
	}

	@Test
	public void test9() {
		Set<Integer> set = Sets.newHashSet(1, 2, 3, 4, 5);
		Set<Integer> set2 = Sets.newHashSet(2, 4);
		Set<Integer> set3 = Sets.newHashSet(2, 6);
		boolean b2 = set.containsAll(set2);
		boolean b3 = set.containsAll(set3);
		assertThat(b2, is(true));
		assertThat(b3, is(false));
	}

	@Test
	public void test10() {
		String[] strs = new String[] { "a", "b" };
		String[] strs2 = new String[] { "a", "c" };
		String[] strs3 = new String[] { "a", "b" };
		LinkedList<String[]> list = Lists.newLinkedList();
		list.push(strs2);
		list.push(strs3);
		assertThat(list.contains(strs), is(false));
		LinkedList<List<String>> list2 = Lists.newLinkedList();
		list2.push(Lists.newArrayList(strs2));
		list2.push(Lists.newArrayList(strs3));
		assertThat(list2.contains(Lists.newArrayList(strs)), is(true));
	}

	@Test
	public void test11() {
		boolean b = "/hoge/imsxeADSUXu".matches(".*/.*/\\w*e\\w*");
		assertThat(b, is(true));
	}

	@Test
	public void test12() {
		System.out.println(Lists.newArrayList("\\a\\b\\c".split("\\\\")));
	}

	@Test
	public void test13() {
		System.out.println("hoge".startsWith(""));
		System.out.println("hoge".startsWith("hoge"));
	}

	@Test
	public void test14() {
		Map<Object, Boolean> map = Maps.newHashMap();
		map.put(0L, true);
		Object key = ((Integer) 0).longValue();
		assertThat(map.get(0L), is(true));
		assertThat(map.get(key), is(true));
		assertThat(map.get(0), nullValue());
	}
}
