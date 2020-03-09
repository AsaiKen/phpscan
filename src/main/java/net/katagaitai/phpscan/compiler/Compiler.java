package net.katagaitai.phpscan.compiler;

import java.io.File;
import java.io.FileReader;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.katagaitai.phpscan.ast.PHPFlags;
import net.katagaitai.phpscan.ast.PHPVersion;
import net.katagaitai.phpscan.ast.PhpAstParser;
import net.katagaitai.phpscan.ast.Visitor;
import net.katagaitai.phpscan.ast.nodes.AST;
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
import net.katagaitai.phpscan.ast.nodes.Expression;
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
import net.katagaitai.phpscan.ast.nodes.Statement;
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
import net.katagaitai.phpscan.ast.nodes.VariableBase;
import net.katagaitai.phpscan.ast.nodes.WhileStatement;
import net.katagaitai.phpscan.ast.nodes.YieldExpression;
import net.katagaitai.phpscan.command.AddToReturnValue;
import net.katagaitai.phpscan.command.Assign;
import net.katagaitai.phpscan.command.AssignArgument;
import net.katagaitai.phpscan.command.AssignArgumentReference;
import net.katagaitai.phpscan.command.AssignReference;
import net.katagaitai.phpscan.command.AssignStatic;
import net.katagaitai.phpscan.command.CallFunction;
import net.katagaitai.phpscan.command.CallMethod;
import net.katagaitai.phpscan.command.CallStaticMethod;
import net.katagaitai.phpscan.command.Cast;
import net.katagaitai.phpscan.command.CastType;
import net.katagaitai.phpscan.command.Clone;
import net.katagaitai.phpscan.command.Command;
import net.katagaitai.phpscan.command.CommentSource;
import net.katagaitai.phpscan.command.CreateArray;
import net.katagaitai.phpscan.command.CreateClass;
import net.katagaitai.phpscan.command.CreateFunction;
import net.katagaitai.phpscan.command.CreateMethod;
import net.katagaitai.phpscan.command.CreateStaticMethod;
import net.katagaitai.phpscan.command.Echo;
import net.katagaitai.phpscan.command.Else_;
import net.katagaitai.phpscan.command.EndLoop;
import net.katagaitai.phpscan.command.EndNamespace;
import net.katagaitai.phpscan.command.GetArrayValue;
import net.katagaitai.phpscan.command.GetClassConstant;
import net.katagaitai.phpscan.command.GetConstant;
import net.katagaitai.phpscan.command.GetFieldValue;
import net.katagaitai.phpscan.command.GetStaticFieldValue;
import net.katagaitai.phpscan.command.GetVariable;
import net.katagaitai.phpscan.command.IfElseEnd;
import net.katagaitai.phpscan.command.If_;
import net.katagaitai.phpscan.command.Instanceof;
import net.katagaitai.phpscan.command.LoadGlobal;
import net.katagaitai.phpscan.command.Mix;
import net.katagaitai.phpscan.command.New;
import net.katagaitai.phpscan.command.Operator;
import net.katagaitai.phpscan.command.PutArrayValue;
import net.katagaitai.phpscan.command.PutConstant;
import net.katagaitai.phpscan.command.PutFieldValue;
import net.katagaitai.phpscan.command.PutStaticFieldValue;
import net.katagaitai.phpscan.command.PutVariable;
import net.katagaitai.phpscan.command.StartLoop;
import net.katagaitai.phpscan.command.StartNamespace;
import net.katagaitai.phpscan.command.Use;
import net.katagaitai.phpscan.util.Constants;
import net.katagaitai.phpscan.util.SymbolUtils;

import com.google.common.collect.Lists;

@RequiredArgsConstructor
@Log4j2
public class Compiler implements Visitor {
	private final File file;

	private PhpFunction currentFunction;

	enum State {
		STATIC
	}

	private State state;

	@Getter
	private Program program;

	// TODO デッドコードを除去していない問題

	public PhpFunction compile() throws Exception {
		log.info("コンパイル開始：" + file.getAbsolutePath());
		if (file.exists() && file.isFile()) {
			// OK
		} else {
			log.error("ファイルが存在しません：" + file.getAbsolutePath());
			return null;
		}
		FileReader reader = new FileReader(file);
		AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
		PhpAstParser parser = ast.getParser();
		try {
			parser.parse();
		} catch (Exception e) {
			log.error("", e);
		}
		program = parser.getProgram();
		if (program == null) {
			throw new RuntimeException("プログラムがありません。:" + file.getAbsolutePath());
		}
		log.trace(program.toString());
		PhpFunction mainFunction = new PhpFunction(
				Constants.MAIN_FUNCTION_NAME, Lists.newArrayList(), false, file);
		currentFunction = mainFunction;
		visit(program);
		log.trace(mainFunction.toString());
		log.info("コンパイル終了：" + file.getAbsolutePath());
		return mainFunction;
	}

	private void addCommand(Command command) {
		if (state == State.STATIC) {
			currentFunction.addStaticCommand(command);
		} else {
			currentFunction.addCommand(command);
		}
	}

	private void addCommentSource(ASTNode node) {
		addCommand(new CommentSource(file, node.getStart(), node.getLength()));
	}

	@Override
	public Object visit(AnonymousClassDeclaration anonymousClassDeclaration) {
		log.warn("5.6では非対応");
		return null;
	}

	@Override
	public Object visit(ArrayAccess arrayAccess) {
		Expression name = arrayAccess.getName();
		String nameString = (String) name.accept(this);
		Expression index = arrayAccess.getIndex();
		String resultString = SymbolUtils.createSymbolName();
		String indexString;
		if (index == null) {
			log.warn("キーが不明：" + arrayAccess + " " + file);
			indexString = Constants.PREFIX_INTEGER_LITERAL + "0";
		} else {
			indexString = (String) index.accept(this);
		}
		addCommand(new GetArrayValue(resultString, nameString, indexString));
		return resultString;
	}

	@Override
	public Object visit(ArrayCreation arrayCreation) {
		String arrayString = SymbolUtils.createSymbolName();
		addCommand(new CreateArray(arrayString));
		List<ArrayElement> elements = arrayCreation.getElements();
		int i = 0;
		for (ArrayElement element : elements) {
			String keyString;
			if (element.getKey() == null) {
				keyString = Constants.PREFIX_INTEGER_LITERAL + i++;
			} else {
				keyString = (String) element.getKey().accept(this);
			}
			String valueString = (String) element.getValue().accept(this);
			addCommand(new PutArrayValue(arrayString, keyString, valueString));
		}
		return arrayString;
	}

	@Override
	public Object visit(ArrayElement arrayElement) {
		throw new RuntimeException();
	}

	@Override
	public Object visit(Assignment assignment) {
		VariableBase left = assignment.getLeftHandSide();
		Expression right = assignment.getRightHandSide();
		int op = assignment.getOperator();
		String rightString;
		boolean assginReference = false;
		if (right instanceof Reference) {
			Reference reference = (Reference) right;
			Expression expression = reference.getExpression();
			rightString = (String) expression.accept(this);
			assginReference = true;
		} else {
			rightString = (String) right.accept(this);
		}
		assign(left, op, rightString, assginReference);
		return rightString;
	}

	private void assign(VariableBase left, int op, String rightString, boolean assignReference) {
		if (left instanceof Variable) {
			if (left instanceof ArrayAccess) {
				// $a[] = xxx
				ArrayAccess arrayAccess = (ArrayAccess) left;
				Expression name = arrayAccess.getName();
				String nameString = (String) name.accept(this);
				Expression index = arrayAccess.getIndex();
				if (op != Assignment.OP_EQUAL) {
					String indexString;
					if (index == null) {
						// array[] += 1;
						log.warn("キーが不明：" + arrayAccess + " " + file);
						indexString = Constants.PREFIX_INTEGER_LITERAL + "0";
					} else {
						indexString = (String) index.accept(this);
					}
					String tmpString = SymbolUtils.createSymbolName();
					addCommand(new GetArrayValue(tmpString, nameString,
							indexString));
					String tmpString2 = SymbolUtils.createSymbolName();
					addCommand(new Mix(tmpString2, tmpString,
							Operator.get(Assignment.getOperator(op)),
							rightString));
					rightString = tmpString2;
				}
				String indexString = null;
				if (index != null) {
					indexString = (String) index.accept(this);
				}
				// indexStringがnullのケースはInterpreter側で対応する
				addCommand(new PutArrayValue(nameString, indexString,
						rightString));
			} else if (left instanceof ReflectionVariable) {
				// $$a = xxx
				ReflectionVariable reflectionVariable = (ReflectionVariable) left;
				Expression name = reflectionVariable.getName();
				String nameString = (String) name.accept(this);
				if (op != Assignment.OP_EQUAL) {
					String tmpString2 = SymbolUtils.createSymbolName();
					addCommand(new GetVariable(tmpString2, nameString));
					String tmpString3 = SymbolUtils.createSymbolName();
					addCommand(new Mix(tmpString3, tmpString2,
							Operator.get(Assignment.getOperator(op)),
							rightString));
					rightString = tmpString3;
				}
				addCommand(new PutVariable(nameString, rightString));
			} else {
				// $a = xxx
				// $$a = xxx
				// ${'a'} = xxx
				Variable variable = (Variable) left;
				String nameString = (String) variable.accept(this);
				if (op != Assignment.OP_EQUAL) {
					String tmpString2 = SymbolUtils.createSymbolName();
					addCommand(new Mix(tmpString2, nameString,
							Operator.get(Assignment.getOperator(op)),
							rightString));
					rightString = tmpString2;
				}
				if (state == State.STATIC) {
					addCommand(new AssignStatic(nameString, rightString));
				} else {
					if (assignReference) {
						addCommand(new AssignReference(nameString, rightString));
					} else {
						addCommand(new Assign(nameString, rightString));
					}
				}
			}
		} else if (left instanceof FieldAccess) {
			// $a->b = xxx
			FieldAccess fieldAccess = (FieldAccess) left;
			String dispatcherString = (String) fieldAccess.getDispatcher()
					.accept(this);
			String fieldString = (String) fieldAccess.getField().accept(this);
			if (op != Assignment.OP_EQUAL) {
				String tmpString = SymbolUtils.createSymbolName();
				addCommand(new GetFieldValue(tmpString, dispatcherString,
						fieldString));
				String tmpString2 = SymbolUtils.createSymbolName();
				addCommand(new Mix(tmpString2, tmpString,
						Operator.get(Assignment.getOperator(op)), rightString));
				rightString = tmpString2;
			}
			addCommand(new PutFieldValue(dispatcherString, fieldString,
					rightString));
		} else if (left instanceof StaticFieldAccess) {
			// A::$b = xxx
			StaticFieldAccess staticFieldAccess = (StaticFieldAccess) left;
			Expression className = staticFieldAccess.getClassName();
			String classNameString = (String) className.accept(this);
			Variable field = staticFieldAccess.getField();
			if (field instanceof ArrayAccess) {
				ArrayAccess arrayAccess = (ArrayAccess) field;
				Expression field2 = arrayAccess.getName();
				String nameString = (String) field2.accept(this);
				if (field2 instanceof ReflectionVariable) {
					// そのまま
				} else {
					nameString = nameString.substring(1);
				}
				Expression index = arrayAccess.getIndex();
				String indexString = null;
				if (index != null) {
					indexString = (String) index.accept(this);
				}
				String tmpString = SymbolUtils.createSymbolName();
				addCommand(new GetStaticFieldValue(tmpString, classNameString,
						nameString));
				addCommand(new PutArrayValue(tmpString, indexString,
						rightString));
			} else {
				String nameString = (String) field.accept(this);
				if (field instanceof ReflectionVariable) {
					// そのまま
				} else {
					nameString = nameString.substring(1);
				}
				if (op != Assignment.OP_EQUAL) {
					String tmpString = SymbolUtils.createSymbolName();
					addCommand(new GetStaticFieldValue(tmpString,
							classNameString, nameString));
					String tmpString2 = SymbolUtils.createSymbolName();
					addCommand(new Mix(tmpString2, tmpString,
							Operator.get(Assignment.getOperator(op)),
							rightString));
					rightString = tmpString2;
				}
				addCommand(new PutStaticFieldValue(classNameString, nameString,
						rightString));
			}
		} else if (left instanceof ListVariable) {
			// list(, $a, $b[], $c->d, ...) = xxx
			ListVariable listVariable = (ListVariable) left;
			List<VariableBase> variables = listVariable.getVariables();
			for (int i = 0; i < variables.size(); i++) {
				VariableBase variableBase = variables.get(i);
				assignListElement(variableBase, rightString, i);
			}
		} else {
			log.warn("不明な代入：" + left + " " + file);
		}
	}

	private void assignListElement(VariableBase variableBase,
			String rightString, int i) {
		String valueString = SymbolUtils.createSymbolName();
		addCommand(new GetArrayValue(valueString, rightString,
				Constants.PREFIX_INTEGER_LITERAL + Integer.toString(i)));
		assign(variableBase, Assignment.OP_EQUAL, valueString, false);
	}

	@Override
	public Object visit(ASTError astError) {
		log.warn("パースエラー：" + astError + " " + file);
		return null;
	}

	@Override
	public Object visit(BackTickExpression backTickExpression) {
		// http://php.net/manual/ja/language.operators.execution.php
		// バッククォート演算子の使用は shell_exec() と等価
		String bodyString = SymbolUtils.createSymbolName();
		addCommand(new Assign(bodyString, Constants.PREFIX_STRING_LITERAL + ""));
		for (Expression expression : backTickExpression.getExpressions()) {
			String string = (String) expression.accept(this);
			String tmpString = SymbolUtils.createSymbolName();
			addCommand(new Mix(tmpString, bodyString, Operator.CONCAT, string));
			addCommand(new Assign(bodyString, tmpString));
		}
		String resultString = SymbolUtils.createSymbolName();
		addCommand(new CallFunction(resultString, "shell_exec",
				Lists.newArrayList(bodyString)));
		return resultString;
	}

	@Override
	public Object visit(Block block) {
		// スコープは関数単位なので、ここでは切り替えない
		for (Statement statement : block.getStatements()) {
			statement.accept(this);
		}
		return null;
	}

	@Override
	public Object visit(BreakStatement breakStatement) {
		addCommentSource(breakStatement);

		return null;
	}

	@Override
	public Object visit(CastExpression castExpression) {
		int castingType = castExpression.getCastingType();
		String castingTypeString = CastExpression.getCastType(castingType);
		CastType type = CastType.valueOf(castingTypeString.toUpperCase());
		Expression expression = castExpression.getExpression();
		String expressionString = (String) expression.accept(this);
		String resultString = SymbolUtils.createSymbolName();
		addCommand(new Cast(resultString, type, expressionString));
		return resultString;
	}

	@Override
	public Object visit(CatchClause catchClause) {
		addCommentSource(catchClause);

		Expression className = catchClause.getClassName();
		String classNameString = (String) className.accept(this);
		Variable variable = catchClause.getVariable();
		String variableString = (String) variable.accept(this);
		addCommand(new New(variableString, classNameString,
				Lists.newArrayList()));
		Block body = catchClause.getBody();
		body.accept(this);
		return null;
	}

	@Override
	public Object visit(ConstantDeclaration classConstantDeclaration) {
		// http://php.net/manual/ja/language.constants.syntax.php
		// 定数は、定義することができ、変数のスコープ規則に関係なく、あらゆる場所からアクセス可能です。
		State savedState = state;
		state = State.STATIC;

		addCommentSource(classConstantDeclaration);

		List<Identifier> names = classConstantDeclaration.getNames();
		List<Expression> initializers = classConstantDeclaration
				.getInitializers();
		if (names.size() == initializers.size()) {
			for (int i = 0; i < names.size(); i++) {
				Identifier name = names.get(i);
				String nameString = (String) name.accept(this);
				Expression initializer = initializers.get(i);
				String initializerString = (String) initializer.accept(this);
				addCommand(new PutConstant(nameString, initializerString));
			}
		} else {
			throw new RuntimeException();
		}

		state = savedState;
		return null;
	}

	@Override
	public Object visit(ClassDeclaration classDeclaration) {
		Expression superClass = classDeclaration.getSuperClass();
		String superClassString = null;
		if (superClass != null) {
			superClassString = (String) superClass.accept(this);
		}
		List<Identifier> identifiers = classDeclaration.getInterfaces();
		List<String> interfaceList = Lists.newArrayList();
		for (Identifier identifier : identifiers) {
			interfaceList.add(identifier.getName());
		}
		Identifier name = classDeclaration.getName();
		String nameString = name.getName();
		PhpFunction phpFunction = new PhpFunction(Constants.PREFIX
				+ "initializer", Lists.newArrayList(), false, file);
		PhpClass phpClass = new PhpClass(nameString, superClassString,
				interfaceList, phpFunction, file);
		addCommand(new CreateClass(phpClass));

		PhpFunction savedCurrentFunction = currentFunction;
		currentFunction = phpFunction;

		Block body = classDeclaration.getBody();
		body.accept(this);

		currentFunction = savedCurrentFunction;

		return null;
	}

	@Override
	public Object visit(ClassInstanceCreation classInstanceCreation) {
		ClassName className = classInstanceCreation.getClassName();
		String classNameString = (String) className.getName().accept(this);
		List<Expression> ctorParams = classInstanceCreation.getCtorParams();
		List<String> list = Lists.newArrayList();
		for (Expression ctorParam : ctorParams) {
			String element = (String) ctorParam.accept(this);
			list.add(element);
		}
		String resultString = SymbolUtils.createSymbolName();
		addCommand(new New(resultString, classNameString, list));
		return resultString;
	}

	@Override
	public Object visit(ClassName className) {
		Expression name = className.getName();
		return name.accept(this);
	}

	@Override
	public Object visit(CloneExpression cloneExpression) {
		String resultString = SymbolUtils.createSymbolName();
		Expression expression = cloneExpression.getExpression();
		String expressionString = (String) expression.accept(this);
		addCommand(new Clone(resultString, expressionString));
		return resultString;
	}

	@Override
	public Object visit(Comment comment) {
		return null;
	}

	@Override
	public Object visit(ConditionalExpression conditionalExpression) {
		String resultString = SymbolUtils.createSymbolNameForConditionalExpression();

		Expression condition = conditionalExpression.getCondition();
		String conditionString = (String) condition.accept(this);

		String label = SymbolUtils.createLabelName();
		addCommand(new If_(conditionString, label));
		Expression ifTrue = conditionalExpression.getIfTrue();
		if (ifTrue != null) {
			String ifTrueString = (String) ifTrue.accept(this);
			addCommand(new Assign(resultString, ifTrueString));
		}
		addCommand(new Else_(label));
		Expression ifFalse = conditionalExpression.getIfFalse();
		if (ifFalse != null) {
			String ifFalseString = (String) ifFalse.accept(this);
			addCommand(new Assign(resultString, ifFalseString));
		}
		addCommand(new IfElseEnd(label));

		return resultString;
	}

	@Override
	public Object visit(ContinueStatement continueStatement) {
		addCommentSource(continueStatement);
		return null;
	}

	@Override
	public Object visit(DeclareStatement declareStatement) {
		addCommentSource(declareStatement);
		Statement body = declareStatement.getBody();
		body.accept(this);
		return null;
	}

	@Override
	public Object visit(DoStatement doStatement) {
		addCommentSource(doStatement);
		Statement body = doStatement.getBody();
		addCommand(new StartLoop());
		body.accept(this);
		addCommand(new EndLoop());
		Expression condition = doStatement.getCondition();
		condition.accept(this);
		return null;
	}

	@Override
	public Object visit(EchoStatement echoStatement) {
		addCommentSource(echoStatement);
		String argString = SymbolUtils.createSymbolName();
		List<Expression> expressions = echoStatement.getExpressions();
		if (expressions.size() == 1) {
			addCommand(new Assign(argString, (String) expressions.get(0)
					.accept(this)));
		} else {
			addCommand(new Assign(argString, Constants.PREFIX_STRING_LITERAL
					+ ""));
			for (Expression expression : expressions) {
				String expressionString = (String) expression.accept(this);
				String tmpString = SymbolUtils.createSymbolName();
				addCommand(new Mix(tmpString, argString, Operator.CONCAT,
						expressionString));
				addCommand(new Assign(argString, tmpString));
			}
		}
		addCommand(new Echo(argString));
		return null;
	}

	@Override
	public Object visit(EmptyStatement emptyStatement) {
		addCommentSource(emptyStatement);
		return null;
	}

	@Override
	public Object visit(ExpressionStatement expressionStatement) {
		addCommentSource(expressionStatement);
		Expression expression = expressionStatement.getExpression();
		String expressionString = (String) expression.accept(this);
		return expressionString;
	}

	@Override
	public Object visit(FieldAccess fieldAccess) {
		String resultString = SymbolUtils.createSymbolName();
		VariableBase dispatcher = fieldAccess.getDispatcher();
		String dispatcherString = (String) dispatcher.accept(this);
		Variable field = fieldAccess.getField();
		String fieldString = (String) field.accept(this);
		addCommand(new GetFieldValue(resultString, dispatcherString,
				fieldString));
		return resultString;
	}

	@Override
	public Object visit(FieldsDeclaration fieldsDeclaration) {
		// public static $a = 1、private $a = 1、private $a;
		addCommentSource(fieldsDeclaration);

		List<SingleFieldDeclaration> fields = fieldsDeclaration.getFields();
		int modifier = fieldsDeclaration.getModifier();
		State savedState = state;
		if (PHPFlags.isStatic(modifier)) {
			state = State.STATIC;
		}
		for (SingleFieldDeclaration singleFieldDeclaration : fields) {
			Variable name = singleFieldDeclaration.getName();
			String nameString = (String) name.accept(this);
			if (nameString.startsWith("$")) {
				// $a
				nameString = nameString.substring(1);
			}
			Expression value = singleFieldDeclaration.getValue();
			String valueString;
			if (value == null) {
				valueString = Constants.NULL_LITERAL;
			} else {
				valueString = (String) value.accept(this);
				if (valueString == null) {
					valueString = Constants.NULL_LITERAL;
				}
			}
			if (state == State.STATIC) {
				String resultString = SymbolUtils.createSymbolName();
				addCommand(new GetConstant(resultString, "__CLASS__"));
				addCommand(new PutStaticFieldValue(resultString, nameString,
						valueString));
			} else {
				addCommand(new PutFieldValue("$this", nameString, valueString));
			}
		}
		state = savedState;
		return null;
	}

	@Override
	public Object visit(ForEachStatement forEachStatement) {
		addCommentSource(forEachStatement);
		Expression expression = forEachStatement.getExpression();
		Expression key = forEachStatement.getKey();
		Expression value = forEachStatement.getValue();
		String expressionString = (String) expression.accept(this);
		String keyString;
		if (key == null) {
			keyString = SymbolUtils.createSymbolName();
		} else {
			keyString = (String) key.accept(this);
		}
		String valueString;
		if (value == null) {
			valueString = SymbolUtils.createSymbolName();
		} else {
			valueString = (String) value.accept(this);
		}

		// http://php.net/manual/ja/class.iterator.php
		addCommand(new CallMethod(SymbolUtils.createSymbolName(),
				expressionString, "rewind", Lists.newArrayList()));
		addCommand(new CallMethod(SymbolUtils.createSymbolName(),
				expressionString, "valid", Lists.newArrayList()));
		addCommand(new CallMethod(valueString, expressionString, "current",
				Lists.newArrayList()));
		addCommand(new CallMethod(keyString, expressionString, "key",
				Lists.newArrayList()));
		addCommand(new CallMethod(SymbolUtils.createSymbolName(),
				expressionString, "next", Lists.newArrayList()));

		addCommand(new StartLoop());
		Statement statement = forEachStatement.getStatement();
		statement.accept(this);
		addCommand(new EndLoop());

		return null;
	}

	@Override
	public Object visit(FormalParameter formalParameter) {
		// ここでは初期値の代入のみを行う
		Expression parameterName = formalParameter.getParameterName();
		Expression defaultValue = formalParameter.getDefaultValue();
		if (defaultValue == null) {
			return null;
		}
		addCommentSource(formalParameter);
		String nameString = (String) parameterName.accept(this);
		String valueString = (String) defaultValue.accept(this);
		addCommand(new Assign(nameString, valueString));
		return null;
	}

	@Override
	public Object visit(ForStatement forStatement) {
		addCommentSource(forStatement);
		List<Expression> initializers = forStatement.getInitializers();
		for (Expression initializer : initializers) {
			initializer.accept(this);
		}
		addCommand(new StartLoop());
		List<Expression> conditions = forStatement.getConditions();
		for (Expression condition : conditions) {
			condition.accept(this);
		}
		Statement body = forStatement.getBody();
		body.accept(this);
		List<Expression> updaters = forStatement.getUpdaters();
		for (Expression updater : updaters) {
			updater.accept(this);
		}
		addCommand(new EndLoop());
		return null;
	}

	@Override
	public Object visit(FunctionDeclaration functionDeclaration) {
		boolean isReference = functionDeclaration.isReference();
		Identifier name = functionDeclaration.getName();
		String nameString = name.getName();
		List<FormalParameter> formalParameters = functionDeclaration
				.getFormalParameters();
		List<String> list = Lists.newArrayList();
		for (FormalParameter formalParameter : formalParameters) {
			Expression parameterName = formalParameter.getParameterName();
			StringBuffer buffer = new StringBuffer();
			if (parameterName instanceof Reference) {
				Reference reference = (Reference) parameterName;
				buffer.append("&");
				parameterName = reference.getExpression();
			}
			if (parameterName instanceof Variable) {
				String parameterNameString = (String) parameterName
						.accept(this);
				buffer.append(parameterNameString);
			} else {
				throw new RuntimeException();
			}
			list.add(buffer.toString());
		}
		PhpFunction phpFunction = new PhpFunction(nameString, list,
				isReference, file);
		// グローバル関数、関数内関数、メソッド内関数
		addCommand(new CreateFunction(phpFunction));
		PhpFunction savedCurrentFunction = currentFunction;
		currentFunction = phpFunction;
		for (FormalParameter formalParameter : formalParameters) {
			formalParameter.accept(this);
		}
		addCommentSource(functionDeclaration);
		for (int i = 0; i < list.size(); i++) {
			String argument = list.get(i);
			if (argument.startsWith("&")) {
				argument = argument.substring(1);
				addCommand(new AssignArgumentReference(argument, i));
			} else {
				addCommand(new AssignArgument(argument, i));
			}
		}
		Block body = functionDeclaration.getBody();
		body.accept(this);
		currentFunction = savedCurrentFunction;

		return null;
	}

	@Override
	public Object visit(FunctionInvocation functionInvocation) {
		// 引数に&は付かない
		FunctionName functionName = functionInvocation.getFunctionName();
		String nameString = (String) functionName.accept(this);
		List<Expression> parameters = functionInvocation.getParameters();
		List<String> list = Lists.newArrayList();
		for (Expression parameter : parameters) {
			String parameterString = (String) parameter.accept(this);
			list.add(parameterString);
		}
		String resultString = SymbolUtils.createSymbolName();
		addCommand(new CallFunction(resultString, nameString, list));
		return resultString;
	}

	@Override
	public Object visit(FunctionName functionName) {
		Expression name = functionName.getName();
		return name.accept(this);
	}

	@Override
	public Object visit(GlobalStatement globalStatement) {
		addCommentSource(globalStatement);
		List<Variable> variables = globalStatement.getVariables();
		for (Variable variable : variables) {
			String variableString = (String) variable.accept(this);
			addCommand(new LoadGlobal(variableString));
		}
		return null;
	}

	@Override
	public Object visit(GotoLabel gotoLabel) {
		addCommentSource(gotoLabel);
		return null;
	}

	@Override
	public Object visit(GotoStatement gotoStatement) {
		addCommentSource(gotoStatement);
		return null;
	}

	@Override
	public Object visit(Identifier identifier) {
		String name = identifier.getName();
		return name;
	}

	@Override
	public Object visit(IfStatement ifStatement) {
		addCommentSource(ifStatement);
		Expression condition = ifStatement.getCondition();
		String conditionString = (String) condition.accept(this);

		String label = SymbolUtils.createLabelName();
		addCommand(new If_(conditionString, label));
		Statement trueStatement = ifStatement.getTrueStatement();
		trueStatement.accept(this);

		addCommand(new Else_(label));
		Statement falseStatement = ifStatement.getFalseStatement();
		if (falseStatement != null) {
			falseStatement.accept(this);
		}

		addCommand(new IfElseEnd(label));

		return null;
	}

	@Override
	public Object visit(IgnoreError ignoreError) {
		String resultString = SymbolUtils.createSymbolName();
		Expression expression = ignoreError.getExpression();
		resultString = (String) expression.accept(this);
		return resultString;
	}

	@Override
	public Object visit(Include include) {
		Expression expression = include.getExpression();
		String path = (String) expression.accept(this);
		int includeType = include.getIncludeType();

		String resultString = SymbolUtils.createSymbolName();
		if (includeType == Include.IT_INCLUDE
				|| includeType == Include.IT_REQUIRE) {
			addCommand(new net.katagaitai.phpscan.command.Include(resultString,
					path));
		} else {
			addCommand(new net.katagaitai.phpscan.command.IncludeOnce(
					resultString, path));
		}
		return resultString;
	}

	@Override
	public Object visit(InfixExpression infixExpression) {
		Expression left = infixExpression.getLeft();
		int operator = infixExpression.getOperator();
		Expression right = infixExpression.getRight();
		Operator op = Operator.get(InfixExpression.getOperator(operator));
		String resultString = SymbolUtils.createSymbolName();
		// TODO パーサのバグにより再帰するケースがある。
		StackTraceElement[] stackTrace = new Throwable().getStackTrace();
		if (stackTrace.length >= 100) {
			log.warn("打ち切り：" + infixExpression);
			return resultString;
		}
		if (op.equals(Operator.AND) || op.equals(Operator.AND2)) {
			// $a && $b
			// $c = $a ? $b : false
			String leftString = (String) left.accept(this);
			String label = SymbolUtils.createLabelName();
			addCommand(new If_(leftString, label));
			String rightString = (String) right.accept(this);
			addCommand(new Assign(resultString, rightString));
			addCommand(new Else_(label));
			addCommand(new Assign(resultString, Constants.PREFIX_BOOLEAN_LITERAL + "false"));
			addCommand(new IfElseEnd(label));
		} else if (op.equals(Operator.OR) || op.equals(Operator.OR2)) {
			// $a || $b
			// $c = $a ? true : $b
			String leftString = (String) left.accept(this);
			String label = SymbolUtils.createLabelName();
			addCommand(new If_(leftString, label));
			addCommand(new Assign(resultString, Constants.PREFIX_BOOLEAN_LITERAL + "true"));
			addCommand(new Else_(label));
			String rightString = (String) right.accept(this);
			addCommand(new Assign(resultString, rightString));
			addCommand(new IfElseEnd(label));
		} else {
			String leftString = (String) left.accept(this);
			String rightString = (String) right.accept(this);
			addCommand(new Mix(resultString, leftString, op, rightString));
		}
		return resultString;
	}

	@Override
	public Object visit(InLineHtml inLineHtml) {
		addCommentSource(inLineHtml);
		return null;
	}

	@Override
	public Object visit(InstanceOfExpression instanceOfExpression) {
		String resultString = SymbolUtils.createSymbolName();
		Expression expression = instanceOfExpression.getExpression();
		String expressionString = (String) expression.accept(this);
		ClassName className = instanceOfExpression.getClassName();
		String classNameString = (String) className.accept(this);
		addCommand(new Instanceof(resultString, expressionString,
				classNameString));
		return resultString;
	}

	@Override
	public Object visit(InterfaceDeclaration interfaceDeclaration) {
		List<Identifier> interfaces = interfaceDeclaration.getInterfaces();
		List<String> interfaceList = Lists.newArrayList();
		for (Identifier interface_ : interfaces) {
			interfaceList.add(interface_.getName());
		}

		Identifier name = interfaceDeclaration.getName();
		String nameString = name.getName();
		PhpFunction phpFunction = new PhpFunction(Constants.PREFIX
				+ "initializer", Lists.newArrayList(), false, file);
		PhpClass phpClass = new PhpClass(nameString, null, interfaceList,
				phpFunction, file);
		addCommand(new CreateClass(phpClass));

		PhpFunction savedCurrentFunction = currentFunction;
		currentFunction = phpFunction;

		Block body = interfaceDeclaration.getBody();
		body.accept(this);

		currentFunction = savedCurrentFunction;

		return null;
	}

	@Override
	public Object visit(LambdaFunctionDeclaration lambdaFunctionDeclaration) {

		boolean isReference = lambdaFunctionDeclaration.isReference();
		List<FormalParameter> formalParameters = lambdaFunctionDeclaration
				.getFormalParameters();
		List<String> argumentList = Lists.newArrayList();
		for (FormalParameter formalParameter : formalParameters) {
			Expression parameterName = formalParameter.getParameterName();
			StringBuffer buffer = new StringBuffer();
			if (parameterName instanceof Reference) {
				Reference reference = (Reference) parameterName;
				buffer.append("&");
				parameterName = reference.getExpression();
			}
			if (parameterName instanceof Variable) {
				String parameterNameString = (String) parameterName
						.accept(this);
				buffer.append(parameterNameString);
			} else {
				throw new RuntimeException();
			}
			argumentList.add(buffer.toString());
		}

		List<Expression> lexicalVariables = lambdaFunctionDeclaration
				.getLexicalVariables();
		List<String> lexicalList = Lists.newArrayList();
		for (Expression lexicalVariable : lexicalVariables) {
			String nameString = (String) lexicalVariable.accept(this);
			lexicalList.add(nameString);
		}

		String nameString = SymbolUtils.createLambdaName();
		PhpFunction phpFunction = new PhpFunction(nameString, argumentList,
				isReference, file, lexicalList);
		addCommand(new CreateFunction(phpFunction));
		PhpFunction savedCurrentFunction = currentFunction;
		currentFunction = phpFunction;
		for (FormalParameter formalParameter : formalParameters) {
			formalParameter.accept(this);
		}
		for (int i = 0; i < argumentList.size(); i++) {
			String argument = argumentList.get(i);
			if (argument.startsWith("&")) {
				argument = argument.substring(1);
				addCommand(new AssignArgumentReference(argument, i));
			} else {
				addCommand(new AssignArgument(argument, i));
			}
		}
		Block body = lambdaFunctionDeclaration.getBody();
		body.accept(this);
		currentFunction = savedCurrentFunction;

		String resultString = SymbolUtils.createSymbolName();
		addCommand(new New(resultString, "Closure",
				Lists.newArrayList(nameString)));
		return resultString;
	}

	@Override
	public Object visit(ListVariable listVariable) {
		throw new RuntimeException();
	}

	@Override
	public Object visit(MethodDeclaration methodDeclaration) {
		FunctionDeclaration functionDeclaration = methodDeclaration
				.getFunction();
		Block body = functionDeclaration.getBody();
		// abstractは無視する
		if (body == null) {
			return null;
		}

		boolean isReference = functionDeclaration.isReference();
		Identifier name = functionDeclaration.getName();
		String nameString = name.getName();
		List<FormalParameter> formalParameters = functionDeclaration
				.getFormalParameters();
		List<String> list = Lists.newArrayList();
		for (FormalParameter formalParameter : formalParameters) {
			Expression parameterName = formalParameter.getParameterName();
			StringBuffer buffer = new StringBuffer();
			if (parameterName instanceof Reference) {
				Reference reference = (Reference) parameterName;
				buffer.append("&");
				parameterName = reference.getExpression();
			}
			if (parameterName instanceof Variable) {
				String parameterNameString = (String) parameterName
						.accept(this);
				buffer.append(parameterNameString);
			} else {
				throw new RuntimeException();
			}
			list.add(buffer.toString());
		}
		PhpFunction phpFunction = new PhpFunction(nameString, list,
				isReference, file);
		int modifier = methodDeclaration.getModifier();
		State savedState1 = state;
		state = State.STATIC;
		if (PHPFlags.isStatic(modifier)) {
			phpFunction.setStaticFlg(true);
			addCommand(new CreateStaticMethod(phpFunction));
		} else {
			addCommand(new CreateMethod(phpFunction));
		}
		state = savedState1;

		PhpFunction savedCurrentFunction = currentFunction;
		currentFunction = phpFunction;
		for (FormalParameter formalParameter : formalParameters) {
			formalParameter.accept(this);
		}
		addCommentSource(methodDeclaration);
		for (int i = 0; i < list.size(); i++) {
			String argument = list.get(i);
			if (argument.startsWith("&")) {
				argument = argument.substring(1);
				addCommand(new AssignArgumentReference(argument, i));
			} else {
				addCommand(new AssignArgument(argument, i));
			}
		}
		body.accept(this);
		currentFunction = savedCurrentFunction;

		return null;
	}

	@Override
	public Object visit(MethodInvocation methodInvocation) {
		VariableBase dispatcher = methodInvocation.getDispatcher();
		String dispatcherString = (String) dispatcher.accept(this);
		FunctionInvocation method = methodInvocation.getMethod();
		FunctionName functionName = method.getFunctionName();
		Expression name = functionName.getName();
		String nameString = (String) name.accept(this);
		List<Expression> parameters = method.getParameters();
		List<String> list = Lists.newArrayList();
		for (Expression parameter : parameters) {
			String parameterString = (String) parameter.accept(this);
			list.add(parameterString);
		}
		String resultString = SymbolUtils.createSymbolName();
		addCommand(new CallMethod(resultString, dispatcherString, nameString,
				list));
		return resultString;
	}

	@Override
	public Object visit(NamespaceName namespaceName) {
		List<Identifier> segments = namespaceName.getSegments();
		List<String> list = Lists.newArrayList();
		for (Identifier segment : segments) {
			list.add(segment.getName());
		}
		String prefix = "";
		if (namespaceName.isGlobal()) {
			prefix = "\\";
		} else if (namespaceName.isCurrent()) {
			prefix = ".\\";
		}
		return prefix + String.join("\\", list);
	}

	@Override
	public Object visit(NamespaceDeclaration namespaceDeclaration) {
		addCommentSource(namespaceDeclaration);
		NamespaceName namespaceName = namespaceDeclaration.getName();
		String namespaceNameString;
		if (namespaceName == null) {
			namespaceNameString = "";
		} else {
			namespaceNameString = (String) namespaceName.accept(this);
		}
		if (!namespaceNameString.startsWith("\\")) {
			namespaceNameString = "\\" + namespaceNameString;
		}
		addCommand(new StartNamespace(namespaceNameString));
		State savedState = state;
		state = State.STATIC;
		addCommand(new StartNamespace(namespaceNameString));
		state = savedState;
		Block body = namespaceDeclaration.getBody();
		body.accept(this);
		addCommand(new EndNamespace(namespaceNameString));
		State savedState2 = state;
		state = State.STATIC;
		addCommand(new EndNamespace(namespaceNameString));
		state = savedState2;
		return null;
	}

	@Override
	public Object visit(ParenthesisExpression parenthesisExpression) {
		Expression expression = parenthesisExpression.getExpression();
		String resultString = (String) expression.accept(this);
		return resultString;
	}

	@Override
	public Object visit(PostfixExpression postfixExpression) {
		VariableBase variable = postfixExpression.getVariable();
		String variableString = (String) variable.accept(this);
		int operator = postfixExpression.getOperator();
		// TODO 評価順が不正確である問題
		Operator op;
		if (operator == PostfixExpression.OP_INC) {
			op = Operator.ADD;
		} else {
			op = Operator.SUB;
		}
		String resultString = SymbolUtils.createSymbolName();
		addCommand(new Mix(resultString, variableString, op,
				Constants.PREFIX_INTEGER_LITERAL + "1"));
		addCommand(new Assign(variableString, resultString));
		return variableString;
	}

	@Override
	public Object visit(PrefixExpression prefixExpression) {
		Expression variable = prefixExpression.getVariable();
		String variableString = (String) variable.accept(this);
		int operator = prefixExpression.getOperator();
		// TODO 評価順が不正確である問題
		Operator op;
		if (operator == PrefixExpression.OP_INC) {
			op = Operator.ADD;
		} else {
			op = Operator.SUB;
		}
		String resultString = SymbolUtils.createSymbolName();
		addCommand(new Mix(resultString, variableString, op,
				Constants.PREFIX_INTEGER_LITERAL + "1"));
		addCommand(new Assign(variableString, resultString));
		return variableString;
	}

	@Override
	public Object visit(Program program) {
		if (program != null) {
			for (Statement statement : program.getStatements()) {
				statement.accept(this);
			}
		}
		return null;
	}

	@Override
	public Object visit(Quote quote) {
		// "$a"、"test $a test"
		String resultString = SymbolUtils.createSymbolName();
		addCommand(new Assign(resultString, Constants.PREFIX_STRING_LITERAL
				+ ""));
		List<Expression> expressions = quote.getExpressions();
		for (Expression expression : expressions) {
			expression.setParent(quote);
			String expressionString = (String) expression.accept(this);
			String tmpString = SymbolUtils.createSymbolName();
			addCommand(new Mix(tmpString, resultString, Operator.CONCAT,
					expressionString));
			addCommand(new Assign(resultString, tmpString));
		}
		return resultString;
	}

	@Override
	public Object visit(Reference reference) {
		Expression expression = reference.getExpression();
		String expressionString = (String) expression.accept(this);
		String leftString = SymbolUtils.createSymbolName();
		addCommand(new AssignReference(leftString, expressionString));
		return leftString;
	}

	@Override
	public Object visit(ReflectionVariable reflectionVariable) {
		// $$a
		Expression name = reflectionVariable.getName();
		String nameString = (String) name.accept(this);
		if (reflectionVariable.getParent() instanceof Quote) {
			return nameString;
		} else {
			String resultString = SymbolUtils.createSymbolName();
			addCommand(new GetVariable(resultString, nameString));
			return resultString;
		}
	}

	@Override
	public Object visit(ReturnStatement returnStatement) {
		addCommentSource(returnStatement);
		Expression expression = returnStatement.getExpression();
		if (expression != null) {
			String expressionString = (String) expression.accept(this);
			addCommand(new AddToReturnValue(expressionString));
		}
		return null;
	}

	@Override
	public Object visit(Scalar scalar) {
		String stringValue = scalar.getStringValue();
		int scalarType = scalar.getScalarType();

		if (scalarType == Scalar.TYPE_INT) {
			// // 'int'
			// public static final int TYPE_INT = 0;
			// 1、0777
			return Constants.PREFIX_INTEGER_LITERAL + stringValue;
		} else if (scalarType == Scalar.TYPE_REAL) {
			// // 'real'
			// public static final int TYPE_REAL = 1;
			// 0.1、0xfff
			if (stringValue.startsWith("0x")) {
				try {
					Long l = Long.parseLong(stringValue, 16);
					return Constants.PREFIX_INTEGER_LITERAL + l;
				} catch (NumberFormatException e) {
					return Constants.PREFIX_INTEGER_LITERAL + 0;
				}
			} else {
				return Constants.PREFIX_FLOAT_LITERAL + stringValue;
			}
		} else if (scalarType == Scalar.TYPE_STRING) {
			// // 'string'
			// public static final int TYPE_STRING = 2;
			String stringValue2 = stringValue;
			if (scalar.getParent() instanceof Quote) {
				// "test $a"とか
				// そのままでOK
			} else if (stringValue.length() >= 2
					&& ((stringValue.startsWith("\"") && stringValue.endsWith("\""))
					|| (stringValue.startsWith("'") && stringValue.endsWith("'")))) {
				// "test"とか
				// エスケープを解除
				// http://php.net/manual/ja/regexp.reference.escape.php
				stringValue = stringValue.replace("\\\\", "\\");
				if (stringValue.startsWith("\"") && stringValue.endsWith("\"")) {
					stringValue = stringValue.replace("\\\"", "\"");
					stringValue = stringValue.replace("\\$", "$");
				}
				if (stringValue.startsWith("'") && stringValue.endsWith("'")) {
					stringValue = stringValue.replace("\\'", "'");
				}
				// 先頭と末尾のクオートを除去
				stringValue2 = stringValue.substring(1,
						stringValue.length() - 1);
			} else {
				// TESTとか
				// 定数
				if (stringValue.equalsIgnoreCase("true")) {
					return Constants.PREFIX_BOOLEAN_LITERAL + "true";
				} else if (stringValue.equalsIgnoreCase("false")) {
					return Constants.PREFIX_BOOLEAN_LITERAL + "false";
				} else if (stringValue.equalsIgnoreCase("null")) {
					return Constants.NULL_LITERAL;
				}
				String resultString = SymbolUtils.createSymbolName();
				addCommand(new GetConstant(resultString, stringValue));
				return resultString;
			}
			return Constants.PREFIX_STRING_LITERAL + stringValue2;
		} else if (scalarType == Scalar.TYPE_SYSTEM) {
			// // system scalars (__CLASS__ / ...)
			// public static final int TYPE_SYSTEM = 4;
			// __NAMESPACE__とか
			// 予約語
			String resultString = SymbolUtils.createSymbolName();
			addCommand(new GetConstant(resultString, stringValue));
			return resultString;
		} else if (scalarType == Scalar.TYPE_BIN) {
			// // 'binary' starts with "0b",e.g "0b"[01]+
			// public static final int TYPE_BIN = 5;
			// 2進数
			long value = 0;
			try {
				value = Long.parseLong(stringValue.substring(2), 2);
				return Constants.PREFIX_INTEGER_LITERAL + value;
			} catch (NumberFormatException e) {
				log.warn("", e);
			}
			return Constants.PREFIX_INTEGER_LITERAL + value;
		} else {
			// // unknown scalar in quote expression
			// public static final int TYPE_UNKNOWN = 3;
			log.warn("Scalar.TYPE_UNKNOWN:" + stringValue);
			return Constants.PREFIX_STRING_LITERAL + stringValue;
		}
	}

	@Override
	public Object visit(SingleFieldDeclaration singleFieldDeclaration) {
		throw new RuntimeException();
	}

	@Override
	public Object visit(StaticConstantAccess classConstantAccess) {
		Expression className = classConstantAccess.getClassName();
		String classNameString = (String) className.accept(this);
		Identifier constant = classConstantAccess.getConstant();
		String constantString = (String) constant.accept(this);
		String resultString = SymbolUtils.createSymbolName();
		addCommand(new GetClassConstant(resultString, classNameString,
				constantString));
		return resultString;
	}

	@Override
	public Object visit(StaticFieldAccess staticFieldAccess) {
		Expression className = staticFieldAccess.getClassName();
		String classNameString = (String) className.accept(this);
		Variable field = staticFieldAccess.getField();
		if (field instanceof ArrayAccess) {
			ArrayAccess arrayAccess = (ArrayAccess) field;
			Expression field2 = arrayAccess.getName();
			String fieldString = (String) field2.accept(this);
			if (field2 instanceof ReflectionVariable) {
				// そのまま
			} else {
				fieldString = fieldString.substring(1);
			}
			Expression index = arrayAccess.getIndex();
			String indexString;
			if (index == null) {
				log.warn("キーが不明：" + arrayAccess + " " + file);
				indexString = Constants.PREFIX_INTEGER_LITERAL + "0";
			} else {
				indexString = (String) index.accept(this);
			}
			String tmpString = SymbolUtils.createSymbolName();
			addCommand(new GetStaticFieldValue(tmpString, classNameString,
					fieldString));
			String resultString = SymbolUtils.createSymbolName();
			addCommand(new GetArrayValue(resultString, tmpString, indexString));
			return resultString;
		} else {
			String fieldString = (String) field.accept(this);
			if (field instanceof ReflectionVariable) {
				// そのまま
			} else {
				// A::$a
				fieldString = fieldString.substring(1);
			}
			String resultString = SymbolUtils.createSymbolName();
			addCommand(new GetStaticFieldValue(resultString, classNameString,
					fieldString));
			return resultString;
		}
	}

	@Override
	public Object visit(StaticMethodInvocation staticMethodInvocation) {
		Expression className = staticMethodInvocation.getClassName();
		String classNameString = (String) className.accept(this);
		FunctionInvocation method = staticMethodInvocation.getMethod();
		FunctionName functionName = method.getFunctionName();
		Expression name = functionName.getName();
		String nameString = (String) name.accept(this);
		List<Expression> parameters = method.getParameters();
		List<String> list = Lists.newArrayList();
		for (Expression parameter : parameters) {
			String parameterString = (String) parameter.accept(this);
			list.add(parameterString);
		}
		String resultString = SymbolUtils.createSymbolName();
		addCommand(new CallStaticMethod(resultString, classNameString,
				nameString, list));
		return resultString;
	}

	@Override
	public Object visit(StaticStatement staticStatement) {
		addCommentSource(staticStatement);
		List<Expression> expressions = staticStatement.getExpressions();
		State savedState = state;
		state = State.STATIC;
		for (Expression expression : expressions) {
			if (expression instanceof Variable) {
				Variable variable = (Variable) expression;
				String nameString = (String) variable.getName().accept(this);
				addCommand(new AssignStatic(nameString, Constants.NULL_LITERAL));
			} else {
				expression.accept(this);
			}
		}
		state = savedState;
		return null;
	}

	@Override
	public Object visit(SwitchCase switchCase) {
		addCommentSource(switchCase);
		Expression value = switchCase.getValue(); // nullあり
		String valueString = Constants.PREFIX_BOOLEAN_LITERAL + "true";
		if (value != null) {
			valueString = (String) value.accept(this);
		}
		String conditionString = SymbolUtils.createSymbolName();
		addCommand(new Mix(conditionString, valueString,
				Operator.EQ, Constants.PREFIX_BOOLEAN_LITERAL + "true"));
		String labelString = SymbolUtils.createLabelName();
		addCommand(new If_(conditionString, labelString));
		List<Statement> actions = switchCase.getActions();
		for (Statement action : actions) {
			action.accept(this);
		}
		// TODO 末尾にbreakがある前提になっている
		addCommand(new Else_(labelString));
		addCommand(new IfElseEnd(labelString));
		return null;
	}

	@Override
	public Object visit(SwitchStatement switchStatement) {
		addCommentSource(switchStatement);
		Expression expression = switchStatement.getExpression();
		expression.accept(this);
		Block body = switchStatement.getBody();
		body.accept(this);
		return null;
	}

	@Override
	public Object visit(ThrowStatement throwStatement) {
		addCommentSource(throwStatement);
		Expression expression = throwStatement.getExpression();
		expression.accept(this);
		return null;
	}

	@Override
	public Object visit(TryStatement tryStatement) {
		addCommentSource(tryStatement);
		String label = SymbolUtils.createLabelName();
		addCommand(new If_(Constants.PREFIX_BOOLEAN_LITERAL + "true", label));
		Block tryStatement_ = tryStatement.getTryStatement();
		tryStatement_.accept(this);
		addCommand(new Else_(label));
		List<CatchClause> catchClauses = tryStatement.getCatchClauses();
		for (CatchClause catchClause : catchClauses) {
			String label2 = SymbolUtils.createLabelName();
			addCommand(new If_(Constants.PREFIX_BOOLEAN_LITERAL + "true", label2));
			catchClause.accept(this);
			addCommand(new Else_(label2));
			addCommand(new IfElseEnd(label2));
		}
		addCommand(new IfElseEnd(label));
		FinallyClause finallyClause = tryStatement.getFinallyClause();
		if (finallyClause != null) {
			finallyClause.accept(this);
		}
		return null;
	}

	@Override
	public Object visit(UnaryOperation unaryOperation) {
		// // '+'
		// public static final int OP_PLUS = 0;
		// // '-'
		// public static final int OP_MINUS = 1;
		// // '!'
		// public static final int OP_NOT = 2;
		// // '~'
		// public static final int OP_TILDA = 3;
		Expression expression = unaryOperation.getExpression();
		String expressionString = (String) expression.accept(this);
		int operator = unaryOperation.getOperator();
		Operator op;
		if (operator == UnaryOperation.OP_PLUS) {
			op = Operator.ADD;
		} else if (operator == UnaryOperation.OP_MINUS) {
			op = Operator.SUB;
		} else if (operator == UnaryOperation.OP_NOT) {
			op = Operator.NOT;
		} else {
			op = Operator.BITNOT;
		}
		String resultString = SymbolUtils.createSymbolName();
		addCommand(new Mix(resultString,
				Constants.PREFIX_INTEGER_LITERAL + "0", op, expressionString));
		return resultString;
	}

	@Override
	public Object visit(Variable variable) {
		Expression name = variable.getName();
		String nameString = (String) name.accept(this);
		String prefix = variable.isDollared() ? "$" : "";
		return prefix + nameString;
	}

	@Override
	public Object visit(UseStatement useStatement) {
		addCommentSource(useStatement);
		List<UseStatementPart> parts = useStatement.getParts();
		for (UseStatementPart part : parts) {
			NamespaceName namespaceName = part.getName();
			String nameString = (String) namespaceName.accept(this);
			if (!nameString.startsWith("\\")) {
				nameString = "\\" + nameString;
			}
			Identifier alias = part.getAlias();
			if (alias == null) {
				addCommand(new Use(nameString));
			} else {
				addCommand(new Use(nameString, alias.getName()));
			}
		}
		return null;
	}

	@Override
	public Object visit(UseStatementPart useStatementPart) {
		throw new RuntimeException();
	}

	@Override
	public Object visit(WhileStatement whileStatement) {
		addCommentSource(whileStatement);
		addCommand(new StartLoop());
		Expression condition = whileStatement.getCondition();
		condition.accept(this);
		Statement body = whileStatement.getBody();
		body.accept(this);
		addCommand(new EndLoop());
		return null;
	}

	@Override
	public Object visit(ASTNode node) {
		throw new RuntimeException();
	}

	@Override
	public Object visit(FullyQualifiedTraitMethodReference node) {
		log.warn("未実装" + node);
		return null;
	}

	@Override
	public Object visit(TraitAlias node) {
		log.warn("未実装" + node);
		return null;
	}

	@Override
	public Object visit(TraitAliasStatement node) {
		addCommentSource(node);
		log.warn("未実装" + node);
		return null;
	}

	@Override
	public Object visit(TraitDeclaration node) {
		addCommentSource(node);
		log.warn("未実装" + node);
		return null;
	}

	@Override
	public Object visit(TraitPrecedence node) {
		log.warn("未実装" + node);
		return null;
	}

	@Override
	public Object visit(TraitPrecedenceStatement node) {
		addCommentSource(node);
		log.warn("未実装" + node);
		return null;
	}

	@Override
	public Object visit(TraitUseStatement node) {
		addCommentSource(node);
		log.warn("未実装" + node);
		return null;
	}

	@Override
	public Object visit(YieldExpression yieldExpression) {
		// // yield $a or yield $k => $a
		// public static final int OP_NONE = 0;
		// // yield from $k
		// public static final int OP_FROM = 1;
		int operator = yieldExpression.getOperator();
		if (operator == YieldExpression.OP_FROM) {
			log.warn("5.6では非対応");
			return null;
		}
		Expression key = yieldExpression.getKey();
		Expression expression = yieldExpression.getExpression();
		String expressionString = (String) expression.accept(this);
		if (key == null) {
			addCommand(new AddToReturnValue(expressionString));
		} else {
			String keyString = (String) key.accept(this);
			String arrayString = SymbolUtils.createSymbolName();
			addCommand(new CreateArray(arrayString));
			addCommand(new PutArrayValue(arrayString, keyString,
					expressionString));
			addCommand(new AddToReturnValue(arrayString));
		}
		return null;
	}

	@Override
	public Object visit(FinallyClause finallyClause) {
		addCommentSource(finallyClause);
		Block body = finallyClause.getBody();
		body.accept(this);
		return null;
	}
}
