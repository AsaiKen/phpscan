package net.katagaitai.phpscan.php.builtin;

import net.katagaitai.phpscan.compiler.BuiltinBase;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.scope.Scope;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;

public class _Superglobals extends BuiltinBase {
    public _Superglobals(Interpreter ip) {
        super(ip);
    }

    public static void load(Interpreter ip, Scope globalScope, Symbol globals) {
        SymbolOperator operator = ip.getOperator();
        globalScope.put("$GLOBALS", globals);
        globalScope.put("$_COOKIE", operator.array());
        globalScope.put("$_ENV", operator.array());
        globalScope.put("$HTTP_ENV_VARS", globalScope.getOrPhpNull("$_ENV"));
        if (ip.getFileArray() != null) {
            globalScope.put("$_FILES", operator.array(operator.createSymbol(ip.getFileArray())));
        }
        globalScope.put("$HTTP_POST_FILES", globalScope.getOrPhpNull("$_FILES"));
        globalScope.put("$_GET", operator.array());
        globalScope.put("$HTTP_GET_VARS", globalScope.getOrPhpNull("$_GET"));
        globalScope.put("$_POST", operator.array());
        globalScope.put("$HTTP_POST_VARS", globalScope.getOrPhpNull("$_POST"));
        globalScope.put("$_REQUEST", operator.array());
        if (ip.getServerArray() != null) {
            globalScope.put("$_SERVER", operator.createSymbol(ip.getServerArray()));
        }
        globalScope.put("$HTTP_SERVER_VARS", globalScope.getOrPhpNull("$_SERVER"));
        globalScope.put("$_SESSION", operator.createSymbol(operator.createPhpArray()));
        globalScope.put("$HTTP_SESSION_VARS", globalScope.getOrPhpNull("$_SESSION"));
        globalScope.put("$argc", operator.integer());
        globalScope.put("$argv", operator.array());
        globalScope.put("$HTTP_RAW_POST_DATA", operator.string());
        globalScope.put("$http_response_header", operator.array());
        globalScope.put("$php_errormsg", operator.string());
    }
}
