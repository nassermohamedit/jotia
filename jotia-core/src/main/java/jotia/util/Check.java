package jotia.util;

import java.util.function.Supplier;

/**
 * @author nasser
 */
public final class Check {
    private Check() {}

    public static void isNotNull(Object ref) {
        if (ref == null) {
            throw new NullPointerException();
        }
    }

    public static void isNotNull(Object ref, Supplier<RuntimeException> exception) {
        if (ref == null) {
            throw exception.get();
        }
    }

    public static void isNotNull(Object ref, String errerMsg) {
        if (ref == null) {
            throw new NullPointerException(errerMsg);
        }
    }

    public static <T> T notNull2(T ref) {
        if (ref == null) {
            throw new NullPointerException();
        }
        return ref;
    }

    public static <T> T isNotNull2(T ref, Supplier<RuntimeException> exception) {
        if (ref == null) {
            throw exception.get();
        }
        return ref;
    }

    public static <T> T isNotNull2(T ref, String errorMsg) {
        if (ref == null) {
            throw new NullPointerException(errorMsg);
        }
        return ref;
    }

    public static void isStrictlyPositive(int x) {
        if (x <= 0) {
            throw new IllegalArgumentException();
        }
    }

    public static void isStrictlyPositive(int x, Supplier<RuntimeException> exception) {
        if (x <= 0) {
            throw exception.get();
        }
    }

    public static void isPositive(int x) {
        if (x < 0) {
            throw new IllegalArgumentException();
        }
    }


    public static void isPositive(int x, Supplier<RuntimeException> exception) {
        if (x <= 0) {
            throw exception.get();
        }
    }

}
