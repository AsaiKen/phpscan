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

import java.util.List;

/**
 * Represents base class for class declaration and interface declaration
 */
public abstract class TypeDeclaration extends Statement {
    @Setter
    @Getter
    private Identifier name;
    @Getter
    protected List<Identifier> interfaces = Lists.newArrayList();
    @Setter
    @Getter
    private Block body;

    public TypeDeclaration(int start, int end, AST ast, final Identifier name, final Identifier[] interfaces,
                           final Block body) {
        super(start, end, ast);

        if (name == null || body == null) {
            throw new IllegalArgumentException();
        }

        setName(name);
        setBody(body);
        if (interfaces != null) {
            for (Identifier identifier : interfaces) {
                this.interfaces.add(identifier);
            }
        }
    }

    public TypeDeclaration(AST ast) {
        super(ast);
    }

}
