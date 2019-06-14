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

import com.google.common.collect.Lists;
import lombok.Getter;
import net.katagaitai.phpscan.ast.Visitor;

import java.util.List;

/**
 * Represent a echo statement.
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * echo "hello", echo "hello", "world"
 */
public class EchoStatement extends Statement {
    @Getter
    private List<Expression> expressions = Lists.newArrayList();

    private EchoStatement(int start, int end, AST ast, Expression[] expressions) {
        super(start, end, ast);
        if (expressions == null) {
            throw new IllegalArgumentException();
        }

        for (Expression expression : expressions) {
            this.expressions.add(expression);
        }
    }

    public EchoStatement(int start, int end, AST ast, List expressions) {
        this(start, end, ast, (Expression[]) expressions.toArray(new Expression[expressions.size()]));
    }

    public EchoStatement(AST ast) {
        super(ast);
    }

    public void toString(StringBuffer buffer, String tab) {
        buffer.append(tab).append("<EchoStatement"); //$NON-NLS-1$
        appendInterval(buffer);
        buffer.append(">\n"); //$NON-NLS-1$
        for (ASTNode node : this.expressions) {
            node.toString(buffer, TAB + tab);
            buffer.append("\n"); //$NON-NLS-1$
        }
        buffer.append(tab).append("</EchoStatement>"); //$NON-NLS-1$
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
