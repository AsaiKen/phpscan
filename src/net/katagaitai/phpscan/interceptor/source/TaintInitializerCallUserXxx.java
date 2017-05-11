package net.katagaitai.phpscan.interceptor.source;

import java.util.List;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import net.katagaitai.phpscan.Vulnerability;
import net.katagaitai.phpscan.VulnerabilityCategory;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.interceptor.CallInterceptor;
import net.katagaitai.phpscan.interpreter.CallDecorator;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.php.builtin.Standard_4.call_user_func;
import net.katagaitai.phpscan.php.builtin.Standard_4.call_user_func_array;
import net.katagaitai.phpscan.php.builtin.Standard_4.call_user_method;
import net.katagaitai.phpscan.php.builtin.Standard_4.call_user_method_array;
import net.katagaitai.phpscan.php.builtin.Standard_4.forward_static_call;
import net.katagaitai.phpscan.php.builtin.Standard_4.forward_static_call_array;
import net.katagaitai.phpscan.php.types.PhpArray;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.taint.Taint;
import net.katagaitai.phpscan.util.ScanUtils;
import net.katagaitai.phpscan.util.SymbolUtils;
import net.katagaitai.phpscan.util.TaintUtils;

@RequiredArgsConstructor
public class TaintInitializerCallUserXxx implements CallInterceptor {
	private final Interpreter ip;

	@Override
	public void intercept(CallDecorator decorator) {
		SymbolOperator operator = ip.getOperator();
		PhpCallable callable = decorator.getDecrated();
		//		Symbol resultSymbol = decorator.getResult();
		String functionName = SymbolUtils.getFunctionName(callable);
		if (callable instanceof call_user_func) {
			Symbol functionSymbol = SymbolUtils.getArgument(ip, 0);
			Set<Taint> functionTaintSet = TaintUtils.getFunctionNameTaintSet(functionSymbol.getTaintSet());
			Symbol arg1Symbol = SymbolUtils.getArgument(ip, 1);
			Set<Taint> arg1TaintSet = TaintUtils.getPhpScriptTaintSet(arg1Symbol.getTaintSet());
			if (functionTaintSet.size() > 0 && arg1TaintSet.size() > 0) {
				List<Vulnerability> vulnerabilityList =
						ScanUtils.getVulnerabilityList(ip, VulnerabilityCategory.RCE, functionTaintSet,
								"リフレクションによる任意のコード実行：" + functionName);
				ScanUtils.addVulnerability(ip, vulnerabilityList);
			} else if (functionTaintSet.size() > 0) {
				//				List<Symbol> argumentSymbolList = Lists.newArrayList();
				//				for (int i = 1; i < SymbolUtils.getArgumentSize(ip); i++) {
				//					argumentSymbolList.add(SymbolUtils.getArgument(ip, i));
				//				}
				//				for (PhpCallable callable2 : ip.getFuntionList()) {
				//					if (callable2 instanceof PhpFunction) {
				//						operator.merge(resultSymbol,
				//								SymbolUtils.callFunction(ip, callable2, argumentSymbolList));
				//					}
				//				}
			}
		} else if (callable instanceof call_user_func_array) {
			Symbol functionSymbol = SymbolUtils.getArgument(ip, 0);
			Set<Taint> functionTaintSet = TaintUtils.getFunctionNameTaintSet(functionSymbol.getTaintSet());
			Symbol paramarrSymbol = SymbolUtils.getArgument(ip, 1);
			Symbol arr0Symbol = operator.getArrayValue(paramarrSymbol, operator.createSymbol(0));
			Set<Taint> arr0TaintSet = TaintUtils.getPhpScriptTaintSet(arr0Symbol.getTaintSet());
			if (functionTaintSet.size() > 0 && arr0TaintSet.size() > 0) {
				List<Vulnerability> vulnerabilityList =
						ScanUtils.getVulnerabilityList(ip, VulnerabilityCategory.RCE, functionTaintSet,
								"リフレクションによる任意のコード実行：" + functionName);
				ScanUtils.addVulnerability(ip, vulnerabilityList);
			} else if (functionTaintSet.size() > 0) {
				//				Symbol paramarrSymbol = SymbolUtils.getArgument(ip, 1);
				//				List<Symbol> argumentSymbolList = Lists.newArrayList();
				//				for (int i = 0; i < 100; i++) {
				//					Symbol argumentSymbol = operator.getArrayValue(paramarrSymbol, operator.createSymbol(i));
				//					if (operator.isNull(argumentSymbol)) {
				//						break;
				//					}
				//					argumentSymbolList.add(argumentSymbol);
				//				}
				//				for (PhpCallable callable2 : ip.getFuntionList()) {
				//					if (callable2 instanceof PhpFunction) {
				//						operator.merge(resultSymbol,
				//								SymbolUtils.callFunction(ip, callable2, argumentSymbolList));
				//					}
				//				}
			}
		} else if (callable instanceof call_user_method) {
			Symbol methodnameSymbol = SymbolUtils.getArgument(ip, 0);
			Set<Taint> taintSet = TaintUtils.getFunctionNameTaintSet(methodnameSymbol.getTaintSet());
			if (taintSet.size() > 0) {
				//				Symbol objSymbol = SymbolUtils.getArgument(ip, 1);
				//				List<Symbol> argumentSymbolList = Lists.newArrayList();
				//				for (int i = 2; i < SymbolUtils.getArgumentSize(ip); i++) {
				//					argumentSymbolList.add(SymbolUtils.getArgument(ip, i));
				//				}
				//				for (PhpObject thisObject : operator.extractPhpObject(objSymbol)) {
				//					for (PhpCallable callable2 : thisObject.getPhpClass().getMethodMap().values()) {
				//						operator.merge(resultSymbol,
				//								SymbolUtils.callMethod(ip, thisObject, callable2, argumentSymbolList));
				//					}
				//				}
			}
		} else if (callable instanceof call_user_method_array) {
			Symbol methodnameSymbol = SymbolUtils.getArgument(ip, 0);
			Set<Taint> taintSet = TaintUtils.getFunctionNameTaintSet(methodnameSymbol.getTaintSet());
			if (taintSet.size() > 0) {
				//				Symbol objSymbol = SymbolUtils.getArgument(ip, 1);
				//				Symbol paramarrSymbol = SymbolUtils.getArgument(ip, 2);
				//				List<Symbol> argumentSymbolList = Lists.newArrayList();
				//				for (int i = 0; i < 100; i++) {
				//					Symbol argumentSymbol = operator.getArrayValue(paramarrSymbol, operator.createSymbol(i));
				//					if (operator.isNull(argumentSymbol)) {
				//						break;
				//					}
				//					argumentSymbolList.add(argumentSymbol);
				//				}
				//				for (PhpObject thisObject : operator.extractPhpObject(objSymbol)) {
				//					for (PhpCallable callable2 : thisObject.getPhpClass().getMethodMap().values()) {
				//						operator.merge(resultSymbol,
				//								SymbolUtils.callMethod(ip, thisObject, callable2, argumentSymbolList));
				//					}
				//				}
			}
		} else if (callable instanceof forward_static_call) {
			Symbol functionSymbol = SymbolUtils.getArgument(ip, 0);
			Set<Taint> taintSet = TaintUtils.getFunctionNameTaintSet(functionSymbol.getTaintSet());
			boolean vuln = false;
			if (taintSet.size() > 0) {
				vuln = true;
			} else {
				for (PhpArray phpArray : operator.extractPhpArray(functionSymbol)) {
					Symbol clazzSymbol = operator.getArrayValue(phpArray, 0);
					Symbol methodSymbol = operator.getArrayValue(phpArray, 1);
					if (TaintUtils.getClassNameTaintSet(clazzSymbol.getTaintSet()).size() > 0
							&& TaintUtils.getFunctionNameTaintSet(methodSymbol.getTaintSet()).size() > 0) {
						vuln = true;
					}
				}
			}
			if (vuln) {
				//				List<Symbol> argumentSymbolList = Lists.newArrayList();
				//				for (int i = 1; i < SymbolUtils.getArgumentSize(ip); i++) {
				//					argumentSymbolList.add(SymbolUtils.getArgument(ip, i));
				//				}
				//				for (PhpNewable newable : SymbolUtils.getUserDefinedPhpClassList(ip)) {
				//					for (PhpCallable callable2 : newable.getStaticMethodMap().values()) {
				//						operator.merge(resultSymbol,
				//								SymbolUtils.callFunction(ip, callable2, argumentSymbolList));
				//					}
				//				}
			}
		} else if (callable instanceof forward_static_call_array) {
			Symbol functionSymbol = SymbolUtils.getArgument(ip, 0);
			Set<Taint> taintSet = TaintUtils.getFunctionNameTaintSet(functionSymbol.getTaintSet());
			boolean vuln = false;
			if (taintSet.size() > 0) {
				vuln = true;
			} else {
				for (PhpArray phpArray : operator.extractPhpArray(functionSymbol)) {
					Symbol clazzSymbol = operator.getArrayValue(phpArray, 0);
					Symbol methodSymbol = operator.getArrayValue(phpArray, 1);
					if (TaintUtils.getClassNameTaintSet(clazzSymbol.getTaintSet()).size() > 0
							&& TaintUtils.getFunctionNameTaintSet(methodSymbol.getTaintSet()).size() > 0) {
						vuln = true;
					}
				}
			}
			if (vuln) {
				//				Symbol parametersSymbol = SymbolUtils.getArgument(ip, 1);
				//				List<Symbol> argumentSymbolList = Lists.newArrayList();
				//				for (int i = 0; i < 100; i++) {
				//					Symbol argumentSymbol = operator.getArrayValue(parametersSymbol, operator.createSymbol(i));
				//					if (operator.isNull(argumentSymbol)) {
				//						break;
				//					}
				//					argumentSymbolList.add(argumentSymbol);
				//				}
				//				for (PhpNewable newable : SymbolUtils.getUserDefinedPhpClassList(ip)) {
				//					for (PhpCallable callable2 : newable.getStaticMethodMap().values()) {
				//						operator.merge(resultSymbol,
				//								SymbolUtils.callFunction(ip, callable2, argumentSymbolList));
				//					}
				//				}
			}
		}
	}
}
