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
 * Represents the static statement
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * static $a;
 * static $a, $b=5;
 */
public class StaticStatement extends Statement {

	@Getter
	private List<Expression> expressions = Lists.newArrayList();

	private StaticStatement(int start, int end, AST ast, Expression[] expressions) {
		super(start, end, ast);

		if (expressions == null) {
			throw new IllegalArgumentException();
		}
		for (Expression expression : expressions) {
			this.expressions.add(expression);
		}
	}

	public StaticStatement(AST ast) {
		super(ast);
	}

	public StaticStatement(int start, int end, AST ast, List expressions) {
		this(start, end, ast,
				expressions == null ? null : (Expression[]) expressions.toArray(new Expression[expressions.size()]));
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<StaticStatement"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		for (ASTNode node : this.expressions) {
			node.toString(buffer, TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append("</StaticStatement>"); //$NON-NLS-1$
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
