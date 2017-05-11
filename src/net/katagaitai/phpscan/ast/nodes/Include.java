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
 * Represents include, include_once, require and require_once expressions
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * include('myFile.php'), include_once($myFile),
 * require($myClass->getFileName()), require_once(A::FILE_NAME)
 */
public class Include extends Expression {

	public static final int IT_REQUIRE = 0;
	public static final int IT_REQUIRE_ONCE = 1;
	public static final int IT_INCLUDE = 2;
	public static final int IT_INCLUDE_ONCE = 3;

	@Setter
	@Getter
	private Expression expression;
	@Setter
	@Getter
	private int includeType;

	public Include(int start, int end, AST ast, Expression expr, int type) {
		super(start, end, ast);

		if (expr == null) {
			throw new IllegalArgumentException();
		}
		setExpression(expr);
		setIncludeType(type);
	}

	public Include(AST ast) {
		super(ast);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<Include"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(" kind='").append(getType(includeType)).append("'>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		expression.toString(buffer, TAB + tab);
		buffer.append("\n").append(tab).append("</Include>"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public static String getType(int type) {
		switch (type) {
		case IT_REQUIRE:
			return "require"; //$NON-NLS-1$
		case IT_REQUIRE_ONCE:
			return "require_once"; //$NON-NLS-1$
		case IT_INCLUDE:
			return "include"; //$NON-NLS-1$
		case IT_INCLUDE_ONCE:
			return "include_once"; //$NON-NLS-1$
		default:
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
