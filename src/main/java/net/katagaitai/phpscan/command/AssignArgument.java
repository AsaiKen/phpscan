package net.katagaitai.phpscan.command;

import lombok.Value;
import net.katagaitai.phpscan.interpreter.Interpreter;

@Value
public class AssignArgument implements Command {
    private String argument;
    private int index;

    @Override
    public String toString() {
        return String.format("%s = arguments[%d]", argument, index);
    }

    @Override
    public void accept(Interpreter vm) {
        vm.accept(this);
    }
}
