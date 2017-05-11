package net.katagaitai.phpscan.command;

import lombok.Value;
import net.katagaitai.phpscan.interpreter.Interpreter;

@Value
public class PutVariable implements Command {
	private String name;
	private String value;

	public String toString() {
		return String.format("$%s = %s", name, value);
	}

	@Override
	public void accept(Interpreter vm) {
		vm.accept(this);
	}

}
