package net.katagaitai.phpscan.ast.nodes;

import net.katagaitai.phpscan.ast.Visitor;

import java.util.List;

public class TraitDeclaration extends ClassDeclaration {

    public TraitDeclaration(int start, int end, AST ast, int modifier, Identifier className, Expression superClass,
                            List interfaces, Block body) {
        super(start, end, ast, modifier, className, superClass, interfaces, body);
    }

    public TraitDeclaration(AST ast) {
        super(ast);
    }

    public void toString(StringBuffer buffer, String tab) {
        buffer.append(tab).append("<TraitDeclaration"); //$NON-NLS-1$
        appendInterval(buffer);
        buffer.append("'>\n"); //$NON-NLS-1$ //$NON-NLS-2$
        buffer.append(tab).append(TAB).append("<TraitName>\n"); //$NON-NLS-1$
        getName().toString(buffer, TAB + TAB + tab);
        buffer.append("\n"); //$NON-NLS-1$
        buffer.append(tab).append(TAB).append("</TraitName>\n"); //$NON-NLS-1$

        getBody().toString(buffer, TAB + tab);
        buffer.append("\n"); //$NON-NLS-1$
        buffer.append(tab).append("</TraitDeclaration>"); //$NON-NLS-1$
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
