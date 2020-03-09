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

import java.io.IOException;
import java.io.Reader;

import java_cup.runtime.Scanner;
import lombok.Getter;
import net.katagaitai.phpscan.ast.PHPVersion;
import net.katagaitai.phpscan.ast.PhpAstLexer;
import net.katagaitai.phpscan.ast.PhpAstParser;

/**
 * Umbrella owner and abstract syntax tree node factory. An <code>AST</code>
 * instance serves as the common owner of any number of AST nodes, and as the
 * factory for creating new AST nodes owned by that instance.
 * <p>
 * Abstract syntax trees may be hand constructed by clients, using the
 * <code>new<i>TYPE</i></code> factory methods to create new nodes, and the
 * various <code>set<i>CHILD</i></code> methods (see
 * {@link net.katagaitai.phpscan.ast.nodes.ASTNode} and its subclasses)
 * to connect them together.
 * </p>
 * <p>
 * Each AST node belongs to a unique AST instance, called the owning AST. The
 * children of an AST node always have the same owner as their parent node. If a
 * node from one AST is to be added to a different AST, the subtree must be
 * cloned first to ensures that the added nodes have the correct owning AST.
 * </p>
 * <p>
 * There can be any number of AST nodes owned by a single AST instance that are
 * unparented. Each of these nodes is the root of a separate little tree of
 * nodes. The method <code>ASTNode.getProgramRoot()</code> navigates from any
 * node to the root of the tree that it is contained in. Ordinarily, an AST
 * instance has one main tree (rooted at a <code>Program</code>), with
 * newly-created nodes appearing as additional roots until they are parented
 * somewhere under the main tree. One can navigate from any node to its AST
 * instance, but not conversely.
 * </p>
 * <p>
 * The class {@link ASTParser} parses a string containing a PHP source code and
 * returns an abstract syntax tree for it. The resulting nodes carry source
 * ranges relating the node back to the original source characters.
 * </p>
 * <p>
 * Programs created by <code>ASTParser</code> from a source document can be
 * serialized after arbitrary modifications with minimal loss of original
 * formatting. Here is an example:
 *
 * <pre>
 *
 * Document doc = new Document("<?\n class X {} \n echo 'hello world';\n  ?>");
 * ASTParser parser = ASTParser.newParser(AST.PHP5);
 * parser.setSource(doc.get().toCharArray());
 * Program program = parser.createAST(null);
 * program.recordModifications();
 * AST ast = program.getAST();
 * EchoStatement echo = ast.newEchoStatement();
 * echo.setExpression(ast.newScalar("hello world");
 * program.statements().add(echo);
 * TextEdit edits = program.rewrite(document, null);
 * UndoEdit undo = edits.apply(document);
 *
 * </pre>
 *
 * See also {@link ASTRewrite} for an alternative way to describe and serialize
 * changes to a read-only AST.
 * </p>
 * <p>
 * Clients may create instances of this class using {@link #newAST(int)}, but
 * this class is not intended to be subclassed.
 * </p>
 *
 * @see ASTParser
 * @see ASTNode
 * @since 2.0
 */
public class AST {

	/**
	 * The scanner capabilities to the AST - all has package access to enable
	 * ASTParser access
	 */
	@Getter
	final PhpAstLexer lexer;
	@Getter
	final PhpAstParser parser;
	@Getter
	final PHPVersion apiLevel;
	final boolean useASPTags;

	private int defaultNodeFlag = 0;

	public AST(Reader reader, PHPVersion apiLevel, boolean aspTagsAsPhp, boolean useShortTags) throws IOException {
		this.useASPTags = aspTagsAsPhp;
		this.apiLevel = apiLevel;
		this.lexer = getLexerInstance(reader, apiLevel, aspTagsAsPhp, useShortTags);
		this.parser = getParserInstance(apiLevel, this.lexer);
	}

	/**
	 * Constructs a scanner from a given reader
	 *
	 * @param reader
	 * @param phpVersion
	 * @param aspTagsAsPhp
	 * @return
	 * @throws IOException
	 */
	private PhpAstLexer getLexerInstance(Reader reader, PHPVersion phpVersion, boolean aspTagsAsPhp,
			boolean useShortTags)
			throws IOException {
		if (PHPVersion.PHP5_6 == phpVersion) {
			final PhpAstLexer lexer56 = getLexer56(reader);
			lexer56.setUseAspTagsAsPhp(aspTagsAsPhp);
			lexer56.setUseShortTags(useShortTags);
			return lexer56;
		} else {
			throw new IllegalArgumentException();
		}
	}

	private PhpAstLexer getLexer56(Reader reader) throws IOException {
		final PhpAstLexer phpPhpAstLexer5 = new PhpAstLexer(reader);
		phpPhpAstLexer5.setAST(this);
		return phpPhpAstLexer5;
	}

	private PhpAstParser getParserInstance(PHPVersion phpVersion, Scanner lexer) {
		if (PHPVersion.PHP5_6 == phpVersion) {
			final PhpAstParser parser = new PhpAstParser(lexer);
			parser.setAST(this);
			return parser;
		} else {
			throw new IllegalArgumentException();
		}
	}

	int getDefaultNodeFlag() {
		return this.defaultNodeFlag;
	}
}
