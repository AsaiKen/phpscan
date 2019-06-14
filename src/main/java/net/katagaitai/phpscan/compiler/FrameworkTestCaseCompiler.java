package net.katagaitai.phpscan.compiler;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import net.katagaitai.phpscan.ast.PHPFlags;
import net.katagaitai.phpscan.ast.PHPVersion;
import net.katagaitai.phpscan.ast.PhpAstParser;
import net.katagaitai.phpscan.ast.Visitor;
import net.katagaitai.phpscan.ast.nodes.*;
import net.katagaitai.phpscan.util.Constants;
import net.katagaitai.phpscan.util.FileUtils;
import net.katagaitai.phpscan.util.PhpUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
public class FrameworkTestCaseCompiler implements Visitor {
    private final File frameworkDirectoryFile;
    private final String frameworkName;

    @Setter
    private List<String> excludeRegexList;
    @Setter
    private List<String> includeRegexList;

    private String filepath;
    private String classname;
    private String namespace = "\\";
    private Map<String, String> useMap = Maps.newHashMap();

    private List<String> staticMethodNameList = Lists.newArrayList();
    private List<String> methodNameList = Lists.newArrayList();

    public void compile() throws Exception {
        compileDirectory(frameworkDirectoryFile);
    }

    private void compileDirectory(File directoryFile) throws IOException {
        log.info("フレームワークコンパイル開始：" + directoryFile.getAbsolutePath());
        if (directoryFile.exists() && directoryFile.isDirectory()) {
            // OK
        } else {
            throw new RuntimeException("ディレクトリが存在しません：" + directoryFile.getAbsolutePath());
        }
        for (String filePath : FileUtils.getFilepathList(directoryFile.getAbsolutePath())) {
            if (isExclude(filePath)) {
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
        log.info("フレームワークコンパイル終了：" + directoryFile.getAbsolutePath());
    }

    private boolean isExclude(String filePath) {
        if (excludeRegexList != null) {
            boolean excludeFlg = false;
            for (String regex : excludeRegexList) {
                if (filePath.matches(regex)) {
                    excludeFlg = true;
                    break;
                }
            }
            if (excludeFlg) {
                return true;
            }
        }
        if (includeRegexList != null) {
            boolean includeFlg = false;
            for (String regex : includeRegexList) {
                if (filePath.matches(regex)) {
                    includeFlg = true;
                    break;
                }
            }
            if (includeFlg) {
                // OK
            } else {
                return true;
            }
        }
        return false;
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
        return null;
    }

    @Override
    public Object visit(ConstantDeclaration classConstantDeclaration) {
        return null;
    }

    @Override
    public Object visit(ClassDeclaration classDeclaration) {
        Identifier name = classDeclaration.getName();
        String nameString = name.getName();
        String savedClassname = classname;
        classname = getAbsoluteClassName(nameString);
        Block body = classDeclaration.getBody();
        body.accept(this);
        try {
            // テストケース作成
            createTestCase();
        } catch (IOException e) {
            log.error("テストケースの作成に失敗しました。:" + filepath + "#" + classname);
        }
        classname = savedClassname;
        return null;
    }

    private void createTestCase() throws IOException {
        if (staticMethodNameList.size() == 0 && methodNameList.size() == 0) {
            return;
        }
        File testCaseFile = createTestCaseFile(classname);
        StringBuffer buffer = new StringBuffer();
        buffer.append("<?php\n");
        buffer.append("include '" + filepath + "';\n");
        for (String staticMethodName : staticMethodNameList) {
            buffer.append(classname
                    + "::"
                    + staticMethodName
                    +
                    "($_GET['test'], $_GET['test'], $_GET['test'], $_GET['test'], $_GET['test'], $_GET['test'], $_GET['test'], $_GET['test'], $_GET['test'], $_GET['test']);\n"
            );
        }
        staticMethodNameList.clear();
        if (methodNameList.size() > 0) {
            buffer.append("$a = new "
                    + classname
                    +
                    "($_GET['test'], $_GET['test'], $_GET['test'], $_GET['test'], $_GET['test'], $_GET['test'], $_GET['test'], $_GET['test'], $_GET['test'], $_GET['test']);\n");
            List<String> sortedMethodNameList = Lists.newArrayList();
            for (String methodName : methodNameList) {
                // セッターは先に配置する
                if (methodName.toLowerCase().startsWith("set")
                        || methodName.toLowerCase().startsWith("add")) {
                    sortedMethodNameList.add(methodName);
                }
            }
            for (String methodName : methodNameList) {
                if (sortedMethodNameList.contains(methodName)) {
                    // skip
                } else {
                    sortedMethodNameList.add(methodName);
                }
            }
            for (String methodName : sortedMethodNameList) {
                buffer.append("$a->"
                        + methodName
                        +
                        "($_GET['test'], $_GET['test'], $_GET['test'], $_GET['test'], $_GET['test'], $_GET['test'], $_GET['test'], $_GET['test'], $_GET['test'], $_GET['test']);\n"
                );
            }
            methodNameList.clear();
        }
        Files.write(buffer.toString(), testCaseFile, Constants.DEFAULT_CHARSET);
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
        return null;
    }

    @Override
    public Object visit(ContinueStatement continueStatement) {
        return null;
    }

    @Override
    public Object visit(DeclareStatement declareStatement) {
        return null;
    }

    @Override
    public Object visit(DoStatement doStatement) {
        return null;
    }

    @Override
    public Object visit(EchoStatement echoStatement) {
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
        return null;
    }

    @Override
    public Object visit(ForEachStatement forEachStatement) {
        return null;
    }

    @Override
    public Object visit(FormalParameter formalParameter) {
        return null;
    }

    @Override
    public Object visit(ForStatement forStatement) {
        return null;
    }

    @Override
    public Object visit(FunctionDeclaration functionDeclaration) {
        return null;
    }

    @Override
    public Object visit(FunctionInvocation functionInvocation) {
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
        return null;
    }

    @Override
    public Object visit(LambdaFunctionDeclaration lambdaFunctionDeclaration) {
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
        String[] classNameElements = classname.split("\\\\");
        String classnameTail = classNameElements[classNameElements.length - 1];
        if (nameString.equalsIgnoreCase("__construct")
                || nameString.equalsIgnoreCase(classnameTail)) {
            // コンストラクタはskip
        } else {
            int modifier = methodDeclaration.getModifier();
            if (PHPFlags.isPublic(modifier)) {
                if (PHPFlags.isStatic(modifier)) {
                    staticMethodNameList.add(nameString);
                } else {
                    methodNameList.add(nameString);
                }
            }
        }
        return null;
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
        return null;
    }

    @Override
    public Object visit(SwitchStatement switchStatement) {
        return null;
    }

    @Override
    public Object visit(ThrowStatement throwStatement) {
        return null;
    }

    @Override
    public Object visit(TryStatement tryStatement) {
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
        return null;
    }

    public File createTestCaseFile(String className) throws IOException {
        String filename = className
                .replace("\\", "_").replace("/", "_").replace(":", "_");
        File result = new File("./tmp/framework_testcase/" + frameworkName + "/" + filename + ".php");
        Files.createParentDirs(result);
        return result;
    }

}
