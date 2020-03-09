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
 * Represents static function invocation. Holds the function invocation and the
 * class name.
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * MyClass::foo($a)
 */
public class StaticMethodInvocation extends StaticDispatch {
	@Setter
	@Getter
	private FunctionInvocation method;

	public StaticMethodInvocation(int start, int end, AST ast, Expression className, FunctionInvocation method) {
		super(start, end, ast, className);

		if (method == null) {
			throw new IllegalArgumentException();
		}
		setMethod(method);
	}

	public StaticMethodInvocation(AST ast) {
		super(ast);
	}

	/**
	 * @see #getMethod()
	 */
	public ASTNode getMember() {
		return getMethod();
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<StaticMethodInvocation"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("<ClassName>\n"); //$NON-NLS-1$
		getClassName().toString(buffer, TAB + TAB + tab);
		buffer.append("\n").append(TAB).append(tab).append("</ClassName>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		method.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(tab).append("</StaticMethodInvocation>"); //$NON-NLS-1$
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
