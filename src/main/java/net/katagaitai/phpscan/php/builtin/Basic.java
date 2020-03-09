package net.katagaitai.phpscan.php.builtin;

import java.util.List;

import net.katagaitai.phpscan.compiler.BuiltinBase;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.php.types.PhpArray;
import net.katagaitai.phpscan.php.types.PhpResource;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;

public class Basic extends BuiltinBase {
	public Basic(Interpreter ip) {
		super(ip);
	}

	/**
	 * Returns active resources
	 * @link http://php.net/manual/en/function.get-resources.php
	 * @param string $type [optional]<p>
	 *
	 * If defined, this will cause get_resources() to only return resources of the given type. A list of resource types is available.
	 *
	 * If the string Unknown is provided as the type, then only resources that are of an unknown type will be returned.
	 *
	 * If omitted, all resources will be returned.
	 * </p>
	 * @return array Returns an array of currently active resources, indexed by resource number.
	 * @since 7.0
	 */
	//	function get_resources ($type) {}
	public static class get_resources implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			List<PhpResource> list = ip.getStorage().getResourceList();
			PhpArray phpArray = operator.createPhpArray();
			for (int i = 0; i < list.size(); i++) {
				operator.putArrayValue(phpArray, i, operator.createSymbol(list.get(i)));
			}
			return operator.createSymbol(phpArray);
		}
	}

}
