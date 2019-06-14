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
 * Represents a fields declaration
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * var $a, $b; public $a = 3; final private static $var;
 */
public class SingleFieldDeclaration extends ASTNode {
    @Setter
    @Getter
    private Variable name;
    @Setter
    @Getter
    private Expression value;

    public SingleFieldDeclaration(AST ast) {
        super(ast);
    }

    public SingleFieldDeclaration(int start, int end, AST ast, Variable name, Expression value) {
        super(start, end, ast);

        if (name == null) {
            throw new IllegalArgumentException();
        }

        setName(name);
        if (value != null) {
            setValue(value);
        }
    }

    public void toString(StringBuffer buffer, String tab) {
        buffer.append(tab).append("<SingleFieldDeclaration"); //$NON-NLS-1$
        appendInterval(buffer);
        buffer.append("'>\n").append(tab).append(TAB).append("<VariableName>\n"); //$NON-NLS-1$ //$NON-NLS-2$
        name.toString(buffer, TAB + TAB + tab);
        buffer.append("\n"); //$NON-NLS-1$
        buffer.append(tab).append(TAB).append("</VariableName>\n"); //$NON-NLS-1$
        buffer.append(tab).append(TAB).append("<InitialValue>\n"); //$NON-NLS-1$
        if (value != null) {
            value.toString(buffer, TAB + TAB + tab);
            buffer.append("\n"); //$NON-NLS-1$
        }
        buffer.append(tab).append(TAB).append("</InitialValue>\n"); //$NON-NLS-1$
        buffer.append(tab).append("</SingleFieldDeclaration>"); //$NON-NLS-1$
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
