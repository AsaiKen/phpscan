package net.katagaitai.phpscan.command;

import lombok.Value;
import net.katagaitai.phpscan.interpreter.Interpreter;

@Value
public class LoadGlobal implements Command {
	private String name;

	public String toString() {
		return String.format("global %s", name);
	}

	@Override
	public void accept(Interpreter vm) {
		vm.accept(this);
	}

}
