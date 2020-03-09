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
 * Represents a prefix expression
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * --$a, --foo()
 */
public class PrefixExpression extends Expression implements IOperationNode {

	// '++'
	public static final int OP_INC = 0;
	// '--'
	public static final int OP_DEC = 1;
	// '...'
	public static final int OP_UNPACK = 2;

	@Setter
	@Getter
	private Expression variable;
	@Setter
	@Getter
	private int operator;

	public PrefixExpression(AST ast) {
		super(ast);
	}

	public PrefixExpression(int start, int end, AST ast, Expression variable, int operator) {
		super(start, end, ast);

		if (variable == null) {
			throw new IllegalArgumentException();
		}

		setVariable(variable);
		setOperator(operator);
	}

	public static String getOperator(int operator) {
		switch (operator) {
		case OP_DEC:
			return "--"; //$NON-NLS-1$
		case OP_INC:
			return "++"; //$NON-NLS-1$
		case OP_UNPACK:
			return "..."; //$NON-NLS-1$
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
		buffer.append(tab).append("<PrefixExpression"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(" operator='").append(getOperator(operator)).append("'>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		variable.toString(buffer, TAB + tab);
		buffer.append("\n").append(tab).append("</PrefixExpression>"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
