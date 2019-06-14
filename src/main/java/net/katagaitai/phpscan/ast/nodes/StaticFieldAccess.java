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
 * Represents a static field access.
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * MyClass::$a MyClass::$$a[3]
 */
public class StaticFieldAccess extends StaticDispatch {
    @Setter
    @Getter
    private Variable field;

    public StaticFieldAccess(int start, int end, AST ast, Expression className, Variable field) {
        super(start, end, ast, className);

        if (field == null) {
            throw new IllegalArgumentException();
        }
        setField(field);
    }

    public StaticFieldAccess(AST ast) {
        super(ast);
    }

    public ASTNode getMember() {
        return getField();
    }

    public void toString(StringBuffer buffer, String tab) {
        buffer.append(tab).append("<StaticFieldAccess"); //$NON-NLS-1$
        appendInterval(buffer);
        buffer.append(">\n"); //$NON-NLS-1$
        buffer.append(TAB).append(tab).append("<ClassName>\n"); //$NON-NLS-1$
        getClassName().toString(buffer, TAB + TAB + tab);
        buffer.append("\n").append(TAB).append(tab).append("</ClassName>\n"); //$NON-NLS-1$ //$NON-NLS-2$
        field.toString(buffer, TAB + tab);
        buffer.append("\n").append(tab).append("</StaticFieldAccess>"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
