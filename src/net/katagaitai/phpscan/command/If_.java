package net.katagaitai.phpscan.command;

import lombok.Value;
import net.katagaitai.phpscan.interpreter.Interpreter;

@Value
public class If_ implements Command {
	private String condition;
	private String label;

	@Override
	public String toString() {
		return "// IF " + condition + " == true";
	}

	@Override
	public void accept(Interpreter vm) {
		vm.accept(this);
	}

}
