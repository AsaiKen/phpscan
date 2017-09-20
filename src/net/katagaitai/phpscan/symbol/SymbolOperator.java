package net.katagaitai.phpscan.symbol;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import net.katagaitai.phpscan.command.CastType;
import net.katagaitai.phpscan.command.Command;
import net.katagaitai.phpscan.command.Operator;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.compiler.PhpClass;
import net.katagaitai.phpscan.compiler.PhpNewable;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.interpreter.InterpreterStorage;
import net.katagaitai.phpscan.interpreter.SourcePosition;
import net.katagaitai.phpscan.php.types.PhpAnyType;
import net.katagaitai.phpscan.php.types.PhpArray;
import net.katagaitai.phpscan.php.types.PhpArrayAny;
import net.katagaitai.phpscan.php.types.PhpBoolean;
import net.katagaitai.phpscan.php.types.PhpFloat;
import net.katagaitai.phpscan.php.types.PhpInteger;
import net.katagaitai.phpscan.php.types.PhpNull;
import net.katagaitai.phpscan.php.types.PhpObject;
import net.katagaitai.phpscan.php.types.PhpObjectAny;
import net.katagaitai.phpscan.php.types.PhpResource;
import net.katagaitai.phpscan.php.types.PhpString;
import net.katagaitai.phpscan.taint.Taint;
import net.katagaitai.phpscan.util.Constants;
import net.katagaitai.phpscan.util.SymbolUtils;
import net.katagaitai.phpscan.util.TaintUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@Log4j2
@ToString(exclude = "ip")
@RequiredArgsConstructor
public class SymbolOperator {
	private final Interpreter ip;
	private List<SymbolStack> symbolStackList = Lists.newArrayList();

	public void assign(Symbol left, Symbol right) {
		if (right == null) {
			return;
		}
		Symbol rightClone = copy(right);
		setTypeSet(left, getTypeSet(rightClone));
		left.getTaintSet().clear();
		addNewTaintSet(left, rightClone.getTaintSet());
	}

	public void merge(Symbol left, Symbol right) {
		if (right == null) {
			return;
		}
		List<SymbolId> pair = Lists.newArrayList(left.getId(), right.getId());
		LinkedList<List<SymbolId>> mergeSymbolStack = ip.getStorage()
				.getMergeSymbolStack();
		if (mergeSymbolStack.contains(pair)) {
			return;
		}
		mergeSymbolStack.push(pair);
		try {
			addTypeSet(left, getTypeSet(right));
			Symbol objectSymbol = getSymbol(left.getObjectSymbolId());
			if (objectSymbol == null) {
				setObjectSymbolId(left, right.getObjectSymbolId());
			} else {
				merge(objectSymbol, getSymbol(right.getObjectSymbolId()));
			}
			Symbol keySymbol = getSymbol(left.getKeySymbolId());
			if (keySymbol == null) {
				setKeySymbolId(left, right.getKeySymbolId());
			} else {
				merge(keySymbol, getSymbol(right.getKeySymbolId()));
			}
			addNewTaintSet(left, right.getTaintSet());
		} finally {
			mergeSymbolStack.pop();
		}
	}

	public void merge2(Symbol left, Symbol right) {
		if (right == null) {
			return;
		}
		List<SymbolId> pair = Lists.newArrayList(left.getId(), right.getId());
		LinkedList<List<SymbolId>> mergeSymbolStack = ip.getStorage()
				.getMergeSymbolStack();
		if (mergeSymbolStack.contains(pair)) {
			return;
		}
		mergeSymbolStack.push(pair);
		try {
			addTypeSet(left, getTypeSet(right));
			Symbol objectSymbol = getSymbol(left.getObjectSymbolId());
			if (objectSymbol == null) {
				setObjectSymbolId(left, right.getObjectSymbolId());
			} else {
				merge2(objectSymbol, getSymbol(right.getObjectSymbolId()));
			}
			Symbol keySymbol = getSymbol(left.getKeySymbolId());
			if (keySymbol == null) {
				setKeySymbolId(left, right.getKeySymbolId());
			} else {
				merge2(keySymbol, getSymbol(right.getKeySymbolId()));
			}
			addTaintSet(left, right.getTaintSet());
		} finally {
			mergeSymbolStack.pop();
		}
	}

	public Symbol cast(Symbol targetSymbol, CastType castType) {
		Set<PhpAnyType> tSet = Sets.newHashSet();
		Set<Taint> newTaintSet = Sets.newHashSet();
		for (PhpAnyType type : getTypeSet(targetSymbol)) {
			// 実体
			PhpAnyType casted = cast(type, castType);
			tSet.add(casted);
			// シンボル
			if (type.getClass().equals(casted.getClass())) {
				// 無変換なのでそのままセット
				newTaintSet = targetSymbol.getTaintSet();
			} else {
				if (castType == CastType.INT || castType == CastType.REAL
						|| castType == CastType.BOOL
						|| castType == CastType.UNSET) {
					// pass
				} else if (castType == CastType.ARRAY) {
					// http://php.net/manual/ja/language.types.array.php#language.types.array.casting
					PhpArray phpArray = (PhpArray) casted;
					if (type instanceof PhpInteger || type instanceof PhpFloat
							|| type instanceof PhpString
							|| type instanceof PhpBoolean
							|| type instanceof PhpResource) {
						// integer, float, string, boolean, resourceのいずれの型においても、
						// array に変換する場合、
						// 最初のスカラー値が割り当てられている一つの要素 (添字は 0) を持つ配列
						Symbol symbol = getArrayValue(phpArray, 0);
						addNewTaintSet(symbol, targetSymbol.getTaintSet());
					} else if (type instanceof PhpObject) {
						// objectを配列にする場合には、配列の要素として
						// オブジェクトの属性 (メンバ変数) を持つ配列
						// class A {
						// private $A; // これは '\0A\0A' となります
						// }
						// public $AA; // これは 'AA' となります
						// public 変数の場合は、変数名の頭に '*' がつきます。
					} else if (type instanceof PhpNull) {
						// NULL を配列に変換すると、空の配列を得ます。
					}
				} else if (castType == CastType.OBJECT) {
					PhpObject phpObject = (PhpObject) casted;
					// http://php.net/manual/ja/language.types.object.php#language.types.object.casting
					if (type instanceof PhpNull) {
						// 値が null の場合は新しいインスタンスは空となります。
					} else if (type instanceof PhpArray) {
						// 配列がオブジェクトに変換される場合、
						// 配列のキーと値がそれぞれオブジェクトのプロパティ名とその値となります。
					} else {
						// 上記以外の値の場合には、scalar という名前のメンバ変数が値を格納します。
						Symbol field = getFieldValue(phpObject,
								createSymbol(new PhpString("scalar")));
						if (field != null) {
							addNewTaintSet(field, targetSymbol.getTaintSet());
						}
					}
				} else if (castType == CastType.STRING) {
					// http://php.net/manual/ja/language.types.string.php#language.types.string.casting
					// boolean の TRUE は文字列の "1" に、 FALSE は "" (空文字列) に変換
					// integer (整数) や浮動小数点数 (float) は その数値の数字として文字列に変換
					// 配列は常に "Array" という文字列に変換

					// PHP 4 のオブジェクトは、常に "Object" という文字列に変換
					// PHP 5 以降では、もし存在すれば __toString() メソッドを使用
					if (type instanceof PhpObject) {
						PhpObject phpObject = (PhpObject) type;
						Symbol symbol = phpObject.getPhpClass().__toString(ip,
								phpObject);
						if (symbol != null) {
							newTaintSet.addAll(symbol.getTaintSet());
						}
					}

					// リソースは常に "Resource id #1" という文字列に変換
					// NULL は常に空文字列に変換
				}
			}
		}
		Symbol symbol = createSymbol(tSet);
		addNewTaintSet(symbol, newTaintSet);
		return symbol;
	}

	public Symbol getArrayValue(Symbol arraySymbol, Symbol keySymbol) {
		if (keySymbol == null) {
			return createNull();
		}
		if (isNull(arraySymbol)) {
			setTypeSet(arraySymbol, Sets.newHashSet(createPhpArray()));
		}
		List<PhpArray> phpArrayList = extractPhpArray(arraySymbol);
		Symbol resultSymbol;
		if (phpArrayList.size() == 1) {
			resultSymbol = getArrayValue(phpArrayList.get(0), keySymbol);
		} else {
			resultSymbol = createSymbol();
			for (PhpArray phpArray : phpArrayList) {
				merge(resultSymbol, getArrayValue(phpArray, keySymbol));
			}
		}
		if (getTypeSet(resultSymbol).size() == 0) {
			resultSymbol = createNull();
		}
		insertHeadTaintSet(resultSymbol, arraySymbol.getTaintSet());
		return resultSymbol;
	}

	private void insertHeadTaintSet(Symbol resultSymbol, Set<Taint> headTaintSet) {
		List<Taint> removeList = Lists.newArrayList();
		List<Taint> addList = Lists.newArrayList();
		for (Taint currentTaint : resultSymbol.getTaintSet()) {
			for (Taint headTaint : headTaintSet) {
				if (currentTaint.getPositionList().containsAll(headTaint.getPositionList())) {
					continue;
				}
				List<Command> newCommandList = Lists.newArrayList(headTaint.getCommandList());
				newCommandList.addAll(currentTaint.getCommandList());
				List<SourcePosition> newPositionList = Lists.newArrayList(headTaint.getPositionList());
				newPositionList.addAll(currentTaint.getPositionList());
				Taint newTaint = new Taint(newCommandList, newPositionList,
						currentTaint.getEncodeTagStack(), currentTaint.getCommentList());
				removeList.add(currentTaint);
				addList.add(newTaint);
			}
		}
		resultSymbol.getTaintSet().removeAll(removeList);
		resultSymbol.getTaintSet().addAll(addList);
	}

	public Symbol getFieldValue(Symbol objectSymbol, Symbol keySymbol) {
		if (keySymbol == null) {
			return createNull();
		}
		Symbol resultSymbol = createSymbol();
		for (PhpObject phpObject : extractPhpObject(objectSymbol)) {
			merge(resultSymbol, getFieldValue(phpObject, keySymbol));
		}
		if (getTypeSet(resultSymbol).size() == 0) {
			resultSymbol = createNull();
		}
		setObjectSymbolId(resultSymbol, objectSymbol.getId());
		setKeySymbolId(resultSymbol, keySymbol.getId());
		return resultSymbol;
	}

	// 全ての実体を文字列にキャストする
	public List<PhpString> getPhpStringList(Symbol targetSymbol) {
		List<PhpString> result = Lists.newArrayList();
		for (PhpAnyType type : getTypeSet(targetSymbol)) {
			result.add((PhpString) cast(type, CastType.STRING));
		}
		return result;
	}

	public Symbol mix(Symbol leftSymbol, Operator op, Symbol rightSymbol) {
		if (rightSymbol == null) {
			return null;
		}
		Set<PhpAnyType> tSet = Sets.newHashSet();
		Set<Taint> newTaintSet = Sets.newHashSet();
		for (PhpAnyType type : getTypeSet(leftSymbol)) {
			for (PhpAnyType type2 : getTypeSet(rightSymbol)) {
				PhpAnyType mixed = mix(type, op, type2);
				tSet.add(mixed);
				if ((op == Operator.BITAND || op == Operator.BITOR || op == Operator.BITXOR)
						&& type instanceof PhpString
						&& type2 instanceof PhpString) {
					// &、| そして ^ 演算子の左右のオペランドが文字列の場合、
					// その演算は、 文字列を構成する文字の ASCII 値を使って行います。
					// その結果は文字列になります。
					newTaintSet.addAll(leftSymbol.getTaintSet());
					newTaintSet.addAll(rightSymbol.getTaintSet());
				} else if (op == Operator.CONCAT) {
					// 結合演算子('.')
					newTaintSet.addAll(leftSymbol.getTaintSet());
					newTaintSet.addAll(rightSymbol.getTaintSet());
				} else {
					// + 演算子は、右側の配列を左側の配列に追加したものを返します。
					// →対応不要
				}
			}
		}
		Symbol symbol = createSymbol(tSet);
		addNewTaintSet(symbol, newTaintSet);
		return symbol;
	}

	public void decrementReference(Symbol targetSymbol) {
		if (targetSymbol == null) {
			return;
		}
		// 再帰防止
		LinkedList<SymbolId> decrementSymbolStack = ip.getStorage()
				.getDecrementSymbolStack();
		if (decrementSymbolStack.contains(targetSymbol.getId())) {
			return;
		}
		decrementSymbolStack.push(targetSymbol.getId());
		try {
			targetSymbol
					.setReferenceCount(targetSymbol.getReferenceCount() - 1);
			if (targetSymbol.getReferenceCount() <= 0) {
				log.trace("削除:" + targetSymbol);
				for (PhpAnyType type : getTypeSet(targetSymbol)) {
					decrementReference(type);
				}
				targetSymbol.getTaintSet().clear();
				setObjectSymbolId(targetSymbol, null);
				setKeySymbolId(targetSymbol, null);
				ip.getStorage().getSymbolMap().remove(targetSymbol.getId());
			}
		} finally {
			decrementSymbolStack.pop();
		}
	}

	public void putArrayValue(Symbol arraySymbol, Symbol keySymbol,
			Symbol valueSymbol) {
		if (isNull(arraySymbol)) {
			setTypeSet(arraySymbol, Sets.newHashSet(createPhpArray()));
		}
		for (PhpArray phpArray : extractPhpArray(arraySymbol)) {
			// http://php.net/manual/ja/language.types.array.php
			// key は、整数 または 文字列です。 value には任意の型を指定できます。
			// さらに、次のような key のキャストが発生します。
			// integer として妥当な形式の文字列は integer 型にキャストされます。 つまり、キーに "8"
			// を指定すると、実際には 8 として格納されるということです。一方 "08"
			// はキャストされません。これは十進数として妥当な形式ではないからです。
			// floats もまた integer にキャストされます。つまり、 小数部分は切り捨てられるということです。たとえばキーに
			// 8.7 を指定すると、実際には 8 として格納されます。
			// bool も integer にキャストされます。つまり、 キーに true を指定すると実際には 1 に格納され、
			// 同様にキーを false とすると実際には 0 となります。
			// Null は空文字列にキャストされます。つまり、キーに null を指定すると、実際には "" として格納されます。
			// array や object は、キーとして使えません。 キーとして使おうとすると Illegal offset type
			// という警告が発生します。
			putArrayValue(phpArray, keySymbol, valueSymbol);
		}
	}

	public void putFieldValue(Symbol objectSymbol, Symbol keySymbol,
			Symbol valueSymbol) {
		for (PhpObject phpObject : extractPhpObject(objectSymbol)) {
			putFieldValue(phpObject, keySymbol, valueSymbol);
		}
	}

	public void addTypeSet(Symbol targetSymbol, Set<PhpAnyType> typeSet2) {
		for (PhpAnyType type2 : typeSet2) {
			incrementReference(type2);
		}
		targetSymbol.getTypeSet().addAll(typeSet2);
	}

	public void incrementReference(Symbol targetSymbol) {
		if (targetSymbol == null) {
			return;
		}
		targetSymbol.setReferenceCount(targetSymbol.getReferenceCount() + 1);
	}

	public void incrementReference(PhpAnyType type) {
		if (type == null) {
			return;
		}
		if (type instanceof PhpArray) {
			PhpArray phpArray = (PhpArray) type;
			phpArray.setReferenceCounter(phpArray.getReferenceCounter() + 1);
		} else if (type instanceof PhpObject) {
			PhpObject phpObject = (PhpObject) type;
			phpObject.setReferenceCounter(phpObject.getReferenceCounter() + 1);
		} else if (type instanceof PhpResource) {
			PhpResource phpResource = (PhpResource) type;
			phpResource
					.setReferenceCounter(phpResource.getReferenceCounter() + 1);
		}
	}

	public void removeTypeSet(Symbol targetSymbol, Set<PhpAnyType> set) {
		Set<PhpAnyType> newSet = Sets.newHashSet(getTypeSet(targetSymbol));
		newSet.removeAll(set);
		setTypeSet(targetSymbol, newSet);
	}

	// 全ての実態を文字列にキャストする
	public List<String> getJavaStringList(Symbol targetSymbol) {
		List<String> result = Lists.newArrayList();
		for (PhpString phpString : getPhpStringList(targetSymbol)) {
			result.add(phpString.getString());
		}
		return result;
	}

	public void setTypeSet(Symbol targetSymbol, Set<PhpAnyType> typeSet2) {
		// 1st
		if (typeSet2.size() > Constants.MAX_TYPESET_SIZE) {
			for (PhpAnyType type : Sets.newHashSet(typeSet2)) {
				if (hasTaint(type)) {
					// OK
				} else {
					typeSet2.remove(type);
				}
			}
		}
		// 2nd
		if (typeSet2.size() > Constants.MAX_TYPESET_SIZE) {
			typeSet2 = Sets.newHashSet(Lists.newArrayList(typeSet2)
					.subList(0, Constants.MAX_TYPESET_SIZE));
		}
		for (PhpAnyType type2 : typeSet2) {
			incrementReference(type2);
		}
		for (PhpAnyType type : getTypeSet(targetSymbol)) {
			decrementReference(type);
		}
		targetSymbol.setTypeSet(Sets.newHashSet(typeSet2));
	}

	public Set<PhpAnyType> getTypeSet(Symbol targetSymbol) {
		if (targetSymbol == null) {
			return Sets.newHashSet();
		}
		return Sets.newHashSet(targetSymbol.getTypeSet());
	}

	private boolean hasTaint(PhpAnyType type) {
		if (type instanceof PhpObject) {
			// メソッド呼び出しでシンクに至る可能性があるので残す
			return true;
		} else if (type instanceof PhpArray) {
			PhpArray phpArray = (PhpArray) type;
			Symbol keySymbol = getMergedArrayKeySymbol(phpArray);
			Symbol valueSymbol = getMergedArrayValueSymbol(phpArray);
			if ((keySymbol != null && keySymbol.getTaintSet().size() > 0)
					|| (valueSymbol != null && valueSymbol.getTaintSet().size() > 0)) {
				return true;
			}
		} else if (type instanceof PhpResource) {
			PhpResource phpResource = (PhpResource) type;
			for (SymbolId valueSymbolId : phpResource.getResource().values()) {
				Symbol valueSymbol = getSymbol(valueSymbolId);
				if (valueSymbol != null && valueSymbol.getTaintSet().size() > 0) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isNull(Symbol targetSymbol) {
		if (getTypeSet(targetSymbol).size() == 0) {
			return true;
		}
		for (PhpAnyType type : getTypeSet(targetSymbol)) {
			if (type instanceof PhpNull) {
				// OK
			} else {
				return false;
			}
		}
		return true;
	}

	public boolean isNumber(Symbol targetSymbol) {
		for (PhpAnyType type : getTypeSet(targetSymbol)) {
			if (type instanceof PhpInteger || type instanceof PhpFloat) {
				// OK
			} else {
				return false;
			}
		}
		return true;
	}

	public boolean isBool(Symbol targetSymbol) {
		for (PhpAnyType type : getTypeSet(targetSymbol)) {
			if (type instanceof PhpBoolean) {
				// OK
			} else {
				return false;
			}
		}
		return true;
	}

	public Symbol getArrayValueOfNumber(Symbol arraySymbol) {
		Symbol resultSymbol = createSymbol();
		if (isNull(arraySymbol)) {
			setTypeSet(arraySymbol, Sets.newHashSet(createPhpArray()));
		}
		for (PhpArray phpArray : extractPhpArray(arraySymbol)) {
			merge(resultSymbol, getArrayValueOfNumber(phpArray));
		}
		if (getTypeSet(resultSymbol).size() == 0) {
			resultSymbol = createNull();
		}
		insertHeadTaintSet(resultSymbol, arraySymbol.getTaintSet());
		return resultSymbol;
	}

	public void clear(Symbol targetSymbol) {
		setTypeSet(targetSymbol, Sets.newHashSet());
		targetSymbol.getTaintSet().clear();
		setObjectSymbolId(targetSymbol, null);
		setKeySymbolId(targetSymbol, null);
	}

	public void setObjectSymbolId(Symbol targetSymbol, SymbolId objectSymbolId2) {
		incrementReference(getSymbol(objectSymbolId2));
		decrementReference(getSymbol(targetSymbol.getObjectSymbolId()));
		targetSymbol.setObjectSymbolId(objectSymbolId2);
	}

	public void setKeySymbolId(Symbol targetSymbol, SymbolId keySymbolId2) {
		incrementReference(getSymbol(keySymbolId2));
		decrementReference(getSymbol(targetSymbol.getKeySymbolId()));
		targetSymbol.setKeySymbolId(keySymbolId2);
	}

	public void putFieldValue(Symbol targetSymbol, String string, String string2) {
		putFieldValue(targetSymbol, createSymbol(new PhpString(string)),
				createSymbol(new PhpString(string2)));
	}

	public void putFieldValue(Symbol targetSymbol, String string, long long_) {
		putFieldValue(targetSymbol, createSymbol(new PhpString(string)),
				createSymbol(new PhpInteger(long_)));
	}

	public Symbol getFieldValue(Symbol targetSymbol, String string) {
		return getFieldValue(targetSymbol, createSymbol(new PhpString(string)));
	}

	public List<PhpObject> extractPhpObject(Symbol targetSymbol) {
		List<PhpObject> result = Lists.newArrayList();
		for (PhpAnyType type : getTypeSet(targetSymbol)) {
			if (type instanceof PhpArray) {
				if (type instanceof PhpArrayAny) {
					Set<Taint> taintSet = ((PhpArrayAny) type).getTaintSet();
					// object
					List<PhpNewable> newableList = SymbolUtils.getUserDefinedPhpClassList(ip);
					for (PhpNewable newable : newableList) {
						PhpObjectAny phpObjectAny = createPhpObjectAny(newable, taintSet);
						result.add(phpObjectAny);
					}
					return result;
				} else {
					type = cast(type, CastType.OBJECT);
				}
			}
			if (type instanceof PhpObject) {
				result.add((PhpObject) type);
			}
		}
		return result;
	}

	public List<Long> getJavaLongList(Symbol targetSymbol) {
		List<Long> result = Lists.newArrayList();
		for (PhpAnyType type : getTypeSet(targetSymbol)) {
			PhpInteger phpInteger = (PhpInteger) cast(type, CastType.INT);
			result.add(phpInteger.getInteger());
		}
		return result;
	}

	public List<PhpResource> extractPhpResource(Symbol targetSymbol) {
		List<PhpResource> result = Lists.newArrayList();
		for (PhpAnyType type : getTypeSet(targetSymbol)) {
			if (type instanceof PhpResource) {
				result.add((PhpResource) type);
			}
		}
		return result;
	}

	public Symbol getNextIndex(Symbol arraySymbol) {
		Symbol resultSymbol = createSymbol();
		for (PhpArray phpArray : extractPhpArray(arraySymbol)) {
			merge(resultSymbol, getNextIndex(phpArray));
		}
		if (getTypeSet(resultSymbol).size() == 0) {
			setTypeSet(resultSymbol, Sets.newHashSet(new PhpInteger(0)));
		}
		return resultSymbol;
	}

	public List<PhpArray> extractPhpArray(Symbol arraySymbol) {
		List<PhpArray> result = Lists.newArrayList();
		for (PhpAnyType type : getTypeSet(arraySymbol)) {
			if (type instanceof PhpObject) {
				if (type instanceof PhpObjectAny) {
					Set<Taint> taintSet = ((PhpObjectAny) type).getTaintSet();
					type = createPhpArrayAny(taintSet);
				} else {
					type = cast(type, CastType.ARRAY);
				}
			} else if (type instanceof PhpString) {
				PhpArray phpArray = createPhpArray();
				_putArrayValue(phpArray, anyString(), copy2(arraySymbol));
				type = phpArray;
			}
			if (type instanceof PhpArray) {
				result.add((PhpArray) type);
			}
		}
		return result;
	}

	public void putFieldValue(Symbol objectSymbol, String string,
			PhpAnyType type) {
		putFieldValue(objectSymbol, createSymbol(string), createSymbol(type));
	}

	public void putFieldValue(Symbol objectSymbol, String string,
			Symbol valueSymbol) {
		putFieldValue(objectSymbol, createSymbol(string), valueSymbol);
	}

	public Symbol getMergedArrayKeySymbol(Symbol arraySymbol) {
		Symbol resultSymbol = createSymbol();
		for (PhpArray phpArray : extractPhpArray(arraySymbol)) {
			merge(resultSymbol, getMergedArrayKeySymbol(phpArray));
		}
		return resultSymbol;
	}

	public Symbol getMergedArrayValueSymbol(Symbol arraySymbol) {
		Symbol resultSymbol = createSymbol();
		for (PhpArray phpArray : extractPhpArray(arraySymbol)) {
			merge(resultSymbol, getMergedArrayValueSymbol(phpArray));
		}
		return resultSymbol;
	}

	public Symbol getMergedFieldKeySymbol(Symbol objectSymbol) {
		Symbol resultSymbol = createSymbol();
		for (PhpObject phpObject : extractPhpObject(objectSymbol)) {
			merge(resultSymbol, getMergedFieldKeySymbol(phpObject));
		}
		return resultSymbol;
	}

	public Symbol getMergedFieldValueSymbol(Symbol objectSymbol) {
		Symbol resultSymbol = createSymbol();
		for (PhpObject phpObject : extractPhpObject(objectSymbol)) {
			merge(resultSymbol, getMergedFieldValueSymbol(phpObject));
		}
		return resultSymbol;
	}

	public void putArrayValue(Symbol arraySymbol, String string,
			Symbol valueSymbol) {
		putArrayValue(arraySymbol, createSymbol(string), valueSymbol);
	}

	public Symbol getArrayValue(Symbol arraySymbol, String string) {
		return getArrayValue(arraySymbol, createSymbol(string));
	}

	public Symbol getResourceValue(Symbol targetSymbol, String key) {
		Symbol resultSymbol = createSymbol();
		for (PhpResource phpResource : extractPhpResource(targetSymbol)) {
			merge(resultSymbol, getResourceValue(phpResource, key));
		}
		return resultSymbol;
	}

	public Symbol getResourceValue(PhpResource phpResource, String key) {
		if (key == null) {
			return createNull();
		}
		Map<String, SymbolId> resource = phpResource.getResource();
		Symbol result = getSymbol(resource.get(key));
		if (result == null) {
			result = createNull();
			putResourceValue(phpResource, key, result);
		}
		return result;
	}

	public void addResourceValue(Symbol targetSymbol, String key,
			Symbol valueSymbol) {
		for (PhpResource phpResource : extractPhpResource(targetSymbol)) {
			Symbol symbol = getResourceValue(phpResource, key);
			merge(symbol, valueSymbol);
		}
	}

	public void putResourceValue(PhpResource phpResource, String key, Symbol valueSymbol) {
		if (key == null || valueSymbol == null) {
			return;
		}
		// IDを更新
		phpResource.setId(ip.getStorage().getResourceCounter());
		_putResourceValue(phpResource, key, valueSymbol);
	}

	private void _putResourceValue(PhpResource phpResource, String key, Symbol valueSymbol) {
		if (key == null || valueSymbol == null) {
			return;
		}
		incrementReference(valueSymbol);
		Map<String, SymbolId> resource = phpResource.getResource();
		Symbol oldSymbol = getSymbol(resource.get(key));
		if (oldSymbol != null) {
			decrementReference(oldSymbol);
		}
		resource.put(key, valueSymbol.getId());
	}

	public SymbolStack createStack() {
		SymbolStack stack = new SymbolStack(this);
		symbolStackList.add(stack);
		return stack;
	}

	public Symbol getSymbol(SymbolId id) {
		return ip.getStorage().getSymbolMap().get(id);
	}

	public Symbol createSymbol() {
		Symbol result = new Symbol();
		SymbolId id = new SymbolId(ip.getStorage().getSymbolCounter());
		result.setId(id);
		ip.getStorage().getSymbolMap().put(id, result);
		return result;
	}

	public Symbol createSymbol(PhpAnyType phpAnyType) {
		Symbol result = createSymbol();
		setTypeSet(result, Sets.newHashSet(phpAnyType));
		return result;
	}

	public Symbol createSymbol(Set<PhpAnyType> tSet) {
		Symbol result = createSymbol();
		setTypeSet(result, tSet);
		return result;
	}

	public void gc(Interpreter ip) {
		Map<SymbolId, Symbol> symbolMap = ip.getStorage().getSymbolMap();
		Set<SymbolId> idSet = Sets.newHashSet(symbolMap.keySet());
		for (SymbolId id : idSet) {
			Symbol symbol = symbolMap.get(id);
			if (symbol == null) {
				symbolMap.remove(id);
			} else if (symbol.getReferenceCount() <= 0) {
				decrementReference(symbol);
			}
		}
	}

	public Symbol createNull() {
		return createSymbol(PhpNull.getNull());
	}

	public Symbol createTrue() {
		return createSymbol(PhpBoolean.getTrue());
	}

	public Symbol createFalse() {
		return createSymbol(PhpBoolean.getFalse());
	}

	public Symbol createSymbol(long i) {
		return createSymbol(new PhpInteger(i));
	}

	public Symbol createSymbol(String string) {
		return createSymbol(new PhpString(string));
	}

	public Symbol createSymbol(double d) {
		return createSymbol(new PhpFloat(d));
	}

	public Symbol bool() {
		return createTrue();
	}

	public Symbol string() {
		return createSymbol(Constants.DUMMY_STRING);
	}

	public Symbol array() {
		return createSymbol(createPhpArrayDummy());
	}

	public Symbol array(Symbol symbol) {
		return createSymbol(createPhpArrayDummy(symbol));
	}

	public Symbol object() {
		return createSymbol(createPhpObjectDummy(SymbolUtils.getStdClass(ip),
				string()));
	}

	public Symbol object(Symbol symbol) {
		return createSymbol(createPhpObjectDummy(SymbolUtils.getStdClass(ip),
				symbol));
	}

	public Symbol null_() {
		return createNull();
	}

	public Symbol integer() {
		return createSymbol(new PhpInteger(Constants.DUMMY_INTEGER));
	}

	public Symbol copy(Symbol targetSymbol) {
		if (targetSymbol == null) {
			return createNull();
		}
		Set<PhpAnyType> tSet = Sets.newHashSet();
		for (PhpAnyType type : getTypeSet(targetSymbol)) {
			tSet.add(copy(type));
		}
		Symbol symbol = createSymbol(tSet);
		addNewTaintSet(symbol, targetSymbol.getTaintSet());
		return symbol;
	}

	public Symbol copy2(Symbol targetSymbol) {
		if (targetSymbol == null) {
			return createNull();
		}
		Set<PhpAnyType> tSet = Sets.newHashSet();
		for (PhpAnyType type : getTypeSet(targetSymbol)) {
			tSet.add(copy2(type));
		}
		Symbol symbol = createSymbol(tSet);
		addTaintSet(symbol, targetSymbol.getTaintSet());
		return symbol;
	}

	public Symbol clone(Symbol targetSymbol) {
		Symbol resultSymbol = createSymbol();
		Set<PhpAnyType> tSet = Sets.newHashSet();
		for (PhpAnyType type : getTypeSet(targetSymbol)) {
			if (type instanceof PhpObject) {
				PhpObject phpObject = (PhpObject) type;
				Symbol cloneSymbol = phpObject.getPhpClass().__clone(ip,
						phpObject);
				merge(resultSymbol, cloneSymbol);
			} else {
				tSet.add(copy(type));
			}
		}
		addTypeSet(resultSymbol, tSet);
		addNewTaintSet(resultSymbol, targetSymbol.getTaintSet());
		return resultSymbol;
	}

	public PhpArray createPhpArray() {
		InterpreterStorage storage = ip.getStorage();
		int id = storage.getArrayCounter();
		PhpArray phpArray = new PhpArray(id);
		return phpArray;
	}

	public PhpString toPhpString(PhpAnyType type) {
		if (type instanceof PhpArray) {
			return new PhpString("Array");
		} else if (type instanceof PhpBoolean) {
			return new PhpString(((PhpBoolean) type).isBool() ? "1" : "");
		} else if (type instanceof PhpFloat) {
			return new PhpString(Double.toString(((PhpFloat) type).getFloat_()));
		} else if (type instanceof PhpInteger) {
			return new PhpString(
					Long.toString(((PhpInteger) type).getInteger()));
		} else if (type instanceof PhpNull) {
			return new PhpString("");
		} else if (type instanceof PhpObject) {
			PhpObject phpObject = (PhpObject) type;
			// TODO
			phpObject.getPhpClass().__toString(ip, phpObject);
			return new PhpString("Object");
		} else if (type instanceof PhpResource) {
			return new PhpString("Resource id #0");
		} else if (type instanceof PhpString) {
			return (PhpString) type;
		}
		throw new RuntimeException();
	}

	public PhpAnyType cast(PhpAnyType type, CastType castType) {
		if (type instanceof PhpArray) {
			PhpArray phpArray = (PhpArray) type;
			Map<SymbolId, SymbolId> array = phpArray.getArray();
			if (castType == CastType.INT) {
				return new PhpInteger(array.size() > 0 ? 1 : 0);
			} else if (castType == CastType.REAL) {
				return new PhpFloat(array.size() > 0 ? 1 : 0);
			} else if (castType == CastType.STRING) {
				return toPhpString(type);
			} else if (castType == CastType.ARRAY) {
				return type;
			} else if (castType == CastType.OBJECT) {
				PhpClass phpClass = SymbolUtils.getStdClass(ip);
				for (Entry<String, PhpCallable> entry : PhpArray.METHOD_MAP
						.entrySet()) {
					phpClass.putMethod(entry.getKey(), entry.getValue());
				}
				PhpObject phpObject = createPhpObject(phpClass);
				for (Entry<SymbolId, SymbolId> entry : array.entrySet()) {
					putFieldValue(phpObject, getSymbol(entry.getKey()),
							getSymbol(entry.getValue()));
				}
				return phpObject;
			} else if (castType == CastType.BOOL) {
				return array.size() > 0 ? PhpBoolean.getTrue() : PhpBoolean
						.getFalse();
			} else if (castType == CastType.UNSET) {
				return PhpNull.getNull();
			} else {
				throw new RuntimeException();
			}
		} else if (type instanceof PhpBoolean) {
			PhpBoolean phpBoolean = (PhpBoolean) type;
			boolean bool = phpBoolean.isBool();
			if (castType == CastType.INT) {
				return new PhpInteger(bool ? 1 : 0);
			} else if (castType == CastType.REAL) {
				return new PhpFloat(bool ? 1 : 0);
			} else if (castType == CastType.STRING) {
				return toPhpString(phpBoolean);
			} else if (castType == CastType.ARRAY) {
				PhpArray phpArray = createPhpArray();
				putArrayValue(phpArray, createSymbol(new PhpInteger(0)),
						createSymbol(phpArray));
				return phpArray;
			} else if (castType == CastType.OBJECT) {
				PhpObject phpObject = createPhpObject(SymbolUtils.getStdClass(ip));
				putFieldValue(phpObject, createSymbol(new PhpString("scalar")),
						createSymbol(phpBoolean));
				return phpObject;
			} else if (castType == CastType.BOOL) {
				return phpBoolean;
			} else if (castType == CastType.UNSET) {
				return PhpNull.getNull();
			} else {
				throw new RuntimeException();
			}
		} else if (type instanceof PhpFloat) {
			PhpFloat phpFloat = (PhpFloat) type;
			double float_ = phpFloat.getFloat_();
			if (castType == CastType.INT) {
				return new PhpInteger((long) float_);
			} else if (castType == CastType.REAL) {
				return type;
			} else if (castType == CastType.STRING) {
				return toPhpString(type);
			} else if (castType == CastType.ARRAY) {
				PhpArray phpArray = createPhpArray();
				putArrayValue(phpArray, createSymbol(new PhpInteger(0)),
						createSymbol(type));
				return phpArray;
			} else if (castType == CastType.OBJECT) {
				PhpObject phpObject = createPhpObject(SymbolUtils.getStdClass(ip));
				putFieldValue(phpObject, createSymbol(new PhpString("scalar")),
						createSymbol(type));
				return phpObject;
			} else if (castType == CastType.BOOL) {
				return float_ == 0 ? PhpBoolean.getFalse() : PhpBoolean
						.getTrue();
			} else if (castType == CastType.UNSET) {
				return PhpNull.getNull();
			} else {
				throw new RuntimeException();
			}
		} else if (type instanceof PhpInteger) {
			PhpInteger phpInteger = (PhpInteger) type;
			long integer = phpInteger.getInteger();
			if (castType == CastType.INT) {
				return type;
			} else if (castType == CastType.REAL) {
				return new PhpFloat((double) integer);
			} else if (castType == CastType.STRING) {
				return toPhpString(type);
			} else if (castType == CastType.ARRAY) {
				PhpArray phpArray = createPhpArray();
				putArrayValue(phpArray, createSymbol(new PhpInteger(0)),
						createSymbol(type));
				return phpArray;
			} else if (castType == CastType.OBJECT) {
				PhpObject phpObject = createPhpObject(SymbolUtils.getStdClass(ip));
				putFieldValue(phpObject, createSymbol(new PhpString("scalar")),
						createSymbol(type));
				return phpObject;
			} else if (castType == CastType.BOOL) {
				return integer == 0 ? PhpBoolean.getFalse() : PhpBoolean
						.getTrue();
			} else if (castType == CastType.UNSET) {
				return PhpNull.getNull();
			} else {
				throw new RuntimeException();
			}
		} else if (type instanceof PhpNull) {
			if (castType == CastType.INT) {
				return new PhpInteger(0);
			} else if (castType == CastType.REAL) {
				return new PhpFloat(0);
			} else if (castType == CastType.STRING) {
				return toPhpString(type);
			} else if (castType == CastType.ARRAY) {
				PhpArray phpArray = createPhpArray();
				return phpArray;
			} else if (castType == CastType.OBJECT) {
				PhpObject phpObject = createPhpObject(SymbolUtils.getStdClass(ip));
				return phpObject;
			} else if (castType == CastType.BOOL) {
				return PhpBoolean.getFalse();
			} else if (castType == CastType.UNSET) {
				return PhpNull.getNull();
			} else {
				throw new RuntimeException();
			}
		} else if (type instanceof PhpObject) {
			PhpObject phpObject = (PhpObject) type;
			Map<SymbolId, SymbolId> fieldMap = phpObject.getFieldMap();
			if (castType == CastType.INT) {
				return new PhpInteger(1);
			} else if (castType == CastType.REAL) {
				return new PhpFloat(1);
			} else if (castType == CastType.STRING) {
				return toPhpString(type);
			} else if (castType == CastType.ARRAY) {
				PhpArray phpArray = createPhpArray();
				for (Entry<SymbolId, SymbolId> entry : fieldMap.entrySet()) {
					putArrayValue(phpArray, getSymbol(entry.getKey()),
							getSymbol(entry.getValue()));
				}
				return phpArray;
			} else if (castType == CastType.OBJECT) {
				return type;
			} else if (castType == CastType.BOOL) {
				return PhpBoolean.getTrue();
			} else if (castType == CastType.UNSET) {
				return PhpNull.getNull();
			} else {
				throw new RuntimeException();
			}
		} else if (type instanceof PhpResource) {
			if (castType == CastType.INT) {
				return new PhpInteger(1);
			} else if (castType == CastType.REAL) {
				return new PhpFloat(1);
			} else if (castType == CastType.STRING) {
				return toPhpString(type);
			} else if (castType == CastType.ARRAY) {
				PhpArray phpArray = createPhpArray();
				putArrayValue(phpArray, createSymbol(new PhpInteger(0)),
						createSymbol(type));
				return phpArray;
			} else if (castType == CastType.OBJECT) {
				PhpObject phpObject = createPhpObject(SymbolUtils.getStdClass(ip));
				putFieldValue(phpObject, createSymbol(new PhpString("scalar")),
						createSymbol(type));
				return phpObject;
			} else if (castType == CastType.BOOL) {
				return PhpBoolean.getTrue();
			} else if (castType == CastType.UNSET) {
				return PhpNull.getNull();
			}
			throw new RuntimeException();
		} else if (type instanceof PhpString) {
			PhpString phpString = (PhpString) type;
			String string = phpString.getString();
			if (castType == CastType.INT) {
				int sign = 1;
				if (string.startsWith("-")) {
					sign = -1;
				}
				// http://php.net/manual/ja/language.types.string.php#language.types.string.conversion
				// 文字列の中に '.' や 'e'、'E' といった文字が含まれず、
				// 数値が integer 型の範囲内 (PHP_INT_MAX で定義されています) におさまる場合は
				// integer として評価されます。
				// 文字列の最初の部分により値が決まります。
				// 文字列が、 有効な数値データから始まる場合、この値が使用されます。
				// その他の場合、 値は 0 (ゼロ) となります。

				// 使用可能な整数リテラルの形式は以下のように定義されています。
				// decimal : [1-9][0-9]*
				// | 0
				// hexadecimal : 0[xX][0-9a-fA-F]+
				// octal : 0[0-7]+
				// binary : 0b[01]+
				// integer : [+-]?decimal
				// | [+-]?hexadecimal
				// | [+-]?octal
				// | [+-]?binary
				long value = 0;
				try {
					if (string.matches("[+-]?[1-9][0-9]*.*")) {
						Pattern p = Pattern.compile("^[+-]?([1-9][0-9]*)");
						Matcher m = p.matcher(string);
						if (m.find()) {
							value = Long.parseLong(m.group(1));
						}
					} else if (string.matches("[+-]?0")) {
						// pass
					} else if (string.matches("[+-]?0[xX][0-9a-fA-F]+.*")) {
						Pattern p = Pattern.compile("^[+-]?0[xX]([0-9a-fA-F]+)");
						Matcher m = p.matcher(string);
						if (m.find()) {
							value = Long.parseLong(m.group(1), 16);
						}
					} else if (string.matches("[+-]?0[0-7]+.*")) {
						Pattern p = Pattern.compile("^[+-]?0([0-7]+)");
						Matcher m = p.matcher(string);
						if (m.find()) {
							value = Long.parseLong(m.group(1), 8);
						}
					} else if (string.matches("[+-]?0b[01]+.*")) {
						Pattern p = Pattern.compile("^[+-]?0b([01]+)");
						Matcher m = p.matcher(string);
						if (m.find()) {
							value = Long.parseLong(m.group(1), 2);
						}
					}
				} catch (NumberFormatException e) {
				}
				return new PhpInteger(value * sign);
			} else if (castType == CastType.REAL) {
				if (string.contains(".") || string.toLowerCase().contains("e")) {
					// それ以外の場合は、すべて float として評価されます。
					// 有効な数値データは符号(オプション)の後に、
					// 1 つ以上の数字 (オプションとして小数点を 1 つ含む)、
					// オプションとして指数部が続きます。
					// 指数部は 'e' または 'E' の後に 1 つ以上の数字が続く形式です。

					// http://php.net/manual/ja/language.types.float.php#language.types.float.casting
					// LNUM [0-9]+
					// DNUM ([0-9]*[\.]{LNUM}) | ({LNUM}[\.][0-9]*)
					// EXPONENT_DNUM [+-]?(({LNUM} | {DNUM}) [eE][+-]? {LNUM})

					// 面倒なのでパースできる最長の値を使う
					double value = 0;
					for (int i = 1; i < string.length(); i++) {
						try {
							value = Double.parseDouble(string.substring(0, i));
						} catch (NumberFormatException e) {
							break;
						}
					}
					return new PhpFloat(value);
				} else {
					PhpInteger phpInteger = (PhpInteger) cast(type,
							CastType.INT);
					return new PhpFloat(phpInteger.getInteger());
				}
			} else if (castType == CastType.STRING) {
				return type;
			} else if (castType == CastType.ARRAY) {
				PhpArray phpArray = createPhpArray();
				putArrayValue(phpArray, createSymbol(new PhpInteger(0)),
						createSymbol(type));
				return phpArray;
			} else if (castType == CastType.OBJECT) {
				PhpObject phpObject = createPhpObject(SymbolUtils.getStdClass(ip));
				putFieldValue(phpObject, createSymbol(new PhpString("scalar")),
						createSymbol(type));
				return phpObject;
			} else if (castType == CastType.BOOL) {
				if (string.isEmpty() || string.equals("0")) {
					return PhpBoolean.getFalse();
				} else {
					return PhpBoolean.getTrue();
				}
			} else if (castType == CastType.UNSET) {
				return PhpNull.getNull();
			} else {
				throw new RuntimeException();
			}
		}
		throw new RuntimeException();
	}

	public PhpAnyType copy(PhpAnyType type) {
		if (type instanceof PhpArray) {
			PhpArray phpArray = (PhpArray) type;
			PhpArray clone;
			if (type instanceof PhpArrayAny) {
				clone = createPhpArrayAny(((PhpArrayAny) type).getTaintSet());
			} else {
				clone = createPhpArray();
			}
			// 同じIDを持つPhpArrayオブジェクトを作る
			clone.setId(phpArray.getId());
			for (Entry<SymbolId, SymbolId> entry : phpArray.getArray()
					.entrySet()) {
				_putArrayValue(clone, getSymbol(entry.getKey()),
						getSymbol(entry.getValue()));
			}
			return clone;
		} else if (type instanceof PhpObject) {
			return type;
		} else if (type instanceof PhpBoolean) {
			return type;
		} else if (type instanceof PhpFloat) {
			return type;
		} else if (type instanceof PhpInteger) {
			return type;
		} else if (type instanceof PhpNull) {
			return type;
		} else if (type instanceof PhpResource) {
			return type;
		} else if (type instanceof PhpString) {
			return type;
		}
		throw new RuntimeException();
	}

	public PhpAnyType copy2(PhpAnyType type) {
		if (type instanceof PhpArray) {
			PhpArray phpArray = (PhpArray) type;
			PhpArray clone;
			if (type instanceof PhpArrayAny) {
				clone = createPhpArrayAny(((PhpArrayAny) type).getTaintSet());
			} else {
				clone = createPhpArray();
			}
			// 同じIDを持つPhpArrayオブジェクトを作る
			clone.setId(phpArray.getId());
			for (Entry<SymbolId, SymbolId> entry : phpArray.getArray()
					.entrySet()) {
				_putArrayValue(clone, getSymbol(entry.getKey()),
						getSymbol(entry.getValue()));
			}
			return clone;
		} else if (type instanceof PhpObject) {
			PhpObject phpObject = (PhpObject) type;
			PhpNewable phpClass = phpObject.getPhpClass();
			Map<SymbolId, SymbolId> fieldMap = phpObject.getFieldMap();
			PhpObject clone;
			if (type instanceof PhpObjectAny) {
				clone = createPhpObjectAny(phpClass, ((PhpObjectAny) type).getTaintSet());
			} else {
				clone = createPhpObject(phpClass);
			}
			// 同じIDを持つPhpObjectオブジェクトを作る。
			// idは上書き。realIdはそのまま。
			clone.setId(phpObject.getId());
			for (Entry<SymbolId, SymbolId> entry : fieldMap.entrySet()) {
				_putFieldValue(clone, getSymbol(entry.getKey()),
						getSymbol(entry.getValue()));
			}
			return clone;
		} else if (type instanceof PhpBoolean) {
			return type;
		} else if (type instanceof PhpFloat) {
			return type;
		} else if (type instanceof PhpInteger) {
			return type;
		} else if (type instanceof PhpNull) {
			return type;
		} else if (type instanceof PhpResource) {
			PhpResource phpResource = (PhpResource) type;
			PhpResource clone = createPhpResource();
			// 同じIDを持つPhpResourceオブジェクトを作る
			clone.setId(phpResource.getId());
			for (Entry<String, SymbolId> entry : phpResource.getResource().entrySet()) {
				_putResourceValue(clone, entry.getKey(), getSymbol(entry.getValue()));
			}
			return clone;
		} else if (type instanceof PhpString) {
			return type;
		}
		throw new RuntimeException();
	}

	public Symbol getArrayValue(PhpArray phpArray, Symbol keySymbol) {
		if (keySymbol == null) {
			return createNull();
		}
		Map<SymbolId, SymbolId> array = phpArray.getArray();
		List<SymbolId> arrayKeySymbolIdList = Lists
				.newArrayList(array.keySet());
		Collections.reverse(arrayKeySymbolIdList);
		for (SymbolId id : arrayKeySymbolIdList) {
			Symbol arrayKeySymbol = getSymbol(id);
			if (arrayKeySymbol == null) {
				continue;
			}
			if (hasAnyString(arrayKeySymbol)
					|| (getTypeSet(arrayKeySymbol).containsAll(getTypeSet(keySymbol)))) {
				Symbol symbol = getSymbol(array.get(arrayKeySymbol.getId()));
				if (symbol != null) {
					return symbol;
				}
			}
		}
		// TODO 別シンボルを返すので参照関係が崩れてしまう
		Symbol result = createSymbol();
		for (PhpAnyType type : getTypeSet(keySymbol)) {
			Set<PhpAnyType> setSet = Sets.newHashSet();
			for (SymbolId arrayKeySymbolId : arrayKeySymbolIdList) {
				Symbol arrayKeySymbol = getSymbol(arrayKeySymbolId);
				if (arrayKeySymbol == null) {
					continue;
				}
				for (PhpAnyType type2 : getTypeSet(arrayKeySymbol)) {
					if (setSet.contains(type2)) {
						continue;
					}
					if (isAnyString(type2) || type.equals(type2)) {
						merge(result, getSymbol(array.get(arrayKeySymbolId)));
						setSet.addAll(getTypeSet(arrayKeySymbol));
						break;
					}
				}
			}
		}
		if (getTypeSet(result).size() == 0) {
			if (phpArray instanceof PhpArrayAny) {
				Set<Taint> taintSet = ((PhpArrayAny) phpArray).getTaintSet();
				// string
				Symbol stringSymbol = string();
				addNewTaintSet(stringSymbol, taintSet);
				merge(result, stringSymbol);
				// object
				List<PhpNewable> newableList = SymbolUtils.getUserDefinedPhpClassList(ip);
				for (PhpNewable newable : newableList) {
					PhpObjectAny phpObjectAny = createPhpObjectAny(newable, taintSet);
					Symbol objSymbol = createSymbol(phpObjectAny);
					merge(result, objSymbol);
				}
				// array
				merge(result, createSymbol(createPhpArrayAny(taintSet)));
			} else {
				if (phpArray.equals(ip.getGlobalsArray())) {
					result = createNull();
				} else {
					result = getMergedArrayValueSymbol(phpArray);
				}
				putArrayValue(phpArray, keySymbol, result);
			}
		}
		return result;
	}

	private boolean isAnyString(PhpAnyType type) {
		return type instanceof PhpString
				&& ((PhpString) type).getString().toLowerCase()
						.contains(Constants.DUMMY_ANY_STRING.toLowerCase());
	}

	private boolean hasAnyString(Symbol symbol) {
		for (PhpAnyType type : getTypeSet(symbol)) {
			if (isAnyString(type)) {
				return true;
			}
		}
		return false;
	}

	public void putArrayValue(PhpArray phpArray, Symbol keySymbol,
			Symbol valueSymbol) {
		if (keySymbol == null || valueSymbol == null) {
			return;
		}
		// IDを更新
		phpArray.setId(ip.getStorage().getArrayCounter());
		_putArrayValue(phpArray, keySymbol, valueSymbol);
	}

	private void _putArrayValue(PhpArray phpArray, Symbol keySymbol,
			Symbol valueSymbol) {
		if (keySymbol == null || valueSymbol == null) {
			return;
		}
		incrementReference(keySymbol);
		incrementReference(valueSymbol);
		Map<SymbolId, SymbolId> array = phpArray.getArray();
		Set<SymbolId> oldKeySymbolIdSet = Sets.newHashSet(array.keySet());
		for (SymbolId oldKeySymbolId : oldKeySymbolIdSet) {
			Symbol oldKeySymbol = getSymbol(oldKeySymbolId);
			if (oldKeySymbol == null) {
				continue;
			}
			if (getTypeSet(keySymbol).containsAll(getTypeSet(oldKeySymbol))) {
				SymbolId oldValueSymbolId = array.get(oldKeySymbolId);
				Symbol oldValueSymbol = getSymbol(oldValueSymbolId);
				decrementReference(oldKeySymbol);
				decrementReference(oldValueSymbol);
				array.remove(oldKeySymbolId, oldValueSymbolId);
			}
		}
		array.put(keySymbol.getId(), valueSymbol.getId());
	}

	public PhpAnyType mix(PhpAnyType type, Operator operator, PhpAnyType type2) {
		if (type instanceof PhpArray) {
			PhpArray phpArray = (PhpArray) type;
			if (operator == Operator.ADD) {
				if (type2 instanceof PhpArray) {
					PhpArray result = (PhpArray) copy(phpArray);
					PhpArray phpArray2 = (PhpArray) type2;
					for (Entry<SymbolId, SymbolId> entry : phpArray2.getArray()
							.entrySet()) {
						Symbol keySymbol = getSymbol(entry.getKey());
						Symbol valueSymbol = getSymbol(entry.getValue());
						// 同キーの場合、左側のarrayが優先される
						if (result.getArray().get(keySymbol) == null) {
							// 無ければ追加
							putArrayValue(result, keySymbol, valueSymbol);
						}
					}
					return result;
				} else {
					return mix(cast(phpArray, CastType.INT), operator, type2);
				}
			} else if (operator == Operator.SUB || operator == Operator.MUL
					|| operator == Operator.DIV || operator == Operator.MOD
					|| operator == Operator.POW || operator == Operator.BITAND
					|| operator == Operator.BITOR
					|| operator == Operator.BITXOR
					|| operator == Operator.BITNOT || operator == Operator.LSH
					|| operator == Operator.RSH) {
				return mix(cast(phpArray, CastType.INT), operator, type2);
			} else if (operator == Operator.EQ) {
				if (type2 instanceof PhpArray) {
					PhpArray phpArray2 = (PhpArray) type2;
					return compare(phpArray, phpArray2) == 0 ? PhpBoolean
							.getTrue() : PhpBoolean.getFalse();
				} else {
					return PhpBoolean.getFalse();
				}
			} else if (operator == Operator.SHEQ) {
				return type.equals(type2) ? PhpBoolean.getTrue() : PhpBoolean
						.getFalse();
			} else if (operator == Operator.NE || operator == Operator.NE2) {
				if (type2 instanceof PhpArray) {
					PhpArray phpArray2 = (PhpArray) type2;
					return compare(phpArray, phpArray2) != 0 ? PhpBoolean
							.getTrue() : PhpBoolean.getFalse();
				} else {
					return PhpBoolean.getTrue();
				}
			} else if (operator == Operator.SHNE) {
				return !type.equals(type2) ? PhpBoolean.getTrue() : PhpBoolean
						.getFalse();
			} else if (operator == Operator.LT) {
				if (type2 instanceof PhpArray) {
					PhpArray phpArray2 = (PhpArray) type2;
					return compare(phpArray, phpArray2) < 0 ? PhpBoolean
							.getTrue() : PhpBoolean.getFalse();
				} else {
					return PhpBoolean.getFalse();
				}
			} else if (operator == Operator.GT) {
				if (type2 instanceof PhpArray) {
					PhpArray phpArray2 = (PhpArray) type2;
					return compare(phpArray, phpArray2) > 0 ? PhpBoolean
							.getTrue() : PhpBoolean.getFalse();
				} else {
					return PhpBoolean.getTrue();
				}
			} else if (operator == Operator.LE) {
				if (type2 instanceof PhpArray) {
					PhpArray phpArray2 = (PhpArray) type2;
					return compare(phpArray, phpArray2) <= 0 ? PhpBoolean
							.getTrue() : PhpBoolean.getFalse();
				} else {
					return PhpBoolean.getFalse();
				}
			} else if (operator == Operator.GE) {
				if (type2 instanceof PhpArray) {
					PhpArray phpArray2 = (PhpArray) type2;
					return compare(phpArray, phpArray2) >= 0 ? PhpBoolean
							.getTrue() : PhpBoolean.getFalse();
				} else {
					return PhpBoolean.getTrue();
				}
			} else if (operator == Operator.AND || operator == Operator.OR
					|| operator == Operator.XOR || operator == Operator.NOT
					|| operator == Operator.AND2 || operator == Operator.OR2) {
				return mix(cast(phpArray, CastType.BOOL), operator, type2);
			} else if (operator == Operator.CONCAT) {
				return mix(cast(phpArray, CastType.STRING), operator, type2);
			} else if (operator == Operator.SPACESHIP) {
				return PhpNull.getNull();
			} else {
				throw new RuntimeException();
			}
		} else if (type instanceof PhpBoolean) {
			PhpBoolean phpBoolean = (PhpBoolean) type;
			if (operator == Operator.ADD || operator == Operator.SUB
					|| operator == Operator.MUL || operator == Operator.DIV
					|| operator == Operator.MOD || operator == Operator.POW
					|| operator == Operator.BITAND
					|| operator == Operator.BITOR
					|| operator == Operator.BITXOR
					|| operator == Operator.BITNOT || operator == Operator.LSH
					|| operator == Operator.RSH) {
				return mix(cast(type, CastType.INT), operator, type2);
			} else if (operator == Operator.EQ) {
				if (type.equals(cast(type2, CastType.BOOL))) {
					return PhpBoolean.getTrue();
				} else {
					return PhpBoolean.getFalse();
				}
			} else if (operator == Operator.SHEQ) {
				if (type.equals(type2)) {
					return PhpBoolean.getTrue();
				} else {
					return PhpBoolean.getFalse();
				}
			} else if (operator == Operator.NE || operator == Operator.NE2) {
				if (type.equals(cast(type2, CastType.BOOL))) {
					return PhpBoolean.getFalse();
				} else {
					return PhpBoolean.getTrue();
				}
			} else if (operator == Operator.SHNE) {
				if (type.equals(type2)) {
					return PhpBoolean.getFalse();
				} else {
					return PhpBoolean.getTrue();
				}
			} else if (operator == Operator.LT || operator == Operator.GT
					|| operator == Operator.LE || operator == Operator.GE) {
				return mix(cast(type, CastType.REAL), operator, type2);
			} else if (operator == Operator.AND || operator == Operator.AND2) {
				if (phpBoolean.isBool()
						&& ((PhpBoolean) (cast(type2, CastType.BOOL))).isBool()) {
					return PhpBoolean.getTrue();
				} else {
					return PhpBoolean.getFalse();
				}
			} else if (operator == Operator.OR || operator == Operator.OR2) {
				if (phpBoolean.isBool()
						|| ((PhpBoolean) (cast(type2, CastType.BOOL))).isBool()) {
					return PhpBoolean.getTrue();
				} else {
					return PhpBoolean.getFalse();
				}
			} else if (operator == Operator.XOR) {
				if (phpBoolean.isBool()
						^ ((PhpBoolean) (cast(type2, CastType.BOOL))).isBool()) {
					return PhpBoolean.getTrue();
				} else {
					return PhpBoolean.getFalse();
				}
			} else if (operator == Operator.NOT) {
				PhpBoolean phpBoolean2 = (PhpBoolean) cast(type2, CastType.BOOL);
				if (phpBoolean2.isBool()) {
					return PhpBoolean.getFalse();
				} else {
					return PhpBoolean.getTrue();
				}
			} else if (operator == Operator.CONCAT) {
				return mix(cast(type, CastType.STRING), operator, type2);
			} else if (operator == Operator.SPACESHIP) {
				return PhpNull.getNull();
			} else {
				throw new RuntimeException();
			}
		} else if (type instanceof PhpFloat) {
			PhpFloat phpFloat = (PhpFloat) type;
			double float_ = phpFloat.getFloat_();
			if (operator == Operator.ADD) {
				PhpFloat phpFloat2 = (PhpFloat) cast(type2, CastType.REAL);
				return new PhpFloat(float_ + phpFloat2.getFloat_());
			} else if (operator == Operator.SUB) {
				PhpFloat phpFloat2 = (PhpFloat) cast(type2, CastType.REAL);
				return new PhpFloat(float_ - phpFloat2.getFloat_());
			} else if (operator == Operator.MUL) {
				PhpFloat phpFloat2 = (PhpFloat) cast(type2, CastType.REAL);
				return new PhpFloat(float_ * phpFloat2.getFloat_());
			} else if (operator == Operator.DIV) {
				PhpFloat phpFloat2 = (PhpFloat) cast(type2, CastType.REAL);
				return new PhpFloat(float_ / phpFloat2.getFloat_());
			} else if (operator == Operator.MOD) {
				PhpFloat phpFloat2 = (PhpFloat) cast(type2, CastType.REAL);
				return new PhpFloat(float_ % phpFloat2.getFloat_());
			} else if (operator == Operator.POW) {
				PhpFloat phpFloat2 = (PhpFloat) cast(type2, CastType.REAL);
				return new PhpFloat(Math.pow(float_, phpFloat2.getFloat_()));
			} else if (operator == Operator.BITAND
					|| operator == Operator.BITOR
					|| operator == Operator.BITXOR
					|| operator == Operator.BITNOT || operator == Operator.LSH
					|| operator == Operator.RSH) {
				return mix(cast(type, CastType.INT), operator, type2);
			} else if (operator == Operator.EQ) {
				PhpFloat phpFloat2 = (PhpFloat) cast(type2, CastType.REAL);
				if (float_ == phpFloat2.getFloat_()) {
					return PhpBoolean.getTrue();
				} else {
					return PhpBoolean.getFalse();
				}
			} else if (operator == Operator.SHEQ) {
				if (type.equals(type2)) {
					return PhpBoolean.getTrue();
				} else {
					return PhpBoolean.getFalse();
				}
			} else if (operator == Operator.NE || operator == Operator.NE2) {
				PhpFloat phpFloat2 = (PhpFloat) cast(type2, CastType.REAL);
				if (float_ != phpFloat2.getFloat_()) {
					return PhpBoolean.getTrue();
				} else {
					return PhpBoolean.getFalse();
				}
			} else if (operator == Operator.SHNE) {
				if (!type.equals(type2)) {
					return PhpBoolean.getTrue();
				} else {
					return PhpBoolean.getFalse();
				}
			} else if (operator == Operator.LT) {
				PhpFloat phpFloat2 = (PhpFloat) cast(type2, CastType.REAL);
				if (float_ < phpFloat2.getFloat_()) {
					return PhpBoolean.getTrue();
				} else {
					return PhpBoolean.getFalse();
				}
			} else if (operator == Operator.GT) {
				PhpFloat phpFloat2 = (PhpFloat) cast(type2, CastType.REAL);
				if (float_ > phpFloat2.getFloat_()) {
					return PhpBoolean.getTrue();
				} else {
					return PhpBoolean.getFalse();
				}
			} else if (operator == Operator.LE) {
				PhpFloat phpFloat2 = (PhpFloat) cast(type2, CastType.REAL);
				if (float_ <= phpFloat2.getFloat_()) {
					return PhpBoolean.getTrue();
				} else {
					return PhpBoolean.getFalse();
				}
			} else if (operator == Operator.GE) {
				PhpFloat phpFloat2 = (PhpFloat) cast(type2, CastType.REAL);
				if (float_ >= phpFloat2.getFloat_()) {
					return PhpBoolean.getTrue();
				} else {
					return PhpBoolean.getFalse();
				}
			} else if (operator == Operator.AND || operator == Operator.OR
					|| operator == Operator.XOR || operator == Operator.NOT
					|| operator == Operator.AND2 || operator == Operator.OR2) {
				return mix(cast(type, CastType.BOOL), operator, type2);
			} else if (operator == Operator.CONCAT) {
				return mix(cast(type, CastType.STRING), operator, type2);
			} else if (operator == Operator.SPACESHIP) {
				return PhpNull.getNull();
			} else {
				throw new RuntimeException();
			}
		} else if (type instanceof PhpInteger) {
			PhpInteger phpInteger = (PhpInteger) type;
			long integer = phpInteger.getInteger();
			if (operator == Operator.ADD) {
				if (type2 instanceof PhpFloat) {
					return mix(cast(type, CastType.REAL), operator, type2);
				} else {
					PhpInteger phpInteger2 = (PhpInteger) cast(type2,
							CastType.INT);
					return new PhpInteger(integer + phpInteger2.getInteger());
				}
			} else if (operator == Operator.SUB) {
				if (type2 instanceof PhpFloat) {
					return mix(cast(type, CastType.REAL), operator, type2);
				} else {
					PhpInteger phpInteger2 = (PhpInteger) cast(type2,
							CastType.INT);
					return new PhpInteger(integer - phpInteger2.getInteger());
				}
			} else if (operator == Operator.MUL) {
				if (type2 instanceof PhpFloat) {
					return mix(cast(type, CastType.REAL), operator, type2);
				} else {
					PhpInteger phpInteger2 = (PhpInteger) cast(type2,
							CastType.INT);
					return new PhpInteger(integer * phpInteger2.getInteger());
				}
			} else if (operator == Operator.DIV) {
				if (type2 instanceof PhpFloat) {
					return mix(cast(type, CastType.REAL), operator, type2);
				} else {
					PhpInteger phpInteger2 = (PhpInteger) cast(type2,
							CastType.INT);
					if (phpInteger2.getInteger() == 0) {
						return new PhpInteger(0);
					}
					return new PhpInteger(integer / phpInteger2.getInteger());
				}
			} else if (operator == Operator.MOD) {
				if (type2 instanceof PhpFloat) {
					return mix(cast(type, CastType.REAL), operator, type2);
				} else {
					PhpInteger phpInteger2 = (PhpInteger) cast(type2,
							CastType.INT);
					if (phpInteger2.getInteger() == 0) {
						return new PhpInteger(0);
					}
					return new PhpInteger(integer % phpInteger2.getInteger());
				}
			} else if (operator == Operator.POW) {
				if (type2 instanceof PhpFloat) {
					return mix(cast(type, CastType.REAL), operator, type2);
				} else {
					PhpInteger phpInteger2 = (PhpInteger) cast(type2,
							CastType.INT);
					return new PhpInteger((long) Math.pow(integer,
							phpInteger2.getInteger()));
				}
			} else if (operator == Operator.BITAND) {
				PhpInteger phpInteger2 = (PhpInteger) cast(type2, CastType.INT);
				return new PhpInteger(integer & phpInteger2.getInteger());
			} else if (operator == Operator.BITOR) {
				PhpInteger phpInteger2 = (PhpInteger) cast(type2, CastType.INT);
				return new PhpInteger(integer | phpInteger2.getInteger());
			} else if (operator == Operator.BITXOR) {
				PhpInteger phpInteger2 = (PhpInteger) cast(type2, CastType.INT);
				return new PhpInteger(integer ^ phpInteger2.getInteger());
			} else if (operator == Operator.BITNOT) {
				PhpInteger phpInteger2 = (PhpInteger) cast(type2, CastType.INT);
				return new PhpInteger(~phpInteger2.getInteger());
			} else if (operator == Operator.LSH) {
				PhpInteger phpInteger2 = (PhpInteger) cast(type2, CastType.INT);
				return new PhpInteger(integer << phpInteger2.getInteger());
			} else if (operator == Operator.RSH) {
				PhpInteger phpInteger2 = (PhpInteger) cast(type2, CastType.INT);
				return new PhpInteger(integer >> phpInteger2.getInteger());
			} else if (operator == Operator.EQ) {
				return mix(cast(type, CastType.REAL), operator, type2);
			} else if (operator == Operator.SHEQ) {
				if (type.equals(type2)) {
					return PhpBoolean.getTrue();
				} else {
					return PhpBoolean.getFalse();
				}
			} else if (operator == Operator.NE || operator == Operator.NE2) {
				return mix(cast(type, CastType.REAL), operator, type2);
			} else if (operator == Operator.SHNE) {
				if (!type.equals(type2)) {
					return PhpBoolean.getTrue();
				} else {
					return PhpBoolean.getFalse();
				}
			} else if (operator == Operator.LT || operator == Operator.GT
					|| operator == Operator.LE || operator == Operator.GE) {
				return mix(cast(type, CastType.REAL), operator, type2);
			} else if (operator == Operator.AND || operator == Operator.OR
					|| operator == Operator.XOR || operator == Operator.NOT
					|| operator == Operator.AND2 || operator == Operator.OR2) {
				return mix(cast(type, CastType.BOOL), operator, type2);
			} else if (operator == Operator.CONCAT) {
				return mix(cast(type, CastType.STRING), operator, type2);
			} else if (operator == Operator.SPACESHIP) {
				return PhpNull.getNull();
			} else {
				throw new RuntimeException();
			}
		} else if (type instanceof PhpNull) {
			if (operator == Operator.ADD || operator == Operator.SUB
					|| operator == Operator.MUL || operator == Operator.DIV
					|| operator == Operator.MOD || operator == Operator.POW
					|| operator == Operator.BITAND
					|| operator == Operator.BITOR
					|| operator == Operator.BITXOR
					|| operator == Operator.BITNOT || operator == Operator.LSH
					|| operator == Operator.RSH) {
				return mix(cast(type, CastType.INT), operator, type2);
			} else if (operator == Operator.EQ) {
				return mix(cast(type, CastType.REAL), operator, type2);
			} else if (operator == Operator.SHEQ) {
				if (type.equals(type2)) {
					return PhpBoolean.getTrue();
				} else {
					return PhpBoolean.getFalse();
				}
			} else if (operator == Operator.NE || operator == Operator.NE2) {
				return mix(cast(type, CastType.REAL), operator, type2);
			} else if (operator == Operator.SHNE) {
				if (!type.equals(type2)) {
					return PhpBoolean.getTrue();
				} else {
					return PhpBoolean.getFalse();
				}
			} else if (operator == Operator.LT || operator == Operator.GT
					|| operator == Operator.LE || operator == Operator.GE) {
				return mix(cast(type, CastType.REAL), operator, type2);
			} else if (operator == Operator.AND || operator == Operator.OR
					|| operator == Operator.XOR || operator == Operator.NOT
					|| operator == Operator.AND2 || operator == Operator.OR2) {
				return mix(cast(type, CastType.BOOL), operator, type2);
			} else if (operator == Operator.CONCAT) {
				return mix(cast(type, CastType.STRING), operator, type2);
			} else if (operator == Operator.SPACESHIP) {
				return PhpNull.getNull();
			} else {
				throw new RuntimeException();
			}
		} else if (type instanceof PhpObject) {
			if (operator == Operator.ADD || operator == Operator.SUB
					|| operator == Operator.MUL || operator == Operator.DIV
					|| operator == Operator.MOD || operator == Operator.POW
					|| operator == Operator.BITAND
					|| operator == Operator.BITOR
					|| operator == Operator.BITXOR
					|| operator == Operator.BITNOT || operator == Operator.LSH
					|| operator == Operator.RSH) {
				return mix(cast(type, CastType.INT), operator, type2);
			} else if (operator == Operator.EQ || operator == Operator.SHEQ
					|| operator == Operator.NE || operator == Operator.NE2
					|| operator == Operator.SHNE || operator == Operator.LT
					|| operator == Operator.GT || operator == Operator.LE
					|| operator == Operator.GE) {
				// http://php.net/manual/ja/language.operators.comparison.php
				// 組み込みクラスには独自の比較基準が定義されています。それ以外の クラスは比較できません。
				return PhpBoolean.getTrue();
			} else if (operator == Operator.AND || operator == Operator.OR
					|| operator == Operator.XOR || operator == Operator.NOT
					|| operator == Operator.AND2 || operator == Operator.OR2) {
				return mix(cast(type, CastType.BOOL), operator, type2);
			} else if (operator == Operator.CONCAT) {
				return mix(cast(type, CastType.STRING), operator, type2);
			} else if (operator == Operator.SPACESHIP) {
				return PhpNull.getNull();
			} else {
				throw new RuntimeException();
			}
		} else if (type instanceof PhpResource) {
			// 叩き台としてObjectと同じにしておく
			if (operator == Operator.ADD || operator == Operator.SUB
					|| operator == Operator.MUL || operator == Operator.DIV
					|| operator == Operator.MOD || operator == Operator.POW
					|| operator == Operator.BITAND
					|| operator == Operator.BITOR
					|| operator == Operator.BITXOR
					|| operator == Operator.BITNOT || operator == Operator.LSH
					|| operator == Operator.RSH) {
				return mix(cast(type, CastType.INT), operator, type2);
			} else if (operator == Operator.EQ || operator == Operator.SHEQ
					|| operator == Operator.NE || operator == Operator.NE2
					|| operator == Operator.SHNE || operator == Operator.LT
					|| operator == Operator.GT || operator == Operator.LE
					|| operator == Operator.GE) {
				return PhpBoolean.getTrue();
			} else if (operator == Operator.AND || operator == Operator.OR
					|| operator == Operator.XOR || operator == Operator.NOT
					|| operator == Operator.AND2 || operator == Operator.OR2) {
				return mix(cast(type, CastType.BOOL), operator, type2);
			} else if (operator == Operator.CONCAT) {
				return mix(cast(type, CastType.STRING), operator, type2);
			} else if (operator == Operator.SPACESHIP) {
				return PhpNull.getNull();
			} else {
				throw new RuntimeException();
			}
		} else if (type instanceof PhpString) {
			PhpString phpString = (PhpString) type;
			String string = phpString.getString();
			if (operator == Operator.ADD || operator == Operator.SUB
					|| operator == Operator.MUL || operator == Operator.DIV
					|| operator == Operator.MOD || operator == Operator.POW
					|| operator == Operator.BITAND
					|| operator == Operator.BITOR
					|| operator == Operator.BITXOR
					|| operator == Operator.BITNOT || operator == Operator.LSH
					|| operator == Operator.RSH) {
				return mix(cast(type, CastType.REAL), operator, type2);
			} else if (operator == Operator.EQ) {
				PhpString phpString2 = (PhpString) cast(type2, CastType.STRING);
				if (string.equals(phpString2.getString())) {
					return PhpBoolean.getTrue();
				} else {
					return PhpBoolean.getFalse();
				}
			} else if (operator == Operator.SHEQ) {
				if (equals(type2)) {
					return PhpBoolean.getTrue();
				} else {
					return PhpBoolean.getFalse();
				}
			} else if (operator == Operator.NE || operator == Operator.NE2) {
				PhpString phpString2 = (PhpString) cast(type2, CastType.STRING);
				if (!string.equals(phpString2.getString())) {
					return PhpBoolean.getTrue();
				} else {
					return PhpBoolean.getFalse();
				}
			} else if (operator == Operator.SHNE) {
				if (!equals(type2)) {
					return PhpBoolean.getTrue();
				} else {
					return PhpBoolean.getFalse();
				}
			} else if (operator == Operator.LT) {
				PhpString phpString2 = (PhpString) cast(type2, CastType.STRING);
				if (string.compareTo(phpString2.getString()) < 0) {
					return PhpBoolean.getTrue();
				} else {
					return PhpBoolean.getFalse();
				}
			} else if (operator == Operator.GT) {
				PhpString phpString2 = (PhpString) cast(type2, CastType.STRING);
				if (string.compareTo(phpString2.getString()) > 0) {
					return PhpBoolean.getTrue();
				} else {
					return PhpBoolean.getFalse();
				}
			} else if (operator == Operator.LE) {
				PhpString phpString2 = (PhpString) cast(type2, CastType.STRING);
				if (string.compareTo(phpString2.getString()) <= 0) {
					return PhpBoolean.getTrue();
				} else {
					return PhpBoolean.getFalse();
				}
			} else if (operator == Operator.GE) {
				PhpString phpString2 = (PhpString) cast(type2, CastType.STRING);
				if (string.compareTo(phpString2.getString()) >= 0) {
					return PhpBoolean.getTrue();
				} else {
					return PhpBoolean.getFalse();
				}
			} else if (operator == Operator.AND || operator == Operator.OR
					|| operator == Operator.XOR || operator == Operator.NOT
					|| operator == Operator.AND2 || operator == Operator.OR2) {
				return mix(cast(type, CastType.BOOL), operator, type2);
			} else if (operator == Operator.CONCAT) {
				PhpString phpString2 = (PhpString) cast(type2, CastType.STRING);
				return new PhpString(string + phpString2.getString());
			} else if (operator == Operator.SPACESHIP) {
				return PhpNull.getNull();
			} else {
				throw new RuntimeException();
			}
		}
		throw new RuntimeException();
	}

	private int compare(PhpArray phpArray, PhpArray phpArray2) {
		if (phpArray.getArray().size() > phpArray2.getArray().size()) {
			return 1;
		} else if (phpArray.getArray().size() < phpArray2.getArray().size()) {
			return -1;
		} else {
			// TODO 同じキーの値同士を比較していない問題
			return 0;
		}
	}

	public void decrementReference(PhpAnyType type) {
		if (type == null) {
			return;
		}
		LinkedList<PhpAnyType> decrementTypeStack = ip.getStorage()
				.getDecrementTypeStack();
		if (decrementTypeStack.contains(type)) {
			return;
		}
		decrementTypeStack.push(type);
		try {
			if (type instanceof PhpArray) {
				PhpArray phpArray = (PhpArray) type;
				phpArray.setReferenceCounter(phpArray.getReferenceCounter() - 1);
				if (phpArray.getReferenceCounter() <= 0) {
					log.trace("削除:" + type);
					for (Entry<SymbolId, SymbolId> entry : phpArray.getArray()
							.entrySet()) {
						Symbol keySymbol = getSymbol(entry.getKey());
						decrementReference(keySymbol);
						Symbol valueSymbol = getSymbol(entry.getValue());
						decrementReference(valueSymbol);
					}
				}
			} else if (type instanceof PhpObject) {
				PhpObject phpObject = (PhpObject) type;
				Map<SymbolId, SymbolId> fieldMap = phpObject.getFieldMap();
				phpObject
						.setReferenceCounter(phpObject.getReferenceCounter() - 1);
				if (phpObject.getReferenceCounter() <= 0) {
					log.trace("削除:" + phpObject);
					for (Entry<SymbolId, SymbolId> entry : fieldMap.entrySet()) {
						Symbol keySymbol = getSymbol(entry.getKey());
						decrementReference(keySymbol);
						Symbol valueSymbol = getSymbol(entry.getValue());
						decrementReference(valueSymbol);
					}
					phpObject.getPhpClass().__destruct(ip, phpObject);
					ip.getStorage().getObjectMap()
							.remove(phpObject.getRealId());
				}
			} else if (type instanceof PhpResource) {
				PhpResource phpResource = (PhpResource) type;
				phpResource.setReferenceCounter(phpResource
						.getReferenceCounter() - 1);
				if (phpResource.getReferenceCounter() <= 0) {
					log.trace("削除:" + phpResource);
					for (SymbolId id : phpResource.getResource().values()) {
						decrementReference(getSymbol(id));
					}
				}
			}
		} finally {
			decrementTypeStack.pop();
		}
	}

	public Symbol getMergedArrayKeySymbol(PhpArray phpArray) {
		Map<SymbolId, SymbolId> array = phpArray.getArray();
		Symbol result = createSymbol();
		List<SymbolId> arrayKeySymbolIdList = Lists
				.newArrayList(array.keySet());
		Collections.reverse(arrayKeySymbolIdList);
		Set<PhpAnyType> setSet = Sets.newHashSet();
		for (SymbolId arrayKeySymbolId : arrayKeySymbolIdList) {
			if (getSymbol(arrayKeySymbolId) == null) {
				continue;
			}
			for (PhpAnyType type : getTypeSet(getSymbol(arrayKeySymbolId))) {
				if (setSet.contains(type)) {
					continue;
				}
				merge(result, getSymbol(arrayKeySymbolId));
				setSet.addAll(getTypeSet(getSymbol(arrayKeySymbolId)));
				break;
			}
		}
		if (getTypeSet(result).size() == 0) {
			return createNull();
		} else {
			return result;
		}
	}

	public Symbol getMergedArrayValueSymbol(PhpArray phpArray) {
		Map<SymbolId, SymbolId> array = phpArray.getArray();
		Symbol result = createSymbol();
		List<SymbolId> arrayKeySymbolIdList = Lists
				.newArrayList(array.keySet());
		Collections.reverse(arrayKeySymbolIdList);
		Set<PhpAnyType> setSet = Sets.newHashSet();
		for (SymbolId arrayKeySymbolId : arrayKeySymbolIdList) {
			if (getSymbol(arrayKeySymbolId) == null) {
				continue;
			}
			for (PhpAnyType type : getTypeSet(getSymbol(arrayKeySymbolId))) {
				if (setSet.contains(type)) {
					continue;
				}
				merge(result, getSymbol(array.get(arrayKeySymbolId)));
				setSet.addAll(getTypeSet(getSymbol(arrayKeySymbolId)));
				break;
			}
		}
		if (phpArray instanceof PhpArrayAny) {
			Set<Taint> taintSet = ((PhpArrayAny) phpArray).getTaintSet();
			// string
			Symbol stringSymbol = string();
			addNewTaintSet(stringSymbol, taintSet);
			merge(result, stringSymbol);
			// object
			List<PhpNewable> newableList = SymbolUtils.getUserDefinedPhpClassList(ip);
			for (PhpNewable newable : newableList) {
				PhpObjectAny phpObjectAny = createPhpObjectAny(newable, taintSet);
				Symbol objSymbol = createSymbol(phpObjectAny);
				merge(result, objSymbol);
			}
			// array
			merge(result, createSymbol(createPhpArrayAny(taintSet)));
		}
		if (getTypeSet(result).size() == 0) {
			return createNull();
		} else {
			return result;
		}
	}

	public Symbol getArrayValueOfNumber(PhpArray phpArray) {
		Map<SymbolId, SymbolId> array = phpArray.getArray();
		Symbol result = createSymbol();
		List<SymbolId> arrayKeySymbolIdList = Lists
				.newArrayList(array.keySet());
		Collections.reverse(arrayKeySymbolIdList);
		Set<PhpAnyType> setSet = Sets.newHashSet();
		for (SymbolId arrayKeySymbolId : arrayKeySymbolIdList) {
			if (getSymbol(arrayKeySymbolId) == null) {
				continue;
			}
			for (PhpAnyType type2 : getTypeSet(getSymbol(arrayKeySymbolId))) {
				if (setSet.contains(type2)) {
					continue;
				}
				if (type2 instanceof PhpInteger || type2 instanceof PhpFloat) {
					merge(result, getSymbol(array.get(arrayKeySymbolId)));
					setSet.addAll(getTypeSet(getSymbol(arrayKeySymbolId)));
					break;
				}
			}
		}
		if (phpArray instanceof PhpArrayAny) {
			Set<Taint> taintSet = ((PhpArrayAny) phpArray).getTaintSet();
			// string
			Symbol stringSymbol = string();
			addNewTaintSet(stringSymbol, taintSet);
			merge(result, stringSymbol);
			// object
			List<PhpNewable> newableList = SymbolUtils.getUserDefinedPhpClassList(ip);
			for (PhpNewable newable : newableList) {
				PhpObjectAny phpObjectAny = createPhpObjectAny(newable, taintSet);
				Symbol objSymbol = createSymbol(phpObjectAny);
				merge(result, objSymbol);
			}
			// array
			merge(result, createSymbol(createPhpArrayAny(taintSet)));
		}
		if (getTypeSet(result).size() == 0) {
			return createNull();
		} else {
			return result;
		}
	}

	public void putArrayValue(PhpArray phpArray, long i, String string) {
		putArrayValue(phpArray, i, createSymbol(string));
	}

	public void putArrayValue(PhpArray phpArray, long i, Symbol symbol) {
		putArrayValue(phpArray, createSymbol(i), symbol);
	}

	public void putArrayValue(PhpArray phpArray, String string, String string2) {
		putArrayValue(phpArray, string, createSymbol(string2));
	}

	public void putArrayValue(PhpArray phpArray, String string, Symbol symbol) {
		putArrayValue(phpArray, createSymbol(string), symbol);
	}

	public void putArrayValue(PhpArray phpArray, String string, int i) {
		putArrayValue(phpArray, string, createSymbol(i));
	}

	public Symbol getNextIndex(PhpArray phpArray) {
		long max = -1;
		Map<SymbolId, SymbolId> array = phpArray.getArray();
		for (SymbolId keySymbolId : array.keySet()) {
			Symbol keySymbol = getSymbol(keySymbolId);
			if (keySymbol == null) {
				continue;
			}
			for (long long_ : getJavaLongList(keySymbol)) {
				max = Math.max(long_, max);
			}
		}
		return createSymbol(max + 1);
	}

	public Symbol getArrayValue(PhpArray phpArray, String string) {
		return getArrayValue(phpArray, createSymbol(string));
	}

	public Symbol getArrayValue(PhpArray phpArray, long i) {
		return getArrayValue(phpArray, createSymbol(i));
	}

	public PhpArray createPhpArrayDummy() {
		InterpreterStorage storage = ip.getStorage();
		int id = storage.getArrayCounter();
		PhpArray phpArray = new PhpArray(id);
		_putArrayValue(phpArray, anyString(), string());
		return phpArray;
	}

	private Symbol anyString() {
		return createSymbol(new PhpString(Constants.DUMMY_ANY_STRING));
	}

	public PhpArray createPhpArrayDummy(Symbol dummySymbol) {
		InterpreterStorage storage = ip.getStorage();
		int id = storage.getArrayCounter();
		PhpArray phpArray = new PhpArray(id);
		_putArrayValue(phpArray, anyString(), dummySymbol);
		return phpArray;
	}

	public PhpObject createPhpObject(PhpNewable newable) {
		InterpreterStorage storage = ip.getStorage();
		int id = storage.getObjectCounter();
		PhpObject phpObject = new PhpObject(id, newable);
		storage.getObjectMap().put(phpObject.getRealId(), phpObject);
		return phpObject;
	}

	public Symbol getFieldValue(PhpObject phpObject, Symbol keySymbol) {
		if (keySymbol == null) {
			return createNull();
		}
		Map<SymbolId, SymbolId> fieldMap = phpObject.getFieldMap();
		List<SymbolId> fieldKeySymbolIdList = Lists.newArrayList(fieldMap
				.keySet());
		Collections.reverse(fieldKeySymbolIdList);
		for (SymbolId id : fieldKeySymbolIdList) {
			Symbol fieldKeySymbol = getSymbol(id);
			if (fieldKeySymbol == null) {
				continue;
			}
			if (hasAnyString(fieldKeySymbol)
					|| (getTypeSet(fieldKeySymbol).containsAll(getTypeSet(keySymbol)))) {
				Symbol symbol = getSymbol(fieldMap.get(fieldKeySymbol.getId()));
				if (symbol != null) {
					return symbol;
				}
			}
		}
		// TODO 別シンボルを返すので参照関係が崩れてしまう
		Symbol result = createSymbol();
		for (PhpAnyType type : getTypeSet(keySymbol)) {
			Set<PhpAnyType> setSet = Sets.newHashSet();
			for (SymbolId fieldKeySymbolId : fieldKeySymbolIdList) {
				Symbol fieldKeySymbol = getSymbol(fieldKeySymbolId);
				if (fieldKeySymbol == null) {
					continue;
				}
				for (PhpAnyType type2 : getTypeSet(fieldKeySymbol)) {
					if (setSet.contains(type2)) {
						continue;
					}
					if (isAnyString(type2) || type.equals(type2)) {
						merge(result, getSymbol(fieldMap.get(fieldKeySymbolId)));
						setSet.addAll(getTypeSet(fieldKeySymbol));
						break;
					}
				}
			}
		}
		if (getTypeSet(result).size() == 0) {
			if (phpObject instanceof PhpObjectAny) {
				Set<Taint> taintSet = ((PhpObjectAny) phpObject).getTaintSet();
				// string
				Symbol stringSymbol = string();
				addNewTaintSet(stringSymbol, taintSet);
				merge(result, stringSymbol);
				// object
				List<PhpNewable> newableList = SymbolUtils.getUserDefinedPhpClassList(ip);
				for (PhpNewable newable : newableList) {
					PhpObjectAny phpObjectAny = createPhpObjectAny(newable, taintSet);
					Symbol objSymbol = createSymbol(phpObjectAny);
					merge(result, objSymbol);
				}
				merge(result, createSymbol(createPhpArrayAny(taintSet)));
			} else {
				result = createNull();
				//				merge(result, getMergedFieldValueSymbol(phpObject));
				putFieldValue(phpObject, keySymbol, result);
			}
		}
		return result;
	}

	public void putFieldValue(PhpObject phpObject, Symbol keySymbol,
			Symbol valueSymbol) {
		if (keySymbol == null || valueSymbol == null) {
			return;
		}
		// IDを更新。idフィールドは上書き。realIdフィールドはそのまま。
		phpObject.setId(ip.getStorage().getObjectCounter());
		_putFieldValue(phpObject, keySymbol, valueSymbol);
	}

	private void _putFieldValue(PhpObject phpObject, Symbol keySymbol,
			Symbol valueSymbol) {
		if (keySymbol == null || valueSymbol == null) {
			return;
		}
		incrementReference(keySymbol);
		incrementReference(valueSymbol);
		Map<SymbolId, SymbolId> fieldMap = phpObject.getFieldMap();
		Set<SymbolId> oldKeySymbolIdSet = Sets.newHashSet(fieldMap.keySet());
		for (SymbolId oldKeySymbolId : oldKeySymbolIdSet) {
			Symbol oldKeySymbol = getSymbol(oldKeySymbolId);
			if (oldKeySymbol == null) {
				continue;
			}
			if (getTypeSet(keySymbol).containsAll(getTypeSet(oldKeySymbol))) {
				SymbolId oldValueSymbolId = fieldMap.get(oldKeySymbolId);
				Symbol oldValueSymbol = getSymbol(oldValueSymbolId);
				decrementReference(oldKeySymbol);
				decrementReference(oldValueSymbol);
				fieldMap.remove(oldKeySymbolId, oldValueSymbolId);
			}
		}
		fieldMap.put(keySymbol.getId(), valueSymbol.getId());
	}

	public PhpObject createPhpObjectDummy(PhpNewable newable) {
		InterpreterStorage storage = ip.getStorage();
		int id = storage.getObjectCounter();
		PhpObject phpObject = new PhpObject(id, newable);
		_putFieldValue(phpObject, anyString(), string());
		storage.getObjectMap().put(phpObject.getRealId(), phpObject);
		return phpObject;
	}

	public PhpObject createPhpObjectDummy(PhpNewable newable, Symbol dummySymbol) {
		InterpreterStorage storage = ip.getStorage();
		int id = storage.getObjectCounter();
		PhpObject phpObject = new PhpObject(id, newable);
		_putFieldValue(phpObject, anyString(), dummySymbol);
		storage.getObjectMap().put(phpObject.getRealId(), phpObject);
		return phpObject;
	}

	public Symbol getMergedFieldKeySymbol(PhpObject phpObject) {
		Map<SymbolId, SymbolId> fieldMap = phpObject.getFieldMap();
		Symbol result = createSymbol();
		List<SymbolId> fieldKeySymbolIdList = Lists
				.newArrayList(fieldMap.keySet());
		Collections.reverse(fieldKeySymbolIdList);
		Set<PhpAnyType> setSet = Sets.newHashSet();
		for (SymbolId fieldKeySymbolId : fieldKeySymbolIdList) {
			if (getSymbol(fieldKeySymbolId) == null) {
				continue;
			}
			for (PhpAnyType type : getTypeSet(getSymbol(fieldKeySymbolId))) {
				if (setSet.contains(type)) {
					continue;
				}
				merge(result, getSymbol(fieldKeySymbolId));
				setSet.addAll(getTypeSet(getSymbol(fieldKeySymbolId)));
				break;
			}
		}
		if (getTypeSet(result).size() == 0) {
			return createNull();
		} else {
			return result;
		}
	}

	public Symbol getMergedFieldValueSymbol(PhpObject phpObject) {
		Map<SymbolId, SymbolId> fieldMap = phpObject.getFieldMap();
		Symbol result = createSymbol();
		List<SymbolId> fieldKeySymbolIdList = Lists.newArrayList(fieldMap
				.keySet());
		Collections.reverse(fieldKeySymbolIdList);
		Set<PhpAnyType> setSet = Sets.newHashSet();
		for (SymbolId fieldKeySymbolId : fieldKeySymbolIdList) {
			if (getSymbol(fieldKeySymbolId) == null) {
				continue;
			}
			for (PhpAnyType type : getTypeSet(getSymbol(fieldKeySymbolId))) {
				if (setSet.contains(type)) {
					continue;
				}
				merge(result, getSymbol(fieldMap.get(fieldKeySymbolId)));
				setSet.addAll(getTypeSet(getSymbol(fieldKeySymbolId)));
				break;
			}
		}
		if (phpObject instanceof PhpObjectAny) {
			Set<Taint> taintSet = ((PhpObjectAny) phpObject).getTaintSet();
			// string
			Symbol stringSymbol = string();
			addNewTaintSet(stringSymbol, taintSet);
			merge(result, stringSymbol);
			// object
			List<PhpNewable> newableList = SymbolUtils.getUserDefinedPhpClassList(ip);
			for (PhpNewable newable : newableList) {
				PhpObjectAny phpObjectAny = createPhpObjectAny(newable, taintSet);
				Symbol objSymbol = createSymbol(phpObjectAny);
				merge(result, objSymbol);
			}
			merge(result, createSymbol(createPhpArrayAny(taintSet)));
		}
		if (getTypeSet(result).size() == 0) {
			return createNull();
		} else {
			return result;
		}
	}

	public PhpResource createPhpResource() {
		InterpreterStorage storage = ip.getStorage();
		int id = storage.getResourceCounter();
		PhpResource phpResource = new PhpResource(id);
		return phpResource;
	}

	public void addNewTaint(Symbol targetSymbol, String comment) {
		Taint taint = TaintUtils.createTaint(ip, comment);
		targetSymbol.getTaintSet().add(taint);
		trimTaintSet(targetSymbol);
	}

	public void addNewTaint(Symbol targetSymbol, Taint taint) {
		targetSymbol.getTaintSet().add(TaintUtils.createTaint(ip, taint));
		trimTaintSet(targetSymbol);
	}

	public void addNewTaintSet(Symbol targetSymbol, Set<Taint> taintSet2) {
		for (Taint taint : Lists.newArrayList(taintSet2)) {
			targetSymbol.getTaintSet().add(TaintUtils.createTaint(ip, taint));
		}
		trimTaintSet(targetSymbol);
	}

	public void addTaintSet(Symbol targetSymbol, Set<Taint> taintSet2) {
		targetSymbol.getTaintSet().addAll(taintSet2);
		trimTaintSet(targetSymbol);
	}

	private void trimTaintSet(Symbol targetSymbol) {
		targetSymbol.setTaintSet(TaintUtils.trim(targetSymbol.getTaintSet()));
		if (targetSymbol.getTaintSet().size() > Constants.MAX_TAINTSET_SIZE) {
			targetSymbol.setTaintSet(Sets.newHashSet(Lists.newArrayList(
					targetSymbol.getTaintSet()).subList(0, Constants.MAX_TAINTSET_SIZE)));
		}
	}

	public PhpObjectAny createPhpObjectAny(PhpNewable newable, Set<Taint> taintSet) {
		InterpreterStorage storage = ip.getStorage();
		int id = storage.getObjectCounter();
		PhpObjectAny phpObjectAny = new PhpObjectAny(id, newable, createNewTaintSet(taintSet));
		storage.getObjectMap().put(phpObjectAny.getRealId(), phpObjectAny);
		return phpObjectAny;
	}

	private Set<Taint> createNewTaintSet(Set<Taint> taintSet) {
		Set<Taint> result = Sets.newHashSet();
		for (Taint taint : taintSet) {
			result.add(TaintUtils.createTaint(ip, taint));
		}
		return result;
	}

	public PhpArrayAny createPhpArrayAny(Set<Taint> taintSet) {
		InterpreterStorage storage = ip.getStorage();
		int id = storage.getArrayCounter();
		PhpArrayAny phpArrayAny = new PhpArrayAny(id, createNewTaintSet(taintSet));
		return phpArrayAny;
	}

	public Symbol getResourceValue(PhpResource phpResource, Symbol keySymbol) {
		Symbol result = createSymbol();
		for (String key : getJavaStringList(keySymbol)) {
			merge(result, getResourceValue(phpResource, key));
		}
		if (getTypeSet(result).size() == 0) {
			result = createNull();
		}
		return result;
	}

	public void putResourceValue(PhpResource phpResource, Symbol keySymbol, Symbol valueSymbol) {
		for (String key : getJavaStringList(keySymbol)) {
			putResourceValue(phpResource, key, valueSymbol);
		}
	}

	public Symbol getMergedResourceKeySymbol(PhpResource phpResource) {
		Symbol result = createSymbol();
		for (String key : phpResource.getResource().keySet()) {
			addTypeSet(result, Sets.newHashSet(new PhpString(key)));
		}
		if (getTypeSet(result).size() == 0) {
			result = createNull();
		}
		return result;
	}

	public List<Double> getJavaDoubleList(Symbol targetSymbol) {
		List<Double> result = Lists.newArrayList();
		for (PhpAnyType type : getTypeSet(targetSymbol)) {
			PhpFloat phpFloat = (PhpFloat) cast(type, CastType.REAL);
			result.add(phpFloat.getFloat_());
		}
		return result;
	}

	public boolean containsNumberEq(Symbol symbol, double i) {
		return getJavaDoubleList(symbol).contains(i);
	}

	public boolean containsNumberNe(Symbol symbol, double i) {
		for (double double_ : getJavaDoubleList(symbol)) {
			if (double_ != i) {
				return true;
			}
		}
		return false;
	}

}
