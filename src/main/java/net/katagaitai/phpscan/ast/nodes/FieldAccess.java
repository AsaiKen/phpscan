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
 * Represents a field access
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * $a->b, $a->$b
 */
public class FieldAccess extends Dispatch {
    @Setter
    @Getter
    private Variable field;

    public FieldAccess(int start, int end, AST ast, VariableBase dispatcher, Variable field) {
        super(start, end, ast, dispatcher);

        if (field == null) {
            throw new IllegalArgumentException();
        }
        setField(field);
    }

    public FieldAccess(AST ast) {
        super(ast);
    }

    public void toString(StringBuffer buffer, String tab) {
        buffer.append(tab).append("<FieldAccess"); //$NON-NLS-1$
        appendInterval(buffer);
        buffer.append(">\n"); //$NON-NLS-1$
        buffer.append(TAB).append(tab).append("<Dispatcher>\n"); //$NON-NLS-1$
        getDispatcher().toString(buffer, TAB + TAB + tab);
        buffer.append("\n").append(TAB).append(tab).append("</Dispatcher>\n"); //$NON-NLS-1$ //$NON-NLS-2$
        buffer.append(TAB).append(tab).append("<Property>\n"); //$NON-NLS-1$
        field.toString(buffer, TAB + TAB + tab);
        buffer.append("\n").append(TAB).append(tab).append("</Property>\n"); //$NON-NLS-1$ //$NON-NLS-2$
        buffer.append(tab).append("</FieldAccess>"); //$NON-NLS-1$
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
