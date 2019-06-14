package net.katagaitai.phpscan.php.builtin;

import com.google.common.collect.Lists;
import net.katagaitai.phpscan.compiler.BuiltinBase;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.compiler.PhpClassBase;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.php.types.PhpResource;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.util.Constants;
import net.katagaitai.phpscan.util.SymbolUtils;

import java.util.List;

public class Sqlite3 extends BuiltinBase {
    public Sqlite3(Interpreter ip) {
        super(ip);
        SymbolOperator operator = ip.getOperator();
        putConstant("\\SQLITE3_ASSOC", operator.createSymbol(1));
        putConstant("\\SQLITE3_NUM", operator.createSymbol(2));
        putConstant("\\SQLITE3_BOTH", operator.createSymbol(3));
        putConstant("\\SQLITE3_INTEGER", operator.createSymbol(1));
        putConstant("\\SQLITE3_FLOAT", operator.createSymbol(2));
        putConstant("\\SQLITE3_TEXT", operator.createSymbol(3));
        putConstant("\\SQLITE3_BLOB", operator.createSymbol(4));
        putConstant("\\SQLITE3_NULL", operator.createSymbol(5));
        putConstant("\\SQLITE3_OPEN_READONLY", operator.createSymbol(1));
        putConstant("\\SQLITE3_OPEN_READWRITE", operator.createSymbol(2));
        putConstant("\\SQLITE3_OPEN_CREATE", operator.createSymbol(4));
    }

    public static class SQLite3 extends PhpClassBase {
        public SQLite3(Interpreter ip) {
            super(ip);
        }

        // public function open ($filename, $flags = SQLITE3_OPEN_READWRITE | SQLITE3_OPEN_CREATE, $encryption_key = null) {}
        public static class open implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function close () {}
        public static class close implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function exec ($query) {}
        public static class exec implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public static function version () {}
        public static class version implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.array();
            }
        }

        // public function lastInsertRowID () {}
        public static class lastInsertRowID implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function lastErrorCode () {}
        public static class lastErrorCode implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function lastErrorMsg () {}
        public static class lastErrorMsg implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.string();
            }
        }

        // public function busyTimeout ($msecs) {}
        public static class busyTimeout implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function loadExtension ($shared_library) {}
        public static class loadExtension implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function changes () {}
        public static class changes implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public static function escapeString ($value) {}
        public static class escapeString implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                return SymbolUtils.getArgument(ip, 0);
            }
        }

        // public function prepare ($query) {}
        public static class prepare implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SQLite3Stmt clazz = new SQLite3Stmt(ip);
                return SymbolUtils.new_(ip, clazz, Lists.newArrayList());
            }
        }

        // public function query ($query) {}
        public static class query implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SQLite3Result clazz = new SQLite3Result(ip);
                return SymbolUtils.new_(ip, clazz, Lists.newArrayList());
            }
        }

        // public function querySingle ($query, $entire_row = false) {}
        public static class querySingle implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.array();
            }
        }

        // public function createFunction ($name, $callback, $argument_count = -1) {}
        public static class createFunction implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol callbackSymbol = SymbolUtils.getArgument(ip, 1);
                List<Symbol> dummyArgList = Lists.newArrayList();
                for (int j = 0; j < Constants.DUMMY_ARRAY_SIZE; j++) {
                    dummyArgList.add(operator.string());
                }
                SymbolUtils.callCallable(ip, callbackSymbol, dummyArgList);
                return operator.bool();
            }
        }

        // public function createAggregate ($name, $step_callback, $final_callback, $argument_count = -1) {}
        public static class createAggregate implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                List<Symbol> dummyArgList = Lists.newArrayList(operator.array());
                for (int j = 0; j < Constants.DUMMY_ARRAY_SIZE - 1; j++) {
                    dummyArgList.add(operator.string());
                }
                Symbol callbackSymbol1 = SymbolUtils.getArgument(ip, 1);
                SymbolUtils.callCallable(ip, callbackSymbol1, dummyArgList);
                Symbol callbackSymbol2 = SymbolUtils.getArgument(ip, 2);
                SymbolUtils.callCallable(ip, callbackSymbol2, dummyArgList);
                return operator.bool();
            }
        }

        // public function createCollation ($name, callable $callback) {}
        public static class createCollation implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                Symbol callbackSymbol = SymbolUtils.getArgument(ip, 1);
                List<Symbol> dummyArgList = Lists.newArrayList();
                for (int j = 0; j < Constants.DUMMY_ARRAY_SIZE; j++) {
                    dummyArgList.add(operator.string());
                }
                SymbolUtils.callCallable(ip, callbackSymbol, dummyArgList);
                return operator.bool();
            }
        }

        // public function openBlob ($table, $column, $rowid, $dbname) {}
        public static class openBlob implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                //				$fp = $db->openBlob('files', 'content', $id);
                //				while (!feof($fp))
                //				{
                //				    echo fgets($fp);
                //				}
                //				fclose($fp);
                PhpResource phpResource = operator.createPhpResource();
                operator.putResourceValue(phpResource, Standard_5.OPEN_CONTENT, operator.string());
                return operator.createSymbol(phpResource);
            }
        }

        // public function enableExceptions ($enableExceptions) {}
        public static class enableExceptions implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function __construct ($filename, $flags = null, $encryption_key = null) {}
    }

    public static class SQLite3Stmt extends PhpClassBase {
        public SQLite3Stmt(Interpreter ip) {
            super(ip);
        }

        // public function paramCount () {}
        public static class paramCount implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function close () {}
        public static class close implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function reset () {}
        public static class reset implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function clear () {}
        public static class clear implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function execute () {}
        public static class execute implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SQLite3Result clazz = new SQLite3Result(ip);
                return SymbolUtils.new_(ip, clazz, Lists.newArrayList());
            }
        }

        // public function bindParam ($sql_param, &$param, $type = null) {}
        public static class bindParam implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function bindValue ($sql_param, $value, $type = null) {}
        public static class bindValue implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function readOnly () {}
        // http://php.net/manual/ja/book.sqlite3.php
        // SQLite3Stmt::readOnly(void) appeared in PHP 5.3.5 and returns true if a statement doesn't write in the database.
        public static class readOnly implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // private function __construct ($sqlite3) {}
    }

    public static class SQLite3Result extends PhpClassBase {
        public SQLite3Result(Interpreter ip) {
            super(ip);
        }

        // public function numColumns () {}
        public static class numColumns implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function columnName ($column_number) {}
        public static class columnName implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.string();
            }
        }

        // public function columnType ($column_number) {}
        public static class columnType implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        // public function fetchArray ($mode = SQLITE3_BOTH) {}
        public static class fetchArray implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.array();
            }
        }

        // public function reset () {}
        public static class reset implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // public function finalize () {}
        public static class finalize implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        // private function __construct () {}
    }
}
