package org.toilelibre.libe.inexpressible.util;

public class ToStringArray {

    public static String toString (final Object [] obj) {
        final StringBuffer sb = new StringBuffer ();
        sb.append ("[");
        for (int i = 0; i < 20; i++) {
            sb.append (obj [i]);
            if ((i + 1) < obj.length) {
                sb.append (", ");
            }
        }
        sb.append ("]");
        return sb.toString ();
    }

}
