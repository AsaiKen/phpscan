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
 * Represents a PHP comment
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * // this is a single line comment # this is a single line comment /** this is
 * php doc block (end php docblock here)
 */
public class Comment extends ASTNode {

	public final static int TYPE_SINGLE_LINE = 0;
	public final static int TYPE_MULTILINE = 1;
	public final static int TYPE_PHPDOC = 2;
	@Setter
	@Getter
	private int commentType;

	public Comment(int start, int end, AST ast, int type) {
		super(start, end, ast);

		setCommentType(type);
	}

	public Comment(AST ast) {
		super(ast);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<Comment"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(" commentType='").append(getCommentType(commentType)).append("'/>"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public static String getCommentType(int type) {
		switch (type) {
		case TYPE_SINGLE_LINE:
			return "singleLine"; //$NON-NLS-1$
		case TYPE_MULTILINE:
			return "multiLine"; //$NON-NLS-1$
		case TYPE_PHPDOC:
			return "phpDoc"; //$NON-NLS-1$
		default:
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
