package net.katagaitai.phpscan.command;

import lombok.Value;
import net.katagaitai.phpscan.interpreter.Interpreter;

@Value
public class AddToReturnValue implements Command {
    private String value;

    @Override
    public String toString() {
        return "return " + value;
    }

    @Override
    public void accept(Interpreter vm) {
        vm.accept(this);
    }

}
