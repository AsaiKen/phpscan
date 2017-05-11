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
import net.katagaitai.phpscan.ast.Visitor;

import com.google.common.collect.Lists;

/**
 * Represent a 'use' statement.
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * use A; use A as B; use \A\B as C;
 */
public class TraitUseStatement extends Statement {

	@Getter
	private List<NamespaceName> traitList = Lists.newArrayList();
	@Getter
	private List<TraitStatement> tsList = Lists.newArrayList();

	public TraitUseStatement(int start, int end, AST ast, List<NamespaceName> traitList, List<TraitStatement> tsList) {
		super(start, end, ast);

		if (traitList != null) {
			this.traitList.addAll(traitList);
		}
		if (tsList != null) {
			this.tsList.addAll(tsList);
		}
	}

	public TraitUseStatement(AST ast) {
		super(ast);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<TraitUseStatement"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		if (traitList != null && !traitList.isEmpty()) {
			buffer.append(TAB).append(tab).append("<TraitNameList>\n"); //$NON-NLS-1$
			for (NamespaceName name : traitList) {
				name.toString(buffer, TAB + TAB + tab);
				buffer.append("\n"); //$NON-NLS-1$
			}
			buffer.append(TAB).append(tab).append("</TraitNameList>\n"); //$NON-NLS-1$
		}
		if (tsList != null && !tsList.isEmpty()) {
			buffer.append(TAB).append(tab).append("<TraitStatementList>\n"); //$NON-NLS-1$
			for (TraitStatement name : tsList) {
				name.toString(buffer, TAB + TAB + tab);
				buffer.append("\n"); //$NON-NLS-1$
			}
			buffer.append(TAB).append(tab).append("</TraitStatementList>\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append("</TraitUseStatement>"); //$NON-NLS-1$
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
