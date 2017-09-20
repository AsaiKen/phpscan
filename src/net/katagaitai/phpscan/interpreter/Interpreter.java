package net.katagaitai.phpscan.interpreter;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import net.katagaitai.phpscan.Vulnerability;
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
import net.katagaitai.phpscan.command.Include;
import net.katagaitai.phpscan.command.IncludeOnce;
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
import net.katagaitai.phpscan.compiler.BuiltinBase;
import net.katagaitai.phpscan.compiler.Compiler;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.compiler.PhpClass;
import net.katagaitai.phpscan.compiler.PhpFunction;
import net.katagaitai.phpscan.compiler.PhpNewable;
import net.katagaitai.phpscan.compiler.PhpProject;
import net.katagaitai.phpscan.interceptor.Interceptor;
import net.katagaitai.phpscan.php.builtin._Superglobals;
import net.katagaitai.phpscan.php.types.PhpAnyType;
import net.katagaitai.phpscan.php.types.PhpArray;
import net.katagaitai.phpscan.php.types.PhpBoolean;
import net.katagaitai.phpscan.php.types.PhpFloat;
import net.katagaitai.phpscan.php.types.PhpInteger;
import net.katagaitai.phpscan.php.types.PhpNull;
import net.katagaitai.phpscan.php.types.PhpObject;
import net.katagaitai.phpscan.php.types.PhpString;
import net.katagaitai.phpscan.scope.GlobalScope;
import net.katagaitai.phpscan.scope.Scope;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.symbol.SymbolStack;
import net.katagaitai.phpscan.util.Constants;
import net.katagaitai.phpscan.util.IniUtils;
import net.katagaitai.phpscan.util.PhpUtils;
import net.katagaitai.phpscan.util.PropertyUtils;
import net.katagaitai.phpscan.util.ScanUtils;
import net.katagaitai.phpscan.util.SymbolUtils;

import org.apache.commons.lang3.ClassUtils;
import org.ini4j.Config;
import org.ini4j.Ini;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.reflect.ClassPath;

// TODO メイン関数終了後に参照カウントが0でないものが存在する
@Log4j2
@RequiredArgsConstructor
public class Interpreter {
	@Getter
	private final File phpFile;
	@Getter
	private final Ini phpIni;

	@Getter
	private List<Interceptor> interceptorList = Lists.newArrayList();
	@Getter
	private GlobalScope globalScope;
	@Getter
	private LinkedList<Scope> scopeStack = Lists.newLinkedList();
	// グローバルスコープ
	// 絶対クラス名：クラス
	private Map<String, PhpNewable> classMap = Maps.newHashMap();
	// 絶対関数名名：関数
	private Map<String, PhpCallable> functionMap = Maps.newHashMap();
	// 名前：定数
	@Getter
	private Map<String, Symbol> constantMap = Maps.newHashMap();
	@Getter
	private List<String> includedFileList = Lists.newArrayList();
	@Getter
	@Setter
	private SourcePosition sourcePosition;
	@Getter
	private int commandCounter;
	@Getter
	@Setter
	private InterpreterContext context;
	@Getter
	@Setter
	private LinkedList<SourcePosition> callStack = Lists.newLinkedList();
	@Getter
	private List<Symbol> autoloadCallbackList = Lists.newArrayList();
	@Getter
	private Map<File, List<PhpClass>> fileClassMap = Maps.newHashMap();
	@Getter
	private Map<File, List<PhpFunction>> fileFunctionMap = Maps.newHashMap();
	@Getter
	private String[] includePaths = new String[] {};
	@Getter
	private Map<String, Map<PhpCallable, Integer>> callCountMap = Maps.newHashMap();
	@Getter
	private SymbolOperator operator;
	@Getter
	private InterpreterStorage storage;
	@Getter
	private List<Vulnerability> vulnerabilityList = Lists.newArrayList();
	@Getter
	@Setter
	private int maxCallStackSize = Constants.MAX_CALL_STACK_SIZE;
	@Getter
	private PhpClass stdClass;

	static {
		// iniファイルの\をそのまま出力させる
		Config.getGlobal().setEscape(false);
	}

	@Getter
	@Setter
	private PhpProject phpProject = new PhpProject();
	@Getter
	private PhpArray globalsArray;
	@Getter
	private PhpArray fileArray;
	@Getter
	private PhpArray serverArray;

	@Setter
	@Getter
	private boolean timeout;
	@Getter
	@Setter
	private List<List<String>> vulnerabilitySignatureList = Lists.newArrayList();
	@Getter
	@Setter
	private boolean useEcho = true;

	public void execute() throws Exception {
		context = new InterpreterContext(this);
		operator = new SymbolOperator(this);
		storage = new InterpreterStorage();
		storage.initialize(operator);
		sourcePosition = new SourcePosition(phpFile, 0, 0);

		String includePathString = phpIni.get("PHP", "include_path");
		if (includePathString == null) {
			includePathString = phpIni.get("Customize", "include_path");
		}
		if (includePathString != null) {
			includePaths = IniUtils.trim(includePathString).split(";");
		}

		globalsArray = operator.createPhpArray();
		Symbol globalsSymbol = operator.createSymbol(globalsArray);
		globalScope = new GlobalScope(this, globalsSymbol);
		scopeStack.push(globalScope);

		fileArray = operator.createPhpArray();
		serverArray = operator.createPhpArray();
		loadBuiltin(globalsSymbol);

		String autoPrependFileString = IniUtils.trim(phpIni.get("PHP", "auto_prepend_file"));
		if (autoPrependFileString != null) {
			File autoPrependFile = new File(autoPrependFileString);
			if (autoPrependFile.exists() && autoPrependFile.isFile()) {
				includedFileList.add(autoPrependFile.getAbsolutePath());
				Compiler autoPrependCompiler = new Compiler(autoPrependFile);
				PhpFunction autoPrependFunction = autoPrependCompiler.compile();
				callFunctionInCurrentScope(autoPrependFunction);
			}
		}

		includedFileList.add(phpFile.getAbsolutePath());
		Compiler compiler = new Compiler(phpFile);
		PhpFunction mainFunction = compiler.compile();
		callFunctionInCurrentScope(mainFunction);

		String autoAppendFileString = IniUtils.trim(phpIni.get("PHP", "auto_append_file"));
		if (autoAppendFileString != null) {
			File autoAppendFile = new File(autoAppendFileString);
			if (autoAppendFile.exists() && autoAppendFile.isFile()) {
				includedFileList.add(autoAppendFile.getAbsolutePath());
				Compiler autoAppendCompiler = new Compiler(autoAppendFile);
				PhpFunction autoPrependFunction = autoAppendCompiler.compile();
				callFunctionInCurrentScope(autoPrependFunction);
			}
		}
		globalScope.clear();

		for (PhpObject phpObject : Lists.newArrayList(storage.getObjectMap().values())) {
			if (phpObject != null && phpObject.getPhpClass() != null) {
				phpObject.getPhpClass().__destruct(this, phpObject);
			}
		}

		//		callNotCalledFunctions();

		ScanUtils.report(this, vulnerabilityList);
	}

	private void callNotCalledFunctions() {
		// 未コールの関数を全部呼ぶ
		for (PhpCallable callable : Maps.newHashMap(functionMap).values()) {
			if (callable instanceof PhpFunction) {
				PhpFunction function = (PhpFunction) callable;
				if (function.getFile() != null && function.getFile().getAbsolutePath().equals(
						phpFile.getAbsolutePath())) {
					// OK
				} else {
					continue;
				}
				if (((PhpFunction) callable).isCalled()) {
					continue;
				}
				SymbolUtils.callFunction(this, callable, Lists.newArrayList());
			}
		}
		// 未コールの静的メソッドを全部呼ぶ
		for (PhpNewable newable : Maps.newHashMap(classMap).values()) {
			if (newable instanceof PhpClass) {
				PhpClass phpClass = (PhpClass) newable;
				if (phpClass.getFile() != null && phpClass.getFile().getAbsolutePath().equals(
						phpFile.getAbsolutePath())) {
					// OK
				} else {
					continue;
				}
				for (PhpCallable callable : phpClass.getStaticMethodMap().values()) {
					if (callable instanceof PhpFunction) {
						PhpFunction function = (PhpFunction) callable;
						if (function.isCalled()) {
							continue;
						}
						SymbolUtils.callStaticMethod(this, callable, Lists.newArrayList());
					}
				}
			}
		}

		// 未コールのメソッドを全部呼ぶ
		for (PhpNewable newable : Maps.newHashMap(classMap).values()) {
			if (newable instanceof PhpClass) {
				PhpClass phpClass = (PhpClass) newable;
				if (phpClass.getFile() != null && phpClass.getFile().getAbsolutePath().equals(
						phpFile.getAbsolutePath())) {
					// OK
				} else {
					continue;
				}
				for (PhpCallable callable : phpClass.getMethodMap().values()) {
					if (callable instanceof PhpFunction) {
						PhpFunction function = (PhpFunction) callable;
						if (function.isCalled()) {
							continue;
						}
						Symbol thisSymbol = SymbolUtils.new_(this, phpClass, Lists.newArrayList());
						for (PhpObject thisObject : operator.extractPhpObject(thisSymbol)) {
							SymbolUtils.callMethod(this, thisObject, callable, Lists.newArrayList());
						}
					}
				}
			}
		}
	}

	private Symbol callFunctionInCurrentScope(PhpFunction phpFunction) {
		InterpreterContext savedContext = context;
		setContext(context.copyForCallFunction(phpFunction, Lists.newArrayList()));
		SourcePosition savedSourcePosition = sourcePosition;
		// 戻り値
		Symbol returnValueSymbol = operator.createSymbol();
		operator.incrementReference(returnValueSymbol);
		context.setReturnValueSymbol(returnValueSymbol);
		try {
			for (Command command : phpFunction.getStaticCommandList()) {
				new CommandDecorator(command).accept(this);
			}
			for (Command command : phpFunction.getCommandList()) {
				new CommandDecorator(command).accept(this);
			}
			Symbol resultSymbol = context.getReturnValueSymbol();
			return resultSymbol;
		} finally {
			sourcePosition = savedSourcePosition;
			setContext(savedContext);
		}
	}

	public void execute(List<Interceptor> interceptorList2) throws Exception {
		interceptorList = interceptorList2;
		execute();
	}

	@SuppressWarnings("unchecked")
	private void loadBuiltin(Symbol globals) {
		// stdClass
		stdClass = new PhpClass("stdclass", null, Lists.newArrayList(), null, null);
		classMap.put("\\stdclass", stdClass);

		globalScope.put(Constants.SESSION_ID_VARIABLE, operator.string());
		// 内部状態
		globalScope.put(Constants.STRTOK_STRING_VARIABLE, operator.string());
		globalScope.put(Constants.MBSTRING_EREG_SERACH_STRING_VARIABLE, operator.string());
		globalScope.put(Constants.MBSTRING_INFO_ARRAY_VARIABLE,
				operator.createSymbol(operator.createPhpArray()));
		PhpArray iconvEncodingArray = operator.createPhpArray();
		operator.putArrayValue(iconvEncodingArray, "input_encoding", "ISO-8859-1");
		operator.putArrayValue(iconvEncodingArray, "output_encoding", "ISO-8859-1");
		operator.putArrayValue(iconvEncodingArray, "internal_encoding", "ISO-8859-1");
		Symbol iconvEncodingSymbol = operator.createSymbol(iconvEncodingArray);
		globalScope.put(Constants.ICONV_ENCODING_ARRAY_VARIABLE, iconvEncodingSymbol);
		globalScope.put(Constants.DBA_HANDLER_ARRAY_VARIABLE,
				operator.createSymbol(operator.createPhpArray()));
		globalScope.put(Constants.SESSION_CALLBACK_ARRAY_VARIABLE,
				operator.createSymbol(operator.createPhpArray()));
		globalScope.put(Constants.SESSION_HANDLER_VARIABLE, operator.createSymbol());
		globalScope.put(Constants.ASSERT_ACTIVE_VARIABLE, operator.createSymbol(1));
		globalScope.put(Constants.ASSERT_CALLBACK_VARIABLE, operator.createNull());

		// $_FILES['hoge']
		loadFileVariable();
		// $_SERVER
		loadServerVaribale();

		// globalScope
		_Superglobals.load(this, scopeStack.peek(), globals);
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			Set<Class<?>> classList = ClassPath.from(loader)
					.getTopLevelClasses("net.katagaitai.phpscan.php.builtin").stream()
					.map(info -> info.load())
					.collect(Collectors.toSet());
			for (Class<?> clazz : classList) {
				if (ClassUtils.getAllSuperclasses(clazz).contains(BuiltinBase.class)) {
					Class<?>[] types = { Interpreter.class };
					Constructor<BuiltinBase> constructor =
							(Constructor<BuiltinBase>) clazz.getConstructor(types);
					Object[] args = { this };
					BuiltinBase instance = constructor.newInstance(args);
					// classMap
					classMap.putAll(instance.getClassMap());
					// functionMap
					functionMap.putAll(instance.getFunctionMap());
					// constantMap
					for (Entry<String, Symbol> entry : instance.getConstantMap().entrySet()) {
						putConstant(entry.getKey(), entry.getValue());
					}
				}
			}
		} catch (IOException | InstantiationException | IllegalAccessException
				| NoSuchMethodException | SecurityException
				| IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
		log.debug("組込みクラス数：" + classMap.size());
		log.debug("組込み関数数：" + functionMap.size());
		log.debug("組込み定数数：" + constantMap.size());
	}

	private void loadFileVariable() {
		operator.putArrayValue(fileArray, "name", operator.string());
		operator.putArrayValue(fileArray, "type", operator.string());
		operator.putArrayValue(fileArray, "tmp_name", operator.string());
		operator.putArrayValue(fileArray, "error", operator.string());
		operator.putArrayValue(fileArray, "size", operator.integer());
	}

	private void loadServerVaribale() {
		File file = new File(PropertyUtils.getString(PropertyUtils.ENTRY_POINT_PATH));
		String dirpath;
		if (file.exists() && file.isDirectory()) {
			dirpath = file.getAbsolutePath();
		} else if (file.exists() && file.isFile()) {
			dirpath = file.getParentFile().getAbsolutePath();
		} else {
			dirpath = Constants.DUMMY_STRING;
		}

		operator.putArrayValue(serverArray, "AUTH_TYPE", operator.string());
		operator.putArrayValue(serverArray, "DOCUMENT_ROOT", operator.createSymbol(dirpath));
		operator.putArrayValue(serverArray, "GATEWAY_INTERFACE", operator.string());
		operator.putArrayValue(serverArray, "HTTPS", operator.string());
		operator.putArrayValue(serverArray, "HTTP_ACCEPT", operator.string());
		operator.putArrayValue(serverArray, "HTTP_ACCEPT_CHARSET", operator.string());
		operator.putArrayValue(serverArray, "HTTP_ACCEPT_ENCODING", operator.string());
		operator.putArrayValue(serverArray, "HTTP_ACCEPT_LANGUAGE", operator.string());
		operator.putArrayValue(serverArray, "HTTP_CONNECTION", operator.string());
		operator.putArrayValue(serverArray, "HTTP_HOST", operator.string());
		operator.putArrayValue(serverArray, "HTTP_REFERER", operator.string());
		operator.putArrayValue(serverArray, "HTTP_USER_AGENT", operator.string());
		operator.putArrayValue(serverArray, "ORIG_PATH_INFO", operator.string());
		operator.putArrayValue(serverArray, "PATH_INFO", operator.string());
		operator.putArrayValue(serverArray, "PATH_TRANSLATED", operator.string());
		operator.putArrayValue(serverArray, "PHP_AUTH_DIGEST", operator.string());
		operator.putArrayValue(serverArray, "PHP_AUTH_PW", operator.string());
		operator.putArrayValue(serverArray, "PHP_AUTH_USER", operator.string());
		operator.putArrayValue(serverArray, "PHP_SELF", operator.string());
		operator.putArrayValue(serverArray, "QUERY_STRING", operator.string());
		operator.putArrayValue(serverArray, "REDIRECT_REMOTE_USER", operator.string());
		operator.putArrayValue(serverArray, "REMOTE_ADDR", operator.string());
		operator.putArrayValue(serverArray, "REMOTE_HOST", operator.string());
		operator.putArrayValue(serverArray, "REMOTE_PORT", operator.integer());
		operator.putArrayValue(serverArray, "REMOTE_USER", operator.string());
		operator.putArrayValue(serverArray, "REQUEST_METHOD", operator.string());
		operator.putArrayValue(serverArray, "REQUEST_TIME", operator.integer());
		operator.putArrayValue(serverArray, "REQUEST_TIME_FLOAT", operator.integer());
		operator.putArrayValue(serverArray, "REQUEST_URI", operator.string());
		operator.putArrayValue(serverArray, "SCRIPT_FILENAME", operator.string());
		operator.putArrayValue(serverArray, "SCRIPT_NAME", operator.string());
		operator.putArrayValue(serverArray, "SERVER_ADDR", operator.string());
		operator.putArrayValue(serverArray, "SERVER_ADMIN", operator.string());
		operator.putArrayValue(serverArray, "SERVER_NAME", operator.string());
		operator.putArrayValue(serverArray, "SERVER_PORT", operator.integer());
		operator.putArrayValue(serverArray, "SERVER_PROTOCOL", operator.string());
		operator.putArrayValue(serverArray, "SERVER_SIGNATURE", operator.string());
		operator.putArrayValue(serverArray, "SERVER_SOFTWARE", operator.string());
		operator.putArrayValue(serverArray, "argc", operator.integer());
		operator.putArrayValue(serverArray, "argv", operator.string());
	}

	public void accept(AddToReturnValue addToReturnValue) {
		String value = addToReturnValue.getValue();
		Symbol valueSymbol = getSymbolOrCreate(value);
		operator.merge(context.getReturnValueSymbol(), valueSymbol);
	}

	public void accept(Assign assign) {
		String left = assign.getLeft();
		Symbol leftSymbol = getSymbolOrCreate(left);
		String right = assign.getRight();
		Symbol rightSymbol = getSymbolOrCreate(right);
		operator.assign(leftSymbol, rightSymbol);
	}

	public void accept(AssignStatic assignStatic) {
		String left = assignStatic.getLeft();
		String right = assignStatic.getRight();
		Symbol rightSymbol = getSymbolOrCreate(right);
		if (context.getPhpFunction() != null) {
			PhpFunction phpFunction = context.getPhpFunction();
			if (storage.getStaticScopeMap().get(phpFunction) == null) {
				storage.getStaticScopeMap().put(phpFunction, new Scope(this));
			}
			storage.getStaticScopeMap().get(phpFunction).put(left, rightSymbol);
		}
	}

	public void accept(AssignArgument assignArgument) {
		String argument = assignArgument.getArgument();
		int index = assignArgument.getIndex();
		List<Symbol> argumentSymbolList = context.getArgumentSymbolList();
		if (index < argumentSymbolList.size()) {
			// コピー
			scopeStack.peek().put(argument, operator.copy(argumentSymbolList.get(index)));
		}
	}

	public void accept(AssignArgumentReference assignArgumentReference) {
		String argument = assignArgumentReference.getArgument();
		int index = assignArgumentReference.getIndex();
		List<Symbol> argumentSymbolList = context.getArgumentSymbolList();
		if (index < argumentSymbolList.size()) {
			// そのまま
			scopeStack.peek().put(argument, argumentSymbolList.get(index));
		}
	}

	public void accept(CallFunction callFunction) {
		String result = callFunction.getResult();
		String function = callFunction.getFunction();
		Symbol functionSymbol = getSymbolOrCreate(function);
		List<String> argumentList = callFunction.getArgumentList();
		List<Symbol> argumentSymbolList = Lists.newArrayList();
		for (String argument : argumentList) {
			// 参照渡しに備えてシンボルは作る
			Symbol argumentSymbol = getSymbolOrCreate(argument);
			argumentSymbolList.add(argumentSymbol);
		}
		List<PhpCallable> callableList =
				SymbolUtils.getFunctionList(this, functionSymbol, argumentSymbolList.size(), false);
		Symbol returnValueSymbol = operator.createSymbol();
		scopeStack.peek().put(result, returnValueSymbol);
		if (callableList.size() == 0) {
			log.warn("不明な関数：" + function + " [" + sourcePosition + "]");
			for (PhpObject phpObject : operator.extractPhpObject(functionSymbol)) {
				Symbol returnSymbol =
						phpObject.getPhpClass().__invoke(this, phpObject, context.getArgumentSymbolList());
				operator.merge(returnValueSymbol, returnSymbol);
			}
		}
		for (PhpCallable callable : callableList) {
			operator.merge(returnValueSymbol,
					SymbolUtils.callFunction(this, callable, argumentSymbolList));
		}
		if (operator.getTypeSet(returnValueSymbol).size() == 0) {
			operator.setTypeSet(returnValueSymbol, Sets.newHashSet(PhpNull.getNull()));
		}
		operator.gc(this);
	}

	private boolean isVarName(String name) {
		return name.startsWith("$");
	}

	public void accept(CallMethod callMethod) {
		String result = callMethod.getResult();
		String method = callMethod.getMethod();
		Symbol methodSymbol = getSymbolOrCreate(method);
		List<String> argumentList = callMethod.getArgumentList();
		List<Symbol> argumentSymbolList = Lists.newArrayList();
		for (String argument : argumentList) {
			// 参照渡しに備えてシンボルは作る
			Symbol argumentSymbol = getSymbolOrCreate(argument);
			argumentSymbolList.add(argumentSymbol);
		}
		Symbol returnValueSymbol = operator.createSymbol();
		scopeStack.peek().put(result, returnValueSymbol);
		String object = callMethod.getObject();
		Symbol objectSymbol = getSymbolOrCreate(object);
		operator.merge(returnValueSymbol,
				SymbolUtils.callMethod(this, objectSymbol, methodSymbol, argumentSymbolList));
		if (operator.getTypeSet(returnValueSymbol).size() == 0) {
			operator.setTypeSet(returnValueSymbol, Sets.newHashSet(PhpNull.getNull()));
		}
		operator.gc(this);
	}

	public void accept(CallStaticMethod callStaticMethod) {
		String result = callStaticMethod.getResult();
		String clazz = callStaticMethod.getClazz();
		Symbol clazzSymbol = getSymbolOrCreate(clazz);
		String method = callStaticMethod.getMethod();
		Symbol methodSymbol = getSymbolOrCreate(method);
		List<String> argumentList = callStaticMethod.getArgumentList();
		List<Symbol> argumentSymbolList = Lists.newArrayList();
		for (String argument : argumentList) {
			// 参照渡しに備えてシンボルは作る
			Symbol argumentSymbol = getSymbolOrCreate(argument);
			argumentSymbolList.add(argumentSymbol);
		}
		Symbol returnValueSymbol = operator.createSymbol();
		scopeStack.peek().put(result, returnValueSymbol);
		List<PhpCallable> callableList = SymbolUtils.getStaticMethodList(
				this, clazzSymbol, methodSymbol, argumentSymbolList.size(), false);
		if (callableList.size() == 0) {
			log.warn("不明な静的メソッド：" + callStaticMethod);
			for (PhpNewable phpClass : SymbolUtils.getClassList(this, clazzSymbol)) {
				Symbol returnSymbol = phpClass.__callStatic(this, methodSymbol, argumentSymbolList);
				operator.merge(returnValueSymbol, returnSymbol);
			}
		}
		for (PhpCallable phpCallable : callableList) {
			operator.merge(returnValueSymbol,
					SymbolUtils.callStaticMethod(this, phpCallable, argumentSymbolList));
		}
		if (operator.getTypeSet(returnValueSymbol).size() == 0) {
			operator.setTypeSet(returnValueSymbol, Sets.newHashSet(PhpNull.getNull()));
		}
		operator.gc(this);
	}

	public void accept(Cast cast) {
		String result = cast.getResult();
		CastType casetType = cast.getType();
		String target = cast.getTarget();
		Symbol targetSymbol = getSymbolOrCreate(target);
		scopeStack.peek().put(result, operator.cast(targetSymbol, casetType));
	}

	public void accept(Clone clone) {
		String result = clone.getResult();
		String target = clone.getTarget();
		Symbol targetSymbol = getSymbolOrCreate(target);
		Symbol resultSymbol = operator.createSymbol();
		scopeStack.peek().put(result, resultSymbol);
		for (PhpObject phpObject : operator.extractPhpObject(targetSymbol)) {
			operator.merge(resultSymbol,
					phpObject.getPhpClass().__clone(this, phpObject));
		}
		operator.merge(resultSymbol, operator.clone(targetSymbol));
		if (operator.getTypeSet(resultSymbol).size() == 0) {
			operator.setTypeSet(resultSymbol, Sets.newHashSet(PhpNull.getNull()));
		}
	}

	public void accept(CommentSource commentSource) {
		sourcePosition = new SourcePosition(
				commentSource.getFile(), commentSource.getStart(), commentSource.getLength());
	}

	public void accept(CreateArray createArray) {
		String array = createArray.getArray();
		Symbol resultSymbol = operator.createSymbol(Sets.newHashSet(operator.createPhpArray()));
		scopeStack.peek().put(array, resultSymbol);
	}

	public void accept(CreateClass createClass) {
		PhpClass phpClass = createClass.getPhpClass();
		phpClass.setNamespace(context.getNamespace());
		phpClass.setUseMap(context.getUseMap());
		String absoluteClassName = getAbsoluteClassName(phpClass.getName());
		classMap.put(absoluteClassName.toLowerCase(), phpClass);

		InterpreterContext savedContext = context;
		PhpFunction phpFunction = phpClass.getInitializer();
		setContext(context.copyForCreateClass(phpClass, phpFunction));
		for (Command command : phpFunction.getStaticCommandList()) {
			new CommandDecorator(command).accept(this);
		}
		setContext(savedContext);

		if (fileClassMap.get(context.getFile()) == null) {
			fileClassMap.put(context.getFile(), Lists.newArrayList());
		}
		fileClassMap.get(context.getFile()).add(phpClass);
	}

	public void accept(CreateFunction createFunction) {
		PhpFunction phpFunction = createFunction.getPhpFunction();
		phpFunction.setNamespace(context.getNamespace());
		phpFunction.setUseMap(context.getUseMap());
		String absoluteFunctionName = getAbsoluteFunctionName(phpFunction.getName());
		functionMap.put(absoluteFunctionName.toLowerCase(), phpFunction);

		InterpreterContext savedContext = context;
		setContext(context.copyForCreateFunction(phpFunction));
		for (Command command : phpFunction.getStaticCommandList()) {
			new CommandDecorator(command).accept(this);
		}
		setContext(savedContext);

		// autoload
		if (absoluteFunctionName.endsWith("\\__autoload")) {
			autoloadCallbackList.add(operator.createSymbol(absoluteFunctionName));
		}

		if (fileFunctionMap.get(context.getFile()) == null) {
			fileFunctionMap.put(context.getFile(), Lists.newArrayList());
		}
		fileFunctionMap.get(context.getFile()).add(phpFunction);
	}

	public void accept(CreateMethod createMethod) {
		PhpFunction phpFunction = createMethod.getPhpFunction();
		phpFunction.setNamespace(context.getNamespace());
		phpFunction.setUseMap(context.getUseMap());
		phpFunction.setPhpClass(context.getPhpClass());
		PhpClass phpClass = context.getPhpClass();
		if (phpClass == null) {
			log.warn("メソッドを登録できません。：" + createMethod);
		} else {
			phpClass.putMethod(phpFunction);
		}
	}

	public void accept(CreateStaticMethod createStaticMethod) {
		PhpFunction phpFunction = createStaticMethod.getPhpFunction();
		phpFunction.setNamespace(context.getNamespace());
		phpFunction.setUseMap(context.getUseMap());
		phpFunction.setPhpClass(context.getPhpClass());
		PhpClass phpClass = context.getPhpClass();
		if (phpClass == null) {
			log.warn("メソッドを登録できません。：" + createStaticMethod);
		} else {
			phpClass.putStaticMethod(phpFunction);
		}
	}

	public void accept(Echo echo) {
		if (useEcho) {
			String argument = echo.getArgument();
			Symbol argumentSymbol = getSymbolOrCreate(argument);
			for (String string : operator.getJavaStringList(argumentSymbol)) {
				System.out.print(string);
			}
		}
	}

	public void accept(Else_ else_) {
		// [[if], [org], ...]
		pushStack(1);
	}

	public void accept(EndLoop endLoop) {
		context.getInLoopStack().pop();
	}

	public void accept(EndNamespace endNamespace) {
	}

	public void accept(GetArrayValue getArrayValue) {
		String result = getArrayValue.getResult();
		String array = getArrayValue.getArray();
		Symbol arraySymbol = getSymbolOrCreate(array);
		String name = getArrayValue.getName();
		Symbol keySymbol = getSymbolOrCreate(name);
		if (context.getInLoopStack().size() > 0
				&& context.getInLoopStack().peek() == true
				&& isVarName(name) && operator.isNumber(keySymbol)) {
			// $array[$i]
			scopeStack.peek().put(result, operator.getArrayValueOfNumber(arraySymbol));
		} else {
			scopeStack.peek().put(result, operator.getArrayValue(arraySymbol, keySymbol));
		}
	}

	public void accept(GetClassConstant getClassConstant) {
		String result = getClassConstant.getResult();
		String clazz = getClassConstant.getClazz();
		String name = getClassConstant.getName();
		Symbol clazzSymbol = getSymbolOrCreate(clazz);
		Symbol resultSymbol = operator.createSymbol();
		scopeStack.peek().put(result, resultSymbol);
		for (String className : operator.getJavaStringList(clazzSymbol)) {
			String absoluteClassName = getAbsoluteClassName(className);
			operator.merge(resultSymbol, constantMap.get(absoluteClassName + "::" + name));
		}
		if (operator.getTypeSet(resultSymbol).size() == 0) {
			operator.setTypeSet(resultSymbol, Sets.newHashSet(PhpNull.getNull()));
		}
	}

	public void accept(GetConstant getConstant) {
		String result = getConstant.getResult();
		String name = getConstant.getName();
		Symbol symbol;
		if (name.equals("__FILE__")) {
			symbol = getFilePathSymbol();
		} else if (name.equals("__LINE__")) {
			symbol = getLineNoSymbol();
		} else if (name.equals("__CLASS__")) {
			String string;
			if (context.getPhpClass() == null) {
				string = "";
			} else {
				string = context.getPhpClass().getName();
			}
			symbol = operator.createSymbol(new PhpString(string));
		} else if (name.equals("__FUNCTION__") || name.equals("__METHOD__")) {
			String string;
			if (context.getPhpFunction() == null) {
				string = "";
			} else {
				string = context.getPhpFunction().getName();
			}
			symbol = operator.createSymbol(new PhpString(string));
		} else if (name.equals("__TRAIT__")) {
			log.warn("未実装：" + name + " [" + sourcePosition + "]");
			String string = "";
			symbol = operator.createSymbol(new PhpString(string));
		} else if (name.equals("__DIR__")) {
			String string;
			if (context.getFile() == null) {
				string = "";
			} else {
				string = context.getFile().toPath().getParent().toAbsolutePath().toString();
			}
			symbol = operator.createSymbol(new PhpString(string));
		} else if (name.equals("__NAMESPACE__")) {
			String string;
			if (context.getNamespace() == null) {
				string = "";
			} else {
				// 先頭の\は含まない
				string = context.getNamespace().substring(1);
			}
			symbol = operator.createSymbol(new PhpString(string));
		} else {
			// $a = A;
			symbol = SymbolUtils.getConstant(this, name);
			if (symbol == null) {
				log.warn("不明な定数：" + name + " [" + sourcePosition + "]");
				symbol = operator.createSymbol(name);
			}
		}
		scopeStack.peek().put(result, symbol);
	}

	public void accept(GetFieldValue getFieldValue) {
		String result = getFieldValue.getResult();
		String object = getFieldValue.getObject();
		Symbol objectSymbol;
		if (object.equals("$this")) {
			objectSymbol = context.getThisSymbol();
		} else {
			objectSymbol = getSymbolOrCreate(object);
		}
		String name = getFieldValue.getName();
		Symbol keySymbol = getSymbolOrCreate(name);
		Symbol symbol = operator.getFieldValue(objectSymbol, keySymbol);
		scopeStack.peek().put(result, symbol);
		if (operator.getTypeSet(symbol).size() == 0) {
			for (PhpObject phpObject : operator.extractPhpObject(objectSymbol)) {
				operator.merge(symbol, phpObject.getPhpClass().__get(this, phpObject, keySymbol));
			}
		}
		if (operator.getTypeSet(symbol).size() == 0) {
			operator.setTypeSet(symbol, Sets.newHashSet(PhpNull.getNull()));
		}
	}

	public void accept(AssignReference getReference) {
		String left = getReference.getLeft();
		String right = getReference.getRight();
		Symbol rightSymbol = getSymbolOrCreate(right);
		scopeStack.peek().put(left, rightSymbol);
	}

	public void accept(GetStaticFieldValue getStaticFieldValue) {
		String result = getStaticFieldValue.getResult();
		String clazz = getStaticFieldValue.getClazz();
		String name = getStaticFieldValue.getName();
		List<String> classNameList = Lists.newArrayList();
		Symbol clazzSymbol = getSymbolOrCreate(clazz);
		for (PhpAnyType type : operator.getTypeSet(clazzSymbol)) {
			String className;
			if (type instanceof PhpObject) {
				className = ((PhpObject) type).getPhpClass().getAbsoulteClassName();
			} else {
				className = operator.toPhpString(type).getString();
			}
			classNameList.add(className);
		}
		Symbol resultSymbol = operator.createSymbol();
		scopeStack.peek().put(result, resultSymbol);
		Symbol nameSymbol = getSymbolOrCreate(name);
		for (String className : classNameList) {
			for (PhpNewable phpClass : SymbolUtils.getClassList(this, className)) {
				operator.merge(resultSymbol, phpClass.getStaticField(this, nameSymbol));
			}
		}
		if (operator.getTypeSet(resultSymbol).size() == 0) {
			operator.setTypeSet(resultSymbol, Sets.newHashSet(PhpNull.getNull()));
		}
	}

	public void accept(If_ if_) {
		// [[org], ...]
		pushStack(0);
	}

	private void pushStack(int i) {
		List<String> ignoreList = getIgnoreList();
		for (Entry<String, SymbolStack> entry : scopeStack.peek().getMap().entrySet()) {
			String key = entry.getKey();
			if (key.startsWith("$" + Constants.PREFIX) || ignoreList.contains(key)) {
				continue;
			}
			entry.getValue().pushCopy(i);
		}
		// 静的変数
		if (context.getPhpFunction() != null
				&& storage.getStaticScopeMap().get(context.getPhpFunction()) != null) {
			Scope staticVariableScope = storage.getStaticScopeMap().get(context.getPhpFunction());
			for (Entry<String, SymbolStack> entry : staticVariableScope.getMap().entrySet()) {
				String key = entry.getKey();
				if (key.startsWith("$" + Constants.PREFIX)) {
					continue;
				}
				entry.getValue().pushCopy(i);
			}
		}
	}

	public void accept(IfElseEnd ifElseEnd) {
		Map<String, SymbolStack> map = Maps.newHashMap(scopeStack.peek().getMap());
		List<String> ignoreList = getIgnoreList();
		for (Entry<String, SymbolStack> entry : map.entrySet()) {
			String key = entry.getKey();
			if (key.startsWith("$" + Constants.PREFIX) || ignoreList.contains(key)) {
				continue;
			}
			entry.getValue().mergeCopies();
		}
		// 静的変数
		if (context.getPhpFunction() != null
				&& storage.getStaticScopeMap().get(context.getPhpFunction()) != null) {
			Scope staticVariableScope = storage.getStaticScopeMap().get(context.getPhpFunction());
			for (Entry<String, SymbolStack> entry : staticVariableScope.getMap().entrySet()) {
				String key = entry.getKey();
				if (key.startsWith("$" + Constants.PREFIX)) {
					continue;
				}
				entry.getValue().mergeCopies();
			}
		}
	}

	private List<String> getIgnoreList() {
		List<String> ignoreList =
				Lists.newArrayList(Constants.SUPERGLOBAL_NAMES);
		ignoreList.remove("$_COOKIE");
		ignoreList.remove("$_FILES");
		ignoreList.remove("$_GET");
		ignoreList.remove("$_POST");
		ignoreList.remove("$_REQUEST");
		ignoreList.remove("$_SESSION");
		return ignoreList;
	}

	public void accept(Include include) {
		String result = include.getResult();
		String path = include.getPath();
		Symbol pathSymbol = getSymbolOrCreate(path);
		Symbol resultSymbol = operator.createSymbol();
		scopeStack.peek().put(result, resultSymbol);
		for (String string : operator.getJavaStringList(pathSymbol)) {
			operator.merge(resultSymbol, include(string, false));
		}
		if (operator.getTypeSet(resultSymbol).size() == 0) {
			operator.setTypeSet(resultSymbol, Sets.newHashSet(PhpNull.getNull()));
		}
	}

	public Symbol include(String filename, boolean includeOnce) {
		Symbol resultSymbol = operator.createSymbol();
		// 絶対パス
		File absoluteFile = new File(filename);
		if (absoluteFile.exists() && absoluteFile.isFile()) {
			operator.merge(resultSymbol, include(absoluteFile, includeOnce));
			return resultSymbol;
		}
		// 相対パス
		// include_path
		for (String includePath : includePaths) {
			File relativeFile = new File(includePath, filename);
			if (relativeFile.exists() && relativeFile.isFile()) {
				operator.merge(resultSymbol, include(relativeFile, includeOnce));
				return resultSymbol;
			}
		}
		// スクリプトと同じディレクトリ
		File relativeFile2 = new File(getFile().getParentFile(), filename);
		if (relativeFile2.exists() && relativeFile2.isFile()) {
			operator.merge(resultSymbol, include(relativeFile2, includeOnce));
			return resultSymbol;
		}
		// TODO ワーキングディレクトリを見ていない
		log.warn("不明なインクルードファイル：" + filename + " [" + sourcePosition + "]");
		return resultSymbol;
	}

	public File getFile() {
		File file = context.getFile();
		if (file == null) {
			file = sourcePosition.getFile();
		}
		if (file == null) {
			file = phpFile;
		}
		return file;
	}

	public Symbol include(File file, boolean includeOnce) {
		// 正規化
		File absFile = file.getAbsoluteFile();
		if (absFile.exists() && absFile.isFile()) {
			if (includeOnce && includedFileList.contains(absFile.getAbsolutePath())) {
				return operator.createNull();
			}
			LinkedList<String> includeStack = storage.getIncludeStack();
			if (includeStack.contains(absFile.getAbsolutePath())) {
				log.info("再帰：" + absFile.getAbsolutePath() + " [" + sourcePosition + "]");
				return operator.createNull();
			}
			if (callStack.size() >= maxCallStackSize) {
				log.info("コールスタックが上限に達しました。：" + absFile.getAbsolutePath()
						+ " [" + sourcePosition + "]");
				return operator.createNull();
			}
			includeStack.push(absFile.getAbsolutePath());
			callStack.push(sourcePosition);
			log.info("インクルード：" + absFile);
			if (includedFileList.contains(absFile.getAbsolutePath())) {
				// pass
			} else {
				includedFileList.add(absFile.getAbsolutePath());
			}
			try {
				Compiler compiler = new Compiler(absFile);
				PhpFunction phpFunction = compiler.compile();
				return callFunctionInCurrentScope(phpFunction);
			} catch (Exception e) {
				log.warn("", e);
			} finally {
				callStack.pop();
				includeStack.pop();
			}
		}
		return null;
	}

	public void accept(IncludeOnce includeOnce) {
		String result = includeOnce.getResult();
		String path = includeOnce.getPath();
		Symbol pathSymbol = getSymbolOrCreate(path);
		Symbol resultSymbol = operator.createSymbol();
		scopeStack.peek().put(result, resultSymbol);
		for (String string : operator.getJavaStringList(pathSymbol)) {
			operator.merge(resultSymbol, include(string, true));
		}
		if (operator.getTypeSet(resultSymbol).size() == 0) {
			operator.setTypeSet(resultSymbol, Sets.newHashSet(PhpNull.getNull()));
		}
	}

	public void accept(Instanceof instanceof1) {
		String result = instanceof1.getResult();
		String target = instanceof1.getTarget();
		Symbol targetSymbol = getSymbolOrCreate(target);
		String clazz = instanceof1.getClazz();
		Symbol clazzSymbol = getSymbolOrCreate(clazz);
		List<PhpNewable> newableList = SymbolUtils.getClassList(this, clazzSymbol);
		for (PhpNewable newable : newableList) {
			boolean containsNewable = false;
			for (PhpObject phpObject : operator.extractPhpObject(targetSymbol)) {
				if (phpObject.getPhpClass().equals(newable)) {
					containsNewable = true;
					break;
				}
			}
			if (containsNewable) {
				// OK
			} else {
				Symbol symbol = SymbolUtils.new_(this, newable, Lists.newArrayList());
				operator.merge(targetSymbol, symbol);
			}
		}
		scopeStack.peek().put(result, operator.createTrue());
	}

	public void accept(LoadGlobal loadGlobal) {
		String name = loadGlobal.getName();
		Symbol symbol = globalScope.getOrPhpNull(name);
		scopeStack.peek().put(name, symbol);
	}

	public void accept(Mix mix) {
		String result = mix.getResult();
		String left = mix.getLeft();
		Symbol leftSymbol = getSymbolOrCreate(left);
		Operator op = mix.getOperator();
		String right = mix.getRight();
		Symbol rightSymbol = getSymbolOrCreate(right);
		scopeStack.peek().put(result, operator.mix(leftSymbol, op, rightSymbol));
	}

	public void accept(StartNamespace namespace) {
		String namespace_ = namespace.getNamespace();
		context.setNamespace(namespace_);
	}

	public void accept(New new1) {
		String result = new1.getResult();
		List<String> argumentList = new1.getArgumentList();
		List<Symbol> argumentSymbolList = Lists.newArrayList();
		for (String argument : argumentList) {
			// 参照渡しに備えてシンボルは作る
			Symbol argumentSymbol = getSymbolOrCreate(argument);
			argumentSymbolList.add(argumentSymbol);
		}
		String clazz = new1.getClazz();
		List<PhpNewable> phpClassList = Lists.newArrayList();
		List<String> classNameList = Lists.newArrayList();
		Symbol clazzSymbol = getSymbolOrCreate(clazz);
		for (String className : operator.getJavaStringList(clazzSymbol)) {
			classNameList.add(className);
			for (PhpNewable phpClass : SymbolUtils.getClassList(this, className)) {
				phpClassList.add(phpClass);
			}
			if (phpClassList.size() == 0) {
				// autoload
				autoload(className);
				// try again
				for (PhpNewable phpClass : SymbolUtils.getClassList(this, className)) {
					phpClassList.add(phpClass);
				}
			}
		}
		if (phpClassList.size() == 0) {
			log.warn("不明なクラス：" + clazz + " [" + sourcePosition + "]");
		}
		Symbol resultSymbol = operator.createSymbol();
		scopeStack.peek().put(result, resultSymbol);
		for (PhpNewable phpClass : phpClassList) {
			operator.merge(resultSymbol, SymbolUtils.new_(this, phpClass, argumentSymbolList));
		}
		if (operator.getTypeSet(resultSymbol).size() == 0) {
			operator.setTypeSet(resultSymbol, Sets.newHashSet(PhpNull.getNull()));
		}
	}

	public void autoload(String className) {
		for (Symbol callbackSymbol : Lists.newArrayList(autoloadCallbackList)) {
			SymbolUtils.callCallable(this, callbackSymbol,
					Lists.newArrayList(operator.createSymbol(new PhpString(className))));
		}
	}

	public void accept(PutArrayValue putArrayValue) {
		String array = putArrayValue.getArray();
		Symbol arraySymbol = getSymbolOrCreate(array);
		String key = putArrayValue.getKey();
		Symbol keySymbol;
		if (key == null) {
			keySymbol = operator.getNextIndex(arraySymbol);
		} else {
			keySymbol = getSymbolOrCreate(key);
		}
		String value = putArrayValue.getValue();
		Symbol valueSymbol = getSymbolOrCreate(value);
		operator.putArrayValue(arraySymbol, keySymbol, valueSymbol);
	}

	public void accept(PutConstant putConstant) {
		// const A = 1;
		String left = putConstant.getLeft();
		String right = putConstant.getRight();
		Symbol rightSymbol = getSymbolOrCreate(right);
		String absolute = getAbsoluteConstantName(context.getPhpClass(), left);
		putConstant(absolute, rightSymbol);
	}

	public void putConstant(String absolute, Symbol rightSymbol) {
		operator.incrementReference(rightSymbol);
		constantMap.put(absolute, rightSymbol);
	}

	public void accept(PutFieldValue putFieldValue) {
		String object = putFieldValue.getObject();
		Symbol objectSymbol;
		if (object.equals("$this")) {
			objectSymbol = context.getThisSymbol();
		} else {
			objectSymbol = getSymbolOrCreate(object);
		}
		String name = putFieldValue.getName();
		Symbol keySymbol = getSymbolOrCreate(name);
		String value = putFieldValue.getValue();
		Symbol valueSymbol = getSymbolOrCreate(value);
		if (operator.getTypeSet(valueSymbol).size() == 0) {
			for (PhpObject phpObject : operator.extractPhpObject(objectSymbol)) {
				phpObject.getPhpClass().__set(this, phpObject, keySymbol, valueSymbol);
			}
		} else {
			operator.putFieldValue(objectSymbol, keySymbol, valueSymbol);
		}
	}

	public void accept(PutStaticFieldValue putStaticFieldValue) {
		String clazz = putStaticFieldValue.getClazz();
		String name = putStaticFieldValue.getName();
		String value = putStaticFieldValue.getValue();
		Symbol valueSymbol = getSymbolOrCreate(value);
		List<String> classNameList;
		if (clazz.equals("$this")) {
			classNameList = Lists.newArrayList(
					context.getThisObject().getPhpClass().getAbsoulteClassName());
		} else {
			Symbol clazzSymbol = getSymbolOrCreate(clazz);
			classNameList = Lists.newArrayList();
			for (PhpAnyType type : operator.getTypeSet(clazzSymbol)) {
				if (type instanceof PhpObject) {
					classNameList.add(((PhpObject) type).getPhpClass().getAbsoulteClassName());
				} else {
					classNameList.add(operator.toPhpString(type).getString());
				}
			}
		}
		Symbol fieldSymbol = getSymbolOrCreate(name);
		for (String className : classNameList) {
			for (PhpNewable phpClass : SymbolUtils.getClassList(this, className)) {
				if (phpClass != null && phpClass instanceof PhpClass) {
					((PhpClass) phpClass).putStaticField(fieldSymbol, valueSymbol);
				}
			}
		}
	}

	public void accept(PutVariable putVariable) {
		String name = putVariable.getName();
		String value = putVariable.getValue();
		Symbol nameSymbol = getSymbolOrCreate(name);
		Symbol valueSymbol = getSymbolOrCreate(value);
		for (String nameString : operator.getJavaStringList(nameSymbol)) {
			Symbol symbol = getSymbolOrCreate("$" + nameString);
			operator.assign(symbol, valueSymbol);
		}
	}

	public void accept(GetVariable getVariable) {
		String result = getVariable.getResult();
		String name = getVariable.getName();
		Symbol nameSymbol = getSymbolOrCreate(name);
		Symbol resultSymbol = operator.createSymbol();
		scopeStack.peek().put(result, resultSymbol);
		for (String string : operator.getJavaStringList(nameSymbol)) {
			Symbol symbol = scopeStack.peek().getOrPhpNull("$" + string);
			if (operator.isNull(symbol)) {
				// TODO A::$$a用に入れたが副作用があるかも
				operator.merge(resultSymbol, operator.createSymbol(string));
			} else {
				operator.merge(resultSymbol, symbol);
			}
		}
		if (operator.getTypeSet(resultSymbol).size() == 0) {
			operator.setTypeSet(resultSymbol, Sets.newHashSet(PhpNull.getNull()));
		}
	}

	public void accept(StartLoop startLoop) {
		context.getInLoopStack().push(true);
	}

	public void accept(Use use) {
		String namespace = use.getNamespace();
		if (namespace.startsWith(".\\")) {
			namespace.replaceFirst("\\.", context.getNamespace());
		}
		if (!namespace.startsWith("\\")) {
			namespace = "\\" + namespace;
		}
		String alias = use.getAlias();
		context.getUseMap().put(namespace, alias);
	}

	public void incrementCounter() {
		commandCounter += 1;
	}

	//	public Symbol getSymbolOrCreate(String name) {
	//		Symbol symbol = getSymbolFromLiteral(name);
	//		if (symbol == null) {
	//			if (isVarName(name)) {
	//				// 静的変数
	//				if (symbol == null && context.getPhpFunction() != null
	//						&& storage.getStaticScopeMap().get(context.getPhpFunction()) != null) {
	//					Scope scope2 = storage.getStaticScopeMap().get(context.getPhpFunction());
	//					symbol = scope2.getOrJavaNull(name);
	//				}
	//				if (symbol == null) {
	//					// ローカル変数
	//					Scope scope = scopeStack.peek();
	//					symbol = scope.getOrPhpNull(name);
	//				}
	//			} else {
	//				symbol = getSymbolFromConstant(name);
	//			}
	//		}
	//		return symbol;
	//	}

	private Symbol getSymbolFromConstant(String name) {
		// 定数
		Symbol symbol = SymbolUtils.getConstant(this, name);
		if (symbol == null) {
			symbol = operator.createSymbol(new PhpString(name));
		}
		return symbol;
	}

	private Symbol getSymbolFromLiteral(String name) {
		if (name == null) {
			return operator.createSymbol(PhpNull.getNull());
		} else if (name.startsWith(Constants.PREFIX_INTEGER_LITERAL)) {
			long d = Long.parseLong(name.substring(Constants.PREFIX_INTEGER_LITERAL.length()));
			Symbol result = operator.createSymbol(new PhpInteger(d));
			return result;
		} else if (name.startsWith(Constants.PREFIX_FLOAT_LITERAL)) {
			double d = Double.parseDouble(name.substring(Constants.PREFIX_FLOAT_LITERAL.length()));
			Symbol result = operator.createSymbol(new PhpFloat(d));
			return result;
		} else if (name.startsWith(Constants.PREFIX_STRING_LITERAL)) {
			String s = name.substring(Constants.PREFIX_STRING_LITERAL.length());
			Symbol result = operator.createSymbol(new PhpString(s));
			return result;
		} else if (name.startsWith(Constants.PREFIX_BOOLEAN_LITERAL)) {
			boolean b = Boolean.parseBoolean(name.substring(Constants.PREFIX_BOOLEAN_LITERAL.length()));
			Symbol result = operator.createSymbol(b ? PhpBoolean.getTrue() : PhpBoolean.getFalse());
			return result;
		} else if (name.startsWith(Constants.NULL_LITERAL)) {
			Symbol result = operator.createSymbol(PhpNull.getNull());
			return result;
		} else {
			return null;
		}
	}

	// 参照による代入を実現するために、既存のものがあればそれを取得する仕組みを用意している
	public Symbol getSymbolOrCreate(String name) {
		Symbol symbol = getSymbolFromLiteral(name);
		if (symbol == null) {
			if (isVarName(name)) {
				// 静的変数
				if (symbol == null && context.getPhpFunction() != null
						&& storage.getStaticScopeMap().get(context.getPhpFunction()) != null) {
					Scope scope2 = storage.getStaticScopeMap().get(context.getPhpFunction());
					symbol = scope2.getOrJavaNull(name);
				}
				if (symbol == null) {
					// ローカル変数
					Scope scope = scopeStack.peek();
					symbol = scope.getOrCreate(name);
					// TODO 常にマージするべきだが、遅すぎるので条件付にする。
					if (operator.isNull(symbol)) {
						Symbol symbol2 = scope.getOrJavaNull(Constants.DUMMY_ANY_STRING);
						operator.merge(symbol, symbol2);
					}
				}
			} else {
				symbol = getSymbolFromConstant(name);
			}
		}
		return symbol;
	}

	public String getAbsoluteClassName(String name) {
		if (name.equalsIgnoreCase("self") || name.equalsIgnoreCase("parent") || name.equalsIgnoreCase("static")) {
			return name;
		}
		return PhpUtils.getAbsolute(name, context.getNamespace(), context.getUseMap());
	}

	public String getAbsoluteFunctionName(String name) {
		return PhpUtils.getAbsolute(name, context.getNamespace(), context.getUseMap());
	}

	public String getAbsoluteConstantName(PhpClass phpClass, String name) {
		if (phpClass == null) {
			return PhpUtils.getAbsolute(name, context.getNamespace(), context.getUseMap());
		} else {
			String absolute = PhpUtils.getAbsolute(name,
					phpClass.getAbsoulteClassName(),
					context.getUseMap());
			int i = absolute.lastIndexOf("\\");
			if (i >= 0) {
				absolute = absolute.substring(0, i) + "::" + absolute.substring(i + 1);
			}
			return absolute;
		}
	}

	public PhpCallable getFunction(String name) {
		// http://php.net/manual/ja/language.namespaces.rules.php
		String absoluteName = getAbsoluteFunctionName(name);
		PhpCallable callable = functionMap.get(absoluteName.toLowerCase());
		if (callable == null) {
			callable = functionMap.get("\\" + name.toLowerCase());
		}
		return callable;
	}

	public void addAutoload(Symbol callbackSymbol) {
		operator.incrementReference(callbackSymbol);
		autoloadCallbackList.add(callbackSymbol);
	}

	public void removeAutoload(Symbol callbackSymbol) {
		int i = autoloadCallbackList.indexOf(callbackSymbol);
		if (i >= 0) {
			Symbol symbol = autoloadCallbackList.remove(i);
			operator.decrementReference(symbol);
		}
	}

	public PhpNewable getClass(String name) {
		return classMap.get(name.toLowerCase());
	}

	public List<PhpCallable> getFuntionList() {
		return Lists.newArrayList(functionMap.values());
	}

	public List<PhpNewable> getClassList() {
		return Lists.newArrayList(classMap.values());
	}

	public Symbol getFilePathSymbol() {
		String string = getFile().getAbsolutePath();
		Symbol symbol = operator.createSymbol(new PhpString(string));
		return symbol;
	}

	public Symbol getLineNoSymbol() {
		long lineno;
		if (sourcePosition == null) {
			lineno = 0;
		} else {
			lineno = sourcePosition.getLineno();
		}
		Symbol symbol = operator.createSymbol(new PhpInteger(lineno));
		return symbol;
	}
}
