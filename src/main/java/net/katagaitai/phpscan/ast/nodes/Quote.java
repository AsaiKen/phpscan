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
 * Represents complex qoute(i.e. qoute that includes string and variables). Also
 * represents heredoc
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * "this is $a quote", "'single ${$complex->quote()}'" <<<Heredoc\n This is here
 * documents \nHeredoc;\n
 *
 * Note: "This is".$not." a quote node", 'This is $not a quote too'
 */
public class Quote extends VariableBase {

	public static final int QT_QUOTE = 0;
	public static final int QT_SINGLE = 1;
	public static final int QT_HEREDOC = 2;
	public static final int QT_NOWDOC = 3;

	@Getter
	private final List<Expression> expressions = Lists.newArrayList();
	@Setter
	@Getter
	private int quoteType;

	public Quote(int start, int end, AST ast, Expression[] expressions, int type) {
		super(start, end, ast);

		for (Expression expression : expressions) {
			this.expressions.add(expression);
		}
		setQuoteType(type);
	}

	public Quote(AST ast) {
		super(ast);
	}

	public Quote(int start, int end, AST ast, List expressions, int type) {
		this(start, end, ast,
				expressions == null ? null : (Expression[]) expressions.toArray(new Expression[expressions.size()]),
				type);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<Quote"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(" type='").append(getType(quoteType)).append("'>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		for (ASTNode node : this.expressions) {
			node.toString(buffer, TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append("</Quote>"); //$NON-NLS-1$
	}

	public static String getType(int type) {
		switch (type) {
		case QT_QUOTE:
			return "quote"; //$NON-NLS-1$
		case QT_SINGLE:
			return "single"; //$NON-NLS-1$
		case QT_HEREDOC:
			return "heredoc"; //$NON-NLS-1$
		case QT_NOWDOC:
			return "nowdoc"; //$NON-NLS-1$
		default:
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
