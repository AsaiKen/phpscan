package net.katagaitai.phpscan.command;

import lombok.Value;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.util.SymbolUtils;

@Value
public class Cast implements Command {
    private String result;
    private CastType type;
    private String target;

    public String toString() {
        return String.format("%s = (%s) %s", result, type.toString().toLowerCase(),
                SymbolUtils.decodeSymbolString(target));
    }

    @Override
    public void accept(Interpreter vm) {
        vm.accept(this);
    }

}
