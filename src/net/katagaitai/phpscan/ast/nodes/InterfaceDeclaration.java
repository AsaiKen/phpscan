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

import net.katagaitai.phpscan.ast.Visitor;

/**
 * Represents an interface declaration
 *
 * <pre>
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * interface MyInterface { }, interface MyInterface extends Interface1,
 * Interface2 { const MY_CONSTANT = 3; public function myFunction($a); }
 */
public class InterfaceDeclaration extends TypeDeclaration {
	private InterfaceDeclaration(int start, int end, AST ast, Identifier interfaceName, Identifier[] interfaces,
			Block body) {
		super(start, end, ast, interfaceName, interfaces, body);
	}

	public InterfaceDeclaration(int start, int end, AST ast, Identifier interfaceName, List interfaces, Block body) {
		this(start, end, ast, interfaceName, (Identifier[]) interfaces.toArray(new Identifier[interfaces.size()]),
				body);
	}

	public InterfaceDeclaration(AST ast) {
		super(ast);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<InterfaceDeclaration"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		buffer.append(tab).append(TAB).append("<InterfaceName>\n"); //$NON-NLS-1$
		getName().toString(buffer, TAB + TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(tab).append(TAB).append("</InterfaceName>\n"); //$NON-NLS-1$
		buffer.append(tab).append(TAB).append("<Interfaces>\n"); //$NON-NLS-1$
		final List interfaes = getInterfaces();
		for (Object node : interfaes) {
			ASTNode inter = (ASTNode) node;
			inter.toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append(TAB).append("</Interfaces>\n"); //$NON-NLS-1$
		getBody().toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(tab).append("</InterfaceDeclaration>"); //$NON-NLS-1$
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
