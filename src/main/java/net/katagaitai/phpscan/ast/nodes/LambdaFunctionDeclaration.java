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
 * Represents a lambda function declaration e.g.
 *
 * <pre>
 * function & (parameters) use (lexical vars) { body }
 * </pre>
 *
 * @see http://wiki.php.net/rfc/closures
 */
public class LambdaFunctionDeclaration extends Expression {
    @Setter
    @Getter
    private boolean isReference;
    @Setter
    @Getter
    private boolean isStatic;
    @Setter
    @Getter
    private int staticStart;
    @Setter
    @Getter
    private Block body;
    @Setter
    @Getter
    private Identifier returnType;
    @Getter
    private final List<FormalParameter> formalParameters = Lists.newArrayList();
    @Getter
    private final List<Expression> lexicalVariables = Lists.newArrayList();

    public LambdaFunctionDeclaration(int start, int end, AST ast, List formalParameters, List lexicalVars, Block body,
                                     final boolean isReference) {
        this(start, end, ast, formalParameters, lexicalVars, body, isReference, false, -1);
    }

    public LambdaFunctionDeclaration(int start, int end, AST ast, List formalParameters, List lexicalVars, Block body,
                                     final boolean isReference, final boolean isStatic, int staticStart) {
        this(start, end, ast, formalParameters, lexicalVars, body, isReference, isStatic, staticStart, null);
    }

    public LambdaFunctionDeclaration(int start, int end, AST ast, List formalParameters, List lexicalVars, Block body,
                                     final boolean isReference, final boolean isStatic, int staticStart,
                                     Identifier returnType) {
        super(start, end, ast);

        if (formalParameters == null) {
            throw new IllegalArgumentException();
        }

        setReference(isReference);

        Iterator<FormalParameter> paramIt = formalParameters.iterator();
        while (paramIt.hasNext()) {
            this.formalParameters.add(paramIt.next());
        }
        if (lexicalVars != null) {
            Iterator<Expression> varsIt = lexicalVars.iterator();
            while (varsIt.hasNext()) {
                this.lexicalVariables.add(varsIt.next());
            }
        }
        if (body != null) {
            setBody(body);
        }
        setStatic(isStatic);
        this.staticStart = staticStart;
        this.returnType = returnType;
    }

    public LambdaFunctionDeclaration(AST ast) {
        super(ast);
    }

    public void toString(StringBuffer buffer, String tab) {
        buffer.append(tab).append("<LambdaFunctionDeclaration"); //$NON-NLS-1$
        appendInterval(buffer);
        buffer.append(" isReference='").append(isReference); //$NON-NLS-1$ //$NON-NLS-2$
        if (isStatic) {
            buffer.append(" isStatic='").append(isStatic); //$NON-NLS-1$ //$NON-NLS-2$
        }
        buffer.append("'>\n"); //$NON-NLS-1$
        buffer.append(TAB).append(tab).append("<FormalParameters>\n"); //$NON-NLS-1$
        for (ASTNode node : this.formalParameters) {
            node.toString(buffer, TAB + TAB + tab);
            buffer.append("\n"); //$NON-NLS-1$
        }
        buffer.append(TAB).append(tab).append("</FormalParameters>\n"); //$NON-NLS-1$

        buffer.append(TAB).append(tab).append("<LexicalVariables>\n"); //$NON-NLS-1$
        for (ASTNode node : this.lexicalVariables) {
            node.toString(buffer, TAB + TAB + tab);
            buffer.append("\n"); //$NON-NLS-1$
        }
        buffer.append(TAB).append(tab).append("</LexicalVariables>\n"); //$NON-NLS-1$

        if (getReturnType() != null) {
            buffer.append(TAB).append(tab).append("<ReturnType>\n"); //$NON-NLS-1$
            getReturnType().toString(buffer, TAB + TAB + tab);
            buffer.append("\n"); //$NON-NLS-1$
            buffer.append(TAB).append(tab).append("</ReturnType>\n"); //$NON-NLS-1$
        }

        buffer.append(TAB).append(tab).append("<FunctionBody>\n"); //$NON-NLS-1$
        if (body != null) {
            body.toString(buffer, TAB + TAB + tab);
            buffer.append("\n"); //$NON-NLS-1$
        }
        buffer.append(TAB).append(tab).append("</FunctionBody>\n"); //$NON-NLS-1$
        buffer.append(tab).append("</LambdaFunctionDeclaration>"); //$NON-NLS-1$
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
