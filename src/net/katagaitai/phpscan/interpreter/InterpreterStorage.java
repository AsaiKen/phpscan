package net.katagaitai.phpscan.interpreter;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Data;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.compiler.PhpFunction;
import net.katagaitai.phpscan.compiler.PhpNewable;
import net.katagaitai.phpscan.php.types.PhpAnyType;
import net.katagaitai.phpscan.php.types.PhpArray;
import net.katagaitai.phpscan.php.types.PhpObject;
import net.katagaitai.phpscan.php.types.PhpResource;
import net.katagaitai.phpscan.scope.Scope;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolId;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.util.Constants;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

@Data
public class InterpreterStorage {
	private LinkedList<List<SymbolId>> assignSymbolStack = Lists.newLinkedList();
	private LinkedList<List<SymbolId>> mergeSymbolStack = Lists.newLinkedList();
	private LinkedList<SymbolId> decrementSymbolStack = Lists.newLinkedList();
	private LinkedList<PhpAnyType> decrementTypeStack = Lists.newLinkedList();
	private LinkedList<SymbolId> getTypeSetStack = Lists.newLinkedList();
	private LinkedList<SymbolId> applyTaintStack = Lists.newLinkedList();
	private LinkedList<String> getClassListStack = Lists.newLinkedList();
	private LinkedList<PhpCallable> callableStack = Lists.newLinkedList();
	private LinkedList<PhpArray> arrayWalkRecursiveStack = Lists.newLinkedList();
	private List<SymbolId> serializeSymbolList = Lists.newArrayList();
	private List<PhpArray> serializeArrayList = Lists.newArrayList();
	private List<PhpObject> serializeObjectList = Lists.newArrayList();
	private LinkedList<PhpNewable> newableStack = Lists.newLinkedList();
	private LinkedList<PhpNewable> getStaticFieldStack = Lists.newLinkedList();
	private LinkedList<String> includeStack = Lists.newLinkedList();

	private int arrayCounter;

	private Map<Integer, PhpObject> objectMap = Maps.newHashMap();
	private int objectCounter;

	private int resourceCounter;

	private List<PhpResource> resourceList = Lists.newArrayList();

	private Map<SymbolId, Symbol> symbolMap = Maps.newHashMap();
	private int symbolCounter;

	private Map<Class<?>, PhpCallable> callableObjectCache = Maps.newHashMap();

	private PhpResource pgsqlDefaultConnection;
	private PhpResource mysqlDefaultConnection;

	private Map<PhpFunction, Scope> staticScopeMap = Maps.newHashMap();

	private Set<String> splDefaultExtensionSet = Sets.newHashSet(Constants.SPL_DEFAULT_EXTENSION_SET);

	private Map<String, Symbol> filenameContentMap = Maps.newHashMap();

	public void initialize(SymbolOperator operator) {
		pgsqlDefaultConnection = operator.createPhpResource();
		operator.incrementReference(pgsqlDefaultConnection);
		mysqlDefaultConnection = operator.createPhpResource();
		operator.incrementReference(mysqlDefaultConnection);
	}

	public int getSymbolCounter() {
		return symbolCounter++;
	}

	public int getArrayCounter() {
		return arrayCounter++;
	}

	public int getObjectCounter() {
		return objectCounter++;
	}

	public int getResourceCounter() {
		return resourceCounter++;
	}

}
