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

/**
 * Represents a class declaration
 *
 * <pre>
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * class MyClass { }, class MyClass extends SuperClass implements Interface1,
 * Interface2 { const MY_CONSTANT = 3; public static final $myVar = 5, $yourVar;
 * var $anotherOne; private function myFunction($a) { } }
 */
public class ClassDeclaration extends TypeDeclaration {

	public static final int MODIFIER_NONE = 0;
	public static final int MODIFIER_ABSTRACT = 1;
	public static final int MODIFIER_FINAL = 2;
	public static final int MODIFIER_TRAIT = 3;

	@Setter
	@Getter
	private int modifier;
	@Setter
	@Getter
	private Expression superClass;

	private ClassDeclaration(int start, int end, AST ast, int modifier, Identifier className, Expression superClass,
			Identifier[] interfaces, Block body) {
		super(start, end, ast, className, interfaces, body);

		setModifier(modifier);
		if (superClass != null) {
			setSuperClass(superClass);
		}
	}

	public ClassDeclaration(AST ast) {
		super(ast);
	}

	public ClassDeclaration(int start, int end, AST ast, int modifier, Identifier className, Expression superClass,
			List interfaces, Block body) {
		this(start, end, ast, modifier, className, superClass,
				interfaces == null ? null : (Identifier[]) interfaces.toArray(new Identifier[interfaces.size()]), body);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<ClassDeclaration"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(" modifier='").append(getModifier(modifier)).append("'>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		buffer.append(tab).append(TAB).append("<ClassName>\n"); //$NON-NLS-1$
		getName().toString(buffer, TAB + TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(tab).append(TAB).append("</ClassName>\n"); //$NON-NLS-1$

		buffer.append(tab).append(TAB).append("<SuperClassName>\n"); //$NON-NLS-1$
		if (superClass != null) {
			superClass.toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append(TAB).append("</SuperClassName>\n"); //$NON-NLS-1$

		buffer.append(tab).append(TAB).append("<Interfaces>\n"); //$NON-NLS-1$
		for (Object object : getInterfaces()) {
			final ASTNode node = (ASTNode) object;
			node.toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append(TAB).append("</Interfaces>\n"); //$NON-NLS-1$
		getBody().toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(tab).append("</ClassDeclaration>"); //$NON-NLS-1$
	}

	public static String getModifier(int modifier) {
		switch (modifier) {
		case MODIFIER_NONE:
			return ""; //$NON-NLS-1$
		case MODIFIER_ABSTRACT:
			return "abstract"; //$NON-NLS-1$
		case MODIFIER_FINAL:
			return "final"; //$NON-NLS-1$
		default:
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
