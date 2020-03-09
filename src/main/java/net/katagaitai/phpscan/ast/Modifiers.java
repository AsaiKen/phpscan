package net.katagaitai.phpscan.ast;

public abstract interface Modifiers
{
	public static final int AccDefault = 0;
	public static final int AccAbstract = 1;
	public static final int AccConstant = 2;
	public static final int AccFinal = 4;
	public static final int AccInterface = 8;
	public static final int AccPrivate = 16;
	public static final int AccProtected = 32;
	public static final int AccPublic = 64;
	public static final int AccStatic = 128;
	public static final int AccReference = 256;
	public static final int AccConst = 512;
	public static final int AccModule = 1024;
	public static final int AccNameSpace = 2048;
	public static final int AccAnnotation = 4096;
	public static final int AccGlobal = 8192;
	public static final int AccUpVar = 16384;
	public static final int AccTestCase = 32768;
	public static final int AccTest = 65536;
	public static final int AccSynthetic = 131072;
	public static final int USER_MODIFIER = 18;
}