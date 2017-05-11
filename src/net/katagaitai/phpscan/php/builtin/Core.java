package net.katagaitai.phpscan.php.builtin;

import java.io.File;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import lombok.extern.log4j.Log4j2;
import net.katagaitai.phpscan.command.CastType;
import net.katagaitai.phpscan.compiler.BuiltinBase;
import net.katagaitai.phpscan.compiler.Compiler;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.compiler.PhpClass;
import net.katagaitai.phpscan.compiler.PhpFunction;
import net.katagaitai.phpscan.compiler.PhpNewable;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.php.types.PhpAnyType;
import net.katagaitai.phpscan.php.types.PhpArray;
import net.katagaitai.phpscan.php.types.PhpBoolean;
import net.katagaitai.phpscan.php.types.PhpObject;
import net.katagaitai.phpscan.php.types.PhpString;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.symbol.SymbolStack;
import net.katagaitai.phpscan.util.Constants;
import net.katagaitai.phpscan.util.FileUtils;
import net.katagaitai.phpscan.util.SymbolUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.io.Files;

@Log4j2
public class Core extends BuiltinBase {
	public Core(Interpreter ip) {
		super(ip);
		SymbolOperator operator = ip.getOperator();
		putConstant("\\DEBUG_BACKTRACE_PROVIDE_OBJECT", operator.createSymbol(0));
		putConstant("\\DEBUG_BACKTRACE_IGNORE_ARGS", operator.createSymbol(0));
	}

	public static class zend_version implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.createSymbol("2.5.0");
		}
	}

	public static class func_num_args implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			if (ip.getContext().getPrevContext() == null) {
				return operator.createSymbol(Constants.DUMMY_ARRAY_SIZE);
			} else {
				return operator.createSymbol(ip.getContext().getPrevContext()
						.getArgumentSymbolList().size());
			}
		}
	}

	// function func_get_arg ($arg_num) {}
	public static class func_get_arg implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol argnumSymbol = SymbolUtils.getArgument(ip, 0);
			Symbol resultSymbol = operator.createSymbol();
			if (ip.getContext().getPrevContext() == null) {
				return operator.string();
			} else {
				for (long long_ : operator.getJavaLongList(argnumSymbol)) {
					List<Symbol> list = ip.getContext().getPrevContext()
							.getArgumentSymbolList();
					if (long_ < list.size()) {
						operator.merge(resultSymbol, list.get((int) long_));
					} else {
						operator.merge(resultSymbol, operator.null_());
					}
				}
			}
			return resultSymbol;
		}
	}

	public static class func_get_args implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			if (ip.getContext().getPrevContext() == null) {
				PhpArray phpArray = operator.createPhpArrayDummy();
				return operator.createSymbol(phpArray);
			} else {
				PhpArray phpArray = operator.createPhpArray();
				List<Symbol> list = ip.getContext().getPrevContext()
						.getArgumentSymbolList();
				for (int i = 0; i < list.size(); i++) {
					operator.putArrayValue(phpArray, operator.createSymbol(i), list.get(i));
				}
				return operator.createSymbol(phpArray);
			}
		}
	}

	// function strlen ($string) {}
	public static class strlen implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function strcmp ($str1, $str2) {}
	public static class strcmp implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function strncmp ($str1, $str2, $len) {}
	public static class strncmp implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function strcasecmp ($str1, $str2) {}
	public static class strcasecmp implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function strncasecmp ($str1, $str2, $len) {}
	public static class strncasecmp implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function each (array &$array) {}
	public static class each implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol arraySymbol = SymbolUtils.getArgument(ip, 0);
			Symbol zeroSymbol = operator.createSymbol();
			Symbol keySymbol = operator.createSymbol();
			Symbol oneSymbol = operator.createSymbol();
			Symbol valueSymbol = operator.createSymbol();
			for (PhpObject phpObject : operator.extractPhpObject(arraySymbol)) {
				PhpCallable keyCallable = phpObject.getPhpClass().getMethod("key");
				PhpCallable currentCallable = phpObject.getPhpClass().getMethod("current");
				if (keyCallable != null && currentCallable != null) {
					Symbol keySymbol2 =
							SymbolUtils.callMethod(ip, phpObject, keyCallable, Lists.newArrayList());
					operator.merge(zeroSymbol, keySymbol2);
					operator.merge(keySymbol, keySymbol2);

					//					InterpreterContext saved2 = ip.getContext();
					//					ip.setContext(ip.getContext().copyForCallMethod(currentCallable,
					//							phpObject, Lists.newArrayList()));
					//					Symbol valueSymbol2 = new CallDecorator(currentCallable).call(ip);
					//					ip.setContext(saved2);
					Symbol valueSymbol2 =
							SymbolUtils.callMethod(ip, phpObject, currentCallable, Lists.newArrayList());
					operator.merge(oneSymbol, valueSymbol2);
					operator.merge(valueSymbol, valueSymbol2);
				}
			}
			PhpArray phpArray = operator.createPhpArray();
			operator.putArrayValue(phpArray, 0, keySymbol);
			operator.putArrayValue(phpArray, "key", keySymbol);
			operator.putArrayValue(phpArray, 1, valueSymbol);
			operator.putArrayValue(phpArray, "value", valueSymbol);
			return operator.createSymbol(phpArray);
		}
	}

	// function error_reporting ($level = null) {}
	public static class error_reporting implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function define ($name, $value, $case_insensitive = false) {}
	public static class define implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol nameSymbol = SymbolUtils.getArgument(ip, 0);
			Symbol valueSymbol = SymbolUtils.getArgument(ip, 1);
			for (String string : operator.getJavaStringList(nameSymbol)) {
				ip.putConstant("\\" + string, valueSymbol);
			}
			return operator.null_();
		}
	}

	// function defined ($name) {}
	public static class defined implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function get_class ($object = null) {}
	public static class get_class implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol objectSymbol = SymbolUtils.getArgument(ip, 0);
			Set<PhpAnyType> set = Sets.newHashSet();
			for (PhpObject phpObject : operator.extractPhpObject(objectSymbol)) {
				set.add(new PhpString(phpObject.getPhpClass().getAbsoulteClassName().substring(1)));
			}
			return operator.createSymbol(set);
		}
	}

	// function get_called_class () {}
	public static class get_called_class implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			PhpClass phpClass = ip.getContext().getPhpClass();
			if (phpClass == null) {
				return operator.string();
			} else {
				return operator.createSymbol(phpClass.getAbsoulteClassName());
			}
		}
	}

	// function get_parent_class ($object = null) {}
	public static class get_parent_class implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol objectSymbol = SymbolUtils.getArgument(ip, 0);
			Set<PhpAnyType> set = Sets.newHashSet();
			for (PhpObject phpObject : operator.extractPhpObject(objectSymbol)) {
				String absoluteParentClassName = phpObject.getPhpClass().getAbsoluteParentClassName();
				if (absoluteParentClassName == null) {
					continue;
				}
				set.add(new PhpString(absoluteParentClassName.substring(1)));
			}
			return operator.createSymbol(set);
		}
	}

	// function method_exists ($object, $method_name) {}
	public static class method_exists implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function property_exists ($class, $property) {}
	public static class property_exists implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function trait_exists($traitname, $autoload ) {}
	public static class trait_exists implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function class_exists ($class_name, $autoload = true) {}
	public static class class_exists implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function interface_exists ($interface_name, $autoload = true) {}
	public static class interface_exists implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function function_exists ($function_name) {}
	public static class function_exists implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function class_alias ($original, $alias, $autoload = TRUE) {}
	public static class class_alias implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function get_included_files () {}
	public static class get_included_files implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			PhpArray phpArray = operator.createPhpArray();
			for (int i = 0; i < ip.getIncludedFileList().size(); i++) {
				operator.putArrayValue(phpArray, i, ip.getIncludedFileList().get(i));
			}
			return operator.createSymbol(phpArray);
		}
	}

	// function get_required_files () {}
	public static class get_required_files extends get_included_files {
	}

	// function is_subclass_of ($object, $class_name, $allow_string = TRUE) {}
	public static class is_subclass_of implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function is_a ($object, $class_name, $allow_string = FALSE) {}
	public static class is_a implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function get_class_vars ($class_name) {}
	public static class get_class_vars implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol classnameSymbol = SymbolUtils.getArgument(ip, 0);
			Symbol resultSymbol = operator.createSymbol();
			for (String classname : operator.getJavaStringList(classnameSymbol)) {
				for (PhpNewable newable : SymbolUtils.getClassList(ip, classname)) {
					Symbol symbol = SymbolUtils.new_(ip, newable, Lists.newArrayList());
					symbol = operator.cast(symbol, CastType.ARRAY);
					operator.merge(resultSymbol, symbol);
				}
			}
			return resultSymbol;
		}
	}

	// function get_object_vars ($object) {}
	public static class get_object_vars implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol objectSymbol = SymbolUtils.getArgument(ip, 0);
			return operator.cast(objectSymbol, CastType.ARRAY);
		}
	}

	// function get_class_methods ($class_name) {}
	public static class get_class_methods implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol classnameSymbol = SymbolUtils.getArgument(ip, 0);
			Set<PhpAnyType> set = Sets.newHashSet();
			for (String classname : operator.getJavaStringList(classnameSymbol)) {
				for (PhpNewable newable : SymbolUtils.getClassList(ip, classname)) {
					if (newable != null && newable instanceof PhpClass) {
						PhpClass phpClass = (PhpClass) newable;
						for (PhpCallable callable : phpClass.getMethodList()) {
							if (callable instanceof PhpFunction) {
								PhpFunction phpFunction = (PhpFunction) callable;
								set.add(new PhpString(phpFunction.getName()));
							}
						}
					}
				}
			}
			return operator.createSymbol(set);
		}
	}

	// function trigger_error ($error_msg, $error_type = E_USER_NOTICE) {}
	public static class trigger_error implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function user_error ($message, $error_type) {}
	public static class user_error extends trigger_error {
	}

	// function set_error_handler ($error_handler, $error_types = E_ALL | E_STRICT) {}
	public static class set_error_handler implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol errorhandlerSymbol = SymbolUtils.getArgument(ip, 0);
			for (String string : operator.getJavaStringList(errorhandlerSymbol)) {
				PhpCallable callable = ip.getFunction(string);
				if (callable != null) {
					// $errno, $errstr, $errfile, $errline
					List<Symbol> list = Lists.newArrayList();
					list.add(operator.integer());
					list.add(operator.string());
					list.add(operator.string());
					list.add(operator.integer());
					SymbolUtils.callFunction(ip, callable, list);
				}
			}
			// 元のハンドラ名を返す
			return operator.null_();
		}
	}

	// function restore_error_handler () {}
	public static class restore_error_handler implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			// 元のエラーハンドラに戻す
			return operator.bool();
		}
	}

	// function set_exception_handler ($exception_handler) {}
	public static class set_exception_handler implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol exceptionhandlerSymbol = SymbolUtils.getArgument(ip, 0);
			for (String string : operator.getJavaStringList(exceptionhandlerSymbol)) {
				PhpCallable callable = ip.getFunction(string);
				if (callable != null) {
					List<Symbol> list = Lists.newArrayList();
					list.add(operator.createSymbol(operator.createPhpObject(new Core_c.Exception(ip))));
					SymbolUtils.callFunction(ip, callable, list);
				}
			}
			// 元のハンドラ名を返す
			return operator.bool();
		}
	}

	// function restore_exception_handler () {}
	public static class restore_exception_handler implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			// 元のハンドラに戻す
			return operator.bool();
		}
	}

	// function get_declared_classes () {}
	public static class get_declared_classes implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			PhpArray phpArray = operator.createPhpArray();
			List<PhpClass> list = ip.getFileClassMap().get(ip.getFile());
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					operator.putArrayValue(phpArray, i, list.get(i).getAbsoulteClassName().substring(1));
				}
			}
			return operator.createSymbol(phpArray);
		}
	}

	// function get_declared_interfaces () {}
	public static class get_declared_interfaces extends get_declared_classes {
	}

	// function get_declared_traits() {}
	public static class get_declared_traits extends get_declared_classes {
	}

	// function get_defined_functions () {}
	public static class get_defined_functions implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			PhpArray phpArray = operator.createPhpArray();
			List<PhpFunction> list = ip.getFileFunctionMap().get(ip.getFile());
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					operator.putArrayValue(phpArray, i, list.get(i).getName());
				}
			}
			return operator.createSymbol(phpArray);
		}
	}

	// function get_defined_vars () {}
	public static class get_defined_vars implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			PhpArray phpArray = operator.createPhpArray();
			for (Entry<String, SymbolStack> entry : ip.getScopeStack().peek().getMap().entrySet()) {
				Symbol symbol = operator.getSymbol(entry.getValue().peek());
				if (symbol != null) {
					operator.putArrayValue(phpArray, entry.getKey(), symbol);
				}
			}
			return operator.createSymbol(phpArray);
		}
	}

	// function create_function ($args, $code) {}
	public static class create_function implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol argsSymbol = SymbolUtils.getArgument(ip, 0);
			Symbol codeSymbol = SymbolUtils.getArgument(ip, 1);
			Set<PhpAnyType> set = Sets.newHashSet();
			for (String code : operator.getJavaStringList(codeSymbol)) {
				for (String args : operator.getJavaStringList(argsSymbol)) {
					try {
						File file = FileUtils.createTempFile("create_function", ".php");
						String name = SymbolUtils.createLambdaName();
						set.add(new PhpString(name));
						Files.write(String.format("<?php\nfunction %s (%s) { %s }\n?>", name, args, code),
								file, Constants.DEFAULT_CHARSET);
						Compiler compiler = new Compiler(file);
						PhpFunction phpFunction = compiler.compile();
						SymbolUtils.callFunction(ip, phpFunction, Lists.newArrayList());
					} catch (Exception e) {
						log.warn("", e);
					}
				}
			}
			return operator.createSymbol(set);
		}
	}

	// function get_resource_type ($handle) {}
	public static class get_resource_type implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function get_loaded_extensions ($zend_extensions = false) {}
	public static class get_loaded_extensions implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array();
		}
	}

	// function extension_loaded ($name) {}
	public static class extension_loaded implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function get_extension_funcs ($module_name) {}
	public static class get_extension_funcs implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array();
		}
	}

	// function get_defined_constants ($categorize = false) {}
	public static class get_defined_constants implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol categorizeSymbol = SymbolUtils.getArgument(ip, 0);
			Set<PhpAnyType> set = Sets.newHashSet();
			for (PhpAnyType type : operator.getTypeSet(categorizeSymbol)) {
				PhpBoolean phpBoolean = (PhpBoolean) operator.cast(type, CastType.BOOL);
				if (phpBoolean.isBool()) {
					PhpArray phpArray = operator.createPhpArray();
					PhpArray phpArray2 = operator.createPhpArray();
					for (Entry<String, Symbol> entry : ip.getConstantMap().entrySet()) {
						operator.putArrayValue(phpArray2, entry.getKey(), entry.getValue());
					}
					operator.putArrayValue(phpArray, "Core", operator.createSymbol(phpArray2));
					set.add(phpArray);
				} else {
					PhpArray phpArray = operator.createPhpArray();
					for (Entry<String, Symbol> entry : ip.getConstantMap().entrySet()) {
						operator.putArrayValue(phpArray, entry.getKey(), entry.getValue());
					}
					set.add(phpArray);
				}
			}
			return operator.createSymbol(set);
		}
	}

	// function debug_backtrace ($options = DEBUG_BACKTRACE_PROVIDE_OBJECT, $limit = 0) {}
	public static class debug_backtrace implements PhpCallable {
		//		array(2) {
		//			[0]=>
		//			array(4) {
		//			    ["file"] => string(10) "/tmp/a.php"
		//			    ["line"] => int(10)
		//			    ["function"] => string(6) "a_test"
		//			    ["args"]=>
		//			    array(1) {
		//			      [0] => &string(6) "friend"
		//			    }
		//			}...
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			PhpArray phpArray = operator.createPhpArray();
			PhpArray phpArray2 = operator.createPhpArray();
			operator.putArrayValue(phpArray2, "file", operator.string());
			operator.putArrayValue(phpArray2, "line", operator.integer());
			operator.putArrayValue(phpArray2, "function", operator.string());
			operator.putArrayValue(phpArray2, "args", operator.array());
			operator.putArrayValue(phpArray, 0, operator.createSymbol(phpArray2));
			return operator.createSymbol(phpArray);
		}
	}

	// function error_clear_last () {}
	public static class error_clear_last implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.null_();
		}
	}

	// function debug_print_backtrace () {}
	public static class debug_print_backtrace implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.null_();
		}
	}

	// function gc_collect_cycles () {}
	public static class gc_collect_cycles implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function gc_enabled () {}
	public static class gc_enabled implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function gc_enable () {}
	public static class gc_enable implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.null_();
		}
	}

	// function gc_disable () {}
	public static class gc_disable implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.null_();
		}
	}
}
