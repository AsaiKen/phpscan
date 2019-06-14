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
 * Represents a 'use' statement
 * <p>
 * e.g.
 *
 * <pre>
 * use MyNamespace;
 * use MyNamespace as MyAlias;
 * use MyProject\Sub\Level as MyAlias;
 * use \MyProject\Sub\Level as MyAlias;
 * use \MyProject\Sub\Level as MyAlias, MyNamespace as OtherAlias, MyOtherNamespace;
 * use MyProject\Sub\Level\ { MyAlias, MyNamespace as OtherAlias, MyOtherNamespace };
 * </pre>
 */
public class UseStatement extends Statement {

    // none
    public static final int T_NONE = 0;
    // 'function' keyword
    public static final int T_FUNCTION = 1;
    // 'const' keyword
    public static final int T_CONST = 2;

    @Getter
    private final List<UseStatementPart> parts = Lists.newArrayList();
    @Setter
    @Getter
    private int statementType;
    @Setter
    @Getter
    private NamespaceName namespace;

    public UseStatement(AST ast) {
        super(ast);
    }

    public UseStatement(int start, int end, AST ast, List<UseStatementPart> parts) {
        this(start, end, ast, parts, T_NONE);
    }

    public UseStatement(int start, int end, AST ast, List<UseStatementPart> parts, int statementType) {
        this(start, end, ast, null, parts, statementType);
    }

    public UseStatement(int start, int end, AST ast, NamespaceName namespace, List<UseStatementPart> parts) {
        this(start, end, ast, namespace, parts, T_NONE);
    }

    public UseStatement(int start, int end, AST ast, NamespaceName namespace, List<UseStatementPart> parts,
                        int statementType) {
        super(start, end, ast);

        if (parts == null || parts.size() == 0) {
            throw new IllegalArgumentException();
        }
        setNamespace(namespace);
        Iterator<UseStatementPart> it = parts.iterator();
        while (it.hasNext()) {
            this.parts.add(it.next());
        }
        setStatementType(statementType);
    }

    public UseStatement(int start, int end, AST ast, UseStatementPart[] parts, int statementType) {
        super(start, end, ast);

        if (parts == null || parts.length == 0) {
            throw new IllegalArgumentException();
        }

        for (UseStatementPart part : parts) {
            this.parts.add(part);
        }
        setStatementType(statementType);
    }

    public UseStatement(int start, int end, AST ast, UseStatementPart[] parts) {
        this(start, end, ast, parts, T_NONE);
    }

    public void toString(StringBuffer buffer, String tab) {
        buffer.append(tab).append("<UseStatement"); //$NON-NLS-1$
        appendInterval(buffer);
        if (getStatementType() != T_NONE) {
            buffer.append(" statementType='").append(getStatementType()) //$NON-NLS-1$
                    .append("'"); //$NON-NLS-1$
        }
        buffer.append(">\n"); //$NON-NLS-1$
        if (getNamespace() != null) {
            getNamespace().toString(buffer, TAB + tab);
            buffer.append("\n"); //$NON-NLS-1$
        }
        for (UseStatementPart part : this.parts) {
            part.toString(buffer, TAB + tab);
            buffer.append("\n"); //$NON-NLS-1$
        }
        buffer.append(tab).append("</UseStatement>"); //$NON-NLS-1$
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
