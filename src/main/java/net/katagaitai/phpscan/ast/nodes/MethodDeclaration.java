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
 * Represents a function declaration in a class Holds the function modifier
 *
 * @see {@link FunctionDeclaration}
 */
public class MethodDeclaration extends BodyDeclaration {
	@Setter
	@Getter
	private FunctionDeclaration function;
	@Setter
	@Getter
	private Comment comment;

	public MethodDeclaration(int start, int end, AST ast, int modifier, FunctionDeclaration function,
			boolean shouldComplete) {
		super(start, end, ast, modifier, shouldComplete);

		if (function == null) {
			throw new IllegalArgumentException();
		}
		setFunction(function);
	}

	public MethodDeclaration(int start, int end, AST ast, int modifier, FunctionDeclaration function) {
		this(start, end, ast, modifier, function, false);
	}

	public MethodDeclaration(AST ast) {
		super(ast);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<MethodDeclaration"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(" modifier='").append(getModifierString()).append("'>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		function.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(tab).append("</MethodDeclaration>"); //$NON-NLS-1$
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
