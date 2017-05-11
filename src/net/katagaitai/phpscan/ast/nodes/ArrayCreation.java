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
 * Represents array creation
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * array(1,2,3,), array('Dodo'=>'Golo','Dafna'=>'Dodidu') array($a, $b=>foo(), 1=>$myClass->getFirst())
 */
public class ArrayCreation extends VariableBase {
	@Setter
	@Getter
	private List<ArrayElement> elements = Lists.newArrayList();
	@Setter
	@Getter
	private boolean hasArrayKey;

	public ArrayCreation(AST ast) {
		super(ast);
	}

	private ArrayCreation(int start, int end, AST ast, ArrayElement[] elements, boolean hasArrayKey) {
		super(start, end, ast);

		if (elements == null) {
			throw new IllegalArgumentException();
		}

		for (ArrayElement arrayElement : elements) {
			this.elements.add(arrayElement);
		}
		setHasArrayKey(hasArrayKey);
	}

	public ArrayCreation(int start, int end, AST ast, List<ArrayElement> elements) {
		this(start, end, ast,
				elements == null ? null : (ArrayElement[]) elements.toArray(new ArrayElement[elements.size()]), true);
	}

	public ArrayCreation(int start, int end, AST ast, List<ArrayElement> elements, boolean hasArrayKey) {
		this(start, end, ast,
				elements == null ? null : (ArrayElement[]) elements.toArray(new ArrayElement[elements.size()]),
				hasArrayKey);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<ArrayCreation"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		for (ASTNode node : this.elements) {
			node.toString(buffer, TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append("</ArrayCreation>"); //$NON-NLS-1$
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
