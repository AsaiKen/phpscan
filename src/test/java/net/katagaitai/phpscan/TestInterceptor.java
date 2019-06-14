package net.katagaitai.phpscan;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.katagaitai.phpscan.command.Command;
import net.katagaitai.phpscan.interceptor.CommandInterceptor;
import net.katagaitai.phpscan.interpreter.Interpreter;

@RequiredArgsConstructor
public class TestInterceptor implements CommandInterceptor {
    private final Interpreter ip;
    private final VulnerabilityCategory category;
    private final String comment;
    @Getter
    private boolean found;
    @Setter
    private boolean killOnFoung = true;

    @Override
    public void intercept(Command command) {
        for (Vulnerability vulnerability : ip.getVulnerabilityList()) {
            if (vulnerability.getCategory().equals(category)
                    && (comment == null || vulnerability.getCommentList().contains(comment))) {
                if (killOnFoung) {
                    ip.setTimeout(true);
                }
                found = true;
                return;
            }
        }
    }
}
