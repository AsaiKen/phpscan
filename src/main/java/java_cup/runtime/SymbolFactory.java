package java_cup.runtime;

/**
 * Creates the Symbols interface, which CUP uses as default
 *
 * @author Michael Petter
 * @version last updated 27-03-2006
 */

/* *************************************************
  Interface SymbolFactory
  
  interface for creating new symbols  
  You can also use this interface for your own callback hooks
  Declare Your own factory methods for creation of Objects in Your scanner!
 ***************************************************/
public interface SymbolFactory {
    // Factory methods

    /**
     * newSymbol
     * creates a symbol with a value, grouping other symbols with left/right locations;
     * used frequently by the parser to implement non-terminal symbols
     *
     * @param name  Textual name for the Symbol for verbose error messages
     * @param id    enum value associated with this symbol, generated by cup via sym.java
     * @param left  symbol, to take the left location from
     * @param right symbol, to take the right location from
     * @param value value, attached to this symbol
     */
    Symbol newSymbol(String name, int id, Symbol left, Symbol right, Object value);

    /**
     * newSymbol
     * creates a symbol, grouping other symbols with left/right locations;
     * used frequently by the parser to implement non-terminal symbols
     *
     * @param name  Textual name for the Symbol for verbose error messages
     * @param id    enum value associated with this symbol, generated by cup via sym.java
     * @param left  symbol, to take the left location from
     * @param right symbol, to take the right location from
     */
    Symbol newSymbol(String name, int id, Symbol left, Symbol right);

    /**
     * newSymbol
     * creates a symbol for an empty production, taking its location from the Symbol on the left
     *
     * @param name  Textual name for the Symbol for verbose error messages
     * @param id    enum value associated with this symbol, generated by cup via sym.java
     * @param left  symbol, to take the left location from
     * @param value value, attached to this symbol
     */
    Symbol newSymbol(String name, int id, Symbol left, Object value);

    /**
     * newSymbol
     * creates a basic symbol with an attached value;
     * used frequently for terminal symbols like identifiers
     *
     * @param name  Textual name for the Symbol for verbose error messages
     * @param id    enum value associated with this symbol, generated by cup via sym.java
     * @param value value, attached to this symbol
     */
    Symbol newSymbol(String name, int id, Object value);

    /**
     * newSymbol
     * creates a basic symbol;
     * used frequently for terminal symbols, like keywords
     *
     * @param name  Textual name for the Symbol for verbose error messages
     * @param id    enum value associated with this symbol, generated by cup via sym.java
     * @param value value, attached to this symbol
     */
    Symbol newSymbol(String name, int id);

    /**
     * newSymbol
     * creates the start symbol
     *
     * @param name  Textual name for the Symbol for verbose error messages
     * @param id    enum value associated with this symbol, generated by cup via sym.java
     * @param left  symbol, to take the left location from
     * @param right symbol, to take the right location from
     * @param value value, attached to this symbol
     */
    Symbol startSymbol(String name, int id, int state);
}
