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

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import net.katagaitai.phpscan.ast.Visitor;

import java.util.Iterator;
import java.util.List;

/**
 * Represents a declare statement
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * declare(ticks=1) { } declare(ticks=2) { for ($x = 1; $x < 50; ++$x) { } }
 */
public class DeclareStatement extends Statement {
    @Getter
    private final List<Identifier> directiveNames = Lists.newArrayList();
    @Getter
    private final List<Expression> directiveValues = Lists.newArrayList();
    @Setter
    @Getter
    private Statement body;

    private DeclareStatement(int start, int end, AST ast, Identifier[] directiveNames, Expression[] directiveValues,
                             Statement action) {
        super(start, end, ast);

        if (directiveNames == null || directiveValues == null || directiveNames.length != directiveValues.length) {
            throw new IllegalArgumentException();
        }
        for (Identifier identifier : directiveNames) {
            this.directiveNames.add(identifier);
        }
        for (Expression expression : directiveValues) {
            this.directiveValues.add(expression);
        }
        setBody(action);
    }

    public DeclareStatement(int start, int end, AST ast, List directiveNames, List directiveValues, Statement action) {
        this(start, end, ast,
                directiveNames == null ? null
                        : (Identifier[]) directiveNames.toArray(new Identifier[directiveNames.size()]),
                directiveValues == null ? null
                        : (Expression[]) directiveValues.toArray(new Expression[directiveValues.size()]),
                action);
    }

    public DeclareStatement(AST ast) {
        super(ast);
    }

    public void toString(StringBuffer buffer, String tab) {
        buffer.append(tab).append("<DeclareStatement"); //$NON-NLS-1$
        appendInterval(buffer);
        buffer.append(">\n"); //$NON-NLS-1$
        buffer.append(tab).append(TAB).append("<Directives>\n"); //$NON-NLS-1$
        final Iterator<Identifier> itId = directiveNames.iterator();
        final Iterator<Expression> itExpr = directiveValues.iterator();
        while (itId.hasNext()) {
            final Identifier name = itId.next();
            final Expression value = itExpr.next();
            buffer.append(tab).append(TAB).append(TAB).append("<Name>\n"); //$NON-NLS-1$
            name.toString(buffer, TAB + TAB + TAB + tab);
            buffer.append("\n"); //$NON-NLS-1$
            buffer.append(tab).append(TAB).append(TAB).append("</Name>\n"); //$NON-NLS-1$
            buffer.append(tab).append(TAB).append(TAB).append("<Value>\n"); //$NON-NLS-1$
            value.toString(buffer, TAB + TAB + TAB + tab);
            buffer.append("\n"); //$NON-NLS-1$
            buffer.append(tab).append(TAB).append(TAB).append("</Value>\n"); //$NON-NLS-1$
        }
        buffer.append(tab).append(TAB).append("</Directives>\n"); //$NON-NLS-1$
        body.toString(buffer, TAB + tab);
        buffer.append("\n"); //$NON-NLS-1$
        buffer.append(tab).append("</DeclareStatement>"); //$NON-NLS-1$
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
