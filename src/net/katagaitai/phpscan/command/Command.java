package net.katagaitai.phpscan.command;

import net.katagaitai.phpscan.interpreter.Interpreter;

public interface Command {
	String toString();

	void accept(Interpreter vm);
}
