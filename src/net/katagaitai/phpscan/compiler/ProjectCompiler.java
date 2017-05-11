package net.katagaitai.phpscan.compiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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
import net.katagaitai.phpscan.ast.nodes.WhileStatement;
import net.katagaitai.phpscan.ast.nodes.YieldExpression;
import net.katagaitai.phpscan.compiler.PhpProject.FunctionInfo;
import net.katagaitai.phpscan.compiler.PhpProject.MethodInfo;
import net.katagaitai.phpscan.util.FileUtils;
import net.katagaitai.phpscan.util.PhpUtils;
import net.katagaitai.phpscan.util.ScanUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Log4j2
@RequiredArgsConstructor
public class ProjectCompiler implements Visitor {
	private final File projectDirectoryFile;

	private String filepath;
	private String classname;
	private String namespace = "\\";
	private Map<String, String> useMap = Maps.newHashMap();

	@Getter
	private Map<String, List<FunctionInfo>> functionMap = Maps.newHashMap();
	@Getter
	private Map<String, List<String>> classMap = Maps.newHashMap();
	@Getter
	private Map<String, List<MethodInfo>> methodMap = Maps.newHashMap();
	@Getter
	private Map<String, List<MethodInfo>> staticMethodMap = Maps.newHashMap();
	@Getter
	private Map<String, List<String>> constantMap = Maps.newHashMap();
	@Getter
	private Map<String, List<String>> subClassMap = Maps.newHashMap();
	@Setter
	private File entryPointDirectoryFile;

	public PhpProject compile() throws Exception {
		if (entryPointDirectoryFile != null) {
			compileDirectory(entryPointDirectoryFile);
		}
		compileDirectory(projectDirectoryFile);
		PhpProject phpProject = new PhpProject(
				functionMap, classMap, methodMap, staticMethodMap, constantMap, subClassMap);
		return phpProject;
	}

	private void compileDirectory(File directoryFile) throws IOException, FileNotFoundException {
		log.info("プロジェクトコンパイル開始：" + directoryFile.getAbsolutePath());
		if (directoryFile.exists() && directoryFile.isDirectory()) {
			// OK
		} else {
			throw new RuntimeException("ディレクトリが存在しません：" + directoryFile.getAbsolutePath());
		}
		for (String filePath : FileUtils.getFilepathList(directoryFile.getAbsolutePath())) {
			if (ScanUtils.isIgnorePath(filePath)) {
				log.info("除外：" + filePath);
				continue;
			}
			File file = new File(filePath);
			if (file.exists() && file.isFile()) {
				// OK
			} else {
				log.error("ファイルが存在しません。：" + filePath);
				continue;
			}
			filepath = file.getAbsolutePath();
			log.info("コンパイル開始：" + filepath);
			FileReader reader = new FileReader(file);
			AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
			PhpAstParser parser = ast.getParser();
			try {
				parser.parse();
			} catch (Exception e) {
				log.error("", e);
			}
			Program program = parser.getProgram();
			visit(program);
			log.info("コンパイル終了：" + file.getAbsolutePath());
		}
		log.info("プロジェクトコンパイル終了：" + directoryFile.getAbsolutePath());
	}

	@Override
	public Object visit(AnonymousClassDeclaration anonymousClassDeclaration) {
		return null;
	}

	@Override
	public Object visit(ArrayAccess arrayAccess) {
		return null;
	}

	@Override
	public Object visit(ArrayCreation arrayCreation) {
		return null;
	}

	@Override
	public Object visit(ArrayElement arrayElement) {
		return null;
	}

	@Override
	public Object visit(Assignment assignment) {
		return null;
	}

	@Override
	public Object visit(ASTError astError) {
		return null;
	}

	@Override
	public Object visit(BackTickExpression backTickExpression) {
		return null;
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
		return null;
	}

	@Override
	public Object visit(CastExpression castExpression) {
		return null;
	}

	@Override
	public Object visit(CatchClause catchClause) {
		Block body = catchClause.getBody();
		body.accept(this);
		return null;
	}

	@Override
	public Object visit(ConstantDeclaration classConstantDeclaration) {
		List<Identifier> names = classConstantDeclaration.getNames();
		List<Expression> initializers = classConstantDeclaration.getInitializers();
		if (names.size() == initializers.size()) {
			for (int i = 0; i < names.size(); i++) {
				Identifier name = names.get(i);
				String nameString = (String) name.accept(this);
				if (classname == null) {
					addConstant(getAbsoluteConstantName(nameString));
				} else {
					addConstant(classname + "::" + nameString);
				}
			}
		}
		return null;
	}

	private String getAbsoluteConstantName(String name) {
		return PhpUtils.getAbsolute(name, namespace, useMap);
	}

	private void addConstant(String name) {
		if (constantMap.get(name) == null) {
			constantMap.put(name, Lists.newArrayList());
		}
		constantMap.get(name).add(filepath);
	}

	@Override
	public Object visit(ClassDeclaration classDeclaration) {
		Identifier name = classDeclaration.getName();
		String nameString = name.getName();
		String savedClassname = classname;
		classname = getAbsoluteClassName(nameString);
		addClass(classname);

		Expression superClass = classDeclaration.getSuperClass();
		if (superClass != null) {
			String superClassString = (String) superClass.accept(this);
			String superClassname = getAbsoluteClassName(superClassString);
			addSubClass(superClassname, classname);
		}

		Block body = classDeclaration.getBody();
		body.accept(this);
		classname = savedClassname;
		return null;
	}

	private void addSubClass(String superClassname2, String classname2) {
		String lowerSuperClassname = superClassname2.toLowerCase();
		String lowerClassname = classname2.toLowerCase();
		if (subClassMap.get(lowerSuperClassname) == null) {
			subClassMap.put(lowerSuperClassname, Lists.newArrayList());
		}
		subClassMap.get(lowerSuperClassname).add(lowerClassname);
	}

	private void addClass(String classname2) {
		String lowerClassname = classname2.toLowerCase();
		if (classMap.get(lowerClassname) == null) {
			classMap.put(lowerClassname, Lists.newArrayList());
		}
		classMap.get(lowerClassname).add(filepath);
	}

	public String getAbsoluteClassName(String name) {
		return PhpUtils.getAbsolute(name, namespace, useMap);
	}

	@Override
	public Object visit(ClassInstanceCreation classInstanceCreation) {
		return null;
	}

	@Override
	public Object visit(ClassName className) {
		Expression name = className.getName();
		return name.accept(this);
	}

	@Override
	public Object visit(CloneExpression cloneExpression) {
		return null;
	}

	@Override
	public Object visit(Comment comment) {
		return null;
	}

	@Override
	public Object visit(ConditionalExpression conditionalExpression) {
		Expression condition = conditionalExpression.getCondition();
		condition.accept(this);
		Expression ifTrue = conditionalExpression.getIfTrue();
		if (ifTrue != null) {
			ifTrue.accept(this);
		}
		Expression ifFalse = conditionalExpression.getIfFalse();
		if (ifFalse != null) {
			ifFalse.accept(this);
		}
		return null;
	}

	@Override
	public Object visit(ContinueStatement continueStatement) {
		return null;
	}

	@Override
	public Object visit(DeclareStatement declareStatement) {
		Statement body = declareStatement.getBody();
		body.accept(this);
		return null;
	}

	@Override
	public Object visit(DoStatement doStatement) {
		Statement body = doStatement.getBody();
		body.accept(this);
		Expression condition = doStatement.getCondition();
		condition.accept(this);
		return null;
	}

	@Override
	public Object visit(EchoStatement echoStatement) {
		List<Expression> expressions = echoStatement.getExpressions();
		for (Expression expression : expressions) {
			expression.accept(this);
		}
		return null;
	}

	@Override
	public Object visit(EmptyStatement emptyStatement) {
		return null;
	}

	@Override
	public Object visit(ExpressionStatement expressionStatement) {
		Expression expression = expressionStatement.getExpression();
		String expressionString = (String) expression.accept(this);
		return expressionString;
	}

	@Override
	public Object visit(FieldAccess fieldAccess) {
		return null;
	}

	@Override
	public Object visit(FieldsDeclaration fieldsDeclaration) {
		// TODO フィールドからファイルとクラスを推測する
		return null;
	}

	@Override
	public Object visit(ForEachStatement forEachStatement) {
		Statement statement = forEachStatement.getStatement();
		statement.accept(this);
		return null;
	}

	@Override
	public Object visit(FormalParameter formalParameter) {
		return null;
	}

	@Override
	public Object visit(ForStatement forStatement) {
		List<Expression> initializers = forStatement.getInitializers();
		for (Expression initializer : initializers) {
			initializer.accept(this);
		}
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
		return null;
	}

	@Override
	public Object visit(FunctionDeclaration functionDeclaration) {
		Identifier name = functionDeclaration.getName();
		String nameString = name.getName();
		String funcname = getAbsoluteFunctionName(nameString);

		List<FormalParameter> formalParameters = functionDeclaration
				.getFormalParameters();
		int min = 0;
		int max = 0;
		for (FormalParameter formalParameter : formalParameters) {
			max++;
			if (formalParameter.getDefaultValue() == null) {
				min++;
			}
		}

		addFunction(funcname, min, max);
		Block body = functionDeclaration.getBody();
		body.accept(this);
		return null;
	}

	private void addFunction(String funcname, int min, int max) {
		String lowerFuncname = funcname.toLowerCase();
		if (functionMap.get(lowerFuncname) == null) {
			functionMap.put(lowerFuncname, Lists.newArrayList());
		}
		functionMap.get(lowerFuncname).add(new FunctionInfo(filepath, min, max));
	}

	private String getAbsoluteFunctionName(String name) {
		return PhpUtils.getAbsolute(name, namespace, useMap);
	}

	@Override
	public Object visit(FunctionInvocation functionInvocation) {
		FunctionName functionName = functionInvocation.getFunctionName();
		String nameString = (String) functionName.accept(this);
		if (nameString != null && nameString.toLowerCase().equals("define")) {
			List<Expression> parameters = functionInvocation.getParameters();
			if (parameters.size() > 0) {
				String parameterString = (String) parameters.get(0).accept(this);
				addConstant("\\" + parameterString);
			}
		}
		return null;
	}

	@Override
	public Object visit(FunctionName functionName) {
		Expression name = functionName.getName();
		return name.accept(this);
	}

	@Override
	public Object visit(GlobalStatement globalStatement) {
		return null;
	}

	@Override
	public Object visit(GotoLabel gotoLabel) {
		return null;
	}

	@Override
	public Object visit(GotoStatement gotoStatement) {
		return null;
	}

	@Override
	public Object visit(Identifier identifier) {
		String name = identifier.getName();
		return name;
	}

	@Override
	public Object visit(IfStatement ifStatement) {
		Expression condition = ifStatement.getCondition();
		condition.accept(this);
		Statement trueStatement = ifStatement.getTrueStatement();
		trueStatement.accept(this);
		Statement falseStatement = ifStatement.getFalseStatement();
		if (falseStatement != null) {
			falseStatement.accept(this);
		}
		return null;
	}

	@Override
	public Object visit(IgnoreError ignoreError) {
		return null;
	}

	@Override
	public Object visit(Include include) {
		return null;
	}

	@Override
	public Object visit(InfixExpression infixExpression) {
		return null;
	}

	@Override
	public Object visit(InLineHtml inLineHtml) {
		return null;
	}

	@Override
	public Object visit(InstanceOfExpression instanceOfExpression) {
		return null;
	}

	@Override
	public Object visit(InterfaceDeclaration interfaceDeclaration) {
		Identifier name = interfaceDeclaration.getName();
		String nameString = name.getName();
		String savedClassname = classname;
		classname = getAbsoluteClassName(nameString);
		addClass(classname);
		Block body = interfaceDeclaration.getBody();
		body.accept(this);
		classname = savedClassname;
		return null;
	}

	@Override
	public Object visit(LambdaFunctionDeclaration lambdaFunctionDeclaration) {
		Block body = lambdaFunctionDeclaration.getBody();
		body.accept(this);
		return null;
	}

	@Override
	public Object visit(ListVariable listVariable) {
		return null;
	}

	@Override
	public Object visit(MethodDeclaration methodDeclaration) {
		FunctionDeclaration functionDeclaration = methodDeclaration.getFunction();
		Identifier name = functionDeclaration.getName();
		String nameString = name.getName();

		List<FormalParameter> formalParameters = functionDeclaration
				.getFormalParameters();
		int min = 0;
		int max = 0;
		for (FormalParameter formalParameter : formalParameters) {
			max++;
			if (formalParameter.getDefaultValue() == null) {
				min++;
			}
		}

		int modifier = methodDeclaration.getModifier();
		if (PHPFlags.isStatic(modifier)) {
			addStaticMethod(nameString, min, max);
		} else {
			addMethod(nameString, min, max);
		}
		return null;
	}

	private void addMethod(String methodname, int min, int max) {
		String lowerMethodname = methodname.toLowerCase();
		if (methodMap.get(lowerMethodname) == null) {
			methodMap.put(lowerMethodname, Lists.newArrayList());
		}
		methodMap.get(lowerMethodname).add(new MethodInfo(filepath, classname, min, max));
	}

	private void addStaticMethod(String methodname, int min, int max) {
		String lowerMethodname = methodname.toLowerCase();
		if (staticMethodMap.get(lowerMethodname) == null) {
			staticMethodMap.put(lowerMethodname, Lists.newArrayList());
		}
		staticMethodMap.get(lowerMethodname).add(new MethodInfo(filepath, classname, min, max));
	}

	@Override
	public Object visit(MethodInvocation methodInvocation) {
		return null;
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
		String savedNamespace = namespace;
		namespace = namespaceNameString;
		Block body = namespaceDeclaration.getBody();
		body.accept(this);
		namespace = savedNamespace;
		return null;
	}

	@Override
	public Object visit(ParenthesisExpression parenthesisExpression) {
		return null;
	}

	@Override
	public Object visit(PostfixExpression postfixExpression) {
		return null;
	}

	@Override
	public Object visit(PrefixExpression prefixExpression) {
		return null;
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
		return null;
	}

	@Override
	public Object visit(Reference reference) {
		return null;
	}

	@Override
	public Object visit(ReflectionVariable reflectionVariable) {
		return null;
	}

	@Override
	public Object visit(ReturnStatement returnStatement) {
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
		} else if (scalarType == Scalar.TYPE_REAL) {
			// // 'real'
			// public static final int TYPE_REAL = 1;
			// 0.1、0xfff
		} else if (scalarType == Scalar.TYPE_STRING) {
			// // 'string'
			// public static final int TYPE_STRING = 2;
			if (scalar.getParent() instanceof Quote) {
				// "test $a"とか
				// そのままでOK
			} else if (stringValue.length() >= 2 &&
					((stringValue.startsWith("\"") && stringValue.endsWith("\""))
					|| (stringValue.startsWith("'") && stringValue.endsWith("'")))) {
				// "test"とか
				if (stringValue.startsWith("\"") && stringValue.endsWith("\"")) {
					// エスケープを解除
					// http://php.net/manual/ja/regexp.reference.escape.php
					stringValue = stringValue.replace("\\\\", "\\");
				}
				// 先頭と末尾のクオートを除去
				stringValue = stringValue.substring(1, stringValue.length() - 1);
			} else {
				// TESTとか
				// 定数
			}
		} else if (scalarType == Scalar.TYPE_SYSTEM) {
			// // system scalars (__CLASS__ / ...)
			// public static final int TYPE_SYSTEM = 4;
			// __NAMESPACE__とか
			// 予約語
		} else if (scalarType == Scalar.TYPE_BIN) {
			// // 'binary' starts with "0b",e.g "0b"[01]+
			// public static final int TYPE_BIN = 5;
			// 2進数
		}
		return stringValue;
	}

	@Override
	public Object visit(SingleFieldDeclaration singleFieldDeclaration) {
		return null;
	}

	@Override
	public Object visit(StaticConstantAccess classConstantAccess) {
		return null;
	}

	@Override
	public Object visit(StaticFieldAccess staticFieldAccess) {
		return null;
	}

	@Override
	public Object visit(StaticMethodInvocation staticMethodInvocation) {
		return null;
	}

	@Override
	public Object visit(StaticStatement staticStatement) {
		List<Expression> expressions = staticStatement.getExpressions();
		for (Expression expression : expressions) {
			expression.accept(this);
		}
		return null;
	}

	@Override
	public Object visit(SwitchCase switchCase) {
		Expression value = switchCase.getValue(); // nullあり
		if (value != null) {
			value.accept(this);
		}
		List<Statement> actions = switchCase.getActions();
		for (Statement action : actions) {
			action.accept(this);
		}
		return null;
	}

	@Override
	public Object visit(SwitchStatement switchStatement) {
		Expression expression = switchStatement.getExpression();
		expression.accept(this);
		Block body = switchStatement.getBody();
		body.accept(this);
		return null;
	}

	@Override
	public Object visit(ThrowStatement throwStatement) {
		Expression expression = throwStatement.getExpression();
		expression.accept(this);
		return null;
	}

	@Override
	public Object visit(TryStatement tryStatement) {
		Block tryStatement_ = tryStatement.getTryStatement();
		tryStatement_.accept(this);
		List<CatchClause> catchClauses = tryStatement.getCatchClauses();
		for (CatchClause catchClause : catchClauses) {
			catchClause.accept(this);
		}
		FinallyClause finallyClause = tryStatement.getFinallyClause();
		if (finallyClause != null) {
			finallyClause.accept(this);
		}
		return null;
	}

	@Override
	public Object visit(UnaryOperation unaryOperation) {
		return null;
	}

	@Override
	public Object visit(Variable variable) {
		return null;
	}

	@Override
	public Object visit(UseStatement useStatement) {
		List<UseStatementPart> parts = useStatement.getParts();
		for (UseStatementPart part : parts) {
			NamespaceName namespaceName = part.getName();
			String nameString = (String) namespaceName.accept(this);
			if (!nameString.startsWith("\\")) {
				nameString = "\\" + nameString;
			}
			Identifier alias = part.getAlias();
			String aliasString;
			if (alias == null) {
				String[] names = nameString.split("\\\\");
				if (names.length > 0) {
					aliasString = names[names.length - 1];
				} else {
					log.error("不正な名前空間：" + nameString + " " + useStatement + " " + filepath);
					aliasString = "";
				}
			} else {
				aliasString = alias.getName();
			}
			useMap.put(nameString, aliasString);
		}
		return null;
	}

	@Override
	public Object visit(UseStatementPart useStatementPart) {
		return null;
	}

	@Override
	public Object visit(WhileStatement whileStatement) {
		Expression condition = whileStatement.getCondition();
		condition.accept(this);
		Statement body = whileStatement.getBody();
		body.accept(this);
		return null;
	}

	@Override
	public Object visit(ASTNode node) {
		return null;
	}

	@Override
	public Object visit(FullyQualifiedTraitMethodReference node) {
		return null;
	}

	@Override
	public Object visit(TraitAlias node) {
		return null;
	}

	@Override
	public Object visit(TraitAliasStatement node) {
		return null;
	}

	@Override
	public Object visit(TraitDeclaration node) {
		return null;
	}

	@Override
	public Object visit(TraitPrecedence node) {
		return null;
	}

	@Override
	public Object visit(TraitPrecedenceStatement node) {
		return null;
	}

	@Override
	public Object visit(TraitUseStatement node) {
		return null;
	}

	@Override
	public Object visit(YieldExpression yieldExpression) {
		return null;
	}

	@Override
	public Object visit(FinallyClause finallyClause) {
		Block body = finallyClause.getBody();
		body.accept(this);
		return null;
	}
}
