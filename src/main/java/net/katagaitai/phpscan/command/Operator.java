package net.katagaitai.phpscan.command;

import lombok.Getter;

public enum Operator {
	ADD("+"),
	SUB("-"),
	MUL("*"),
	DIV("/"),
	MOD("%"),
	POW("**"),
	BITAND("&"),
	BITOR("|"),
	BITXOR("^"),
	BITNOT("~"),
	LSH("<<"),
	RSH(">>"),
	EQ("=="),
	SHEQ("==="),
	NE("!="),
	NE2("<>"),
	SHNE("!=="),
	LT("<"),
	GT(">"),
	LE("<="),
	GE(">="),
	AND("and"),
	OR("or"),
	XOR("xor"),
	NOT("!"),
	AND2("&&"),
	OR2("||"),
	CONCAT("."),
	SPACESHIP("<=>"); // php7

	@Getter
	private String token;

	Operator(String token) {
		this.token = token;
	}

	public static Operator get(String token) {
		for (Operator operator : values()) {
			if (operator.getToken().equals(token)) {
				return operator;
			}
		}
		if ("+=".equals(token)) {
			return ADD;
		} else if ("-=".equals(token)) {
			return SUB;
		} else if ("*=".equals(token)) {
			return MUL;
		} else if ("/=".equals(token)) {
			return DIV;
		} else if ("%=".equals(token)) {
			return MOD;
		} else if (".=".equals(token)) {
			return CONCAT;
		} else if ("&=".equals(token)) {
			return BITAND;
		} else if ("|=".equals(token)) {
			return BITOR;
		} else if ("^=".equals(token)) {
			return BITXOR;
		} else if ("<<=".equals(token)) {
			return LSH;
		} else if (">>=".equals(token)) {
			return RSH;
		} else if ("**=".equals(token)) {
			return POW;
		}
		throw new IllegalArgumentException();
	}

	public String toString() {
		return token;
	}

}
