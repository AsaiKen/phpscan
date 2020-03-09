package net.katagaitai.phpscan.interpreter;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.katagaitai.phpscan.command.Command;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.compiler.PhpClass;
import net.katagaitai.phpscan.compiler.PhpFunction;
import net.katagaitai.phpscan.compiler.PhpNewable;
import net.katagaitai.phpscan.php.types.PhpObject;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.util.SymbolUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Data
@RequiredArgsConstructor
public class InterpreterContext {
	private final Interpreter ip;

	// 関数、メソッド、静的メソッド、new

	// 常に入れ替え
	private String namespace = "\\";
	private Map<String, String> useMap = Maps.newHashMap();
	private File file;
	private List<Symbol> argumentSymbolList = Lists.newArrayList();

	// メソッド、静的メソッド、newで入れ替え、それ以外はリセット
	private PhpClass phpClass;

	// メソッド、静的メソッドで入れ替え、それ以外はリセット
	private PhpFunction phpFunction;

	// メソッド、newで入れ替え、それ以外はリセット
	private PhpObject thisObject;
	private Symbol thisSymbol;

	// 常にリセット
	private LinkedList<Boolean> inLoopStack = Lists.newLinkedList(Lists.newArrayList(false));
	private Command command;
	private Symbol returnValueSymbol;

	private InterpreterContext prevContext;

	public InterpreterContext copyForCallMethod(PhpCallable callable, PhpObject thisObject_,
			List<Symbol> argumentSymbolList2) {
		SymbolOperator operator = ip.getOperator();
		InterpreterContext result = new InterpreterContext(ip);
		result.setArgumentSymbolList(argumentSymbolList2);
		result.setThisObject(thisObject_);
		result.setThisSymbol(operator.createSymbol(thisObject_));
		setByPhpFunction(callable, result);
		setByPhpClass(callable, result);
		result.setPrevContext(this);
		result.setCommand(command);
		return result;
	}

	public InterpreterContext copyForNew(PhpObject thisObject_, List<Symbol> argumentSymbolList2) {
		SymbolOperator operator = ip.getOperator();
		InterpreterContext result = new InterpreterContext(ip);
		PhpNewable newable = thisObject_.getPhpClass();
		result.setArgumentSymbolList(argumentSymbolList2);
		result.setThisObject(thisObject_);
		result.setThisSymbol(operator.createSymbol(thisObject_));
		setByPhpClass(newable, result);
		result.setPrevContext(this);
		result.setCommand(command);
		return result;
	}

	public InterpreterContext copyForCallFunction(PhpCallable callable, List<Symbol> argumentSymbolList2) {
		InterpreterContext result = new InterpreterContext(ip);
		result.setArgumentSymbolList(argumentSymbolList2);
		setByPhpFunction(callable, result);
		result.setPrevContext(this);
		result.setCommand(command);
		return result;
	}

	public InterpreterContext copyForCallStaticMethod(PhpCallable callable, List<Symbol> argumentSymbolList2,
			boolean takeoverThis) {
		SymbolOperator operator = ip.getOperator();
		InterpreterContext result = new InterpreterContext(ip);
		result.setArgumentSymbolList(argumentSymbolList2);
		setByPhpFunction(callable, result);
		setByPhpClass(callable, result);
		if (takeoverThis && thisObject != null && thisSymbol != null) {
			// 引き継ぐ
			result.setThisObject(thisObject);
			result.setThisSymbol(thisSymbol);
		} else {
			// ダミーのthisをセットする
			result.setThisObject(operator.createPhpObject(result.getPhpClass()));
			result.setThisSymbol(operator.createSymbol(result.getThisObject()));
		}
		result.setPrevContext(this);
		result.setCommand(command);
		return result;
	}

	public InterpreterContext copyForCreateClass(PhpClass phpClass2, PhpFunction phpFunction2) {
		InterpreterContext result = new InterpreterContext(ip);
		setByPhpFunction(phpFunction2, result);
		setByPhpClass(phpClass2, result);
		result.setPrevContext(this);
		result.setCommand(command);
		return result;
	}

	public InterpreterContext copyForCreateFunction(PhpFunction phpFunction2) {
		InterpreterContext result = new InterpreterContext(ip);
		setByPhpFunction(phpFunction2, result);
		result.setPrevContext(this);
		result.setCommand(command);
		return result;
	}

	public void setThisObject(PhpObject thisObject2) {
		SymbolOperator operator = ip.getOperator();
		operator.incrementReference(thisObject2);
		operator.decrementReference(thisObject);
		thisObject = thisObject2;
	}

	public void setThisSymbol(Symbol thisSymbol2) {
		SymbolOperator operator = ip.getOperator();
		operator.incrementReference(thisSymbol2);
		operator.decrementReference(thisSymbol);
		thisSymbol = thisSymbol2;
	}

	public PhpObject getThisObject() {
		PhpObject result = thisObject;
		if (result == null) {
			result = ip.getOperator().createPhpObject(SymbolUtils.getStdClass(ip));
		}
		return result;
	}

	public Symbol getThisSymbol() {
		SymbolOperator operator = ip.getOperator();
		Symbol result = thisSymbol;
		if (result == null) {
			result = operator.createNull();
		}
		return result;
	}

	private void setByPhpClass(PhpCallable callable, InterpreterContext result) {
		if (callable instanceof PhpFunction && ((PhpFunction) callable).getPhpClass() != null) {
			PhpClass phpClass_ = ((PhpFunction) callable).getPhpClass();
			setByPhpClass(phpClass_, result);
		}
	}

	private void setByPhpClass(PhpNewable newable, InterpreterContext result) {
		if (newable instanceof PhpClass) {
			PhpClass phpClass_ = (PhpClass) newable;
			result.setPhpClass(phpClass_);
			result.setFile(phpClass_.getFile());
			result.setNamespace(phpClass_.getNamespace());
			result.setUseMap(phpClass_.getUseMap());
		}
	}

	private void setByPhpFunction(PhpCallable callable, InterpreterContext result) {
		if (callable instanceof PhpFunction) {
			PhpFunction phpFunction_ = (PhpFunction) callable;
			result.setPhpFunction(phpFunction_);
			result.setFile(phpFunction_.getFile());
			result.setNamespace(phpFunction_.getNamespace());
			result.setUseMap(phpFunction_.getUseMap());
		}
	}

	public InterpreterContext copyForInitialize(PhpNewable newable) {
		InterpreterContext result = new InterpreterContext(ip);
		result.setArgumentSymbolList(argumentSymbolList);
		result.setThisObject(thisObject);
		result.setThisSymbol(thisSymbol);
		result.setPrevContext(this);
		result.setCommand(command);
		// クラスだけ切り替え
		setByPhpClass(newable, result);
		return result;
	}

}
