package net.katagaitai.phpscan.php.builtin;

import net.katagaitai.phpscan.compiler.BuiltinBase;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.compiler.PhpClassBase;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.php.types.PhpResource;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.util.SymbolUtils;

public class Xmlwriter extends BuiltinBase {
	public Xmlwriter(Interpreter ip) {
		super(ip);
	}

	private static final String XML_URI = "XML_URI";

	public static class XMLWriter extends PhpClassBase {

		public XMLWriter(Interpreter ip) {
			super(ip);
		}

		// public function openUri ($uri) {}
		public static class openUri implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				Symbol thisSymbol = ip.getContext().getThisSymbol();
				operator.putFieldValue(thisSymbol, XML_URI, SymbolUtils.getArgument(ip, 0));
				return operator.bool();
			}
		}

		// public function openMemory () {}
		public static class openMemory implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function setIndent ($indent) {}
		public static class setIndent implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function setIndentString ($indentString) {}
		public static class setIndentString implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function startComment () {}
		public static class startComment implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function endComment () {}
		public static class endComment implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function startAttribute ($name) {}
		public static class startAttribute implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function endAttribute () {}
		public static class endAttribute implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function writeAttribute ($name, $value) {}
		public static class writeAttribute implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function startAttributeNs ($prefix, $name, $uri) {}
		public static class startAttributeNs implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function writeAttributeNs ($prefix, $name, $uri, $content) {}
		public static class writeAttributeNs implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function startElement ($name) {}
		public static class startElement implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function endElement () {}
		public static class endElement implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function fullEndElement () {}
		public static class fullEndElement implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function startElementNs ($prefix, $name, $uri) {}
		public static class startElementNs implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function writeElement ($name, $content = null) {}
		public static class writeElement implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function writeElementNs ($prefix, $name, $uri, $content = null) {}
		public static class writeElementNs implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function startPi ($target) {}
		public static class startPi implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function endPi () {}
		public static class endPi implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function writePi ($target, $content) {}
		public static class writePi implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function startCdata () {}
		public static class startCdata implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function endCdata () {}
		public static class endCdata implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function writeCdata ($content) {}
		public static class writeCdata implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function text ($content) {}
		public static class text implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function writeRaw ($content) {}
		public static class writeRaw implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function startDocument ($version = null, $encoding = null, $standalone = null) {}
		public static class startDocument implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function endDocument () {}
		public static class endDocument implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function writeComment ($content) {}
		public static class writeComment implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function startDtd ($qualifiedName, $publicId = null, $systemId = null) {}
		public static class startDtd implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function endDtd () {}
		public static class endDtd implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function writeDtd ($name, $publicId = null, $systemId = null, $subset = null) {}
		public static class writeDtd implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function startDtdElement ($qualifiedName) {}
		public static class startDtdElement implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function endDtdElement () {}
		public static class endDtdElement implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function writeDtdElement ($name, $content) {}
		public static class writeDtdElement implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function startDtdAttlist ($name) {}
		public static class startDtdAttlist implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function endDtdAttlist () {}
		public static class endDtdAttlist implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function writeDtdAttlist ($name, $content) {}
		public static class writeDtdAttlist implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function startDtdEntity ($name, $isparam) {}
		public static class startDtdEntity implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function endDtdEntity () {}
		public static class endDtdEntity implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function writeDtdEntity ($name, $content, $pe, $pubid, $sysid, $ndataid) {}
		public static class writeDtdEntity implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function outputMemory ($flush = null) {}
		public static class outputMemory implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.string();
			}
		}

		// public function flush ($empty = null) {}
		public static class flush implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.integer();
			}
		}
	}

	// function xmlwriter_open_uri ($uri) {}
	public static class xmlwriter_open_uri implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			PhpResource phpResource = operator.createPhpResource();
			operator.putResourceValue(phpResource, XML_URI, operator.string());
			return operator.createSymbol(phpResource);
		}
	}

	// function xmlwriter_open_memory () {}
	public static class xmlwriter_open_memory implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function xmlwriter_set_indent ($xmlwriter, $indent) {}
	public static class xmlwriter_set_indent implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function xmlwriter_set_indent_string ($xmlwriter, $indentString) {}
	public static class xmlwriter_set_indent_string implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function xmlwriter_start_comment ($xmlwriter) {}
	public static class xmlwriter_start_comment implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function xmlwriter_end_comment ($xmlwriter) {}
	public static class xmlwriter_end_comment implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function xmlwriter_start_attribute ($xmlwriter, $name) {}
	public static class xmlwriter_start_attribute implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function xmlwriter_end_attribute ($xmlwriter) {}
	public static class xmlwriter_end_attribute implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function xmlwriter_write_attribute ($xmlwriter, $name, $value) {}
	public static class xmlwriter_write_attribute implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function xmlwriter_start_attribute_ns ($xmlwriter, $prefix, $name, $uri) {}
	public static class xmlwriter_start_attribute_ns implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function xmlwriter_write_attribute_ns ($xmlwriter, $prefix, $name, $uri, $content) {}
	public static class xmlwriter_write_attribute_ns implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function xmlwriter_start_element ($xmlwriter, $name) {}
	public static class xmlwriter_start_element implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function xmlwriter_end_element ($xmlwriter) {}
	public static class xmlwriter_end_element implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function xmlwriter_full_end_element ($xmlwriter) {}
	public static class xmlwriter_full_end_element implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function xmlwriter_start_element_ns ($xmlwriter, $prefix, $name, $uri) {}
	public static class xmlwriter_start_element_ns implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function xmlwriter_write_element ($xmlwriter, $name, $content = null) {}
	public static class xmlwriter_write_element implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function xmlwriter_write_element_ns ($xmlwriter, $prefix, $name, $uri, $content = null) {}
	public static class xmlwriter_write_element_ns implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function xmlwriter_start_pi ($xmlwriter, $target) {}
	public static class xmlwriter_start_pi implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function xmlwriter_end_pi ($xmlwriter) {}
	public static class xmlwriter_end_pi implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function xmlwriter_write_pi ($xmlwriter, $target, $content) {}
	public static class xmlwriter_write_pi implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function xmlwriter_start_cdata ($xmlwriter) {}
	public static class xmlwriter_start_cdata implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function xmlwriter_end_cdata ($xmlwriter) {}
	public static class xmlwriter_end_cdata implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function xmlwriter_write_cdata ($xmlwriter, $content) {}
	public static class xmlwriter_write_cdata implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function xmlwriter_text ($xmlwriter, $content) {}
	public static class xmlwriter_text implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function xmlwriter_write_raw ($xmlwriter, $content) {}
	public static class xmlwriter_write_raw implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function xmlwriter_start_document ($xmlwriter, $version = null, $encoding = null, $standalone = null) {}
	public static class xmlwriter_start_document implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function xmlwriter_end_document ($xmlwriter) {}
	public static class xmlwriter_end_document implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function xmlwriter_write_comment ($xmlwriter, $content) {}
	public static class xmlwriter_write_comment implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function xmlwriter_start_dtd ($xmlwriter, $qualifiedName, $publicId = null, $systemId = null) {}
	public static class xmlwriter_start_dtd implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function xmlwriter_end_dtd ($xmlwriter) {}
	public static class xmlwriter_end_dtd implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function xmlwriter_write_dtd ($xmlwriter, $name, $publicId = null, $systemId = null, $subset = null) {}
	public static class xmlwriter_write_dtd implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function xmlwriter_start_dtd_element ($xmlwriter, $qualifiedName) {}
	public static class xmlwriter_start_dtd_element implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function xmlwriter_end_dtd_element ($xmlwriter) {}
	public static class xmlwriter_end_dtd_element implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function xmlwriter_write_dtd_element ($xmlwriter, $name, $content) {}
	public static class xmlwriter_write_dtd_element implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function xmlwriter_start_dtd_attlist ($xmlwriter, $name) {}
	public static class xmlwriter_start_dtd_attlist implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function xmlwriter_end_dtd_attlist ($xmlwriter) {}
	public static class xmlwriter_end_dtd_attlist implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function xmlwriter_write_dtd_attlist ($xmlwriter, $name, $content) {}
	public static class xmlwriter_write_dtd_attlist implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function xmlwriter_start_dtd_entity ($xmlwriter, $name, $isparam) {}
	public static class xmlwriter_start_dtd_entity implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function xmlwriter_end_dtd_entity ($xmlwriter) {}
	public static class xmlwriter_end_dtd_entity implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function xmlwriter_write_dtd_entity ($xmlwriter, $name, $content) {}
	public static class xmlwriter_write_dtd_entity implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function xmlwriter_output_memory ($xmlwriter, $flush = null) {}
	public static class xmlwriter_output_memory implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function xmlwriter_flush ($xmlwriter, $empty = null) {}
	public static class xmlwriter_flush implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}
}
