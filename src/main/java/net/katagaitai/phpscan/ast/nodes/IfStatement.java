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
 * Represents if statement
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * if ($a > $b) { echo "a is bigger than b"; } elseif ($a == $b) { echo
 * "a is equal to b"; } else { echo "a is smaller than b"; },
 *
 * if ($a): echo "a is bigger than b"; echo "a is NOT bigger than b"; endif;
 */
public class IfStatement extends Statement {
    @Setter
    @Getter
    private Expression condition;
    @Setter
    @Getter
    private Statement trueStatement;
    @Setter
    @Getter
    private Statement falseStatement;

    public IfStatement(int start, int end, AST ast, Expression condition, Statement trueStatement,
                       Statement falseStatement) {
        super(start, end, ast);

        if (condition == null || trueStatement == null) {
            throw new IllegalArgumentException();
        }
        setCondition(condition);
        setTrueStatement(trueStatement);
        if (falseStatement != null) {
            setFalseStatement(falseStatement);
        }
    }

    public IfStatement(AST ast) {
        super(ast);
    }

    public void toString(StringBuffer buffer, String tab) {
        buffer.append(tab).append("<IfStatement"); //$NON-NLS-1$
        appendInterval(buffer);
        buffer.append(">\n"); //$NON-NLS-1$
        buffer.append(TAB).append(tab).append("<Condition>\n"); //$NON-NLS-1$
        condition.toString(buffer, TAB + TAB + tab);
        buffer.append("\n"); //$NON-NLS-1$
        buffer.append(TAB).append(tab).append("</Condition>\n"); //$NON-NLS-1$
        buffer.append(TAB).append(tab).append("<TrueStatement>\n"); //$NON-NLS-1$
        trueStatement.toString(buffer, TAB + TAB + tab);
        buffer.append("\n"); //$NON-NLS-1$
        buffer.append(TAB).append(tab).append("</TrueStatement>\n"); //$NON-NLS-1$
        buffer.append(TAB).append(tab).append("<FalseStatement>\n"); //$NON-NLS-1$
        if (falseStatement != null) {
            falseStatement.toString(buffer, TAB + TAB + tab);
            buffer.append("\n"); //$NON-NLS-1$
        }
        buffer.append(TAB).append(tab).append("</FalseStatement>\n"); //$NON-NLS-1$
        buffer.append(tab).append("</IfStatement>"); //$NON-NLS-1$
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
