package net.katagaitai.phpscan.php.builtin;

import net.katagaitai.phpscan.compiler.BuiltinBase;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;

public class Csprng extends BuiltinBase {
    public Csprng(Interpreter ip) {
        super(ip);
    }

    // function random_bytes ($length) {}
    public static class random_bytes implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function random_int ($min, $max) {}
    public static class random_int implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

}
