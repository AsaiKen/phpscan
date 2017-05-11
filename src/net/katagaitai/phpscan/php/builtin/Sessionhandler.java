package net.katagaitai.phpscan.php.builtin;

import net.katagaitai.phpscan.compiler.BuiltinBase;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.compiler.PhpClassBase;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.symbol.Symbol;

public class Sessionhandler extends BuiltinBase {
	public Sessionhandler(Interpreter ip) {
		super(ip);
	}

	public static class SessionHandlerInterface extends PhpClassBase {
		public SessionHandlerInterface(Interpreter ip) {
			super(ip);
		}
	}

	public static class SessionHandler extends PhpClassBase {
		public SessionHandler(Interpreter ip) {
			super(ip);
		}

		// public function close() { }
		public static class close implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				return null;
			}
		}

		// public function destroy($session_id) { }
		public static class destroy implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				return null;
			}
		}

		// public function gc($maxlifetime) { }
		public static class gc implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				return null;
			}
		}

		// public function open($save_path, $session_id) { }
		public static class open implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				return null;
			}
		}

		// public function read($session_id) { }
		public static class read implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				return null;
			}
		}

		// public function write($session_id, $session_data) { }
		public static class write implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				return null;
			}
		}
	}
}
