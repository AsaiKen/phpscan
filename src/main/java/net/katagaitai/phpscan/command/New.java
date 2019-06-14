package net.katagaitai.phpscan.command;

import com.google.common.collect.Lists;
import lombok.Value;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.util.SymbolUtils;

import java.util.List;

@Value
public class New implements Command {
    private String result;
    private String clazz;
    private List<String> argumentList;

    public String toString() {
        List<String> list = Lists.newArrayList();
        for (String argument : argumentList) {
            list.add(SymbolUtils.decodeSymbolString(argument));
        }
        return String.format("%s = new %s(%s)", result, clazz, String.join(", ", list));
    }

    @Override
    public void accept(Interpreter vm) {
        vm.accept(this);
    }

}
