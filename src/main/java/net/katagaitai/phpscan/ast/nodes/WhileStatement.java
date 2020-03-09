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
 * Represents while statement.
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * while (expr) statement;
 *
 * while (expr): statement ... endwhile;
 */
public class WhileStatement extends Statement {
	@Setter
	@Getter
	private Expression condition;
	@Setter
	@Getter
	private Statement body;

	public WhileStatement(AST ast) {
		super(ast);
	}

	public WhileStatement(int start, int end, AST ast, Expression condition, Statement action) {
		super(start, end, ast);

		if (condition == null || action == null) {
			throw new IllegalArgumentException();
		}
		setCondition(condition);
		setBody(action);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<WhileStatement"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("<Condition>\n"); //$NON-NLS-1$
		condition.toString(buffer, TAB + TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("</Condition>\n"); //$NON-NLS-1$
		body.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(tab).append("</WhileStatement>"); //$NON-NLS-1$
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
