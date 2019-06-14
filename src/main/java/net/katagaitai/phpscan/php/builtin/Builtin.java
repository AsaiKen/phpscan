package net.katagaitai.phpscan.php.builtin;

import net.katagaitai.phpscan.compiler.BuiltinBase;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.util.SymbolUtils;

public class Builtin extends BuiltinBase {
    public Builtin(Interpreter ip) {
        super(ip);
    }

    public static class print implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            if (ip.isUseEcho()) {
                Symbol arg0 = SymbolUtils.getArgument(ip, 0);
                for (String string : operator.getJavaStringList(arg0)) {
                    System.out.print(string);
                }
            }
            return operator.null_();
        }
    }

}
