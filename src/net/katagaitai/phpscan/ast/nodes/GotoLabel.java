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
 * Holds a label declaration that is used in goto expression.
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * START:
 */
public class GotoLabel extends Statement {
	@Setter
	@Getter
	private Identifier name;

	public GotoLabel(int start, int end, AST ast, Identifier name) {
		super(start, end, ast);

		if (name == null) {
			throw new IllegalArgumentException();
		}
		setName(name);
	}

	public GotoLabel(AST ast) {
		super(ast);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<GotoLabel"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		name.toString(buffer, TAB + tab);
		buffer.append("\n").append(tab).append("</GotoLabel>"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
