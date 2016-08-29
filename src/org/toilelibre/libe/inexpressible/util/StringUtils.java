package org.toilelibre.libe.inexpressible.util;

public class StringUtils {

    public static int countMatches (final String string, final String pattern) {
        int i = 0;
        int j = 0;
        while (string.indexOf (pattern, j) != -1) {
            i++;
            j = string.indexOf (pattern, j) + 1;
        }
        return i;
    }

}
