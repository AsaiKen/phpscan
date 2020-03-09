package net.katagaitai.phpscan.util;

public class IniUtils {
	public static String trim(String string) {
		if (string == null) {
			return null;
		}
		String string2 = string;
		if ((string2.startsWith("\"") && string2.endsWith("\""))
				|| (string2.startsWith("'") && string2.endsWith("'"))) {
			string2 = string2.substring(1, string2.length() - 1);
		}
		return string2;
	}
}
