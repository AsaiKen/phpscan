package net.katagaitai.phpscan.php.builtin;

import net.katagaitai.phpscan.compiler.BuiltinBase;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.compiler.PhpClassBase;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.util.SymbolUtils;

public class Xmlreader extends BuiltinBase {
	public Xmlreader(Interpreter ip) {
		super(ip);
		SymbolOperator operator = ip.getOperator();
		putConstant("\\XMLReader::NONE", operator.createSymbol(0));
		putConstant("\\XMLReader::ELEMENT", operator.createSymbol(1));
		putConstant("\\XMLReader::ATTRIBUTE", operator.createSymbol(2));
		putConstant("\\XMLReader::TEXT", operator.createSymbol(3));
		putConstant("\\XMLReader::CDATA", operator.createSymbol(4));
		putConstant("\\XMLReader::ENTITY_REF", operator.createSymbol(5));
		putConstant("\\XMLReader::ENTITY", operator.createSymbol(6));
		putConstant("\\XMLReader::PI", operator.createSymbol(7));
		putConstant("\\XMLReader::COMMENT", operator.createSymbol(8));
		putConstant("\\XMLReader::DOC", operator.createSymbol(9));
		putConstant("\\XMLReader::DOC_TYPE", operator.createSymbol(10));
		putConstant("\\XMLReader::DOC_FRAGMENT", operator.createSymbol(11));
		putConstant("\\XMLReader::NOTATION", operator.createSymbol(12));
		putConstant("\\XMLReader::WHITESPACE", operator.createSymbol(13));
		putConstant("\\XMLReader::SIGNIFICANT_WHITESPACE", operator.createSymbol(14));
		putConstant("\\XMLReader::END_ELEMENT", operator.createSymbol(15));
		putConstant("\\XMLReader::END_ENTITY", operator.createSymbol(16));
		putConstant("\\XMLReader::XML_DECLARATION", operator.createSymbol(17));
		putConstant("\\XMLReader::LOADDTD", operator.createSymbol(1));
		putConstant("\\XMLReader::DEFAULTATTRS", operator.createSymbol(2));
		putConstant("\\XMLReader::VALIDATE", operator.createSymbol(3));
		putConstant("\\XMLReader::SUBST_ENTITIES", operator.createSymbol(4));
	}

	public static class XMLReader extends PhpClassBase {
		public XMLReader(Interpreter ip) {
			super(ip);
		}

		private static final String XML_CONTENT = "XMLReader";

		// public function close () {}
		public static class close implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function getAttribute ($name) {}
		public static class getAttribute implements PhpCallable {

			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				Symbol thisSymbol = ip.getContext().getThisSymbol();
				return operator.getFieldValue(thisSymbol, XML_CONTENT);
			}
		}

		// public function getAttributeNo ($index) {}
		public static class getAttributeNo implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				Symbol thisSymbol = ip.getContext().getThisSymbol();
				return operator.getFieldValue(thisSymbol, XML_CONTENT);
			}
		}

		// public function getAttributeNs ($localName, $namespaceURI) {}
		public static class getAttributeNs implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				Symbol thisSymbol = ip.getContext().getThisSymbol();
				return operator.getFieldValue(thisSymbol, XML_CONTENT);
			}
		}

		// public function getParserProperty ($property) {}
		public static class getParserProperty implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function isValid () {}
		public static class isValid implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function lookupNamespace ($prefix) {}
		public static class lookupNamespace implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function moveToAttributeNo ($index) {}
		public static class moveToAttributeNo implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function moveToAttribute ($name) {}
		public static class moveToAttribute implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function moveToAttributeNs ($localName, $namespaceURI) {}
		public static class moveToAttributeNs implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function moveToElement () {}
		public static class moveToElement implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function moveToFirstAttribute () {}
		public static class moveToFirstAttribute implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function moveToNextAttribute () {}
		public static class moveToNextAttribute implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function open ($URI, $encoding = null, $options = 0) {}
		public static class open implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				Symbol thisSymbol = ip.getContext().getThisSymbol();
				operator.putFieldValue(thisSymbol, XML_CONTENT, operator.string());
				return operator.bool();
			}
		}

		// public function read () {}
		public static class read implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function next ($localname = null) {}
		public static class next implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function readInnerXml () {}
		public static class readInnerXml implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				Symbol thisSymbol = ip.getContext().getThisSymbol();
				return operator.getFieldValue(thisSymbol, XML_CONTENT);
			}
		}

		// public function readOuterXml () {}
		public static class readOuterXml implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				Symbol thisSymbol = ip.getContext().getThisSymbol();
				return operator.getFieldValue(thisSymbol, XML_CONTENT);
			}
		}

		// public function readString () {}
		public static class readString implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				Symbol thisSymbol = ip.getContext().getThisSymbol();
				return operator.getFieldValue(thisSymbol, XML_CONTENT);
			}
		}

		// public function setSchema ($filename) {}
		public static class setSchema implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function setParserProperty ($property, $value) {}
		public static class setParserProperty implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function setRelaxNGSchema ($filename) {}
		public static class setRelaxNGSchema implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function setRelaxNGSchemaSource ($source) {}
		public static class setRelaxNGSchemaSource implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function XML ($source, $encoding = null, $options = 0) {}
		public static class XML implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				Symbol thisSymbol = ip.getContext().getThisSymbol();
				operator.putFieldValue(thisSymbol, XML_CONTENT, SymbolUtils.getArgument(ip, 0));
				return operator.bool();
			}
		}

		// public function expand (DOMNode $basenode = null) {}
		public static class expand implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				// TODO DOMNode
				Symbol thisSymbol = ip.getContext().getThisSymbol();
				return operator.object(operator.getFieldValue(thisSymbol, XML_CONTENT));
			}
		}

	}
}
