package net.katagaitai.phpscan.interpreter;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import net.katagaitai.phpscan.command.Command;
import net.katagaitai.phpscan.command.CommentSource;
import net.katagaitai.phpscan.interceptor.CommandInterceptor;
import net.katagaitai.phpscan.interceptor.Interceptor;

@Log4j2
@ToString
@AllArgsConstructor
public class CommandDecorator implements Command {
	private Command decorated;

	@Override
	public void accept(Interpreter ip) {
		if (ip.isTimeout()) {
			return;
		}
		ip.incrementCounter();
		ip.getContext().setCommand(decorated);
		SourcePosition savedSourcePosition = ip.getSourcePosition();
		try {
			decorated.accept(ip);
		} finally {
			if (decorated instanceof CommentSource) {
				// pass
			} else {
				ip.setSourcePosition(savedSourcePosition);
			}
			ip.getContext().setCommand(decorated);
			for (Interceptor interceptor : ip.getInterceptorList()) {
				if (interceptor instanceof CommandInterceptor) {
					((CommandInterceptor) interceptor).intercept(decorated);
				}
			}
		}
	}
}
