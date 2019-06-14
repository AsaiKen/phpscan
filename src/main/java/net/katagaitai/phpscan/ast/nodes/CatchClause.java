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
 * Represents a catch clause (as part of a try statement)
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * catch (ExceptionClassName $variable) { body; },
 */
public class CatchClause extends Statement {
    @Setter
    @Getter
    private Expression className;
    @Setter
    @Getter
    private Variable variable;
    @Setter
    @Getter
    private Block body;

    public CatchClause(int start, int end, AST ast, Expression className, Variable variable, Block statement) {
        super(start, end, ast);

        if (variable == null || statement == null) {
            throw new IllegalArgumentException();
        }
        if (!(className instanceof Identifier) && !(className instanceof NamespaceName)) {
            throw new IllegalArgumentException();
        }

        this.className = className;
        this.variable = variable;
        this.body = statement;

        className.setParent(this);
        variable.setParent(this);
        statement.setParent(this);
    }

    public CatchClause(AST ast) {
        super(ast);
    }

    public void toString(StringBuffer buffer, String tab) {
        buffer.append(tab).append("<CatchClause"); //$NON-NLS-1$
        appendInterval(buffer);
        buffer.append(">\n"); //$NON-NLS-1$
        buffer.append(TAB).append(tab).append("<ClassName>\n"); //$NON-NLS-1$
        className.toString(buffer, TAB + TAB + tab);
        buffer.append("\n"); //$NON-NLS-1$
        buffer.append(TAB).append(tab).append("</ClassName>\n"); //$NON-NLS-1$
        variable.toString(buffer, TAB + tab);
        buffer.append("\n"); //$NON-NLS-1$
        body.toString(buffer, TAB + tab);
        buffer.append("\n"); //$NON-NLS-1$
        buffer.append(tab).append("</CatchClause>"); //$NON-NLS-1$
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
