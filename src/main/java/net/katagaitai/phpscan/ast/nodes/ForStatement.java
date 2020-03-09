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
import lombok.Setter;
import net.katagaitai.phpscan.ast.Visitor;

import com.google.common.collect.Lists;

/**
 * Represents a for statement
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * for (expr1; expr2; expr3) statement;
 *
 * for (expr1; expr2; expr3): statement ... endfor;
 */
public class ForStatement extends Statement {

	@Getter
	private final List<Expression> initializers = Lists.newArrayList();
	@Getter
	private final List<Expression> conditions = Lists.newArrayList();
	@Getter
	private final List<Expression> updaters = Lists.newArrayList();
	@Setter
	@Getter
	private Statement body;

	private ForStatement(int start, int end, AST ast, Expression[] initializations, Expression[] conditions,
			Expression[] increasements, Statement action) {
		super(start, end, ast);

		if (initializations == null || conditions == null || increasements == null || action == null) {
			throw new IllegalArgumentException();
		}
		for (Expression init : initializations) {
			this.initializers.add(init);
		}
		for (Expression cond : conditions) {
			this.conditions.add(cond);
		}
		for (Expression inc : increasements) {
			this.updaters.add(inc);
		}
		setBody(action);
	}

	public ForStatement(int start, int end, AST ast, List initializations, List conditions, List increasements,
			Statement action) {
		this(start, end, ast,
				initializations == null ? null
						: (Expression[]) initializations.toArray(new Expression[initializations.size()]),
				conditions == null ? null : (Expression[]) conditions.toArray(new Expression[conditions.size()]),
				increasements == null ? null
						: (Expression[]) increasements.toArray(new Expression[increasements.size()]),
				action);
	}

	public ForStatement(AST ast) {
		super(ast);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<ForStatement"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("<Initializations>\n"); //$NON-NLS-1$
		for (ASTNode node : this.initializers) {
			node.toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(TAB).append(tab).append("</Initializations>\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("<Conditions>\n"); //$NON-NLS-1$
		for (ASTNode node : this.conditions) {
			node.toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(TAB).append(tab).append("</Conditions>\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("<Increasements>\n"); //$NON-NLS-1$
		for (ASTNode node : this.updaters) {
			node.toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(TAB).append(tab).append("</Increasements>\n"); //$NON-NLS-1$
		body.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(tab).append("</ForStatement>"); //$NON-NLS-1$
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}
}
