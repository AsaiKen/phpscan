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

/**
 * base class for all the static access
 */
public abstract class StaticDispatch extends VariableBase {
	@Setter
	@Getter
	private Expression className;

	public StaticDispatch(int start, int end, AST ast, Expression className) {
		super(start, end, ast);

		if (className == null) {
			throw new IllegalArgumentException();
		}
		setClassName(className);
	}

	public StaticDispatch(AST ast) {
		super(ast);
	}

	public abstract ASTNode getMember();
}
