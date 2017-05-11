package net.katagaitai.phpscan.command;

import lombok.Value;
import net.katagaitai.phpscan.compiler.PhpFunction;
import net.katagaitai.phpscan.interpreter.Interpreter;

@Value
public class CreateFunction implements Command {
	private PhpFunction phpFunction;

	public String toString() {
		return phpFunction.toString();
	}

	@Override
	public void accept(Interpreter vm) {
		vm.accept(this);
	}

}
