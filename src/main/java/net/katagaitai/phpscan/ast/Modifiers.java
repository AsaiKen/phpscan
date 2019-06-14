package net.katagaitai.phpscan.ast;

public interface Modifiers {
    int AccDefault = 0;
    int AccAbstract = 1;
    int AccConstant = 2;
    int AccFinal = 4;
    int AccInterface = 8;
    int AccPrivate = 16;
    int AccProtected = 32;
    int AccPublic = 64;
    int AccStatic = 128;
    int AccReference = 256;
    int AccConst = 512;
    int AccModule = 1024;
    int AccNameSpace = 2048;
    int AccAnnotation = 4096;
    int AccGlobal = 8192;
    int AccUpVar = 16384;
    int AccTestCase = 32768;
    int AccTest = 65536;
    int AccSynthetic = 131072;
    int USER_MODIFIER = 18;
}