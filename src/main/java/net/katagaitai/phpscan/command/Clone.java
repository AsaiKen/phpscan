package net.katagaitai.phpscan.command;

import lombok.Value;
import net.katagaitai.phpscan.interpreter.Interpreter;

@Value
public class Clone implements Command {
    private String result;
    private String target;

    public String toString() {
        return String.format("%s = clone(%s)", result, target);
    }

    @Override
    public void accept(Interpreter vm) {
        vm.accept(this);
    }

}
