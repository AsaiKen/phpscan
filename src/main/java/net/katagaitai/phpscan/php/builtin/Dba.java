package net.katagaitai.phpscan.php.builtin;

import net.katagaitai.phpscan.compiler.BuiltinBase;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.php.types.PhpResource;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.util.Constants;
import net.katagaitai.phpscan.util.SymbolUtils;

public class Dba extends BuiltinBase {
	public Dba(Interpreter ip) {
		super(ip);
	}

	// function dba_open ($path, $mode, $handler = null, $_ = null) {}
	public static class dba_open implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			PhpResource phpResource = operator.createPhpResource();
			Symbol symbol = ip.getGlobalScope().getOrPhpNull(Constants.DBA_HANDLER_ARRAY_VARIABLE);
			operator.putArrayValue(symbol,
					operator.getNextIndex(symbol),
					operator.createSymbol(phpResource));
			return operator.createSymbol(phpResource);
		}
	}

	// function dba_popen ($path, $mode, $handler = null, $_ = null) {}
	public static class dba_popen implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			PhpResource phpResource = operator.createPhpResource();
			Symbol symbol = ip.getGlobalScope().getOrPhpNull(Constants.DBA_HANDLER_ARRAY_VARIABLE);
			operator.putArrayValue(symbol,
					operator.getNextIndex(symbol),
					operator.createSymbol(phpResource));
			return operator.createSymbol(phpResource);
		}
	}

	// function dba_close ($handle) {}
	public static class dba_close implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.null_();
		}
	}

	// function dba_delete ($key, $handle) {}
	public static class dba_delete implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function dba_exists ($key, $handle) {}
	public static class dba_exists implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function dba_fetch ($key, $handle) {}
	public static class dba_fetch implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol keySymbol = SymbolUtils.getArgument(ip, 0);
			Symbol handleSymbol = SymbolUtils.getArgument(ip, 1);
			Symbol resultSymbol = operator.createSymbol();
			for (PhpResource phpResource : operator.extractPhpResource(handleSymbol)) {
				operator.merge(resultSymbol, operator.getResourceValue(phpResource, keySymbol));
			}
			return resultSymbol;
		}
	}

	// function dba_insert ($key, $value, $handle) {}
	public static class dba_insert implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol keySymbol = SymbolUtils.getArgument(ip, 0);
			Symbol valueSymbol = SymbolUtils.getArgument(ip, 1);
			Symbol handleSymbol = SymbolUtils.getArgument(ip, 2);
			for (PhpResource phpResource : operator.extractPhpResource(handleSymbol)) {
				operator.putResourceValue(phpResource, keySymbol, valueSymbol);
			}
			return operator.bool();
		}
	}

	// function dba_replace ($key, $value, $handle) {}
	public static class dba_replace extends dba_insert {
	}

	// function dba_firstkey ($handle) {}
	public static class dba_firstkey implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol handleSymbol = SymbolUtils.getArgument(ip, 0);
			Symbol resultSymbol = operator.createSymbol();
			for (PhpResource phpResource : operator.extractPhpResource(handleSymbol)) {
				operator.merge(resultSymbol, operator.getMergedResourceKeySymbol(phpResource));
			}
			return resultSymbol;
		}
	}

	// function dba_nextkey ($handle) {}
	public static class dba_nextkey extends dba_firstkey {
	}

	// function dba_optimize ($handle) {}
	public static class dba_optimize implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function dba_sync ($handle) {}
	public static class dba_sync implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function dba_handlers ($full_info = false) {}
	public static class dba_handlers implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			Symbol symbol = ip.getGlobalScope().getOrPhpNull(Constants.DBA_HANDLER_ARRAY_VARIABLE);
			return symbol;
		}
	}

	// function dba_list () {}
	public static class dba_list implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array();
		}
	}

	// function dba_key_split ($key) {}
	public static class dba_key_split implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array();
		}
	}

}
