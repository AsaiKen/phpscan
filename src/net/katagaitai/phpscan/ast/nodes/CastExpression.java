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
 * Represents a type casting expression
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * (int) $a, (string) $b->foo()
 */
public class CastExpression extends Expression {

	// 'int'
	public static final int TYPE_INT = 0;
	// 'real'
	public static final int TYPE_REAL = 1;
	// 'string'
	public static final int TYPE_STRING = 2;
	// 'array'
	public static final int TYPE_ARRAY = 3;
	// 'object'
	public static final int TYPE_OBJECT = 4;
	// 'bool'
	public static final int TYPE_BOOL = 5;
	// 'unset'
	public static final int TYPE_UNSET = 6;

	@Setter
	@Getter
	private Expression expression;
	@Setter
	@Getter
	private int castingType;

	public CastExpression(int start, int end, AST ast, Expression expr, int castType) {
		super(start, end, ast);

		if (expr == null) {
			throw new IllegalArgumentException();
		}
		setExpression(expr);
		setCastingType(castType);
	}

	public CastExpression(AST ast) {
		super(ast);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<CastExpression"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(" castType='").append(getCastType(castingType)).append("'>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		expression.toString(buffer, TAB + tab);
		buffer.append("\n").append(tab).append("</CastExpression>"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public static String getCastType(int type) {
		switch (type) {
		case TYPE_INT:
			return "int"; //$NON-NLS-1$
		case TYPE_REAL:
			return "real"; //$NON-NLS-1$
		case TYPE_STRING:
			return "string"; //$NON-NLS-1$
		case TYPE_ARRAY:
			return "array"; //$NON-NLS-1$
		case TYPE_OBJECT:
			return "object"; //$NON-NLS-1$
		case TYPE_BOOL:
			return "bool"; //$NON-NLS-1$
		case TYPE_UNSET:
			return "unset"; //$NON-NLS-1$
		default:
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
