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
package net.katagaitai.phpscan.ast;

import net.katagaitai.phpscan.ast.nodes.ASTError;
import net.katagaitai.phpscan.ast.nodes.ASTNode;
import net.katagaitai.phpscan.ast.nodes.AnonymousClassDeclaration;
import net.katagaitai.phpscan.ast.nodes.ArrayAccess;
import net.katagaitai.phpscan.ast.nodes.ArrayCreation;
import net.katagaitai.phpscan.ast.nodes.ArrayElement;
import net.katagaitai.phpscan.ast.nodes.Assignment;
import net.katagaitai.phpscan.ast.nodes.BackTickExpression;
import net.katagaitai.phpscan.ast.nodes.Block;
import net.katagaitai.phpscan.ast.nodes.BreakStatement;
import net.katagaitai.phpscan.ast.nodes.CastExpression;
import net.katagaitai.phpscan.ast.nodes.CatchClause;
import net.katagaitai.phpscan.ast.nodes.ClassDeclaration;
import net.katagaitai.phpscan.ast.nodes.ClassInstanceCreation;
import net.katagaitai.phpscan.ast.nodes.ClassName;
import net.katagaitai.phpscan.ast.nodes.CloneExpression;
import net.katagaitai.phpscan.ast.nodes.Comment;
import net.katagaitai.phpscan.ast.nodes.ConditionalExpression;
import net.katagaitai.phpscan.ast.nodes.ConstantDeclaration;
import net.katagaitai.phpscan.ast.nodes.ContinueStatement;
import net.katagaitai.phpscan.ast.nodes.DeclareStatement;
import net.katagaitai.phpscan.ast.nodes.DoStatement;
import net.katagaitai.phpscan.ast.nodes.EchoStatement;
import net.katagaitai.phpscan.ast.nodes.EmptyStatement;
import net.katagaitai.phpscan.ast.nodes.ExpressionStatement;
import net.katagaitai.phpscan.ast.nodes.FieldAccess;
import net.katagaitai.phpscan.ast.nodes.FieldsDeclaration;
import net.katagaitai.phpscan.ast.nodes.FinallyClause;
import net.katagaitai.phpscan.ast.nodes.ForEachStatement;
import net.katagaitai.phpscan.ast.nodes.ForStatement;
import net.katagaitai.phpscan.ast.nodes.FormalParameter;
import net.katagaitai.phpscan.ast.nodes.FullyQualifiedTraitMethodReference;
import net.katagaitai.phpscan.ast.nodes.FunctionDeclaration;
import net.katagaitai.phpscan.ast.nodes.FunctionInvocation;
import net.katagaitai.phpscan.ast.nodes.FunctionName;
import net.katagaitai.phpscan.ast.nodes.GlobalStatement;
import net.katagaitai.phpscan.ast.nodes.GotoLabel;
import net.katagaitai.phpscan.ast.nodes.GotoStatement;
import net.katagaitai.phpscan.ast.nodes.Identifier;
import net.katagaitai.phpscan.ast.nodes.IfStatement;
import net.katagaitai.phpscan.ast.nodes.IgnoreError;
import net.katagaitai.phpscan.ast.nodes.InLineHtml;
import net.katagaitai.phpscan.ast.nodes.Include;
import net.katagaitai.phpscan.ast.nodes.InfixExpression;
import net.katagaitai.phpscan.ast.nodes.InstanceOfExpression;
import net.katagaitai.phpscan.ast.nodes.InterfaceDeclaration;
import net.katagaitai.phpscan.ast.nodes.LambdaFunctionDeclaration;
import net.katagaitai.phpscan.ast.nodes.ListVariable;
import net.katagaitai.phpscan.ast.nodes.MethodDeclaration;
import net.katagaitai.phpscan.ast.nodes.MethodInvocation;
import net.katagaitai.phpscan.ast.nodes.NamespaceDeclaration;
import net.katagaitai.phpscan.ast.nodes.NamespaceName;
import net.katagaitai.phpscan.ast.nodes.ParenthesisExpression;
import net.katagaitai.phpscan.ast.nodes.PostfixExpression;
import net.katagaitai.phpscan.ast.nodes.PrefixExpression;
import net.katagaitai.phpscan.ast.nodes.Program;
import net.katagaitai.phpscan.ast.nodes.Quote;
import net.katagaitai.phpscan.ast.nodes.Reference;
import net.katagaitai.phpscan.ast.nodes.ReflectionVariable;
import net.katagaitai.phpscan.ast.nodes.ReturnStatement;
import net.katagaitai.phpscan.ast.nodes.Scalar;
import net.katagaitai.phpscan.ast.nodes.SingleFieldDeclaration;
import net.katagaitai.phpscan.ast.nodes.StaticConstantAccess;
import net.katagaitai.phpscan.ast.nodes.StaticFieldAccess;
import net.katagaitai.phpscan.ast.nodes.StaticMethodInvocation;
import net.katagaitai.phpscan.ast.nodes.StaticStatement;
import net.katagaitai.phpscan.ast.nodes.SwitchCase;
import net.katagaitai.phpscan.ast.nodes.SwitchStatement;
import net.katagaitai.phpscan.ast.nodes.ThrowStatement;
import net.katagaitai.phpscan.ast.nodes.TraitAlias;
import net.katagaitai.phpscan.ast.nodes.TraitAliasStatement;
import net.katagaitai.phpscan.ast.nodes.TraitDeclaration;
import net.katagaitai.phpscan.ast.nodes.TraitPrecedence;
import net.katagaitai.phpscan.ast.nodes.TraitPrecedenceStatement;
import net.katagaitai.phpscan.ast.nodes.TraitUseStatement;
import net.katagaitai.phpscan.ast.nodes.TryStatement;
import net.katagaitai.phpscan.ast.nodes.UnaryOperation;
import net.katagaitai.phpscan.ast.nodes.UseStatement;
import net.katagaitai.phpscan.ast.nodes.UseStatementPart;
import net.katagaitai.phpscan.ast.nodes.Variable;
import net.katagaitai.phpscan.ast.nodes.WhileStatement;
import net.katagaitai.phpscan.ast.nodes.YieldExpression;

/**
 * A visitor for abstract syntax trees.
 * <p>
 * For each different concrete AST node type there is a method that visits the
 * given node to perform some arbitrary operation.
 * <p>
 * Subclasses may implement this method as needed.
 * <p>
 *
 * @author Moshe S., Roy G. ,2007
 */
public interface Visitor {
	Object visit(AnonymousClassDeclaration anonymousClassDeclaration);

	Object visit(ArrayAccess arrayAccess);

	Object visit(ArrayCreation arrayCreation);

	Object visit(ArrayElement arrayElement);

	Object visit(Assignment assignment);

	Object visit(ASTError astError);

	Object visit(BackTickExpression backTickExpression);

	Object visit(Block block);

	Object visit(BreakStatement breakStatement);

	Object visit(CastExpression castExpression);

	Object visit(CatchClause catchClause);

	Object visit(ConstantDeclaration classConstantDeclaration);

	Object visit(ClassDeclaration classDeclaration);

	Object visit(ClassInstanceCreation classInstanceCreation);

	Object visit(ClassName className);

	Object visit(CloneExpression cloneExpression);

	Object visit(Comment comment);

	Object visit(ConditionalExpression conditionalExpression);

	Object visit(ContinueStatement continueStatement);

	Object visit(DeclareStatement declareStatement);

	Object visit(DoStatement doStatement);

	Object visit(EchoStatement echoStatement);

	Object visit(EmptyStatement emptyStatement);

	Object visit(ExpressionStatement expressionStatement);

	Object visit(FieldAccess fieldAccess);

	Object visit(FieldsDeclaration fieldsDeclaration);

	Object visit(ForEachStatement forEachStatement);

	Object visit(FormalParameter formalParameter);

	Object visit(ForStatement forStatement);

	Object visit(FunctionDeclaration functionDeclaration);

	Object visit(FunctionInvocation functionInvocation);

	Object visit(FunctionName functionName);

	Object visit(GlobalStatement globalStatement);

	Object visit(GotoLabel gotoLabel);

	Object visit(GotoStatement gotoStatement);

	Object visit(Identifier identifier);

	Object visit(IfStatement ifStatement);

	Object visit(IgnoreError ignoreError);

	Object visit(Include include);

	Object visit(InfixExpression infixExpression);

	Object visit(InLineHtml inLineHtml);

	Object visit(InstanceOfExpression instanceOfExpression);

	Object visit(InterfaceDeclaration interfaceDeclaration);

	Object visit(LambdaFunctionDeclaration lambdaFunctionDeclaration);

	Object visit(ListVariable listVariable);

	Object visit(MethodDeclaration methodDeclaration);

	Object visit(MethodInvocation methodInvocation);

	Object visit(NamespaceName namespaceName);

	Object visit(NamespaceDeclaration namespaceDeclaration);

	Object visit(ParenthesisExpression parenthesisExpression);

	Object visit(PostfixExpression postfixExpression);

	Object visit(PrefixExpression prefixExpression);

	Object visit(Program program);

	Object visit(Quote quote);

	Object visit(Reference reference);

	Object visit(ReflectionVariable reflectionVariable);

	Object visit(ReturnStatement returnStatement);

	Object visit(Scalar scalar);

	Object visit(SingleFieldDeclaration singleFieldDeclaration);

	Object visit(StaticConstantAccess classConstantAccess);

	Object visit(StaticFieldAccess staticFieldAccess);

	Object visit(StaticMethodInvocation staticMethodInvocation);

	Object visit(StaticStatement staticStatement);

	Object visit(SwitchCase switchCase);

	Object visit(SwitchStatement switchStatement);

	Object visit(ThrowStatement throwStatement);

	Object visit(TryStatement tryStatement);

	Object visit(UnaryOperation unaryOperation);

	Object visit(Variable variable);

	Object visit(UseStatement useStatement);

	Object visit(UseStatementPart useStatementPart);

	Object visit(WhileStatement whileStatement);

	Object visit(ASTNode node);

	// php5.4 starts

	Object visit(FullyQualifiedTraitMethodReference node);

	Object visit(TraitAlias node);

	Object visit(TraitAliasStatement node);

	Object visit(TraitDeclaration node);

	Object visit(TraitPrecedence node);

	Object visit(TraitPrecedenceStatement node);

	Object visit(TraitUseStatement node);

	// php5.4 ends

	// php5.5
	Object visit(YieldExpression yieldExpression);

	Object visit(FinallyClause finallyClause);

}
