package net.katagaitai.phpscan.compiler;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.Value;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@ToString
@AllArgsConstructor
public class PhpProject {

	//	絶対関数名：「ファイル名+引数の数」リスト
	private Map<String, List<FunctionInfo>> functionMap = Maps.newHashMap();
	//	絶対クラス名：ファイル名リスト
	private Map<String, List<String>> classMap = Maps.newHashMap();
	//	メソッド名：「ファイル名＋絶対クラス名+引数の数」リスト
	private Map<String, List<MethodInfo>> methodMap = Maps.newHashMap();
	//	静的メソッド名：「ファイル名＋絶対クラス名+引数の数」リスト
	private Map<String, List<MethodInfo>> staticMethodMap = Maps.newHashMap();
	//	絶対定数名：ファイル名リスト
	private Map<String, List<String>> constantMap = Maps.newHashMap();
	//	絶対クラス名：絶対サブクラス名リスト
	private Map<String, List<String>> subClassMap = Maps.newHashMap();

	public PhpProject() {
	}

	@Value
	public static class FunctionInfo {
		private String filepath;
		private int minArgumentSize;
		private int maxArgumentSize;
	}

	@Value
	public static class MethodInfo {
		private String filepath;
		private String className;
		private int minArgumentSize;
		private int maxArgumentSize;
	}

	public List<FunctionInfo> getFunction(String name) {
		return functionMap.get(name.toLowerCase());
	}

	public List<String> getClass(String name) {
		return classMap.get(name.toLowerCase());
	}

	public List<String> getConstant(String name) {
		return constantMap.get(name);
	}

	public List<MethodInfo> getMethod(String name) {
		return methodMap.get(name.toLowerCase());
	}

	public List<MethodInfo> getStaticMethod(String name) {
		return staticMethodMap.get(name.toLowerCase());
	}

	public List<String> getClassNameList() {
		return Lists.newArrayList(classMap.keySet());
	}

	public List<String> getAllSubClassNameList(String name) {
		List<String> result = Lists.newArrayList();
		List<String> initList = subClassMap.get(name.toLowerCase());
		if (initList == null) {
			return result;
		}
		result.addAll(initList);
		for (int i = 0; i < result.size(); i++) {
			String cur = result.get(i);
			List<String> nextList = subClassMap.get(cur.toLowerCase());
			if (nextList == null) {
				continue;
			}
			for (String next : nextList) {
				if (result.contains(next)) {
					// pass
				} else {
					result.add(next);
				}
			}
		}
		return result;
	}

	public boolean isSuperClass(String superClassname, String classname) {
		List<String> subClassNameList = getAllSubClassNameList(superClassname);
		return subClassNameList != null && subClassNameList.contains(classname.toLowerCase());
	}
}
