package net.katagaitai.phpscan.php.builtin;

import java.util.List;

import net.katagaitai.phpscan.compiler.BuiltinBase;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.util.Constants;
import net.katagaitai.phpscan.util.SymbolUtils;

import com.google.common.collect.Lists;

public class Session extends BuiltinBase {
	public Session(Interpreter ip) {
		super(ip);
	}

	// function session_name ($name = null) {}
	public static class session_name implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function session_module_name ($module = null) {}
	public static class session_module_name implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function session_save_path ($path = null) {}
	public static class session_save_path implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			// TODO 実装したほうがよいかも
			return operator.string();
		}
	}

	// function session_id ($id = null) {}
	// 現在のセッション ID を取得または設定する
	public static class session_id implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol sessionIdSymbol = ip.getGlobalScope().getOrPhpNull(Constants.SESSION_ID_VARIABLE);
			Symbol idSymbol = SymbolUtils.getArgument(ip, 0);
			if (operator.isNull(idSymbol)) {
				// pass
			} else {
				operator.assign(sessionIdSymbol, idSymbol);
			}
			Symbol resultSymbol = operator.copy(sessionIdSymbol);
			return resultSymbol;
		}
	}

	// function session_regenerate_id ($delete_old_session = false) {}
	public static class session_regenerate_id implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function session_register_shutdown  () {}
	public static class session_register_shutdown implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.null_();
		}
	}

	// function session_decode ($data) {}
	// http://php.net/manual/ja/function.session-decode.php
	// session_decode() は、 $data のセッションデータをデコードし、
	// スーパーグローバル $_SESSION にその結果を格納します。
	public static class session_decode implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol dataSymbol = SymbolUtils.getArgument(ip, 0);
			Symbol sessionSymbol = ip.getGlobalScope().getOrPhpNull("$_SESSION");
			operator.assign(sessionSymbol, operator.createSymbol(
					operator.createPhpArrayDummy(dataSymbol)));
			return operator.createNull();
		}
	}

	// function session_register ($name, $_ = null) {}
	public static class session_register implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			List<Symbol> list = ip.getContext().getArgumentSymbolList();
			for (int i = 0; i < list.size(); i++) {
				Symbol symbol = list.get(i);
				for (String string : operator.getJavaStringList(symbol)) {
					Symbol globalSymbol = ip.getGlobalScope().getOrPhpNull(string);
					if (operator.isNull(globalSymbol)) {
						continue;
					}
					Symbol sessionSymbol = ip.getGlobalScope().getOrPhpNull("$_SESSION");
					if (operator.isNull(sessionSymbol)) {
						continue;
					}
					operator.putArrayValue(sessionSymbol, string, globalSymbol);
				}
			}
			return operator.bool();
		}
	}

	// function session_unregister ($name) {}
	public static class session_unregister implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function session_is_registered ($name) {}
	public static class session_is_registered implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function session_encode () {}
	public static class session_encode implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return serialize(ip, ip.getGlobalScope().getOrPhpNull("$_SESSION"));
		}
	}

	// function session_start () {}
	public static class session_start implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol sessionCallbackArraySymbol =
					ip.getGlobalScope().getOrPhpNull(Constants.SESSION_CALLBACK_ARRAY_VARIABLE);
			Symbol sessionHandlerSymbol = ip.getGlobalScope().getOrPhpNull(Constants.SESSION_HANDLER_VARIABLE);
			Symbol sessionIdSymbol = ip.getGlobalScope().getOrPhpNull(Constants.SESSION_ID_VARIABLE);
			Symbol sessionSymbol = ip.getGlobalScope().getOrPhpNull("$_SESSION");
			PhpCallable callable = ip.getFunction("session_decode");

			Symbol serializedSessionSymbol1 = SymbolUtils.callCallable(ip,
					operator.getArrayValue(sessionCallbackArraySymbol, "read"),
					Lists.newArrayList(sessionIdSymbol));
			if (callable != null) {
				operator.assign(sessionSymbol,
						SymbolUtils.callFunction(ip, callable, Lists.newArrayList(serializedSessionSymbol1)));
			}
			Symbol serializedSessionSymbol2 = SymbolUtils.callMethod(ip,
					sessionHandlerSymbol,
					operator.createSymbol("read"),
					Lists.newArrayList(sessionIdSymbol));
			if (callable != null) {
				operator.assign(sessionSymbol,
						SymbolUtils.callFunction(ip, callable, Lists.newArrayList(serializedSessionSymbol2)));
			}
			return operator.bool();
		}
	}

	// function session_destroy () {}
	public static class session_destroy implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function session_unset () {}
	public static class session_unset implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.null_();
		}
	}

	// function session_set_save_handler ($open, $close, $read, $write, $destroy, $gc) {}
	// function session_set_save_handler (SessionHandlerInterface $session_handler, $register_shutdown = true) {}
	public static class session_set_save_handler implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol sessionIdSymbol = ip.getGlobalScope().getOrPhpNull(Constants.SESSION_ID_VARIABLE);
			Symbol sessionSymbol = ip.getGlobalScope().getOrPhpNull("$_SESSION");
			Symbol sessionCallbackArraySymbol =
					ip.getGlobalScope().getOrPhpNull(Constants.SESSION_CALLBACK_ARRAY_VARIABLE);
			Symbol sessionHandlerSymbol = ip.getGlobalScope().getOrPhpNull(Constants.SESSION_HANDLER_VARIABLE);
			// コールバックは全て呼ぶ
			if (ip.getContext().getArgumentSymbolList().size() == 6) {
				// open(string $savePath, string $sessionName)
				Symbol openSymbol = ip.getContext().getArgumentSymbolList().get(0);
				operator.putArrayValue(sessionCallbackArraySymbol, "open", openSymbol);
				SymbolUtils.callCallable(ip,
						openSymbol,
						Lists.newArrayList(operator.string(), operator.string()));
				// close()
				Symbol closeSymbol = ip.getContext().getArgumentSymbolList().get(1);
				operator.putArrayValue(sessionCallbackArraySymbol, "close", closeSymbol);
				SymbolUtils.callCallable(ip,
						closeSymbol,
						Lists.newArrayList());
				// read(string $sessionId)
				// read コールバックは、常にセッションエンコード (シリアライズ) された文字列を返さなければなりません。
				Symbol readSymbol = ip.getContext().getArgumentSymbolList().get(2);
				operator.putArrayValue(sessionCallbackArraySymbol, "read", readSymbol);
				Symbol serializedSessionSymbol = SymbolUtils.callCallable(ip,
						readSymbol,
						Lists.newArrayList(sessionIdSymbol));
				PhpCallable callable = ip.getFunction("session_decode");
				if (callable != null) {
					operator.assign(sessionSymbol,
							SymbolUtils.callFunction(ip, callable, Lists.newArrayList(serializedSessionSymbol)));
				}
				// write(string $sessionId, string $data)
				//  このコールバックが受け取るのは、現在のセッション ID とシリアライズ後のスーパーグローバル $_SESSION です。
				Symbol writeSymbol = ip.getContext().getArgumentSymbolList().get(3);
				operator.putArrayValue(sessionCallbackArraySymbol, "write", writeSymbol);
				SymbolUtils.callCallable(ip,
						writeSymbol,
						Lists.newArrayList(sessionIdSymbol, serialize(ip, sessionSymbol)));
				// destroy($sessionId)
				Symbol destroySymbol = ip.getContext().getArgumentSymbolList().get(4);
				operator.putArrayValue(sessionCallbackArraySymbol, "destroy", destroySymbol);
				SymbolUtils.callCallable(ip,
						destroySymbol,
						Lists.newArrayList(sessionIdSymbol));
				// gc($lifetime)
				Symbol gcSymbol = ip.getContext().getArgumentSymbolList().get(5);
				operator.putArrayValue(sessionCallbackArraySymbol, "gc", gcSymbol);
				SymbolUtils.callCallable(ip,
						gcSymbol,
						Lists.newArrayList(operator.integer()));
			} else {
				Symbol sessionhandlerSymbol = SymbolUtils.getArgument(ip, 0);
				operator.assign(sessionHandlerSymbol, sessionhandlerSymbol);
				//	abstract public bool close ( void )
				SymbolUtils.callMethod(ip,
						sessionhandlerSymbol,
						operator.createSymbol("close"),
						Lists.newArrayList());
				//	abstract public bool destroy ( string $session_id )
				SymbolUtils.callMethod(ip,
						sessionhandlerSymbol,
						operator.createSymbol("destroy"),
						Lists.newArrayList(sessionIdSymbol));
				//	abstract public bool gc ( int $maxlifetime )
				SymbolUtils.callMethod(ip,
						sessionhandlerSymbol,
						operator.createSymbol("gc"),
						Lists.newArrayList(operator.integer()));
				//	abstract public bool open ( string $save_path , string $name )
				SymbolUtils.callMethod(ip,
						sessionhandlerSymbol,
						operator.createSymbol("open"),
						Lists.newArrayList(operator.string(), operator.string()));
				//	abstract public string read ( string $session_id )
				Symbol serializedSessionSymbol = SymbolUtils.callMethod(ip,
						sessionhandlerSymbol,
						operator.createSymbol("read"),
						Lists.newArrayList(sessionIdSymbol));
				PhpCallable callable = ip.getFunction("session_decode");
				if (callable != null) {
					operator.assign(sessionSymbol,
							SymbolUtils.callFunction(ip, callable, Lists.newArrayList(serializedSessionSymbol)));
				}
				//	abstract public bool write ( string $session_id , string $session_data )
				SymbolUtils.callMethod(ip,
						sessionhandlerSymbol,
						operator.createSymbol("write"),
						Lists.newArrayList(sessionIdSymbol, serialize(ip, sessionSymbol)));
			}
			return operator.bool();
		}
	}

	// function session_cache_limiter ($cache_limiter = null) {}
	public static class session_cache_limiter implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function session_cache_expire ($new_cache_expire = null) {}
	public static class session_cache_expire implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function session_set_cookie_params ($lifetime, $path = null, $domain = null, $secure = false, $httponly = false) {}
	public static class session_set_cookie_params implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.null_();
		}
	}

	// function session_get_cookie_params () {}
	public static class session_get_cookie_params implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array();
		}
	}

	// function session_write_close () {}
	public static class session_write_close implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.null_();
		}
	}

	// function session_commit () {}
	//  * Alias of <b>session_write_close</b>
	public static class session_commit extends session_write_close {
	}

	// function session_status () {}
	public static class session_status implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function session_abort() {}
	public static class session_abort implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function session_reset() {}
	public static class session_reset implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

}
