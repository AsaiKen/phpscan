package net.katagaitai.phpscan.command;

import lombok.Value;
import net.katagaitai.phpscan.interpreter.Interpreter;

@Value
public class Else_ implements Command {
    private String label;

    @Override
    public String toString() {
        return "// ELSE";
    }

    @Override
    public void accept(Interpreter vm) {
        vm.accept(this);
    }

}
