package net.katagaitai.phpscan.interceptor.source;

import java.util.List;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import net.katagaitai.phpscan.Vulnerability;
import net.katagaitai.phpscan.VulnerabilityCategory;
import net.katagaitai.phpscan.command.CallFunction;
import net.katagaitai.phpscan.command.CallMethod;
import net.katagaitai.phpscan.command.CallStaticMethod;
import net.katagaitai.phpscan.command.Command;
import net.katagaitai.phpscan.command.GetArrayValue;
import net.katagaitai.phpscan.command.GetFieldValue;
import net.katagaitai.phpscan.command.GetVariable;
import net.katagaitai.phpscan.command.New;
import net.katagaitai.phpscan.command.PutVariable;
import net.katagaitai.phpscan.interceptor.CommandInterceptor;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.taint.Taint;
import net.katagaitai.phpscan.util.Constants;
import net.katagaitai.phpscan.util.ScanUtils;
import net.katagaitai.phpscan.util.TaintUtils;

@RequiredArgsConstructor
public class TaintInitializerReflection implements CommandInterceptor {
	private final Interpreter ip;

	@Override
	public void intercept(Command command) {
		if (command instanceof GetVariable) {
			GetVariable getVariable = (GetVariable) command;
			doGetVariable(getVariable);
		} else if (command instanceof PutVariable) {
			PutVariable putVariable = (PutVariable) command;
			doPutVariable(putVariable);
		} else if (command instanceof CallFunction) {
			// TODO callbackでのリフレクション
			CallFunction callFunction = (CallFunction) command;
			doCallFunction(callFunction);
		} else if (command instanceof CallMethod) {
			// TODO callbackでのリフレクション
			CallMethod callMethod = (CallMethod) command;
			doCallMethod(callMethod);
		} else if (command instanceof CallStaticMethod) {
			// TODO callbackでのリフレクション
			CallStaticMethod callStaticMethod = (CallStaticMethod) command;
			doCallStaticMethod(callStaticMethod);
		} else if (command instanceof New) {
			New new_ = (New) command;
			doNew(new_);
		} else if (command instanceof GetArrayValue) {
			GetArrayValue getArrayValue = (GetArrayValue) command;
			doGetArrayValue(getArrayValue);
		} else if (command instanceof GetFieldValue) {
			GetFieldValue getFieldValue = (GetFieldValue) command;
			doGetFieldValue(getFieldValue);
		}
	}

	private void doGetFieldValue(GetFieldValue getFieldValue) {
		SymbolOperator operator = ip.getOperator();
		Symbol resultSymbol = ip.getSymbolOrCreate(getFieldValue.getResult());
		Symbol objectSymbol = ip.getSymbolOrCreate(getFieldValue.getObject());
		Symbol nameSymbol = ip.getSymbolOrCreate(getFieldValue.getName());
		Set<Taint> taintSet = nameSymbol.getTaintSet();
		if (taintSet.size() > 0) {
			// OK
		} else {
			return;
		}
		operator.merge(resultSymbol, operator.getMergedFieldValueSymbol(objectSymbol));
	}

	private void doGetArrayValue(GetArrayValue getArrayValue) {
		SymbolOperator operator = ip.getOperator();
		Symbol resultSymbol = ip.getSymbolOrCreate(getArrayValue.getResult());
		Symbol arraySymbol = ip.getSymbolOrCreate(getArrayValue.getArray());
		Symbol nameSymbol = ip.getSymbolOrCreate(getArrayValue.getName());
		Set<Taint> taintSet = nameSymbol.getTaintSet();
		if (taintSet.size() > 0) {
			// OK
		} else {
			return;
		}
		operator.merge(resultSymbol, operator.getMergedArrayValueSymbol(arraySymbol));
	}

	private void doCallFunction(CallFunction callFunction) {
		//		SymbolOperator operator = ip.getOperator();
		//		Symbol resultSymbol = ip.getSymbolOrCreate(callFunction.getResult());
		String function = callFunction.getFunction();
		Symbol functionSymbol = ip.getSymbolOrCreate(function);
		Set<Taint> functiontTaintSet = TaintUtils.getFunctionNameTaintSet(functionSymbol.getTaintSet());
		if (callFunction.getArgumentList().size() > 0) {
			// OK
		} else {
			return;
		}
		String arg0 = callFunction.getArgumentList().get(0);
		Symbol arg0Symbol = ip.getSymbolOrCreate(arg0);
		Set<Taint> arg0TaintSet = TaintUtils.getPhpScriptTaintSet(arg0Symbol.getTaintSet());
		if (functiontTaintSet.size() > 0 && arg0TaintSet.size() > 0) {
			List<Vulnerability> vulnerabilityList =
					ScanUtils.getVulnerabilityList(ip, VulnerabilityCategory.RCE, functiontTaintSet,
							"リフレクションによる任意のコード実行：" + function);
			ScanUtils.addVulnerability(ip, vulnerabilityList);
		} else if (functiontTaintSet.size() > 0) {
		} else {
			return;
		}

		//		List<PhpCallable> callableList = Lists.newArrayList(ip.getFuntionList());
		//		List<String> argumentList = callFunction.getArgumentList();
		//		List<Symbol> argumentSymbolList = Lists.newArrayList();
		//		for (String argument : argumentList) {
		//			Symbol argumentSymbol = ip.getSymbolOrCreate(argument);
		//			argumentSymbolList.add(argumentSymbol);
		//		}
		//		for (PhpCallable callable : callableList) {
		//			operator.merge(resultSymbol,
		//					SymbolUtils.callFunction(ip, callable, argumentSymbolList));
		//		}
	}

	private void doCallStaticMethod(CallStaticMethod callStaticMethod) {
		//		SymbolOperator operator = ip.getOperator();
		//		Symbol resultSymbol = ip.getSymbolOrCreate(callStaticMethod.getResult());
		String clazz = callStaticMethod.getClazz();
		Symbol clazzSymbol = ip.getSymbolOrCreate(clazz);
		Set<Taint> clazzTaintSet = TaintUtils.getClassNameTaintSet(clazzSymbol.getTaintSet());
		String method = callStaticMethod.getMethod();
		Symbol methodSymbol = ip.getSymbolOrCreate(method);
		Set<Taint> methodTaintSet = TaintUtils.getFunctionNameTaintSet(methodSymbol.getTaintSet());
		if (clazzTaintSet.size() > 0 && methodTaintSet.size() > 0) {
		} else if (clazzTaintSet.size() > 0) {
		} else if (methodTaintSet.size() > 0) {
		} else {
			return;
		}

		//		List<String> argumentList = callStaticMethod.getArgumentList();
		//		List<Symbol> argumentSymbolList = Lists.newArrayList();
		//		for (String argument : argumentList) {
		//			Symbol argumentSymbol = ip.getSymbolOrCreate(argument);
		//			argumentSymbolList.add(argumentSymbol);
		//		}
		//		List<PhpNewable> newableList = Lists.newArrayList();
		//		if (clazzTaintSet.size() > 0) {
		//			newableList = SymbolUtils.getUserDefinedPhpClassList(ip);
		//		} else {
		//			for (PhpAnyType type : operator.getTypeSet(clazzSymbol)) {
		//				String className;
		//				if (type instanceof PhpObject) {
		//					className = ((PhpObject) type).getPhpClass().getAbsoulteClassName();
		//				} else {
		//					className = operator.toPhpString(type).getString();
		//				}
		//				for (PhpNewable phpClass : SymbolUtils.getClassList(ip, className)) {
		//					newableList.add(phpClass);
		//				}
		//			}
		//		}
		//		for (PhpNewable newable : newableList) {
		//			if (methodTaintSet.size() > 0) {
		//				for (PhpCallable callable : newable.getStaticMethodMap().values()) {
		//					operator.merge(resultSymbol,
		//							SymbolUtils.callStaticMethod(ip, callable, argumentSymbolList));
		//				}
		//				operator.merge(resultSymbol,
		//						newable.__callStatic(ip, operator.createSymbol(Constants.DUMMY_STRING), argumentSymbolList));
		//			} else {
		//				for (String methodName : operator.getJavaStringList(methodSymbol)) {
		//					PhpCallable callable = newable.getStaticMethod(methodName);
		//					if (callable == null) {
		//						operator.merge(resultSymbol,
		//								newable.__callStatic(ip, operator.createSymbol(methodName), argumentSymbolList));
		//					} else {
		//						operator.merge(resultSymbol,
		//								SymbolUtils.callStaticMethod(ip, callable, argumentSymbolList));
		//					}
		//				}
		//			}
		//		}
	}

	private void doCallMethod(CallMethod callMethod) {
		//		SymbolOperator operator = ip.getOperator();
		//		Symbol resultSymbol = ip.getSymbolOrCreate(callMethod.getResult());
		String method = callMethod.getMethod();
		Symbol methodSymbol = ip.getSymbolOrCreate(method);
		Set<Taint> taintSet = TaintUtils.getFunctionNameTaintSet(methodSymbol.getTaintSet());
		if (taintSet.size() > 0) {
		} else {
			return;
		}

		//		List<String> argumentList = callMethod.getArgumentList();
		//		List<Symbol> argumentSymbolList = Lists.newArrayList();
		//		for (String argument : argumentList) {
		//			Symbol argumentSymbol = ip.getSymbolOrCreate(argument);
		//			argumentSymbolList.add(argumentSymbol);
		//		}
		//		String object = callMethod.getObject();
		//		Symbol objectSymbol = ip.getSymbolOrCreate(object);
		//		for (PhpObject thisObject : operator.extractPhpObject(objectSymbol)) {
		//			for (PhpCallable callable : thisObject.getPhpClass().getMethodMap().values()) {
		//				operator.merge(resultSymbol,
		//						SymbolUtils.callMethod(ip, thisObject, callable, argumentSymbolList));
		//			}
		//		}
	}

	private void doNew(New new_) {
		//		SymbolOperator operator = ip.getOperator();
		//		Symbol resultSymbol = ip.getSymbolOrCreate(new_.getResult());
		String clazz = new_.getClazz();
		Symbol clazzSymbol = ip.getSymbolOrCreate(clazz);
		Set<Taint> taintSet = TaintUtils.getClassNameTaintSet(clazzSymbol.getTaintSet());
		if (taintSet.size() > 0) {
		} else {
			return;
		}

		//		List<String> argumentList = new_.getArgumentList();
		//		List<Symbol> argumentSymbolList = Lists.newArrayList();
		//		for (String argument : argumentList) {
		//			Symbol argumentSymbol = ip.getSymbolOrCreate(argument);
		//			argumentSymbolList.add(argumentSymbol);
		//		}
		//		if (taintSet.size() > 0) {
		//			List<PhpNewable> newableList = SymbolUtils.getUserDefinedPhpClassList(ip);
		//			for (PhpNewable newable : newableList) {
		//				operator.merge(resultSymbol, SymbolUtils.new_(ip, newable, argumentSymbolList));
		//			}
		//		}
	}

	private void doGetVariable(GetVariable getVariable) {
		SymbolOperator operator = ip.getOperator();
		String result = getVariable.getResult();
		Symbol resultSymbol = ip.getSymbolOrCreate(result);
		String name = getVariable.getName();
		Symbol nameSymbol = ip.getSymbolOrCreate(name);
		Set<Taint> taintSet = nameSymbol.getTaintSet();
		if (taintSet.size() > 0) {
			// OK
		} else {
			return;
		}

		// スーパーグローバル
		for (String string : Constants.SUPERGLOBAL_NAMES) {
			Symbol symbol = ip.getGlobalScope().getOrPhpNull(string);
			operator.merge(resultSymbol, symbol);
		}
		// スコープ内の他のシンボル
		for (String string : ip.getScopeStack().peek().getMap().keySet()) {
			if (string.startsWith("$" + Constants.PREFIX) || string.equals(result)) {
				continue;
			}
			Symbol symbol = ip.getScopeStack().peek().getOrPhpNull(string);
			operator.merge(resultSymbol, symbol);
		}
	}

	private void doPutVariable(PutVariable putVariable) {
		SymbolOperator operator = ip.getOperator();
		String value = putVariable.getValue();
		Symbol valueSymbol = ip.getSymbolOrCreate(value);
		String name = putVariable.getName();
		Symbol nameSymbol = ip.getSymbolOrCreate(name);
		Set<Taint> taintSet = nameSymbol.getTaintSet();
		if (taintSet.size() > 0) {
			// OK
		} else {
			return;
		}

		// スーパーグローバル
		for (String string : Constants.SUPERGLOBAL_NAMES) {
			Symbol symbol = ip.getGlobalScope().getOrPhpNull(string);
			operator.merge(symbol, valueSymbol);
		}
		// スコープ内の他のシンボル
		for (String string : ip.getScopeStack().peek().getMap().keySet()) {
			if (string.startsWith("$" + Constants.PREFIX) || string.equals(value)) {
				continue;
			}
			Symbol symbol = ip.getScopeStack().peek().getOrPhpNull(string);
			operator.merge(symbol, valueSymbol);
		}
	}

}
