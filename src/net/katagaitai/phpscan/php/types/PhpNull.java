package net.katagaitai.phpscan.php.types;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class PhpNull extends PhpAnyType {
	private static PhpNull NULL;

	private PhpNull() {
	}

	public static PhpNull getNull() {
		if (NULL == null) {
			NULL = new PhpNull();
		}
		return NULL;
	}

}
