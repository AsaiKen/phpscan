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

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import net.katagaitai.phpscan.ast.Visitor;

/**
 * Represents namespace declaration:
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * namespace MyNamespace; namespace MyProject\Sub\Level;
 */
public class NamespaceDeclaration extends Statement {
	@Setter
	@Getter
	private NamespaceName name;
	@Setter
	@Getter
	private Block body;
	@Setter
	@Getter
	private boolean bracketed = true;

	public NamespaceDeclaration(AST ast) {
		super(ast);
	}

	public NamespaceDeclaration(int start, int end, AST ast, NamespaceName name, Block body, boolean bracketed) {
		super(start, end, ast);

		if (!bracketed && name == null) {
			throw new IllegalArgumentException("Namespace name must not be null in an un-bracketed statement"); //$NON-NLS-1$
		}

		this.bracketed = bracketed;

		if (body == null) {
			body = new Block(end + 1, end + 1, ast, new ArrayList(), false);
		}
		body.setParent(this);

		if (name != null) {
			name.setParent(this);
		}

		this.name = name;
		this.body = body;
	}

	public void addStatement(Statement statement) {
		Block body = getBody();
		body.getStatements().add(statement);

		int statementEnd = statement.getEnd();
		int bodyStart = body.getStart();
		body.setSourceRange(bodyStart, statementEnd - bodyStart + 1);

		int namespaceStart = getStart();
		setSourceRange(namespaceStart, statementEnd - namespaceStart);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<NamespaceDeclaration"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(" isBracketed='").append(bracketed).append("'>\n"); //$NON-NLS-1$ //$NON-NLS-2$

		NamespaceName name = getName();
		if (name != null) {
			name.toString(buffer, TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}

		Block body = getBody();
		body.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$

		buffer.append(tab).append("</NamespaceDeclaration>"); //$NON-NLS-1$
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
