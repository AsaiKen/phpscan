package net.katagaitai.phpscan.command;

import lombok.Value;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.util.SymbolUtils;

@Value
public class PutConstant implements Command {
    protected String left;
    protected String right;

    public String toString() {
        return String.format("const %s = %s", left,
                SymbolUtils.decodeSymbolString(right));
    }

    @Override
    public void accept(Interpreter vm) {
        vm.accept(this);
    }

}
