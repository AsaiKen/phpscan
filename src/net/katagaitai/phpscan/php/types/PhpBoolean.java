package net.katagaitai.phpscan.php.types;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = false)
public class PhpBoolean extends PhpAnyType {
	private static PhpBoolean TRUE;
	private static PhpBoolean FALSE;

	@Getter
	private final boolean bool;

	private PhpBoolean(boolean b) {
		bool = b;
	}

	public static PhpBoolean getTrue() {
		if (TRUE == null) {
			TRUE = new PhpBoolean(true);
		}
		return TRUE;
	}

	public static PhpBoolean getFalse() {
		if (FALSE == null) {
			FALSE = new PhpBoolean(false);
		}
		return FALSE;
	}
}
