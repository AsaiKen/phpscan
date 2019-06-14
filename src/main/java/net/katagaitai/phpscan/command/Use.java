package net.katagaitai.phpscan.command;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import net.katagaitai.phpscan.interpreter.Interpreter;

@Value
@Log4j2
@AllArgsConstructor
public class Use implements Command {
    private String namespace;
    private String alias;

    public Use(String namespace2) {
        namespace = namespace2;
        String[] names = namespace2.split("\\\\");
        if (names.length > 0) {
            alias = names[names.length - 1];
        } else {
            log.warn("不正な名前空間：" + namespace2);
            alias = "";
        }
    }

    public String toString() {
        return String.format("use %s as %s", namespace, alias);
    }

    @Override
    public void accept(Interpreter vm) {
        vm.accept(this);
    }

}
