package net.katagaitai.phpscan.ast.nodes;

import net.katagaitai.phpscan.ast.Visitor;

public interface Visitable {
	public static final String TAB = "\t"; //$NON-NLS-1$

	public Object accept(Visitor visitor);

	public void toString(StringBuffer buffer, String tab);
}
