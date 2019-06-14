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
import net.katagaitai.phpscan.ast.Visitor;

/**
 * Represents ignore error expression
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * '@$a->foo()'
 */
public class IgnoreError extends Expression {
    @Setter
    @Getter
    private Expression expression;

    public IgnoreError(int start, int end, AST ast) {
        this(start, end, ast, null);
    }

    public IgnoreError(AST ast) {
        super(ast);
    }

    public IgnoreError(int start, int end, AST ast, Expression expr) {
        super(start, end, ast);

        if (expr == null) {
            throw new IllegalArgumentException();
        }
        setExpression(expr);
    }

    public void toString(StringBuffer buffer, String tab) {
        buffer.append(tab).append("<IgnoreError"); //$NON-NLS-1$
        appendInterval(buffer);
        buffer.append(">\n"); //$NON-NLS-1$
        expression.toString(buffer, TAB + tab);
        buffer.append("\n").append(tab).append("</IgnoreError>"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
