package jotia.util;

import java.util.LinkedList;
import java.util.List;

/**
 * @author nasser
 */
public final class Strings {

    private Strings() {
    }

    /**
     * Splits a string into an array of substrings of the specified length.
     * The string is divided into chunks of size {@code len}. If the string's length
     * is not perfectly divisible by {@code len}, the last chunk will contain the
     * remaining characters.
     *
     * @param s the string to be split into parts
     * @param len the length of each part; must be > 0
     * @return an array of substrings, each with a maximum length of {@code len}
     * @throws NullPointerException if {@code s} is null
     * @throws IllegalArgumentException if {@code len} is not greater than zero
     */
    public static String[] parts(String s, int len) {
        Check.isNotNull(s);
        Check.isStrictlyPositive(len);
        if (s.isEmpty()) {
            return new String[0];
        }
        int n = s.length();
        int k = n / len;
        if (n % len > 0) k++;
        String[] parts = new String[k];
        for (int i = 0; i < k; ) {
            parts[i] = substring(s, i * len, ++i * len);
        }
        return parts;
    }

    /**
     * Splits a string into an array of substrings using a delimiter and a maximum chunk length.
     * The string is divided into parts based on the delimiter {@code del}. Each part will not
     * exceed the maximum length specified by {@code len}. That is, if a delimiter is encountered,
     * a substring is created, even if the maximum length is not reached.
     *
     * <p><b>Example:</b></p>
     * <pre>
     * String[] result = parts("one,two,three,four", ',', 5);
     * System.out.println(Arrays.toString(result));
     * // Output: ["one", "two", "three", "four"]
     *
     * String[] result2 = parts("abcdefghijk", '-', 3);
     * System.out.println(Arrays.toString(result2));
     * // Output: ["abc", "def", "ghi", "jk"]
     *
     * String[] result3 = parts("ab-cdefg-hij", '-', 4);
     * System.out.println(Arrays.toString(result3));
     * // Output: ["ab", "cdef", "g", "hij"]
     * </pre>
     *
     * @param s the string to be split into parts
     * @param del the delimiter character used to split the string
     * @param len the maximum length of each part; must be greater than zero
     * @return an array of substrings
     * @throws NullPointerException if {@code s} is null
     * @throws IllegalArgumentException if {@code len} is not greater than zero
     */
    public static String[] parts(String s, char del, int len) {
        Check.isNotNull(s);
        Check.isStrictlyPositive(len);
        int n = s.length();
        List<String> parts = new LinkedList<>();
        int l = 0, r = 0;
        while (r < n) {
            if (r - l >= len) {
                parts.add(s.substring(l, r));
                l = r;
            }
            if (s.charAt(r) == del) {
                if (r > l) parts.add(s.substring(l, r));
                l = ++r;
            } else r++;
        }
        if (l < n) parts.add(s.substring(l));
        return parts.toArray(new String[0]);
    }

    /**
     * Splits a string into an array of substrings using a delimiter and a maximum length,
     * including the delimiter as a separate part in the resulting array.
     * The string is divided into parts based on the delimiter {@code del}. Each part will not
     * exceed the maximum length specified by {@code len}. That is, if a delimiter is encountered,
     * a substring is created, even if the maximum length is not reached.
     *
     * <p><b>Example:</b></p>
     * <pre>
     * String[] result = parts(",one,two,,three,", ',', 5);
     * System.out.println(Arrays.toString(result));
     * // Output: [",", "one", ",", "two", ",", ",", "three", ","]
     *
     * String[] result2 = parts("abcdefghijk", '-', 3);
     * System.out.println(Arrays.toString(result2));
     * // Output: ["abc", "def", "ghi", "jk"]
     *
     * String[] result3 = parts("ab-cdefg-hij", '-', 4);
     * System.out.println(Arrays.toString(result3));
     * // Output: ["ab", "-", "cdef", "g", "-", "hij"]
     * </pre>
     *
     * @param s the string to be split into parts
     * @param del the delimiter character used to split the string
     * @param len the maximum length of each part; must be greater than zero
     * @return an array of substrings
     * @throws NullPointerException if {@code s} is null
     * @throws IllegalArgumentException if {@code len} is not greater than zero
     */
    public static String[] partsWithDelimiter(String s, char del, int len) {
        int n = s.length();
        List<String> parts = new LinkedList<>();
        int l = 0, r = 0;
        while (r < n) {
            if (r - l >= len) {
                parts.add(s.substring(l, r));
                l = r;
            }
            if (s.charAt(r) == del) {
                if (r > l) parts.add(s.substring(l, r));
                parts.add(String.valueOf(del));
                l = ++r;
            } else r++;
        }
        if (l < n) parts.add(s.substring(l));
        return parts.toArray(new String[0]);
    }

    /**
     * Returns a substring of the given string based on the specified range.
     * If {@code r} exceeds the string length, it returns a substring from {@code l}
     * to the end of the string.
     *
     * @param s the original string
     * @param l the starting index of the substring (inclusive)
     * @param r the ending index of the substring (exclusive)
     * @return the resulting substring from {@code l} to {@code r}, or from {@code l}
     *         to the end if {@code r} exceeds the string length
     * @throws NullPointerException if {@code s} is null
     * @throws IndexOutOfBoundsException if {@code l} is negative or greater than the
     *         string length
     */
    public static String substring(String s, int l, int r) {
        if (r < s.length()) return s.substring(l, r);
        return s.substring(l);
    }

    /**
     * Checks if a given sequence {@code s} is a repetition of a base sequence {@code base}.
     * This method verifies if the sequence {@code s} can be constructed by repeating {@code base}
     * one or more times consecutively. The length of {@code s} must be a multiple of the length of {@code base},
     *
     * @param s the sequence to check
     * @param base the base sequence to verify repetition against
     * @return {@code true} if {@code s} is arepetition of {@code base}
     * @throws NullPointerException if {@code s} or {@code base} is null
     */
    public static boolean isRepetitionOf(CharSequence s, CharSequence base) {
        Check.isNotNull(s);
        Check.isNotNull(base);
        int n = s.length();
        int m = base.length();
        if (m > n || n % m != 0) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) != base.charAt(i % m)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Formats a string by replacing occurrences of "%s" with the provided arguments.
     * This method takes a template string containing "%s" placeholders and replaces
     * them sequentially with the corresponding values from the {@code args} array.
     * Literal "%%s" sequences are treated as escaped placeholders and are not replaced.
     * If there are more placeholders than arguments, the unmatched placeholders remain
     * in the final string. Excess arguments are ignored.
     *
     * @param template the string containing "%s" placeholders
     * @param args the arguments to replace the placeholders
     * @return a formatted string with placeholders replaced by argument values
     * @throws NullPointerException if {@code template} or {@code args} is null
     */
    public static String format(String template, Object... args) {
        Check.isNotNull(template);
        Check.isNotNull(args);
        StringBuilder sb = new StringBuilder(template.length() + 32 * args.length);
        int l = 0, fromIndex = 0, i = 0;
        while (i < args.length) {
            // indexOf returns -1 if the fromIndex (l) is outside the valid range
            int r = template.indexOf("%s", fromIndex);
            if (r == -1) break;
            if (r > 0 && template.charAt(r - 1) == '%') {
                fromIndex += 2;
                continue;
            }
            if (r > l) sb.append(template, l, r);
            sb.append(args[i++]);
            l = r + 2;
            fromIndex = l;
        }
        if (l < template.length())
            sb.append(template.substring(l));
        return sb.toString();
    }
}
