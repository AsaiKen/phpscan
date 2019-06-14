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
 * Represents a class instantiation. This class holds the class name as an
 * expression and array of constructor parameters
 * <p>
 * e.g.
 *
 * <pre>
 * new MyClass(),
 * new $a('start'),
 * new foo()(1, $a)
 * </pre>
 */
public class ClassInstanceCreation extends VariableBase {
    @Setter
    @Getter
    private ClassName className;
    @Getter
    private List<Expression> ctorParams = Lists.newArrayList();

    //	@Setter
    //	@Getter
    //	private AnonymousClassDeclaration anonymousClassDeclaration;

    public ClassInstanceCreation(int start, int end, AST ast, ClassName className, Expression[] ctorParams) {
        super(start, end, ast);
        if (className == null || ctorParams == null) {
            throw new IllegalArgumentException();
        }

        setClassName(className);
        for (Expression expression : ctorParams) {
            this.ctorParams.add(expression);
        }
    }

    //	public ClassInstanceCreation(int start, int end, AST ast, ClassName className, Expression[] ctorParams,
    //			AnonymousClassDeclaration anonymousClassDeclaration) {
    //		super(start, end, ast);
    //		if (className == null || anonymousClassDeclaration == null || ctorParams == null) {
    //			throw new IllegalArgumentException();
    //		}
    //		setClassName(className);
    //		setAnonymousClassDeclaration(anonymousClassDeclaration);
    //		for (Expression expression : ctorParams) {
    //			this.ctorParams.add(expression);
    //		}
    //	}

    public ClassInstanceCreation(AST ast) {
        super(ast);
    }

    public ClassInstanceCreation(int start, int end, AST ast, ClassName className, List ctorParams) {
        this(start, end, ast, className,
                ctorParams == null ? null : (Expression[]) ctorParams.toArray(new Expression[ctorParams.size()]));
    }

    //	public ClassInstanceCreation(int start, int end, AST ast, ClassName className, List ctorParams,
    //			AnonymousClassDeclaration anonymousClassDeclaration) {
    //		this(start, end, ast, className,
    //				ctorParams == null ? null : (Expression[]) ctorParams.toArray(new Expression[ctorParams.size()]),
    //				anonymousClassDeclaration);
    //	}

    public void toString(StringBuffer buffer, String tab) {
        buffer.append(tab).append("<ClassInstanceCreation"); //$NON-NLS-1$
        appendInterval(buffer);
        buffer.append(">\n"); //$NON-NLS-1$
        if (className != null) {
            className.toString(buffer, TAB + tab);
        }
        buffer.append("\n").append(TAB).append(tab) //$NON-NLS-1$
                .append("<ConstructorParameters>\n"); //$NON-NLS-1$
        for (ASTNode node : this.ctorParams) {
            node.toString(buffer, TAB + TAB + tab);
            buffer.append("\n"); //$NON-NLS-1$
        }
        buffer.append(TAB).append(tab).append("</ConstructorParameters>\n"); //$NON-NLS-1$
        //		if (getAnonymousClassDeclaration() != null) {
        //			getAnonymousClassDeclaration().toString(buffer, TAB + tab);
        //			buffer.append("\n"); //$NON-NLS-1$
        //		}
        buffer.append(tab).append("</ClassInstanceCreation>"); //$NON-NLS-1$
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
