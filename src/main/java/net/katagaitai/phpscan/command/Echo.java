package net.katagaitai.phpscan.command;

import lombok.Value;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.util.SymbolUtils;

@Value
public class Echo implements Command {
    private String argument;

    public String toString() {
        return String.format("echo %s", SymbolUtils.decodeSymbolString(argument));
    }

    @Override
    public void accept(Interpreter vm) {
        vm.accept(this);
    }

}
