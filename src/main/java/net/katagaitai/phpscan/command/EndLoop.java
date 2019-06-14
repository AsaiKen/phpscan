package net.katagaitai.phpscan.command;

import lombok.Value;
import net.katagaitai.phpscan.interpreter.Interpreter;

@Value
public class EndLoop implements Command {
    @Override
    public String toString() {
        return "// loop end";
    }

    @Override
    public void accept(Interpreter vm) {
        vm.accept(this);
    }

}
