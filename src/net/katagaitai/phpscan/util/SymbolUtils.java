package net.katagaitai.phpscan.util;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import lombok.extern.log4j.Log4j2;
import net.katagaitai.phpscan.command.CallStaticMethod;
import net.katagaitai.phpscan.command.Command;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.compiler.PhpClass;
import net.katagaitai.phpscan.compiler.PhpFunction;
import net.katagaitai.phpscan.compiler.PhpNewable;
import net.katagaitai.phpscan.compiler.PhpProject.FunctionInfo;
import net.katagaitai.phpscan.compiler.PhpProject.MethodInfo;
import net.katagaitai.phpscan.interpreter.CallDecorator;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.interpreter.InterpreterContext;
import net.katagaitai.phpscan.php.builtin.Core_c.Closure;
import net.katagaitai.phpscan.php.types.PhpAnyType;
import net.katagaitai.phpscan.php.types.PhpArray;
import net.katagaitai.phpscan.php.types.PhpObject;
import net.katagaitai.phpscan.php.types.PhpString;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@Log4j2
public class SymbolUtils {

	public static String decodeSymbolString(String name) {
		if (name.startsWith(Constants.PREFIX_INTEGER_LITERAL)) {
			Long d = Long.parseLong(name.substring(Constants.PREFIX_INTEGER_LITERAL.length()));
			return d.toString();
		} else if (name.startsWith(Constants.PREFIX_FLOAT_LITERAL)) {
			Double d = Double.parseDouble(name.substring(Constants.PREFIX_FLOAT_LITERAL.length()));
			return d.toString();
		} else if (name.startsWith(Constants.PREFIX_STRING_LITERAL)) {
			String s = String.format("\"%s\"", name.substring(Constants.PREFIX_STRING_LITERAL.length()));
			return s;
		} else if (name.startsWith(Constants.PREFIX_BOOLEAN_LITERAL)) {
			Boolean b = Boolean.parseBoolean(name.substring(Constants.PREFIX_BOOLEAN_LITERAL.length()));
			return b.toString();
		} else if (name.startsWith(Constants.NULL_LITERAL)) {
			return "NULL";
		} else {
			return name;
		}
	}

	public static String createSymbolName() {
		return "$" + Constants.PREFIX + "var" + UNIQUE_NUMBER++;
	}

	public static String createSymbolNameForConditionalExpression() {
		return "$" + Constants.APP_NAME + "_var" + UNIQUE_NUMBER++;
	}

	public static String createLambdaName() {
		return Constants.PREFIX + "lambda" + UNIQUE_NUMBER++;
	}

	public static String createLabelName() {
		return Constants.PREFIX + "label" + UNIQUE_NUMBER++;
	}

	private static int UNIQUE_NUMBER;

	public static List<PhpCallable> getFunctionList(Interpreter ip, Symbol functionSymbol,
			int argumentSize, boolean rootNamespace) {
		SymbolOperator operator = ip.getOperator();
		List<PhpCallable> functionList = Lists.newArrayList();
		for (PhpAnyType type : operator.getTypeSet(functionSymbol)) {
			if (type instanceof PhpString) {
				PhpString phpString = (PhpString) type;
				String name = phpString.getString();
				functionList.addAll(getFunctionList(ip, name, argumentSize, rootNamespace));
			} else if (type instanceof PhpObject) {
				if (((PhpObject) type).getPhpClass() instanceof Closure) {
					Closure closure = (Closure) ((PhpObject) type).getPhpClass();
					functionList.addAll(closure.getCallableList());
				}
			}
		}
		return Lists.newArrayList(Sets.newHashSet(functionList));
	}

	private static List<PhpCallable> getFunctionList(Interpreter ip, String name,
			int argumentSize, boolean rootNamespace) {
		List<PhpCallable> result = Lists.newArrayList();
		if (name.contains("::")) {
			// pass
		} else {
			// 関数
			if (rootNamespace && !name.startsWith("\\")) {
				name = "\\" + name;
			}
			PhpCallable callable = ip.getFunction(name);
			if (callable != null) {
				result.add(callable);
			}
		}
		if (result.size() == 0) {
			List<FunctionInfo> funcInfoList = ip.getPhpProject().getFunction(ip.getAbsoluteFunctionName(name));
			if (funcInfoList != null) {
				List<FunctionInfo> candidateList = Lists.newArrayList();
				for (FunctionInfo funcInfo : funcInfoList) {
					int min = funcInfo.getMinArgumentSize();
					int max = funcInfo.getMaxArgumentSize();
					if (min <= argumentSize && argumentSize <= max) {
						candidateList.add(funcInfo);
					}
				}
				// ユニークならそれを使う
				if (candidateList.size() == 1) {
					String filepath = candidateList.get(0).getFilepath();
					log.info("関数のインクルード：" + name + " in " + filepath);
					forceInclude(ip, filepath);
					PhpCallable callable = ip.getFunction(name);
					if (callable != null) {
						result.add(callable);
					}
				}
			}
		}
		return Lists.newArrayList(Sets.newHashSet(result));
	}

	public static List<PhpCallable> getStaticMethodList(Interpreter ip, Symbol functionSymbol,
			int argumentSize) {
		SymbolOperator operator = ip.getOperator();
		List<PhpCallable> staticMethodList = Lists.newArrayList();
		Set<PhpAnyType> typeSet = Sets.newHashSet(operator.getTypeSet(functionSymbol));
		for (PhpAnyType type : typeSet) {
			if (type instanceof PhpString) {
				PhpString phpString = (PhpString) type;
				String name = phpString.getString();

				List<PhpCallable> callableList = Lists.newArrayList();
				if (name.contains("::")) {
					// クラスメソッド
					String[] tokens = name.split("::");
					String className = tokens[0];
					String methodName = "";
					if (tokens.length >= 2) {
						methodName = tokens[1];
					}
					// ルート名前空間を利用する
					if (!className.startsWith("\\")) {
						className = "\\" + className;
					}
					for (PhpNewable newable : getClassList(ip, className)) {
						PhpCallable callable = newable.getStaticMethod(methodName);
						if (callable != null) {
							callableList.add(callable);
						}
						// PARENT::test()とか
						PhpCallable callable2 = newable.getMethod(methodName);
						if (callable2 != null) {
							callableList.add(callable2);
						}
					}

					// クラス名で判別
					if (callableList.size() == 0) {
						List<String> filepathList = ip.getPhpProject().getClass(className);
						// ユニークならそれを使う
						if (filepathList != null && filepathList.size() == 1) {
							String filepath = filepathList.get(0);
							log.info("クラスのインクルード：" + className + " in " + filepath);
							forceInclude(ip, filepath);
							for (PhpNewable newable : getClassList(ip, className)) {
								PhpCallable callable = newable.getStaticMethod(methodName);
								if (callable != null) {
									callableList.add(callable);
								}
							}
						}
						//						}
					}
					// 静的メソッド名で判別
					if (callableList.size() == 0) {
						List<MethodInfo> methodInfoList = ip.getPhpProject().getStaticMethod(methodName);
						if (methodInfoList != null) {
							List<MethodInfo> candidateList = Lists.newArrayList();
							for (MethodInfo methodInfo : methodInfoList) {
								int min = methodInfo.getMinArgumentSize();
								int max = methodInfo.getMaxArgumentSize();
								if (min <= argumentSize && argumentSize <= max) {
									candidateList.add(methodInfo);
								}
							}
							// ユニークならそれを使う
							if (candidateList.size() == 1) {
								MethodInfo methodInfo = candidateList.get(0);
								log.info("静的メソッドのインクルード：" + methodName + " in " + methodInfo);
								forceInclude(ip, methodInfo.getFilepath());
								for (PhpNewable newable : getClassList(ip, methodInfo.getClassName())) {
									PhpCallable callable = newable.getStaticMethod(methodName);
									if (callable != null) {
										callableList.add(callable);
										operator.addTypeSet(functionSymbol, Sets.newHashSet(
												new PhpString(methodInfo.getClassName() + "::" + methodName)));
									}
								}
							}
						}
					}
				}

				staticMethodList.addAll(callableList);
			} else if (type instanceof PhpArray) {
				PhpArray phpArray = (PhpArray) type;
				Symbol clazzSymbol = operator.getArrayValue(phpArray, 0);
				Symbol methodSymbol = operator.getArrayValue(phpArray, 1);
				staticMethodList.addAll(getStaticMethodList(ip, clazzSymbol, methodSymbol, argumentSize, true));
			}
		}
		return Lists.newArrayList(Sets.newHashSet(staticMethodList));
	}

	public static List<PhpCallable> getStaticMethodList(Interpreter ip, Symbol clazzSymbol, Symbol methodSymbol,
			int argumentSize, boolean rootNamespace) {
		List<PhpCallable> result = Lists.newArrayList();
		SymbolOperator operator = ip.getOperator();
		Set<PhpAnyType> typeSet = Sets.newHashSet(operator.getTypeSet(clazzSymbol));
		for (PhpAnyType type : typeSet) {
			String className;
			if (type instanceof PhpString) {
				PhpString phpString = (PhpString) type;
				className = phpString.getString();
				if (rootNamespace && !className.startsWith("\\")) {
					className = "\\" + className;
				}
				for (PhpNewable newable : getClassList(ip, className)) {
					for (String methodName : operator.getJavaStringList(methodSymbol)) {
						PhpCallable callable = newable.getStaticMethod(methodName);
						if (callable != null) {
							result.add(callable);
						}
						// PARENT::test()とか
						PhpCallable callable2 = newable.getMethod(methodName);
						if (callable2 != null) {
							result.add(callable2);
						}
					}
				}

				// クラス名で判別
				if (result.size() == 0) {
					List<String> filepathList = ip.getPhpProject().getClass(ip.getAbsoluteClassName(className));
					// ユニークならそれを使う
					if (filepathList != null && filepathList.size() == 1) {
						String filepath = filepathList.get(0);
						log.info("クラスのインクルード：" + className + " in " + filepath);
						forceInclude(ip, filepath);
						for (PhpNewable newable : getClassList(ip, className)) {
							for (String methodName : operator.getJavaStringList(methodSymbol)) {
								PhpCallable callable = newable.getStaticMethod(methodName);
								if (callable != null) {
									result.add(callable);
								}
							}
						}
						//						}
					}
				}
				// 静的メソッド名で判別
				if (result.size() == 0) {
					for (String methodName : operator.getJavaStringList(methodSymbol)) {
						List<MethodInfo> methodInfoList = ip.getPhpProject().getStaticMethod(methodName);
						if (methodInfoList != null) {
							List<MethodInfo> candidateList = Lists.newArrayList();
							for (MethodInfo methodInfo : methodInfoList) {
								int min = methodInfo.getMinArgumentSize();
								int max = methodInfo.getMaxArgumentSize();
								if (min <= argumentSize && argumentSize <= max) {
									candidateList.add(methodInfo);
								}
							}
							// ユニークならそれを使う
							if (candidateList.size() == 1) {
								MethodInfo methodInfo = candidateList.get(0);
								log.info("静的メソッドのインクルード：" + methodName + " in " + methodInfo);
								forceInclude(ip, methodInfo.getFilepath());
								for (PhpNewable newable : getClassList(ip, methodInfo.getClassName())) {
									PhpCallable callable = newable.getStaticMethod(methodName);
									if (callable != null) {
										result.add(callable);
										operator.addTypeSet(clazzSymbol, Sets.newHashSet(
												new PhpString(methodInfo.getClassName())));
									}
								}
							}
						}
					}
				}
			} else if (type instanceof PhpObject) {
				PhpNewable newable = ((PhpObject) type).getPhpClass();
				for (String method : operator.getJavaStringList(methodSymbol)) {
					PhpCallable callable = newable.getStaticMethod(method);
					if (callable != null) {
						result.add(callable);
					}
				}
			} else {
				// 静的メソッド名で判別
				for (String methodName : operator.getJavaStringList(methodSymbol)) {
					List<MethodInfo> methodInfoList = ip.getPhpProject().getStaticMethod(methodName);
					if (methodInfoList != null) {
						List<MethodInfo> candidateList = Lists.newArrayList();
						for (MethodInfo methodInfo : methodInfoList) {
							int min = methodInfo.getMinArgumentSize();
							int max = methodInfo.getMaxArgumentSize();
							if (min <= argumentSize && argumentSize <= max) {
								candidateList.add(methodInfo);
							}
						}
						// ユニークならそれを使う
						if (candidateList.size() == 1) {
							MethodInfo methodInfo = candidateList.get(0);
							log.info("静的メソッドのインクルード：" + methodName + " in " + methodInfo);
							forceInclude(ip, methodInfo.getFilepath());
							for (PhpNewable newable : getClassList(ip, methodInfo.getClassName())) {
								PhpCallable callable = newable.getStaticMethod(methodName);
								if (callable != null) {
									result.add(callable);
									operator.addTypeSet(clazzSymbol,
											Sets.newHashSet(new PhpString(methodInfo.getClassName())));
								}
							}
						}
					}
				}
			}
		}
		return Lists.newArrayList(Sets.newHashSet(result));
	}

	public static Symbol callCallable(Interpreter ip, Symbol callbackSymbol, List<Symbol> argumentSymbolList) {
		SymbolOperator operator = ip.getOperator();
		Symbol resultSymbol = operator.createSymbol();
		// 関数
		List<PhpCallable> functionList = getFunctionList(ip, callbackSymbol, argumentSymbolList.size(), true);
		for (PhpCallable callable : functionList) {
			operator.merge(resultSymbol, callFunction(ip, callable, argumentSymbolList));
		}

		// 静的メソッド
		List<PhpCallable> staticMethodList = getStaticMethodList(ip, callbackSymbol, argumentSymbolList.size());
		for (PhpCallable callable : staticMethodList) {
			operator.merge(resultSymbol, callStaticMethod(ip, callable, argumentSymbolList));
		}

		// 通常メソッド
		for (PhpArray phpArray : operator.extractPhpArray(callbackSymbol)) {
			Symbol objectSymbol = operator.getArrayValue(phpArray, 0);
			Symbol methodSymbol = operator.getArrayValue(phpArray, 1);
			operator.merge(resultSymbol, callMethod(ip, objectSymbol, methodSymbol, argumentSymbolList));
		}
		return resultSymbol;
	}

	public static Symbol callFunction(Interpreter ip, PhpCallable callable, List<Symbol> argumentSymbolList) {
		SymbolOperator operator = ip.getOperator();
		Symbol resultSymbol = operator.createSymbol();
		if (callable != null) {
			if (callable instanceof PhpFunction) {
				((PhpFunction) callable).setCalled(true);
			}
			InterpreterContext saved = ip.getContext();
			ip.setContext(ip.getContext().copyForCallFunction(callable, argumentSymbolList));
			operator.merge(resultSymbol, new CallDecorator(callable).call(ip));
			ip.setContext(saved);
		}
		return resultSymbol;
	}

	public static Symbol callStaticMethod(Interpreter ip, PhpCallable callable, List<Symbol> argumentSymbolList) {
		SymbolOperator operator = ip.getOperator();
		Symbol resultSymbol = operator.createSymbol();
		if (callable != null) {
			if (callable instanceof PhpFunction) {
				((PhpFunction) callable).setCalled(true);
			}
			InterpreterContext saved = ip.getContext();
			boolean takeoverThis = false;
			Command command = ip.getContext().getCommand();
			if (command instanceof CallStaticMethod
					&& (((CallStaticMethod) command).getClazz().toLowerCase().equals("parent")
							|| ((CallStaticMethod) command).getClazz().toLowerCase().equals("self")
							|| ((CallStaticMethod) command).getClazz().toLowerCase().equals("static"))) {
				takeoverThis = true;
			}
			ip.setContext(ip.getContext().copyForCallStaticMethod(callable, argumentSymbolList, takeoverThis));
			operator.merge(resultSymbol, new CallDecorator(callable).call(ip));
			ip.setContext(saved);
		}
		return resultSymbol;
	}

	public static Symbol callMethod(Interpreter ip, PhpObject thisObject, PhpCallable callable,
			List<Symbol> argumentSymbolList) {
		SymbolOperator operator = ip.getOperator();
		Symbol resultSymbol = operator.createSymbol();
		if (callable != null) {
			if (callable instanceof PhpFunction) {
				((PhpFunction) callable).setCalled(true);
			}
			InterpreterContext saved = ip.getContext();
			ip.setContext(ip.getContext().copyForCallMethod(callable, thisObject, argumentSymbolList));
			operator.merge(resultSymbol, new CallDecorator(callable).call(ip));
			ip.setContext(saved);
		}
		return resultSymbol;
	}

	public static Symbol new_(Interpreter ip, PhpNewable clazz, List<Symbol> list) {
		SymbolOperator operator = ip.getOperator();
		InterpreterContext saved = ip.getContext();
		ip.setContext(ip.getContext().copyForNew(operator.createPhpObject(clazz), list));
		clazz.initialize(ip);
		clazz.__construct(ip);
		// クラスと同名のメソッドもコンストラクタになる
		String clazzName = clazz.getAbsoulteClassName();
		clazzName = clazzName.substring(clazzName.lastIndexOf("\\") + 1);
		if (clazz.getMethod(clazzName) != null) {
			PhpCallable classNameConstructor = clazz.getMethod(clazzName);
			callMethod(ip, ip.getContext().getThisObject(), classNameConstructor, list);
		}
		Symbol resultSymbol = ip.getContext().getThisSymbol();
		ip.setContext(saved);
		return resultSymbol;
	}

	private static Symbol getArgument(Interpreter ip, int i, boolean reference) {
		SymbolOperator operator = ip.getOperator();
		List<Symbol> argumentSymbolList =
				ip.getContext().getArgumentSymbolList();
		if (argumentSymbolList.size() == 0) {
			return operator.createNull();
		}
		// 負のインデックスにも対応
		while (i < 0) {
			i += argumentSymbolList.size();
		}
		Symbol symbol;
		if (i < argumentSymbolList.size()) {
			if (reference) {
				// そのまま
				symbol = argumentSymbolList.get(i);
			} else {
				// コピー
				symbol = operator.copy(argumentSymbolList.get(i));
			}
		} else {
			symbol = operator.createNull();
		}
		return symbol;
	}

	public static Symbol getArgumentRef(Interpreter ip, int i) {
		return getArgument(ip, i, true);
	}

	public static Symbol getArgument(Interpreter ip, int i) {
		return getArgument(ip, i, false);
	}

	public static int getArgumentSize(Interpreter ip) {
		return ip.getContext().getArgumentSymbolList().size();
	}

	public static String getFunctionName(PhpCallable callable) {
		String string;
		if (callable instanceof PhpFunction) {
			string = ((PhpFunction) callable).getName();
		} else {
			string = getAbsoluteNameFromJavaClassName(callable.getClass().getName());
		}
		return string;
	}

	public static List<PhpNewable> getClassList(Interpreter ip, Symbol clazzSymbol) {
		List<PhpNewable> result = Lists.newArrayList();
		SymbolOperator operator = ip.getOperator();
		for (PhpAnyType type : operator.getTypeSet(clazzSymbol)) {
			String name;
			if (type instanceof PhpString) {
				name = ((PhpString) type).getString();
			} else if (type instanceof PhpObject) {
				name = ((PhpObject) type).getPhpClass().getAbsoulteClassName();
			} else {
				continue;
			}
			result.addAll(getClassList(ip, name));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public static List<PhpNewable> getClassList(Interpreter ip, String name) {
		List<PhpNewable> result = Lists.newArrayList();
		if (name == null) {
			return result;
		}
		String absoluteClassName = ip.getAbsoluteClassName(name);
		LinkedList<String> getClassListStack = ip.getStorage().getGetClassListStack();
		if (getClassListStack.contains(absoluteClassName)) {
			return result;
		}
		getClassListStack.push(absoluteClassName);
		try {
			InterpreterContext context = ip.getContext();
			if (absoluteClassName.equalsIgnoreCase("self")) {
				if (context.getPhpClass() != null) {
					result.add(context.getPhpClass());
				}
			} else if (absoluteClassName.equalsIgnoreCase("parent")) {
				if (context.getPhpClass() != null) {
					result.addAll(getClassList(ip, context.getPhpClass().getAbsoluteParentClassName()));
				}
			} else if (absoluteClassName.equalsIgnoreCase("static")) {
				if (context.getThisObject() != null) {
					result.add(context.getThisObject().getPhpClass());
				}
			} else {
				PhpNewable newable = ip.getClass(absoluteClassName);
				if (newable == null) {
					// pass
				} else if (newable instanceof PhpClass) {
					result.add(newable);
				} else {
					// Closureクラスは関数リストのフィールドを持つ。インスタンスを共有できない。
					// インスタンスを新規作成する。
					try {
						Class<?> clazz = newable.getClass();
						Class<?>[] types = { Interpreter.class };
						Constructor<PhpNewable> constructor =
								(Constructor<PhpNewable>) clazz.getConstructor(types);
						Object[] args = { ip };
						PhpNewable instance = constructor.newInstance(args);
						result.add(instance);
					} catch (InstantiationException | IllegalAccessException
							| NoSuchMethodException | SecurityException
							| IllegalArgumentException | InvocationTargetException e) {
						throw new RuntimeException(e);
					}
				}
			}
			if (result.size() == 0) {
				List<String> filepathList = ip.getPhpProject().getClass(absoluteClassName);
				// ユニークならそれを使う
				if (filepathList != null && filepathList.size() == 1) {
					String filepath = filepathList.get(0);
					log.info("クラスのインクルード：" + absoluteClassName + " in " + filepath);
					forceInclude(ip, filepath);
					PhpNewable newable = ip.getClass(absoluteClassName);
					if (newable != null && newable instanceof PhpClass) {
						result.add(newable);
					}
				}
			}
			if (result.size() == 0) {
				for (String className : ip.getPhpProject().getClassNameList()) {
					if (className.toLowerCase().endsWith(absoluteClassName.toLowerCase())) {
						List<String> filepathList = ip.getPhpProject().getClass(className);
						if (filepathList != null) {
							for (String filepath : filepathList) {
								log.info("クラスのインクルード：" + className + " in " + filepath);
								forceInclude(ip, filepath);
								PhpNewable newable = ip.getClass(className);
								if (newable != null && newable instanceof PhpClass) {
									result.add(newable);
								}
							}
						}
					}
				}
			}
			return result;
		} finally {
			getClassListStack.pop();
		}
	}

	public static Symbol getConstant(Interpreter ip, String name) {
		Symbol result = null;
		String absolute = null;
		if (name.startsWith("\\")) {
			absolute = name;
			result = ip.getConstantMap().get(absolute);
		} else {
			// 相対名前空間を利用している場合
			// まずは現在の名前空間から探す
			if (ip.getContext().getNamespace() == null
					|| ip.getContext().getNamespace().equals("\\")) {
				// pass
			} else {
				absolute = ip.getContext().getNamespace() + "\\" + name;
				result = ip.getConstantMap().get(absolute);
			}
			// 見つからなければグローバル名前空間から探す
			if (result == null) {
				absolute = "\\" + name;
				result = ip.getConstantMap().get(absolute);
			}
		}
		if (result == null) {
			List<String> filepathList = ip.getPhpProject().getConstant(absolute);
			// ユニークならそれを使う
			if (filepathList != null && filepathList.size() == 1) {
				String filepath = filepathList.get(0);
				log.info("定数のインクルード：" + absolute + " in " + filepath);
				forceInclude(ip, filepath);
				result = ip.getConstantMap().get(absolute);
			}
		}
		return result;
	}

	public static String getAbsoluteNameFromJavaClassName(String name) {
		if (name.indexOf(".") >= 0) {
			name = name.substring(name.lastIndexOf(".") + 1);
		}
		if (name.indexOf("$") >= 0) {
			name = name.substring(name.lastIndexOf("$") + 1);
		}
		if (name.endsWith("_")) {
			name = name.substring(0, name.length() - 1);
		}
		return "\\" + name;
	}

	public static Symbol callMethod(Interpreter ip, Symbol objectSymbol, Symbol methodSymbol,
			List<Symbol> argumentSymbolList) {
		SymbolOperator operator = ip.getOperator();
		Symbol resultSymbol = operator.createSymbol();
		List<PhpObject> phpObjectList = operator.extractPhpObject(objectSymbol);
		if (phpObjectList.size() == 0) {
			// PHPオブジェクトを認識できていない場合
			for (String methodName : operator.getJavaStringList(methodSymbol)) {
				List<MethodInfo> methodInfoList = ip.getPhpProject().getMethod(methodName);
				if (methodInfoList != null) {
					List<MethodInfo> candidateList = Lists.newArrayList();
					for (MethodInfo methodInfo : methodInfoList) {
						int min = methodInfo.getMinArgumentSize();
						int max = methodInfo.getMaxArgumentSize();
						int argumentSize = argumentSymbolList.size();
						if (min <= argumentSize && argumentSize <= max) {
							candidateList.add(methodInfo);
						}
					}
					// ユニークならそれを使う
					if (candidateList.size() == 1) {
						MethodInfo methodInfo = candidateList.get(0);
						log.info("メソッドのインクルード：" + methodName + " in " + methodInfo);
						forceInclude(ip, methodInfo.getFilepath());
						for (PhpNewable newable : getClassList(ip, methodInfo.getClassName())) {
							Symbol newThisSymbol = new_(ip, newable, argumentSymbolList);
							for (PhpObject phpObject : operator.extractPhpObject(newThisSymbol)) {
								PhpCallable callable = phpObject.getPhpClass().getMethod(methodName);
								if (callable != null) {
									operator.merge(resultSymbol,
											callMethod(ip, phpObject, callable, argumentSymbolList));
								}
							}
							operator.merge(objectSymbol, newThisSymbol);
						}
					} else if (candidateList.size() > 1) {
						// 親子関係が1つのツリーに閉じているならツリー全てを呼ぶ
						List<String> classNameList = Lists.newArrayList();
						for (MethodInfo methodInfo : candidateList) {
							classNameList.add(methodInfo.getClassName());
						}
						for (String className : classNameList) {
							boolean isParentOfOthers = true;
							for (String className2 : classNameList) {
								if (className.equals(className2)) {
									continue;
								}
								if (ip.getPhpProject().isSuperClass(className, className2)) {
									// OK
								} else {
									isParentOfOthers = false;
									break;
								}
							}
							if (isParentOfOthers) {
								List<String> filepathList = ip.getPhpProject().getClass(className);
								if (filepathList != null) {
									for (String filepath : filepathList) {
										log.info("クラスのインクルード：" + className + " in " + filepath);
										forceInclude(ip, filepath);
										PhpNewable newable = ip.getClass(className);
										if (newable == null) {
											continue;
										}
										List<PhpNewable> newableList = Lists.newArrayList(newable);
										// サブクラスのメソッドも呼んでみる
										newableList.addAll(getSubClassList(ip, newable));
										for (PhpNewable newable2 : newableList) {
											PhpCallable subCallable = newable2.getMethod(methodName);
											if (subCallable == null) {
												continue;
											}
											Symbol newThisSymbol = new_(ip, newable2, argumentSymbolList);
											for (PhpObject phpObject2 : operator.extractPhpObject(newThisSymbol)) {
												operator.merge(resultSymbol,
														callMethod(ip, phpObject2, subCallable, argumentSymbolList));
											}
											operator.merge(objectSymbol, newThisSymbol);
										}
									}
								}
								break;
							}
						}
					}
				}
			}
		} else {
			for (PhpObject phpObject : phpObjectList) {
				for (String methodName : operator.getJavaStringList(methodSymbol)) {
					PhpNewable phpClass = phpObject.getPhpClass();
					PhpCallable callable = phpClass.getMethod(methodName);
					if (callable == null) {
						log.warn("不明なメソッド：" + phpClass.getAbsoulteClassName() + "#" + methodName);
						operator.merge(resultSymbol,
								phpObject.getPhpClass().__call(ip, phpObject, operator.createSymbol(methodName),
										argumentSymbolList));
					} else {
						operator.merge(resultSymbol, callMethod(ip, phpObject, callable, argumentSymbolList));
					}
				}
			}
		}
		return resultSymbol;
	}

	private static void forceInclude(Interpreter ip, String filepath) {
		//		LinkedList<SourcePosition> saved = ip.getCallStack();
		//		ip.setCallStack(Lists.newLinkedList());
		ip.include(new File(filepath), true);
		//		ip.setCallStack(saved);
	}

	private static List<PhpNewable> getSubClassList(Interpreter ip, PhpNewable newable) {
		List<PhpNewable> result = Lists.newArrayList();
		List<String> classNameList = ip.getPhpProject().getAllSubClassNameList(newable.getAbsoulteClassName());
		for (String className : classNameList) {
			List<String> filepathList = ip.getPhpProject().getClass(className);
			if (filepathList != null) {
				for (String filepath : filepathList) {
					log.info("クラスのインクルード：" + className + " in " + filepath);
					forceInclude(ip, filepath);
					PhpNewable subNewable = ip.getClass(className);
					if (subNewable != null && subNewable instanceof PhpClass) {
						result.add(subNewable);
					}
				}
			}
		}
		return result;
	}

	public static List<PhpNewable> getUserDefinedPhpClassList(Interpreter ip) {
		List<PhpNewable> newableList = Lists.newArrayList();
		if (ip.getAutoloadCallbackList().size() > 0) {
			// 自動ロードがある場合はすべてのクラスを呼べると仮定する
			List<String> classNameList = ip.getPhpProject().getClassNameList();
			for (String className : classNameList) {
				List<String> filepathList = ip.getPhpProject().getClass(className);
				for (String filepath : filepathList) {
					log.info("クラスのインクルード：" + className + " in " + filepath);
					forceInclude(ip, filepath);
					for (PhpNewable newable : getClassList(ip, className)) {
						if (newableList.contains(newable)) {
							// pass
						} else {
							newableList.add(newable);
						}
					}
				}
			}
		} else {
			for (PhpNewable newable : ip.getClassList()) {
				if (newable instanceof PhpClass) {
					if (newableList.contains(newable)) {
						// pass
					} else {
						newableList.add(newable);
					}
				}
			}
		}
		return newableList;
	}

	public static PhpClass getStdClass(Interpreter ip) {
		PhpClass result = ip.getStdClass();
		if (result == null) {
			log.warn("stdClassが未定義です。");
			result = new PhpClass("stdclass", null, Lists.newArrayList(), null, null);
		}
		return result;
	}

	public static boolean hasTaint(Interpreter ip, List<Symbol> symbolList) {
		SymbolOperator operator = ip.getOperator();
		for (Symbol symbol : symbolList) {
			if (symbol != null && symbol.getTaintSet().size() > 0) {
				return true;
			}
			//			for (PhpAnyType type : operator.getTypeSet(symbol)) {
			//				if (type instanceof PhpObject) {
			//					PhpObject phpObject = (PhpObject) type;
			//					Symbol keySymbol = operator.getMergedFieldKeySymbol(phpObject);
			//					Symbol valueSymbol = operator.getMergedFieldValueSymbol(phpObject);
			//					if ((keySymbol != null && keySymbol.getTaintSet().size() > 0)
			//							|| (valueSymbol != null && valueSymbol.getTaintSet().size() > 0)) {
			//						return true;
			//					}
			//				} else if (type instanceof PhpArray) {
			//					PhpArray phpArray = (PhpArray) type;
			//					Symbol keySymbol = operator.getMergedArrayKeySymbol(phpArray);
			//					Symbol valueSymbol = operator.getMergedArrayValueSymbol(phpArray);
			//					if ((keySymbol != null && keySymbol.getTaintSet().size() > 0)
			//							|| (valueSymbol != null && valueSymbol.getTaintSet().size() > 0)) {
			//						return true;
			//					}
			//				} else if (type instanceof PhpResource) {
			//					PhpResource phpResource = (PhpResource) type;
			//					for (SymbolId valueSymbolId : phpResource.getResource().values()) {
			//						Symbol valueSymbol = operator.getSymbol(valueSymbolId);
			//						if (valueSymbol != null && valueSymbol.getTaintSet().size() > 0) {
			//							return true;
			//						}
			//					}
			//				}
			//			}
		}
		return false;
	}
}
