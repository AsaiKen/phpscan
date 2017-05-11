package net.katagaitai.phpscan.php.builtin;

import net.katagaitai.phpscan.compiler.BuiltinBase;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.compiler.PhpClassBase;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.util.SymbolUtils;

import com.google.common.collect.Lists;

public class Simplexml extends BuiltinBase {
	public Simplexml(Interpreter ip) {
		super(ip);
	}

	public static class SimpleXMLElement extends PhpClassBase {
		public SimpleXMLElement(Interpreter ip) {
			super(ip);
		}

		// final public function __construct ($data, $options = 0, $data_is_url = false, $ns = "", $is_prefix = false) {}

		// function __get($name) {}
		public static class __get implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				SimpleXMLElement clazz = new SimpleXMLElement(ip);
				Symbol objSymbol = SymbolUtils.new_(ip, clazz, Lists.newArrayList());
				return operator.array(objSymbol);
			}
		}

		// public function asXML ($filename = null) {}
		public static class asXML implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				if (ip.getContext().getArgumentSymbolList().size() == 0) {
					return operator.string();
				} else {
					return operator.bool();
				}
			}
		}

		// public function saveXML ($filename = null) {}
		public static class saveXML implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				if (ip.getContext().getArgumentSymbolList().size() == 0) {
					return operator.string();
				} else {
					return operator.bool();
				}
			}
		}

		// public function xpath ($path) {}
		public static class xpath implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				SimpleXMLElement clazz = new SimpleXMLElement(ip);
				Symbol objSymbol = SymbolUtils.new_(ip, clazz, Lists.newArrayList());
				return operator.array(objSymbol);
			}
		}

		// public function registerXPathNamespace ($prefix, $ns) {}
		public static class registerXPathNamespace implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function attributes ($ns = null, $is_prefix = false) {}
		public static class attributes implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SimpleXMLElement clazz = new SimpleXMLElement(ip);
				Symbol objSymbol = SymbolUtils.new_(ip, clazz, Lists.newArrayList());
				return objSymbol;
			}
		}

		// public function children ($ns = null, $is_prefix = false) {}
		public static class children implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SimpleXMLElement clazz = new SimpleXMLElement(ip);
				Symbol objSymbol = SymbolUtils.new_(ip, clazz, Lists.newArrayList());
				return objSymbol;
			}
		}

		// public function getNamespaces ($recursive = false) {}
		public static class getNamespaces implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.array();
			}
		}

		// public function getDocNamespaces ($recursive = false, $from_root = true) {}
		public static class getDocNamespaces implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.array();
			}
		}

		// public function getName () {}
		public static class getName implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.string();
			}
		}

		// public function addChild ($name, $value = null, $namespace = null) {}
		public static class addChild implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SimpleXMLElement clazz = new SimpleXMLElement(ip);
				Symbol objSymbol = SymbolUtils.new_(ip, clazz, Lists.newArrayList());
				return objSymbol;
			}
		}

		// public function addAttribute ($name, $value = null, $namespace = null) {}
		public static class addAttribute implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.null_();
			}
		}

		// public function __toString () {}

		// public function count () {}
		public static class count implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.integer();
			}
		}

	}

	public static class SimpleXMLIterator extends PhpClassBase {
		public SimpleXMLIterator(Interpreter ip) {
			super(ip);
		}

		@Override
		public String getAbsoluteParentClassName() {
			return SymbolUtils.getAbsoluteNameFromJavaClassName(SimpleXMLElement.class.getName());
		}

		// public function rewind () {}
		public static class rewind implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.null_();
			}
		}

		// public function valid () {}
		public static class valid implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function current () {}
		public static class current implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SimpleXMLIterator clazz = new SimpleXMLIterator(ip);
				Symbol objSymbol = SymbolUtils.new_(ip, clazz, Lists.newArrayList());
				return objSymbol;
			}
		}

		// public function key () {}
		public static class key implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.string();
			}
		}

		// public function next () {}
		public static class next implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.null_();
			}
		}

		// public function hasChildren () {}
		public static class hasChildren implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function getChildren () {}
		public static class getChildren implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SimpleXMLIterator clazz = new SimpleXMLIterator(ip);
				Symbol objSymbol = SymbolUtils.new_(ip, clazz, Lists.newArrayList());
				return objSymbol;
			}
		}

		// public function __toString () {}

		// public function count () {}
		public static class count implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.integer();
			}
		}
	}

	// function simplexml_load_file ($filename, $class_name = "SimpleXMLElement", $options = 0, $ns = "", $is_prefix = false) {}
	public static class simplexml_load_file implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SimpleXMLElement clazz = new SimpleXMLElement(ip);
			Symbol objSymbol = SymbolUtils.new_(ip, clazz, Lists.newArrayList());
			return objSymbol;
		}
	}

	// function simplexml_load_string ($data, $class_name = "SimpleXMLElement", $options = 0, $ns = "", $is_prefix = false) {}
	public static class simplexml_load_string implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SimpleXMLElement clazz = new SimpleXMLElement(ip);
			Symbol objSymbol = SymbolUtils.new_(ip, clazz, Lists.newArrayList());
			return objSymbol;
		}
	}

	// function simplexml_import_dom (DOMNode $node, $class_name = "SimpleXMLElement") {}
	public static class simplexml_import_dom implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SimpleXMLElement clazz = new SimpleXMLElement(ip);
			Symbol objSymbol = SymbolUtils.new_(ip, clazz, Lists.newArrayList());
			return objSymbol;
		}
	}

}
