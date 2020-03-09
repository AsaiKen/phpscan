package net.katagaitai.phpscan.php.builtin;

import net.katagaitai.phpscan.compiler.BuiltinBase;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.util.SymbolUtils;

public class Ereg extends BuiltinBase {
	public Ereg(Interpreter ip) {
		super(ip);
	}

	// function ereg ($pattern, $string, array &$regs = null) {}
	public static class ereg implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol stringSymbol = SymbolUtils.getArgument(ip, 1);
			Symbol regsSymbol = SymbolUtils.getArgumentRef(ip, 2);
			operator.merge(regsSymbol, operator.createSymbol(operator.createPhpArrayDummy(stringSymbol)));
			return operator.integer();
		}
	}

	// function ereg_replace ($pattern, $replacement, $string) {}
	public static class ereg_replace implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol replacementSymbol = SymbolUtils.getArgument(ip, 1);
			Symbol stringSymbol = SymbolUtils.getArgument(ip, 2);
			operator.addNewTaintSet(stringSymbol, replacementSymbol.getTaintSet());
			return stringSymbol;
		}
	}

	// function eregi ($pattern, $string, array &$regs = null) {}
	public static class eregi extends ereg {
	}

	// function eregi_replace ($pattern, $replacement, $string) {}
	public static class eregi_replace extends ereg_replace {
	}

	// function split ($pattern, $string, $limit = -1) {}
	public static class split implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol stringSymbol = SymbolUtils.getArgument(ip, 1);
			return operator.createSymbol(operator.createPhpArrayDummy(stringSymbol));
		}
	}

	// function spliti ($pattern, $string, $limit = -1) {}
	public static class spliti extends split {
	}

	// function sql_regcase ($string) {}
	public static class sql_regcase implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, 0);
		}
	}

}
