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
import net.katagaitai.phpscan.ast.Visitor;

/**
 * Represents a catch clause (as part of a try statement)
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * catch (ExceptionClassName $variable) { body; },
 *
 */
public class FinallyClause extends Statement {
	@Getter
	private Block body;

	public FinallyClause(int start, int end, AST ast, Block statement) {
		super(start, end, ast);

		this.body = statement;
		statement.setParent(this);
	}

	public FinallyClause(AST ast) {
		super(ast);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<FinallyClause"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		body.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(tab).append("</FinallyClause>"); //$NON-NLS-1$
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
