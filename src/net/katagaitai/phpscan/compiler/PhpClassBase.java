package net.katagaitai.phpscan.compiler;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import lombok.extern.log4j.Log4j2;
import net.katagaitai.phpscan.command.CastType;
import net.katagaitai.phpscan.interpreter.CallDecorator;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.interpreter.InterpreterContext;
import net.katagaitai.phpscan.php.types.PhpAnyType;
import net.katagaitai.phpscan.php.types.PhpArray;
import net.katagaitai.phpscan.php.types.PhpInteger;
import net.katagaitai.phpscan.php.types.PhpObject;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.util.SymbolUtils;

import org.apache.commons.lang3.ClassUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

@Log4j2
public class PhpClassBase implements PhpNewable {
	protected Map<String, PhpCallable> staticMethodMap = Maps.newHashMap();
	protected Map<String, PhpCallable> methodMap = Maps.newHashMap();
	protected Map<Symbol, Symbol> staticFieldMap = Maps.newLinkedHashMap();

	public PhpClassBase(Interpreter ip) {
		if (ip == null) {
			return;
		}
		try {
			for (Class<?> clazz : getClass().getDeclaredClasses()) {
				if (ClassUtils.getAllInterfaces(clazz).contains(PhpCallableStatic.class)) {
					PhpCallable callable = getCallable(ip, clazz);
					putStaticMethod(callable);
				} else if (ClassUtils.getAllInterfaces(clazz).contains(PhpCallable.class)) {
					PhpCallable callable = getCallable(ip, clazz);
					putMethod(callable);
				}
			}
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	private PhpCallable getCallable(Interpreter ip, Class<?> clazz) throws InstantiationException,
			IllegalAccessException {
		PhpCallable callable;
		Map<Class<?>, PhpCallable> callableObjectCache = ip.getStorage().getCallableObjectCache();
		if (callableObjectCache.get(clazz) != null) {
			callable = callableObjectCache.get(clazz);
		} else {
			callable = (PhpCallable) clazz.newInstance();
			callableObjectCache.put(clazz, callable);
		}
		return callable;
	}

	@Override
	public void initialize(Interpreter ip) {
		LinkedList<PhpNewable> newableStack = ip.getStorage().getNewableStack();
		if (newableStack.contains(this)) {
			log.info("再帰：" + this + " [" + ip.getSourcePosition() + "]");
			return;
		}
		newableStack.push(this);
		try {
			for (PhpNewable parentClass : SymbolUtils.getClassList(ip, getAbsoluteParentClassName())) {
				// 親クラスの初期化
				InterpreterContext savedContext = ip.getContext();
				ip.setContext(ip.getContext().copyForInitialize(parentClass));
				parentClass.initialize(ip);
				ip.setContext(savedContext);
				// メソッド
				for (Entry<String, PhpCallable> entry : parentClass.getMethodMap().entrySet()) {
					if (getMethod(entry.getKey()) == null) {
						putMethod(entry.getKey(), entry.getValue());
					}
				}
				for (Entry<String, PhpCallable> entry : parentClass.getStaticMethodMap().entrySet()) {
					if (getStaticMethod(entry.getKey()) == null) {
						putStaticMethod(entry.getKey(), entry.getValue());
					}
				}
			}
		} finally {
			newableStack.pop();
		}
	}

	@Override
	public Symbol getStaticField(Interpreter ip, Symbol nameSymbol) {
		LinkedList<PhpNewable> getStaticFieldStack = ip.getStorage().getGetStaticFieldStack();
		if (getStaticFieldStack.contains(this)) {
			return null;
		}
		getStaticFieldStack.push(this);
		try {
			SymbolOperator operator = ip.getOperator();
			Symbol result = operator.createSymbol();
			for (PhpAnyType type : operator.getTypeSet(nameSymbol)) {
				List<Symbol> fieldKeySymbolList = Lists.newArrayList(staticFieldMap.keySet());
				Collections.reverse(fieldKeySymbolList);
				Set<PhpAnyType> setSet = Sets.newHashSet();
				for (Symbol fieldKeySymbol : fieldKeySymbolList) {
					for (PhpAnyType type2 : operator.getTypeSet(fieldKeySymbol)) {
						if (setSet.contains(type2)) {
							continue;
						}
						if (type.equals(type2)) {
							operator.merge(result, staticFieldMap.get(fieldKeySymbol));
							setSet.addAll(operator.getTypeSet(fieldKeySymbol));
							break;
						}
					}
				}
			}
			if (operator.getTypeSet(result).size() == 0 && getAbsoluteParentClassName() != null) {
				for (PhpNewable newable : SymbolUtils.getClassList(ip, getAbsoluteParentClassName())) {
					operator.merge(result, newable.getStaticField(ip, nameSymbol));
				}
			}
			return result;
		} finally {
			getStaticFieldStack.pop();
		}
	}

	@Override
	public PhpCallable getMethod(String name2) {
		return methodMap.get(name2.toLowerCase());
	}

	@Override
	public PhpCallable getStaticMethod(String name2) {
		return staticMethodMap.get(name2.toLowerCase());
	}

	//	@Override
	//	public PhpCallable getAnyMethod(String name2) {
	//		if (getMethod(name2) != null) {
	//			return getMethod(name2);
	//		} else if (getStaticMethod(name2) != null) {
	//			return getStaticMethod(name2);
	//		}
	//		return null;
	//	}

	@Override
	public String getAbsoulteClassName() {
		String name = getClass().getName();
		return SymbolUtils.getAbsoluteNameFromJavaClassName(name);
	}

	@Override
	public String getAbsoluteParentClassName() {
		return null;
	}

	public void putStaticField(Symbol fieldSymbol, Symbol valueSymbol) {
		staticFieldMap.put(fieldSymbol, valueSymbol);
	}

	public void putMethod(String key, PhpCallable value) {
		methodMap.put(key.toLowerCase(), value);
	}

	public void putMethod(PhpCallable value) {
		if (value instanceof PhpFunction) {
			PhpFunction phpFunction = (PhpFunction) value;
			putMethod(phpFunction.getName(), phpFunction);
			return;
		}
		String name = value.getClass().getName();
		name = SymbolUtils.getAbsoluteNameFromJavaClassName(name).substring(1);
		putMethod(name, value);
	}

	public void putStaticMethod(String key, PhpCallable value) {
		staticMethodMap.put(key.toLowerCase(), value);
	}

	public void putStaticMethod(PhpCallable value) {
		if (value instanceof PhpFunction) {
			PhpFunction phpFunction = (PhpFunction) value;
			putStaticMethod(phpFunction.getName(), phpFunction);
			return;
		}
		String name = value.getClass().getName();
		name = SymbolUtils.getAbsoluteNameFromJavaClassName(name).substring(1);
		putStaticMethod(name, value);
	}

	@Override
	public void __construct(Interpreter ip) {
		String name2 = "__construct";
		if (getMethod(name2) != null) {
			PhpCallable method = getMethod(name2);
			new CallDecorator(method).call(ip);
		}
	}

	@Override
	public void __destruct(Interpreter ip, PhpObject phpObject) {
		String name2 = "__destruct";
		if (getMethod(name2) != null) {
			PhpCallable method = getMethod(name2);
			SymbolUtils.callMethod(ip, phpObject, method, Lists.newArrayList());
		}
	}

	@Override
	public Symbol __call(Interpreter ip, PhpObject phpObject, Symbol functionSymbol, List<Symbol> list) {
		SymbolOperator operator = ip.getOperator();
		String name2 = "__call";
		if (getMethod(name2) != null) {
			PhpCallable method = getMethod(name2);
			//			InterpreterContext savedContext = interpreter.getContext();
			PhpArray phpArray = operator.createPhpArray();
			for (int i = 0; i < list.size(); i++) {
				operator.putArrayValue(phpArray, operator.createSymbol(new PhpInteger(i)), list.get(i));
			}
			Symbol phpArraySymbol = operator.createSymbol(phpArray);
			Symbol result = SymbolUtils.callMethod(
					ip, phpObject, method, Lists.newArrayList(functionSymbol, phpArraySymbol));
			return result;
		}
		return null;
	}

	@Override
	public Symbol __callStatic(Interpreter ip, Symbol methodSymbol, List<Symbol> list) {
		SymbolOperator operator = ip.getOperator();
		String name2 = "__callStatic";
		if (getStaticMethod(name2) != null) {
			PhpCallable method = getStaticMethod(name2);
			PhpArray phpArray = operator.createPhpArray();
			for (int i = 0; i < list.size(); i++) {
				operator.putArrayValue(phpArray, operator.createSymbol(new PhpInteger(i)), list.get(i));
			}
			Symbol phpArraySymbol = operator.createSymbol(phpArray);
			Symbol result = SymbolUtils.callStaticMethod(
					ip, method, Lists.newArrayList(methodSymbol, phpArraySymbol));
			return result;
		}
		return null;
	}

	@Override
	public Symbol __get(Interpreter ip, PhpObject phpObject, Symbol keySymbol) {
		String name2 = "__get";
		if (getMethod(name2) != null) {
			PhpCallable method = getMethod(name2);
			Symbol result = SymbolUtils.callMethod(
					ip, phpObject, method, Lists.newArrayList(keySymbol));
			return result;
		}
		return null;
	}

	@Override
	public void __set(Interpreter ip, PhpObject phpObject, Symbol keySymbol, Symbol valueSymbol) {
		String name2 = "__set";
		if (getMethod(name2) != null) {
			PhpCallable method = getMethod(name2);
			SymbolUtils.callMethod(
					ip, phpObject, method, Lists.newArrayList(keySymbol, valueSymbol));
		}
	}

	@Override
	public Symbol __isset(Interpreter ip, PhpObject phpObject, Symbol keySymbol) {
		String name2 = "__isset";
		if (getMethod(name2) != null) {
			PhpCallable method = getMethod(name2);
			Symbol result = SymbolUtils.callMethod(
					ip, phpObject, method, Lists.newArrayList(keySymbol));
			return result;
		}
		return null;
	}

	@Override
	public void __unset(Interpreter ip, PhpObject phpObject, Symbol keySymbol) {
		String name2 = "__unset";
		if (getMethod(name2) != null) {
			PhpCallable method = getMethod(name2);
			SymbolUtils.callMethod(
					ip, phpObject, method, Lists.newArrayList(keySymbol));
		}
	}

	@Override
	public Symbol __sleep(Interpreter ip, PhpObject phpObject) {
		String name2 = "__sleep";
		if (getMethod(name2) != null) {
			PhpCallable method = getMethod(name2);
			Symbol result = SymbolUtils.callMethod(
					ip, phpObject, method, Lists.newArrayList());
			return result;
		}
		return null;
	}

	@Override
	public void __wakeup(Interpreter ip, PhpObject phpObject) {
		String name2 = "__wakeup";
		if (getMethod(name2) != null) {
			PhpCallable method = getMethod(name2);
			SymbolUtils.callMethod(
					ip, phpObject, method, Lists.newArrayList());
		}
	}

	@Override
	public Symbol __toString(Interpreter ip, PhpObject phpObject) {
		SymbolOperator operator = ip.getOperator();
		String name2 = "__toString";
		if (getMethod(name2) != null) {
			PhpCallable method = getMethod(name2);
			Symbol result = SymbolUtils.callMethod(
					ip, phpObject, method, Lists.newArrayList());
			return result;
		}
		return operator.createSymbol("Object");
	}

	@Override
	public Symbol __invoke(Interpreter ip, PhpObject phpObject, List<Symbol> list) {
		String name2 = "__invoke";
		if (getMethod(name2) != null) {
			PhpCallable method = getMethod(name2);
			Symbol result = SymbolUtils.callMethod(
					ip, phpObject, method, list);
			return result;
		}
		return null;
	}

	@Override
	public Symbol __debugInfo(Interpreter ip, PhpObject phpObject) {
		String name2 = "__debugInfo";
		if (getMethod(name2) != null) {
			PhpCallable method = getMethod(name2);
			Symbol result = SymbolUtils.callMethod(
					ip, phpObject, method, Lists.newArrayList());
			return result;
		}
		return null;
	}

	@Override
	public Symbol __set_state(Interpreter ip, PhpObject phpObject) {
		SymbolOperator operator = ip.getOperator();
		String name2 = "__set_state";
		if (getStaticMethod(name2) != null) {
			PhpCallable method = getStaticMethod(name2);
			Symbol result = SymbolUtils.callStaticMethod(
					ip, method, Lists.newArrayList(
							operator.createSymbol(operator.cast(phpObject, CastType.ARRAY))));
			return result;
		}
		return null;
	}

	@Override
	public Symbol __clone(Interpreter ip, PhpObject phpObject) {
		SymbolOperator operator = ip.getOperator();
		String name2 = "__clone";
		if (getMethod(name2) != null) {
			PhpCallable method = getMethod(name2);
			Symbol result = SymbolUtils.callMethod(
					ip, phpObject, method, Lists.newArrayList());
			return result;
		}
		// そのまま返す
		return operator.createSymbol(phpObject);
	}

	public List<PhpCallable> getMethodList() {
		List<PhpCallable> result = Lists.newArrayList();
		result.addAll(methodMap.values());
		result.addAll(staticMethodMap.values());
		return result;
	}

	@Override
	public Map<String, PhpCallable> getMethodMap() {
		return Collections.unmodifiableMap(Maps.newHashMap(methodMap));
	}

	@Override
	public Map<String, PhpCallable> getStaticMethodMap() {
		return Collections.unmodifiableMap(Maps.newHashMap(staticMethodMap));
	}

}
