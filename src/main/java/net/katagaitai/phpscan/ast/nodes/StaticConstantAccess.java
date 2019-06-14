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
 * Represents a constant class access
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * MyClass::CONST
 */
public class StaticConstantAccess extends StaticDispatch {
    @Setter
    @Getter
    private Identifier constant;

    public StaticConstantAccess(int start, int end, AST ast, Expression className, Identifier constant) {
        super(start, end, ast, className);

        if (constant == null) {
            throw new IllegalArgumentException();
        }
        setConstant(constant);
    }

    public StaticConstantAccess(AST ast) {
        super(ast);
    }

    public StaticConstantAccess(int start, int end, AST ast, Identifier name) {
        this(start, end, ast, null, name);
    }

    public ASTNode getMember() {
        return getConstant();
    }

    public void toString(StringBuffer buffer, String tab) {
        buffer.append(tab).append("<StaticConstantAccess"); //$NON-NLS-1$
        appendInterval(buffer);
        buffer.append(">\n"); //$NON-NLS-1$
        buffer.append(TAB).append(tab).append("<ClassName>\n"); //$NON-NLS-1$
        getClassName().toString(buffer, TAB + TAB + tab);
        buffer.append("\n").append(TAB).append(tab).append("</ClassName>\n"); //$NON-NLS-1$ //$NON-NLS-2$
        buffer.append(TAB).append(tab).append("<Constant>\n"); //$NON-NLS-1$
        constant.toString(buffer, TAB + TAB + tab);
        buffer.append("\n").append(TAB).append(tab).append("</Constant>\n"); //$NON-NLS-1$ //$NON-NLS-2$
        buffer.append(tab).append("</StaticConstantAccess>"); //$NON-NLS-1$
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
