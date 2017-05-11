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

import net.katagaitai.phpscan.ast.Visitor;

/**
 * Represents an indirect reference to a variable.
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * $$a $$foo()
 */
public class ReflectionVariable extends Variable {

	public ReflectionVariable(int start, int end, AST ast, Expression variable) {
		super(start, end, ast, variable);
	}

	public ReflectionVariable(AST ast) {
		super(ast);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<ReflectionVariable"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		getName().toString(buffer, TAB + tab);
		buffer.append("\n").append(tab).append("</ReflectionVariable>"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
