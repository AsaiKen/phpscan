package net.katagaitai.phpscan.interceptor.source;

import java.util.Set;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.katagaitai.phpscan.interceptor.CallInterceptor;
import net.katagaitai.phpscan.interpreter.CallDecorator;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.php.builtin.Mbstring.mb_parse_str;
import net.katagaitai.phpscan.php.builtin.Standard_2.parse_str;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.taint.Taint;
import net.katagaitai.phpscan.util.SymbolUtils;

@Log4j2
@RequiredArgsConstructor
public class TaintInitializerParseStr implements CallInterceptor {
	private final Interpreter ip;

	@Override
	public void intercept(CallDecorator decorator) {
		SymbolOperator operator = ip.getOperator();
		int size = SymbolUtils.getArgumentSize(ip);
		if (size > 1) {
			return;
		}
		if (decorator.getDecorated() instanceof parse_str) {
			// 現在のスコープを汚染
			Symbol strSymbol = SymbolUtils.getArgument(ip, 0);
			Set<Taint> taintSet = strSymbol.getTaintSet();
			for (String name : ip.getScopeStack().peek().getMap().keySet()) {
				Symbol symbol = ip.getSymbolOrCreate(name);
				operator.addNewTaintSet(symbol, taintSet);
				log.debug("テイント：" + symbol);
			}
		} else if (decorator.getDecorated() instanceof mb_parse_str) {
			// グローバルスコープを汚染
			Symbol encodedstringSymbol = SymbolUtils.getArgument(ip, 0);
			Set<Taint> taintSet = encodedstringSymbol.getTaintSet();
			for (String name : ip.getGlobalScope().getMap().keySet()) {
				Symbol symbol = ip.getGlobalScope().getOrPhpNull(name);
				operator.addNewTaintSet(symbol, taintSet);
				log.debug("テイント：" + symbol);
			}
		}
	}
}
