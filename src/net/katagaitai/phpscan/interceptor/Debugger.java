package net.katagaitai.phpscan.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.katagaitai.phpscan.command.AddToReturnValue;
import net.katagaitai.phpscan.command.Assign;
import net.katagaitai.phpscan.command.AssignArgument;
import net.katagaitai.phpscan.command.AssignArgumentReference;
import net.katagaitai.phpscan.command.AssignReference;
import net.katagaitai.phpscan.command.CallFunction;
import net.katagaitai.phpscan.command.CallMethod;
import net.katagaitai.phpscan.command.CallStaticMethod;
import net.katagaitai.phpscan.command.Command;
import net.katagaitai.phpscan.command.GetArrayValue;
import net.katagaitai.phpscan.command.GetFieldValue;
import net.katagaitai.phpscan.command.Instanceof;
import net.katagaitai.phpscan.command.Mix;
import net.katagaitai.phpscan.command.New;
import net.katagaitai.phpscan.command.PutArrayValue;
import net.katagaitai.phpscan.command.PutFieldValue;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.symbol.Symbol;

import org.apache.commons.lang3.StringUtils;

@Log4j2
@RequiredArgsConstructor
public class Debugger implements CommandInterceptor {
	private final Interpreter ip;

	@Override
	public void intercept(Command command) {
		debug(ip.getCommandCounter() + ": " + command);

		if (command instanceof Assign) {
			Assign assign = (Assign) command;
			debugSymbol(assign.getLeft());
			debugSymbol(assign.getRight());
		} else if (command instanceof Mix) {
			Mix mix = (Mix) command;
			debugSymbol(mix.getResult());
			debugSymbol(mix.getLeft());
			debugSymbol(mix.getRight());
		} else if (command instanceof New) {
			New new_ = (New) command;
			debugSymbol(new_.getResult());
			debugSymbol(new_.getClazz());
			for (String string : new_.getArgumentList()) {
				debugSymbol(string);
			}
		} else if (command instanceof GetFieldValue) {
			GetFieldValue getFieldValue = (GetFieldValue) command;
			debugSymbol(getFieldValue.getResult());
			debugSymbol(getFieldValue.getObject());
			debugSymbol(getFieldValue.getName());
		} else if (command instanceof CallFunction) {
			CallFunction callFunction = (CallFunction) command;
			debugSymbol(callFunction.getResult());
			debugSymbol(callFunction.getFunction());
			for (String string : callFunction.getArgumentList()) {
				debugSymbol(string);
			}
		} else if (command instanceof PutFieldValue) {
			PutFieldValue putFieldValue = (PutFieldValue) command;
			debugSymbol(putFieldValue.getObject());
			debugSymbol(putFieldValue.getName());
			debugSymbol(putFieldValue.getValue());
		} else if (command instanceof AssignArgument) {
			AssignArgument assignArgument = (AssignArgument) command;
			debugSymbol(assignArgument.getArgument());
		} else if (command instanceof AssignReference) {
			AssignReference getReference = (AssignReference) command;
			debugSymbol(getReference.getLeft());
			debugSymbol(getReference.getRight());
		} else if (command instanceof GetArrayValue) {
			GetArrayValue getArrayValue = (GetArrayValue) command;
			debugSymbol(getArrayValue.getResult());
			debugSymbol(getArrayValue.getArray());
			debugSymbol(getArrayValue.getName());
		} else if (command instanceof AssignArgumentReference) {
			AssignArgumentReference assignArgumentReference = (AssignArgumentReference) command;
			debugSymbol(assignArgumentReference.getArgument());
		} else if (command instanceof CallStaticMethod) {
			CallStaticMethod callStaticMethod = (CallStaticMethod) command;
			debugSymbol(callStaticMethod.getResult());
			debugSymbol(callStaticMethod.getClazz());
			debugSymbol(callStaticMethod.getMethod());
			for (String string : callStaticMethod.getArgumentList()) {
				debugSymbol(string);
			}
		} else if (command instanceof PutArrayValue) {
			PutArrayValue putArrayValue = (PutArrayValue) command;
			debugSymbol(putArrayValue.getArray());
			debugSymbol(putArrayValue.getKey());
			debugSymbol(putArrayValue.getValue());
		} else if (command instanceof AddToReturnValue) {
			AddToReturnValue addToReturnValue = (AddToReturnValue) command;
			debugSymbol(addToReturnValue.getValue());
			debugSymbol(ip.getContext().getReturnValueSymbol());
		} else if (command instanceof CallMethod) {
			CallMethod callMethod = (CallMethod) command;
			debugSymbol(callMethod.getResult());
			debugSymbol(callMethod.getObject());
			debugSymbol(callMethod.getMethod());
			for (String string : callMethod.getArgumentList()) {
				debugSymbol(string);
			}
		} else if (command instanceof Instanceof) {
			Instanceof instanceof1 = (Instanceof) command;
			debug(instanceof1.getTarget());
			String clazz = instanceof1.getClazz();
			debug(clazz);
		}
	}

	private void debugSymbol(Symbol symbol) {
		if (symbol == null) {
			return;
		}
		debug(String.format("|  %s", symbol));
	}

	private void debug(String string) {
		if (string == null) {
			return;
		}
		int indent = ip.getCallStack().size();
		log.debug(" " + StringUtils.repeat("\t", indent) + string);
	}

	private void debugSymbol(String string) {
		if (string == null) {
			return;
		}
		Symbol symbol;
		if (string.equals("$this")) {
			symbol = ip.getContext().getThisSymbol();
		} else {
			symbol = ip.getSymbolOrCreate(string);
		}
		debug(String.format("|  %s: %s", string, symbol));
	}

}
