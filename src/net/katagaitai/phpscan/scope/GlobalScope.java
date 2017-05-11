package net.katagaitai.phpscan.scope;

import lombok.ToString;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;

@ToString(callSuper = true)
public class GlobalScope extends Scope {
	private final Symbol globals;

	public GlobalScope(Interpreter ip, Symbol globals2) {
		super(ip);
		globals = globals2;
	}

	@Override
	public void put(String name, Symbol symbol) {
		super.put(name, symbol);
		SymbolOperator operator = ip.getOperator();
		operator.putArrayValue(globals, name, symbol);
	}
}
