package net.katagaitai.phpscan.command;

import lombok.Value;
import net.katagaitai.phpscan.interpreter.Interpreter;

@Value
public class Instanceof implements Command {
	private String result;
	private String target;
	private String clazz;

	public String toString() {
		return String.format("%s = %s instanceof %s", result, target, clazz);
	}

	@Override
	public void accept(Interpreter vm) {
		vm.accept(this);
	}

}
