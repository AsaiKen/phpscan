package net.katagaitai.phpscan.command;

import lombok.Value;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.util.SymbolUtils;

@Value
public class Include implements Command {
	private String result;
	private String path;

	public String toString() {
		return String.format("%s = include %s", result, SymbolUtils.decodeSymbolString(path));
	}

	@Override
	public void accept(Interpreter vm) {
		vm.accept(this);
	}

}
