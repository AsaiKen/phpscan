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
 * Represents a base class for method invocation and field access
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * $a->$b, foo()->bar(), $myClass->foo()->bar(), A::$a->foo()
 */
public abstract class Dispatch extends VariableBase {
	@Setter
	@Getter
	private VariableBase dispatcher;

	public Dispatch(int start, int end, AST ast, VariableBase dispatcher) {
		super(start, end, ast);

		if (dispatcher == null) {
			throw new IllegalArgumentException();
		}
		setDispatcher(dispatcher);
	}

	public Dispatch(AST ast) {
		super(ast);
	}

}
