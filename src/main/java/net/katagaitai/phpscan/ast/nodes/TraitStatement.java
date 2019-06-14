package net.katagaitai.phpscan.ast.nodes;

import lombok.Getter;
import lombok.Setter;

public abstract class TraitStatement extends Statement {
    @Setter
    @Getter
    private Expression exp;

    public TraitStatement(int start, int end, AST ast, Expression exp) {
        super(start, end, ast);
        setExp(exp);
    }

    public TraitStatement(AST ast) {
        super(ast);
    }

    public void toString(StringBuffer buffer, String tab) {
        buffer.append(tab).append("<TraitStatement"); //$NON-NLS-1$
        appendInterval(buffer);
        buffer.append(">\n"); //$NON-NLS-1$
        exp.toString(buffer, TAB + tab);
        buffer.append("\n"); //$NON-NLS-1$
        buffer.append(tab).append("</TraitStatement>"); //$NON-NLS-1$
    }

}
