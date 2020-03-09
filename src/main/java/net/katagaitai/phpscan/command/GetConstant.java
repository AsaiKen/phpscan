package net.katagaitai.phpscan.command;

import lombok.Value;
import net.katagaitai.phpscan.interpreter.Interpreter;

@Value
public class GetConstant implements Command {
	private String result;
	private String name;

	public String toString() {
		return String.format("%s = constant(\"%s\")", result, name);
	}

	@Override
	public void accept(Interpreter vm) {
		vm.accept(this);
	}

}
