package net.katagaitai.phpscan.ast.nodes;

import net.katagaitai.phpscan.ast.Visitor;

public interface Visitable {
    String TAB = "\t"; //$NON-NLS-1$

    Object accept(Visitor visitor);

    void toString(StringBuffer buffer, String tab);
}
