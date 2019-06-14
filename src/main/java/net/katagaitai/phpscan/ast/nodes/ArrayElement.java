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
 * Represents a single element of array. Holds the key and the value both can be
 * any expression The key can be null
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * 1, 'Dodo'=>'Golo', $a, $b=>foo(), 1=>$myClass->getFirst() *
 */
public class ArrayElement extends ASTNode {
    @Setter
    @Getter
    private Expression key;
    @Setter
    @Getter
    private Expression value;

    public ArrayElement(AST ast) {
        super(ast);
    }

    public ArrayElement(int start, int end, AST ast, Expression key, Expression value) {
        super(start, end, ast);
        if (value == null) {
            throw new IllegalArgumentException();
        }

        setValue(value);
        if (key != null) {
            setKey(key);
        }
    }

    public ArrayElement(int start, int end, AST ast, Expression value) {
        this(start, end, ast, null, value);
    }

    public void toString(StringBuffer buffer, String tab) {
        buffer.append(tab).append("<ArrayElement"); //$NON-NLS-1$
        appendInterval(buffer);
        buffer.append(">\n"); //$NON-NLS-1$
        buffer.append(TAB).append(tab).append("<Key>\n"); //$NON-NLS-1$
        if (key != null) {
            key.toString(buffer, TAB + TAB + tab);
            buffer.append("\n"); //$NON-NLS-1$
        }
        buffer.append(TAB).append(tab).append("</Key>\n"); //$NON-NLS-1$
        buffer.append(TAB).append(tab).append("<Value>\n"); //$NON-NLS-1$
        value.toString(buffer, TAB + TAB + tab);
        buffer.append("\n").append(TAB).append(tab).append("</Value>\n"); //$NON-NLS-1$ //$NON-NLS-2$
        buffer.append(tab).append("</ArrayElement>"); //$NON-NLS-1$
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
