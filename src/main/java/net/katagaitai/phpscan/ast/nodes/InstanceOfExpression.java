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
 * Represent instanceof expression
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * $a instanceof MyClass, foo() instanceof $myClass, $a instanceof $b->$myClass
 */
public class InstanceOfExpression extends Expression {
    @Setter
    @Getter
    private Expression expression;
    @Setter
    @Getter
    private ClassName className;

    public InstanceOfExpression(int start, int end, AST ast, Expression expr, ClassName className) {
        super(start, end, ast);

        if (expr == null || className == null) {
            throw new IllegalArgumentException();
        }
        setExpression(expr);
        setClassName(className);
    }

    public InstanceOfExpression(AST ast) {
        super(ast);
    }

    public void toString(StringBuffer buffer, String tab) {
        buffer.append(tab).append("<InstanceofExpression"); //$NON-NLS-1$
        appendInterval(buffer);
        buffer.append(">\n"); //$NON-NLS-1$
        expression.toString(buffer, TAB + tab);
        buffer.append("\n"); //$NON-NLS-1$
        className.toString(buffer, TAB + tab);
        buffer.append("\n").append(tab).append("</InstanceofExpression>"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
