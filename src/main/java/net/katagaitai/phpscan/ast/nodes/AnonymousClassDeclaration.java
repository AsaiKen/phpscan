/*******************************************************************************
 * Copyright (c) 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package net.katagaitai.phpscan.ast.nodes;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import net.katagaitai.phpscan.ast.Visitor;

import java.util.List;

public class AnonymousClassDeclaration extends Expression {
    @Setter
    @Getter
    private Expression superClass;
    @Setter
    @Getter
    protected List<Identifier> interfaces = Lists.newArrayList();
    @Setter
    @Getter
    private Block body;

    public AnonymousClassDeclaration(AST ast) {
        super(ast);
    }

    public AnonymousClassDeclaration(int start, int end, AST ast, Expression superClass, List<Identifier> interfaces,
                                     Block body) {
        super(start, end, ast);
        setInterfaces(interfaces);
        setSuperClass(superClass);
        setBody(body);
    }

    public void toString(StringBuffer buffer, String tab) {
        buffer.append(tab).append("<AnonymousClassDeclaration"); //$NON-NLS-1$
        appendInterval(buffer);
        buffer.append("'>\n"); //$NON-NLS-1$

        buffer.append(TAB).append(tab).append("<SuperClass>\n"); //$NON-NLS-1$
        if (superClass != null) {
            superClass.toString(buffer, TAB + TAB + tab);
            buffer.append("\n"); //$NON-NLS-1$
        }
        buffer.append(TAB).append(tab).append("<SuperClass>\n"); //$NON-NLS-1$

        buffer.append(TAB).append(tab).append("<Interfaces>\n"); //$NON-NLS-1$
        if (interfaces != null) {
            for (Identifier identifier : interfaces) {
                identifier.toString(buffer, TAB + TAB + tab);
                buffer.append("\n"); //$NON-NLS-1$
            }
        }
        buffer.append(TAB).append(tab).append("<Interfaces>\n"); //$NON-NLS-1$

        buffer.append(TAB).append(tab).append("<Body>\n"); //$NON-NLS-1$
        if (body != null) {
            body.toString(buffer, TAB + TAB + tab);
            buffer.append("\n"); //$NON-NLS-1$
        }
        buffer.append(TAB).append(tab).append("<Body>\n"); //$NON-NLS-1$
        buffer.append(tab).append("</AnonymousClassDeclaration>"); //$NON-NLS-1$
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
