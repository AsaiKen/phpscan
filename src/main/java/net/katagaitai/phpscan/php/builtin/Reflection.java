package net.katagaitai.phpscan.php.builtin;

import java.util.List;

import net.katagaitai.phpscan.compiler.BuiltinBase;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.compiler.PhpClassBase;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.php.builtin.Core_c.Closure;
import net.katagaitai.phpscan.php.builtin.Core_c.Exception;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.util.SymbolUtils;

import com.google.common.collect.Lists;

public class Reflection extends BuiltinBase {
	public Reflection(Interpreter ip) {
		super(ip);
		SymbolOperator operator = ip.getOperator();
		putConstant("\\ReflectionFunction::IS_DEPRECATED",
				operator.createSymbol(262144));

		putConstant("\\ReflectionMethod::IS_STATIC",
				operator.createSymbol(1));
		putConstant("\\ReflectionMethod::IS_PUBLIC",
				operator.createSymbol(256));
		putConstant("\\ReflectionMethod::IS_PROTECTED",
				operator.createSymbol(512));
		putConstant("\\ReflectionMethod::IS_PRIVATE",
				operator.createSymbol(1024));
		putConstant("\\ReflectionMethod::IS_ABSTRACT",
				operator.createSymbol(2));
		putConstant("\\ReflectionMethod::IS_FINAL",
				operator.createSymbol(4));

		putConstant("\\ReflectionClass::IS_IMPLICIT_ABSTRACT",
				operator.createSymbol(16));
		putConstant("\\ReflectionClass::IS_EXPLICIT_ABSTRACT",
				operator.createSymbol(32));
		putConstant("\\ReflectionClass::IS_FINAL",
				operator.createSymbol(64));

		putConstant("\\ReflectionProperty::IS_STATIC",
				operator.createSymbol(1));
		putConstant("\\ReflectionProperty::IS_PUBLIC",
				operator.createSymbol(256));
		putConstant("\\ReflectionProperty::IS_PROTECTED",
				operator.createSymbol(512));
		putConstant("\\ReflectionProperty::IS_PRIVATE",
				operator.createSymbol(1024));

	}

	public static class ReflectionException extends PhpClassBase {
		public ReflectionException(Interpreter ip) {
			super(ip);
		}

		@Override
		public String getAbsoluteParentClassName() {
			return SymbolUtils.getAbsoluteNameFromJavaClassName(Exception.class.getName());
		}
	}

	public static class Reflection_ extends PhpClassBase {
		public Reflection_(Interpreter ip) {
			super(ip);
		}

		//		public static function getModifierNames ($modifiers) {}
		public static class getModifierNames implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.array(operator.string());
			}
		}

		//		public static function export (Reflector $reflector, $return = false) {}
		public static class export implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.string();
			}
		}

	}

	// interface Reflector  {
	public static class Reflector extends PhpClassBase {
		public Reflector(Interpreter ip) {
			super(ip);
		}
	}

	// abstract class ReflectionFunctionAbstract implements Reflector {
	public static class ReflectionFunctionAbstract extends PhpClassBase {
		public ReflectionFunctionAbstract(Interpreter ip) {
			super(ip);
		}

		@Override
		public String getAbsoluteParentClassName() {
			return SymbolUtils.getAbsoluteNameFromJavaClassName(Reflector.class.getName());
		}

		@Override
		public void initialize(Interpreter interpreter) {
			super.initialize(interpreter);
			//			public $name;
			SymbolOperator operator = interpreter.getOperator();
			Symbol thisSymbol = interpreter.getContext().getThisSymbol();
			operator.putFieldValue(thisSymbol, "name", operator.string());
		}

		//		final private function __clone () {}
		public static class export implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				Symbol thisSymbol = ip.getContext().getThisSymbol();
				return thisSymbol;
			}
		}

		//		abstract public function __toString ();

		//		public function inNamespace () {}
		public static class inNamespace implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		//		public function isClosure () {}
		public static class isClosure implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		//		public function isDeprecated () {}
		public static class isDeprecated implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		//		public function isInternal () {}
		public static class isInternal implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		//		public function isGenerator() {}
		public static class isGenerator implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		//		public function isUserDefined () {}
		public static class isUserDefined implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		//		public function getClosureThis () {}
		//		public function getClosureScopeClass () {}
		//		public function getDocComment () {}
		//		public function getEndLine () {}
		//		public function getExtension () {}
		//		public function getExtensionName () {}
		//		public function getFileName () {}
		//		public function getName () {}
		//		public function getNamespaceName () {}
		//		public function getNumberOfParameters () {}
		//		public function getNumberOfRequiredParameters () {}
		//		public function getParameters () {}
		//		public function getReturnType () {}
		//		public function getShortName () {}
		//		public function getStartLine () {}
		//		public function getStaticVariables () {}
		//		public function returnsReference () {}
		//		public function isGenerator() {}
		//		public function isVariadic() {}

	}

	// class ReflectionFunction extends ReflectionFunctionAbstract implements Reflector {
	public static class ReflectionFunction extends PhpClassBase {
		public ReflectionFunction(Interpreter ip) {
			super(ip);
		}

		@Override
		public String getAbsoluteParentClassName() {
			return SymbolUtils.getAbsoluteNameFromJavaClassName(
					ReflectionFunctionAbstract.class.getName());
		}

		@Override
		public void initialize(Interpreter interpreter) {
			super.initialize(interpreter);
			//			public $name;
			SymbolOperator operator = interpreter.getOperator();
			Symbol thisSymbol = interpreter.getContext().getThisSymbol();
			operator.putFieldValue(thisSymbol, "name", operator.string());
		}

		//		public function __construct ($name) {}
		public static class __construct implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				Symbol thisSymbol = ip.getContext().getThisSymbol();
				operator.putFieldValue(thisSymbol, "name", SymbolUtils.getArgument(ip, 0));
				return operator.createNull();
			}
		}

		//		public function __toString () {}
		public static class __toString implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				return null;
			}
		}

		//		public static function export ($name, $return = null) {}
		public static class export implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				return null;
			}
		}

		//		public function isDisabled () {}
		public static class isDisabled implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				return ip.getOperator().bool();
			}
		}

		//		public function invoke ($args = null) {}
		public static class invoke implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				Symbol thisSymbol = ip.getContext().getThisSymbol();
				Symbol nameSymbol = operator.getFieldValue(thisSymbol, "name");
				Symbol resultSymbol = operator.createSymbol();
				for (String name : operator.getJavaStringList(nameSymbol)) {
					PhpCallable callable = ip.getFunction(name);
					if (callable != null) {
						operator.merge(resultSymbol,
								SymbolUtils.callFunction(ip, callable, ip.getContext().getArgumentSymbolList()));
					}
				}
				return resultSymbol;
			}
		}

		//		public function invokeArgs (array $args) {}
		public static class invokeArgs implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				Symbol thisSymbol = ip.getContext().getThisSymbol();
				Symbol nameSymbol = operator.getFieldValue(thisSymbol, "name");
				Symbol paramarrSymbol = SymbolUtils.getArgument(ip, 0);
				List<Symbol> argumentSymbolList = Lists.newArrayList();
				for (int i = 0; i < 100; i++) {
					Symbol argumentSymbol = operator.getArrayValue(paramarrSymbol, operator.createSymbol(i));
					if (operator.isNull(argumentSymbol)) {
						break;
					}
					argumentSymbolList.add(argumentSymbol);
				}
				Symbol resultSymbol = operator.createSymbol();
				for (String name : operator.getJavaStringList(nameSymbol)) {
					PhpCallable callable = ip.getFunction(name);
					if (callable != null) {
						operator.merge(resultSymbol,
								SymbolUtils.callFunction(ip, callable, argumentSymbolList));
					}
				}
				return resultSymbol;
			}
		}

		//		public function getClosure () {}
		public static class getClosure implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				Symbol thisSymbol = ip.getContext().getThisSymbol();
				Symbol nameSymbol = operator.getFieldValue(thisSymbol, "name");
				List<PhpCallable> callableList = Lists.newArrayList();
				for (String name : operator.getJavaStringList(nameSymbol)) {
					PhpCallable callable = ip.getFunction(name);
					if (callable != null) {
						callableList.add(callable);
					}
				}
				Symbol closureSymbol = SymbolUtils.new_(ip, new Closure(ip), Lists.newArrayList(nameSymbol));
				return closureSymbol;
			}
		}

	}

	// class ReflectionParameter implements Reflector {
	public static class ReflectionParameter extends PhpClassBase {
		public ReflectionParameter(Interpreter ip) {
			super(ip);
		}

		@Override
		public String getAbsoluteParentClassName() {
			return SymbolUtils.getAbsoluteNameFromJavaClassName(
					Reflector.class.getName());
		}

		@Override
		public void initialize(Interpreter interpreter) {
			super.initialize(interpreter);
			//			public $name;
			SymbolOperator operator = interpreter.getOperator();
			Symbol thisSymbol = interpreter.getContext().getThisSymbol();
			operator.putFieldValue(thisSymbol, "name", operator.string());
		}

		//		final private function __clone () {}
		//		public static function export ($function, $parameter, $return = null) {}
		//		public function __construct ($function, $parameter) {}
		//		public function __toString () {}
		//		public function getName () {}
		//		public function getType() {}
		//		public function hasType () {}
		//		public function isPassedByReference () {}
		//		public function canBePassedByValue () {}
		//		public function getDeclaringFunction () {}
		//		public function getDeclaringClass () {}
		//		public function getClass () {}
		//		public function isArray () {}
		//		public function isCallable () {}
		//		public function allowsNull () {}
		//		public function getPosition () {}
		//		public function isOptional () {}
		//		public function isDefaultValueAvailable () {}
		//		public function getDefaultValue () {}
		//		public function isDefaultValueConstant () {}
		//		public function getDefaultValueConstantName () {}
		//		public function isVariadic() {}
	}

	// class ReflectionMethod extends ReflectionFunctionAbstract implements Reflector {
	public static class ReflectionMethod extends PhpClassBase {
		public ReflectionMethod(Interpreter ip) {
			super(ip);
		}

		@Override
		public String getAbsoluteParentClassName() {
			return SymbolUtils.getAbsoluteNameFromJavaClassName(
					ReflectionFunctionAbstract.class.getName());
		}

		@Override
		public void initialize(Interpreter interpreter) {
			super.initialize(interpreter);
			//			public $name;
			//			public $class;
			SymbolOperator operator = interpreter.getOperator();
			Symbol thisSymbol = interpreter.getContext().getThisSymbol();
			operator.putFieldValue(thisSymbol, "name", operator.string());
			operator.putFieldValue(thisSymbol, "class", operator.string());
		}

		//		public static function export ($class, $name, $return = false) {}
		//		public function __construct ($class, $name) {}
		//		public function __toString () {}
		//		public function isPublic () {}
		//		public function isPrivate () {}
		//		public function isProtected () {}
		//		public function isAbstract () {}
		//		public function isFinal () {}
		//		public function isStatic () {}
		//		public function isConstructor () {}
		//		public function isDestructor () {}
		//		public function getClosure ($object) {}
		//		public function getModifiers () {}
		//		public function invoke ($object, $parameter = null, $_ = null) {}
		//		public function invokeArgs ($object, array $args) {}
		//		public function getDeclaringClass () {}
		//		public function getPrototype () {}
		//		public function setAccessible ($accessible) {}
	}

	// class ReflectionClass implements Reflector {
	public static class ReflectionClass extends PhpClassBase {
		public ReflectionClass(Interpreter ip) {
			super(ip);
		}

		@Override
		public String getAbsoluteParentClassName() {
			return SymbolUtils.getAbsoluteNameFromJavaClassName(
					Reflector.class.getName());
		}

		@Override
		public void initialize(Interpreter interpreter) {
			super.initialize(interpreter);
			//			public $name;
			SymbolOperator operator = interpreter.getOperator();
			Symbol thisSymbol = interpreter.getContext().getThisSymbol();
			operator.putFieldValue(thisSymbol, "name", operator.string());
		}

		//		final private function __clone () {}
		//		public static function export ($argument, $return = false) {}
		//		public function __construct ($argument) {}
		//		public function __toString () {}
		//		public function getName () {}
		//		public function isInternal () {}
		//		public function isUserDefined () {}
		//		public function isInstantiable () {}
		//		public function isCloneable () {}
		//		public function getFileName () {}
		//		public function getStartLine () {}
		//		public function getEndLine () {}
		//		public function getDocComment () {}
		//		public function getConstructor () {}
		//		public function hasMethod ($name) {}
		//		public function getMethod ($name) {}
		//		public function getMethods ($filter = null) {}
		//		public function hasProperty ($name) {}
		//		public function getProperty ($name) {}
		//		public function getProperties ($filter = null) {}
		//		public function hasConstant ($name) {}
		//		public function getConstants () {}
		//		public function getConstant ($name) {}
		//		public function getInterfaces () {}
		//		public function getInterfaceNames () {}
		//		public function isAnonymous () {}
		//		public function isInterface () {}
		//		public function getTraits () {}
		//		public function getTraitNames () {}
		//		public function getTraitAliases () {}
		//		public function isTrait () {}
		//		public function isAbstract () {}
		//		public function isFinal () {}
		//		public function getModifiers () {}
		//		public function isInstance ($object) {}
		//		public function newInstance ($args = null, $_ = null) {}
		//		public function newInstanceWithoutConstructor() {}
		//		public function newInstanceArgs (array $args = null) {}
		//		public function getParentClass () {}
		//		public function isSubclassOf ($class) {}
		//		public function getStaticProperties () {}
		//		public function getStaticPropertyValue ($name, $default = null) {}
		//		public function setStaticPropertyValue ($name, $value) {}
		//		public function getDefaultProperties () {}
		//		public function isIterateable () {}
		//		public function implementsInterface ($interface) {}
		//		public function getExtension () {}
		//		public function getExtensionName () {}
		//		public function inNamespace () {}
		//		public function getNamespaceName () {}
		//		public function getShortName () {}
	}

	// class ReflectionObject extends ReflectionClass implements Reflector {
	public static class ReflectionObject extends PhpClassBase {
		public ReflectionObject(Interpreter ip) {
			super(ip);
		}

		@Override
		public String getAbsoluteParentClassName() {
			return SymbolUtils.getAbsoluteNameFromJavaClassName(
					ReflectionClass.class.getName());
		}

		//		public static function export ($argument, $return = null) {}
		//		public function __construct ($argument) {}

	}

	// class ReflectionProperty implements Reflector {
	public static class ReflectionProperty extends PhpClassBase {
		public ReflectionProperty(Interpreter ip) {
			super(ip);
		}

		@Override
		public String getAbsoluteParentClassName() {
			return SymbolUtils.getAbsoluteNameFromJavaClassName(
					Reflector.class.getName());
		}

		@Override
		public void initialize(Interpreter interpreter) {
			super.initialize(interpreter);
			//			public $name;
			//			public $class;
			SymbolOperator operator = interpreter.getOperator();
			Symbol thisSymbol = interpreter.getContext().getThisSymbol();
			operator.putFieldValue(thisSymbol, "name", operator.string());
			operator.putFieldValue(thisSymbol, "class", operator.string());
		}

		//		final private function __clone () {}
		//		public static function export ($class, $name, $return = null) {}
		//		public function __construct ($class, $name) {}
		//		public function __toString () {}
		//		public function getName () {}
		//		public function getValue ($object) {}
		//		public function setValue ($object, $value) {}
		//		public function setValue ($value) {}
		//		public function isPublic () {}
		//		public function isPrivate () {}
		//		public function isProtected () {}
		//		public function isStatic () {}
		//		public function isDefault () {}
		//		public function getModifiers () {}
		//		public function getDeclaringClass () {}
		//		public function getDocComment () {}
		//		public function setAccessible ($accessible) {}
	}

	// class ReflectionExtension implements Reflector {
	public static class ReflectionExtension extends PhpClassBase {
		public ReflectionExtension(Interpreter ip) {
			super(ip);
		}

		@Override
		public String getAbsoluteParentClassName() {
			return SymbolUtils.getAbsoluteNameFromJavaClassName(
					Reflector.class.getName());
		}

		@Override
		public void initialize(Interpreter interpreter) {
			super.initialize(interpreter);
			//			public $name;
			SymbolOperator operator = interpreter.getOperator();
			Symbol thisSymbol = interpreter.getContext().getThisSymbol();
			operator.putFieldValue(thisSymbol, "name", operator.string());
		}

		//		final private function __clone () {}
		//		public static function export ($name, $return = false) {}
		//		public function __construct ($name) {}
		//		public function __toString () {}
		//		public function getName () {}
		//		public function getVersion () {}
		//		public function getFunctions () {}
		//		public function getConstants () {}
		//		public function getINIEntries () {}
		//		public function getClasses () {}
		//		public function getClassNames () {}
		//		public function getDependencies () {}
		//		public function info () {}
		//		public function isPersistent () {}
		//		public function isTemporary () {}
	}

	// class ReflectionZendExtension implements Reflector {
	public static class ReflectionZendExtension extends PhpClassBase {
		public ReflectionZendExtension(Interpreter ip) {
			super(ip);
		}

		@Override
		public String getAbsoluteParentClassName() {
			return SymbolUtils.getAbsoluteNameFromJavaClassName(
					Reflector.class.getName());
		}

		@Override
		public void initialize(Interpreter interpreter) {
			super.initialize(interpreter);
			//			public $name;
			SymbolOperator operator = interpreter.getOperator();
			Symbol thisSymbol = interpreter.getContext().getThisSymbol();
			operator.putFieldValue(thisSymbol, "name", operator.string());
		}

		//		final private function __clone () {}
		//		public static function export ($name, $return = null) {}
		//		public function __construct ($name) {}
		//		public function __toString () {}
		//		public function getName () {}
		//		public function getVersion () {}
		//		public function getAuthor () {}
		//		public function getURL () {}
		//		public function getCopyright () {}
	}

	//	class ReflectionGenerator
	public static class ReflectionGenerator extends PhpClassBase {
		public ReflectionGenerator(Interpreter ip) {
			super(ip);
		}

		//		public function __construct(Generator $generator)
		//		public function getExecutingFile()
		//		public function getExecutingGenerator()
		//		public function getExecutingLine()
		//		public function getFunction()
		//		public function getThis()
		//		public function getTrace($options = DEBUG_BACKTRACE_PROVIDE_OBJECT)

	}

	// class ReflectionType
	public static class ReflectionType extends PhpClassBase {
		public ReflectionType(Interpreter ip) {
			super(ip);
		}

		//		public function allowsNull()
		//		public function isBuiltin()
		//		public function __toString()

	}
}
