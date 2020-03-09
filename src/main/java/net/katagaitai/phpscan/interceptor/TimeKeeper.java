package net.katagaitai.phpscan.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.katagaitai.phpscan.command.Command;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.util.Constants;

@Log4j2
@RequiredArgsConstructor
public class TimeKeeper implements CommandInterceptor {
	private final Interpreter ip;
	private long timeout = Constants.TIMEOUT;
	private long start = System.currentTimeMillis();

	public TimeKeeper(Interpreter ip2, long timeout2) {
		this(ip2);
		timeout = timeout2;
	}

	@Override
	public void intercept(Command command) {
		if (ip.isTimeout()) {
			return;
		}
		long duration = System.currentTimeMillis() - start;
		if (timeout < duration) {
			log.info("タイムアウトしました。：" + timeout);
			ip.setTimeout(true);
		}
		if (timeout / 2 < duration) {
			ip.setMaxCallStackSize(Constants.MAX_CALL_STACK_SIZE / 2);
		}
	}
}
