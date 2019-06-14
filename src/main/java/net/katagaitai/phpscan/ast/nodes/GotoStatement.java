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
 * Holds a goto statement.
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * goto START;
 */
public class GotoStatement extends Statement {
    @Setter
    @Getter
    private Identifier label;

    public GotoStatement(int start, int end, AST ast, Identifier label) {
        super(start, end, ast);

        if (label == null) {
            throw new IllegalArgumentException();
        }
        setLabel(label);
    }

    public GotoStatement(AST ast) {
        super(ast);
    }

    public void toString(StringBuffer buffer, String tab) {
        buffer.append(tab).append("<GotoStatement"); //$NON-NLS-1$
        appendInterval(buffer);
        buffer.append(">\n"); //$NON-NLS-1$
        label.toString(buffer, TAB + tab);
        buffer.append("\n").append(tab).append("</GotoStatement>"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
