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

import java.util.List;

/**
 * Represents the try statement
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * try { statements... } catch (Exception $e) { statements... } catch
 * (AnotherException $ae) { statements... }
 */
public class TryStatement extends Statement {
    @Setter
    @Getter
    private Block tryStatement;
    @Setter
    @Getter
    private FinallyClause finallyClause;
    @Getter
    private List<CatchClause> catchClauses = Lists.newArrayList();

    private TryStatement(int start, int end, AST ast, Block tryStatement, CatchClause[] catchClauses,
                         FinallyClause finallyClause) {
        super(start, end, ast);

        if (tryStatement == null || catchClauses == null) {
            throw new IllegalArgumentException();
        }
        setTryStatement(tryStatement);
        for (int i = 0; i < catchClauses.length; i++) {
            this.catchClauses.add(catchClauses[i]);
        }
        setFinallyClause(finallyClause);
    }

    private TryStatement(int start, int end, AST ast, Block tryStatement, CatchClause[] catchClauses) {
        super(start, end, ast);
        if (tryStatement == null || catchClauses == null) {
            throw new IllegalArgumentException();
        }
        setTryStatement(tryStatement);
        for (int i = 0; i < catchClauses.length; i++) {
            this.catchClauses.add(catchClauses[i]);
        }
    }

    public TryStatement(int start, int end, AST ast, Block tryStatement, List catchClauses) {
        this(start, end, ast, tryStatement, catchClauses == null ? null
                : (CatchClause[]) catchClauses.toArray(new CatchClause[catchClauses.size()]));
    }

    public TryStatement(int start, int end, AST ast, Block tryStatement, List catchClauses,
                        FinallyClause finallyClause) {
        this(start, end, ast, tryStatement, catchClauses == null ? null
                : (CatchClause[]) catchClauses.toArray(new CatchClause[catchClauses.size()]), finallyClause);
    }

    public TryStatement(AST ast) {
        super(ast);
    }

    public void toString(StringBuffer buffer, String tab) {
        buffer.append(tab).append("<TryStatement"); //$NON-NLS-1$
        appendInterval(buffer);
        buffer.append(">\n"); //$NON-NLS-1$
        tryStatement.toString(buffer, TAB + tab);
        buffer.append("\n"); //$NON-NLS-1$
        for (ASTNode catchClause : this.catchClauses) {
            catchClause.toString(buffer, TAB + tab);
            buffer.append("\n"); //$NON-NLS-1$
        }
        if (finallyClause != null) {
            finallyClause.toString(buffer, TAB + tab);
            buffer.append("\n"); //$NON-NLS-1$
        }
        buffer.append(tab).append("</TryStatement>"); //$NON-NLS-1$
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
