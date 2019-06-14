package net.katagaitai.phpscan.command;

import lombok.Value;
import net.katagaitai.phpscan.interpreter.Interpreter;

@Value
public class AssignReference implements Command {
    private String left;
    private String right;

    public String toString() {
        return String.format("%s = &%s", left, right);
    }

    @Override
    public void accept(Interpreter vm) {
        vm.accept(this);
    }

}
