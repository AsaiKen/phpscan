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

import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import net.katagaitai.phpscan.ast.Visitor;

import com.google.common.collect.Lists;

/**
 * Represents a function declaration
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * function foo() {}
 *
 * function &foo() {}
 *
 * function foo($a, int $b, $c = 5, int $d = 6) {}
 *
 * function foo(); -abstract function in class declaration
 *
 * function foo() : MyType {};
 */
public class FunctionDeclaration extends Statement {
	@Setter
	@Getter
	private boolean isReference;
	@Setter
	@Getter
	private Identifier name;
	@Getter
	private final List<FormalParameter> formalParameters = Lists.newArrayList();
	@Setter
	@Getter
	private Block body;
	@Setter
	@Getter
	private Identifier returnType;

	public FunctionDeclaration(int start, int end, AST ast, Identifier functionName, List<?> formalParameters,
			Block body, final boolean isReference) {
		this(start, end, ast, functionName,
				(FormalParameter[]) formalParameters.toArray(new FormalParameter[formalParameters.size()]), body,
				isReference);
	}

	public FunctionDeclaration(int start, int end, AST ast, Identifier functionName, List formalParameters, Block body,
			final boolean isReference, Identifier returnType) {
		super(start, end, ast);

		if (functionName == null || formalParameters == null) {
			throw new IllegalArgumentException();
		}

		setReference(isReference);
		setName(functionName);
		for (Object obj : formalParameters) {
			FormalParameter formalParameter = (FormalParameter) obj;
			this.formalParameters.add(formalParameter);
		}
		if (body != null) {
			setBody(body);
		}
		this.returnType = returnType;
	}

	private FunctionDeclaration(int start, int end, AST ast, Identifier functionName,
			FormalParameter[] formalParameters, Block body, final boolean isReference) {
		this(start, end, ast, functionName, Arrays.asList(formalParameters), body, isReference, null);
	}

	public FunctionDeclaration(AST ast) {
		super(ast);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<FunctionDeclaration"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(" isReference='").append(isReference).append("'>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		buffer.append(TAB).append(tab).append("<FunctionName>\n"); //$NON-NLS-1$
		name.toString(buffer, TAB + TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(TAB).append(tab).append("</FunctionName>\n"); //$NON-NLS-1$

		buffer.append(TAB).append(tab).append("<FormalParameters>\n"); //$NON-NLS-1$
		for (ASTNode node : this.formalParameters) {
			node.toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(TAB).append(tab).append("</FormalParameters>\n"); //$NON-NLS-1$
		if (getReturnType() != null) {
			buffer.append(TAB).append(tab).append("<ReturnType>\n"); //$NON-NLS-1$
			getReturnType().toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
			buffer.append(TAB).append(tab).append("</ReturnType>\n"); //$NON-NLS-1$
		}
		buffer.append(TAB).append(tab).append("<FunctionBody>\n"); //$NON-NLS-1$
		if (body != null) {
			body.toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(TAB).append(tab).append("</FunctionBody>\n"); //$NON-NLS-1$
		buffer.append(tab).append("</FunctionDeclaration>"); //$NON-NLS-1$
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
