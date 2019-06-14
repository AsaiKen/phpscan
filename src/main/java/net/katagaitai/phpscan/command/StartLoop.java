package net.katagaitai.phpscan.command;

import lombok.Value;
import net.katagaitai.phpscan.interpreter.Interpreter;

@Value
public class StartLoop implements Command {
    @Override
    public String toString() {
        return "// loop start";
    }

    @Override
    public void accept(Interpreter vm) {
        vm.accept(this);
    }

}
