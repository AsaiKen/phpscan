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

/**
 * Abstract superclass of all Abstract Syntax Tree (AST) node types.
 * <p>
 * An AST node represents a PHP source code construct, such as a name, type,
 * expression, statement, or declaration.
 * <p>
 * ASTs do not contain cycles.
 *
 */
public abstract class ASTNode implements Visitable {

	/**
	 * ASTNode Types
	 */
	public static final int ARRAY_ACCESS = 0;
	public static final int ARRAY_CREATION = 1;
	public static final int ARRAY_ELEMENT = 2;
	public static final int ASSIGNMENT = 3;
	public static final int AST_ERROR = 4;
	public static final int BACK_TICK_EXPRESSION = 5;
	public static final int BLOCK = 6;
	public static final int BREAK_STATEMENT = 7;
	public static final int CAST_EXPRESSION = 8;
	public static final int CATCH_CLAUSE = 9;
	public static final int STATIC_CONSTANT_ACCESS = 10;
	public static final int CONSTANT_DECLARATION = 11;
	public static final int CLASS_DECLARATION = 12;
	public static final int CLASS_INSTANCE_CREATION = 13;
	public static final int CLASS_NAME = 14;
	public static final int CLONE_EXPRESSION = 15;
	public static final int COMMENT = 16;
	public static final int CONDITIONAL_EXPRESSION = 17;
	public static final int CONTINUE_STATEMENT = 18;
	public static final int DECLARE_STATEMENT = 19;
	public static final int DO_STATEMENT = 20;
	public static final int ECHO_STATEMENT = 21;
	public static final int EMPTY_STATEMENT = 22;
	public static final int EXPRESSION_STATEMENT = 23;
	public static final int FIELD_ACCESS = 24;
	public static final int FIELD_DECLARATION = 25;
	public static final int FOR_EACH_STATEMENT = 26;
	public static final int FORMAL_PARAMETER = 27;
	public static final int FOR_STATEMENT = 28;
	public static final int FUNCTION_DECLARATION = 29;
	public static final int FUNCTION_INVOCATION = 30;
	public static final int FUNCTION_NAME = 31;
	public static final int GLOBAL_STATEMENT = 32;
	public static final int IDENTIFIER = 33;
	public static final int IF_STATEMENT = 34;
	public static final int IGNORE_ERROR = 35;
	public static final int INCLUDE = 36;
	public static final int INFIX_EXPRESSION = 37;
	public static final int IN_LINE_HTML = 38;
	public static final int INSTANCE_OF_EXPRESSION = 39;
	public static final int INTERFACE_DECLARATION = 40;
	public static final int LIST_VARIABLE = 41;
	public static final int METHOD_DECLARATION = 42;
	public static final int METHOD_INVOCATION = 43;
	public static final int POSTFIX_EXPRESSION = 44;
	public static final int PREFIX_EXPRESSION = 45;
	public static final int PROGRAM = 46;
	public static final int QUOTE = 47;
	public static final int REFERENCE = 48;
	public static final int REFLECTION_VARIABLE = 49;
	public static final int RETURN_STATEMENT = 50;
	public static final int SCALAR = 51;
	public static final int STATIC_FIELD_ACCESS = 52;
	public static final int STATIC_METHOD_INVOCATION = 53;
	public static final int STATIC_STATEMENT = 54;
	public static final int SWITCH_CASE = 55;
	public static final int SWITCH_STATEMENT = 56;
	public static final int THROW_STATEMENT = 57;
	public static final int TRY_STATEMENT = 58;
	public static final int UNARY_OPERATION = 59;
	public static final int VARIABLE = 60;
	public static final int WHILE_STATEMENT = 61;
	public static final int PARENTHESIS_EXPRESSION = 62;
	public static final int SINGLE_FIELD_DECLARATION = 63;
	public static final int NAMESPACE = 64;
	public static final int NAMESPACE_NAME = 65;
	public static final int USE_STATEMENT_PART = 66;
	public static final int USE_STATEMENT = 67;
	public static final int GOTO_LABEL = 68;
	public static final int GOTO_STATEMENT = 69;
	public static final int LAMBDA_FUNCTION_DECLARATION = 70;
	public static final int TRAIT_USE_STATEMENT = 71;
	public static final int TRAIT_DECLARATION = 72;
	public static final int FULLY_QUALIFIED_TRAIT_METHOD_REFERENCE = 73;
	public static final int TRAIT_ALIAS = 74;
	public static final int YIELD_STATEMENT = 75;
	public static final int FINALLY_CLAUSE = 76;
	public static final int ANONYMOUS_CLASS_DECLARATION = 77;

	/**
	 * Source range
	 */
	@Getter
	@Setter
	private int start = -1;
	@Getter
	@Setter
	private int length = 0;

	/**
	 * character containing flags; none set by default.
	 * <p>
	 * N.B. This is a private field, but declared as package-visible for more
	 * efficient access from inner classes.
	 * </p>
	 *
	 * @see #MALFORMED, #PROTECT, #RECOVERED, #ORIGINAL
	 */
	@Getter
	@Setter
	int flags = 0;

	/**
	 * Owning AST.
	 * <p>
	 * N.B. This is a private field, but declared as package-visible for more
	 * efficient access from inner classes.
	 * </p>
	 */
	@Getter
	final AST ast;

	/**
	 * Parent AST node, or <code>null</code> if this node is a root. Initially
	 * <code>null</code>.
	 */
	@Getter
	@Setter
	private ASTNode parent;

	/**
	 * Construct an empty ASTNode and attach it with the given AST
	 *
	 * @param ast
	 */
	public ASTNode(AST ast) {
		if (ast == null) {
			throw new IllegalArgumentException();
		}
		this.ast = ast;
		setFlags(ast.getDefaultNodeFlag());
	}

	/**
	 * Construct a ranged ASTNode and attach it with the given AST
	 *
	 * @param ast
	 */
	public ASTNode(int start, int end, AST ast) {
		this(ast);
		this.start = start;
		this.length = end - start;
	}

	public final int getEnd() {
		return start + length;
	}

	/**
	 * Sets the source range of the original source file where the source
	 * fragment corresponding to this node was found.
	 * <p>
	 * See {@link ASTParser#setKind(int)} for details on precisely where source
	 * ranges are supposed to begin and end.
	 * </p>
	 *
	 * @param startPosition
	 *            a 0-based character index, or <code>-1</code> if no source
	 *            position information is available for this node
	 * @param length
	 *            a (possibly 0) length, or <code>0</code> if no source position
	 *            information is recorded for this node
	 * @see #getStartPosition()
	 * @see #getLength()
	 * @see ASTParser
	 */
	public final void setSourceRange(int startPosition, int length) {
		if (startPosition >= 0 && length < 0) {
			throw new IllegalArgumentException();
		}
		if (startPosition < 0 && length != 0) {
			throw new IllegalArgumentException();
		}
		this.start = startPosition;
		this.length = length;
	}

	/**
	 * Appends the start, length parameters to the buffer
	 */
	protected void appendInterval(StringBuffer buffer) {
		buffer.append(" start='").append(start).append("' length='") //$NON-NLS-1$ //$NON-NLS-2$
				.append(length).append("'"); //$NON-NLS-1$
	}

	protected static String getXmlStringValue(String input) {
		String escapedString = input;
		escapedString = escapedString.replaceAll("&", "&amp;"); //$NON-NLS-1$ //$NON-NLS-2$
		escapedString = escapedString.replaceAll(">", "&gt;"); //$NON-NLS-1$ //$NON-NLS-2$
		escapedString = escapedString.replaceAll("<", "&lt;"); //$NON-NLS-1$ //$NON-NLS-2$
		escapedString = escapedString.replaceAll("'", "&apos;"); //$NON-NLS-1$ //$NON-NLS-2$
		return escapedString;
	}

	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		toString(buffer, ""); //$NON-NLS-1$
		return buffer.toString();
	}

}
