package net.katagaitai.phpscan.interpreter;

import java.util.LinkedList;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.compiler.PhpFunction;
import net.katagaitai.phpscan.interceptor.CallInterceptor;
import net.katagaitai.phpscan.interceptor.Interceptor;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.util.Constants;
import net.katagaitai.phpscan.util.SymbolUtils;

import com.google.common.collect.Maps;

@Log4j2
@RequiredArgsConstructor
public class CallDecorator implements PhpCallable {
	@Getter
	private final PhpCallable decrated;
	@Getter
	private Symbol result;

	@Override
	public Symbol call(Interpreter ip) {
		SymbolOperator operator = ip.getOperator();

		// callableStack
		LinkedList<PhpCallable> callableStack = ip.getStorage().getCallableStack();
		if (callableStack.contains(decrated)) {
			String string = SymbolUtils.getFunctionName(decrated);
			log.warn("再帰：" + string + " [" + ip.getSourcePosition() + "]");
			return operator.createNull();
		}

		// callCountMap
		if (SymbolUtils.hasTaint(ip, ip.getContext().getArgumentSymbolList())) {
			// 引数にテイントがあれば必ず実行する
		} else if (decrated instanceof PhpFunction) {
			String className = "\\";
			if (ip.getContext().getThisObject() != null
					&& ip.getContext().getThisObject().getPhpClass() != null) {
				className = ip.getContext().getThisObject().getPhpClass().getAbsoulteClassName();
			}
			if (ip.getCallCountMap().get(className) == null) {
				ip.getCallCountMap().put(className, Maps.newHashMap());
			}
			if (ip.getCallCountMap().get(className).get(decrated) == null) {
				ip.getCallCountMap().get(className).put(decrated, 0);
			}
			int count = ip.getCallCountMap().get(className).get(decrated);
			if (count >= Constants.MAX_CALL_COUNT) {
				// ユーザ定義関数かつN回以上呼んでいる場合は高速化のために実行をスキップする
				String string = SymbolUtils.getFunctionName(decrated);
				log.warn("省略：" + string + " [" + ip.getSourcePosition() + "]");
				return operator.createNull();
			}
		}

		SourcePosition savedSourcePosition = ip.getSourcePosition();
		callableStack.push(decrated);
		// 戻り値
		Symbol returnValueSymbol = operator.createSymbol();
		operator.incrementReference(returnValueSymbol);
		ip.getContext().setReturnValueSymbol(returnValueSymbol);
		try {
			result = decrated.call(ip);
			if (operator.getTypeSet(result).size() == 0) {
				result = operator.createNull();
			}
			return result;
		} finally {
			ip.setSourcePosition(savedSourcePosition);
			callableStack.pop();
			if (decrated instanceof PhpFunction) {
				String className = "\\";
				if (ip.getContext().getThisObject() != null
						&& ip.getContext().getThisObject().getPhpClass() != null) {
					className = ip.getContext().getThisObject().getPhpClass().getAbsoulteClassName();
				}
				if (ip.getCallCountMap().get(className) == null) {
					ip.getCallCountMap().put(className, Maps.newHashMap());
				}
				if (ip.getCallCountMap().get(className).get(decrated) == null) {
					ip.getCallCountMap().get(className).put(decrated, 0);
				}
				int count = ip.getCallCountMap().get(className).get(decrated);
				ip.getCallCountMap().get(className).put(decrated, count + 1);
			}
			for (Interceptor interceptor : ip.getInterceptorList()) {
				if (interceptor instanceof CallInterceptor) {
					((CallInterceptor) interceptor).intercept(this);
				}
			}
		}
	}

}
