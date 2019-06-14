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
 * Represents a for each statement
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * foreach (array_expression as $value) statement;
 *
 * foreach (array_expression as $key => $value) statement;
 *
 * foreach (array_expression as $key => $value): statement; ... endforeach;
 */
public class ForEachStatement extends Statement {
    @Setter
    @Getter
    private Expression expression;
    @Setter
    @Getter
    private Expression key;
    @Setter
    @Getter
    private Expression value;
    @Setter
    @Getter
    private Statement statement;

    public ForEachStatement(int start, int end, AST ast, Expression expression, Expression key, Expression value,
                            Statement statement) {
        super(start, end, ast);

        if (expression == null || value == null || statement == null) {
            throw new IllegalArgumentException();
        }

        setExpression(expression);
        setValue(value);
        setStatement(statement);
        if (key != null) {
            setKey(key);
        }
    }

    public ForEachStatement(AST ast) {
        super(ast);
    }

    public ForEachStatement(int start, int end, AST ast, Expression expression, Expression value, Statement statement) {
        this(start, end, ast, expression, null, value, statement);
    }

    public void toString(StringBuffer buffer, String tab) {
        buffer.append(tab).append("<ForEachStatement"); //$NON-NLS-1$
        appendInterval(buffer);
        buffer.append(">\n"); //$NON-NLS-1$
        buffer.append(TAB).append(tab).append("<Expression>\n"); //$NON-NLS-1$
        expression.toString(buffer, TAB + TAB + tab);
        buffer.append("\n").append(TAB).append(tab).append("</Expression>\n"); //$NON-NLS-1$ //$NON-NLS-2$
        buffer.append(TAB).append(tab).append("<Key>\n"); //$NON-NLS-1$
        if (key != null) {
            key.toString(buffer, TAB + TAB + tab);
            buffer.append("\n"); //$NON-NLS-1$
        }
        buffer.append(TAB).append(tab).append("</Key>\n"); //$NON-NLS-1$
        buffer.append(TAB).append(tab).append("<Value>\n"); //$NON-NLS-1$
        value.toString(buffer, TAB + TAB + tab);
        buffer.append("\n").append(TAB).append(tab).append("</Value>\n"); //$NON-NLS-1$ //$NON-NLS-2$
        statement.toString(buffer, TAB + tab);
        buffer.append("\n"); //$NON-NLS-1$
        buffer.append(tab).append("</ForEachStatement>"); //$NON-NLS-1$
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
