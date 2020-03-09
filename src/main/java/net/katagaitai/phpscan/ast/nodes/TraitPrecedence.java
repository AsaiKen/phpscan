package net.katagaitai.phpscan.ast.nodes;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import net.katagaitai.phpscan.ast.Visitor;

import com.google.common.collect.Lists;

public class TraitPrecedence extends Expression {
	@Setter
	@Getter
	private FullyQualifiedTraitMethodReference methodReference;
	@Getter
	private List<NamespaceName> trList = Lists.newArrayList();

	public TraitPrecedence(int start, int end, AST ast, FullyQualifiedTraitMethodReference methodReference,
			List<NamespaceName> trList) {
		super(start, end, ast);
		setMethodReference(methodReference);

		if (trList != null) {
			this.trList.addAll(trList);
		}
	}

	public TraitPrecedence(AST ast) {
		super(ast);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<TraitPrecedence"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		methodReference.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		if (trList != null) {
			buffer.append(TAB).append(tab).append("<TraitReferenceList>\n"); //$NON-NLS-1$
			for (NamespaceName name : trList) {
				name.toString(buffer, TAB + TAB + tab);
				buffer.append("\n"); //$NON-NLS-1$
			}
			buffer.append(TAB).append(tab).append("</TraitReferenceList>\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append("</TraitPrecedence>"); //$NON-NLS-1$
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
