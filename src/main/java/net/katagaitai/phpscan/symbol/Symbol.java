package net.katagaitai.phpscan.symbol;

import com.google.common.collect.Sets;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.katagaitai.phpscan.php.types.PhpAnyType;
import net.katagaitai.phpscan.taint.Taint;

import java.util.Set;

@ToString(exclude = {"objectSymbolId", "keySymbolId"})
@EqualsAndHashCode(exclude = {"id", "referenceCount", "objectSymbolId", "keySymbolId"})
@Data
public class Symbol {
    private SymbolId id;
    private Set<PhpAnyType> typeSet = Sets.newHashSet();
    private Set<Taint> taintSet = Sets.newHashSet();
    private int referenceCount;
    private SymbolId objectSymbolId;
    private SymbolId keySymbolId;

}
