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
 * Holds an identifier.<br>
 * uses for variable name, function name and class name.
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * $variableName - variableName is the identifier, foo() - foo is the
 * identifier, $myClass->fun() - myClass and fun are identifiers
 */
public class Identifier extends VariableBase {
    @Setter
    @Getter
    private String name;

    public Identifier(int start, int end, AST ast, String value) {
        super(start, end, ast);

        if (value == null) {
            throw new IllegalArgumentException();
        }
        // intern the string for fast equality check
        value.intern();
        setName(value);
    }

    public Identifier(AST ast) {
        super(ast);
    }

    public void toString(StringBuffer buffer, String tab) {
        buffer.append(tab).append("<Identifier"); //$NON-NLS-1$
        appendInterval(buffer);
        buffer.append(" name='").append(name).append("'/>"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
