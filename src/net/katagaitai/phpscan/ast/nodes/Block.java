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

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import net.katagaitai.phpscan.ast.Visitor;

import com.google.common.collect.Lists;

/**
 * Represents a block of statements
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * { statement1; statement2; }, : statement1; statement2; ,
 */
public class Block extends Statement {
	@Getter
	private final List<Statement> statements = Lists.newArrayList();
	@Getter
	@Setter
	private boolean isCurly;

	private static enum BodyStartSymbol {
		NONE, BRACKLET, COLON
	};

	private BodyStartSymbol bodyStartSymbol = BodyStartSymbol.NONE;

	private Block(int start, int end, AST ast, Statement[] statements, boolean isCurly) {
		super(start, end, ast);

		if (statements == null) {
			throw new IllegalArgumentException();
		}

		setCurly(isCurly);
		// set the child nodes' parent
		for (int i = 0; i < statements.length; i++) {
			this.statements.add(statements[i]);
		}
	}

	public Block(int start, int end, AST ast, List statements, boolean isCurly) {
		this(start, end, ast,
				statements == null ? null : (Statement[]) statements.toArray(new Statement[statements.size()]),
				isCurly);
	}

	public Block(int start, int end, AST ast, List statements) {
		this(start, end, ast, statements, true);
	}

	public Block(AST ast) {
		super(ast);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<Block"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(" isCurly='").append(isCurly).append("'>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		for (ASTNode statement : statements) {
			statement.toString(buffer, TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append("</Block>"); //$NON-NLS-1$
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
