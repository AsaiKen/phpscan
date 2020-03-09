package net.katagaitai.phpscan.ast;

public interface IPHPModifiers extends Modifiers {

	/**
	 * Represents non-PHP language element
	 */
	public static final int NonPhp = 1 << Modifiers.USER_MODIFIER;

	/**
	 * Element that has "@internal" in its PHPDoc
	 *
	 * @deprecated
	 */
	public static final int Internal = 1 << (Modifiers.USER_MODIFIER + 1);

	/**
	 * Constructor method
	 */
	public static final int Constructor = 1 << (Modifiers.USER_MODIFIER + 2);

	public static final int AccTrait = (1 << Modifiers.USER_MODIFIER + 3);
	public static final int AccMagicProperty = (1 << Modifiers.USER_MODIFIER + 4);

	public static final int AccDeprecated = (1 << Modifiers.USER_MODIFIER + 5);
	public static final int AccAnonymous = (1 << Modifiers.USER_MODIFIER + 6);

	public static final int USER_MODIFIER = Modifiers.USER_MODIFIER + 7;
}
