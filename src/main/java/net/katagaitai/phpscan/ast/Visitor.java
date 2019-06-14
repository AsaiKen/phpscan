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

import net.katagaitai.phpscan.ast.nodes.*;

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
