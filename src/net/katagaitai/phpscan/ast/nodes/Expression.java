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

/**
 * Base class for all expression in PHP
 */
public abstract class Expression extends ASTNode {

	public Expression(int start, int end, AST ast) {
		super(start, end, ast);
	}

	public Expression(AST ast) {
		super(ast);
	}

}
