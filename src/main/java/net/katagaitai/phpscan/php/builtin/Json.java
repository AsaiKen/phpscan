package net.katagaitai.phpscan.php.builtin;

import net.katagaitai.phpscan.compiler.BuiltinBase;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.util.SymbolUtils;

public class Json extends BuiltinBase {
    public Json(Interpreter ip) {
        super(ip);
        SymbolOperator operator = ip.getOperator();
        putConstant("\\JSON_HEX_TAG", operator.createSymbol(1));
        putConstant("\\JSON_HEX_AMP", operator.createSymbol(2));
        putConstant("\\JSON_HEX_APOS", operator.createSymbol(4));
        putConstant("\\JSON_HEX_QUOT", operator.createSymbol(8));
        putConstant("\\JSON_FORCE_OBJECT", operator.createSymbol(16));
        putConstant("\\JSON_NUMERIC_CHECK", operator.createSymbol(32));
        putConstant("\\JSON_UNESCAPED_SLASHES", operator.createSymbol(64));
        putConstant("\\JSON_PRETTY_PRINT", operator.createSymbol(128));
        putConstant("\\JSON_UNESCAPED_UNICODE", operator.createSymbol(256));
        putConstant("\\JSON_PARTIAL_OUTPUT_ON_ERROR", operator.createSymbol(512));
        putConstant("\\JSON_ERROR_STATE_MISMATCH", operator.createSymbol(2));
        putConstant("\\JSON_ERROR_CTRL_CHAR", operator.createSymbol(3));
        putConstant("\\JSON_ERROR_UTF8", operator.createSymbol(5));
        putConstant("\\JSON_ERROR_RECURSION", operator.createSymbol(6));
        putConstant("\\JSON_ERROR_INF_OR_NAN", operator.createSymbol(7));
        putConstant("\\JSON_ERROR_UNSUPPORTED_TYPE", operator.createSymbol(8));
        putConstant("\\JSON_ERROR_NONE", operator.createSymbol(0));
        putConstant("\\JSON_ERROR_DEPTH", operator.createSymbol(1));
        putConstant("\\JSON_ERROR_SYNTAX", operator.createSymbol(4));
        putConstant("\\JSON_OBJECT_AS_ARRAY", operator.createSymbol(1));
        putConstant("\\JSON_PARSER_NOTSTRICT", operator.createSymbol(4));
        putConstant("\\JSON_BIGINT_AS_STRING", operator.createSymbol(2));
        putConstant("\\JSON_PRESERVE_ZERO_FRACTION", operator.createSymbol(1024));
    }

    // function json_encode ($value, $options = 0, $depth = 512) {}
    public static class json_encode implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol valueSymbol = SymbolUtils.getArgument(ip, 0);
            Symbol resultSymbol = operator.createSymbol();
            Symbol symbol = serialize(ip, valueSymbol);
            operator.merge(resultSymbol, symbol);
            return resultSymbol;
        }
    }

    // function json_decode ($json, $assoc = false, $depth = 512, $options = 0) {}
    public static class json_decode implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol jsonSymbol = SymbolUtils.getArgument(ip, 0);
            return operator.object(jsonSymbol);
        }
    }

    // function json_last_error () {}
    public static class json_last_error implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function json_last_error_msg () {}
    public static class json_last_error_msg implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }
}
