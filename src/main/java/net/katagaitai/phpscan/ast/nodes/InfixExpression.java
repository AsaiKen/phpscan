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
import lombok.extern.log4j.Log4j2;
import net.katagaitai.phpscan.ast.Visitor;

/**
 * Represents an infix expression
 *
 * <pre>
 * e.g.
 *
 * <pre>
 * $a + 1, 3 - 2, foo() * $a->bar(), 'string'.$c
 */
@Log4j2
public class InfixExpression extends Expression {

	// '==='
	public static final int OP_IS_IDENTICAL = 0;
	// '!=='
	public static final int OP_IS_NOT_IDENTICAL = 1;
	// '=='
	public static final int OP_IS_EQUAL = 2;
	// '!='
	public static final int OP_IS_NOT_EQUAL = 3;
	// '<'
	public static final int OP_RGREATER = 4;
	// '<='
	public static final int OP_IS_SMALLER_OR_EQUAL = 5;
	// '>'
	public static final int OP_LGREATER = 6;
	// '>='
	public static final int OP_IS_GREATER_OR_EQUAL = 7;
	// '||'
	public static final int OP_BOOL_OR = 8;
	// '&&'
	public static final int OP_BOOL_AND = 9;
	// 'or'
	public static final int OP_STRING_OR = 10;
	// 'and'
	public static final int OP_STRING_AND = 11;
	// 'xor'
	public static final int OP_STRING_XOR = 12;
	// '|'
	public static final int OP_OR = 13;
	// '&'
	public static final int OP_AND = 14;
	// '^'
	public static final int OP_XOR = 15;
	// '.'
	public static final int OP_CONCAT = 16;
	// '+'
	public static final int OP_PLUS = 17;
	// '-'
	public static final int OP_MINUS = 18;
	// '*'
	public static final int OP_MUL = 19;
	// '/'
	public static final int OP_DIV = 20;
	// '%'
	public static final int OP_MOD = 21;
	// '<<'
	public static final int OP_SL = 22;
	// '>>'
	public static final int OP_SR = 23;
	// '**'
	public static final int OP_POW = 24;
	// '<=>'
	public static final int OP_SPACESHIP = 25;

	@Setter
	@Getter
	private Expression left;
	@Setter
	@Getter
	private int operator;
	@Setter
	@Getter
	private Expression right;

	public InfixExpression(int start, int end, AST ast, Expression left, int operator, Expression right) {
		super(start, end, ast);

		if (right == null || left == null || getOperator(operator) == null) {
			throw new IllegalArgumentException();
		}

		setLeft(left);
		setOperator(operator);
		setRight(right);
	}

	public InfixExpression(AST ast) {
		super(ast);
	}

	public static String getOperator(int operator) {
		switch (operator) {
		case OP_IS_IDENTICAL:
			return "==="; //$NON-NLS-1$
		case OP_IS_NOT_IDENTICAL:
			return "!=="; //$NON-NLS-1$
		case OP_IS_EQUAL:
			return "=="; //$NON-NLS-1$
		case OP_IS_NOT_EQUAL:
			return "!="; //$NON-NLS-1$
		case OP_RGREATER:
			return "<"; //$NON-NLS-1$
		case OP_IS_SMALLER_OR_EQUAL:
			return "<="; //$NON-NLS-1$
		case OP_LGREATER:
			return ">"; //$NON-NLS-1$
		case OP_IS_GREATER_OR_EQUAL:
			return ">="; //$NON-NLS-1$
		case OP_BOOL_OR:
			return "||"; //$NON-NLS-1$
		case OP_BOOL_AND:
			return "&&"; //$NON-NLS-1$
		case OP_STRING_OR:
			return "or"; //$NON-NLS-1$
		case OP_STRING_AND:
			return "and"; //$NON-NLS-1$
		case OP_STRING_XOR:
			return "xor"; //$NON-NLS-1$
		case OP_OR:
			return "|"; //$NON-NLS-1$
		case OP_AND:
			return "&"; //$NON-NLS-1$
		case OP_XOR:
			return "^"; //$NON-NLS-1$
		case OP_CONCAT:
			return "."; //$NON-NLS-1$
		case OP_PLUS:
			return "+"; //$NON-NLS-1$
		case OP_MINUS:
			return "-"; //$NON-NLS-1$
		case OP_MUL:
			return "*"; //$NON-NLS-1$
		case OP_DIV:
			return "/"; //$NON-NLS-1$
		case OP_MOD:
			return "%"; //$NON-NLS-1$
		case OP_SL:
			return "<<"; //$NON-NLS-1$
		case OP_SR:
			return ">>"; //$NON-NLS-1$
		case OP_POW:
			return "**"; //$NON-NLS-1$
		case OP_SPACESHIP:
			return "<=>"; //$NON-NLS-1$
		default:
			throw new IllegalArgumentException();
		}
	}

	public void toString(StringBuffer buffer, String tab) {
		// TODO パーサのバグにより再帰するケースがある。
		StackTraceElement[] stackTrace = new Throwable().getStackTrace();
		if (stackTrace.length >= 100) {
			log.warn("打ち切り");
			return;
		}
		buffer.append(tab).append("<InfixExpression"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(" operator='") //$NON-NLS-1$
				.append(getXmlStringValue(getOperator(operator))).append("'>\n"); //$NON-NLS-1$
		left.toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		right.toString(buffer, TAB + tab);
		buffer.append("\n").append(tab).append("</InfixExpression>"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	public Object accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
