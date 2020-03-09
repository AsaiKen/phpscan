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

import java.util.List;

import lombok.Getter;
import net.katagaitai.phpscan.ast.Visitor;

import com.google.common.collect.Lists;

/**
 * Represents a list expression. The list contains variables and/or other lists.
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * list($a,$b) = array (1,2), list($a, list($b, $c))
 */
public class ListVariable extends VariableBase {

	@Getter
	private final List<VariableBase> variables = Lists.newArrayList();

	private ListVariable(int start, int end, AST ast, VariableBase[] variables) {
		super(start, end, ast);

		if (variables == null) {
			throw new IllegalArgumentException();
		}
		for (VariableBase variableBase : variables) {
			this.variables.add(variableBase);
		}
	}

	public ListVariable(AST ast) {
		super(ast);
	}

	public ListVariable(int start, int end, AST ast, List variables) {
		this(start, end, ast,
				variables == null ? null : (VariableBase[]) variables.toArray(new VariableBase[variables.size()]));
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<List"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		for (ASTNode node : this.variables) {
			node.toString(buffer, TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append("</List>"); //$NON-NLS-1$
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
