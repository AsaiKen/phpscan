package net.katagaitai.phpscan.php.types;

import com.google.common.collect.Maps;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.katagaitai.phpscan.command.CastType;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolId;
import net.katagaitai.phpscan.symbol.SymbolOperator;

import java.util.Map;

@ToString
@EqualsAndHashCode(exclude = {"array", "referenceCounter"})
public class PhpArray extends PhpAnyType {
    @Getter
    @Setter
    private int id;
    @Getter
    protected Map<SymbolId, SymbolId> array = Maps.newLinkedHashMap();
    @Getter
    @Setter
    protected int referenceCounter;

    public PhpArray(int id2) {
        id = id2;
    }

    public static final Map<String, PhpCallable> METHOD_MAP =
            Maps.newHashMap();

    static {
        // http://php.net/manual/ja/class.iterator.php
        METHOD_MAP.put("current", new current());
        METHOD_MAP.put("key", new key());
        METHOD_MAP.put("next", new next());
        METHOD_MAP.put("rewind", new rewind());
        METHOD_MAP.put("valid", new valid());
    }

    private static class current implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            PhpObject thisObject = ip.getContext().getThisObject();
            PhpArray phpArray = (PhpArray) operator.cast(thisObject, CastType.ARRAY);
            return operator.getMergedArrayValueSymbol(phpArray);
        }
    }

    private static class key implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            PhpObject thisObject = ip.getContext().getThisObject();
            PhpArray phpArray = (PhpArray) operator.cast(thisObject, CastType.ARRAY);
            return operator.getMergedArrayKeySymbol(phpArray);
        }
    }

    private static class next implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.createNull();
        }
    }

    private static class rewind implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.createNull();
        }
    }

    private static class valid implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.createTrue();
        }
    }

}
