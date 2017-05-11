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
 * Holds a variable. note that the variable name can be expression,
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * $a
 *
 * Subclasses: {@link ArrayAccess}, {@link ReflectionVariable},
 * {@link StaticFieldAccess}
 */
public class Variable extends VariableBase {
	@Setter
	@Getter
	private Expression name;
	@Setter
	@Getter
	private boolean isDollared;

	protected Variable(int start, int end, AST ast, Expression variableName, boolean isDollared) {
		super(start, end, ast);

		if (variableName == null) {
			throw new IllegalArgumentException();
		}
		setName(variableName);
		setDollared(isDollared);
	}

	protected Variable(int start, int end, AST ast, Expression variableName) {
		this(start, end, ast, variableName, false);
	}

	/**
	 * A simple variable (like $a) can be constructed with a string The string
	 * is warped by an identifier
	 *
	 * @param start
	 * @param end
	 * @param variableName
	 */
	public Variable(int start, int end, AST ast, String variableName) {
		this(start, end, ast, createIdentifier(start, end, ast, variableName), checkIsDollared(variableName));
	}

	public Variable(AST ast) {
		super(ast);
	}

	private static boolean checkIsDollared(String variableName) {
		return variableName.indexOf('$') == 0;
	}

	private static Identifier createIdentifier(int start, int end, AST ast, String idName) {
		if (checkIsDollared(idName)) {
			idName = idName.substring(1);
			// the start position move after the the dollar mark
			start++;
		}
		return new Identifier(start, end, ast, idName);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<Variable"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(" isDollared='").append(isDollared).append("'>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		name.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(tab).append("</Variable>"); //$NON-NLS-1$
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
