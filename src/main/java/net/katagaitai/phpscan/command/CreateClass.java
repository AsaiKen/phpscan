package net.katagaitai.phpscan.command;

import lombok.Value;
import net.katagaitai.phpscan.compiler.PhpClass;
import net.katagaitai.phpscan.interpreter.Interpreter;

@Value
public class CreateClass implements Command {
    private PhpClass phpClass;

    public String toString() {
        return phpClass.toString();
    }

    @Override
    public void accept(Interpreter vm) {
        vm.accept(this);
    }

}
