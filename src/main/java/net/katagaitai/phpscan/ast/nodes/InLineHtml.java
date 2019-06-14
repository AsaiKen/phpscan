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

import net.katagaitai.phpscan.ast.Visitor;

/**
 * Represents an HTML blocks in the resource
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * <html> </html> <html> <?php ?> </html> <?php ?>
 */
public class InLineHtml extends Statement {

    public InLineHtml(int start, int end, AST ast) {
        super(start, end, ast);
    }

    public InLineHtml(AST ast) {
        super(ast);
    }

    public void toString(StringBuffer buffer, String tab) {
        buffer.append(tab).append("<InLineHtml"); //$NON-NLS-1$
        appendInterval(buffer);
        buffer.append("/>"); //$NON-NLS-1$
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
