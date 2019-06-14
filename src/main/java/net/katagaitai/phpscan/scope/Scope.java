package net.katagaitai.phpscan.scope;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolId;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.symbol.SymbolStack;

import java.util.Map;

@Log4j2
@ToString
@RequiredArgsConstructor
public class Scope {
    protected final Interpreter ip;
    @Getter
    protected Map<String, SymbolStack> map = Maps.newHashMap();

    public void put(String name, Symbol symbol) {
        SymbolOperator operator = ip.getOperator();
        operator.incrementReference(symbol);

        if (map.get(name) == null) {
            map.put(name, operator.createStack());
        }
        SymbolStack stack = map.get(name);
        // replace
        if (stack.size() > 0) {
            SymbolId oldId = stack.pop();
            Symbol oldSymbol = operator.getSymbol(oldId);
            operator.decrementReference(oldSymbol);
        }
        stack.push(symbol);
    }

    public Symbol getOrPhpNull(String name) {
        SymbolOperator operator = ip.getOperator();
        Symbol result = getOrJavaNull(name);
        if (result == null) {
            log.warn("不明なシンボル：" + name
                    + " [" + ip.getSourcePosition() + "]");
            return operator.createNull();
        } else {
            return result;
        }
    }

    public Symbol getOrJavaNull(String name) {
        SymbolOperator operator = ip.getOperator();
        Symbol result = null;
        SymbolStack stack = map.get(name);
        if (stack != null && stack.size() > 0) {
            SymbolId id = stack.peek();
            result = operator.getSymbol(id);
        }
        return result;
    }

    public Symbol getOrCreate(String name) {
        SymbolOperator operator = ip.getOperator();
        Symbol old = getOrJavaNull(name);
        if (old != null) {
            return old;
        }
        // create
        //		Symbol symbol = operator.createSymbol();
        Symbol symbol = operator.createNull();
        put(name, symbol);
        return symbol;
    }

    public void clear() {
        for (SymbolStack stack : map.values()) {
            stack.clear();
        }
    }

}
