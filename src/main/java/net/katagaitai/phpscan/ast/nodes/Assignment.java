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
 * Represents an assignment statement.
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * $a = 5, $a += 5, $a .= $b,
 */
public class Assignment extends Expression {

    // '='
    public static final int OP_EQUAL = 0;
    // '+='
    public static final int OP_PLUS_EQUAL = 1;
    // '-='
    public static final int OP_MINUS_EQUAL = 2;
    // '*='
    public static final int OP_MUL_EQUAL = 3;
    // '/='
    public static final int OP_DIV_EQUAL = 4;
    // '.='
    public static final int OP_CONCAT_EQUAL = 5;
    // '%='
    public static final int OP_MOD_EQUAL = 6;
    // '&='
    public static final int OP_AND_EQUAL = 7;
    // '|='
    public static final int OP_OR_EQUAL = 8;
    // '^='
    public static final int OP_XOR_EQUAL = 9;
    // '<<='
    public static final int OP_SL_EQUAL = 10;
    // '>>='
    public static final int OP_SR_EQUAL = 11;
    // '**='
    public static final int OP_POW_EQUAL = 12;

    @Setter
    @Getter
    private VariableBase leftHandSide;
    @Setter
    @Getter
    private int operator;
    @Setter
    @Getter
    private Expression rightHandSide;

    public Assignment(int start, int end, AST ast, VariableBase leftHandSide, int operator, Expression rightHandSide) {
        super(start, end, ast);
        if (leftHandSide == null || rightHandSide == null || getOperator(operator) == null) {
            throw new IllegalArgumentException();
        }

        setLeftHandSide(leftHandSide);
        setOperator(operator);
        setRightHandSide(rightHandSide);
    }

    public Assignment(AST ast) {
        super(ast);
    }

    public static String getOperator(int operator) {
        switch (operator) {
            case OP_EQUAL:
                return "="; //$NON-NLS-1$
            case OP_PLUS_EQUAL:
                return "+="; //$NON-NLS-1$
            case OP_MINUS_EQUAL:
                return "-="; //$NON-NLS-1$
            case OP_MUL_EQUAL:
                return "*="; //$NON-NLS-1$
            case OP_DIV_EQUAL:
                return "/="; //$NON-NLS-1$
            case OP_MOD_EQUAL:
                return "%="; //$NON-NLS-1$
            case OP_CONCAT_EQUAL:
                return ".="; //$NON-NLS-1$
            case OP_AND_EQUAL:
                return "&="; //$NON-NLS-1$
            case OP_OR_EQUAL:
                return "|="; //$NON-NLS-1$
            case OP_XOR_EQUAL:
                return "^="; //$NON-NLS-1$
            case OP_SL_EQUAL:
                return "<<="; //$NON-NLS-1$
            case OP_SR_EQUAL:
                return ">>="; //$NON-NLS-1$
            case OP_POW_EQUAL:
                return "**="; //$NON-NLS-1$
            default:
                throw new IllegalArgumentException();
        }
    }

    public void toString(StringBuffer buffer, String tab) {
        buffer.append(tab).append("<Assignment"); //$NON-NLS-1$
        appendInterval(buffer);
        buffer.append(" operator='").append(getXmlStringValue(getOperator(operator)))
                .append("'>\n"); //$NON-NLS-1$ //$NON-NLS-2$
        leftHandSide.toString(buffer, TAB + tab);
        buffer.append("\n").append(TAB).append(tab).append("<Value>\n"); //$NON-NLS-1$ //$NON-NLS-2$
        rightHandSide.toString(buffer, TAB + TAB + tab);
        buffer.append("\n").append(TAB).append(tab).append("</Value>\n"); //$NON-NLS-1$ //$NON-NLS-2$
        buffer.append(tab).append("</Assignment>"); //$NON-NLS-1$
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
