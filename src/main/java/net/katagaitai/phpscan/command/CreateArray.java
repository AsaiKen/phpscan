package net.katagaitai.phpscan.command;

import lombok.Value;
import net.katagaitai.phpscan.interpreter.Interpreter;

@Value
public class CreateArray implements Command {
	private String array;

	public String toString() {
		return String.format("%s = array()", array);
	}

	@Override
	public void accept(Interpreter vm) {
		vm.accept(this);
	}

}
