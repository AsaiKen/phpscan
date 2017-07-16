package net.katagaitai.phpscan.interceptor.cms;

import java.util.List;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.katagaitai.phpscan.Vulnerability;
import net.katagaitai.phpscan.VulnerabilityCategory;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.compiler.PhpClass;
import net.katagaitai.phpscan.compiler.PhpFunction;
import net.katagaitai.phpscan.interceptor.BeforeCallInterceptor;
import net.katagaitai.phpscan.interpreter.CallDecorator;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.php.types.PhpArray;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.taint.EncodeTag;
import net.katagaitai.phpscan.taint.Taint;
import net.katagaitai.phpscan.util.ScanUtils;
import net.katagaitai.phpscan.util.SymbolUtils;
import net.katagaitai.phpscan.util.TaintUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@Log4j2
@RequiredArgsConstructor
public class WordPressInterceptor implements BeforeCallInterceptor {
	private final Interpreter ip;

	@Override
	public void intercept(CallDecorator decorator) {
		SymbolOperator operator = ip.getOperator();
		PhpCallable callable = decorator.getDecorated();
		if (callable instanceof PhpFunction) {
			PhpFunction function = (PhpFunction) callable;
			PhpClass phpClass = function.getPhpClass();
			String namespace = function.getNamespace();
			String name = function.getName();
			String fullName = String.format("%s%s", namespace, name);
			if (phpClass == null) {
				// 関数
				if (fullName.equals("\\add_shortcode") || fullName.equals("\\add_action")
						|| fullName.equals("\\add_filter")) {
					// 戻り値なし、第2引数を呼ぶ
					Symbol functionSymbol = SymbolUtils.getArgument(ip, 1);
					Symbol arraySymbol = ip.getOperator().array();
					// GetArrayValue時にトレースできるように自身にも空のテイントを付与する
					arraySymbol.getTaintSet().add(new Taint(Lists.newArrayList(), Lists.newArrayList(),
							Lists.newLinkedList(), Lists.newArrayList()));
					log.debug("テイント初期化：" + arraySymbol);
					TaintUtils.applyInitialTaintRecursive(ip, arraySymbol, fullName);
					SymbolUtils.callCallable(ip, functionSymbol, Lists.newArrayList(arraySymbol));
					decorator.setResult(operator.createNull());
				} else if (fullName.equals("\\esc_url") || fullName.equals("\\esc_url_raw")) {
					// 値渡し、戻り値あり、URLエンコード
					Symbol dataSymbol = SymbolUtils.getArgument(ip, 0);
					for (Taint taint : dataSymbol.getTaintSet()) {
						taint.getEncodeTagStack().push(EncodeTag.URL_ENCODE);
					}
					decorator.setResult(dataSymbol);
				} else if (fullName.equals("\\esc_js") || fullName.equals("\\esc_html")
						|| fullName.equals("\\esc_attr") || fullName.equals("\\esc_textarea")
						|| fullName.equals("\\esc_attr__") || fullName.equals("\\esc_html__")
						|| fullName.equals("\\esc_attr_e") || fullName.equals("\\esc_html_e")
						|| fullName.equals("\\esc_attr_x") || fullName.equals("\\esc_html_x")) {
					// 値渡し、戻り値あり、HTMLエスケープ
					Symbol dataSymbol = SymbolUtils.getArgument(ip, 0);
					for (Taint taint : dataSymbol.getTaintSet()) {
						taint.getEncodeTagStack().push(EncodeTag.HTML_ESCAPE);
					}
					decorator.setResult(dataSymbol);
				}
			} else {
				// メソッド
				if (phpClass.getAbsoulteClassName().equals("\\wpdb")) {
					if (name.equals("prepare")) {
						// 値渡し、戻り値あり、SQLエスケープ
						Symbol querySymbol = SymbolUtils.getArgument(ip, 0);
						Set<Taint> taintSet = Sets.newHashSet();
						// 可変長引数指定の場合
						for (int i = 1; i < SymbolUtils.getArgumentSize(ip); i++) {
							taintSet.addAll(SymbolUtils.getArgument(ip, i).getTaintSet());
						}
						// 配列指定の場合
						if (SymbolUtils.getArgumentSize(ip) > 1) {
							for (PhpArray phpArray : operator.extractPhpArray(SymbolUtils.getArgument(ip, 1))) {
								taintSet.addAll(operator.getMergedArrayValueSymbol(phpArray).getTaintSet());
							}
						}
						for (Taint taint : taintSet) {
							taint.getEncodeTagStack().push(EncodeTag.SQL_ESCAPE);
						}
						operator.addNewTaintSet(querySymbol, taintSet);
						decorator.setResult(querySymbol);
					} else if (name.equals("escape") || name.equals("esc_like")) {
						// 値渡し、戻り値あり、再帰的SQLエスケープ
						Symbol dataSymbol = SymbolUtils.getArgument(ip, 0);
						TaintUtils.pushEncodeTagRecursive(ip, dataSymbol, EncodeTag.SQL_ESCAPE);
						decorator.setResult(dataSymbol);
					} else if (name.equals("escape_by_ref")) {
						// 参照渡し、戻り値無し、SQLエスケープ
						Symbol dataSymbol = SymbolUtils.getArgumentRef(ip, 0);
						for (Taint taint : dataSymbol.getTaintSet()) {
							taint.getEncodeTagStack().push(EncodeTag.SQL_ESCAPE);
						}
						decorator.setResult(operator.createNull());
					} else if (name.equals("get_var")) {
						// 頻繁に呼ばれる複雑な関数であるため、実行を省略する
						Symbol symbol = SymbolUtils.getArgument(ip, 0);
						Set<Taint> taintSet = TaintUtils.getSQLTaintSet(symbol.getTaintSet());
						List<Vulnerability> vulnerabilityList = ScanUtils.getVulnerabilityList(
								ip, VulnerabilityCategory.SQLI, taintSet, "\\wpdb\\get_var");
						ScanUtils.addVulnerability(ip, vulnerabilityList);
						decorator.setResult(operator.string());
					} else if (name.equals("query")) {
						// 頻繁に呼ばれる複雑な関数であるため、実行を省略する
						Symbol symbol = SymbolUtils.getArgument(ip, 0);
						Set<Taint> taintSet = TaintUtils.getSQLTaintSet(symbol.getTaintSet());
						List<Vulnerability> vulnerabilityList = ScanUtils.getVulnerabilityList(
								ip, VulnerabilityCategory.SQLI, taintSet, "\\wpdb\\query");
						ScanUtils.addVulnerability(ip, vulnerabilityList);
						decorator.setResult(operator.integer());
					}
				} else if ("\\wp_xmlrpc_server".equals(phpClass.getAbsoulteClassName()) && name.equals("escape")) {
					// 参照渡し、戻り値無し、再帰的SQLエスケープ
					Symbol dataSymbol = SymbolUtils.getArgumentRef(ip, 0);
					TaintUtils.pushEncodeTagRecursive(ip, dataSymbol, EncodeTag.SQL_ESCAPE);
					decorator.setResult(operator.createNull());
				}
			}
		}

	}
}
