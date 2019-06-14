package net.katagaitai.phpscan.php.builtin;

import com.google.common.collect.Lists;
import net.katagaitai.phpscan.compiler.BuiltinBase;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.php.types.PhpResource;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.util.SymbolUtils;

public class Xml extends BuiltinBase {
    public Xml(Interpreter ip) {
        super(ip);
        SymbolOperator operator = ip.getOperator();
        putConstant("\\XML_ERROR_NONE", operator.createSymbol(0));
        putConstant("\\XML_ERROR_NO_MEMORY", operator.createSymbol(1));
        putConstant("\\XML_ERROR_SYNTAX", operator.createSymbol(2));
        putConstant("\\XML_ERROR_NO_ELEMENTS", operator.createSymbol(3));
        putConstant("\\XML_ERROR_INVALID_TOKEN", operator.createSymbol(4));
        putConstant("\\XML_ERROR_UNCLOSED_TOKEN", operator.createSymbol(5));
        putConstant("\\XML_ERROR_PARTIAL_CHAR", operator.createSymbol(6));
        putConstant("\\XML_ERROR_TAG_MISMATCH", operator.createSymbol(7));
        putConstant("\\XML_ERROR_DUPLICATE_ATTRIBUTE", operator.createSymbol(8));
        putConstant("\\XML_ERROR_JUNK_AFTER_DOC_ELEMENT", operator.createSymbol(9));
        putConstant("\\XML_ERROR_PARAM_ENTITY_REF", operator.createSymbol(10));
        putConstant("\\XML_ERROR_UNDEFINED_ENTITY", operator.createSymbol(11));
        putConstant("\\XML_ERROR_RECURSIVE_ENTITY_REF", operator.createSymbol(12));
        putConstant("\\XML_ERROR_ASYNC_ENTITY", operator.createSymbol(13));
        putConstant("\\XML_ERROR_BAD_CHAR_REF", operator.createSymbol(14));
        putConstant("\\XML_ERROR_BINARY_ENTITY_REF", operator.createSymbol(15));
        putConstant("\\XML_ERROR_ATTRIBUTE_EXTERNAL_ENTITY_REF", operator.createSymbol(16));
        putConstant("\\XML_ERROR_MISPLACED_XML_PI", operator.createSymbol(17));
        putConstant("\\XML_ERROR_UNKNOWN_ENCODING", operator.createSymbol(18));
        putConstant("\\XML_ERROR_INCORRECT_ENCODING", operator.createSymbol(19));
        putConstant("\\XML_ERROR_UNCLOSED_CDATA_SECTION", operator.createSymbol(20));
        putConstant("\\XML_ERROR_EXTERNAL_ENTITY_HANDLING", operator.createSymbol(21));
        putConstant("\\XML_OPTION_CASE_FOLDING", operator.createSymbol(1));
        putConstant("\\XML_OPTION_TARGET_ENCODING", operator.createSymbol(2));
        putConstant("\\XML_OPTION_SKIP_TAGSTART", operator.createSymbol(3));
        putConstant("\\XML_OPTION_SKIP_WHITE", operator.createSymbol(4));
        putConstant("\\XML_SAX_IMPL", operator.createSymbol("libxml"));
    }

    // function xml_parser_create ($encoding = null) {}
    public static class xml_parser_create implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            PhpResource phpResource = operator.createPhpResource();
            return operator.createSymbol(phpResource);
        }
    }

    // function xml_parser_create_ns ($encoding = null, $separator = ':') {}
    public static class xml_parser_create_ns implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            PhpResource phpResource = operator.createPhpResource();
            return operator.createSymbol(phpResource);
        }
    }

    // function xml_set_object ($parser, &$object) {}
    public static class xml_set_object implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function xml_set_element_handler ($parser, callable $start_element_handler, callable $end_element_handler) {}
    public static class xml_set_element_handler implements PhpCallable {

        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol parserSymbol = SymbolUtils.getArgument(ip, 0);
            Symbol startelementhandlerSymbol = SymbolUtils.getArgument(ip, 1);
            operator.addResourceValue(parserSymbol, STARTELEMENTHANDLER, startelementhandlerSymbol);
            Symbol endelementhandlerSymbol = SymbolUtils.getArgument(ip, 2);
            operator.addResourceValue(parserSymbol, ENDELEMENTHANDLER, endelementhandlerSymbol);
            return operator.bool();
        }
    }

    // function xml_set_character_data_handler ($parser, callable $handler) {}
    public static class xml_set_character_data_handler implements PhpCallable {

        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol parserSymbol = SymbolUtils.getArgument(ip, 0);
            Symbol handlerSymbol = SymbolUtils.getArgument(ip, 1);
            operator.addResourceValue(parserSymbol, XML_SET_CHARACTER_DATA_HANDLER, handlerSymbol);
            return operator.bool();
        }
    }

    // function xml_set_processing_instruction_handler ($parser, callable $handler) {}
    public static class xml_set_processing_instruction_handler implements PhpCallable {

        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol parserSymbol = SymbolUtils.getArgument(ip, 0);
            Symbol handlerSymbol = SymbolUtils.getArgument(ip, 1);
            operator.addResourceValue(parserSymbol, XML_SET_PROCESSING_INSTRUCTION_HANDLER, handlerSymbol);
            return operator.bool();
        }
    }

    // function xml_set_default_handler ($parser, callable $handler) {}
    public static class xml_set_default_handler implements PhpCallable {

        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol parserSymbol = SymbolUtils.getArgument(ip, 0);
            Symbol handlerSymbol = SymbolUtils.getArgument(ip, 1);
            operator.addResourceValue(parserSymbol, XML_SET_DEFAULT_HANDLER, handlerSymbol);
            return operator.bool();
        }
    }

    // function xml_set_unparsed_entity_decl_handler ($parser, callable $handler) {}
    public static class xml_set_unparsed_entity_decl_handler implements PhpCallable {

        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol parserSymbol = SymbolUtils.getArgument(ip, 0);
            Symbol handlerSymbol = SymbolUtils.getArgument(ip, 1);
            operator.addResourceValue(parserSymbol, XML_SET_UNPARSED_ENTITY_DECL_HANDLER, handlerSymbol);
            return operator.bool();
        }
    }

    // function xml_set_notation_decl_handler ($parser, callable $handler) {}
    public static class xml_set_notation_decl_handler implements PhpCallable {

        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol parserSymbol = SymbolUtils.getArgument(ip, 0);
            Symbol handlerSymbol = SymbolUtils.getArgument(ip, 1);
            operator.addResourceValue(parserSymbol, XML_SET_NOTATION_DECL_HANDLER, handlerSymbol);
            return operator.bool();
        }
    }

    // function xml_set_external_entity_ref_handler ($parser, callable $handler) {}
    public static class xml_set_external_entity_ref_handler implements PhpCallable {

        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol parserSymbol = SymbolUtils.getArgument(ip, 0);
            Symbol handlerSymbol = SymbolUtils.getArgument(ip, 1);
            operator.addResourceValue(parserSymbol, XML_SET_EXTERNAL_ENTITY_REF_HANDLER, handlerSymbol);
            return operator.bool();
        }
    }

    // function xml_set_start_namespace_decl_handler ($parser, callable $handler) {}
    public static class xml_set_start_namespace_decl_handler implements PhpCallable {

        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol parserSymbol = SymbolUtils.getArgument(ip, 0);
            Symbol handlerSymbol = SymbolUtils.getArgument(ip, 1);
            operator.addResourceValue(parserSymbol, XML_SET_START_NAMESPACE_DECL_HANDLER, handlerSymbol);
            return operator.bool();
        }
    }

    // function xml_set_end_namespace_decl_handler ($parser, callable $handler) {}
    public static class xml_set_end_namespace_decl_handler implements PhpCallable {

        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol parserSymbol = SymbolUtils.getArgument(ip, 0);
            Symbol handlerSymbol = SymbolUtils.getArgument(ip, 1);
            operator.addResourceValue(parserSymbol, XML_SET_END_NAMESPACE_DECL_HANDLER, handlerSymbol);
            return operator.bool();
        }
    }

    private static final String STARTELEMENTHANDLER = "startelementhandler";
    private static final String ENDELEMENTHANDLER = "endelementhandler";
    private static final String XML_SET_CHARACTER_DATA_HANDLER = "xml_set_character_data_handler";
    private static final String XML_SET_PROCESSING_INSTRUCTION_HANDLER = "xml_set_processing_instruction_handler";
    private static final String XML_SET_DEFAULT_HANDLER = "xml_set_default_handler";
    private static final String XML_SET_UNPARSED_ENTITY_DECL_HANDLER = "xml_set_unparsed_entity_decl_handler";
    private static final String XML_SET_NOTATION_DECL_HANDLER = "xml_set_notation_decl_handler";
    private static final String XML_SET_EXTERNAL_ENTITY_REF_HANDLER = "xml_set_external_entity_ref_handler";
    private static final String XML_SET_START_NAMESPACE_DECL_HANDLER = "xml_set_start_namespace_decl_handler";
    private static final String XML_SET_END_NAMESPACE_DECL_HANDLER = "xml_set_end_namespace_decl_handler";
    private static final String XML_CONTENT = "XML_CONTENT";

    // function xml_parse ($parser, $data, $is_final = false) {}
    public static class xml_parse implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol parserSymbol = SymbolUtils.getArgument(ip, 0);
            Symbol dataSymbol = SymbolUtils.getArgument(ip, 1);
            operator.addResourceValue(parserSymbol, XML_CONTENT, dataSymbol);

            SymbolUtils.callCallable(ip, operator.getResourceValue(parserSymbol, STARTELEMENTHANDLER),
                    Lists.newArrayList(parserSymbol, dataSymbol, operator.array(dataSymbol)));
            SymbolUtils.callCallable(ip, operator.getResourceValue(parserSymbol, ENDELEMENTHANDLER),
                    Lists.newArrayList(parserSymbol, dataSymbol, operator.array(dataSymbol)));
            SymbolUtils.callCallable(ip, operator.getResourceValue(parserSymbol, XML_SET_CHARACTER_DATA_HANDLER),
                    Lists.newArrayList(parserSymbol, dataSymbol));
            SymbolUtils.callCallable(ip,
                    operator.getResourceValue(parserSymbol, XML_SET_PROCESSING_INSTRUCTION_HANDLER),
                    Lists.newArrayList(parserSymbol, dataSymbol, dataSymbol));
            SymbolUtils.callCallable(ip, operator.getResourceValue(parserSymbol, XML_SET_DEFAULT_HANDLER),
                    Lists.newArrayList(parserSymbol, dataSymbol));
            SymbolUtils.callCallable(ip, operator.getResourceValue(parserSymbol, XML_SET_UNPARSED_ENTITY_DECL_HANDLER),
                    Lists.newArrayList(parserSymbol, dataSymbol, dataSymbol, dataSymbol, dataSymbol, dataSymbol));
            SymbolUtils.callCallable(ip, operator.getResourceValue(parserSymbol, XML_SET_NOTATION_DECL_HANDLER),
                    Lists.newArrayList(parserSymbol, dataSymbol, dataSymbol, dataSymbol, dataSymbol));
            SymbolUtils.callCallable(ip, operator.getResourceValue(parserSymbol, XML_SET_EXTERNAL_ENTITY_REF_HANDLER),
                    Lists.newArrayList(parserSymbol, dataSymbol, dataSymbol, dataSymbol, dataSymbol));
            SymbolUtils.callCallable(ip, operator.getResourceValue(parserSymbol, XML_SET_START_NAMESPACE_DECL_HANDLER),
                    Lists.newArrayList(parserSymbol, dataSymbol, dataSymbol));
            SymbolUtils.callCallable(ip, operator.getResourceValue(parserSymbol, XML_SET_END_NAMESPACE_DECL_HANDLER),
                    Lists.newArrayList(parserSymbol, dataSymbol));

            return operator.integer();
        }
    }

    // function xml_parse_into_struct ($parser, $data, array &$values, array &$index = null) {}
    public static class xml_parse_into_struct implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol parserSymbol = SymbolUtils.getArgument(ip, 0);
            Symbol dataSymbol = SymbolUtils.getArgument(ip, 1);
            operator.addResourceValue(parserSymbol, XML_CONTENT, dataSymbol);
            Symbol valuesSymbol = SymbolUtils.getArgumentRef(ip, 2);
            operator.assign(valuesSymbol, operator.array(operator.array(dataSymbol)));
            Symbol indexSymbol = SymbolUtils.getArgumentRef(ip, 3);
            operator.assign(indexSymbol, operator.array(operator.array(operator.integer())));
            return operator.integer();
        }
    }

    // function xml_get_error_code ($parser) {}
    public static class xml_get_error_code implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function xml_error_string ($code) {}
    public static class xml_error_string implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function xml_get_current_line_number ($parser) {}
    public static class xml_get_current_line_number implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function xml_get_current_column_number ($parser) {}
    public static class xml_get_current_column_number implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function xml_get_current_byte_index ($parser) {}
    public static class xml_get_current_byte_index implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function xml_parser_free ($parser) {}
    public static class xml_parser_free implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function xml_parser_set_option ($parser, $option, $value) {}
    public static class xml_parser_set_option implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function xml_parser_get_option ($parser, $option) {}
    public static class xml_parser_get_option implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 1);
        }
    }

    // function utf8_encode ($data) {}
    public static class utf8_encode implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

    // function utf8_decode ($data) {}
    public static class utf8_decode implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

}
