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
 * Represent a yield expression
 * <p>
 * e.g.
 *
 * <pre>
 * yield
 * yield $a
 * yield $k => $a
 * yield from $k
 * </pre>
 */
public class YieldExpression extends Expression {

    // yield $a or yield $k => $a
    public static final int OP_NONE = 0;
    // yield from $k
    public static final int OP_FROM = 1;

    @Setter
    @Getter
    private Expression key;
    @Setter
    @Getter
    private Expression expression;
    @Setter
    @Getter
    private int operator;

    public YieldExpression(int start, int end, AST ast) {
        this(start, end, ast, null);
    }

    public YieldExpression(AST ast) {
        super(ast);
    }

    public YieldExpression(int start, int end, AST ast, Expression expr) {
        this(start, end, ast, expr, OP_NONE);
    }

    public YieldExpression(int start, int end, AST ast, Expression key, Expression expr) {
        super(start, end, ast);
        if (key != null) {
            setKey(key);
        }
        if (expr != null) {
            setExpression(expr);
        }
        this.operator = OP_NONE;
    }

    public YieldExpression(int start, int end, AST ast, Expression expr, int operator) {
        super(start, end, ast);

        if (expr != null) {
            setExpression(expr);
        }
        this.operator = operator;
    }

    public void toString(StringBuffer buffer, String tab) {
        buffer.append(tab).append("<YieldExpression"); //$NON-NLS-1$
        appendInterval(buffer);
        if (getOperator() != OP_NONE) {
            buffer.append(" operator='" + operator + "'"); //$NON-NLS-1$ //$NON-NLS-2$
        }
        buffer.append(">\n"); //$NON-NLS-1$
        if (key != null) {
            key.toString(buffer, TAB + tab);
            buffer.append("\n"); //$NON-NLS-1$
        }
        if (expression != null) {
            expression.toString(buffer, TAB + tab);
            buffer.append("\n"); //$NON-NLS-1$
        }
        buffer.append(tab).append("</YieldExpression>"); //$NON-NLS-1$
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
