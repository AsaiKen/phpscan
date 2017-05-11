package net.katagaitai.phpscan.compiler;

import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.symbol.Symbol;

public interface PhpCallable {
	Symbol call(Interpreter ip);
}
