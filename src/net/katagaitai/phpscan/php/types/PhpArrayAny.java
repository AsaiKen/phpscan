package net.katagaitai.phpscan.php.types;

import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import net.katagaitai.phpscan.taint.Taint;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PhpArrayAny extends PhpArray {
	@Getter
	private final Set<Taint> taintSet;

	public PhpArrayAny(int id2, Set<Taint> taintSet2) {
		super(id2);
		taintSet = taintSet2;
	}
}
