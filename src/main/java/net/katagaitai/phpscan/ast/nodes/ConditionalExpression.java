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
import net.katagaitai.phpscan.ast.PHPVersion;
import net.katagaitai.phpscan.ast.Visitor;

/**
 * Represents conditional expression Holds the condition, if true expression and
 * if false expression each on e can be any expression
 *
 * <pre>
 * e.g. (bool) $a ? 3 : 4
 * $a > 0 ? $a : -$a
 * </pre>
 * <p>
 * The node supports also the new notation introduced in PHP 5.3:
 *
 * <pre>
 * $a ? : $b
 * </pre>
 * <p>
 * The node supports also the new notation introduced in PHP 7:
 *
 * <pre>
 * $a ?? 0
 * </pre>
 */
public class ConditionalExpression extends Expression {

    // ?:
    public static final int OP_TERNARY = 0;
    // ??
    public static final int OP_COALESCE = 1;

    @Setter
    @Getter
    private Expression condition;
    @Setter
    @Getter
    private Expression ifTrue;
    @Setter
    @Getter
    private Expression ifFalse;
    @Setter
    @Getter
    private int operatorType;

    public ConditionalExpression(AST ast) {
        super(ast);
    }

    /**
     * Constructor for Ternary Operator
     */
    public ConditionalExpression(int start, int end, AST ast, Expression condition, Expression ifTrue,
                                 Expression ifFalse) {
        super(start, end, ast);

        if (condition == null || (ast.getApiLevel().isLessThan(PHPVersion.PHP5_3) && ifTrue == null) ||
                ifFalse == null) {
            throw new IllegalArgumentException();
        }
        setCondition(condition);
        setIfTrue(ifTrue);
        setIfFalse(ifFalse);
        operatorType = OP_TERNARY;
    }

    /**
     * Constructor for Null Coalesce Operator
     */
    public ConditionalExpression(int start, int end, AST ast, Expression expression, Expression ifNull) {
        super(start, end, ast);

        if (expression == null || ast.getApiLevel().isLessThan(PHPVersion.PHP7_0) || ifNull == null) {
            throw new IllegalArgumentException();
        }
        setCondition(expression);
        setIfTrue(ifNull);
        setIfFalse(null);
        operatorType = OP_COALESCE;
    }

    public void toString(StringBuffer buffer, String tab) {
        buffer.append(tab).append("<ConditionalExpression"); //$NON-NLS-1$
        appendInterval(buffer);
        buffer.append(" operatorType='" + operatorType + "'");
        buffer.append(">\n"); //$NON-NLS-1$
        buffer.append(TAB).append(tab).append("<Condition>\n"); //$NON-NLS-1$
        condition.toString(buffer, TAB + TAB + tab);
        buffer.append("\n").append(TAB).append(tab).append("</Condition>\n"); //$NON-NLS-1$ //$NON-NLS-2$

        if (operatorType == OP_TERNARY) {
            if (ifTrue != null) {
                buffer.append(TAB).append(tab).append("<IfTrue>\n"); //$NON-NLS-1$
                ifTrue.toString(buffer, TAB + TAB + tab);
                buffer.append("\n").append(TAB).append(tab) //$NON-NLS-1$
                        .append("</IfTrue>\n"); //$NON-NLS-1$
            }
            if (ifFalse != null) {
                buffer.append(TAB).append(tab).append("<IfFalse>\n"); //$NON-NLS-1$
                ifFalse.toString(buffer, TAB + TAB + tab);
                buffer.append("\n").append(TAB).append(tab) //$NON-NLS-1$
                        .append("</IfFalse>\n"); //$NON-NLS-1$
            }
        } else if (operatorType == OP_COALESCE) {
            buffer.append(TAB).append(tab).append("<IfNull>\n"); //$NON-NLS-1$
            ifTrue.toString(buffer, TAB + TAB + tab);
            buffer.append("\n").append(TAB).append(tab).append("</IfNull>\n"); //$NON-NLS-1$ //$NON-NLS-2$
        }
        buffer.append(tab).append("</ConditionalExpression>"); //$NON-NLS-1$
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
