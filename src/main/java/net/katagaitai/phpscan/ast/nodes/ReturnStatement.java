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

import lombok.Getter;
import lombok.Setter;
import net.katagaitai.phpscan.ast.Visitor;

/**
 * Represent a return statement
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * return; return $a;
 */
public class ReturnStatement extends Statement {
	@Setter
	@Getter
	private Expression expression;

	public ReturnStatement(int start, int end, AST ast) {
		this(start, end, ast, null);
	}

	public ReturnStatement(AST ast) {
		super(ast);
	}

	public ReturnStatement(int start, int end, AST ast, Expression expr) {
		super(start, end, ast);

		if (expr != null) {
			setExpression(expr);
		}
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<ReturnStatement"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		if (expression != null) {
			expression.toString(buffer, TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append("</ReturnStatement>"); //$NON-NLS-1$
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
