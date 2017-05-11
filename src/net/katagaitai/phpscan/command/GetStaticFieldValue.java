package net.katagaitai.phpscan.command;

import lombok.Value;
import net.katagaitai.phpscan.interpreter.Interpreter;

@Value
public class GetStaticFieldValue implements Command {
	private String result;
	private String clazz;
	private String name;

	public String toString() {
		return String.format("%s = %s::$%s", result, clazz, name);
	}

	@Override
	public void accept(Interpreter vm) {
		vm.accept(this);
	}

}
