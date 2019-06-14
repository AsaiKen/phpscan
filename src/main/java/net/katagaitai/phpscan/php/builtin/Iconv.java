package net.katagaitai.phpscan.php.builtin;

import net.katagaitai.phpscan.compiler.BuiltinBase;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.util.Constants;
import net.katagaitai.phpscan.util.SymbolUtils;

public class Iconv extends BuiltinBase {
    public Iconv(Interpreter ip) {
        super(ip);
        SymbolOperator operator = ip.getOperator();
        putConstant("\\ICONV_IMPL", operator.createSymbol("glibc"));
        putConstant("\\ICONV_VERSION", operator.createSymbol(2.17));
        putConstant("\\ICONV_MIME_DECODE_STRICT", operator.createSymbol(1));
        putConstant("\\ICONV_MIME_DECODE_CONTINUE_ON_ERROR", operator.createSymbol(2));
    }

    // function iconv ($in_charset, $out_charset, $str) {}
    public static class iconv implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 2);
        }
    }

    // function ob_iconv_handler ($contents, $status) {}
    public static class ob_iconv_handler implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

    // function iconv_get_encoding ($type = "all") {}
    //	array(3) {
    //		  ["input_encoding"]=>
    //		  string(10) "ISO-8859-1"
    //		  ["output_encoding"]=>
    //		  string(10) "ISO-8859-1"
    //		  ["internal_encoding"]=>
    //		  string(10) "ISO-8859-1"
    //		}
    public static class iconv_get_encoding implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            Symbol symbol = ip.getGlobalScope().getOrPhpNull(Constants.ICONV_ENCODING_ARRAY_VARIABLE);
            return symbol;
        }
    }

    // function iconv_set_encoding ($type, $charset) {}
    public static class iconv_set_encoding implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol typeSymbol = SymbolUtils.getArgument(ip, 0);
            Symbol charsetSymbol = SymbolUtils.getArgument(ip, 1);
            Symbol symbol = ip.getGlobalScope().getOrPhpNull(Constants.ICONV_ENCODING_ARRAY_VARIABLE);
            operator.putArrayValue(symbol, typeSymbol, charsetSymbol);
            return operator.bool();
        }
    }

    // function iconv_strlen ($str, $charset = 'ini_get("iconv.internal_encoding")') {}
    public static class iconv_strlen implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function iconv_substr ($str, $offset, $length = 'iconv_strlen($str, $charset)', $charset = 'ini_get("iconv.internal_encoding")') {}
    public static class iconv_substr implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

    // function iconv_strpos ($haystack, $needle, $offset = 0, $charset = 'ini_get("iconv.internal_encoding")') {}
    public static class iconv_strpos implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function iconv_strrpos ($haystack, $needle, $charset = 'ini_get("iconv.internal_encoding")') {}
    public static class iconv_strrpos implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function iconv_mime_encode ($field_name, $field_value, array $preferences = null) {}
    public static class iconv_mime_encode implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol fieldnameSymbol = SymbolUtils.getArgument(ip, 0);
            Symbol fieldvalueSymbol = SymbolUtils.getArgument(ip, 1);
            operator.addNewTaintSet(fieldnameSymbol, fieldvalueSymbol.getTaintSet());
            return fieldnameSymbol;
        }
    }

    // function iconv_mime_decode ($encoded_header, $mode = 0, $charset = 'ini_get("iconv.internal_encoding")') {}
    public static class iconv_mime_decode implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

    // function iconv_mime_decode_headers ($encoded_headers, $mode = 0, $charset = 'ini_get("iconv.internal_encoding")') {}
    public static class iconv_mime_decode_headers implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

}
