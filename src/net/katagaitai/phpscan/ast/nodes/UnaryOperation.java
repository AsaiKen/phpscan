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
 * Represents an unary operation expression
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * +$a, -3, -foo(), +-+-$a
 */
public class UnaryOperation extends Expression implements IOperationNode {

	// '+'
	public static final int OP_PLUS = 0;
	// '-'
	public static final int OP_MINUS = 1;
	// '!'
	public static final int OP_NOT = 2;
	// '~'
	public static final int OP_TILDA = 3;

	@Setter
	@Getter
	private Expression expression;
	@Setter
	@Getter
	private int operator;

	public UnaryOperation(int start, int end, AST ast, Expression expr, int operator) {
		super(start, end, ast);

		if (expr == null) {
			throw new IllegalArgumentException();
		}
		setExpression(expr);
		setOperator(operator);
	}

	public UnaryOperation(AST ast) {
		super(ast);
	}

	public static String getOperator(int operator) {
		switch (operator) {
		case OP_PLUS:
			return "+"; //$NON-NLS-1$
		case OP_MINUS:
			return "-"; //$NON-NLS-1$
		case OP_NOT:
			return "!"; //$NON-NLS-1$
		case OP_TILDA:
			return "~"; //$NON-NLS-1$
		default:
			throw new IllegalArgumentException();
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.php.internal.core.ast.nodes.IOperationNode#getOperationString
	 * ()
	 */
	public String getOperationString() {
		return getOperator(this.getOperator());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.php.internal.core.ast.nodes.IOperationNode#getOperationString
	 * (int)
	 */
	public String getOperationString(int op) {
		return getOperator(op);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<UnaryOperation"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(" operator='").append(getOperator(operator)).append("'>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		expression.toString(buffer, TAB + tab);
		buffer.append("\n").append(tab).append("</UnaryOperation>"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
