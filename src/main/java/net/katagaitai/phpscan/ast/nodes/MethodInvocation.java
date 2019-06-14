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
 * Represents a dispaching expression
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * foo()->bar(), $myClass->foo()->bar(), A::$a->foo()
 */
public class MethodInvocation extends Dispatch {
    @Setter
    @Getter
    private FunctionInvocation method;

    public MethodInvocation(AST ast) {
        super(ast);
    }

    public MethodInvocation(int start, int end, AST ast, VariableBase dispatcher, FunctionInvocation method) {
        super(start, end, ast, dispatcher);

        if (method == null) {
            throw new IllegalArgumentException();
        }

        setMethod(method);
    }

    public void toString(StringBuffer buffer, String tab) {
        buffer.append(tab).append("<MethodInvocation"); //$NON-NLS-1$
        appendInterval(buffer);
        buffer.append(">\n"); //$NON-NLS-1$
        buffer.append(TAB).append(tab).append("<Dispatcher>\n"); //$NON-NLS-1$
        getDispatcher().toString(buffer, TAB + TAB + tab);
        buffer.append("\n").append(TAB).append(tab).append("</Dispatcher>\n"); //$NON-NLS-1$ //$NON-NLS-2$
        buffer.append(TAB).append(tab).append("<Property>\n"); //$NON-NLS-1$
        method.toString(buffer, TAB + TAB + tab);
        buffer.append("\n").append(TAB).append(tab).append("</Property>\n"); //$NON-NLS-1$ //$NON-NLS-2$
        buffer.append(tab).append("</MethodInvocation>"); //$NON-NLS-1$
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
