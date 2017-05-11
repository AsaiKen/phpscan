package net.katagaitai.phpscan.ast.nodes;

import lombok.Getter;
import net.katagaitai.phpscan.ast.Visitor;

public class TraitAliasStatement extends TraitStatement {
	@Getter
	private TraitAlias alias;

	public TraitAliasStatement(int start, int end, AST ast, TraitAlias alias) {
		super(start, end, ast, alias);
		this.alias = alias;
	}

	public TraitAliasStatement(AST ast) {
		super(ast);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<TraitAliasStatement"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		alias.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(tab).append("</TraitAliasStatement>"); //$NON-NLS-1$
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
