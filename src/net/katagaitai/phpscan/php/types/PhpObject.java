package net.katagaitai.phpscan.php.types;

import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import net.katagaitai.phpscan.compiler.PhpNewable;
import net.katagaitai.phpscan.symbol.SymbolId;

import com.google.common.collect.Maps;

@EqualsAndHashCode(exclude = { "phpClass", "fieldMap", "referenceCounter", "realId" })
public class PhpObject extends PhpAnyType {
	@Override
	public String toString() {
		return "PhpObject [id=" + id + ", phpClass=" + phpClass.getAbsoulteClassName() + ", fieldMap=" + fieldMap
				+ ", referenceCounter="
				+ referenceCounter + ", realId=" + realId + "]";
	}

	@Getter
	@Setter
	private int id;
	@Getter
	private final PhpNewable phpClass;
	@Getter
	private Map<SymbolId, SymbolId> fieldMap = Maps.newLinkedHashMap();
	@Getter
	@Setter
	protected int referenceCounter;

	// objectMap上のID
	@Getter
	private final int realId;

	public PhpObject(int id2, PhpNewable phpClass2) {
		id = id2;
		realId = id2;
		phpClass = phpClass2;
	}
}
