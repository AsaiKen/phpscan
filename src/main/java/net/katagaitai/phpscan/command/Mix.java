package net.katagaitai.phpscan.command;

import lombok.Value;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.util.SymbolUtils;

@Value
public class Mix implements Command {
	private String result;
	private String left;
	private Operator operator;
	private String right;

	public String toString() {
		return String.format("%s = (%s %s %s)", result,
				SymbolUtils.decodeSymbolString(left),
				operator,
				SymbolUtils.decodeSymbolString(right));
	}

	@Override
	public void accept(Interpreter vm) {
		vm.accept(this);
	}

}
