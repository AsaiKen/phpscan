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
 * Represents a case statement. A case statement is part of switch statement
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * case expr: statement1; break;,
 *
 * default: statement2;
 */
public class SwitchCase extends Statement {
	@Setter
	@Getter
	private Expression value;
	@Setter
	@Getter
	private boolean isDefault;
	@Getter
	private List<Statement> actions = Lists.newArrayList();

	public SwitchCase(int start, int end, AST ast, Expression value, Statement[] actions, boolean isDefault) {
		super(start, end, ast);

		if (actions == null) {
			throw new IllegalArgumentException();
		}

		if (value != null) {
			setValue(value);
		}
		for (Statement statement : actions) {
			this.actions.add(statement);
		}
		setDefault(isDefault);
	}

	public SwitchCase(int start, int end, AST ast, Expression value, List actions, boolean isDefault) {
		this(start, end, ast, value,
				actions == null ? null : (Statement[]) actions.toArray(new Statement[actions.size()]), isDefault);
	}

	public SwitchCase(AST ast) {
		super(ast);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<SwitchCase"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(" isDefault='").append(isDefault).append("'>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		buffer.append(TAB).append(tab).append("<Value>\n"); //$NON-NLS-1$
		if (value != null) {
			value.toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(TAB).append(tab).append("</Value>\n"); //$NON-NLS-1$
		for (ASTNode node : this.actions) {
			node.toString(buffer, TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append("</SwitchCase>"); //$NON-NLS-1$
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
