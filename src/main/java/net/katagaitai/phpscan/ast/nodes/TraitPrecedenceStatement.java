package net.katagaitai.phpscan.ast.nodes;

import lombok.Getter;
import net.katagaitai.phpscan.ast.Visitor;

public class TraitPrecedenceStatement extends TraitStatement {
    @Getter
    private TraitPrecedence precedence;

    public TraitPrecedenceStatement(int start, int end, AST ast, TraitPrecedence precedence) {
        super(start, end, ast, precedence);
        this.precedence = precedence;
    }

    public TraitPrecedenceStatement(AST ast) {
        super(ast);
    }

    public void toString(StringBuffer buffer, String tab) {
        buffer.append(tab).append("<TraitPrecedenceStatement"); //$NON-NLS-1$
        appendInterval(buffer);
        buffer.append(">\n"); //$NON-NLS-1$
        precedence.toString(buffer, TAB + tab);
        buffer.append("\n"); //$NON-NLS-1$
        buffer.append(tab).append("</TraitPrecedenceStatement>"); //$NON-NLS-1$
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
