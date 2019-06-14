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
 * Represents an reference to a variable or class instantiation.
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * &$a, &new MyClass() &foo()
 */
public class Reference extends Expression {

    /**
     * the expressions can be either variable or class instantiation note that
     * other expressions can not be assigned to this field
     */
    @Setter
    @Getter
    private Expression expression;

    private Reference(int start, int end, AST ast, Expression expression) {
        super(start, end, ast);

        if (expression == null) {
            throw new IllegalArgumentException();
        }
        setExpression(expression);
    }

    public Reference(int start, int end, AST ast, VariableBase variable) {
        this(start, end, ast, (Expression) variable);
    }

    public Reference(AST ast) {
        super(ast);
    }

    public Reference(int start, int end, AST ast, ClassInstanceCreation classInstanciation) {
        this(start, end, ast, (Expression) classInstanciation);
    }

    public void toString(StringBuffer buffer, String tab) {
        buffer.append(tab).append("<Reference"); //$NON-NLS-1$
        appendInterval(buffer);
        buffer.append(">\n"); //$NON-NLS-1$
        expression.toString(buffer, TAB + tab);
        buffer.append("\n").append(tab).append("</Reference>"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
