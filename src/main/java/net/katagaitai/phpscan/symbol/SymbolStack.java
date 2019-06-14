package net.katagaitai.phpscan.symbol;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.katagaitai.phpscan.php.types.PhpAnyType;

import java.util.LinkedList;

@ToString(exclude = "operator")
@RequiredArgsConstructor
public class SymbolStack {
    private final SymbolOperator operator;
    private LinkedList<SymbolId> stack = Lists.newLinkedList();

    public void pushCopy(int index) {
        while (index >= stack.size()) {
            Symbol symbol = operator.createSymbol();
            operator.incrementReference(symbol);
            stack.addLast(symbol.getId());
        }
        Symbol symbol = operator.getSymbol(stack.get(index));
        if (symbol == null) {
            symbol = operator.createSymbol();
        }
        Symbol newSymbol = operator.copy2(symbol);
        operator.incrementReference(newSymbol);
        stack.push(newSymbol.getId());
    }

    public void push(Symbol symbol) {
        stack.push(symbol.getId());
    }

    public SymbolId pop() {
        if (stack.size() > 0) {
            return stack.pop();
        } else {
            return operator.createSymbol().getId();
        }
    }

    public SymbolId peek() {
        if (stack.size() > 0) {
            return stack.peek();
        } else {
            return operator.createSymbol().getId();
        }
    }

    public void clear() {
        for (SymbolId id : stack) {
            Symbol symbol = operator.getSymbol(id);
            operator.decrementReference(symbol);
        }
    }

    public int size() {
        return stack.size();
    }

    public void mergeCopies() {
        // 0
        SymbolId elseId = pop();
        Symbol elseSymbol = operator.getSymbol(elseId);
        if (elseSymbol == null) {
            elseSymbol = operator.createSymbol();
        }
        // 1
        SymbolId ifId = pop();
        Symbol ifSymbol = operator.getSymbol(ifId);
        if (ifSymbol == null) {
            ifSymbol = operator.createSymbol();
        }
        // 2
        SymbolId originalId = peek();
        Symbol originalSymbol = operator.getSymbol(originalId);
        if (originalSymbol == null) {
            originalSymbol = operator.createSymbol();
        }

        Symbol mergedSymbol = operator.createSymbol();
        operator.merge2(mergedSymbol, ifSymbol);
        operator.merge2(mergedSymbol, elseSymbol);
        // 同値の値はオリジナルの値に差し替える
        for (PhpAnyType oldType : operator.getTypeSet(originalSymbol)) {
            if (operator.getTypeSet(mergedSymbol).contains(oldType)) {
                operator.removeTypeSet(mergedSymbol, Sets.newHashSet(oldType));
                operator.addTypeSet(mergedSymbol, Sets.newHashSet(oldType));
            }
        }

        operator.setTypeSet(originalSymbol, operator.getTypeSet(mergedSymbol));
        originalSymbol.setTaintSet(Sets.newHashSet(mergedSymbol.getTaintSet()));
        operator.setObjectSymbolId(originalSymbol, mergedSymbol.getObjectSymbolId());
        operator.setKeySymbolId(originalSymbol, mergedSymbol.getKeySymbolId());

        operator.decrementReference(ifSymbol);
        operator.decrementReference(elseSymbol);
    }
}
