package net.katagaitai.phpscan.ast;

public interface IPHPModifiers extends Modifiers {

    /**
     * Represents non-PHP language element
     */
    int NonPhp = 1 << Modifiers.USER_MODIFIER;

    /**
     * Element that has "@internal" in its PHPDoc
     *
     * @deprecated
     */
    int Internal = 1 << (Modifiers.USER_MODIFIER + 1);

    /**
     * Constructor method
     */
    int Constructor = 1 << (Modifiers.USER_MODIFIER + 2);

    int AccTrait = (1 << Modifiers.USER_MODIFIER + 3);
    int AccMagicProperty = (1 << Modifiers.USER_MODIFIER + 4);

    int AccDeprecated = (1 << Modifiers.USER_MODIFIER + 5);
    int AccAnonymous = (1 << Modifiers.USER_MODIFIER + 6);

    int USER_MODIFIER = Modifiers.USER_MODIFIER + 7;
}
