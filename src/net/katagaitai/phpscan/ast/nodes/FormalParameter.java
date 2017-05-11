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
 * Represents a function formal parameter
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * $a, MyClass $a, $a = 3, int $a = 3
 */
public class FormalParameter extends ASTNode {
	@Setter
	@Getter
	private Expression parameterType;
	@Setter
	@Getter
	private Expression parameterName;
	@Setter
	@Getter
	private Expression defaultValue;
	@Setter
	@Getter
	private boolean isMandatory; // php4 "const" keyword
	@Setter
	@Getter
	private boolean isVariadic;

	public FormalParameter(AST ast) {
		super(ast);
	}

	public FormalParameter(int start, int end, AST ast, Expression type, final Expression parameterName,
			Expression defaultValue, boolean isMandatory, boolean isVariadic) {
		super(start, end, ast);

		if (parameterName == null) {
			throw new IllegalArgumentException();
		}
		setParameterName(parameterName);
		if (type != null) {
			setParameterType(type);
		}
		if (defaultValue != null) {
			setDefaultValue(defaultValue);
		}
		setMandatory(isMandatory);
		setVariadic(isVariadic);
	}

	private FormalParameter(int start, int end, AST ast, Expression type, final Expression parameterName,
			Expression defaultValue, boolean isMandatory) {
		this(start, end, ast, type, parameterName, defaultValue, isMandatory, false);
	}

	public FormalParameter(int start, int end, AST ast, Expression type, final Variable parameterName,
			Expression defaultValue) {
		this(start, end, ast, type, (Expression) parameterName, defaultValue, false);
	}

	public FormalParameter(int start, int end, AST ast, Expression type, final Reference parameterName,
			Expression defaultValue) {
		this(start, end, ast, type, (Expression) parameterName, defaultValue, false);
	}

	public FormalParameter(int start, int end, AST ast, Expression type, final Variable parameterName) {
		this(start, end, ast, type, (Expression) parameterName, null, false);
	}

	public FormalParameter(int start, int end, AST ast, Expression type, final Variable parameterName,
			boolean isMandatory) {
		this(start, end, ast, type, (Expression) parameterName, null, isMandatory);
	}

	public FormalParameter(int start, int end, AST ast, Expression type, final Reference parameterName) {
		this(start, end, ast, type, (Expression) parameterName, null, false);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<FormalParameter"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(" isMandatory='").append(isMandatory); //$NON-NLS-1$
		if (isVariadic()) {
			buffer.append(" isVariadic='").append(isVariadic);//$NON-NLS-1$
		}
		buffer.append("'>\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("<Type>\n"); //$NON-NLS-1$
		if (parameterType != null) {
			parameterType.toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(TAB).append(tab).append("</Type>\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("<ParameterName>\n"); //$NON-NLS-1$
		parameterName.toString(buffer, TAB + TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("</ParameterName>\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("<DefaultValue>\n"); //$NON-NLS-1$
		if (defaultValue != null) {
			defaultValue.toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(TAB).append(tab).append("</DefaultValue>\n"); //$NON-NLS-1$
		buffer.append(tab).append("</FormalParameter>"); //$NON-NLS-1$
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
