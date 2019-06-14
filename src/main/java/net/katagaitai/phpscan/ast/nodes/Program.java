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
import net.katagaitai.phpscan.ast.Visitor;

import java.util.List;

/**
 * The AST root node for PHP program (meaning a PHP file). The program holds
 * array of statements such as Class, Function and evaluation statement. The
 * program also holds the PHP file comments.
 *
 * @author Moshe S. & Roy G. 2007
 */
public class Program extends ASTNode {

    /**
     * Statements array of php program
     */
    @Getter
    private final List<Statement> statements = Lists.newArrayList();

    /**
     * Comments array of the php program
     */
    @Getter
    private final List<Comment> comments = Lists.newArrayList();

    private Program(int start, int end, AST ast, Statement[] statements, List comments) {
        super(start, end, ast);

        if (statements == null || comments == null) {
            throw new IllegalArgumentException();
        }

        for (Statement statement : statements) {
            this.statements.add(statement);
        }
        for (Object comment : comments) {
            this.comments.add((Comment) comment);
        }
    }

    public Program(int start, int end, AST ast, List statements, List commentList) {
        this(start, end, ast, (Statement[]) statements.toArray(new Statement[statements.size()]), commentList);
    }

    public Program(AST ast) {
        super(ast);
    }

    /**
     * create program node in XML format.
     */
    public void toString(StringBuffer buffer, String tab) {
        buffer.append("<Program"); //$NON-NLS-1$
        appendInterval(buffer);
        buffer.append(">\n").append(TAB).append("<Statements>\n"); //$NON-NLS-1$ //$NON-NLS-2$
        for (ASTNode node : this.statements) {
            node.toString(buffer, TAB + TAB + tab);
            buffer.append("\n"); //$NON-NLS-1$
        }
        buffer.append(TAB).append("</Statements>\n").append(TAB).append("<Comments>\n"); //$NON-NLS-1$ //$NON-NLS-2$
        for (ASTNode comment : this.comments) {
            comment.toString(buffer, TAB + TAB + tab);
            buffer.append("\n"); //$NON-NLS-1$
        }
        buffer.append(TAB).append("</Comments>\n").append("</Program>"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
