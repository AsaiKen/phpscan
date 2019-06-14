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
import net.katagaitai.phpscan.ast.Visitor;

/**
 * Holds a variable and an index that point to array or hashtable
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * $a[], $a[1], $a[$b], $a{'name'}
 */
public class ArrayAccess extends Variable {
    public static final int VARIABLE_ARRAY = 1; // default
    public static final int VARIABLE_HASHTABLE = 2;

    /**
     * In case of array / hashtable variable, the index expression is added
     */
    @Setter
    @Getter
    private Expression index;
    @Setter
    @Getter
    private int arrayType;

    public ArrayAccess(AST ast) {
        super(ast);
    }

    public ArrayAccess(int start, int end, AST ast, VariableBase variableName, Expression index, int arrayType) {
        super(start, end, ast, variableName);

        if (index != null) {
            setIndex(index);
        }
        setArrayType(arrayType);
    }

    /**
     * Create a new ArrayAccess. Default array type is VARIABLE_ARRAY
     *
     * @param start
     * @param end
     * @param ast
     * @param variableName
     * @param index
     */
    public ArrayAccess(int start, int end, AST ast, VariableBase variableName, Expression index) {
        super(start, end, ast, variableName);

        if (index != null) {
            setIndex(index);
        }
    }

    public void toString(StringBuffer buffer, String tab) {
        buffer.append(tab).append("<ArrayAccess"); //$NON-NLS-1$
        appendInterval(buffer);
        buffer.append(" type='").append(getArrayType(arrayType)).append("'>\n"); //$NON-NLS-1$ //$NON-NLS-2$
        getName().toString(buffer, TAB + tab);
        buffer.append("\n").append(TAB).append(tab).append("<Index>\n"); //$NON-NLS-1$ //$NON-NLS-2$
        if (index != null) {
            index.toString(buffer, TAB + TAB + tab);
            buffer.append("\n"); //$NON-NLS-1$
        }
        buffer.append(TAB).append(tab).append("</Index>\n"); //$NON-NLS-1$
        buffer.append(tab).append("</ArrayAccess>"); //$NON-NLS-1$
    }

    public static String getArrayType(int type) {
        switch (type) {
            case VARIABLE_ARRAY:
                return "array"; //$NON-NLS-1$
            case VARIABLE_HASHTABLE:
                return "hashtable"; //$NON-NLS-1$
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
