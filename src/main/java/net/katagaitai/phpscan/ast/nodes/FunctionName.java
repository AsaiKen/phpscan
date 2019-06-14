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
 * Holds a function name. note that the function name can be expression,
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * foo() - the name is foo $a() - the variable $a holds the function name
 */
public class FunctionName extends ASTNode {
    @Setter
    @Getter
    private Expression name;

    public FunctionName(int start, int end, AST ast, Expression functionName) {
        super(start, end, ast);

        if (functionName == null) {
            throw new IllegalArgumentException();
        }

        setName(functionName);
    }

    public FunctionName(AST ast) {
        super(ast);
    }

    public void toString(StringBuffer buffer, String tab) {
        buffer.append(tab).append("<FunctionName"); //$NON-NLS-1$
        appendInterval(buffer);
        buffer.append(">\n"); //$NON-NLS-1$
        name.toString(buffer, TAB + tab);
        buffer.append("\n"); //$NON-NLS-1$
        buffer.append(tab).append("</FunctionName>"); //$NON-NLS-1$
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
