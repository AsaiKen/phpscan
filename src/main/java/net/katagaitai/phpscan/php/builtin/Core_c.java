package net.katagaitai.phpscan.php.builtin;

import java.io.File;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import net.katagaitai.phpscan.compiler.BuiltinBase;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.compiler.PhpCallableStatic;
import net.katagaitai.phpscan.compiler.PhpClassBase;
import net.katagaitai.phpscan.compiler.PhpNewable;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.interpreter.SourcePosition;
import net.katagaitai.phpscan.php.types.PhpArray;
import net.katagaitai.phpscan.php.types.PhpObject;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.util.SymbolUtils;

import com.google.common.collect.Lists;

public class Core_c extends BuiltinBase {
	public Core_c(Interpreter ip) {
		super(ip);
	}

	public static class Exception extends PhpClassBase {
		public Exception(Interpreter ip) {
			super(ip);
		}

		@Override
		public void initialize(Interpreter interpreter) {
			super.initialize(interpreter);
			//		    protected $message;
			//		    protected $code;
			//		    protected $file;
			//		    protected $line;
			SymbolOperator operator = interpreter.getOperator();
			Symbol thisSymbol = interpreter.getContext().getThisSymbol();
			operator.putFieldValue(thisSymbol, "message", operator.string());
			operator.putFieldValue(thisSymbol, "code", operator.integer());
			File file = interpreter.getContext().getFile();
			operator.putFieldValue(thisSymbol, "file", file != null ? file.getAbsolutePath() : "");
			SourcePosition sp = interpreter.getSourcePosition();
			operator.putFieldValue(thisSymbol, "lineno", sp != null ? sp.getLineno() : 0);
		}

		public static class getMessage implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.getFieldValue(ip.getContext().getThisSymbol(), "message");
			}
		}

		public static class getCode implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.getFieldValue(ip.getContext().getThisSymbol(), "code");
			}
		}

		public static class getFile implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.getFieldValue(ip.getContext().getThisSymbol(), "file");
			}
		}

		public static class getLine implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.getFieldValue(ip.getContext().getThisSymbol(), "line");
			}
		}

		public static class getTrace implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				PhpArray phpArray = operator.createPhpArrayDummy();
				return operator.createSymbol(phpArray);
			}
		}

		public static class getPrevious implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				return ip.getContext().getThisSymbol();
			}
		}

		public static class getTraceAsString implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.string();
			}
		}
	}

	public static class ErrorException extends PhpClassBase {
		public ErrorException(Interpreter ip) {
			super(ip);
		}

		@Override
		public String getAbsoluteParentClassName() {
			return SymbolUtils.getAbsoluteNameFromJavaClassName(Exception.class.getName());
		}

		public static class getSeverity implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.createSymbol(1);
			}
		}

	}

	public static class Closure extends PhpClassBase {
		public Closure(Interpreter ip) {
			super(ip);
		}

		@Getter
		protected List<PhpCallable> callableList = Lists.newArrayList();
		@Setter
		protected Symbol thisSymbol;

		@Override
		public void __construct(Interpreter interpreter) {
			SymbolOperator operator = interpreter.getOperator();
			Symbol functionSymbol = SymbolUtils.getArgument(interpreter, 0);
			for (String functionString : operator.getJavaStringList(functionSymbol)) {
				PhpCallable callable = interpreter.getFunction(functionString);
				if (callable == null) {
					callable = interpreter.getFunction("\\" + functionString);
				}
				if (callable != null) {
					callableList.add(callable);
				}
			}
		}

		public static class bindTo implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				Symbol newthisSymbol = SymbolUtils.getArgument(ip, 0);
				Symbol newscopeSymbol = SymbolUtils.getArgument(ip, 1);
				if (operator.isNull(newscopeSymbol)) {
					newscopeSymbol = operator.createSymbol("static");
				}

				PhpNewable newable = ip.getContext().getThisObject().getPhpClass();
				if (newable instanceof Closure) {
					Closure closure = (Closure) newable;
					closure.setThisSymbol(newthisSymbol);
				}
				return ip.getContext().getThisSymbol();
			}
		}

		public static class bind implements PhpCallableStatic {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				Symbol closureSymbol = SymbolUtils.getArgument(ip, 0);
				Symbol newthisSymbol = SymbolUtils.getArgument(ip, 1);
				Symbol newscopeSymbol = SymbolUtils.getArgument(ip, 2);
				if (operator.isNull(newscopeSymbol)) {
					newscopeSymbol = operator.createSymbol("static");
				}
				for (PhpObject phpObject : operator.extractPhpObject(closureSymbol)) {
					PhpNewable newable = phpObject.getPhpClass();
					if (newable instanceof Closure) {
						Closure closure = (Closure) newable;
						closure.setThisSymbol(newthisSymbol);
					}
				}
				return closureSymbol;
			}
		}

		public static class call_ implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				Symbol newthisSymbol = SymbolUtils.getArgument(ip, 0);
				List<Symbol> argumentSymbolList = ip.getContext().getArgumentSymbolList();
				List<Symbol> list = Lists.newArrayList();
				if (argumentSymbolList.size() > 1) {
					list = ip.getContext().getArgumentSymbolList()
							.subList(1, argumentSymbolList.size());
				}
				PhpNewable newable = ip.getContext().getThisObject().getPhpClass();
				Symbol resultSymbol = operator.createSymbol();
				if (newable instanceof Closure) {
					Closure closure = (Closure) newable;
					for (PhpObject thisObject_ : operator.extractPhpObject(newthisSymbol)) {
						for (PhpCallable callable : closure.getCallableList()) {
							//							InterpreterContext saved = ip.getContext();
							//							ip.setContext(ip.getContext().copyForCallMethod(
							//									callable, thisObject_, list));
							//							operator.merge(resultSymbol, new CallDecorator(callable).call(ip));
							//							ip.setContext(saved);
							operator.merge(resultSymbol,
									SymbolUtils.callMethod(ip, thisObject_, callable, list));
						}
					}
				}
				return resultSymbol;
			}
		}

	}
}