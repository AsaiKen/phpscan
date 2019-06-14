package net.katagaitai.phpscan.ast;

public class Flags
        implements Modifiers {
    public static boolean isPrivate(int flags) {
        return ((flags & 0x10) != 0);
    }

    public static boolean isProtected(int flags) {
        return ((flags & 0x20) != 0);
    }

    public static boolean isPublic(int flags) {
        return ((flags & 0x40) != 0);
    }

    public static boolean isStatic(int flags) {
        return ((flags & 0x80) != 0);
    }

    public static boolean isFinal(int flags) {
        return ((flags & 0x4) != 0);
    }

    public static boolean isAbstract(int flags) {
        return ((flags & 0x1) != 0);
    }

    public static boolean isInterface(int flags) {
        return ((flags & 0x8) != 0);
    }

    public static boolean isSynthetic(int flags) {
        return ((flags & 0x20000) != 0);
    }
}