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

import java.util.Iterator;
import java.util.List;

import lombok.Getter;
import net.katagaitai.phpscan.ast.Visitor;

import com.google.common.collect.Lists;

/**
 * Represents a class or namespace constant declaration
 *
 * <pre>
 * e.g.
 * const MY_CONST = 5;
 * const MY_CONST = 5, YOUR_CONSTANT = 8;
 * </pre>
 */
public class ConstantDeclaration extends Statement {
	@Getter
	private final List<Identifier> names = Lists.newArrayList();
	@Getter
	private final List<Expression> initializers = Lists.newArrayList();

	public ConstantDeclaration(int start, int end, AST ast, List<Identifier> names, List<Expression> initializers) {
		super(start, end, ast);

		if (names == null || initializers == null || names.size() != initializers.size()) {
			throw new IllegalArgumentException();
		}

		Iterator<Identifier> iteratorNames = names.iterator();
		Iterator<Expression> iteratorInitializers = initializers.iterator();
		while (iteratorNames.hasNext()) {
			this.names.add(iteratorNames.next());
			this.initializers.add(iteratorInitializers.next());
		}
	}

	public ConstantDeclaration(int start, int end, AST ast, List variablesAndDefaults) {
		super(start, end, ast);
		if (variablesAndDefaults == null || variablesAndDefaults.size() == 0) {
			throw new IllegalArgumentException();
		}

		for (Iterator iter = variablesAndDefaults.iterator(); iter.hasNext();) {
			ASTNode[] element = (ASTNode[]) iter.next();
			assert element != null && element.length == 2 && element[0] != null && element[1] != null;

			this.names.add((Identifier) element[0]);
			this.initializers.add((Expression) element[1]);
		}
	}

	public ConstantDeclaration(AST ast) {
		super(ast);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<ConstantDeclaration"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		Iterator<Identifier> iterator1 = names.iterator();
		Iterator<Expression> iterator2 = initializers.iterator();
		while (iterator1.hasNext()) {
			buffer.append(tab).append(TAB).append("<VariableName>\n"); //$NON-NLS-1$
			iterator1.next().toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
			buffer.append(tab).append(TAB).append("</VariableName>\n"); //$NON-NLS-1$
			buffer.append(tab).append(TAB).append("<InitialValue>\n"); //$NON-NLS-1$
			Expression expr = iterator2.next();
			if (expr != null) {
				expr.toString(buffer, TAB + TAB + tab);
				buffer.append("\n"); //$NON-NLS-1$
			}
			buffer.append(tab).append(TAB).append("</InitialValue>\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append("</ConstantDeclaration>"); //$NON-NLS-1$
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
