package net.katagaitai.phpscan.command;

import lombok.Value;
import net.katagaitai.phpscan.interpreter.Interpreter;

@Value
public class EndNamespace implements Command {
	private String namespace;

	public String toString() {
		return String.format("} // namespace %s end", namespace);
	}

	@Override
	public void accept(Interpreter vm) {
		vm.accept(this);
	}

}
