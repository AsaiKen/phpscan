package net.katagaitai.phpscan.command;

import lombok.Value;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.util.SymbolUtils;

@Value
public class PutArrayValue implements Command {
	private String array;
	private String key;
	private String value;

	public String toString() {
		return String.format("%s[%s] = %s", array, key == null ? "" :
				SymbolUtils.decodeSymbolString(key), SymbolUtils.decodeSymbolString(value));
	}

	@Override
	public void accept(Interpreter vm) {
		vm.accept(this);
	}

}
