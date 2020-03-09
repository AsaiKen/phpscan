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
 * Represents function invocation. Holds the function name and the invocation
 * parameters.
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * foo(), $a(), foo($a, 'a', 12)
 */
public class FunctionInvocation extends VariableBase {
	@Setter
	@Getter
	private FunctionName functionName;
	@Getter
	private final List<Expression> parameters = Lists.newArrayList();

	private FunctionInvocation(int start, int end, AST ast, FunctionName functionName, Expression[] parameters) {
		super(start, end, ast);

		if (functionName == null || parameters == null) {
			throw new IllegalArgumentException();
		}

		setFunctionName(functionName);
		for (Expression expression : parameters) {
			this.parameters.add(expression);
		}
	}

	public FunctionInvocation(AST ast) {
		super(ast);
	}

	public FunctionInvocation(int start, int end, AST ast, FunctionName functionName, List parameters) {
		this(start, end, ast, functionName,
				parameters == null ? null : (Expression[]) parameters.toArray(new Expression[parameters.size()]));
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<FunctionInvocation"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		functionName.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("<Parameters>\n"); //$NON-NLS-1$
		for (ASTNode node : parameters) {
			node.toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(TAB).append(tab).append("</Parameters>\n"); //$NON-NLS-1$
		buffer.append(tab).append("</FunctionInvocation>"); //$NON-NLS-1$
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
