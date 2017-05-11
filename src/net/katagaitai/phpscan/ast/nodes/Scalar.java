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
 * Represents a scalar
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * 'string', 1, 1.3, __CLASS__
 */
public class Scalar extends VariableBase {

	// 'int'
	public static final int TYPE_INT = 0;
	// 'real'
	public static final int TYPE_REAL = 1;
	// 'string'
	public static final int TYPE_STRING = 2;
	// unknown scalar in quote expression
	public static final int TYPE_UNKNOWN = 3;
	// system scalars (__CLASS__ / ...)
	public static final int TYPE_SYSTEM = 4;
	// 'binary' starts with "0b",e.g "0b"[01]+
	public static final int TYPE_BIN = 5;

	@Setter
	@Getter
	private String stringValue;
	@Setter
	@Getter
	private int scalarType;

	public Scalar(int start, int end, AST ast, String value, int type) {
		super(start, end, ast);

		if (value == null) {
			throw new IllegalArgumentException();
		}

		setScalarType(type);
		setStringValue(value);
	}

	public Scalar(AST ast) {
		super(ast);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<Scalar"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(" type='").append(getType(scalarType)).append("'"); //$NON-NLS-1$ //$NON-NLS-2$
		if (stringValue != null) {
			buffer.append(" value='").append(getXmlStringValue(stringValue)).append("'"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		buffer.append("/>"); //$NON-NLS-1$
	}

	public static String getType(int type) {
		switch (type) {
		case TYPE_INT:
			return "int"; //$NON-NLS-1$
		case TYPE_REAL:
			return "real"; //$NON-NLS-1$
		case TYPE_STRING:
			return "string"; //$NON-NLS-1$
		case TYPE_UNKNOWN:
			return "unknown"; //$NON-NLS-1$
		case TYPE_SYSTEM:
			return "system"; //$NON-NLS-1$
		case TYPE_BIN:
			return "bin"; //$NON-NLS-1$
		default:
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
