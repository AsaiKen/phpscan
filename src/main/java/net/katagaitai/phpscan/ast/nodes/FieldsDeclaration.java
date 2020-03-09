/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package net.katagaitai.phpscan.ast.nodes;

import java.util.Iterator;
import java.util.List;

import lombok.Getter;
import net.katagaitai.phpscan.ast.Visitor;

import com.google.common.collect.Lists;

/**
 * Represents a fields declaration
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * var $a, $b; public $a = 3; final private static $var;
 */
public class FieldsDeclaration extends BodyDeclaration {

	@Getter
	private final List<SingleFieldDeclaration> fields = Lists.newArrayList();

	public FieldsDeclaration(int start, int end, AST ast, int modifier, List variablesAndDefaults) {
		super(start, end, ast, modifier);

		if (variablesAndDefaults == null || variablesAndDefaults.size() == 0) {
			throw new IllegalArgumentException();
		}

		for (Iterator iter = variablesAndDefaults.iterator(); iter.hasNext();) {
			final Object next = iter.next();
			if (next instanceof SingleFieldDeclaration) {
				this.fields.add((SingleFieldDeclaration) next);
			} else {
				ASTNode[] element = (ASTNode[]) next;
				SingleFieldDeclaration field = createField(ast, (Variable) element[0], (Expression) element[1]);
				this.fields.add(field);
			}
		}
	}

	public FieldsDeclaration(AST ast) {
		super(ast);
	}

	private SingleFieldDeclaration createField(AST ast, Variable name, Expression value) {
		int start = name.getStart();
		int end = value == null ? name.getEnd() : value.getEnd();
		final SingleFieldDeclaration result = new SingleFieldDeclaration(start, end, ast, name, value);
		return result;
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<FieldsDeclaration"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(" modifier='").append(getModifierString()).append("'>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		for (SingleFieldDeclaration node : this.fields) {
			buffer.append(tab).append(TAB).append("<VariableName>\n"); //$NON-NLS-1$
			node.getName().toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
			buffer.append(tab).append(TAB).append("</VariableName>\n"); //$NON-NLS-1$
			buffer.append(tab).append(TAB).append("<InitialValue>\n"); //$NON-NLS-1$
			Expression expr = node.getValue();
			if (expr != null) {
				expr.toString(buffer, TAB + TAB + tab);
				buffer.append("\n"); //$NON-NLS-1$
			}
			buffer.append(tab).append(TAB).append("</InitialValue>\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append("</FieldsDeclaration>"); //$NON-NLS-1$
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
