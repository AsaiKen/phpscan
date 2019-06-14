package net.katagaitai.phpscan.php.builtin;

import com.google.common.collect.Sets;
import net.katagaitai.phpscan.compiler.BuiltinBase;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.php.types.PhpAnyType;
import net.katagaitai.phpscan.php.types.PhpArray;
import net.katagaitai.phpscan.php.types.PhpInteger;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.util.SymbolUtils;

import java.util.Set;

public class _Standard_manual extends BuiltinBase {
    public _Standard_manual(Interpreter ip) {
        super(ip);
    }

    /**
     * @param string $str The string being translated.
     * @param array  $replace_pairs The replace_pairs parameter may be used as a substitute for to and from in which case it's an array in the form array('from' => 'to', ...).
     * @return string A copy of str, translating all occurrences of each character in from to the corresponding character in to.
     */
    //	function strtr ($str, array $replace_pairs) {};
    public static class strtr implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol strSymbol = SymbolUtils.getArgument(ip, 0);
            if (ip.getContext().getArgumentSymbolList().size() == 2) {
                Symbol replacepairsSymbol = SymbolUtils.getArgument(ip, 1);
                for (PhpArray phpArray : operator.extractPhpArray(replacepairsSymbol)) {
                    operator.addNewTaintSet(strSymbol, operator.getMergedArrayValueSymbol(phpArray).getTaintSet());
                }
            } else if (ip.getContext().getArgumentSymbolList().size() > 2) {
                Symbol replacementSymbol = SymbolUtils.getArgument(ip, 2);
                operator.addNewTaintSet(strSymbol, replacementSymbol.getTaintSet());
            }
            return strSymbol;
        }
    }

    /**
     * Convert hex to binary
     *
     * @param string $data
     * @link http://php.net/manual/en/function.hex2bin.php
     * @return string Returns the binary representation of the given data.
     * @see bin2hex(), unpack()
     * @since 5.4.0
     */
    //	function hex2bin($data) {};
    public static class hex2bin implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

    /**
     * Get or Set the HTTP response code
     *
     * @param int $response_code [optional] The optional response_code will set the response code.
     * @return int The current response code. By default the return value is int(200).
     */
    //function http_response_code($response_code) {}
    public static class http_response_code implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            Set<PhpAnyType> tSet =
                    Sets.newHashSet(new PhpInteger(200));
            return ip.getOperator().createSymbol(tSet);
        }
    }

}
