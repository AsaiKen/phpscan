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
 * Represents a global statement
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * global $a global $a, $b global ${foo()->bar()}, global $$a
 */
public class GlobalStatement extends Statement {

	@Getter
	private final List<Variable> variables = Lists.newArrayList();

	private GlobalStatement(int start, int end, AST ast, Variable[] variables) {
		super(start, end, ast);

		if (variables == null) {
			throw new IllegalArgumentException();
		}
		for (Variable variable : variables) {
			this.variables.add(variable);
		}
	}

	public GlobalStatement(int start, int end, AST ast, List variables) {
		this(start, end, ast,
				variables == null ? null : (Variable[]) variables.toArray(new Variable[variables.size()]));
	}

	public GlobalStatement(AST ast) {
		super(ast);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<GlobalStatement"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		for (ASTNode node : this.variables) {
			node.toString(buffer, TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append("</GlobalStatement>"); //$NON-NLS-1$
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
