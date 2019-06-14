package net.katagaitai.phpscan.php.types;

import com.google.common.collect.Maps;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.katagaitai.phpscan.symbol.SymbolId;

import java.util.Map;

@ToString
@EqualsAndHashCode(exclude = {"resource", "referenceCounter"})
public class PhpResource extends PhpAnyType {
    @Getter
    @Setter
    private int id;
    @Getter
    protected Map<String, SymbolId> resource = Maps.newHashMap();
    @Getter
    @Setter
    protected int referenceCounter;

    public PhpResource(int id2) {
        id = id2;
    }
}
