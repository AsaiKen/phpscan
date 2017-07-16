package net.katagaitai.phpscan.compiler;

import java.io.File;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import net.katagaitai.phpscan.command.Command;
import net.katagaitai.phpscan.command.CreateClass;
import net.katagaitai.phpscan.command.CreateFunction;
import net.katagaitai.phpscan.command.CreateMethod;
import net.katagaitai.phpscan.command.CreateStaticMethod;
import net.katagaitai.phpscan.command.EndNamespace;
import net.katagaitai.phpscan.command.StartNamespace;
import net.katagaitai.phpscan.interpreter.CommandDecorator;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.scope.Scope;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.util.Constants;
import net.katagaitai.phpscan.util.SymbolUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Log4j2
@RequiredArgsConstructor
public class PhpFunction implements PhpCallable {
	@Getter
	private final String name;
	@Getter
	private final List<String> argumentList;
	@Getter
	private final boolean returnReference;
	@Getter
	private final File file;
	@Getter
	@Setter
	private boolean staticFlg;

	@Getter
	private List<String> lexicalList = Lists.newArrayList();

	// 引数の初期値
	@Getter
	private List<Command> staticCommandList = Lists.newArrayList();
	@Getter
	private List<Command> commandList = Lists.newArrayList();

	private int scIndex = 0;
	private int cIndex = 0;

	@Getter
	@Setter
	private String namespace = "\\";
	@Getter
	@Setter
	private Map<String, String> useMap = Maps.newHashMap();
	@Getter
	@Setter
	private PhpClass phpClass;
	@Getter
	@Setter
	private boolean called;

	public PhpFunction(String nameString, List<String> argumentList2, boolean isReference,
			File file, List<String> lexicalList2) {
		this(nameString, argumentList2, isReference, file);
		this.lexicalList = lexicalList2;
	}

	public void addCommand(Command command) {
		if (command instanceof StartNamespace) {
			// そのまま
			commandList.add(command);
			cIndex = commandList.size();
		} else
		// TODO ifの中で宣言されている場合も巻き上げてしまう問題
		if (command instanceof CreateClass || command instanceof CreateFunction) {
			// 先頭
			commandList.add(cIndex, command);
			cIndex += 1;
		} else {
			// 末尾
			commandList.add(command);
		}
	}

	public void addStaticCommand(Command command) {
		if (command instanceof StartNamespace || command instanceof EndNamespace) {
			// そのまま
			staticCommandList.add(command);
			scIndex = staticCommandList.size();
		} else if (command instanceof CreateStaticMethod || command instanceof CreateMethod) {
			// 先頭
			staticCommandList.add(scIndex, command);
			scIndex += 1;
		} else {
			// 末尾
			staticCommandList.add(command);
		}
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(String.format("function %s%s (%s) {\n",
				returnReference ? "&" : "",
				name, String.join(", ", argumentList)));
		buffer.append("\tstatic {\n");
		for (Command command : staticCommandList) {
			buffer.append("\t\t");
			buffer.append(command.toString().replace("\n", "\n\t\t"));
			buffer.append("\n");
		}
		buffer.append("\t}\n");
		for (Command command : commandList) {
			buffer.append("\t");
			buffer.append(command.toString().replace("\n", "\n\t"));
			buffer.append("\n");
		}
		buffer.append("}");
		return buffer.toString();
	}

	@Override
	public Symbol call(Interpreter ip) {
		SymbolOperator operator = ip.getOperator();
		// callStack
		if (SymbolUtils.hasTaint(ip, ip.getContext().getArgumentSymbolList())) {
			// 引数にテイントがあれば必ず実行する
		} else if (ip.getCallStack().size() >= ip.getMaxCallStackSize()) {
			log.info("コールスタックが上限に達しました。：" + SymbolUtils.getFunctionName(this)
					+ " [" + ip.getSourcePosition() + "]");
			return operator.createNull();
		}

		Scope previousScope = ip.getScopeStack().peek();
		Scope scope = new Scope(ip);
		// スーパーグローバルズをインポート
		for (String keyString : Constants.SUPERGLOBAL_NAMES) {
			scope.put(keyString, ip.getGlobalScope().getOrPhpNull(keyString));
		}
		// 内部変数をインポート
		for (String keyString : Constants.INTERNAL_VARIABLE_NAME) {
			scope.put(keyString, ip.getGlobalScope().getOrPhpNull(keyString));
		}
		if (ip.getContext().getThisObject() != null) {
			scope.put("$this", ip.getContext().getThisSymbol());
		}
		// レキスカルスコープ
		for (String lexical : lexicalList) {
			Symbol symbol = previousScope.getOrPhpNull(lexical);
			scope.put(lexical, symbol);
		}
		ip.getScopeStack().push(scope);
		ip.getCallStack().push(ip.getSourcePosition());
		try {
			// コマンド実行
			for (Command command : getCommandList()) {
				new CommandDecorator(command).accept(ip);
			}
			Symbol result = ip.getContext().getReturnValueSymbol();
			if (returnReference) {
				return result;
			} else {
				return operator.copy(result);
			}
		} finally {
			ip.getCallStack().pop();
			ip.getScopeStack().pop();
			scope.clear();
		}
	}
}
