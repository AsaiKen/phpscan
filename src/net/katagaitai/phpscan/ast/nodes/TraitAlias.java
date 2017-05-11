package net.katagaitai.phpscan.ast.nodes;

import lombok.Getter;
import lombok.Setter;
import net.katagaitai.phpscan.ast.PHPFlags;
import net.katagaitai.phpscan.ast.Visitor;

public class TraitAlias extends Expression {
	@Setter
	@Getter
	private Expression traitMethod;
	@Setter
	@Getter
	private int modifier;
	@Setter
	@Getter
	private int modifierOffset;
	@Setter
	@Getter
	private Identifier functionName;

	public TraitAlias(int start, int end, AST ast, Expression traitMethod, int modifier, int modifierOffset,
			Identifier functionName) {
		super(start, end, ast);
		setTraitMethod(traitMethod);
		setModifier(modifier);
		setModifierOffset(modifierOffset);
		setFunctionName(functionName);
	}

	public TraitAlias(AST ast) {
		super(ast);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<TraitAlias"); //$NON-NLS-1$
		appendInterval(buffer);
		if (functionName != null) {
			buffer.append(" functionName='").append(functionName.getName()).append("' "); //$NON-NLS-1$ //$NON-NLS-2$
		}
		buffer.append(" modifier='").append(PHPFlags.toString(modifier)).append("'>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		traitMethod.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(tab).append("</TraitAlias>"); //$NON-NLS-1$
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
