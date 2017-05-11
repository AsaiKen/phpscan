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
 * Represents a switch statement.
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * switch ($i) { case 0: echo "i equals 0"; break; case 1: echo "i equals 1";
 * break; default: echo "i not equals 0 or 1"; break; }
 */
public class SwitchStatement extends Statement {
	@Setter
	@Getter
	private Expression expression;
	@Setter
	@Getter
	private Block body;

	public SwitchStatement(AST ast) {
		super(ast);
	}

	public SwitchStatement(int start, int end, AST ast, Expression expression, Block body) {
		super(start, end, ast);

		if (expression == null || body == null) {
			throw new IllegalArgumentException();
		}

		setExpression(expression);
		setBody(body);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<SwitchStatement"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("<Expression>\n"); //$NON-NLS-1$
		expression.toString(buffer, TAB + TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("</Expression>\n"); //$NON-NLS-1$
		body.toString(buffer, TAB + tab);
		buffer.append("\n").append(tab).append("</SwitchStatement>"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
