package net.katagaitai.phpscan.interceptor.source;

import java.util.Set;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.interceptor.CallInterceptor;
import net.katagaitai.phpscan.interpreter.CallDecorator;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.php.builtin.Curl;
import net.katagaitai.phpscan.php.builtin.Curl.curl_init;
import net.katagaitai.phpscan.php.types.PhpResource;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.taint.Taint;
import net.katagaitai.phpscan.util.SymbolUtils;
import net.katagaitai.phpscan.util.TaintUtils;

@Log4j2
@RequiredArgsConstructor
public class TaintInitializerCurlAPI implements CallInterceptor {
	private final Interpreter ip;

	@Override
	public void intercept(CallDecorator decorator) {
		SymbolOperator operator = ip.getOperator();
		PhpCallable callable = decorator.getDecorated();
		if (callable instanceof curl_init) {
			Symbol urlSymbol = SymbolUtils.getArgument(ip, 0);
			Set<Taint> taintSet = TaintUtils.getUrlTaint(urlSymbol.getTaintSet());
			if (taintSet.size() > 0) {
				// OK
			} else {
				return;
			}
			Symbol resultSymbol = decorator.getResult();
			for (PhpResource phpResource : operator.extractPhpResource(resultSymbol)) {
				Symbol contentSymbol =
						operator.getResourceValue(phpResource, Curl.CURL_CONTENT);
				operator.addNewTaintSet(contentSymbol, taintSet);
				log.debug("テイント：" + contentSymbol);
			}
		}
	}

}
