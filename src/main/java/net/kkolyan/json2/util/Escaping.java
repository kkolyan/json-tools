package net.kkolyan.json2.util;

/**
 * @author n.plekhanov
 */
public class Escaping {
    static public String unescapedString(String image) {
        image = image.substring(1, image.length() - 1);

        int l = image.length();
        StringBuffer sb = new StringBuffer(l);

        /*
         *
         * \XXX - The character specified by up to three octal digits XXX
         * between 0 and 377 ( example: \251 ) \xXX - The character specified by
         * the two hexadecimal digits XX between 00 and FF ( example: \xA9 ) \
         * uXXXX - The Unicode character specified by the four hexadecimal
         * digits XXXX ( example: \u00A9 )
         */
        for (int i = 0; i < l; i++) {
            char c = image.charAt(i);
            if ((c == '\\') && (i + 1 < l)) {
                i++;
                c = image.charAt(i);
                if (c == 'n') {
                    c = '\n';
                } else if (c == 'b') {
                    c = '\b';
                } else if (c == 'f') {
                    c = '\f';
                } else if (c == 'r') {
                    c = '\r';
                } else if (c == 't') {
                    c = '\t';
                } else if (c == 'x' && l == 4) {
                    c = (char) (hexval(image.charAt(i + 1)) << 4 | hexval(image
                            .charAt(i + 1)));
                    i += 2;
                } else if (c == 'u') {
                    c = (char) (hexval(image.charAt(i + 1)) << 12
                            | hexval(image.charAt(i + 2)) << 8
                            | hexval(image.charAt(i + 3)) << 4 | hexval(image
                            .charAt(i + 4)));
                    i += 4;
                } else if ((c >= '0') && (c <= '7') && l == 4) {
                    c = (char) (octval(image.charAt(i)));
                    if ((image.length() > i) && (image.charAt(i + 1) >= '0')
                            && (image.charAt(i + 1) <= '7')) {
                        i++;
                        c = (char) ((c << 4) | octval(image.charAt(i)));
                    }
                }
            }
            sb.append(c);
        }
        return sb.toString();
    }

    static public String escapedString(String image, char quoteChar) {
        int l = image.length();
        StringBuffer sb = new StringBuffer(l);

        for (int i = 0; i < l; i++) {
            char c = image.charAt(i);

            if (c == '\n') {
                sb.append("\\n");
            } else if (c == '\r') {
                sb.append("\\r");
            } else if (c == '\b') {
                sb.append("\\b");
            } else if (c == '\f') {
                sb.append("\\f");
            } else if (c == '\t') {
                sb.append("\\t");
            } else if (c == quoteChar) {
                sb.append("\\");
                sb.append(quoteChar);
            } else {
                // PENDING(uwe): unicode escapes, hex escapes etc
                sb.append(c);
            }
        }
        return sb.toString();
    }

    static final int hexval(char c) {
        switch (c) {
        case '0':
            return 0;
        case '1':
            return 1;
        case '2':
            return 2;
        case '3':
            return 3;
        case '4':
            return 4;
        case '5':
            return 5;
        case '6':
            return 6;
        case '7':
            return 7;
        case '8':
            return 8;
        case '9':
            return 9;

        case 'a':
        case 'A':
            return 10;
        case 'b':
        case 'B':
            return 11;
        case 'c':
        case 'C':
            return 12;
        case 'd':
        case 'D':
            return 13;
        case 'e':
        case 'E':
            return 14;
        case 'f':
        case 'F':
            return 15;
        }

        throw new IllegalStateException("Illegal hex or unicode constant"); // Should
        // never
        // come
        // here
    }

    static final int octval(char c) {
        switch (c) {
        case '0':
            return 0;
        case '1':
            return 1;
        case '2':
            return 2;
        case '3':
            return 3;
        case '4':
            return 4;
        case '5':
            return 5;
        case '6':
            return 6;
        case '7':
            return 7;
        case '8':
            return 8;
        case '9':
            return 9;

        case 'a':
        case 'A':
            return 10;
        case 'b':
        case 'B':
            return 11;
        case 'c':
        case 'C':
            return 12;
        case 'd':
        case 'D':
            return 13;
        case 'e':
        case 'E':
            return 14;
        case 'f':
        case 'F':
            return 15;
        }

        throw new IllegalStateException("Illegal octal constant"); // Should
        // never
        // come here
    }
}
