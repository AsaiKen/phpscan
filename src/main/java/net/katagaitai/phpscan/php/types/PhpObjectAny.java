package net.katagaitai.phpscan.php.types;

import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import net.katagaitai.phpscan.compiler.PhpNewable;
import net.katagaitai.phpscan.taint.Taint;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PhpObjectAny extends PhpObject {
	@Getter
	private final Set<Taint> taintSet;

	public PhpObjectAny(int id2, PhpNewable phpClass2, Set<Taint> taintSet2) {
		super(id2, phpClass2);
		taintSet = taintSet2;
	}
}
