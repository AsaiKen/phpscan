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
import net.katagaitai.phpscan.ast.Modifiers;
import net.katagaitai.phpscan.ast.PHPFlags;

/**
 * Base class for class member declarations
 */
public abstract class BodyDeclaration extends Statement {
    @Setter
    @Getter
    private int modifier;

    public BodyDeclaration(int start, int end, AST ast, int modifier, boolean shouldComplete) {
        super(start, end, ast);

        setModifier(shouldComplete ? completeModifier(modifier) : modifier);
    }

    public BodyDeclaration(int start, int end, AST ast, int modifier) {
        this(start, end, ast, modifier, false);
    }

    public BodyDeclaration(AST ast) {
        super(ast);
    }

    /**
     * Complets the modidifer to public if needed
     *
     * @param mod
     */
    private static int completeModifier(int mod) {
        if (!PHPFlags.isPrivate(mod) && !PHPFlags.isProtected(mod)) {
            mod |= Modifiers.AccPublic;
        }
        return mod;
    }

    public String getModifierString() {
        return PHPFlags.toString(modifier);
    }

}
