package net.katagaitai.phpscan.compiler;

import com.google.common.collect.Maps;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.katagaitai.phpscan.command.Command;
import net.katagaitai.phpscan.interpreter.CallDecorator;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.util.PhpUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

@ToString
@EqualsAndHashCode
public class PhpClass extends PhpClassBase {
    // パッケージはグローバルなマップで管理
    @Getter
    private final String name;
    @Getter
    private final String parentClassName;
    @Getter
    private final List<String> interfaceNameList;
    @Getter
    private final PhpFunction initializer;
    @Getter
    private final File file;

    public PhpClass(String name2, String parentClassName2, List<String> interfaceNameList2,
                    PhpFunction initializer2, File file2) {
        super(null);
        name = name2;
        parentClassName = parentClassName2;
        interfaceNameList = interfaceNameList2;
        initializer = initializer2;
        file = file2;
    }

    @Getter
    @Setter
    private String namespace = "\\";
    @Getter
    @Setter
    private Map<String, String> useMap = Maps.newHashMap();

    // フィールドとメソッドのオーバライド時には親よりも広い修飾子でないといけない仕様
    // 名前の解決は葉から根に順番に上っていくのみである。下りはない。
    // 修飾子を気にせず、全てのフィールドとメソッドを根から葉に向けてロードしていけば、
    // PHPの動作と同じになる

    @Override
    public String getAbsoulteClassName() {
        return PhpUtils.getAbsolute(name, namespace, useMap);
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(String.format("class %s ", getName()));
        if (getParentClassName() != null) {
            buffer.append(String.format("extends %s ", getParentClassName()));
        }
        if (getInterfaceNameList() != null && getInterfaceNameList().size() > 0) {
            buffer.append(String.format("implements %s ", String.join(", ", getInterfaceNameList())));
        }
        buffer.append("{\n");
        buffer.append("\tstatic{\n");
        for (Command command : getInitializer().getStaticCommandList()) {
            buffer.append("\t\t");
            buffer.append(command.toString().replace("\n", "\n\t\t"));
            buffer.append("\n");
        }
        buffer.append("\t}\n");
        for (Command command : getInitializer().getCommandList()) {
            buffer.append("\t");
            buffer.append(command.toString().replace("\n", "\n\t"));
            buffer.append("\n");
        }
        buffer.append("}");
        return buffer.toString();
    }

    @Override
    public String getAbsoluteParentClassName() {
        return PhpUtils.getAbsolute(parentClassName, namespace, useMap);
    }

    @Override
    public void initialize(Interpreter ip) {
        super.initialize(ip);
        if (initializer != null) {
            new CallDecorator(initializer).call(ip);
        }
    }
}
