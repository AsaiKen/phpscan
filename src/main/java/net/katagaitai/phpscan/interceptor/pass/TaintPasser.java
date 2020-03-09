package net.katagaitai.phpscan.interceptor.pass;

import java.util.Set;

import lombok.RequiredArgsConstructor;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.compiler.PhpFunction;
import net.katagaitai.phpscan.interceptor.CallInterceptor;
import net.katagaitai.phpscan.interpreter.CallDecorator;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.php.builtin.Filter.filter_input;
import net.katagaitai.phpscan.php.builtin.Filter.filter_input_array;
import net.katagaitai.phpscan.php.builtin.Filter.filter_var;
import net.katagaitai.phpscan.php.builtin.Filter.filter_var_array;
import net.katagaitai.phpscan.php.builtin.Json.json_encode;
import net.katagaitai.phpscan.php.builtin.Mysql.mysql_escape_string;
import net.katagaitai.phpscan.php.builtin.Mysql.mysql_real_escape_string;
import net.katagaitai.phpscan.php.builtin.Mysqli.mysqli;
import net.katagaitai.phpscan.php.builtin.Mysqli.mysqli_escape_string;
import net.katagaitai.phpscan.php.builtin.Mysqli.mysqli_real_escape_string;
import net.katagaitai.phpscan.php.builtin.Pcre.preg_replace;
import net.katagaitai.phpscan.php.builtin.Pdo.PDO;
import net.katagaitai.phpscan.php.builtin.Pgsql.pg_escape_bytea;
import net.katagaitai.phpscan.php.builtin.Pgsql.pg_escape_identifier;
import net.katagaitai.phpscan.php.builtin.Pgsql.pg_escape_literal;
import net.katagaitai.phpscan.php.builtin.Pgsql.pg_escape_string;
import net.katagaitai.phpscan.php.builtin.Pgsql.pg_unescape_bytea;
import net.katagaitai.phpscan.php.builtin.Sqlite.sqlite_escape_string;
import net.katagaitai.phpscan.php.builtin.Sqlite3.SQLite3;
import net.katagaitai.phpscan.php.builtin.Standard_0.html_entity_decode;
import net.katagaitai.phpscan.php.builtin.Standard_0.htmlentities;
import net.katagaitai.phpscan.php.builtin.Standard_0.htmlspecialchars;
import net.katagaitai.phpscan.php.builtin.Standard_0.htmlspecialchars_decode;
import net.katagaitai.phpscan.php.builtin.Standard_1.addslashes;
import net.katagaitai.phpscan.php.builtin.Standard_1.basename;
import net.katagaitai.phpscan.php.builtin.Standard_1.str_replace;
import net.katagaitai.phpscan.php.builtin.Standard_1.strip_tags;
import net.katagaitai.phpscan.php.builtin.Standard_1.stripcslashes;
import net.katagaitai.phpscan.php.builtin.Standard_1.stripslashes;
import net.katagaitai.phpscan.php.builtin.Standard_2.escapeshellarg;
import net.katagaitai.phpscan.php.builtin.Standard_2.http_build_query;
import net.katagaitai.phpscan.php.builtin.Standard_2.rawurldecode;
import net.katagaitai.phpscan.php.builtin.Standard_2.rawurlencode;
import net.katagaitai.phpscan.php.builtin.Standard_2.urldecode;
import net.katagaitai.phpscan.php.builtin.Standard_2.urlencode;
import net.katagaitai.phpscan.php.builtin.Standard_3.base64_decode;
import net.katagaitai.phpscan.php.builtin.Standard_3.base64_encode;
import net.katagaitai.phpscan.php.builtin._Standard_manual.strtr;
import net.katagaitai.phpscan.taint.EncodeTag;
import net.katagaitai.phpscan.taint.Taint;
import net.katagaitai.phpscan.util.PropertyUtils;

@RequiredArgsConstructor
public class TaintPasser implements CallInterceptor {
	private final Interpreter ip;

	@Override
	public void intercept(CallDecorator decorator) {
		PhpCallable callable = decorator.getDecorated();
		if (callable instanceof escapeshellarg) {
			// escapeshellcmdは
			// 「' および " は、対になっていない場合にのみエスケープされます。」
			// の仕様があるので危険。除外する。
			for (Taint taint : decorator.getResult().getTaintSet()) {
				taint.getEncodeTagStack().push(EncodeTag.SHELL_ENCODE);
			}
		} else if (callable instanceof htmlspecialchars || callable instanceof htmlentities) {
			for (Taint taint : decorator.getResult().getTaintSet()) {
				taint.getEncodeTagStack().push(EncodeTag.HTML_ESCAPE);
			}
		} else if (callable instanceof htmlspecialchars_decode || callable instanceof html_entity_decode) {
			for (Taint taint : decorator.getResult().getTaintSet()) {
				if (taint.getEncodeTagStack().size() > 0
						&& taint.getEncodeTagStack().peek() == EncodeTag.HTML_ESCAPE) {
					taint.getEncodeTagStack().pop();
				}
			}
		} else if (callable instanceof urlencode || callable instanceof rawurlencode
				|| callable instanceof http_build_query) {
			for (Taint taint : decorator.getResult().getTaintSet()) {
				taint.getEncodeTagStack().push(EncodeTag.URL_ENCODE);
			}
		} else if (callable instanceof urldecode || callable instanceof rawurldecode) {
			for (Taint taint : decorator.getResult().getTaintSet()) {
				if (taint.getEncodeTagStack().size() > 0
						&& taint.getEncodeTagStack().peek() == EncodeTag.URL_ENCODE) {
					taint.getEncodeTagStack().pop();
				}
			}
		} else if (callable instanceof base64_encode) {
			for (Taint taint : decorator.getResult().getTaintSet()) {
				taint.getEncodeTagStack().push(EncodeTag.BASE64_ENCODE);
			}
		} else if (callable instanceof base64_decode) {
			for (Taint taint : decorator.getResult().getTaintSet()) {
				if (taint.getEncodeTagStack().size() > 0
						&& taint.getEncodeTagStack().peek() == EncodeTag.BASE64_ENCODE) {
					taint.getEncodeTagStack().pop();
				}
			}
		} else if (callable instanceof mysql_escape_string
				|| callable instanceof mysql_real_escape_string
				|| callable instanceof mysqli.real_escape_string
				|| callable instanceof mysqli.escape_string
				|| callable instanceof mysqli_real_escape_string
				|| callable instanceof mysqli_escape_string
				|| callable instanceof pg_escape_string
				|| callable instanceof pg_escape_bytea
				|| callable instanceof pg_escape_identifier
				|| callable instanceof pg_escape_literal
				|| callable instanceof sqlite_escape_string
				|| callable instanceof SQLite3.escapeString
				|| callable instanceof addslashes
				|| callable instanceof PDO.quote) {
			for (Taint taint : decorator.getResult().getTaintSet()) {
				taint.getEncodeTagStack().push(EncodeTag.SQL_ESCAPE);
			}
		} else if (callable instanceof stripslashes || callable instanceof stripcslashes
				|| callable instanceof pg_unescape_bytea) {
			for (Taint taint : decorator.getResult().getTaintSet()) {
				if (taint.getEncodeTagStack().size() > 0
						&& taint.getEncodeTagStack().peek() == EncodeTag.SQL_ESCAPE) {
					taint.getEncodeTagStack().pop();
				}
			}
		} else if (callable instanceof strip_tags) {
			for (Taint taint : decorator.getResult().getTaintSet()) {
				taint.getEncodeTagStack().push(EncodeTag.STRIP_TAGS);
			}
		} else if (callable instanceof json_encode) {
			for (Taint taint : decorator.getResult().getTaintSet()) {
				taint.getEncodeTagStack().push(EncodeTag.JSON_ENCODE);
			}
		} else if (callable instanceof preg_replace) {
			// TODO
			//			preg_replace("/[^a-z0-9-]/","_",$str)
			//			preg_replace('/[^\w\.-]/', '', $_POST['filename'])
			//			preg_replace("/$input/","",$str)
		} else if (callable instanceof str_replace) {
			// TODO
			//			str_replace("..", "_", $str)
			//			str_replace(array('\0', '/', '\\', '..'), '', $app);
		} else if (callable instanceof strtr) {
			// TODO
			//			strtr($_GET['language'], './\\:', '____');
		} else if (callable instanceof filter_input) {
			// TODO
		} else if (callable instanceof filter_input_array) {
			// TODO
		} else if (callable instanceof filter_var) {
			// TODO
		} else if (callable instanceof filter_var_array) {
			// TODO
		} else if (callable instanceof basename) {
			for (Taint taint : decorator.getResult().getTaintSet()) {
				taint.getEncodeTagStack().push(EncodeTag.BASENAME);
			}
		}
		pushTag(callable, PropertyUtils.SQL_ESCAPE_FUNCTIONS, EncodeTag.SQL_ESCAPE,
				decorator.getResult().getTaintSet());
		pushTag(callable, PropertyUtils.HTML_ESCAPE_FUNCTIONS, EncodeTag.HTML_ESCAPE,
				decorator.getResult().getTaintSet());
	}

	private void pushTag(PhpCallable callable, String propName, EncodeTag toPushTag, Set<Taint> taintSet) {
		String functionsString = PropertyUtils.getString(propName);
		if (functionsString != null) {
			String[] functions = functionsString.split(",");
			for (String function : functions) {
				if (callable instanceof PhpFunction &&
						ip.getAbsoluteFunctionName(((PhpFunction) callable).getName())
								.equalsIgnoreCase(function)) {
					for (Taint taint : taintSet) {
						taint.getEncodeTagStack().push(toPushTag);
					}
				}
			}
		}
	}
}
