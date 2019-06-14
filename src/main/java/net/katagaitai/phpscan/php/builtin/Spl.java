package net.katagaitai.phpscan.php.builtin;

import com.google.common.collect.Lists;
import net.katagaitai.phpscan.compiler.*;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.php.builtin.Core_c.Exception;
import net.katagaitai.phpscan.php.types.PhpArray;
import net.katagaitai.phpscan.php.types.PhpObject;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.util.SymbolUtils;

import java.util.Set;

public class Spl extends BuiltinBase {
    public Spl(Interpreter ip) {
        super(ip);
        SymbolOperator operator = ip.getOperator();
        putConstant("\\RecursiveIteratorIterator::LEAVES_ONLY", operator.createSymbol(0));
        putConstant("\\RecursiveIteratorIterator::SELF_FIRST", operator.createSymbol(1));
        putConstant("\\RecursiveIteratorIterator::CHILD_FIRST", operator.createSymbol(2));
        putConstant("\\RecursiveIteratorIterator::CATCH_GET_CHILD", operator.createSymbol(16));

        putConstant("\\CachingIterator::CALL_TOSTRING", operator.createSymbol(1));
        putConstant("\\CachingIterator::CATCH_GET_CHILD", operator.createSymbol(16));
        putConstant("\\CachingIterator::TOSTRING_USE_KEY", operator.createSymbol(2));
        putConstant("\\CachingIterator::TOSTRING_USE_CURRENT", operator.createSymbol(4));
        putConstant("\\CachingIterator::TOSTRING_USE_INNER", operator.createSymbol(8));
        putConstant("\\CachingIterator::FULL_CACHE", operator.createSymbol(256));

        putConstant("\\RecursiveCachingIterator::CALL_TOSTRING", operator.createSymbol(1));
        putConstant("\\RecursiveCachingIterator::CATCH_GET_CHILD", operator.createSymbol(16));
        putConstant("\\RecursiveCachingIterator::TOSTRING_USE_KEY", operator.createSymbol(2));
        putConstant("\\RecursiveCachingIterator::TOSTRING_USE_CURRENT", operator.createSymbol(4));
        putConstant("\\RecursiveCachingIterator::TOSTRING_USE_INNER", operator.createSymbol(8));
        putConstant("\\RecursiveCachingIterator::FULL_CACHE", operator.createSymbol(256));

        putConstant("\\RegexIterator::USE_KEY", operator.createSymbol(1));
        putConstant("\\RegexIterator::INVERT_MATCH", operator.createSymbol(2));
        putConstant("\\RegexIterator::MATCH", operator.createSymbol(0));
        putConstant("\\RegexIterator::GET_MATCH", operator.createSymbol(1));
        putConstant("\\RegexIterator::ALL_MATCHES", operator.createSymbol(2));
        putConstant("\\RegexIterator::SPLIT", operator.createSymbol(3));
        putConstant("\\RegexIterator::REPLACE", operator.createSymbol(4));

        putConstant("\\RecursiveRegexIterator::USE_KEY", operator.createSymbol(1));
        putConstant("\\RecursiveRegexIterator::INVERT_MATCH", operator.createSymbol(2));
        putConstant("\\RecursiveRegexIterator::MATCH", operator.createSymbol(0));
        putConstant("\\RecursiveRegexIterator::GET_MATCH", operator.createSymbol(1));
        putConstant("\\RecursiveRegexIterator::ALL_MATCHES", operator.createSymbol(2));
        putConstant("\\RecursiveRegexIterator::SPLIT", operator.createSymbol(3));
        putConstant("\\RecursiveRegexIterator::REPLACE", operator.createSymbol(4));

        putConstant("\\RecursiveTreeIterator::LEAVES_ONLY", operator.createSymbol(0));
        putConstant("\\RecursiveTreeIterator::SELF_FIRST", operator.createSymbol(1));
        putConstant("\\RecursiveTreeIterator::CHILD_FIRST", operator.createSymbol(2));
        putConstant("\\RecursiveTreeIterator::CATCH_GET_CHILD", operator.createSymbol(16));
        putConstant("\\RecursiveTreeIterator::BYPASS_CURRENT", operator.createSymbol(4));
        putConstant("\\RecursiveTreeIterator::BYPASS_KEY", operator.createSymbol(8));
        putConstant("\\RecursiveTreeIterator::PREFIX_LEFT", operator.createSymbol(0));
        putConstant("\\RecursiveTreeIterator::PREFIX_MID_HAS_NEXT", operator.createSymbol(1));
        putConstant("\\RecursiveTreeIterator::PREFIX_MID_LAST", operator.createSymbol(2));
        putConstant("\\RecursiveTreeIterator::PREFIX_END_HAS_NEXT", operator.createSymbol(3));
        putConstant("\\RecursiveTreeIterator::PREFIX_END_LAST", operator.createSymbol(4));
        putConstant("\\RecursiveTreeIterator::PREFIX_RIGHT", operator.createSymbol(5));

        putConstant("\\ArrayObject::STD_PROP_LIST", operator.createSymbol(1));
        putConstant("\\ArrayObject::ARRAY_AS_PROPS", operator.createSymbol(2));

        putConstant("\\ArrayIterator::STD_PROP_LIST", operator.createSymbol(1));
        putConstant("\\ArrayIterator::ARRAY_AS_PROPS", operator.createSymbol(2));

        putConstant("\\RecursiveArrayIterator::CHILD_ARRAYS_ONLY", operator.createSymbol(4));

        putConstant("\\FilesystemIterator::CURRENT_MODE_MASK", operator.createSymbol(240));
        putConstant("\\FilesystemIterator::CURRENT_AS_PATHNAME", operator.createSymbol(32));
        putConstant("\\FilesystemIterator::CURRENT_AS_FILEINFO", operator.createSymbol(0));
        putConstant("\\FilesystemIterator::CURRENT_AS_SELF", operator.createSymbol(16));
        putConstant("\\FilesystemIterator::KEY_MODE_MASK", operator.createSymbol(3840));
        putConstant("\\FilesystemIterator::KEY_AS_PATHNAME", operator.createSymbol(0));
        putConstant("\\FilesystemIterator::FOLLOW_SYMLINKS", operator.createSymbol(512));
        putConstant("\\FilesystemIterator::KEY_AS_FILENAME", operator.createSymbol(256));
        putConstant("\\FilesystemIterator::NEW_CURRENT_AND_KEY", operator.createSymbol(256));
        putConstant("\\FilesystemIterator::OTHER_MODE_MASK", operator.createSymbol(12288));
        putConstant("\\FilesystemIterator::SKIP_DOTS", operator.createSymbol(4096));
        putConstant("\\FilesystemIterator::UNIX_PATHS", operator.createSymbol(8192));

        putConstant("\\RecursiveDirectoryIterator::CURRENT_MODE_MASK", operator.createSymbol(240));
        putConstant("\\RecursiveDirectoryIterator::CURRENT_AS_PATHNAME", operator.createSymbol(32));
        putConstant("\\RecursiveDirectoryIterator::CURRENT_AS_FILEINFO", operator.createSymbol(0));
        putConstant("\\RecursiveDirectoryIterator::CURRENT_AS_SELF", operator.createSymbol(16));
        putConstant("\\RecursiveDirectoryIterator::KEY_MODE_MASK", operator.createSymbol(3840));
        putConstant("\\RecursiveDirectoryIterator::KEY_AS_PATHNAME", operator.createSymbol(0));
        putConstant("\\RecursiveDirectoryIterator::FOLLOW_SYMLINKS", operator.createSymbol(512));
        putConstant("\\RecursiveDirectoryIterator::KEY_AS_FILENAME", operator.createSymbol(256));
        putConstant("\\RecursiveDirectoryIterator::NEW_CURRENT_AND_KEY", operator.createSymbol(256));
        putConstant("\\RecursiveDirectoryIterator::OTHER_MODE_MASK", operator.createSymbol(12288));
        putConstant("\\RecursiveDirectoryIterator::SKIP_DOTS", operator.createSymbol(4096));
        putConstant("\\RecursiveDirectoryIterator::UNIX_PATHS", operator.createSymbol(8192));

        putConstant("\\GlobIterator::CURRENT_MODE_MASK", operator.createSymbol(240));
        putConstant("\\GlobIterator::CURRENT_AS_PATHNAME", operator.createSymbol(32));
        putConstant("\\GlobIterator::CURRENT_AS_FILEINFO", operator.createSymbol(0));
        putConstant("\\GlobIterator::CURRENT_AS_SELF", operator.createSymbol(16));
        putConstant("\\GlobIterator::KEY_MODE_MASK", operator.createSymbol(3840));
        putConstant("\\GlobIterator::KEY_AS_PATHNAME", operator.createSymbol(0));
        putConstant("\\GlobIterator::FOLLOW_SYMLINKS", operator.createSymbol(512));
        putConstant("\\GlobIterator::KEY_AS_FILENAME", operator.createSymbol(256));
        putConstant("\\GlobIterator::NEW_CURRENT_AND_KEY", operator.createSymbol(256));
        putConstant("\\GlobIterator::OTHER_MODE_MASK", operator.createSymbol(12288));
        putConstant("\\GlobIterator::SKIP_DOTS", operator.createSymbol(4096));
        putConstant("\\GlobIterator::UNIX_PATHS", operator.createSymbol(8192));

        putConstant("\\SplFileObject::DROP_NEW_LINE", operator.createSymbol(1));
        putConstant("\\SplFileObject::READ_AHEAD", operator.createSymbol(2));
        putConstant("\\SplFileObject::SKIP_EMPTY", operator.createSymbol(4));
        putConstant("\\SplFileObject::READ_CSV", operator.createSymbol(8));

        putConstant("\\SplTempFileObject::DROP_NEW_LINE", operator.createSymbol(1));
        putConstant("\\SplTempFileObject::READ_AHEAD", operator.createSymbol(2));
        putConstant("\\SplTempFileObject::SKIP_EMPTY", operator.createSymbol(4));
        putConstant("\\SplTempFileObject::READ_CSV", operator.createSymbol(8));

        putConstant("\\SplDoublyLinkedList::IT_MODE_LIFO", operator.createSymbol(2));
        putConstant("\\SplDoublyLinkedList::IT_MODE_FIFO", operator.createSymbol(0));
        putConstant("\\SplDoublyLinkedList::IT_MODE_DELETE", operator.createSymbol(1));
        putConstant("\\SplDoublyLinkedList::IT_MODE_KEEP", operator.createSymbol(0));

        putConstant("\\SplQueue::IT_MODE_LIFO", operator.createSymbol(2));
        putConstant("\\SplQueue::IT_MODE_FIFO", operator.createSymbol(0));
        putConstant("\\SplQueue::IT_MODE_DELETE", operator.createSymbol(1));
        putConstant("\\SplQueue::IT_MODE_KEEP", operator.createSymbol(0));

        putConstant("\\SplStack::IT_MODE_LIFO", operator.createSymbol(2));
        putConstant("\\SplStack::IT_MODE_FIFO", operator.createSymbol(0));
        putConstant("\\SplStack::IT_MODE_DELETE", operator.createSymbol(1));
        putConstant("\\SplStack::IT_MODE_KEEP", operator.createSymbol(0));

        putConstant("\\SplPriorityQueue::EXTR_BOTH", operator.createSymbol(3));
        putConstant("\\SplPriorityQueue::EXTR_PRIORITY", operator.createSymbol(2));
        putConstant("\\SplPriorityQueue::EXTR_DATA", operator.createSymbol(1));

        putConstant("\\MultipleIterator::MIT_NEED_ANY", operator.createSymbol(0));
        putConstant("\\MultipleIterator::MIT_NEED_ALL", operator.createSymbol(1));
        putConstant("\\MultipleIterator::MIT_KEYS_NUMERIC", operator.createSymbol(0));
        putConstant("\\MultipleIterator::MIT_KEYS_ASSOC", operator.createSymbol(2));

    }

    public static class LogicException extends PhpClassBase {
        public LogicException(Interpreter ip) {
            super(ip);
        }

        @Override
        public void initialize(Interpreter interpreter) {
            super.initialize(interpreter);
        }

        @Override
        public String getAbsoluteParentClassName() {
            return SymbolUtils.getAbsoluteNameFromJavaClassName(Exception.class.getName());
        }
    }

    public static class BadFunctionCallException extends PhpClassBase {
        public BadFunctionCallException(Interpreter ip) {
            super(ip);
        }

        @Override
        public void initialize(Interpreter interpreter) {
            super.initialize(interpreter);
        }

        @Override
        public String getAbsoluteParentClassName() {
            return SymbolUtils.getAbsoluteNameFromJavaClassName(LogicException.class.getName());
        }
    }

    public static class BadMethodCallException extends PhpClassBase {
        public BadMethodCallException(Interpreter ip) {
            super(ip);
        }

        @Override
        public void initialize(Interpreter interpreter) {
            super.initialize(interpreter);
        }

        @Override
        public String getAbsoluteParentClassName() {
            return SymbolUtils.getAbsoluteNameFromJavaClassName(BadFunctionCallException.class.getName());
        }
    }

    public static class DomainException extends PhpClassBase {
        public DomainException(Interpreter ip) {
            super(ip);
        }

        @Override
        public void initialize(Interpreter interpreter) {
            super.initialize(interpreter);
        }

        @Override
        public String getAbsoluteParentClassName() {
            return SymbolUtils.getAbsoluteNameFromJavaClassName(LogicException.class.getName());
        }
    }

    public static class InvalidArgumentException extends PhpClassBase {
        public InvalidArgumentException(Interpreter ip) {
            super(ip);
        }

        @Override
        public void initialize(Interpreter interpreter) {
            super.initialize(interpreter);
        }

        @Override
        public String getAbsoluteParentClassName() {
            return SymbolUtils.getAbsoluteNameFromJavaClassName(LogicException.class.getName());
        }
    }

    public static class LengthException extends PhpClassBase {
        public LengthException(Interpreter ip) {
            super(ip);
        }

        @Override
        public void initialize(Interpreter interpreter) {
            super.initialize(interpreter);
        }

        @Override
        public String getAbsoluteParentClassName() {
            return SymbolUtils.getAbsoluteNameFromJavaClassName(LogicException.class.getName());
        }
    }

    public static class OutOfRangeException extends PhpClassBase {
        public OutOfRangeException(Interpreter ip) {
            super(ip);
        }

        @Override
        public void initialize(Interpreter interpreter) {
            super.initialize(interpreter);
        }

        @Override
        public String getAbsoluteParentClassName() {
            return SymbolUtils.getAbsoluteNameFromJavaClassName(LogicException.class.getName());
        }
    }

    public static class RuntimeException extends PhpClassBase {
        public RuntimeException(Interpreter ip) {
            super(ip);
        }

        @Override
        public void initialize(Interpreter interpreter) {
            super.initialize(interpreter);
        }

        @Override
        public String getAbsoluteParentClassName() {
            return SymbolUtils.getAbsoluteNameFromJavaClassName(Exception.class.getName());
        }
    }

    public static class OutOfBoundsException extends PhpClassBase {
        public OutOfBoundsException(Interpreter ip) {
            super(ip);
        }

        @Override
        public void initialize(Interpreter interpreter) {
            super.initialize(interpreter);
        }

        @Override
        public String getAbsoluteParentClassName() {
            return SymbolUtils.getAbsoluteNameFromJavaClassName(RuntimeException.class.getName());
        }
    }

    public static class OverflowException extends PhpClassBase {
        public OverflowException(Interpreter ip) {
            super(ip);
        }

        @Override
        public void initialize(Interpreter interpreter) {
            super.initialize(interpreter);
        }

        @Override
        public String getAbsoluteParentClassName() {
            return SymbolUtils.getAbsoluteNameFromJavaClassName(RuntimeException.class.getName());
        }
    }

    public static class RangeException extends PhpClassBase {
        public RangeException(Interpreter ip) {
            super(ip);
        }

        @Override
        public void initialize(Interpreter interpreter) {
            super.initialize(interpreter);
        }

        @Override
        public String getAbsoluteParentClassName() {
            return SymbolUtils.getAbsoluteNameFromJavaClassName(RuntimeException.class.getName());
        }
    }

    public static class UnderflowException extends PhpClassBase {
        public UnderflowException(Interpreter ip) {
            super(ip);
        }

        @Override
        public void initialize(Interpreter interpreter) {
            super.initialize(interpreter);
        }

        @Override
        public String getAbsoluteParentClassName() {
            return SymbolUtils.getAbsoluteNameFromJavaClassName(RuntimeException.class.getName());
        }
    }

    public static class UnexpectedValueException extends PhpClassBase {
        public UnexpectedValueException(Interpreter ip) {
            super(ip);
        }

        @Override
        public void initialize(Interpreter interpreter) {
            super.initialize(interpreter);
        }

        @Override
        public String getAbsoluteParentClassName() {
            return SymbolUtils.getAbsoluteNameFromJavaClassName(RuntimeException.class.getName());
        }
    }

    public static class RecursiveIterator extends PhpClassBase {

        public RecursiveIterator(Interpreter ip) {
            super(ip);
        }
    }

    private static final String ITERATOR = "ITERATOR";

    public static class RecursiveIteratorIterator extends PhpClassBase {

        public RecursiveIteratorIterator(Interpreter ip) {
            super(ip);
        }

        // public function __construct (Traversable $iterator, $mode = null, $flags = null) {}
        public static class __construct implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol iteratorSymbol = SymbolUtils.getArgument(ip, 0);
                operator.putFieldValue(ip.getContext().getThisSymbol(), ITERATOR, iteratorSymbol);
                return operator.null_();
            }
        }

        // public function rewind () {}
        public static class rewind implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol resultSymbol = operator.createSymbol();
                Symbol iteratorSymbol =
                        operator.getFieldValue(ip.getContext().getThisSymbol(), ITERATOR);
                for (PhpObject phpObject : operator.extractPhpObject(iteratorSymbol)) {
                    PhpCallable method = phpObject.getPhpClass().getMethod("rewind");
                    if (method != null) {
                        operator.merge(resultSymbol, SymbolUtils.callMethod(
                                ip, phpObject, method, Lists.newArrayList()));
                    }
                }
                return resultSymbol;
            }
        }

        // public function valid () {}
        public static class valid implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol resultSymbol = operator.createSymbol();
                Symbol iteratorSymbol =
                        operator.getFieldValue(ip.getContext().getThisSymbol(), ITERATOR);
                for (PhpObject phpObject : operator.extractPhpObject(iteratorSymbol)) {
                    PhpCallable method = phpObject.getPhpClass().getMethod("valid");
                    if (method != null) {
                        operator.merge(resultSymbol, SymbolUtils.callMethod(
                                ip, phpObject, method, Lists.newArrayList()));
                    }
                }
                return resultSymbol;
            }
        }

        // public function key () {}
        public static class key implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol resultSymbol = operator.createSymbol();
                Symbol iteratorSymbol =
                        operator.getFieldValue(ip.getContext().getThisSymbol(), ITERATOR);
                for (PhpObject phpObject : operator.extractPhpObject(iteratorSymbol)) {
                    PhpCallable method = phpObject.getPhpClass().getMethod("key");
                    if (method != null) {
                        operator.merge(resultSymbol, SymbolUtils.callMethod(
                                ip, phpObject, method, Lists.newArrayList()));
                    }
                }
                return resultSymbol;
            }
        }

        // public function current () {}
        public static class current implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol resultSymbol = operator.createSymbol();
                Symbol iteratorSymbol =
                        operator.getFieldValue(ip.getContext().getThisSymbol(), ITERATOR);
                for (PhpObject phpObject : operator.extractPhpObject(iteratorSymbol)) {
                    PhpCallable method = phpObject.getPhpClass().getMethod("current");
                    if (method != null) {
                        operator.merge(resultSymbol, SymbolUtils.callMethod(
                                ip, phpObject, method, Lists.newArrayList()));
                    }
                }
                return resultSymbol;
            }
        }

        // public function next () {}
        public static class next implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol resultSymbol = operator.createSymbol();
                Symbol iteratorSymbol =
                        operator.getFieldValue(ip.getContext().getThisSymbol(), ITERATOR);
                for (PhpObject phpObject : operator.extractPhpObject(iteratorSymbol)) {
                    PhpCallable method = phpObject.getPhpClass().getMethod("next");
                    if (method != null) {
                        operator.merge(resultSymbol, SymbolUtils.callMethod(
                                ip, phpObject, method, Lists.newArrayList()));
                    }
                }
                return resultSymbol;
            }
        }

        // public function getDepth () {}
        public static class getDepth implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function getSubIterator ($level = null) {}
        public static class getSubIterator implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                RecursiveIterator clazz = new RecursiveIterator(ip);
                Symbol iteratorSymbol =
                        operator.getFieldValue(ip.getContext().getThisSymbol(), ITERATOR);
                return SymbolUtils.new_(ip, clazz,
                        Lists.newArrayList(iteratorSymbol));
            }
        }

        // public function getInnerIterator () {}
        public static class getInnerIterator implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                RecursiveIterator clazz = new RecursiveIterator(ip);
                Symbol iteratorSymbol =
                        operator.getFieldValue(ip.getContext().getThisSymbol(), ITERATOR);
                return SymbolUtils.new_(ip, clazz,
                        Lists.newArrayList(iteratorSymbol));
            }
        }

        // public function beginIteration () {}
        public static class beginIteration implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function endIteration () {}
        public static class endIteration implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function callHasChildren () {}
        public static class callHasChildren implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function callGetChildren () {}
        public static class callGetChildren implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                RecursiveIterator clazz = new RecursiveIterator(ip);
                Symbol iteratorSymbol =
                        operator.getFieldValue(ip.getContext().getThisSymbol(), ITERATOR);
                return SymbolUtils.new_(ip, clazz,
                        Lists.newArrayList(iteratorSymbol));
            }
        }

        // public function beginChildren () {}
        public static class beginChildren implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function endChildren () {}
        public static class endChildren implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function nextElement () {}
        public static class nextElement implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function setMaxDepth ($max_depth = null) {}
        public static class setMaxDepth implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function getMaxDepth () {}
        public static class getMaxDepth implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }
    }

    public static class OuterIterator extends PhpClassBase {

        public OuterIterator(Interpreter ip) {
            super(ip);
        }
    }

    public static class IteratorIterator extends PhpClassBase {
        public IteratorIterator(Interpreter ip) {
            super(ip);
        }

        // public function __construct (Traversable $iterator) {}
        public static class __construct implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol iteratorSymbol = SymbolUtils.getArgument(ip, 0);
                operator.putFieldValue(ip.getContext().getThisSymbol(), ITERATOR, iteratorSymbol);
                return operator.null_();
            }
        }

        // public function rewind () {}
        public static class rewind implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol resultSymbol = operator.createSymbol();
                Symbol iteratorSymbol =
                        operator.getFieldValue(ip.getContext().getThisSymbol(), ITERATOR);
                for (PhpObject phpObject : operator.extractPhpObject(iteratorSymbol)) {
                    PhpCallable method = phpObject.getPhpClass().getMethod("rewind");
                    if (method != null) {
                        operator.merge(resultSymbol, SymbolUtils.callMethod(
                                ip, phpObject, method, Lists.newArrayList()));
                    }
                }
                return resultSymbol;
            }
        }

        // public function valid () {}
        public static class valid implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol resultSymbol = operator.createSymbol();
                Symbol iteratorSymbol =
                        operator.getFieldValue(ip.getContext().getThisSymbol(), ITERATOR);
                for (PhpObject phpObject : operator.extractPhpObject(iteratorSymbol)) {
                    PhpCallable method = phpObject.getPhpClass().getMethod("valid");
                    if (method != null) {
                        operator.merge(resultSymbol, SymbolUtils.callMethod(
                                ip, phpObject, method, Lists.newArrayList()));
                    }
                }
                return resultSymbol;
            }
        }

        // public function key () {}
        public static class key implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol resultSymbol = operator.createSymbol();
                Symbol iteratorSymbol =
                        operator.getFieldValue(ip.getContext().getThisSymbol(), ITERATOR);
                for (PhpObject phpObject : operator.extractPhpObject(iteratorSymbol)) {
                    PhpCallable method = phpObject.getPhpClass().getMethod("key");
                    if (method != null) {
                        operator.merge(resultSymbol, SymbolUtils.callMethod(
                                ip, phpObject, method, Lists.newArrayList()));
                    }
                }
                return resultSymbol;
            }
        }

        // public function current () {}
        public static class current implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol resultSymbol = operator.createSymbol();
                Symbol iteratorSymbol =
                        operator.getFieldValue(ip.getContext().getThisSymbol(), ITERATOR);
                for (PhpObject phpObject : operator.extractPhpObject(iteratorSymbol)) {
                    PhpCallable method = phpObject.getPhpClass().getMethod("current");
                    if (method != null) {
                        operator.merge(resultSymbol, SymbolUtils.callMethod(
                                ip, phpObject, method, Lists.newArrayList()));
                    }
                }
                return resultSymbol;
            }
        }

        // public function next () {}
        public static class next implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol resultSymbol = operator.createSymbol();
                Symbol iteratorSymbol =
                        operator.getFieldValue(ip.getContext().getThisSymbol(), ITERATOR);
                for (PhpObject phpObject : operator.extractPhpObject(iteratorSymbol)) {
                    PhpCallable method = phpObject.getPhpClass().getMethod("next");
                    if (method != null) {
                        operator.merge(resultSymbol, SymbolUtils.callMethod(
                                ip, phpObject, method, Lists.newArrayList()));
                    }
                }
                return resultSymbol;
            }
        }

        // public function getInnerIterator () {}
        public static class getInnerIterator implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                RecursiveIterator clazz = new RecursiveIterator(ip);
                Symbol iteratorSymbol =
                        operator.getFieldValue(ip.getContext().getThisSymbol(), ITERATOR);
                return SymbolUtils.new_(ip, clazz,
                        Lists.newArrayList(iteratorSymbol));
            }
        }
    }

    public static class FilterIterator extends PhpClassBase {
        public FilterIterator(Interpreter ip) {
            super(ip);
        }

        @Override
        public String getAbsoluteParentClassName() {
            return SymbolUtils.getAbsoluteNameFromJavaClassName(IteratorIterator.class.getName());
        }

        public static class __construct implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol iteratorSymbol = SymbolUtils.getArgument(ip, 0);
                operator.putFieldValue(ip.getContext().getThisSymbol(), ITERATOR, iteratorSymbol);
                return operator.null_();
            }
        }

        // abstract public function accept () {}
    }

    public static class RecursiveFilterIterator extends PhpClassBase {

        public RecursiveFilterIterator(Interpreter ip) {
            super(ip);
        }

        @Override
        public String getAbsoluteParentClassName() {
            return SymbolUtils.getAbsoluteNameFromJavaClassName(FilterIterator.class.getName());
        }

        public static class __construct implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol iteratorSymbol = SymbolUtils.getArgument(ip, 0);
                operator.putFieldValue(ip.getContext().getThisSymbol(), ITERATOR, iteratorSymbol);
                return operator.null_();
            }
        }

        // public function hasChildren () {}
        public static class hasChildren implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function getChildren () {}
        public static class getChildren implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol iteratorSymbol =
                        operator.getFieldValue(ip.getContext().getThisSymbol(), ITERATOR);
                return iteratorSymbol;
            }
        }

        // abstract public function accept () {}
    }

    private static final String CALLBACK = "CALLBACK";

    public static class CallbackFilterIterator extends PhpClassBase {

        public CallbackFilterIterator(Interpreter ip) {
            super(ip);
        }

        @Override
        public String getAbsoluteParentClassName() {
            return SymbolUtils.getAbsoluteNameFromJavaClassName(FilterIterator.class.getName());
        }

        // public function __construct (Iterator $iterator, $callback) {}
        public static class __construct implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol iteratorSymbol = SymbolUtils.getArgument(ip, 0);
                operator.putFieldValue(ip.getContext().getThisSymbol(), ITERATOR, iteratorSymbol);
                Symbol callbackSymbol = SymbolUtils.getArgument(ip, 1);
                operator.putFieldValue(ip.getContext().getThisSymbol(), CALLBACK, callbackSymbol);
                return operator.null_();
            }
        }

        // public function accept () {}
        public static class accept implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol iteratorSymbol =
                        operator.getFieldValue(ip.getContext().getThisSymbol(), ITERATOR);

                Symbol currentSymbol = operator.createSymbol();
                for (PhpObject phpObject : operator.extractPhpObject(iteratorSymbol)) {
                    PhpCallable method = phpObject.getPhpClass().getMethod("current");
                    if (method != null) {
                        operator.merge(currentSymbol, SymbolUtils.callMethod(
                                ip, phpObject, method, Lists.newArrayList()));
                    }
                }

                Symbol keySymbol = operator.createSymbol();
                for (PhpObject phpObject : operator.extractPhpObject(iteratorSymbol)) {
                    PhpCallable method = phpObject.getPhpClass().getMethod("key");
                    if (method != null) {
                        operator.merge(keySymbol, SymbolUtils.callMethod(
                                ip, phpObject, method, Lists.newArrayList()));
                    }
                }

                Symbol callbackSymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), CALLBACK);
                SymbolUtils.callCallable(ip, callbackSymbol, Lists.newArrayList(
                        currentSymbol, keySymbol, iteratorSymbol));
                return operator.string();
            }
        }
    }

    public static class RecursiveCallbackFilterIterator extends PhpClassBase {

        public RecursiveCallbackFilterIterator(Interpreter ip) {
            super(ip);
        }

        @Override
        public String getAbsoluteParentClassName() {
            return SymbolUtils.getAbsoluteNameFromJavaClassName(CallbackFilterIterator.class.getName());
        }

        public static class __construct implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol iteratorSymbol = SymbolUtils.getArgument(ip, 0);
                operator.putFieldValue(ip.getContext().getThisSymbol(), ITERATOR, iteratorSymbol);
                Symbol callbackSymbol = SymbolUtils.getArgument(ip, 1);
                operator.putFieldValue(ip.getContext().getThisSymbol(), CALLBACK, callbackSymbol);
                return operator.null_();
            }
        }

        // public function hasChildren () {}
        public static class hasChildren implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function getChildren () {}
        public static class getChildren implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol iteratorSymbol =
                        operator.getFieldValue(ip.getContext().getThisSymbol(), ITERATOR);
                return iteratorSymbol;
            }
        }

    }

    public static class ParentIterator extends PhpClassBase {

        public ParentIterator(Interpreter ip) {
            super(ip);
        }

        @Override
        public String getAbsoluteParentClassName() {
            return SymbolUtils.getAbsoluteNameFromJavaClassName(RecursiveFilterIterator.class.getName());
        }

        public static class __construct implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol iteratorSymbol = SymbolUtils.getArgument(ip, 0);
                operator.putFieldValue(ip.getContext().getThisSymbol(), ITERATOR, iteratorSymbol);
                return operator.null_();
            }
        }

        // public function accept () {}
        public static class accept implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol iteratorSymbol =
                        operator.getFieldValue(ip.getContext().getThisSymbol(), ITERATOR);

                Symbol currentSymbol = operator.createSymbol();
                for (PhpObject phpObject : operator.extractPhpObject(iteratorSymbol)) {
                    PhpCallable method = phpObject.getPhpClass().getMethod("current");
                    if (method != null) {
                        operator.merge(currentSymbol, SymbolUtils.callMethod(
                                ip, phpObject, method, Lists.newArrayList()));
                    }
                }

                Symbol keySymbol = operator.createSymbol();
                for (PhpObject phpObject : operator.extractPhpObject(iteratorSymbol)) {
                    PhpCallable method = phpObject.getPhpClass().getMethod("key");
                    if (method != null) {
                        operator.merge(keySymbol, SymbolUtils.callMethod(
                                ip, phpObject, method, Lists.newArrayList()));
                    }
                }

                Symbol callbackSymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), CALLBACK);
                SymbolUtils.callCallable(ip, callbackSymbol, Lists.newArrayList(
                        currentSymbol, keySymbol, iteratorSymbol));
                return operator.string();
            }
        }

    }

    public static class Countable extends PhpClassBase {

        public Countable(Interpreter ip) {
            super(ip);
        }
    }

    public static class SeekableIterator extends PhpClassBase {

        public SeekableIterator(Interpreter ip) {
            super(ip);
        }
    }

    public static class LimitIterator extends PhpClassBase {
        public LimitIterator(Interpreter ip) {
            super(ip);
        }

        @Override
        public String getAbsoluteParentClassName() {
            return SymbolUtils.getAbsoluteNameFromJavaClassName(IteratorIterator.class.getName());
        }

        public static class __construct implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol iteratorSymbol = SymbolUtils.getArgument(ip, 0);
                operator.putFieldValue(ip.getContext().getThisSymbol(), ITERATOR, iteratorSymbol);
                return operator.null_();
            }
        }

        //		public function seek ($position) {}
        public static class seek implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        //		public function getPosition () {}
        public static class getPosition implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

    }

    public static class CachingIterator extends PhpClassBase {
        public CachingIterator(Interpreter ip) {
            super(ip);
        }

        @Override
        public String getAbsoluteParentClassName() {
            return SymbolUtils.getAbsoluteNameFromJavaClassName(IteratorIterator.class.getName());
        }

        public static class __construct implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol iteratorSymbol = SymbolUtils.getArgument(ip, 0);
                operator.putFieldValue(ip.getContext().getThisSymbol(), ITERATOR, iteratorSymbol);
                return operator.null_();
            }
        }

        //		public function hasNext () {}
        public static class hasNext implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        //		public function __toString () {}

        //		public function getFlags () {}
        public static class getFlags implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        //		public function setFlags ($flags) {}
        public static class setFlags implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        //		public function offsetGet ($index) {}
        public static class offsetGet implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        //		public function offsetSet ($index, $newval) {}
        public static class offsetSet implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        //		public function offsetUnset ($index) {}
        public static class offsetUnset implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        //		public function offsetExists ($index) {}
        public static class offsetExists implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        //		public function getCache () {}
        public static class getCache implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.array();
            }
        }

        //		public function count () {}
        public static class count implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

    }

    public static class RecursiveCachingIterator extends PhpClassBase {
        public RecursiveCachingIterator(Interpreter ip) {
            super(ip);
        }

        @Override
        public String getAbsoluteParentClassName() {
            return SymbolUtils.getAbsoluteNameFromJavaClassName(CachingIterator.class.getName());
        }

        public static class __construct implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol iteratorSymbol = SymbolUtils.getArgument(ip, 0);
                operator.putFieldValue(ip.getContext().getThisSymbol(), ITERATOR, iteratorSymbol);
                return operator.null_();
            }
        }

        //		public function hasChildren () {}
        public static class hasChildren implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        //		public function getChildren () {}
        public static class getChildren implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                RecursiveCachingIterator clazz = new RecursiveCachingIterator(ip);
                Symbol iteratorSymbol =
                        operator.getFieldValue(ip.getContext().getThisSymbol(), ITERATOR);
                return SymbolUtils.new_(ip, clazz,
                        Lists.newArrayList(iteratorSymbol));
            }
        }

    }

    public static class NoRewindIterator extends PhpClassBase {
        public NoRewindIterator(Interpreter ip) {
            super(ip);
        }

        @Override
        public String getAbsoluteParentClassName() {
            return SymbolUtils.getAbsoluteNameFromJavaClassName(IteratorIterator.class.getName());
        }

        public static class __construct implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol iteratorSymbol = SymbolUtils.getArgument(ip, 0);
                operator.putFieldValue(ip.getContext().getThisSymbol(), ITERATOR, iteratorSymbol);
                return operator.null_();
            }
        }
    }

    public static class AppendIterator extends PhpClassBase {
        public AppendIterator(Interpreter ip) {
            super(ip);
        }

        @Override
        public String getAbsoluteParentClassName() {
            return SymbolUtils.getAbsoluteNameFromJavaClassName(IteratorIterator.class.getName());
        }

        public static class __construct implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol iteratorSymbol = SymbolUtils.getArgument(ip, 0);
                operator.putFieldValue(ip.getContext().getThisSymbol(), ITERATOR, iteratorSymbol);
                return operator.null_();
            }
        }

        //		public function append ($iterator) {}
        public static class getChildren implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol iteratorSymbol = SymbolUtils.getArgument(ip, 0);
                Symbol iteratorSymbol2 =
                        operator.getFieldValue(ip.getContext().getThisSymbol(), ITERATOR);
                operator.merge(iteratorSymbol2, iteratorSymbol);
                operator.putFieldValue(ip.getContext().getThisSymbol(), ITERATOR, iteratorSymbol2);
                return operator.integer();
            }
        }

        //		public function getIteratorIndex () {}
        public static class getIteratorIndex implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        //		public function getArrayIterator () {}
        public static class getArrayIterator implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                ArrayIterator clazz = new ArrayIterator(ip);
                Symbol iteratorSymbol =
                        operator.getFieldValue(ip.getContext().getThisSymbol(), ITERATOR);
                return SymbolUtils.new_(ip, clazz,
                        Lists.newArrayList(iteratorSymbol));
            }
        }

    }

    public static class InfiniteIterator extends PhpClassBase {
        public InfiniteIterator(Interpreter ip) {
            super(ip);
        }

        @Override
        public String getAbsoluteParentClassName() {
            return SymbolUtils.getAbsoluteNameFromJavaClassName(IteratorIterator.class.getName());
        }

        public static class __construct implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol iteratorSymbol = SymbolUtils.getArgument(ip, 0);
                operator.putFieldValue(ip.getContext().getThisSymbol(), ITERATOR, iteratorSymbol);
                return operator.null_();
            }
        }
    }

    private static final String REGEX = "REGEX";

    public static class RegexIterator extends PhpClassBase {
        public RegexIterator(Interpreter ip) {
            super(ip);
        }

        @Override
        public String getAbsoluteParentClassName() {
            return SymbolUtils.getAbsoluteNameFromJavaClassName(FilterIterator.class.getName());
        }

        //		public function __construct (Iterator $iterator, $regex, $mode = null, $flags = null, $preg_flags = null) {}
        public static class __construct implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol iteratorSymbol = SymbolUtils.getArgument(ip, 0);
                operator.putFieldValue(ip.getContext().getThisSymbol(), ITERATOR, iteratorSymbol);
                Symbol regexSymbol = SymbolUtils.getArgument(ip, 1);
                operator.putFieldValue(ip.getContext().getThisSymbol(), REGEX, regexSymbol);
                return operator.null_();
            }
        }

        //		public function accept () {}
        public static class accept implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        //		public function getMode () {}
        public static class getMode implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        //		public function setMode ($mode) {}
        public static class setMode implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        //		public function getFlags () {}
        public static class getFlags implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        //		public function setFlags ($flags) {}
        public static class setFlags implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        //		public function getPregFlags () {}
        public static class getPregFlags implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        //		public function setPregFlags ($preg_flags) {}
        public static class setPregFlags implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        //		public function getRegex () {}
        public static class getRegex implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.getFieldValue(ip.getContext().getThisSymbol(), REGEX);
            }
        }

    }

    public static class RecursiveRegexIterator extends PhpClassBase {
        public RecursiveRegexIterator(Interpreter ip) {
            super(ip);
        }

        @Override
        public String getAbsoluteParentClassName() {
            return SymbolUtils.getAbsoluteNameFromJavaClassName(RegexIterator.class.getName());
        }

        //		public function __construct (RecursiveIterator $iterator, $regex, $mode = null, $flags = null, $preg_flags = null) {}
        public static class __construct implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol iteratorSymbol = SymbolUtils.getArgument(ip, 0);
                operator.putFieldValue(ip.getContext().getThisSymbol(), ITERATOR, iteratorSymbol);
                Symbol regexSymbol = SymbolUtils.getArgument(ip, 1);
                operator.putFieldValue(ip.getContext().getThisSymbol(), REGEX, regexSymbol);
                return operator.null_();
            }
        }

        //		public function hasChildren () {}
        public static class hasChildren implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        //		public function getChildren () {}
        public static class getChildren implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                RecursiveRegexIterator clazz = new RecursiveRegexIterator(ip);
                Symbol iteratorSymbol =
                        operator.getFieldValue(ip.getContext().getThisSymbol(), ITERATOR);
                Symbol regexSymbol =
                        operator.getFieldValue(ip.getContext().getThisSymbol(), REGEX);
                return SymbolUtils.new_(ip, clazz,
                        Lists.newArrayList(iteratorSymbol, regexSymbol));
            }
        }

    }

    public static class EmptyIterator extends PhpClassBase {
        public EmptyIterator(Interpreter ip) {
            super(ip);
        }

        // public function rewind () {}
        public static class rewind implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol resultSymbol = operator.createSymbol();
                Symbol iteratorSymbol =
                        operator.getFieldValue(ip.getContext().getThisSymbol(), ITERATOR);
                for (PhpObject phpObject : operator.extractPhpObject(iteratorSymbol)) {
                    PhpCallable method = phpObject.getPhpClass().getMethod("rewind");
                    if (method != null) {
                        operator.merge(resultSymbol, SymbolUtils.callMethod(
                                ip, phpObject, method, Lists.newArrayList()));
                    }
                }
                return resultSymbol;
            }
        }

        // public function valid () {}
        public static class valid implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol resultSymbol = operator.createSymbol();
                Symbol iteratorSymbol =
                        operator.getFieldValue(ip.getContext().getThisSymbol(), ITERATOR);
                for (PhpObject phpObject : operator.extractPhpObject(iteratorSymbol)) {
                    PhpCallable method = phpObject.getPhpClass().getMethod("valid");
                    if (method != null) {
                        operator.merge(resultSymbol, SymbolUtils.callMethod(
                                ip, phpObject, method, Lists.newArrayList()));
                    }
                }
                return resultSymbol;
            }
        }

        // public function key () {}
        public static class key implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol resultSymbol = operator.createSymbol();
                Symbol iteratorSymbol =
                        operator.getFieldValue(ip.getContext().getThisSymbol(), ITERATOR);
                for (PhpObject phpObject : operator.extractPhpObject(iteratorSymbol)) {
                    PhpCallable method = phpObject.getPhpClass().getMethod("key");
                    if (method != null) {
                        operator.merge(resultSymbol, SymbolUtils.callMethod(
                                ip, phpObject, method, Lists.newArrayList()));
                    }
                }
                return resultSymbol;
            }
        }

        // public function current () {}
        public static class current implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol resultSymbol = operator.createSymbol();
                Symbol iteratorSymbol =
                        operator.getFieldValue(ip.getContext().getThisSymbol(), ITERATOR);
                for (PhpObject phpObject : operator.extractPhpObject(iteratorSymbol)) {
                    PhpCallable method = phpObject.getPhpClass().getMethod("current");
                    if (method != null) {
                        operator.merge(resultSymbol, SymbolUtils.callMethod(
                                ip, phpObject, method, Lists.newArrayList()));
                    }
                }
                return resultSymbol;
            }
        }

        // public function next () {}
        public static class next implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol resultSymbol = operator.createSymbol();
                Symbol iteratorSymbol =
                        operator.getFieldValue(ip.getContext().getThisSymbol(), ITERATOR);
                for (PhpObject phpObject : operator.extractPhpObject(iteratorSymbol)) {
                    PhpCallable method = phpObject.getPhpClass().getMethod("next");
                    if (method != null) {
                        operator.merge(resultSymbol, SymbolUtils.callMethod(
                                ip, phpObject, method, Lists.newArrayList()));
                    }
                }
                return resultSymbol;
            }
        }

    }

    public static class RecursiveTreeIterator extends PhpClassBase {
        public RecursiveTreeIterator(Interpreter ip) {
            super(ip);
        }

        @Override
        public String getAbsoluteParentClassName() {
            return SymbolUtils.getAbsoluteNameFromJavaClassName(RecursiveIteratorIterator.class.getName());
        }

        // public function __construct (Traversable $iterator, $flags = null, $caching_it_flags = null, $mode = null) {}
        public static class __construct implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol iteratorSymbol = SymbolUtils.getArgument(ip, 0);
                operator.putFieldValue(ip.getContext().getThisSymbol(), ITERATOR, iteratorSymbol);
                return operator.null_();
            }
        }

        // public function getPrefix () {}
        public static class getPrefix implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.string();
            }
        }

        // public function setPrefixPart ($part, $value) {}
        public static class setPrefixPart implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function getEntry () {}
        public static class getEntry implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.string();
            }
        }

        // public function setPostfix () {}
        public static class setPostfix implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function getPostfix () {}
        public static class getPostfix implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.string();
            }
        }

    }

    private static final String ARRAY = "ARRAY";

    public static class ArrayObject extends PhpClassBase {
        public ArrayObject(Interpreter ip) {
            super(ip);
        }

        // public function __construct ($array) {}
        public static class __construct implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol arraySymbol = SymbolUtils.getArgument(ip, 0);
                operator.putFieldValue(ip.getContext().getThisSymbol(), ARRAY, arraySymbol);
                return operator.null_();
            }
        }

        // public function offsetExists ($index) {}
        public static class offsetExists implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function offsetGet ($index) {}
        public static class offsetGet implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol arraySymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), ARRAY);
                return operator.getArrayValue(arraySymbol, SymbolUtils.getArgument(ip, 0));
            }
        }

        // public function offsetSet ($index, $newval) {}
        public static class offsetSet implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function offsetUnset ($index) {}
        public static class offsetUnset implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function append ($value) {}
        public static class append implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol arraySymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), ARRAY);
                operator.putArrayValue(arraySymbol,
                        operator.getNextIndex(arraySymbol), SymbolUtils.getArgument(ip, 0));
                return operator.null_();
            }
        }

        // public function getArrayCopy () {}
        public static class getArrayCopy implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol arraySymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), ARRAY);
                return operator.copy(arraySymbol);
            }
        }

        // public function count () {}
        public static class count implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function getFlags () {}
        public static class getFlags implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function setFlags ($flags) {}
        public static class setFlags implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function asort () {}
        public static class asort implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function ksort () {}
        public static class ksort implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function uasort ($cmp_function) {}
        public static class uasort implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol arraySymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), ARRAY);
                Symbol cmpfunctionSymbol = SymbolUtils.getArgument(ip, 0);
                SymbolUtils.callCallable(ip, cmpfunctionSymbol,
                        Lists.newArrayList(operator.getMergedArrayValueSymbol(arraySymbol),
                                operator.getMergedArrayValueSymbol(arraySymbol)));
                return operator.null_();
            }
        }

        // public function uksort ($cmp_function) {}
        public static class uksort implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol arraySymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), ARRAY);
                Symbol cmpfunctionSymbol = SymbolUtils.getArgument(ip, 0);
                SymbolUtils.callCallable(ip, cmpfunctionSymbol,
                        Lists.newArrayList(operator.getMergedArrayKeySymbol(arraySymbol),
                                operator.getMergedArrayKeySymbol(arraySymbol)));
                return operator.null_();
            }
        }

        // public function natsort () {}
        public static class natsort implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function natcasesort () {}
        public static class natcasesort implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function unserialize ($serialized) {}
        public static class unserialize implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                ArrayObject clazz = new ArrayObject(ip);
                return SymbolUtils.new_(ip, clazz, Lists.newArrayList(operator.array(SymbolUtils.getArgument(ip, 0))));
            }
        }

        // public function serialize () {}
        public static class serialize implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol arraySymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), ARRAY);
                return serialize(ip, arraySymbol);
            }
        }

        // public function getIterator () {}
        public static class getIterator implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol arraySymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), ARRAY);
                ArrayIterator clazz = new ArrayIterator(ip);
                return SymbolUtils.new_(ip, clazz, Lists.newArrayList(arraySymbol));
            }
        }

        // public function exchangeArray ($input) {}
        public static class exchangeArray implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol arraySymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), ARRAY);
                Symbol inputSymbol = SymbolUtils.getArgument(ip, 0);
                operator.putFieldValue(ip.getContext().getThisSymbol(), ARRAY, inputSymbol);
                return arraySymbol;
            }
        }

        // public function setIteratorClass ($iterator_class) {}
        public static class setIteratorClass implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function getIteratorClass () {}
        public static class getIteratorClass implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.string();
            }
        }
    }

    public static class ArrayIterator extends PhpClassBase {
        public ArrayIterator(Interpreter ip) {
            super(ip);
        }

        // public function __construct ($array) {}
        public static class __construct implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol arraySymbol = SymbolUtils.getArgument(ip, 0);
                operator.putFieldValue(ip.getContext().getThisSymbol(), ARRAY, arraySymbol);
                return operator.null_();
            }
        }

        // public function offsetExists ($index) {}
        public static class offsetExists implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function offsetGet ($index) {}
        public static class offsetGet implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol arraySymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), ARRAY);
                return operator.getArrayValue(arraySymbol, SymbolUtils.getArgument(ip, 0));
            }
        }

        // public function offsetSet ($index, $newval) {}
        public static class offsetSet implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function offsetUnset ($index) {}
        public static class offsetUnset implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function append ($value) {}
        public static class append implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol arraySymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), ARRAY);
                operator.putArrayValue(arraySymbol, operator.getNextIndex(arraySymbol), SymbolUtils.getArgument(ip, 0));
                return operator.null_();
            }
        }

        // public function getArrayCopy () {}
        public static class getArrayCopy implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol arraySymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), ARRAY);
                return operator.copy(arraySymbol);
            }
        }

        // public function count () {}
        public static class count implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function getFlags () {}
        public static class getFlags implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function setFlags ($flags) {}
        public static class setFlags implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function asort () {}
        public static class asort implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function ksort () {}
        public static class ksort implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function uasort ($cmp_function) {}
        public static class uasort implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol arraySymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), ARRAY);
                Symbol cmpfunctionSymbol = SymbolUtils.getArgument(ip, 0);
                SymbolUtils.callCallable(ip, cmpfunctionSymbol,
                        Lists.newArrayList(operator.getMergedArrayValueSymbol(arraySymbol),
                                operator.getMergedArrayValueSymbol(arraySymbol)));
                return operator.null_();
            }
        }

        // public function uksort ($cmp_function) {}
        public static class uksort implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol arraySymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), ARRAY);
                Symbol cmpfunctionSymbol = SymbolUtils.getArgument(ip, 0);
                SymbolUtils.callCallable(ip, cmpfunctionSymbol,
                        Lists.newArrayList(operator.getMergedArrayKeySymbol(arraySymbol),
                                operator.getMergedArrayKeySymbol(arraySymbol)));
                return operator.null_();
            }
        }

        // public function natsort () {}
        public static class natsort implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function natcasesort () {}
        public static class natcasesort implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function unserialize ($serialized) {}
        public static class unserialize implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                ArrayIterator clazz = new ArrayIterator(ip);
                return SymbolUtils.new_(ip, clazz, Lists.newArrayList(operator.array(SymbolUtils.getArgument(ip, 0))));
            }
        }

        // public function serialize () {}
        public static class serialize implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol arraySymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), ARRAY);
                return serialize(ip, arraySymbol);
            }
        }

        // public function rewind () {}
        public static class rewind implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol resultSymbol = operator.createSymbol();
                Symbol iteratorSymbol =
                        operator.getFieldValue(ip.getContext().getThisSymbol(), ARRAY);
                for (PhpObject phpObject : operator.extractPhpObject(iteratorSymbol)) {
                    PhpCallable method = phpObject.getPhpClass().getMethod("rewind");
                    if (method != null) {
                        operator.merge(resultSymbol, SymbolUtils.callMethod(
                                ip, phpObject, method, Lists.newArrayList()));
                    }
                }
                return resultSymbol;
            }
        }

        // public function valid () {}
        public static class valid implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol resultSymbol = operator.createSymbol();
                Symbol iteratorSymbol =
                        operator.getFieldValue(ip.getContext().getThisSymbol(), ARRAY);
                for (PhpObject phpObject : operator.extractPhpObject(iteratorSymbol)) {
                    PhpCallable method = phpObject.getPhpClass().getMethod("valid");
                    if (method != null) {
                        operator.merge(resultSymbol, SymbolUtils.callMethod(
                                ip, phpObject, method, Lists.newArrayList()));
                    }
                }
                return resultSymbol;
            }
        }

        // public function key () {}
        public static class key implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol resultSymbol = operator.createSymbol();
                Symbol iteratorSymbol =
                        operator.getFieldValue(ip.getContext().getThisSymbol(), ARRAY);
                for (PhpObject phpObject : operator.extractPhpObject(iteratorSymbol)) {
                    PhpCallable method = phpObject.getPhpClass().getMethod("key");
                    if (method != null) {
                        operator.merge(resultSymbol, SymbolUtils.callMethod(
                                ip, phpObject, method, Lists.newArrayList()));
                    }
                }
                return resultSymbol;
            }
        }

        // public function current () {}
        public static class current implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol resultSymbol = operator.createSymbol();
                Symbol iteratorSymbol =
                        operator.getFieldValue(ip.getContext().getThisSymbol(), ARRAY);
                for (PhpObject phpObject : operator.extractPhpObject(iteratorSymbol)) {
                    PhpCallable method = phpObject.getPhpClass().getMethod("current");
                    if (method != null) {
                        operator.merge(resultSymbol, SymbolUtils.callMethod(
                                ip, phpObject, method, Lists.newArrayList()));
                    }
                }
                return resultSymbol;
            }
        }

        // public function next () {}
        public static class next implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol resultSymbol = operator.createSymbol();
                Symbol iteratorSymbol =
                        operator.getFieldValue(ip.getContext().getThisSymbol(), ARRAY);
                for (PhpObject phpObject : operator.extractPhpObject(iteratorSymbol)) {
                    PhpCallable method = phpObject.getPhpClass().getMethod("next");
                    if (method != null) {
                        operator.merge(resultSymbol, SymbolUtils.callMethod(
                                ip, phpObject, method, Lists.newArrayList()));
                    }
                }
                return resultSymbol;
            }
        }

        // public function seek ($position) {}
        public static class seek implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }
    }

    public static class RecursiveArrayIterator extends PhpClassBase {
        public RecursiveArrayIterator(Interpreter ip) {
            super(ip);
        }

        @Override
        public String getAbsoluteParentClassName() {
            return SymbolUtils.getAbsoluteNameFromJavaClassName(ArrayIterator.class.getName());
        }

        // public function hasChildren () {}
        public static class hasChildren implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function getChildren () {}
        public static class getChildren implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol arraySymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), ARRAY);
                RecursiveArrayIterator clazz = new RecursiveArrayIterator(ip);
                return SymbolUtils.new_(ip, clazz, Lists.newArrayList(arraySymbol));
            }
        }

        // public function __construct ($array) {}
        public static class __construct implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol arraySymbol = SymbolUtils.getArgument(ip, 0);
                operator.putFieldValue(ip.getContext().getThisSymbol(), ARRAY, arraySymbol);
                return operator.null_();
            }
        }

    }

    private static final String FILENAME = "FILENAME";

    public static class SplFileInfo extends PhpClassBase {
        public SplFileInfo(Interpreter ip) {
            super(ip);
        }

        // public function __construct ($file_name) {}
        public static class __construct implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol filenameSymbol = SymbolUtils.getArgument(ip, 0);
                operator.putFieldValue(ip.getContext().getThisSymbol(), FILENAME, filenameSymbol);
                return operator.null_();
            }
        }

        // public function getPath () {}
        public static class getPath implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.getFieldValue(ip.getContext().getThisSymbol(), FILENAME);
            }
        }

        // public function getFilename () {}
        public static class getFilename implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.getFieldValue(ip.getContext().getThisSymbol(), FILENAME);
            }
        }

        // public function getExtension () {}
        public static class getExtension implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.getFieldValue(ip.getContext().getThisSymbol(), FILENAME);
            }
        }

        // public function getBasename ($suffix = null) {}
        public static class getBasename implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.getFieldValue(ip.getContext().getThisSymbol(), FILENAME);
            }
        }

        // public function getPathname () {}
        public static class getPathname implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.getFieldValue(ip.getContext().getThisSymbol(), FILENAME);
            }
        }

        // public function getPerms () {}
        public static class getPerms implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function getInode () {}
        public static class getInode implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function getSize () {}
        public static class getSize implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function getOwner () {}
        public static class getOwner implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function getGroup () {}
        public static class getGroup implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function getATime () {}
        public static class getATime implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function getMTime () {}
        public static class getMTime implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function getCTime () {}
        public static class getCTime implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function getType () {}
        public static class getType implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.string();
            }
        }

        // public function isWritable () {}
        public static class isWritable implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function isReadable () {}
        public static class isReadable implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function isExecutable () {}
        public static class isExecutable implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function isFile () {}
        public static class isFile implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function isDir () {}
        public static class isDir implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function isLink () {}
        public static class isLink implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function getLinkTarget () {}
        public static class getLinkTarget implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.string();
            }
        }

        // public function getRealPath () {}
        public static class getRealPath implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.getFieldValue(ip.getContext().getThisSymbol(), FILENAME);
            }
        }

        // public function getFileInfo ($class_name = null) {}
        public static class getFileInfo implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                SplFileInfo clazz = new SplFileInfo(ip);
                return SymbolUtils.new_(ip, clazz, Lists.newArrayList(
                        operator.getFieldValue(ip.getContext().getThisSymbol(), FILENAME)));
            }
        }

        // public function getPathInfo ($class_name = null) {}
        public static class getPathInfo implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                SplFileInfo clazz = new SplFileInfo(ip);
                return SymbolUtils.new_(ip, clazz, Lists.newArrayList(
                        operator.getFieldValue(ip.getContext().getThisSymbol(), FILENAME)));
            }
        }

        // public function openFile ($open_mode = null, $use_include_path = null, $context = null) {}
        public static class openFile implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                SplFileObject clazz = new SplFileObject(ip);
                return SymbolUtils.new_(ip, clazz, Lists.newArrayList(
                        operator.getFieldValue(ip.getContext().getThisSymbol(), FILENAME)));
            }
        }

        // public function setFileClass ($class_name = null) {}
        public static class setFileClass implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function setInfoClass ($class_name = null) {}
        public static class setInfoClass implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // final public function _bad_state_ex () {}
        public static class _bad_state_ex implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function __toString () {}
    }

    public static class DirectoryIterator extends PhpClassBase {
        public DirectoryIterator(Interpreter ip) {
            super(ip);
        }

        @Override
        public String getAbsoluteParentClassName() {
            return SymbolUtils.getAbsoluteNameFromJavaClassName(SplFileInfo.class.getName());
        }

        // public function __construct ($path) {}
        public static class __construct implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol filenameSymbol = SymbolUtils.getArgument(ip, 0);
                operator.putFieldValue(ip.getContext().getThisSymbol(), FILENAME, filenameSymbol);
                return operator.null_();
            }
        }

        // public function isDot () {}
        public static class isDot implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function rewind () {}
        public static class rewind implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function valid () {}
        public static class valid implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function key () {}
        public static class key implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function current () {}
        public static class current implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                DirectoryIterator clazz = new DirectoryIterator(ip);
                return SymbolUtils.new_(ip, clazz, Lists.newArrayList(
                        operator.getFieldValue(ip.getContext().getThisSymbol(), FILENAME)));
            }
        }

        // public function next () {}
        public static class next implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function seek ($position) {}
        public static class seek implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

    }

    public static class FilesystemIterator extends PhpClassBase {
        public FilesystemIterator(Interpreter ip) {
            super(ip);
        }

        @Override
        public String getAbsoluteParentClassName() {
            return SymbolUtils.getAbsoluteNameFromJavaClassName(DirectoryIterator.class.getName());
        }

        // public function __construct ($path, $flags = null) {}
        public static class __construct implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol filenameSymbol = SymbolUtils.getArgument(ip, 0);
                operator.putFieldValue(ip.getContext().getThisSymbol(), FILENAME, filenameSymbol);
                return operator.null_();
            }
        }

    }

    public static class RecursiveDirectoryIterator extends PhpClassBase {
        public RecursiveDirectoryIterator(Interpreter ip) {
            super(ip);
        }

        @Override
        public String getAbsoluteParentClassName() {
            return SymbolUtils.getAbsoluteNameFromJavaClassName(FilesystemIterator.class.getName());
        }

        // public function __construct ($path, $flags = null) {}
        public static class __construct implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol filenameSymbol = SymbolUtils.getArgument(ip, 0);
                operator.putFieldValue(ip.getContext().getThisSymbol(), FILENAME, filenameSymbol);
                return operator.null_();
            }
        }

        // public function hasChildren ($allow_links = null) {}
        public static class hasChildren implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function getChildren () {}
        public static class getChildren implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                return ip.getContext().getThisSymbol();
            }
        }

        // public function getSubPath () {}
        public static class getSubPath implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.getFieldValue(ip.getContext().getThisSymbol(), FILENAME);
            }
        }

        // public function getSubPathname () {}
        public static class getSubPathname implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.getFieldValue(ip.getContext().getThisSymbol(), FILENAME);
            }
        }

    }

    public static class GlobIterator extends PhpClassBase {
        public GlobIterator(Interpreter ip) {
            super(ip);
        }

        @Override
        public String getAbsoluteParentClassName() {
            return SymbolUtils.getAbsoluteNameFromJavaClassName(FilesystemIterator.class.getName());
        }

        // public function __construct ($path, $flags = null) {}
        public static class __construct implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol filenameSymbol = SymbolUtils.getArgument(ip, 0);
                operator.putFieldValue(ip.getContext().getThisSymbol(), FILENAME, filenameSymbol);
                return operator.null_();
            }
        }

        // public function count () {}
        public static class count implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

    }

    private static final String FILE_CONTENT = "FILE_CONTENT";

    public static class SplFileObject extends PhpClassBase {
        public SplFileObject(Interpreter ip) {
            super(ip);
        }

        @Override
        public String getAbsoluteParentClassName() {
            return SymbolUtils.getAbsoluteNameFromJavaClassName(SplFileInfo.class.getName());
        }

        // public function __construct ($file_name, $open_mode = null, $use_include_path = null, $context = null) {}
        public static class __construct implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol filenameSymbol = SymbolUtils.getArgument(ip, 0);
                operator.putFieldValue(ip.getContext().getThisSymbol(), FILENAME, filenameSymbol);
                operator.putFieldValue(ip.getContext().getThisSymbol(), FILE_CONTENT, operator.string());
                return operator.null_();
            }
        }

        // public function rewind () {}
        public static class rewind implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function eof () {}
        public static class eof implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function valid () {}
        public static class valid implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function fgets () {}
        public static class fgets implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.getFieldValue(ip.getContext().getThisSymbol(), FILE_CONTENT);
            }
        }

        // public function fgetcsv ($delimiter = null, $enclosure = null, $escape = null) {}
        public static class fgetcsv implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.array(operator.getFieldValue(ip.getContext().getThisSymbol(), FILE_CONTENT));
            }
        }

        // public function fputcsv (array $fields, $delimiter = null, $enclosure = null) {}
        public static class fputcsv implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol fieldsSymbol = SymbolUtils.getArgument(ip, 0);
                Symbol contentSymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), FILE_CONTENT);
                operator.merge(contentSymbol, operator.getMergedArrayValueSymbol(fieldsSymbol));
                operator.putFieldValue(ip.getContext().getThisSymbol(), FILE_CONTENT, contentSymbol);
                return operator.integer();
            }
        }

        // public function setCsvControl ($delimiter = null, $enclosure = null, $escape = null) {}
        public static class setCsvControl implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function getCsvControl () {}
        public static class getCsvControl implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.array();
            }
        }

        // public function flock ($operation, &$wouldblock = null) {}
        public static class flock implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function fflush () {}
        public static class fflush implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function ftell () {}
        public static class ftell implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function fseek ($offset, $whence = null) {}
        public static class fseek implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function fgetc () {}
        public static class fgetc implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.getFieldValue(ip.getContext().getThisSymbol(), FILE_CONTENT);
            }
        }

        // public function fpassthru () {}
        public static class fpassthru implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function fgetss ($allowable_tags = null) {}
        public static class fgetss implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.getFieldValue(ip.getContext().getThisSymbol(), FILE_CONTENT);
            }
        }

        // public function fscanf ($format, &$_ = null) {}
        public static class fscanf implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.array(operator.integer());
            }
        }

        // public function fwrite ($str, $length = null) {}
        public static class fwrite implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol fieldsSymbol = SymbolUtils.getArgument(ip, 0);
                Symbol contentSymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), FILE_CONTENT);
                operator.merge(contentSymbol, operator.getMergedArrayValueSymbol(fieldsSymbol));
                operator.putFieldValue(ip.getContext().getThisSymbol(), FILE_CONTENT, contentSymbol);
                return operator.integer();
            }
        }

        // public function fread ($length) {}
        public static class fread implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.getFieldValue(ip.getContext().getThisSymbol(), FILE_CONTENT);
            }
        }

        // public function fstat () {}
        public static class fstat implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.array();
            }
        }

        // public function ftruncate ($size) {}
        public static class ftruncate implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function current () {}
        public static class current implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.getFieldValue(ip.getContext().getThisSymbol(), FILE_CONTENT);
            }
        }

        // public function key () {}
        public static class key implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function next () {}
        public static class next implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function setFlags ($flags) {}
        public static class setFlags implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function getFlags () {}
        public static class getFlags implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function setMaxLineLen ($max_len) {}
        public static class setMaxLineLen implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function getMaxLineLen () {}
        public static class getMaxLineLen implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function hasChildren () {}
        public static class hasChildren implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function getChildren () {}
        public static class getChildren implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function seek ($line_pos) {}
        public static class seek implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function getCurrentLine () {}
        // 	 * Alias of <methodname>SplFileObject::fgets</methodname>
        public static class getCurrentLine extends fgets {
        }

    }

    public static class SplTempFileObject extends PhpClassBase {
        public SplTempFileObject(Interpreter ip) {
            super(ip);
        }

        @Override
        public String getAbsoluteParentClassName() {
            return SymbolUtils.getAbsoluteNameFromJavaClassName(SplFileObject.class.getName());
        }

    }

    private static final String LIST = "LIST";

    public static class SplDoublyLinkedList extends PhpClassBase {
        public SplDoublyLinkedList(Interpreter ip) {
            super(ip);
        }

        public static class __construct implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                operator.putFieldValue(ip.getContext().getThisSymbol(), LIST,
                        operator.createSymbol(operator.createPhpArray()));
                return operator.null_();
            }
        }

        // public function pop () {}
        public static class pop implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol listSymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), LIST);
                return operator.getMergedArrayValueSymbol(listSymbol);
            }
        }

        // public function shift () {}
        public static class shift implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol listSymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), LIST);
                return operator.getMergedArrayValueSymbol(listSymbol);
            }
        }

        // public function push ($value) {}
        public static class push implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol listSymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), LIST);
                operator.putArrayValue(listSymbol, operator.getNextIndex(listSymbol), SymbolUtils.getArgument(ip, 0));
                return operator.null_();
            }
        }

        // public function unshift ($value) {}
        public static class unshift implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol listSymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), LIST);
                operator.putArrayValue(listSymbol, operator.getNextIndex(listSymbol), SymbolUtils.getArgument(ip, 0));
                return operator.null_();
            }
        }

        // public function top () {}
        public static class top implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol listSymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), LIST);
                return operator.getMergedArrayValueSymbol(listSymbol);
            }
        }

        // public function bottom () {}
        public static class bottom implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol listSymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), LIST);
                return operator.getMergedArrayValueSymbol(listSymbol);
            }
        }

        // public function isEmpty () {}
        public static class isEmpty implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function setIteratorMode ($mode) {}
        public static class setIteratorMode implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function getIteratorMode () {}
        public static class getIteratorMode implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function count () {}
        public static class count implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function offsetExists ($index) {}
        public static class offsetExists implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function offsetGet ($index) {}
        public static class offsetGet implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol listSymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), LIST);
                return operator.getMergedArrayValueSymbol(listSymbol);
            }
        }

        // public function offsetSet ($index, $newval) {}
        public static class offsetSet implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function offsetUnset ($index) {}
        public static class offsetUnset implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function add ($index, $newval) {}
        public static class add implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol listSymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), LIST);
                operator.putArrayValue(listSymbol, operator.getNextIndex(listSymbol), SymbolUtils.getArgument(ip, 0));
                return operator.null_();
            }
        }

        // public function rewind () {}
        public static class rewind implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function current () {}
        public static class current implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol listSymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), LIST);
                return operator.getMergedArrayValueSymbol(listSymbol);
            }
        }

        // public function key () {}
        public static class key implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function next () {}
        public static class next implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function prev () {}
        public static class prev implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function valid () {}
        public static class valid implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function unserialize ($serialized) {}
        public static class unserialize implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol listSymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), LIST);
                operator.putArrayValue(listSymbol, operator.getNextIndex(listSymbol), SymbolUtils.getArgument(ip, 0));
                return operator.null_();
            }
        }

        // public function serialize () {}
        public static class serialize implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                return serialize(ip, ip.getContext().getThisSymbol());
            }
        }

    }

    public static class SplQueue extends PhpClassBase {
        public SplQueue(Interpreter ip) {
            super(ip);
        }

        @Override
        public String getAbsoluteParentClassName() {
            return SymbolUtils.getAbsoluteNameFromJavaClassName(SplDoublyLinkedList.class.getName());
        }

        public static class __construct implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                operator.putFieldValue(ip.getContext().getThisSymbol(), LIST,
                        operator.createSymbol(operator.createPhpArray()));
                return operator.null_();
            }
        }

        // public function enqueue ($value) {}
        public static class enqueue implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol listSymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), LIST);
                operator.putArrayValue(listSymbol, operator.getNextIndex(listSymbol), SymbolUtils.getArgument(ip, 0));
                return operator.null_();
            }
        }

        // public function dequeue () {}
        public static class dequeue implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol listSymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), LIST);
                return operator.getMergedArrayValueSymbol(listSymbol);
            }
        }

    }

    public static class SplStack extends PhpClassBase {
        public SplStack(Interpreter ip) {
            super(ip);
        }

        @Override
        public String getAbsoluteParentClassName() {
            return SymbolUtils.getAbsoluteNameFromJavaClassName(SplDoublyLinkedList.class.getName());
        }

        public static class __construct implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                operator.putFieldValue(ip.getContext().getThisSymbol(), LIST,
                        operator.createSymbol(operator.createPhpArray()));
                return operator.null_();
            }
        }

    }

    public static class SplHeap extends PhpClassBase {

        public SplHeap(Interpreter ip) {
            super(ip);
        }

        public static class __construct implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                operator.putFieldValue(ip.getContext().getThisSymbol(), LIST,
                        operator.createSymbol(operator.createPhpArray()));
                return operator.null_();
            }
        }

        // public function extract () {}
        public static class extract implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol listSymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), LIST);
                return operator.getMergedArrayValueSymbol(listSymbol);
            }
        }

        // public function insert ($value) {}
        public static class insert implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol listSymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), LIST);
                operator.putArrayValue(listSymbol, operator.getNextIndex(listSymbol), SymbolUtils.getArgument(ip, 0));
                return operator.null_();
            }
        }

        // public function top () {}
        public static class top implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol listSymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), LIST);
                return operator.getMergedArrayValueSymbol(listSymbol);
            }
        }

        // public function count () {}
        public static class count implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function isEmpty () {}
        public static class isEmpty implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function rewind () {}
        public static class rewind implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function current () {}
        public static class current implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol listSymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), LIST);
                return operator.getMergedArrayValueSymbol(listSymbol);
            }
        }

        // public function key () {}
        public static class key implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function next () {}
        public static class next implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function valid () {}
        public static class valid implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function recoverFromCorruption () {}
        public static class recoverFromCorruption implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // abstract protected function compare ($value1, $value2) {}
    }

    public static class SplMinHeap extends PhpClassBase {
        public SplMinHeap(Interpreter ip) {
            super(ip);
        }

        @Override
        public String getAbsoluteParentClassName() {
            return SymbolUtils.getAbsoluteNameFromJavaClassName(SplHeap.class.getName());
        }

        public static class __construct implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                operator.putFieldValue(ip.getContext().getThisSymbol(), LIST,
                        operator.createSymbol(operator.createPhpArray()));
                return operator.null_();
            }
        }

        // protected function compare ($value1, $value2) {}
        public static class compare implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function recoverFromCorruption () {}
        public static class recoverFromCorruption implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

    }

    public static class SplMaxHeap extends PhpClassBase {
        public SplMaxHeap(Interpreter ip) {
            super(ip);
        }

        @Override
        public String getAbsoluteParentClassName() {
            return SymbolUtils.getAbsoluteNameFromJavaClassName(SplHeap.class.getName());
        }

        public static class __construct implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                operator.putFieldValue(ip.getContext().getThisSymbol(), LIST,
                        operator.createSymbol(operator.createPhpArray()));
                return operator.null_();
            }
        }

        // protected function compare ($value1, $value2) {}
        public static class compare implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function recoverFromCorruption () {}
        public static class recoverFromCorruption implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

    }

    public static class SplPriorityQueue extends PhpClassBase {

        public SplPriorityQueue(Interpreter ip) {
            super(ip);
        }

        public static class __construct implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                operator.putFieldValue(ip.getContext().getThisSymbol(), LIST,
                        operator.createSymbol(operator.createPhpArray()));
                return operator.null_();
            }
        }

        // protected function compare ($value1, $value2) {}
        public static class compare implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function insert ($value, $priority) {}
        public static class insert implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol listSymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), LIST);
                operator.putArrayValue(listSymbol, operator.getNextIndex(listSymbol), SymbolUtils.getArgument(ip, 0));
                return operator.null_();
            }
        }

        // public function setExtractFlags ($flags) {}
        public static class setExtractFlags implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function top () {}
        public static class top implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol listSymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), LIST);
                return operator.getMergedArrayValueSymbol(listSymbol);
            }
        }

        // public function extract () {}
        public static class extract implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol listSymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), LIST);
                return operator.getMergedArrayValueSymbol(listSymbol);
            }
        }

        // public function count () {}
        public static class count implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function isEmpty () {}
        public static class isEmpty implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function rewind () {}
        public static class rewind implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function current () {}
        public static class current implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol listSymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), LIST);
                return operator.getMergedArrayValueSymbol(listSymbol);
            }
        }

        // public function key () {}
        public static class key implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function next () {}
        public static class next implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function valid () {}
        public static class valid implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function recoverFromCorruption () {}
        public static class recoverFromCorruption implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

    }

    public static class SplFixedArray extends PhpClassBase {
        public SplFixedArray(Interpreter ip) {
            super(ip);
        }

        // public function __construct ($size = null) {}
        public static class __construct implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                operator.putFieldValue(ip.getContext().getThisSymbol(), LIST,
                        operator.createSymbol(operator.createPhpArray()));
                return operator.null_();
            }
        }

        // public function __wakeup () {}

        // public function count () {}
        public static class count implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function toArray () {}
        public static class toArray implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.getFieldValue(ip.getContext().getThisSymbol(), LIST);
            }
        }

        // public static function fromArray (array $array, $save_indexes = null) {}
        public static class fromArray implements PhpCallableStatic {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                SplFixedArray clazz = new SplFixedArray(ip);
                Symbol objSymbol = SymbolUtils.new_(ip, clazz, Lists.newArrayList());
                operator.putFieldValue(objSymbol, LIST, SymbolUtils.getArgument(ip, 0));
                return objSymbol;
            }
        }

        // public function getSize () {}
        public static class getSize implements PhpCallableStatic {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function setSize ($size) {}
        public static class setSize implements PhpCallableStatic {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function offsetExists ($index) {}
        public static class offsetExists implements PhpCallableStatic {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function offsetGet ($index) {}
        public static class offsetGet implements PhpCallableStatic {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.getFieldValue(ip.getContext().getThisSymbol(), LIST);
            }
        }

        // public function offsetSet ($index, $newval) {}
        public static class offsetSet implements PhpCallableStatic {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function offsetUnset ($index) {}
        public static class offsetUnset implements PhpCallableStatic {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function rewind () {}
        public static class rewind implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function current () {}
        public static class current implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol listSymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), LIST);
                return operator.getMergedArrayValueSymbol(listSymbol);
            }
        }

        // public function key () {}
        public static class key implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function next () {}
        public static class next implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function valid () {}
        public static class valid implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }
    }

    public static class SplObserver extends PhpClassBase {

        public SplObserver(Interpreter ip) {
            super(ip);
        }
    }

    public static class SplSubject extends PhpClassBase {

        public SplSubject(Interpreter ip) {
            super(ip);
        }
    }

    private static final String STORAGE = "STORAGE";

    public static class SplObjectStorage extends PhpClassBase {

        public SplObjectStorage(Interpreter ip) {
            super(ip);
        }

        public static class __construct implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                operator.putFieldValue(ip.getContext().getThisSymbol(), STORAGE,
                        operator.createSymbol(operator.createPhpArray()));
                return operator.null_();
            }
        }

        // public function attach ($object, $data = null) {}
        public static class attach implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol storageSymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), STORAGE);
                operator.putArrayValue(storageSymbol, SymbolUtils.getArgument(ip, 0), SymbolUtils.getArgument(ip, 1));
                return operator.null_();
            }
        }

        // public function detach ($object) {}
        public static class detach implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function contains ($object) {}
        public static class contains implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function addAll (SplObjectStorage $storage) {}
        public static class addAll implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol storageSymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), STORAGE);
                Symbol storageSymbol2 = operator.getFieldValue(SymbolUtils.getArgument(ip, 0), STORAGE);
                operator.merge(storageSymbol, storageSymbol2);
                operator.putFieldValue(ip.getContext().getThisSymbol(), STORAGE, storageSymbol);
                return operator.null_();
            }
        }

        // public function removeAll (SplObjectStorage $storage) {}
        public static class removeAll implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function removeAllExcept (SplObjectStorage $storage) {}
        public static class removeAllExcept implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function getInfo () {}
        public static class getInfo implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.getMergedArrayValueSymbol(
                        operator.getFieldValue(ip.getContext().getThisSymbol(), STORAGE));
            }
        }

        // public function setInfo ($data) {}
        public static class setInfo implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol storageSymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), STORAGE);
                operator.putArrayValue(storageSymbol,
                        operator.getNextIndex(storageSymbol), SymbolUtils.getArgument(ip, 0));
                return operator.null_();
            }
        }

        // public function getHash ($object) {}
        public static class getHash implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.string();
            }
        }

        // public function count () {}
        public static class count implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function rewind () {}
        public static class rewind implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function current () {}
        public static class current implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol listSymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), STORAGE);
                return operator.getMergedArrayValueSymbol(listSymbol);
            }
        }

        // public function key () {}
        public static class key implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function next () {}
        public static class next implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function valid () {}
        public static class valid implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function unserialize ($serialized) {}
        public static class unserialize implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol listSymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), STORAGE);
                operator.putArrayValue(listSymbol, operator.getNextIndex(listSymbol), SymbolUtils.getArgument(ip, 0));
                return operator.null_();
            }
        }

        // public function serialize () {}
        public static class serialize implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                return serialize(ip, ip.getContext().getThisSymbol());
            }
        }

        // public function offsetExists ($object) {}
        public static class offsetExists implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function offsetSet ($object, $data = null) {}
        public static class offsetSet implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol storageSymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), STORAGE);
                operator.putArrayValue(storageSymbol, SymbolUtils.getArgument(ip, 0), SymbolUtils.getArgument(ip, 1));
                return operator.null_();
            }
        }

        // public function offsetUnset ($object) {}
        public static class offsetUnset implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function offsetGet ($object) {}
        public static class offsetGet implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.getMergedArrayValueSymbol(
                        operator.getFieldValue(ip.getContext().getThisSymbol(), STORAGE));
            }
        }

    }

    public static class MultipleIterator extends PhpClassBase {
        public MultipleIterator(Interpreter ip) {
            super(ip);
        }

        // public function __construct ($flags) {}
        public static class __construct implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                operator.putFieldValue(ip.getContext().getThisSymbol(), LIST,
                        operator.createSymbol(operator.createPhpArray()));
                return operator.null_();
            }
        }

        // public function getFlags () {}
        public static class getFlags implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function setFlags ($flags) {}
        public static class setFlags implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function attachIterator ($iterator, $infos = null) {}
        public static class attachIterator implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol listSymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), LIST);
                operator.putArrayValue(listSymbol, operator.getNextIndex(listSymbol), SymbolUtils.getArgument(ip, 0));
                return operator.null_();
            }
        }

        // public function detachIterator ($iterator) {}
        public static class detachIterator implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function containsIterator ($iterator) {}
        public static class containsIterator implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function countIterators () {}
        public static class countIterators implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function rewind () {}
        public static class rewind implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function current () {}
        public static class current implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol listSymbol = operator.getFieldValue(ip.getContext().getThisSymbol(), LIST);
                return operator.getMergedArrayValueSymbol(listSymbol);
            }
        }

        // public function key () {}
        public static class key implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function next () {}
        public static class next implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function valid () {}
        public static class valid implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

    }

    // function spl_classes () {}
    public static class spl_classes implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array();
        }
    }

    // function spl_autoload ($class_name, $file_extensions = null) {}
    // デフォルトでは、クラス名を小文字にして .inc および .php を拡張子につけたファイル名のファイルが存在するかどうかを
    // すべてのインクルードパスから探します。
    public static class spl_autoload implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol classnameSymbol = SymbolUtils.getArgument(ip, 0);
            Set<String> extensionSet = ip.getStorage().getSplDefaultExtensionSet();
            if (SymbolUtils.getArgumentSize(ip) > 1) {
                Symbol fileextensionsSymbol = SymbolUtils.getArgument(ip, 1);
                for (PhpArray phpArray : operator.extractPhpArray(fileextensionsSymbol)) {
                    // 念のため既存の値は残す
                    extensionSet.addAll(operator.getJavaStringList(operator.getMergedArrayValueSymbol(phpArray)));
                }
            }
            for (String classnameString : operator.getJavaStringList(classnameSymbol)) {
                for (String extension : extensionSet) {
                    ip.include(classnameString.toLowerCase() + extension, false);
                }
            }
            return operator.null_();
        }
    }

    // function spl_autoload_extensions ($file_extensions = null) {}
    public static class spl_autoload_extensions implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol fileextensionsSymbol = SymbolUtils.getArgument(ip, 0);
            Set<String> extensionSet = ip.getStorage().getSplDefaultExtensionSet();
            for (PhpArray phpArray : operator.extractPhpArray(fileextensionsSymbol)) {
                // 念のため既存の値は残す
                extensionSet.addAll(
                        operator.getJavaStringList(operator.getMergedArrayValueSymbol(phpArray)));
            }
            return operator.createSymbol(String.join(",", extensionSet));
        }
    }

    // function spl_autoload_register ($autoload_function = null, $throw = null, $prepend = null) {}
    public static class spl_autoload_register implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol defaultAutoloadSymbol = operator.createSymbol("\\spl_autoload");
            ip.removeAutoload(defaultAutoloadSymbol);
            Symbol autoloadfunctionSymbol = SymbolUtils.getArgument(ip, 0);
            ip.addAutoload(autoloadfunctionSymbol);
            ip.addAutoload(defaultAutoloadSymbol);
            return operator.bool();
        }
    }

    // function spl_autoload_unregister ($autoload_function) {}
    public static class spl_autoload_unregister implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function spl_autoload_functions () {}
    public static class spl_autoload_functions implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array();
        }
    }

    // function spl_autoload_call ($class_name) {}
    public static class spl_autoload_call implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol classnameSymbol = SymbolUtils.getArgument(ip, 0);
            for (String string : operator.getJavaStringList(classnameSymbol)) {
                ip.autoload(string);
            }
            return operator.null_();
        }
    }

    // function class_parents ($class, $autoload = null) {}
    public static class class_parents implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            PhpArray phpArray = operator.createPhpArray();
            Symbol classSymbol = SymbolUtils.getArgument(ip, 0);
            for (String string : operator.getJavaStringList(classSymbol)) {
                for (PhpNewable newable : SymbolUtils.getClassList(ip, "\\" + string)) {
                    if (newable != null && newable.getAbsoluteParentClassName() != null) {
                        operator.putArrayValue(phpArray, operator.getNextIndex(phpArray),
                                operator.createSymbol(newable.getAbsoluteParentClassName().substring(1)));
                    }
                }
            }
            return operator.createSymbol(phpArray);
        }
    }

    // function class_implements ($class, $autoload = null) {}
    public static class class_implements implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array();
        }
    }

    // function class_uses ($class, $autoload = null) {}
    public static class class_uses implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array();
        }
    }

    // function spl_object_hash ($obj) {}
    public static class spl_object_hash implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function iterator_to_array ($iterator, $use_keys = null) {}
    // array iterator_to_array ( Traversable $iterator [, bool $use_keys = true ] )
    public static class iterator_to_array implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol iteratorSymbol = SymbolUtils.getArgument(ip, 0);
            Symbol currentSymbol = operator.createSymbol();
            for (PhpObject phpObject : operator.extractPhpObject(iteratorSymbol)) {
                PhpCallable method = phpObject.getPhpClass().getMethod("current");
                if (method != null) {
                    operator.merge(currentSymbol, SymbolUtils.callMethod(
                            ip, phpObject, method, Lists.newArrayList()));
                }
            }
            Symbol keySymbol = operator.createSymbol();
            for (PhpObject phpObject : operator.extractPhpObject(iteratorSymbol)) {
                PhpCallable method = phpObject.getPhpClass().getMethod("key");
                if (method != null) {
                    operator.merge(keySymbol, SymbolUtils.callMethod(
                            ip, phpObject, method, Lists.newArrayList()));
                }
            }
            PhpArray phpArray = operator.createPhpArray();
            operator.putArrayValue(phpArray, keySymbol, currentSymbol);
            return operator.createSymbol(phpArray);
        }
    }

    // function iterator_count ($iterator) {}
    public static class iterator_count implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function iterator_apply ($iterator, $function, array $args = null) {}
    // ユーザー関数をイテレータのすべての要素でコールする
    public static class iterator_apply implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol iteratorSymbol = SymbolUtils.getArgument(ip, 0);
            Symbol currentSymbol = operator.createSymbol();
            for (PhpObject phpObject : operator.extractPhpObject(iteratorSymbol)) {
                PhpCallable method = phpObject.getPhpClass().getMethod("current");
                if (method != null) {
                    operator.merge(currentSymbol, SymbolUtils.callMethod(
                            ip, phpObject, method, Lists.newArrayList()));
                }
            }
            Symbol functionSymbol = SymbolUtils.getArgument(ip, 1);
            SymbolUtils.callCallable(ip, functionSymbol,
                    Lists.newArrayList(currentSymbol));
            return operator.integer();
        }
    }

}
