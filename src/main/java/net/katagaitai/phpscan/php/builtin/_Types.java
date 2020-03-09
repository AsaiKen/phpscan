package net.katagaitai.phpscan.php.builtin;

import java.io.File;

import lombok.extern.log4j.Log4j2;
import net.katagaitai.phpscan.compiler.BuiltinBase;
import net.katagaitai.phpscan.compiler.Compiler;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.compiler.PhpFunction;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.php.types.PhpObject;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.util.Constants;
import net.katagaitai.phpscan.util.FileUtils;
import net.katagaitai.phpscan.util.SymbolUtils;

import com.google.common.collect.Lists;
import com.google.common.io.Files;

public class _Types extends BuiltinBase {
	public _Types(Interpreter ip) {
		super(ip);
	}

	/**
	 * @param int|string $status [optional]
	 * @return void
	 */
	//    function PS_UNRESERVE_PREFIX_die($status = ""){};
	public static class die implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.null_();
		}
	}

	/**
	 * @param int|string $status [optional]
	 * @return void
	 */
	//    function PS_UNRESERVE_PREFIX_exit($status = ""){};
	public static class exit implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.null_();
		}
	}

	/**
	 * @param mixed $var
	 * @return bool
	 */
	//    function PS_UNRESERVE_PREFIX_empty($var){};
	public static class empty implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol arg0 = SymbolUtils.getArgument(ip, 0);
			if (operator.getTypeSet(arg0).size() == 0 //  アクセス不能プロパティ
					&& operator.getSymbol(arg0.getObjectSymbolId()) != null
					&& operator.getSymbol(arg0.getKeySymbolId()) != null) {
				for (PhpObject phpObject : operator.extractPhpObject(operator.getSymbol(arg0.getObjectSymbolId()))) {
					phpObject.getPhpClass().__isset(
							ip, phpObject, operator.getSymbol(arg0.getKeySymbolId()));
				}
			}
			return operator.bool();
		}
	}

	/**
	 * @param mixed $var
	 * @param mixed $_ [optional]
	 * @return bool
	 */
	//    function PS_UNRESERVE_PREFIX_isset($var, ...$_){};
	public static class isset implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			for (int i = 0; i < SymbolUtils.getArgumentSize(ip); i++) {
				Symbol symbol = SymbolUtils.getArgument(ip, i);
				Symbol objectSymbol = operator.getSymbol(symbol.getObjectSymbolId());
				Symbol keySymbol = operator.getSymbol(symbol.getKeySymbolId());
				if (operator.isNull(symbol) && objectSymbol != null && keySymbol != null) {
					for (PhpObject phpObject : operator.extractPhpObject(objectSymbol)) {
						phpObject.getPhpClass().__isset(ip, phpObject, keySymbol);
					}
				}
			}
			return operator.bool();
		}
	}

	/**
	 * @param mixed $var
	 * @param mixed $_ [optional]
	 * @return void
	 */
	//    function PS_UNRESERVE_PREFIX_unset($var, ...$_){};
	public static class unset implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			for (int i = 0; i < SymbolUtils.getArgumentSize(ip); i++) {
				Symbol symbol = SymbolUtils.getArgument(ip, i);
				Symbol objectSymbol = operator.getSymbol(symbol.getObjectSymbolId());
				Symbol keySymbol = operator.getSymbol(symbol.getKeySymbolId());
				if (operator.isNull(symbol) && objectSymbol != null && keySymbol != null) {
					for (PhpObject phpObject : operator.extractPhpObject(objectSymbol)) {
						phpObject.getPhpClass().__unset(
								ip, phpObject, operator.getSymbol(symbol.getKeySymbolId()));
					}
				} else {
					//					operator.assign(symbol, operator.null_());
				}
			}
			return operator.bool();
		}
	}

	/**
	 * @param string $code
	 * @return mixed
	 */
	//    function PS_UNRESERVE_PREFIX_eval($code){};
	@Log4j2
	public static class eval implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol symbol = SymbolUtils.getArgument(ip, 0);
			Symbol result = operator.createSymbol();
			for (String string : operator.getJavaStringList(symbol)) {
				try {
					File file = FileUtils.createTempFile("eval", ".php");
					Files.write(string, file, Constants.DEFAULT_CHARSET);
					Compiler compiler = new Compiler(file);
					PhpFunction phpFunction = compiler.compile();
					//	php > $a = 1;
					//	php > $b = eval('$a;'); → $b == NULL
					//	php > $b = eval('return $a;'); → $b == 1
					Symbol evalResult =
							SymbolUtils.callFunction(ip, phpFunction, Lists.newArrayList());
					operator.merge(result, evalResult);
				} catch (Exception e) {
					log.warn("", e);
				}
			}
			return result;
		}
	}

}
