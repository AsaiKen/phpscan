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
 * Represents an element of the 'use' declaration.
 * <p>
 * e.g.
 *
 * <pre>
 * MyNamespace;
 * MyNamespace as MyAlias;
 * MyProject\Sub\Level as MyAlias;
 * \MyProject\Sub\Level as MyAlias;
 * function \MyProject\Sub\Level as MyAlias;
 * </pre>
 */
public class UseStatementPart extends ASTNode {
    @Setter
    @Getter
    private NamespaceName name;
    @Setter
    @Getter
    private Identifier alias;
    @Setter
    @Getter
    private int statementType;

    public UseStatementPart(AST ast) {
        super(ast);
    }

    public UseStatementPart(int start, int end, AST ast, NamespaceName name, Identifier alias) {
        this(start, end, ast, name, alias, UseStatement.T_NONE);
    }

    public UseStatementPart(int start, int end, AST ast, NamespaceName name, Identifier alias, int statementType) {
        super(start, end, ast);
        if (name == null) {
            throw new IllegalArgumentException();
        }

        setName(name);
        if (alias != null) {
            setAlias(alias);
        }
        setStatementType(statementType);
    }

    public void toString(StringBuffer buffer, String tab) {
        buffer.append(tab).append("<UseStatementPart"); //$NON-NLS-1$
        appendInterval(buffer);
        if (getStatementType() != UseStatement.T_NONE) {
            buffer.append(" statementType='").append(getStatementType()) //$NON-NLS-1$
                    .append("'"); //$NON-NLS-1$
        }
        buffer.append(">\n"); //$NON-NLS-1$

        buffer.append(TAB).append(tab).append("<Name>\n"); //$NON-NLS-1$
        name.toString(buffer, TAB + TAB + tab);
        buffer.append("\n"); //$NON-NLS-1$
        buffer.append(TAB).append(tab).append("</Name>\n"); //$NON-NLS-1$

        if (alias != null) {
            buffer.append(TAB).append(tab).append("<Alias>\n"); //$NON-NLS-1$
            alias.toString(buffer, TAB + TAB + tab);
            buffer.append("\n").append(TAB).append(tab).append("</Alias>\n"); //$NON-NLS-1$ //$NON-NLS-2$
        }

        buffer.append(tab).append("</UseStatementPart>"); //$NON-NLS-1$
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
