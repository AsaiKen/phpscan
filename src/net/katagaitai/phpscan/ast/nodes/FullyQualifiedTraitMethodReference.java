package net.katagaitai.phpscan.ast.nodes;

import lombok.Getter;
import lombok.Setter;
import net.katagaitai.phpscan.ast.Visitor;

public class FullyQualifiedTraitMethodReference extends Expression {
	@Setter
	@Getter
	private NamespaceName className;
	@Setter
	@Getter
	private Identifier functionName;

	public FullyQualifiedTraitMethodReference(int start, int end, AST ast, NamespaceName className,
			Identifier functionName) {
		super(start, end, ast);
		setClassName(className);
		setFunctionName(functionName);
	}

	public FullyQualifiedTraitMethodReference(AST ast) {
		super(ast);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<FunctionName"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(" functionName='").append(functionName.getName()) //$NON-NLS-1$
				.append("'"); //$NON-NLS-1$
		buffer.append(">\n"); //$NON-NLS-1$
		className.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(tab).append("</FunctionName>"); //$NON-NLS-1$
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
