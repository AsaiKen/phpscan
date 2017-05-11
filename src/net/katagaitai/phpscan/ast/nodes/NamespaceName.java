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
 * Represents namespace name:
 *
 * e.g.
 *
 * <pre>
 * MyNamespace;
 * MyProject\Sub\Level;
 * namespace\MyProject\Sub\Level;
 * </pre>
 */
public class NamespaceName extends Identifier {
	@Getter
	protected List<Identifier> segments = Lists.newArrayList();

	/**
	 * Whether the namespace name has '\' prefix, which means it relates to the
	 * global scope
	 */
	@Getter
	private boolean global;

	/**
	 * Whether the namespace name has 'namespace' prefix, which means it relates
	 * to the current namespace scope
	 */
	@Getter
	private boolean current;

	public NamespaceName(AST ast) {
		super(ast);
	}

	public NamespaceName(int start, int end, AST ast, List segments, boolean global, boolean current) {
		super(start, end, ast,
				buildName((Identifier[]) segments.toArray(new Identifier[getSegmentSize(segments)]), global, current));

		Iterator<Identifier> it = segments.iterator();
		while (it.hasNext()) {
			this.segments.add(it.next());
		}

		this.global = global;
		this.current = current;
	}

	private static int getSegmentSize(List segments) {
		if (segments == null) {
			throw new IllegalArgumentException();
		}
		return segments.size();
	}

	protected static String buildName(Identifier[] segments, boolean global, boolean current) {
		if (segments == null) {
			throw new IllegalArgumentException();
		}
		StringBuilder buf = new StringBuilder();
		if (global) {
			buf.append('\\');
		} else if (current) {
			buf.append("namespace\\"); //$NON-NLS-1$
		}
		for (int i = 0; i < segments.length; ++i) {
			if (i > 0) {
				buf.append('\\');
			}
			buf.append(segments[i].getName());
		}
		return buf.toString();
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<NamespaceName"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(" global='").append(global).append('\''); //$NON-NLS-1$
		buffer.append(" current='").append(current).append('\''); //$NON-NLS-1$
		buffer.append(">\n"); //$NON-NLS-1$
		for (ASTNode node : this.segments) {
			node.toString(buffer, TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append("</NamespaceName>"); //$NON-NLS-1$
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
